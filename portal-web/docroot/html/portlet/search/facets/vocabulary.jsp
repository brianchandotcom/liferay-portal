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
Facet facet = (Facet)request.getAttribute("view.jsp-facet");

FacetConfiguration facetConfiguration = facet.getFacetConfiguration();

String panelLabel = facetConfiguration.getLabel();
String facetDisplayStyle = facetConfiguration.getDisplayStyle();
String cssClass = "search-facet search-".concat(facetDisplayStyle);

String randomNamespace = PortalUtil.generateRandomKey(request, "portlet_search_facets_vocabulary") + StringPool.UNDERLINE;

String[] assetCategoryIds = StringUtil.split(ParamUtil.getString(request, facet.getFieldName()));

JSONObject dataJSONObject = facetConfiguration.getData();

boolean matchByName = false;

if (dataJSONObject.has("matchByName")) {
	matchByName = dataJSONObject.getBoolean("matchByName");
}

long vocabularyId = 0;

if (dataJSONObject.has("vocabularyId")) {
	vocabularyId = dataJSONObject.getLong("vocabularyId");
}

List<AssetVocabulary> assetVocabularies = new ArrayList<AssetVocabulary>();

if (vocabularyId > 0) {
	assetVocabularies.add(AssetVocabularyServiceUtil.getVocabulary(vocabularyId));
}
else {
	assetVocabularies = AssetVocabularyServiceUtil.getGroupsVocabularies(new long[] {themeDisplay.getScopeGroupId(), themeDisplay.getParentGroupId()});
}

FacetCollector facetCollector = facet.getFacetCollector();
%>

<c:if test="<%= !assetVocabularies.isEmpty() %>">
	<div class="<%= cssClass %>" id='<%= randomNamespace + "facet" %>'>
		<aui:input name="<%= facet.getFieldName() %>" type="hidden" value="<%= StringUtil.merge(assetCategoryIds) %>" />

		<aui:field-wrapper cssClass='<%= randomNamespace + "vocabulary vocabulary" %>' label="" name="<%= facet.getFieldName() %>">

			<%
			for (AssetVocabulary assetVocabulary : assetVocabularies) {
				String name = HtmlUtil.escape(assetVocabulary.getName());

				if (vocabularyId > 0) {
					name = StringPool.BLANK;
				}

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
			var container = A.one('.portlet-search .menu .search-vocabulary .<%= randomNamespace %>vocabulary');

			if (container) {
				container.delegate(
					'click',
					function(event) {
						var term = event.currentTarget;

						var wasSelfSelected = false;

						var field = document.<portlet:namespace />fm['<portlet:namespace /><%= facet.getFieldName() %>'];

						var currentTerms = A.all('.portlet-search .menu .search-vocabulary .<%= randomNamespace %>vocabulary .entry.current-term a');

						if (currentTerms) {
							currentTerms.each(
								function(item, index, collection) {
									item.ancestor('.entry').removeClass('current-term');

									if (item == term) {
										wasSelfSelected = true;
									}
								}
							);

							field.value = '';
						}

						if (!wasSelfSelected) {
							term.ancestor('.entry').addClass('current-term');

							field.value = term.attr('data-value');
						}

						submitForm(document.<portlet:namespace />fm);
					},
					'.entry a'
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

		sb.append("<li class=\"entry");

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

	sb.append("<div class=\"search-vocabulary-list-container\"><ul class=\"search-vocabulary-list\">");

	_buildCategoriesNavigation(assetCategories, assetCategoryIds, matchByName, sb, facetCollector);

	sb.append("</ul></div>");

	return sb.toString();
}
%>