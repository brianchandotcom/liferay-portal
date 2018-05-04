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
CommerceOrderEditDisplayContext commerceOrderEditDisplayContext = (CommerceOrderEditDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

SearchContainer<CommerceOrderItem> commerceOrderItemsSearchContainer = commerceOrderEditDisplayContext.getCommerceOrderItemsSearchContainer();

PortletURL portletURL = commerceOrderEditDisplayContext.getCommerceOrderItemsPortletURL();
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= true %>"
	searchContainerId="commerceOrderItems"
>
	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-sort
			orderByCol="<%= commerceOrderItemsSearchContainer.getOrderByCol() %>"
			orderByType="<%= commerceOrderItemsSearchContainer.getOrderByType() %>"
			orderColumns="<%= commerceOrderItemsSearchContainer.getOrderableHeaders() %>"
			portletURL="<%= portletURL %>"
		/>

		<li>
			<aui:form action="<%= portletURL %>" method="get" name="fm">
				<liferay-portlet:renderURLParams portletURL="<%= portletURL %>" />

				<liferay-ui:search-form
					page="/order/item_search.jsp"
					servletContext="<%= application %>"
				/>
			</aui:form>
		</li>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-buttons>
		<portlet:actionURL name="editCommerceOrderItem" var="addCommerceOrderItemURL" />

		<aui:form action="<%= addCommerceOrderItemURL %>" cssClass="hide" name="addCommerceOrderItemFm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>" />
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
			<aui:input name="commerceOrderId" type="hidden" value="<%= commerceOrderEditDisplayContext.getCommerceOrderId() %>" />
			<aui:input name="cpInstanceIds" type="hidden" value="" />
		</aui:form>

		<liferay-frontend:add-menu
			inline="<%= true %>"
		>
			<liferay-frontend:add-menu-item
				id="addCommerceOrderItem"
				title='<%= LanguageUtil.get(request, "add-item") %>'
				url="javascript:;"
			/>
		</liferay-frontend:add-menu>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-button
			href='<%= "javascript:" + renderResponse.getNamespace() + "deleteCommerceOrderItems();" %>'
			icon="times"
			label="delete"
		/>
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<div class="container-fluid-1280">
	<liferay-ui:search-container
		id="commerceOrderItems"
		searchContainer="<%= commerceOrderItemsSearchContainer %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.commerce.model.CommerceOrderItem"
			escapedModel="<%= true %>"
			keyProperty="commerceOrderItemId"
			modelVar="commerceOrderItem"
		>

			<%
			PortletURL rowURL = renderResponse.createRenderURL();

			rowURL.setParameter("mvcRenderCommandName", "editCommerceOrderItem");
			rowURL.setParameter("redirect", currentURL);
			rowURL.setParameter("commerceOrderItemId", String.valueOf(commerceOrderItem.getCommerceOrderItemId()));
			%>

			<liferay-ui:search-container-column-text
				cssClass="important table-cell-content"
				href="<%= rowURL %>"
				property="sku"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="title"
				value="<%= HtmlUtil.escape(commerceOrderItem.getTitle(locale)) %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				property="quantity"
			/>

			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="price"
				value="<%= commerceOrderEditDisplayContext.getCommerceOrderItemPrice(commerceOrderItem) %>"
			/>

			<liferay-ui:search-container-column-jsp
				cssClass="entry-action-column"
				path="/order/item_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</div>

<aui:script>

	Liferay.provide(
		window,
		'refreshPortlet',
		function() {
			var curPortlet = '#p_p_id<portlet:namespace/>';

			Liferay.Portlet.refresh(curPortlet);
		},
		['aui-dialog','aui-dialog-iframe']
	);

	Liferay.provide(
		window,
		'closePopup',
		function(dialogId) {
			var dialog = Liferay.Util.Window.getById(dialogId);

			dialog.destroy();
		},
		['liferay-util-window']
	);

	function <portlet:namespace />deleteCommerceOrderItems() {
		if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-order-items" />')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.attr('method', 'post');
			form.fm('<%= Constants.CMD %>').val('<%= Constants.DELETE %>');
			form.fm('deleteCommerceOrderItemIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form, '<portlet:actionURL name="editCommerceOrderItem" />');
		}
	}
</aui:script>

<aui:script use="liferay-item-selector-dialog">
	$('#<portlet:namespace />addCommerceOrderItem').on(
		'click',
		function(event) {
			event.preventDefault();

			var itemSelectorDialog = new A.LiferayItemSelectorDialog(
				{
					eventName: 'productInstancesSelectItem',
					on: {
						selectedItemChange: function(event) {
							var selectedItems = event.newVal;

							if (selectedItems) {
								$('#<portlet:namespace />cpInstanceIds').val(selectedItems);

								var addCommerceOrderItemFm = $('#<portlet:namespace />addCommerceOrderItemFm');

								submitForm(addCommerceOrderItemFm);
							}
						}
					},
					title: '<liferay-ui:message arguments="<%= commerceOrderEditDisplayContext.getCommerceOrderId() %>" key="add-new-product-to-order-x" />',
					url: '<%= commerceOrderEditDisplayContext.getItemSelectorUrl() %>'
				}
			);

			itemSelectorDialog.open();
		}
	);
</aui:script>