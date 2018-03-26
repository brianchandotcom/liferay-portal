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
String mvcRenderCommandName = ParamUtil.getString(request, "mvcRenderCommandName");

long groupId = ParamUtil.getLong(request, "groupId");
boolean privateLayout = ParamUtil.getBoolean(request, "privateLayout");
String displayStyle = ParamUtil.getString(request, "displayStyle", "descriptive");
String orderByCol = ParamUtil.getString(request, "orderByCol");
String orderByType = ParamUtil.getString(request, "orderByType");
String navigation = ParamUtil.getString(request, "navigation", "all");
String searchContainerId = ParamUtil.getString(request, "searchContainerId");

PortletURL portletURL = liferayPortletResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", mvcRenderCommandName);
portletURL.setParameter("groupId", String.valueOf(groupId));
portletURL.setParameter("privateLayout", String.valueOf(privateLayout));
portletURL.setParameter("displayStyle", displayStyle);
portletURL.setParameter("navigation", navigation);
portletURL.setParameter("orderByCol", orderByCol);
portletURL.setParameter("orderByType", orderByType);
portletURL.setParameter("searchContainerId", String.valueOf(searchContainerId));
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="<%= searchContainerId %>"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all", "completed", "in-progress"} %>'
			navigationParam="navigation"
			portletURL="<%= PortletURLUtil.clone(portletURL, liferayPortletResponse) %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= orderByCol %>"
			orderByType="<%= orderByType %>"
			orderColumns='<%= new String[] {"create-date", "completion-date", "name"} %>'
			portletURL="<%= PortletURLUtil.clone(portletURL, liferayPortletResponse) %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"descriptive", "list"} %>'
			portletURL="<%= PortletURLUtil.clone(portletURL, liferayPortletResponse) %>"
			selectedDisplayStyle="<%= displayStyle %>"
		/>

		<%
		GroupDisplayContextHelper groupDisplayContextHelper = new GroupDisplayContextHelper(request);
		%>

		<c:choose>
			<c:when test='<%= mvcRenderCommandName.equals("importLayoutsView") %>'>
				<liferay-util:include page="/import/add_button.jsp" servletContext="<%= application %>">
					<liferay-util:param name="groupId" value="<%= String.valueOf(groupDisplayContextHelper.getGroupId()) %>" />
					<liferay-util:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
				</liferay-util:include>
			</c:when>
			<c:when test='<%= mvcRenderCommandName.equals("exportLayoutsView") %>'>
				<liferay-util:include page="/export/add_button.jsp" servletContext="<%= application %>">
					<liferay-util:param name="groupId" value="<%= String.valueOf(groupDisplayContextHelper.getGroupId()) %>" />
					<liferay-util:param name="liveGroupId" value="<%= String.valueOf(groupDisplayContextHelper.getLiveGroupId()) %>" />
					<liferay-util:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
					<liferay-util:param name="displayStyle" value="<%= displayStyle %>" />
				</liferay-util:include>
			</c:when>
		</c:choose>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button
			href='<%= "javascript:" + liferayPortletResponse.getNamespace() + "deleteEntries();" %>'
			icon="times"
			label="delete"
		/>
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>