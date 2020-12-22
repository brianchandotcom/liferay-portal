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
String tabs1 = ParamUtil.getString(request, "tabs1", "public-pages");

GroupDisplayContextHelper groupDisplayContextHelper = new GroupDisplayContextHelper(request);

Group liveGroup = groupDisplayContextHelper.getLiveGroup();

boolean privateLayout = false;

if (tabs1.equals("private-pages")) {
	privateLayout = true;
}

String rootNodeName = liveGroup.getLayoutRootNodeName(privateLayout, themeDisplay.getLocale());
%>

<liferay-portlet:renderURL varImpl="portletURL">
	<portlet:param name="mvcPath" value="/view.jsp" />
</liferay-portlet:renderURL>

<clay:container-fluid>
	<liferay-ui:tabs
		names="public-pages,private-pages"
		param="tabs1"
		portletURL="<%= portletURL %>"
	/>

	<aui:nav-bar cssClass="navbar-collapse-absolute navbar-expand-md navbar-underline navigation-bar navigation-bar-light" markupView="lexicon">
		<aui:nav cssClass="navbar-nav">
			<liferay-portlet:renderURL var="exportPagesURL">
				<portlet:param name="mvcRenderCommandName" value="/export_import/export_layouts" />
				<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.EXPORT %>" />
				<portlet:param name="backURL" value="<%= currentURL %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(groupDisplayContextHelper.getGroupId()) %>" />
				<portlet:param name="liveGroupId" value="<%= String.valueOf(groupDisplayContextHelper.getLiveGroupId()) %>" />
				<portlet:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
				<portlet:param name="rootNodeName" value="<%= rootNodeName %>" />
			</liferay-portlet:renderURL>

			<aui:nav-item href="<%= exportPagesURL %>" label="export" />

			<liferay-portlet:renderURL var="importPagesURL">
				<portlet:param name="mvcRenderCommandName" value="/export_import/import_layouts" />
				<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.VALIDATE %>" />
				<portlet:param name="backURL" value="<%= currentURL %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(groupDisplayContextHelper.getGroupId()) %>" />
				<portlet:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
				<portlet:param name="rootNodeName" value="<%= rootNodeName %>" />
			</liferay-portlet:renderURL>

			<aui:nav-item href="<%= importPagesURL %>" label="import" />
		</aui:nav>
	</aui:nav-bar>
</clay:container-fluid>