<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceShippingFixedOptionsDisplayContext commerceShippingFixedOptionsDisplayContext = (CommerceShippingFixedOptionsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<liferay-frontend:side-panel-content
	screenNavigatorKey="<%= CommerceShippingFixedOptionScreenNavigationConstants.SCREEN_NAVIGATION_KEY_COMMERCE_SHIPPING_FIXED_OPTION %>"
	screenNavigatorModelBean="<%= commerceShippingFixedOptionsDisplayContext.getCommerceShippingFixedOption() %>"
	screenNavigatorPortletURL="<%= currentURLObj %>"
	title="<%= commerceShippingFixedOptionsDisplayContext.getCommerceShippingFixedOptionName(resourceBundle) %>"
/>