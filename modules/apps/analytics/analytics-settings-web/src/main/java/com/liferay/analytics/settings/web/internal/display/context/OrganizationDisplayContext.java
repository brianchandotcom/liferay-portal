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
import com.liferay.analytics.settings.web.internal.search.OrganizationChecker;
import com.liferay.analytics.settings.web.internal.search.OrganizationSearch;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.util.comparator.OrganizationNameComparator;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author André Miranda
 */
public class OrganizationDisplayContext {

	public OrganizationDisplayContext(
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

		_orderByType = ParamUtil.getString(
			_renderRequest, "orderByType", "asc");

		return _orderByType;
	}

	public OrganizationSearch getOrganizationSearch() {
		OrganizationSearch organizationSearch = new OrganizationSearch(
			_renderRequest, getPortletURL());

		organizationSearch.setOrderByCol(_getOrderByCol());
		organizationSearch.setOrderByType(getOrderByType());

		List<Organization> organizations = OrganizationLocalServiceUtil.search(
			_getCompanyId(), OrganizationConstants.ANY_PARENT_ORGANIZATION_ID,
			_getKeywords(), null, null, null, _getOrganizationParams(),
			organizationSearch.getStart(), organizationSearch.getEnd(),
			new OrganizationNameComparator(_isOrderByAscending()));

		organizationSearch.setResults(organizations);

		organizationSearch.setRowChecker(
			new OrganizationChecker(
				_renderResponse,
				SetUtil.fromArray(
					_analyticsConfiguration.syncedOrganizationIds())));

		int total = OrganizationLocalServiceUtil.searchCount(
			_getCompanyId(), OrganizationConstants.ANY_PARENT_ORGANIZATION_ID,
			_getKeywords(), null, null, null, _getOrganizationParams());

		organizationSearch.setTotal(total);

		return organizationSearch;
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "/view_configuration_screen");
		portletURL.setParameter(
			"configurationScreenKey", "synced-contacts-organizations");

		return portletURL;
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

		_orderByCol = ParamUtil.getString(
			_renderRequest, "orderByCol", "organization-name");

		return _orderByCol;
	}

	private LinkedHashMap<String, Object> _getOrganizationParams() {
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