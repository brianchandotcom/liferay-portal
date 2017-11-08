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

SearchContainer<CommerceShipment> commerceShipmentSearchContainer = commerceShipmentDisplayContext.getSearchContainer();

PortletURL portletURL = commerceShipmentDisplayContext.getPortletURL();

portletURL.setParameter("searchContainerId", "commerceShipments");

request.setAttribute("view.jsp-portletURL", portletURL);
%>

<div class="container-fluid-1280">
	<h1><liferay-ui:message key="shipments" /></h1>

	<div id="<portlet:namespace />shipmentsContainer">
		<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" />
			<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
			<aui:input name="deleteCommerceShipmentIds" type="hidden" />

			<div class="orders-container" id="<portlet:namespace />entriesContainer">
				<liferay-ui:search-container
					id="commerceShipments"
					iteratorURL="<%= portletURL %>"
					searchContainer="<%= commerceShipmentSearchContainer %>"
				>
					<liferay-ui:search-container-row
						className="com.liferay.commerce.model.CommerceShipment"
						cssClass="entry-display-style"
						keyProperty="commerceShipmentId"
						modelVar="commerceShipment"
					>
						<liferay-ui:search-container-column-status
							cssClass="table-cell-content"
							name="status"
							status="<%= commerceShipment.getStatus() %>"
						/>

						<liferay-ui:search-container-column-text
							cssClass="table-cell-content"
							name="shipment-number"
							property="commerceShipmentId"
						/>

						<liferay-ui:search-container-column-text
							cssClass="table-cell-content"
							name="shipping-address"
						>

							<%
							CommerceAddress commerceAddress = commerceShipment.fetchCommerceAddress();
							%>

							<c:if test="<%= commerceAddress != null %>">

								<%
								CommerceRegion commerceRegion = commerceAddress.getCommerceRegion();
								%>

								<p><%= HtmlUtil.escape(commerceAddress.getStreet1()) %></p>
								<p><%= HtmlUtil.escape(commerceAddress.getCity() + StringPool.COMMA_AND_SPACE + commerceRegion.getCode() + StringPool.SPACE + commerceAddress.getZip()) %></p>
							</c:if>
						</liferay-ui:search-container-column-text>

						<liferay-ui:search-container-column-text
							cssClass="table-cell-content"
							name="carrier-and-tracking-number"
						>
							<p><%= HtmlUtil.escape(commerceShipment.getCarrier()) %></p>
							<p><%= HtmlUtil.escape(commerceShipment.getTrackingNumber()) %></p>
						</liferay-ui:search-container-column-text>

						<liferay-ui:search-container-column-date
							cssClass="table-cell-content"
							name="shipping-date"
							property="shippingDate"
						/>

						<liferay-ui:search-container-column-date
							cssClass="table-cell-content"
							name="expected-delivery"
							property="expectedDate"
						/>

						<liferay-ui:search-container-column-jsp
							cssClass="entry-action-column"
							path="/shipment_action.jsp"
						/>
					</liferay-ui:search-container-row>

					<liferay-ui:search-iterator displayStyle="<%= commerceShipmentDisplayContext.getDisplayStyle() %>" markupView="lexicon" searchContainer="<%= commerceShipmentSearchContainer %>" />
				</liferay-ui:search-container>
			</div>
		</aui:form>
	</div>
</div>

<portlet:renderURL var="addCommerceShipmentURL">
	<portlet:param name="mvcRenderCommandName" value="editCommerceShipment" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:renderURL>

<liferay-frontend:add-menu>
	<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add-shipment") %>' url="<%= addCommerceShipmentURL.toString() %>" />
</liferay-frontend:add-menu>

<aui:script>
	function <portlet:namespace />deleteCommerceShipments() {
		if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-the-selected-shipments") %>')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.fm('deleteCommerceShipmentIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form);
		}
	}
</aui:script>