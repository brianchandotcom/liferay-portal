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

package com.liferay.portal.action;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.license.util.LicenseManagerUtil;
import com.liferay.portal.license.util.LicenseUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.admin.util.OmniadminUtil;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Amos Fong
 */
public class UpdateLicenseAction extends Action {

	@Override
	public ActionForward execute(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		// PLACEHOLDER 01
		// PLACEHOLDER 02
		// PLACEHOLDER 03
		// PLACEHOLDER 04
		// PLACEHOLDER 05
		// PLACEHOLDER 06
		// PLACEHOLDER 07
		// PLACEHOLDER 08

		if (_isValidRequest(request)) {
			String cmd = ParamUtil.getString(request, Constants.CMD);

			String clusterNodeId = ParamUtil.getString(
				request, "clusterNodeId");

			if (cmd.equals("licenseProperties")) {
				String licenseProperties = _getLicenseProperties(clusterNodeId);

				response.setContentType(ContentTypes.TEXT_JAVASCRIPT);

				ServletResponseUtil.write(response, licenseProperties);

				return null;
			}
			else if (cmd.equals("serverInfo")) {
				String serverInfo = _getServerInfo(clusterNodeId);

				response.setContentType(ContentTypes.TEXT_JAVASCRIPT);

				ServletResponseUtil.write(response, serverInfo);

				return null;
			}

			return mapping.findForward("portal.license");
		}
		else {
			response.sendRedirect(
				PortalUtil.getPathContext() + "/c/portal/layout");

			return null;
		}
	}

	private String _getLicenseProperties(String clusterNodeId) {
		List<Map<String, String>> licenseProperties =
			LicenseManagerUtil.getClusterLicenseProperties(clusterNodeId);

		if (licenseProperties == null) {
			return StringPool.BLANK;
		}

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (Map<String, String> propertiesMap : licenseProperties) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			for (Map.Entry<String, String> entry : propertiesMap.entrySet()) {
				jsonObject.put(entry.getKey(), entry.getValue());
			}

			jsonArray.put(jsonObject);
		}

		return jsonArray.toString();
	}

	private String _getServerInfo(String clusterNodeId) throws Exception {
		Map<String, String> serverInfo = LicenseUtil.getClusterServerInfo(
			clusterNodeId);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (serverInfo != null) {
			for (Map.Entry<String, String> entry : serverInfo.entrySet()) {
				jsonObject.put(entry.getKey(), entry.getValue());
			}
		}

		return jsonObject.toString();
	}

	private boolean _isOmniAdmin(HttpServletRequest request) {
		User user = null;

		try {
			user = PortalUtil.getUser(request);
		}
		catch (Exception e) {
		}

		if ((user != null) && OmniadminUtil.isOmniadmin(user.getUserId())) {
			return true;
		}
		else {
			return false;
		}
	}

	private boolean _isValidRequest(HttpServletRequest request) {

		// PLACEHOLDER 09
		// PLACEHOLDER 10
		// PLACEHOLDER 11
		// PLACEHOLDER 12
		// PLACEHOLDER 13
		// PLACEHOLDER 14
		// PLACEHOLDER 15
		// PLACEHOLDER 16
		// PLACEHOLDER 17
		// PLACEHOLDER 18
		// PLACEHOLDER 19
		// PLACEHOLDER 20
		// PLACEHOLDER 21
		// PLACEHOLDER 22

		if (_isOmniAdmin(request)) {
			LicenseUtil.registerOrder(request);

			return true;
		}
		else {
			return false;
		}
	}

}