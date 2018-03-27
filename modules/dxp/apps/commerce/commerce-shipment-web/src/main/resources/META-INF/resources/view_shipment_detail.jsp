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

SearchContainer<CommerceShipmentItem> commerceShipmentItemSearchContainer = commerceShipmentItemDisplayContext.getSearchContainer();

CommerceShipment commerceShipment = commerceShipmentItemDisplayContext.getCommerceShipment();

long commerceShipmentId = commerceShipmentItemDisplayContext.getCommerceShipmentId();

CommerceAddress commerceAddress = commerceShipment.fetchCommerceAddress();

String street1 = StringPool.BLANK;
String street2 = StringPool.BLANK;
String street3 = StringPool.BLANK;
String city = StringPool.BLANK;
String zip = StringPool.BLANK;
String phoneNumber = StringPool.BLANK;
String regionCode = StringPool.BLANK;

if (commerceAddress != null) {
	street1 = commerceAddress.getStreet1();
	street2 = commerceAddress.getStreet2();
	street3 = commerceAddress.getStreet3();
	city = commerceAddress.getCity();
	zip = commerceAddress.getZip();
	phoneNumber = commerceAddress.getPhoneNumber();

	CommerceRegion commerceRegion = commerceAddress.getCommerceRegion();

	if (commerceRegion != null) {
		regionCode = commerceRegion.getCode();
	}
}

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(shipmentsURL);

PortletURL portletURL = commerceShipmentItemDisplayContext.getPortletURL();

portletURL.setParameter("searchContainerId", "commerceShipmentItems");

request.setAttribute("view.jsp-portletURL", portletURL);
%>

