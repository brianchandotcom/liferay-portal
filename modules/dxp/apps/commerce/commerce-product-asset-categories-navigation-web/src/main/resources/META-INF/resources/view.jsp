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
String vocabularyNavigation = cpAssetCategoriesNavigationDisplayContext.getVocabularyNavigation(themeDisplay);
List<AssetCategory> assetCategories = cpAssetCategoriesNavigationDisplayContext.getAssetCategories();

Map<String, Object> contextObjects = new HashMap<>();

contextObjects.put("cpAssetCategoriesNavigationDisplayContext", cpAssetCategoriesNavigationDisplayContext);

String vocabularyTitle = StringPool.BLANK;

if (assetVocabulary != null) {
	vocabularyTitle = assetVocabulary.getTitle(locale);
}
%>

<liferay-ddm:template-renderer
	className="<%= CPAssetCategoriesNavigationPortlet.class.getName() %>"
	contextObjects="<%= contextObjects %>"
	displayStyle="<%= cpAssetCategoriesNavigationDisplayContext.getDisplayStyle() %>"
	displayStyleGroupId="<%= cpAssetCategoriesNavigationDisplayContext.getDisplayStyleGroupId() %>"
	entries="<%= assetCategories %>"
>
	<liferay-ui:panel-container
		cssClass="taglib-asset-categories-navigation"
		extended="<%= true %>"
		id='<%= renderResponse.getNamespace() + "taglibAssetCategoriesNavigationPanel" %>'
		persistState="<%= true %>"
	>
		<liferay-ui:panel
			collapsible="<%= false %>"
			extended="<%= true %>"
			markupView="lexicon"
			persistState="<%= true %>"
			title="<%= HtmlUtil.escape(vocabularyTitle) %>"
		>
			<%= vocabularyNavigation %>
		</liferay-ui:panel>
	</liferay-ui:panel-container>

	<aui:script use="aui-tree-view">
		var treeViews = A.all('#<portlet:namespace />taglibAssetCategoriesNavigationPanel .lfr-asset-category-list-container');

		treeViews.each(
			function(item, index, collection) {
				var assetCategoryList = item.one('.lfr-asset-category-list');

				var treeView = new A.TreeView(
					{
						boundingBox: item,
						contentBox: assetCategoryList,
						type: 'normal'
					}
				).render();

				var selected = assetCategoryList.one('.tree-node .tag-selected');

				if (selected) {
					var selectedChild = treeView.getNodeByChild(selected);

					selectedChild.expand();

					selectedChild.eachParent(
						function(node) {
							if (node instanceof A.TreeNode) {
								node.expand();
							}
						}
					);
				}
			}
		);
	</aui:script>
</liferay-ddm:template-renderer>