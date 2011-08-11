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
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

FileEntry fileEntry = null;

Object object = row.getObject();

if (object instanceof AssetEntry) {
	AssetEntry assetEntry = (AssetEntry)object;

	if (assetEntry.getClassName().equals(DLFileEntryConstants.getClassName())) {
		fileEntry = DLAppLocalServiceUtil.getFileEntry(assetEntry.getClassPK());

		fileEntry = fileEntry.toEscapedModel();
	}
}
else if (object instanceof FileEntry) {
	fileEntry = (FileEntry)object;

	fileEntry = fileEntry.toEscapedModel();
}
%>

<table class="lfr-table">
<tr>
	<td>
		<aui:a href='<%= themeDisplay.getPathImage() + "/image_gallery?img_id=" + fileEntry.getLargeImageId() + "&t=" + WebServerServletTokenUtil.getToken(fileEntry.getLargeImageId()) %>' target="_blank">
			<img border="1" src="<%= themeDisplay.getPathImage() %>/image_gallery?img_id=<%= fileEntry.getSmallImageId() %>&t=<%= WebServerServletTokenUtil.getToken(fileEntry.getSmallImageId()) %>" title="<%= fileEntry.getDescription() %>" />
		</aui:a>
	</td>
</tr>

<c:if test="<%= (fileEntry.getCustom1ImageId() > 0) || (fileEntry.getCustom2ImageId() > 0) %>">
	<tr>
		<td>
			<aui:a href='<%= themeDisplay.getPathImage() + "/image_gallery?img_id=" + fileEntry.getLargeImageId() + "&t=" + WebServerServletTokenUtil.getToken(fileEntry.getLargeImageId()) %>' target="_blank">
				<liferay-ui:message key="original" />
			</aui:a>

			<c:if test="<%= fileEntry.getCustom1ImageId() > 0 %>">
				|

				<aui:a href='<%= themeDisplay.getPathImage() + "/image_gallery?img_id=" + fileEntry.getCustom1ImageId() + "&t=" + WebServerServletTokenUtil.getToken(fileEntry.getCustom1ImageId()) %>' target="_blank">
					<liferay-ui:message key="size" /> 1
				</aui:a>
			</c:if>

			<c:if test="<%= fileEntry.getCustom2ImageId() > 0 %>">
				|

				<aui:a href='<%= themeDisplay.getPathImage() + "/image_gallery?img_id=" + fileEntry.getCustom2ImageId() + "&t=" + WebServerServletTokenUtil.getToken(fileEntry.getCustom2ImageId()) %>' target="_blank">
					<liferay-ui:message key="size" /> 2
				</aui:a>
			</c:if>
		</td>
	</tr>
</c:if>

</table>