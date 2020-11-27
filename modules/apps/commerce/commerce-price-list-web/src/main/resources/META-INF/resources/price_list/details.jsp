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
List<CommerceCatalog> commerceCatalogs = commercePriceListDisplayContext.getCommerceCatalogs();
List<CommercePriceListAccountRel> commercePriceListAccountRels = commercePriceListDisplayContext.getCommercePriceListAccountRels();
List<CommercePriceListCommerceAccountGroupRel> commercePriceListAccountGroupEntryRels = commercePriceListDisplayContext.getCommercePriceListCommerceAccountGroupRels();
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="details"
/>

<liferay-ui:error exception="<%= CommercePriceListCurrencyException.class %>" message="please-select-a-valid-store-currency" />
<liferay-ui:error exception="<%= CommercePriceListParentPriceListGroupIdException.class %>" message="please-select-a-valid-parent-price-list-for-the-selected-catalog" />
<liferay-ui:error exception="<%= NoSuchCatalogException.class %>" message="please-select-a-valid-catalog" />

<aui:model-context bean="<%= commercePriceList %>" model="<%= CommercePriceList.class %>" />

<liferay-util:buffer
	var="removeItemIcon"
>
	<liferay-ui:icon
		icon="times"
		markupView="lexicon"
		message="remove"
	/>
</liferay-util:buffer>

<aui:fieldset>
	<aui:select disabled="<%= commercePriceList != null %>" label="catalog" name="commerceCatalogGroupId" required="<%= true %>" showEmptyOption="<%= true %>">

		<%
		for (CommerceCatalog commerceCatalog : commerceCatalogs) {
		%>

			<aui:option label="<%= commerceCatalog.getName() %>" selected="<%= (commercePriceList == null) ? (commerceCatalogs.size() == 1) : commercePriceListDisplayContext.isSelectedCatalog(commerceCatalog) %>" value="<%= commerceCatalog.getGroupId() %>" />

		<%
		}
		%>

	</aui:select>

	<aui:input name="name" />

	<aui:select label="store-currency" name="commerceCurrencyId" showEmptyOption="<%= true %>">

		<%
		List<CommerceCurrency> commerceCurrencies = commercePriceListDisplayContext.getCommerceCurrencies();

		for (CommerceCurrency commerceCurrency : commerceCurrencies) {
		%>

			<aui:option label="<%= HtmlUtil.escape(commerceCurrency.getCode()) %>" selected="<%= (commercePriceList != null) && (commercePriceList.getCommerceCurrencyId() == commerceCurrency.getCommerceCurrencyId()) %>" value="<%= commerceCurrency.getCommerceCurrencyId() %>" />

		<%
		}
		%>

	</aui:select>

	<aui:input name="priority">
		<aui:validator name="number" />
	</aui:input>

	<aui:select label="price-type" name="netPrice">

		<%
		boolean isNetPrice = true;

		if (commercePriceList != null) {
			isNetPrice = commercePriceList.isNetPrice();
		}
		%>

		<aui:option label="net-price" selected="<%= isNetPrice %>" value="true" />
		<aui:option label="gross-price" selected="<%= !isNetPrice %>" value="false" />
	</aui:select>
</aui:fieldset>

<h5 class="text-default"><liferay-ui:message key="parent-price-list" /></h5>

<%
List<CommercePriceList> parentCommercePriceLists = Collections.emptyList();

CommercePriceList parentCommercePriceList = commercePriceListDisplayContext.getParentCommercePriceList();

if (parentCommercePriceList != null) {
	parentCommercePriceLists = Collections.singletonList(parentCommercePriceList);
}
%>

<liferay-ui:search-container
	curParam="parentCommercePriceList"
	headerNames="null,null"
	id="parentCommercePriceListSearchContainer"
	iteratorURL="<%= currentURLObj %>"
	total="<%= parentCommercePriceLists.size() %>"
>
	<liferay-ui:search-container-results
		results="<%= parentCommercePriceLists %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.commerce.price.list.model.CommercePriceList"
		keyProperty="commercePriceListId"
		modelVar="curCommercePriceList"
	>
		<liferay-ui:search-container-column-text
			cssClass="table-cell-expand"
			value="<%= HtmlUtil.escape(curCommercePriceList.getName()) %>"
		/>

		<liferay-ui:search-container-column-text>
			<a class="remove-parent-link" data-rowId="<%= curCommercePriceList.getCommercePriceListId() %>" href="javascript:;"><%= removeItemIcon %></a>
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		markupView="lexicon"
	/>
