<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceShipmentDisplayContext commerceShipmentDisplayContext = (CommerceShipmentDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceShipment commerceShipment = commerceShipmentDisplayContext.getCommerceShipment();
%>

<portlet:actionURL name="/commerce_shipment/edit_commerce_shipment_external_reference_code" var="editCommerceShipmentExternalReferenceCodeURL" />

<liferay-ui:error embed="<%= false %>" exception="<%= DuplicateCommerceShipmentException.class %>" message="please-enter-a-unique-external-reference-code" />

<commerce-ui:modal-content>
	<aui:form action="<%= editCommerceShipmentExternalReferenceCodeURL %>" cssClass="container-fluid container-fluid-max-xl p-0" method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="commerceShipmentId" type="hidden" value="<%= commerceShipment.getCommerceShipmentId() %>" />

		<aui:model-context bean="<%= commerceShipment %>" model="<%= CommerceShipment.class %>" />

		<aui:input name="externalReferenceCode" type="text" value="<%= commerceShipment.getExternalReferenceCode() %>" wrapperCssClass="form-group-item" />
	</aui:form>
</commerce-ui:modal-content>