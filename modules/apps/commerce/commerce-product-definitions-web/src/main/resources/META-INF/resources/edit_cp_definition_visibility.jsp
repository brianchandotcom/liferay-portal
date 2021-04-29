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
CPDefinitionsDisplayContext cpDefinitionsDisplayContext = (CPDefinitionsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinition cpDefinition = cpDefinitionsDisplayContext.getCPDefinition();

long cpDefinitionId = cpDefinitionsDisplayContext.getCPDefinitionId();

Map<String, String> contextParams = HashMapBuilder.<String, String>put(
	"cpDefinitionId", String.valueOf(cpDefinitionId)
).build();
%>

<portlet:actionURL name="/cp_definitions/edit_cp_definition" var="editProductDefinitionActionURL" />

<aui:form action="<%= editProductDefinitionActionURL %>" cssClass="pt-4" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="updateVisibility" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="cpDefinitionId" type="hidden" value="<%= String.valueOf(cpDefinitionId) %>" />
	<aui:input name="commerceAccountGroupIds" type="hidden" value="" />
	<aui:input name="commerceChannelIds" type="hidden" value="" />

	<commerce-ui:panel
		bodyClasses="p-0"
		collapsed="<%= !cpDefinition.isChannelFilterEnabled() %>"
		collapseLabel='<%= LanguageUtil.get(request, "filter") %>'
		collapseSwitchName='<%= liferayPortletResponse.getNamespace() + "channelFilterEnabled" %>'
		title='<%= LanguageUtil.get(request, "channels") %>'
	>
		<clay:data-set-display
			contextParams="<%= contextParams %>"
			creationMenu="<%= cpDefinitionsDisplayContext.getChannelsCreationMenu() %>"
			dataProviderKey="<%= CommerceProductDataSetConstants.COMMERCE_DATA_SET_KEY_PRODUCT_CHANNELS %>"
			formId="fm"
			id="<%= CommerceProductDataSetConstants.COMMERCE_DATA_SET_KEY_PRODUCT_CHANNELS %>"
			itemsPerPage="<%= 10 %>"
			namespace="<%= liferayPortletResponse.getNamespace() %>"
			pageNumber="<%= 1 %>"
			portletURL="<%= currentURLObj %>"
		/>
	</commerce-ui:panel>

	<commerce-ui:panel
		bodyClasses="p-0"
		collapsed="<%= !cpDefinition.isAccountGroupFilterEnabled() %>"
		collapseLabel='<%= LanguageUtil.get(request, "filter") %>'
		collapseSwitchName='<%= liferayPortletResponse.getNamespace() + "accountGroupFilterEnabled" %>'
		title='<%= LanguageUtil.get(request, "account-groups") %>'
	>
		<clay:data-set-display
			contextParams="<%= contextParams %>"
			creationMenu="<%= cpDefinitionsDisplayContext.getAccountGroupsCreationMenu() %>"
			dataProviderKey="<%= CommerceProductDataSetConstants.COMMERCE_DATA_SET_KEY_PRODUCT_ACCOUNT_GROUPS %>"
			formId="fm"
			id="<%= CommerceProductDataSetConstants.COMMERCE_DATA_SET_KEY_PRODUCT_ACCOUNT_GROUPS %>"
			itemsPerPage="<%= 10 %>"
			namespace="<%= liferayPortletResponse.getNamespace() %>"
			pageNumber="<%= 1 %>"
			portletURL="<%= currentURLObj %>"
		/>
	</commerce-ui:panel>
</aui:form>

<aui:script sandbox="<%= true %>">
	const eventHandlers = [];

	const selectCommerceAccountGroupHandler = Liferay.on(
		'<portlet:namespace />selectCommerceAccountGroup',
		() => {
			Liferay.Util.openSelectionModal({
				multiple: true,
				onSelect: (selectedItems) => {
					if (!selectedItems || !selectedItems.length) {
						return;
					}

					const commerceAccountGroupIds = [];

					selectedItems.map((item) => {
						commerceAccountGroupIds.push(item.commerceAccountGroupId);
					});

					const commerceAccountGroupIdsInput = document.getElementById(
						'<portlet:namespace />commerceAccountGroupIds'
					);

					if (commerceAccountGroupIdsInput) {
						commerceAccountGroupIdsInput.value = commerceAccountGroupIds;
					}

					const form = document.getElementById('<portlet:namespace />fm');

					if (form) {
						submitForm(form);
					}
				},
				title: '<liferay-ui:message key="select-account-group" />',
				url:
					'<%= cpDefinitionsDisplayContext.getAccountGroupItemSelectorUrl() %>',
			});
		}
	);

	eventHandlers.push(selectCommerceAccountGroupHandler);

	const selectCommerceChannelHandler = Liferay.on(
		'<portlet:namespace />selectCommerceChannel',
		() => {
			Liferay.Util.openSelectionModal({
				multiple: true,
				onSelect: (selectedItems) => {
					if (!selectedItems || !selectedItems.length) {
						return;
					}

					const commerceChannelIds = [];

					selectedItems.map((item) => {
						commerceChannelIds.push(item.commerceChannelId);
					});

					const commerceChannelIdsInput = document.getElementById(
						'<portlet:namespace />commerceChannelIds'
					);

					if (commerceChannelIdsInput) {
						commerceChannelIdsInput.value = commerceChannelIds;
					}

					const form = document.getElementById('<portlet:namespace />fm');

					if (form) {
						submitForm(form);
					}
				},
				title: '<liferay-ui:message key="select-channel" />',
				url:
					'<%= cpDefinitionsDisplayContext.getChannelItemSelectorUrl() %>',
			});
		}
	);

	eventHandlers.push(selectCommerceChannelHandler);

	Liferay.on('destroyPortlet', () => {
		eventHandlers.forEach((eventHandler) => {
			eventHandler.detach();
		});
	});
</aui:script>