<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/blogs/info/item/renderer/init.jsp" %>

<%
AssetRenderer<?> assetRenderer = (AssetRenderer)request.getAttribute(WebKeys.ASSET_RENDERER);
BlogsEntry entry = (BlogsEntry)request.getAttribute(WebKeys.BLOGS_ENTRY);
%>

<div class="asset-summary">
	<c:if test="<%= entry.isSmallImage() %>">
		<aui:style type="text/css">
			.blogs-abstract-background-image {
				background-image: url(<%= entry.getSmallImageURL(themeDisplay) %>);
			}
		</aui:style>

		<div class="aspect-ratio aspect-ratio-8-to-3 aspect-ratio-bg-cover blogs-abstract-background-image cover-image mb-4"></div>
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