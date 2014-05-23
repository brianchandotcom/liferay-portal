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
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

WikiPage wikiPage = (WikiPage)row.getObject();
%>

<liferay-ui:icon-menu>
	<c:if test="<%= (wikiPage.getStatus() == WorkflowConstants.STATUS_APPROVED) && WikiPagePermission.contains(permissionChecker, wikiPage, ActionKeys.UPDATE) %>">
		<portlet:actionURL var="revertURL">
			<portlet:param name="struts_action" value="/wiki/edit_page" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.REVERT %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="nodeId" value="<%= String.valueOf(wikiPage.getNodeId()) %>" />
			<portlet:param name="title" value="<%= HtmlUtil.unescape(wikiPage.getTitle()) %>" />
			<portlet:param name="version" value="<%= String.valueOf(wikiPage.getVersion()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon
			iconCssClass="icon-undo"
			message="revert"
			url="<%= revertURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>