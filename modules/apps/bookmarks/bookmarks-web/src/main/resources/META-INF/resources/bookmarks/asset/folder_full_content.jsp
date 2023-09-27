<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/bookmarks/init.jsp" %>

<%
BookmarksFolder folder = (BookmarksFolder)request.getAttribute(BookmarksWebKeys.BOOKMARKS_FOLDER);
%>

<c:if test="<%= folder != null %>">

	<%
	int status = WorkflowConstants.STATUS_APPROVED;

	if (permissionChecker.isContentReviewer(user.getCompanyId(), scopeGroupId)) {
		status = WorkflowConstants.STATUS_ANY;
	}

	int foldersCount = BookmarksFolderServiceUtil.getFoldersCount(scopeGroupId, folder.getFolderId(), status);
	int entriesCount = BookmarksEntryServiceUtil.getEntriesCount(scopeGroupId, folder.getFolderId(), status);
	%>

	<clay:row>
		<clay:col
			cssClass="lfr-asset-column lfr-asset-column-details"
		>
			<c:if test="<%= Validator.isNotNull(folder.getDescription()) %>">
				<div class="lfr-asset-description">
					<%= HtmlUtil.escape(folder.getDescription()) %>
				</div>
			</c:if>

			<div class="lfr-asset-metadata">
				<div class="icon-calendar lfr-asset-icon">
					<liferay-ui:message arguments="<%= dateFormatDate.format(folder.getModifiedDate()) %>" key="last-updated-x" translateArguments="<%= false %>" />
				</div>

				<%
				AssetRendererFactory<BookmarksFolder> bookmarksFolderAssetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClass(BookmarksFolder.class);
				%>

				<div class="lfr-asset-icon">
					<liferay-ui:icon
						icon="<%= bookmarksFolderAssetRendererFactory.getIconCssClass() %>"
						markupView="lexicon"
					/>

					<%= foldersCount %> <liferay-ui:message key='<%= (foldersCount == 1) ? "folder" : "folders" %>' />
				</div>

				<%
				AssetRendererFactory<BookmarksEntry> bookmarksEntryAssetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClass(BookmarksEntry.class);
				%>

				<div class="last lfr-asset-icon">
					<liferay-ui:icon
						icon="<%= bookmarksEntryAssetRendererFactory.getIconCssClass() %>"
						markupView="lexicon"
					/>

					<%= entriesCount %> <liferay-ui:message key='<%= (entriesCount == 1) ? "bookmark" : "bookmarks" %>' />
				</div>
			</div>

			<liferay-expando:custom-attributes-available
				className="<%= BookmarksFolder.class.getName() %>"
			>
				<liferay-expando:custom-attribute-list
					className="<%= BookmarksFolder.class.getName() %>"
					classPK="<%= (folder != null) ? folder.getFolderId() : 0 %>"
					editable="<%= false %>"
					label="<%= true %>"
				/>
			</liferay-expando:custom-attributes-available>
		</clay:col>
	</clay:row>
</c:if>