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
CommerceShipmentItemDisplayContext commerceShipmentItemDisplayContext = (CommerceShipmentItemDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceShipment commerceShipment = commerceShipmentItemDisplayContext.getCommerceShipment();
long commerceShipmentId = commerceShipmentItemDisplayContext.getCommerceShipmentId();
%>

<portlet:actionURL name="editCommerceShipment" var="editCommerceShipmentActionURL" />

<aui:form action="<%= editCommerceShipmentActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="commerceShipmentId" type="hidden" value="<%= String.valueOf(commerceShipmentId) %>" />

	<liferay-ui:error exception="<%= CommerceShipmentStatusException.class %>" message="please-select-a-valid-status" />

	<aui:model-context bean="<%= commerceShipment %>" model="<%= CommerceShipment.class %>" />

	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>
			<aui:input name="carrier" />

			<aui:input name="trackingNumber" />

			<aui:select name="status">

				<%
				for (int status : CommerceShipmentConstants.SHIPMENT_STATUSES) {
				%>

					<aui:option label="<%= CommerceShipmentConstants.getShipmentStatusLabel(status) %>" selected="<%= status == commerceShipment.getStatus() %>" value="<%= status %>" />

				<%
				}
				%>

			</aui:select>

			<aui:input name="shippingDate" />

			<aui:input label="expected-delivery-date" name="expectedDate" />
		</aui:fieldset>
	</aui:fieldset-group>

	<aui:button-row>
		<aui:button cssClass="btn-lg" name="saveButton" type="submit" value="save" />

		<aui:button cssClass="btn-lg" href="<%= shipmentsURL %>" type="cancel" />
	</aui:button-row>
</aui:form>