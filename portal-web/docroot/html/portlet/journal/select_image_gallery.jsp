<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/image_gallery_display/init.jsp" %>

<%
Folder folder = (Folder)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_ENTRY);

long folderId = BeanParamUtil.getLong(folder, request, "folderId", DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

long repositoryId = BeanParamUtil.getLong(folder, request, "repositoryId", scopeGroupId);

long groupId = ParamUtil.getLong(request, "groupId");

int status = WorkflowConstants.STATUS_APPROVED;

if (permissionChecker.isCompanyAdmin() || permissionChecker.isGroupAdmin(scopeGroupId)) {
	status = WorkflowConstants.STATUS_ANY;
}

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/journal/select_image_gallery");
portletURL.setParameter("folderId", String.valueOf(folderId));
portletURL.setParameter("groupId", String.valueOf(groupId));

if (folder != null) {
	IGUtil.addPortletBreadcrumbEntries(folder, request, renderResponse);
}
%>

<aui:form method="post">
	<liferay-ui:header
		title="folders"
	/>

	<liferay-ui:breadcrumb showGuestGroup="<%= false %>" showLayout="<%= false %>" showParentGroups="<%= false %>" />

	<%
	List<String> headerNames = new ArrayList<String>();

	headerNames.add("folder");
	headerNames.add("num-of-folders");
	headerNames.add("num-of-images");

	SearchContainer searchContainer = new SearchContainer(renderRequest, null, null, "cur1", SearchContainer.DEFAULT_DELTA, portletURL, headerNames, "there-are-no-folders");

	int total = DLAppLocalServiceUtil.getFoldersCount(repositoryId, folderId);

	searchContainer.setTotal(total);

	List results = DLAppLocalServiceUtil.getFolders(repositoryId, folderId, searchContainer.getStart(), searchContainer.getEnd());

	searchContainer.setResults(results);

	List resultRows = searchContainer.getResultRows();

	for (int i = 0; i < results.size(); i++) {
		Folder curFolder = (Folder)results.get(i);

		ResultRow row = new ResultRow(curFolder, curFolder.getFolderId(), i);

		PortletURL rowURL = renderResponse.createRenderURL();

		rowURL.setParameter("struts_action", "/journal/select_image_gallery");
		rowURL.setParameter("folderId", String.valueOf(curFolder.getFolderId()));
		rowURL.setParameter("groupId", String.valueOf(groupId));

		// Name

		row.addText(curFolder.getName(), rowURL);

		// Statistics

		List subfolderIds = new ArrayList();

		subfolderIds.add(new Long(curFolder.getFolderId()));

		DLAppServiceUtil.getSubfolderIds(repositoryId, subfolderIds, curFolder.getFolderId());

		int foldersCount = subfolderIds.size() - 1;
		int imagesCount = DLAppLocalServiceUtil.getFoldersFileEntriesCount(repositoryId, subfolderIds, status);

		row.addText(String.valueOf(foldersCount), rowURL);
		row.addText(String.valueOf(imagesCount), rowURL);

		// Add result row

		resultRows.add(row);
	}
	%>

	<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />

	<liferay-ui:header
		title="images"
	/>

	<%
	headerNames.clear();

	headerNames.add("thumbnail");
	headerNames.add("name");
	headerNames.add("height");
	headerNames.add("width");
	headerNames.add("size");
	headerNames.add(StringPool.BLANK);

	searchContainer = new SearchContainer(renderRequest, null, null, "cur2", SearchContainer.DEFAULT_DELTA, portletURL, headerNames, "there-are-no-images-in-this-folder");

	total = DLAppLocalServiceUtil.getFileEntriesCount(repositoryId, folderId);

	searchContainer.setTotal(total);

	results = DLAppLocalServiceUtil.getFileEntries(repositoryId, folderId, searchContainer.getStart(), searchContainer.getEnd());

	searchContainer.setResults(results);

	resultRows = searchContainer.getResultRows();

	for (int i = 0; i < results.size(); i++) {
		FileEntry image = (FileEntry)results.get(i);

		Image largeImage = ImageLocalServiceUtil.getImage(image.getLargeImageId());

		ResultRow row = new ResultRow(image, image.getFileEntryId(), i);

		// Thumbnail

		row.addJSP("/html/portlet/image_gallery_display/image_thumbnail.jsp");

		// Name

		row.addText(image.getNameWithExtension());

		// Statistics

		row.addText(String.valueOf(largeImage.getHeight()));
		row.addText(String.valueOf(largeImage.getWidth()));
		row.addText(TextFormatter.formatKB(largeImage.getSize(), locale) + "k");

		// Action

		StringBundler sb = new StringBundler(11);

		sb.append("parent.");
		sb.append(renderResponse.getNamespace());
		sb.append("selectImageGallery('");
		sb.append(themeDisplay.getPathImage());
		sb.append("/image_gallery?uuid=");
		sb.append(image.getUuid());
		sb.append("&groupId=");
		sb.append(image.getGroupId());
		sb.append("&t=");
		sb.append(ImageServletTokenUtil.getToken(image.getLargeImageId()));
		sb.append("'); Liferay.Util.getWindow().close();");

		row.addButton("right", SearchEntry.DEFAULT_VALIGN, LanguageUtil.get(pageContext, "choose"), sb.toString());

		// Add result row

		resultRows.add(row);
	}
	%>

	<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
</aui:form>