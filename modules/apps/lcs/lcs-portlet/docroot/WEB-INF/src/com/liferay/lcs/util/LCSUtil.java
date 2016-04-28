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

import com.liferay.lcs.advisor.InstallationEnvironmentAdvisor;
import com.liferay.lcs.advisor.InstallationEnvironmentAdvisorFactory;
import com.liferay.lcs.advisor.LCSAlertAdvisor;
import com.liferay.lcs.advisor.LCSClusterEntryTokenAdvisor;
import com.liferay.lcs.exception.InitializationException;
import com.liferay.lcs.exception.MissingLCSCredentialsException;
import com.liferay.lcs.InvalidLCSClusterEntryException;
import com.liferay.lcs.jsonwebserviceclient.OAuthJSONWebServiceClientImpl;
import com.liferay.lcs.oauth.OAuthUtil;
import com.liferay.lcs.rest.LCSClusterEntry;
import com.liferay.lcs.rest.LCSClusterEntryServiceUtil;
import com.liferay.lcs.rest.LCSClusterNode;
import com.liferay.lcs.rest.LCSClusterNodeServiceUtil;
import com.liferay.lcs.rest.LCSProject;
import com.liferay.petra.json.web.service.client.JSONWebServiceClient;
import com.liferay.petra.json.web.service.client.JSONWebServiceInvocationException;
import com.liferay.petra.json.web.service.client.JSONWebServiceTransportException;
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.license.messaging.LCSPortletState;
import com.liferay.portal.kernel.license.messaging.LicenseManagerMessageType;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.patcher.PatcherUtil;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletQName;
import com.liferay.portal.kernel.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.service.ReleaseLocalServiceUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.lang.reflect.Field;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Igor Beslic
 * @author Ivica Cardic
 */
public class LCSUtil {

	public static final int CREDENTIALS_INVALID = 2;

	public static final int CREDENTIALS_MISSING = 1;

	public static final int CREDENTIALS_SET = 3;

	public static void checkDefaultPortletPreferences() {
		try {
			javax.portlet.PortletPreferences jxPortletPreferences =
				fetchJxPortletPreferences();

			if (Validator.isNull(
					jxPortletPreferences.getValue(
						LCSConstants.METRICS_LCS_SERVICE_ENABLED, null)) &&
				Validator.isNotNull(
					PortletPropsValues.METRICS_LCS_SERVICE_ENABLED)) {

				jxPortletPreferences.setValue(
					LCSConstants.METRICS_LCS_SERVICE_ENABLED,
					PortletPropsValues.METRICS_LCS_SERVICE_ENABLED);

				jxPortletPreferences.store();
			}

			if (Validator.isNull(getPortalPropertiesBlacklist()) &&
				Validator.isNotNull(
					PortletPropsValues.PORTAL_PROPERTIES_BLACKLIST)) {

				jxPortletPreferences.setValue(
					LCSConstants.PORTAL_PROPERTIES_BLACKLIST,
					PortletPropsValues.PORTAL_PROPERTIES_BLACKLIST);

				jxPortletPreferences.store();
			}
		}
		catch (Exception e) {
			_log.error(
				"LCS Portlet plugin default preferences check failed", e);
		}
	}

