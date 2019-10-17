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

<%@ include file="/init.jsp" %>

<%
AssetEntriesSearchFacet assetEntriesSearchFacet = (AssetEntriesSearchFacet)request.getAttribute("facet_configuration.jsp-searchFacet");

JSONObject dataJSONObject = assetEntriesSearchFacet.getData();

int frequencyThreshold = dataJSONObject.getInt("frequencyThreshold");

String[] assetTypes = new String[0];

List<KeyValuePair> currentAssetTypes = new ArrayList<KeyValuePair>();

if (dataJSONObject.has("values")) {
	JSONArray valuesJSONArray = dataJSONObject.getJSONArray("values");

	assetTypes = new String[valuesJSONArray.length()];

	for (int i = 0; i < valuesJSONArray.length(); i++) {
		assetTypes[i] = valuesJSONArray.getString(i);

		currentAssetTypes.add(new KeyValuePair(assetTypes[i], ResourceActionsUtil.getModelResource(locale, assetTypes[i])));
	}
}

List<KeyValuePair> availableAssetTypes = new ArrayList<KeyValuePair>();

for (AssetRendererFactory<?> assetRendererFactory : assetEntriesSearchFacet.getAssetRendererFactories(company.getCompanyId())) {
	String className = assetRendererFactory.getClassName();

	if (assetRendererFactory.isSearchable() && !ArrayUtil.contains(assetTypes, className)) {
		availableAssetTypes.add(new KeyValuePair(className, ResourceActionsUtil.getModelResource(locale, className)));
	}
}
%>

<aui:input label="frequency-threshold" name='<%= assetEntriesSearchFacet.getClassName() + "frequencyThreshold" %>' value="<%= frequencyThreshold %>" />

<aui:input name='<%= assetEntriesSearchFacet.getClassName() + "assetTypes" %>' type="hidden" />

<liferay-ui:input-move-boxes
	leftBoxName="currentAssetTypes"
	leftList="<%= currentAssetTypes %>"
	leftTitle="current"
	rightBoxName="availableAssetTypes"
	rightList="<%= availableAssetTypes %>"
	rightTitle="available"
/>

<script>
	var form = document.<portlet:namespace />fm;

	var currentAssetTypes = Liferay.Util.getFormElement(form, 'currentAssetTypes');

	if (currentAssetTypes) {
		form.addEventListener('submit', function(event) {
			event.preventDefault();

			Liferay.Util.postForm(form, {
				data: {
					<%= assetEntriesSearchFacet.getClassName() + "assetTypes" %>: Liferay.Util.listSelect(
						currentAssetTypes
					)
				}
			});
		});
	}
</script>