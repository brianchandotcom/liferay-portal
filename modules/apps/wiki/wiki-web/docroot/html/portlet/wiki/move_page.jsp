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

<%@ include file="/html/portlet/wiki/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

WikiNode node = (WikiNode)request.getAttribute(WikiWebKeys.WIKI_NODE);
WikiPage wikiPage = (WikiPage)request.getAttribute(WikiWebKeys.WIKI_PAGE);

String title = wikiPage.getTitle();
String newTitle = ParamUtil.get(request, "newTitle", StringPool.BLANK);
%>

<liferay-util:include page="/html/portlet/wiki/top_links.jsp" />

<liferay-ui:error exception="<%= DuplicatePageException.class %>" message="there-is-already-a-page-with-the-specified-title" />

<liferay-ui:error exception="<%= NodeChangeException.class %>">

	<%
	NodeChangeException nce = (NodeChangeException)errorException;
	%>

	<c:choose>
		<c:when test="<%= nce.getType() == NodeChangeException.DUPLICATE_PAGE %>">
			<liferay-ui:message arguments="<%= new String[] {nce.getNodeName(), nce.getPageTitle()} %>" key="x-already-has-a-page-titled-x" translateArguments="<%= false %>" />
		</c:when>
		<c:when test="<%= nce.getType() == NodeChangeException.REDIRECT_PAGE %>">
			<liferay-ui:message arguments="<%= new String[] {nce.getPageTitle()} %>" key="x-is-a-redirection-page.-it-must-be-placed-in-the-same-node-as-its-redirect-page" translateArguments="<%= false %>" />
		</c:when>
	</c:choose>
</liferay-ui:error>

<liferay-ui:error exception="<%= PageTitleException.class %>" message="please-enter-a-valid-title" />

<%@ include file="/html/portlet/wiki/page_name.jspf" %>

<portlet:actionURL var="movePageURL">
	<portlet:param name="struts_action" value="/wiki/move_page" />
</portlet:actionURL>

<aui:form action="<%= movePageURL %>" method="post" name="fm" onSubmit='<%= renderResponse.getNamespace() + "changeParent();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="nodeId" type="hidden" value="<%= node.getNodeId() %>" />
	<aui:input name="title" type="hidden" value="<%= title %>" />
	<aui:input name="workflowAction" type="hidden" value="<%= WorkflowConstants.ACTION_PUBLISH %>" />

	<liferay-ui:tabs
		names="rename,change-parent,change-node"
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
			<%@ include file="/html/portlet/wiki/rename_page.jspf" %>
		</liferay-ui:section>
		<liferay-ui:section>
			<%@ include file="/html/portlet/wiki/change_page_parent.jspf" %>
		</liferay-ui:section>
		<liferay-ui:section>
			<%@ include file="/html/portlet/wiki/change_page_node.jspf" %>
		</liferay-ui:section>
	</liferay-ui:tabs>
</aui:form>