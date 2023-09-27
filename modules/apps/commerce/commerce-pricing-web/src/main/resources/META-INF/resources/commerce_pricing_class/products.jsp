<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommercePricingClassCPDefinitionDisplayContext commercePricingClassCPDefinitionDisplayContext = (CommercePricingClassCPDefinitionDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommercePricingClass commercePricingClass = commercePricingClassCPDefinitionDisplayContext.getCommercePricingClass();

long commercePricingClassId = commercePricingClass.getCommercePricingClassId();

boolean hasPermission = commercePricingClassCPDefinitionDisplayContext.hasPermission();
%>

<portlet:actionURL name="/commerce_pricing_classes/edit_commerce_pricing_class" var="editCommercePricingClassActionURL" />

<aui:form action="<%= editCommercePricingClassActionURL %>" cssClass="pt-4" method="post" name="fm">
	<c:if test="<%= hasPermission %>">
		<div class="row">
			<div class="col-12 pt-4">
				<div id="item-finder-root"></div>

				<aui:script require="commerce-frontend-js/components/item_finder/entry as itemFinder, commerce-frontend-js/utilities/slugify as slugify, commerce-frontend-js/utilities/eventsDefinitions as events, commerce-frontend-js/ServiceProvider/index as ServiceProvider">
					var CommerceProductGroupsResource = ServiceProvider.default.AdminCatalogAPI(
						'v1'
					);

					var id = <%= commercePricingClass.getCommercePricingClassId() %>;
					var pricingClassExternalReferenceCode =
						'<%= HtmlUtil.escapeJS(commercePricingClass.getExternalReferenceCode()) %>';

					function selectItem(product) {
						var productData = {
							productExternalReferenceCode: product.externalReferenceCode,
							productId: product.productId,
							productGroupExternalReferenceCode: pricingClassExternalReferenceCode,
							productGroupId: id,
						};

						return CommerceProductGroupsResource.addProductToProductGroup(
							id,
							productData
						)
							.then(() => {
								Liferay.fire(events.FDS_UPDATE_DISPLAY, {
									id:
										'<%= CommercePricingFDSNames.PRICING_CLASSES_PRODUCT_DEFINITIONS %>',
								});
							})
							.catch((error) => {
								return Promise.reject(error.errorDescription);
							});
					}

					function getSelectedItems() {
						return Promise.resolve([]);
					}

					itemFinder.default('itemFinder', 'item-finder-root', {
						apiUrl:
							'/o/headless-commerce-admin-catalog/v1.0/products?nestedFields=catalog',
						getSelectedItems: getSelectedItems,
						inputPlaceholder: '<%= LanguageUtil.get(request, "find-a-product") %>',
						itemSelectedMessage: '<%= LanguageUtil.get(request, "product-selected") %>',
						linkedDataSetsId: [
							'<%= CommercePricingFDSNames.PRICING_CLASSES_PRODUCT_DEFINITIONS %>',
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
							{
								fieldName: ['catalog', 'name'],
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
					<frontend-data-set:classic-display
						contextParams='<%=
							HashMapBuilder.<String, String>put(
								"commercePricingClassId", String.valueOf(commercePricingClassId)
							).build()
						%>'
						dataProviderKey="<%= CommercePricingFDSNames.PRICING_CLASSES_PRODUCT_DEFINITIONS %>"
						formName="fm"
						id="<%= CommercePricingFDSNames.PRICING_CLASSES_PRODUCT_DEFINITIONS %>"
						itemsPerPage="<%= 10 %>"
					/>
				</commerce-ui:panel>
			</div>
		</div>
	</c:if>
</aui:form>