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
CommerceInventoryWarehousesDisplayContext commerceInventoryWarehousesDisplayContext = (CommerceInventoryWarehousesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<c:if test="<%= commerceInventoryWarehousesDisplayContext.hasManageCommerceInventoryWarehousePermission() %>">
	<liferay-ui:error exception="<%= CommerceGeocoderException.class %>">
		<liferay-ui:message arguments="<%= HtmlUtil.escape(errorException.toString()) %>" key="an-unexpected-error-occurred-while-invoking-the-geolocation-service-x" translateArguments="<%= false %>" />
	</liferay-ui:error>

	<clay:management-toolbar
		managementToolbarDisplayContext="<%= new CommerceInventoryWarehousesManagementToolbarDisplayContext(commerceInventoryWarehousesDisplayContext, request, liferayPortletRequest, liferayPortletResponse) %>"
	/>

	<div class="container-fluid container-fluid-max-xl">
		<liferay-ui:search-container
			id="commerceInventoryWarehouses"
			searchContainer="<%= commerceInventoryWarehousesDisplayContext.getSearchContainer() %>"
		>
			<liferay-ui:search-container-row
				className="com.liferay.commerce.inventory.model.CommerceInventoryWarehouse"
				keyProperty="commerceInventoryWarehouseId"
				modelVar="commerceInventoryWarehouse"
			>
				<liferay-ui:search-container-column-text
					cssClass="font-weight-bold important table-cell-expand"
					href='<%=
						PortletURLBuilder.createRenderURL(
							renderResponse
						).setMVCRenderCommandName(
							"/commerce_inventory_warehouse/edit_commerce_inventory_warehouse"
						).setRedirect(
							currentURL
						).setParameter(
							"commerceInventoryWarehouseId", commerceInventoryWarehouse.getCommerceInventoryWarehouseId()
						).buildPortletURL()
					%>'
					name="name"
					value="<%= HtmlUtil.escape(commerceInventoryWarehouse.getName()) %>"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand"
					name="city"
					value="<%= HtmlUtil.escape(commerceInventoryWarehouse.getCity()) %>"
				/>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-expand"
					name="active"
				>
					<c:choose>
						<c:when test="<%= commerceInventoryWarehouse.isActive() %>">
							<liferay-ui:icon
								cssClass="commerce-admin-icon-check"
								icon="check"
								markupView="lexicon"
							/>
						</c:when>
						<c:otherwise>
							<liferay-ui:icon
								cssClass="commerce-admin-icon-times"
								icon="times"
								markupView="lexicon"
							/>
						</c:otherwise>
					</c:choose>
				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-jsp
					cssClass="entry-action-column"
					path="/warehouse_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator
				markupView="lexicon"
			/>
		</liferay-ui:search-container>
	</div>
</c:if>