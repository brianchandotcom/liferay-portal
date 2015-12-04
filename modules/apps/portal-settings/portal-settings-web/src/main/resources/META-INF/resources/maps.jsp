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
String groupMapsAPIProvider = (String)request.getAttribute(MapProviderWebKeys.GROUP_MAPS_API_PROVIDER);

MapProviderTracker mapProviderTracker = (MapProviderTracker)request.getAttribute(MapProviderWebKeys.MAP_PROVIDER_TRACKER);

Set<String> mapProviders = mapProviderTracker.getMapProviders();
%>

<liferay-ui:error-marker key="errorSection" value="maps" />

<h3><liferay-ui:message key="maps" /></h3>

<p><liferay-ui:message key="select-the-maps-api-provider-to-use-when-displaying-geolocalized-assets" /></p>

<%
	for (String provider : mapProviders) {
		MapProvider mapProvider = mapProviderTracker.getMapProvider(provider);
%>

<aui:input checked='<%= groupMapsAPIProvider.equals(mapProvider.getKey()) %>'
helpMessage='<%= mapProvider.getHelpMessage() %>'
id='<%= mapProvider.getKey() + "Enabled" %>'
label='<%= mapProvider.getLabel() %>'
name='TypeSettingsProperties--mapsAPIProvider--'
type='radio'
value='<%= mapProvider.getKey() %>'
/>

<div id="<portlet:namespace />mapsProvider<%= mapProvider.getKey() %>">
	<c:if test="<%= !mapProvider.includeConfiguration(request, new PipingServletResponse(pageContext)) %>" />
</div>

<aui:script>
	Liferay.Util.toggleRadio('<portlet:namespace /><%= mapProvider.getKey() %>Enabled', '<portlet:namespace />mapsProvider<%= mapProvider.getKey() %>', '');
</aui:script>

<%
	}
%>