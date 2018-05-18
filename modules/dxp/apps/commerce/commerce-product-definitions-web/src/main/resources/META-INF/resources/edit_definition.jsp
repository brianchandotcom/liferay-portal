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
CPDefinitionsDisplayContext cpDefinitionsDisplayContext = (CPDefinitionsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinition cpDefinition = cpDefinitionsDisplayContext.getCPDefinition();

String productURL = cpDefinitionsDisplayContext.getProductURL(cpDefinition);

PortletURL portletURL = cpDefinitionsDisplayContext.getEditProductDefinitionURL();

String title = LanguageUtil.get(request, "add-product");

if (cpDefinition != null) {
	title = cpDefinition.getName(languageId);
}

Map<String, Object> data = new HashMap<>();

data.put("direction-right", StringPool.TRUE);

String selectedScreenNavigationCategoryKey = cpDefinitionsDisplayContext.getSelectedScreenNavigationCategoryKey();

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "products"), catalogURL, data);
PortalUtil.addPortletBreadcrumbEntry(request, title, portletURL.toString(), data);
PortalUtil.addPortletBreadcrumbEntry(request, selectedScreenNavigationCategoryKey, StringPool.BLANK, data);

request.setAttribute("view.jsp-cpDefinition", cpDefinition);
request.setAttribute("view.jsp-cpType", cpDefinitionsDisplayContext.getCPType());
request.setAttribute("view.jsp-portletURL", portletURL);
request.setAttribute("view.jsp-showSearch", false);
%>

<clay:navigation-bar
	inverted="<%= true %>"
	items="<%= CPNavigationItemRegistryUtil.getNavigationItems(renderRequest) %>"
/>

<%@ include file="/definition_breadcrumb.jspf" %>

<liferay-frontend:screen-navigation
	containerCssClass="col-md-10"
	key="<%= CPDefinitionScreenNavigationConstants.SCREEN_NAVIGATION_KEY_CP_DEFINITION_GENERAL %>"
	modelBean="<%= cpDefinition %>"
	navCssClass="col-md-2"
	portletURL="<%= currentURLObj %>"
/>