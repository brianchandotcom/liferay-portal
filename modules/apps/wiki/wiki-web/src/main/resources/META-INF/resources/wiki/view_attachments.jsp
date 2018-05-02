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
WikiPage wikiPage = (WikiPage)request.getAttribute(WikiWebKeys.WIKI_PAGE);

boolean copyPageAttachments = ParamUtil.getBoolean(request, "copyPageAttachments", true);

List<FileEntry> attachmentsFileEntries = null;

if (wikiPage != null) {
	attachmentsFileEntries = wikiPage.getAttachmentsFileEntries();
}

long templateNodeId = ParamUtil.getLong(request, "templateNodeId");
String templateTitle = ParamUtil.getString(request, "templateTitle");

WikiPage templatePage = null;

if ((templateNodeId > 0) && Validator.isNotNull(templateTitle)) {
	try {
		templatePage = WikiPageServiceUtil.getPage(templateNodeId, templateTitle);

		attachmentsFileEntries = templatePage.getAttachmentsFileEntries();
	}
	catch (Exception e) {
	}
}

int deletedAttachmentsCount = 0;

if (wikiPage != null) {
	deletedAttachmentsCount = wikiPage.getDeletedAttachmentsFileEntriesCount();
}
%>

<c:if test="<%= trashHelper.isTrashEnabled(scopeGroupId) && (deletedAttachmentsCount > 0) %>">
	<portlet:renderURL var="viewTrashAttachmentsURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
		<portlet:param name="mvcRenderCommandName" value="/wiki/view_trash_page_attachments" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="nodeId" value="<%= String.valueOf(wikiPage.getNodeId()) %>" />
		<portlet:param name="title" value="<%= wikiPage.getTitle() %>" />
		<portlet:param name="viewTrashAttachments" value="<%= Boolean.TRUE.toString() %>" />
	</portlet:renderURL>

	<div align="right">
		<a href="javascript:;" id="view-removed-attachments-link"><liferay-ui:message arguments="<%= deletedAttachmentsCount %>" key='<%= (deletedAttachmentsCount == 1) ? "x-recently-removed-attachment" : "x-recently-removed-attachments" %>' /> &raquo;</a>
	</div>

	<aui:script use="liferay-util-window">
		var viewRemovedAttachmentsLink = A.one('#view-removed-attachments-link');

		viewRemovedAttachmentsLink.on(
			'click',
			function(event) {
				Liferay.Util.openWindow(
					{
						id: '<portlet:namespace />openRemovedPageAttachments',
						title: '<%= LanguageUtil.get(request, "removed-attachments") %>',
						uri: '<%= viewTrashAttachmentsURL %>'
					}
				);
			}
		);
	</aui:script>
</c:if>

<c:if test="<%= attachmentsFileEntries != null %>">
	<c:if test="<%= (templatePage != null) && !attachmentsFileEntries.isEmpty() %>">
		<aui:input name="copyPageAttachments" type="checkbox" value="<%= copyPageAttachments %>" />
	</c:if>

	<%
	int attachmentsFileEntriesCount = attachmentsFileEntries.size();
	String emptyResultsMessage = "this-page-does-not-have-file-attachments";
	boolean paginate = false;
	boolean showPageAttachmentAction = (templateNodeId == 0);
	int status = WorkflowConstants.STATUS_APPROVED;
	%>

	<%@ include file="/wiki/attachments_list.jspf" %>
</c:if>