/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.assetpublisher.util;

import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetRendererFactory;

import javax.portlet.PortletURL;

/**
 * @author Juan Fernández
 */
public class AssetPublisherHelperImpl implements AssetPublisherHelper {

	@Override
	public String getAssetViewURL(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse, AssetEntry assetEntry) {

		PortletURL viewURL = liferayPortletResponse.createRenderURL();

		viewURL.setParameter("struts_action", "/asset_publisher/view_content");

		String currentURL = PortalUtil.getCurrentURL(liferayPortletRequest);

		viewURL.setParameter("redirect", currentURL);

		viewURL.setParameter(
			"assetEntryId", String.valueOf(assetEntry.getEntryId()));

		AssetRendererFactory assetRendererFactory =
			assetEntry.getAssetRendererFactory();

		AssetRenderer assetRenderer = assetEntry.getAssetRenderer();

		viewURL.setParameter("type", assetRendererFactory.getType());

		if (Validator.isNotNull(assetRenderer.getUrlTitle())) {
			viewURL.setParameter("urlTitle", assetRenderer.getUrlTitle());
		}

		return viewURL.toString();
	}

}