<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
SelectAssetCategoryTreeNodeDisplayContext selectAssetCategoryTreeNodeDisplayContext = (SelectAssetCategoryTreeNodeDisplayContext)request.getAttribute(AssetCategoryItemSelectorWebKeys.SELECT_ASSET_CATEGORY_TREE_NODE_ITEM_SELECTOR_DISPLAY_CONTEXT);
%>

<div class="container-fluid container-fluid-max-xl p-4" id="<portlet:namespace />assetCategoryTreeNodeSelector">
	<clay:alert
		displayType="info"
		message="select-the-vocabulary-or-category-to-be-displayed"
	/>

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
			label="select-this-level"
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
					displayType="dark"
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