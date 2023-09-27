<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			previewURL = DLURLHelperUtil.getImagePreviewURL(fileEntry, fileVersion, themeDisplay);
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
							<svg aria-hidden="true" class="h4 lexicon-icon reference-mark">
								<use xlink:href="<%= themeDisplay.getPathThemeSpritemap() %>#<%= assetRenderer.getIconCssClass() %>" />
							</svg>
						</div>
					</div>
				</div>
			</c:otherwise>
		</c:choose>

		<%= HtmlUtil.replaceNewLine(HtmlUtil.escape(StringUtil.shorten(fileEntry.getDescription(), abstractLength))) %>
	</div>
</c:if>