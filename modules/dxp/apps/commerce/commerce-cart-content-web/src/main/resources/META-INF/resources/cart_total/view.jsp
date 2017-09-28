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
CommerceCartContentTotalDisplayContext commerceCartContentTotalDisplayContext = (CommerceCartContentTotalDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

Map<String, Object> contextObjects = new HashMap<>();

contextObjects.put("commerceCartContentTotalDisplayContext", commerceCartContentTotalDisplayContext);

SearchContainer<CommerceCartItem> commerceCartItemSearchContainer = commerceCartContentTotalDisplayContext.getSearchContainer();
%>

<liferay-ddm:template-renderer
	className="<%= CommerceCartContentTotalPortlet.class.getName() %>"
	contextObjects="<%= contextObjects %>"
	displayStyle="<%= commerceCartContentTotalDisplayContext.getDisplayStyle() %>"
	displayStyleGroupId="<%= commerceCartContentTotalDisplayContext.getDisplayStyleGroupId() %>"
	entries="<%= commerceCartItemSearchContainer.getResults() %>"
>
	<div class="order-total">
		<div class="row">
			<div class="col-md-6">
				<h3><liferay-ui:message key="total" /></h3>
			</div>

			<div class="col-md-6">
				<h3><%= HtmlUtil.escape(commerceCartContentTotalDisplayContext.getCommerceCartTotal()) %></h3>
			</div>
		</div>
	</div>

	<aui:button-row>

		<%
		PortletURL checkoutPortletURL = commerceCartContentTotalDisplayContext.getCheckoutPortletURL();
		%>

		<aui:button cssClass="btn-lg btn-primary" href="<%= checkoutPortletURL.toString() %>" value="checkout" />
	</aui:button-row>
</liferay-ddm:template-renderer>