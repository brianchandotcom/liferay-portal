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
String servletPath = GetterUtil.getString(request.getServletPath());

PortletURL baseURL = liferayPortletResponse.createRenderURL();

baseURL.setParameter("p_u_i_d", String.valueOf(selectedUser.getUserId()));
%>

<clay:navigation-bar
	navigationItems='<%=
		new JSPNavigationItemList(pageContext) {
			{
				add(
					navigationItem -> {
						boolean active = servletPath.equals("/review_uad_data.jsp");
						String href = StringPool.BLANK;

						if (!active) {
							baseURL.setParameter("mvcRenderCommandName", "/review_uad_data");

							href = baseURL.toString();
						}

						navigationItem.setActive(active);
						navigationItem.setHref(href);
						navigationItem.setLabel(LanguageUtil.get(request, "review-data"));
					});
				add(
					navigationItem -> {
						boolean active = servletPath.equals("/anonymize_nonreviewable_uad_data.jsp");
						String href = StringPool.BLANK;

						if (!active) {
							baseURL.setParameter("mvcRenderCommandName", "/anonymize_nonreviewable_uad_data");

							href = baseURL.toString();
						}

						navigationItem.setActive(active);
						navigationItem.setHref(href);
						navigationItem.setLabel(LanguageUtil.get(request, "anonymize-nonreviewable-data"));
					});
			}
		} %>'
/>