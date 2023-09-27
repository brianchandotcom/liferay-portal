<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommercePriceListDisplayContext commercePriceListDisplayContext = (CommercePriceListDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<liferay-frontend:side-panel-content
	screenNavigatorKey="<%= CommercePriceListScreenNavigationConstants.SCREEN_NAVIGATION_KEY_COMMERCE_PRICE_MODIFIER_GENERAL %>"
	screenNavigatorModelBean="<%= commercePriceListDisplayContext.getCommercePriceModifier() %>"
	screenNavigatorPortletURL="<%= currentURLObj %>"
	title='<%= LanguageUtil.get(request, "edit-price-modifier") %>'
/>