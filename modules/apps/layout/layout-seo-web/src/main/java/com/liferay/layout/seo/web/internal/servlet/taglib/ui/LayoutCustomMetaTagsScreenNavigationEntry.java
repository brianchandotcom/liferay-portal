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

package com.liferay.layout.seo.web.internal.servlet.taglib.ui;

import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.dynamic.data.mapping.storage.StorageEngine;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.layout.admin.constants.LayoutScreenNavigationEntryConstants;
import com.liferay.layout.seo.canonical.url.LayoutSEOCanonicalURLProvider;
import com.liferay.layout.seo.kernel.LayoutSEOLinkManager;
import com.liferay.layout.seo.service.LayoutSEOSiteLocalService;
import com.liferay.layout.seo.web.internal.constants.LayoutSEOWebKeys;
import com.liferay.layout.seo.web.internal.display.context.LayoutsSEODisplayContext;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.io.IOException;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia Garcia
 */
@Component(
	property = "screen.navigation.entry.order:Integer=3",
	service = ScreenNavigationEntry.class
)
public class LayoutCustomMetaTagsScreenNavigationEntry
	implements ScreenNavigationEntry<Layout> {

	@Override
	public String getCategoryKey() {
		return LayoutScreenNavigationEntryConstants.CATEGORY_KEY_GENERAL;
	}

	@Override
	public String getEntryKey() {
		return LayoutScreenNavigationEntryConstants.ENTRY_KEY_CUSTOM_META_TAGS;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(_getResourceBundle(locale), getEntryKey());
	}

	@Override
	public String getScreenNavigationKey() {
		return LayoutScreenNavigationEntryConstants.
			SCREEN_NAVIGATION_KEY_LAYOUT;
	}

	@Override
	public boolean isVisible(User user, Layout layout) {
		Group group = _groupLocalService.fetchGroup(layout.getGroupId());

		if ((group != null) && group.isLayoutPrototype()) {
			return false;
		}

		Layout draftLayout = _layoutLocalService.fetchLayout(
			_portal.getClassNameId(Layout.class), layout.getPlid());

		if ((Objects.equals(
				layout.getType(), LayoutConstants.TYPE_ASSET_DISPLAY) ||
			 Objects.equals(layout.getType(), LayoutConstants.TYPE_CONTENT)) &&
			(draftLayout == null)) {

			return false;
		}

		return true;
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		httpServletRequest.setAttribute(
			LayoutSEOWebKeys.LAYOUT_PAGE_LAYOUT_SEO_DISPLAY_CONTEXT,
			new LayoutsSEODisplayContext(
				_dlAppService, _dlurlHelper, _layoutSEOCanonicalURLProvider,
				_layoutSEOLinkManager, _layoutSEOSiteLocalService,
				_portal.getLiferayPortletRequest(
					(PortletRequest)httpServletRequest.getAttribute(
						JavaConstants.JAVAX_PORTLET_REQUEST)),
				_portal.getLiferayPortletResponse(
					(RenderResponse)httpServletRequest.getAttribute(
						JavaConstants.JAVAX_PORTLET_RESPONSE)),
				_storageEngine));

		_jspRenderer.renderJSP(
			httpServletRequest, httpServletResponse,
			"/layout/screen/navigation/entries/custom_meta_tags.jsp");
	}

	private ResourceBundle _getResourceBundle(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return new AggregateResourceBundle(
			resourceBundle, _portal.getResourceBundle(locale));
	}

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLURLHelper _dlurlHelper;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutSEOCanonicalURLProvider _layoutSEOCanonicalURLProvider;

	@Reference
	private LayoutSEOLinkManager _layoutSEOLinkManager;

	@Reference
	private LayoutSEOSiteLocalService _layoutSEOSiteLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private StorageEngine _storageEngine;

}