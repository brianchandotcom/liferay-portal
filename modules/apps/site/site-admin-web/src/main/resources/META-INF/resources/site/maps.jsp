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
PortletPreferences companyPortletPreferences = PrefsPropsUtil.getPreferences(company.getCompanyId());

String companyMapsAPIProvider = PrefsParamUtil.getString(companyPortletPreferences, request, "mapsAPIProvider", "Google");
String companyGoogleMapsAPIKey = PrefsParamUtil.getString(companyPortletPreferences, request, "googleMapsAPIKey", "");

Group liveGroup = (Group)request.getAttribute("site.liveGroup");

UnicodeProperties groupTypeSettings = null;

if (liveGroup != null) {
	groupTypeSettings = liveGroup.getTypeSettingsProperties();
}
else {
	groupTypeSettings = new UnicodeProperties();
}

String groupMapsAPIProvider = PropertiesParamUtil.getString(groupTypeSettings, request, "mapsAPIProvider", companyMapsAPIProvider);
String groupGoogleMapsAPIKey = PropertiesParamUtil.getString(groupTypeSettings, request, "googleMapsAPIKey", companyGoogleMapsAPIKey);
%>

<liferay-ui:error-marker key="errorSection" value="maps" />

<h3><liferay-ui:message key="maps" /></h3>

<p><liferay-ui:message key="select-the-maps-api-provider-to-use-when-displaying-geolocalized-assets" /></p>

<aui:input checked='<%= groupMapsAPIProvider.equals("Google") %>' helpMessage="use-google-maps-as-the-maps-api-provider" id="mapsGoogleMapsEnabled" label="google-maps" name="TypeSettingsProperties--mapsAPIProvider--" type="radio" value="Google" />

<div class="maps-google-maps-api-key" id="<portlet:namespace />googleMapsAPIKey">
	<aui:input helpMessage="set-the-google-maps-api-key-that-is-used-for-this-set-of-pages" label='<%= LanguageUtil.get(request, "google-maps-api-key") + " (" + LanguageUtil.get(request, "optional") + ")" %>' name="TypeSettingsProperties--googleMapsAPIKey--" size="40" type="text" value="<%= groupGoogleMapsAPIKey %>" />
</div>

<aui:input checked='<%= groupMapsAPIProvider.equals("OpenStreet") %>' helpMessage="use-openstreetmap-as-the-maps-api-provider" id="mapsOpenStreetMapEnabled" label="openstreetmap" name="TypeSettingsProperties--mapsAPIProvider--" type="radio" value="OpenStreet" />

<aui:script>
	Liferay.Util.toggleRadio('<portlet:namespace />mapsGoogleMapsEnabled', '<portlet:namespace />googleMapsAPIKey', '');
	Liferay.Util.toggleRadio('<portlet:namespace />mapsOpenStreetMapEnabled', '', '<portlet:namespace />googleMapsAPIKey');
</aui:script>