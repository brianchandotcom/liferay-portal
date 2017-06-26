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
String redirect = ParamUtil.getString(request, "redirect");

WikiNode node = (WikiNode)request.getAttribute(WikiWebKeys.WIKI_NODE);
WikiPage wikiPage = (WikiPage)request.getAttribute(WikiWebKeys.WIKI_PAGE);

String title = wikiPage.getTitle();
String newTitle = ParamUtil.get(request, "newTitle", StringPool.BLANK);
%>

<liferay-util:include page="/wiki/top_links.jsp" servletContext="<%= application %>" />

<liferay-ui:error exception="<%= DuplicatePageException.class %>" message="there-is-already-a-page-with-the-specified-title" />

<liferay-ui:error exception="<%= PageTitleException.class %>" message="please-enter-a-valid-title" />

<%@ include file="/wiki/page_name.jspf" %>

<portlet:actionURL name="/wiki/move_page" var="movePageURL" />

<liferay-ui:tabs
	names="rename,change-parent"
	refresh="<%= false %>"
>

	<%
	boolean pending = false;

	boolean hasWorkflowDefinitionLink = WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(themeDisplay.getCompanyId(), scopeGroupId, WikiPage.class.getName());

	if (hasWorkflowDefinitionLink) {
		WikiPage latestWikiPage = WikiPageServiceUtil.getPage(wikiPage.getNodeId(), wikiPage.getTitle(), null);

		pending = latestWikiPage.isPending();
	}
	%>

	<liferay-ui:section>
		<%@ include file="/wiki/rename_page.jspf" %>
	</liferay-ui:section>

	<liferay-ui:section>
		<%@ include file="/wiki/change_page_parent.jspf" %>
	</liferay-ui:section>
</liferay-ui:tabs>