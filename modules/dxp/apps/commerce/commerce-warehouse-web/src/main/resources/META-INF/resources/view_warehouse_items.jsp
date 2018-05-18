<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceWarehouseItemsDisplayContext commerceWarehouseItemsDisplayContext = (CommerceWarehouseItemsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPInstance cpInstance = commerceWarehouseItemsDisplayContext.getCPInstance();

List<CommerceWarehouse> commerceWarehouses = commerceWarehouseItemsDisplayContext.getCommerceWarehouses();

String backURL = commerceWarehouseItemsDisplayContext.getBackURL();

if (Validator.isNotNull(backURL)) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(backURL);
}
%>

<div class="container-fluid-1280">
	<c:choose>
		<c:when test="<%= commerceWarehouses.isEmpty() %>">
			<div class="alert alert-info">
				<liferay-ui:message key="there-are-no-warehouses" />
			</div>
		</c:when>
		<c:otherwise>
			<portlet:actionURL name="editCommerceWarehouseItem" var="updateCommerceWarehouseItemURL" />

			<aui:form action="<%= updateCommerceWarehouseItemURL %>" method="post" name="fm">
				<aui:input name="<%= Constants.CMD %>" type="hidden" />
				<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
				<aui:input name="commerceWarehouseId" type="hidden" />
				<aui:input name="commerceWarehouseItemId" type="hidden" />
				<aui:input name="cpInstanceId" type="hidden" value="<%= cpInstance.getCPInstanceId() %>" />

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
						for (CommerceWarehouse commerceWarehouse : commerceWarehouses) {
							CommerceWarehouseItem commerceWarehouseItem = commerceWarehouseItemsDisplayContext.getCommerceWarehouseItem(commerceWarehouse);

							long commerceWarehouseItemId = 0;

							if (commerceWarehouseItem != null) {
								commerceWarehouseItemId = commerceWarehouseItem.getCommerceWarehouseItemId();
							}

							int curIndex = commerceWarehouses.indexOf(commerceWarehouse);
						%>

							<aui:model-context bean="<%= commerceWarehouseItem %>" model="<%= CommerceWarehouseItem.class %>" />

							<tr>
								<td>
									<%= commerceWarehouse.getName() %>
								</td>
								<td>
									<aui:input id='<%= "commerceWarehouseItemQuantity" + curIndex %>' label="" name="quantity" wrapperCssClass="m-0" />
								</td>
								<td class="text-center">
									<aui:button cssClass="btn btn-primary" name='<%= "saveButton" + curIndex %>' onClick="<%= commerceWarehouseItemsDisplayContext.getUpdateCommerceWarehouseItemTaglibOnClick(commerceWarehouse.getCommerceWarehouseId(), commerceWarehouseItemId, curIndex) %>" primary="<%= true %>" value="save" />
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
</div>

<aui:script>
	function <portlet:namespace/>updateCommerceWarehouseItem(commerceWarehouseId, commerceWarehouseItemId, index) {
		var form = $(document.<portlet:namespace />fm);

		if (commerceWarehouseItemId > 0) {
			form.fm('<%= Constants.CMD %>').val('<%= Constants.UPDATE %>');
		}
		else {
			form.fm('<%= Constants.CMD %>').val('<%= Constants.ADD %>');
		}

		form.fm('commerceWarehouseId').val(commerceWarehouseId);
		form.fm('commerceWarehouseItemId').val(commerceWarehouseItemId);

		var quantityInputId = '#<portlet:namespace />commerceWarehouseItemQuantity' + index;

		var quantityInput = $(quantityInputId);

		form.fm('quantity').val(quantityInput.val());

		submitForm(form);
	}
</aui:script>