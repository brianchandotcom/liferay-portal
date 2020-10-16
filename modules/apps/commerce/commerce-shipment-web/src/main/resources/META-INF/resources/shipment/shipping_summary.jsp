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

String trackingNumber = commerceShipment.getTrackingNumber();
String carrier = commerceShipment.getCarrier();
Date expectedDate = commerceShipment.getExpectedDate();
Date shippingDate = commerceShipment.getShippingDate();

Format dateFormat = FastDateFormatFactoryUtil.getDate(DateFormat.MEDIUM, locale, user.getTimeZone());
%>

<div class="mb-4">
	<commerce-ui:step-tracker
		steps="<%= commerceShipmentDisplayContext.getShipmentSteps() %>"
	/>
</div>

<commerce-ui:panel
	elementClasses="flex-fill"
	title='<%= LanguageUtil.get(request, "details") %>'
>
	<div class="row vertically-divided">
		<div class="col-xl-4">
			<liferay-portlet:renderURL var="editCommerceShipmentCourierDetailURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
				<portlet:param name="mvcRenderCommandName" value="/commerce_shipment/edit_commerce_shipment_courier_detail" />
				<portlet:param name="commerceShipmentId" value="<%= String.valueOf(commerceShipment.getCommerceShipmentId()) %>" />
			</liferay-portlet:renderURL>

			<commerce-ui:modal
				id="edit-courier-modal"
				refreshPageOnClose="<%= true %>"
				url="<%= editCommerceShipmentCourierDetailURL %>"
			/>

			<commerce-ui:info-box
				actionLabel='<%= LanguageUtil.get(request, "edit") %>'
				actionTargetId="edit-courier-modal"
				actionUrl="<%= editCommerceShipmentCourierDetailURL %>"
				title='<%= LanguageUtil.get(request, "carrier-details") %>'
			>
				<div class="item">
					<span class="title">
						<liferay-ui:message key="carrier" />
					</span>

					<c:choose>
						<c:when test="<%= Validator.isBlank(carrier) %>">
							<span class="text-muted"><liferay-ui:message key="click-edit-to-insert" /></span>
						</c:when>
						<c:otherwise>
							<b><%= HtmlUtil.escape(carrier) %></b>
						</c:otherwise>
					</c:choose>
				</div>

				<div class="item">
					<span class="title">
						<liferay-ui:message key="tracking-number" />
					</span>

					<c:choose>
						<c:when test="<%= Validator.isBlank(trackingNumber) %>">
							<span class="text-muted"><liferay-ui:message key="click-edit-to-insert" /></span>
						</c:when>
						<c:otherwise>
							<b><%= HtmlUtil.escape(trackingNumber) %></b>
						</c:otherwise>
					</c:choose>
				</div>
			</commerce-ui:info-box>
		</div>

		<div class="col-xl-4">
			<liferay-portlet:renderURL var="editCommerceShipmentAddressURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
				<portlet:param name="mvcRenderCommandName" value="/commerce_shipment/edit_commerce_shipment_address" />
				<portlet:param name="commerceShipmentId" value="<%= String.valueOf(commerceShipment.getCommerceShipmentId()) %>" />
			</liferay-portlet:renderURL>

			<commerce-ui:modal
				id="edit-address-modal"
				refreshPageOnClose="<%= true %>"
				size="lg"
				url="<%= editCommerceShipmentAddressURL %>"
			/>

			<commerce-ui:info-box
				actionLabel='<%= LanguageUtil.get(request, "edit") %>'
				actionTargetId="edit-address-modal"
				actionUrl="<%= editCommerceShipmentAddressURL %>"
				title='<%= LanguageUtil.get(request, "shipping-address") %>'
			>
				<div class="item">
					<%= HtmlUtil.replaceNewLine(HtmlUtil.escape(commerceShipmentDisplayContext.getDescriptiveShippingAddress())) %>
				</div>
			</commerce-ui:info-box>

			<commerce-ui:info-box
				title='<%= LanguageUtil.get(request, "channel") %>'
			>
				<div class="item">
					<%= HtmlUtil.escape(commerceShipmentDisplayContext.getCommerceChannelName()) %>
				</div>
			</commerce-ui:info-box>
		</div>

		<div class="col-xl-4">
			<liferay-portlet:renderURL var="editCommerceShipmentShippingDateURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
				<portlet:param name="mvcRenderCommandName" value="/commerce_shipment/edit_commerce_shipment_shipping_date" />
				<portlet:param name="commerceShipmentId" value="<%= String.valueOf(commerceShipment.getCommerceShipmentId()) %>" />
			</liferay-portlet:renderURL>

			<commerce-ui:modal
				id="edit-shipping-date-modal"
				refreshPageOnClose="<%= true %>"
				size="lg"
				url="<%= editCommerceShipmentShippingDateURL %>"
			/>

			<commerce-ui:info-box
				actionLabel='<%= LanguageUtil.get(request, "edit") %>'
				actionTargetId="edit-shipping-date-modal"
				actionUrl=""
				title='<%= LanguageUtil.get(request, "estimated-shipping-date") %>'
			>
				<div class="item">
					<c:choose>
						<c:when test="<%= Validator.isNull(shippingDate) %>">
							<span class="text-muted"><liferay-ui:message key="click-edit-to-insert" /></span>
						</c:when>
						<c:otherwise>
							<b><%= dateFormat.format(shippingDate) %></b>
						</c:otherwise>
					</c:choose>
				</div>
			</commerce-ui:info-box>

			<liferay-portlet:renderURL var="editCommerceShipmentExpectedDateURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
				<portlet:param name="mvcRenderCommandName" value="/commerce_shipment/edit_commerce_shipment_expected_date" />
				<portlet:param name="commerceShipmentId" value="<%= String.valueOf(commerceShipment.getCommerceShipmentId()) %>" />
			</liferay-portlet:renderURL>

			<commerce-ui:modal
				id="edit-expected-date-modal"
				refreshPageOnClose="<%= true %>"
				size="lg"
				url="<%= editCommerceShipmentExpectedDateURL %>"
			/>

			<commerce-ui:info-box
				actionLabel='<%= LanguageUtil.get(request, "edit") %>'
				actionTargetId="edit-expected-date-modal"
				actionUrl=""
				title='<%= LanguageUtil.get(request, "estimated-delivery-date") %>'
			>
				<div class="item">
					<c:choose>
						<c:when test="<%= Validator.isNull(expectedDate) %>">
							<span class="text-muted"><liferay-ui:message key="click-edit-to-insert" /></span>
						</c:when>
						<c:otherwise>
							<b><%= dateFormat.format(expectedDate) %></b>
						</c:otherwise>
					</c:choose>
				</div>
			</commerce-ui:info-box>
		</div>
	</div>
</commerce-ui:panel>

<commerce-ui:panel
	bodyClasses="p-0"
	title='<%= LanguageUtil.get(request, "products") %>'
>
	<clay:data-set-display
		contextParams='<%=
			HashMapBuilder.<String, String>put(
				"commerceShipmentId", String.valueOf(commerceShipment.getCommerceShipmentId())
			).build()
		%>'
		creationMenu="<%= commerceShipmentDisplayContext.getShipmentItemCreationMenu() %>"
		dataProviderKey="<%= CommerceShipmentDataSetConstants.COMMERCE_DATA_SET_KEY_SHIPMENT_ITEMS %>"
		id="<%= commerceShipmentDisplayContext.getDatasetView() %>"
		itemsPerPage="<%= 10 %>"
		namespace="<%= liferayPortletResponse.getNamespace() %>"
		pageNumber="<%= 1 %>"
		portletURL="<%= currentURLObj %>"
		showSearch="<%= false %>"
	/>
</commerce-ui:panel>