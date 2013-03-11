<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

List<Portlet> portlets = new ArrayList<Portlet>();

for (String portletId : PropsValues.DOCKBAR_ADD_PORTLETS) {
	Portlet portlet = PortletLocalServiceUtil.getPortletById(portletId);

	if ((portlet != null) && portlet.isInclude() && portlet.isActive() && PortletPermissionUtil.contains(permissionChecker, layout, portlet, ActionKeys.ADD_TO_PAGE)) {
		portlets.add(portlet);
	}
}

boolean hasLayoutCustomizePermission = LayoutPermissionUtil.contains(permissionChecker, layout, ActionKeys.CUSTOMIZE);
boolean hasLayoutUpdatePermission = LayoutPermissionUtil.contains(permissionChecker, layout, ActionKeys.UPDATE);
%>

<div class="dockbar" data-namespace="<portlet:namespace />" id="dockbar">
	<ul class="aui-toolbar">
		<li class="pin-dockbar">
			<a href="javascript:;"><img alt='<liferay-ui:message key="pin-the-dockbar" />' src="<%= HtmlUtil.escape(themeDisplay.getPathThemeImages()) %>/spacer.png" /></a>
		</li>

		<c:if test="<%= !group.isControlPanel() && (!group.hasStagingGroup() || group.isStagingGroup()) && (GroupPermissionUtil.contains(permissionChecker, group.getGroupId(), ActionKeys.ADD_LAYOUT) || hasLayoutUpdatePermission || (layoutTypePortlet.isCustomizable() && layoutTypePortlet.isCustomizedView() && hasLayoutCustomizePermission)) %>">
			<li class="add-content has-submenu" id="<portlet:namespace />addContent">
				<a class="menu-button" href="javascript:;">
					<span>
						<liferay-ui:message key="add" />
					</span>
				</a>

				<div class="aui-menu add-content-menu aui-overlaycontext-hidden" id="<portlet:namespace />addContentContainer">
					<div class="aui-menu-content">
						<ul>
							<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, scopeGroupId, ActionKeys.ADD_LAYOUT) && !group.isLayoutPrototype() %>">
								<li class="first add-page">
									<a href="javascript:;" id="addPage">
										<liferay-ui:message key="page" />
									</a>
								</li>
							</c:if>

							<c:if test="<%= !themeDisplay.isStateMaximized() && layout.isTypePortlet() && !layout.isLayoutPrototypeLinkActive() %>">
								<li class="last common-items">
									<div class="aui-menugroup">
										<div class="aui-menugroup-content">
											<c:if test="<%= hasLayoutUpdatePermission || (layoutTypePortlet.isCustomizable() && layoutTypePortlet.isCustomizedView() && hasLayoutCustomizePermission) %>">
												<span class="aui-menu-label"><liferay-ui:message key="applications" /></span>

												<ul>

													<%
													int j = 0;

													for (int i = 0; i < portlets.size(); i++) {
														Portlet portlet = portlets.get(i);

														boolean portletInstanceable = portlet.isInstanceable();

														boolean portletUsed = layoutTypePortlet.hasPortletId(portlet.getPortletId());

														boolean portletLocked = !portletInstanceable && portletUsed;

														if (!PortletPermissionUtil.contains(permissionChecker, layout, portlet.getPortletId(), ActionKeys.ADD_TO_PAGE)) {
															continue;
														}
													%>

														<li class="<%= (j == 0) ? "first" : "" %>">
															<a class="app-shortcut <c:if test="<%= portletLocked %>">lfr-portlet-used</c:if> <c:if test="<%= portletInstanceable %>">lfr-instanceable</c:if>" data-portlet-id="<%= portlet.getPortletId() %>" href="javascript:;" <c:if test="<%= portletLocked %>">tabIndex="-1"</c:if>>
																<liferay-portlet:icon-portlet portlet="<%= portlet %>" />

																<%= PortalUtil.getPortletTitle(portlet.getPortletId(), locale) %>
															</a>
														</li>

													<%
														j++;
													}
													%>

													<li class="add-application last more-applications">
														<a href="javascript:;" id="<portlet:namespace />addApplication">
															<liferay-ui:message key="more" />&hellip;
														</a>
													</li>
												</ul>
											</c:if>
										</div>
									</div>
								</li>
							</c:if>
						</ul>
					</div>
				</div>
			</li>
		</c:if>

		<c:if test="<%= !group.isControlPanel() && (themeDisplay.isShowLayoutTemplatesIcon() || themeDisplay.isShowManageSiteMembershipsIcon() || themeDisplay.isShowPageSettingsIcon() || themeDisplay.isShowSiteContentIcon() || themeDisplay.isShowSiteMapSettingsIcon() || themeDisplay.isShowSiteSettingsIcon()) %>">
			<li class="manage-content has-submenu" id="<portlet:namespace />manageContent">
				<a class="menu-button" href="javascript:;">
					<span>
						<liferay-ui:message key="manage" />
					</span>
				</a>

				<div class="aui-menu manage-content-menu aui-overlaycontext-hidden" id="<portlet:namespace />manageContentContainer">
					<div class="aui-menu-content">
						<ul>

							<%
							String useDialogFullDialog = StringPool.BLANK;

							if (PropsValues.DOCKBAR_ADMINISTRATIVE_LINKS_SHOW_IN_POP_UP) {
								useDialogFullDialog = " use-dialog full-dialog";
							}
							%>

							<c:if test="<%= themeDisplay.isShowPageSettingsIcon() %>">
								<li class='<%= "first manage-page" + useDialogFullDialog %>'>
									<aui:a href='<%= themeDisplay.getURLPageSettings().toString() + "#details" %>' label="page" title="manage-page" />
								</li>
							</c:if>

							<c:if test="<%= themeDisplay.isShowLayoutTemplatesIcon() %>">
								<li class='<%= "page-layout" + useDialogFullDialog %>'>
									<aui:a href='<%= themeDisplay.getURLPageSettings().toString() + "#layout" %>' label="page-layout" title="manage-page" />
								</li>
							</c:if>

							<c:if test="<%= themeDisplay.isShowPageCustomizationIcon() && !themeDisplay.isStateMaximized() %>">
								<li class="manage-page-customization">
									<aui:a cssClass='<%= themeDisplay.isFreeformLayout() ? "disabled" : StringPool.BLANK %>' href='<%= themeDisplay.isFreeformLayout() ? null : "javascript:;" %>' id="manageCustomization" label='<%= group.isLayoutPrototype() ? "page-modifications" : "page-customizations" %>' title='<%= themeDisplay.isFreeformLayout() ? "it-is-not-possible-to-specify-customization-settings-for-freeform-layouts" : null %>' />
								</li>
							</c:if>

							<c:if test="<%= themeDisplay.isShowSiteSettingsIcon() %>">
								<li class='<%= "settings" + useDialogFullDialog %>'>
									<aui:a href="<%= themeDisplay.getURLSiteSettings().toString() %>" label="site-settings" title="edit-site-settings" />
								</li>
							</c:if>

							<c:if test="<%= themeDisplay.isShowSiteMapSettingsIcon() %>">
								<li class='<%= "sitemap" + useDialogFullDialog %>'>
									<aui:a href="<%= themeDisplay.getURLSiteMapSettings().toString() %>" label="site-pages" title="manage-site-pages" />
								</li>
							</c:if>

							<c:if test="<%= themeDisplay.isShowManageSiteMembershipsIcon() %>">
								<li class='<%= "manage-site-memberships" + useDialogFullDialog %>'>
									<aui:a href="<%= themeDisplay.getURLManageSiteMemberships().toString() %>" label="site-memberships" title="manage-site-memberships" />
								</li>
							</c:if>

							<c:if test="<%= themeDisplay.isShowSiteContentIcon() %>">
								<li class='<%= "manage-site-content" + useDialogFullDialog %>'>
									<aui:a href="<%= themeDisplay.getURLSiteContent() %>" label="site-content" title="manage-site-content" />
								</li>
							</c:if>
						</ul>
					</div>
				</div>
			</li>

			<c:if test="<%= themeDisplay.isShowPageCustomizationIcon() %>">
				<div class="aui-helper-hidden layout-customizable-controls" id="<portlet:namespace />layout-customizable-controls">
					<span title='<liferay-ui:message key="customizable-help" />'>
						<aui:input helpMessage='<%= group.isLayoutPrototype() ? "modifiable-help" : "customizable-help" %>' id="TypeSettingsProperties--[COLUMN_ID]-customizable--" inputCssClass="layout-customizable-checkbox" label='<%= (group.isLayoutSetPrototype() || group.isLayoutPrototype()) ? "modifiable" : "customizable" %>' name="TypeSettingsProperties--[COLUMN_ID]-customizable--" type="checkbox" useNamespace="<%= false %>" />
					</span>
				</div>
			</c:if>
		</c:if>

		<li class="aui-toolbar-separator">
			<span></span>
		</li>

		<c:if test="<%= !group.isControlPanel() && hasLayoutUpdatePermission || (layoutTypePortlet.isCustomizable() && layoutTypePortlet.isCustomizedView() && hasLayoutCustomizePermission) %>">
			<li class="toggle-controls" id="<portlet:namespace />toggleControls">
				<a href="javascript:;">
					<liferay-ui:message key="edit-controls" />
				</a>
			</li>
		</c:if>

		<c:if test="<%= group.isControlPanel() %>">

			<%
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

			<li class="back-link" id="<portlet:namespace />backLink">
				<a class="portlet-icon-back nobr" href="<%= PortalUtil.escapeRedirect(backURL) %>">
					<%= LanguageUtil.format(pageContext, "back-to-x", HtmlUtil.escape(refererGroupDescriptiveName), false) %>
				</a>
			</li>
		</c:if>
	</ul>

	<ul class="aui-toolbar user-toolbar">
		<c:if test="<%= user.hasMySites() %>">
			<li class="my-sites has-submenu" id="<portlet:namespace />mySites">
				<a class="menu-button" href="javascript:;">
					<span>
						<liferay-ui:message key="go-to" />
					</span>
				</a>

				<div class="aui-menu my-sites-menu aui-overlaycontext-hidden" id="<portlet:namespace />mySitesContainer">
					<div class="aui-menu-content">
						<liferay-ui:my-sites />
					</div>
				</div>
			</li>
		</c:if>

		<li class="aui-toolbar-separator">
			<span></span>
		</li>

		<li class="user-avatar <%= themeDisplay.isImpersonated() ? "impersonating-user has-submenu" : "" %>" id="<portlet:namespace />userAvatar">
			<span class="user-links <%= themeDisplay.isImpersonated() ? "menu-button": "" %>">

				<%
				String useDialog = StringPool.BLANK;

				if (!group.isControlPanel() && PropsValues.DOCKBAR_ADMINISTRATIVE_LINKS_SHOW_IN_POP_UP) {
					useDialog = StringPool.SPACE + "use-dialog";
				}

				String controlPanelCategory = StringPool.BLANK;

				if (!group.isControlPanel()) {
					controlPanelCategory = PortletCategoryKeys.MY;
				}

				String myAccountURL = themeDisplay.getURLMyAccount().toString();

				myAccountURL = HttpUtil.setParameter(myAccountURL, "controlPanelCategory", controlPanelCategory);
				%>

				<liferay-util:buffer var="userName">
					<img alt="<liferay-ui:message key="manage-my-account" />" src="<%= HtmlUtil.escape(user.getPortraitURL(themeDisplay)) %>" />

					<span class="user-full-name">
						<%= HtmlUtil.escape(user.getFullName()) %>
					</span>
				</liferay-util:buffer>

				<c:choose>
					<c:when test="<%= PortalPermissionUtil.contains(permissionChecker, ActionKeys.VIEW_CONTROL_PANEL) %>">
						<aui:a cssClass='<%= "user-portrait" + useDialog %>' href="<%= myAccountURL %>" title="manage-my-account">
							<%= userName %>
						</aui:a>
					</c:when>
					<c:otherwise>
						<span class="user-portrait">
							<%= userName %>
						</span>
					</c:otherwise>
				</c:choose>

				<c:if test="<%= themeDisplay.isShowSignOutIcon() %>">
					<span class="sign-out">(<aui:a href="<%= themeDisplay.getURLSignOut() %>" label="sign-out" />)</span>
				</c:if>
			</span>

			<c:if test="<%= themeDisplay.isImpersonated() %>">
				<div class="aui-menu impersonation-menu aui-overlaycontext-hidden" id="<portlet:namespace />userOptionsContainer">
					<div class="aui-menu-content">
						<div class="notice-message portlet-msg-info">
							<c:choose>
								<c:when test="<%= themeDisplay.isSignedIn() %>">
									<%= LanguageUtil.format(pageContext, "you-are-impersonating-x", new Object[] {HtmlUtil.escape(user.getFullName())}) %>
								</c:when>
								<c:otherwise>
									<liferay-ui:message key="you-are-impersonating-the-guest-user" />
								</c:otherwise>
							</c:choose>
						</div>

						<ul>
							<li>
								<aui:a href="<%= PortalUtil.getLayoutURL(layout, themeDisplay, false) %>"><liferay-ui:message key="be-yourself-again" /> (<%= HtmlUtil.escape(realUser.getFullName()) %>)</aui:a>
							</li>

							<%
							Locale realUserLocale = realUser.getLocale();
							Locale userLocale = user.getLocale();
							%>

							<c:if test="<%= !realUserLocale.equals(userLocale) %>">

								<%
								String doAsUserLanguageId = null;
								String changeLanguageMessage = null;

								if (locale.getLanguage().equals(realUserLocale.getLanguage()) && locale.getCountry().equals(realUserLocale.getCountry())) {
									doAsUserLanguageId = userLocale.getLanguage() + "_" + userLocale.getCountry();
									changeLanguageMessage = LanguageUtil.format(realUserLocale, "use-x's-preferred-language-(x)", new String[] {HtmlUtil.escape(user.getFullName()), userLocale.getDisplayLanguage(realUserLocale)});
								}
								else {
									doAsUserLanguageId = realUserLocale.getLanguage() + "_" + realUserLocale.getCountry();
									changeLanguageMessage = LanguageUtil.format(realUserLocale, "use-your-preferred-language-(x)", realUserLocale.getDisplayLanguage(realUserLocale));
								}
								%>

								<li class="current-user-language">
									<aui:a href='<%= HttpUtil.setParameter(PortalUtil.getCurrentURL(request), "doAsUserLanguageId", doAsUserLanguageId) %>'><%= changeLanguageMessage %></aui:a>
								</li>
							</c:if>
						</ul>
					</div>
				</div>
			</c:if>
		</li>
	</ul>

	<div class="dockbar-messages" id="<portlet:namespace />dockbarMessages">
		<div class="aui-header"></div>

		<div class="aui-body"></div>

		<div class="aui-footer"></div>
	</div>

	<%
	List<LayoutPrototype> layoutPrototypes = LayoutPrototypeServiceUtil.search(company.getCompanyId(), Boolean.TRUE, null);
	%>

	<c:if test="<%= !layoutPrototypes.isEmpty() %>">
		<div class="aui-html-template" id="layoutPrototypeTemplate">
			<ul>

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
</div>

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
	Liferay.Dockbar.init();

	var customizableColumns = A.all('.portlet-column-content.customizable');

	if (customizableColumns.size() > 0) {
		customizableColumns.get('parentNode').addClass('customizable');
	}
</aui:script>