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

String randomNamespace = PortalUtil.generateRandomKey(request, "portlet_search_facets_range") + StringPool.UNDERLINE;

String fieldParam = ParamUtil.getString(request, facet.getFieldName());

JSONObject dataJSONObject = facetConfiguration.getData();

int frequencyThreshold = 0;

if (dataJSONObject.has("frequencyThreshold")) {
	frequencyThreshold = dataJSONObject.getInt("frequencyThreshold");
}

JSONArray rangesJSONArray = null;

if (dataJSONObject.has("ranges")) {
	rangesJSONArray = dataJSONObject.getJSONArray("ranges");
}

FacetCollector facetCollector = facet.getFacetCollector();
List<TermCollector> termCollectors = facetCollector.getTermCollectors();
%>

<c:if test="<%= !termCollectors.isEmpty() %>">
	<div class="<%= cssClass %>" id='<%= randomNamespace + "facet" %>'>
		<aui:input name="<%= facet.getFieldName() %>" type="hidden" value="<%= fieldParam %>" />

		<%
		String rangeNavigation = _buildRangeNavigation(themeDisplay, fieldParam, frequencyThreshold, rangesJSONArray, facetCollector, termCollectors);

		if (Validator.isNotNull(rangeNavigation)) {
		%>

			<aui:field-wrapper cssClass='<%= randomNamespace + "range range" %>' label="" name="<%= facet.getFieldName() %>">
				<%= rangeNavigation %>
			</aui:field-wrapper>

		<%
		}
		%>

		<liferay-ui:message key="<%= panelLabel %>" />: <aui:a href='<%= "javascript:" + renderResponse.getNamespace() + facet.getFieldName() + "clearFacet();" %>'><liferay-ui:message key="clear" /></aui:a>

		<aui:script position="inline" use="aui-base">
			var container = A.one('.portlet-search .menu .search-range .<%= randomNamespace %>range');

			if (container) {
				container.delegate(
					'click',
					function(event) {
						var term = event.currentTarget;

						var wasSelfSelected = false;

						var field = document.<portlet:namespace />fm['<portlet:namespace /><%= facet.getFieldName() %>'];

						var currentTerms = A.all('.portlet-search .menu .search-range .<%= randomNamespace %>range .entry.current-term a');

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
private String _buildRangeNavigation(ThemeDisplay themeDisplay, String selectedTerm, long frequencyThreshold, JSONArray rangesJSONArray, FacetCollector facetCollector, List<TermCollector> termCollectors) throws Exception {
	StringBundler sb = new StringBundler();

	sb.append("<ul class=\"range\">");

	sb.append("<li class=\"entry default");

	if (Validator.isNull(selectedTerm)) {
		sb.append(" current-term");
	}

	sb.append("\"><a href=\"#\" data-value=\"\">");
	sb.append(LanguageUtil.get(themeDisplay.getLocale(), "any-range"));
	sb.append("</a></li>");

	for (int i = 0; i < rangesJSONArray.length(); i++) {
		JSONObject rangeJSONObject = rangesJSONArray.getJSONObject(i);

		String label = rangeJSONObject.getString("label");
		String range = rangeJSONObject.getString("range");

		TermCollector termCollector = facetCollector.getTermCollector(range);

		int frequency = 0;

		if (termCollector != null) {
			frequency = termCollector.getFrequency();
		}

		if (frequency < frequencyThreshold) {
			continue;
		}

		sb.append("<li class=\"entry");

		if (range.equals(selectedTerm)) {
			sb.append(" current-term");
		}

		sb.append("\"><a href=\"#\" data-value=\"");
		sb.append(HtmlUtil.escapeAttribute(range));
		sb.append("\">");
		sb.append(LanguageUtil.get(themeDisplay.getLocale(), label));
		sb.append("</a> <span class=\"frequency\">(");
		sb.append(frequency);
		sb.append(")</span></li>");
	}

	sb.append("</ul>");

	return sb.toString();
}
%>