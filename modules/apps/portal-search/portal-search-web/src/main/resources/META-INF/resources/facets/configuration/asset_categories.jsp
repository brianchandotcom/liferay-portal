<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
SearchFacet searchFacet = (SearchFacet)request.getAttribute("facet_configuration.jsp-searchFacet");

JSONObject dataJSONObject = searchFacet.getData();

String displayStyle = dataJSONObject.getString("displayStyle", "cloud");
%>

<aui:select label="display-style" name='<%= searchFacet.getClassName() + "displayStyleFacet" %>'>
	<aui:option label="cloud" selected='<%= displayStyle.equals("cloud") %>' />
	<aui:option label="list" selected='<%= displayStyle.equals("list") %>' />
</aui:select>

<aui:input label="frequency-threshold" name='<%= searchFacet.getClassName() + "frequencyThreshold" %>' value='<%= dataJSONObject.getInt("frequencyThreshold") %>' />

<aui:input label="max-terms" name='<%= searchFacet.getClassName() + "maxTerms" %>' value='<%= dataJSONObject.getInt("maxTerms", 10) %>' />

<aui:input label="show-asset-count" name='<%= searchFacet.getClassName() + "showAssetCount" %>' type="checkbox" value='<%= dataJSONObject.getBoolean("showAssetCount", true) %>' />