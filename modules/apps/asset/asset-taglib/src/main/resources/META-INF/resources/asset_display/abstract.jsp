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

<%@ include file="/asset_display/init.jsp" %>

<%
AssetRenderer<?> assetRenderer = (AssetRenderer<?>)request.getAttribute(WebKeys.ASSET_RENDERER);
int abstractLength = GetterUtil.getInteger(request.getAttribute(WebKeys.ASSET_ENTRY_ABSTRACT_LENGTH));
%>

<div class="asset-summary">

	<%
	String imagePreviewURL = assetRenderer.getURLImagePreview(renderRequest);
	%>

	<c:if test="<%= Validator.isNotNull(imagePreviewURL) %>">
		<div class="aspect-ratio aspect-ratio-8-to-3 aspect-ratio-bg-cover cover-image mb-4" style="background-image: url(<%= imagePreviewURL %>)"></div>
	</c:if>

	<%
	String summary = StringUtil.shorten(assetRenderer.getSummary(renderRequest, renderResponse), abstractLength);
	%>

	<%= HtmlUtil.replaceNewLine(HtmlUtil.escape(summary)) %>
</div>