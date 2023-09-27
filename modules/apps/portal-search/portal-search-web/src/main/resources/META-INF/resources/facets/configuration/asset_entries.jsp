<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	function <portlet:namespace />saveConfiguration() {
		var form = document.<portlet:namespace />fm;

		var currentAssetTypes = Liferay.Util.getFormElement(
			form,
			'currentAssetTypes'
		);

		var data = {};

		if (currentAssetTypes) {
			data[
				'<%= assetEntriesSearchFacet.getClassName() + "assetTypes" %>'
			] = Liferay.Util.getSelectedOptionValues(currentAssetTypes);
		}

		Liferay.Util.postForm(form, {data: data});
	}
</script>