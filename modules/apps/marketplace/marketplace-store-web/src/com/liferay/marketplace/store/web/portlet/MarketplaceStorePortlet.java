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

package com.liferay.marketplace.store.web.portlet;

import com.liferay.marketplace.model.App;
import com.liferay.marketplace.service.AppLocalServiceUtil;
import com.liferay.marketplace.service.AppServiceUtil;
import com.liferay.marketplace.store.web.configuration.MarketplaceStoreWebConfigurationValues;
import com.liferay.marketplace.store.web.constants.MarketplaceStorePortletKeys;
import com.liferay.marketplace.store.web.constants.MarketplaceStoreWebKeys;
import com.liferay.marketplace.store.web.oauth.util.OAuthManager;
import com.liferay.marketplace.store.web.util.MarketplaceLicenseUtil;
import com.liferay.marketplace.util.MarketplaceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.expando.service.ExpandoValueLocalServiceUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.util.Map;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import org.scribe.model.Token;

/**
 * @author Ryan Park
 * @author Joan Kim
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.control-panel-entry-category=marketplace",
		"com.liferay.portlet.control-panel-entry-weight=2.0",
		"com.liferay.portlet.css-class-wrapper=marketplace-portlet",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.header-portlet-javascript=/js/main.js",
		"com.liferay.portlet.icon=/icons/store.png",
		"com.liferay.portlet.preferences-owned-by-group=false",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.description=", "javax.portlet.display-name=Store",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + MarketplaceStorePortletKeys.MARKETPLACE_STORE,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator,guest,power-user,user",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = {Portlet.class}
)
public class MarketplaceStorePortlet extends RemoteMVCPortlet {

	public void downloadApp(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String token = ParamUtil.getString(actionRequest, "token");
		long remoteAppId = ParamUtil.getLong(actionRequest, "appId");
		String url = ParamUtil.getString(actionRequest, "url");
		String version = ParamUtil.getString(actionRequest, "version");

		if (!url.startsWith(
				MarketplaceStoreWebConfigurationValues.MARKETPLACE_URL)) {

			JSONObject jsonObject = getAppJSONObject(remoteAppId);

			jsonObject.put("cmd", "downloadApp");
			jsonObject.put("message", "fail");

			writeJSON(actionRequest, actionResponse, jsonObject);

			return;
		}

		url = getRemoteAppPackageURL(
			themeDisplay.getCompanyId(), themeDisplay.getUserId(), token, url);

		URL urlObj = new URL(url);

		File tempFile = null;

		try {
			InputStream inputStream = urlObj.openStream();

			tempFile = FileUtil.createTempFile();

			FileUtil.write(tempFile, inputStream);

			App app = AppServiceUtil.updateApp(remoteAppId, version, tempFile);

			JSONObject jsonObject = getAppJSONObject(app.getRemoteAppId());

			jsonObject.put("cmd", "downloadApp");
			jsonObject.put("message", "success");

			writeJSON(actionRequest, actionResponse, jsonObject);
		}
		finally {
			if (tempFile != null) {
				tempFile.delete();
			}
		}
	}

	public void getApp(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long remoteAppId = ParamUtil.getLong(actionRequest, "appId");

		JSONObject jsonObject = getAppJSONObject(remoteAppId);

		jsonObject.put("cmd", "getApp");
		jsonObject.put("message", "success");

		writeJSON(actionRequest, actionResponse, jsonObject);
	}

	public void getBundledApps(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		Map<String, String> bundledApps = AppLocalServiceUtil.getBundledApps();

		JSONObject bundledAppJsonObject = JSONFactoryUtil.createJSONObject();

		Set<String> keys = bundledApps.keySet();

		for (String key : keys) {
			bundledAppJsonObject.put(key, bundledApps.get(key));
		}

		jsonObject.put("bundledApps", bundledAppJsonObject);

		jsonObject.put("cmd", "getBundledApps");
		jsonObject.put("message", "success");

		writeJSON(actionRequest, actionResponse, jsonObject);
	}

	public void getClientId(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String token = ParamUtil.getString(actionRequest, "token");

		String encodedClientId = MarketplaceUtil.encodeClientId(
			themeDisplay.getCompanyId(), themeDisplay.getUserId(), token);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("cmd", "getClientId");
		jsonObject.put("clientId", encodedClientId);
		jsonObject.put("token", token);

		writeJSON(actionRequest, actionResponse, jsonObject);
	}

	public void installApp(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long remoteAppId = ParamUtil.getLong(actionRequest, "appId");

		AppServiceUtil.installApp(remoteAppId);

		JSONObject jsonObject = getAppJSONObject(remoteAppId);

		jsonObject.put("cmd", "installApp");
		jsonObject.put("message", "success");

		writeJSON(actionRequest, actionResponse, jsonObject);
	}

	public void uninstallApp(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long remoteAppId = ParamUtil.getLong(actionRequest, "appId");

		AppServiceUtil.uninstallApp(remoteAppId);

		JSONObject jsonObject = getAppJSONObject(remoteAppId);

		jsonObject.put("cmd", "uninstallApp");
		jsonObject.put("message", "success");

		writeJSON(actionRequest, actionResponse, jsonObject);
	}

	public void updateApp(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String token = ParamUtil.getString(actionRequest, "token");
		long remoteAppId = ParamUtil.getLong(actionRequest, "appId");
		String version = ParamUtil.getString(actionRequest, "version");
		String url = ParamUtil.getString(actionRequest, "url");
		String orderUuid = ParamUtil.getString(actionRequest, "orderUuid");
		String productEntryName = ParamUtil.getString(
			actionRequest, "productEntryName");

		if (!url.startsWith(
				MarketplaceStoreWebConfigurationValues.MARKETPLACE_URL)) {

			JSONObject jsonObject = getAppJSONObject(remoteAppId);

			jsonObject.put("cmd", "downloadApp");
			jsonObject.put("message", "fail");

			writeJSON(actionRequest, actionResponse, jsonObject);

			return;
		}

		url = getRemoteAppPackageURL(
			themeDisplay.getCompanyId(), themeDisplay.getUserId(), token, url);

		URL urlObj = new URL(url);

		File tempFile = null;

		try {
			InputStream inputStream = urlObj.openStream();

			tempFile = FileUtil.createTempFile();

			FileUtil.write(tempFile, inputStream);

			AppServiceUtil.updateApp(remoteAppId, version, tempFile);

			if (Validator.isNull(orderUuid) &&
				Validator.isNotNull(productEntryName)) {

				orderUuid = MarketplaceLicenseUtil.getOrder(productEntryName);
			}

			if (Validator.isNotNull(orderUuid)) {
				MarketplaceLicenseUtil.registerOrder(
					orderUuid, productEntryName);
			}

			AppServiceUtil.installApp(remoteAppId);

			JSONObject jsonObject = getAppJSONObject(remoteAppId);

			jsonObject.put("cmd", "updateApp");
			jsonObject.put("message", "success");

			writeJSON(actionRequest, actionResponse, jsonObject);
		}
		finally {
			if (tempFile != null) {
				tempFile.delete();
			}
		}
	}

	public void updateClientId(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!themeDisplay.isSignedIn()) {
			return;
		}

		String clientId = ParamUtil.getString(actionRequest, "clientId");
		String token = ParamUtil.getString(actionRequest, "token");

		String decodedClientId = MarketplaceUtil.decodeClientId(
			clientId, token);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("cmd", "updateClientId");

		if (Validator.isNull(decodedClientId)) {
			jsonObject.put("message", "fail");

			writeJSON(actionRequest, actionResponse, jsonObject);

			return;
		}

		ExpandoValueLocalServiceUtil.addValue(
			themeDisplay.getCompanyId(), User.class.getName(), "MP", "clientId",
			themeDisplay.getUserId(), decodedClientId);

		jsonObject.put("message", "success");

		writeJSON(actionRequest, actionResponse, jsonObject);
	}

	@Override
	protected boolean callActionMethod(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (Validator.isNull(cmd)) {
			return super.callActionMethod(actionRequest, actionResponse);
		}

		try {
			if (cmd.equals("downloadApp")) {
				downloadApp(actionRequest, actionResponse);
			}
			else if (cmd.equals("getApp")) {
				getApp(actionRequest, actionResponse);
			}
			else if (cmd.equals("getBundledApps")) {
				getBundledApps(actionRequest, actionResponse);
			}
			else if (cmd.equals("getClientId")) {
				getClientId(actionRequest, actionResponse);
			}
			else if (cmd.equals("installApp")) {
				installApp(actionRequest, actionResponse);
			}
			else if (cmd.equals("updateApp")) {
				updateApp(actionRequest, actionResponse);
			}
			else if (cmd.equals("updateClientId")) {
				updateClientId(actionRequest, actionResponse);
			}
			else if (cmd.equals("uninstallApp")) {
				uninstallApp(actionRequest, actionResponse);
			}
			else {
				return super.callActionMethod(actionRequest, actionResponse);
			}
		}
		catch (Exception e) {
			throw new PortletException(e);
		}

		return true;
	}

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);

			Token accessToken = oAuthManager.getAccessToken(
				themeDisplay.getUser());

			if (accessToken == null) {
				include("/login.jsp", renderRequest, renderResponse);

				return;
			}
		}
		catch (PortalException pe) {
			throw new PortletException(pe);
		}

		renderRequest.setAttribute(
			MarketplaceStoreWebKeys.OAUTH_AUTHORIZED, Boolean.TRUE);

		super.doDispatch(renderRequest, renderResponse);
	}

	protected JSONObject getAppJSONObject(long remoteAppId) throws Exception {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		App app = AppLocalServiceUtil.fetchRemoteApp(remoteAppId);

		if (app != null) {
			jsonObject.put("appId", app.getRemoteAppId());
			jsonObject.put("downloaded", app.isDownloaded());
			jsonObject.put("installed", app.isInstalled());
			jsonObject.put("version", app.getVersion());
		}
		else {
			jsonObject.put("appId", remoteAppId);
			jsonObject.put("downloaded", false);
			jsonObject.put("installed", false);
			jsonObject.put("version", StringPool.BLANK);
		}

		return jsonObject;
	}

	protected String getRemoteAppPackageURL(
			long companyId, long userId, String token, String url)
		throws Exception {

		String encodedClientId = MarketplaceUtil.encodeClientId(
			companyId, userId, token);

		String portletNameSpace = PortalUtil.getPortletNamespace(
			_OSB_PORTLET_ID);

		url = HttpUtil.addParameter(
			url, portletNameSpace.concat("clientId"), encodedClientId);
		url = HttpUtil.addParameter(
			url, portletNameSpace.concat("token"), token);

		return url;
	}

	@Override
	protected String getServerPortletId() {
		return _OSB_PORTLET_ID;
	}

	@Override
	protected String getServerPortletURL() {
		return
			MarketplaceStoreWebConfigurationValues.MARKETPLACE_URL +
				"/osb-portlet/mp_server";
	}

	@Override
	@Reference
	protected void setOAuthManager(OAuthManager oAuthManager) {
		super.setOAuthManager(oAuthManager);
	}

	private static final String _OSB_PORTLET_ID = "12_WAR_osbportlet";

}