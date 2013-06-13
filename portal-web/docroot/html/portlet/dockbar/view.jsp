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

String toggleControlsState = GetterUtil.getString(SessionClicks.get(request, "liferay_toggle_controls", ""));
%>

<aui:nav-bar cssClass="navbar-static-top dockbar" data-namespace="<%= renderResponse.getNamespace() %>" id="dockbar">
	<aui:nav>
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
							refererGroupDescriptiveName = LanguageUtil.get(pageContext, "my-public-pages");
						}
						else {
							refererGroupDescriptiveName = LanguageUtil.get(pageContext, "my-private-pages");
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

			<c:if test="<%= controlPanelCategory.equals(PortletCategoryKeys.CURRENT_SITE) || !controlPanelCategory.equals(PortletCategoryKeys.MY) %>">
				<aui:nav-item anchorCssClass="back-link" href="<%= backURL %>" iconClass="icon-arrow-left" id="backLink" label="<%= StringPool.NBSP %>" title='<%= LanguageUtil.format(pageContext, "back-to-x", HtmlUtil.escape(refererGroupDescriptiveName), false) %>' />
			</c:if>

			<c:if test="<%= !controlPanelCategory.equals(PortletCategoryKeys.MY) && !controlPanelCategory.equals(PortletCategoryKeys.CURRENT_SITE) %>">
				<aui:nav-item anchorId="controlPanelNavHomeLink" href="<%= themeDisplay.getURLControlPanel() %>" iconClass="icon-list" selected="<%= Validator.isNull(controlPanelCategory) %>" />

				<%
				String[] categories = PortletCategoryKeys.ALL;

				for (String curCategory : categories) {
					String urlControlPanelCategory = HttpUtil.setParameter(themeDisplay.getURLControlPanel(), "controlPanelCategory", curCategory);

					String iconClass = StringPool.BLANK;

					if (curCategory.equals(PortletCategoryKeys.APPS)) {
						iconClass = "icon-th";
					}
					else if (curCategory.equals(PortletCategoryKeys.CONFIGURATION)) {
						iconClass = "icon-cog";
					}
					else if (curCategory.equals(PortletCategoryKeys.SITES)) {
						iconClass = "icon-globe";
					}
					else if (curCategory.equals(PortletCategoryKeys.USERS)) {
						iconClass = "icon-user";
					}
				%>

					<c:if test="<%= _hasPortlets(curCategory, themeDisplay) %>">
						<aui:nav-item anchorId='<%= "controlPanelNav" + curCategory + "Link" %>' href="<%= urlControlPanelCategory %>" iconClass="<%= iconClass %>" label='<%= "category." + curCategory %>' selected="<%= controlPanelCategory.equals(curCategory) %>" />
					</c:if>

				<%
				}
				%>

			</c:if>
		</c:if>

		<c:if test="<%= !group.isControlPanel() && (!group.hasStagingGroup() || group.isStagingGroup()) && (GroupPermissionUtil.contains(permissionChecker, group.getGroupId(), ActionKeys.ADD_LAYOUT) || hasLayoutUpdatePermission || (layoutTypePortlet.isCustomizable() && layoutTypePortlet.isCustomizedView() && hasLayoutCustomizePermission)) %>">
			<portlet:renderURL var="addURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
				<portlet:param name="struts_action" value="/dockbar/add_panel" />
				<portlet:param name="viewEntries" value="<%= Boolean.TRUE.toString() %>" />
			</portlet:renderURL>

			<aui:nav-item anchorId="addPanel" href="<%= addURL %>" iconClass="icon-plus" label="add" />
		</c:if>

		<c:if test="<%= !group.isControlPanel() && (themeDisplay.isShowLayoutTemplatesIcon() || themeDisplay.isShowPageSettingsIcon()) %>">
			<aui:nav-item anchorCssClass="manage-content-link" dropdown="<%= true %>" iconClass="icon-edit" id="manageContent" label="edit">

				<%
				String useDialogFullDialog = StringPool.BLANK;

				if (PropsValues.DOCKBAR_ADMINISTRATIVE_LINKS_SHOW_IN_POP_UP) {
					useDialogFullDialog = " use-dialog full-dialog";
				}
				%>

				<c:if test="<%= themeDisplay.isShowPageSettingsIcon() %>">
					<aui:nav-item cssClass='<%= "first manage-page" + useDialogFullDialog %>' href='<%= themeDisplay.getURLPageSettings().toString() + "#tab=details" %>' label="page" title="manage-page" />
				</c:if>

				<c:if test="<%= themeDisplay.isShowLayoutTemplatesIcon() %>">
					<aui:nav-item cssClass='<%= "page-layout" + useDialogFullDialog %>' href='<%= themeDisplay.getURLPageSettings().toString() + "#tab=layout" %>' label="page-layout" title="manage-page" />
				</c:if>

				<c:if test="<%= themeDisplay.isShowPageCustomizationIcon() && !themeDisplay.isStateMaximized() %>">
					<aui:nav-item anchorCssClass='<%= themeDisplay.isFreeformLayout() ? "disabled" : StringPool.BLANK %>' anchorId="manageCustomization" cssClass="manage-page-customization" href='<%= themeDisplay.isFreeformLayout() ? null : "javascript:;" %>' label='<%= group.isLayoutPrototype() ? "page-modifications" : "page-customizations" %>' title='<%= themeDisplay.isFreeformLayout() ? "it-is-not-possible-to-specify-customization-settings-for-freeform-layouts" : null %>' />
				</c:if>
			</aui:nav-item>

			<c:if test="<%= themeDisplay.isShowPageCustomizationIcon() %>">
				<div class="hide layout-customizable-controls" id="<portlet:namespace />layout-customizable-controls">
					<span title='<liferay-ui:message key="customizable-help" />'>
						<aui:input cssClass="layout-customizable-checkbox" helpMessage='<%= group.isLayoutPrototype() ? "modifiable-help" : "customizable-help" %>' id="TypeSettingsProperties--[COLUMN_ID]-customizable--" label='<%= (group.isLayoutSetPrototype() || group.isLayoutPrototype()) ? "modifiable" : "customizable" %>' name="TypeSettingsProperties--[COLUMN_ID]-customizable--" type="checkbox" useNamespace="<%= false %>" />
					</span>
				</div>
			</c:if>

			<aui:nav-item cssClass="divider-vertical"></aui:nav-item>
		</c:if>

		<c:if test="<%= !group.isControlPanel() && (!group.hasStagingGroup() || group.isStagingGroup()) && (hasLayoutUpdatePermission || (layoutTypePortlet.isCustomizable() && layoutTypePortlet.isCustomizedView() && hasLayoutCustomizePermission) || PortletPermissionUtil.hasConfigurationPermission(permissionChecker, themeDisplay.getSiteGroupId(), layout, ActionKeys.CONFIGURATION)) %>">
			<liferay-util:buffer var="editControlsLabel">
				<i class="controls-state-icon <%= toggleControlsState.equals("visible") ? "icon-ok" : "icon-remove" %>"></i>

				<liferay-ui:message key="edit-controls" />
			</liferay-util:buffer>

			<aui:nav-item anchorCssClass="toggle-controls-link" cssClass="toggle-controls" id="toggleControls" label="<%= editControlsLabel %>" />
		</c:if>
	</aui:nav>

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

<c:if test="<%= (layoutSet != null) && layoutSet.isLayoutSetPrototypeLinkActive() && SitesUtil.isLayoutModifiedSinceLastMerge(layout) && LayoutPermissionUtil.contains(themeDisplay.getPermissionChecker(), layout, ActionKeys.UPDATE) %>">
	<div class="page-customization-bar">
		<img alt="" class="customized-icon" src="<%= themeDisplay.getPathThemeImages() %>/common/edit.png" />

		<liferay-ui:message key="this-page-has-been-changed-since-the-last-update-from-the-site-template" />

		<liferay-portlet:actionURL portletName="<%= PortletKeys.LAYOUTS_ADMIN %>" var="resetPrototypeURL">
			<portlet:param name="struts_action" value="/layouts_admin/edit_layouts" />
		</liferay-portlet:actionURL>

		<aui:form action="<%= resetPrototypeURL %>" cssClass="reset-prototype" name="resetFm">
			<input name="<%= Constants.CMD %>" type="hidden" value="reset_prototype" />
			<input name="redirect" type="hidden" value="<%= PortalUtil.getLayoutURL(themeDisplay) %>" />
			<input name="groupId" type="hidden" value="<%= String.valueOf(themeDisplay.getSiteGroupId()) %>" />

			<aui:button name="submit" type="submit" value="reset" />
		</aui:form>
	</div>
</c:if>

<c:if test="<%= (!SitesUtil.isLayoutUpdateable(layout) || (layout.isLayoutPrototypeLinkActive() && !group.hasStagingGroup())) && LayoutPermissionUtil.containsWithoutViewableGroup(themeDisplay.getPermissionChecker(), layout, null, false, ActionKeys.UPDATE) %>">
	<div class="page-customization-bar">
		<img alt="" class="customized-icon" src="<%= themeDisplay.getPathThemeImages() %>/common/site_icon.png" />

		<c:choose>
			<c:when test="<%= layout.isLayoutPrototypeLinkActive() && !group.hasStagingGroup() %>">
				<liferay-ui:message key="this-page-is-linked-to-a-page-template" />
			</c:when>
			<c:when test="<%= layout instanceof VirtualLayout %>">
				<liferay-ui:message key="this-page-belongs-to-a-user-group" />
			</c:when>
			<c:otherwise>
				<liferay-ui:message key="this-page-is-linked-to-a-site-template-which-does-not-allow-modifications-to-it" />
			</c:otherwise>
		</c:choose>
	</div>
</c:if>

<c:if test="<%= !(group.isLayoutPrototype() || group.isLayoutSetPrototype() || group.isUserGroup()) && layoutTypePortlet.isCustomizable() && LayoutPermissionUtil.containsWithoutViewableGroup(permissionChecker, layout, null, false, ActionKeys.CUSTOMIZE) %>">
	<div class="page-customization-bar">
		<img alt="" class="customized-icon" src="<%= themeDisplay.getPathThemeImages() %>/common/guest_icon.png" />

		<c:choose>
			<c:when test="<%= layoutTypePortlet.isCustomizedView() %>">
				<liferay-ui:message key="you-can-customize-this-page" />

				<liferay-ui:icon-help message="customizable-user-help" />
			</c:when>
			<c:otherwise>
				<liferay-ui:message key="this-is-the-default-page-without-your-customizations" />

				<c:if test="<%= hasLayoutUpdatePermission %>">
					<liferay-ui:icon-help message="customizable-admin-help" />
				</c:if>
			</c:otherwise>
		</c:choose>

		<span class="page-customization-actions">

			<%
			String taglibImage = "search";
			String taglibMessage = "view-default-page";

			if (!layoutTypePortlet.isCustomizedView()) {
				taglibMessage = "view-my-customized-page";
			}
			else if (layoutTypePortlet.isDefaultUpdated()) {
				taglibImage = "activate";
				taglibMessage = "the-defaults-for-the-current-page-have-been-updated-click-here-to-see-them";
			}
			%>

			<liferay-ui:icon cssClass='<%= layoutTypePortlet.isCustomizedView() ? StringPool.BLANK : "false" %>' id="toggleCustomizedView" image="<%= taglibImage %>" label="<%= true %>" message="<%= taglibMessage %>" url="javascript:;" />

			<c:if test="<%= layoutTypePortlet.isCustomizedView() %>">
				<liferay-portlet:actionURL portletName="<%= PortletKeys.LAYOUTS_ADMIN %>" var="resetCustomizationViewURL">
					<portlet:param name="struts_action" value="/layouts_admin/edit_layouts" />
					<portlet:param name="groupId" value="<%= String.valueOf(themeDisplay.getSiteGroupId()) %>" />
					<portlet:param name="<%= Constants.CMD %>" value="reset_customized_view" />
				</liferay-portlet:actionURL>

				<%
				String taglibURL = "javascript:if (confirm('" + UnicodeLanguageUtil.get(pageContext, "are-you-sure-you-want-to-reset-your-customizations-to-default") + "')){submitForm(document.hrefFm, '" + HttpUtil.encodeURL(resetCustomizationViewURL) + "');}";
				%>

				<liferay-ui:icon image="../portlet/refresh" label="<%= true %>" message="reset-my-customizations" url="<%= taglibURL %>" />
			</c:if>
		</span>
	</div>

	<aui:script>
		Liferay.provide(
			window,
			'<portlet:namespace />toggleCustomizedView',
			function(event) {
				var A = AUI();

				A.io.request(
					themeDisplay.getPathMain() + '/portal/update_layout',
					{
						data: {
							cmd: 'toggle_customized_view',
							customized_view: '<%= String.valueOf(!layoutTypePortlet.isCustomizedView()) %>',
							p_auth: '<%= AuthTokenUtil.getToken(request) %>'
						},
						on: {
							success: function(event, id, obj) {
								window.location.href = themeDisplay.getLayoutURL();
							}
						}
					}
				);
			},
			['aui-io-request']
		);
	</aui:script>

	<aui:script use="aui-base">
		var toggleCustomizedView = A.one('#<portlet:namespace />toggleCustomizedView');

		if (toggleCustomizedView) {
			toggleCustomizedView.on('click', <portlet:namespace />toggleCustomizedView);
		}
	</aui:script>
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