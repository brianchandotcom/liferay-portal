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

Folder folder = (Folder)request.getAttribute(WebKeys.DOCUMENT_LIBRARY_FOLDER);
%>

<div class="asset-summary">
	<div class="aspect-ratio aspect-ratio-8-to-3 bg-light mb-4">
		<div class="aspect-ratio-item-center-middle aspect-ratio-item-fluid card-type-asset-icon">
			<div class="text-secondary">
				<svg aria-hidden="true" class="lexicon-icon lexicon-icon-folder reference-mark user-icon-xl">
					<use xlink:href="<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg#folder" />
				</svg>
			</div>
		</div>
	</div>

	<%= HtmlUtil.replaceNewLine(HtmlUtil.escape(StringUtil.shorten(folder.getDescription(), abstractLength))) %>
</div>