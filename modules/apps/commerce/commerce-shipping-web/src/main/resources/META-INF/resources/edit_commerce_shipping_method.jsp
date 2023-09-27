<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceShippingMethodsDisplayContext commerceShippingMethodsDisplayContext = (CommerceShippingMethodsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceShippingMethod commerceShippingMethod = commerceShippingMethodsDisplayContext.getCommerceShippingMethod();

if (commerceShippingMethod != null) {
	currentURLObj.setParameter("commerceShippingMethodId", String.valueOf(commerceShippingMethod.getCommerceShippingMethodId()));
}
%>

<liferay-frontend:side-panel-content
	screenNavigatorKey="<%= CommerceShippingScreenNavigationConstants.SCREEN_NAVIGATION_KEY_COMMERCE_SHIPPING_METHOD %>"
	screenNavigatorModelBean="<%= commerceShippingMethod %>"
	screenNavigatorPortletURL="<%= currentURLObj %>"
	title="<%= commerceShippingMethodsDisplayContext.getCommerceShippingMethodEngineName(locale) %>"
/>