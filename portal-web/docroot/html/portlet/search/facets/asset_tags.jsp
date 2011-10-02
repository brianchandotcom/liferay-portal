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

String displayStyle = dataJSONObject.getString("displayStyle", "cloud");
int frequencyThreshold = dataJSONObject.getInt("frequencyThreshold");
int maxTerms = dataJSONObject.getInt("maxTerms", 10);
boolean showAssetCount = dataJSONObject.getBoolean("showAssetCount", true);
%>

<div class="<%= cssClass %>" id="<%= randomNamespace %>facet">
	<aui:input name="<%= facet.getFieldName() %>" type="hidden" value="<%= fieldParam %>" />

	<aui:field-wrapper cssClass='<%= randomNamespace + "asset-tags asset-tags" %>' label="" name="assetTags">
		<ul class="<%= (showAssetCount && displayStyle.equals("cloud")) ? "tag-cloud" : "tag-list" %>">
			<li class="facet-value default <%= Validator.isNull(fieldParam) ? "current-term" : StringPool.BLANK %>">
				<a href="#" data-value=""><img alt="" src="<%= themeDisplay.getPathThemeImages() %>/common/<%= facetConfiguration.getLabel() %>.png" /><liferay-ui:message key="any" /> <liferay-ui:message key="<%= facetConfiguration.getLabel() %>" /></a>
			</li>

			<%
			int maxCount = 1;
			int minCount = 1;

			if (showAssetCount && displayStyle.equals("cloud")) {
				for (int i = 0; i < termCollectors.size(); i++) {
					if (i >= maxTerms) {
						break;
					}

					TermCollector termCollector = termCollectors.get(i);

					int frequency = termCollector.getFrequency();

					if (frequencyThreshold > frequency) {
						continue;
					}

					maxCount = Math.max(maxCount, frequency);
					minCount = Math.min(minCount, frequency);
				}
			}

			double multiplier = 1;

			if (maxCount != minCount) {
				multiplier = (double)5 / (maxCount - minCount);
			}

			for (int i = 0; i < termCollectors.size(); i++) {
				if (i >= maxTerms) {
					break;
				}

				TermCollector termCollector = termCollectors.get(i);

				int popularity = (int)(1 + ((maxCount - (maxCount - (termCollector.getFrequency() - minCount))) * multiplier));

				if (frequencyThreshold > termCollector.getFrequency()) {
					continue;
				}
			%>

				<li class="facet-value tag-popularity-"<%= popularity %> <%= fieldParam.equals(termCollector.getTerm()) ? "current-term" : StringPool.BLANK %>">
					<a href="#" data-value="<%= termCollector.getTerm() %>"><%= termCollector.getTerm() %></a>

					<c:if test="<%= showAssetCount %>">
						<span class="frequency">(<%= termCollector.getFrequency() %>)</span>
					</c:if>
				</li>

			<%
			}
			%>

		</ul>
	</aui:field-wrapper>
</div>

<aui:script position="inline" use="aui-base">
	var container = A.one('.portlet-search .menu <%= cssClassSelector %> .<%= randomNamespace %>asset-tags');

	if (container) {
		container.delegate(
			'click',
			function(event) {
				var term = event.currentTarget;

				var wasSelfSelected = false;

				var field = document.<portlet:namespace />fm['<portlet:namespace /><%= facet.getFieldName() %>'];

				var currentTerms = A.all('.portlet-search .menu <%= cssClassSelector %> .<%= randomNamespace %>asset-tags .facet-value.current-term a');

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