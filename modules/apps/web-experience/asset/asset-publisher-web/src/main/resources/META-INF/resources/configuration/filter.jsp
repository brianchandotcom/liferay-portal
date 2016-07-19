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

<div id="<portlet:namespace />queryRules">
	<aui:fieldset label="displayed-assets-must-match-these-rules">
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

		<%
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

		int index = 0;

		for (int queryLogicIndex : queryLogicIndexes) {
			String queryValues = StringUtil.merge(portletPreferences.getValues("queryValues" + queryLogicIndex, new String[0]));
			String tagNames = ParamUtil.getString(request, "queryTagNames" + queryLogicIndex, queryValues);
			String categoryIds = ParamUtil.getString(request, "queryCategoryIds" + queryLogicIndex, queryValues);

			if (Validator.isNotNull(tagNames) || Validator.isNotNull(categoryIds) || (queryLogicIndexes.length == 1)) {
				request.setAttribute("configuration.jsp-categorizableGroupIds", assetPublisherDisplayContext.getReferencedModelsGroupIds());
				request.setAttribute("configuration.jsp-index", String.valueOf(index));
				request.setAttribute("configuration.jsp-queryLogicIndex", String.valueOf(queryLogicIndex));

				String cssClass = StringPool.BLANK;

				if (dqre != null) {
					boolean queryContains = PrefsParamUtil.getBoolean(portletPreferences, request, "queryContains" + queryLogicIndex, true);
					boolean queryAndOperator = PrefsParamUtil.getBoolean(portletPreferences, request, "queryAndOperator" + queryLogicIndex);
					String queryName = PrefsParamUtil.getString(portletPreferences, request, "queryName" + queryLogicIndex, "assetTags");

					String dqreQueryName = dqre.getName();

					if ((dqre.isContains() == queryContains) && (dqre.isAndOperator() == queryAndOperator) && dqreQueryName.equals(queryName)) {
						cssClass = "asset-query-rule-error";
					}
				}
		%>

				<div class="lfr-form-row <%= cssClass %>">
					<div class="row-fields">
						<liferay-util:include page="/edit_query_rule.jsp" servletContext="<%= application %>" />
					</div>
				</div>

		<%
			}

			index++;
		}
		%>

	</aui:fieldset>
</div>

<aui:input label='<%= LanguageUtil.format(request, "show-only-assets-with-x-as-its-display-page", HtmlUtil.escape(layout.getName(locale)), false) %>' name="preferences--showOnlyLayoutAssets--" type="toggle-switch" value="<%= assetPublisherDisplayContext.isShowOnlyLayoutAssets() %>" />

<aui:input label="include-tags-specified-in-the-url" name="preferences--mergeUrlTags--" type="toggle-switch" value="<%= assetPublisherDisplayContext.isMergeURLTags() %>" />

<aui:script use="liferay-auto-fields">
	var autoFields = new Liferay.AutoFields(
		{
			contentBox: '#<portlet:namespace />queryRules',
			fieldIndexes: '<portlet:namespace />queryLogicIndexes',
			namespace: '<portlet:namespace />',
			url: '<liferay-portlet:renderURL portletName="<%= AssetPublisherPortletKeys.ASSET_PUBLISHER %>" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="mvcPath" value="/edit_query_rule.jsp" /><portlet:param name="categorizableGroupIds" value="<%= StringUtil.merge(assetPublisherDisplayContext.getReferencedModelsGroupIds()) %>" /></liferay-portlet:renderURL>'
		}
	).render();
</aui:script>