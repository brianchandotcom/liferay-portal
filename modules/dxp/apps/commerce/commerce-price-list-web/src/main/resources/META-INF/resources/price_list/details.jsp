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

List<CommercePriceListUserSegmentEntryRel> commercePriceListUserSegmentEntryRels = commercePriceListDisplayContext.getCommercePriceListUserSegmentEntryRels();
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="details"
/>

<liferay-ui:error exception="<%= CommercePriceListCurrencyException.class %>" message="please-select-a-valid-store-currency" />

<aui:model-context bean="<%= commercePriceList %>" model="<%= CommercePriceList.class %>" />

<liferay-util:buffer
	var="removeCommercePriceListUserSegmentEntryRelIcon"
>
	<liferay-ui:icon
		icon="times"
		markupView="lexicon"
		message="remove"
	/>
</liferay-util:buffer>

<aui:fieldset>
	<aui:input name="name" />

	<aui:select label="store-currency" name="commerceCurrencyId" showEmptyOption="<%= true %>">

		<%
		List<CommerceCurrency> commerceCurrencies = commercePriceListDisplayContext.getCommerceCurrencies();

		for (CommerceCurrency commerceCurrency : commerceCurrencies) {
		%>

			<aui:option label="<%= commerceCurrency.getCode() %>" selected="<%= (commercePriceList != null) && (commercePriceList.getCommerceCurrencyId() == commerceCurrency.getCommerceCurrencyId()) %>" value="<%= commerceCurrency.getCommerceCurrencyId() %>" />

		<%
		}
		%>

	</aui:select>

	<aui:input name="priority" />
</aui:fieldset>

<h5 class="text-default"><liferay-ui:message key="user-segments" /></h5>

<liferay-ui:search-container
	curParam="commercePriceListUserSegmentEntryRel"
	headerNames="null,null"
	id="commercePriceListUserSegmentEntryRelSearchContainer"
	iteratorURL="<%= currentURLObj %>"
	total="<%= commercePriceListUserSegmentEntryRels.size() %>"
>
	<liferay-ui:search-container-results
		results="<%= commercePriceListUserSegmentEntryRels.subList(searchContainer.getStart(), searchContainer.getResultEnd()) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.commerce.price.list.model.CommercePriceListUserSegmentEntryRel"
		keyProperty="commercePriceListUserSegmentEntryRelId"
		modelVar="commercePriceListUserSegmentEntryRel"
	>

		<%
		CommerceUserSegmentEntry commerceUserSegmentEntry = commercePriceListDisplayContext.getCommerceUserSegmentEntry(commercePriceListUserSegmentEntryRel.getCommerceUserSegmentEntryId());
		%>

		<liferay-ui:search-container-column-text
			cssClass="table-cell-content"
			value="<%= HtmlUtil.escape(commerceUserSegmentEntry.getName(locale)) %>"
		/>

		<liferay-ui:search-container-column-text>
			<a class="modify-link" data-rowId="<%= commercePriceListUserSegmentEntryRel.getCommercePriceListUserSegmentEntryRelId() %>" href="javascript:;"><%= removeCommercePriceListUserSegmentEntryRelIcon %></a>
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		markupView="lexicon"
	/>
</liferay-ui:search-container>

<aui:button name="selectCommercePriceListUserSegmentEntryRel" value="select" />

<aui:script use="liferay-item-selector-dialog">
	$('#<portlet:namespace />selectCommercePriceListUserSegmentEntryRel').on(
		'click',
		function(event) {
			event.preventDefault();

			var itemSelectorDialog = new A.LiferayItemSelectorDialog(
				{
					eventName: 'userSegmentsSelectItem',
					on: {
						selectedItemChange: function(event) {
							var selectedItems = event.newVal;

							if (selectedItems) {
								var A = AUI();

								A.Array.each(
									selectedItems,
									function(item, index, selectedItems) {
										<portlet:namespace />addCommercePriceListUserSegmentEntryRel(item);
									}
								);
							}
						}
					},
					title: '<liferay-ui:message arguments="criterion" key="select-x" />',
					url: '<%= commercePriceListDisplayContext.getItemSelectorUrl() %>'
				}
			);

			itemSelectorDialog.open();
		}
	);
</aui:script>

<aui:script>
	var <portlet:namespace />addCommerceUserSegmentEntryIds = [];
	var <portlet:namespace />deleteCommercePriceListUserSegmentEntryRelIds = [];

	function <portlet:namespace />addCommercePriceListUserSegmentEntryRel(item) {
		var A = AUI();

		var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />commercePriceListUserSegmentEntryRelSearchContainer');

		var rowColumns = [];

		rowColumns.push(item.name);
		rowColumns.push('<a class="modify-link" data-rowId="' + item.commerceUserSegmentEntryId + '" href="javascript:;"><%= UnicodeFormatter.toString(removeCommercePriceListUserSegmentEntryRelIcon) %></a>');

		A.Array.removeItem(<portlet:namespace />deleteCommercePriceListUserSegmentEntryRelIds, item.commerceUserSegmentEntryId);

		<portlet:namespace />addCommerceUserSegmentEntryIds.push(item.commerceUserSegmentEntryId);

		document.<portlet:namespace />fm.<portlet:namespace />addCommerceUserSegmentEntryIds.value = <portlet:namespace />addCommerceUserSegmentEntryIds.join(',');
		document.<portlet:namespace />fm.<portlet:namespace />deleteCommercePriceListUserSegmentEntryRelIds.value = <portlet:namespace />deleteCommercePriceListUserSegmentEntryRelIds.join(',');

		searchContainer.addRow(rowColumns, item.commerceUserSegmentEntryId);

		searchContainer.updateDataStore();
	}

	function <portlet:namespace />deleteCommercePriceListUserSegmentEntryRel(commercePriceListUserSegmentEntryRelId) {
		var A = AUI();

		A.Array.removeItem(<portlet:namespace />addCommerceUserSegmentEntryIds, commercePriceListUserSegmentEntryRelId);

		<portlet:namespace />deleteCommercePriceListUserSegmentEntryRelIds.push(commercePriceListUserSegmentEntryRelId);

		document.<portlet:namespace />fm.<portlet:namespace />addCommerceUserSegmentEntryIds.value = <portlet:namespace />addCommerceUserSegmentEntryIds.join(',');
		document.<portlet:namespace />fm.<portlet:namespace />deleteCommercePriceListUserSegmentEntryRelIds.value = <portlet:namespace />deleteCommercePriceListUserSegmentEntryRelIds.join(',');
	}
</aui:script>

<aui:script use="liferay-search-container">
	var Util = Liferay.Util;

	var searchContainer = Liferay.SearchContainer.get('<portlet:namespace />commercePriceListUserSegmentEntryRelSearchContainer');

	var searchContainerContentBox = searchContainer.get('contentBox');

	searchContainerContentBox.delegate(
		'click',
		function(event) {
			var link = event.currentTarget;

			var rowId = link.attr('data-rowId');

			var tr = link.ancestor('tr');

			searchContainer.deleteRow(tr, link.getAttribute('data-rowId'));

			<portlet:namespace />deleteCommercePriceListUserSegmentEntryRel(rowId);
		},
		'.modify-link'
	);
</aui:script>