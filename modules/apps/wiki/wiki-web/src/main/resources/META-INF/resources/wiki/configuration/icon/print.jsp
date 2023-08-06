<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"printPageURL", printURL
		).build()
	%>'
	module="wiki/js/printPageButtonEventListener"
/>