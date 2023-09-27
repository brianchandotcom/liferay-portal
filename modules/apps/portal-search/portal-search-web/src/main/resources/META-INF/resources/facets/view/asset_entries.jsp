<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/facets/init.jsp" %>

<%
int frequencyThreshold = dataJSONObject.getInt("frequencyThreshold");
boolean showAssetCount = dataJSONObject.getBoolean("showAssetCount", true);

String[] values = new String[0];

if (dataJSONObject.has("values")) {
	JSONArray valuesJSONArray = dataJSONObject.getJSONArray("values");

	values = new String[valuesJSONArray.length()];

	for (int i = 0; i < valuesJSONArray.length(); i++) {
		values[i] = valuesJSONArray.getString(i);
	}
}

AssetEntriesSearchFacetDisplayContextBuilder assetEntriesSearchFacetDisplayContextBuilder = new AssetEntriesSearchFacetDisplayContextBuilder(renderRequest);

assetEntriesSearchFacetDisplayContextBuilder.setClassNames(values);
assetEntriesSearchFacetDisplayContextBuilder.setFacet(facet);
assetEntriesSearchFacetDisplayContextBuilder.setFrequenciesVisible(showAssetCount);
assetEntriesSearchFacetDisplayContextBuilder.setFrequencyThreshold(frequencyThreshold);
assetEntriesSearchFacetDisplayContextBuilder.setLocale(locale);
assetEntriesSearchFacetDisplayContextBuilder.setParameterName(facet.getFieldId());
assetEntriesSearchFacetDisplayContextBuilder.setParameterValue(fieldParam);

AssetEntriesSearchFacetDisplayContext assetEntriesSearchFacetDisplayContext = assetEntriesSearchFacetDisplayContextBuilder.build();
%>

<div class="panel panel-secondary">
	<div class="panel-heading">
		<div class="panel-title">
			<liferay-ui:message key="asset-entries" />
		</div>
	</div>

	<div class="panel-body">
		<div class="<%= cssClass %>" data-facetFieldName="<%= HtmlUtil.escapeAttribute(facet.getFieldId()) %>" id="<%= randomNamespace %>facet">
			<aui:input autocomplete="off" name="<%= HtmlUtil.escapeAttribute(facet.getFieldId()) %>" type="hidden" value="<%= fieldParam %>" />

			<ul class="asset-type list-unstyled">
				<li class="default facet-value">
					<a class="<%= assetEntriesSearchFacetDisplayContext.isNothingSelected() ? "facet-term-selected" : "facet-term-unselected" %>" data-value="" href="javascript:void(0);"><liferay-ui:message key="<%= HtmlUtil.escape(facetConfiguration.getLabel()) %>" /></a>
				</li>

				<%
				for (BucketDisplayContext bucketDisplayContext : assetEntriesSearchFacetDisplayContext.getBucketDisplayContexts()) {
				%>

					<li class="facet-value">
						<a class="<%= bucketDisplayContext.isSelected() ? "facet-term-selected" : "facet-term-unselected" %>" data-value="<%= HtmlUtil.escapeAttribute(bucketDisplayContext.getFilterValue()) %>" href="javascript:void(0);">
							<%= bucketDisplayContext.getBucketText() %>

							<c:if test="<%= bucketDisplayContext.isFrequencyVisible() %>">
								<span class="frequency">(<%= bucketDisplayContext.getFrequency() %>)</span>
							</c:if>
						</a>
					</li>

				<%
				}
				%>

			</ul>
		</div>
	</div>
</div>