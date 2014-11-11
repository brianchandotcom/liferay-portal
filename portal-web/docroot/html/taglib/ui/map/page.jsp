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

<%@ include file="/html/taglib/init.jsp" %>

<%
String protocol = HttpUtil.getProtocol(request);

String points = GetterUtil.getString(request.getAttribute("liferay-ui:map:points"));
boolean geolocation = GetterUtil.getBoolean(request.getAttribute("liferay-ui:map:geolocation"));
double latitude = (Double)request.getAttribute("liferay-ui:map:latitude");
double longitude = (Double)request.getAttribute("liferay-ui:map:longitude");
String name = GetterUtil.getString((String)request.getAttribute("liferay-ui:map:name"));
String mapsAPIProvider = GetterUtil.getString((String)request.getAttribute("liferay-ui:map:provider"));

if (Validator.isNull(mapsAPIProvider)) {
	Group group = layout.getGroup();

	mapsAPIProvider = group.getLiveParentTypeSettingsProperty("mapsAPIProvider");

	if (Validator.isNull(mapsAPIProvider)) {
		PortletPreferences companyPortletPreferences = PrefsPropsUtil.getPreferences(company.getCompanyId(), true);

		mapsAPIProvider = companyPortletPreferences.getValue("mapsAPIProvider", "Google");
	}
}

name = namespace + name;

String modules = "liferay-map-" + StringUtil.toLowerCase(mapsAPIProvider);
%>

<c:if test='<%= mapsAPIProvider.equals("Google") %>'>
	<liferay-util:html-top outputKey="js_maps_google_skip_map_loading">
		<script src="<%= protocol %>://maps.googleapis.com/maps/api/js?v=3.exp&libraries=places" type="text/javascript"></script>
	</liferay-util:html-top>
</c:if>

<c:if test='<%= mapsAPIProvider.equals("OpenStreet") %>'>
	<liferay-util:html-top outputKey="js_maps_openstreet_skip_loading">
		<link href="<%= protocol %>://cdn.leafletjs.com/leaflet-0.7.3/leaflet.css" rel="stylesheet" />
		<script src="<%= protocol %>://cdn.leafletjs.com/leaflet-0.7.3/leaflet.js"></script>
	</liferay-util:html-top>
</c:if>

<div class="lfr-map" id="<%= name %>Map"></div>

<aui:script use="<%= modules %>">
	var MapControls = Liferay.MapBase.CONTROLS;

	var mapConfig = {
		boundingBox: '#<%= name %>Map',

		<c:if test="<%= geolocation %>">
			controls: [MapControls.GEOLOCATION, MapControls.HOME, MapControls.PAN, MapControls.SEARCH, MapControls.TYPE, MapControls.ZOOM],
		</c:if>

		<c:if test="<%= Validator.isNotNull(points) %>">
			data: <%= points %>,
		</c:if>

		geolocation: <%= geolocation %>

		<c:if test="<%= Validator.isNotNull(latitude) && Validator.isNotNull(longitude) %>">
			,position: {
				location: {
					lat: <%= latitude %>,
					lng: <%= longitude %>
				}
			}
		</c:if>
	};

	var map = new Liferay['<%= mapsAPIProvider %>Map'](mapConfig).render();

	Liferay.component('<%= name %>', map);
</aui:script>