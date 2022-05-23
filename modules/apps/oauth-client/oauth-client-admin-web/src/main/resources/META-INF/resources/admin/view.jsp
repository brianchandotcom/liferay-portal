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
String navigation = ParamUtil.getString(request, "navigation", "oauth-clients");

String redirect = ParamUtil.getString(request, "redirect");

renderResponse.setTitle("OAuth Client Administration");

portletDisplay.setURLBack(redirect);
%>

<clay:navigation-bar
	navigationItems='<%=
		new JSPNavigationItemList(pageContext) {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(navigation.equals("oauth-clients"));

						PortletURL portletURL = PortletURLBuilder.createRenderURL(
							renderResponse
						).setMVCRenderCommandName(
							"/oauth_client_admin/view_o_auth_clients"
						).setRedirect(
							redirect
						).setNavigation(
							"oauth-clients"
						).buildPortletURL();

						navigationItem.setHref(portletURL.toString());

						navigationItem.setLabel(LanguageUtil.get(httpServletRequest, "oauth-clients"));
					});

				add(
					navigationItem -> {
						navigationItem.setActive(navigation.equals("oauth-client-as-local-metadata"));

						PortletURL portletURL = PortletURLBuilder.createRenderURL(
							renderResponse
						).setMVCRenderCommandName(
							"/oauth_client_admin/view_o_auth_client_as_local_metadata"
						).setRedirect(
							redirect
						).setNavigation(
							"oauth-client-as-local-metadata"
						).buildPortletURL();

						navigationItem.setHref(portletURL.toString());

						navigationItem.setLabel(LanguageUtil.get(httpServletRequest, "oauth-client-as-local-metadata"));
					});
			}
		}
	%>'
/>

<c:choose>
	<c:when test='<%= navigation.equals("oauth-clients") %>'>
		<liferay-util:include page="/admin/view_oauth_clients.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:when test='<%= navigation.equals("oauth-client-as-local-metadata") %>'>
		<liferay-util:include page="/admin/view_oauth_client_as_local_metadata.jsp" servletContext="<%= application %>" />
	</c:when>
</c:choose>