<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
						Liferay.fire(events.FDS_UPDATE_DISPLAY, {
							id: '<%= CommerceProductFDSNames.PRODUCT_OPTIONS %>',
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
				linkedDataSetsId: ['<%= CommerceProductFDSNames.PRODUCT_OPTIONS %>'],
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
				spritemap: '<%= themeDisplay.getPathThemeSpritemap() %>',
				titleLabel: '<%= LanguageUtil.get(request, "add-existing-option") %>',
			});
		</aui:script>

		<commerce-ui:panel
			bodyClasses="p-0"
			elementClasses="mt-4"
			title='<%= LanguageUtil.get(request, "options") %>'
		>
			<portlet:actionURL name="/cp_definitions/edit_cp_definition" var="editProductDefinitionOptionRelsActionURL" />

			<aui:form action="<%= editProductDefinitionOptionRelsActionURL %>" method="post" name="fm">
				<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
				<aui:input name="cpDefinitionId" type="hidden" value="<%= cpDefinitionOptionRelDisplayContext.getCPDefinitionId() %>" />
				<aui:input name="workflowAction" type="hidden" value="<%= WorkflowConstants.ACTION_SAVE_DRAFT %>" />

				<frontend-data-set:classic-display
					contextParams='<%=
						HashMapBuilder.<String, String>put(
							"cpDefinitionId", String.valueOf(cpDefinitionOptionRelDisplayContext.getCPDefinitionId())
						).build()
					%>'
					dataProviderKey="<%= CommerceProductFDSNames.PRODUCT_OPTIONS %>"
					id="<%= CommerceProductFDSNames.PRODUCT_OPTIONS %>"
					itemsPerPage="<%= 10 %>"
					selectedItemsKey="cpdefinitionOptionRelId"
				/>
			</aui:form>
		</commerce-ui:panel>
	</div>
</c:if>