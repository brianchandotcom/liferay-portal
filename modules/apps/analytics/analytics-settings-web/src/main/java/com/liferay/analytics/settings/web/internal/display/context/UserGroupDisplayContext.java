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
import com.liferay.analytics.settings.web.internal.search.UserGroupChecker;
import com.liferay.analytics.settings.web.internal.search.UserGroupSearch;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.portlet.SearchOrderByUtil;
import com.liferay.portal.kernel.service.UserGroupServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.util.comparator.UserGroupNameComparator;

import java.util.LinkedHashMap;
import java.util.Objects;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author André Miranda
 */
public class UserGroupDisplayContext {

	public UserGroupDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_analyticsConfiguration =
			(AnalyticsConfiguration)renderRequest.getAttribute(
				AnalyticsSettingsWebKeys.ANALYTICS_CONFIGURATION);
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = SearchOrderByUtil.getOrderByType(
			_renderRequest, AnalyticsSettingsWebKeys.ANALYTICS_CONFIGURATION,
			"user-group-order-by-type", "asc");

		return _orderByType;
	}

	public PortletURL getPortletURL() {
		return PortletURLBuilder.createRenderURL(
			_renderResponse
		).setMVCRenderCommandName(
			"/analytics_settings/edit_synced_contacts_groups"
		).buildPortletURL();
	}

	public UserGroupSearch getUserGroupSearch() {
		UserGroupSearch userGroupSearch = new UserGroupSearch(
			_renderRequest, getPortletURL());

		userGroupSearch.setOrderByCol(_getOrderByCol());
		userGroupSearch.setOrderByType(getOrderByType());
		userGroupSearch.setResultsAndTotal(
			() -> UserGroupServiceUtil.search(
				_getCompanyId(), _getKeywords(), _getUserGroupParams(),
				userGroupSearch.getStart(), userGroupSearch.getEnd(),
				new UserGroupNameComparator(_isOrderByAscending())),
			UserGroupServiceUtil.searchCount(
				_getCompanyId(), _getKeywords(), _getUserGroupParams()));
		userGroupSearch.setRowChecker(
			new UserGroupChecker(
				_renderResponse,
				SetUtil.fromArray(
					_analyticsConfiguration.syncedUserGroupIds())));

		return userGroupSearch;
	}

	private long _getCompanyId() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return themeDisplay.getCompanyId();
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

		_orderByCol = SearchOrderByUtil.getOrderByCol(
			_renderRequest, AnalyticsSettingsWebKeys.ANALYTICS_CONFIGURATION,
			"user-group-order-by-col", "user-group-name");

		return _orderByCol;
	}

	private LinkedHashMap<String, Object> _getUserGroupParams() {
		return LinkedHashMapBuilder.<String, Object>put(
			"active", Boolean.TRUE
		).build();
	}

	private boolean _isOrderByAscending() {
		if (Objects.equals("asc", getOrderByType())) {
			return true;
		}

		return false;
	}

	private final AnalyticsConfiguration _analyticsConfiguration;
	private String _keywords;
	private String _orderByCol;
	private String _orderByType;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;

}