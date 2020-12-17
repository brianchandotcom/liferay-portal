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

package com.liferay.change.tracking.web.internal.display.context;

import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTProcess;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTProcessService;
import com.liferay.change.tracking.service.CTSchemaVersionLocalService;
import com.liferay.change.tracking.web.internal.constants.CTWebConstants;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemListBuilder;
import com.liferay.portal.background.task.model.BackgroundTask;
import com.liferay.portal.background.task.service.BackgroundTaskLocalService;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.util.PropsValues;

import java.util.List;
import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Samuel Trong Tran
 */
public class ViewHistoryDisplayContext {

	public ViewHistoryDisplayContext(
		BackgroundTaskLocalService backgroundTaskLocalService,
		CTCollectionLocalService ctCollectionLocalService,
		CTProcessService ctProcessService,
		CTSchemaVersionLocalService ctSchemaVersionLocalService,
		HttpServletRequest httpServletRequest, Language language,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_backgroundTaskLocalService = backgroundTaskLocalService;
		_ctCollectionLocalService = ctCollectionLocalService;
		_ctProcessService = ctProcessService;
		_ctSchemaVersionLocalService = ctSchemaVersionLocalService;
		_httpServletRequest = httpServletRequest;
		_language = language;

		_renderRequest = renderRequest;

		_themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_renderResponse = renderResponse;
	}

	public CTCollection getCtCollection(CTProcess ctProcess)
		throws PortalException {

		return _ctCollectionLocalService.getCTCollection(
			ctProcess.getCtCollectionId());
	}

	public String getDisplayStyle() {
		return ParamUtil.getString(_renderRequest, "displayStyle", "list");
	}

	public SearchContainer<CTProcess> getSearchContainer() {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		SearchContainer<CTProcess> searchContainer = new SearchContainer<>(
			_renderRequest, new DisplayTerms(_renderRequest), null,
			SearchContainer.DEFAULT_CUR_PARAM, 5,
			PortletURLUtil.getCurrent(_renderRequest, _renderResponse), null,
			_language.get(
				_httpServletRequest, "no-publication-has-been-published-yet"));

		searchContainer.setDeltaConfigurable(false);
		searchContainer.setId("history");
		searchContainer.setOrderByCol(_getOrderByCol());
		searchContainer.setOrderByType(_getOrderByType());

		DisplayTerms displayTerms = searchContainer.getDisplayTerms();

		List<CTProcess> results = _ctProcessService.getCTProcesses(
			_themeDisplay.getCompanyId(), CTWebConstants.USER_FILTER_ALL,
			displayTerms.getKeywords(), _getStatus(_getFilterByStatus()), 0, 5,
			_getOrderByComparator(_getOrderByCol(), _getOrderByType()));

		searchContainer.setResults(results);
		searchContainer.setTotal(results.size());

		_searchContainer = searchContainer;

		return _searchContainer;
	}

	public int getStatus(CTProcess ctProcess) {
		BackgroundTask backgroundTask =
			_backgroundTaskLocalService.fetchBackgroundTask(
				ctProcess.getBackgroundTaskId());

		if (backgroundTask == null) {
			return -1;
		}

		return backgroundTask.getStatus();
	}

	public String getStatusLabel(int status) {
		if (status == BackgroundTaskConstants.STATUS_SUCCESSFUL) {
			return "published";
		}

		return BackgroundTaskConstants.getStatusLabel(status);
	}

	public String getStatusStyle(int status) {
		if (status == BackgroundTaskConstants.STATUS_IN_PROGRESS) {
			return "warning";
		}
		else if (status == BackgroundTaskConstants.STATUS_SUCCESSFUL) {
			return "success";
		}

		return "danger";
	}

	public List<NavigationItem> getViewNavigationItems() {
		return NavigationItemListBuilder.add(
			navigationItem -> {
				navigationItem.setActive(false);
				navigationItem.setHref(_renderResponse.createRenderURL());
				navigationItem.setLabel(
					_language.get(_httpServletRequest, "ongoing"));
			}
		).add(
			() -> PropsValues.SCHEDULER_ENABLED,
			navigationItem -> {
				navigationItem.setActive(false);
				navigationItem.setHref(
					_renderResponse.createRenderURL(), "mvcRenderCommandName",
					"/change_tracking/view_scheduled", "displayStyle",
					getDisplayStyle());
				navigationItem.setLabel(
					_language.get(_httpServletRequest, "scheduled"));
			}
		).add(
			navigationItem -> {
				navigationItem.setActive(true);
				navigationItem.setHref(
					_renderResponse.createRenderURL(), "mvcRenderCommandName",
					"/change_tracking/view_history", "displayStyle",
					getDisplayStyle());
				navigationItem.setLabel(
					_language.get(_httpServletRequest, "history"));
			}
		).build();
	}

	public boolean isExpired(CTCollection ctCollection) {
		return !_ctSchemaVersionLocalService.isLatestCTSchemaVersion(
			ctCollection.getSchemaVersionId());
	}

	private String _getFilterByStatus() {
		return ParamUtil.getString(_renderRequest, "status", "all");
	}

	private String _getOrderByCol() {
		return ParamUtil.getString(
			_renderRequest, SearchContainer.DEFAULT_ORDER_BY_COL_PARAM,
			"published-date");
	}

	private OrderByComparator<CTProcess> _getOrderByComparator(
		String orderByCol, String orderByType) {

		OrderByComparator<CTProcess> orderByComparator = null;

		if (orderByCol.equals("name")) {
			orderByComparator = OrderByComparatorFactoryUtil.create(
				"CTCollection",
				new Object[] {orderByCol, orderByType.equals("asc")});
		}
		else if (orderByCol.equals("published-date")) {
			orderByComparator = OrderByComparatorFactoryUtil.create(
				"CTProcess", "createDate", orderByType.equals("asc"));
		}

		return orderByComparator;
	}

	private String _getOrderByType() {
		return ParamUtil.getString(
			_renderRequest, SearchContainer.DEFAULT_ORDER_BY_TYPE_PARAM,
			"desc");
	}

	private int _getStatus(String type) {
		return _statuses.getOrDefault(type, 0);
	}

	private static final Map<String, Integer> _statuses = HashMapBuilder.put(
		"all", WorkflowConstants.STATUS_ANY
	).put(
		"failed", BackgroundTaskConstants.STATUS_FAILED
	).put(
		"in-progress", BackgroundTaskConstants.STATUS_IN_PROGRESS
	).put(
		"published", BackgroundTaskConstants.STATUS_SUCCESSFUL
	).build();

	private final BackgroundTaskLocalService _backgroundTaskLocalService;
	private final CTCollectionLocalService _ctCollectionLocalService;
	private final CTProcessService _ctProcessService;
	private final CTSchemaVersionLocalService _ctSchemaVersionLocalService;
	private final HttpServletRequest _httpServletRequest;
	private final Language _language;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private SearchContainer<CTProcess> _searchContainer;
	private final ThemeDisplay _themeDisplay;

}