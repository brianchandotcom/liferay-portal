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
Folder folder = (Folder)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FOLDER);

long folderId = BeanParamUtil.getLong(folder, request, "folderId", DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "selectFolder");

long repositoryId = scopeGroupId;
String folderName = LanguageUtil.get(request, "home");

if (folder != null) {
	repositoryId = folder.getRepositoryId();
	folderName = folder.getName();

	DLBreadcrumbUtil.addPortletBreadcrumbEntries(folder, request, renderResponse);
}

DLVisualizationHelper dlVisualizationHelper = new DLVisualizationHelper(dlRequestHelper);
%>

<div class="container-fluid-1280">
	<aui:form method="post" name="selectFolderFm">
		<liferay-ui:breadcrumb
			showCurrentGroup="<%= false %>"
			showGuestGroup="<%= false %>"
			showLayout="<%= false %>"
			showParentGroups="<%= false %>"
		/>

		<aui:button-row>
			<c:if test="<%= dlVisualizationHelper.isAddFolderButtonVisible() && DLFolderPermission.contains(permissionChecker, repositoryId, folderId, ActionKeys.ADD_FOLDER) %>">
				<portlet:renderURL var="editFolderURL">
					<portlet:param name="mvcRenderCommandName" value="/document_library/edit_folder" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="repositoryId" value="<%= String.valueOf(repositoryId) %>" />
					<portlet:param name="parentFolderId" value="<%= String.valueOf(folderId) %>" />
					<portlet:param name="ignoreRootFolder" value="<%= Boolean.TRUE.toString() %>" />
				</portlet:renderURL>

				<aui:button href="<%= editFolderURL %>" value="add-folder" />
			</c:if>

			<%
			Map<String, Object> data = new HashMap<String, Object>();

			data.put("folderid", folderId);
			data.put("folderissupportsmetadata", ((folder != null) ? folder.isSupportsMetadata() : Boolean.TRUE.toString()));
			data.put("folderissupportssocial", ((folder != null) ? folder.isSupportsSocial() : Boolean.TRUE.toString()));
			data.put("foldername", folderName);
			%>

			<aui:button cssClass="selector-button" data="<%= data %>" value="select-this-folder" />
		</aui:button-row>

		<%
		PortletURL portletURL = renderResponse.createRenderURL();

		portletURL.setParameter("mvcRenderCommandName", "/document_library/select_folder");
		portletURL.setParameter("folderId", String.valueOf(folderId));
		portletURL.setParameter("ignoreRootFolder", Boolean.TRUE.toString());
		portletURL.setParameter("showMountFolder", String.valueOf(dlVisualizationHelper.isMountFolderVisible()));
		%>

		<liferay-ui:search-container
			iteratorURL="<%= portletURL %>"
			total="<%= DLAppServiceUtil.getFoldersCount(repositoryId, folderId, dlVisualizationHelper.isMountFolderVisible()) %>"
		>
			<liferay-ui:search-container-results
				results="<%= DLAppServiceUtil.getFolders(repositoryId, folderId, dlVisualizationHelper.isMountFolderVisible(), searchContainer.getStart(), searchContainer.getEnd()) %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.portal.kernel.repository.model.Folder"
				keyProperty="folderId"
				modelVar="curFolder"
				rowVar="row"
			>
				<liferay-portlet:renderURL varImpl="rowURL">
					<portlet:param name="mvcRenderCommandName" value="/document_library/select_folder" />
					<portlet:param name="folderId" value="<%= String.valueOf(curFolder.getFolderId()) %>" />
					<portlet:param name="ignoreRootFolder" value="<%= Boolean.TRUE.toString() %>" />
					<portlet:param name="showMountFolder" value="<%= String.valueOf(dlVisualizationHelper.isMountFolderVisible()) %>" />
				</liferay-portlet:renderURL>

				<%
				AssetRendererFactory<?> assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(DLFolder.class.getName());

				AssetRenderer<?> assetRenderer = assetRendererFactory.getAssetRenderer(curFolder.getFolderId());

				int fileEntriesCount = 0;
				int foldersCount = 0;

				try {
					fileEntriesCount = DLAppServiceUtil.getFoldersFileEntriesCount(curFolder.getRepositoryId(), Arrays.asList(curFolder.getFolderId()), WorkflowConstants.STATUS_APPROVED);
					foldersCount = DLAppServiceUtil.getFoldersCount(curFolder.getRepositoryId(), curFolder.getFolderId());
				}
				catch (com.liferay.portal.kernel.repository.RepositoryException re) {
					rowURL = null;
				}
				catch (com.liferay.portal.kernel.security.auth.PrincipalException pe) {
					rowURL = null;
				}
				%>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand table-cell-minw-200 table-title"
					name="folder"
				>
					<liferay-ui:icon
						icon="<%= assetRenderer.getIconCssClass() %>"
						label="<%= true %>"
						localizeMessage="<%= false %>"
						markupView="lexicon"
						message="<%= HtmlUtil.escape(curFolder.getName()) %>"
						url="<%= (rowURL != null) ? rowURL.toString() : StringPool.BLANK %>"
					/>
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand-smallest table-column-text-end"
					href="<%= rowURL %>"
					name="folders"
					value="<%= String.valueOf(foldersCount) %>"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand-smallest table-column-text-end"
					href="<%= rowURL %>"
					name="documents"
					value="<%= String.valueOf(fileEntriesCount) %>"
				/>

				<liferay-ui:search-container-column-text>
					<c:if test="<%= rowURL != null %>">

						<%
						Map<String, Object> data = new HashMap<String, Object>();

						data.put("folderid", curFolder.getFolderId());
						data.put("folderissupportsmetadata", curFolder.isSupportsMetadata());
						data.put("folderissupportssocial", curFolder.isSupportsSocial());
						data.put("foldername", curFolder.getName());
						%>

						<aui:button cssClass="selector-button" data="<%= data %>" value="select" />
					</c:if>
				</liferay-ui:search-container-column-text>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</div>

<aui:script>
	Liferay.Util.selectEntityHandler(
		'#<portlet:namespace />selectFolderFm',
		'<%= HtmlUtil.escapeJS(eventName) %>'
	);
</aui:script>