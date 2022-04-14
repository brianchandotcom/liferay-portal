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
CommerceInventoryWarehousesDisplayContext cIWarehousesDisplayContext = (CommerceInventoryWarehousesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceInventoryWarehouse commerceInventoryWarehouse = cIWarehousesDisplayContext.getCommerceInventoryWarehouse();

String countryTwoLettersISOCode = BeanParamUtil.getString(commerceInventoryWarehouse, request, "countryTwoLettersISOCode");

String commerceRegionCode = BeanParamUtil.getString(commerceInventoryWarehouse, request, "commerceRegionCode");
%>

<liferay-ui:error exception="<%= CommerceInventoryWarehouseActiveException.class %>" message="please-add-geolocation-information-to-the-warehouse-to-activate" />
<liferay-ui:error exception="<%= CommerceInventoryWarehouseNameException.class %>" message="please-enter-a-valid-name" />
<liferay-ui:error exception="<%= MVCCException.class %>" message="this-item-is-no-longer-valid-please-try-again" />

<portlet:actionURL name="/commerce_inventory_warehouse/edit_commerce_inventory_warehouse" var="editCommerceInventoryWarehouseActionURL" />

<liferay-util:html-top>
	<link href="<%= PortalUtil.getStaticResourceURL(request, PortalUtil.getPathProxy() + application.getContextPath() + "/css/main.css") %>" rel="stylesheet" type="text/css" />
</liferay-util:html-top>

<aui:form action="<%= editCommerceInventoryWarehouseActionURL %>" cssClass="pt-4" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceInventoryWarehouse == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="externalReferenceCode" type="hidden" value="<%= commerceInventoryWarehouse.getExternalReferenceCode() %>" />
	<aui:input name="commerceInventoryWarehouseId" type="hidden" value="<%= commerceInventoryWarehouse.getCommerceInventoryWarehouseId() %>" />
	<aui:input name="mvccVersion" type="hidden" value="<%= commerceInventoryWarehouse.getMvccVersion() %>" />

	<aui:model-context bean="<%= commerceInventoryWarehouse %>" model="<%= CommerceInventoryWarehouse.class %>" />

	<div class="mt-4 row">
		<div class="col-lg-6">
			<commerce-ui:panel
				title='<%= LanguageUtil.get(request, "details") %>'
			>
				<aui:input localized="<%= true %>" name="name" required="<%= true %>" value="<%= commerceInventoryWarehouse.getName(locale) %>" />

				<aui:input localized="<%= true %>" name="description" type="textarea" value="<%= commerceInventoryWarehouse.getDescription(locale) %>" />

				<aui:input label='<%= HtmlUtil.escape("active") %>' name="active" type="toggle-switch" value="<%= commerceInventoryWarehouse.isActive() %>" />
			</commerce-ui:panel>
		</div>

		<div class="col-lg-6">
			<commerce-ui:panel
				bodyClasses="flex-fill"
				elementClasses="card-full-height h-100"
				title='<%= LanguageUtil.get(request, "geolocation") %>'
			>
				<aui:input name="latitude" />

				<aui:input name="longitude" />
			</commerce-ui:panel>
		</div>

		<div class="col">
			<commerce-ui:panel
				title='<%= LanguageUtil.get(request, "address") %>'
			>
				<div class="row">
					<div class="col-lg-6">
						<aui:input name="street1" />

						<aui:input name="street3" />

						<aui:select label="region" name="commerceRegionCode" />

						<aui:input name="city" />
					</div>

					<div class="col-lg-6">
						<aui:input name="street2" />

						<aui:select label="country" name="countryTwoLettersISOCode" />

						<aui:input label="postal-code" name="zip" />
					</div>
				</div>
			</commerce-ui:panel>
		</div>
	</div>
</aui:form>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"commerceRegionCode", commerceRegionCode
		).put(
			"companyId", company.getCompanyId()
		).put(
			"countryTwoLettersISOCode", HtmlUtil.escape(countryTwoLettersISOCode)
		).build()
	%>'
	module="js/warehouseAddress"
/>