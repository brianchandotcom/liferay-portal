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
CommerceInventoryDisplayContext commerceInventoryDisplayContext = (CommerceInventoryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem = commerceInventoryDisplayContext.getCommerceInventoryReplenishmentItem();
%>

<liferay-ui:error exception="<%= MVCCException.class %>" message="this-item-is-no-longer-valid-please-try-again" />

<portlet:actionURL name="/commerce_inventory/edit_commerce_inventory_replenishment_item" var="editCommerceInventoryReplenishmentItemActionURL" />

<c:choose>
	<c:when test="<%= commerceInventoryReplenishmentItem == null %>">
		<commerce-ui:modal-content
			title='<%= LanguageUtil.get(request, "add-income") %>'
		>
			<aui:form action="<%= editCommerceInventoryReplenishmentItemActionURL %>" method="post" name="fm">
				<%@ include file="/edit_commerce_inventory_replenishment_item.jspf" %>
			</aui:form>
		</commerce-ui:modal-content>
	</c:when>
	<c:otherwise>
		<liferay-frontend:side-panel-content
			title='<%= LanguageUtil.get(request, "edit-incoming-quantity") %>'
		>
			<commerce-ui:panel
				title='<%= LanguageUtil.get(request, "details") %>'
			>
				<aui:form action="<%= editCommerceInventoryReplenishmentItemActionURL %>" method="post" name="fm">
					<%@ include file="/edit_commerce_inventory_replenishment_item.jspf" %>

					<aui:button-row>
						<aui:button cssClass="btn-lg" type="submit" value="save" />

						<aui:button cssClass="btn-lg" type="cancel" />
					</aui:button-row>
				</aui:form>
			</commerce-ui:panel>
		</liferay-frontend:side-panel-content>
	</c:otherwise>
</c:choose>