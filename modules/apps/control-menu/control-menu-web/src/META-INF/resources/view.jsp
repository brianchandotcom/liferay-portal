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
List<ControlMenuCategory> controlMenuCategories = (List<ControlMenuCategory>)request.getAttribute(ControlMenuWebKeys.CONTROL_MENU_CATEGORIES);
ControlMenuEntryRegistry controlMenuEntryRegistry = (ControlMenuEntryRegistry)request.getAttribute(ControlMenuWebKeys.CONTROL_MENU_ENTRY_REGISTRY);

Group group = null;
LayoutSet layoutSet = null;

if (layout != null) {
	group = layout.getGroup();
	layoutSet = layout.getLayoutSet();
}

boolean hasLayoutCustomizePermission = LayoutPermissionUtil.contains(permissionChecker, layout, ActionKeys.CUSTOMIZE);
boolean hasLayoutUpdatePermission = LayoutPermissionUtil.contains(permissionChecker, layout, ActionKeys.UPDATE);

String toggleControlsState = GetterUtil.getString(SessionClicks.get(request, "com.liferay.frontend.js.web_toggleControls", "visible"));

boolean userSetupComplete = false;

if (user.isSetupComplete() || themeDisplay.isImpersonated()) {
	userSetupComplete = true;
}
%>

<c:if test="<%= !layout.isTypeControlPanel() && !group.isControlPanel() && userSetupComplete %>">
	<div class="control-menu">
		<c:if test="<%= (user.isSetupComplete() || themeDisplay.isImpersonated()) && themeDisplay.isShowStagingIcon() %>">
			<div class="control-menu-level-2">
				<div class="container-fluid-1280">

					<%
					String renderPortletBoundary = GetterUtil.getString(request.getAttribute(WebKeys.RENDER_PORTLET_BOUNDARY));

					request.setAttribute(WebKeys.RENDER_PORTLET_BOUNDARY, Boolean.FALSE.toString());
					%>

					<liferay-portlet:runtime portletName="<%= PortletKeys.STAGING_BAR %>" />

					<%
					request.setAttribute(WebKeys.RENDER_PORTLET_BOUNDARY, renderPortletBoundary);
					%>

				</div>
			</div>
		</c:if>

		<div class="control-menu-level-1">
			<div class="container-fluid-1280">
				<ul class="control-menu-nav" data-namespace="<portlet:namespace />" id="<portlet:namespace />controlMenu">

					<%
					for (ControlMenuCategory controlMenuCategory : controlMenuCategories) {
						List<ControlMenuEntry> controlMenuEntries = controlMenuEntryRegistry.getControlMenuEntries(controlMenuCategory, request);

						for (ControlMenuEntry controlMenuEntry : controlMenuEntries) {
							if (controlMenuEntry.include(request, new PipingServletResponse(pageContext))) {
								continue;
							}
					%>

							<li>
								<liferay-ui:icon
									iconCssClass='<%= controlMenuEntry.getIconCssClass(request) + " icon-monospaced" %>'
									label="<%= false %>"
									linkCssClass="control-menu-icon"
									message="<%= controlMenuEntry.getLabel(locale) %>"
									url="<%= controlMenuEntry.getURL(request) %>"
								/>
							</li>

					<%
						}
					}
					%>

					<%
					boolean customizableLayout = !(group.isLayoutPrototype() || group.isLayoutSetPrototype() || group.isStagingGroup() || group.isUserGroup()) && layoutTypePortlet.isCustomizable() && LayoutPermissionUtil.containsWithoutViewableGroup(permissionChecker, layout, false, ActionKeys.CUSTOMIZE);
					boolean linkedLayout = (!SitesUtil.isLayoutUpdateable(layout) || (layout.isLayoutPrototypeLinkActive() && !group.hasStagingGroup())) && LayoutPermissionUtil.containsWithoutViewableGroup(themeDisplay.getPermissionChecker(), layout, false, ActionKeys.UPDATE);
					boolean modifiedLayout = (layoutSet != null) && layoutSet.isLayoutSetPrototypeLinkActive() && SitesUtil.isLayoutModifiedSinceLastMerge(layout) && hasLayoutUpdatePermission;
					boolean hasMessages = modifiedLayout || linkedLayout || customizableLayout;
					%>

					<c:if test="<%= (user.isSetupComplete() || themeDisplay.isImpersonated()) && hasMessages %>">
						<li>
							<liferay-ui:icon
								iconCssClass="icon-info icon-monospaced"
								id="infoButton"
								linkCssClass="control-menu-icon"
								url="javascript:;"
							/>

							<c:if test="<%= hasMessages %>">
								<liferay-util:buffer var="infoContainer">
									<%@ include file="/view_page_customization_bar.jspf" %>
								</liferay-util:buffer>

								<aui:script sandbox="<%= true %>">
									$('#<portlet:namespace />infoButton').popover(
										{
											content: '<%= HtmlUtil.escapeJS(infoContainer) %>',
											html: true,
											placement: 'top'
										}
									);
								</aui:script>
							</c:if>
						</li>
					</c:if>

					<c:if test="<%= !group.isControlPanel() && userSetupComplete && (!group.hasStagingGroup() || group.isStagingGroup()) && (hasLayoutUpdatePermission || (layoutTypePortlet.isCustomizable() && layoutTypePortlet.isCustomizedView() && hasLayoutCustomizePermission) || PortletPermissionUtil.hasConfigurationPermission(permissionChecker, themeDisplay.getSiteGroupId(), layout, ActionKeys.CONFIGURATION)) %>">
						<li id="<portlet:namespace />toggleControls">
							<liferay-ui:icon
								cssClass="toggle-controls"
								iconCssClass='<%= "controls-state-icon " + (toggleControlsState.equals("visible") ? "icon-eye-open icon-monospaced" : "icon-eye-close icon-monospaced") %>'
								label="edit-controls"
								linkCssClass="control-menu-icon"
								url="javascript:;"
							/>
						</li>
					</c:if>

					<c:if test="<%= !group.isControlPanel() && userSetupComplete && (hasLayoutUpdatePermission || GroupPermissionUtil.contains(permissionChecker, group, ActionKeys.PREVIEW_IN_DEVICE)) %>">
						<portlet:renderURL var="previewContentURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
							<portlet:param name="mvcPath" value="/preview_panel.jsp" />
						</portlet:renderURL>

						<%
						Map<String, Object> data = new HashMap<String, Object>();

						data.put("panelURL", previewContentURL);
						%>

						<li>
							<liferay-ui:icon
								data="<%= data %>"
								iconCssClass="icon-desktop icon-monospaced"
								id="previewPanel"
								label="preview"
								linkCssClass="control-menu-icon"
								url="javascript:;"
							/>
						</li>
					</c:if>
				</ul>
			</div>
		</div>
	</div>
</c:if>

<aui:script position="inline" use="liferay-control-menu">
	Liferay.ControlMenu.init('#<portlet:namespace />controlMenu');
</aui:script>