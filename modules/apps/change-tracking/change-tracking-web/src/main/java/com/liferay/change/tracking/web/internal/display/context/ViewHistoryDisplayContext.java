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
import com.liferay.change.tracking.model.CTProcessTable;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTProcessService;
import com.liferay.change.tracking.service.CTSchemaVersionLocalService;
import com.liferay.change.tracking.web.internal.constants.CTWebConstants;
import com.liferay.change.tracking.web.internal.util.PublicationsPortletURLUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemListBuilder;
import com.liferay.portal.background.task.model.BackgroundTask;
import com.liferay.portal.background.task.service.BackgroundTaskLocalService;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.UserTable;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.util.PropsValues;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Samuel Trong Tran
 */
public class ViewHistoryDisplayContext extends BasePublicationsDisplayContext {

	public ViewHistoryDisplayContext(
		BackgroundTaskLocalService backgroundTaskLocalService,
		CTCollectionLocalService ctCollectionLocalService,
		CTProcessService ctProcessService,
		CTSchemaVersionLocalService ctSchemaVersionLocalService,
		HttpServletRequest httpServletRequest, Language language,
		RenderRequest renderRequest, RenderResponse renderResponse,
		UserLocalService userLocalService) {

		super(httpServletRequest);

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
		_userLocalService = userLocalService;
	}

	public CTCollection getCtCollection(CTProcess ctProcess)
		throws PortalException {

		return _ctCollectionLocalService.getCTCollection(
			ctProcess.getCtCollectionId());
	}

	public Map<String, Object> getReactProps() {
		Map<Long, CTCollection> ctCollectionMap = new HashMap<>();
		Set<Long> ctProcessIds = new HashSet<>();
		JSONArray entriesJSONArray = JSONFactoryUtil.createJSONArray();

		SearchContainer<CTProcess> searchContainer = getSearchContainer();

		List<CTProcess> ctProcesses = searchContainer.getResults();

		for (CTProcess ctProcess : ctProcesses) {
			CTCollection ctCollection = ctCollectionMap.computeIfAbsent(
				ctProcess.getCtCollectionId(),
				_ctCollectionLocalService::fetchCTCollection);

			if (ctCollection == null) {
				continue;
			}

			ctProcessIds.add(ctProcess.getCtProcessId());

			BackgroundTask backgroundTask =
				_backgroundTaskLocalService.fetchBackgroundTask(
					ctProcess.getBackgroundTaskId());

			String displayType = null;
			String label = null;

			if (backgroundTask.getStatus() ==
					BackgroundTaskConstants.STATUS_SUCCESSFUL) {

				displayType = "success";
				label = _language.get(_themeDisplay.getLocale(), "published");
			}

			if (backgroundTask.getStatus() ==
					BackgroundTaskConstants.STATUS_FAILED) {

				displayType = "danger";
				label = _language.get(_themeDisplay.getLocale(), "failed");
			}

			Date publishedDate = ctProcess.getCreateDate();

			String timeDescription = _language.getTimeDescription(
				_themeDisplay.getLocale(),
				System.currentTimeMillis() - publishedDate.getTime(), true);

			ResourceURL statusURL = _renderResponse.createResourceURL();

			statusURL.setResourceID("/change_tracking/get_publication_status");

			statusURL.setParameter(
				"ctProcessId", String.valueOf(ctProcess.getCtProcessId()));

			entriesJSONArray.put(
				JSONUtil.put(
					"description", ctCollection.getDescription()
				).put(
					"displayType", displayType
				).put(
					"expired",
					!_ctSchemaVersionLocalService.isLatestCTSchemaVersion(
						ctCollection.getSchemaVersionId())
				).put(
					"label", label
				).put(
					"name", ctCollection.getName()
				).put(
					"published",
					backgroundTask.getStatus() ==
						BackgroundTaskConstants.STATUS_SUCCESSFUL
				).put(
					"publishedDate", publishedDate.getTime()
				).put(
					"revertURL",
					PublicationsPortletURLUtil.getHref(
						_renderResponse.createRenderURL(),
						"mvcRenderCommandName",
						"/change_tracking/undo_ct_collection", "ctCollectionId",
						String.valueOf(ctCollection.getCtCollectionId()),
						"redirect", _themeDisplay.getURLCurrent(), "revert",
						Boolean.TRUE.toString())
				).put(
					"statusURL", statusURL.toString()
				).put(
					"timeDescription",
					_language.format(
						_themeDisplay.getLocale(), "x-ago",
						new String[] {timeDescription}, false)
				).put(
					"userId", ctProcess.getUserId()
				).put(
					"viewURL",
					PublicationsPortletURLUtil.getHref(
						_renderResponse.createRenderURL(),
						"mvcRenderCommandName", "/change_tracking/view_changes",
						"ctCollectionId",
						String.valueOf(ctCollection.getCtCollectionId()))
				));
		}

		Map<String, Object> props = HashMapBuilder.<String, Object>put(
			"displayStyle", getDisplayStyle()
		).put(
			"entries", entriesJSONArray
		).put(
			"spritemap", _themeDisplay.getPathThemeImages() + "/clay/icons.svg"
		).build();

		if (!ctProcessIds.isEmpty()) {
			props.put(
				"userInfo",
				DisplayContextUtil.getUserInfoJSONObject(
					CTProcessTable.INSTANCE.userId.eq(
						UserTable.INSTANCE.userId),
					CTProcessTable.INSTANCE, _themeDisplay, _userLocalService,
					CTProcessTable.INSTANCE.ctProcessId.in(
						ctProcessIds.toArray(new Long[0]))));
		}

		return props;
	}

	public SearchContainer<CTProcess> getSearchContainer() {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		SearchContainer<CTProcess> searchContainer = new SearchContainer<>(
			_renderRequest, new DisplayTerms(_renderRequest), null,
			SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA,
			PortletURLUtil.getCurrent(_renderRequest, _renderResponse), null,
			_language.get(
				_httpServletRequest, "no-publication-has-been-published-yet"));

		searchContainer.setId("history");
		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByType(getOrderByType());

		DisplayTerms displayTerms = searchContainer.getDisplayTerms();

		List<CTProcess> results = _ctProcessService.getCTProcesses(
			_themeDisplay.getCompanyId(), CTWebConstants.USER_FILTER_ALL,
			displayTerms.getKeywords(), _getStatus(_getFilterByStatus()),
			searchContainer.getStart(), searchContainer.getEnd(),
			_getOrderByComparator(getOrderByCol(), getOrderByType()));

		searchContainer.setResults(results);

		int count = _ctProcessService.getCTProcessesCount(
			_themeDisplay.getCompanyId(), CTWebConstants.USER_FILTER_ALL,
			displayTerms.getKeywords(), _getStatus(_getFilterByStatus()));

		searchContainer.setTotal(count);

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

	public boolean isSearch() {
		SearchContainer<CTProcess> searchContainer = getSearchContainer();

		DisplayTerms displayTerms = searchContainer.getDisplayTerms();

		return Validator.isNotNull(displayTerms.getKeywords());
	}

	@Override
	protected String getDefaultOrderByCol() {
		return "published-date";
	}

	@Override
	protected String getPortalPreferencesPrefix() {
		return "history";
	}

	private String _getFilterByStatus() {
		return ParamUtil.getString(_renderRequest, "status", "all");
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
	private final UserLocalService _userLocalService;

}