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

<%@ include file="/html/portlet/control_panel_home/init.jsp" %>

<div class="control-panel-home-actions">

	<%
	Map<String, List<Portlet>> categoriesMap = PortalUtil.getControlPanelCategoriesMap(request);

	for (String category : categoriesMap.keySet()) {
	%>

		<aui:col width="<%= 25 %>">
			<div class="control-panel-home-actions-category">
				<c:choose>
					<c:when test="<%= category.equals(PortletCategoryKeys.APPS) %>">
						<c:choose>
							<c:when test="<%= PortletLocalServiceUtil.hasPortlet(themeDisplay.getCompanyId(), PortletKeys.MARKETPLACE_APP_MANAGER) %>">
								<liferay-portlet:renderURL portletName="<%= PortletKeys.MARKETPLACE_APP_MANAGER %>" var="appManagerURL">
									<portlet:param name="redirect" value="<%= PortalUtil.getCurrentURL(request) %>" />
								</liferay-portlet:renderURL>

								<p>
									<liferay-ui:message key="do-you-want-to-manage-the-installed-apps" />
								</p>

								<aui:button href="<%= appManagerURL %>" id="controlPanelHomeActionManageApps" primary="<%= true %>" value="manage-apps" />
							</c:when>
							<c:otherwise>
								<liferay-portlet:renderURL portletName="<%= PortletKeys.PLUGINS_ADMIN %>" var="pluginsAdminURL">
									<portlet:param name="redirect" value="<%= PortalUtil.getCurrentURL(request) %>" />
								</liferay-portlet:renderURL>

								<p>
									<liferay-ui:message key="do-you-want-to-manage-the-installed-apps" />
								</p>

								<aui:button href="<%= pluginsAdminURL %>" id="controlPanelHomeActionManageApps" primary="<%= true %>" value="manage-apps" />
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:when test="<%= category.equals(PortletCategoryKeys.CONFIGURATION) %>">
						<liferay-portlet:renderURL portletName="<%= PortletKeys.PORTAL_SETTINGS %>" var="editPortalSettingsURL">
							<portlet:param name="redirect" value="<%= PortalUtil.getCurrentURL(request) %>" />
						</liferay-portlet:renderURL>

						<p>
							<liferay-ui:message key="do-you-want-to-modify-any-settings-of-your-portal" />
						</p>

						<aui:button href="<%= editPortalSettingsURL %>" id="controlPanelHomeActionPortalSettings" primary="<%= true %>" value="edit-portal-settings" />
					</c:when>
					<c:when test="<%= category.equals(PortletCategoryKeys.SITES) %>">

						<%
						if (themeDisplay.getRefererPlid() > 0) {
							Layout refererLayout = LayoutLocalServiceUtil.fetchLayout(themeDisplay.getRefererPlid());

							if (refererLayout != null) {
								Group refererGroup = refererLayout.getGroup();

								ThemeDisplay siteThemeDisplay = (ThemeDisplay)themeDisplay.clone();

								siteThemeDisplay.setScopeGroupId(refererGroup.getGroupId());

								String siteName = HtmlUtil.escape(StringUtil.shorten(refererGroup.getDescriptiveName(locale), 35));

								String buttonLabel = LanguageUtil.format(pageContext, "manage-x", siteName, false);

								PortletURL siteAdministrationURL = PortalUtil.getSiteAdministrationURL(renderResponse, siteThemeDisplay);
						%>

								<c:if test="<%= siteAdministrationURL != null %>">
									<p>
										<liferay-ui:message key="you-can-manage-the-site-you-are-coming-from" />
									</p>

									<aui:button href="<%= siteAdministrationURL.toString() %>" id="controlPanelHomeActionManageSite" primary="<%= true %>" value="<%= buttonLabel %>" />
								</c:if>

						<%
							}
						}
						%>

					</c:when>
					<c:when test="<%= category.equals(PortletCategoryKeys.USERS) %>">
						<c:if test="<%= PortalPermissionUtil.contains(permissionChecker, ActionKeys.ADD_USER) %>">
							<liferay-portlet:renderURL portletName="<%= PortletKeys.USERS_ADMIN %>" var="addUserURL">
								<portlet:param name="struts_action" value="/users_admin/edit_user" />
								<portlet:param name="redirect" value="<%= PortalUtil.getCurrentURL(request) %>" />
							</liferay-portlet:renderURL>

							<p>
								<liferay-ui:message key="do-you-want-to-create-a-user" />
							</p>

							<aui:button href="<%= addUserURL %>" id="controlPanelHomeActionAddUser" primary="<%= true %>" value="add-user" />
						</c:if>
					</c:when>
				</c:choose>
			</div>
		</aui:col>

	<%
	}
	%>

</div>