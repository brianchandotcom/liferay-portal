<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	<aui:input name="unitOfMeasureKey" type="hidden" value="" />
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
	const eventHandlers = [];

	const openSelectionModal = (key, title, url) => {
		const openerWindow = Liferay.Util.getOpener();

		const unitOfMeasureKeyInput = document.getElementById(
			'<portlet:namespace />unitOfMeasureKey'
		);

		if (key && unitOfMeasureKeyInput) {
			unitOfMeasureKeyInput.value = key;
		}

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
			title,
			url,
		});
	};

	<%
	List<CPInstanceUnitOfMeasure> cpInstanceUnitOfMeasures = cpInstanceCommercePriceEntryDisplayContext.getCPInstanceUnitOfMeasures();

	if (cpInstanceUnitOfMeasures.isEmpty()) {
	%>

		eventHandlers.push(
			Liferay.on('<portlet:namespace />addCommercePriceEntry', () => {
				openSelectionModal(
					null,
					'<liferay-ui:message arguments="<%= HtmlUtil.escape(cpInstance.getSku()) %>" key="add-x-to-price-list" />',
					'<%= cpInstanceCommercePriceEntryDisplayContext.getItemSelectorUrl(null) %>'
				);
			})
		);

		<%
		}
		else {
			for (CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure : cpInstanceUnitOfMeasures) {
				String key = cpInstanceUnitOfMeasure.getKey();
		%>

			eventHandlers.push(
				Liferay.on(
					'<portlet:namespace />addCommercePriceEntry<%= HtmlUtil.escapeJS(key) %>',
					() => {
						openSelectionModal(
							'<%= HtmlUtil.escapeJS(key) %>',
							'<liferay-ui:message arguments='<%= HtmlUtil.escape(cpInstance.getSku() + " " + key) %>' key="add-x-to-price-list" />',
							'<%= cpInstanceCommercePriceEntryDisplayContext.getItemSelectorUrl(key) %>'
						);
					}
				)
			);

	<%
		}
	}
	%>

	Liferay.on('destroyPortlet', () => {
		eventHandlers.forEach((eventHandler) => {
			eventHandler.detach();
		});
	});
</aui:script>