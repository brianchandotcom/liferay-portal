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

import com.liferay.lcs.oauth.OAuthUtil;
import com.liferay.lcs.rest.DuplicateLCSClusterEntryNameException;
import com.liferay.lcs.rest.DuplicateLCSClusterNodeNameException;
import com.liferay.lcs.rest.LCSClusterEntry;
import com.liferay.lcs.rest.LCSClusterEntryServiceUtil;
import com.liferay.lcs.rest.LCSProject;
import com.liferay.lcs.rest.LCSProjectServiceUtil;
import com.liferay.lcs.rest.LCSRoleServiceUtil;
import com.liferay.lcs.rest.NoSuchLCSSubscriptionEntryException;
import com.liferay.lcs.rest.RequiredLCSClusterEntryNameException;
import com.liferay.lcs.rest.RequiredLCSClusterNodeNameException;
import com.liferay.lcs.util.ClusterNodeUtil;
import com.liferay.lcs.util.LCSConnectionManagerUtil;
import com.liferay.lcs.util.LCSConstants;
import com.liferay.lcs.util.LCSUtil;
import com.liferay.lcs.util.PortletKeys;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.license.messaging.LCSPortletState;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletMode;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
import javax.portlet.PortletURL;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;

import org.scribe.exceptions.OAuthException;
import org.scribe.model.Token;

/**
 * @author Ivica Cardic
 * @author Igor Beslic
 * @author Marko Cikos
 * @author Peter Shin
 */
public class ConnectedServicesPortlet extends MVCPortlet {

	public void addLCSClusterNode(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long lcsClusterEntryId = ParamUtil.getLong(
			actionRequest, "lcsClusterEntryId");
		String lcsClusterNodeName = ParamUtil.getString(
			actionRequest, "lcsClusterNodeName");
		String lcsClusterNodeDescription = ParamUtil.getString(
			actionRequest, "lcsClusterNodeDescription");
		String lcsClusterNodeLocation = ParamUtil.getString(
			actionRequest, "lcsClusterNodeLocation");

		boolean addLCSClusterEntry = ParamUtil.getBoolean(
			actionRequest, "addLCSClusterEntry");

		try {
			if (addLCSClusterEntry) {
				long lcsProjectId = ParamUtil.getLong(
					actionRequest, "lcsProjectId");
				String lcsClusterEntryDescription = ParamUtil.getString(
					actionRequest, "newLCSClusterEntryDescription");
				String lcsClusterEntryLocation = ParamUtil.getString(
					actionRequest, "newLCSClusterEntryLocation");
				String lcsClusterEntryName = ParamUtil.getString(
					actionRequest, "newLCSClusterEntryName");

				LCSClusterEntry lcsClusterEntry = addLCSClusterEntry(
					lcsProjectId, lcsClusterEntryName,
					lcsClusterEntryDescription, lcsClusterEntryLocation);

				lcsClusterEntryId = lcsClusterEntry.getLcsClusterEntryId();
			}

			String siblingKey = ClusterNodeUtil.registerClusterNode(
				lcsClusterEntryId, lcsClusterNodeName,
				lcsClusterNodeDescription, lcsClusterNodeLocation);

			ClusterNodeUtil.registerUnregisteredClusterNodes(siblingKey);

			LCSConnectionManagerUtil.start();

			actionResponse.sendRedirect(getRedirect(actionRequest));
		}
		catch (DuplicateLCSClusterEntryNameException dlcscene) {
			SessionErrors.add(actionRequest, "duplicateLCSClusterEntryName");
		}
		catch (DuplicateLCSClusterNodeNameException dlcscnne) {
			SessionErrors.add(actionRequest, "duplicateLCSClusterNodeName");
		}
		catch (NoSuchLCSSubscriptionEntryException nslcssee) {
			SessionErrors.add(actionRequest, "noSuchLCSSubscriptionEntry");
		}
		catch (RequiredLCSClusterEntryNameException rlcscene) {
			SessionErrors.add(actionRequest, "requiredLCSClusterEntryName");
		}
		catch (RequiredLCSClusterNodeNameException rlcscnne) {
			SessionErrors.add(actionRequest, "requiredLCSClusterNodeName");
		}
	}

	public void addSiblingLCSClusterNode(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ClusterNodeUtil.registerClusterNode();

		LCSConnectionManagerUtil.start();

		actionResponse.sendRedirect(getRedirect(actionRequest));
	}

	public void resetCredentials(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		LCSConnectionManagerUtil.stop();

		LCSUtil.removeCredentials();
	}

