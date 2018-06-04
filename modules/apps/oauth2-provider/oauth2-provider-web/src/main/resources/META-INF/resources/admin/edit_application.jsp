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

<%@ include file="/admin/init.jsp" %>

<%
final String redirect = ParamUtil.getString(request, "redirect");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

OAuth2Application oAuth2Application = oAuth2AdminPortletDisplayContext.getOAuth2Application();

long oAuth2ApplicationId = 0;

String headerTitle = LanguageUtil.get(request, "add-o-auth2-application");

if (oAuth2Application != null) {
	oAuth2ApplicationId = oAuth2Application.getOAuth2ApplicationId();
	headerTitle = oAuth2Application.getName();
}

renderResponse.setTitle(headerTitle);

final String currentAppTab = ParamUtil.getString(request, "appTab", "credentials");
final String oAuth2ApplicationIdString = String.valueOf(oAuth2ApplicationId);
%>

<c:if test="<%= oAuth2Application != null %>">
	<clay:navigation-bar
		inverted="<%= true %>"
		navigationItems="<%=
				new JSPNavigationItemList(pageContext) {
				{
					add(
						navigationItem -> {
							PortletURL portletURL = renderResponse.createRenderURL();
							portletURL.setParameter("appTab", "credentials");
							portletURL.setParameter("mvcRenderCommandName", "/admin/update_oauth2_application");
							portletURL.setParameter("oAuth2ApplicationId", oAuth2ApplicationIdString);
							portletURL.setParameter("redirect", redirect);

							navigationItem.setActive(currentAppTab.equals("credentials"));
							navigationItem.setHref(portletURL.toString());
							navigationItem.setLabel(LanguageUtil.get(request, "credentials"));
						});
					add(
						navigationItem -> {
							PortletURL portletURL = renderResponse.createRenderURL();
							portletURL.setParameter("appTab", "assign_scopes");
							portletURL.setParameter("mvcRenderCommandName", "/admin/assign_scopes");
							portletURL.setParameter("oAuth2ApplicationId", oAuth2ApplicationIdString);
							portletURL.setParameter("redirect", redirect);

							navigationItem.setActive(currentAppTab.equals("assign_scopes"));
							navigationItem.setHref(portletURL.toString());
							navigationItem.setLabel(LanguageUtil.get(request, "scopes"));
						});
					if (oAuth2AdminPortletDisplayContext.hasViewGrantedAuthorizationsPermission()) {
						add(
							navigationItem -> {
								PortletURL portletURL = renderResponse.createRenderURL();
								portletURL.setParameter("appTab", "application_authorizations");
								portletURL.setParameter("mvcRenderCommandName", "/admin/view_oauth2_authorizations");
								portletURL.setParameter("oAuth2ApplicationId", oAuth2ApplicationIdString);
								portletURL.setParameter("redirect", redirect);

								navigationItem.setActive(currentAppTab.equals("application_authorizations"));
								navigationItem.setHref(portletURL.toString());
								navigationItem.setLabel(LanguageUtil.get(request, "authorizations"));
							});
					}
				}
			}
		%>"
	/>
</c:if>

<c:choose>
	<c:when test='<%= currentAppTab.equals("credentials") %>'>
		<liferay-util:include page="/admin/edit_application_credentials.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:when test='<%= (oAuth2Application != null) && currentAppTab.equals("assign_scopes") %>'>
		<liferay-util:include page="/admin/assign_scopes.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:when test='<%= (oAuth2Application != null) && currentAppTab.equals("application_authorizations") && oAuth2AdminPortletDisplayContext.hasViewGrantedAuthorizationsPermission() %>'>
		<liferay-util:include page="/admin/application_authorizations.jsp" servletContext="<%= application %>" />
	</c:when>
</c:choose>