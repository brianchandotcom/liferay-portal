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
ServletContext commerceAdminServletContext = (ServletContext)request.getAttribute(CommerceAdminWebKeys.COMMERCE_ADMIN_SERVLET_CONTEXT);

CommerceCountriesDisplayContext commerceCountriesDisplayContext = (CommerceCountriesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceCountry commerceCountry = commerceCountriesDisplayContext.getCommerceCountry();

PortletURL portletURL = commerceCountriesDisplayContext.getPortletURL();

portletURL.setParameter("mvcRenderCommandName", "editCommerceCountry");

String title = LanguageUtil.get(request, "add-country");

if (commerceCountry != null) {
	title = LanguageUtil.format(request, "edit-x", commerceCountry.getName(locale), false);
}

Map<String, Object> data = new HashMap<>();

data.put("direction-right", StringPool.TRUE);

String selectedScreenNavigationCategoryKey = commerceCountriesDisplayContext.getSelectedScreenNavigationCategoryKey();

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, commerceAdminModuleKey), countriesURL, data);
PortalUtil.addPortletBreadcrumbEntry(request, title, portletURL.toString(), data);
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, selectedScreenNavigationCategoryKey), StringPool.BLANK, data);

renderResponse.setTitle(LanguageUtil.get(request, "settings"));
%>

<liferay-util:include page="/navbar.jsp" servletContext="<%= commerceAdminServletContext %>">
	<liferay-util:param name="commerceAdminModuleKey" value="<%= commerceAdminModuleKey %>" />
</liferay-util:include>

<%@ include file="/breadcrumb.jspf" %>

<liferay-frontend:screen-navigation
	containerCssClass="col-md-10"
	context="<%= commerceCountry %>"
	key="<%= CommerceCountryScreenNavigationConstants.SCREEN_NAVIGATION_KEY_COMMERCE_COUNTRY_GENERAL %>"
	navCssClass="col-md-2"
	portletURL="<%= currentURLObj %>"
/>