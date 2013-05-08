<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portal/init.jsp" %>

<%
String ppid = ParamUtil.getString(request, "p_p_id");

String controlPanelCategory = themeDisplay.getControlPanelCategory();

if (controlPanelCategory.equals(PortletCategoryKeys.CONTENT) && Validator.isNull(ppid)) {
	List<Portlet> portlets = PortalUtil.getControlPanelPortlets(PortletCategoryKeys.CONTENT, themeDisplay);

	for (Portlet portlet : portlets) {
		if (PortletPermissionUtil.hasControlPanelAccessPermission(permissionChecker, scopeGroupId, portlet)) {
			ppid = portlet.getPortletId();

			break;
		}
	}
}

if (ppid.equals(PortletKeys.PORTLET_CONFIGURATION)) {
	String portletResource = ParamUtil.getString(request, PortalUtil.getPortletNamespace(ppid) + "portletResource");

	if (Validator.isNull(portletResource)) {
		portletResource = ParamUtil.getString(request, "portletResource");
	}

	if (Validator.isNotNull(portletResource)) {
		String strutsAction = ParamUtil.getString(request, PortalUtil.getPortletNamespace(ppid) + "struts_action");

		if (!strutsAction.startsWith("/portlet_configuration/")) {
			ppid = portletResource;
		}
	}
}

if (ppid.equals(PortletKeys.PLUGIN_INSTALLER)) {
	ppid = PortletKeys.ADMIN_PLUGINS;
}

String category = PortalUtil.getControlPanelCategory(ppid, themeDisplay);

List<Layout> scopeLayouts = new ArrayList<Layout>();

Portlet portlet = null;

boolean denyAccess = true;

if (Validator.isNull(ppid)) {
	denyAccess = false;
}
else {
	portlet = PortletLocalServiceUtil.getPortletById(company.getCompanyId(), ppid);

	if ((portlet != null) &&
		(portlet.isSystem() || PortletPermissionUtil.hasControlPanelAccessPermission(permissionChecker, scopeGroupId, portlet) || PortalUtil.isAllowAddPortletDefaultResource(request, portlet))) {

		denyAccess = false;
	}
}

request.setAttribute("control_panel.jsp-ppid", ppid);
%>

<c:choose>
	<c:when test="<%= !themeDisplay.isStatePopUp() %>">

		<%
		String panelBodyCssClass = "panel-page-body";
		String panelCategory = "lfr-ctrl-panel";
		String categoryTitle = Validator.isNotNull(category) ? LanguageUtil.get(pageContext, "category." + category) : StringPool.BLANK;

		if (!layoutTypePortlet.hasStateMax()) {
			panelBodyCssClass += " panel-page-frontpage";
		}
		else {
			panelBodyCssClass += " panel-page-application";
		}

		if (category.equals(PortletCategoryKeys.CONTENT)) {
			panelCategory += " panel-manage-content";
		}
		else if (category.equals(PortletCategoryKeys.MY)) {
			panelCategory += " panel-manage-my";
			categoryTitle = user.getFullName();
		}
		else if (category.equals(PortletCategoryKeys.PORTAL)) {
			panelCategory += " panel-manage-portal";

			if (CompanyLocalServiceUtil.getCompaniesCount(false) > 1) {
				categoryTitle += " " + company.getName();
			}
		}
		else if (category.equals(PortletCategoryKeys.SERVER)) {
			panelCategory += " panel-manage-server";
		}
		else {
			panelCategory += " panel-manage-frontpage";
		}

		Layout scopeLayout = null;
		Group curGroup = themeDisplay.getScopeGroup();

		if (curGroup.isLayout()) {
			scopeLayout = LayoutLocalServiceUtil.getLayout(curGroup.getClassPK());
			curGroup = scopeLayout.getGroup();
		}

		if (Validator.isNotNull(categoryTitle) && !category.equals(PortletCategoryKeys.CONTENT)) {
			PortalUtil.addPortletBreadcrumbEntry(request, categoryTitle, null);
		}
		%>

		<div id="content-wrapper">
			<aui:container cssClass="<%= panelCategory %>">
				<aui:row>
					<aui:col cssClass="panel-page-menu" width="<%= 25 %>">
						<liferay-portlet:runtime portletName="160" />
					</aui:col>

					<aui:col cssClass="<%= panelBodyCssClass %>" width="<%= 75 %>">
						<%@ include file="/html/portal/layout/view/panel_content.jspf" %>
					</aui:col>
				</aui:row>
			</aui:container>
		</div>
	</c:when>
	<c:otherwise>
		<%@ include file="/html/portal/layout/view/panel_content.jspf" %>
	</c:otherwise>
</c:choose>

<%@ include file="/html/portal/layout/view/common.jspf" %>