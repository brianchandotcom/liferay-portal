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

<liferay-ui:error-header />

<liferay-ui:error exception="<%= NoSuchNodeException.class %>" message="please-enter-a-valid-page-title" />

<c:if test="<%= SessionErrors.contains(renderRequest, NoSuchPageException.class.getName()) %>">

	<%
	long nodeId = ParamUtil.getLong(request, "nodeId");

	if (nodeId == 0) {
		WikiNode node = (WikiNode)request.getAttribute(WikiWebKeys.WIKI_NODE);

		if (node != null) {
			nodeId = node.getNodeId();
		}
	}

	String title = ParamUtil.getString(request, "title");

	PortletURL searchURL = renderResponse.createRenderURL();

	searchURL.setParameter("mvcRenderCommandName", "/wiki/search");
	searchURL.setParameter("redirect", currentURL);
	searchURL.setParameter("nodeId", String.valueOf(nodeId));
	searchURL.setParameter("keywords", title);

	PortletURL editPageURL = renderResponse.createRenderURL();

	editPageURL.setParameter("mvcRenderCommandName", "/wiki/edit_page");
	editPageURL.setParameter("redirect", currentURL);
	editPageURL.setParameter("nodeId", String.valueOf(nodeId));
	editPageURL.setParameter("title", title);
	%>

	<div class="alert alert-info">
		<liferay-ui:message key="this-page-is-empty.-use-the-buttons-below-to-create-it-or-to-search-for-the-words-in-the-title" />
	</div>

	<div class="btn-toolbar">

		<%
		String taglibSearch = "location.href = '" + searchURL.toString() + "';";
		%>

		<aui:button onClick="<%= taglibSearch %>" value='<%= LanguageUtil.format(request, "search-for-x", HtmlUtil.escapeAttribute(title), false) %>' />

		<%
		String taglibEditPage = "location.href = '" + editPageURL.toString() + "';";
		%>

		<aui:button onClick="<%= taglibEditPage %>" value='<%= LanguageUtil.format(request, "create-page-x", HtmlUtil.escapeAttribute(title), false) %>' />
	</div>
</c:if>

<liferay-ui:error exception="<%= PageTitleException.class %>" message="please-enter-a-valid-page-title" />

<liferay-ui:error-principal />