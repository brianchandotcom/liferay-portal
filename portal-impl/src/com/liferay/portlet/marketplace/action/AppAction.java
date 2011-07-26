/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.marketplace.action;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.struts.JSONAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.marketplace.model.App;
import com.liferay.portlet.marketplace.service.AppLocalServiceUtil;

import java.io.InputStream;

import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author Ryan Park
 */
public class AppAction extends JSONAction {

	public String getJSON(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		String cmd = ParamUtil.getString(request, Constants.CMD);

		try {
			if (cmd.equals("download")) {
				downloadApp(request);
			}
			else if (cmd.equals("get")) {
				JSONObject data = getApp(request);

				jsonObject.put("data", data);
			}
			else if (cmd.equals("install")) {
				installApp(request);
			}
			else if (cmd.equals("uninstall")) {
				uninstallApp(request);
			}

			jsonObject.put("message", "success");
		}
		catch (Exception e) {
			jsonObject.put("message", "fail");
		}

		return jsonObject.toString();
	}

	protected void downloadApp(HttpServletRequest request)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long marketplaceAppId = ParamUtil.getLong(request, "appId");
		String version = ParamUtil.getString(request, "version");
		String[] moduleContextNames = StringUtil.split(ParamUtil.getString(
			request, "modules"));

		String url = ParamUtil.getString(request, "url");

		URL marketplaceURL = new URL(url);

		InputStream is = marketplaceURL.openStream();

		App app = AppLocalServiceUtil.fetchApp(marketplaceAppId);

		if (app == null) {
			app = AppLocalServiceUtil.addApp(
				themeDisplay.getUserId(), marketplaceAppId, version,
				moduleContextNames, is);
		}
		else {
			app = AppLocalServiceUtil.updateApp(
				app.getAppId(), version, moduleContextNames, is);
		}
	}

	public JSONObject getApp(HttpServletRequest request) throws Exception {
		long marketplaceAppId = ParamUtil.getLong(request, "appId");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		App app = AppLocalServiceUtil.fetchApp(marketplaceAppId);

		if (app != null) {
			jsonObject.put("appId", app.getMarketplaceAppId());
			jsonObject.put("downloaded", app.isDownloaded());
			jsonObject.put("installed", app.isInstalled());
			jsonObject.put("version", app.getVersion());
		}
		else {
			jsonObject.put("appId", marketplaceAppId);
			jsonObject.put("downloaded", false);
			jsonObject.put("installed", false);
			jsonObject.put("version", StringPool.BLANK);
		}

		return jsonObject;
	}

	public void installApp(HttpServletRequest request) throws Exception {
		long marketplaceAppId = ParamUtil.getLong(request, "appId");

		AppLocalServiceUtil.installApp(marketplaceAppId);
	}

	public void uninstallApp(HttpServletRequest request) throws Exception {
		long marketplaceAppId = ParamUtil.getLong(request, "appId");

		AppLocalServiceUtil.uninstallApp(marketplaceAppId);
	}

}