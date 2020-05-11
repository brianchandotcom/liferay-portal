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

<%@ include file="/blogs/info/item/renderer/init.jsp" %>

<%
AssetRenderer assetRenderer = (AssetRenderer)request.getAttribute(WebKeys.ASSET_RENDERER);
BlogsEntry entry = (BlogsEntry)request.getAttribute(WebKeys.BLOGS_ENTRY);
%>

<div class="asset-summary">
	<c:if test="<%= entry.isSmallImage() %>">
		<div class="aspect-ratio aspect-ratio-8-to-3 aspect-ratio-bg-cover cover-image mb-4" style="background-image: url(<%= entry.getSmallImageURL(themeDisplay) %>);"></div>
	</c:if>

	<%
	String summary = assetRenderer.getSummary(renderRequest, renderResponse);
	%>

	<c:choose>
		<c:when test="<%= Validator.isNull(summary) %>">

			<%
			assetRenderer.include(request, response, "abstract");
			%>

		</c:when>
		<c:otherwise>
			<%= summary %>
		</c:otherwise>
	</c:choose>
</div>