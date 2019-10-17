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
String namespace = AUIUtil.getNamespace(liferayPortletRequest, liferayPortletResponse);

String bootstrapRequire = (String)request.getAttribute("liferay-map:map:bootstrapRequire");
boolean geolocation = GetterUtil.getBoolean(request.getAttribute("liferay-map:map:geolocation"));
double latitude = (Double)request.getAttribute("liferay-map:map:latitude");
double longitude = (Double)request.getAttribute("liferay-map:map:longitude");
String name = (String)request.getAttribute("liferay-map:map:name");
String points =(String)request.getAttribute("liferay-map:map:points");

name = namespace + name;
%>

<liferay-util:html-top
	outputKey="js_maps_openstreet_skip_loading"
>
	<link href="https://npmcdn.com/leaflet@1.2.0/dist/leaflet.css" rel="stylesheet" />

	<script src="https://npmcdn.com/leaflet@1.2.0/dist/leaflet.js" type="text/javascript"></script>
</liferay-util:html-top>

<aui:script require="<%= bootstrapRequire %>">
	var MapControls = Liferay.MapBase.CONTROLS;

	var mapConfig = {
		boundingBox: '#<%= HtmlUtil.escapeJS(name) %>Map',

		<c:if test="<%= geolocation %>">
			<c:choose>
				<c:when test="<%= BrowserSnifferUtil.isMobile(request) %>">
					controls: [MapControls.HOME, MapControls.SEARCH],
				</c:when>
				<c:otherwise>
					controls: [
						MapControls.HOME,
						MapControls.PAN,
						MapControls.SEARCH,
						MapControls.TYPE,
						MapControls.ZOOM
					],
				</c:otherwise>
			</c:choose>
		</c:if>

		<c:if test="<%= Validator.isNotNull(points) %>">
			data: <%= points %>,
		</c:if>

		geolocation: <%= geolocation %>,

		<c:if test="<%= Validator.isNotNull(latitude) && Validator.isNotNull(longitude) %>">
			position: {
				location: {
					lat: <%= latitude %>,
					lng: <%= longitude %>
				}
			}
		</c:if>
	};

	var createMap = function() {
		var map = new MapOpenStreetMap.default(mapConfig);

		Liferay.MapBase.register(
			'<%= HtmlUtil.escapeJS(name) %>',
			map,
			'<%= portletDisplay.getId() %>'
		);
	};

	createMap();
</aui:script>