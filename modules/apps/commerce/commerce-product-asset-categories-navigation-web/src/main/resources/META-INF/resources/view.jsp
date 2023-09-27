<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String vocabularyNavigation = cpAssetCategoriesNavigationDisplayContext.getVocabularyNavigation(themeDisplay);
List<AssetCategory> assetCategories = cpAssetCategoriesNavigationDisplayContext.getAssetCategories();

Map<String, Object> contextObjects = HashMapBuilder.<String, Object>put(
	"cpAssetCategoriesNavigationDisplayContext", cpAssetCategoriesNavigationDisplayContext
).build();

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
		id='<%= liferayPortletResponse.getNamespace() + "taglibAssetCategoriesNavigationPanel" %>'
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
		var treeViews = A.all(
			'#<portlet:namespace />taglibAssetCategoriesNavigationPanel .lfr-asset-category-list-container'
		);

		treeViews.each((item, index, collection) => {
			var assetCategoryList = item.one('.lfr-asset-category-list');

			var treeView = new A.TreeView({
				boundingBox: item,
				contentBox: assetCategoryList,
				type: 'normal',
			}).render();

			var selected = assetCategoryList.one('.tree-node .tag-selected');

			if (selected) {
				var selectedChild = treeView.getNodeByChild(selected);

				selectedChild.expand();

				selectedChild.eachParent((node) => {
					if (node instanceof A.TreeNode) {
						node.expand();
					}
				});
			}
		});
	</aui:script>
</liferay-ddm:template-renderer>