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
CommerceShippingMethodsDisplayContext commerceShippingMethodsDisplayContext = (CommerceShippingMethodsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceShippingMethod commerceShippingMethod = commerceShippingMethodsDisplayContext.getCommerceShippingMethod();

if (commerceShippingMethod != null) {
	currentURLObj.setParameter("commerceShippingMethodId", String.valueOf(commerceShippingMethod.getCommerceShippingMethodId()));
}
%>

<commerce-ui:side-panel-content
	screenNavigatorKey="<%= CommerceShippingScreenNavigationConstants.SCREEN_NAVIGATION_KEY_COMMERCE_SHIPPING_METHOD %>"
	screenNavigatorModelBean="<%= commerceShippingMethod %>"
	screenNavigatorPortletURL="<%= currentURLObj %>"
	title="<%= commerceShippingMethodsDisplayContext.getCommerceShippingMethodEngineName(locale) %>"
/>