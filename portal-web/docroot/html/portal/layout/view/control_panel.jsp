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

boolean showControlPanelMenu = true;

if (controlPanelCategory.equals(PortletCategoryKeys.CURRENT_SITE)) {
	showControlPanelMenu = false;
}

if (controlPanelCategory.equals(PortletCategoryKeys.CURRENT_SITE)) {
	controlPanelCategory = PortletCategoryKeys.SITE_ADMINISTRATION;
}

List<Portlet> portlets = PortalUtil.getControlPanelPortlets(controlPanelCategory, themeDisplay);

if (Validator.isNull(ppid)) {
	if (controlPanelCategory.equals(PortletCategoryKeys.SITE_ADMINISTRATION)) {
		Portlet firstPortlet = PortalUtil.getFirstSiteAdministrationPortlet(themeDisplay);

		ppid = firstPortlet.getPortletId();
	}
	else {
		for (Portlet portlet : portlets) {
			if (PortletPermissionUtil.hasControlPanelAccessPermission(permissionChecker, scopeGroupId, portlet)) {
				ppid = portlet.getPortletId();

				break;
			}
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

		if (category.equals(PortletCategoryKeys.APPS)) {
			panelCategory += " panel-manage-apps";
		}
		else if (category.equals(PortletCategoryKeys.CONFIGURATION)) {
			panelCategory += " panel-manage-configuration";
		}
		else if (category.equals(PortletCategoryKeys.MY)) {
			panelCategory += " panel-manage-my";
			categoryTitle = user.getFullName();
		}
		else if (category.equals(PortletCategoryKeys.SITES)) {
			panelCategory += " panel-manage-sites";
		}
		else if (category.equals(PortletCategoryKeys.USERS)) {
			panelCategory += " panel-manage-users";
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

		if (Validator.isNotNull(categoryTitle) && !category.startsWith(PortletCategoryKeys.SITE_ADMINISTRATION)) {
			PortalUtil.addPortletBreadcrumbEntry(request, categoryTitle, null);
		}
		%>

		<div id="content-wrapper">
			<div class="<%= panelCategory %>">
				<c:if test="<%= showControlPanelMenu %>">
					<%@ include file="/html/portal/layout/view/control_panel_nav_main.jspf" %>
				</c:if>

				<div class="<%= panelBodyCssClass %>">
					<c:choose>
						<c:when test="<%= Validator.isNull(controlPanelCategory) %>">
							<%@ include file="/html/portal/layout/view/control_panel_home.jspf" %>
						</c:when>
						<c:when test="<%= ((portlet != null) && !portlet.getControlPanelEntryCategory().startsWith(PortletCategoryKeys.SITE_ADMINISTRATION)) %>">
							<%@ include file="/html/portal/layout/view/panel_content.jspf" %>
						</c:when>
						<c:otherwise>
							<aui:container cssClass="<%= panelCategory %>">
								<aui:row>
									<h1><%= curGroup.getDescriptiveName(themeDisplay.getLocale()) %></h1>
								</aui:row>
								<aui:row>
									<aui:col cssClass="panel-page-menu" width="<%= 25 %>">
										<liferay-portlet:runtime portletName="160" />
									</aui:col>

									<aui:col cssClass="<%= panelBodyCssClass %>"  width="<%= 75 %>">
										<%@ include file="/html/portal/layout/view/panel_content.jspf" %>
									</aui:col>
								</aui:row>
							</aui:container>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
	</c:when>
	<c:otherwise>
		<%@ include file="/html/portal/layout/view/panel_content.jspf" %>
	</c:otherwise>
</c:choose>

<%@ include file="/html/portal/layout/view/common.jspf" %>