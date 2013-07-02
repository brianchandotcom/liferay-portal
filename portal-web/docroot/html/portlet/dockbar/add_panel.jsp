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

		<c:if test="<%= !themeDisplay.isStateMaximized() && (layout != null) && (layout.isTypePortlet() || layout.isTypePanel()) && !layout.isLayoutPrototypeLinkActive() && !group.isControlPanel() && (!group.hasStagingGroup() || group.isStagingGroup()) && (GroupPermissionUtil.contains(permissionChecker, group.getGroupId(), ActionKeys.ADD_LAYOUT) || hasLayoutUpdatePermission || (layoutTypePortlet.isCustomizable() && layoutTypePortlet.isCustomizedView() && hasLayoutCustomizePermission)) %>">
			<div class="add-content-menu" id="<portlet:namespace />addPanelContainer">
				<button class="close pull-right" id="closePanel" type="button">&#x00D7;</button>

				<%
				String[] tabs1Names = new String[0];

				boolean hasAddContentPermission = (GroupPermissionUtil.contains(permissionChecker, scopeGroupId, ActionKeys.ADD_LAYOUT) && !group.isLayoutPrototype());

				if (hasAddContentPermission) {
					tabs1Names = ArrayUtil.append(tabs1Names, "content");
				}

				boolean hasAddApplicationsAndPagePermission = (!themeDisplay.isStateMaximized() && layout.isTypePortlet() && !layout.isLayoutPrototypeLinkActive());

				if (hasAddApplicationsAndPagePermission) {
					tabs1Names = ArrayUtil.append(tabs1Names, "applications,page");
				}

				String selectedTab = GetterUtil.getString(SessionClicks.get(request, "liferay_addpanel_tab", "content"));
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

					<c:if test="<%= hasAddApplicationsAndPagePermission %>">
						<liferay-ui:section>
							<liferay-util:include page="/html/portlet/dockbar/add_application.jsp" />
						</liferay-ui:section>

						<liferay-ui:section>
							<liferay-util:include page="/html/portlet/dockbar/add_page.jsp" />
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
	A.one('#closePanel').on('click', Liferay.Dockbar.loadPanel, Liferay.Dockbar);
</aui:script>