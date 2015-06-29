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

<%@ include file="/html/portlet/export_import/init.jsp" %>

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
	<portlet:param name="struts_action" value="/export_import/view" />
</liferay-portlet:renderURL>

<liferay-ui:tabs
	names="public-pages,private-pages"
	param="tabs1"
	portletURL="<%= portletURL %>"
	type="pills"
/>

<aui:nav-bar>
	<aui:nav cssClass="navbar-nav">
		<liferay-portlet:renderURL var="exportPagesURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
			<portlet:param name="struts_action" value="/export_import/export_layouts" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.EXPORT %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(groupDisplayContextHelper.getGroupId()) %>" />
			<portlet:param name="liveGroupId" value="<%= String.valueOf(groupDisplayContextHelper.getLiveGroupId()) %>" />
			<portlet:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
			<portlet:param name="rootNodeName" value="<%= rootNodeName %>" />
			<portlet:param name="showHeader" value="<%= Boolean.FALSE.toString() %>" />
		</liferay-portlet:renderURL>

		<aui:nav-item href="<%= exportPagesURL %>" iconCssClass="icon-arrow-down" label="export" useDialog="<%= true %>" />

		<liferay-portlet:renderURL var="importPagesURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
			<portlet:param name="struts_action" value="/export_import/import_layouts" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.VALIDATE %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(groupDisplayContextHelper.getGroupId()) %>" />
			<portlet:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
			<portlet:param name="rootNodeName" value="<%= rootNodeName %>" />
			<portlet:param name="showHeader" value="<%= Boolean.FALSE.toString() %>" />
		</liferay-portlet:renderURL>

		<aui:nav-item href="<%= importPagesURL %>" iconCssClass="icon-arrow-up" label="import" useDialog="<%= true %>" />
	</aui:nav>
</aui:nav-bar>