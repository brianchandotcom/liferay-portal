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

<%
AssetListContentDisplayContext assetListContentDisplayContext = (AssetListContentDisplayContext)request.getAttribute(AssetListWebKeys.ASSET_LIST_CONTENT_DISPLAY_CONTEXT);

AssetListContentActionDropdownItems assetListContentActionDropdownItems = (AssetListContentActionDropdownItems)request.getAttribute(AssetListWebKeys.ASSET_LIST_CONTENT_ACTION_DROPDOWN_ITEMS);
%>

<clay:container-fluid
	cssClass="container-view"
>
	<liferay-ui:search-container
		id="assetEntries"
		searchContainer="<%= assetListContentDisplayContext.getAssetListContentSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.asset.kernel.model.AssetEntry"
			keyProperty="entryId"
			modelVar="assetEntry"
		>

			<%
			AssetRenderer<?> assetRenderer = assetEntry.getAssetRenderer();

			AssetRendererFactory<?> assetRendererFactory = assetRenderer.getAssetRendererFactory();
			%>

			<liferay-ui:search-container-column-text
				name="title"
				value="<%= HtmlUtil.escape(assetRenderer.getTitle(locale)) %>"
			/>

			<liferay-ui:search-container-column-text
				name="type"
				value="<%= assetRendererFactory.getTypeName(locale) %>"
			/>

			<liferay-ui:search-container-column-text
				name="author"
				value="<%= assetEntry.getUserName() %>"
			/>

			<liferay-ui:search-container-column-date
				name="modified-date"
				value="<%= assetEntry.getModifiedDate() %>"
			/>

			<liferay-ui:search-container-column-date
				name="create-date"
				value="<%= assetEntry.getCreateDate() %>"
			/>

			<c:if test="<%= assetListContentDisplayContext.isShowActions() %>">
				<liferay-ui:search-container-column-text>
					<clay:dropdown-actions
						defaultEventHandler="<%= AssetListWebKeys.ASSET_LIST_CONTENT_DROPDOWN_DEFAULT_EVENT_HANDLER %>"
						dropdownItems="<%= assetListContentActionDropdownItems.getActionDropdownItems(assetEntry) %>"
					/>
				</liferay-ui:search-container-column-text>
			</c:if>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</clay:container-fluid>

<liferay-frontend:component
	componentId="<%= AssetListWebKeys.ASSET_LIST_CONTENT_DROPDOWN_DEFAULT_EVENT_HANDLER %>"
	module="js/AssetListContentDropdownDefaultEventHandler.es"
/>