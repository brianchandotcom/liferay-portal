<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

ServletContext commerceAdminServletContext = (ServletContext)request.getAttribute(CommerceAdminWebKeys.COMMERCE_ADMIN_SERVLET_CONTEXT);

CommerceTaxMethodsDisplayContext commerceTaxMethodsDisplayContext = (CommerceTaxMethodsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceTaxMethod commerceTaxMethod = commerceTaxMethodsDisplayContext.getCommerceTaxMethod();

String title = LanguageUtil.format(request, "edit-x", commerceTaxMethod.getName(locale), false);

Map<String, Object> data = new HashMap<>();

data.put("direction-right", StringPool.TRUE);

String screenNavigationEntryKey = commerceTaxMethodsDisplayContext.getScreenNavigationEntryKey();

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, commerceAdminModuleKey), redirect, data);
PortalUtil.addPortletBreadcrumbEntry(request, title, currentURL, data);
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, screenNavigationEntryKey), StringPool.BLANK, data);

renderResponse.setTitle(LanguageUtil.get(request, "settings"));
%>

<liferay-util:include page="/navbar.jsp" servletContext="<%= commerceAdminServletContext %>">
	<liferay-util:param name="commerceAdminModuleKey" value="<%= commerceAdminModuleKey %>" />
</liferay-util:include>

<%@ include file="/breadcrumb.jspf" %>

<liferay-frontend:screen-navigation
	containerCssClass="col-md-10"
	key="<%= CommerceTaxScreenNavigationConstants.SCREEN_NAVIGATION_KEY_COMMERCE_TAX_METHOD %>"
	modelBean="<%= commerceTaxMethod %>"
	navCssClass="col-md-2"
	portletURL="<%= currentURLObj %>"
/>