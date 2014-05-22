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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
Folder folder = (Folder)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FOLDER);

long folderId = BeanParamUtil.getLong(folder, request, "folderId", DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

long groupId = BeanParamUtil.getLong(folder, request, "groupId");
String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "selectFileEntry_" + groupId);

if (folder != null) {
	DLUtil.addPortletBreadcrumbEntries(folder, request, renderResponse);
}
%>

<aui:form method="post" name="selectFileEntryFm">
	<liferay-ui:header
		title="home"
	/>

	<liferay-ui:breadcrumb showGuestGroup="<%= false %>" showLayout="<%= false %>" showParentGroups="<%= false %>" />

	<%
	PortletURL portletURL = renderResponse.createRenderURL();

	portletURL.setParameter("struts_action", "/document_library/select_file_entry");
	portletURL.setParameter("groupId", String.valueOf(groupId));
	portletURL.setParameter("folderId", String.valueOf(folderId));
	%>

	<liferay-ui:search-container
		iteratorURL="<%= portletURL %>"
		total="<%= DLAppServiceUtil.getFoldersCount(groupId, folderId) %>"
	>
		<liferay-ui:search-container-results
			results="<%= DLAppServiceUtil.getFolders(groupId, folderId, searchContainer.getStart(), searchContainer.getEnd()) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.repository.model.Folder"
			keyProperty="folderId"
			modelVar="curFolder"
			rowVar="row"
		>
			<liferay-portlet:renderURL varImpl="rowURL">
				<portlet:param name="struts_action" value="/document_library/select_file_entry" />
				<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
				<portlet:param name="folderId" value="<%= String.valueOf(curFolder.getFolderId()) %>" />
			</liferay-portlet:renderURL>

			<%
			int fileEntriesCount = 0;
			int foldersCount = 0;

			try{
				List<Long> subfolderIds = DLAppServiceUtil.getSubfolderIds(groupId, curFolder.getFolderId(), false);

				foldersCount = subfolderIds.size();

				subfolderIds.clear();
				subfolderIds.add(curFolder.getFolderId());

				fileEntriesCount = DLAppServiceUtil.getFoldersFileEntriesCount(groupId, subfolderIds, WorkflowConstants.STATUS_APPROVED);
			}
			catch (com.liferay.portal.kernel.repository.RepositoryException re) {
				rowURL = null;
			}
			catch (com.liferay.portal.security.auth.PrincipalException pe) {
				rowURL = null;
			}

			AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(DLFolder.class.getName());

			AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(curFolder.getFolderId());
			%>

			<liferay-ui:search-container-column-text
				name="folder"
			>
				<liferay-ui:icon iconCssClass="<%= assetRenderer.getIconCssClass() %>" label="<%= true %>" message="<%= HtmlUtil.escape(curFolder.getName()) %>" url="<%= rowURL.toString() %>" />
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="num-of-folders"
				value="<%= String.valueOf(foldersCount) %>"
			/>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="num-of-documents"
				value="<%= String.valueOf(fileEntriesCount) %>"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator />
	</liferay-ui:search-container>

	<br />

	<liferay-ui:header
		title="documents"
	/>

	<liferay-ui:search-container
		iteratorURL="<%= portletURL %>"
		total="<%= DLAppServiceUtil.getFileEntriesCount(groupId, folderId) %>"
	>
		<liferay-ui:search-container-results
			results="<%= DLAppServiceUtil.getFileEntries(groupId, folderId, searchContainer.getStart(), searchContainer.getEnd()) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.repository.model.FileEntry"
			keyProperty="fileEntryId"
			modelVar="curFile"
			rowVar="row"
		>
			<liferay-ui:search-container-column-text
				name="document"
			>

				<%
				AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(DLFolder.class.getName());

				AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(curFile.getFileEntryId());
				%>

				<liferay-ui:icon iconCssClass="<%= assetRenderer.getIconCssClass() %>" label="<%= true %>" message="<%= HtmlUtil.escape(curFile.getTitle()) %>" />

				<c:if test="<%= Validator.isNotNull(curFile.getDescription()) %>">
					<br />
					<%= HtmlUtil.escape(curFile.getDescription()) %>
				</c:if>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				name="size"
				value="<%= TextFormatter.formatStorageSize(curFile.getSize(), locale) %>"
			/>

			<c:if test="<%= PropsValues.DL_FILE_ENTRY_BUFFERED_INCREMENT_ENABLED %>">
				<liferay-ui:search-container-column-text
					name="downloads"
					value="<%= String.valueOf(curFile.getReadCount()) %>"
				/>
			</c:if>

			<liferay-ui:search-container-column-text
				name="locked"
				value='<%= LanguageUtil.get(pageContext, curFile.isCheckedOut() ? "yes" : "no") %>'
			/>

			<liferay-ui:search-container-column-text>

				<%
				Map<String, Object> data = new HashMap<String, Object>();

				data.put("entryid", curFile.getFileEntryId());
				data.put("entryname", curFile.getTitle());
				%>

				<aui:button cssClass="selector-button" data="<%= data %>" value="choose" />
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</aui:form>

<aui:script use="aui-base">
	Liferay.Util.selectEntityHandler('#<portlet:namespace />selectFileEntryFm', '<%= HtmlUtil.escapeJS(eventName) %>');
</aui:script>