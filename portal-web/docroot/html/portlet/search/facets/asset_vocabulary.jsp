<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
	assetVocabularies = AssetVocabularyServiceUtil.getGroupsVocabularies(new long[] {themeDisplay.getScopeGroupId(), themeDisplay.getParentGroupId()});
}

if (assetVocabularies.isEmpty()) {
	return;
}
%>

<div class="<%= cssClass %>" id="<%= randomNamespace %>facet">
	<aui:input name="<%= facet.getFieldName() %>" type="hidden" value="<%= StringUtil.merge(assetCategoryIdsOrNames) %>" />

	<aui:field-wrapper cssClass='<%= randomNamespace + "asset-vocabulary asset-vocabulary" %>' label="" name="<%= facet.getFieldName() %>">

		<%
		for (AssetVocabulary assetVocabulary : assetVocabularies) {
			List<AssetCategory> assetCategories = AssetCategoryServiceUtil.getVocabularyRootCategories(assetVocabulary.getVocabularyId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

			if (assetCategories.isEmpty()) {
				continue;
			}
		%>

			<div class="search-asset-vocabulary-list-container">
				<ul class="search-asset-vocabulary-list">

					<%
					StringBundler sb = new StringBundler();

					_buildCategoriesNavigation(assetCategoryIdsOrNames, matchByName, facetCollector, assetCategories, sb);
					%>

					<%= sb.toString() %>

				</ul>
			</div>

		<%
		}
		%>

	</aui:field-wrapper>

	<liferay-ui:message key="<%= facetConfiguration.getLabel() %>" />: <aui:a href='<%= "javascript:" + renderResponse.getNamespace() + facet.getFieldName() + "clearFacet();" %>'><liferay-ui:message key="clear" /></aui:a>
</div>

<aui:script position="inline" use="aui-base">
	var container = A.one('.portlet-search .menu .search-asset-vocabulary .<%= randomNamespace %>asset-vocabulary');

	if (container) {
		container.delegate(
			'click',
			function(event) {
				var term = event.currentTarget;

				var wasSelfSelected = false;

				var field = document.<portlet:namespace />fm['<portlet:namespace /><%= facet.getFieldName() %>'];

				var currentTerms = A.all('.portlet-search .menu .search-asset-vocabulary .<%= randomNamespace %>asset-vocabulary .facet-value.current-term a');

				if (currentTerms) {
					currentTerms.each(
						function(item, index, collection) {
							item.ancestor('.facet-value').removeClass('current-term');

							if (item == term) {
								wasSelfSelected = true;
							}
						}
					);

					field.value = '';
				}

				if (!wasSelfSelected) {
					term.ancestor('.facet-value').addClass('current-term');

					field.value = term.attr('data-value');
				}

				submitForm(document.<portlet:namespace />fm);
			},
			'.facet-value a'
		);
	}

	Liferay.provide(
		window,
		'<portlet:namespace /><%= facet.getFieldName() %>clearFacet',
		function() {
			document.<portlet:namespace />fm['<portlet:namespace /><%= facet.getFieldName() %>'].value = '';

			submitForm(document.<portlet:namespace />fm);
		},
		['aui-base']
	);
</aui:script>

<%!
private void _buildCategoriesNavigation(String[] assetCategoryIdsOrNames, boolean matchByName, FacetCollector facetCollector, List<AssetCategory> assetCategories, StringBundler sb) throws Exception {
	for (AssetCategory assetCategory : assetCategories) {
		String term = String.valueOf(assetCategory.getCategoryId());

		if (matchByName) {
			term = HtmlUtil.escape(assetCategory.getName());
		}

		int frequency = 0;

		TermCollector termCollector = facetCollector.getTermCollector(term);

		if (termCollector != null) {
			frequency = termCollector.getFrequency();
		}

		sb.append("<li class=\"facet-value");

		if (ArrayUtil.contains(assetCategoryIdsOrNames, term)) {
			sb.append(" current-term");
		}

		sb.append("\"><a href=\"#\" data-value=\"");
		sb.append(HtmlUtil.escapeAttribute(term));
		sb.append("\">");
		sb.append(term);
		sb.append("</a> <span class=\"frequency\">(");
		sb.append(frequency);
		sb.append(")</span>");

		List<AssetCategory> childAssetCategories = AssetCategoryServiceUtil.getChildCategories(assetCategory.getCategoryId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		if (!childAssetCategories.isEmpty()) {
			sb.append("<ul>");

			_buildCategoriesNavigation(assetCategoryIdsOrNames, matchByName, facetCollector, childAssetCategories, sb);

			sb.append("</ul>");
		}

		sb.append("</li>");
	}
}
%>