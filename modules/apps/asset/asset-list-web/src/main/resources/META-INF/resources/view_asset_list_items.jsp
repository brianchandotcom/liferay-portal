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
AssetListItemsDisplayContext assetListItemsDisplayContext = (AssetListItemsDisplayContext)request.getAttribute(AssetListWebKeys.ASSET_LIST_ITEMS_DISPLAY_CONTEXT);
ListItemsActionDropdownItems listItemsActionDropdownItems = (ListItemsActionDropdownItems)request.getAttribute(AssetListWebKeys.LIST_ITEMS_ACTION_DROPDOWN_ITEMS);
%>

<clay:container-fluid
	cssClass="container-view"
>
	<liferay-ui:search-container
		id="assetEntries"
		searchContainer="<%= assetListItemsDisplayContext.getAssetListContentSearchContainer() %>"
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
			>

				<%
				String displayPageURL = StringPool.BLANK;

				if (assetListItemsDisplayContext.isShowActions()) {
					displayPageURL = listItemsActionDropdownItems.getViewDisplayPageURL(AssetEntry.class.getName(), assetEntry);
				}
				%>

				<c:choose>
					<c:when test="<%= Validator.isNull(displayPageURL) %>">
						<%= HtmlUtil.escape(assetRenderer.getTitle(locale)) %>
					</c:when>
					<c:otherwise>
						<aui:a href="<%= displayPageURL %>" target="_top">
							<%= HtmlUtil.escape(assetRenderer.getTitle(locale)) %>
						</aui:a>
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-column-text>

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

			<c:if test="<%= assetListItemsDisplayContext.isShowActions() %>">
				<liferay-ui:search-container-column-text>
					<clay:dropdown-actions
						aria-label='<%= LanguageUtil.get(request, "show-actions") %>'
						dropdownItems="<%= listItemsActionDropdownItems.getActionDropdownItems(AssetEntry.class.getName(), assetEntry) %>"
						propsTransformer="js/ListItemsDropdownPropsTransformer"
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
	module="js/TopLinkEventHandler.es"
/>