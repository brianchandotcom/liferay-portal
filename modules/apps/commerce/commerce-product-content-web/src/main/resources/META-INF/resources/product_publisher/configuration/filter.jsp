<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

<div>
	<div class="inline-item my-5 p-5 w-100">
		<span aria-hidden="true" class="loading-animation"></span>
	</div>

	<react:component
		module="js/AutoField"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"categorySelectorURL", cpPublisherConfigurationDisplayContext.getCategorySelectorURL()
			).put(
				"groupIds", String.valueOf(company.getGroupId())
			).put(
				"id", "autofield"
			).put(
				"namespace", liferayPortletResponse.getNamespace()
			).put(
				"rules", cpPublisherConfigurationDisplayContext.getAutoFieldRulesJSONArray()
			).put(
				"spritemap", themeDisplay.getPathThemeSpritemap()
			).put(
				"tagSelectorURL", cpPublisherConfigurationDisplayContext.getTagSelectorURL()
			).put(
				"vocabularyIds", cpPublisherConfigurationDisplayContext.getVocabularyIds()
			).build()
		%>'
	/>
</div>