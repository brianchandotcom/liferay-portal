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

<c:if test="<%= !portletName.equals(PortletKeys.FRIENDS_DIRECTORY) %>">
	<clay:navigation-bar
		inverted="<%= false %>"
		navigationItems='<%=
			new JSPNavigationItemList(pageContext) {
				{
					PortletURL portletURL = renderResponse.createRenderURL();

					portletURL.setParameter("mvcRenderCommandName", "/directory/view");
					portletURL.setParameter("tabs1", "users");

					add(
						navigationItem -> {
							navigationItem.setActive(tabs1.equals("users"));
							navigationItem.setHref(portletURL);
							navigationItem.setLabel(LanguageUtil.get(request, "users"));
						});

					portletURL.setParameter("tabs1", "organizations");

					add(
						navigationItem -> {
							navigationItem.setActive(tabs1.equals("organizations"));
							navigationItem.setHref(portletURL);
							navigationItem.setLabel(LanguageUtil.get(request, "organizations"));
						});

					portletURL.setParameter("tabs1", "user-groups");

					add(
						navigationItem -> {
							navigationItem.setActive(tabs1.equals("user-groups"));
							navigationItem.setHref(portletURL);
							navigationItem.setLabel(LanguageUtil.get(request, "user-groups"));
						});
				}
			}
		%>'
	/>
</c:if>