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

<%@ include file="/bookmarks/init.jsp" %>

<%
BookmarksFolder folder = (BookmarksFolder)request.getAttribute(BookmarksWebKeys.BOOKMARKS_FOLDER);

long folderId = BeanParamUtil.getLong(folder, request, "folderId", BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID);

String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "selectFolder");

String folderName = LanguageUtil.get(request, "home");

if (folder != null) {
	folderName = folder.getName();

	BookmarksUtil.addPortletBreadcrumbEntries(folder, request, renderResponse);
}
%>

<div class="container-fluid-1280">
	<aui:form method="post" name="selectFolderFm">
		<liferay-ui:breadcrumb
			showCurrentGroup="<%= false %>"
			showGuestGroup="<%= false %>"
			showLayout="<%= false %>"
			showParentGroups="<%= false %>"
		/>

		<%
		PortletURL portletURL = renderResponse.createRenderURL();

		portletURL.setParameter("mvcRenderCommandName", "/bookmarks/select_folder");
		portletURL.setParameter("folderId", String.valueOf(folderId));
		portletURL.setParameter("eventName", eventName);
		%>

		<br />

		<liferay-ui:search-container
			iteratorURL="<%= portletURL %>"
			total="<%= BookmarksFolderServiceUtil.getFoldersCount(scopeGroupId, folderId) %>"
		>
			<liferay-ui:search-container-results
				results="<%= BookmarksFolderServiceUtil.getFolders(scopeGroupId, folderId, searchContainer.getStart(), searchContainer.getEnd()) %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.bookmarks.model.BookmarksFolder"
				modelVar="curFolder"
			>
				<portlet:renderURL var="viewFolderURL">
					<portlet:param name="mvcRenderCommandName" value="/bookmarks/select_folder" />
					<portlet:param name="folderId" value="<%= String.valueOf(curFolder.getFolderId()) %>" />
				</portlet:renderURL>

				<liferay-ui:search-container-column-text
					name="folder"
				>

					<%
					AssetRendererFactory<BookmarksFolder> assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClass(BookmarksFolder.class);

					AssetRenderer<BookmarksFolder> assetRenderer = assetRendererFactory.getAssetRenderer(curFolder.getFolderId());
					%>

					<liferay-ui:icon
						icon="<%= assetRenderer.getIconCssClass() %>"
						label="<%= true %>"
						localizeMessage="<%= false %>"
						markupView="lexicon"
						message="<%= HtmlUtil.escape(curFolder.getName()) %>"
						url="<%= viewFolderURL %>"
					/>
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text
					href="<%= viewFolderURL %>"
					name="num-of-folders"
					value="<%= String.valueOf(BookmarksFolderServiceUtil.getFoldersCount(scopeGroupId, curFolder.getFolderId(), WorkflowConstants.STATUS_APPROVED)) %>"
				/>

				<liferay-ui:search-container-column-text
					href="<%= viewFolderURL %>"
					name="num-of-entries"
					value="<%= String.valueOf(BookmarksEntryServiceUtil.getFoldersEntriesCount(scopeGroupId, Arrays.asList(curFolder.getFolderId()))) %>"
				/>

				<liferay-ui:search-container-column-text>

					<%
					Map<String, Object> data = new HashMap<String, Object>();

					data.put("entityid", curFolder.getFolderId());
					data.put("entityname", curFolder.getName());
					%>

					<aui:button cssClass="selector-button" data="<%= data %>" value="choose" />
				</liferay-ui:search-container-column-text>
			</liferay-ui:search-container-row>

			<aui:button-row>
				<c:if test="<%= BookmarksFolderPermission.contains(permissionChecker, scopeGroupId, folderId, ActionKeys.ADD_FOLDER) %>">
					<portlet:renderURL var="editFolderURL">
						<portlet:param name="mvcRenderCommandName" value="/bookmarks/edit_folder" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="parentFolderId" value="<%= String.valueOf(folderId) %>" />
					</portlet:renderURL>

					<aui:button href="<%= editFolderURL %>" value='<%= (folder == null) ? "add-folder" : "add-subfolder" %>' />
				</c:if>

				<%
				Map<String, Object> data = new HashMap<String, Object>();

				data.put("entityid", folderId);
				data.put("entityname", folderName);
				%>

				<aui:button cssClass="selector-button" data="<%= data %>" value="choose-this-folder" />
			</aui:button-row>

			<liferay-ui:search-iterator />
		</liferay-ui:search-container>
	</aui:form>
</div>

<aui:script>
	Liferay.Util.selectEntityHandler(
		'#<portlet:namespace />selectFolderFm',
		'<%= HtmlUtil.escapeJS(eventName) %>'
	);
</aui:script>