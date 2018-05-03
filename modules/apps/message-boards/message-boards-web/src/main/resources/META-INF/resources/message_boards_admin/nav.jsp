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

<%@ include file="/message_boards/init.jsp" %>

<%
String navItemSelected = ParamUtil.getString(request, "navItemSelected");

PortletURL messageBoardsHomeURL = renderResponse.createRenderURL();

messageBoardsHomeURL.setParameter("mvcRenderCommandName", "/message_boards/view");
messageBoardsHomeURL.setParameter("tag", StringPool.BLANK);

PortletURL viewStatisticsURL = renderResponse.createRenderURL();

viewStatisticsURL.setParameter("mvcRenderCommandName", "/message_boards/view_statistics");

PortletURL bannedUsersURL = renderResponse.createRenderURL();

bannedUsersURL.setParameter("mvcRenderCommandName", "/message_boards/view_banned_users");
%>

<clay:navigation-bar
	inverted="<%= true %>"
	items='<%=
		new JSPNavigationItemList(pageContext) {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(navItemSelected.equals("threads"));
						navigationItem.setHref(messageBoardsHomeURL);
						navigationItem.setLabel(LanguageUtil.get(request, "threads"));
					});

				add(
					navigationItem -> {
						navigationItem.setActive(navItemSelected.equals("statistics"));
						navigationItem.setHref(viewStatisticsURL);
						navigationItem.setLabel(LanguageUtil.get(request, "statistics"));
					});

				add(
					navigationItem -> {
						navigationItem.setActive(navItemSelected.equals("banned-users"));
						navigationItem.setHref(bannedUsersURL);
						navigationItem.setLabel(LanguageUtil.get(request, "banned-users"));
					});
			}
		}
	%>'
/>