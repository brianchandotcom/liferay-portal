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

String apiKey = GetterUtil.getString(request.getAttribute("liferay-ui:map:apiKey"));
boolean geolocation = GetterUtil.getBoolean(request.getAttribute("liferay-ui:map:geolocation"));
double latitude = (Double)request.getAttribute("liferay-ui:map:latitude");
double longitude = (Double)request.getAttribute("liferay-ui:map:longitude");
String name = GetterUtil.getString((String)request.getAttribute("liferay-ui:map:name"));
String points = GetterUtil.getString(request.getAttribute("liferay-ui:map:points"));
String provider = GetterUtil.getString((String)request.getAttribute("liferay-ui:map:provider"));

if (Validator.isNull(apiKey) || Validator.isNull(provider)) {
	Group group = themeDisplay.getSiteGroup();

	if (group.isStagingGroup()) {
		group = group.getLiveGroup();
	}

	UnicodeProperties groupTypeSettings = new UnicodeProperties();

	if (group != null) {
		groupTypeSettings = group.getTypeSettingsProperties();
	}

	PortletPreferences companyPortletPreferences = PrefsPropsUtil.getPreferences(company.getCompanyId());

	if (Validator.isNull(provider)) {
		provider = groupTypeSettings.getProperty("mapsAPIProvider", companyPortletPreferences.getValue("mapsAPIProvider", "Google"));
	}

	if (Validator.isNull(apiKey)) {
		apiKey = groupTypeSettings.getProperty("googleMapsAPIKey", companyPortletPreferences.getValue("googleMapsAPIKey", null));
	}
}

name = namespace + name;
%>

<c:choose>
	<c:when test='<%= provider.equals("Google") %>'>
		<liferay-util:html-bottom outputKey="js_maps_google_skip_map_loading">
			<script>
				Liferay.namespace('Maps').onGMapsReady = function(event) {
					Liferay.Maps.gmapsReady = true;

					Liferay.fire('gmapsReady');
				};
			</script>

			<%
				String apiURL = protocol + "://maps.googleapis.com/maps/api/js?v=3.exp&libraries=places&callback=Liferay.Maps.onGMapsReady";

				if (Validator.isNotNull(apiKey)) {
					apiURL += "&key=" + apiKey;
				}
			%>

			<script src="<%= apiURL %>" type="text/javascript"></script>
		</liferay-util:html-bottom>
	</c:when>
	<c:when test='<%= provider.equals("OpenStreet") %>'>
		<liferay-util:html-top outputKey="js_maps_openstreet_skip_loading">
			<link href="<%= protocol %>://cdn.leafletjs.com/leaflet-0.7.3/leaflet.css" rel="stylesheet" />
			<script src="<%= protocol %>://cdn.leafletjs.com/leaflet-0.7.3/leaflet.js"></script>
		</liferay-util:html-top>
	</c:when>
</c:choose>

<div class="lfr-map" id="<%= name %>Map"></div>

<aui:script use='<%= "liferay-map-" + StringUtil.toLowerCase(provider) %>'>
	var MapControls = Liferay.MapBase.CONTROLS;

	var mapConfig = {
		boundingBox: '#<%= name %>Map',

		<c:if test="<%= geolocation %>">
			<c:choose>
				<c:when test="<%= BrowserSnifferUtil.isMobile(request) %>">
					controls: [MapControls.HOME, MapControls.SEARCH],
				</c:when>
				<c:otherwise>
					controls: [MapControls.HOME, MapControls.PAN, MapControls.SEARCH, MapControls.TYPE, MapControls.ZOOM],
				</c:otherwise>
			</c:choose>
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

	var destroyMap = function(event, map) {
		if (event.portletId === '<%= portletDisplay.getRootPortletId() %>') {
			map.destroy();

			Liferay.detach('destroyPortlet', destroyMap);
		}
	};

	var createMap = function() {
		var map = new Liferay['<%= provider %>Map'](mapConfig).render();

		Liferay.MapBase.register('<%= name %>', map);

		Liferay.on('destroyPortlet', A.rbind(destroyMap, destroyMap, map));
	};

	<c:choose>
		<c:when test='<%= provider.equals("Google") %>'>
			if (Liferay.Maps.gmapsReady) {
				createMap();
			}
			else {
				Liferay.once('gmapsReady', createMap);
			}
		</c:when>
		<c:otherwise>
			createMap();
		</c:otherwise>
	</c:choose>
</aui:script>