	public static void deletePortletPreferences() {
		try {
			PortletPreferencesLocalServiceUtil.deletePortletPreferences(
				CompanyConstants.SYSTEM, PortletKeys.PREFS_OWNER_TYPE_COMPANY,
				0, PortletKeys.MONITORING);

			if (_log.isInfoEnabled()) {
				_log.info("Deleted LCS portlet preferences");
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public static javax.portlet.PortletPreferences fetchJxPortletPreferences() {
		return fetchJxPortletPreferences(null);
	}

	public static synchronized javax.portlet.PortletPreferences
		fetchJxPortletPreferences(PortletRequest portletRequest) {

		PortletPreferences portletPreferences = null;

		try {
			DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
				PortletPreferences.class,
				PortalClassLoaderUtil.getClassLoader());

			dynamicQuery.add(
				RestrictionsFactoryUtil.eq("ownerId", CompanyConstants.SYSTEM));
			dynamicQuery.add(
				RestrictionsFactoryUtil.eq(
					"ownerType", PortletKeys.PREFS_OWNER_TYPE_COMPANY));
			dynamicQuery.add(
				RestrictionsFactoryUtil.eq("plid", Long.valueOf(0)));
			dynamicQuery.add(
				RestrictionsFactoryUtil.eq(
					"portletId", PortletKeys.MONITORING));

			List<PortletPreferences> portletPreferencesList =
				PortletPreferencesLocalServiceUtil.dynamicQuery(dynamicQuery);

			if (!portletPreferencesList.isEmpty()) {
				if (portletPreferencesList.size() == 1) {
					portletPreferences = portletPreferencesList.get(0);
				}
				else {
					_log.error(
						"Unable to determine unique portlet preferences");

					if (portletRequest != null) {
						SessionErrors.add(
							portletRequest, "oAuthSettingsAccess");
					}

					return null;
				}
			}

			if (portletPreferences == null) {
				portletPreferences =
					PortletPreferencesLocalServiceUtil.addPortletPreferences(
						CompanyConstants.SYSTEM, CompanyConstants.SYSTEM,
						PortletKeys.PREFS_OWNER_TYPE_COMPANY, 0,
						PortletKeys.MONITORING, null, null);
			}
		}
		catch (SystemException se) {
			_log.error("Unable to fetch LCS portlet preferences", se);

			if (portletRequest != null) {
				SessionErrors.add(portletRequest, "oAuthSettingsAccess");
			}

			return null;
		}

		try {
			return PortletPreferencesFactoryUtil.fromXML(
				CompanyConstants.SYSTEM, CompanyConstants.SYSTEM,
				PortletKeys.PREFS_OWNER_TYPE_COMPANY, 0, PortletKeys.MONITORING,
				portletPreferences.getPreferences());
		}
		catch (SystemException se) {
			_log.error(se, se);

			if (portletRequest != null) {
				SessionErrors.add(portletRequest, "oAuthSettingsAccess");
			}
		}

		return null;
	}

	public static int getCredentialsStatus() {
		javax.portlet.PortletPreferences jxPortletPreferences =
			fetchJxPortletPreferences();

		if (jxPortletPreferences == null) {
			return CREDENTIALS_MISSING;
		}

		String lcsAccessToken = jxPortletPreferences.getValue(
			"lcsAccessToken", null);
		String lcsAccessSecret = jxPortletPreferences.getValue(
			"lcsAccessSecret", null);

		if (Validator.isNull(lcsAccessToken) ||
			Validator.isNull(lcsAccessSecret)) {

			return CREDENTIALS_INVALID;
		}

		return CREDENTIALS_SET;
	}

	public static String getDownloadsLayoutURL() {
		return getLCSLayoutURL(
			PortletPropsValues.OSB_LCS_PORTLET_LAYOUT_DOWNLOADS,
			Collections.<String, String>emptyMap());
	}

	public static String getFeedbackURL(HttpServletRequest request) {
		InstallationEnvironmentAdvisor installationEnvironmentAdvisor =
			InstallationEnvironmentAdvisorFactory.getInstance();

		Map<String, String> softwareMetadata =
			installationEnvironmentAdvisor.getSoftwareMetadata();

		StringBundler sb = new StringBundler(4 * softwareMetadata.size() + 19);

		sb.append("mailto:");
		sb.append(PortletPropsValues.FEEDBACK_EMAIL_ADDRESS);
		sb.append("?subject=");
		sb.append(
			LanguageUtil.get(
				request, "liferay-connected-services-client-feedback"));
		sb.append("&body=");
		sb.append(LanguageUtil.get(request, "system-details"));

		String newLine = HttpUtil.encodeURL(StringPool.NEW_LINE);

		sb.append(newLine);
		sb.append(LanguageUtil.get(request, "portal-edition"));
		sb.append(": ");
		sb.append(getPortalEdition());
		sb.append(newLine);
		sb.append(LanguageUtil.get(request, "portal-build-number"));
		sb.append(": ");
		sb.append(ReleaseInfo.getBuildNumber());
		sb.append(newLine);
		sb.append(LanguageUtil.get(request, "patching-tool-version"));
		sb.append(": ");
		sb.append(PatcherUtil.getPatchingToolVersion());
		sb.append(newLine);

		for (Map.Entry<String, String> entry : softwareMetadata.entrySet()) {
			sb.append(entry.getKey());
			sb.append(": ");
			sb.append(entry.getValue());
			sb.append(newLine);
		}

		return sb.toString();
	}

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

	public static Set<LCSAlert>
		getLCSClusterEntryTokenAlerts() {

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

	public static Map<String, Boolean> getLCSServicesPreferences() {
		javax.portlet.PortletPreferences jxPortletPreferences =
			LCSUtil.fetchJxPortletPreferences();

		Boolean metricsServiceEnabled = Boolean.valueOf(
			jxPortletPreferences.getValue(
				LCSConstants.METRICS_LCS_SERVICE_ENABLED,
				Boolean.TRUE.toString()));

		Boolean portalPropertiesServiceEnabled = Boolean.valueOf(
			jxPortletPreferences.getValue(
				LCSConstants.PORTAL_PROPERTIES_LCS_SERVICE_ENABLED,
				Boolean.TRUE.toString()));

		String portalEdition = LCSUtil.getPortalEdition();

		String patchesServiceEnabledDefault = StringPool.FALSE;

		if (Validator.equals(portalEdition, LCSConstants.PORTAL_EDITION_EE)) {
			patchesServiceEnabledDefault = StringPool.TRUE;
		}

		Boolean patchesServiceEnabled = Boolean.valueOf(
			jxPortletPreferences.getValue(
				LCSConstants.PATCHES_LCS_SERVICE_ENABLED,
				patchesServiceEnabledDefault));

		Boolean enableAllLCSServices =
			metricsServiceEnabled && portalPropertiesServiceEnabled &&
			(Validator.equals(portalEdition, LCSConstants.PORTAL_EDITION_CE) ||
			patchesServiceEnabled);

		Map<String, Boolean> preferences = new HashMap<>();

		preferences.put(
			LCSConstants.METRICS_LCS_SERVICE_ENABLED, metricsServiceEnabled);

		preferences.put(
			LCSConstants.PORTAL_PROPERTIES_LCS_SERVICE_ENABLED,
			portalPropertiesServiceEnabled);

		preferences.put(
			LCSConstants.PATCHES_LCS_SERVICE_ENABLED, patchesServiceEnabled);

		preferences.put("enableAllLCSServices", enableAllLCSServices);

		return preferences;
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

	public static String getPortalPropertiesBlacklist() {
		javax.portlet.PortletPreferences jxPortletPreferences =
			fetchJxPortletPreferences();

		return jxPortletPreferences.getValue(
			LCSConstants.PORTAL_PROPERTIES_BLACKLIST, null);
	}

	public static boolean hasLCSServicesPreferences() {
		javax.portlet.PortletPreferences jxPortletPreferences =
			LCSUtil.fetchJxPortletPreferences();

		Map<String, String[]> map = jxPortletPreferences.getMap();

		if (!map.containsKey(
				LCSConstants.PORTAL_PROPERTIES_LCS_SERVICE_ENABLED)) {

			return false;
		}

		return true;
	}

	public static boolean isLCSClusterNodeRegistered() {
		return isLCSClusterNodeRegistered(null);
	}

	public static boolean isLCSClusterNodeRegistered(
		PortletRequest portletRequest) {

		try {
			String serverKey = KeyGeneratorUtil.getKey();

			LCSClusterNode lcsClusterNode =
				LCSClusterNodeServiceUtil.fetchLCSClusterNode(serverKey);

			if ((lcsClusterNode == null) || lcsClusterNode.isArchived()) {
				return false;
			}

			return true;
		}
		catch (InitializationException ie) {
			_log.error("Unable to initialize LCS cluster node", ie);

			if (ie instanceof
					InitializationException.FileSystemAccessException) {

				addSessionErrors(portletRequest, "serverIdFileSystemAccess");
			}
			else if (ie instanceof InitializationException.KeyStoreException) {
				addSessionErrors(portletRequest, "keyStoreAccess");
			}
			else {
				addSessionErrors(portletRequest, "generalPluginAccess");
			}
		}
		catch (JSONWebServiceTransportException jsonwste) {
			_log.error(
				"Unable to connect to the LCS JSON web service", jsonwste);

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
			else if (jsonwste instanceof
						JSONWebServiceTransportException.CommunicationFailure) {

				addSessionErrors(portletRequest, "noConnection");
			}
		}
		catch (Exception e) {
			_log.error(
				"Unable to verify if the LCS cluster node is registered", e);

			Throwable cause = e.getCause();

			if (cause instanceof JSONWebServiceInvocationException) {
				String message = e.getMessage();

				if (message.contains("PrincipalException")) {
					addSessionErrors(
						portletRequest, "lcsInsufficientPrivileges");
				}
			}

			addSessionErrors(portletRequest, "generalPluginAccess");
		}

		return false;
	}

	public static boolean isLCSPortletAuthorized() {
		return isLCSPortletAuthorized(null);
	}

	public static boolean isLCSPortletAuthorized(
		PortletRequest portletRequest) {

		javax.portlet.PortletPreferences jxPortletPreferences =
			fetchJxPortletPreferences(portletRequest);

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
			jxPortletPreferences.setValue(
				"lcsAccessTokenNextValidityCheck",
				String.valueOf(lcsAccessTokenNextValidityCheckMillis));

			jxPortletPreferences.store();
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to store portlet preferences", e);
			}
		}

		return true;
	}

	public static boolean isMetricsServiceEnabled() {
		javax.portlet.PortletPreferences jxPortletPreferences =
			fetchJxPortletPreferences();

		return GetterUtil.getBoolean(
			jxPortletPreferences.getValue(
				LCSConstants.METRICS_LCS_SERVICE_ENABLED,
				Boolean.TRUE.toString()));
	}

	public static void removeCredentials() {
		javax.portlet.PortletPreferences jxPortletPreferences =
			fetchJxPortletPreferences();

		if (jxPortletPreferences == null) {
			return;
		}

		try {
			jxPortletPreferences.reset("lcsAccessToken");
			jxPortletPreferences.reset("lcsAccessSecret");
			jxPortletPreferences.reset("lcsClusterEntryId");
			jxPortletPreferences.reset("lcsClusterEntryTokenId");

			jxPortletPreferences.store();
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public static void sendServiceAvailabilityNotification(
		LCSPortletState lcsPortletState) {

		Message message = LicenseManagerMessageType.LCS_AVAILABLE.createMessage(
			lcsPortletState);

		MessageBusUtil.sendMessage(message.getDestinationName(), message);

		if (_log.isDebugEnabled()) {
			_log.debug("Service availability message published");
		}
	}

	public static void setUpJSONWebServiceClientCredentials()
		throws PortalException {

		javax.portlet.PortletPreferences jxPortletPreferences =
			fetchJxPortletPreferences();

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

	public static boolean storeLCSPortletCredentials(
			PortletRequest portletRequest, String lcsAccessSecret,
			String lcsAccessToken)
		throws Exception {

		return storeLCSPortletCredentials(
			portletRequest, lcsAccessSecret, lcsAccessToken, 0, 0);
	}

	public static boolean storeLCSPortletCredentials(
			PortletRequest portletRequest, String lcsAccessSecret,
			String lcsAccessToken, long lcsClusterEntryId,
			long lcsClusterEntryTokenId)
		throws Exception {

		javax.portlet.PortletPreferences jxPortletPreferences =
			fetchJxPortletPreferences(portletRequest);

		if (jxPortletPreferences == null) {
			return false;
		}

		jxPortletPreferences.setValue("lcsAccessSecret", lcsAccessSecret);
		jxPortletPreferences.setValue("lcsAccessToken", lcsAccessToken);

		if (lcsClusterEntryId != 0) {
			jxPortletPreferences.setValue(
				"lcsClusterEntryId", String.valueOf(lcsClusterEntryId));
			jxPortletPreferences.setValue(
				"lcsClusterEntryTokenId",
				String.valueOf(lcsClusterEntryTokenId));
		}

		jxPortletPreferences.store();

		return true;
	}

	public static boolean storeLCSPortletCredentials(
			String lcsAccessSecret, String lcsAccessToken,
			long lcsClusterEntryId, long lcsClusterEntryTokenId)
		throws Exception {

		return storeLCSPortletCredentials(
			null, lcsAccessSecret, lcsAccessToken, lcsClusterEntryId,
			lcsClusterEntryTokenId);
	}

	public static void validateLCSClusterNodeLCSClusterEntry()
		throws PortalException {

		if (!ClusterExecutorUtil.isEnabled()) {
			return;
		}

		String serverKey = KeyGeneratorUtil.getKey();

		LCSClusterNode lcsClusterNode =
			LCSClusterNodeServiceUtil.fetchLCSClusterNode(serverKey);

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

		sb.append("This node is clustered but it was already registered ");
		sb.append("at the LCS portal as a member of an nonclustered parent ");
		sb.append("environment. Please go to the LCS Portal and unregister ");
		sb.append("this node and register it as a member of clustered ");
		sb.append("environment.");

		throw new InvalidLCSClusterEntryException(sb.toString());
	}

	public void setJSONWebServiceClient(
		JSONWebServiceClient jsonWebServiceClient) {

		_jsonWebServiceClient = jsonWebServiceClient;
	}

	public void setLCSAlertAdvisor(LCSAlertAdvisor lcsAlertAdvisor) {
		_lcsAlertAdviser = lcsAlertAdvisor;
	}

	public void setLCSClusterEntryTokenAdvisor(
		LCSClusterEntryTokenAdvisor lcsClusterEntryTokenAdvisor) {

		_lcsClusterEntryTokenAdvisor = lcsClusterEntryTokenAdvisor;
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

		if (publicRenderParameters.size() == 0) {
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

	private static Log _log = LogFactoryUtil.getLog(LCSUtil.class);

	private static JSONWebServiceClient _jsonWebServiceClient;
	private static LCSAlertAdvisor _lcsAlertAdviser;
	private static LCSClusterEntryTokenAdvisor _lcsClusterEntryTokenAdvisor;

}