<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String tabs2 = ParamUtil.getString(request, "tabs2", "portlets");

PortletURL portletURL = PortletURLBuilder.createRenderURL(
	renderResponse
).setTabs2(
	tabs2
).setParameter(
	"struts_action", "/plugins_admin/view"
).buildPortletURL();

PortletURL marketplaceURL = null;

boolean showEditPluginHREF = true;
%>

<clay:navigation-bar
	navigationItems='<%=
		new JSPNavigationItemList(pageContext) {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(tabs2.equals("portlets"));
						navigationItem.setHref(renderResponse.createRenderURL(), "tabs2", "portlets");
						navigationItem.setLabel(LanguageUtil.get(httpServletRequest, "portlets"));
					});

				add(
					navigationItem -> {
						navigationItem.setActive(tabs2.equals("themes"));
						navigationItem.setHref(renderResponse.createRenderURL(), "tabs2", "themes");
						navigationItem.setLabel(LanguageUtil.get(httpServletRequest, "themes"));
					});

				add(
					navigationItem -> {
						navigationItem.setActive(tabs2.equals("layout-templates"));
						navigationItem.setHref(renderResponse.createRenderURL(), "tabs2", "layout-templates");
						navigationItem.setLabel(LanguageUtil.get(httpServletRequest, "layout-templates"));
					});
			}
		}
	%>'
/>

<clay:container-fluid>
	<c:choose>
		<c:when test='<%= tabs2.equals("themes") %>'>
			<%@ include file="/themes.jspf" %>
		</c:when>
		<c:when test='<%= tabs2.equals("layout-templates") %>'>
			<%@ include file="/layout_templates.jspf" %>
		</c:when>
		<c:when test='<%= tabs2.equals("hook-plugins") %>'>
		</c:when>
		<c:when test='<%= tabs2.equals("web-plugins") %>'>
		</c:when>
		<c:otherwise>
			<%@ include file="/portlets.jspf" %>
		</c:otherwise>
	</c:choose>
</clay:container-fluid>