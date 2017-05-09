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
boolean viewTrashAttachments = ParamUtil.getBoolean(request, "viewTrashAttachments");

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

FileEntry attachmentsFileEntry = (FileEntry)row.getObject();

WikiPage wikiPage = WikiPageAttachmentsUtil.getPage(attachmentsFileEntry.getFileEntryId());
%>

<liferay-ui:icon-menu direction="left-side" icon="<%= StringPool.BLANK %>" markupView="lexicon" message="<%= StringPool.BLANK %>">
	<c:choose>
		<c:when test="<%= viewTrashAttachments %>">
			<c:if test="<%= WikiNodePermissionChecker.contains(permissionChecker, wikiPage.getNodeId(), ActionKeys.ADD_ATTACHMENT) %>">
				<portlet:actionURL name="/wiki/edit_page_attachment" var="restoreEntryURL">
					<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESTORE %>" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="nodeId" value="<%= String.valueOf(wikiPage.getNodeId()) %>" />
					<portlet:param name="title" value="<%= wikiPage.getTitle() %>" />
					<portlet:param name="fileName" value="<%= attachmentsFileEntry.getTitle() %>" />
				</portlet:actionURL>

				<liferay-ui:icon
					message="restore"
					url="<%= restoreEntryURL %>"
				/>
			</c:if>

			<c:if test="<%= WikiPagePermissionChecker.contains(permissionChecker, wikiPage.getNodeId(), wikiPage.getTitle(), ActionKeys.DELETE) %>">
				<portlet:actionURL name="/wiki/edit_page_attachment" var="deleteURL">
					<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="nodeId" value="<%= String.valueOf(wikiPage.getNodeId()) %>" />
					<portlet:param name="title" value="<%= wikiPage.getTitle() %>" />
					<portlet:param name="fileName" value="<%= attachmentsFileEntry.getTitle() %>" />
				</portlet:actionURL>

				<liferay-ui:icon-delete
					url="<%= deleteURL %>"
				/>
			</c:if>
		</c:when>
		<c:otherwise>
			<c:if test="<%= WikiPagePermissionChecker.contains(permissionChecker, wikiPage.getNodeId(), wikiPage.getTitle(), ActionKeys.DELETE) %>">
				<portlet:actionURL name="/wiki/edit_page_attachment" var="deleteURL">
					<portlet:param name="<%= Constants.CMD %>" value="<%= trashHelper.isTrashEnabled(scopeGroupId) ? Constants.MOVE_TO_TRASH : Constants.DELETE %>" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="nodeId" value="<%= String.valueOf(wikiPage.getNodeId()) %>" />
					<portlet:param name="title" value="<%= wikiPage.getTitle() %>" />
					<portlet:param name="fileName" value="<%= attachmentsFileEntry.getTitle() %>" />
				</portlet:actionURL>

				<liferay-ui:icon-delete
					trash="<%= trashHelper.isTrashEnabled(scopeGroupId) %>"
					url="<%= deleteURL %>"
				/>
			</c:if>
		</c:otherwise>
	</c:choose>
</liferay-ui:icon-menu>