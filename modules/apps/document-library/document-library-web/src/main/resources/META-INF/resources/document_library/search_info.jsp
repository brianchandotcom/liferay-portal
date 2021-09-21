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

DLAdminDisplayContext dlAdminDisplayContext = (DLAdminDisplayContext)request.getAttribute(DLAdminDisplayContext.class.getName());

if (folderId != dlAdminDisplayContext.getRootFolderId()) {
	folder = DLAppServiceUtil.getFolder(folderId);
}

List<Folder> mountFolders = DLAppServiceUtil.getMountFolders(scopeGroupId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
%>

<c:if test="<%= !(mountFolders.isEmpty() && (folder == null)) %>">
	<div class="search-info">
		<liferay-util:whitespace-remover>
			<liferay-ui:message key="search-colon" />

			<%
			long repositoryId = ParamUtil.getLong(request, "repositoryId");

			if (repositoryId == 0) {
				repositoryId = scopeGroupId;
			}

			PortletURL searchEverywhereURL = PortletURLBuilder.createRenderURL(
				liferayPortletResponse
			).setMVCRenderCommandName(
				"/document_library/search"
			).setParameter(
				"repositoryId", repositoryId
			).buildPortletURL();

			long searchRepositoryId = ParamUtil.getLong(request, "searchRepositoryId");

			if (searchRepositoryId == 0) {
				searchRepositoryId = repositoryId;
			}

			searchEverywhereURL.setParameter("searchRepositoryId", String.valueOf(searchRepositoryId));

			searchEverywhereURL.setParameter("folderId", String.valueOf(folderId));

			searchEverywhereURL.setParameter("searchFolderId", String.valueOf(dlAdminDisplayContext.getRootFolderId()));

			String keywords = ParamUtil.getString(request, "keywords");

			searchEverywhereURL.setParameter("keywords", keywords);

			searchEverywhereURL.setParameter("showSearchInfo", Boolean.TRUE.toString());

			PortletURL searchFolderURL = PortletURLBuilder.create(
				PortletURLUtil.clone(searchEverywhereURL, liferayPortletResponse)
			).setParameter(
				"folderId", folderId
			).setParameter(
				"searchFolderId", folderId
			).setParameter(
				"searchRepositoryId", scopeGroupId
			).buildPortletURL();

			long searchFolderId = ParamUtil.getLong(request, "searchFolderId");
			%>

			<c:if test="<%= mountFolders.isEmpty() && (folder != null) %>">
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

			<c:if test="<%= folder != null %>">
				<clay:link
					cssClass='<%= (searchFolderId == folder.getFolderId()) ? "active" : "" %>'
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
					PortletURLUtil.clone(searchEverywhereURL, liferayPortletResponse)
				).setParameter(
					"repositoryId", scopeGroupId
				).setParameter(
					"searchRepositoryId", scopeGroupId
				).buildPortletURL();
				%>

				<clay:link
					cssClass='<%= ((searchRepositoryId == scopeGroupId) && (searchFolderId == dlAdminDisplayContext.getRootFolderId())) ? "active" : "" %>'
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
					searchRepositoryURL.setParameter("repositoryId", String.valueOf(mountFolder.getRepositoryId()));
					searchRepositoryURL.setParameter("searchRepositoryId", String.valueOf(mountFolder.getRepositoryId()));
					searchRepositoryURL.setParameter("searchFolderId", String.valueOf(mountFolder.getFolderId()));
				%>

					<clay:link
						cssClass='<%= (mountFolder.getFolderId() == searchFolderId) ? "active" : "" %>'
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
		</liferay-util:whitespace-remover>
	</div>
</c:if>

<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) %>">
	<aui:script>
		Liferay.componentReady('<portlet:namespace />entriesManagementToolbar').then(
			() => {
				Liferay.Util.focusFormField(
					document.getElementsByName('<portlet:namespace />keywords')[0]
				);
			}
		);
	</aui:script>
</c:if>