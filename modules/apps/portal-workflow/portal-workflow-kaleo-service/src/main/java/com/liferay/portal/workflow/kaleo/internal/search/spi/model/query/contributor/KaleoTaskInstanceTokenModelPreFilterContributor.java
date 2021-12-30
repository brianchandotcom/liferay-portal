/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.workflow.kaleo.internal.search.spi.model.query.contributor;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.model.UserGroupGroupRole;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.QueryFilter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.search.filter.DateRangeFilterBuilder;
import com.liferay.portal.search.filter.FilterBuilders;
import com.liferay.portal.search.spi.model.query.contributor.ModelPreFilterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchSettings;
import com.liferay.portal.workflow.kaleo.internal.search.KaleoTaskInstanceTokenField;
import com.liferay.portal.workflow.kaleo.service.persistence.KaleoTaskInstanceTokenQuery;

import java.text.Format;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken",
	service = ModelPreFilterContributor.class
)
public class KaleoTaskInstanceTokenModelPreFilterContributor
	implements ModelPreFilterContributor {

	@Override
	public void contribute(
		BooleanFilter booleanFilter, ModelSearchSettings modelSearchSettings,
		SearchContext searchContext) {

		KaleoTaskInstanceTokenQuery kaleoTaskInstanceTokenQuery =
			(KaleoTaskInstanceTokenQuery)searchContext.getAttribute(
				"kaleoTaskInstanceTokenQuery");

		if (kaleoTaskInstanceTokenQuery == null) {
			return;
		}

		BooleanFilter innerBooleanFilter = new BooleanFilter();

		appendAssetTitleTerm(
			innerBooleanFilter, kaleoTaskInstanceTokenQuery, searchContext);
		_appendAssetTypeTerm(innerBooleanFilter, kaleoTaskInstanceTokenQuery);
		_appendAssigneeClassIdsNameTerm(
			innerBooleanFilter, kaleoTaskInstanceTokenQuery);
		_appendAssigneeClassPKsTerm(
			innerBooleanFilter, kaleoTaskInstanceTokenQuery);
		_appendTaskNameTerm(innerBooleanFilter, kaleoTaskInstanceTokenQuery);

		if (innerBooleanFilter.hasClauses()) {
			booleanFilter.add(innerBooleanFilter, BooleanClauseOccur.MUST);
		}

		appendClassNameIdsTerm(booleanFilter, kaleoTaskInstanceTokenQuery);
		appendCompletedTerm(booleanFilter, kaleoTaskInstanceTokenQuery);
		_appendKaleoDefinitionIdTerm(
			booleanFilter, kaleoTaskInstanceTokenQuery);
		_appendKaleoInstanceIdsTerm(booleanFilter, kaleoTaskInstanceTokenQuery);
		_appendRoleIdsTerm(booleanFilter, kaleoTaskInstanceTokenQuery);
		_appendSearchByUserRolesTerm(
			booleanFilter, kaleoTaskInstanceTokenQuery);

		if (_appendSearchCriteria(kaleoTaskInstanceTokenQuery)) {
			_appendAssetPrimaryKeyTerm(
				booleanFilter, kaleoTaskInstanceTokenQuery);
			_appendDueDateRangeTerm(booleanFilter, kaleoTaskInstanceTokenQuery);
		}
	}

	protected void appendAssetTitleTerm(
		BooleanFilter booleanFilter,
		KaleoTaskInstanceTokenQuery kaleoTaskInstanceTokenQuery,
		SearchContext searchContext) {

		String assetTitle = kaleoTaskInstanceTokenQuery.getAssetTitle();

		if (Validator.isNull(assetTitle)) {
			return;
		}

		String assetTitleLocalizedName = LocalizationUtil.getLocalizedName(
			KaleoTaskInstanceTokenField.ASSET_TITLE,
			searchContext.getLanguageId());

		BooleanQuery booleanQuery = new BooleanQueryImpl();

		try {
			booleanQuery.addTerm(assetTitleLocalizedName, assetTitle);
		}
		catch (ParseException parseException) {
			throw new RuntimeException(parseException);
		}

		booleanFilter.add(new QueryFilter(booleanQuery));
	}

	protected void appendClassNameIdsTerm(
		BooleanFilter booleanFilter,
		KaleoTaskInstanceTokenQuery kaleoTaskInstanceTokenQuery) {

		if (!kaleoTaskInstanceTokenQuery.isSearchByActiveWorkflowHandlers()) {
			return;
		}

		TermsFilter classNameIdsTermsFilter = new TermsFilter(
			Field.CLASS_NAME_ID);

		classNameIdsTermsFilter.addValues(
			Stream.of(
				WorkflowHandlerRegistryUtil.getWorkflowHandlers()
			).flatMap(
				List::stream
			).map(
				workflowHandler -> portal.getClassNameId(
					workflowHandler.getClassName())
			).map(
				String::valueOf
			).toArray(
				String[]::new
			));

		booleanFilter.add(classNameIdsTermsFilter, BooleanClauseOccur.MUST);
	}

	protected void appendCompletedTerm(
		BooleanFilter booleanFilter,
		KaleoTaskInstanceTokenQuery kaleoTaskInstanceTokenQuery) {

		Boolean completed = kaleoTaskInstanceTokenQuery.isCompleted();

		if (completed == null) {
			return;
		}

		booleanFilter.addRequiredTerm(
			KaleoTaskInstanceTokenField.COMPLETED, completed);
	}

	@Reference
	protected FilterBuilders filterBuilders;

	@Reference
	protected GroupLocalService groupLocalService;

	@Reference
	protected Portal portal;

	@Reference
	protected RoleLocalService roleLocalService;

	@Reference
	protected UserGroupGroupRoleLocalService userGroupGroupRoleLocalService;

	@Reference
	protected UserGroupLocalService userGroupLocalService;

	@Reference
	protected UserGroupRoleLocalService userGroupRoleLocalService;

	@Reference
	protected UserLocalService userLocalService;

	private void _appendAssetPrimaryKeyTerm(
		BooleanFilter booleanFilter,
		KaleoTaskInstanceTokenQuery kaleoTaskInstanceTokenQuery) {

		Long[] assetPrimaryKeys =
			kaleoTaskInstanceTokenQuery.getAssetPrimaryKeys();

		if (ArrayUtil.isEmpty(assetPrimaryKeys)) {
			return;
		}

		TermsFilter assetPrimaryKeyTermsFilter = new TermsFilter(
			Field.CLASS_PK);

		assetPrimaryKeyTermsFilter.addValues(
			Stream.of(
				assetPrimaryKeys
			).map(
				String::valueOf
			).toArray(
				String[]::new
			));

		booleanFilter.add(assetPrimaryKeyTermsFilter, BooleanClauseOccur.MUST);
	}

	private void _appendAssetTypeTerm(
		BooleanFilter booleanFilter,
		KaleoTaskInstanceTokenQuery kaleoTaskInstanceTokenQuery) {

		String[] assetTypes = kaleoTaskInstanceTokenQuery.getAssetTypes();

		if (ListUtil.isNull(ListUtil.fromArray(assetTypes))) {
			return;
		}

		BooleanQuery booleanQuery = new BooleanQueryImpl();

		for (String assetType : assetTypes) {
			try {
				booleanQuery.addTerm(
					KaleoTaskInstanceTokenField.CLASS_NAME, assetType);
			}
			catch (ParseException parseException) {
				throw new RuntimeException(parseException);
			}
		}

		booleanFilter.add(new QueryFilter(booleanQuery));
	}

	private void _appendAssigneeClassIdsNameTerm(
		BooleanFilter booleanFilter,
		KaleoTaskInstanceTokenQuery kaleoTaskInstanceTokenQuery) {

		String assigneeClassName =
			kaleoTaskInstanceTokenQuery.getAssigneeClassName();

		if (Validator.isNull(assigneeClassName)) {
			return;
		}

		TermFilter assigneeClassNameIdsTermFilter = new TermFilter(
			KaleoTaskInstanceTokenField.ASSIGNEE_CLASS_NAME_IDS,
			String.valueOf(portal.getClassNameId(assigneeClassName)));

		booleanFilter.add(assigneeClassNameIdsTermFilter);
	}

	private void _appendAssigneeClassPKsTerm(
		BooleanFilter booleanFilter,
		KaleoTaskInstanceTokenQuery kaleoTaskInstanceTokenQuery) {

		Long[] assigneeClassPKs =
			kaleoTaskInstanceTokenQuery.getAssigneeClassPKs();

		if (ArrayUtil.isEmpty(assigneeClassPKs)) {
			return;
		}

		TermsFilter assigneeClassPKsTermsFilter = new TermsFilter(
			KaleoTaskInstanceTokenField.ASSIGNEE_CLASS_PKS);

		assigneeClassPKsTermsFilter.addValues(
			Stream.of(
				assigneeClassPKs
			).map(
				String::valueOf
			).toArray(
				String[]::new
			));

		booleanFilter.add(assigneeClassPKsTermsFilter);
	}

	private void _appendDueDateRangeTerm(
		BooleanFilter booleanFilter,
		KaleoTaskInstanceTokenQuery kaleoTaskInstanceTokenQuery) {

		Date dueDateGT = kaleoTaskInstanceTokenQuery.getDueDateGT();
		Date dueDateLT = kaleoTaskInstanceTokenQuery.getDueDateLT();

		if ((dueDateGT == null) && (dueDateLT == null)) {
			return;
		}

		String formatPattern = PropsUtil.get(
			PropsKeys.INDEX_DATE_FORMAT_PATTERN);

		Format dateFormat = FastDateFormatFactoryUtil.getSimpleDateFormat(
			formatPattern);

		DateRangeFilterBuilder dueDateRangeFilterBuilder =
			filterBuilders.dateRangeFilterBuilder();

		dueDateRangeFilterBuilder.setFieldName(
			KaleoTaskInstanceTokenField.DUE_DATE);

		if (dueDateGT != null) {
			dueDateRangeFilterBuilder.setFrom(dateFormat.format(dueDateGT));
		}

		if (dueDateLT != null) {
			dueDateRangeFilterBuilder.setTo(dateFormat.format(dueDateLT));
		}

		booleanFilter.add(
			dueDateRangeFilterBuilder.build(), BooleanClauseOccur.MUST);
	}

	private void _appendKaleoDefinitionIdTerm(
		BooleanFilter booleanFilter,
		KaleoTaskInstanceTokenQuery kaleoTaskInstanceTokenQuery) {

		Long kaleoDefinitionId =
			kaleoTaskInstanceTokenQuery.getKaleoDefinitionId();

		if (kaleoDefinitionId == null) {
			return;
		}

		booleanFilter.addRequiredTerm(
			KaleoTaskInstanceTokenField.KALEO_DEFINITION_ID, kaleoDefinitionId);
	}

	private void _appendKaleoInstanceIdsTerm(
		BooleanFilter booleanFilter,
		KaleoTaskInstanceTokenQuery kaleoTaskInstanceTokenQuery) {

		Long[] kaleoInstanceIds =
			kaleoTaskInstanceTokenQuery.getKaleoInstanceIds();

		if (ArrayUtil.isEmpty(kaleoInstanceIds)) {
			return;
		}

		TermsFilter kaleoInstanceIdsTermsFilter = new TermsFilter(
			KaleoTaskInstanceTokenField.KALEO_INSTANCE_ID);

		kaleoInstanceIdsTermsFilter.addValues(
			Stream.of(
				kaleoInstanceIds
			).map(
				String::valueOf
			).toArray(
				String[]::new
			));

		booleanFilter.add(kaleoInstanceIdsTermsFilter, BooleanClauseOccur.MUST);
	}

	private void _appendRoleIdsTerm(
		BooleanFilter booleanFilter,
		KaleoTaskInstanceTokenQuery kaleoTaskInstanceTokenQuery) {

		Boolean searchByUserRoles =
			kaleoTaskInstanceTokenQuery.isSearchByUserRoles();

		if (searchByUserRoles != null) {
			return;
		}

		List<Long> roleIds = kaleoTaskInstanceTokenQuery.getRoleIds();

		if (ListUtil.isEmpty(roleIds)) {
			return;
		}

		TermsFilter assigneeClassPKsTermsFilter = new TermsFilter(
			KaleoTaskInstanceTokenField.ASSIGNEE_CLASS_PKS);

		assigneeClassPKsTermsFilter.addValues(
			Stream.of(
				roleIds
			).flatMap(
				List::stream
			).map(
				String::valueOf
			).toArray(
				String[]::new
			));

		booleanFilter.add(assigneeClassPKsTermsFilter, BooleanClauseOccur.MUST);
	}

	private void _appendSearchByUserRolesTerm(
		BooleanFilter booleanFilter,
		KaleoTaskInstanceTokenQuery kaleoTaskInstanceTokenQuery) {

		Boolean searchByUserRoles =
			kaleoTaskInstanceTokenQuery.isSearchByUserRoles();

		if (searchByUserRoles == null) {
			return;
		}

		if (searchByUserRoles) {
			List<Long> roleIds = _getSearchByUserRoleIds(
				kaleoTaskInstanceTokenQuery.getUserId());

			Map<Long, Set<Long>> roleIdGroupIdsMap = _getRoleIdGroupIdsMap(
				kaleoTaskInstanceTokenQuery);

			if (roleIds.isEmpty() && roleIdGroupIdsMap.isEmpty()) {
				return;
			}

			BooleanFilter searchByRolesBooleanFilter = new BooleanFilter();

			TermFilter rolesClassNameIdTermFilter = new TermFilter(
				KaleoTaskInstanceTokenField.ASSIGNEE_CLASS_NAME_IDS,
				String.valueOf(portal.getClassNameId(Role.class)));

			searchByRolesBooleanFilter.add(
				rolesClassNameIdTermFilter, BooleanClauseOccur.MUST);

			BooleanFilter innerSearchByRolesBooleanFilter = new BooleanFilter();

			searchByRolesBooleanFilter.add(
				innerSearchByRolesBooleanFilter, BooleanClauseOccur.MUST);

			innerSearchByRolesBooleanFilter.add(
				_createRoleAssigneeClassPKBooleanFilter(roleIds));

			if (!roleIdGroupIdsMap.isEmpty()) {
				BooleanFilter roleIdGroupIdsMapBooleanFilter =
					_createRoleIdGroupIdsMapBooleanFilter(roleIdGroupIdsMap);

				BooleanClauseOccur roleIdGroupIdsMapBooleanClauseOccur =
					BooleanClauseOccur.SHOULD;

				if (roleIds.isEmpty()) {
					roleIdGroupIdsMapBooleanClauseOccur =
						BooleanClauseOccur.MUST;
				}

				innerSearchByRolesBooleanFilter.add(
					roleIdGroupIdsMapBooleanFilter,
					roleIdGroupIdsMapBooleanClauseOccur);
			}

			booleanFilter.add(
				searchByRolesBooleanFilter, BooleanClauseOccur.MUST);
		}
		else {
			TermFilter assigneeClassNameIdsTermFilter = new TermFilter(
				KaleoTaskInstanceTokenField.ASSIGNEE_CLASS_NAME_IDS,
				String.valueOf(portal.getClassNameId(User.class)));

			booleanFilter.add(
				assigneeClassNameIdsTermFilter, BooleanClauseOccur.MUST);

			TermFilter assigneeClassPKsTermFilter = new TermFilter(
				KaleoTaskInstanceTokenField.ASSIGNEE_CLASS_PKS,
				String.valueOf(kaleoTaskInstanceTokenQuery.getUserId()));

			booleanFilter.add(
				assigneeClassPKsTermFilter, BooleanClauseOccur.MUST);
		}
	}

	private boolean _appendSearchCriteria(
		KaleoTaskInstanceTokenQuery kaleoTaskInstanceTokenQuery) {

		if (ArrayUtil.isNotEmpty(
				kaleoTaskInstanceTokenQuery.getAssetPrimaryKeys()) ||
			ArrayUtil.isNotEmpty(kaleoTaskInstanceTokenQuery.getAssetTypes()) ||
			(kaleoTaskInstanceTokenQuery.getDueDateGT() != null) ||
			(kaleoTaskInstanceTokenQuery.getDueDateLT() != null)) {

			return true;
		}

		return false;
	}

	private void _appendTaskNameTerm(
		BooleanFilter booleanFilter,
		KaleoTaskInstanceTokenQuery kaleoTaskInstanceTokenQuery) {

		String[] taskNames = kaleoTaskInstanceTokenQuery.getTaskNames();

		if (ListUtil.isNull(ListUtil.fromArray(taskNames))) {
			return;
		}

		BooleanQuery booleanQuery = new BooleanQueryImpl();

		for (String taskName : taskNames) {
			try {
				booleanQuery.addTerm(
					KaleoTaskInstanceTokenField.TASK_NAME, taskName);
			}
			catch (ParseException parseException) {
				throw new RuntimeException(parseException);
			}
		}

		booleanFilter.add(new QueryFilter(booleanQuery));
	}

	private BooleanFilter _createRoleAssigneeClassPKBooleanFilter(
		List<Long> roleIds) {

		BooleanFilter roleClassPKBooleanFilter = new BooleanFilter();

		if (ListUtil.isEmpty(roleIds)) {
			return roleClassPKBooleanFilter;
		}

		TermsFilter assigneeClassPKsTermsFilter = new TermsFilter(
			KaleoTaskInstanceTokenField.ASSIGNEE_CLASS_PKS);

		assigneeClassPKsTermsFilter.addValues(
			Stream.of(
				roleIds
			).flatMap(
				List::stream
			).map(
				String::valueOf
			).toArray(
				String[]::new
			));

		roleClassPKBooleanFilter.add(assigneeClassPKsTermsFilter);

		return roleClassPKBooleanFilter;
	}

	private BooleanFilter _createRoleIdGroupIdsMapBooleanFilter(
		Map<Long, Set<Long>> roleIdGroupIdsMap) {

		BooleanFilter roleIdGroupIdsMapBooleanFilter = new BooleanFilter();

		for (Map.Entry<Long, Set<Long>> entry : roleIdGroupIdsMap.entrySet()) {
			BooleanFilter roleIdGroupIdsBooleanFilter = new BooleanFilter();

			roleIdGroupIdsBooleanFilter.add(
				new TermFilter(
					KaleoTaskInstanceTokenField.ASSIGNEE_CLASS_PKS,
					String.valueOf(entry.getKey())),
				BooleanClauseOccur.MUST);

			if (SetUtil.isNotEmpty(entry.getValue())) {
				TermsFilter assigneeGroupIdsTermsFilter = new TermsFilter(
					KaleoTaskInstanceTokenField.ASSIGNEE_GROUP_IDS);

				assigneeGroupIdsTermsFilter.addValues(
					Stream.of(
						entry.getValue()
					).flatMap(
						Set::stream
					).map(
						String::valueOf
					).toArray(
						String[]::new
					));

				roleIdGroupIdsBooleanFilter.add(
					assigneeGroupIdsTermsFilter, BooleanClauseOccur.MUST);
			}

			roleIdGroupIdsMapBooleanFilter.add(
				roleIdGroupIdsBooleanFilter, BooleanClauseOccur.SHOULD);
		}

		return roleIdGroupIdsMapBooleanFilter;
	}

	private Map<Long, Set<Long>> _getRoleIdGroupIdsMap(
		KaleoTaskInstanceTokenQuery kaleoTaskInstanceTokenQuery) {

		Map<Long, Set<Long>> roleIdGroupIdsMap = new HashMap<>();

		List<UserGroupRole> userGroupRoles =
			userGroupRoleLocalService.getUserGroupRoles(
				kaleoTaskInstanceTokenQuery.getUserId());

		for (UserGroupRole userGroupRole : userGroupRoles) {
			_mapRoleIdGroupId(
				userGroupRole.getRoleId(), userGroupRole.getGroupId(),
				roleIdGroupIdsMap);
		}

		List<UserGroupGroupRole> userGroupGroupRoles = _getUserGroupGroupRoles(
			kaleoTaskInstanceTokenQuery.getUserId());

		for (UserGroupGroupRole userGroupGroupRole : userGroupGroupRoles) {
			_mapRoleIdGroupId(
				userGroupGroupRole.getRoleId(), userGroupGroupRole.getGroupId(),
				roleIdGroupIdsMap);
		}

		_mapSiteMemberRoleIdGroupId(
			kaleoTaskInstanceTokenQuery.getCompanyId(),
			kaleoTaskInstanceTokenQuery.getUserId(), roleIdGroupIdsMap);

		return roleIdGroupIdsMap;
	}

	private List<Long> _getSearchByUserRoleIds(long userId) {
		try {
			List<Role> roles = roleLocalService.getUserRoles(userId);

			List<Group> groups = new ArrayList<>();

			User user = userLocalService.getUserById(userId);

			groups.addAll(user.getGroups());
			groups.addAll(
				groupLocalService.getOrganizationsGroups(
					user.getOrganizations()));
			groups.addAll(
				groupLocalService.getOrganizationsRelatedGroups(
					user.getOrganizations()));
			groups.addAll(
				groupLocalService.getUserGroupsGroups(user.getUserGroups()));
			groups.addAll(
				groupLocalService.getUserGroupsRelatedGroups(
					user.getUserGroups()));

			for (Group group : groups) {
				roles.addAll(
					roleLocalService.getGroupRoles(group.getGroupId()));
			}

			Stream<Role> stream = roles.parallelStream();

			return stream.map(
				Role::getRoleId
			).collect(
				Collectors.toList()
			);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}

		return Collections.emptyList();
	}

	private List<UserGroupGroupRole> _getUserGroupGroupRoles(long userId) {
		List<UserGroupGroupRole> userGroupGroupRoles = new ArrayList<>();

		List<UserGroup> userGroups = userGroupLocalService.getUserUserGroups(
			userId);

		for (UserGroup userGroup : userGroups) {
			userGroupGroupRoles.addAll(
				userGroupGroupRoleLocalService.getUserGroupGroupRoles(
					userGroup.getUserGroupId()));
		}

		return userGroupGroupRoles;
	}

	private void _mapRoleIdGroupId(
		long roleId, long groupId, Map<Long, Set<Long>> roleIdGroupIdsMap) {

		Set<Long> groupIds = roleIdGroupIdsMap.get(roleId);

		if (groupIds == null) {
			groupIds = new TreeSet<>();

			roleIdGroupIdsMap.put(roleId, groupIds);
		}

		groupIds.add(groupId);
	}

	private void _mapSiteMemberRoleIdGroupId(
		long companyId, long userId, Map<Long, Set<Long>> roleIdGroupIdsMap) {

		try {
			Role siteMemberRole = roleLocalService.getRole(
				companyId, RoleConstants.SITE_MEMBER);

			User user = userLocalService.getUserById(userId);

			for (Long groupId : user.getGroupIds()) {
				_mapRoleIdGroupId(
					siteMemberRole.getRoleId(), groupId, roleIdGroupIdsMap);
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		KaleoTaskInstanceTokenModelPreFilterContributor.class);

}