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
long commerceShipmentId = ParamUtil.getLong(request, "commerceShipmentId");
long commerceShipmentItemId = ParamUtil.getLong(request, "commerceShipmentItemId");

String title = "shipment";
%>

<portlet:actionURL name="editCommerceShipment" var="editCommerceShipmentURL" />

<c:if test="<%= commerceShipmentItemId > 0 %>">
	<portlet:actionURL name="editCommerceShipmentItem" var="editCommerceShipmentURL" />

	<%
	title = "shipment-item";
	%>

</c:if>

<commerce-ui:modal-content
	title='<%= LanguageUtil.format(request, "delete-x", title) %>'
>
	<liferay-ui:error exception="<%= CommerceShipmentShippingDateException.class %>" />

	<aui:form action="<%= editCommerceShipmentURL %>" cssClass="container-fluid container-fluid-max-xl p-0" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.DELETE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

		<c:choose>
			<c:when test="<%= commerceShipmentId > 0 %>">
				<aui:input name="commerceShipmentId" type="hidden" value="<%= commerceShipmentId %>" />
			</c:when>
			<c:when test="<%= commerceShipmentItemId > 0 %>">
				<aui:input name="commerceShipmentItemId" type="hidden" value="<%= commerceShipmentItemId %>" />
			</c:when>
		</c:choose>

		<h2><liferay-ui:message key="restock-the-items-that-are-being-deleted" /></h2>

		<aui:input label="yes-restock-the-items" name="restoreStockQuantity" type="checkbox" value="true" />
	</aui:form>
</commerce-ui:modal-content>