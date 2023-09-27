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
	<div id="item-finder-root"></div>

	<aui:script require="commerce-frontend-js/components/item_finder/entry as itemFinder, commerce-frontend-js/utilities/slugify as slugify, commerce-frontend-js/utilities/eventsDefinitions as events, commerce-frontend-js/ServiceProvider/index as ServiceProvider">
		var CommercePriceModifierProductGroupsResource = ServiceProvider.default.AdminPricingAPI(
			'v2'
		);

		var id = <%= commercePriceModifierId %>;
		var priceModifierExternalReferenceCode =
			'<%= HtmlUtil.escapeJS(commercePriceModifier.getExternalReferenceCode()) %>';

		function selectItem(productGroup) {
			var productGroupData = {
				productGroupExternalReferenceCode: productGroup.externalReferenceCode,
				productGroupId: productGroup.id,
				priceModifierExternalReferenceCode: priceModifierExternalReferenceCode,
				priceModifierId: id,
			};

			return CommercePriceModifierProductGroupsResource.addPriceModifierProductGroup(
				id,
				productGroupData
			)
				.then(() => {
					Liferay.fire(events.FDS_UPDATE_DISPLAY, {
						id:
							'<%= CommercePricingFDSNames.PRICE_MODIFIER_PRICING_CLASSES %>',
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
			apiUrl: '/o/headless-commerce-admin-catalog/v1.0/product-groups',
			getSelectedItems: getSelectedItems,
			inputPlaceholder:
				'<%= LanguageUtil.get(request, "find-a-product-group") %>',
			itemSelectedMessage:
				'<%= LanguageUtil.get(request, "product-group-selected") %>',
			itemsKey: 'id',
			itemCreation: false,
			linkedDataSetsId: [
				'<%= CommercePricingFDSNames.PRICE_MODIFIER_PRICING_CLASSES %>',
			],
			onItemSelected: selectItem,
			pageSize: 10,
			panelHeaderLabel:
				'<%= LanguageUtil.get(request, "select-product-group") %>',
			portletId: '<%= portletDisplay.getRootPortletId() %>',
			schema: [
				{
					fieldName: ['title', 'LANG'],
				},
			],
			spritemap: '<%= themeDisplay.getPathThemeSpritemap() %>',
			titleLabel:
				'<%= LanguageUtil.get(request, "add-existing-product-group") %>',
		});
	</aui:script>

	<commerce-ui:panel
		bodyClasses="p-0"
		title='<%= LanguageUtil.get(request, "product-groups") %>'
	>
		<frontend-data-set:headless-display
			apiURL="<%= commercePriceListDisplayContext.getPriceModifierPricingClassesAPIURL() %>"
			fdsActionDropdownItems="<%= commercePriceListDisplayContext.getPriceModifierPricingClassFDSActionDropdownItems() %>"
			formName="fm"
			id="<%= CommercePricingFDSNames.PRICE_MODIFIER_PRICING_CLASSES %>"
			itemsPerPage="<%= 10 %>"
		/>
	</commerce-ui:panel>
</c:if>