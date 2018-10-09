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

package com.liferay.asset.list.web.internal.display.context;

import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.asset.list.service.AssetListEntryUsageLocalServiceUtil;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Pavel Savinov
 */
public class AssetListUsagesDisplayContext {

	public AssetListUsagesDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	public int getAllUsageCount() {
		return AssetListEntryUsageLocalServiceUtil.getAssetListEntryUsagesCount(
			getAssetListEntryId());
	}

	public long getAssetListEntryId() {
		if (_assetListEntryId != null) {
			return _assetListEntryId;
		}

		_assetListEntryId = ParamUtil.getLong(
			_renderRequest, "assetListEntryId");

		return _assetListEntryId;
	}

	public int getDisplayPagesUsageCount() {
		return AssetListEntryUsageLocalServiceUtil.getAssetListEntryUsagesCount(
			getAssetListEntryId(),
			PortalUtil.getClassNameId(AssetDisplayPageEntry.class));
	}

	public String getNavigation() {
		if (Validator.isNotNull(_navigation)) {
			return _navigation;
		}

		_navigation = ParamUtil.getString(_renderRequest, "navigation", "all");

		return _navigation;
	}

	public int getPagesUsageCount() {
		return AssetListEntryUsageLocalServiceUtil.getAssetListEntryUsagesCount(
			getAssetListEntryId(), PortalUtil.getClassNameId(Layout.class));
	}

	public int getPageTemplatesUsageCount() {
		return AssetListEntryUsageLocalServiceUtil.getAssetListEntryUsagesCount(
			getAssetListEntryId(),
			PortalUtil.getClassNameId(LayoutPageTemplateEntry.class));
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/view_usages.jsp");
		portletURL.setParameter("redirect", getRedirect());
		portletURL.setParameter(
			"assetListEntryId", String.valueOf(getAssetListEntryId()));

		return portletURL;
	}

	public String getRedirect() {
		if (_redirect != null) {
			return _redirect;
		}

		_redirect = ParamUtil.getString(_renderRequest, "redirect");

		return _redirect;
	}

	private Long _assetListEntryId;
	private String _navigation;
	private String _redirect;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;

}