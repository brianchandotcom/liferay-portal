<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String bootstrapRequire = (String)request.getAttribute("liferay-map:map:bootstrapRequire");
boolean geolocation = GetterUtil.getBoolean(request.getAttribute("liferay-map:map:geolocation"));
double latitude = (Double)request.getAttribute("liferay-map:map:latitude");
double longitude = (Double)request.getAttribute("liferay-map:map:longitude");
String name = (String)request.getAttribute("liferay-map:map:name");
String points = (String)request.getAttribute("liferay-map:map:points");

name = AUIUtil.getNamespace(liferayPortletRequest, liferayPortletResponse) + name;
%>

<liferay-util:html-top
	outputKey="js_maps_openstreet_skip_loading"
>
	<link crossorigin="anonymous" href="https://npmcdn.com/leaflet@1.7.1/dist/leaflet.css" integrity="sha384-VzLXTJGPSyTLX6d96AxgkKvE/LRb7ECGyTxuwtpjHnVWVZs2gp5RDjeM/tgBnVdM" rel="stylesheet" />

	<script crossorigin="anonymous" integrity="sha384-RFZC58YeKApoNsIbBxf4z6JJXmh+geBSgkCQXFyh+4tiFSJmJBt+2FbjxW7Ar16M" src="https://npmcdn.com/leaflet@1.7.1/dist/leaflet.js" type="text/javascript"></script>
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
						MapControls.ZOOM,
					],
				</c:otherwise>
			</c:choose>
		</c:if>

		<c:if test="<%= Validator.isNotNull(points) %>">
			data: <%= points %>,
		</c:if>

		geolocation: <%= geolocation %>,

		<c:if test="<%= (latitude != 0) && (longitude != 0) %>">
			position: {
				location: {
					lat: <%= latitude %>,
					lng: <%= longitude %>,
				},
			},
		</c:if>
	};

	var createMap = function () {
		var map = new MapOpenStreetMap.default(mapConfig);

		Liferay.MapBase.register(
			'<%= HtmlUtil.escapeJS(name) %>',
			map,
			'<%= portletDisplay.getId() %>'
		);
	};

	createMap();
</aui:script>