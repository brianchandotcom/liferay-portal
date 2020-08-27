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
String orderDetailURL = (String)request.getAttribute(CommerceCheckoutWebKeys.COMMERCE_CHECKOUT_STEP_ORDER_DETAIL_URL);
%>

<div class="commerce-checkout-confirmation">
	<div class="success-message">
		<liferay-ui:message key="your-order-has-already-been-placed" />
	</div>

	<aui:button-row>
		<aui:button href="<%= orderDetailURL %>" primary="<%= true %>" type="submit" value="go-to-order-details" />
	</aui:button-row>
</div>