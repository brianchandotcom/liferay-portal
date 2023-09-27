<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/blogs_admin/init.jsp" %>

<%
final String navigation = ParamUtil.getString(request, "navigation", "entries");
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems='<%=
		new JSPNavigationItemList(pageContext) {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(navigation.equals("entries"));
						navigationItem.setHref(renderResponse.createRenderURL());
						navigationItem.setLabel(LanguageUtil.get(httpServletRequest, "entries"));
					});

				add(
					navigationItem -> {
						navigationItem.setActive(navigation.equals("images"));
						navigationItem.setHref(renderResponse.createRenderURL(), "navigation", "images");
						navigationItem.setLabel(LanguageUtil.get(httpServletRequest, "images"));
					});
			}
		}
	%>'
/>

<c:choose>
	<c:when test='<%= navigation.equals("entries") %>'>
		<liferay-util:include page="/blogs_admin/view_entries.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:otherwise>
		<liferay-util:include page="/blogs_admin/view_images.jsp" servletContext="<%= application %>" />
	</c:otherwise>
</c:choose>