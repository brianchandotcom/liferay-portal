<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceInventoryDisplayContext commerceInventoryDisplayContext = (CommerceInventoryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<portlet:actionURL name="/commerce_inventory/edit_commerce_inventory_warehouse" var="editCommerceInventoryWarehouseActionURL" />

<commerce-ui:modal-content
	title='<%= LanguageUtil.get(request, "add-inventory-item") %>'
>
	<aui:form action="<%= editCommerceInventoryWarehouseActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

		<liferay-ui:error exception="<%= CPInstanceUnitOfMeasureKeyException.class %>" message="inventory-item-with-this-sku-and-uom-already-exists-in-the-selected-warehouse" />
		<liferay-ui:error exception="<%= DuplicateCommerceInventoryWarehouseItemException.class %>" message="inventory-item-with-this-sku-already-exists-in-the-selected-warehouse" />
		<liferay-ui:error exception="<%= NoSuchCPInstanceUnitOfMeasureException.class %>" message="no-such-uom-exists-with-this-sku" />

		<div class="row">
			<div class="col-6">
				<aui:input name="sku" required="<%= true %>" type="text" />
			</div>

			<div class="col-6">
				<aui:input name="unitOfMeasure" type="text" />
			</div>
		</div>

		<div class="row">
			<div class="col-12">
				<aui:select label="warehouse" name="commerceInventoryWarehouseId" required="<%= true %>" title="warehouse">

					<%
					for (CommerceInventoryWarehouse commerceInventoryWarehouse : commerceInventoryDisplayContext.getCommerceInventoryWarehouses()) {
					%>

					<aui:option label="<%= commerceInventoryWarehouse.getName(locale) %>" value="<%= commerceInventoryWarehouse.getCommerceInventoryWarehouseId() %>" />

					<%
					}
					%>

				</aui:select>
			</div>
		</div>

		<div class="row">
			<div class="col-12">
				<aui:input min="0" name="quantity" required="<%= true %>" type="text" wrapperCssClass="mb-0">
					<aui:validator errorMessage='<%= LanguageUtil.format(request, "please-enter-a-value-greater-than-x", 0) %>' name="custom">
						function(val) {
							if (Number(val) > 0) {
								return true;
							}

							return false;
						}
					</aui:validator>
				</aui:input>
			</div>
		</div>
	</aui:form>
</commerce-ui:modal-content>