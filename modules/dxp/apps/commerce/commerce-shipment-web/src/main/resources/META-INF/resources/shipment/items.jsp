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
CommerceShipmentItemDisplayContext commerceShipmentItemDisplayContext = (CommerceShipmentItemDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

SearchContainer<CommerceShipmentItem> commerceShipmentItemSearchContainer = commerceShipmentItemDisplayContext.getSearchContainer();
long commerceShipmentId = commerceShipmentItemDisplayContext.getCommerceShipmentId();
String addCommerceShipmentItemsURL = commerceShipmentItemDisplayContext.getAddCommerceShipmentItemsURL();

PortletURL portletURL = commerceShipmentItemDisplayContext.getPortletURL();

portletURL.setParameter("searchContainerId", "commerceShipmentItems");

request.setAttribute("view.jsp-portletURL", portletURL);
%>

<liferay-frontend:management-bar
	searchContainerId="commerceShipmentItems"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= portletURL %>"
			selectedDisplayStyle="list"
		/>

		<liferay-frontend:add-menu
			inline="<%= true %>"
		>
			<liferay-frontend:add-menu-item
				title='<%= LanguageUtil.get(request, "add-shipment-item") %>'
				url="<%= addCommerceShipmentItemsURL %>"
			/>
		</liferay-frontend:add-menu>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= portletURL %>"
		/>
	</liferay-frontend:management-bar-filters>
</liferay-frontend:management-bar>

<div class="container-fluid-1280" id="<portlet:namespace />shipmentItemsContainer">
	<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
		<aui:input name="commerceShipmentId" type="hidden" value="<%= commerceShipmentId %>" />
		<aui:input name="deleteCommerceShipmentItemIds" type="hidden" />

		<div class="orders-container" id="<portlet:namespace />entriesContainer">
			<liferay-ui:search-container
				id="commerceShipmentItems"
				iteratorURL="<%= portletURL %>"
				searchContainer="<%= commerceShipmentItemSearchContainer %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.commerce.model.CommerceShipmentItem"
					cssClass="entry-display-style"
					escapedModel="<%= true %>"
					keyProperty="commerceShipmentItemId"
					modelVar="commerceShipmentItem"
				>

					<%
					CommerceOrderItem commerceOrderItem = commerceShipmentItem.fetchCommerceOrderItem();
					%>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						name="sku"
						value="<%= (commerceOrderItem == null) ? StringPool.BLANK : commerceOrderItem.getSku() %>"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						name="name"
						value="<%= (commerceOrderItem == null) ? StringPool.BLANK : commerceOrderItem.getName(languageId) %>"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						property="quantity"
					/>

					<liferay-ui:search-container-column-jsp
						cssClass="entry-action-column"
						path="/shipment_item_action.jsp"
					/>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator
					displayStyle="<%= commerceShipmentItemDisplayContext.getDisplayStyle() %>"
					markupView="lexicon"
					searchContainer="<%= commerceShipmentItemSearchContainer %>"
				/>
			</liferay-ui:search-container>
		</div>
	</aui:form>
</div>

<aui:script>
	function <portlet:namespace />deleteCommerceShipmentItems() {
		if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-shipment-items" />')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.attr('method', 'post');
			form.fm('<%= Constants.CMD %>').val('<%= Constants.DELETE %>');
			form.fm('deleteCommerceShipmentItemIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form, '<portlet:actionURL name="editCommerceShipmentItem" />');
		}
	}
</aui:script>