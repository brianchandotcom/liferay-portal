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

package com.liferay.layout.seo.web.internal.display.context;

import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType;
import com.liferay.item.selector.criteria.URLItemSelectorReturnType;
import com.liferay.item.selector.criteria.image.criterion.ImageItemSelectorCriterion;
import com.liferay.layout.seo.model.LayoutSEOSite;
import com.liferay.layout.seo.open.graph.OpenGraphConfiguration;
import com.liferay.layout.seo.service.LayoutSEOSiteLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class OpenGraphSettingsDisplayContext {

	public OpenGraphSettingsDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse, DLURLHelper dlurlHelper,
		ItemSelector itemSelector,
		OpenGraphConfiguration openGraphConfiguration) {

		_httpServletRequest = httpServletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_dlurlHelper = dlurlHelper;
		_itemSelector = itemSelector;
		_openGraphConfiguration = openGraphConfiguration;

		_themeDisplay = (ThemeDisplay)liferayPortletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public String getItemSelectorURL() {
		ItemSelectorCriterion imageItemSelectorCriterion =
			new ImageItemSelectorCriterion();

		imageItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new FileEntryItemSelectorReturnType(),
			new URLItemSelectorReturnType());

		PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(_httpServletRequest),
			_liferayPortletResponse.getNamespace() +
				"openGraphImageSelectedItem",
			imageItemSelectorCriterion);

		return itemSelectorURL.toString();
	}

	public String getOpenGraphImageURL() {
		LayoutSEOSite layoutSEOSite = _getLayoutSEOSite();

		if ((layoutSEOSite == null) ||
			(layoutSEOSite.getOpenGraphImageFileEntryId() == 0)) {

			return null;
		}

		try {
			return _dlurlHelper.getImagePreviewURL(
				DLAppLocalServiceUtil.getFileEntry(
					layoutSEOSite.getOpenGraphImageFileEntryId()),
				_themeDisplay);
		}
		catch (Exception e) {
			_log.error(e, e);

			return null;
		}
	}

	public boolean isOpenGraphEnabled() throws PortalException {
		return _openGraphConfiguration.isOpenGraphEnabled(_getGroup());
	}

	private Group _getGroup() {
		Group liveGroup = (Group)_httpServletRequest.getAttribute(
			"site.liveGroup");

		if (liveGroup != null) {
			return liveGroup;
		}

		return (Group)_httpServletRequest.getAttribute("site.group");
	}

	private LayoutSEOSite _getLayoutSEOSite() {
		Group group = _getGroup();

		return LayoutSEOSiteLocalServiceUtil.fetchLayoutSEOSiteByGroupId(
			group.getGroupId());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OpenGraphSettingsDisplayContext.class);

	private final DLURLHelper _dlurlHelper;
	private final HttpServletRequest _httpServletRequest;
	private final ItemSelector _itemSelector;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final OpenGraphConfiguration _openGraphConfiguration;
	private final ThemeDisplay _themeDisplay;

}