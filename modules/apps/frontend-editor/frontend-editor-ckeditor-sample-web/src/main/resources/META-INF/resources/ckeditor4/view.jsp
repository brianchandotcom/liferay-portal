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
						navigationItem.setHref(renderResponse.createRenderURL(), "navigation", "ckeditor4");
						navigationItem.setLabel("Classic");
					});
				add(
					navigationItem -> {
						navigationItem.setActive(innerNavigation.equals("react"));
						navigationItem.setHref(renderResponse.createRenderURL(), "navigation", "ckeditor4", "innerNavigation", "react");
						navigationItem.setLabel("React");
					});
				add(
					navigationItem -> {
						navigationItem.setActive(innerNavigation.equals("legacy"));
						navigationItem.setHref(renderResponse.createRenderURL(), "navigation", "ckeditor4", "innerNavigation", "legacy");
						navigationItem.setLabel("Legacy");
					});
				add(
					navigationItem -> {
						navigationItem.setActive(innerNavigation.equals("alloy"));
						navigationItem.setHref(renderResponse.createRenderURL(), "navigation", "ckeditor4", "innerNavigation", "alloy");
						navigationItem.setLabel("Alloy");
					});
				add(
					navigationItem -> {
						navigationItem.setActive(innerNavigation.equals("balloon"));
						navigationItem.setHref(renderResponse.createRenderURL(), "navigation", "ckeditor4", "innerNavigation", "balloon");
						navigationItem.setLabel("Balloon");
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
			<liferay-util:include page="/ckeditor4/partials/classic.jsp" servletContext="<%= application %>" />
		</c:when>
		<c:when test='<%= StringUtil.equals(innerNavigation, "react") %>'>
			<liferay-util:include page="/ckeditor4/partials/react.jsp" servletContext="<%= application %>" />
		</c:when>
		<c:when test='<%= StringUtil.equals(innerNavigation, "legacy") %>'>
			<liferay-util:include page="/ckeditor4/partials/legacy.jsp" servletContext="<%= application %>" />
		</c:when>
		<c:when test='<%= StringUtil.equals(innerNavigation, "alloy") %>'>
			<liferay-util:include page="/ckeditor4/partials/alloy.jsp" servletContext="<%= application %>" />
		</c:when>
		<c:otherwise>
			<liferay-util:include page="/ckeditor4/partials/balloon.jsp" servletContext="<%= application %>" />
		</c:otherwise>
	</c:choose>
</clay:container-fluid>