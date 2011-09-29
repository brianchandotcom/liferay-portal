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
int frequencyThreshold = dataJSONObject.getInt("frequencyThreshold");

String[] values = new String[0];

if (dataJSONObject.has("values")) {
	JSONArray valuesJSONArray = dataJSONObject.getJSONArray("values");

	values = new String[valuesJSONArray.length()];

	for (int i = 0; i < valuesJSONArray.length(); i++) {
		values[i] = valuesJSONArray.getString(i);
	}
}
%>

<div class="<%= cssClass %>" id="<%= randomNamespace %>facet">
	<aui:input name="<%= facet.getFieldName() %>" type="hidden" value="<%= fieldParam %>" />

	<aui:field-wrapper cssClass="asset-entries" label="" name="<%= fieldParam %>">
		<ul class="asset-type">
			<li class="facet-value default <%= Validator.isNull(fieldParam) ? "current-term" : StringPool.BLANK %>">
				<a href="#" data-value=""><img alt="" src="<%= themeDisplay.getPathThemeImages() %>/common/search.png" /><liferay-ui:message key="everything" /></a>
			</li>

			<%
			List<String> assetTypes = new SortedArrayList<String>(new ModelResourceComparator(locale));

			for (String className : values) {
				if (assetTypes.contains(className)) {
					continue;
				}

				if (!ArrayUtil.contains(values, className)) {
					continue;
				}

				assetTypes.add(className);
			}

			for (String assetType : assetTypes) {
				TermCollector termCollector = facetCollector.getTermCollector(assetType);

				int frequency = 0;

				if (termCollector != null) {
					frequency = termCollector.getFrequency();
				}

				if (frequencyThreshold > frequency) {
					continue;
				}

				AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(assetType);
			%>

				<li class="facet-value" <%= fieldParam.equals(termCollector.getTerm()) ? "current-term" : StringPool.BLANK %>">
					<a href="#" data-value="<%= HtmlUtil.escapeAttribute(assetType) %>"><c:if test="<%= assetRendererFactory != null %>"><img alt="" src="<%= assetRendererFactory.getIconPath(renderRequest) %>" /></c:if><%= ResourceActionsUtil.getModelResource(locale, assetType) %></a> <span class="frequency">(<%= frequency %>)</span>
				</li>

			<%
			}
			%>

		</ul>
	</aui:field-wrapper>
</div>

<aui:script position="inline" use="aui-base">
	var container = A.one('.portlet-search .menu <%= cssClassSelector %> .asset-entries');

	if (container) {
		container.delegate(
			'click',
			function(event) {
				var term = event.currentTarget;

				var wasSelfSelected = false;

				var field = document.<portlet:namespace />fm['<portlet:namespace /><%= facet.getFieldName() %>'];

				var currentTerms = A.all('.portlet-search .menu <%= cssClassSelector %> .asset-entries .facet-value.current-term a');

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