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
WikiNode wikiNode = ActionUtil.getNode(liferayPortletRequest);

WikiPage wikiPage = ActionUtil.getPage(liferayPortletRequest);
%>

<portlet:renderURL var="printURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcRenderCommandName" value="/wiki/view" />
	<portlet:param name="nodeName" value="<%= wikiNode.getName() %>" />
	<portlet:param name="title" value="<%= wikiPage.getTitle() %>" />
	<portlet:param name="viewMode" value="<%= Constants.PRINT %>" />
</portlet:renderURL>

<liferay-util:buffer
	var="onClickFn"
>
	window.open('<%= printURL %>', '', 'directories=0,height=480,left=80,location=1, menubar=1,resizable=1,scrollbars=yes,status=0, toolbar=0,top=180,width=640');
</liferay-util:buffer>

<liferay-ui:icon
	message="print"
	onClick="<%= onClickFn %>"
	url="javascript:void(0);"
/>