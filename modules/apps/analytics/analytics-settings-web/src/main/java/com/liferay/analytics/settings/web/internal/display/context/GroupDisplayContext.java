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

package com.liferay.analytics.settings.web.internal.display.context;

import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.web.internal.constants.AnalyticsSettingsWebKeys;
import com.liferay.analytics.settings.web.internal.search.GroupChecker;
import com.liferay.analytics.settings.web.internal.search.GroupSearch;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.util.comparator.GroupNameComparator;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Marcellus Tavares
 */
public class GroupDisplayContext {

	public GroupDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_analyticsConfiguration =
			(AnalyticsConfiguration)renderRequest.getAttribute(
				AnalyticsSettingsWebKeys.ANALYTICS_CONFIGURATION);
	}

	public GroupSearch getGroupSearch() {
		GroupSearch groupSearch = new GroupSearch(
			_renderRequest, getPortletURL());

		groupSearch.setOrderByCol(_getOrderByCol());
		groupSearch.setOrderByType(getOrderByType());

		List<Group> groups = Collections.emptyList();

		try {
			groups = GroupServiceUtil.search(
				_getCompanyId(), _getClassNameIds(), _getKeywords(),
				_getGroupParams(), groupSearch.getStart(), groupSearch.getEnd(),
				new GroupNameComparator(_isOrderByAscending()));
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}

		groupSearch.setResults(groups);

		groupSearch.setRowChecker(
			new GroupChecker(
				_renderResponse,
				Validator.isBlank(_analyticsConfiguration.token()),
				SetUtil.fromArray(_analyticsConfiguration.syncedGroupIds())));

		int total = GroupServiceUtil.searchCount(
			_getCompanyId(), _getClassNameIds(), _getKeywords(),
			_getGroupParams());

		groupSearch.setTotal(total);

		return groupSearch;
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(
			_renderRequest, "orderByType", "asc");

		return _orderByType;
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "/view_configuration_screen");
		portletURL.setParameter("configurationScreenKey", "synced-sites");

		return portletURL;
	}

	private long[] _getClassNameIds() {
		if (_classNameIds != null) {
			return _classNameIds;
		}

		_classNameIds = new long[] {PortalUtil.getClassNameId(Group.class)};

		return _classNameIds;
	}

	private long _getCompanyId() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return themeDisplay.getCompanyId();
	}

	private LinkedHashMap<String, Object> _getGroupParams() {
		return LinkedHashMapBuilder.<String, Object>put(
			"active", Boolean.TRUE
		).put(
			"site", Boolean.TRUE
		).build();
	}

	private String _getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_renderRequest, "keywords");

		return _keywords;
	}

	private String _getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(
			_renderRequest, "orderByCol", "site-name");

		return _orderByCol;
	}

	private boolean _isOrderByAscending() {
		if (Objects.equals("asc", getOrderByType())) {
			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GroupDisplayContext.class);

	private final AnalyticsConfiguration _analyticsConfiguration;
	private long[] _classNameIds;
	private String _keywords;
	private String _orderByCol;
	private String _orderByType;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;

}