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
%>

<portlet:actionURL name="/cp_definitions/edit_cp_instance_commerce_price_entry" var="addCommercePriceEntryURL" />

<aui:form action="<%= addCommercePriceEntryURL %>" cssClass="hide" name="addCommercePriceEntryFm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD_MULTIPLE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="cpInstanceId" type="hidden" value="<%= cpInstanceId %>" />
	<aui:input name="commercePriceListIds" type="hidden" value="" />
</aui:form>

<div id="<portlet:namespace />entriesContainer">
	<aui:form action="<%= cpInstanceCommercePriceEntryDisplayContext.getPortletURL() %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="deleteCommercePriceEntryIds" type="hidden" />

		<liferay-ui:error exception="<%= DuplicateCommercePriceEntryException.class %>" message="one-or-more-selected-entries-already-exist" />

		<frontend-data-set:classic-display
			contextParams='<%=
				HashMapBuilder.<String, String>put(
					"cpDefinitionId", String.valueOf(cpInstance.getCPDefinitionId())
				).put(
					"cpInstanceId", String.valueOf(cpInstanceId)
				).build()
			%>'
			creationMenu="<%= cpInstanceCommercePriceEntryDisplayContext.getCreationMenu() %>"
			dataProviderKey="<%= CommercePricingFDSNames.INSTANCE_PRICE_ENTRIES %>"
			formName="fm"
			id="<%= CommercePricingFDSNames.INSTANCE_PRICE_ENTRIES %>"
			itemsPerPage="<%= 10 %>"
			style="stacked"
		/>
	</aui:form>
</div>

<aui:script sandbox="<%= true %>">
	Liferay.on('<portlet:namespace />addCommercePriceEntry', () => {
		const openerWindow = Liferay.Util.getOpener();

		openerWindow.Liferay.Util.openSelectionModal({
			multiple: true,
			onSelect: (selectedItems) => {
				if (!selectedItems || !selectedItems.length) {
					return;
				}

				const commercePriceListIds = document.getElementById(
					'<portlet:namespace />commercePriceListIds'
				);

				if (commercePriceListIds) {
					commercePriceListIds.value = selectedItems;
				}

				var addCommercePriceEntryFm = window.document.querySelector(
					'#<portlet:namespace />addCommercePriceEntryFm'
				);

				if (addCommercePriceEntryFm) {
					submitForm(addCommercePriceEntryFm);
				}
			},
			selectEventName: 'priceListsSelectItem',
			title:
				'<liferay-ui:message arguments="<%= HtmlUtil.escape(cpInstance.getSku()) %>" key="add-x-to-price-list" />',
			url:
				'<%= cpInstanceCommercePriceEntryDisplayContext.getItemSelectorUrl() %>',
		});
	});
</aui:script>