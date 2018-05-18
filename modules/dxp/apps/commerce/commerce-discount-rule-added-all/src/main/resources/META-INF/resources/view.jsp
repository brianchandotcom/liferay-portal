<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
AddedAllCommerceDiscountRuleDisplayContext addedAllCommerceDiscountRuleDisplayContext = (AddedAllCommerceDiscountRuleDisplayContext)request.getAttribute("view.jsp-addedAllCommerceDiscountRuleDisplayContext");

List<CPDefinition> cpDefinitions = addedAllCommerceDiscountRuleDisplayContext.getCPDefinitions();
%>

<liferay-util:buffer
	var="removeCommerceDiscountRuleCPDefinitionIcon"
>
	<liferay-ui:icon
		icon="times"
		markupView="lexicon"
		message="remove"
	/>
</liferay-util:buffer>

<liferay-ui:search-container
	cssClass="lfr-search-container-discount-rule-product-definitions"
	curParam="commerceDiscountRuleCPDefinitionCur"
	headerNames="null,null"
	id="commerceDiscountRuleCPDefinitionSearchContainer"
	iteratorURL="<%= currentURLObj %>"
	total="<%= cpDefinitions.size() %>"
>
	<liferay-ui:search-container-results
		results="<%= cpDefinitions.subList(searchContainer.getStart(), searchContainer.getResultEnd()) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.commerce.product.model.CPDefinition"
		keyProperty="CPDefinitionId"
		modelVar="cpDefinition"
	>
		<liferay-ui:search-container-column-text
			cssClass="table-cell-content"
			value="<%= HtmlUtil.escape(cpDefinition.getName(themeDisplay.getLanguageId())) %>"
		/>

		<liferay-ui:search-container-column-text>
			<a class="float-right modify-link" data-rowId="<%= cpDefinition.getCPDefinitionId() %>" href="javascript:;"><%= removeCommerceDiscountRuleCPDefinitionIcon %></a>
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		markupView="lexicon"
	/>
</liferay-ui:search-container>

<aui:button name="selectCommerceDiscountCPDefinition" value="select" />

<aui:script use="liferay-item-selector-dialog">
	$('#<portlet:namespace />selectCommerceDiscountCPDefinition').on(
		'click',
		function(event) {
			event.preventDefault();

			var itemSelectorDialog = new A.LiferayItemSelectorDialog(
				{
					eventName: 'productDefinitionsSelectItem',
					on: {
						selectedItemChange: function(event) {
							var selectedItems = event.newVal;

							if (selectedItems) {
								var A = AUI();

								A.Array.each(
									selectedItems,
									function(item, index, selectedItems) {
										<portlet:namespace />addCommerceDiscountRuleCPDefinition(item);
									}
								);
							}
						}
					},
					title: '<liferay-ui:message arguments="product" key="select-x" />',
					url: '<%= addedAllCommerceDiscountRuleDisplayContext.getItemSelectorUrl() %>'
				}
			);

			itemSelectorDialog.open();
		}
	);
</aui:script>

<aui:script>
	var <portlet:namespace />addCommerceDiscountRuleCPDefinitionIds = [];
	var <portlet:namespace />deleteCommerceDiscountRuleCPDefinitionIds = [];

	function <portlet:namespace />addCommerceDiscountRuleCPDefinition(item) {
		var A = AUI();

		var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />commerceDiscountRuleCPDefinitionSearchContainer');

		var rowColumns = [];

		rowColumns.push(item.name);
		rowColumns.push('<a class="float-right modify-link" data-rowId="' + item.cpDefinitionId + '" href="javascript:;"><%= UnicodeFormatter.toString(removeCommerceDiscountRuleCPDefinitionIcon) %></a>');

		A.Array.removeItem(<portlet:namespace />deleteCommerceDiscountRuleCPDefinitionIds, item.cpDefinitionId);

		<portlet:namespace />addCommerceDiscountRuleCPDefinitionIds.push(item.cpDefinitionId);

		document.<portlet:namespace />fm.<portlet:namespace />addTypeSettings.value = <portlet:namespace />addCommerceDiscountRuleCPDefinitionIds.join(',');
		document.<portlet:namespace />fm.<portlet:namespace />deleteTypeSettings.value = <portlet:namespace />deleteCommerceDiscountRuleCPDefinitionIds.join(',');

		searchContainer.addRow(rowColumns, item.cpDefinitionId);

		searchContainer.updateDataStore();
	}

	function <portlet:namespace />deleteCommerceDiscountRuleCPDefinition(cpDefinitionId) {
		var A = AUI();

		A.Array.removeItem(<portlet:namespace />addCommerceDiscountRuleCPDefinitionIds, cpDefinitionId);

		<portlet:namespace />deleteCommerceDiscountRuleCPDefinitionIds.push(cpDefinitionId);

		document.<portlet:namespace />fm.<portlet:namespace />addTypeSettings.value = <portlet:namespace />addCommerceDiscountRuleCPDefinitionIds.join(',');
		document.<portlet:namespace />fm.<portlet:namespace />deleteTypeSettings.value = <portlet:namespace />deleteCommerceDiscountRuleCPDefinitionIds.join(',');
	}
</aui:script>

<aui:script use="liferay-search-container">
	var Util = Liferay.Util;

	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />commerceDiscountRuleCPDefinitionSearchContainer');

	var searchContainerContentBox = searchContainer.get('contentBox');

	searchContainerContentBox.delegate(
		'click',
		function(event) {
			var link = event.currentTarget;

			var rowId = link.attr('data-rowId');

			var tr = link.ancestor('tr');

			searchContainer.deleteRow(tr, link.getAttribute('data-rowId'));

			<portlet:namespace />deleteCommerceDiscountRuleCPDefinition(rowId);
		},
		'.modify-link'
	);
</aui:script>