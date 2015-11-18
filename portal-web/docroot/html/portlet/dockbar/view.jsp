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
%>

<aui:nav-bar cssClass="navbar-static-top dockbar" data-namespace="<%= renderResponse.getNamespace() %>" id="dockbar">
	<c:if test="<%= group.isControlPanel() %>">

		<%
		String controlPanelCategory = themeDisplay.getControlPanelCategory();

		String refererGroupDescriptiveName = null;
		String backURL = null;

		if (themeDisplay.getRefererPlid() > 0) {
			Layout refererLayout = LayoutLocalServiceUtil.fetchLayout(themeDisplay.getRefererPlid());

			if (refererLayout != null) {
				Group refererGroup = refererLayout.getGroup();

				if (refererGroup.isUserGroup() && (themeDisplay.getRefererGroupId() > 0)) {
					refererGroup = GroupLocalServiceUtil.getGroup(themeDisplay.getRefererGroupId());

					refererLayout = new VirtualLayout(refererLayout, refererGroup);
				}

				refererGroupDescriptiveName = refererGroup.getDescriptiveName(locale);

				if (refererGroup.isUser() && (refererGroup.getClassPK() == user.getUserId())) {
					if (refererLayout.isPublicLayout()) {
						refererGroupDescriptiveName = LanguageUtil.get(pageContext, "my-profile");
					}
					else {
						refererGroupDescriptiveName = LanguageUtil.get(pageContext, "my-dashboard");
					}
				}

				backURL = PortalUtil.getLayoutURL(refererLayout, themeDisplay);

				if (!CookieKeys.hasSessionId(request)) {
					backURL = PortalUtil.getURLWithSessionId(backURL, session.getId());
				}
			}
		}

		if (Validator.isNull(refererGroupDescriptiveName) || Validator.isNull(backURL)) {
			refererGroupDescriptiveName = themeDisplay.getAccount().getName();
			backURL = themeDisplay.getURLHome();
		}

		if (Validator.isNotNull(themeDisplay.getDoAsUserId())) {
			backURL = HttpUtil.addParameter(backURL, "doAsUserId", themeDisplay.getDoAsUserId());
		}

		if (Validator.isNotNull(themeDisplay.getDoAsUserLanguageId())) {
			backURL = HttpUtil.addParameter(backURL, "doAsUserLanguageId", themeDisplay.getDoAsUserLanguageId());
		}
		%>

		<c:if test="<%= controlPanelCategory.startsWith(PortletCategoryKeys.CURRENT_SITE) || !(controlPanelCategory.equals(PortletCategoryKeys.MY) && PropsValues.DOCKBAR_ADMINISTRATIVE_LINKS_SHOW_IN_POP_UP) %>">
			<div class="brand">
				<a class="control-panel-back-link" href="<%= backURL %>" title="<liferay-ui:message key="back" />">
					<i class="control-panel-back-icon icon-chevron-sign-left"></i>

					<span class="control-panel-back-text helper-hidden-accessible">
						<liferay-ui:message key="back" />
					</span>
				</a>

				<h1>
					<c:choose>
						<c:when test="<%= controlPanelCategory.startsWith(PortletCategoryKeys.CURRENT_SITE) %>">
							<%@ include file="/html/portal/layout/view/control_panel_site_selector.jspf" %>

							<span class="divider">/</span>

							<span class="site-administration-title">
								<liferay-ui:message key="site-administration" />
							</span>
						</c:when>
						<c:when test="<%= controlPanelCategory.equals(PortletCategoryKeys.MY) %>">
							<a href="<%= themeDisplay.getURLMyAccount() %>">
								<liferay-ui:message key="my-account" />
							</a>
						</c:when>
						<c:otherwise>
							<a href="<%= themeDisplay.getURLControlPanel() %>">
								<liferay-ui:message key="control-panel" />
							</a>
						</c:otherwise>
					</c:choose>
				</h1>
			</div>
		</c:if>
	</c:if>

	<%
	String controlPanelCategory = themeDisplay.getControlPanelCategory();
	%>

	<c:if test="<%= !(group.isControlPanel() && controlPanelCategory.startsWith(PortletCategoryKeys.CURRENT_SITE)) %>">
		<aui:nav collapsible="<%= true %>" cssClass="nav-navigation" icon="reorder" id="navSiteNavigation">
			<c:if test="<%= group.isControlPanel() && !controlPanelCategory.equals(PortletCategoryKeys.MY) && !controlPanelCategory.startsWith(PortletCategoryKeys.CURRENT_SITE) %>">

				<%
				String[] categories = PortletCategoryKeys.ALL;

				for (String curCategory : categories) {
					String urlControlPanelCategory = HttpUtil.setParameter(themeDisplay.getURLControlPanel(), "controlPanelCategory", curCategory);

					String cssClass = StringPool.BLANK;
					String iconCssClass = StringPool.BLANK;

					if (curCategory.equals(PortletCategoryKeys.APPS)) {
						cssClass = "control-panel-apps";
						iconCssClass = "icon-th";
					}
					else if (curCategory.equals(PortletCategoryKeys.CONFIGURATION)) {
						cssClass = "control-panel-configuration";
						iconCssClass = "icon-cog";
					}
					else if (curCategory.equals(PortletCategoryKeys.SITES)) {
						cssClass = "control-panel-sites";
						iconCssClass = "icon-globe";
					}
					else if (curCategory.equals(PortletCategoryKeys.USERS)) {
						cssClass = "control-panel-users";
						iconCssClass = "icon-user";
					}
				%>

					<c:if test="<%= _hasPortlets(curCategory, themeDisplay) %>">
						<aui:nav-item anchorId='<%= "controlPanelNav" + curCategory + "Link" %>' cssClass="<%= cssClass %>" href="<%= urlControlPanelCategory %>" iconCssClass="<%= iconCssClass %>" label='<%= "category." + curCategory %>' selected="<%= controlPanelCategory.equals(curCategory) %>" />
					</c:if>

				<%
				}
				%>

			</c:if>
		</aui:nav>
	</c:if>

	<%
	boolean userSetupComplete = user.isSetupComplete();

	boolean portalMessageUseAnimation = GetterUtil.getBoolean(PortalMessages.get(request, PortalMessages.KEY_ANIMATION), true);

	boolean hasLayoutAddPermission = false;

	if (layout.getParentLayoutId() == LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) {
		hasLayoutAddPermission = GroupPermissionUtil.contains(permissionChecker, group.getGroupId(), ActionKeys.ADD_LAYOUT);
	}
	else {
		hasLayoutAddPermission = LayoutPermissionUtil.contains(permissionChecker, layout, ActionKeys.ADD_LAYOUT);
	}

	boolean showAddControls = hasLayoutAddPermission || hasLayoutUpdatePermission || (layoutTypePortlet.isCustomizable() && layoutTypePortlet.isCustomizedView() && hasLayoutCustomizePermission);
	boolean showEditControls = themeDisplay.isShowLayoutTemplatesIcon() || themeDisplay.isShowPageSettingsIcon();
	boolean showPreviewControls = hasLayoutUpdatePermission || GroupPermissionUtil.contains(permissionChecker, group.getGroupId(), ActionKeys.PREVIEW_IN_DEVICE);
	boolean showToggleControls = (!group.hasStagingGroup() || group.isStagingGroup()) && (hasLayoutUpdatePermission || (layoutTypePortlet.isCustomizable() && layoutTypePortlet.isCustomizedView() && hasLayoutCustomizePermission) || PortletPermissionUtil.hasConfigurationPermission(permissionChecker, themeDisplay.getSiteGroupId(), layout, ActionKeys.CONFIGURATION));
	%>

	<c:if test="<%= !group.isControlPanel() && userSetupComplete && (showAddControls || showPreviewControls || showEditControls || showToggleControls) %>">
		<aui:nav ariaLabel='<%= LanguageUtil.get(pageContext, "layout-controls") %>' collapsible="<%= true %>" cssClass='<%= portalMessageUseAnimation ? "nav-add-controls" : "nav-add-controls nav-add-controls-notice" %>' icon="pencil" id="navAddControls">
			<c:if test="<%= showAddControls %>">
				<portlet:renderURL var="addURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
					<portlet:param name="struts_action" value="/dockbar/add_panel" />
					<portlet:param name="stateMaximized" value="<%= String.valueOf(themeDisplay.isStateMaximized()) %>" />
					<portlet:param name="viewEntries" value="<%= Boolean.TRUE.toString() %>" />
				</portlet:renderURL>

				<aui:nav-item anchorId="addPanel" cssClass="site-add-controls" data-panelURL="<%= addURL %>" href="javascript:;" iconCssClass="icon-plus" label="add" />
			</c:if>

			<c:if test="<%= showPreviewControls %>">
				<portlet:renderURL var="previewContentURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
					<portlet:param name="struts_action" value="/dockbar/preview_panel" />
				</portlet:renderURL>

				<aui:nav-item anchorId="previewPanel" cssClass="page-preview-controls" data-panelURL="<%= previewContentURL %>" href="javascript:;" iconCssClass="icon-desktop" label="preview" />
			</c:if>

			<c:if test="<%= showEditControls %>">
				<portlet:renderURL var="editLayoutURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
					<portlet:param name="struts_action" value="/dockbar/edit_layout_panel" />
					<portlet:param name="closeRedirect" value="<%= PortalUtil.getLayoutURL(layout, themeDisplay) %>" />
					<portlet:param name="selPlid" value="<%= String.valueOf(plid) %>" />
				</portlet:renderURL>

				<aui:nav-item anchorId="editLayoutPanel" cssClass="page-edit-controls" data-panelURL="<%= editLayoutURL %>" href="javascript:;" iconCssClass="icon-edit" label="edit" />
			</c:if>

			<c:if test="<%= showToggleControls %>">
				<aui:nav-item anchorCssClass="toggle-controls-link" cssClass="toggle-controls" iconCssClass='<%= "controls-state-icon " + (toggleControlsState.equals("visible") ? "icon-eye-open" : "icon-eye-close") %>' id="toggleControls" label="edit-controls" />
			</c:if>
		</aui:nav>
	</c:if>

	<%@ include file="/html/portlet/dockbar/view_user_panel.jspf" %>