<div class="container-fluid-1280">
	<portlet:actionURL name="editCommerceShipment" var="editCommerceShipmentActionURL" />

	<aui:form action="<%= editCommerceShipmentActionURL.toString() %>" name="editCommerceShipmentFm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="commerceAddressId" type="hidden" value="<%= commerceShipment.getCommerceAddressId() %>" />
		<aui:input name="commerceShipmentId" type="hidden" value="<%= commerceShipmentId %>" />
		<aui:input name="shipmentUserId" type="hidden" value="<%= commerceShipment.getShipmentUserId() %>" />

		<aui:model-context bean="<%= commerceShipment %>" model="<%= CommerceShipment.class %>" />

		<h1><liferay-ui:message key="shipment-detail" /></h1>

		<div>
			<liferay-ui:message arguments="<%= commerceShipmentId %>" key="shipment-number-x" />
		</div>

		<div class="row shipment-detail-header">
			<div class="col-md-2">
				<h2><liferay-ui:message key="shipping" /></h2>

				<c:if test="<%= Validator.isNotNull(street2) %>">
					<p><%= HtmlUtil.escape(street1) %></p>
				</c:if>

				<c:if test="<%= Validator.isNotNull(street2) %>">
					<p><%= HtmlUtil.escape(street2) %></p>
				</c:if>

				<c:if test="<%= Validator.isNotNull(street3) %>">
					<p><%= HtmlUtil.escape(street3) %></p>
				</c:if>

				<p><%= city + StringPool.COMMA_AND_SPACE + regionCode + StringPool.SPACE + zip %></p>

				<c:if test="<%= Validator.isNotNull(phoneNumber) %>">
					<p><%= HtmlUtil.escape(phoneNumber) %></p>
				</c:if>
			</div>

			<div class="col-md-2">
				<c:choose>
					<c:when test="<%= hasManageCommerceShipmentsPermission %>">
						<aui:input name="shippingDate" />
					</c:when>
					<c:otherwise>
						<h4><liferay-ui:message key="shipping-date" /></h4>

						<p><%= HtmlUtil.escape(String.valueOf(commerceShipment.getShippingDate())) %></p>
					</c:otherwise>
				</c:choose>
			</div>

			<div class="col-md-3">
				<c:choose>
					<c:when test="<%= hasManageCommerceShipmentsPermission %>">
						<aui:input name="trackingNumber" />
					</c:when>
					<c:otherwise>
						<p class="text-muted"><liferay-ui:message key="tracking-number" /></p>

						<p><%= HtmlUtil.escape(commerceShipment.getTrackingNumber()) %></p>
					</c:otherwise>
				</c:choose>

				<p class="text-muted"><liferay-ui:message key="warehouse" /></p>

				<p><%= HtmlUtil.escape(commerceShipmentItemDisplayContext.getCommerceWarehouseName(commerceShipment.getCommerceWarehouseId())) %></p>
			</div>

			<div class="col-md-2">
				<h2><liferay-ui:message key="delivery" /></h2>

				<c:if test="<%= Validator.isNotNull(street2) %>">
					<p><%= HtmlUtil.escape(street1) %></p>
				</c:if>

				<c:if test="<%= Validator.isNotNull(street2) %>">
					<p><%= HtmlUtil.escape(street2) %></p>
				</c:if>

				<c:if test="<%= Validator.isNotNull(street3) %>">
					<p><%= HtmlUtil.escape(street3) %></p>
				</c:if>

				<p><%= city + StringPool.COMMA_AND_SPACE + regionCode + StringPool.SPACE + zip %></p>

				<c:if test="<%= Validator.isNotNull(phoneNumber) %>">
					<p><%= HtmlUtil.escape(phoneNumber) %></p>
				</c:if>
			</div>

			<div class="col-md-2">
				<c:choose>
					<c:when test="<%= hasManageCommerceShipmentsPermission %>">
						<aui:input name="expectedDate" />
					</c:when>
					<c:otherwise>
						<h4><liferay-ui:message key="expected-date" /></h4>

						<p><%= HtmlUtil.escape(String.valueOf(commerceShipment.getExpectedDate())) %></p>
					</c:otherwise>
				</c:choose>
			</div>

			<div class="col-md-1">

			</div>
		</div>

		<div class="row">
			<div class="col-md-4">
				<c:choose>
					<c:when test="<%= hasManageCommerceShipmentsPermission %>">
						<aui:input name="carrier" />
					</c:when>
					<c:otherwise>
						<liferay-ui:message arguments="<%= commerceShipment.getCarrier() %>" key="carrier-x" />
					</c:otherwise>
				</c:choose>
			</div>

			<div class="col-md-4">
				<c:choose>
					<c:when test="<%= hasManageCommerceShipmentsPermission %>">
						<aui:select label="method" name="commerceShippingMethodId" showEmptyOption="<%= true %>">

							<%
							List<CommerceShippingMethod> commerceShippingMethods = commerceShipmentItemDisplayContext.getCommerceShippingMethods();

							for (CommerceShippingMethod commerceShippingMethod : commerceShippingMethods) {
							%>

								<aui:option
									label="<%= commerceShippingMethod.getName() %>"
									selected="<%= (commerceShipment != null) && (commerceShipment.getCommerceShippingMethodId() == commerceShippingMethod.getCommerceShippingMethodId()) %>"
									value="<%= commerceShippingMethod.getCommerceShippingMethodId() %>"
								/>

							<%
							}
							%>

						</aui:select>
					</c:when>
					<c:otherwise>

						<%
						String shippingMethod = StringPool.BLANK;

						CommerceShippingMethod commerceShippingMethod = commerceShipment.fetchCommerceShippingMethod();

						if (commerceShippingMethod != null) {
							shippingMethod = commerceShippingMethod.getName(languageId);
						}
						%>

						<liferay-ui:message arguments="<%= shippingMethod %>" key="method-x" />
					</c:otherwise>
				</c:choose>
			</div>

			<div class="col-md-4">
				<c:choose>
					<c:when test="<%= hasManageCommerceShipmentsPermission %>">
						<aui:input name="expectedDuration" />
					</c:when>
					<c:otherwise>
						<liferay-ui:message arguments="<%= commerceShipment.getExpectedDuration() %>" key="expected-duration-x" />
					</c:otherwise>
				</c:choose>
			</div>
		</div>

		<aui:button cssClass="btn-lg" name="saveButton" type="submit" value="save" />
	</aui:form>
</div>

