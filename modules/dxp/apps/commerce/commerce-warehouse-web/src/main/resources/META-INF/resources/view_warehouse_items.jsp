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
CommerceWarehouseItemsDisplayContext commerceWarehouseItemsDisplayContext = (CommerceWarehouseItemsDisplayContext<?>)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
CPInstance cpInstance = commerceWarehouseItemsDisplayContext.getCPInstance();
PortletURL portletURL = commerceWarehouseItemsDisplayContext.getPortletURL();
String title = commerceWarehouseItemsDisplayContext.getTitle();

String backURL = commerceWarehouseItemsDisplayContext.getBackURL();

if (Validator.isNotNull(backURL)) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(backURL);
}

renderResponse.setTitle(title);
%>

<liferay-frontend:management-bar
	searchContainerId="commerceWarehouseItems"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= portletURL %>"
			selectedDisplayStyle="list"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= portletURL %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= commerceWarehouseItemsDisplayContext.getOrderByCol() %>"
			orderByType="<%= commerceWarehouseItemsDisplayContext.getOrderByType() %>"
			orderColumns='<%= new String[] {"name", "quantity"} %>'
			portletURL="<%= portletURL %>"
		/>
	</liferay-frontend:management-bar-filters>
</liferay-frontend:management-bar>

<div class="container-fluid-1280">
	<liferay-ui:search-container
		id="commerceWarehouseItems"
		searchContainer="<%= commerceWarehouseItemsDisplayContext.getSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.commerce.model.CommerceWarehouseItem"
			keyProperty="commerceWarehouseItemId"
			modelVar="commerceWarehouseItem"
		>

			<%
			CommerceWarehouse commerceWarehouse = commerceWarehouseItem.getCommerceWarehouse();

			PortletURL rowURL = renderResponse.createRenderURL();

			rowURL.setParameter("mvcRenderCommandName", "editCommerceWarehouseItem");
			rowURL.setParameter("redirect", currentURL);
			rowURL.setParameter("commerceWarehouseItemId", String.valueOf(commerceWarehouseItem.getCommerceWarehouseItemId()));
			%>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				href="<%= rowURL %>"
				name="name"
				value="<%= HtmlUtil.escape(commerceWarehouse.getName()) %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				property="quantity"
			/>

			<liferay-ui:search-container-column-jsp
				cssClass="entry-action-column"
				path="/warehouse_item_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator markupView="lexicon" />
	</liferay-ui:search-container>
</div>

<c:if test="<%= commerceWarehouseItemsDisplayContext.isShowAddButton() %>">
	<portlet:actionURL name="editCommerceWarehouseItem" var="addCommerceWarehouseItemURL" />

	<aui:form action="<%= addCommerceWarehouseItemURL %>" method="post" name="addCommerceWarehouseItemFm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD_MULTIPLE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="commerceWarehouseIds" type="hidden" />
		<aui:input name="cpInstaceId" type="hidden" value="<%= cpInstance.getCPInstanceId() %>" />
	</aui:form>

	<liferay-frontend:add-menu>
		<liferay-frontend:add-menu-item id="addCommerceWarehouseItem" title='<%= LanguageUtil.get(request, "add-to-a-warehouse") %>' url="javascript:;" />
	</liferay-frontend:add-menu>

	<aui:script use="liferay-item-selector-dialog">
		$('#<portlet:namespace />addCommerceWarehouseItem').on(
			'click',
			function(event) {
				event.preventDefault();

				var itemSelectorDialog = new A.LiferayItemSelectorDialog(
					{
						eventName: 'commerceWarehousesSelectItem',
						on: {
							selectedItemChange: function(event) {
								var selectedItems = event.newVal;

								if (selectedItems) {
									$('#<portlet:namespace />commerceWarehouseIds').val(selectedItems);

									var addCommerceWarehouseItemFm = $('#<portlet:namespace />addCommerceWarehouseItemFm');

									submitForm(addCommerceWarehouseItemFm);
								}
							}
						},
						title: '<liferay-ui:message arguments="<%= title %>" key="add-x-to-a-warehouse" />',
						url: '<%= commerceWarehouseItemsDisplayContext.getItemSelectorUrl() %>'
					}
				);

				itemSelectorDialog.open();
			}
		);
	</aui:script>
</c:if>