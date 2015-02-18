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

<%@ include file="/html/portlet/search/init.jsp" %>

<%
long assetEntryId = ParamUtil.getLong(request, "assetEntryId");
String type = ParamUtil.getString(request, "type");

AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByType(type);

AssetEntry assetEntry = assetRendererFactory.getAssetEntry(assetEntryId);
%>

<c:if test="<%= (assetEntry != null) && assetEntry.isVisible() %>">
	<liferay-ui:header
		localizeTitle="<%= false %>"
		title="<%= assetEntry.getTitle(locale) %>"
	/>

	<liferay-ui:asset-display
		assetEntry="<%= assetEntry %>"
	/>
</c:if>