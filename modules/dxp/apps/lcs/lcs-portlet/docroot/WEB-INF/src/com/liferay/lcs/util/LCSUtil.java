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

package com.liferay.lcs.util;

import com.liferay.lcs.advisor.LCSAlertAdvisor;
import com.liferay.lcs.advisor.LCSClusterEntryTokenAdvisor;
import com.liferay.lcs.exception.InvalidLCSClusterEntryException;
import com.liferay.lcs.exception.MissingLCSCredentialsException;
import com.liferay.lcs.jsonwebserviceclient.OAuthJSONWebServiceClientImpl;
import com.liferay.lcs.oauth.OAuthUtil;
import com.liferay.lcs.rest.LCSClusterEntry;
import com.liferay.lcs.rest.LCSClusterEntryServiceUtil;
import com.liferay.lcs.rest.LCSClusterNode;
import com.liferay.lcs.rest.LCSClusterNodeService;
import com.liferay.lcs.rest.LCSProject;
import com.liferay.petra.json.web.service.client.JSONWebServiceClient;
import com.liferay.petra.json.web.service.client.JSONWebServiceInvocationException;
import com.liferay.petra.json.web.service.client.JSONWebServiceTransportException;
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.license.messaging.LCSPortletState;
import com.liferay.portal.kernel.license.messaging.LicenseManagerMessageType;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.portlet.PortletQName;
import com.liferay.portal.kernel.service.ReleaseLocalServiceUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.lang.reflect.Field;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.portlet.PortletRequest;

/**
 * @author Igor Beslic
 * @author Ivica Cardic
 */
public class LCSUtil {

	public static String getLCSClusterEntryLayoutURL(
		LCSProject lcsProject, LCSClusterNode lcsClusterNode) {

		Map<String, String> publicRenderParameters = new HashMap<>();

		publicRenderParameters.put(
			getPublicRenderParameterName("layoutLCSClusterEntryId"),
			String.valueOf(lcsClusterNode.getLcsClusterEntryId()));
		publicRenderParameters.put(
			getPublicRenderParameterName("layoutLCSProjectId"),
			String.valueOf(lcsProject.getLcsProjectId()));

		return getLCSLayoutURL(
			PortletPropsValues.OSB_LCS_PORTLET_LAYOUT_LCS_CLUSTER_ENTRY,
			publicRenderParameters);
	}

	public static Set<LCSAlert> getLCSClusterEntryTokenAlerts() {
		return _lcsClusterEntryTokenAdvisor.getLCSClusterEntryTokenAlerts();
	}

	public static String getLCSClusterNodeLayoutURL(
		LCSProject lcsProject, LCSClusterNode lcsClusterNode) {

		Map<String, String> publicRenderParameters = new HashMap<>();

		publicRenderParameters.put(
			getPublicRenderParameterName("layoutLCSClusterEntryId"),
			String.valueOf(lcsClusterNode.getLcsClusterEntryId()));
		publicRenderParameters.put(
			getPublicRenderParameterName("layoutLCSClusterNodeId"),
			String.valueOf(lcsClusterNode.getLcsClusterNodeId()));
		publicRenderParameters.put(
			getPublicRenderParameterName("layoutLCSProjectId"),
			String.valueOf(lcsProject.getLcsProjectId()));

		return getLCSLayoutURL(
			PortletPropsValues.OSB_LCS_PORTLET_LAYOUT_LCS_CLUSTER_NODE,
			publicRenderParameters);
	}

	public static String getLCSPortalURL() {
		StringBundler sb = new StringBundler(5);

		sb.append(PortletPropsValues.OSB_LCS_PORTLET_PROTOCOL);
		sb.append(Http.PROTOCOL_DELIMITER);
		sb.append(PortletPropsValues.OSB_LCS_PORTLET_HOST_NAME);

		if ((PortletPropsValues.OSB_LCS_PORTLET_HOST_PORT == Http.HTTP_PORT) ||
			(PortletPropsValues.OSB_LCS_PORTLET_HOST_PORT == Http.HTTPS_PORT)) {

			return sb.toString();
		}

		sb.append(StringPool.COLON);
		sb.append(PortletPropsValues.OSB_LCS_PORTLET_HOST_PORT);

		return sb.toString();
	}

