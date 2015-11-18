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

<c:choose>
	<c:when test="<%= themeDisplay.isSignedIn() %>">
		<c:if test="<%= layout != null %>">

			<%
			Group group = layout.getGroup();

			boolean hasLayoutAddPermission = false;

			if (layout.getParentLayoutId() == LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) {
				hasLayoutAddPermission = GroupPermissionUtil.contains(permissionChecker, group, ActionKeys.ADD_LAYOUT);
			}
			else {
				hasLayoutAddPermission = LayoutPermissionUtil.contains(permissionChecker, layout, ActionKeys.ADD_LAYOUT);
			}

			boolean hasLayoutCustomizePermission = LayoutPermissionUtil.contains(permissionChecker, layout, ActionKeys.CUSTOMIZE);
			boolean hasLayoutUpdatePermission = LayoutPermissionUtil.contains(permissionChecker, layout, ActionKeys.UPDATE);
			%>

			<c:if test="<%= !group.isControlPanel() && (hasLayoutAddPermission || hasLayoutUpdatePermission || (layoutTypePortlet.isCustomizable() && layoutTypePortlet.isCustomizedView() && hasLayoutCustomizePermission)) %>">
				<div class="add-content-menu" id="<portlet:namespace />addPanelContainer">
					<aui:button cssClass="close" name="closePanelAdd" value="&times;" />

					<%
					String[] tabs1Names = new String[0];

					boolean stateMaximized = ParamUtil.getBoolean(request, "stateMaximized");

					LayoutTypeController layoutTypeController = layoutTypePortlet.getLayoutTypeController();

					boolean hasAddApplicationsPermission = !stateMaximized && layout.isTypePortlet() && !layout.isLayoutPrototypeLinkActive() && !layoutTypeController.isFullPageDisplayable() && (hasLayoutUpdatePermission || (layoutTypePortlet.isCustomizable() && layoutTypePortlet.isCustomizedView() && hasLayoutCustomizePermission));

					boolean hasAddContentPermission = hasAddApplicationsPermission && !group.isLayoutPrototype();

					if (hasAddContentPermission) {
						tabs1Names = ArrayUtil.append(tabs1Names, "content");
					}

					if (hasAddApplicationsPermission) {
						tabs1Names = ArrayUtil.append(tabs1Names, "applications");
					}

					String selectedTab = GetterUtil.getString(SessionClicks.get(request, "com.liferay.control.menu.web_addPanelTab", "content"));
					%>

					<h1><liferay-ui:message key="add" /></h1>

					<liferay-ui:tabs
						names="<%= StringUtil.merge(tabs1Names) %>"
						refresh="<%= false %>"
						type="pills"
						value="<%= selectedTab %>"
					>
						<c:if test="<%= hasAddContentPermission %>">
							<liferay-ui:section>
								<liferay-util:include page="/add_content.jsp" servletContext="<%= application %>" />
							</liferay-ui:section>
						</c:if>

						<c:if test="<%= hasAddApplicationsPermission %>">
							<liferay-ui:section>
								<liferay-util:include page="/add_application.jsp" servletContext="<%= application %>" />
							</liferay-ui:section>
						</c:if>
					</liferay-ui:tabs>

					<span class="added-message hide" id="<portlet:namespace />addedMessage">
						<span class="alert-success message">
							<liferay-ui:icon iconCssClass="icon-ok-sign" /> <span id="<portlet:namespace />portletName"></span> <liferay-ui:message key="added" />

							<a class="content-link" href="javascript:;" id="<portlet:namespace />contentLink"><liferay-ui:message key="skip-to-content" /></a>
						</span>
					</span>
				</div>
			</c:if>
		</c:if>
	</c:when>
	<c:otherwise>
		<liferay-ui:message key="please-sign-in-to-continue" />
	</c:otherwise>
</c:choose>

<aui:script use="liferay-control-menu">
	A.one('#<portlet:namespace />closePanelAdd').on('click', Liferay.ControlMenu.toggleAddPanel, Liferay.ControlMenu);
</aui:script>