</liferay-ui:search-container>

<aui:button cssClass="mb-4" name="setParentCommercePriceList" value="select" />

<h5 class="text-default"><liferay-ui:message key="account-groups" /></h5>

<liferay-ui:search-container
	curParam="commercePriceListAccountGroupEntryRel"
	headerNames="null,null"
	id="commercePriceListAccountGroupEntryRelSearchContainer"
	iteratorURL="<%= currentURLObj %>"
	total="<%= commercePriceListAccountGroupEntryRels.size() %>"
>
	<liferay-ui:search-container-results
		results="<%= commercePriceListAccountGroupEntryRels.subList(searchContainer.getStart(), searchContainer.getResultEnd()) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.commerce.price.list.model.CommercePriceListCommerceAccountGroupRel"
		keyProperty="commercePriceListAccountGroupEntryRelId"
		modelVar="commercePriceListAccountGroupEntryRel"
	>

		<%
		CommerceAccountGroup commerceAccountGroup = commercePriceListDisplayContext.getCommerceAccountGroup(commercePriceListAccountGroupEntryRel.getCommerceAccountGroupId());
		%>

		<liferay-ui:search-container-column-text
			cssClass="table-cell-expand"
			value="<%= HtmlUtil.escape(commerceAccountGroup.getName()) %>"
		/>

		<liferay-ui:search-container-column-text>
			<a class="remove-rel-link" data-rowId="<%= commercePriceListAccountGroupEntryRel.getCommercePriceListCommerceAccountGroupRelId() %>" href="javascript:;"><%= removeItemIcon %></a>
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		markupView="lexicon"
	/>
</liferay-ui:search-container>

<aui:button cssClass="mb-4" name="selectCommercePriceListCommerceAccountGroupRel" value="select" />

<h5 class="text-default"><liferay-ui:message key="accounts" /></h5>

<liferay-ui:search-container
	curParam="commercePriceListAccountRel"
	headerNames="null,null"
	id="commercePriceListAccountRelSearchContainer"
	iteratorURL="<%= currentURLObj %>"
	total="<%= commercePriceListAccountRels.size() %>"
>
	<liferay-ui:search-container-results
		results="<%= commercePriceListAccountRels.subList(searchContainer.getStart(), searchContainer.getResultEnd()) %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.commerce.price.list.model.CommercePriceListAccountRel"
		keyProperty="commercePriceListAccountRelId"
		modelVar="commercePriceListAccountRel"
	>

		<%
		CommerceAccount commerceAccount = commercePriceListDisplayContext.getCommerceAccount(commercePriceListAccountRel.getCommerceAccountId());
		%>

		<liferay-ui:search-container-column-text
			cssClass="table-cell-expand"
			value="<%= HtmlUtil.escape(commerceAccount.getName()) %>"
		/>

		<liferay-ui:search-container-column-text>
			<a class="remove-account-rel-link" data-rowId="<%= commercePriceListAccountRel.getCommercePriceListAccountRelId() %>" href="javascript:;"><%= removeItemIcon %></a>
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		markupView="lexicon"
	/>
</liferay-ui:search-container>

<aui:button cssClass="mb-4" name="selectCommercePriceListAccountRel" value="select" />

