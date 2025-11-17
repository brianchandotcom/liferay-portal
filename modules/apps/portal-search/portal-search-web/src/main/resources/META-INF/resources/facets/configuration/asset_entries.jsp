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

// Left list

List<KeyValuePair> leftList = new ArrayList<KeyValuePair>();

for (AssetRendererFactory<?> assetRendererFactory : assetEntriesSearchFacet.getAssetRendererFactories(company.getCompanyId())) {
	String className = assetRendererFactory.getClassName();

	if (assetRendererFactory.isSearchable() && !ArrayUtil.contains(assetTypes, className)) {
		leftList.add(new KeyValuePair(className, ResourceActionsUtil.getModelResource(locale, className)));
	}
}

// Right list

List<KeyValuePair> rightList = new ArrayList<KeyValuePair>();

if (dataJSONObject.has("values")) {
	JSONArray valuesJSONArray = dataJSONObject.getJSONArray("values");

	assetTypes = new String[valuesJSONArray.length()];

	for (int i = 0; i < valuesJSONArray.length(); i++) {
		assetTypes[i] = valuesJSONArray.getString(i);

		rightList.add(new KeyValuePair(assetTypes[i], ResourceActionsUtil.getModelResource(locale, assetTypes[i])));
	}
}
%>

<aui:input label="frequency-threshold" name='<%= assetEntriesSearchFacet.getClassName() + "frequencyThreshold" %>' value="<%= frequencyThreshold %>" />

<aui:input name='<%= assetEntriesSearchFacet.getClassName() + "assetTypes" %>' type="hidden" />

<liferay-ui:input-move-boxes
	leftBoxName="availableAssetTypes"
	leftList="<%= leftList %>"
	leftTitle="available"
	rightBoxName="currentAssetTypes"
	rightList="<%= rightList %>"
	rightTitle="in-use"
/>

<aui:script>
	function <portlet:namespace />saveConfiguration() {
		var form = document.<portlet:namespace />fm;

		var currentAssetTypes = Liferay.Util.getFormElement(
			form,
			'currentAssetTypes'
		);

		var data = {};

		if (currentAssetTypes) {
			data['<%= assetEntriesSearchFacet.getClassName() + "assetTypes" %>'] =
				Liferay.Util.getSelectedOptionValues(currentAssetTypes);
		}

		Liferay.Util.postForm(form, {data: data});
	}
</aui:script>