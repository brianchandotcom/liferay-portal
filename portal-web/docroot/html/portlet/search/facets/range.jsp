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
if (termCollectors.isEmpty()) {
	return;
}

int frequencyThreshold = dataJSONObject.getInt("frequencyThreshold");
JSONArray rangesJSONArray = dataJSONObject.getJSONArray("ranges");
%>

<div class="<%= cssClass %>" id="<%= randomNamespace %>facet">
	<aui:input name="<%= facet.getFieldName() %>" type="hidden" value="<%= fieldParam %>" />

	<aui:field-wrapper cssClass='<%= randomNamespace + "range range" %>' label="" name="<%= facet.getFieldName() %>">
		<ul class="range">
			<li class="facet-value default <%= Validator.isNull(fieldParam) ? "current-term" : StringPool.BLANK %>">
				<a href="#" data-value=""><liferay-ui:message key="any-range" /></a>
			</li>

			<%
			for (int i = 0; i < rangesJSONArray.length(); i++) {
				JSONObject rangeJSONObject = rangesJSONArray.getJSONObject(i);

				String label = rangeJSONObject.getString("label");
				String range = rangeJSONObject.getString("range");

				TermCollector termCollector = facetCollector.getTermCollector(range);

				int frequency = 0;

				if (termCollector != null) {
					frequency = termCollector.getFrequency();
				}

				if (frequencyThreshold > frequency) {
					continue;
				}
			%>

				<li class="facet-value" <%= fieldParam.equals(range) ? "current-term" : StringPool.BLANK %>">
					<a href="#" data-value="<%= HtmlUtil.escapeAttribute(range) %>"><liferay-ui:message key="<%= label %>" /></a> <span class="frequency">(<%= frequency %>)</span>
				</li>

			<%
			}
			%>

		</ul>
	</aui:field-wrapper>

	<liferay-ui:message key="<%= facetConfiguration.getLabel() %>" />: <aui:a href='<%= "javascript:" + renderResponse.getNamespace() + facet.getFieldName() + "clearFacet();" %>'><liferay-ui:message key="clear" /></aui:a>
</div>

<aui:script position="inline" use="aui-base">
	var container = A.one('.portlet-search .menu .search-range .<%= randomNamespace %>range');

	if (container) {
		container.delegate(
			'click',
			function(event) {
				var term = event.currentTarget;

				var wasSelfSelected = false;

				var field = document.<portlet:namespace />fm['<portlet:namespace /><%= facet.getFieldName() %>'];

				var currentTerms = A.all('.portlet-search .menu .search-range .<%= randomNamespace %>range .facet-value.current-term a');

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