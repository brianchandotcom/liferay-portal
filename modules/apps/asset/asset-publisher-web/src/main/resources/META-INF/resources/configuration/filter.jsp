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

<liferay-frontend:fieldset-group>
	<liferay-frontend:fieldset>
		<liferay-asset:asset-tags-error />

		<%
		DuplicateQueryRuleException dqre = null;
		%>

		<liferay-ui:error exception="<%= DuplicateQueryRuleException.class %>">

			<%
			dqre = (DuplicateQueryRuleException)errorException;

			String name = "categories";

			if (Objects.equals(dqre.getName(), "assetTags")) {
				name = "tags";
			}
			else if (Objects.equals(dqre.getName(), "keywords")) {
				name = "keywords";
			}
			%>

			<liferay-util:buffer
				var="messageArgument"
			>
				<em>(<liferay-ui:message key='<%= dqre.isContains() ? "contains" : "does-not-contain" %>' /> - <liferay-ui:message key='<%= dqre.isAndOperator() ? "all" : "any" %>' /> - <liferay-ui:message key="<%= name %>" />)</em>
			</liferay-util:buffer>

			<liferay-ui:message arguments="<%= messageArgument %>" key="only-one-rule-with-the-combination-x-is-supported" translateArguments="<%= false %>" />
		</liferay-ui:error>

		<p><liferay-ui:message key="displayed-items-must-match-these-rules" /></p>

		<div id="<portlet:namespace />ConditionForm"></div>

		<div>
			<react:component
				module="auto_field/index"
				props='<%=
					HashMapBuilder.<String, Object>put(
						"categorySelectorURL", assetPublisherDisplayContext.getCategorySelectorURL()
					).put(
						"groupIds", ListUtil.toList(assetPublisherDisplayContext.getReferencedModelsGroupIds())
					).put(
						"namespace", liferayPortletResponse.getNamespace()
					).put(
						"pathThemeImages", themeDisplay.getPathThemeImages()
					).put(
						"rules", assetPublisherDisplayContext.getAutoFieldRulesJSONArray()
					).put(
						"tagSelectorURL", assetPublisherDisplayContext.getTagSelectorURL()
					).put(
						"vocabularyIds", assetPublisherDisplayContext.getVocabularyIds()
					).build()
				%>'
			/>
		</div>
	</liferay-frontend:fieldset>
</liferay-frontend:fieldset-group>