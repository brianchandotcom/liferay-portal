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

<%@ include file="/html/portlet/dockbar/init.jsp" %>

<c:choose>
	<c:when test="<%= themeDisplay.isSignedIn() %>">

		<%
		Group group = null;

		if (layout != null) {
			group = layout.getGroup();
		}

		boolean hasLayoutCustomizePermission = LayoutPermissionUtil.contains(permissionChecker, layout, ActionKeys.CUSTOMIZE);
		boolean hasLayoutUpdatePermission = LayoutPermissionUtil.contains(permissionChecker, layout, ActionKeys.UPDATE);
		%>

		<c:if test="<%= (layout != null) && (layout.isTypePortlet() || layout.isTypePanel()) && !layout.isLayoutPrototypeLinkActive() && !group.isControlPanel() && (!group.hasStagingGroup() || group.isStagingGroup()) && (GroupPermissionUtil.contains(permissionChecker, group.getGroupId(), ActionKeys.ADD_LAYOUT) || hasLayoutUpdatePermission || (layoutTypePortlet.isCustomizable() && layoutTypePortlet.isCustomizedView() && hasLayoutCustomizePermission)) %>">
			<div class="add-content-menu" id="<portlet:namespace />addPanelContainer">
				<aui:button cssClass="close pull-right" name="closePanelAdd" value="&times;" />

				<%
				String[] tabs1Names = new String[0];

				boolean stateMaximized = ParamUtil.getBoolean(request, "stateMaximized");

				boolean hasAddContentPermission = !stateMaximized && (GroupPermissionUtil.contains(permissionChecker, scopeGroupId, ActionKeys.ADD_LAYOUT) && !group.isLayoutPrototype() && !layout.isTypePanel());

				if (hasAddContentPermission) {
					tabs1Names = ArrayUtil.append(tabs1Names, "content");
				}

				boolean hasAddApplicationsPermission = !stateMaximized && layout.isTypePortlet() && !layout.isLayoutPrototypeLinkActive();

				if (hasAddApplicationsPermission) {
					tabs1Names = ArrayUtil.append(tabs1Names, "applications");
				}

				boolean hasAddPagePermission = !layout.isLayoutPrototypeLinkActive();

				if (hasAddPagePermission) {
					tabs1Names = ArrayUtil.append(tabs1Names, "page");
				}

				String selectedTab = GetterUtil.getString(SessionClicks.get(request, "liferay_addpanel_tab", "content"));

				if (stateMaximized) {
					selectedTab = "page";
				}
				%>

				<liferay-ui:tabs
					names="<%= StringUtil.merge(tabs1Names) %>"
					refresh="<%= false %>"
					value="<%= selectedTab %>"
				>
					<c:if test="<%= hasAddContentPermission %>">
						<liferay-ui:section>
							<liferay-util:include page="/html/portlet/dockbar/add_content.jsp" />
						</liferay-ui:section>
					</c:if>

					<c:if test="<%= hasAddApplicationsPermission %>">
						<liferay-ui:section>
							<liferay-util:include page="/html/portlet/dockbar/add_application.jsp" />
						</liferay-ui:section>
					</c:if>

					<c:if test="<%= hasAddPagePermission %>">
						<liferay-ui:section>
							<liferay-util:include page="/html/portlet/layouts_admin/add_layout.jsp" />
						</liferay-ui:section>
					</c:if>
				</liferay-ui:tabs>
			</div>
		</c:if>
	</c:when>
	<c:otherwise>
		<liferay-ui:message key="please-sign-in-to-continue" />
	</c:otherwise>
</c:choose>

<aui:script use="liferay-dockbar">
	A.one('#<portlet:namespace />closePanelAdd').on('click', Liferay.Dockbar.toggleAddPanel, Liferay.Dockbar);
</aui:script>