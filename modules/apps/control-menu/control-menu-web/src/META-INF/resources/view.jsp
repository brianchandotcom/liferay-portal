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
Group group = null;
LayoutSet layoutSet = null;

if (layout != null) {
	group = layout.getGroup();
	layoutSet = layout.getLayoutSet();
}

boolean hasLayoutCustomizePermission = LayoutPermissionUtil.contains(permissionChecker, layout, ActionKeys.CUSTOMIZE);
boolean hasLayoutUpdatePermission = LayoutPermissionUtil.contains(permissionChecker, layout, ActionKeys.UPDATE);

String toggleControlsState = GetterUtil.getString(SessionClicks.get(request, "liferay_toggle_controls", "visible"));

boolean userSetupComplete = false;

if (user.isSetupComplete() || themeDisplay.isImpersonated()) {
	userSetupComplete = true;
}

boolean portalMessageUseAnimation = GetterUtil.getBoolean(PortalMessages.get(request, PortalMessages.KEY_ANIMATION), true);
%>

<c:if test="<%= !group.isControlPanel() && !group.isUserPersonalPanel() && userSetupComplete %>">
	<aui:nav-bar cssClass="dockbar navbar-static-top" data-namespace="<%= renderResponse.getNamespace() %>">
		<aui:nav ariaLabel='<%= LanguageUtil.get(request, "layout-controls") %>' collapsible="<%= false %>" cssClass='<%= portalMessageUseAnimation ? "nav-add-controls navbar-nav" : "nav-add-controls nav-add-controls-notice navbar-nav" %>' icon="pencil" id="navAddControls">
			<aui:nav-item cssClass="dockbar-item" dropdown="<%= true %>" iconCssClass="icon-pencil" toggleTouch="<%= false %>">

				<%
				boolean hasLayoutAddPermission = false;

				if (layout.getParentLayoutId() == LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) {
					hasLayoutAddPermission = GroupPermissionUtil.contains(permissionChecker, group, ActionKeys.ADD_LAYOUT);
				}
				else {
					hasLayoutAddPermission = LayoutPermissionUtil.contains(permissionChecker, layout, ActionKeys.ADD_LAYOUT);
				}
				%>

				<c:if test="<%= !group.isControlPanel() && !group.isUserPersonalPanel() && userSetupComplete && (hasLayoutAddPermission || hasLayoutUpdatePermission || (layoutTypePortlet.isCustomizable() && layoutTypePortlet.isCustomizedView() && hasLayoutCustomizePermission)) %>">
					<portlet:renderURL var="addURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
						<portlet:param name="mvcPath" value="/add_panel.jsp" />
						<portlet:param name="stateMaximized" value="<%= String.valueOf(themeDisplay.isStateMaximized()) %>" />
						<portlet:param name="viewEntries" value="<%= Boolean.TRUE.toString() %>" />
					</portlet:renderURL>

					<aui:nav-item anchorId="addPanel" cssClass="site-add-controls" data-panelURL="<%= HtmlUtil.escapeAttribute(addURL) %>" href="javascript:;" iconCssClass="icon-plus" label="add" />
				</c:if>

				<c:if test="<%= !group.isControlPanel() && !group.isUserPersonalPanel() && userSetupComplete && (hasLayoutUpdatePermission || GroupPermissionUtil.contains(permissionChecker, group, ActionKeys.PREVIEW_IN_DEVICE)) %>">
					<portlet:renderURL var="previewContentURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
						<portlet:param name="mvcPath" value="/preview_panel.jsp" />
					</portlet:renderURL>

					<aui:nav-item anchorId="previewPanel" cssClass="page-preview-controls" data-panelURL="<%= HtmlUtil.escapeAttribute(previewContentURL) %>" href="javascript:;" iconCssClass="icon-desktop" label="preview" />
				</c:if>

				<c:if test="<%= !group.isControlPanel() && !group.isUserPersonalPanel() && userSetupComplete && (themeDisplay.isShowLayoutTemplatesIcon() || themeDisplay.isShowPageSettingsIcon()) %>">

					<%
					PortletURL editPageURL = PortletProviderUtil.getPortletURL(request, Layout.class.getName(), PortletProvider.Action.EDIT);

					editPageURL.setParameter("tabs1", layout.isPrivateLayout() ? "private-pages" : "public-pages");
					editPageURL.setParameter("groupId", String.valueOf(groupDisplayContextHelper.getLiveGroupId()));
					editPageURL.setParameter("selPlid", String.valueOf(layout.getPlid()));
					editPageURL.setParameter("treeId", "layoutsTree");
					editPageURL.setParameter("viewLayout", Boolean.TRUE.toString());

					String editPageURLString = HttpUtil.setParameter(editPageURL.toString(), "controlPanelCategory", "current_site");

					editPageURLString = HttpUtil.setParameter(editPageURLString, "doAsGroupId", String.valueOf(groupDisplayContextHelper.getLiveGroupId()));
					editPageURLString = HttpUtil.setParameter(editPageURLString, "refererPlid", String.valueOf(layout.getPlid()));
					%>

					<aui:nav-item cssClass="page-edit-controls" href="<%= editPageURLString %>" iconCssClass="icon-edit" label="edit" />
				</c:if>

				<c:if test="<%= !group.isControlPanel() && !group.isUserPersonalPanel() && userSetupComplete && (!group.hasStagingGroup() || group.isStagingGroup()) && (hasLayoutUpdatePermission || (layoutTypePortlet.isCustomizable() && layoutTypePortlet.isCustomizedView() && hasLayoutCustomizePermission) || PortletPermissionUtil.hasConfigurationPermission(permissionChecker, themeDisplay.getSiteGroupId(), layout, ActionKeys.CONFIGURATION)) %>">
					<aui:nav-item anchorCssClass="toggle-controls-link" cssClass="toggle-controls" iconCssClass='<%= "controls-state-icon " + (toggleControlsState.equals("visible") ? "icon-eye-open" : "icon-eye-close") %>' id="toggleControls" label="edit-controls" />
				</c:if>
			</aui:nav-item>
		</aui:nav>
	</aui:nav-bar>
</c:if>

<aui:script position="inline" use="liferay-control-menu">
	Liferay.once(
		'dockbarLoaded',
		function() {
			Liferay.ControlMenu._init('<portlet:namespace />');
		}
	);
</aui:script>