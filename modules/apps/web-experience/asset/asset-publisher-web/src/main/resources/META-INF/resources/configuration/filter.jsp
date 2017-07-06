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

<%
Map<String, Object> context = new HashMap<>();

context.put("categorySelectorURL", assetPublisherDisplayContext.getCategorySelectorURL());
context.put("id", "autofield");
context.put("groupIds", StringUtil.merge(categorizableGroupIds));
context.put("namespace", liferayPortletResponse.getNamespace());
context.put("pathThemeImages", themeDisplay.getPathThemeImages());
context.put("rules", assetPublisherDisplayContext.getAutoFieldRulesJSONArray());
context.put("tagSelectorURL", assetPublisherDisplayContext.getTagSelectorURL());
context.put("vocabularyIds", assetPublisherDisplayContext.getVocabularyIds());
%>

<soy:template-renderer
	context="<%= context %>"
	module="asset-publisher-web/js/AutoField.es"
	templateNamespace="AutoField.render"
/>