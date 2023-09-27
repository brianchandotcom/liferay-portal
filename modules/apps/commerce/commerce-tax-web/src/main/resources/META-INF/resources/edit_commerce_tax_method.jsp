<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceTaxMethodsDisplayContext commerceTaxMethodsDisplayContext = (CommerceTaxMethodsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceTaxMethod commerceTaxMethod = commerceTaxMethodsDisplayContext.getCommerceTaxMethod();

if (commerceTaxMethod != null) {
	currentURLObj.setParameter("commerceTaxMethodId", String.valueOf(commerceTaxMethod.getCommerceTaxMethodId()));
}
%>

<liferay-frontend:side-panel-content
	screenNavigatorKey="<%= CommerceTaxScreenNavigationConstants.SCREEN_NAVIGATION_KEY_COMMERCE_TAX_METHOD %>"
	screenNavigatorModelBean="<%= commerceTaxMethod %>"
	screenNavigatorPortletURL="<%= currentURLObj %>"
	title="<%= commerceTaxMethodsDisplayContext.getCommerceTaxMethodEngineName(locale) %>"
/>