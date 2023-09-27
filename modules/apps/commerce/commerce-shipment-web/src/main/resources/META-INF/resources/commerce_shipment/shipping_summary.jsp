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

<c:if test="<%= commerceShipment != null %>">

	<%
	long commerceShipmentId = commerceShipment.getCommerceShipmentId();

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
					<portlet:param name="commerceShipmentId" value="<%= String.valueOf(commerceShipmentId) %>" />
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

					<%
					String carrier = commerceShipment.getCarrier();
					%>

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

					<%
					String trackingNumber = commerceShipment.getTrackingNumber();
					%>

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

					<%
					String commerceShippingMethodName = commerceShipmentDisplayContext.getCommerceShippingMethodName(locale);
					%>

					<div class="item">
						<span class="title">
							<liferay-ui:message key="shipping-method" />
						</span>

						<c:choose>
							<c:when test="<%= Validator.isBlank(commerceShippingMethodName) %>">
								<span class="text-muted"><liferay-ui:message key="click-edit-to-insert" /></span>
							</c:when>
							<c:otherwise>
								<b><%= HtmlUtil.escape(commerceShippingMethodName) %></b>
							</c:otherwise>
						</c:choose>
					</div>

					<%
					String trackingURL = commerceShipment.getTrackingURL();
					%>

					<div class="item">
						<span class="title">
							<liferay-ui:message key="base-tracking-url" />
						</span>

						<c:choose>
							<c:when test="<%= Validator.isBlank(trackingURL) %>">
								<span class="text-muted"><liferay-ui:message key="click-edit-to-insert" /></span>
							</c:when>
							<c:otherwise>
								<b><%= HtmlUtil.escape(trackingURL) %></b>
							</c:otherwise>
						</c:choose>
					</div>

					<c:if test="<%= commerceShipmentDisplayContext.hasMultipleShippingMethods() %>">
						<div class="alert alert-info" role="alert">
							<clay:icon
								symbol="info-circle"
							/>

							<strong><liferay-ui:message key="info" /></strong>: <liferay-ui:message key="there-are-shipment-items-from-orders-with-different-shipping-methods-selected" />
						</div>
					</c:if>
				</commerce-ui:info-box>
			</div>

			<div class="col-xl-4">
				<liferay-portlet:renderURL var="editCommerceShipmentAddressURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
					<portlet:param name="mvcRenderCommandName" value="/commerce_shipment/edit_commerce_shipment_address" />
					<portlet:param name="commerceShipmentId" value="<%= String.valueOf(commerceShipmentId) %>" />
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
					<portlet:param name="commerceShipmentId" value="<%= String.valueOf(commerceShipmentId) %>" />
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

					<%
					Date shippingDate = commerceShipment.getShippingDate();
					%>

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
					<portlet:param name="commerceShipmentId" value="<%= String.valueOf(commerceShipmentId) %>" />
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

					<%
					Date expectedDate = commerceShipment.getExpectedDate();
					%>

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
		<frontend-data-set:classic-display
			contextParams='<%=
				HashMapBuilder.<String, String>put(
					"commerceShipmentId", String.valueOf(commerceShipmentId)
				).build()
			%>'
			creationMenu="<%= commerceShipmentDisplayContext.getShipmentItemCreationMenu() %>"
			dataProviderKey="<%= CommerceShipmentFDSNames.SHIPMENT_ITEMS %>"
			id="<%= commerceShipmentDisplayContext.getFDSName() %>"
			itemsPerPage="<%= 10 %>"
			showSearch="<%= false %>"
		/>
	</commerce-ui:panel>
</c:if>