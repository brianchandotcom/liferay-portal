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
CommerceInventoryWarehouseItemsDisplayContext commerceInventoryWarehouseItemsDisplayContext = (CommerceInventoryWarehouseItemsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

String backURL = commerceInventoryWarehouseItemsDisplayContext.getBackURL();

if (Validator.isNotNull(backURL)) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(backURL);
}
%>

<c:if test="<%= commerceInventoryWarehouseItemsDisplayContext.hasManageCommerceInventoryWarehousePermission() %>">

	<%
	List<CommerceInventoryWarehouse> commerceInventoryWarehouses = commerceInventoryWarehouseItemsDisplayContext.getCommerceInventoryWarehouses();
	CPInstance cpInstance = commerceInventoryWarehouseItemsDisplayContext.getCPInstance();
	%>

	<c:choose>
		<c:when test="<%= commerceInventoryWarehouses.isEmpty() %>">
			<div class="alert alert-info">
				<liferay-ui:message key="there-are-no-active-warehouses" />
			</div>
		</c:when>
		<c:otherwise>
			<portlet:actionURL name="editCommerceInventoryWarehouseItem" var="updateCommerceInventoryWarehouseItemURL" />

			<aui:form action="<%= updateCommerceInventoryWarehouseItemURL %>" method="post" name="fm">
				<aui:input name="<%= Constants.CMD %>" type="hidden" />
				<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
				<aui:input name="commerceInventoryWarehouseId" type="hidden" />
				<aui:input name="commerceInventoryWarehouseItemId" type="hidden" />
				<aui:input name="sku" type="hidden" value="<%= cpInstance.getSku() %>" />
				<aui:input name="quantity" type="hidden" />
				<aui:input name="mvccVersion" type="hidden" />

				<table class="show-quick-actions-on-hover table table-autofit table-list table-responsive-lg">
					<thead>
						<tr>
							<th class="table-cell-expand"><liferay-ui:message key="warehouse" /></th>
							<th><liferay-ui:message key="quantity" /></th>
							<th></th>
						</tr>
					</thead>

					<tbody>

						<%
						for (CommerceInventoryWarehouse commerceInventoryWarehouse : commerceInventoryWarehouses) {
							CommerceInventoryWarehouseItem commerceInventoryWarehouseItem = commerceInventoryWarehouseItemsDisplayContext.getCommerceInventoryWarehouseItem(commerceInventoryWarehouse);

							long commerceInventoryWarehouseItemId = 0;
							int quantity = 0;
							long mvccVersion = 0;

							if (commerceInventoryWarehouseItem != null) {
								commerceInventoryWarehouseItemId = commerceInventoryWarehouseItem.getCommerceInventoryWarehouseItemId();
								quantity = commerceInventoryWarehouseItem.getQuantity();
								mvccVersion = commerceInventoryWarehouseItem.getMvccVersion();
							}

							int curIndex = commerceInventoryWarehouses.indexOf(commerceInventoryWarehouse);
						%>

							<tr>
								<td>
									<%= HtmlUtil.escape(commerceInventoryWarehouse.getName()) %>
								</td>
								<td>
									<aui:input id='<%= "commerceInventoryWarehouseItemQuantity" + curIndex %>' label="" name="commerceInventoryWarehouseItemQuantity" value="<%= quantity %>" wrapperCssClass="m-0" />
								</td>
								<td class="text-center">
									<aui:button cssClass="btn-primary" name='<%= "saveButton" + curIndex %>' onClick="<%= commerceInventoryWarehouseItemsDisplayContext.getUpdateCommerceInventoryWarehouseItemTaglibOnClick(commerceInventoryWarehouse.getCommerceInventoryWarehouseId(), commerceInventoryWarehouseItemId, mvccVersion, curIndex) %>" primary="<%= true %>" value="save" />
								</td>
							</tr>

						<%
						}
						%>

					</tbody>
				</table>
			</aui:form>
		</c:otherwise>
	</c:choose>

	<aui:script>
		function <portlet:namespace />updateCommerceInventoryWarehouseItem(
			commerceInventoryWarehouseId,
			commerceInventoryWarehouseItemId,
			mvccVersion,
			index
		) {
			var form = window.document['<portlet:namespace />fm'];

			if (commerceInventoryWarehouseItemId > 0) {
				form['<portlet:namespace /><%= Constants.CMD %>'].value =
					'<%= Constants.UPDATE %>';
			}
			else {
				form['<portlet:namespace /><%= Constants.CMD %>'].value =
					'<%= Constants.ADD %>';
			}

			form[
				'<portlet:namespace />commerceInventoryWarehouseId'
			].value = commerceInventoryWarehouseId;
			form[
				'<portlet:namespace />commerceInventoryWarehouseItemId'
			].value = commerceInventoryWarehouseItemId;

			var quantityInputId =
				'#<portlet:namespace />commerceInventoryWarehouseItemQuantity' + index;

			var quantityInput = window.document.querySelector(quantityInputId);

			form['<portlet:namespace />quantity'].value = quantityInput.value;

			form['<portlet:namespace />mvccVersion'].value = mvccVersion;

			submitForm(form);
		}
	</aui:script>

	<aui:script>
		var quantityPrefix =
			'<portlet:namespace />commerceInventoryWarehouseItemQuantity';
		var enterKeyCode = 13;

		var quantityInputElements = window.document.querySelectorAll(
			'input[id^=' + quantityPrefix + ']'
		);

		Array.from(quantityInputElements).forEach(function (quantityInputElement) {
			quantityInputElement.addEventListener('keypress', function (event) {
				if (event.keyCode == enterKeyCode) {
					event.preventDefault();

					var curIndex = event.currentTarget
						.getAttribute('id')
						.split(quantityPrefix)[1];

					window.document
						.querySelector('#<portlet:namespace />saveButton' + curIndex)
						.click();
				}
			});
		});
	</aui:script>
</c:if>