<aui:script use="liferay-item-selector-dialog">
	window.document
		.querySelector('#<portlet:namespace />selectCommercePriceListAccountRel')
		.addEventListener('click', function (event) {
			event.preventDefault();

			var itemSelectorDialog = new A.LiferayItemSelectorDialog({
				eventName: 'accountsSelectItem',
				on: {
					selectedItemChange: function (event) {
						var selectedItems = event.newVal;

						if (selectedItems) {
							var A = AUI();

							A.Array.each(selectedItems, function (
								item,
								index,
								selectedItems
							) {
								<portlet:namespace />addCommercePriceListAccountRel(
									item
								);
							});
						}
					},
				},
				title:
					'<liferay-ui:message arguments="criterion" key="select-x" />',
				url:
					'<%= commercePriceListDisplayContext.getCommerceAccountSelectorUrl() %>',
			});

			itemSelectorDialog.open();
		});

	window.document
		.querySelector(
			'#<portlet:namespace />selectCommercePriceListCommerceAccountGroupRel'
		)
		.addEventListener('click', function (event) {
			event.preventDefault();

			var itemSelectorDialog = new A.LiferayItemSelectorDialog({
				eventName: 'accountGroupsSelectItem',
				on: {
					selectedItemChange: function (event) {
						var selectedItems = event.newVal;

						if (selectedItems) {
							var A = AUI();

							A.Array.each(selectedItems, function (
								item,
								index,
								selectedItems
							) {
								<portlet:namespace />addCommercePriceListCommerceAccountGroupRel(
									item
								);
							});
						}
					},
				},
				title:
					'<liferay-ui:message arguments="criterion" key="select-x" />',
				url:
					'<%= commercePriceListDisplayContext.getCommerceAccountGroupSelectorUrl() %>',
			});

			itemSelectorDialog.open();
		});

	window.document
		.querySelector('#<portlet:namespace />setParentCommercePriceList')
		.addEventListener('click', function (event) {
			event.preventDefault();

			var itemSelectorDialog = new A.LiferayItemSelectorDialog({
				eventName: 'priceListsSelectItem',
				on: {
					selectedItemChange: function (event) {
						var selectedItems = event.newVal;

						if (selectedItems) {
							var parentCommercePriceListId = selectedItems.replace(
								/(\d+).*/,
								'$1'
							);

							<portlet:namespace />setParentCommercePriceList(
								parentCommercePriceListId
							);
						}
					},
				},
				title: '<liferay-ui:message key="set-parent-price-list" />',
				url:
					'<%= commercePriceListDisplayContext.getPriceListItemSelectorUrl() %>',
			});

			itemSelectorDialog.open();
		});
</aui:script>

<aui:script>
	var <portlet:namespace />addCommerceAccountIds = [];
	var <portlet:namespace />addCommerceAccountGroupIds = [];
	var <portlet:namespace />deleteCommercePriceListAccountRelIds = [];
	var <portlet:namespace />deleteCommercePriceListCommerceAccountGroupRelIds = [];

	function <portlet:namespace />addCommercePriceListAccountRel(item) {
		var A = AUI();

		var searchContainer = Liferay.SearchContainer.get(
			'<portlet:namespace />commercePriceListAccountRelSearchContainer'
		);

		var rowColumns = [];

		rowColumns.push(item.name);
		rowColumns.push(
			'<a class="remove-account-rel-link" data-rowId="' +
				item.commerceAccountId +
				'" href="javascript:;"><%= UnicodeFormatter.toString(removeItemIcon) %></a>'
		);

		A.Array.removeItem(
			<portlet:namespace />deleteCommercePriceListAccountRelIds,
			item.commerceAccountId
		);

		<portlet:namespace />addCommerceAccountIds.push(item.commerceAccountId);

		document.<portlet:namespace />fm.<portlet:namespace />addCommerceAccountIds.value = <portlet:namespace />addCommerceAccountIds.join(
			','
		);
		document.<portlet:namespace />fm.<portlet:namespace />deleteCommercePriceListAccountRelIds.value = <portlet:namespace />deleteCommercePriceListAccountRelIds.join(
			','
		);

		searchContainer.addRow(rowColumns, item.commerceAccountId);

		searchContainer.updateDataStore();
	}

	function <portlet:namespace />deleteCommercePriceListAccountRel(
		commercePriceListAccountRelId
	) {
		var A = AUI();

		A.Array.removeItem(
			<portlet:namespace />addCommerceAccountIds,
			commercePriceListAccountRelId
		);

		<portlet:namespace />deleteCommercePriceListAccountRelIds.push(
			commercePriceListAccountRelId
		);

		document.<portlet:namespace />fm.<portlet:namespace />addCommerceAccountIds.value = <portlet:namespace />addCommerceAccountIds.join(
			','
		);
		document.<portlet:namespace />fm.<portlet:namespace />deleteCommercePriceListAccountRelIds.value = <portlet:namespace />deleteCommercePriceListAccountRelIds.join(
			','
		);
	}

	function <portlet:namespace />addCommercePriceListCommerceAccountGroupRel(
		item
	) {
		var A = AUI();

		var searchContainer = Liferay.SearchContainer.get(
			'<portlet:namespace />commercePriceListAccountGroupEntryRelSearchContainer'
		);

		var rowColumns = [];

		rowColumns.push(item.name);
		rowColumns.push(
			'<a class="remove-rel-link" data-rowId="' +
				item.commerceAccountGroupId +
				'" href="javascript:;"><%= UnicodeFormatter.toString(removeItemIcon) %></a>'
		);

		A.Array.removeItem(
			<portlet:namespace />deleteCommercePriceListCommerceAccountGroupRelIds,
			item.commerceAccountGroupId
		);

		<portlet:namespace />addCommerceAccountGroupIds.push(
			item.commerceAccountGroupId
		);

		document.<portlet:namespace />fm.<portlet:namespace />addCommerceAccountGroupIds.value = <portlet:namespace />addCommerceAccountGroupIds.join(
			','
		);
		document.<portlet:namespace />fm.<portlet:namespace />deleteCommercePriceListCommerceAccountGroupRelIds.value = <portlet:namespace />deleteCommercePriceListCommerceAccountGroupRelIds.join(
			','
		);

		searchContainer.addRow(rowColumns, item.commerceAccountGroupId);

		searchContainer.updateDataStore();
	}

	function <portlet:namespace />deleteCommercePriceListCommerceAccountGroupRel(
		commercePriceListAccountGroupEntryRelId
	) {
		var A = AUI();

		A.Array.removeItem(
			<portlet:namespace />addCommerceAccountGroupIds,
			commercePriceListAccountGroupEntryRelId
		);

		<portlet:namespace />deleteCommercePriceListCommerceAccountGroupRelIds.push(
			commercePriceListAccountGroupEntryRelId
		);

		document.<portlet:namespace />fm.<portlet:namespace />addCommerceAccountGroupIds.value = <portlet:namespace />addCommerceAccountGroupIds.join(
			','
		);
		document.<portlet:namespace />fm.<portlet:namespace />deleteCommercePriceListCommerceAccountGroupRelIds.value = <portlet:namespace />deleteCommercePriceListCommerceAccountGroupRelIds.join(
			','
		);
	}

	function <portlet:namespace />setParentCommercePriceList(
		parentCommercePriceListId
	) {
		var A = AUI();

		Liferay.Service(
			'/commerce.commercepricelist/fetch-commerce-price-list',
			{
				commercePriceListId: parentCommercePriceListId,
			},
			function (commercePriceList) {
				var searchContainer = Liferay.SearchContainer.get(
					'<portlet:namespace />parentCommercePriceListSearchContainer'
				);

				var rowColumns = [];

				rowColumns.push(commercePriceList.name);
				rowColumns.push(
					'<a class="remove-parent-link" data-rowId="' +
						parentCommercePriceListId +
						'" href="javascript:;"><%= UnicodeFormatter.toString(removeItemIcon) %></a>'
				);

				searchContainer.addRow(rowColumns, parentCommercePriceListId);

				searchContainer.updateDataStore();

				document.<portlet:namespace />fm.<portlet:namespace />parentCommercePriceListId.value = parentCommercePriceListId;
			}
		);
	}
