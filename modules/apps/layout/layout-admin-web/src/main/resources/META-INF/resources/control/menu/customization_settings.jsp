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

<%@ page import="com.liferay.layout.admin.web.control.menu.CustomizationSettingsProductNavigationControlMenuEntry" %>
<%@ page import="com.liferay.portal.kernel.language.UnicodeLanguageUtil" %>
<%@ page import="com.liferay.portal.kernel.security.auth.AuthTokenUtil" %>
<%@ page import="com.liferay.portal.kernel.util.GetterUtil" %>
<%@ page import="com.liferay.portal.kernel.util.HttpUtil" %>
<%@ page import="com.liferay.portal.kernel.util.StringPool" %>

<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
boolean hasUpdateLayoutPermission = GetterUtil.getBoolean(request.getAttribute(CustomizationSettingsProductNavigationControlMenuEntry.CUSTOMIZATION_SETTINGS_LAYOUT_UPDATE_PERMISSION));
Map<String, Object> data = new HashMap<>();

data.put("qa-id", "customizations");
%>

<li class="active control-menu-link control-menu-nav-item customization-link">
	<liferay-ui:icon
		data="<%= data %>"
		icon="pencil"
		id="customizationButton"
		label="<%= false %>"
		linkCssClass="control-menu-icon"
		markupView="lexicon"
		message="this-page-can-be-customized"
		url="javascript:;"
	/>
</li>

<li class="control-menu-nav-item" id="customizationBar">
	<div class="control-menu-level-2">
		<div class="container-fluid-1280">
			<div class="control-menu-level-2-heading visible-xs">
				<liferay-ui:message key="customization-options" />

				<button aria-labelledby="Close" class="close" id="closeCustomizationOptions" type="button">
					<aui:icon image="times" markupView="lexicon" />
				</button>
			</div>
			<ul class="control-menu-level-2-nav control-menu-nav">
				<c:if test="<%= hasUpdateLayoutPermission %>">
					<li class="control-menu-nav-item">
						<liferay-ui:message key="you-can-customize-this-page" />
						<liferay-ui:message key="customizable-user-help" />
					</li>

					<c:if test="<%= hasUpdateLayoutPermission %>">
						<li class="control-menu-nav-item">
							<aui:input
								id="manageCustomization"
								inlineField="<%= true %>"
								label="<%= StringPool.BLANK %>"
								labelOff="hide-customizable-zones"
								labelOn="view-customizable-zones"
								name="manageCustomization"
								type="toggle-switch"
								useNamespace="<%= false %>"
								wrappedField="<%= true %>"
							/>

							<div class="hide layout-customizable-controls-container" id="<portlet:namespace />layoutCustomizableControls">
								<div class="layout-customizable-controls">
									<span title="<liferay-ui:message key="customizable-help" />">
										<aui:input cssClass="layout-customizable-checkbox" helpMessage="customizable-help" id="TypeSettingsProperties--[COLUMN_ID]-customizable--" label="" labelOff="no-customizable" labelOn="customizable" name="TypeSettingsProperties--[COLUMN_ID]-customizable--" type="toggle-switch" useNamespace="<%= false %>" />
									</span>
								</div>
							</div>
						</li>

						<aui:script use="liferay-layout-customization-settings">
							new Liferay.LayoutCustomizationSettings(
								{
									namespace: '<portlet:namespace />'
								}
							);
						</aui:script>
					</c:if>

					<portlet:actionURL name="resetCustomizationView" var="resetCustomizationViewURL">
						<portlet:param name="groupId" value="<%= String.valueOf(themeDisplay.getSiteGroupId()) %>" />
					</portlet:actionURL>

					<%
					String taglibURL = "javascript:if (confirm('" + UnicodeLanguageUtil.get(resourceBundle, "are-you-sure-you-want-to-reset-your-customizations-to-default") + "')){submitForm(document.hrefFm, '" + HttpUtil.encodeURL(resetCustomizationViewURL) + "');}";
					%>

					<li class="control-menu-nav-item hidden-xs">
						<liferay-ui:icon-menu direction="left-side" icon="<%= StringPool.BLANK %>" markupView="lexicon" message="<%= StringPool.BLANK %>" showWhenSingleIcon="<%= true %>">
							<liferay-ui:icon
								linkCssClass="toggle-customized-view"
								message="view-default-page"
								url="javascript:;"
							/>

							<c:if test="<%= layoutTypePortlet.isCustomizedView() %>">
								<liferay-ui:icon
									message="reset-my-customizations"
									url="<%= taglibURL %>"
								/>
							</c:if>
						</liferay-ui:icon-menu>
					</li>

					<li class="control-menu-nav-item visible-xs">
						<div class="btn-group dropdown">
							<button class="btn btn-primary toggle-customized-view" type="button"><liferay-ui:message key="view-default-page" /></button>
							<button aria-expanded="false" class="btn btn-primary dropdown-toggle" data-toggle="dropdown" type="button">
								<span class="caret"></span>
								<span class="sr-only">Toggle Dropdown</span>
							</button>
							<c:if test="<%= layoutTypePortlet.isCustomizedView() %>">
								<ul class="dropdown-menu" role="menu">
									<li>
										<aui:a href="<%= taglibURL %>" label="reset-my-customizations" />
									</li>
								</ul>
							</c:if>
						</div>
					</li>

					<aui:script>
						$('#customizationButton, #closeCustomizationOptions').on(
							'click',
							function(event) {
								$('#customizationBar .control-menu-level-2').toggleClass('open');
							}
						);

						$('#customizationBar').delegate(
							'.toggle-customized-view',
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
			</ul>
		</div>
	</div>
</li>