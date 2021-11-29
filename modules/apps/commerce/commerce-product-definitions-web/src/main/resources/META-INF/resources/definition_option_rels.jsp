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
CPDefinitionOptionRelDisplayContext cpDefinitionOptionRelDisplayContext = (CPDefinitionOptionRelDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinition cpDefinition = cpDefinitionOptionRelDisplayContext.getCPDefinition();
%>

<c:if test="<%= CommerceCatalogPermission.contains(permissionChecker, cpDefinitionOptionRelDisplayContext.getCPDefinition(), ActionKeys.VIEW) %>">
	<div class="pt-4" id="<portlet:namespace />productOptionRelsContainer">
		<div id="item-finder-root"></div>

		<aui:script require="commerce-frontend-js/components/item_finder/entry as itemFinder, commerce-frontend-js/utilities/slugify as slugify, commerce-frontend-js/utilities/eventsDefinitions as events, commerce-frontend-js/utilities/index as utilities">
			var headers = utilities.fetchParams.headers;
			var productId = <%= cpDefinition.getCProductId() %>;

			function selectItem(option) {
				return Liferay.Util.fetch(
					'/o/headless-commerce-admin-catalog/v1.0/products/' +
						productId +
						'/productOptions/',
					{
						body: JSON.stringify([
							{
								facetable: option.facetable,
								fieldType: option.fieldType,
								key: option.key,
								name: option.name,
								optionId: option.id,
								required: option.required,
								skuContributor: option.skuContributor,
								productOptionValues: [],
							},
						]),
						headers: headers,
						method: 'POST',
					}
				)
					.then((response) => {
						if (response.ok) {
							return response.json();
						}

						return response.json().then((data) => {
							return Promise.reject(data.errorDescription);
						});
					})
					.then((e) => {
						Liferay.fire(events.UPDATE_DATASET_DISPLAY, {
							id:
								'<%= CommerceProductDataSetConstants.COMMERCE_DATA_SET_KEY_PRODUCT_OPTIONS %>',
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
					'/o/headless-commerce-admin-catalog/v1.0/options',
					{
						body: JSON.stringify({
							fieldType: 'select',
							key: slugify.default(name),
							name: nameDefinition,
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
							return Promise.reject(data.errorDescription);
						});
					})
					.then(selectItem);
			}

			function getSelectedItems() {
				return Promise.resolve([]);
			}

			itemFinder.default('itemFinder', 'item-finder-root', {
				apiUrl: '/o/headless-commerce-admin-catalog/v1.0/options',
				createNewItemLabel: '<%= LanguageUtil.get(request, "create-new") %>',
				getSelectedItems: getSelectedItems,
				inputPlaceholder:
					'<%= LanguageUtil.get(request, "find-or-create-an-option") %>',
				itemCreatedMessage: '<%= LanguageUtil.get(request, "option-created") %>',
				itemSelectedMessage: '<%= LanguageUtil.get(request, "option-selected") %>',
				itemsKey: 'id',
				linkedDatasetsId: [
					'<%= CommerceProductDataSetConstants.COMMERCE_DATA_SET_KEY_PRODUCT_OPTIONS %>',
				],
				multiSelectableEntries: true,
				onItemCreated: addNewItem,
				onItemSelected: selectItem,
				pageSize: 10,
				panelHeaderLabel: '<%= LanguageUtil.get(request, "add-options") %>',
				portletId: '<%= portletDisplay.getRootPortletId() %>',
				schema: [
					{
						fieldName: ['name', 'LANG'],
					},
				],
				spritemap: '<%= themeDisplay.getPathThemeImages() %>/clay/icons.svg',
				titleLabel: '<%= LanguageUtil.get(request, "add-existing-option") %>',
			});
		</aui:script>

		<commerce-ui:panel
			bodyClasses="p-0"
			elementClasses="mt-4"
			title='<%= LanguageUtil.get(request, "options") %>'
		>
			<aui:form action="<%= cpDefinitionOptionRelDisplayContext.getPortletURL() %>" method="post" name="fm">
				<clay:data-set-display
					contextParams='<%=
						HashMapBuilder.<String, String>put(
							"cpDefinitionId", String.valueOf(cpDefinitionOptionRelDisplayContext.getCPDefinitionId())
						).build()
					%>'
					dataProviderKey="<%= CommerceProductDataSetConstants.COMMERCE_DATA_SET_KEY_PRODUCT_OPTIONS %>"
					id="<%= CommerceProductDataSetConstants.COMMERCE_DATA_SET_KEY_PRODUCT_OPTIONS %>"
					itemsPerPage="<%= 10 %>"
					namespace="<%= liferayPortletResponse.getNamespace() %>"
					pageNumber="<%= 1 %>"
					portletURL="<%= currentURLObj %>"
					selectedItemsKey="cpdefinitionOptionRelId"
				/>
			</aui:form>
		</commerce-ui:panel>
	</div>
</c:if>