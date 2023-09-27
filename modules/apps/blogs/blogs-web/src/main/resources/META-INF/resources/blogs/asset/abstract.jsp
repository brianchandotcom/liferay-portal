<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
BlogsEntry entry = (BlogsEntry)request.getAttribute(WebKeys.BLOGS_ENTRY);
%>

<div class="asset-summary">
	<liferay-util:include page="/blogs/entry_cover_image_caption.jsp" servletContext="<%= application %>">
		<liferay-util:param name="coverImageCaption" value="<%= entry.getCoverImageCaption() %>" />
		<liferay-util:param name="coverImageURL" value="<%= entry.getCoverImageURL(themeDisplay) %>" />
	</liferay-util:include>

	<%
	AssetRenderer<?> assetRenderer = (AssetRenderer<?>)request.getAttribute(WebKeys.ASSET_RENDERER);
	%>

	<%= HtmlUtil.stripHtml(assetRenderer.getSummary(renderRequest, renderResponse)) %>
</div>