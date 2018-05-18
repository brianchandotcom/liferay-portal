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

CPRule cpRule = cpCatalogRuleDisplayContext.getCPRule();
long cpRuleId = cpCatalogRuleDisplayContext.getCPRuleId();
SearchContainer<CPRuleUserSegmentRel> cpRuleUserSegmentRelSearchContainer = cpCatalogRuleDisplayContext.getCPRuleUserSegmentRelsSearchContainer();
PortletURL portletURL = cpCatalogRuleDisplayContext.getPortletURL();

portletURL.setParameter("mvcRenderCommandName", "editCPRule");
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="cpRuleUserSegmentRels"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			disabled="<%= true %>"
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= portletURL %>"
			selectedDisplayStyle="list"
		/>

		<portlet:actionURL name="editCPRuleUserSegmentRel" var="addCPRuleUserSegmentRelURL" />

		<aui:form action="<%= addCPRuleUserSegmentRelURL %>" cssClass="hide" name="addCPRuleUserSegmentRelFm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>" />
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
			<aui:input name="cpRuleId" type="hidden" value="<%= cpRuleId %>" />
			<aui:input name="commerceUserSegmentEntryIds" type="hidden" value="" />
		</aui:form>

		<liferay-frontend:add-menu
			inline="<%= true %>"
		>
			<liferay-frontend:add-menu-item
				id="addCPRuleUserSegmentRelMenuItem"
				title='<%= LanguageUtil.get(request, "add-user-segment-catalog-rule") %>'
				url="javascript:;"
			/>
		</liferay-frontend:add-menu>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-sort
			orderByCol="<%= cpRuleUserSegmentRelSearchContainer.getOrderByCol() %>"
			orderByType="<%= cpRuleUserSegmentRelSearchContainer.getOrderByType() %>"
			orderColumns='<%= new String[] {"create-date"} %>'
			portletURL="<%= portletURL %>"
		/>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button
			href='<%= "javascript:" + renderResponse.getNamespace() + "deleteCPRuleUserSegmentRels();" %>'
			icon="times"
			label="delete"
		/>
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<portlet:actionURL name="editCPRuleUserSegmentRel" var="editCPRuleUserSegmentRelActionURL" />

<aui:form action="<%= editCPRuleUserSegmentRelActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.DELETE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="deleteCPRuleUserSegmentRelIds" type="hidden" />

	<liferay-ui:search-container
		id="cpRuleUserSegmentRels"
		searchContainer="<%= cpRuleUserSegmentRelSearchContainer %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.commerce.product.model.CPRuleUserSegmentRel"
			keyProperty="CPRuleUserSegmentRelId"
			modelVar="cpRuleUserSegmentRel"
		>

			<%
			CommerceUserSegmentEntry commerceUserSegmentEntry = cpRuleUserSegmentRel.getCommerceUserSegmentEntry();
			%>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="name"
				value="<%= HtmlUtil.escape(commerceUserSegmentEntry.getName(locale)) %>"
			/>

			<liferay-ui:search-container-column-jsp
				cssClass="entry-action-column"
				path="/rule_user_segment_rel_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<aui:script>
	function <portlet:namespace />deleteCPRuleUserSegmentRels() {
		if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-user-segment-catalog-rules" />')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.fm('deleteCPRuleUserSegmentRelIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form);
		}
	}
</aui:script>

<aui:script use="liferay-item-selector-dialog">
	$('#<portlet:namespace />addCPRuleUserSegmentRelMenuItem').on(
		'click',
		function(event) {
			event.preventDefault();

			var itemSelectorDialog = new A.LiferayItemSelectorDialog(
				{
					eventName: 'userSegmentSelectItem',
					on: {
						selectedItemChange: function(event) {
							var <portlet:namespace />addCommerceUserSegmentEntryIds = [];

							var selectedItems = event.newVal;

							if (selectedItems) {
								A.Array.each(
									selectedItems,
									function(item, index, selectedItems) {
										<portlet:namespace />addCommerceUserSegmentEntryIds.push(item.commerceUserSegmentEntryId);
									}
								);

								$('#<portlet:namespace />commerceUserSegmentEntryIds').val(<portlet:namespace />addCommerceUserSegmentEntryIds.join(','));

								var addCPRuleUserSegmentRelFm = $('#<portlet:namespace />addCPRuleUserSegmentRelFm');

								submitForm(addCPRuleUserSegmentRelFm);
							}
						}
					},
					title: '<liferay-ui:message arguments="<%= cpRule.getName() %>" key="add-new-user-segment-to-x" />',
					url: '<%= cpCatalogRuleDisplayContext.getItemSelectorUrl() %>'
				}
			);

			itemSelectorDialog.open();
		}
	);
</aui:script>