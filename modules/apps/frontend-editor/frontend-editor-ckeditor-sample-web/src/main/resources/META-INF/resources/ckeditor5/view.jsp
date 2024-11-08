<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String innerNavigation = ParamUtil.getString(request, "innerNavigation", "classic");
%>

<clay:navigation-bar
	navigationItems='<%=
		new JSPNavigationItemList(pageContext) {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(innerNavigation.equals("classic"));
						navigationItem.setHref(renderResponse.createRenderURL(), "navigation", "ckeditor5");
						navigationItem.setLabel("Classic");
					});
				add(
					navigationItem -> {
						navigationItem.setActive(innerNavigation.equals("react"));
						navigationItem.setHref(renderResponse.createRenderURL(), "navigation", "ckeditor5", "innerNavigation", "react");
						navigationItem.setLabel("React");
					});
			}
		}
	%>'
/>

<clay:container-fluid
	cssClass="mt-3"
>
	<c:choose>
		<c:when test='<%= StringUtil.equals(innerNavigation, "classic") %>'>
			<liferay-util:include page="/ckeditor5/partials/classic.jsp" servletContext="<%= application %>" />
		</c:when>
		<c:otherwise>
			<liferay-util:include page="/ckeditor5/partials/react.jsp" servletContext="<%= application %>" />
		</c:otherwise>
	</c:choose>
</clay:container-fluid>