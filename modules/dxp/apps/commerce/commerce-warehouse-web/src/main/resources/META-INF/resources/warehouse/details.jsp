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
CommerceWarehousesDisplayContext commerceWarehousesDisplayContext = (CommerceWarehousesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceWarehouse commerceWarehouse = commerceWarehousesDisplayContext.getCommerceWarehouse();
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="details"
/>

<liferay-ui:error exception="<%= CommerceWarehouseActiveException.class %>" message="please-geolocate-warehouse-to-set-as-active" />
<liferay-ui:error exception="<%= CommerceWarehouseNameException.class %>" message="please-enter-a-valid-name" />

<aui:model-context bean="<%= commerceWarehouse %>" model="<%= CommerceWarehouse.class %>" />

<aui:fieldset>
	<aui:input name="name" />

	<aui:input name="description" />

	<aui:input name="active" />
</aui:fieldset>