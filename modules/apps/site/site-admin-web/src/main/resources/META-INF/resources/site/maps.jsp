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
String mapProviderKey = (String)request.getAttribute(MapProviderWebKeys.MAP_PROVIDER_KEY);
MapProviderTracker mapProviderTracker = (MapProviderTracker)request.getAttribute(MapProviderWebKeys.MAP_PROVIDER_TRACKER);
%>

<liferay-ui:error-marker key="errorSection" value="maps" />

<p><liferay-ui:message key="select-the-maps-api-provider-to-use-when-displaying-geolocalized-assets" /></p>

<%
for (MapProvider mapProvider : mapProviderTracker.getMapProviders()) {
%>

	<aui:input checked='<%= Validator.equals(mapProviderKey, mapProvider.getKey()) %>'
		helpMessage='<%= mapProvider.getHelpMessage() %>'
		id='<%= mapProvider.getKey() + "Enabled" %>'
		label='<%= mapProvider.getLabel() %>'
		name='TypeSettingsProperties--mapsAPIProvider--'
		type='radio'
		value='<%= mapProvider.getKey() %>'
	/>

	<div id="<portlet:namespace />mapsProvider<%= mapProvider.getKey() %>">

		<%
		mapProvider.includeConfiguration(request, new PipingServletResponse(pageContext));
		%>

	</div>

	<aui:script>
		Liferay.Util.toggleRadio('<portlet:namespace /><%= mapProvider.getKey() %>Enabled', '<portlet:namespace />mapsProvider<%= mapProvider.getKey() %>', '');
	</aui:script>

<%
}
%>