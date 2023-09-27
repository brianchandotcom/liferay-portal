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
				var CommercePriceModifierCategoriesResource = ServiceProvider.default.AdminPricingAPI(
					'v2'
				);

				var id = <%= commercePriceModifierId %>;
				var priceModifierExternalReferenceCode =
					'<%= HtmlUtil.escapeJS(commercePriceModifier.getExternalReferenceCode()) %>';

				function selectItem(category) {
					var categoryData = {
						categoryExternalReferenceCode: category.externalReferenceCode,
						categoryId: category.id,
						priceModifierExternalReferenceCode: priceModifierExternalReferenceCode,
						priceModifierId: id,
					};

					return CommercePriceModifierCategoriesResource.addPriceModifierCategory(
						id,
						categoryData
					)
						.then(() => {
							Liferay.fire(events.FDS_UPDATE_DISPLAY, {
								id: '<%= CommercePricingFDSNames.PRICE_MODIFIER_CATEGORIES %>',
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
						'/o/headless-admin-taxonomy/v1.0/taxonomy-categories/0/taxonomy-categories',
					getSelectedItems: getSelectedItems,
					inputPlaceholder: '<%= LanguageUtil.get(request, "find-a-category") %>',
					itemSelectedMessage:
						'<%= LanguageUtil.get(request, "category-selected") %>',
					itemsKey: 'id',
					itemCreation: false,
					linkedDataSetsId: [
						'<%= CommercePricingFDSNames.PRICE_MODIFIER_CATEGORIES %>',
					],
					onItemSelected: selectItem,
					pageSize: 10,
					panelHeaderLabel: '<%= LanguageUtil.get(request, "select-category") %>',
					portletId: '<%= portletDisplay.getRootPortletId() %>',
					schema: [
						{
							fieldName: ['name'],
						},
					],
					spritemap: '<%= themeDisplay.getPathThemeSpritemap() %>',
					titleLabel: '<%= LanguageUtil.get(request, "add-existing-category") %>',
				});
			</aui:script>
		</div>

		<div class="col-12">
			<commerce-ui:panel
				bodyClasses="p-0"
				title='<%= LanguageUtil.get(request, "categories") %>'
			>
				<frontend-data-set:headless-display
					apiURL="<%= commercePriceListDisplayContext.getPriceModifierCategoriesAPIURL() %>"
					fdsActionDropdownItems="<%= commercePriceListDisplayContext.getPriceModifierCategoryFDSActionDropdownItems() %>"
					formName="fm"
					id="<%= CommercePricingFDSNames.PRICE_MODIFIER_CATEGORIES %>"
					itemsPerPage="<%= 10 %>"
				/>
			</commerce-ui:panel>
		</div>
	</div>
</c:if>