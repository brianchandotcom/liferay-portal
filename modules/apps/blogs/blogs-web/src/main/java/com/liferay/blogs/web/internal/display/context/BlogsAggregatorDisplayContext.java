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

package com.liferay.blogs.web.internal.display.context;

import com.liferay.blogs.constants.BlogsPortletKeys;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryServiceUtil;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Mariano Álvaro Sáiz
 */
public class BlogsAggregatorDisplayContext {

	public BlogsAggregatorDisplayContext(
			HttpServletRequest httpServletRequest, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws PortalException {

		_httpServletRequest = httpServletRequest;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
		_portletPreferences =
			PortletPreferencesFactoryUtil.getPortletPreferences(
				_httpServletRequest, BlogsPortletKeys.BLOGS_AGGREGATOR);
	}

	public int getMax() {
		if (_max != null) {
			return _max;
		}

		_max = GetterUtil.getInteger(_portletPreferences.getValue("max", "20"));

		return _max;
	}

	public long getOrganizationId() {
		if (_organizationId != null) {
			return _organizationId;
		}

		_organizationId = GetterUtil.getLong(
			_portletPreferences.getValue("organizationId", "0"));

		if (_organizationId == 0) {
			Group group = _themeDisplay.getScopeGroup();

			if (group.isOrganization()) {
				_organizationId = group.getOrganizationId();
			}
		}

		return _organizationId;
	}

	public PortletURL getPortletURL() {
		if (_portletURL != null) {
			return _portletURL;
		}

		_portletURL = PortletURLBuilder.createRenderURL(
			_renderResponse
		).setMVCRenderCommandName(
			"/blogs_aggregator/view"
		).buildPortletURL();

		return _portletURL;
	}

	public SearchContainer<BlogsEntry> getSearchContainer()
		throws PortalException {

		_searchContainer = new SearchContainer(
			_renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, 5,
			getPortletURL(), null, null);

		List<BlogsEntry> blogEntries = new ArrayList<>();

		if (Objects.equals(getSelectionMethod(), "users")) {
			if (getOrganizationId() > 0) {
				blogEntries.addAll(
					BlogsEntryServiceUtil.getOrganizationEntries(
						getOrganizationId(), new Date(),
						WorkflowConstants.STATUS_APPROVED, getMax()));
			}
			else {
				blogEntries.addAll(
					BlogsEntryServiceUtil.getGroupsEntries(
						_themeDisplay.getCompanyId(),
						_themeDisplay.getScopeGroupId(), new Date(),
						WorkflowConstants.STATUS_APPROVED, getMax()));
			}
		}
		else {
			blogEntries.addAll(
				BlogsEntryServiceUtil.getGroupEntries(
					_themeDisplay.getScopeGroupId(), new Date(),
					WorkflowConstants.STATUS_APPROVED, getMax()));
		}

		_searchContainer.setResultsAndTotal(
			() -> ListUtil.subList(
				blogEntries, _searchContainer.getStart(),
				_searchContainer.getEnd()),
			blogEntries.size());

		return _searchContainer;
	}

	public String getSelectionMethod() {
		if (_selectionMethod != null) {
			return _selectionMethod;
		}

		_selectionMethod = _portletPreferences.getValue(
			"selectionMethod", "users");

		return _selectionMethod;
	}

	private final HttpServletRequest _httpServletRequest;
	private Integer _max;
	private Long _organizationId;
	private final PortletPreferences _portletPreferences;
	private PortletURL _portletURL;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private SearchContainer<BlogsEntry> _searchContainer;
	private String _selectionMethod;
	private final ThemeDisplay _themeDisplay;

}