</aui:script>

<aui:script use="liferay-search-container">
	var Util = Liferay.Util;

	var parentSearchContainer = Liferay.SearchContainer.get(
		'<portlet:namespace />parentCommercePriceListSearchContainer'
	);

	var parentSearchContainerContentBox = parentSearchContainer.get('contentBox');

	parentSearchContainerContentBox.delegate(
		'click',
		function (event) {
			var link = event.currentTarget;

			var tr = link.ancestor('tr');
			var rowId = link.attr('data-rowId');

			parentSearchContainer.deleteRow(tr, rowId);

			document.<portlet:namespace />fm.<portlet:namespace />parentCommercePriceListId.value = 0;
		},
		'.remove-parent-link'
	);

	var relSearchContainer = Liferay.SearchContainer.get(
		'<portlet:namespace />commercePriceListAccountGroupEntryRelSearchContainer'
	);

	var relSearchContainerContentBox = relSearchContainer.get('contentBox');

	relSearchContainerContentBox.delegate(
		'click',
		function (event) {
			var link = event.currentTarget;

			var tr = link.ancestor('tr');
			var rowId = link.attr('data-rowId');

			relSearchContainer.deleteRow(tr, rowId);

			<portlet:namespace />deleteCommercePriceListCommerceAccountGroupRel(
				rowId
			);
		},
		'.remove-rel-link'
	);

	var accountRelSearchContainer = Liferay.SearchContainer.get(
		'<portlet:namespace />commercePriceListAccountRelSearchContainer'
	);

	var accountRelSearchContainerContentBox = accountRelSearchContainer.get(
		'contentBox'
	);

	accountRelSearchContainerContentBox.delegate(
		'click',
		function (event) {
			var link = event.currentTarget;

			var tr = link.ancestor('tr');
			var rowId = link.attr('data-rowId');

			accountRelSearchContainer.deleteRow(tr, rowId);

			<portlet:namespace />deleteCommercePriceListAccountRel(rowId);
		},
		'.remove-account-rel-link'
	);
</aui:script>