	public void saveLCSServicesPreferences(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		Boolean hasLCSServicesPreferences = LCSUtil.hasLCSServicesPreferences();

		PortletPreferences portletPreferences =
			LCSUtil.fetchJxPortletPreferences();

		Boolean metricsServiceEnabled = ParamUtil.getBoolean(
			actionRequest, LCSConstants.METRICS_LCS_SERVICE_ENABLED, true);
		Boolean patchesServiceEnabled = ParamUtil.getBoolean(
			actionRequest, LCSConstants.PATCHES_LCS_SERVICE_ENABLED, true);
		Boolean portalPropertiesServiceEnabled = ParamUtil.getBoolean(
			actionRequest, LCSConstants.PORTAL_PROPERTIES_LCS_SERVICE_ENABLED,
			true);
		String portalPropertiesBlacklist = ParamUtil.getString(
			actionRequest, LCSConstants.PORTAL_PROPERTIES_BLACKLIST);

		portletPreferences.setValue(
			LCSConstants.METRICS_LCS_SERVICE_ENABLED,
			String.valueOf(metricsServiceEnabled));

		String portalEdition = LCSUtil.getPortalEdition();

		if (Validator.equals(portalEdition, LCSConstants.PORTAL_EDITION_EE)) {
			portletPreferences.setValue(
				LCSConstants.PATCHES_LCS_SERVICE_ENABLED,
				String.valueOf(patchesServiceEnabled));
		}

		portletPreferences.setValue(
			LCSConstants.PORTAL_PROPERTIES_LCS_SERVICE_ENABLED,
			String.valueOf(portalPropertiesServiceEnabled));

		portletPreferences.setValue(
			LCSConstants.PORTAL_PROPERTIES_BLACKLIST,
			portalPropertiesBlacklist);

		portletPreferences.store();

		if (hasLCSServicesPreferences &&
			LCSUtil.isLCSPortletAuthorized(actionRequest)) {

			ClusterNodeUtil.restartPosts(true);
		}
	}

