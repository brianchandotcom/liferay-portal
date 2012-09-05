<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

WikiNode node = (WikiNode)request.getAttribute(WebKeys.WIKI_NODE);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/trash/view_content");
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("className", WikiNode.class.getName());
portletURL.setParameter("classPK", String.valueOf(node.getNodeId()));
%>

<liferay-ui:search-container
	curParam="cur1"
	iteratorURL="<%= portletURL %>"
>

	<liferay-ui:search-container-results
		results="<%= WikiPageLocalServiceUtil.getPages(node.getNodeId(), true, WorkflowConstants.STATUS_ANY, searchContainer.getStart(), searchContainer.getEnd(), searchContainer.getOrderByComparator()) %>"
		total="<%= WikiPageLocalServiceUtil.getPagesCount(node.getNodeId(), true, WorkflowConstants.STATUS_ANY) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portlet.wiki.model.WikiPage"
		escapedModel="<%= true %>"
		keyProperty="pageId"
		modelVar="curWikiPage"
		rowVar="row"
	>

		<liferay-portlet:renderURL varImpl="rowURL">
			<portlet:param name="struts_action" value="/trash/view_content" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="className" value="<%= WikiPage.class.getName() %>" />
			<portlet:param name="classPK" value="<%= String.valueOf(curWikiPage.getResourcePrimKey()) %>" />
		</liferay-portlet:renderURL>

		<liferay-ui:search-container-column-text
			href="<%= rowURL %>"
			name="page"
			value="<%= curWikiPage.getTitle() %>"
		/>

		<%
		String revision = String.valueOf(curWikiPage.getVersion());

		if (curWikiPage.isMinorEdit()) {
			revision += " (" + LanguageUtil.get(pageContext, "minor-edit") + ")";
		}
		%>

		<liferay-ui:search-container-column-text
			href="<%= rowURL %>"
			name="revision"
			value="<%= revision %>"
		/>

		<liferay-ui:search-container-column-text
			href="<%= rowURL %>"
			name="user"
			value="<%= HtmlUtil.escape(PortalUtil.getUserName(curWikiPage.getUserId(), curWikiPage.getUserName())) %>"
		/>

		<liferay-ui:search-container-column-text
			href="<%= rowURL %>"
			name="date"
			value="<%= dateFormatDateTime.format(curWikiPage.getCreateDate()) %>"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>