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
long[] categorizableGroupIds = (long[])request.getAttribute("configuration.jsp-categorizableGroupIds");

if (categorizableGroupIds == null) {
	categorizableGroupIds = StringUtil.split(ParamUtil.getString(request, "categorizableGroupIds"), 0L);
}

String queryLogicIndexesParam = ParamUtil.getString(request, "queryLogicIndexes");

int[] queryLogicIndexes = null;

if (Validator.isNotNull(queryLogicIndexesParam)) {
	queryLogicIndexes = StringUtil.split(queryLogicIndexesParam, 0);
}
else {
	queryLogicIndexes = new int[0];

	for (int i = 0; true; i++) {
		String queryValues = PrefsParamUtil.getString(portletPreferences, request, "queryValues" + i);

		if (Validator.isNull(queryValues)) {
			break;
		}

		queryLogicIndexes = ArrayUtil.append(queryLogicIndexes, i);
	}

	if (queryLogicIndexes.length == 0) {
		queryLogicIndexes = ArrayUtil.append(queryLogicIndexes, -1);
	}
}

JSONArray rulesJSONArray = JSONFactoryUtil.createJSONArray();

for (int queryLogicIndex : queryLogicIndexes) {
	JSONObject rulesJSONObject = JSONFactoryUtil.createJSONObject();

	boolean queryAndOperator = PrefsParamUtil.getBoolean(portletPreferences, request, "queryAndOperator" + queryLogicIndex);

	rulesJSONObject.put("queryAndOperator", queryAndOperator);

	boolean queryContains = PrefsParamUtil.getBoolean(portletPreferences, request, "queryContains" + queryLogicIndex, true);

	rulesJSONObject.put("queryContains", queryContains);

	String queryValues = StringUtil.merge(portletPreferences.getValues("queryValues" + queryLogicIndex, new String[0]));
	String queryName = PrefsParamUtil.getString(portletPreferences, request, "queryName" + queryLogicIndex, "assetTags");

	if (Objects.equals(queryName, "assetTags")) {
		queryValues = ParamUtil.getString(request, "queryTagNames" + queryLogicIndex, queryValues);

		queryValues = AssetPublisherUtil.filterAssetTagNames(scopeGroupId, queryValues);
	}
	else {
		queryValues = ParamUtil.getString(request, "queryCategoryIds" + queryLogicIndex, queryValues);

		rulesJSONObject.put("categoryIdsTitles", AssetCategoryUtil.getCategoryIdsTitles(queryValues, StringPool.BLANK, 0, themeDisplay));
	}

	rulesJSONObject.put("queryValues", queryValues);
	rulesJSONObject.put("type", queryName);

	rulesJSONArray.put(rulesJSONObject);
}

Map<String, Object> context = new HashMap<>();

context.put("rules", rulesJSONArray);
context.put("namespace", liferayPortletResponse.getNamespace());
context.put("groupIds", StringUtil.merge(categorizableGroupIds));
context.put("id", "autofield");
context.put("categorySelectorURL", assetPublisherDisplayContext.getCategorySelectorURL());
context.put("tagSelectorURL", assetPublisherDisplayContext.getTagSelectorURL());
context.put("vocabularyIds", assetPublisherDisplayContext.getVocabularyIds());
%>

<aui:fieldset label="displayed-assets-must-match-these-rules" markupView="lexicon">
	<liferay-ui:asset-tags-error />

	<%
	DuplicateQueryRuleException dqre = null;
	%>

	<liferay-ui:error exception="<%= DuplicateQueryRuleException.class %>">

		<%
		dqre = (DuplicateQueryRuleException)errorException;

		String name = dqre.getName();
		%>

		<liferay-util:buffer var="messageArgument">
			<em>(<liferay-ui:message key='<%= dqre.isContains() ? "contains" : "does-not-contain" %>' /> - <liferay-ui:message key='<%= dqre.isAndOperator() ? "all" : "any" %>' /> - <liferay-ui:message key='<%= name.equals(("assetTags")) ? "tags" : "categories" %>' />)</em>
		</liferay-util:buffer>

		<liferay-ui:message arguments="<%= messageArgument %>" key="only-one-rule-with-the-combination-x-is-supported" translateArguments="<%= false %>" />
	</liferay-ui:error>

	<aui:input label='<%= LanguageUtil.format(request, "show-only-assets-with-x-as-its-display-page", HtmlUtil.escape(layout.getName(locale)), false) %>' name="preferences--showOnlyLayoutAssets--" type="checkbox" value="<%= assetPublisherDisplayContext.isShowOnlyLayoutAssets() %>" />

	<aui:input label="include-tags-specified-in-the-url" name="preferences--mergeUrlTags--" type="checkbox" value="<%= assetPublisherDisplayContext.isMergeURLTags() %>" />
</aui:fieldset>

<div id="<portlet:namespace />ConditionForm"></div>

<soy:template-renderer
	context="<%= context %>"
	module="asset-publisher-web/js/AutoField.es"
	templateNamespace="AutoField.render"
/>