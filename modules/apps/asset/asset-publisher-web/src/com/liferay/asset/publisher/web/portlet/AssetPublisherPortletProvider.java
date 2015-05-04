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

package com.liferay.asset.publisher.web.portlet;

import com.liferay.asset.publisher.web.constants.AssetPublisherPortletKeys;
import com.liferay.asset.publisher.web.util.AssetPublisherUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.AddPortletProvider;
import com.liferay.portal.kernel.portlet.BasePortletProvider;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.ViewPortletProvider;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {"model.class.name=" + PortletProvider.CLASS_NAME_ANY},
	service = {AddPortletProvider.class, ViewPortletProvider.class}
)
public class AssetPublisherPortletProvider extends BasePortletProvider
	implements AddPortletProvider, ViewPortletProvider {

	@Override
	public String getPortletId() {
		return AssetPublisherPortletKeys.ASSET_PUBLISHER;
	}

	@Override
	public PortletURL getPortletURL(HttpServletRequest request)
		throws PortalException {

		PortletURL assetPublisherURL = super.getPortletURL(request);

		assetPublisherURL.setParameter("mvcPath", "/view_content.jsp");

		return assetPublisherURL;
	}

	@Override
	public void updatePortletPreferences(
			PortletPreferences portletPreferences, String portletId,
			String className, long classPK, ThemeDisplay themeDisplay)
		throws Exception {

		portletPreferences.setValue("displayStyle", "full-content");
		portletPreferences.setValue(
			"emailAssetEntryAddedEnabled", Boolean.FALSE.toString());
		portletPreferences.setValue("selectionStyle", "manual");
		portletPreferences.setValue(
			"showAddContentButton", Boolean.FALSE.toString());
		portletPreferences.setValue("showAssetTitle", Boolean.FALSE.toString());
		portletPreferences.setValue("showExtraInfo", Boolean.FALSE.toString());

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(
			className, classPK);

		AssetPublisherUtil.addSelection(
			themeDisplay, portletPreferences, portletId,
			assetEntry.getEntryId(), -1, assetEntry.getClassName());
	}

	@Override
	protected long getPlid(ThemeDisplay themeDisplay) throws PortalException {
		return themeDisplay.getPlid();
	}

}