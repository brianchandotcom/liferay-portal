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
CPCatalogRuleDisplayContext cpCatalogRuleDisplayContext = (CPCatalogRuleDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

SearchContainer<CPRule> cpRuleSearchContainer = cpCatalogRuleDisplayContext.getSearchContainer();
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="cpRules"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			disabled="<%= true %>"
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= cpCatalogRuleDisplayContext.getPortletURL() %>"
			selectedDisplayStyle="list"
		/>

		<portlet:renderURL var="addCPRuleURL">
			<portlet:param name="mvcRenderCommandName" value="editCPRule" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
		</portlet:renderURL>

		<liferay-frontend:add-menu
			inline="<%= true %>"
		>
			<liferay-frontend:add-menu-item
				title='<%= LanguageUtil.get(request, "add-catalog-rule") %>'
				url="<%= addCPRuleURL.toString() %>"
			/>
		</liferay-frontend:add-menu>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-sort
			orderByCol="<%= cpRuleSearchContainer.getOrderByCol() %>"
			orderByType="<%= cpRuleSearchContainer.getOrderByType() %>"
			orderColumns='<%= new String[] {"create-date"} %>'
			portletURL="<%= cpCatalogRuleDisplayContext.getPortletURL() %>"
		/>

		<li>
			<aui:form action="<%= String.valueOf(cpCatalogRuleDisplayContext.getPortletURL()) %>" name="searchFm">
				<liferay-ui:input-search
					markupView="lexicon"
				/>
			</aui:form>
		</li>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button
			href='<%= "javascript:" + renderResponse.getNamespace() + "deleteCPRules();" %>'
			icon="times"
			label="delete"
		/>
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<div class="container-fluid-1280">
	<portlet:actionURL name="editCPRule" var="editCPRuleActionURL" />

	<aui:form action="<%= editCPRuleActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.DELETE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="deleteCPRuleIds" type="hidden" />

		<liferay-ui:search-container
			id="cpRules"
			searchContainer="<%= cpRuleSearchContainer %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.commerce.product.model.CPRule"
				keyProperty="CPRuleId"
				modelVar="cpRule"
			>
				<portlet:renderURL var="rowURL">
					<portlet:param name="mvcRenderCommandName" value="editCPRule" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="cpRuleId" value="<%= String.valueOf(cpRule.getCPRuleId()) %>" />
				</portlet:renderURL>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					href="<%= rowURL %>"
					property="name"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					name="active"
					value='<%= LanguageUtil.get(request, cpRule.isActive() ? "yes" : "no") %>'
				/>

				<liferay-ui:search-container-column-jsp
					cssClass="entry-action-column"
					path="/rule_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</aui:form>
</div>

<aui:script>
	function <portlet:namespace />deleteCPRules() {
		if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-catalog-rules" />')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.fm('deleteCPRuleIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form);
		}
	}
</aui:script>