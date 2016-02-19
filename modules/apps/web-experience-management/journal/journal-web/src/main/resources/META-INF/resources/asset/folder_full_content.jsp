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

<%@ include file="/init.jsp" %>

<%
JournalFolder folder = journalDisplayContext.getFolder();
%>

<c:if test="<%= folder != null %>">

	<%
	int foldersCount = JournalFolderServiceUtil.getFoldersCount(scopeGroupId, folder.getFolderId(), journalDisplayContext.getStatus());
	int entriesCount = JournalArticleServiceUtil.getArticlesCount(scopeGroupId, folder.getFolderId(), journalDisplayContext.getStatus());
	%>

	<aui:row>
		<aui:col cssClass="lfr-asset-column lfr-asset-column-details" width="<%= 100 %>">
			<c:if test="<%= Validator.isNotNull(folder.getDescription()) %>">
				<div class="lfr-asset-description">
					<%= HtmlUtil.escape(folder.getDescription()) %>
				</div>
			</c:if>

			<div class="lfr-asset-metadata">
				<div class="icon-calendar lfr-asset-icon">
					<liferay-ui:message arguments="<%= dateFormatDateTime.format(folder.getModifiedDate()) %>" key="last-updated-x" translateArguments="<%= false %>" />
				</div>

				<div class="lfr-asset-icon">
					<%= foldersCount %> <liferay-ui:message key='<%= (foldersCount == 1) ? "subfolder" : "subfolders" %>' />
				</div>

				<div class="last lfr-asset-icon">
					<%= entriesCount %> <liferay-ui:message key='<%= (entriesCount == 1) ? "article" : "articles" %>' />
				</div>
			</div>

			<liferay-ui:custom-attributes-available className="<%= JournalFolder.class.getName() %>">
				<liferay-ui:custom-attribute-list
					className="<%= JournalFolder.class.getName() %>"
					classPK="<%= folder.getFolderId() %>"
					editable="<%= false %>"
					label="<%= true %>"
				/>
			</liferay-ui:custom-attributes-available>
		</aui:col>
	</aui:row>
</c:if>