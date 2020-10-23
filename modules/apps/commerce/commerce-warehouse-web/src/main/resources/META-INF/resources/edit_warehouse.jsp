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
String redirect = ParamUtil.getString(request, "redirect");

String backURL = ParamUtil.getString(request, "backURL", redirect);

CommerceInventoryWarehousesDisplayContext commerceInventoryWarehousesDisplayContext = (CommerceInventoryWarehousesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceInventoryWarehouse commerceInventoryWarehouse = commerceInventoryWarehousesDisplayContext.getCommerceInventoryWarehouse();

if (Validator.isNotNull(backURL)) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(backURL);
}
%>

<portlet:actionURL name="editCommerceInventoryWarehouse" var="editCommerceInventoryWarehouseActionURL" />

<aui:form action="<%= editCommerceInventoryWarehouseActionURL %>" cssClass="container-fluid-1280" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "saveCommerceInventoryWarehouse();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceInventoryWarehouse == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="commerceInventoryWarehouseId" type="hidden" value="<%= (commerceInventoryWarehouse == null) ? 0 : commerceInventoryWarehouse.getCommerceInventoryWarehouseId() %>" />
	<aui:input name="commerceChannelIds" type="hidden" />
	<aui:input name="mvccVersion" type="hidden" value="<%= (commerceInventoryWarehouse == null) ? 0 : commerceInventoryWarehouse.getMvccVersion() %>" />

	<liferay-frontend:form-navigator
		formModelBean="<%= commerceInventoryWarehouse %>"
		id="<%= CommerceInventoryWarehouseFormNavigatorConstants.FORM_NAVIGATOR_ID_COMMERCE_WAREHOUSE %>"
	/>
</aui:form>

<aui:script>
	function <portlet:namespace />saveCommerceInventoryWarehouse() {
		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>