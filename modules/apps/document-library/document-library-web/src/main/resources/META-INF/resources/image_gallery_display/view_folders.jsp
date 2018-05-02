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

<%@ include file="/image_gallery_display/init.jsp" %>

<%
long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));

long repositoryId = GetterUtil.getLong((String)request.getAttribute("view.jsp-repositoryId"));

if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
	Folder folder = DLAppServiceUtil.getFolder(folderId);

	repositoryId = folder.getRepositoryId();
}

int status = WorkflowConstants.STATUS_APPROVED;

if (permissionChecker.isContentReviewer(user.getCompanyId(), scopeGroupId)) {
	status = WorkflowConstants.STATUS_ANY;
}

PortletURL portletURL = (PortletURL)request.getAttribute("view.jsp-portletURL");
%>

<liferay-ui:search-container
	curParam="cur1"
	headerNames="folder,num-of-folders,num-of-images"
	iteratorURL="<%= portletURL %>"
	total="<%= DLAppServiceUtil.getFoldersCount(repositoryId, folderId) %>"
>
	<liferay-ui:search-container-results
		results="<%= DLAppServiceUtil.getFolders(repositoryId, folderId, searchContainer.getStart(), searchContainer.getEnd()) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.portal.kernel.repository.model.Folder"
		escapedModel="<%= true %>"
		keyProperty="folderId"
		modelVar="curFolder"
	>
		<liferay-portlet:renderURL varImpl="rowURL">
			<portlet:param name="mvcRenderCommandName" value="/image_gallery_display/view" />
			<portlet:param name="folderId" value="<%= String.valueOf(curFolder.getFolderId()) %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
		</liferay-portlet:renderURL>

		<liferay-ui:search-container-column-text
			name="folder"
		>
			<a href="<%= rowURL %>">

				<%
				AssetRendererFactory<?> assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(DLFolder.class.getName());

				AssetRenderer<?> assetRenderer = assetRendererFactory.getAssetRenderer(curFolder.getFolderId());
				%>

				<liferay-ui:icon
					icon="<%= assetRenderer.getIconCssClass() %>"
					markupView="lexicon"
				/>

				<strong><%= curFolder.getName() %></strong>

				<c:if test="<%= Validator.isNotNull(curFolder.getDescription()) %>"> {
					<br />

					<%= curFolder.getDescription() %>
				</c:if>
			</a>

			<%
			List subfolders = DLAppServiceUtil.getFolders(repositoryId, curFolder.getFolderId(), 0, 5);
			%>

			<c:if test="<%= !subfolders.isEmpty() %>">
				<br />

				<liferay-ui:message key="subfolders" />:

				<%
				int subfoldersCount = DLAppServiceUtil.getFoldersCount(repositoryId, curFolder.getFolderId());

				for (int j = 0; j < subfolders.size(); j++) {
					Folder subfolder = (Folder)subfolders.get(j);

					String name = HtmlUtil.escape(subfolder.getName());

					if (((j + 1) < subfolders.size()) || (subfoldersCount > subfolders.size())) {
						name += StringPool.COMMA_AND_SPACE;
					}
				%>

					<a href="<%= rowURL %>"><%= name %></a>

				<%
				}

				rowURL.setParameter("folderId", String.valueOf(curFolder.getFolderId()));
				%>

				<c:if test="<%= subfoldersCount > subfolders.size() %>">
					<a href="<%= rowURL %>"><liferay-ui:message key="more" /> &raquo;</a>
				</c:if>
			</c:if>
		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-text
			href="<%= rowURL %>"
			name="num-of-folders"
			value="<%= String.valueOf(DLAppServiceUtil.getFoldersCount(repositoryId, curFolder.getFolderId())) %>"
		/>

		<liferay-ui:search-container-column-text
			href="<%= rowURL %>"
			name="num-of-entries"
			value="<%= String.valueOf(DLAppServiceUtil.getFoldersFileEntriesCount(repositoryId, Arrays.asList(curFolder.getFolderId()), status)) %>"
		/>

		<liferay-ui:search-container-column-jsp
			align="right"
			cssClass="entry-action"
			path="/document_library/folder_action.jsp"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>