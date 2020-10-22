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
String navigation = ParamUtil.getString(request, "navigation", "redirects");
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems='<%=
		new JSPNavigationItemList(pageContext) {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(!navigation.equals("404-urls"));
						navigationItem.setHref(renderResponse.createRenderURL());
						navigationItem.setLabel(LanguageUtil.get(httpServletRequest, "redirects"));
					});

				add(
					navigationItem -> {
						navigationItem.setActive(navigation.equals("404-urls"));
						navigationItem.setHref(renderResponse.createRenderURL(), "navigation", "404-urls");
						navigationItem.setLabel(LanguageUtil.format(httpServletRequest, "x-urls", HttpServletResponse.SC_NOT_FOUND, false));
					});
			}
		}
	%>'
/>

<c:choose>
	<c:when test='<%= navigation.equals("404-urls") %>'>
		<liferay-util:include page="/view_redirect_not_found_entries.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:otherwise>
		<liferay-util:include page="/view_redirect_entries.jsp" servletContext="<%= application %>" />
	</c:otherwise>
</c:choose>