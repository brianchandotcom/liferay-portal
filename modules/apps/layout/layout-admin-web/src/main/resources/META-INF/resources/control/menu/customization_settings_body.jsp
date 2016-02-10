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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.layout.admin.web.control.menu.CustomizationSettingsControlMenuEntry" %>
<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %>
<%@ page import="com.liferay.portal.kernel.language.UnicodeLanguageUtil" %>
<%@ page import="com.liferay.portal.kernel.security.auth.AuthTokenUtil" %>
<%@ page import="com.liferay.portal.kernel.util.GetterUtil" %>
<%@ page import="com.liferay.portal.kernel.util.HttpUtil" %>

<%@ page import="javax.portlet.PortletURL" %>

<%@ page
		import="com.liferay.layout.admin.web.constants.LayoutAdminPortletKeys" %>
<%@ page import="javax.portlet.PortletRequest" %>
<%@ page import="com.liferay.portal.kernel.portlet.PortletURLFactoryUtil" %>
<%@ page import="javax.portlet.ActionRequest" %>
<%@ page import="com.liferay.portal.kernel.util.PortalUtil" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
String portletNamespace = PortalUtil.getPortletNamespace(LayoutAdminPortletKeys.LAYOUT_ADMIN);

boolean hasUpdateLayoutPermission = GetterUtil.getBoolean(request.getAttribute(CustomizationSettingsControlMenuEntry.CUSTOMIZATION_SETTINGS_LAYOUT_UPDATE_PERMISSION));
%>

<div class="hidden-xs">
	<aui:icon image="information-live" markupView="lexicon" />

	<c:if test="<%= layoutTypePortlet.isCustomizable() %>">
		<div class="customizable-layout">
			<span class="message-info">
				<c:choose>
					<c:when test="<%= layoutTypePortlet.isCustomizedView() %>">
						<liferay-ui:message key="you-can-customize-this-page" />

						<liferay-ui:icon-help message="customizable-user-help" />
					</c:when>
					<c:otherwise>
						<liferay-ui:message key="this-is-the-default-page-without-your-customizations" />

						<c:if test="<%= hasUpdateLayoutPermission %>">
							<liferay-ui:icon-help message="customizable-admin-help" />
						</c:if>
					</c:otherwise>
				</c:choose>
			</span>

			<span class="button-info">

				<%
				String taglibMessage = "view-default-page";

				if (!layoutTypePortlet.isCustomizedView()) {
					taglibMessage = "view-my-customized-page";
				}
				else if (layoutTypePortlet.isDefaultUpdated()) {
					taglibMessage = "the-defaults-for-the-current-page-have-been-updated-click-here-to-see-them";
				}
				%>

				<liferay-ui:icon
					cssClass="view-default"
					id='<%= portletNamespace + "toggleCustomizedView" %>'
					label="<%= true %>"
					message="<%= LanguageUtil.get(resourceBundle, taglibMessage) %>"
					url="javascript:;"
				/>

				<c:if test="<%= layoutTypePortlet.isCustomizedView() %>">

					<%
					PortletURL resetCustomizationViewURL = PortletURLFactoryUtil.create(request, LayoutAdminPortletKeys.LAYOUT_ADMIN, plid, PortletRequest.ACTION_PHASE);

					resetCustomizationViewURL.setParameter(ActionRequest.ACTION_NAME, "resetCustomizationView");
					resetCustomizationViewURL.setParameter("groupId", String.valueOf(themeDisplay.getSiteGroupId()));

					String taglibURL = "javascript:if (confirm('" + UnicodeLanguageUtil.get(resourceBundle, "are-you-sure-you-want-to-reset-your-customizations-to-default") + "')){submitForm(document.hrefFm, '" + HttpUtil.encodeURL(resetCustomizationViewURL.toString()) + "');}";
					%>

					<liferay-ui:icon cssClass="reset-my-customizations" label="<%= true %>" message='<%= LanguageUtil.get(resourceBundle, "reset-my-customizations") %>' url="<%= taglibURL %>" />
				</c:if>
			</span>
		</div>

		<aui:script position="inline" sandbox="<%= true %>">
			$('#<%= portletNamespace %>toggleCustomizedView').on(
				'click',
				function(event) {
					$.ajax(
						themeDisplay.getPathMain() + '/portal/update_layout',
						{
							data: {
								cmd: 'toggle_customized_view',
								customized_view: '<%= String.valueOf(!layoutTypePortlet.isCustomizedView()) %>',
								p_auth: '<%= AuthTokenUtil.getToken(request) %>'
							},
							success: function() {
								window.location.href = themeDisplay.getLayoutURL();
							}
						}
					);
				}
			);
		</aui:script>
	</c:if>

	<c:if test="<%= hasUpdateLayoutPermission %>">
		<aui:button name="manageCustomization" value="show-customizable-sections" />

		<div class="hide layout-customizable-controls-container" id="<portlet:namespace />layoutCustomizableControls">
			<div class="layout-customizable-controls">
				<span title="<liferay-ui:message key="customizable-help" />">
					<aui:input cssClass="layout-customizable-checkbox" helpMessage="customizable-help" id="TypeSettingsProperties--[COLUMN_ID]-customizable--" label="" labelOff="no-customizable" labelOn="customizable" name="TypeSettingsProperties--[COLUMN_ID]-customizable--" type="toggle-switch" useNamespace="<%= false %>" />
				</span>
			</div>
		</div>

		<aui:script use="liferay-layout-customization-settings">
			new Liferay.LayoutCustomizationSettings(
				{
					namespace: '<portlet:namespace />'
				}
			);
		</aui:script>
	</c:if>
</div>