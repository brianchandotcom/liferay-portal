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

<portlet:renderURL var="addURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
	<portlet:param name="mvcPath" value="/add_panel.jsp" />
	<portlet:param name="stateMaximized" value="<%= String.valueOf(themeDisplay.isStateMaximized()) %>" />
	<portlet:param name="viewAssetEntries" value="<%= Boolean.TRUE.toString() %>" />
</portlet:renderURL>

<%
Map<String, Object> data = new HashMap<String, Object>();

data.put("panelURL", addURL);
%>

<li>
	<aui:icon
		cssClass="control-menu-icon"
		data="<%= data %>"
		id="addPanel"
		image="plus"
		label="add"
		markupView="lexicon"
		url="javascript:;"
	/>
</li>