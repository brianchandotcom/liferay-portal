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

package com.liferay.layout.admin.web.internal.display.context;

import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.SearchDisplayStyleUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.util.comparator.PortletTitleComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class OrphanPortletsDisplayContext {

	public OrphanPortletsDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_httpServletRequest = httpServletRequest;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
	}

	public String getBackURL() {
		if (Validator.isNotNull(_backURL)) {
			return _backURL;
		}

		_backURL = ParamUtil.getString(_httpServletRequest, "backURL");

		return _backURL;
	}

	public String getDisplayStyle() {
		if (Validator.isNotNull(_displayStyle)) {
			return _displayStyle;
		}

		_displayStyle = SearchDisplayStyleUtil.getDisplayStyle(
			PortalUtil.getHttpServletRequest(_liferayPortletRequest),
			LayoutAdminPortletKeys.GROUP_PAGES, "orphan-display-style", "list");

		return _displayStyle;
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(
			_liferayPortletRequest, "orderByType", "asc");

		return _orderByType;
	}

	public List<Portlet> getOrphanPortlets() {
		return getOrphanPortlets(getSelLayout());
	}

	public List<Portlet> getOrphanPortlets(Layout layout) {
		if (!layout.isSupportsEmbeddedPortlets()) {
			return Collections.emptyList();
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		LayoutTypePortlet selLayoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		List<Portlet> explicitlyAddedPortlets =
			selLayoutTypePortlet.getExplicitlyAddedPortlets();

		List<String> explicitlyAddedPortletIds = new ArrayList<>();

		for (Portlet explicitlyAddedPortlet : explicitlyAddedPortlets) {
			explicitlyAddedPortletIds.add(
				explicitlyAddedPortlet.getPortletId());
		}

		List<Portlet> orphanPortlets = new ArrayList<>();

		List<PortletPreferences> portletPreferences =
			PortletPreferencesLocalServiceUtil.getPortletPreferences(
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid());

		for (PortletPreferences portletPreference : portletPreferences) {
			String portletId = portletPreference.getPortletId();

			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				themeDisplay.getCompanyId(), portletId);

			if (portlet.isSystem() ||
				explicitlyAddedPortletIds.contains(portletId)) {

				continue;
			}

			orphanPortlets.add(portlet);
		}

		HttpServletRequest httpServletRequest =
			PortalUtil.getHttpServletRequest(_liferayPortletRequest);

		boolean orderByAsc = false;

		if (Objects.equals(getOrderByType(), "asc")) {
			orderByAsc = true;
		}

		PortletTitleComparator portletTitleComparator =
			new PortletTitleComparator(
				httpServletRequest.getServletContext(),
				themeDisplay.getLocale(), orderByAsc);

		return ListUtil.sort(orphanPortlets, portletTitleComparator);
	}

	public SearchContainer<Portlet> getOrphanPortletsSearchContainer() {
		if (_orphanPortletsSearchContainer != null) {
			return _orphanPortletsSearchContainer;
		}

		SearchContainer<Portlet> orphanPortletsSearchContainer =
			new SearchContainer(
				_liferayPortletRequest, getPortletURL(), null, null);

		orphanPortletsSearchContainer.setDeltaConfigurable(false);
		orphanPortletsSearchContainer.setId("portlets");
		orphanPortletsSearchContainer.setOrderByCol("name");
		orphanPortletsSearchContainer.setOrderByType(getOrderByType());

		Layout selLayout = getSelLayout();

		if (!selLayout.isLayoutPrototypeLinkActive()) {
			orphanPortletsSearchContainer.setRowChecker(
				new EmptyOnClickRowChecker(_liferayPortletResponse));
		}

		List<Portlet> portlets = getOrphanPortlets();

		orphanPortletsSearchContainer.setResults(
			ListUtil.subList(
				portlets, orphanPortletsSearchContainer.getStart(),
				orphanPortletsSearchContainer.getEnd()));
		orphanPortletsSearchContainer.setTotal(portlets.size());

		_orphanPortletsSearchContainer = orphanPortletsSearchContainer;

		return _orphanPortletsSearchContainer;
	}

	public PortletURL getPortletURL() {
		return PortletURLBuilder.createRenderURL(
			_liferayPortletResponse
		).setMVCPath(
			"/orphan_portlets.jsp"
		).setBackURL(
			getBackURL()
		).setParameter(
			"displayStyle", getDisplayStyle()
		).buildPortletURL();
	}

	public Layout getSelLayout() {
		if (_selLayout != null) {
			return _selLayout;
		}

		if (getSelPlid() != LayoutConstants.DEFAULT_PLID) {
			_selLayout = LayoutLocalServiceUtil.fetchLayout(getSelPlid());
		}

		return _selLayout;
	}

	public Long getSelPlid() {
		if (_selPlid != null) {
			return _selPlid;
		}

		_selPlid = ParamUtil.getLong(
			_liferayPortletRequest, "selPlid", LayoutConstants.DEFAULT_PLID);

		return _selPlid;
	}

	public String getStatus(Portlet portlet) {
		HttpServletRequest httpServletRequest =
			PortalUtil.getHttpServletRequest(_liferayPortletRequest);

		if (!portlet.isActive()) {
			return LanguageUtil.get(httpServletRequest, "inactive");
		}
		else if (!portlet.isReady()) {
			return LanguageUtil.format(
				httpServletRequest, "is-not-ready", "portlet");
		}
		else if (portlet.isUndeployedPortlet()) {
			return LanguageUtil.get(httpServletRequest, "undeployed");
		}

		return LanguageUtil.get(httpServletRequest, "active");
	}

	private String _backURL;
	private String _displayStyle;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _orderByType;
	private SearchContainer<Portlet> _orphanPortletsSearchContainer;
	private Layout _selLayout;
	private Long _selPlid;

}