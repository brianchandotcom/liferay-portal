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
CommerceShipmentDisplayContext commerceShipmentDisplayContext = (CommerceShipmentDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceShipment commerceShipment = commerceShipmentDisplayContext.getCommerceShipment();
%>

<commerce-ui:modal-content
	title='<%= LanguageUtil.format(request, "edit-x", "carrier-details") %>'
>
	<c:if test="<%= commerceShipment != null %>">
		<portlet:actionURL name="/commerce_shipment/edit_commerce_shipment" var="editCommerceShipmentURL" />

		<aui:form action="<%= editCommerceShipmentURL %>" cssClass="container-fluid container-fluid-max-xl p-0" method="post" name="fm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="carrierDetails" />
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

			<aui:model-context bean="<%= commerceShipmentDisplayContext.getCommerceShipment() %>" model="<%= CommerceShipment.class %>" />

			<aui:input name="commerceShipmentId" type="hidden" />

			<aui:input name="carrier" wrapperCssClass="form-group-item" />

			<aui:input name="trackingNumber" wrapperCssClass="form-group-item" />

			<aui:select name="shippingMethod">

				<%
				List<CommerceShippingMethod> commerceShippingMethods = commerceShipmentDisplayContext.getCommerceShippingMethods();

				for (CommerceShippingMethod commerceShippingMethod : commerceShippingMethods) {
				%>

					<aui:option data='<%= HashMapBuilder.<String, Object>put("trackingURL", commerceShippingMethod.getTrackingURL()).build() %>' label="<%= commerceShippingMethod.getName(locale) %>" selected="<%= (commerceShippingMethod.getCommerceShippingMethodId() == commerceShipment.getCommerceShippingMethodId()) ? true : false %>" value="<%= commerceShippingMethod.getCommerceShippingMethodId() %>" />

				<%
				}
				%>

			</aui:select>

			<aui:input label="base-tracking-url" name="trackingURL" wrapperCssClass="form-group-item" />
		</aui:form>

		<liferay-frontend:component
			module="js/edit_commerce_shipment_courier_detail"
		/>
	</c:if>
</commerce-ui:modal-content>