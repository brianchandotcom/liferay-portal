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
TrashHandler trashHandler = trashDisplayContext.getTrashHandler();
%>

<portlet:renderURL var="restoreTrashURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcPath" value="/view_container_model.jsp" />
	<portlet:param name="redirect" value="<%= trashDisplayContext.getViewContentRedirectURL() %>" />
	<portlet:param name="classNameId" value="<%= String.valueOf(trashDisplayContext.getClassNameId()) %>" />
	<portlet:param name="classPK" value="<%= String.valueOf(trashDisplayContext.getClassPK()) %>" />
	<portlet:param name="containerModelClassNameId" value="<%= String.valueOf(PortalUtil.getClassNameId(trashHandler.getContainerModelClassName(trashDisplayContext.getClassPK()))) %>" />
</portlet:renderURL>

<liferay-util:buffer
	var="onClickFn"
>
	<portlet:namespace />restoreDialog('<%= restoreTrashURL %>');
</liferay-util:buffer>

<liferay-ui:icon
	iconCssClass="restore"
	message="restore"
	onClick="<%= onClickFn %>"
	url="javascript:void(0);"
/>