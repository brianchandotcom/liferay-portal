
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
String controls = GetterUtil.getString(request.getAttribute("liferay-ui:map:controls"));
boolean geolocation = GetterUtil.getBoolean(request.getAttribute("liferay-ui:map:geolocation"));
double latitude = (Double)request.getAttribute("liferay-ui:map:latitude");
double longitude = (Double)request.getAttribute("liferay-ui:map:longitude");
String name = GetterUtil.getString((String)request.getAttribute("liferay-ui:map:name"));
String points = GetterUtil.getString(request.getAttribute("liferay-ui:map:points"));
String provider = GetterUtil.getString((String)request.getAttribute("liferay-ui:map:provider"));
int zoom = (Integer)request.getAttribute("liferay-ui:map:zoom");

boolean showControls = false;

if (Validator.isNotNull(controls) || geolocation) {
	showControls = true;
}

if (Validator.isNull(provider)) {
	Group group = layout.getGroup();

	provider = group.getLiveParentTypeSettingsProperty("provider");

	if (Validator.isNull(provider)) {
		PortletPreferences companyPortletPreferences = PrefsPropsUtil.getPreferences(company.getCompanyId(), true);

		provider = companyPortletPreferences.getValue("provider", "Google");
	}
}

name = namespace + name;
%>

<c:if test='<%= provider.equals("Google") %>'>
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
</c:if>

<c:if test='<%= provider.equals("OpenStreet") %>'>
	<liferay-util:html-top outputKey="js_maps_openstreet_skip_loading">
		<link href="<%= protocol %>://cdn.leafletjs.com/leaflet-0.7.3/leaflet.css" rel="stylesheet" />
		<script src="<%= protocol %>://cdn.leafletjs.com/leaflet-0.7.3/leaflet.js"></script>
	</liferay-util:html-top>
</c:if>

<div class="lfr-map" id="<%= name %>Map"></div>

<aui:script use='<%= "liferay-map-" + StringUtil.toLowerCase(provider) %>'>
	var MapControls = Liferay.MapBase.CONTROLS;

	var mapConfig = {
		boundingBox: '#<%= name %>Map',

		<c:if test="<%= showControls %>">
			<c:choose>
				<c:when test="<%= Validator.isNotNull(controls) %>">
					controls: [<%= controls %>],
				</c:when>
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

		<c:if test="<%= zoom != 0 %>">
			,zoom: <%= zoom %>
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