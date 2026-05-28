<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String navigation = ParamUtil.getString(request, "navigation", "classic");
%>

<clay:container-fluid>
	<clay:navigation-bar
		navigationItems='<%=
			new JSPNavigationItemList(pageContext) {
				{
					add(
						navigationItem -> {
							navigationItem.setActive(navigation.equals("classic"));
							navigationItem.setHref(renderResponse.createRenderURL());
							navigationItem.setLabel("Classic");
						});
					add(
						navigationItem -> {
							navigationItem.setActive(navigation.equals("react"));
							navigationItem.setHref(renderResponse.createRenderURL(), "navigation", "react");
							navigationItem.setLabel("React");
						});
					add(
						navigationItem -> {
							navigationItem.setActive(navigation.equals("legacy"));
							navigationItem.setHref(renderResponse.createRenderURL(), "navigation", "legacy");
							navigationItem.setLabel("Legacy");
						});
					add(
						navigationItem -> {
							navigationItem.setActive(navigation.equals("alloy"));
							navigationItem.setHref(renderResponse.createRenderURL(), "navigation", "alloy");
							navigationItem.setLabel("Alloy");
						});
					add(
						navigationItem -> {
							navigationItem.setActive(navigation.equals("balloon"));
							navigationItem.setHref(renderResponse.createRenderURL(), "navigation", "balloon");
							navigationItem.setLabel("Balloon");
						});
				}
			}
		%>'
	/>

	<div class="mt-3">
		<c:choose>
			<c:when test='<%= StringUtil.equals(navigation, "classic") %>'>
				<liferay-util:include page="/partials/classic.jsp" servletContext="<%= application %>" />
			</c:when>
			<c:when test='<%= StringUtil.equals(navigation, "react") %>'>
				<liferay-util:include page="/partials/react.jsp" servletContext="<%= application %>" />
			</c:when>
			<c:when test='<%= StringUtil.equals(navigation, "legacy") %>'>
				<liferay-util:include page="/partials/legacy.jsp" servletContext="<%= application %>" />
			</c:when>
			<c:when test='<%= StringUtil.equals(navigation, "alloy") %>'>
				<liferay-util:include page="/partials/alloy.jsp" servletContext="<%= application %>" />
			</c:when>
			<c:otherwise>
				<liferay-util:include page="/partials/balloon.jsp" servletContext="<%= application %>" />
			</c:otherwise>
		</c:choose>
	</div>
</clay:container-fluid>