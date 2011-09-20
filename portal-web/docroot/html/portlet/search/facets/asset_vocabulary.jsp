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

<%@ include file="/html/portlet/search/init.jsp" %>

<%
String randomNamespace = PortalUtil.generateRandomKey(request, "portlet_search_facets_asset_vocabulary") + StringPool.UNDERLINE;

Facet facet = (Facet)request.getAttribute("view.jsp-facet");

FacetConfiguration facetConfiguration = facet.getFacetConfiguration();

String panelLabel = facetConfiguration.getLabel();
String facetDisplayStyle = facetConfiguration.getDisplayStyle();
String cssClass = "search-facet search-".concat(facetDisplayStyle);

String[] assetCategoryIds = StringUtil.split(ParamUtil.getString(request, facet.getFieldName()));

JSONObject dataJSONObject = facetConfiguration.getData();

boolean matchByName = dataJSONObject.getBoolean("matchByName");
long assetVocabularyId = dataJSONObject.getLong("assetVocabularyId");

List<AssetVocabulary> assetVocabularies = new ArrayList<AssetVocabulary>();

if (assetVocabularyId > 0) {
	assetVocabularies.add(AssetVocabularyServiceUtil.getVocabulary(assetVocabularyId));
}
else {
	assetVocabularies = AssetVocabularyServiceUtil.getGroupsVocabularies(new long[] {themeDisplay.getScopeGroupId(), themeDisplay.getParentGroupId()});
}

FacetCollector facetCollector = facet.getFacetCollector();
%>

<c:if test="<%= !assetVocabularies.isEmpty() %>">
	<div class="<%= cssClass %>" id='<%= randomNamespace + "facet" %>'>
		<aui:input name="<%= facet.getFieldName() %>" type="hidden" value="<%= StringUtil.merge(assetCategoryIds) %>" />

		<aui:field-wrapper cssClass='<%= randomNamespace + "asset-vocabulary asset-vocabulary" %>' label="" name="<%= facet.getFieldName() %>">

			<%
			for (AssetVocabulary assetVocabulary : assetVocabularies) {
				String vocabularyNavigation = _buildVocabularyNavigation(assetVocabulary, assetCategoryIds, matchByName, facetCollector);
			%>

				<c:if test="<%= Validator.isNotNull(vocabularyNavigation) %>">
					<%= vocabularyNavigation %>
				</c:if>

			<%
			}
			%>

		</aui:field-wrapper>

		<liferay-ui:message key="<%= panelLabel %>" />: <aui:a href='<%= "javascript:" + renderResponse.getNamespace() + facet.getFieldName() + "clearFacet();" %>'><liferay-ui:message key="clear" /></aui:a>

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
	</div>
</c:if>

<%!
private void _buildCategoriesNavigation(List<AssetCategory> assetCategories, String[] assetCategoryIds, boolean matchByName, StringBundler sb, FacetCollector facetCollector) throws Exception {
	for (AssetCategory assetCategory : assetCategories) {
		long categoryId = assetCategory.getCategoryId();
		String name = HtmlUtil.escape(assetCategory.getName());
		String term = String.valueOf(categoryId);
		int frequency = 0;

		if (matchByName) {
			term = name;
		}

		TermCollector termCollector = facetCollector.getTermCollector(term);

		if (termCollector != null) {
			frequency = termCollector.getFrequency();
		}

		sb.append("<li class=\"facet-value");

		if (ArrayUtil.contains(assetCategoryIds, term)) {
			sb.append(" current-term");
		}

		sb.append("\"><a href=\"#\" data-value=\"");
		sb.append(HtmlUtil.escapeAttribute(term));
		sb.append("\">");
		sb.append(term);
		sb.append("</a> <span class=\"frequency\">(");
		sb.append(frequency);
		sb.append(")</span>");

		List<AssetCategory> categoriesChildren = AssetCategoryServiceUtil.getChildCategories(assetCategory.getCategoryId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		if (!categoriesChildren.isEmpty()) {
			sb.append("<ul>");

			_buildCategoriesNavigation(categoriesChildren, assetCategoryIds, matchByName, sb, facetCollector);

			sb.append("</ul>");
		}

		sb.append("</li>");
	}
}

private String _buildVocabularyNavigation(AssetVocabulary assetVocabulary, String[] assetCategoryIds, boolean matchByName, FacetCollector facetCollector) throws Exception {
	List<AssetCategory> assetCategories = AssetCategoryServiceUtil.getVocabularyRootCategories(assetVocabulary.getVocabularyId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

	if (assetCategories.isEmpty()) {
		return null;
	}

	StringBundler sb = new StringBundler();

	sb.append("<div class=\"search-asset-vocabulary-list-container\"><ul class=\"search-asset-vocabulary-list\">");

	_buildCategoriesNavigation(assetCategories, assetCategoryIds, matchByName, sb, facetCollector);

	sb.append("</ul></div>");

	return sb.toString();
}
%>