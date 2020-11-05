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
CPDefinitionPricingClassDisplayContext cpDefinitionPricingClassDisplayContext = (CPDefinitionPricingClassDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinition cpDefinition = cpDefinitionPricingClassDisplayContext.getCPDefinition();

CProduct cProduct = cpDefinition.getCProduct();
%>

<c:if test="<%= cpDefinitionPricingClassDisplayContext.hasPermission(permissionChecker, cpDefinition, ActionKeys.VIEW) %>">
	<div class="pt-4" id="<portlet:namespace />productPricingClassRelsContainer">
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
				).then(function (response) {
					if (!response.ok) {
						return response.json().then(function (data) {
							return Promise.reject(data.errorDescription);
						});
					}
					Liferay.fire(events.UPDATE_DATASET_DISPLAY, {
						id:
							'<%= CommercePricingDataSetConstants.COMMERCE_DATA_SET_KEY_PRODUCT_PRICING_CLASSES %>',
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
					.then(function (response) {
						if (response.ok) {
							return response.json();
						}

						return response.json().then(function (data) {
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
				linkedDatasetsId: [
					'<%= CommercePricingDataSetConstants.COMMERCE_DATA_SET_KEY_PRODUCT_PRICING_CLASSES %>',
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
				spritemap: '<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg',
				titleLabel:
					'<%= LanguageUtil.get(request, "add-existing-product-group") %>',
			});
		</aui:script>

		<commerce-ui:panel
			bodyClasses="p-0"
			elementClasses="mt-4"
			title='<%= LanguageUtil.get(request, "product-groups") %>'
		>
			<clay:data-set-display
				contextParams='<%=
					HashMapBuilder.<String, String>put(
						"cpDefinitionId", String.valueOf(cpDefinitionPricingClassDisplayContext.getCPDefinitionId())
					).build()
				%>'
				dataProviderKey="<%= CommercePricingDataSetConstants.COMMERCE_DATA_SET_KEY_PRODUCT_PRICING_CLASSES %>"
				id="<%= CommercePricingDataSetConstants.COMMERCE_DATA_SET_KEY_PRODUCT_PRICING_CLASSES %>"
				itemsPerPage="<%= 10 %>"
				namespace="<%= liferayPortletResponse.getNamespace() %>"
				pageNumber="<%= 1 %>"
				portletURL="<%= currentURLObj %>"
			/>
		</commerce-ui:panel>
	</div>
</c:if>