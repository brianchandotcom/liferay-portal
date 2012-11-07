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
boolean viewTrashAttachments = ParamUtil.getBoolean(request, "viewTrashAttachments");

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Object[] objArray = (Object[])row.getObject();

WikiNode node = (WikiNode)objArray[0];
WikiPage wikiPage = (WikiPage)objArray[1];
String fileName = (String)objArray[2];
%>

<liferay-ui:icon-menu>
	<c:choose>
		<c:when test="<%= viewTrashAttachments %>">
			<c:if test="<%= WikiNodePermission.contains(permissionChecker, node.getNodeId(), ActionKeys.ADD_ATTACHMENT) %>">
				<portlet:actionURL var="restoreEntryURL">
					<portlet:param name="struts_action" value="/wiki/edit_page_attachment" />
					<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.MOVE_FROM_TRASH %>" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="nodeId" value="<%= String.valueOf(node.getPrimaryKey()) %>" />
					<portlet:param name="title" value="<%= wikiPage.getTitle() %>" />
					<portlet:param name="fileName" value="<%= fileName %>" />
				</portlet:actionURL>

				<liferay-ui:icon
					image="undo"
					message="restore"
					url="<%= restoreEntryURL %>"
				/>
			</c:if>

			<c:if test="<%= WikiPagePermission.contains(permissionChecker, wikiPage.getNodeId(), wikiPage.getTitle(), ActionKeys.DELETE) %>">
				<portlet:actionURL var="deleteURL">
					<portlet:param name="struts_action" value="/wiki/edit_page_attachment" />
					<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="nodeId" value="<%= String.valueOf(node.getPrimaryKey()) %>" />
					<portlet:param name="title" value="<%= wikiPage.getTitle() %>" />
					<portlet:param name="fileName" value="<%= fileName %>" />
				</portlet:actionURL>

				<liferay-ui:icon-delete
					url="<%= deleteURL %>"
				/>
			</c:if>
		</c:when>
		<c:otherwise>
			<c:if test="<%= WikiPagePermission.contains(permissionChecker, wikiPage.getNodeId(), wikiPage.getTitle(), ActionKeys.DELETE) %>">
				<portlet:actionURL var="deleteURL">
					<portlet:param name="struts_action" value="/wiki/edit_page_attachment" />
					<portlet:param name="<%= Constants.CMD %>" value="<%= TrashUtil.isTrashEnabled(scopeGroupId) ? Constants.MOVE_TO_TRASH : Constants.DELETE %>" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="nodeId" value="<%= String.valueOf(node.getPrimaryKey()) %>" />
					<portlet:param name="title" value="<%= wikiPage.getTitle() %>" />
					<portlet:param name="fileName" value="<%= fileName %>" />
				</portlet:actionURL>

				<liferay-ui:icon-delete
					message='<%= TrashUtil.isTrashEnabled(scopeGroupId) ? "remove" : "delete" %>'
					trash="<%= TrashUtil.isTrashEnabled(scopeGroupId) %>"
					url="<%= deleteURL %>"
				/>
			</c:if>
		</c:otherwise>
	</c:choose>
</liferay-ui:icon-menu>