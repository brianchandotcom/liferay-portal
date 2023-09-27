<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommercePriceListDisplayContext commercePriceListDisplayContext = (CommercePriceListDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommercePriceModifier commercePriceModifier = commercePriceListDisplayContext.getCommercePriceModifier();
long commercePriceModifierId = commercePriceListDisplayContext.getCommercePriceModifierId();
%>

<c:if test="<%= commercePriceListDisplayContext.hasPermission(commercePriceListDisplayContext.getCommercePriceListId(), ActionKeys.UPDATE) %>">
	<div class="row">
		<div class="col-12">
			<div id="item-finder-root"></div>

			<aui:script require="commerce-frontend-js/components/item_finder/entry as itemFinder, commerce-frontend-js/utilities/slugify as slugify, commerce-frontend-js/utilities/eventsDefinitions as events, commerce-frontend-js/ServiceProvider/index as ServiceProvider">
				var CommercePriceModifierProductsResource = ServiceProvider.default.AdminPricingAPI(
					'v2'
				);

				var id = <%= commercePriceModifierId %>;
				var priceModifierExternalReferenceCode =
					'<%= HtmlUtil.escapeJS(commercePriceModifier.getExternalReferenceCode()) %>';

				function selectItem(product) {
					var productData = {
						productExternalReferenceCode: product.externalReferenceCode,
						productId: product.id,
						priceModifierExternalReferenceCode: priceModifierExternalReferenceCode,
						priceModifierId: id,
					};

					return CommercePriceModifierProductsResource.addPriceModifierProduct(
						id,
						productData
					)
						.then(() => {
							Liferay.fire(events.FDS_UPDATE_DISPLAY, {
								id:
									'<%= CommercePricingFDSNames.PRICE_MODIFIER_PRODUCT_DEFINITIONS %>',
							});
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
						'/o/headless-commerce-admin-catalog/v1.0/products?filter=catalogId eq <%= commercePriceListDisplayContext.getCommerceCatalogId() %>',
					getSelectedItems: getSelectedItems,
					inputPlaceholder: '<%= LanguageUtil.get(request, "find-a-product") %>',
					itemSelectedMessage: '<%= LanguageUtil.get(request, "product-selected") %>',
					linkedDataSetsId: [
						'<%= CommercePricingFDSNames.PRICE_MODIFIER_PRODUCT_DEFINITIONS %>',
					],
					itemCreation: false,
					itemsKey: 'id',
					onItemSelected: selectItem,
					pageSize: 10,
					panelHeaderLabel: '<%= LanguageUtil.get(request, "add-products") %>',
					portletId: '<%= portletDisplay.getRootPortletId() %>',
					schema: [
						{
							fieldName: ['name', 'LANG'],
						},
						{
							fieldName: 'productId',
						},
					],
					spritemap: '<%= themeDisplay.getPathThemeSpritemap() %>',
					titleLabel: '<%= LanguageUtil.get(request, "add-existing-product") %>',
				});
			</aui:script>
		</div>

		<div class="col-12">
			<commerce-ui:panel
				bodyClasses="p-0"
				title='<%= LanguageUtil.get(request, "products") %>'
			>
				<frontend-data-set:headless-display
					apiURL="<%= commercePriceListDisplayContext.getPriceModifierCPDefinitionAPIURL() %>"
					fdsActionDropdownItems="<%= commercePriceListDisplayContext.getPriceModifierCPDefinitionFDSActionDropdownItems() %>"
					formName="fm"
					id="<%= CommercePricingFDSNames.PRICE_MODIFIER_PRODUCT_DEFINITIONS %>"
					itemsPerPage="<%= 10 %>"
				/>
			</commerce-ui:panel>
		</div>
	</div>
</c:if>