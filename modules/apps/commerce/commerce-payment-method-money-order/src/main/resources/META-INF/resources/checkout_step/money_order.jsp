<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
MoneyOrderCheckoutStepDisplayContext moneyOrderCheckoutStepDisplayContext = (MoneyOrderCheckoutStepDisplayContext)request.getAttribute(CommerceCheckoutWebKeys.COMMERCE_CHECKOUT_STEP_DISPLAY_CONTEXT);
%>

<c:if test="<%= Validator.isNotNull(moneyOrderCheckoutStepDisplayContext.getMessage()) %>">
	<%= HtmlUtil.escape(moneyOrderCheckoutStepDisplayContext.getMessage()) %>
</c:if>