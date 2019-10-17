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
boolean hidePortletWhenEmpty = GetterUtil.getBoolean((String)request.getAttribute("liferay-asset:asset-tags-navigation:hidePortletWhenEmpty"));
long[] vocabularyIds = (long[])request.getAttribute("liferay-asset:asset-tags-navigation:vocabularyIds");

long categoryId = ParamUtil.getLong(request, "categoryId");

List<AssetVocabulary> vocabularies = null;

if (vocabularyIds == null) {
	vocabularies = AssetVocabularyServiceUtil.getGroupVocabularies(PortalUtil.getCurrentAndAncestorSiteGroupIds(scopeGroupId));
}
else {
	vocabularies = new ArrayList<AssetVocabulary>();

	for (long vocabularyId : vocabularyIds) {
		try {
			vocabularies.add(AssetVocabularyServiceUtil.getVocabulary(vocabularyId));
		}
		catch (PortalException pe) {
		}
	}
}

PortletURL portletURL = renderResponse.createRenderURL();
%>

<liferay-ui:panel-container
	cssClass="taglib-asset-categories-navigation"
	extended="<%= true %>"
	id='<%= namespace + "taglibAssetCategoriesNavigationPanel" %>'
	persistState="<%= true %>"
>

	<%
	for (int i = 0; i < vocabularies.size(); i++) {
		AssetVocabulary vocabulary = vocabularies.get(i);

		String vocabularyNavigation = _buildVocabularyNavigation(vocabulary, categoryId, portletURL, themeDisplay);

		if (Validator.isNotNull(vocabularyNavigation)) {
			hidePortletWhenEmpty = false;
	%>

			<liferay-ui:panel
				collapsible="<%= false %>"
				extended="<%= true %>"
				markupView="lexicon"
				persistState="<%= true %>"
				title="<%= HtmlUtil.escape(vocabulary.getUnambiguousTitle(vocabularies, themeDisplay.getSiteGroupId(), themeDisplay.getLocale())) %>"
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

if (categoryId > 0) {
	AssetCategoryUtil.addPortletBreadcrumbEntries(categoryId, request, portletURL, false);
}
%>

<aui:script use="aui-tree-view">
	var treeViews = A.all(
		'#<%= namespace %>taglibAssetCategoriesNavigationPanel .lfr-asset-category-list-container'
	);

	treeViews.each(function(item, index, collection) {
		var assetCategoryList = item.one('.lfr-asset-category-list');

		var treeView = new A.TreeView({
			boundingBox: item,
			contentBox: assetCategoryList,
			type: 'normal'
		}).render();

		var selected = assetCategoryList.one('.tree-node .tag-selected');

		if (selected) {
			var selectedChild = treeView.getNodeByChild(selected);

			selectedChild.expand();

			selectedChild.eachParent(function(node) {
				if (node instanceof A.TreeNode) {
					node.expand();
				}
			});
		}
	});
</aui:script>

<%!
private void _buildCategoriesNavigation(List<AssetCategory> categories, long categoryId, PortletURL portletURL, ThemeDisplay themeDisplay, StringBundler sb) throws Exception {
	portletURL.setParameter("categoryId", StringPool.BLANK);

	String originalPortletURLString = portletURL.toString();

	for (AssetCategory category : categories) {
		List<AssetCategory> categoriesChildren = AssetCategoryServiceUtil.getChildCategories(category.getCategoryId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		sb.append("<li class=\"tree-node\"><span>");

		if (categoryId == category.getCategoryId()) {
			sb.append("<a class=\"tag-selected\" href=\"");
			sb.append(HtmlUtil.escape(originalPortletURLString));
		}
		else {
			portletURL.setParameter("categoryId", String.valueOf(category.getCategoryId()));

			sb.append("<a href=\"");
			sb.append(HtmlUtil.escape(portletURL.toString()));
		}

		sb.append("\">");
		sb.append(HtmlUtil.escape(category.getTitle(themeDisplay.getLocale())));
		sb.append("</a>");
		sb.append("</span>");

		if (!categoriesChildren.isEmpty()) {
			sb.append("<ul>");

			_buildCategoriesNavigation(categoriesChildren, categoryId, portletURL, themeDisplay, sb);

			sb.append("</ul>");
		}

		sb.append("</li>");
	}
}

private String _buildVocabularyNavigation(AssetVocabulary vocabulary, long categoryId, PortletURL portletURL, ThemeDisplay themeDisplay) throws Exception {
	List<AssetCategory> categories = AssetCategoryServiceUtil.getVocabularyRootCategories(vocabulary.getGroupId(), vocabulary.getVocabularyId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

	if (categories.isEmpty()) {
		return null;
	}

	StringBundler sb = new StringBundler();

	sb.append("<div class=\"lfr-asset-category-list-container\"><ul class=\"lfr-asset-category-list\">");

	_buildCategoriesNavigation(categories, categoryId, portletURL, themeDisplay, sb);

	sb.append("</ul></div>");

	return sb.toString();
}
%>