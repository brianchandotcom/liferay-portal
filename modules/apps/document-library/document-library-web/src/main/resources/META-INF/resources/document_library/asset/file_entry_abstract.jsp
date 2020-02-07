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
int abstractLength = GetterUtil.getInteger(request.getAttribute(WebKeys.ASSET_ENTRY_ABSTRACT_LENGTH), AssetHelper.ASSET_ENTRY_ABSTRACT_LENGTH);
AssetRenderer<?> assetRenderer = (AssetRenderer<?>)request.getAttribute(WebKeys.ASSET_RENDERER);

FileEntry fileEntry = (FileEntry)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_ENTRY);

FileVersion fileVersion = (FileVersion)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FILE_VERSION);
%>

<c:if test="<%= fileVersion.isApproved() %>">
	<div class="asset-summary">

		<%
		String previewURL = StringPool.BLANK;

		if (Objects.equals(fileEntry.getVersion(), fileVersion.getVersion())) {
			if (ImageProcessorUtil.hasImages(fileVersion)) {
				previewURL = DLURLHelperUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, "&imagePreview=1");
			}
			else if (PDFProcessorUtil.hasImages(fileVersion)) {
				previewURL = DLURLHelperUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, "&previewFileIndex=1");
			}
			else if (VideoProcessorUtil.hasVideo(fileVersion)) {
				previewURL = DLURLHelperUtil.getPreviewURL(fileEntry, fileVersion, themeDisplay, "&videoThumbnail=1");
			}
		}
		%>

		<c:choose>
			<c:when test="<%= Validator.isNotNull(previewURL) %>">
				<div class="aspect-ratio aspect-ratio-8-to-3 aspect-ratio-bg-cover cover-image mb-4" style="background-image: url(<%= previewURL %>);"></div>
			</c:when>
			<c:otherwise>
				<div class="aspect-ratio aspect-ratio-8-to-3 bg-light mb-4">
					<div class="aspect-ratio-item-center-middle aspect-ratio-item-fluid card-type-asset-icon">
						<div class="text-secondary">
							<svg aria-hidden="true" class="lexicon-icon reference-mark user-icon-xl">
								<use xlink:href="<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg#<%= assetRenderer.getIconCssClass() %>" />
							</svg>
						</div>
					</div>
				</div>
			</c:otherwise>
		</c:choose>

		<%= HtmlUtil.replaceNewLine(HtmlUtil.escape(StringUtil.shorten(fileEntry.getDescription(), abstractLength))) %>
	</div>
</c:if>