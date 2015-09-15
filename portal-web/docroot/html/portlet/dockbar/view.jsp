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

<%@ include file="/html/portlet/dockbar/init.jsp" %>

<%
Group group = null;
LayoutSet layoutSet = null;

if (layout != null) {
	group = layout.getGroup();
	layoutSet = layout.getLayoutSet();
}
%>

<aui:nav-bar cssClass="dockbar navbar-static-top" data-namespace="<%= renderResponse.getNamespace() %>" id="dockbar">
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
						refererGroupDescriptiveName = LanguageUtil.get(request, "my-profile");
					}
					else {
						refererGroupDescriptiveName = LanguageUtil.get(request, "my-dashboard");
					}
				}

				backURL = PortalUtil.getLayoutRelativeURL(refererLayout, themeDisplay);

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

		<c:if test="<%= controlPanelCategory.startsWith(PortletCategoryKeys.CURRENT_SITE) || !controlPanelCategory.equals(PortletCategoryKeys.USER_MY_ACCOUNT) %>">
			<div class="navbar-brand">
				<a class="control-panel-back-link" href="<%= backURL %>" title="<liferay-ui:message key="back" />">
					<i class="control-panel-back-icon icon-chevron-sign-left"></i>

					<span class="control-panel-back-text helper-hidden-accessible">
						<liferay-ui:message key="back" />
					</span>
				</a>

				<h1>
					<c:choose>
						<c:when test="<%= controlPanelCategory.startsWith(PortletCategoryKeys.CURRENT_SITE) %>">
							<liferay-ui:control-panel-site-selector />

							<span class="site-administration-title">
								<liferay-ui:message key="site-administration" />
							</span>
						</c:when>
						<c:when test="<%= group.isControlPanel() %>">
							<a href="<%= themeDisplay.getURLControlPanel() %>">
								<liferay-ui:message key="control-panel" />
							</a>
						</c:when>
						<c:otherwise>
							<a href="<%= themeDisplay.getURLControlPanel() %>">
								<liferay-ui:message key="user-personal-panel" />
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
		<aui:nav collapsible="<%= false %>" cssClass="nav-navigation navbar-nav">
			<c:if test="<%= !group.isControlPanel() %>">
				<aui:nav-item anchorCssClass="site-navigation-btn" anchorId="navSiteNavigation" href="javascript:;" iconCssClass="icon-reorder" />
			</c:if>

			<aui:nav-item dropdown="<%= true %>" iconCssClass="icon-cog" toggleTouch="<%= false %>">
				<c:if test="<%= group.isControlPanel() && !controlPanelCategory.equals(PortletCategoryKeys.USER_MY_ACCOUNT) && !controlPanelCategory.startsWith(PortletCategoryKeys.CURRENT_SITE) %>">

					<%
					String[] categories = PortletCategoryKeys.ALL;

					for (String curCategory : categories) {
						String urlControlPanelCategory = HttpUtil.setParameter(themeDisplay.getURLControlPanel(), "controlPanelCategory", curCategory);

						String cssClass = StringPool.BLANK;
						String iconCssClass = StringPool.BLANK;

						if (curCategory.equals(PortletCategoryKeys.CONTROL_PANEL_APPS)) {
							cssClass = "control-panel-apps";
							iconCssClass = "icon-th";
						}
						else if (curCategory.equals(PortletCategoryKeys.CONTROL_PANEL_CONFIGURATION)) {
							cssClass = "control-panel-configuration";
							iconCssClass = "icon-cog";
						}
						else if (curCategory.equals(PortletCategoryKeys.SITES)) {
							cssClass = "control-panel-sites";
							iconCssClass = "icon-globe";
						}
						else if (curCategory.equals(PortletCategoryKeys.CONTROL_PANEL_USERS)) {
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
			</aui:nav-item>
		</aui:nav>
	</c:if>

	<%
	boolean portalMessageUseAnimation = GetterUtil.getBoolean(PortalMessages.get(request, PortalMessages.KEY_ANIMATION), true);
	%>

	<c:if test="<%= user.isSetupComplete() || themeDisplay.isImpersonated() %>">
		<%@ include file="/html/portlet/dockbar/view_user_panel.jspf" %>
	</c:if>
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
		<ul class="list-unstyled">

			<%
			for (LayoutPrototype layoutPrototype : layoutPrototypes) {
			%>

				<li>
					<a href="javascript:;">
						<label>
							<input name="template" type="radio" value="<%= layoutPrototype.getLayoutPrototypeId() %>" /> <%= HtmlUtil.escape(layoutPrototype.getName(locale)) %>
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