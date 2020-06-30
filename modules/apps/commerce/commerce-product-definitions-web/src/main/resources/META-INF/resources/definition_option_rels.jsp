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

		<aui:script require="commerce-frontend-js/components/item_finder/entry.es as itemFinder, commerce-frontend-js/utilities/index.es as utilities, commerce-frontend-js/utilities/eventsDefinitions.es as events">
			var headers = new Headers({
				Accept: 'application/json',
				'Content-Type': 'application/json',
				'x-csrf-token': Liferay.authToken
			});

			var productId = <%= cpDefinition.getCProductId() %>;

			function selectItem(option) {
				return fetch(
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
								productOptionValues: []
							}
						]),
						credentials: 'include',
						headers: headers,
						method: 'POST'
					}
				).then(function() {
					Liferay.fire(events.UPDATE_DATASET_DISPLAY, {
						id:
							'<%= CommerceProductDataSetConstants.COMMERCE_DATA_SET_KEY_PRODUCT_OPTIONS %>'
					});
					return option.id;
				});
			}

			function addNewItem(name) {
				return fetch('/o/headless-commerce-admin-catalog/v1.0/options', {
					body: JSON.stringify({
						fieldType: 'select',
						key: utilities.slugify(encodeURIComponent(name)),
						name: {
							[themeDisplay.getLanguageId()]: name
						}
					}),
					credentials: 'include',
					headers: headers,
					method: 'POST'
				})
					.then(function(response) {
						if (response.ok) {
							return response.json();
						}

						return response.json().then(function(data) {
							return Promise.reject(data.message);
						});
					})
					.then(selectItem);
			}

			function getSelectedItems() {
				return fetch(
					'/o/headless-commerce-admin-catalog/v1.0/products/' +
						productId +
						'/productOptions/',
					{
						credentials: 'include',
						headers: headers
					}
				)
					.then(function(response) {
						return response.json();
					})
					.then(function(jsonResponse) {
						return jsonResponse.items.map(function(option) {
							return option.optionId;
						});
					});
			}

			itemFinder.default('itemFinder', 'item-finder-root', {
				apiUrl: '/o/headless-commerce-admin-catalog/v1.0/options',
				createNewItemLabel: '<%= LanguageUtil.get(request, "create-new") %>',
				getSelectedItems: getSelectedItems,
				inputPlaceholder:
					'<%= LanguageUtil.get(request, "find-or-create-an-option") %>',
				itemsKey: 'id',
				linkedDatasetsId: [
					'<%= CommerceProductDataSetConstants.COMMERCE_DATA_SET_KEY_PRODUCT_OPTIONS %>'
				],
				onItemCreated: addNewItem,
				onItemSelected: selectItem,
				pageSize: 10,
				panelHeaderLabel: '<%= LanguageUtil.get(request, "add-options") %>',
				portletId: '<%= portletDisplay.getRootPortletId() %>',
				schema: {
					itemTitle: ['name', themeDisplay.getLanguageId()]
				},
				spritemap: '<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg',
				titleLabel: '<%= LanguageUtil.get(request, "add-an-existing-option") %>'
			});
		</aui:script>

		<commerce-ui:panel
			bodyClasses="p-0"
			elementClasses="mt-4"
			title='<%= LanguageUtil.get(request, "options") %>'
		>

			<%
			Map<String, String> contextParams = new HashMap<>();

			contextParams.put("cpDefinitionId", String.valueOf(cpDefinitionOptionRelDisplayContext.getCPDefinitionId()));
			%>

			<commerce-ui:dataset-display
				contextParams="<%= contextParams %>"
				dataProviderKey="<%= CommerceProductDataSetConstants.COMMERCE_DATA_SET_KEY_PRODUCT_OPTIONS %>"
				id="<%= CommerceProductDataSetConstants.COMMERCE_DATA_SET_KEY_PRODUCT_OPTIONS %>"
				itemsPerPage="<%= 10 %>"
				namespace="<%= renderResponse.getNamespace() %>"
				pageNumber="<%= 1 %>"
				portletURL="<%= currentURLObj %>"
			/>
		</commerce-ui:panel>
	</div>
</c:if>