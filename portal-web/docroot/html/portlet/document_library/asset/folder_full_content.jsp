<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
Folder folder = (Folder)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FOLDER);
%>

<c:if test="<%= folder != null %>">

	<%
	int status = WorkflowConstants.STATUS_APPROVED;

	if (permissionChecker.isContentReviewer(user.getCompanyId(), scopeGroupId)) {
		status = WorkflowConstants.STATUS_ANY;
	}

	int foldersCount = DLAppServiceUtil.getFoldersCount(folder.getRepositoryId(), folder.getFolderId());
	int fileEntriesCount = DLAppServiceUtil.getFileEntriesAndFileShortcutsCount(folder.getRepositoryId(), folder.getFolderId(), status);
	%>

	<aui:row>
		<aui:col cssClass="lfr-asset-column lfr-asset-column-details" width="100">
			<c:if test="<%= Validator.isNotNull(folder.getDescription()) %>">
				<div class="lfr-asset-description">
					<%= HtmlUtil.escape(folder.getDescription()) %>
				</div>
			</c:if>

			<div class="lfr-asset-metadata">
				<div class="lfr-asset-icon lfr-asset-date">
					<%= LanguageUtil.format(pageContext, "last-updated-x", dateFormatDateTime.format(folder.getModifiedDate())) %>
				</div>

				<div class="lfr-asset-icon lfr-asset-subfolders">
					<%= foldersCount %> <liferay-ui:message key='<%= (foldersCount == 1) ? "subfolder" : "subfolders" %>' />
				</div>

				<div class="lfr-asset-icon lfr-asset-items last">
					<%= fileEntriesCount %> <liferay-ui:message key='<%= (fileEntriesCount == 1) ? "document" : "documents" %>' />
				</div>
			</div>

			<liferay-ui:custom-attributes-available className="<%= DLFolderConstants.getClassName() %>">
				<liferay-ui:custom-attribute-list
					className="<%= DLFolderConstants.getClassName() %>"
					classPK="<%= (folder != null) ? folder.getFolderId() : 0 %>"
					editable="<%= false %>"
					label="<%= true %>"
				/>
			</liferay-ui:custom-attributes-available>
		</aui:col>
	</aui:row>
</c:if>