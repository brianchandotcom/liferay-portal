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
FileVersion fileVersion = null;

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

fileVersion = fileEntry.getFileVersion();

String largeURL = themeDisplay.getPortalURL() + themeDisplay.getPathContext() + "/documents/" + themeDisplay.getScopeGroupId() + StringPool.SLASH + fileEntry.getFolderId() + StringPool.SLASH + HttpUtil.encodeURL(HtmlUtil.unescape(fileEntry.getTitle())) + "?version=" + fileEntry.getVersion() + "&t=" + WebServerServletTokenUtil.getToken(fileEntry.getFileEntryId());
String thumbnailURL = themeDisplay.getPortalURL() + themeDisplay.getPathContext() + "/documents/" + themeDisplay.getScopeGroupId() + StringPool.SLASH + fileEntry.getFolderId() + StringPool.SLASH + HttpUtil.encodeURL(HtmlUtil.unescape(fileEntry.getTitle())) + "?version=" + fileEntry.getVersion() + "&imageThumbnail=1&t=" + WebServerServletTokenUtil.getToken(fileEntry.getFileEntryId());
String custom1URL = themeDisplay.getPortalURL() + themeDisplay.getPathContext() + "/documents/" + themeDisplay.getScopeGroupId() + StringPool.SLASH + fileEntry.getFolderId() + StringPool.SLASH + HttpUtil.encodeURL(HtmlUtil.unescape(fileEntry.getTitle())) + "?version=" + fileEntry.getVersion() + "&imageThumbnail=2&t=" + WebServerServletTokenUtil.getToken(fileEntry.getFileEntryId());
String custom2URL = themeDisplay.getPortalURL() + themeDisplay.getPathContext() + "/documents/" + themeDisplay.getScopeGroupId() + StringPool.SLASH + fileEntry.getFolderId() + StringPool.SLASH + HttpUtil.encodeURL(HtmlUtil.unescape(fileEntry.getTitle())) + "?version=" + fileEntry.getVersion() + "&imageThumbnail=3&t=" + WebServerServletTokenUtil.getToken(fileEntry.getFileEntryId());

int custom1MaxHeight = PrefsPropsUtil.getInteger(PropsKeys.DL_FILE_ENTRY_THUMBNAIL_CUSTOM_1_MAX_HEIGHT);
int custom1MaxWidth = PrefsPropsUtil.getInteger(PropsKeys.DL_FILE_ENTRY_THUMBNAIL_CUSTOM_1_MAX_WIDTH);
int custom2MaxHeight = PrefsPropsUtil.getInteger(PropsKeys.DL_FILE_ENTRY_THUMBNAIL_CUSTOM_2_MAX_HEIGHT);
int custom2MaxWidth = PrefsPropsUtil.getInteger(PropsKeys.DL_FILE_ENTRY_THUMBNAIL_CUSTOM_2_MAX_WIDTH);
%>

<table class="lfr-table">
<tr>
	<td>
		<aui:a href='<%= largeURL %>' target="_blank">
			<img border="1" src="<%= thumbnailURL %>" title="<%= fileEntry.getDescription() %>" />
		</aui:a>
	</td>
</tr>

<c:if test="<%= (custom1MaxHeight > 0) || (custom1MaxWidth > 0) || (custom2MaxHeight > 0) || (custom2MaxWidth > 0) %>">
	<tr>
		<td>
			<aui:a href='<%= largeURL %>' target="_blank">
				<liferay-ui:message key="original" />
			</aui:a>

			<c:if test="<%= (custom1MaxHeight > 0) || (custom1MaxWidth > 0) %>">
				|

				<aui:a href='<%= custom1URL %>' target="_blank">
					<liferay-ui:message key="size" /> 1
				</aui:a>
			</c:if>

			<c:if test="<%= (custom2MaxHeight > 0) || (custom2MaxWidth > 0) %>">
				|

				<aui:a href='<%= custom2URL %>' target="_blank">
					<liferay-ui:message key="size" /> 2
				</aui:a>
			</c:if>
		</td>
	</tr>
</c:if>

</table>