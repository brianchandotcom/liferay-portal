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

package com.liferay.user.associated.data.web.internal.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.user.associated.data.display.UADDisplay;
import com.liferay.user.associated.data.web.internal.constants.UADConstants;
import com.liferay.user.associated.data.web.internal.display.UADApplicationSummaryDisplay;
import com.liferay.user.associated.data.web.internal.display.UADEntity;
import com.liferay.user.associated.data.web.internal.display.UADHierarchyDisplay;
import com.liferay.user.associated.data.web.internal.search.UADHierarchyChecker;

import java.io.Serializable;

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

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(immediate = true, service = UADSearchContainerBuilder.class)
public class UADSearchContainerBuilder {

	public SearchContainer<UADEntity> getSearchContainer(
			RenderRequest renderRequest,
			LiferayPortletResponse liferayPortletResponse,
			PortletURL currentURL,
			List<UADApplicationSummaryDisplay> uadApplicationSummaryDisplays)
		throws PortletException {

		SearchContainer<UADEntity> searchContainer = _constructSearchContainer(
			renderRequest, currentURL, "name", new String[] {"name", "count"});

		List<UADEntity> uadEntities = new ArrayList<>();

		for (UADApplicationSummaryDisplay uadApplicationSummaryDisplay :
				uadApplicationSummaryDisplays) {

			String applicationKey =
				uadApplicationSummaryDisplay.getApplicationKey();

			if (applicationKey.equals(UADConstants.ALL_APPLICATIONS) ||
				(uadApplicationSummaryDisplay.getCount() == 0)) {

				continue;
			}

			uadEntities.add(
				_constructUADEntity(
					renderRequest, liferayPortletResponse, currentURL,
					uadApplicationSummaryDisplay));
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

		searchContainer.setRowChecker(
			new EmptyOnClickRowChecker(liferayPortletResponse));
		searchContainer.setTotal(uadEntities.size());

		return searchContainer;
	}

	public SearchContainer<UADEntity> getSearchContainer(
		RenderRequest renderRequest,
		LiferayPortletResponse liferayPortletResponse, PortletURL currentURL,
		long[] groupIds, User selectedUser, UADDisplay uadDisplay) {

		SearchContainer<UADEntity> searchContainer = _constructSearchContainer(
			renderRequest, currentURL, "modifiedDate",
			uadDisplay.getSortingFieldNames());

		try {
			DisplayTerms displayTerms = searchContainer.getDisplayTerms();

			List entities = uadDisplay.search(
				selectedUser.getUserId(), groupIds, displayTerms.getKeywords(),
				searchContainer.getOrderByCol(),
				searchContainer.getOrderByType(), searchContainer.getStart(),
				searchContainer.getEnd());

			LiferayPortletRequest liferayPortletRequest =
				_portal.getLiferayPortletRequest(renderRequest);

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

		RowChecker rowChecker = new UADHierarchyChecker(
			liferayPortletResponse, new UADDisplay[] {uadDisplay});

		searchContainer.setRowChecker(rowChecker);

		return searchContainer;
	}

	public SearchContainer<UADEntity> getSearchContainer(
		RenderRequest renderRequest,
		LiferayPortletResponse liferayPortletResponse, String applicationKey,
		PortletURL currentURL, long[] groupIds, Class<?> parentContainerClass,
		Serializable parentContainerId, User selectedUser,
		UADHierarchyDisplay uadHierarchyDisplay) {

		SearchContainer<UADEntity> searchContainer = _constructSearchContainer(
			renderRequest, currentURL, "name",
			uadHierarchyDisplay.getSortingFieldNames());

		try {
			DisplayTerms displayTerms = searchContainer.getDisplayTerms();

			List<Object> entities = new ArrayList<>();

			entities.addAll(
				uadHierarchyDisplay.search(
					parentContainerClass, parentContainerId,
					selectedUser.getUserId(), groupIds,
					displayTerms.getKeywords(), null, null, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS));

			String parentContainerIdString = String.valueOf(parentContainerId);

			if (parentContainerIdString.equals("0")) {
				entities.addAll(
					uadHierarchyDisplay.search(
						parentContainerClass, -1L, selectedUser.getUserId(),
						groupIds, displayTerms.getKeywords(), null, null,
						QueryUtil.ALL_POS, QueryUtil.ALL_POS));
			}

			LiferayPortletRequest liferayPortletRequest =
				_portal.getLiferayPortletRequest(renderRequest);

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

			searchContainer.setTotal(entities.size());
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}

			searchContainer.setResults(Collections.emptyList());
			searchContainer.setTotal(0);
		}

		RowChecker rowChecker = new UADHierarchyChecker(
			liferayPortletResponse, uadHierarchyDisplay.getUADDisplays());

		searchContainer.setRowChecker(rowChecker);

		return searchContainer;
	}

	private SearchContainer<UADEntity> _constructSearchContainer(
		RenderRequest renderRequest, PortletURL currentURL,
		String defaultOrderByCol, String[] sortingFieldNames) {

		DisplayTerms displayTerms = new DisplayTerms(renderRequest);

		int cur = ParamUtil.getInteger(
			renderRequest, SearchContainer.DEFAULT_CUR_PARAM,
			SearchContainer.DEFAULT_CUR);

		SearchContainer<UADEntity> searchContainer = new SearchContainer<>(
			renderRequest, displayTerms, displayTerms,
			SearchContainer.DEFAULT_CUR_PARAM, cur,
			SearchContainer.DEFAULT_DELTA, currentURL, null,
			"no-entities-remain-of-this-type", null);

		searchContainer.setId(
			StringBundler.concat(
				"UADEntities", StringPool.UNDERLINE, StringUtil.randomId()));

		String orderByCol = ParamUtil.getString(
			renderRequest, SearchContainer.DEFAULT_ORDER_BY_COL_PARAM);

		if (!ArrayUtil.contains(sortingFieldNames, orderByCol)) {
			orderByCol = defaultOrderByCol;
		}

		searchContainer.setOrderByCol(orderByCol);

		String orderByType = ParamUtil.getString(
			renderRequest, SearchContainer.DEFAULT_ORDER_BY_TYPE_PARAM, "asc");

		searchContainer.setOrderByType(orderByType);

		Map<String, String> orderableHeaders = new LinkedHashMap<>();

		for (String orderByColumn : sortingFieldNames) {
			orderableHeaders.put(
				TextFormatter.format(orderByColumn, TextFormatter.K),
				orderByColumn);
		}

		searchContainer.setOrderableHeaders(orderableHeaders);

		return searchContainer;
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
			uadHierarchyDisplay.getPrimaryKey(entity), editURL,
			uadHierarchyDisplay.isInTrash(entity),
			uadHierarchyDisplay.getTypeClass(entity),
			uadHierarchyDisplay.isUserOwned(entity, selectedUserId), viewURL);

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
			uadDisplay.isInTrash(entity), uadDisplay.getTypeClass(), true,
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

	private <T> UADEntity<T> _constructUADEntity(
			RenderRequest renderRequest,
			LiferayPortletResponse liferayPortletResponse,
			PortletURL currentURL,
			UADApplicationSummaryDisplay uadApplicationSummaryDisplay)
		throws PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletURL viewURL = PortletURLUtil.clone(
			currentURL, liferayPortletResponse);

		viewURL.setParameter(
			"applicationKey", uadApplicationSummaryDisplay.getApplicationKey());

		UADEntity<T> uadEntity = new UADEntity(
			null, uadApplicationSummaryDisplay.getApplicationKey(), null, false,
			null, true, viewURL.toString());

		uadEntity.addColumnEntry(
			"name",
			UADLanguageUtil.getApplicationName(
				uadApplicationSummaryDisplay.getApplicationKey(),
				themeDisplay.getLocale()));

		uadEntity.addColumnEntry(
			"count", uadApplicationSummaryDisplay.getCount());

		return uadEntity;
	}

	private Comparator<UADEntity> _getComparator(
		String orderByColumn, String orderByType) {

		Comparator<UADEntity> comparator = Comparator.comparing(
			uadEntity -> {
				Object entry = uadEntity.getColumnEntry(orderByColumn);

				if (entry == null) {
					return "";
				}

				return (String)entry;
			});

		if (orderByColumn.equals("count")) {
			comparator = Comparator.comparingLong(
				uadEntity -> {
					Object entry = uadEntity.getColumnEntry(orderByColumn);

					try {
						return Long.valueOf((String)entry);
					}
					catch (NumberFormatException nfe) {
						return 0L;
					}
				});
		}

		if (orderByType.equals("desc")) {
			comparator = comparator.reversed();
		}

		return comparator;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UADSearchContainerBuilder.class);

	@Reference
	private Portal _portal;

}