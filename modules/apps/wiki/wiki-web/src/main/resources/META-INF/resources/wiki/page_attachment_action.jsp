<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/wiki/init.jsp" %>

<%
boolean viewTrashAttachments = ParamUtil.getBoolean(request, "viewTrashAttachments");

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

FileEntry attachmentsFileEntry = (FileEntry)row.getObject();

WikiPage wikiPage = WikiPageAttachmentsUtil.getPage(attachmentsFileEntry.getFileEntryId());
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="actions"
>
	<c:choose>
		<c:when test="<%= viewTrashAttachments %>">
			<c:if test="<%= WikiNodePermission.contains(permissionChecker, wikiPage.getNodeId(), ActionKeys.ADD_ATTACHMENT) %>">
				<portlet:actionURL name="/wiki/edit_page_attachment" var="restoreEntryURL">
					<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESTORE %>" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="nodeId" value="<%= String.valueOf(wikiPage.getNodeId()) %>" />
					<portlet:param name="title" value="<%= wikiPage.getTitle() %>" />
					<portlet:param name="fileName" value="<%= HtmlUtil.unescape(attachmentsFileEntry.getTitle()) %>" />
				</portlet:actionURL>

				<liferay-ui:icon
					message="restore"
					url="<%= restoreEntryURL %>"
				/>
			</c:if>

			<c:if test="<%= WikiPagePermission.contains(permissionChecker, wikiPage, ActionKeys.DELETE) %>">
				<portlet:actionURL name="/wiki/edit_page_attachment" var="deleteURL">
					<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="nodeId" value="<%= String.valueOf(wikiPage.getNodeId()) %>" />
					<portlet:param name="title" value="<%= wikiPage.getTitle() %>" />
					<portlet:param name="fileName" value="<%= HtmlUtil.unescape(attachmentsFileEntry.getTitle()) %>" />
				</portlet:actionURL>

				<liferay-ui:icon-delete
					url="<%= deleteURL %>"
				/>
			</c:if>
		</c:when>
		<c:otherwise>
			<c:if test="<%= WikiPagePermission.contains(permissionChecker, wikiPage, ActionKeys.DELETE) %>">
				<portlet:actionURL name="/wiki/edit_page_attachment" var="deleteURL">
					<portlet:param name="<%= Constants.CMD %>" value="<%= trashHelper.isTrashEnabled(scopeGroupId) ? Constants.MOVE_TO_TRASH : Constants.DELETE %>" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="nodeId" value="<%= String.valueOf(wikiPage.getNodeId()) %>" />
					<portlet:param name="title" value="<%= wikiPage.getTitle() %>" />
					<portlet:param name="fileName" value="<%= HtmlUtil.unescape(attachmentsFileEntry.getTitle()) %>" />
				</portlet:actionURL>

				<div class="delete-attachment" data-rowid="<%= attachmentsFileEntry.getFileEntryId() %>" data-url="<%= deleteURL.toString() %>">
					<liferay-ui:icon-delete
						trash="<%= trashHelper.isTrashEnabled(scopeGroupId) %>"
						url="javascript:void(0);"
					/>
				</div>
			</c:if>
		</c:otherwise>
	</c:choose>
</liferay-ui:icon-menu>