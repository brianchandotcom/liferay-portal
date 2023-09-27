<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/document_library/init.jsp" %>

<%
Folder folder = (Folder)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FOLDER);
%>

<c:if test="<%= folder != null %>">
	<div class="aspect-ratio aspect-ratio-8-to-3 bg-light mb-4">
		<div class="aspect-ratio-item-center-middle aspect-ratio-item-fluid card-type-asset-icon">
			<div class="text-secondary">
				<svg aria-hidden="true" class="h4 lexicon-icon lexicon-icon-folder reference-mark">
					<use xlink:href="<%= themeDisplay.getPathThemeSpritemap() %>#folder" />
				</svg>
			</div>
		</div>
	</div>

	<c:if test="<%= Validator.isNotNull(folder.getDescription()) %>">
		<p>
			<%= HtmlUtil.replaceNewLine(HtmlUtil.escape(folder.getDescription())) %>
		</p>
	</c:if>

	<%
	int foldersCount = DLAppServiceUtil.getFoldersCount(folder.getRepositoryId(), folder.getFolderId());
	%>

	<div class="small">
		<%= foldersCount %> <liferay-ui:message key='<%= (foldersCount == 1) ? "folder" : "folders" %>' />
	</div>

	<%
	int status = WorkflowConstants.STATUS_APPROVED;

	if (permissionChecker.isContentReviewer(user.getCompanyId(), scopeGroupId)) {
		status = WorkflowConstants.STATUS_ANY;
	}

	int fileEntriesCount = DLAppServiceUtil.getFileEntriesAndFileShortcutsCount(folder.getRepositoryId(), folder.getFolderId(), status);
	%>

	<div class="small">
		<%= fileEntriesCount %> <liferay-ui:message key='<%= (fileEntriesCount == 1) ? "document" : "documents" %>' />
	</div>

	<liferay-expando:custom-attributes-available
		className="<%= DLFolderConstants.getClassName() %>"
	>
		<liferay-expando:custom-attribute-list
			className="<%= DLFolderConstants.getClassName() %>"
			classPK="<%= (folder != null) ? folder.getFolderId() : 0 %>"
			editable="<%= false %>"
			label="<%= true %>"
		/>
	</liferay-expando:custom-attributes-available>
</c:if>