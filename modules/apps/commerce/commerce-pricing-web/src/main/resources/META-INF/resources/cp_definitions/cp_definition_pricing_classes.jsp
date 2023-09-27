<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CPDefinitionPricingClassDisplayContext cpDefinitionPricingClassDisplayContext = (CPDefinitionPricingClassDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinition cpDefinition = cpDefinitionPricingClassDisplayContext.getCPDefinition();

CProduct cProduct = cpDefinition.getCProduct();
%>

<c:if test="<%= cpDefinitionPricingClassDisplayContext.hasPermission(permissionChecker, cpDefinition, ActionKeys.VIEW) %>">
	<div class="pt-4" id="<portlet:namespace />productPricingClassRelsContainer"><portlet:actionURL name="/cp_definitions/edit_cp_definition" var="editProductDefinitionProductGroupsActionURL" />

		<aui:form action="<%= editProductDefinitionProductGroupsActionURL %>" method="post" name="fm">
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
			<aui:input name="cpDefinitionId" type="hidden" value="<%= cpDefinitionPricingClassDisplayContext.getCPDefinitionId() %>" />
			<aui:input name="workflowAction" type="hidden" value="<%= WorkflowConstants.ACTION_SAVE_DRAFT %>" />
			<div id="item-finder-root"></div>

			<aui:script require="commerce-frontend-js/components/item_finder/entry as itemFinder, commerce-frontend-js/utilities/slugify as slugify, commerce-frontend-js/utilities/eventsDefinitions as events, commerce-frontend-js/utilities/index as utilities">
				var headers = utilities.fetchParams.headers;
				var productId = <%= cpDefinition.getCProductId() %>;
				var productExternalReferenceCode = '<%= cProduct.getExternalReferenceCode() %>';

				function selectItem(productPricingClass) {
					return Liferay.Util.fetch(
						'/o/headless-commerce-admin-catalog/v1.0/product-groups/' +
							productPricingClass.id +
							'/product-group-products/',
						{
							body: JSON.stringify({
								productExternalReferenceCode: productExternalReferenceCode,
								productId: productId,
								productGroupExternalReferenceCode:
									productPricingClass.externalReferenceCode,
								productGroupId: productPricingClass.id,
							}),
							headers: headers,
							method: 'POST',
						}
					).then((response) => {
						if (!response.ok) {
							return response.json().then((data) => {
								return Promise.reject(data.errorDescription);
							});
						}
						Liferay.fire(events.FDS_UPDATE_DISPLAY, {
							id: '<%= CommercePricingFDSNames.PRODUCT_PRICING_CLASSES %>',
						});
						return null;
					});
				}

				function addNewItem(name) {
					var nameDefinition = {};

					nameDefinition[themeDisplay.getLanguageId()] = name;

					if (themeDisplay.getLanguageId() !== themeDisplay.getDefaultLanguageId()) {
						nameDefinition[themeDisplay.getDefaultLanguageId()] = name;
					}

					return Liferay.Util.fetch(
						'/o/headless-commerce-admin-catalog/v1.0/product-groups',
						{
							body: JSON.stringify({
								title: nameDefinition,
							}),
							headers: headers,
							method: 'POST',
						}
					)
						.then((response) => {
							if (response.ok) {
								return response.json();
							}

							return response.json().then((data) => {
								return Promise.reject(data.message);
							});
						})
						.then(selectItem);
				}

				function getSelectedItems() {
					return Promise.resolve([]);
				}

				itemFinder.default('itemFinder', 'item-finder-root', {
					apiUrl: '/o/headless-commerce-admin-catalog/v1.0/product-groups',
					createNewItemLabel: '<%= LanguageUtil.get(request, "create-new") %>',
					getSelectedItems: getSelectedItems,
					inputPlaceholder:
						'<%= LanguageUtil.get(request, "find-or-create-a-product-group") %>',
					itemSelectedMessage:
						'<%= LanguageUtil.get(request, "product-group-selected") %>',
					itemsKey: 'id',
					linkedDataSetsId: [
						'<%= CommercePricingFDSNames.PRODUCT_PRICING_CLASSES %>',
					],
					onItemCreated: addNewItem,
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
				elementClasses="mt-4"
				title='<%= LanguageUtil.get(request, "product-groups") %>'
			>
				<frontend-data-set:classic-display
					contextParams='<%=
						HashMapBuilder.<String, String>put(
							"cpDefinitionId", String.valueOf(cpDefinitionPricingClassDisplayContext.getCPDefinitionId())
						).build()
					%>'
					dataProviderKey="<%= CommercePricingFDSNames.PRODUCT_PRICING_CLASSES %>"
					id="<%= CommercePricingFDSNames.PRODUCT_PRICING_CLASSES %>"
					itemsPerPage="<%= 10 %>"
					selectedItemsKey="pricingClassId"
				/>
			</commerce-ui:panel>
		</aui:form>
	</div>
</c:if>