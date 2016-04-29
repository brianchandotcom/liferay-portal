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
Map<String, Boolean> lcsServicesPreferences = LCSUtil.getLCSServicesPreferences();
%>

<h3>
	<c:choose>
		<c:when test="<%= LCSUtil.hasLCSServicesPreferences() && LCSUtil.isLCSPortletAuthorized(liferayPortletRequest) %>">
			<liferay-ui:message key="enable-services" />
		</c:when>
		<c:otherwise>
			<liferay-ui:message arguments="1" key="registration-step-x-3" /> - <liferay-ui:message key="enable-services" />
		</c:otherwise>
	</c:choose>
</h3>

<c:if test='<%= SessionErrors.contains(liferayPortletRequest, "generalPluginAccess") %>'>
	<div class="alert alert-danger lcs-alert">
		<liferay-ui:message key="an-error-occurred-while-accessing-liferay-connected-services" />
	</div>
</c:if>

<liferay-portlet:actionURL name="saveLCSServicesPreferences" var="saveLCSServicesPreferencesURL" />

<aui:form action="<%= saveLCSServicesPreferencesURL.toString() %>" method="post" name="fm">
	<div class="all-lcs-services-panel">
		<aui:input checked='<%= lcsServicesPreferences.get("enableAllLCSServices") %>' label="enable-all-services" name="enableAllLCSServices" type="checkbox" value='<%= lcsServicesPreferences.get("enableAllLCSServices") %>' />

		<p>
			<liferay-ui:message arguments="portal-properties-security-sensitive" key="the-services-include-portal-analytics-fix-packs-management-and-portal-properties-analysis" />
		</p>

		<div class="lcs-services-panel <%= lcsServicesPreferences.get("enableAllLCSServices") ? "hide" : StringPool.BLANK %>" id="<portlet:namespace />lcsServicesPanel">
			<aui:input label="portal-analytics" name="<%= LCSConstants.METRICS_LCS_SERVICE_ENABLED %>" type="checkbox" value="<%= lcsServicesPreferences.get(LCSConstants.METRICS_LCS_SERVICE_ENABLED) %>" />

			<p>
				<liferay-ui:message key="by-enabling-portal-analytics-you-will-be-able-to-see-metrics-data-of-your-portal-for-example-page-and-portlet-load-times-and-analyze-portal-performance" />
			</p>

			<aui:input disabled="<%= Validator.equals(LCSUtil.getPortalEdition(), LCSConstants.PORTAL_EDITION_CE) %>" label="fix-packs-management" name="<%= LCSConstants.PATCHES_LCS_SERVICE_ENABLED %>" type="checkbox" value="<%= lcsServicesPreferences.get(LCSConstants.PATCHES_LCS_SERVICE_ENABLED) && Validator.equals(LCSUtil.getPortalEdition(), LCSConstants.PORTAL_EDITION_EE) %>" />

			<p>
				<liferay-ui:message key="by-enabling-fix-packs-management-you-will-be-able-to-see-if-there-are-updates-for-your-fix-packs-and-download-updates-to-your-portal" />
			</p>

			<aui:input label="portal-properties-analysis" name="<%= LCSConstants.PORTAL_PROPERTIES_LCS_SERVICE_ENABLED %>" type="checkbox" value="<%= lcsServicesPreferences.get(LCSConstants.PORTAL_PROPERTIES_LCS_SERVICE_ENABLED) %>" />

			<p class="properties-message">
				<liferay-ui:message arguments="portal-properties-security-sensitive" key="by-enabling-portal-properties-analysis-you-will-be-able-to-compare-your-portals-properties-to-default-properties-of-your-portal-version" />
			</p>

			<div class='properties-panel <%= lcsServicesPreferences.get(LCSConstants.PORTAL_PROPERTIES_LCS_SERVICE_ENABLED) %> ? "hide" : StringPool.BLANK %>' id="<portlet:namespace />propertiesPanel">
				<aui:input helpMessage="additional-blacklisted-properties-help" label="additional-blacklisted-properties" name="<%= LCSConstants.PORTAL_PROPERTIES_BLACKLIST %>" type="textarea" value="<%= LCSUtil.getPortalPropertiesBlacklist() %>" />
			</div>
		</div>
	</div>

	<aui:button-row>
		<c:choose>
			<c:when test="<%= LCSUtil.hasLCSServicesPreferences() && LCSUtil.isLCSPortletAuthorized(liferayPortletRequest) %>">
				<liferay-portlet:renderURL var="lcsPortletHomeURL" />

				<aui:button href="<%= lcsPortletHomeURL.toString() %>" name="back" value="back" />

				<aui:button cssClass="btn-success" name="nextPage" type="submit" value="save" />
			</c:when>
			<c:otherwise>
				<aui:button cssClass="btn-success" name="nextPage" type="submit" value="next" />
			</c:otherwise>
		</c:choose>
	</aui:button-row>
</aui:form>

<aui:script use="lcs">
	var lcsPortlet = new Liferay.Portlet.LCS(
		{
			namespace: '<portlet:namespace />'
		}
	);

	lcsPortlet.initializeConfigureLCSServicesPage(
		{
			metricsLCSServiceEnabled: '<%= LCSConstants.METRICS_LCS_SERVICE_ENABLED %>',
			patchesLCSServiceEnabled: '<%= LCSConstants.PATCHES_LCS_SERVICE_ENABLED %>',
			portalPropertiesLCSServiceEnabled: '<%= LCSConstants.PORTAL_PROPERTIES_LCS_SERVICE_ENABLED %>',
			portalPropertiesSecuritySensitive: '<%= StringUtil.merge(LCSConstants.PORTAL_PROPERTIES_SECURITY_SENSITIVE, "<br />") %>'
		}
	);
</aui:script>