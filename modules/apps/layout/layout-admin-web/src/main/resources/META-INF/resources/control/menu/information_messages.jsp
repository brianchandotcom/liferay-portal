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

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.layout.admin.web.constants.LayoutAdminPortletKeys" %>
<%@ page import="com.liferay.layout.admin.web.constants.LayoutAdminWebKeys" %>
<%@ page import="com.liferay.layout.admin.web.control.menu.InformationMessagesProductNavigationControlMenuEntry" %>
<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %>
<%@ page import="com.liferay.portal.kernel.language.UnicodeLanguageUtil" %>
<%@ page import="com.liferay.portal.kernel.model.Group" %>
<%@ page import="com.liferay.portal.kernel.portlet.PortletURLFactoryUtil" %>
<%@ page import="com.liferay.portal.kernel.security.auth.AuthTokenUtil" %>
<%@ page import="com.liferay.portal.kernel.util.HtmlUtil" %>
<%@ page import="com.liferay.portal.kernel.util.HttpUtil" %>
<%@ page import="com.liferay.portal.kernel.util.PortalUtil" %>
<%@ page import="com.liferay.sites.kernel.util.SitesUtil" %>

<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>

<%@ page import="javax.portlet.ActionRequest" %>
<%@ page import="javax.portlet.PortletRequest" %>
<%@ page import="javax.portlet.PortletURL" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<%
String namespace = PortalUtil.getPortletNamespace(LayoutAdminPortletKeys.LAYOUT_ADMIN);

InformationMessagesProductNavigationControlMenuEntry informationMessagesProductNavigationControlMenuEntry = (InformationMessagesProductNavigationControlMenuEntry)request.getAttribute(LayoutAdminWebKeys.CONTROL_MENU_ENTRY);

Map<String, Object> data = new HashMap<>();

data.put("qa-id", "info");
%>

<liferay-ui:icon
	data="<%= data %>"
	icon="information-live"
	id='<%= namespace + "infoButton" %>'
	label="<%= false %>"
	linkCssClass="control-menu-icon"
	markupView="lexicon"
	message="additional-information"
	url="javascript:;"
/>

<div class="hide">
	<div id="<%= namespace %>infoContainer">
		<c:if test="<%= informationMessagesProductNavigationControlMenuEntry.isModifiedLayout(themeDisplay) %>">
			<div class="modified-layout">
				<aui:icon image="information-live" markupView="lexicon" />

				<span class="message-info">
					<liferay-ui:message key="this-page-has-been-changed-since-the-last-update-from-the-site-template-excerpt" />

					<liferay-ui:icon-help message="this-page-has-been-changed-since-the-last-update-from-the-site-template" />
				</span>

				<%
				PortletURL resetPrototypeURL = PortletURLFactoryUtil.create(request, LayoutAdminPortletKeys.LAYOUT_ADMIN, plid, PortletRequest.ACTION_PHASE);

				resetPrototypeURL.setParameter(ActionRequest.ACTION_NAME, "resetPrototype");
				resetPrototypeURL.setParameter("redirect", PortalUtil.getLayoutURL(themeDisplay));
				resetPrototypeURL.setParameter("groupId", String.valueOf(themeDisplay.getSiteGroupId()));

				String taglibURL = "submitForm(document.hrefFm, '" + HtmlUtil.escapeJS(resetPrototypeURL.toString()) + "');";
				%>

				<span class="button-info">
					<aui:button cssClass="btn-link" name="submit" onClick="<%= taglibURL %>" type="submit" value="reset-changes" />
				</span>
			</div>
		</c:if>

		<c:if test="<%= informationMessagesProductNavigationControlMenuEntry.isLinkedLayout(themeDisplay) %>">
			<div class="linked-layout">
				<aui:icon image="information-live" markupView="lexicon" />

				<span class="message-info">

					<%
					Group group = themeDisplay.getScopeGroup();
					%>

					<c:choose>
						<c:when test="<%= layout.isLayoutPrototypeLinkActive() && !group.hasStagingGroup() %>">
							<liferay-ui:message key="this-page-is-linked-to-a-page-template" />
						</c:when>
						<c:when test="<%= SitesUtil.isUserGroupLayout(layout) %>">
							<liferay-ui:message key="this-page-belongs-to-a-user-group" />
						</c:when>
						<c:otherwise>
							<liferay-ui:message key="this-page-is-linked-to-a-site-template-which-does-not-allow-modifications-to-it" />
						</c:otherwise>
					</c:choose>
				</span>
			</div>
		</c:if>

		<c:if test="<%= informationMessagesProductNavigationControlMenuEntry.isCustomizableLayout(themeDisplay) %>">
			<div class="customizable-layout">
				<aui:icon image="information-live" markupView="lexicon" />

				<span class="message-info">
					<c:choose>
						<c:when test="<%= layoutTypePortlet.isCustomizedView() %>">
							<liferay-ui:message key="you-can-customize-this-page" />

							<liferay-ui:icon-help message="customizable-user-help" />
						</c:when>
						<c:otherwise>
							<liferay-ui:message key="this-is-the-default-page-without-your-customizations" />

							<c:if test="<%= informationMessagesProductNavigationControlMenuEntry.hasUpdateLayoutPermission(themeDisplay) %>">
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
						id='<%= namespace + "toggleCustomizedView" %>'
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
				$('#<%= namespace %>toggleCustomizedView').on(
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

			<c:if test="<%= informationMessagesControlMenuEntry.hasUpdateLayoutPermission(themeDisplay) %>">
				<aui:button name="manageCustomization" value="show-customizable-sections" />

				<div class="hide layout-customizable-controls" id="<portlet:namespace />layoutCustomizableControls">
					<span title="<liferay-ui:message key="customizable-help" />">
						<aui:input cssClass="layout-customizable-checkbox" helpMessage="customizable-help" id="TypeSettingsProperties--[COLUMN_ID]-customizable--" label="customizable" name="TypeSettingsProperties--[COLUMN_ID]-customizable--" type="checkbox" useNamespace="<%= false %>" />
					</span>
				</div>

				<aui:script use="liferay-layout-customization-settings">
					new Liferay.LayoutCustomizationSettings(
						{
							namespace: '<portlet:namespace />'
						}
					);
				</aui:script>
			</c:if>
		</c:if>
	</div>
</div>

<aui:script position="auto" use="aui-popover,event-outside">
	var trigger = A.one('#<%= namespace %>infoButton');

	var popOver = new A.Popover(
		{
			align: {
					node: trigger,
					points:[A.WidgetPositionAlign.TC, A.WidgetPositionAlign.BC]
				},
			bodyContent: A.one('#<%= namespace %>infoContainer'),
			constrain: true,
			hideOn: [
				{
					node: A.one('document'),
					eventName: 'key',
					keyCode: 'esc'
				},
				{
					node: A.one('document'),
					eventName: 'clickoutside'
				}
			],
			position: 'bottom',
			trigger: trigger,
			visible: false,
			width: 300,
			zIndex: Liferay.zIndex.TOOLTIP
		}
	).render();
</aui:script>