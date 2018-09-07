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
String redirect = editAssetListDisplayContext.getRedirectURL();
%>

<portlet:actionURL name="/asset_list/edit_asset_list_entry_settings" var="editAssetListEntrySettingsURL" />

<liferay-frontend:edit-form
	action="<%= editAssetListEntrySettingsURL %>"
	method="post"
	name="fm"
>
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="assetListEntryId" type="hidden" value="<%= assetListDisplayContext.getAssetListEntryId() %>" />
	<aui:input name="type" type="hidden" value="<%= assetListDisplayContext.getAssetListEntryType() %>" />

	<liferay-frontend:edit-form-body>
		<h3 class="sheet-subtitle">
			<span class="autofit-padded-no-gutters autofit-row">
				<span class="autofit-col autofit-col-expand">
					<span class="heading-text">
						<liferay-ui:message key="filter" />
					</span>
				</span>
			</span>
		</h3>

		<liferay-frontend:fieldset-group>
			<aui:fieldset label="displayed-assets-must-match-these-rules" markupView="lexicon">
				<liferay-asset:asset-tags-error />

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

				<div id="<portlet:namespace />ConditionForm"></div>

				<%
				Map<String, Object> context = new HashMap<>();

				context.put("categorySelectorURL", editAssetListDisplayContext.getCategorySelectorURL());
				context.put("groupIds", StringUtil.merge(editAssetListDisplayContext.getReferencedModelsGroupIds()));
				context.put("id", "autofield");
				context.put("namespace", liferayPortletResponse.getNamespace());
				context.put("pathThemeImages", themeDisplay.getPathThemeImages());
				context.put("rules", editAssetListDisplayContext.getAutoFieldRulesJSONArray());
				context.put("tagSelectorURL", editAssetListDisplayContext.getTagSelectorURL());
				context.put("vocabularyIds", editAssetListDisplayContext.getVocabularyIds());
				%>

				<soy:component-renderer
					context="<%= context %>"
					module="asset-list-web/js/AutoField.es"
					templateNamespace="com.liferay.asset.list.web.AutoField.render"
				/>
			</aui:fieldset>
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>