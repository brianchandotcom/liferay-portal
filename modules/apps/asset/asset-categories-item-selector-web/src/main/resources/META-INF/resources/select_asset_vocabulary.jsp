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
SelectAssetVocabularyDisplayContext selectAssetVocabularyDisplayContext = (SelectAssetVocabularyDisplayContext)request.getAttribute(AssetCategoryItemSelectorWebKeys.SELECT_ASSET_VOCABULARY_DISPLAY_CONTEXT);
%>

<c:choose>
	<c:when test="<%= selectAssetVocabularyDisplayContext.getAssetCategoryTreeNodeId() >= 0 %>">
		<liferay-util:include page="/select_asset_category_tree_node.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:otherwise>
		<div class="container-fluid container-fluid-max-xl p-4">
			<div class="alert alert-info">
				<liferay-ui:message key="select-the-vocabulary-or-category-to-be-displayed" />
			</div>

			<div class="align-items-center d-flex justify-content-between">
				<liferay-site-navigation:breadcrumb
					breadcrumbEntries="<%= selectAssetVocabularyDisplayContext.getBreadcrumbEntries() %>"
				/>

				<clay:button
					cssClass="asset-category-tree-node-selector"
					disabled="<%= true %>"
					displayType="primary"
					label='<%= LanguageUtil.get(resourceBundle, "select-this-level") %>'
					small="<%= true %>"
				/>
			</div>

			<liferay-ui:search-container
				cssClass="table-hover"
				searchContainer="<%= selectAssetVocabularyDisplayContext.getAssetVocabularySearchContainer() %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.asset.kernel.model.AssetVocabulary"
					keyProperty="assetVocabularyId"
					modelVar="assetVocabulary"
				>
					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand"
						name="name"
					>
						<clay:sticker
							cssClass="bg-light mr-3"
							displayType="light"
							icon="vocabulary"
						/>

						<a href="<%= selectAssetVocabularyDisplayContext.getAssetVocabularyURL(assetVocabulary.getVocabularyId()) %>">
							<b><%= HtmlUtil.escape(selectAssetVocabularyDisplayContext.getAssetVocabularyTitle(assetVocabulary)) %></b>
						</a>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-minw-300"
						name="number-of-items"
					>
						<%= assetVocabulary.getCategoriesCount() %>
					</liferay-ui:search-container-column-text>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator
					markupView="lexicon"
					searchResultCssClass="table table-autofit"
				/>
			</liferay-ui:search-container>
		</div>
	</c:otherwise>
</c:choose>