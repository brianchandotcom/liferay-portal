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
boolean friendsProfileMap = false;
boolean organizationProfileMap = false;
boolean siteProfileMap = false;
boolean userProfileMap = false;

if ((user2 != null) && layout.getFriendlyURL().equals("/friends")) {
	friendsProfileMap = true;
}
else if (organization != null) {
	organizationProfileMap = true;
}
else if (user2 != null) {
	userProfileMap = true;
}
else {
	siteProfileMap = true;
}

if (organizationProfileMap || siteProfileMap) {
	renderResponse.setTitle(LanguageUtil.format(request, "where-are-the-x-members", group.getDescriptiveName(locale), false));
}
else if (friendsProfileMap) {
	renderResponse.setTitle(LanguageUtil.format(request, "where-are-x's-friends", user2.getFirstName(), false));
}
else {
	renderResponse.setTitle(LanguageUtil.format(request, "where-is-x", user2.getFirstName(), false));
}
%>

<%
IPGeocoder ipGeocoder = (IPGeocoder)request.getAttribute(SocialNetworkingWebKeys.IP_GEOCODER);
%>

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

List<User> users = null;

if (siteProfileMap) {
	LinkedHashMap<String, Object> userParams = new LinkedHashMap<String, Object>();

	userParams.put("usersGroups", new Long(group.getGroupId()));

	users = UserLocalServiceUtil.search(company.getCompanyId(), null, WorkflowConstants.STATUS_APPROVED, userParams, 0, 50, new UserLoginDateComparator());
}
else if (friendsProfileMap) {
	users = UserLocalServiceUtil.getSocialUsers(user2.getUserId(), SocialRelationConstants.TYPE_BI_FRIEND, 0, 50, new UserLoginDateComparator());
}
else if (organizationProfileMap) {
	LinkedHashMap<String, Object> userParams = new LinkedHashMap<String, Object>();

	userParams.put("usersOrgs", new Long(organization.getOrganizationId()));

	users = UserLocalServiceUtil.search(company.getCompanyId(), null, WorkflowConstants.STATUS_APPROVED, userParams, 0, 50, new UserLoginDateComparator());
}
else if (userProfileMap) {
	users = new ArrayList<User>();

	users.add(user2);
}

JSONObject featureCollectionJSONObject = JSONFactoryUtil.createJSONObject();

featureCollectionJSONObject.put("type", "FeatureCollection");

JSONArray featureJSONArray = JSONFactoryUtil.createJSONArray();

boolean hasPoints = false;

for (int i = 0; i < users.size(); i++) {
	User mapUser = users.get(i);

	if (Validator.isNull(mapUser.getLastLoginIP())) {
		continue;
	}

	IPInfo ipInfo = ipGeocoder.getIPInfo(mapUser.getLastLoginIP());

	double latitude = ipInfo.getLatitude();
	double longitude = ipInfo.getLongitude();

	if (ipInfo == null) {
		continue;
	}

	hasPoints = true;

	JSONObject featureJSONObject = JSONFactoryUtil.createJSONObject();
	featureJSONObject.put("type", "Feature");

	JSONObject geometryJSONObject = JSONFactoryUtil.createJSONObject();

	geometryJSONObject.put("type", "Point");

	JSONArray coordinatesJSONArray = JSONFactoryUtil.createJSONArray();

	coordinatesJSONArray.put(longitude);
	coordinatesJSONArray.put(latitude);

	geometryJSONObject.put("coordinates", coordinatesJSONArray);

	featureJSONObject.put("geometry", geometryJSONObject);

	JSONObject propertiesJSONObject = JSONFactoryUtil.createJSONObject();

	propertiesJSONObject.put("title", mapUser.getFullName());

	featureJSONObject.put("properties", propertiesJSONObject);

	featureJSONArray.put(featureJSONObject);
}

featureCollectionJSONObject.put("features", featureJSONArray);

double latitude = 0.0;
double longitude = 0.0;

if (userProfileMap) {
	IPInfo ipInfo = ipGeocoder.getIPInfo(user2.getLastLoginIP());

	if (ipInfo != null) {
		latitude = ipInfo.getLatitude();
		longitude = ipInfo.getLongitude();
	}
}

boolean maximized = windowState.equals(WindowState.MAXIMIZED);

int zoom = 0;

if (maximized) {
	zoom = 2;

	if (userProfileMap) {
		zoom = 5;
	}
}
%>

<div class="<%= maximized ? "maximized-map" : "default-map"%>">
	<c:choose>
		<c:when test="<%= hasPoints %>">
			<liferay-ui:map apiKey="<%= groupGoogleMapsAPIKey %>" controls="MapControls.TYPE, MapControls.ZOOM" latitude="<%= latitude %>" longitude="<%= longitude %>" name="map" points= "<%= featureCollectionJSONObject.toString() %>" provider="<%= groupMapsAPIProvider %>" zoom="<%= zoom %>" />
		</c:when>
		<c:otherwise>
			<liferay-ui:map apiKey="<%= groupGoogleMapsAPIKey %>" controls="MapControls.TYPE, MapControls.ZOOM" latitude="<%= latitude %>" longitude="<%= longitude %>" name="map" provider="<%= groupMapsAPIProvider %>" zoom="<%= zoom %>" />
		</c:otherwise>
	</c:choose>
</div>

<c:if test="<%= !maximized %>">
	<div style="padding-top: 5px;">
		<a href="<%= PortalUtil.getLayoutURL(layout, themeDisplay) %>/-/map"><liferay-ui:message key="view-larger-map" /> &raquo;</a>
	</div>
</c:if>