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

String randomNamespace = PortalUtil.generateRandomKey(request, "portlet_search_facets_asset_entries") + StringPool.UNDERLINE;

String fieldParam = ParamUtil.getString(request, facet.getFieldName());

JSONObject dataJSONObject = facetConfiguration.getData();

int frequencyThreshold = 0;

if (dataJSONObject.has("frequencyThreshold")) {
	frequencyThreshold = dataJSONObject.getInt("frequencyThreshold");
}

String[] values = new String[0];

if (dataJSONObject.has("values")) {
	JSONArray valuesJSONArray = dataJSONObject.getJSONArray("values");

	values = new String[valuesJSONArray.length()];

	for (int i = 0; i < valuesJSONArray.length(); i++) {
		values[i] = valuesJSONArray.getString(i);
	}
}

FacetCollector facetCollector = facet.getFacetCollector();
List<TermCollector> termCollectors = facetCollector.getTermCollectors();
%>

<div class="<%= cssClass %>" id='<%= randomNamespace + "facet" %>'>
	<aui:input name="<%= facet.getFieldName() %>" type="hidden" value="<%= fieldParam %>" />

	<%
	String assetEntriesNavigation = _buildAssetEntriesNavigation(renderRequest, themeDisplay, fieldParam, frequencyThreshold, values, facetCollector, termCollectors);

	if (Validator.isNotNull(assetEntriesNavigation)) {
	%>

		<aui:field-wrapper cssClass="asset_entries" label="" name="<%= fieldParam %>">
			<%= assetEntriesNavigation %>
		</aui:field-wrapper>

	<%
	}
	%>

	<aui:script position="inline" use="aui-base">
		var container = A.one('.portlet-search .menu .search-asset_entries .asset_entries');

		if (container) {
			container.delegate(
				'click',
				function(event) {
					var term = event.currentTarget;

					var wasSelfSelected = false;

					var field = document.<portlet:namespace />fm['<portlet:namespace /><%= facet.getFieldName() %>'];

					var currentTerms = A.all('.portlet-search .menu .search-asset_entries .asset_entries .entry.current-term a');

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

<%!
private String _buildAssetEntriesNavigation(RenderRequest renderRequest, ThemeDisplay themeDisplay, String selectedTerm, long frequencyThreshold, String[] values, FacetCollector facetCollector, List<TermCollector> termCollectors) throws Exception {
	List<String> assetTypes = new ArrayList<String>();

	for (String className : values) {
		if (!assetTypes.contains(className)) {
			if (ArrayUtil.contains(values, className)) {
				assetTypes.add(className);
			}
		}
	}

	assetTypes = ListUtil.sort(assetTypes, new ModelResourceComparator(themeDisplay.getLocale()));

	StringBundler sb = new StringBundler();

	sb.append("<ul class=\"asset-type\">");

	sb.append("<li class=\"entry default");

	if (Validator.isNull(selectedTerm)) {
		sb.append(" current-term");
	}

	sb.append("\"><a href=\"#\" data-value=\"\"><img alt=\"\" src=\"");
	sb.append(themeDisplay.getPathThemeImages());
	sb.append("/common/search.png\" /> ");

	sb.append(LanguageUtil.get(themeDisplay.getLocale(), "everything"));
	sb.append("</a></li>");

	for (int i = 0; i < assetTypes.size(); i++) {
		String assetType = assetTypes.get(i);

		TermCollector termCollector = facetCollector.getTermCollector(assetType);

		int frequency = 0;

		if (termCollector != null) {
			frequency = termCollector.getFrequency();
		}

		if (frequency < frequencyThreshold) {
			continue;
		}

		sb.append("<li class=\"entry");

		if (assetType.equals(selectedTerm)) {
			sb.append(" current-term");
		}

		sb.append("\"><a href=\"#\" data-value=\"");
		sb.append(HtmlUtil.escapeAttribute(assetType));
		sb.append("\">");

		AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(assetType);

		if (assetRendererFactory != null) {
			sb.append("<img alt=\"\" src=\"");
			sb.append(assetRendererFactory.getIconPath(renderRequest));
			sb.append("\" /> ");
		}

		sb.append(LanguageUtil.get(themeDisplay.getLocale(), "model.resource." + assetType));
		sb.append("</a> <span class=\"frequency\">(");
		sb.append(frequency);
		sb.append(")</span></li>");
	}

	sb.append("</ul>");

	return sb.toString();
}
%>