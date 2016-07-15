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

<div class="card">
	<c:choose>
		<c:when test="<%= Validator.isNotNull(assetRenderer.getThumbnailPath(renderRequest)) %>">
			<div class="aspect-ratio aspect-ratio-bg-center aspect-ratio-bg-cover" style="background-image: url('<%= assetRenderer.getThumbnailPath(renderRequest) %>')">
				<img alt="" class="sr-only" src="<%= assetRenderer.getThumbnailPath(renderRequest) %>" />
			</div>
		</c:when>
		<c:otherwise>
			<div class="aspect-ratio aspect-ratio-bg-center aspect-ratio-bg-cover icon-vertical-card-container">
				<aui:icon cssClass="icon-vertical-card-image" image="<%= assetRendererFactory.getIconCssClass() %>" markupView="lexicon" />
			</div>
		</c:otherwise>
	</c:choose>

	<div class="card-row card-row-layout-fixed card-row-padded card-row-valign-top">
		<div class="card-col-content lfr-card-details-column">
			<span class="lfr-card-title-text truncate-text">
				<%= HtmlUtil.escape(assetRenderer.getTitle(locale)) %>
			</span>
			<span class="lfr-card-subtitle-text truncate-text">
				<%= HtmlUtil.escape(assetRendererFactory.getTypeName(locale)) %>
			</span>
		</div>
	</div>
</div>