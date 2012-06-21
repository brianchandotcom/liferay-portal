<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/document_library_display/init.jsp" %>

<%
Folder folder = (Folder)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FOLDER);

long folderId = folder.getFolderId();

long repositoryId = folder.getRepositoryId();

int status = WorkflowConstants.STATUS_ANY;

int foldersCount = DLAppServiceUtil.getFoldersCount(repositoryId, folderId);

String redirect = ParamUtil.getString(request, "redirect");

long assetCategoryId = ParamUtil.getLong(request, "categoryId");
String assetTagName = ParamUtil.getString(request, "tag");
boolean useAssetEntryQuery = (assetCategoryId > 0) || Validator.isNotNull(assetTagName);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/trash/view_content");
portletURL.setParameter("redirect", redirect);
portletURL.setParameter("className", DLFolderConstants.getClassName());
portletURL.setParameter("classPK", String.valueOf(folder.getPrimaryKey()));
%>

<liferay-ui:panel-container extended="<%= false %>" id="documentLibraryDisplayInfoPanelContainer" persistState="<%= true %>">
	<c:if test="<%= foldersCount > 0 %>">
		<liferay-ui:panel collapsible="<%= true %>" cssClass="view-folders" extended="<%= true %>" id="documentLibraryDisplayFoldersListingPanel" persistState="<%= true %>" title='<%= (folder != null) ? "subfolders" : "folders" %>'>
			<liferay-ui:search-container
				curParam="cur1"
				delta="<%= foldersPerPage %>"
				deltaConfigurable="<%= false %>"
				iteratorURL="<%= portletURL %>"
			>
				<liferay-ui:search-container-results
					results="<%= DLAppServiceUtil.getFolders(repositoryId, folderId, searchContainer.getStart(), searchContainer.getEnd()) %>"
					total="<%= foldersCount %>"
				/>

				<liferay-ui:search-container-row
					className="com.liferay.portal.kernel.repository.model.Folder"
					escapedModel="<%= true %>"
					keyProperty="folderId"
					modelVar="curFolder"
					rowVar="row"
				>
					<liferay-portlet:renderURL varImpl="rowURL">
						<portlet:param name="struts_action" value="/trash/view_content" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="className" value="<%= DLFolderConstants.getClassName() %>" />
						<portlet:param name="classPK" value="<%= String.valueOf(curFolder.getPrimaryKey()) %>" />
					</liferay-portlet:renderURL>

					<%@ include file="/html/portlet/document_library/folder_columns.jspf" %>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator />
			</liferay-ui:search-container>
		</liferay-ui:panel>
	</c:if>

	<liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="documentLibraryDisplayDocumentsListingPanel" persistState="<%= true %>" title="documents">
		<%@ include file="/html/portlet/document_library/view_file_entries.jspf" %>
	</liferay-ui:panel>
</liferay-ui:panel-container>

<%!
private static Log _log = LogFactoryUtil.getLog("portal-web.docroot.html.portlet.document_library.trash.folder_jsp");
%>