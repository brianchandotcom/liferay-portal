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

package com.liferay.lcs.portlet;

import com.liferay.lcs.util.LCSConnectionManagerUtil;
import com.liferay.lcs.util.LCSConstants;
import com.liferay.lcs.util.LCSPortletPreferencesUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import java.io.IOException;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * @author Ivica Cardic
 * @author Igor Beslic
 * @author Marko Cikos
 * @author Peter Shin
 */
public class ConnectedServicesPortlet extends MVCPortlet {

	@Override
	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException {

		try {
			String resourceID = resourceRequest.getResourceID();

			if (resourceID.equals("serveConnectionStatus")) {
				serveConnectionStatus(resourceRequest, resourceResponse);
			}
			else {
				super.serveResource(resourceRequest, resourceResponse);
			}
		}
		catch (Exception e) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put(
				LCSConstants.JSON_KEY_RESULT, LCSConstants.JSON_VALUE_FAILURE);

			_log.error(e, e);

			writeJSON(resourceRequest, resourceResponse, jsonObject);
		}
	}

	protected void serveConnectionStatus(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put(
			LCSConstants.JSON_KEY_RESULT, LCSConstants.JSON_VALUE_SUCCESS);
		jsonObject.put(
			"lcsGatewayAvailable",
			LCSConnectionManagerUtil.isLCSGatewayAvailable());
		jsonObject.put("ready", LCSConnectionManagerUtil.isReady());

		writeJSON(resourceRequest, resourceResponse, jsonObject);
	}

	protected void storeLCSServices(
			boolean metricsServiceEnabled, boolean patchesServiceEnabled,
			boolean portalPropertiesServiceEnabled,
			String portalPropertiesBlacklist)
		throws Exception {

		LCSPortletPreferencesUtil.store(
			LCSConstants.METRICS_LCS_SERVICE_ENABLED,
			String.valueOf(metricsServiceEnabled));

		LCSPortletPreferencesUtil.store(
			LCSConstants.PATCHES_LCS_SERVICE_ENABLED,
			String.valueOf(patchesServiceEnabled));

		LCSPortletPreferencesUtil.store(
			LCSConstants.PORTAL_PROPERTIES_LCS_SERVICE_ENABLED,
			String.valueOf(portalPropertiesServiceEnabled));

		LCSPortletPreferencesUtil.store(
			LCSConstants.PORTAL_PROPERTIES_BLACKLIST,
			portalPropertiesBlacklist);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ConnectedServicesPortlet.class);

}