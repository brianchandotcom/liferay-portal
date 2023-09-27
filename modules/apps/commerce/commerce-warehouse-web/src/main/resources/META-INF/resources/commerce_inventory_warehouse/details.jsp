<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceInventoryWarehousesDisplayContext cIWarehousesDisplayContext = (CommerceInventoryWarehousesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceInventoryWarehouse commerceInventoryWarehouse = cIWarehousesDisplayContext.getCommerceInventoryWarehouse();

String countryTwoLettersISOCode = BeanParamUtil.getString(commerceInventoryWarehouse, request, "countryTwoLettersISOCode");

String commerceRegionCode = BeanParamUtil.getString(commerceInventoryWarehouse, request, "commerceRegionCode");
%>

<liferay-ui:error exception="<%= CommerceGeocoderException.class %>">
	<liferay-ui:message arguments="<%= HtmlUtil.escape(errorException.toString()) %>" key="an-unexpected-error-occurred-while-invoking-the-geolocation-service-x" translateArguments="<%= false %>" />
</liferay-ui:error>

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
	<aui:input name="mvccVersion" type="hidden" value="<%= commerceInventoryWarehouse.getMvccVersion() %>" />
	<aui:input name="externalReferenceCode" type="hidden" value="<%= commerceInventoryWarehouse.getExternalReferenceCode() %>" />
	<aui:input name="commerceInventoryWarehouseId" type="hidden" value="<%= commerceInventoryWarehouse.getCommerceInventoryWarehouseId() %>" />

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

		<div class="col-lg-6 d-flex">
			<portlet:actionURL name="/commerce_inventory_warehouse/edit_commerce_inventory_warehouse" var="geolocateURL">
				<portlet:param name="<%= Constants.CMD %>" value="geolocate" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="commerceInventoryWarehouseId" value="<%= String.valueOf(commerceInventoryWarehouse.getCommerceInventoryWarehouseId()) %>" />
			</portlet:actionURL>

			<commerce-ui:panel
				bodyClasses="flex-fill"
				elementClasses="card-full-height w-100"
				title='<%= LanguageUtil.get(request, "geolocation") %>'
			>
				<aui:input name="latitude" />

				<aui:input name="longitude" />

				<div>
					<clay:link
						displayType="secondary"
						href="<%= geolocateURL.toString() %>"
						label="geolocate"
						type="button"
					/>
				</div>
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