<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommercePaymentMethodGroupRelsDisplayContext commercePaymentMethodGroupRelsDisplayContext = (CommercePaymentMethodGroupRelsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<liferay-frontend:side-panel-content
	screenNavigatorKey="<%= CommercePaymentScreenNavigationConstants.SCREEN_NAVIGATION_KEY_COMMERCE_PAYMENT_METHOD %>"
	screenNavigatorModelBean="<%= commercePaymentMethodGroupRelsDisplayContext.getCommercePaymentMethodGroupRel() %>"
	screenNavigatorPortletURL="<%= currentURLObj %>"
	title="<%= commercePaymentMethodGroupRelsDisplayContext.getCommercePaymentMethodEngineName(locale) %>"
/>