	@Override
	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException {

		try {
			String resourceID = resourceRequest.getResourceID();

			if (resourceID.equals("connect")) {
				connect(resourceRequest, resourceResponse);
			}
			else if (resourceID.equals("disconnect")) {
				disconnect(resourceRequest, resourceResponse);
			}
			else if (resourceID.equals("serveConnectionStatus")) {
				serveConnectionStatus(resourceRequest, resourceResponse);
			}
			else if (resourceID.equals("serveLCSProject")) {
				serveLCSProject(resourceRequest, resourceResponse);
			}
			else {
				super.serveResource(resourceRequest, resourceResponse);
			}
		}
		catch (Exception e) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put("result", "failure");

			if (e instanceof DuplicateLCSClusterEntryNameException) {
				jsonObject.put("message", "duplicateLCSClusterEntryName");

				_log.error(e.getMessage());
			}
			else if (e instanceof RequiredLCSClusterEntryNameException) {
				jsonObject.put("message", "requiredLCSClusterEntryName");

				_log.error(e.getMessage());
			}
			else {
				_log.error(e, e);
			}

			writeJSON(resourceRequest, resourceResponse, jsonObject);
		}
	}

	public void setupOAuth(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		PortletSession portletSession = actionRequest.getPortletSession();

		Token requestToken = (Token)portletSession.getAttribute(
			Token.class.getName());

		String oAuthVerifier = ParamUtil.getString(
			actionRequest, "oauth_verifier");

		Token token = null;

		try {
			token = OAuthUtil.extractAccessToken(requestToken, oAuthVerifier);
		}
		catch (OAuthException oae) {
			HttpServletRequest httpServletRequest =
				PortalUtil.getHttpServletRequest(actionRequest);

			String oauthProblem = httpServletRequest.getParameter(
				"oauth_problem");

			if (oauthProblem.contains("token_expired")) {
				SessionErrors.add(actionRequest, "oAuthTokenExpired");

				return;
			}

			if (_log.isErrorEnabled()) {
				_log.error("OAuth authorization failed", oae);
			}
		}

		if (!LCSUtil.storeLCSPortletCredentials(
				actionRequest, token.getSecret(), token.getToken())) {

			return;
		}

		LCSUtil.setUpJSONWebServiceClientCredentials();

		if (LCSUtil.isLCSPortletAuthorized(actionRequest)) {
			List<LCSProject> lcsProjects =
				LCSProjectServiceUtil.getUserManageableLCSProjects();

			if (lcsProjects.isEmpty()) {
				LCSProjectServiceUtil.addDefaultLCSProject();
			}

			if (LCSUtil.isLCSClusterNodeRegistered(actionRequest)) {
				LCSUtil.sendServiceAvailabilityNotification(
					LCSPortletState.NO_SUBSCRIPTION);
			}

			actionResponse.sendRedirect(getRedirect(actionRequest));

			return;
		}

		SessionErrors.add(actionRequest, "oAuthAuthorizationFailed");
	}

	protected LCSClusterEntry addLCSClusterEntry(
			long lcsProjectId, String name, String description, String location)
		throws Exception {

		return LCSClusterEntryServiceUtil.addLCSClusterEntry(
			lcsProjectId, name, description, location,
			LCSUtil.getLocalLCSClusterEntryType());
	}

	protected void connect(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		boolean applyToSiblingClusterNodes = ParamUtil.getBoolean(
			resourceRequest, "applyToSiblingClusterNodes");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			ClusterNodeUtil.startPosts(applyToSiblingClusterNodes);

			jsonObject.put("result", "success");
		}
		catch (Exception e) {
			_log.error(e, e);

			jsonObject.put("result", "failure");
		}

		writeJSON(resourceRequest, resourceResponse, jsonObject);
	}

	protected void disconnect(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		boolean applyToSiblingClusterNodes = ParamUtil.getBoolean(
			resourceRequest, "applyToSiblingClusterNodes");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			ClusterNodeUtil.stopPosts(applyToSiblingClusterNodes);

			jsonObject.put("result", "success");
		}
		catch (Exception e) {
			_log.error(e, e);

			jsonObject.put("result", "failure");
		}

		writeJSON(resourceRequest, resourceResponse, jsonObject);
	}

	protected JSONArray getLCSClusterEntriesJSONArray(long lcsProjectId)
		throws Exception {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		List<LCSClusterEntry> lcsClusterEntries =
			LCSClusterEntryServiceUtil.getLCSProjectManageableLCSClusterEntries(
				lcsProjectId);

		for (LCSClusterEntry lcsClusterEntry : lcsClusterEntries) {
			jsonArray.put(getLCSClusterEntryJSONObject(lcsClusterEntry));
		}

		return jsonArray;
	}

	protected JSONObject getLCSClusterEntryJSONObject(
		LCSClusterEntry lcsClusterEntry) {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put(
			"lcsClusterEntryId", lcsClusterEntry.getLcsClusterEntryId());
		jsonObject.put("name", lcsClusterEntry.getName());
		jsonObject.put("type", lcsClusterEntry.getType());

		return jsonObject;
	}

	protected String getRedirect(PortletRequest portletRequest)
		throws Exception {

		return getRedirect(portletRequest, null);
	}

	protected String getRedirect(
			PortletRequest portletRequest, Map<String, String> parameters)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletURL portletURL = PortletURLFactoryUtil.create(
			portletRequest, PortletKeys.MONITORING, themeDisplay.getPlid(),
			PortletRequest.RENDER_PHASE);

		if (parameters != null) {
			for (Map.Entry<String, String> parameter : parameters.entrySet()) {
				portletURL.setParameter(
					parameter.getKey(), parameter.getValue());
			}
		}

		portletURL.setPortletMode(LiferayPortletMode.VIEW);
		portletURL.setWindowState(LiferayWindowState.NORMAL);

		return portletURL.toString();
	}

	protected void serveConnectionStatus(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put(
			"heartbeatExpiredError",
			LCSConnectionManagerUtil.isHandshakeExpired());
		jsonObject.put("pending", LCSConnectionManagerUtil.isPending());
		jsonObject.put("ready", LCSConnectionManagerUtil.isReady());

		writeJSON(resourceRequest, resourceResponse, jsonObject);
	}

	protected void serveLCSProject(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		long lcsProjectId = ParamUtil.getLong(resourceRequest, "lcsProjectId");

		jsonObject.put(
			"lcsAdministratorLCSRole",
			LCSRoleServiceUtil.hasUserLCSAdministratorLCSRole(lcsProjectId));
		jsonObject.put(
			"lcsClusterEntries", getLCSClusterEntriesJSONArray(lcsProjectId));

		jsonObject.put("result", "success");

		writeJSON(resourceRequest, resourceResponse, jsonObject);
	}

	private static Log _log = LogFactoryUtil.getLog(
		ConnectedServicesPortlet.class);

}