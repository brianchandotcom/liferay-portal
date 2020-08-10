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
CPPublisherConfigurationDisplayContext cpPublisherConfigurationDisplayContext = (CPPublisherConfigurationDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<aui:fieldset label="displayed-assets-must-match-these-rules" markupView="lexicon">

	<%
	DuplicateQueryRuleException dqre = null;
	%>

	<liferay-ui:error exception="<%= DuplicateQueryRuleException.class %>">

		<%
		dqre = (DuplicateQueryRuleException)errorException;
		String name = dqre.getName();
		%>

		<liferay-util:buffer
			var="messageArgument"
		>
			<em>(<liferay-ui:message key='<%= dqre.isContains() ? "contains" : "does-not-contain" %>' /> - <liferay-ui:message key='<%= dqre.isAndOperator() ? "all" : "any" %>' /> - <liferay-ui:message key='<%= name.equals("assetTags") ? "tags" : "categories" %>' />)</em>
		</liferay-util:buffer>

		<liferay-ui:message arguments="<%= messageArgument %>" key="only-one-rule-with-the-combination-x-is-supported" translateArguments="<%= false %>" />
	</liferay-ui:error>
</aui:fieldset>

<div id="<portlet:namespace />ConditionForm"></div>

<%
Map<String, Object> context = new HashMap<>();

context.put("categorySelectorURL", cpPublisherConfigurationDisplayContext.getCategorySelectorURL());
context.put("id", "autofield");
context.put("groupIds", String.valueOf(company.getGroupId()));
context.put("namespace", liferayPortletResponse.getNamespace());
context.put("pathThemeImages", themeDisplay.getPathThemeImages());
context.put("rules", cpPublisherConfigurationDisplayContext.getAutoFieldRulesJSONArray());
context.put("tagSelectorURL", cpPublisherConfigurationDisplayContext.getTagSelectorURL());
context.put("vocabularyIds", cpPublisherConfigurationDisplayContext.getVocabularyIds());
%>

<soy:template-renderer
	context="<%= context %>"
	module="js/AutoField.es"
	templateNamespace="com.liferay.commerce.product.content.web.AutoField.render"
/>