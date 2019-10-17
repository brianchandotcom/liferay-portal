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

<%@ include file="/document_library/init.jsp" %>

<%
long folderId = ParamUtil.getLong(request, "folderId");

Folder folder = null;

if (folderId != rootFolderId) {
	folder = DLAppServiceUtil.getFolder(folderId);
}

List<Folder> mountFolders = DLAppServiceUtil.getMountFolders(scopeGroupId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
%>

<c:if test="<%= !(mountFolders.isEmpty() && (folder == null)) %>">
	<div class="search-info">
		<liferay-util:whitespace-remover>
			<liferay-ui:message key="search-colon" />

			<%
			PortletURL searchEverywhereURL = liferayPortletResponse.createRenderURL();

			searchEverywhereURL.setParameter("mvcRenderCommandName", "/document_library/search");

			long repositoryId = ParamUtil.getLong(request, "repositoryId");

			if (repositoryId == 0) {
				repositoryId = scopeGroupId;
			}

			searchEverywhereURL.setParameter("repositoryId", String.valueOf(repositoryId));

			long searchRepositoryId = ParamUtil.getLong(request, "searchRepositoryId");

			if (searchRepositoryId == 0) {
				searchRepositoryId = repositoryId;
			}

			searchEverywhereURL.setParameter("searchRepositoryId", String.valueOf(searchRepositoryId));

			searchEverywhereURL.setParameter("folderId", String.valueOf(folderId));

			searchEverywhereURL.setParameter("searchFolderId", String.valueOf(DLFolderConstants.DEFAULT_PARENT_FOLDER_ID));

			String keywords = ParamUtil.getString(request, "keywords");

			searchEverywhereURL.setParameter("keywords", keywords);

			searchEverywhereURL.setParameter("showSearchInfo", Boolean.TRUE.toString());

			PortletURL searchFolderURL = PortletURLUtil.clone(searchEverywhereURL, liferayPortletResponse);

			searchFolderURL.setParameter("searchRepositoryId", String.valueOf(scopeGroupId));
			searchFolderURL.setParameter("folderId", String.valueOf(folderId));
			searchFolderURL.setParameter("searchFolderId", String.valueOf(folderId));

			long searchFolderId = ParamUtil.getLong(request, "searchFolderId");
			%>

			<c:if test="<%= (mountFolders.size() == 0) && (folder != null) %>">
				<clay:link
					buttonStyle="secondary"
					elementClasses='<%= "btn-sm" + ((searchFolderId == rootFolderId) ? " active" : "") %>'
					href="<%= searchEverywhereURL.toString() %>"
					label='<%= LanguageUtil.get(resourceBundle, "everywhere") %>'
					title='<%= LanguageUtil.get(resourceBundle, "everywhere") %>'
				/>
			</c:if>

			<c:if test="<%= folder != null %>">
				<clay:link
					buttonStyle="secondary"
					elementClasses='<%= "btn-sm" + ((searchFolderId == folder.getFolderId()) ? " active" : "") %>'
					href="<%= searchFolderURL.toString() %>"
					icon="folder"
					label="<%= folder.getName() %>"
					title="<%= folder.getName() %>"
				/>
			</c:if>

			<c:if test="<%= mountFolders.size() > 0 %>">

				<%
				PortletURL searchRepositoryURL = PortletURLUtil.clone(searchEverywhereURL, liferayPortletResponse);

				searchRepositoryURL.setParameter("repositoryId", String.valueOf(scopeGroupId));
				searchRepositoryURL.setParameter("searchRepositoryId", String.valueOf(scopeGroupId));
				%>

				<clay:link
					buttonStyle="secondary"
					elementClasses='<%= "btn-sm" + (((searchRepositoryId == scopeGroupId) && (searchFolderId == rootFolderId)) ? " active" : "") %>'
					href="<%= searchRepositoryURL.toString() %>"
					icon="repository"
					label='<%= LanguageUtil.get(request, "local") %>'
					title='<%= LanguageUtil.get(request, "local") %>'
				/>

				<%
				for (Folder mountFolder : mountFolders) {
					searchRepositoryURL.setParameter("repositoryId", String.valueOf(mountFolder.getRepositoryId()));
					searchRepositoryURL.setParameter("searchRepositoryId", String.valueOf(mountFolder.getRepositoryId()));
					searchRepositoryURL.setParameter("searchFolderId", String.valueOf(mountFolder.getFolderId()));
				%>

					<clay:link
						buttonStyle="secondary"
						elementClasses='<%= "btn-sm" + ((mountFolder.getFolderId() == searchFolderId) ? " active" : "") %>'
						href="<%= searchRepositoryURL.toString() %>"
						icon="repository"
						label="<%= mountFolder.getName() %>"
						title="<%= mountFolder.getName() %>"
					/>

				<%
				}
				%>

			</c:if>
		</liferay-util:whitespace-remover>
	</div>
</c:if>

<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
	<aui:script>
		Liferay.Util.focusFormField(
			document.getElementsByName('<portlet:namespace />keywords')[0]
		);
	</aui:script>
</c:if>