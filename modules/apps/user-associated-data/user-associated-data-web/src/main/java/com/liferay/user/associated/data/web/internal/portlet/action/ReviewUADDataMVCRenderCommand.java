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

package com.liferay.user.associated.data.web.internal.portlet.action;

import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.user.associated.data.constants.UserAssociatedDataPortletKeys;
import com.liferay.user.associated.data.display.UADDisplay;
import com.liferay.user.associated.data.web.internal.constants.UADConstants;
import com.liferay.user.associated.data.web.internal.constants.UADWebKeys;
import com.liferay.user.associated.data.web.internal.display.UADApplicationSummaryDisplay;
import com.liferay.user.associated.data.web.internal.display.UADEntity;
import com.liferay.user.associated.data.web.internal.display.UADHierarchyDisplay;
import com.liferay.user.associated.data.web.internal.display.ViewUADEntitiesDisplay;
import com.liferay.user.associated.data.web.internal.registry.UADRegistry;
import com.liferay.user.associated.data.web.internal.search.UADHierarchyResultRowSplitter;
import com.liferay.user.associated.data.web.internal.util.SafeDisplayValueUtil;
import com.liferay.user.associated.data.web.internal.util.SelectedUserHelper;
import com.liferay.user.associated.data.web.internal.util.UADApplicationSummaryHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + UserAssociatedDataPortletKeys.USER_ASSOCIATED_DATA,
		"mvc.command.name=/review_uad_data"
	},
	service = MVCRenderCommand.class
)
public class ReviewUADDataMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			User selectedUser = _selectedUserHelper.getSelectedUser(
				renderRequest);

			String scope = ParamUtil.getString(
				renderRequest, "scope", UADConstants.SCOPE_PERSONAL_SITE);

			long[] groupIds = _getGroupIds(selectedUser, scope);

			List<UADApplicationSummaryDisplay> uadApplicationSummaryDisplays =
				_uadApplicationSummaryHelper.getUADApplicationSummaryDisplays(
					selectedUser.getUserId(), groupIds);

			UADApplicationSummaryDisplay uadApplicationSummaryDisplay =
				uadApplicationSummaryDisplays.get(0);

			for (UADApplicationSummaryDisplay
					currentUadApplicationSummaryDisplay :
						uadApplicationSummaryDisplays) {

				if (currentUadApplicationSummaryDisplay.getCount() > 0) {
					uadApplicationSummaryDisplay =
						currentUadApplicationSummaryDisplay;

					break;
				}
			}

			String applicationKey = ParamUtil.getString(
				renderRequest, "applicationKey");

			if (Validator.isNull(applicationKey)) {
				applicationKey =
					uadApplicationSummaryDisplay.getApplicationKey();
			}

			ViewUADEntitiesDisplay viewUADEntitiesDisplay =
				new ViewUADEntitiesDisplay();

			viewUADEntitiesDisplay.setApplicationKey(applicationKey);

			LiferayPortletResponse liferayPortletResponse =
				_portal.getLiferayPortletResponse(renderResponse);

			PortletURL currentURL = PortletURLUtil.getCurrent(
				renderRequest, renderResponse);

			UADHierarchyDisplay uadHierarchyDisplay =
				_uadRegistry.getUADHierarchyDisplay(applicationKey);

			if (uadHierarchyDisplay != null) {
				viewUADEntitiesDisplay.setHierarchical(true);
				viewUADEntitiesDisplay.setResultRowSplitter(
					new UADHierarchyResultRowSplitter(
						LocaleThreadLocal.getThemeDisplayLocale(),
						uadHierarchyDisplay.getUADDisplays()));
				viewUADEntitiesDisplay.setSearchContainer(
					_getSearchContainer(
						renderRequest, liferayPortletResponse, applicationKey,
						currentURL, groupIds, selectedUser,
						uadHierarchyDisplay));

				UADDisplay<?>[] uadDisplays =
					uadHierarchyDisplay.getUADDisplays();

				renderRequest.setAttribute(
					UADWebKeys.INFO_PANEL_UAD_DISPLAY, uadDisplays[0]);
			}
			else {
				String uadRegistryKey = ParamUtil.getString(
					renderRequest, "uadRegistryKey");

				if (Validator.isNull(uadRegistryKey)) {
					uadRegistryKey =
						_uadApplicationSummaryHelper.getDefaultUADRegistryKey(
							applicationKey);
				}

				UADDisplay uadDisplay = _uadRegistry.getUADDisplay(
					uadRegistryKey);

				viewUADEntitiesDisplay.setHierarchical(false);
				viewUADEntitiesDisplay.setSearchContainer(
					_getSearchContainer(
						renderRequest, liferayPortletResponse, currentURL,
						groupIds, selectedUser, uadDisplay));
				viewUADEntitiesDisplay.setTypeName(
					uadDisplay.getTypeName(
						LocaleThreadLocal.getThemeDisplayLocale()));

				viewUADEntitiesDisplay.setUADRegistryKey(uadRegistryKey);

				renderRequest.setAttribute(
					UADWebKeys.INFO_PANEL_UAD_DISPLAY, uadDisplay);
			}

			renderRequest.setAttribute(
				UADWebKeys.APPLICATION_UAD_DISPLAYS,
				_uadRegistry.getApplicationUADDisplays(applicationKey));
			renderRequest.setAttribute(UADWebKeys.GROUP_IDS, groupIds);
			renderRequest.setAttribute(
				UADWebKeys.TOTAL_UAD_ENTITIES_COUNT,
				_uadApplicationSummaryHelper.getTotalReviewableUADEntitiesCount(
					selectedUser.getUserId()));
			renderRequest.setAttribute(
				UADWebKeys.UAD_APPLICATION_SUMMARY_DISPLAY_LIST,
				uadApplicationSummaryDisplays);
			renderRequest.setAttribute(
				UADWebKeys.VIEW_UAD_ENTITIES_DISPLAY, viewUADEntitiesDisplay);
		}
		catch (Exception e) {
			throw new PortletException(e);
		}

		return "/review_uad_data.jsp";
	}

	private <T> UADEntity<T> _constructUADEntity(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			String applicationKey, T entity, long selectedUserId,
			UADHierarchyDisplay uadHierarchyDisplay)
		throws Exception {

		String editURL = uadHierarchyDisplay.getEditURL(
			liferayPortletRequest, liferayPortletResponse, entity);

		String viewURL = uadHierarchyDisplay.getViewURL(
			liferayPortletRequest, liferayPortletResponse, applicationKey,
			entity, selectedUserId);

		UADEntity<T> uadEntity = new UADEntity(
			uadHierarchyDisplay.unwrap(entity),
			uadHierarchyDisplay.getPrimaryKey(entity), editURL, viewURL);

		Map<String, Object> columnFieldValues =
			uadHierarchyDisplay.getFieldValues(
				entity, LocaleThreadLocal.getThemeDisplayLocale());

		for (Map.Entry<String, Object> entry : columnFieldValues.entrySet()) {
			uadEntity.addColumnEntry(
				entry.getKey(), SafeDisplayValueUtil.get(entry.getValue()));
		}

		return uadEntity;
	}

	private <T> UADEntity<T> _constructUADEntity(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse, T entity,
			UADDisplay<T> uadDisplay)
		throws Exception {

		UADEntity<T> uadEntity = new UADEntity(
			entity, uadDisplay.getPrimaryKey(entity),
			uadDisplay.getEditURL(
				entity, liferayPortletRequest, liferayPortletResponse),
			null);

		Map<String, Object> columnFieldValues = uadDisplay.getFieldValues(
			entity, uadDisplay.getColumnFieldNames(),
			LocaleThreadLocal.getThemeDisplayLocale());

		for (String columnFieldName : uadDisplay.getColumnFieldNames()) {
			uadEntity.addColumnEntry(
				columnFieldName,
				SafeDisplayValueUtil.get(
					columnFieldValues.get(columnFieldName)));
		}

		return uadEntity;
	}

	private Comparator<UADEntity> _getComparator(
		String orderByColumn, String orderByType) {

		Comparator<UADEntity> comparator = Comparator.comparingLong(
			uadEntity -> {
				Object entry = uadEntity.getColumnEntry(orderByColumn);

				try {
					return Long.valueOf((String)entry);
				}
				catch (NumberFormatException nfe) {
					return 0L;
				}
			});

		if (!orderByColumn.equals("count")) {
			comparator = Comparator.comparing(
				uadEntity -> {
					Object entry = uadEntity.getColumnEntry(orderByColumn);

					if (entry == null) {
						return "";
					}

					return (String)entry;
				});
		}

		if (orderByType.equals("desc")) {
			comparator = comparator.reversed();
		}

		return comparator;
	}

	private long[] _getGroupIds(User user, String scope) {
		try {
			if (scope.equals(UADConstants.SCOPE_PERSONAL_SITE)) {
				Group userGroup = _groupLocalService.getUserGroup(
					user.getCompanyId(), user.getUserId());

				return new long[] {userGroup.getGroupId()};
			}

			if (scope.equals(UADConstants.SCOPE_REGULAR_SITES)) {
				List<Group> groups = _groupLocalService.getGroups(
					user.getCompanyId(), GroupConstants.ANY_PARENT_GROUP_ID,
					true);

				int size = groups.size();

				long[] groupIds = new long[size];

				for (int i = 0; i < size; i++) {
					Group group = groups.get(i);

					groupIds[i] = group.getGroupId();
				}

				return groupIds;
			}
		}
		catch (PortalException pe) {
			_log.error(pe, pe);
		}

		return null;
	}

	private SearchContainer<UADEntity> _getSearchContainer(
			RenderRequest renderRequest,
			LiferayPortletResponse liferayPortletResponse,
			PortletURL currentURL, long[] groupIds, User selectedUser,
			UADDisplay uadDisplay)
		throws Exception {

		LiferayPortletRequest liferayPortletRequest =
			_portal.getLiferayPortletRequest(renderRequest);

		DisplayTerms displayTerms = new DisplayTerms(renderRequest);

		int cur = ParamUtil.getInteger(
			renderRequest, SearchContainer.DEFAULT_CUR_PARAM,
			SearchContainer.DEFAULT_CUR);

		SearchContainer<UADEntity> searchContainer = new SearchContainer<>(
			renderRequest, displayTerms, displayTerms,
			SearchContainer.DEFAULT_CUR_PARAM, cur,
			SearchContainer.DEFAULT_DELTA, currentURL, null,
			"no-entities-remain-of-this-type", null);

		searchContainer.setId("UADEntities");

		String orderByCol = ParamUtil.getString(
			renderRequest, SearchContainer.DEFAULT_ORDER_BY_COL_PARAM);

		if (!ArrayUtil.contains(
				uadDisplay.getSortingFieldNames(), orderByCol)) {

			orderByCol = "modifiedDate";
		}

		searchContainer.setOrderByCol(orderByCol);

		String orderByType = ParamUtil.getString(
			renderRequest, SearchContainer.DEFAULT_ORDER_BY_TYPE_PARAM, "asc");

		searchContainer.setOrderByType(orderByType);

		Map<String, String> orderableHeaders = new LinkedHashMap<>();

		for (String orderByColumn : uadDisplay.getSortingFieldNames()) {
			orderableHeaders.put(
				TextFormatter.format(orderByColumn, TextFormatter.K),
				orderByColumn);
		}

		searchContainer.setOrderableHeaders(orderableHeaders);

		try {
			List entities = uadDisplay.search(
				selectedUser.getUserId(), groupIds, displayTerms.getKeywords(),
				searchContainer.getOrderByCol(),
				searchContainer.getOrderByType(), searchContainer.getStart(),
				searchContainer.getEnd());

			List<UADEntity> uadEntities = new ArrayList<>();

			for (Object entity : entities) {
				uadEntities.add(
					_constructUADEntity(
						liferayPortletRequest, liferayPortletResponse, entity,
						uadDisplay));
			}

			searchContainer.setResults(uadEntities);

			searchContainer.setTotal(
				(int)uadDisplay.searchCount(
					selectedUser.getUserId(), groupIds,
					displayTerms.getKeywords()));
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}

			searchContainer.setResults(Collections.emptyList());
			searchContainer.setTotal(0);
		}

		RowChecker rowChecker = new EmptyOnClickRowChecker(
			liferayPortletResponse);

		Class<?> uadClass = uadDisplay.getTypeClass();

		rowChecker.setRememberCheckBoxStateURLRegex(
			"uadRegistryKey=" + uadClass.getName());

		searchContainer.setRowChecker(rowChecker);

		return searchContainer;
	}

	private SearchContainer<UADEntity> _getSearchContainer(
			RenderRequest renderRequest,
			LiferayPortletResponse liferayPortletResponse,
			String applicationKey, PortletURL currentURL, long[] groupIds,
			User selectedUser, UADHierarchyDisplay uadHierarchyDisplay)
		throws Exception {

		LiferayPortletRequest liferayPortletRequest =
			_portal.getLiferayPortletRequest(renderRequest);

		DisplayTerms displayTerms = new DisplayTerms(renderRequest);

		int cur = ParamUtil.getInteger(
			renderRequest, SearchContainer.DEFAULT_CUR_PARAM,
			SearchContainer.DEFAULT_CUR);

		SearchContainer<UADEntity> searchContainer = new SearchContainer<>(
			renderRequest, displayTerms, displayTerms,
			SearchContainer.DEFAULT_CUR_PARAM, cur,
			SearchContainer.DEFAULT_DELTA, currentURL, null,
			"no-entities-remain-of-this-type", null);

		searchContainer.setId("UADEntities");

		String orderByCol = ParamUtil.getString(
			renderRequest, SearchContainer.DEFAULT_ORDER_BY_COL_PARAM);

		if (!ArrayUtil.contains(
				uadHierarchyDisplay.getSortingFieldNames(), orderByCol)) {

			orderByCol = "name";
		}

		searchContainer.setOrderByCol(orderByCol);

		String orderByType = ParamUtil.getString(
			renderRequest, SearchContainer.DEFAULT_ORDER_BY_TYPE_PARAM, "asc");

		searchContainer.setOrderByType(orderByType);

		Map<String, String> orderableHeaders = new LinkedHashMap<>();

		for (String orderByColumn :
				uadHierarchyDisplay.getSortingFieldNames()) {

			orderableHeaders.put(
				TextFormatter.format(orderByColumn, TextFormatter.K),
				orderByColumn);
		}

		searchContainer.setOrderableHeaders(orderableHeaders);

		try {
			Class<?> parentContainerClass =
				uadHierarchyDisplay.getFirstContainerTypeClass();

			List entities = uadHierarchyDisplay.search(
				parentContainerClass, 0L, selectedUser.getUserId(), groupIds,
				displayTerms.getKeywords(), null, null,
				searchContainer.getStart(), searchContainer.getEnd());

			List<UADEntity> uadEntities = new ArrayList<>();

			for (Object entity : entities) {
				uadEntities.add(
					_constructUADEntity(
						liferayPortletRequest, liferayPortletResponse,
						applicationKey, entity, selectedUser.getUserId(),
						uadHierarchyDisplay));
			}

			Stream<UADEntity> uadEntitiesStream = uadEntities.stream();

			List<UADEntity> results = uadEntitiesStream.sorted(
				_getComparator(
					searchContainer.getOrderByCol(),
					searchContainer.getOrderByType())
			).skip(
				searchContainer.getStart()
			).limit(
				searchContainer.getDelta()
			).collect(
				Collectors.toList()
			);

			searchContainer.setResults(results);

			searchContainer.setTotal(results.size());
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}

			searchContainer.setResults(Collections.emptyList());
			searchContainer.setTotal(0);
		}

		RowChecker rowChecker = new EmptyOnClickRowChecker(
			liferayPortletResponse);

		searchContainer.setRowChecker(rowChecker);

		return searchContainer;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ReviewUADDataMVCRenderCommand.class);

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private SelectedUserHelper _selectedUserHelper;

	@Reference
	private UADApplicationSummaryHelper _uadApplicationSummaryHelper;

	@Reference
	private UADRegistry _uadRegistry;

}