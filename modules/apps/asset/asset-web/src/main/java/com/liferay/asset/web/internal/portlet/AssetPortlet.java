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

package com.liferay.asset.web.internal.portlet;

import com.liferay.asset.constants.AssetPortletKeys;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.util.AssetEntryUsageHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ParamUtil;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.struts-path=asset",
		"com.liferay.portlet.system=true",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Asset", "javax.portlet.expiration-cache=0",
		"javax.portlet.name=" + AssetPortletKeys.ASSET,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class AssetPortlet extends MVCPortlet {

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		long assetEntryId = ParamUtil.getLong(renderRequest, "assetEntryId");

		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			assetEntryId);

		try {
			if (assetEntry != null) {
				_assetEntryUsageHelper.checkAssetEntryUsages(assetEntry);
			}
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"An error occurred while executing the checking of asset " +
						"entry usages for asset entry " + assetEntryId,
					pe);
			}
		}

		super.render(renderRequest, renderResponse);
	}

	private static final Log _log = LogFactoryUtil.getLog(AssetPortlet.class);

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private AssetEntryUsageHelper _assetEntryUsageHelper;

}