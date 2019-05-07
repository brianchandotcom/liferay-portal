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

package com.liferay.site.admin.web.internal.display.context;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.service.LayoutSetPrototypeServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.admin.web.internal.display.context.comparator.SiteInitializerNameComparator;
import com.liferay.site.admin.web.internal.util.SiteInitializerItem;
import com.liferay.site.constants.SiteWebKeys;
import com.liferay.site.initializer.SiteInitializer;
import com.liferay.site.initializer.SiteInitializerRegistry;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class SelectSiteInitializerDisplayContext {

	public SelectSiteInitializerDisplayContext(
		HttpServletRequest httpServletRequest, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_httpServletRequest = httpServletRequest;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_siteInitializerRegistry =
			(SiteInitializerRegistry)httpServletRequest.getAttribute(
				SiteWebKeys.SITE_INITIALIZER_REGISTRY);
	}

	public String getBackURL() {
		if (_backURL != null) {
			return _backURL;
		}

		String redirect = ParamUtil.getString(_httpServletRequest, "redirect");

		_backURL = ParamUtil.getString(
			_httpServletRequest, "backURL", redirect);

		return _backURL;
	}

	public long getParentGroupId() {
		if (_parentGroupId != null) {
			return _parentGroupId;
		}

		_parentGroupId = ParamUtil.getLong(
			_httpServletRequest, "parentGroupId");

		return _parentGroupId;
	}

	public SearchContainer getSearchContainer() throws PortalException {
		SearchContainer<SiteInitializerItem>
			siteInitializerItemSearchContainer = new SearchContainer<>(
				_renderRequest, _getPortletURL(), null,
				"there-are-no-site-templates");

		List<SiteInitializerItem> siteInitializerItems =
			_getSiteInitializerItems();

		siteInitializerItemSearchContainer.setTotal(
			siteInitializerItems.size());

		siteInitializerItems = ListUtil.subList(
			siteInitializerItems, siteInitializerItemSearchContainer.getStart(),
			siteInitializerItemSearchContainer.getEnd());

		siteInitializerItemSearchContainer.setResults(siteInitializerItems);

		return siteInitializerItemSearchContainer;
	}

	private PortletURL _getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "/site/select_site_initializer");
		portletURL.setParameter("redirect", getBackURL());

		return portletURL;
	}

	private List<SiteInitializerItem> _getSiteInitializerItems()
		throws PortalException {

		List<SiteInitializerItem> siteInitializerItems = new ArrayList<>();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		List<LayoutSetPrototype> layoutSetPrototypes =
			LayoutSetPrototypeServiceUtil.search(
				themeDisplay.getCompanyId(), Boolean.TRUE, null);

		for (LayoutSetPrototype layoutSetPrototype : layoutSetPrototypes) {
			siteInitializerItems.add(
				new SiteInitializerItem(
					layoutSetPrototype, themeDisplay.getLocale()));
		}

		List<SiteInitializer> siteInitializers =
			_siteInitializerRegistry.getSiteInitializers(
				themeDisplay.getCompanyId());

		for (SiteInitializer siteInitializer : siteInitializers) {
			SiteInitializerItem siteInitializerItem = new SiteInitializerItem(
				siteInitializer, themeDisplay.getLocale());

			siteInitializerItems.add(siteInitializerItem);
		}

		siteInitializerItems = ListUtil.sort(
			siteInitializerItems, new SiteInitializerNameComparator(true));

		return siteInitializerItems;
	}

	private String _backURL;
	private final HttpServletRequest _httpServletRequest;
	private Long _parentGroupId;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final SiteInitializerRegistry _siteInitializerRegistry;

}