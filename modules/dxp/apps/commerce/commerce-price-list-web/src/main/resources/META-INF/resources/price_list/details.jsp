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
CommercePriceListDisplayContext commercePriceListDisplayContext = (CommercePriceListDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommercePriceList commercePriceList = commercePriceListDisplayContext.getCommercePriceList();

List<CommercePriceListQualificationTypeRel> commercePriceListQualificationTypeRels = commercePriceListDisplayContext.getCommercePriceListQualificationTypeRels();
%>

<liferay-ui:error-marker key="<%= WebKeys.ERROR_SECTION %>" value="details" />

<aui:model-context bean="<%= commercePriceList %>" model="<%= CommercePriceList.class %>" />

<liferay-util:buffer var="removeCommercePriceListQualificationTypeIcon">
	<liferay-ui:icon
		icon="times"
		markupView="lexicon"
		message="remove"
	/>
</liferay-util:buffer>

<aui:fieldset>
	<aui:input name="name" />

	<aui:select label="commerce-currency" name="commerceCurrencyId" showEmptyOption="<%= true %>">

		<%
		List<CommerceCurrency> commerceCurrencies = commercePriceListDisplayContext.getCommerceCurrencies();

		for (CommerceCurrency commerceCurrency : commerceCurrencies) {
		%>

			<aui:option
				label="<%= commerceCurrency.getCode(languageId) %>"
				selected="<%= (commercePriceList != null) && (commercePriceList.getCommerceCurrencyId() == commerceCurrency.getCommerceCurrencyId()) %>"
				value="<%= commerceCurrency.getCommerceCurrencyId() %>"
			/>

		<%
		}
		%>

	</aui:select>

	<aui:input name="priority" />
</aui:fieldset>

<h5 class="text-default"><liferay-ui:message key="qualification-type" /></h5>

<liferay-ui:search-container
	cssClass="lfr-search-container-qualification-type-rels"
	curParam="commercePriceListQualificationTypeCur"
	headerNames="null,null"
	id="commercePriceListQualificationTypeRelSearchContainer"
	iteratorURL="<%= currentURLObj %>"
	total="<%= commercePriceListQualificationTypeRels.size() %>"
>
	<liferay-ui:search-container-results
		results="<%= commercePriceListQualificationTypeRels.subList(searchContainer.getStart(), searchContainer.getResultEnd()) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.commerce.model.CommercePriceListQualificationTypeRel"
		keyProperty="commercePriceListQualificationTypeRelId"
		modelVar="commercePriceListQualificationTypeRel"
	>

		<%
		CommercePriceListQualificationType commercePriceListQualificationType = commercePriceListDisplayContext.getCommercePriceListQualificationType(commercePriceListQualificationTypeRel.getCommercePriceListQualificationType());
		%>

		<liferay-ui:search-container-column-text
			cssClass="table-cell-content"
			value="<%= HtmlUtil.escape(commercePriceListQualificationType.getLabel(request)) %>"
		/>

		<liferay-ui:search-container-column-text>
			<a class="modify-link" data-rowId="<%= commercePriceListQualificationTypeRel.getCommercePriceListQualificationTypeRelId() %>" href="javascript:;"><%= removeCommercePriceListQualificationTypeIcon %></a>
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator markupView="lexicon" />
</liferay-ui:search-container>

<aui:button name="selectCommercePriceListQualificationType" value="select" />

<aui:script use="liferay-item-selector-dialog">
	$('#<portlet:namespace />selectCommercePriceListQualificationType').on(
		'click',
		function(event) {
			event.preventDefault();

			var itemSelectorDialog = new A.LiferayItemSelectorDialog(
				{
					eventName: 'qualificationTypesSelectItem',
					on: {
						selectedItemChange: function(event) {
							var selectedItems = event.newVal;

							if (selectedItems) {
								var A = AUI();

								A.Array.each(
									selectedItems,
									function(item, index, selectedItems) {
										<portlet:namespace />addCommercePriceListQualificationTypeRel(item);
									}
								);
							}
						}
					},
					title: '<liferay-ui:message arguments="qualification-type" key="select-x" />',
					url: '<%= commercePriceListDisplayContext.getItemSelectorUrl() %>'
				}
			);

			itemSelectorDialog.open();
		}
	);
</aui:script>

<aui:script>
	var <portlet:namespace />addCommercePriceListQualificationTypeRelKeys = [];
	var <portlet:namespace />deleteCommercePriceListQualificationTypeRelIds = [];

	function <portlet:namespace />addCommercePriceListQualificationTypeRel(item) {
		var A = AUI();

		var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />commercePriceListQualificationTypeRelSearchContainer');

		var rowColumns = [];

		rowColumns.push(item.label);
		rowColumns.push('<a class="modify-link" data-rowId="' + item.key + '" href="javascript:;"><%= UnicodeFormatter.toString(removeCommercePriceListQualificationTypeIcon) %></a>');

		A.Array.removeItem(<portlet:namespace />deleteCommercePriceListQualificationTypeRelIds, item.key);

		<portlet:namespace />addCommercePriceListQualificationTypeRelKeys.push(item.key);

		document.<portlet:namespace />fm.<portlet:namespace />addCommercePriceListQualificationTypeRelKeys.value = <portlet:namespace />addCommercePriceListQualificationTypeRelKeys.join(',');
		document.<portlet:namespace />fm.<portlet:namespace />deleteCommercePriceListQualificationTypeRelIds.value = <portlet:namespace />deleteCommercePriceListQualificationTypeRelIds.join(',');

		searchContainer.addRow(rowColumns, item.key);

		searchContainer.updateDataStore();
	}

	function <portlet:namespace />deleteCommercePriceListQualificationTypeRel(commercePriceListQualificationTypeRelId) {
		var A = AUI();

		A.Array.removeItem(<portlet:namespace />addCommercePriceListQualificationTypeRelKeys, commercePriceListQualificationTypeRelId);

		<portlet:namespace />deleteCommercePriceListQualificationTypeRelIds.push(commercePriceListQualificationTypeRelId);

		document.<portlet:namespace />fm.<portlet:namespace />addCommercePriceListQualificationTypeRelKeys.value = <portlet:namespace />addCommercePriceListQualificationTypeRelKeys.join(',');
		document.<portlet:namespace />fm.<portlet:namespace />deleteCommercePriceListQualificationTypeRelIds.value = <portlet:namespace />deleteCommercePriceListQualificationTypeRelIds.join(',');
	}
</aui:script>

<aui:script use="liferay-search-container">
	var Util = Liferay.Util;

	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />commercePriceListQualificationTypeRelSearchContainer');

	var searchContainerContentBox = searchContainer.get('contentBox');

	searchContainerContentBox.delegate(
		'click',
		function(event) {
			var link = event.currentTarget;

			var rowId = link.attr('data-rowId');

			var tr = link.ancestor('tr');

			searchContainer.deleteRow(tr, link.getAttribute('data-rowId'));

			<portlet:namespace />deleteCommercePriceListQualificationTypeRel(rowId);
		},
		'.modify-link'
	);
</aui:script>