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

<%@ include file="/html/portlet/image_gallery/init.jsp" %>

<%
IGFolder folder = (IGFolder)request.getAttribute(WebKeys.IMAGE_GALLERY_FOLDER);

long folderId = BeanParamUtil.getLong(folder, request, "folderId", IGFolderConstants.DEFAULT_PARENT_FOLDER_ID);

long groupId = ParamUtil.getLong(request, "groupId");

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

	int total = IGFolderLocalServiceUtil.getFoldersCount(groupId, folderId);

	searchContainer.setTotal(total);

	List results = IGFolderLocalServiceUtil.getFolders(groupId, folderId, searchContainer.getStart(), searchContainer.getEnd());

	searchContainer.setResults(results);

	List resultRows = searchContainer.getResultRows();

	for (int i = 0; i < results.size(); i++) {
		IGFolder curFolder = (IGFolder)results.get(i);

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

		IGFolderLocalServiceUtil.getSubfolderIds(subfolderIds, groupId, curFolder.getFolderId());

		int foldersCount = subfolderIds.size() - 1;
		int imagesCount = IGImageLocalServiceUtil.getFoldersImagesCount(groupId, subfolderIds);

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

	total = IGImageLocalServiceUtil.getImagesCount(groupId, folderId);

	searchContainer.setTotal(total);

	results = IGImageLocalServiceUtil.getImages(groupId, folderId, searchContainer.getStart(), searchContainer.getEnd());

	searchContainer.setResults(results);

	resultRows = searchContainer.getResultRows();

	for (int i = 0; i < results.size(); i++) {
		IGImage image = (IGImage)results.get(i);

		Image largeImage = ImageLocalServiceUtil.getImage(image.getLargeImageId());

		ResultRow row = new ResultRow(image, image.getImageId(), i);

		// Thumbnail

		row.addJSP("/html/portlet/image_gallery/image_thumbnail.jsp");

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