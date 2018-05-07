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

<%@ include file="/instance/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

long assetEntryId = ParamUtil.getLong(request, "assetEntryId");
String type = ParamUtil.getString(request, "type");

AssetRendererFactory<?> assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByType(type);

AssetEntry assetEntry = assetRendererFactory.getAssetEntry(assetEntryId);

long assetEntryVersionId = ParamUtil.getLong(request, "assetEntryVersionId");

AssetRenderer<?> assetRenderer = assetRendererFactory.getAssetRenderer(assetEntryVersionId, AssetRendererFactory.TYPE_LATEST);

request.setAttribute(WebKeys.WORKFLOW_ASSET_PREVIEW, Boolean.TRUE);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(assetRenderer.getTitle(locale));
%>

<c:if test="<%= assetEntry != null %>">
	<liferay-asset:asset-display
		assetEntry="<%= assetEntry %>"
		assetRenderer="<%= assetRenderer %>"
		assetRendererFactory="<%= assetRendererFactory %>"
	/>

	<%
	String viewInContextURL = assetRenderer.getURLViewInContext(liferayPortletRequest, liferayPortletResponse, null);
	%>

	<c:if test="<%= viewInContextURL != null %>">
		<div class="asset-more">
			<aui:a href="<%= viewInContextURL %>"><liferay-ui:message key="view-in-context" /> &raquo;</aui:a>
		</div>
	</c:if>
</c:if>