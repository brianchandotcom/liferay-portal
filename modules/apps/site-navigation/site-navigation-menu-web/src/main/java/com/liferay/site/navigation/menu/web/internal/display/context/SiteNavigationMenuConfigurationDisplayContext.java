/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.menu.web.internal.display.context;

import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.navigation.constants.SiteNavigationConstants;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Michael Bowerman
 */
public class SiteNavigationMenuConfigurationDisplayContext {

	public SiteNavigationMenuConfigurationDisplayContext(
		HttpServletRequest httpServletRequest,
		SiteNavigationMenuDisplayContext siteNavigationMenuDisplayContext) {

		_httpServletRequest = httpServletRequest;
		_siteNavigationMenuDisplayContext = siteNavigationMenuDisplayContext;
	}

	public String getPagesLabel() {
		if (_pagesLabel != null) {
			return _pagesLabel;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Group scopeGroup = themeDisplay.getScopeGroup();
		Layout layout = themeDisplay.getLayout();

		if (scopeGroup.isPrivateLayoutsEnabled()) {
			if (hasLayoutPageTemplateEntry() &&
				(scopeGroup.hasPublicLayouts() ||
				 scopeGroup.hasPrivateLayouts())) {

				_pagesLabel = "pages-hierarchy";
			}
			else if (scopeGroup.hasPublicLayouts() && layout.isPublicLayout()) {
				_pagesLabel = "public-pages-hierarchy";
			}
			else if (scopeGroup.hasPrivateLayouts() &&
					 layout.isPrivateLayout()) {

				_pagesLabel = "private-pages-hierarchy";
			}
			else {
				_pagesLabel = StringPool.BLANK;
			}

			return _pagesLabel;
		}

		if (scopeGroup.hasPublicLayouts() &&
			(hasLayoutPageTemplateEntry() || layout.isPublicLayout())) {

			_pagesLabel = "pages-hierarchy";
		}
		else {
			_pagesLabel = StringPool.BLANK;
		}

		return _pagesLabel;
	}

	public int getPagesValue() {
		if (_pagesValue != null) {
			return _pagesValue;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Group scopeGroup = themeDisplay.getScopeGroup();
		Layout layout = themeDisplay.getLayout();

		if (scopeGroup.isPrivateLayoutsEnabled()) {
			if (!hasLayoutPageTemplateEntry()) {
				if (scopeGroup.hasPublicLayouts() && layout.isPublicLayout()) {
					_pagesValue =
						SiteNavigationConstants.TYPE_PUBLIC_PAGES_HIERARCHY;
				}
				else if (scopeGroup.hasPrivateLayouts() &&
						 layout.isPrivateLayout()) {

					_pagesValue =
						SiteNavigationConstants.TYPE_PRIVATE_PAGES_HIERARCHY;
				}
			}

			if (_pagesValue == null) {
				_pagesValue = SiteNavigationConstants.TYPE_DEFAULT;
			}

			return _pagesValue;
		}

		if (scopeGroup.hasPublicLayouts()) {
			if (hasLayoutPageTemplateEntry()) {
				_pagesValue = SiteNavigationConstants.TYPE_DEFAULT;
			}
			else if (layout.isPublicLayout()) {
				_pagesValue =
					SiteNavigationConstants.TYPE_PUBLIC_PAGES_HIERARCHY;
			}
			else {
				_pagesValue = SiteNavigationConstants.TYPE_DEFAULT;
			}
		}
		else {
			_pagesValue = SiteNavigationConstants.TYPE_DEFAULT;
		}

		return _pagesValue;
	}

	public boolean hasLayoutPageTemplateEntry() {
		if (_hasLayoutPageTemplateEntry != null) {
			return _hasLayoutPageTemplateEntry;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		long plid = layout.getPlid();

		if (layout.isDraftLayout()) {
			plid = layout.getClassPK();
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			LayoutPageTemplateEntryLocalServiceUtil.
				fetchLayoutPageTemplateEntryByPlid(plid);

		if (layoutPageTemplateEntry != null) {
			_hasLayoutPageTemplateEntry = true;
		}
		else {
			_hasLayoutPageTemplateEntry = false;
		}

		return _hasLayoutPageTemplateEntry;
	}

	public boolean isPagesSelected() {
		if (_pagesSelected != null) {
			return _pagesSelected;
		}

		int selectSiteNavigationMenuType =
			_siteNavigationMenuDisplayContext.getSelectSiteNavigationMenuType();

		if (selectSiteNavigationMenuType == getPagesValue()) {
			_pagesSelected = true;
		}
		else {
			_pagesSelected = false;
		}

		return _pagesSelected;
	}

	private Boolean _hasLayoutPageTemplateEntry;
	private final HttpServletRequest _httpServletRequest;
	private String _pagesLabel;
	private Boolean _pagesSelected;
	private Integer _pagesValue;
	private final SiteNavigationMenuDisplayContext
		_siteNavigationMenuDisplayContext;

}