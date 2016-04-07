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

<%@ include file="/html/taglib/ui/asset_display/init.jsp" %>

<%
AssetRenderer<?> assetRenderer = (AssetRenderer<?>)request.getAttribute(WebKeys.ASSET_RENDERER);

AssetRendererFactory assetRendererFactory = (AssetRendererFactory)request.getAttribute(WebKeys.ASSET_RENDERER_FACTORY);
%>

<c:choose>
	<c:when test="<%= Validator.isNotNull(assetRenderer.getThumbnailPath(renderRequest)) %>">
		<liferay-frontend:vertical-card
			imageUrl="<%= assetRenderer.getThumbnailPath(renderRequest) %>"
			subtitle="<%= assetRendererFactory.getTypeName(locale) %>"
			title="<%= assetRenderer.getTitle(locale) %>"
		/>
	</c:when>
	<c:otherwise>
		<liferay-frontend:icon-vertical-card
			icon="<%= assetRendererFactory.getIconCssClass() %>"
			subtitle="<%= assetRendererFactory.getTypeName(locale) %>"
			title="<%= assetRenderer.getTitle(locale) %>"
		/>
	</c:otherwise>
</c:choose>