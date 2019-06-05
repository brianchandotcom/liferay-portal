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
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
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

		appendAssigneeClassIdsNameTerm(
			booleanFilter, kaleoTaskInstanceTokenQuery);
		appendAssigneeClassPKsTerm(booleanFilter, kaleoTaskInstanceTokenQuery);
		appendCompletedTerm(booleanFilter, kaleoTaskInstanceTokenQuery);
		appendKaleoInstanceIdTerm(booleanFilter, kaleoTaskInstanceTokenQuery);
		appendRoleIdsTerm(booleanFilter, kaleoTaskInstanceTokenQuery);
		appendSearchByUserRolesTerm(booleanFilter, kaleoTaskInstanceTokenQuery);

		if (appendSearchCriteria(kaleoTaskInstanceTokenQuery)) {
			appendAssetPrimaryKeyTerm(
				booleanFilter, kaleoTaskInstanceTokenQuery);
			appendDueDateRangeTerm(booleanFilter, kaleoTaskInstanceTokenQuery);
		}
	}

	protected void appendAssetPrimaryKeyTerm(
		BooleanFilter booleanFilter,
		KaleoTaskInstanceTokenQuery kaleoTaskInstanceTokenQuery) {

		Long[] assetPrimaryKeys =
			kaleoTaskInstanceTokenQuery.getAssetPrimaryKeys();

		if (ArrayUtil.isEmpty(assetPrimaryKeys)) {
			return;
		}

		for (Long assetPrimaryKey : assetPrimaryKeys) {
			booleanFilter.addTerm(Field.CLASS_PK, assetPrimaryKey);
		}
	}

	protected void appendAssigneeClassIdsNameTerm(
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

		booleanFilter.add(
			assigneeClassNameIdsTermFilter, BooleanClauseOccur.MUST);
	}

	protected void appendAssigneeClassPKsTerm(
		BooleanFilter booleanFilter,
		KaleoTaskInstanceTokenQuery kaleoTaskInstanceTokenQuery) {

		Long assigneeClassPK = kaleoTaskInstanceTokenQuery.getAssigneeClassPK();

		if (assigneeClassPK == null) {
			return;
		}

		TermFilter assigneeClassPKsTermFilter = new TermFilter(
			KaleoTaskInstanceTokenField.ASSIGNEE_CLASS_PKS,
			String.valueOf(assigneeClassPK));

		booleanFilter.add(assigneeClassPKsTermFilter, BooleanClauseOccur.MUST);
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

	protected void appendDueDateRangeTerm(
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

	protected void appendKaleoInstanceIdTerm(
		BooleanFilter booleanFilter,
		KaleoTaskInstanceTokenQuery kaleoTaskInstanceTokenQuery) {

		Long kaleoInstanceId = kaleoTaskInstanceTokenQuery.getKaleoInstanceId();

		if (kaleoInstanceId == null) {
			return;
		}

		booleanFilter.addRequiredTerm(
			KaleoTaskInstanceTokenField.KALEO_INSTANCE_ID, kaleoInstanceId);
	}

	protected void appendRoleIdsTerm(
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

		BooleanFilter roleIdsBooleanFilter = new BooleanFilter();

		for (Long roleId : roleIds) {
			roleIdsBooleanFilter.add(
				new TermFilter(
					KaleoTaskInstanceTokenField.ASSIGNEE_CLASS_PKS,
					roleId.toString()),
				BooleanClauseOccur.SHOULD);
		}

		booleanFilter.add(roleIdsBooleanFilter, BooleanClauseOccur.MUST);
	}

	protected void appendSearchByUserRolesTerm(
		BooleanFilter booleanFilter,
		KaleoTaskInstanceTokenQuery kaleoTaskInstanceTokenQuery) {

		Boolean searchByUserRoles =
			kaleoTaskInstanceTokenQuery.isSearchByUserRoles();

		if (searchByUserRoles == null) {
			return;
		}

		if (searchByUserRoles) {
			List<Long> roleIds = getSearchByUserRoleIds(
				kaleoTaskInstanceTokenQuery.getUserId());

			Map<Long, Set<Long>> roleIdGroupIdsMap = getRoleIdGroupIdsMap(
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
				createRoleAssigneeClassPKBooleanFilter(roleIds));

			if (!roleIdGroupIdsMap.isEmpty()) {
				BooleanFilter roleIdGroupIdsMapBooleanFilter =
					createRoleIdGroupIdsMapBooleanFilter(roleIdGroupIdsMap);

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

	protected boolean appendSearchCriteria(
		KaleoTaskInstanceTokenQuery kaleoTaskInstanceTokenQuery) {

		if (ArrayUtil.isNotEmpty(
				kaleoTaskInstanceTokenQuery.getAssetPrimaryKeys())) {

			return true;
		}

		if (ArrayUtil.isNotEmpty(kaleoTaskInstanceTokenQuery.getAssetTypes())) {
			return true;
		}

		if (kaleoTaskInstanceTokenQuery.getDueDateGT() != null) {
			return true;
		}

		if (kaleoTaskInstanceTokenQuery.getDueDateLT() != null) {
			return true;
		}

		return false;
	}

	protected BooleanFilter createRoleAssigneeClassPKBooleanFilter(
		List<Long> roleIds) {

		BooleanFilter roleClassPKBooleanFilter = new BooleanFilter();

		for (Long roleId : roleIds) {
			roleClassPKBooleanFilter.add(
				new TermFilter(
					KaleoTaskInstanceTokenField.ASSIGNEE_CLASS_PKS,
					String.valueOf(roleId)));
		}

		return roleClassPKBooleanFilter;
	}

	protected BooleanFilter createRoleIdGroupIdsMapBooleanFilter(
		Map<Long, Set<Long>> roleIdGroupIdsMap) {

		BooleanFilter roleIdGroupIdsMapBooleanFilter = new BooleanFilter();

		for (Map.Entry<Long, Set<Long>> entry : roleIdGroupIdsMap.entrySet()) {
			BooleanFilter roleIdGroupIdsBooleanFilter = new BooleanFilter();

			roleIdGroupIdsBooleanFilter.add(
				new TermFilter(
					KaleoTaskInstanceTokenField.ASSIGNEE_CLASS_PKS,
					String.valueOf(entry.getKey())),
				BooleanClauseOccur.MUST);

			BooleanFilter assigneeGroupIdsBooleanFilter = new BooleanFilter();

			for (Long assigneeGroupId : entry.getValue()) {
				assigneeGroupIdsBooleanFilter.add(
					new TermFilter(
						KaleoTaskInstanceTokenField.ASSIGNEE_GROUP_IDS,
						String.valueOf(assigneeGroupId)));
			}

			roleIdGroupIdsBooleanFilter.add(
				assigneeGroupIdsBooleanFilter, BooleanClauseOccur.MUST);

			roleIdGroupIdsMapBooleanFilter.add(
				roleIdGroupIdsBooleanFilter, BooleanClauseOccur.SHOULD);
		}

		return roleIdGroupIdsMapBooleanFilter;
	}

	protected Map<Long, Set<Long>> getRoleIdGroupIdsMap(
		KaleoTaskInstanceTokenQuery kaleoTaskInstanceTokenQuery) {

		Map<Long, Set<Long>> roleIdGroupIdsMap = new HashMap<>();

		List<UserGroupRole> userGroupRoles =
			userGroupRoleLocalService.getUserGroupRoles(
				kaleoTaskInstanceTokenQuery.getUserId());

		for (UserGroupRole userGroupRole : userGroupRoles) {
			mapRoleIdGroupId(
				userGroupRole.getRoleId(), userGroupRole.getGroupId(),
				roleIdGroupIdsMap);
		}

		List<UserGroupGroupRole> userGroupGroupRoles = getUserGroupGroupRoles(
			kaleoTaskInstanceTokenQuery.getUserId());

		for (UserGroupGroupRole userGroupGroupRole : userGroupGroupRoles) {
			mapRoleIdGroupId(
				userGroupGroupRole.getRoleId(), userGroupGroupRole.getGroupId(),
				roleIdGroupIdsMap);
		}

		mapSiteMemberRoleIdGroupId(
			kaleoTaskInstanceTokenQuery.getCompanyId(),
			kaleoTaskInstanceTokenQuery.getUserId(), roleIdGroupIdsMap);

		return roleIdGroupIdsMap;
	}

	protected List<Long> getSearchByUserRoleIds(long userId) {
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
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		return Collections.emptyList();
	}

	protected List<UserGroupGroupRole> getUserGroupGroupRoles(long userId) {
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

	protected void mapRoleIdGroupId(
		long roleId, long groupId, Map<Long, Set<Long>> roleIdGroupIdsMap) {

		Set<Long> groupIds = roleIdGroupIdsMap.get(roleId);

		if (groupIds == null) {
			groupIds = new TreeSet<>();

			roleIdGroupIdsMap.put(roleId, groupIds);
		}

		groupIds.add(groupId);
	}

	protected void mapSiteMemberRoleIdGroupId(
		long companyId, long userId, Map<Long, Set<Long>> roleIdGroupIdsMap) {

		try {
			Role siteMemberRole = roleLocalService.getRole(
				companyId, RoleConstants.SITE_MEMBER);

			User user = userLocalService.getUserById(userId);

			for (Long groupId : user.getGroupIds()) {
				mapRoleIdGroupId(
					siteMemberRole.getRoleId(), groupId, roleIdGroupIdsMap);
			}
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
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

	private static final Log _log = LogFactoryUtil.getLog(
		KaleoTaskInstanceTokenModelPreFilterContributor.class);

}