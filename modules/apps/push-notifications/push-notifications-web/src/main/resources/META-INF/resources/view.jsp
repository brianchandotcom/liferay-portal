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
String tabs1 = ParamUtil.getString(request, "tabs1", "devices");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("tabs1", tabs1);
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems='<%=
		new JSPNavigationItemList(pageContext) {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(tabs1.equals("devices"));
						navigationItem.setHref(renderResponse.createRenderURL());
						navigationItem.setLabel(LanguageUtil.get(request, "devices"));
					});
				add(
					navigationItem -> {
						navigationItem.setActive(tabs1.equals("test"));
						navigationItem.setHref(renderResponse.createRenderURL(), "tabs1", "test");
						navigationItem.setLabel(LanguageUtil.get(request, "test"));
					});

			}
		}
	%>'
/>

<clay:container>
	<c:choose>
		<c:when test='<%= tabs1.equals("test") %>'>
			<%@ include file="/test.jspf" %>
		</c:when>
		<c:otherwise>
			<%@ include file="/devices.jspf" %>
		</c:otherwise>
	</c:choose>
</clay:container>