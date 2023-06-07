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
CommercePriceEntryDisplayContext commercePriceEntryDisplayContext = (CommercePriceEntryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommercePriceList commercePriceList = commercePriceEntryDisplayContext.getCommercePriceList();
long commercePriceListId = commercePriceEntryDisplayContext.getCommercePriceListId();

String dataSetId = CommercePricingFDSNames.PRICE_LIST_ENTRIES;

if (CommercePriceListConstants.TYPE_PROMOTION.equals(commercePriceEntryDisplayContext.getCommercePriceListType(portletName))) {
	dataSetId = CommercePricingFDSNames.PROMOTION_ENTRIES;
}
%>

<c:if test="<%= commercePriceEntryDisplayContext.hasPermission(commercePriceListId, ActionKeys.UPDATE) %>">
	<div class="pt-4 row">
		<div class="col-12">
			<div id="item-finder-root"></div>

			<aui:script require="commerce-frontend-js/components/item_finder/entry as itemFinder, commerce-frontend-js/utilities/slugify as slugify, commerce-frontend-js/utilities/eventsDefinitions as events, commerce-frontend-js/ServiceProvider/index as ServiceProvider">
				var CommercePriceEntriesResource = ServiceProvider.default.AdminPricingAPI(
					'v2'
				);

				var id = <%= commercePriceListId %>;
				var priceListExternalReferenceCode =
					'<%= HtmlUtil.escapeJS(commercePriceList.getExternalReferenceCode()) %>';

				function selectItem(sku) {
					var priceEntryData = {
						price: '0.0',
						priceListExternalReferenceCode: priceListExternalReferenceCode,
						priceListId: id,
						skuExternalReferenceCode: sku.externalReferenceCode,
						skuId: sku.id,
					};

					return CommercePriceEntriesResource.addPriceEntry(id, priceEntryData)
						.then(() => {
							setTimeout(() => {
								Liferay.fire(events.FDS_UPDATE_DISPLAY, {
									id: '<%= dataSetId %>',
								});
							}, 500);
						})
						.catch((error) => {
							return Promise.reject(error);
						});
				}

				function getSelectedItems() {
					return Promise.resolve([]);
				}

				itemFinder.default('itemFinder', 'item-finder-root', {
					apiUrl:
						'/o/headless-commerce-admin-catalog/v1.0/skus?filter=catalogId eq <%= commercePriceEntryDisplayContext.getCommerceCatalogId() %>',
					getSelectedItems: getSelectedItems,
					inputPlaceholder: '<%= LanguageUtil.get(request, "find-a-sku") %>',
					itemSelectedMessage: '<%= LanguageUtil.get(request, "sku-selected") %>',
					linkedDataSetsId: ['<%= dataSetId %>'],
					itemCreation: false,
					itemsKey: 'id',
					onItemSelected: selectItem,
					pageSize: 10,
					panelHeaderLabel: '<%= LanguageUtil.get(request, "add-skus") %>',
					portletId: '<%= portletDisplay.getRootPortletId() %>',
					schema: [
						{
							fieldName: 'sku',
						},
						{
							fieldName: ['productName', 'LANG'],
						},
					],
					spritemap: '<%= themeDisplay.getPathThemeSpritemap() %>',
					titleLabel: '<%= LanguageUtil.get(request, "add-existing-sku") %>',
				});
			</aui:script>
		</div>

		<div class="col-12">
			<commerce-ui:panel
				bodyClasses="p-0"
				title='<%= LanguageUtil.get(request, "entries") %>'
			>
				<frontend-data-set:headless-display
					apiURL="<%= commercePriceEntryDisplayContext.getPriceEntryApiURL() %>"
					fdsActionDropdownItems="<%= commercePriceEntryDisplayContext.getPriceEntriesFDSActionDropdownItems() %>"
					formName="fm"
					id="<%= dataSetId %>"
					itemsPerPage="<%= 10 %>"
					selectedItemsKey="priceEntryId"
				/>
			</commerce-ui:panel>
		</div>
	</div>
</c:if>