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

<%@ include file="/init.jsp" %>

<div class="container-fluid-1280 pt-3">
	<liferay-ui:search-container
		id="assetEntries"
		searchContainer="<%= assetListDisplayContext.getAssetListContentSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.asset.kernel.model.AssetEntry"
			keyProperty="entryId"
			modelVar="assetEntry"
		>

			<%
			AssetRenderer assetRenderer = assetEntry.getAssetRenderer();

			AssetRendererFactory assetRendererFactory = assetRenderer.getAssetRendererFactory();
			%>

			<liferay-ui:search-container-column-text
				name="title"
				value="<%= HtmlUtil.escape(assetRenderer.getTitle(locale)) %>"
			/>

			<liferay-ui:search-container-column-text
				name="type"
				value="<%= assetRendererFactory.getTypeName(locale) %>"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</div>

<aui:button-row>
	<aui:button type="cancel" value="close" />
</aui:button-row>