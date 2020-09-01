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

<portlet:actionURL name="editProductDefinition" var="editProductDefinitionActionURL" />

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

<aui:script use="liferay-item-selector-dialog">
	Liferay.on('<portlet:namespace />selectCommerceAccountGroup', function () {
		var itemSelectorDialog = new A.LiferayItemSelectorDialog({
			eventName: 'accountGroupSelectItem',
			on: {
				selectedItemChange: function (event) {
					var <portlet:namespace />addCommerceAccountGroupIds = [];

					var selectedItems = event.newVal;

					if (selectedItems) {
						var A = AUI();

						A.Array.each(selectedItems, function (
							item,
							index,
							selectedItems
						) {
							<portlet:namespace />addCommerceAccountGroupIds.push(
								item.commerceAccountGroupId
							);
						});

						window.document.querySelector(
							'#<portlet:namespace />commerceAccountGroupIds'
						).value = <portlet:namespace />addCommerceAccountGroupIds;

						var form = window.document.querySelector(
							'#<portlet:namespace />fm'
						);

						submitForm(form);
					}
				},
			},
			title: '<liferay-ui:message key="select-account-group" />',
			url:
				'<%= cpDefinitionsDisplayContext.getAccountGroupItemSelectorUrl() %>',
		});

		itemSelectorDialog.open();
	});

	Liferay.on('<portlet:namespace />selectCommerceChannel', function () {
		var itemSelectorDialog = new A.LiferayItemSelectorDialog({
			eventName: 'channelSelectItem',
			on: {
				selectedItemChange: function (event) {
					var <portlet:namespace />addCommerceChannelIds = [];

					var selectedItems = event.newVal;

					if (selectedItems) {
						var A = AUI();

						A.Array.each(selectedItems, function (
							item,
							index,
							selectedItems
						) {
							<portlet:namespace />addCommerceChannelIds.push(
								item.commerceChannelId
							);
						});

						window.document.querySelector(
							'#<portlet:namespace />commerceChannelIds'
						).value = <portlet:namespace />addCommerceChannelIds;

						var form = window.document.querySelector(
							'#<portlet:namespace />fm'
						);

						submitForm(form);
					}
				},
			},
			title: '<liferay-ui:message key="select-channel" />',
			url: '<%= cpDefinitionsDisplayContext.getChannelItemSelectorUrl() %>',
		});

		itemSelectorDialog.open();
	});
</aui:script>