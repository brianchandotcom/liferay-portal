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
String randomNamespace = PortalUtil.generateRandomKey(request, "portlet_search_facets_term_list") + StringPool.UNDERLINE;

Facet facet = (Facet)request.getAttribute("view.jsp-facet");

FacetConfiguration facetConfiguration = facet.getFacetConfiguration();

String panelLabel = facetConfiguration.getLabel();
String facetDisplayStyle = facetConfiguration.getDisplayStyle();
String cssClass = "search-facet search-".concat(facetDisplayStyle);

String fieldParam = ParamUtil.getString(request, facet.getFieldName());

JSONObject dataJSONObject = facetConfiguration.getData();

int frequencyThreshold = dataJSONObject.getInt("frequencyThreshold");
int maxTerms = dataJSONObject.getInt("maxTerms");

FacetCollector facetCollector = facet.getFacetCollector();

List<TermCollector> termCollectors = facetCollector.getTermCollectors();
%>

<c:if test="<%= !termCollectors.isEmpty() %>">
	<div class="<%= cssClass %>" id='<%= randomNamespace + "facet" %>'>
		<aui:input name="<%= facet.getFieldName() %>" type="hidden" value="<%= fieldParam %>" />

		<%
		String termListNavigation = _buildTermListNavigation(themeDisplay, fieldParam, frequencyThreshold, maxTerms, facetCollector, termCollectors);

		if (Validator.isNotNull(termListNavigation)) {
		%>

			<aui:field-wrapper cssClass='<%= randomNamespace + "term_list term_list" %>' label="" name="<%= facet.getFieldName() %>">
				<%= termListNavigation %>
			</aui:field-wrapper>

		<%
		}
		%>

		<liferay-ui:message key="<%= panelLabel %>" />: <aui:a href='<%= "javascript:" + renderResponse.getNamespace() + facet.getFieldName() + "clearFacet();" %>'><liferay-ui:message key="clear" /></aui:a>

		<aui:script position="inline" use="aui-base">
			var container = A.one('.portlet-search .menu .search-term_list .<%= randomNamespace %>term_list');

			if (container) {
				container.delegate(
					'click',
					function(event) {
						var term = event.currentTarget;

						var wasSelfSelected = false;

						var field = document.<portlet:namespace />fm['<portlet:namespace /><%= facet.getFieldName() %>'];

						var currentTerms = A.all('.portlet-search .menu .search-term_list .<%= randomNamespace %>term_list .facet-value.current-term a');

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

							field.value = term.text();
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
private String _buildTermListNavigation(ThemeDisplay themeDisplay, String selectedTerm, int frequencyThreshold, int maxTerms, FacetCollector facetCollector, List<TermCollector> termCollectors) throws Exception {
	StringBundler sb = new StringBundler();

	sb.append("<ul class=\"term-list\">");
	sb.append("<li class=\"facet-value default");

	if (Validator.isNull(selectedTerm)) {
		sb.append(" current-term");
	}

	sb.append("\"><a href=\"#\" data-value=\"\">");
	sb.append(LanguageUtil.get(themeDisplay.getLocale(), "any-term"));
	sb.append("</a></li>");

	for (int i = 0; i < termCollectors.size(); i++) {
		TermCollector termCollector = termCollectors.get(i);

		String term = termCollector.getTerm();
		int frequency = termCollector.getFrequency();

		if (((maxTerms > 0) && (i >= maxTerms)) || ((frequencyThreshold > 0) && (frequency < frequencyThreshold))) {
			break;
		}

		sb.append("<li class=\"facet-value");

		if (term.equals(selectedTerm)) {
			sb.append(" current-term");
		}

		sb.append("\"><a href=\"#\">");
		sb.append(term);
		sb.append("</a> <span class=\"frequency\">(");
		sb.append(frequency);
		sb.append(")</span></li>");
	}

	sb.append("</ul>");

	return sb.toString();
}
%>