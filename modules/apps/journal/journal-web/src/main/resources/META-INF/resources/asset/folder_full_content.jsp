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

<%@ include file="/asset/init.jsp" %>

<%
JournalDisplayContext journalDisplayContext = new JournalDisplayContext(request, liferayPortletRequest, liferayPortletResponse, null, null);

JournalFolder folder = journalDisplayContext.getFolder();
%>

<c:if test="<%= folder != null %>">
	<div class="aspect-ratio aspect-ratio-8-to-3 bg-light mb-4">
		<div class="aspect-ratio-item-center-middle aspect-ratio-item-fluid card-type-asset-icon">
			<div class="text-secondary">
				<svg aria-hidden="true" class="lexicon-icon lexicon-icon-folder reference-mark user-icon-xl">
					<use xlink:href="<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg#folder" />
				</svg>
			</div>
		</div>
	</div>

	<c:if test="<%= Validator.isNotNull(folder.getDescription()) %>">
		<div class="asset-content mb-3">
			<%= HtmlUtil.replaceNewLine(HtmlUtil.escape(folder.getDescription())) %>
		</div>
	</c:if>

	<div class="asset-details mb-3">

		<%
		int foldersCount = JournalFolderServiceUtil.getFoldersCount(scopeGroupId, folder.getFolderId(), journalDisplayContext.getStatus());
		%>

		<%= foldersCount %> <liferay-ui:message key='<%= (foldersCount == 1) ? "subfolder" : "subfolders" %>' />
	</div>

	<div class="asset-details mb-3">

		<%
		int entriesCount = JournalArticleServiceUtil.getArticlesCount(scopeGroupId, folder.getFolderId(), journalDisplayContext.getStatus());
		%>

		<%= entriesCount %> <liferay-ui:message key='<%= (entriesCount == 1) ? "article" : "articles" %>' />
	</div>

	<div class="asset-custom-attributes">
		<liferay-expando:custom-attributes-available
			className="<%= JournalFolder.class.getName() %>"
		>
			<liferay-expando:custom-attribute-list
				className="<%= JournalFolder.class.getName() %>"
				classPK="<%= folder.getFolderId() %>"
				editable="<%= false %>"
				label="<%= true %>"
			/>
		</liferay-expando:custom-attributes-available>
	</div>
</c:if>