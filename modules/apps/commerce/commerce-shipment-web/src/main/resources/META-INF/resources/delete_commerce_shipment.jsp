<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
long commerceShipmentId = ParamUtil.getLong(request, "commerceShipmentId");
long commerceShipmentItemId = ParamUtil.getLong(request, "commerceShipmentItemId");

String title = "shipment";
%>

<portlet:actionURL name="/commerce_shipment/edit_commerce_shipment" var="editCommerceShipmentURL" />

<c:if test="<%= commerceShipmentItemId > 0 %>">
	<portlet:actionURL name="/commerce_shipment/edit_commerce_shipment_item" var="editCommerceShipmentURL" />

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