	public static int getLCSPortletBuildNumber() {
		Release release = null;

		try {
			release = ReleaseLocalServiceUtil.fetchRelease("lcs-portlet");
		}
		catch (SystemException se) {
			throw new RuntimeException(se);
		}

		return release.getBuildNumber();
	}

	public static String getLCSProjectLayoutURL(LCSProject lcsProject) {
		Map<String, String> publicRenderParameters = new HashMap<>();

		publicRenderParameters.put(
			getPublicRenderParameterName("layoutLCSProjectId"),
			String.valueOf(lcsProject.getLcsProjectId()));

		return getLCSLayoutURL(
			PortletPropsValues.OSB_LCS_PORTLET_LAYOUT_LCS_PROJECT,
			publicRenderParameters);
	}

	public static int getLocalLCSClusterEntryType() {
		if (ClusterExecutorUtil.isEnabled()) {
			return LCSConstants.LCS_CLUSTER_ENTRY_TYPE_CLUSTER;
		}

		return LCSConstants.LCS_CLUSTER_ENTRY_TYPE_ENVIRONMENT;
	}

	public static String getPortalEdition() {
		try {
			Field field = ReleaseInfo.class.getDeclaredField(
				"_VERSION_DISPLAY_NAME");

			field.setAccessible(true);

			StringTokenizer stringTokenizer = new StringTokenizer(
				(String)field.get(null));

			stringTokenizer.nextToken();

			return stringTokenizer.nextToken();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static synchronized String getPortalPropertiesBlacklist() {
		javax.portlet.PortletPreferences jxPortletPreferences =
			LCSPortletPreferencesUtil.fetchReadOnlyJxPortletPreferences();

		return jxPortletPreferences.getValue(
			LCSConstants.PORTAL_PROPERTIES_BLACKLIST, null);
	}

	public static String getRegistrationLayoutURL(
		LCSProject lcsProject, LCSClusterNode lcsClusterNode) {

		Map<String, String> publicRenderParameters = new HashMap<>();

		publicRenderParameters.put(
			getPublicRenderParameterName("environmentPage"), "registration");
		publicRenderParameters.put(
			getPublicRenderParameterName("layoutLCSClusterEntryId"),
			String.valueOf(lcsClusterNode.getLcsClusterEntryId()));
		publicRenderParameters.put(
			getPublicRenderParameterName("layoutLCSProjectId"),
			String.valueOf(lcsProject.getLcsProjectId()));

		return getLCSLayoutURL(
			PortletPropsValues.OSB_LCS_PORTLET_LAYOUT_LCS_CLUSTER_ENTRY,
			publicRenderParameters);
	}

	public static boolean isLCSClusterNodeRegistered() {
		LCSClusterNode lcsClusterNode =
			_lcsClusterNodeService.fetchLCSClusterNode(_keyGenerator.getKey());

		if ((lcsClusterNode == null) || lcsClusterNode.isArchived()) {
			return false;
		}

		return true;
	}

	public static synchronized boolean isLCSPortletAuthorized() {
		return isLCSPortletAuthorized(null);
	}

	public static synchronized boolean isLCSPortletAuthorized(
		PortletRequest portletRequest) {

		javax.portlet.PortletPreferences jxPortletPreferences =
			LCSPortletPreferencesUtil.fetchReadOnlyJxPortletPreferences(
				portletRequest);

		if (jxPortletPreferences == null) {
			return false;
		}

		String lcsAccessToken = jxPortletPreferences.getValue(
			"lcsAccessToken", null);
		String lcsAccessSecret = jxPortletPreferences.getValue(
			"lcsAccessSecret", null);

		if (Validator.isNull(lcsAccessToken) ||
			Validator.isNull(lcsAccessSecret)) {

			return false;
		}

		String lcsAccessTokenNextValidityCheck = jxPortletPreferences.getValue(
			"lcsAccessTokenNextValidityCheck", null);

		if (System.currentTimeMillis() <
				GetterUtil.getLong(lcsAccessTokenNextValidityCheck)) {

			return true;
		}

		if (!(_jsonWebServiceClient instanceof OAuthJSONWebServiceClientImpl)) {
			return true;
		}

		try {
			((OAuthJSONWebServiceClientImpl)_jsonWebServiceClient).
				testOAuthRequest();
		}
		catch (JSONWebServiceInvocationException jsonwsie) {
			_log.error(
				"Unable to connect to the test JSON web service", jsonwsie);

			addSessionErrors(portletRequest, "jsonWebServicePing");

			return false;
		}
		catch (JSONWebServiceTransportException jsonwste) {
			_log.error(
				"Unable to connect to the test JSON web service", jsonwste);

			if (jsonwste instanceof
					JSONWebServiceTransportException.AuthenticationFailure) {

				if ((portletRequest != null) &&
					OAuthUtil.hasOAuthException(jsonwste)) {

					OAuthUtil.processOAuthException(portletRequest, jsonwste);
				}
				else {
					addSessionErrors(portletRequest, "invalidCredential");
				}
			}

			if (jsonwste instanceof
					JSONWebServiceTransportException.CommunicationFailure) {

				addSessionErrors(portletRequest, "noConnection");

				if (portletRequest == null) {
					throw jsonwste;
				}
			}

			return false;
		}
		catch (Exception e) {
			_log.error(e, e);

			addSessionErrors(portletRequest, "generalPluginAccess");

			return false;
		}

		long lcsAccessTokenNextValidityCheckMillis =
			System.currentTimeMillis() + 300000;

		try {
			LCSPortletPreferencesUtil.store(
				"lcsAccessTokenNextValidityCheck",
				String.valueOf(lcsAccessTokenNextValidityCheckMillis));
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to store portlet preferences", e);
			}
		}

		return true;
	}

	public static void processLCSPortletState(LCSPortletState lcsPortletState) {
		if (lcsPortletState != LCSPortletState.PLUGIN_ABSENT) {
			try {
				LCSPortletPreferencesUtil.store(
					"lcsPortletState", lcsPortletState.name());

				if (_log.isDebugEnabled()) {
					_log.debug("LCS portlet state saved");
				}
			}
			catch (Exception e) {
				_log.error("LCS portlet state is not saved");
			}
		}

		_sendServiceAvailabilityNotification(lcsPortletState);
	}

	public static void setUpJSONWebServiceClientCredentials()
		throws PortalException {

		javax.portlet.PortletPreferences jxPortletPreferences =
			LCSPortletPreferencesUtil.fetchReadOnlyJxPortletPreferences();

		String lcsAccessToken = jxPortletPreferences.getValue(
			"lcsAccessToken", null);
		String lcsAccessSecret = jxPortletPreferences.getValue(
			"lcsAccessSecret", null);

		if (Validator.isNull(lcsAccessToken) ||
			Validator.isNull(lcsAccessSecret)) {

			throw new MissingLCSCredentialsException(
				"Unable to setup LCS credentials");
		}

		OAuthJSONWebServiceClientImpl oAuthJSONWebServiceClientImpl =
			(OAuthJSONWebServiceClientImpl)_jsonWebServiceClient;

		oAuthJSONWebServiceClientImpl.setAccessSecret(lcsAccessSecret);
		oAuthJSONWebServiceClientImpl.setAccessToken(lcsAccessToken);

		_jsonWebServiceClient.resetHttpClient();
	}

	public static void validateLCSClusterNodeLCSClusterEntry()
		throws PortalException {

		if (!ClusterExecutorUtil.isEnabled()) {
			return;
		}

		LCSClusterNode lcsClusterNode =
			_lcsClusterNodeService.fetchLCSClusterNode(_keyGenerator.getKey());

		if (lcsClusterNode == null) {
			return;
		}

		LCSClusterEntry lcsClusterEntry =
			LCSClusterEntryServiceUtil.getLCSClusterEntry(
				lcsClusterNode.getLcsClusterEntryId());

		if (lcsClusterEntry.isCluster()) {
			return;
		}

		_lcsAlertAdviser.add(LCSAlert.ERROR_INVALID_ENVIRONMENT_TYPE);

		StringBundler sb = new StringBundler(5);

		sb.append("This node is clustered but it was already registered at ");
		sb.append("the LCS portal as a member of an nonclustered parent ");
		sb.append("environment. Please go to the LCS Portal and unregister ");
		sb.append("this node and register it as a member of clustered ");
		sb.append("environment.");

		throw new InvalidLCSClusterEntryException(sb.toString());
	}

	public void setJSONWebServiceClient(
		JSONWebServiceClient jsonWebServiceClient) {

		_jsonWebServiceClient = jsonWebServiceClient;
	}

	public void setKeyGenerator(KeyGenerator keyGenerator) {
		_keyGenerator = keyGenerator;
	}

	public void setLCSAlertAdvisor(LCSAlertAdvisor lcsAlertAdvisor) {
		_lcsAlertAdviser = lcsAlertAdvisor;
	}

	public void setLCSClusterEntryTokenAdvisor(
		LCSClusterEntryTokenAdvisor lcsClusterEntryTokenAdvisor) {

		_lcsClusterEntryTokenAdvisor = lcsClusterEntryTokenAdvisor;
	}

	public void setLCSClusterNodeService(
		LCSClusterNodeService lcsClusterNodeService) {

		_lcsClusterNodeService = lcsClusterNodeService;
	}

	protected static void addSessionErrors(
		PortletRequest portletRequest, String key) {

		if (portletRequest == null) {
			return;
		}

		SessionErrors.add(portletRequest, key);
	}

	protected static String getLCSLayoutURL(
		String friendlyURL, Map<String, String> publicRenderParameters) {

		String layoutFullURL = getLCSPortalURL() + friendlyURL;

		if (publicRenderParameters.isEmpty()) {
			return layoutFullURL;
		}

		StringBundler sb = new StringBundler(
			4 * publicRenderParameters.size() + 2);

		sb.append(layoutFullURL);
		sb.append("?p_p_id=5_WAR_osblcsportlet");

		for (Map.Entry<String, String> entry :
				publicRenderParameters.entrySet()) {

			sb.append(StringPool.AMPERSAND);
			sb.append(entry.getKey());
			sb.append(StringPool.EQUAL);
			sb.append(entry.getValue());
		}

		return sb.toString();
	}

	protected static String getPublicRenderParameterName(String parameterName) {
		StringBundler sb = new StringBundler(4);

		sb.append(PortletQName.PUBLIC_RENDER_PARAMETER_NAMESPACE);
		sb.append("http://www.liferay.com/public-render-parameters".hashCode());
		sb.append(StringPool.UNDERLINE);
		sb.append(parameterName);

		return sb.toString();
	}

	private static void _sendServiceAvailabilityNotification(
		LCSPortletState lcsPortletState) {

		Message message = LicenseManagerMessageType.LCS_AVAILABLE.createMessage(
			lcsPortletState);

		MessageBusUtil.sendMessage(message.getDestinationName(), message);

		if (_log.isDebugEnabled()) {
			StringBundler sb = new StringBundler(3);

			sb.append("Service availability message published for LCS ");
			sb.append("portlet state ");
			sb.append(lcsPortletState.name());

			_log.debug(sb.toString());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(LCSUtil.class);

	private static JSONWebServiceClient _jsonWebServiceClient;
	private static KeyGenerator _keyGenerator;
	private static LCSAlertAdvisor _lcsAlertAdviser;
	private static LCSClusterEntryTokenAdvisor _lcsClusterEntryTokenAdvisor;
	private static LCSClusterNodeService _lcsClusterNodeService;

}