<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

List<Folder> mountFolders = dlAdminDisplayContext.getMountFolders();
%>

<c:if test="<%= (folder != null) || !mountFolders.isEmpty() %>">
	<div class="search-info">

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