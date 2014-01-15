<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/search/facets/init.jsp" %>

<%
String[] assetCategoryIdsOrNames = StringUtil.split(fieldParam);

long assetVocabularyId = dataJSONObject.getLong("assetVocabularyId");
boolean matchByName = dataJSONObject.getBoolean("matchByName");

List<AssetVocabulary> assetVocabularies = new ArrayList<AssetVocabulary>();

if (assetVocabularyId > 0) {
	AssetVocabulary assetVocabulary = AssetVocabularyServiceUtil.getVocabulary(assetVocabularyId);

	assetVocabularies.add(assetVocabulary);
}
else {
	assetVocabularies = AssetVocabularyServiceUtil.getGroupsVocabularies(new long[] {themeDisplay.getScopeGroupId(), themeDisplay.getSiteGroupId()});
}

if (assetVocabularies.isEmpty()) {
	return;
}
%>

<div class="asset-vocabulary <%= cssClass %>" data-facetFieldName="<%= HtmlUtil.escapeAttribute(facet.getFieldId()) %>" id="<%= randomNamespace %>facet">
	<aui:input name="<%= HtmlUtil.escapeAttribute(facet.getFieldId()) %>" type="hidden" value="<%= StringUtil.merge(assetCategoryIdsOrNames) %>" />

	<%
	for (AssetVocabulary assetVocabulary : assetVocabularies) {
		List<AssetCategory> assetCategories = AssetCategoryServiceUtil.getVocabularyRootCategories(assetVocabulary.getVocabularyId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		if (assetCategories.isEmpty()) {
			continue;
		}
	%>

		<div class="search-asset-vocabulary-list-container">
			<ul class="nav nav-pills nav-stacked search-asset-vocabulary-list">

				<%
				StringBundler sb = new StringBundler();

				String clearFields = renderResponse.getNamespace() + facet.getFieldId();

				_buildCategoriesNavigation(assetCategoryIdsOrNames, matchByName, facetCollector, assetCategories, clearFields, pageContext, locale, sb);
				%>

				<%= sb.toString() %>

			</ul>
		</div>

	<%
	}
	%>

</div>

<%!
private void _buildCategoriesNavigation(String[] assetCategoryIdsOrNames, boolean matchByName, FacetCollector facetCollector, List<AssetCategory> assetCategories, String clearFields, PageContext pageContext, Locale locale, StringBundler sb) throws Exception {
	for (AssetCategory assetCategory : assetCategories) {
		String term = String.valueOf(assetCategory.getCategoryId());

		String assetCategoryName = HtmlUtil.escape(assetCategory.getName());

		if (matchByName) {
			term = assetCategoryName;
		}

		int frequency = 0;

		TermCollector termCollector = facetCollector.getTermCollector(term);

		if (termCollector != null) {
			frequency = termCollector.getFrequency();
		}

		sb.append("<li class=\"facet-value");

		if (ArrayUtil.contains(assetCategoryIdsOrNames, term)) {
			sb.append(" active");

			ScriptTag.doTag(null, "liferay-token-list", "Liferay.Search.tokenList.add({clearFields: '" + HtmlUtil.escapeJS(clearFields) + "', text: '" + HtmlUtil.escapeJS(assetCategoryName) + "'});", null, pageContext);
		}

		sb.append("\"><a href=\"#\" data-value=\"");
		sb.append(HtmlUtil.escapeAttribute(term));
		sb.append("\">");
		sb.append(assetCategory.getTitle(locale));
		sb.append("</a> <span class=\"frequency\">(");
		sb.append(frequency);
		sb.append(")</span>");

		List<AssetCategory> childAssetCategories = AssetCategoryServiceUtil.getChildCategories(assetCategory.getCategoryId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		if (!childAssetCategories.isEmpty()) {
			sb.append("<ul>");

			_buildCategoriesNavigation(assetCategoryIdsOrNames, matchByName, facetCollector, childAssetCategories, clearFields, pageContext, locale, sb);

			sb.append("</ul>");
		}

		sb.append("</li>");
	}
}
%>