<liferay-frontend:management-bar
	searchContainerId="commerceShipmentItems"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews='<%= new String[] {"list"} %>'
			portletURL="<%= portletURL %>"
			selectedDisplayStyle="list"
		/>

		<portlet:actionURL name="editCommerceShipmentItem" var="addCommerceShipmentItemURL" />

		<aui:form action="<%= addCommerceShipmentItemURL %>" cssClass="hide" name="addCommerceShipmentItemFm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>" />
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
			<aui:input name="commerceShipmentId" type="hidden" value="<%= commerceShipmentId %>" />
			<aui:input name="commerceOrderItemIds" type="hidden" value="" />
		</aui:form>

		<liferay-frontend:add-menu inline="<%= true %>">
			<liferay-frontend:add-menu-item id="addCommerceShipmentItem" title='<%= LanguageUtil.get(request, "add-shipment-item") %>' url="javascript:;" />
		</liferay-frontend:add-menu>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= portletURL %>"
		/>
	</liferay-frontend:management-bar-filters>
</liferay-frontend:management-bar>

<div class="container-fluid-1280" id="<portlet:namespace />shipmentItemsContainer">
	<aui:form action="<%= portletURL.toString() %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= portletURL.toString() %>" />
		<aui:input name="commerceShipmentId" type="hidden" value="<%= commerceShipmentId %>" />
		<aui:input name="deleteCommerceShipmentItemIds" type="hidden" />

		<div class="orders-container" id="<portlet:namespace />entriesContainer">
			<liferay-ui:search-container
				id="commerceShipmentItems"
				iteratorURL="<%= portletURL %>"
				searchContainer="<%= commerceShipmentItemSearchContainer %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.commerce.model.CommerceShipmentItem"
					cssClass="entry-display-style"
					keyProperty="commerceShipmentItemId"
					modelVar="commerceShipmentItem"
				>

					<%
					CPInstance cpInstance = commerceShipmentItemDisplayContext.getCPInstance(commerceShipmentItem.getCommerceShipmentItemId());

					CPDefinition cpDefinition = commerceShipmentItemDisplayContext.getCPDefinition(commerceShipmentItem.getCommerceShipmentItemId());
					%>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						name="sku"
						value="<%= HtmlUtil.escape(cpInstance.getSku()) %>"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						name="name"
						value="<%= HtmlUtil.escape(cpDefinition.getTitle(languageId)) %>"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-content"
						property="quantity"
					/>

					<liferay-ui:search-container-column-jsp
						cssClass="entry-action-column"
						path="/shipment_item_action.jsp"
					/>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator displayStyle="<%= commerceShipmentItemDisplayContext.getDisplayStyle() %>" markupView="lexicon" searchContainer="<%= commerceShipmentItemSearchContainer %>" />
			</liferay-ui:search-container>
		</div>
	</aui:form>
</div>

<aui:script>
	function <portlet:namespace />deleteCommerceShipmentItems() {
		if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-the-selected-shipment-items" />')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.attr('method', 'post');
			form.fm('<%= Constants.CMD %>').val('<%= Constants.DELETE %>');
			form.fm('deleteCommerceShipmentItemIds').val(Liferay.Util.listCheckedExcept(form, '<portlet:namespace />allRowIds'));

			submitForm(form, '<portlet:actionURL name="editCommerceShipmentItem" />');
		}
	}
</aui:script>

<aui:script use="liferay-item-selector-dialog">
	$('#<portlet:namespace />addCommerceShipmentItem').on(
		'click',
		function(event) {
			event.preventDefault();

			var itemSelectorDialog = new A.LiferayItemSelectorDialog(
				{
					eventName: 'orderItemsSelectItem',
					on: {
						selectedItemChange: function(event) {
							var selectedItems = event.newVal;

							if (selectedItems) {
								$('#<portlet:namespace />commerceOrderItemIds').val(selectedItems);

								var addCommerceShipmentItemFm = $('#<portlet:namespace />addCommerceShipmentItemFm');

								submitForm(addCommerceShipmentItemFm);
							}
						}
					},
					title: '<liferay-ui:message arguments="<%= commerceShipmentId %>" key="add-new-order-item-to-shipment-number-x" />',
					url: '<%= commerceShipmentItemDisplayContext.getItemSelectorUrl() %>'
				}
			);

			itemSelectorDialog.open();
		}
	);
</aui:script>