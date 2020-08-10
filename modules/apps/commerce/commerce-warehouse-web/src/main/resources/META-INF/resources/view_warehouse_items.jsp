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

				<table class="show-quick-actions-on-hover table table-autofit table-list table-responsive-lg">
					<thead>
						<tr>
							<th class="table-cell-content"><liferay-ui:message key="warehouse" /></th>
							<th><liferay-ui:message key="quantity" /></th>
							<th></th>
						</tr>
					</thead>

					<tbody>

						<%
						for (CommerceInventoryWarehouse commerceInventoryWarehouse : commerceInventoryWarehouses) {
							CommerceInventoryWarehouseItem commerceInventoryWarehouseItem = commerceInventoryWarehouseItemsDisplayContext.getCommerceInventoryWarehouseItem(commerceInventoryWarehouse);

							long commerceInventoryWarehouseItemId = 0;

							if (commerceInventoryWarehouseItem != null) {
								commerceInventoryWarehouseItemId = commerceInventoryWarehouseItem.getCommerceInventoryWarehouseItemId();
							}

							int curIndex = commerceInventoryWarehouses.indexOf(commerceInventoryWarehouse);
						%>

							<aui:model-context bean="<%= commerceInventoryWarehouseItem %>" model="<%= CommerceInventoryWarehouseItem.class %>" />

							<tr>
								<td>
									<%= HtmlUtil.escape(commerceInventoryWarehouse.getName()) %>
								</td>
								<td>
									<aui:input id='<%= "commerceInventoryWarehouseItemQuantity" + curIndex %>' label="" name="quantity" wrapperCssClass="m-0" />
								</td>
								<td class="text-center">
									<aui:button cssClass="btn-primary" name='<%= "saveButton" + curIndex %>' onClick="<%= commerceInventoryWarehouseItemsDisplayContext.getUpdateCommerceInventoryWarehouseItemTaglibOnClick(commerceInventoryWarehouse.getCommerceInventoryWarehouseId(), commerceInventoryWarehouseItemId, curIndex) %>" primary="<%= true %>" value="save" />
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
		function <portlet:namespace/>updateCommerceInventoryWarehouseItem(
			commerceInventoryWarehouseId,
			commerceInventoryWarehouseItemId,
			index
		) {
			var form = $(document.<portlet:namespace />fm);

			if (commerceInventoryWarehouseItemId > 0) {
				form.fm('<%= Constants.CMD %>').val('<%= Constants.UPDATE %>');
			} else {
				form.fm('<%= Constants.CMD %>').val('<%= Constants.ADD %>');
			}

			form.fm('commerceInventoryWarehouseId').val(commerceInventoryWarehouseId);
			form.fm('commerceInventoryWarehouseItemId').val(
				commerceInventoryWarehouseItemId
			);

			var quantityInputId =
				'#<portlet:namespace />commerceInventoryWarehouseItemQuantity' + index;

			var quantityInput = $(quantityInputId);

			form.fm('quantity').val(quantityInput.val());

			submitForm(form);
		}
	</aui:script>

	<aui:script>
		var quantityPrefix =
			'<portlet:namespace />commerceInventoryWarehouseItemQuantity';
		var enterKeyCode = 13;

		$('input[id^=' + quantityPrefix + ']').on('keypress', function(event) {
			if (event.keyCode == enterKeyCode) {
				event.preventDefault();

				var curIndex = $(this)
					.attr('id')
					.split(quantityPrefix)[1];
				$('#<portlet:namespace/>saveButton' + curIndex).click();
			}
		});
	</aui:script>
</c:if>