<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
OrderSummaryCheckoutStepDisplayContext orderSummaryCheckoutStepDisplayContext = (OrderSummaryCheckoutStepDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceOrder commerceOrder = orderSummaryCheckoutStepDisplayContext.getCommerceOrder();
%>

<react:component
	module="{Multishipping} from commerce-frontend-js"
	props='<%=
		HashMapBuilder.<String, Object>put(
			"accountId", commerceOrder.getCommerceAccountId()
		).put(
			"orderId", commerceOrder.getCommerceOrderId()
		).put(
			"readonly", true
		).build()
	%>'
/>