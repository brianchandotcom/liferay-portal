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

<%@ include file="/shared_with_me/init.jsp" %>

<%
AssetRenderer assetRenderer = (AssetRenderer)renderRequest.getAttribute(AssetRenderer.class.getName());

AssetRendererFactory assetRendererFactory = assetRenderer.getAssetRendererFactory();

AssetEntry assetEntry = assetRendererFactory.getAssetEntry(assetRendererFactory.getClassName(), assetRenderer.getClassPK());
%>

<liferay-asset:asset-display
	assetEntry="<%= assetEntry %>"
	assetRenderer="<%= assetRenderer %>"
	assetRendererFactory="<%= assetRendererFactory %>"
	showExtraInfo="<%= true %>"
	template="<%= AssetRenderer.TEMPLATE_FULL_CONTENT %>"
/>

<c:if test="<%= assetRenderer.isCommentable() %>">
	<liferay-comment:discussion
		className="<%= assetEntry.getClassName() %>"
		classPK="<%= assetEntry.getClassPK() %>"
		formName='<%= "fm" + assetEntry.getClassPK() %>'
		ratingsEnabled="<%= false %>"
		redirect="<%= currentURL %>"
		userId="<%= assetRenderer.getUserId() %>"
	/>
</c:if>