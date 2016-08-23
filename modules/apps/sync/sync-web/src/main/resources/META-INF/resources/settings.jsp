<%--
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
--%>

<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

boolean allowUserPersonalSites = PrefsPropsUtil.getBoolean(themeDisplay.getCompanyId(), SyncServiceConfigurationKeys.SYNC_ALLOW_USER_PERSONAL_SITES, SyncServiceConfigurationValues.SYNC_ALLOW_USER_PERSONAL_SITES);
boolean enabled = PrefsPropsUtil.getBoolean(themeDisplay.getCompanyId(), SyncServiceConfigurationKeys.SYNC_SERVICES_ENABLED, SyncServiceConfigurationValues.SYNC_SERVICES_ENABLED);
boolean forceSecurityMode = PrefsPropsUtil.getBoolean(themeDisplay.getCompanyId(), SyncServiceConfigurationKeys.SYNC_CLIENT_FORCE_SECURITY_MODE, SyncServiceConfigurationValues.SYNC_CLIENT_FORCE_SECURITY_MODE);
int maxConnections = PrefsPropsUtil.getInteger(themeDisplay.getCompanyId(), SyncServiceConfigurationKeys.SYNC_CLIENT_MAX_CONNECTIONS, SyncServiceConfigurationValues.SYNC_CLIENT_MAX_CONNECTIONS);
int maxDownloadRate = PrefsPropsUtil.getInteger(themeDisplay.getCompanyId(), SyncServiceConfigurationKeys.SYNC_CLIENT_MAX_DOWNLOAD_RATE, SyncServiceConfigurationValues.SYNC_CLIENT_MAX_DOWNLOAD_RATE);
int maxUploadRate = PrefsPropsUtil.getInteger(themeDisplay.getCompanyId(), SyncServiceConfigurationKeys.SYNC_CLIENT_MAX_UPLOAD_RATE, SyncServiceConfigurationValues.SYNC_CLIENT_MAX_UPLOAD_RATE);

boolean oAuthEnabled = PrefsPropsUtil.getBoolean(themeDisplay.getCompanyId(), SyncConstants.SYNC_OAUTH_ENABLED);
int pollInterval = PrefsPropsUtil.getInteger(themeDisplay.getCompanyId(), SyncServiceConfigurationKeys.SYNC_CLIENT_POLL_INTERVAL, SyncServiceConfigurationValues.SYNC_CLIENT_POLL_INTERVAL);

boolean deployed = SyncOAuthHelperUtil.isDeployed();
boolean oAuthApplicationAvailable = false;

if (deployed && oAuthEnabled) {
	long oAuthApplicationId = PrefsPropsUtil.getInteger(themeDisplay.getCompanyId(), SyncConstants.SYNC_OAUTH_APPLICATION_ID, 0);

	if (SyncOAuthHelperUtil.isOAuthApplicationAvailable(oAuthApplicationId)) {
		oAuthApplicationAvailable = true;
	}
}
%>

<c:if test="<%= oAuthEnabled %>">
	<c:choose>
		<c:when test="<%= !deployed %>">
			<div class="alert alert-warning">
				<liferay-ui:message key="oauth-publisher-is-not-deployed" />
			</div>
		</c:when>
		<c:when test="<%= !oAuthApplicationAvailable %>">
			<div class="alert alert-warning">
				<liferay-ui:message key="the-oauth-application-for-liferay-sync-is-missing" />
			</div>
		</c:when>
	</c:choose>
</c:if>

<liferay-portlet:actionURL var="configurationActionURL" />

<aui:form action="<%= configurationActionURL %>" cssClass="container-fluid-1280" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "updatePreferences();" %>'>
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />

	<liferay-ui:error exception="<%= OAuthPortletUndeployedException.class %>" message="oauth-publisher-is-not-deployed" />

	<h4><liferay-ui:message key="general" /></h4>

	<aui:fieldset>
		<aui:input label="allow-the-use-of-sync" name="enabled" type="toggle-switch" value="<%= enabled %>" />
		<aui:input label="allow-users-to-sync-their-personal-sites" name="allowUserPersonalSites" type="toggle-switch" value="<%= allowUserPersonalSites %>" />
	</aui:fieldset>

	<h4><liferay-ui:message key="desktop" /></h4>

	<c:if test="<%= deployed %>">
		<aui:fieldset>
			<aui:input helpMessage="oauth-enabled-help" label="oauth-enabled" name="oAuthEnabled" type="toggle-switch" value="<%= oAuthEnabled %>" />
		</aui:fieldset>
	</c:if>

	<aui:input helpMessage="max-connections-help" label="max-connections" name="maxConnections" type="text" value="<%= maxConnections %>" wrapperCssClass="lfr-input-text-container">
		<aui:validator name="digits" />
		<aui:validator name="min">1</aui:validator>
	</aui:input>

	<aui:input helpMessage="poll-interval-help" label="poll-interval" name="pollInterval" type="text" value="<%= pollInterval %>" wrapperCssClass="lfr-input-text-container">
		<aui:validator name="digits" />
		<aui:validator name="min">1</aui:validator>
	</aui:input>

	<aui:input helpMessage="max-download-rate-help" label="max-download-rate" name="maxDownloadRate" type="text" value="<%= maxDownloadRate %>" wrapperCssClass="lfr-input-text-container">
		<aui:validator name="digits" />
	</aui:input>

	<aui:input helpMessage="max-upload-rate-help" label="max-upload-rate" name="maxUploadRate" type="text" value="<%= maxUploadRate %>" wrapperCssClass="lfr-input-text-container">
		<aui:validator name="digits" />
	</aui:input>

	<h4><liferay-ui:message key="mobile" /></h4>

	<aui:fieldset>
		<aui:input helpMessage="force-security-mode-help" label="force-security-mode" name="forceSecurityMode" type="toggle-switch" value="<%= forceSecurityMode %>" />
	</aui:fieldset>

	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />updatePreferences() {
		submitForm(document.<portlet:namespace />fm, '<portlet:actionURL name="updatePreferences" />');
	}
</aui:script>