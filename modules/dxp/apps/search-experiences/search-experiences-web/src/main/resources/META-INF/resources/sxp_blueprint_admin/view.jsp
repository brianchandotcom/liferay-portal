<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String tabs1 = ParamUtil.getString(request, "tabs1", "sxpBlueprints");
%>

<clay:navigation-bar
	inverted="<%= false %>"
	navigationItems='<%=
		new JSPNavigationItemList(pageContext) {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(tabs1.equals("sxpBlueprints"));
						navigationItem.setHref(renderResponse.createRenderURL(), "tabs1", "sxpBlueprints", "mvcRenderCommandName", "/sxp_blueprint_admin/view_sxp_blueprints");
						navigationItem.setLabel(LanguageUtil.get(request, "blueprints"));
					});
				add(
					navigationItem -> {
						navigationItem.setActive(tabs1.equals("sxpElements"));
						navigationItem.setHref(renderResponse.createRenderURL(), "tabs1", "sxpElements", "mvcRenderCommandName", "/sxp_blueprint_admin/view_sxp_elements", "hidden", Boolean.FALSE);
						navigationItem.setLabel(LanguageUtil.get(request, "elements"));
					});
			}
		}
	%>'
/>

<c:choose>
	<c:when test='<%= tabs1.equals("sxpElements") %>'>
		<liferay-util:include page="/sxp_blueprint_admin/view_sxp_elements.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:otherwise>
		<liferay-util:include page="/sxp_blueprint_admin/view_sxp_blueprints.jsp" servletContext="<%= application %>" />
	</c:otherwise>
</c:choose>