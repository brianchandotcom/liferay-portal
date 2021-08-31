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
CPInstanceCommercePriceEntryDisplayContext cpInstanceCommercePriceEntryDisplayContext = (CPInstanceCommercePriceEntryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPInstance cpInstance = cpInstanceCommercePriceEntryDisplayContext.getCPInstance();
long cpInstanceId = cpInstanceCommercePriceEntryDisplayContext.getCPInstanceId();
PortletURL portletURL = cpInstanceCommercePriceEntryDisplayContext.getPortletURL();
%>

<portlet:actionURL name="/cp_definitions/edit_cp_instance_commerce_price_entry" var="addCommercePriceEntryURL" />

<aui:form action="<%= addCommercePriceEntryURL %>" cssClass="hide" name="addCommercePriceEntryFm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD_MULTIPLE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="cpInstanceId" type="hidden" value="<%= cpInstanceId %>" />
	<aui:input name="commercePriceListIds" type="hidden" value="" />
</aui:form>

<div id="<portlet:namespace />entriesContainer">
	<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="deleteCommercePriceEntryIds" type="hidden" />

		<liferay-ui:error exception="<%= DuplicateCommercePriceEntryException.class %>" message="one-or-more-selected-entries-already-exist" />

		<clay:data-set-display
			contextParams='<%=
				HashMapBuilder.<String, String>put(
					"cpInstanceId", String.valueOf(cpInstanceId)
				).build()
			%>'
			creationMenu="<%= cpInstanceCommercePriceEntryDisplayContext.getCreationMenu() %>"
			dataProviderKey="<%= CommercePricingDataSetConstants.COMMERCE_DATA_SET_KEY_INSTANCE_PRICE_ENTRIES %>"
			formId="fm"
			id="<%= CommercePricingDataSetConstants.COMMERCE_DATA_SET_KEY_INSTANCE_PRICE_ENTRIES %>"
			itemsPerPage="<%= 10 %>"
			namespace="<%= liferayPortletResponse.getNamespace() %>"
			pageNumber="<%= 1 %>"
			portletURL="<%= portletURL %>"
			style="stacked"
		/>
	</aui:form>
</div>

<aui:script use="liferay-item-selector-dialog">
	Liferay.on('<portlet:namespace />addCommercePriceEntry', () => {
		var itemSelectorDialog = new A.LiferayItemSelectorDialog({
			eventName: 'priceListsSelectItem',
			on: {
				selectedItemChange: function (event) {
					var selectedItems = event.newVal;

					if (selectedItems) {
						window.document.querySelector(
							'#<portlet:namespace />commercePriceListIds'
						).value = selectedItems;

						var addCommercePriceEntryFm = window.document.querySelector(
							'#<portlet:namespace />addCommercePriceEntryFm'
						);

						submitForm(addCommercePriceEntryFm);
					}
				},
			},
			title:
				'<liferay-ui:message arguments="<%= HtmlUtil.escape(cpInstance.getSku()) %>" key="add-x-to-price-list" />',
			url:
				'<%= cpInstanceCommercePriceEntryDisplayContext.getItemSelectorUrl() %>',
		});

		itemSelectorDialog.open();
	});
</aui:script>