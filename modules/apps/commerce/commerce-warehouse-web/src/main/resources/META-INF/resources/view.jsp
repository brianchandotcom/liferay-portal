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

	<%
	String countryTwoLettersIsoCode = commerceInventoryWarehousesDisplayContext.getCountryTwoLettersIsoCode();
	List<ManagementBarFilterItem> managementBarFilterItems = commerceInventoryWarehousesDisplayContext.getManagementBarFilterItems();

	String managementBarFilterValue = null;

	if (Validator.isNotNull(countryTwoLettersIsoCode)) {
		Country country = commerceInventoryWarehousesDisplayContext.getCountry(countryTwoLettersIsoCode);

		for (ManagementBarFilterItem managementBarFilterItem : managementBarFilterItems) {
			if (country.getCountryId() == Long.valueOf(managementBarFilterItem.getId())) {
				managementBarFilterValue = managementBarFilterItem.getLabel();

				break;
			}
		}
	}
	%>

	<liferay-ui:error exception="<%= CommerceGeocoderException.class %>">
		<liferay-ui:message arguments="<%= HtmlUtil.escape(errorException.toString()) %>" key="an-unexpected-error-occurred-while-invoking-the-geolocation-service-x" translateArguments="<%= false %>" />
	</liferay-ui:error>

	<liferay-frontend:management-bar
		searchContainerId="commerceInventoryWarehouses"
	>
		<liferay-frontend:management-bar-filters>
			<liferay-frontend:management-bar-navigation
				navigationKeys='<%= new String[] {"all", "active", "inactive"} %>'
				portletURL="<%= commerceInventoryWarehousesDisplayContext.getPortletURL() %>"
			/>

			<liferay-frontend:management-bar-filter
				label="country"
				managementBarFilterItems="<%= managementBarFilterItems %>"
				value="<%= managementBarFilterValue %>"
			/>

			<liferay-frontend:management-bar-sort
				orderByCol="<%= commerceInventoryWarehousesDisplayContext.getOrderByCol() %>"
				orderByType="<%= commerceInventoryWarehousesDisplayContext.getOrderByType() %>"
				orderColumns='<%= new String[] {"city", "name"} %>'
				portletURL="<%= commerceInventoryWarehousesDisplayContext.getPortletURL() %>"
			/>

			<li>
				<liferay-commerce:search-input
					actionURL="<%= commerceInventoryWarehousesDisplayContext.getPortletURL() %>"
					formName="searchFm"
				/>
			</li>
		</liferay-frontend:management-bar-filters>

		<liferay-frontend:management-bar-buttons>
			<liferay-frontend:management-bar-display-buttons
				displayViews='<%= new String[] {"list"} %>'
				portletURL="<%= commerceInventoryWarehousesDisplayContext.getPortletURL() %>"
				selectedDisplayStyle="list"
			/>

			<portlet:renderURL var="addCommerceInventoryWarehouseURL">
				<portlet:param name="mvcRenderCommandName" value="/commerce_inventory_warehouse/edit_commerce_inventory_warehouse" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="countryId" value="<%= String.valueOf(countryTwoLettersIsoCode) %>" />
			</portlet:renderURL>

			<liferay-frontend:add-menu
				inline="<%= true %>"
			>
				<liferay-frontend:add-menu-item
					title='<%= LanguageUtil.get(request, "add-warehouse") %>'
					url="<%= addCommerceInventoryWarehouseURL.toString() %>"
				/>
			</liferay-frontend:add-menu>
		</liferay-frontend:management-bar-buttons>
	</liferay-frontend:management-bar>

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
					cssClass="important table-cell-expand"
					href='<%=
						PortletURLBuilder.createRenderURL(
							renderResponse
						).setMVCRenderCommandName(
							"/commerce_inventory_warehouse/edit_commerce_inventory_warehouse"
						).setRedirect(
							currentURL
						).setParameter(
							"commerceInventoryWarehouseId", commerceInventoryWarehouse.getCommerceInventoryWarehouseId()
						).build()
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