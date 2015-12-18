<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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
%>

<div class="<%= cssClass %>" data-facetFieldName="<%= HtmlUtil.escapeAttribute(facet.getFieldId()) %>" id="<%= randomNamespace %>facet">
	<aui:input name="<%= HtmlUtil.escapeAttribute(facet.getFieldId()) %>" type="hidden" value="<%= fieldParam %>" />

	<ul class="asset-type nav nav-pills nav-stacked">
		<li class="default facet-value <%= Validator.isNull(fieldParam) ? "active" : StringPool.BLANK %>">
			<a data-value="" href="javascript:;"><aui:icon image="search" /> <liferay-ui:message key="<%= HtmlUtil.escape(facetConfiguration.getLabel()) %>" /></a>
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
		%>

			<c:if test="<%= fieldParam.equals(termCollector.getTerm()) %>">
				<aui:script use="liferay-token-list">
					Liferay.Search.tokenList.add(
						{
							clearFields: '<%= renderResponse.getNamespace() + HtmlUtil.escapeJS(facet.getFieldId()) %>',
							text: '<%= HtmlUtil.escapeJS(ResourceActionsUtil.getModelResource(locale, assetType)) %>'
						}
					);
				</aui:script>
			</c:if>

		<%
			int frequency = 0;

			if (termCollector != null) {
				frequency = termCollector.getFrequency();
			}

			if (frequencyThreshold > frequency) {
				continue;
			}

			AssetRendererFactory<?> assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(assetType);
		%>

			<li class="facet-value <%= fieldParam.equals(termCollector.getTerm()) ? "active" : StringPool.BLANK %>">
				<a data-value="<%= HtmlUtil.escapeAttribute(assetType) %>" href="javascript:;">
					<%= assetRendererFactory.getTypeName(locale) %>

					<c:if test="<%= showAssetCount %>">
						<span class="badge badge-info frequency"><%= frequency %></span>
					</c:if>
				</a>
			</li>

		<%
		}
		%>

	</ul>
</div>