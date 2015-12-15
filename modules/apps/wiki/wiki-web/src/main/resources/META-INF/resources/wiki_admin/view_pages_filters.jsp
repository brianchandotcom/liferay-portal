
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

<%@ include file="/wiki/init.jsp" %>

<%
WikiNode node = (WikiNode)request.getAttribute(WikiWebKeys.WIKI_NODE);

String navigation = ParamUtil.getString(request, "navigation", "all-pages");

String orderByCol = GetterUtil.getString((String)request.getAttribute("view_pages.jsp-orderByCol"));
String orderByType = GetterUtil.getString((String)request.getAttribute("view_pages.jsp-orderByType"));

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "/wiki_admin/view_pages");
portletURL.setParameter("redirect", currentURL);
portletURL.setParameter("nodeId", String.valueOf(node.getNodeId()));

Map<String, String> orderColumns = new HashMap<String, String>();

orderColumns.put("modifiedDate", "modified-date");
orderColumns.put("title", "title");
%>

<liferay-frontend:management-bar-navigation
	navigationKeys='<%= new String[] {"all-pages", "draft-pages", "frontpage", "orphan-pages", "recent-changes"} %>'
	portletURL="<%= portletURL %>"
/>

<c:if test='<%= navigation.equals("all-pages") %>'>
	<liferay-frontend:management-bar-sort
		orderByCol="<%= orderByCol %>"
		orderByType="<%= orderByType %>"
		orderColumns="<%= orderColumns %>"
		portletURL="<%= portletURL %>"
	/>
</c:if>