</aui:nav-bar>

<div class="dockbar-messages" id="<portlet:namespace />dockbarMessages">
	<div class="header"></div>

	<div class="body"></div>

	<div class="footer"></div>
</div>

<%
List<LayoutPrototype> layoutPrototypes = LayoutPrototypeServiceUtil.search(company.getCompanyId(), Boolean.TRUE, null);
%>

<c:if test="<%= !layoutPrototypes.isEmpty() %>">
	<div class="html-template" id="layoutPrototypeTemplate">
		<ul class="unstyled">

			<%
			for (LayoutPrototype layoutPrototype : layoutPrototypes) {
			%>

				<li>
					<a href="javascript:;">
						<label>
							<input name="template" type="radio" value="<%= layoutPrototype.getLayoutPrototypeId() %>" /> <%= HtmlUtil.escape(layoutPrototype.getName(user.getLanguageId())) %>
						</label>
					</a>
				</li>

			<%
			}
			%>

		</ul>
	</div>
</c:if>

<aui:script position="inline" use="liferay-dockbar">
	Liferay.Dockbar.init('#<portlet:namespace />dockbar');

	var customizableColumns = A.all('.portlet-column-content.customizable');

	if (customizableColumns.size() > 0) {
		customizableColumns.get('parentNode').addClass('customizable');
	}
</aui:script>

<%!
private boolean _hasPortlets(String category, ThemeDisplay themeDisplay) throws SystemException {
	List<Portlet> portlets = PortalUtil.getControlPanelPortlets(category, themeDisplay);

	if (portlets.isEmpty()) {
		return false;
	}

	return true;
}
%>