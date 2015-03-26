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
SearchFacet searchFacet = (SearchFacet)request.getAttribute("facet_configuration.jsp-searchFacet");

JSONObject dataJSONObject = searchFacet.getData();

String displayStyle = dataJSONObject.getString("displayStyle", "cloud");
int frequencyThreshold = dataJSONObject.getInt("frequencyThreshold");
int maxTerms = dataJSONObject.getInt("maxTerms", 10);
boolean showAssetCount = dataJSONObject.getBoolean("showAssetCount", true);
%>

<aui:select label="display-style" name='<%= searchFacet.getClassName() + "displayStyleFacet" %>'>
	<aui:option label="cloud" selected='<%= displayStyle.equals("cloud") %>' />
	<aui:option label="list" selected='<%= displayStyle.equals("list") %>' />
</aui:select>

<aui:input label="frequency-threshold" name='<%= searchFacet.getClassName() + "frequencyThreshold" %>' value="<%= frequencyThreshold %>" />

<aui:input label="max-terms" name='<%= searchFacet.getClassName() + "maxTerms" %>' value="<%= maxTerms %>" />

<aui:input label="show-asset-count" name='<%= searchFacet.getClassName() + "showAssetCount" %>' type="checkbox" value="<%= showAssetCount %>" />