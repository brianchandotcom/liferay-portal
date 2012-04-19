/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.exception.SystemException;
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
public class TemplateHelperImpl implements TemplateHelper {

	public String getAssetViewURL(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			AssetEntry assetEntry)
		throws SystemException {

		AssetRendererFactory assetRendererFactory =
			assetEntry.getAssetRendererFactory();

		AssetRenderer assetRenderer = assetEntry.getAssetRenderer();

		PortletURL viewURL = liferayPortletResponse.createRenderURL();

		String currentURL = PortalUtil.getCurrentURL(liferayPortletRequest);

		viewURL.setParameter("struts_action", "/asset_publisher/view_content");
		viewURL.setParameter(
			"assetEntryId", String.valueOf(assetEntry.getEntryId()));
		viewURL.setParameter("type", assetRendererFactory.getType());
		viewURL.setParameter("redirect", currentURL);

		if (Validator.isNotNull(assetRenderer.getUrlTitle())) {
			viewURL.setParameter("urlTitle", assetRenderer.getUrlTitle());
		}

		return viewURL.toString();
	}

}