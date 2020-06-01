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

<%@ include file="/asset_categories_navigation/init.jsp" %>

<%
AssetCategoriesNavigationDisplayContext assetCategoriesNavigationDisplayContext = new AssetCategoriesNavigationDisplayContext(request, renderResponse);

boolean hidePortletWhenEmpty = GetterUtil.getBoolean((String)request.getAttribute("liferay-asset:asset-tags-navigation:hidePortletWhenEmpty"));
%>

<liferay-ui:panel-container
	cssClass="taglib-asset-categories-navigation"
	extended="<%= true %>"
	id='<%= namespace + "taglibAssetCategoriesNavigationPanel" %>'
	persistState="<%= true %>"
>

	<%
	for (AssetVocabulary vocabulary : assetCategoriesNavigationDisplayContext.getVocabularies()) {
		String vocabularyNavigation = assetCategoriesNavigationDisplayContext.buildVocabularyNavigation(vocabulary);

		if (Validator.isNotNull(vocabularyNavigation)) {
			hidePortletWhenEmpty = false;
	%>

			<liferay-ui:panel
				collapsible="<%= false %>"
				extended="<%= true %>"
				markupView="lexicon"
				persistState="<%= true %>"
				title="<%= HtmlUtil.escape(vocabulary.getUnambiguousTitle(assetCategoriesNavigationDisplayContext.getVocabularies(), themeDisplay.getSiteGroupId(), themeDisplay.getLocale())) %>"
			>
				<%= vocabularyNavigation %>
			</liferay-ui:panel>

	<%
		}
	}
	%>

</liferay-ui:panel-container>

<%
if (hidePortletWhenEmpty) {
	renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.TRUE);
%>

	<div class="alert alert-info">
		<liferay-ui:message key="there-are-no-categories" />
	</div>

<%
}

if (assetCategoriesNavigationDisplayContext.getCategoryId() > 0) {
	AssetCategoryUtil.addPortletBreadcrumbEntries(assetCategoriesNavigationDisplayContext.getCategoryId(), request, renderResponse.createRenderURL(), false);
}
%>

<aui:script use="aui-tree-view">
	var treeViews = A.all(
		'#<%= namespace %>taglibAssetCategoriesNavigationPanel .lfr-asset-category-list-container'
	);

	treeViews.each(function (item, index, collection) {
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

			selectedChild.eachParent(function (node) {
				if (node instanceof A.TreeNode) {
					node.expand();
				}
			});
		}
	});
</aui:script>