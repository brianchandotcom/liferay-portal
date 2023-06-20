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
DLAdminDisplayContext dlAdminDisplayContext = (DLAdminDisplayContext)request.getAttribute(DLAdminDisplayContext.class.getName());

Folder folder = null;
long folderId = ParamUtil.getLong(request, "folderId");

if (folderId != dlAdminDisplayContext.getRootFolderId()) {
	folder = DLAppServiceUtil.getFolder(folderId);
}

List<Folder> mountFolders = DLAppServiceUtil.getMountFolders(scopeGroupId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
%>

<c:if test="<%= (folder != null) || !mountFolders.isEmpty() %>">
	<div class="search-info">
		<c:if test='<%= dlAdminDisplayContext.hasFilterParameters() && !mountFolders.isEmpty() && FeatureFlagManagerUtil.isEnabled("LPS-84424") %>'>
			<clay:alert
				displayType="info"
				message="filters-only-apply-to-documents-in-local-repositories"
			/>
		</c:if>

		<%
		long repositoryId = ParamUtil.getLong(request, "repositoryId");

		if (repositoryId == 0) {
			repositoryId = scopeGroupId;
		}

		long searchRepositoryId = ParamUtil.getLong(request, "searchRepositoryId");

		if (searchRepositoryId == 0) {
			searchRepositoryId = repositoryId;
		}

		PortletURL searchURL = dlAdminDisplayContext.getSearchRenderURL();

		long searchFolderId = ParamUtil.getLong(request, "searchFolderId");
		%>

		<div class="btn-group">
			<c:if test="<%= (folder != null) && mountFolders.isEmpty() %>">

				<%
				PortletURL searchEverywhereURL = PortletURLBuilder.create(
					PortletURLUtil.clone(searchURL, liferayPortletResponse)
				).setParameter(
					"searchFolderId", dlAdminDisplayContext.getRootFolderId()
				).setParameter(
					"searchRepositoryId", searchRepositoryId
				).buildPortletURL();
				%>

				<clay:link
					cssClass='<%= (searchFolderId == dlAdminDisplayContext.getRootFolderId()) ? "active" : "" %>'
					displayType="secondary"
					href="<%= searchEverywhereURL.toString() %>"
					label="everywhere"
					small="<%= true %>"
					title="everywhere"
					type="button"
				/>
			</c:if>

			<c:if test="<%= (folder != null) && !folder.isMountPoint() %>">

				<%
				PortletURL searchFolderURL = PortletURLBuilder.create(
					PortletURLUtil.clone(searchURL, liferayPortletResponse)
				).setParameter(
					"searchFolderId", folderId
				).setParameter(
					"searchRepositoryId", repositoryId
				).buildPortletURL();
				%>

				<clay:link
					cssClass='<%= ((searchFolderId == folderId) && (searchRepositoryId == repositoryId)) ? "active" : "" %>'
					displayType="secondary"
					href="<%= searchFolderURL.toString() %>"
					icon="folder"
					label="<%= folder.getName() %>"
					small="<%= true %>"
					title="<%= folder.getName() %>"
					type="button"
				/>
			</c:if>

			<c:if test="<%= !mountFolders.isEmpty() %>">

				<%
				PortletURL searchRepositoryURL = PortletURLBuilder.create(
					PortletURLUtil.clone(searchURL, liferayPortletResponse)
				).setParameter(
					"searchFolderId", dlAdminDisplayContext.getRootFolderId()
				).setParameter(
					"searchRepositoryId", scopeGroupId
				).buildPortletURL();
				%>

				<clay:link
					cssClass='<%= ((searchFolderId == dlAdminDisplayContext.getRootFolderId()) && (searchRepositoryId == scopeGroupId)) ? "active" : "" %>'
					displayType="secondary"
					href="<%= searchRepositoryURL.toString() %>"
					icon="repository"
					label="local"
					small="<%= true %>"
					title="local"
					type="button"
				/>

				<%
				for (Folder mountFolder : mountFolders) {
					searchRepositoryURL.setParameter("searchFolderId", String.valueOf(mountFolder.getFolderId()));
					searchRepositoryURL.setParameter("searchRepositoryId", String.valueOf(mountFolder.getRepositoryId()));
				%>

					<clay:link
						cssClass='<%= (searchFolderId == mountFolder.getFolderId()) ? "active" : "" %>'
						displayType="secondary"
						href="<%= searchRepositoryURL.toString() %>"
						icon="repository"
						label="<%= mountFolder.getName() %>"
						small="<%= true %>"
						title="<%= mountFolder.getName() %>"
						type="button"
					/>

				<%
				}
				%>

			</c:if>
		</div>
	</div>
</c:if>