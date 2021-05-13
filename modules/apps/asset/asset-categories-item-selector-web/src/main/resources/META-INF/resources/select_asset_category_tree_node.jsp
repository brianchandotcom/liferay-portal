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
SelectAssetCategoryTreeNodeDisplayContext selectAssetCategoryTreeNodeDisplayContext = (SelectAssetCategoryTreeNodeDisplayContext)request.getAttribute(AssetCategoryItemSelectorWebKeys.SELECT_ASSET_CATEGORY_TREE_NODE_ITEM_SELECTOR_DISPLAY_CONTEXT);
%>

<div class="container-fluid container-fluid-max-xl p-4" id="<portlet:namespace />assetCategoryTreeNodeSelector">
	<div class="alert alert-info">
		<liferay-ui:message key="select-the-vocabulary-or-category-to-be-displayed" />
	</div>

	<div class="align-items-center d-flex justify-content-between">
		<liferay-site-navigation:breadcrumb
			breadcrumbEntries="<%= selectAssetCategoryTreeNodeDisplayContext.getBreadcrumbEntries() %>"
		/>

		<clay:button
			cssClass="asset-category-tree-node-selector"
			data-category-tree-node-id="<%= selectAssetCategoryTreeNodeDisplayContext.getAssetCategoryTreeNodeId() %>"
			data-category-tree-node-type="<%= selectAssetCategoryTreeNodeDisplayContext.getAssetCategoryTreeNodeType() %>"
			data-title="<%= selectAssetCategoryTreeNodeDisplayContext.getAssetCategoryTreeNodeName() %>"
			displayType="primary"
			label='<%= LanguageUtil.get(resourceBundle, "select-this-level") %>'
			small="<%= true %>"
		/>
	</div>

	<liferay-ui:search-container
		cssClass="table-hover"
		searchContainer="<%= selectAssetCategoryTreeNodeDisplayContext.getAssetCategorySearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.asset.kernel.model.AssetCategory"
			modelVar="assetCategory"
		>
			<liferay-ui:search-container-column-text
				colspan="<%= 2 %>"
				cssClass="table-title"
				name="name"
			>
				<clay:sticker
					cssClass="bg-light mr-3"
					displayType="light"
					icon="categories"
				/>

				<a href="<%= selectAssetCategoryTreeNodeDisplayContext.getAssetCategoryURL(assetCategory.getCategoryId()) %>">
					<b><%= HtmlUtil.escape(assetCategory.getName()) %></b>
				</a>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
			paginate="<%= false %>"
			searchResultCssClass="table table-autofit"
		/>
	</liferay-ui:search-container>
</div>

<liferay-frontend:component
	componentId="SelectEntityHandler"
	context="<%= selectAssetCategoryTreeNodeDisplayContext.getContext(liferayPortletResponse) %>"
	module="js/SelectEntityHandler"
/>