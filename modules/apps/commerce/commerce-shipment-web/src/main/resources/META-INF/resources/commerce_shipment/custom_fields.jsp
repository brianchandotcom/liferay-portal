<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceShipmentDisplayContext commerceShipmentDisplayContext = (CommerceShipmentDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

long commerceShipmentId = commerceShipmentDisplayContext.getCommerceShipmentId();
%>

<liferay-portlet:actionURL name="/commerce_shipment/edit_commerce_shipment" var="editCommerceShipmentURL" />

<div class="sheet">
	<div class="panel-group panel-group-flush">
		<aui:form action="<%= editCommerceShipmentURL %>" method="post" name="shipmentCustomFieldFm">
			<aui:fieldset>
				<aui:input name="<%= Constants.CMD %>" type="hidden" value="customFields" />
				<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
				<aui:input name="commerceShipmentId" type="hidden" value="<%= commerceShipmentId %>" />

				<liferay-ui:error-marker
					key="<%= WebKeys.ERROR_SECTION %>"
					value="custom-fields"
				/>

				<aui:model-context bean="<%= commerceShipmentDisplayContext.getCommerceShipment() %>" model="<%= CommerceShipment.class %>" />

				<liferay-expando:custom-attribute-list
					className="<%= CommerceShipment.class.getName() %>"
					classPK="<%= commerceShipmentId %>"
					editable="<%= true %>"
					label="<%= true %>"
				/>

				<aui:button-row>
					<aui:button type="submit" />
				</aui:button-row>
			</aui:fieldset>
		</aui:form>
	</div>
</div>