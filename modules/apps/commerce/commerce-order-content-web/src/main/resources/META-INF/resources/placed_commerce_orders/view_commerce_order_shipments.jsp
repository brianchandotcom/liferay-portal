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
long commerceOrderItemId = ParamUtil.getLong(request, "commerceOrderItemId");

//List<CommerceShipmentItem> commerceShipmentItems = commerceOrderContentDisplayContext.getCommerceShipmentItems(commerceOrderItemId);

%>

<commerce-ui:modal-content
	contentCssClasses="p-0"
	showCancelButton="<%= false %>"
	showSubmitButton="<%= false %>"
	title='<%= LanguageUtil.get(request, "shipments") %>'
>
	<frontend-data-set:headless-display
		apiURL="<%= commerceOrderContentDisplayContext.getCommerceShipmentItemsApiUrl() %>"
		formName="fm"
		id="<%= CommerceOrderFDSNames.SHIPMENTS %>"
		showManagementBar="<%= false %>"
		showPagination="<%= false %>"
		style="fluid"
	/>
</commerce-ui:modal-content>