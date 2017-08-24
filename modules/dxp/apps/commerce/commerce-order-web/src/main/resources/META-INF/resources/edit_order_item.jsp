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
CommerceOrderItem commerceOrderItem = (CommerceOrderItem)request.getAttribute(CommerceWebKeys.COMMERCE_ORDER_ITEM);

PortletURL commerceOrderItemsURL = renderResponse.createRenderURL();

commerceOrderItemsURL.setParameter("mvcRenderCommandName", "viewCommerceOrderItems");
commerceOrderItemsURL.setParameter("commerceOrderId", String.valueOf(commerceOrderItem.getCommerceOrderId()));
commerceOrderItemsURL.setParameter("commerceOrderItemId", String.valueOf(commerceOrderItem.getCommerceOrderItemId()));

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(commerceOrderItemsURL.toString());

renderResponse.setTitle(String.valueOf(commerceOrderItem.getCommerceOrderItemId()));
%>

<portlet:actionURL name="editCommerceOrderItem" var="editCommerceOrderItemActionURL" />

<aui:form action="<%= editCommerceOrderItemActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= commerceOrderItemsURL.toString() %>" />
	<aui:input name="commerceOrderItemId" type="hidden" value="<%= commerceOrderItem.getCommerceOrderItemId() %>" />

	<div class="lfr-form-content">
		<aui:model-context bean="<%= commerceOrderItem %>" model="<%= CommerceOrderItem.class %>" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<aui:input name="quantity" />
			</aui:fieldset>
		</aui:fieldset-group>
	</div>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />

		<aui:button cssClass="btn-lg" href="<%= commerceOrderItemsURL.toString() %>" type="cancel" />
	</aui:button-row>
</aui:form>