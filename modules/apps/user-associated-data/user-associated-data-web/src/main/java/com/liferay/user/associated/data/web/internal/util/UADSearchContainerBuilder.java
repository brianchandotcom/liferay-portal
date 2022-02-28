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

import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.SearchOrderByUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.user.associated.data.constants.UserAssociatedDataPortletKeys;
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
import java.util.Objects;
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

	public SearchContainer<UADEntity<?>>
			getApplicationSummaryUADEntitySearchContainer(
				LiferayPortletResponse liferayPortletResponse,
				RenderRequest renderRequest, PortletURL currentURL,
				List<UADApplicationSummaryDisplay>
					uadApplicationSummaryDisplays)
		throws PortletException {

		SearchContainer<UADEntity<?>> searchContainer =
			_constructSearchContainer(
				renderRequest, currentURL, "name",
				new String[] {"name", "count"});

		List<UADEntity<?>> uadEntities = new ArrayList<>();

		for (UADApplicationSummaryDisplay uadApplicationSummaryDisplay :
				uadApplicationSummaryDisplays) {

			if (Objects.equals(
					uadApplicationSummaryDisplay.getApplicationKey(),
					UADConstants.ALL_APPLICATIONS) ||
				(uadApplicationSummaryDisplay.getCount() == 0)) {

				continue;
			}

			uadEntities.add(
				_constructApplicationSummaryUADEntity(
					liferayPortletResponse, renderRequest, currentURL,
					uadApplicationSummaryDisplay));
		}

		Stream<UADEntity<?>> uadEntitiesStream = uadEntities.stream();

		searchContainer.setResultsAndTotal(
			() -> uadEntitiesStream.sorted(
				_getComparator(
					searchContainer.getOrderByCol(),
					searchContainer.getOrderByType())
			).skip(
				searchContainer.getStart()
			).limit(
				searchContainer.getDelta()
			).collect(
				Collectors.toList()
			),
			uadEntities.size());

		searchContainer.setRowChecker(
			new EmptyOnClickRowChecker(liferayPortletResponse));

		return searchContainer;
	}

	public SearchContainer<UADEntity<?>> getHierarchyUADEntitySearchContainer(
		LiferayPortletResponse liferayPortletResponse,
		RenderRequest renderRequest, String applicationKey,
		PortletURL currentURL, long[] groupIds, Class<?> parentContainerClass,
		Serializable parentContainerId, User selectedUser,
		UADHierarchyDisplay uadHierarchyDisplay) {

		SearchContainer<UADEntity<?>> searchContainer =
			_constructSearchContainer(
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

			if (Objects.equals(String.valueOf(parentContainerId), "0")) {
				entities.addAll(
					uadHierarchyDisplay.search(
						parentContainerClass, -1L, selectedUser.getUserId(),
						groupIds, displayTerms.getKeywords(), null, null,
						QueryUtil.ALL_POS, QueryUtil.ALL_POS));
			}

			LiferayPortletRequest liferayPortletRequest =
				_portal.getLiferayPortletRequest(renderRequest);

			List<UADEntity<?>> uadEntities = new ArrayList<>();

			for (Object entity : entities) {
				uadEntities.add(
					_constructHierarchyUADEntity(
						liferayPortletRequest, liferayPortletResponse,
						applicationKey, entity, selectedUser.getUserId(),
						uadHierarchyDisplay));
			}

			Stream<UADEntity<?>> uadEntitiesStream = uadEntities.stream();

			searchContainer.setResultsAndTotal(
				() -> uadEntitiesStream.sorted(
					_getComparator(
						searchContainer.getOrderByCol(),
						searchContainer.getOrderByType())
				).skip(
					searchContainer.getStart()
				).limit(
					searchContainer.getDelta()
				).collect(
					Collectors.toList()
				),
				entities.size());
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}

			searchContainer.setResultsAndTotal(Collections::emptyList, 0);
		}

		searchContainer.setRowChecker(
			new UADHierarchyChecker(
				liferayPortletResponse, uadHierarchyDisplay.getUADDisplays()));

		return searchContainer;
	}

	public SearchContainer<UADEntity<?>> getUADEntitySearchContainer(
		LiferayPortletResponse liferayPortletResponse,
		RenderRequest renderRequest, PortletURL currentURL, long[] groupIds,
		User selectedUser, UADDisplay<Object> uadDisplay) {

		SearchContainer<UADEntity<?>> searchContainer =
			_constructSearchContainer(
				renderRequest, currentURL, "modifiedDate",
				uadDisplay.getSortingFieldNames());

		try {
			DisplayTerms displayTerms = searchContainer.getDisplayTerms();

			List<?> entities = uadDisplay.search(
				selectedUser.getUserId(), groupIds, displayTerms.getKeywords(),
				searchContainer.getOrderByCol(),
				searchContainer.getOrderByType(), searchContainer.getStart(),
				searchContainer.getEnd());

			LiferayPortletRequest liferayPortletRequest =
				_portal.getLiferayPortletRequest(renderRequest);

			List<UADEntity<?>> uadEntities = new ArrayList<>();

			for (Object entity : entities) {
				uadEntities.add(
					_constructUADEntity(
						liferayPortletRequest, liferayPortletResponse, entity,
						uadDisplay));
			}

			searchContainer.setResultsAndTotal(
				() -> uadEntities,
				(int)uadDisplay.searchCount(
					selectedUser.getUserId(), groupIds,
					displayTerms.getKeywords()));
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}

			searchContainer.setResultsAndTotal(Collections::emptyList, 0);
		}

		searchContainer.setRowChecker(
			new UADHierarchyChecker(
				liferayPortletResponse, new UADDisplay[] {uadDisplay}));

		return searchContainer;
	}

	private <T> UADEntity<T> _constructApplicationSummaryUADEntity(
			LiferayPortletResponse liferayPortletResponse,
			RenderRequest renderRequest, PortletURL currentURL,
			UADApplicationSummaryDisplay uadApplicationSummaryDisplay)
		throws PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		UADEntity<T> uadEntity = new UADEntity(
			null, uadApplicationSummaryDisplay.getApplicationKey(), null, false,
			null, true,
			PortletURLBuilder.create(
				PortletURLUtil.clone(currentURL, liferayPortletResponse)
			).setParameter(
				"applicationKey",
				uadApplicationSummaryDisplay.getApplicationKey()
			).buildString());

		uadEntity.addColumnEntry(
			"name",
			UADLanguageUtil.getApplicationName(
				uadApplicationSummaryDisplay.getApplicationKey(),
				themeDisplay.getLocale()));
		uadEntity.addColumnEntry(
			"count", uadApplicationSummaryDisplay.getCount());

		return uadEntity;
	}

	private <T> UADEntity<T> _constructHierarchyUADEntity(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			String applicationKey, T entity, long selectedUserId,
			UADHierarchyDisplay uadHierarchyDisplay)
		throws Exception {

		UADEntity<T> uadEntity = new UADEntity(
			uadHierarchyDisplay.unwrap(entity),
			uadHierarchyDisplay.getPrimaryKey(entity),
			uadHierarchyDisplay.getEditURL(
				liferayPortletRequest, liferayPortletResponse, entity),
			uadHierarchyDisplay.isInTrash(entity),
			uadHierarchyDisplay.getTypeClass(entity),
			uadHierarchyDisplay.isUserOwned(entity, selectedUserId),
			uadHierarchyDisplay.getViewURL(
				liferayPortletRequest, liferayPortletResponse, applicationKey,
				entity, selectedUserId));

		Map<String, Object> columnFieldValues =
			uadHierarchyDisplay.getFieldValues(
				entity, LocaleThreadLocal.getThemeDisplayLocale());

		for (Map.Entry<String, Object> entry : columnFieldValues.entrySet()) {
			uadEntity.addColumnEntry(
				entry.getKey(), SafeDisplayValueUtil.get(entry.getValue()));
		}

		return uadEntity;
	}

	private SearchContainer<UADEntity<?>> _constructSearchContainer(
		RenderRequest renderRequest, PortletURL currentURL,
		String defaultOrderByCol, String[] sortingFieldNames) {

		DisplayTerms displayTerms = new DisplayTerms(renderRequest);

		SearchContainer<UADEntity<?>> searchContainer = new SearchContainer<>(
			renderRequest, displayTerms, displayTerms,
			SearchContainer.DEFAULT_CUR_PARAM,
			ParamUtil.getInteger(
				renderRequest, SearchContainer.DEFAULT_CUR_PARAM,
				SearchContainer.DEFAULT_CUR),
			SearchContainer.DEFAULT_DELTA, currentURL, null,
			"no-entities-remain-of-this-type", null);

		searchContainer.setId("UADEntities_" + StringUtil.randomId());
		searchContainer.setOrderableHeaders(
			new LinkedHashMap<String, String>() {
				{
					for (String orderByColumn : sortingFieldNames) {
						put(
							TextFormatter.format(
								orderByColumn, TextFormatter.K),
							orderByColumn);
					}
				}
			});

		String orderByCol = SearchOrderByUtil.getOrderByCol(
			renderRequest, UserAssociatedDataPortletKeys.USER_ASSOCIATED_DATA,
			StringPool.BLANK);

		if (!ArrayUtil.contains(sortingFieldNames, orderByCol)) {
			orderByCol = defaultOrderByCol;
		}

		searchContainer.setOrderByCol(orderByCol);

		searchContainer.setOrderByType(
			SearchOrderByUtil.getOrderByType(
				renderRequest,
				UserAssociatedDataPortletKeys.USER_ASSOCIATED_DATA, "asc"));

		return searchContainer;
	}

	private UADEntity<?> _constructUADEntity(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse, Object entity,
			UADDisplay<Object> uadDisplay)
		throws Exception {

		UADEntity<?> uadEntity = new UADEntity(
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

	private Comparator<UADEntity<?>> _getComparator(
		String orderByColumn, String orderByType) {

		Comparator<UADEntity<?>> comparator = Comparator.comparing(
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
					try {
						return Long.valueOf(
							(String)uadEntity.getColumnEntry(orderByColumn));
					}
					catch (NumberFormatException numberFormatException) {
						if (_log.isDebugEnabled()) {
							_log.debug(numberFormatException);
						}

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