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

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.layout.admin.constants.LayoutAdminPortletKeys" %><%@
page import="com.liferay.layout.admin.web.internal.control.menu.CustomizationSettingsProductNavigationControlMenuEntry" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.language.UnicodeLanguageUtil" %><%@
page import="com.liferay.portal.kernel.portlet.PortletURLFactoryUtil" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.HttpUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %>

<%@ page import="java.util.HashMap" %><%@
page import="java.util.Map" %>

<%@ page import="javax.portlet.ActionRequest" %><%@
page import="javax.portlet.PortletRequest" %><%@
page import="javax.portlet.PortletURL" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
String portletNamespace = PortalUtil.getPortletNamespace(LayoutAdminPortletKeys.GROUP_PAGES);

boolean hasUpdateLayoutPermission = GetterUtil.getBoolean(request.getAttribute(CustomizationSettingsProductNavigationControlMenuEntry.CUSTOMIZATION_SETTINGS_LAYOUT_UPDATE_PERMISSION));

Map<String, Object> data = new HashMap<>();

data.put("qa-id", "customizations");
%>

<li class="active control-menu-link control-menu-nav-item customization-link visible-xs">
	<liferay-ui:icon
		data="<%= data %>"
		icon="pencil"
		id='<%= portletNamespace + "customizationButton" %>'
		label="<%= false %>"
		linkCssClass="control-menu-icon"
		markupView="lexicon"
		message="this-page-can-be-customized"
		url="javascript:;"
	/>
</li>
<li class="control-menu-nav-item" id="<%= portletNamespace %>customizationBar">
	<div class="control-menu-level-2">
		<div class="container-fluid-1280">
			<div class="control-menu-level-2-heading visible-xs">
				<liferay-ui:message key="customization-options" />

				<button aria-labelledby="Close" class="close" id="<%= portletNamespace %>closeCustomizationOptions" type="button">
					<aui:icon image="times" markupView="lexicon" />
				</button>
			</div>

			<ul class="control-menu-level-2-nav control-menu-nav">
				<li class="control-menu-nav-item">
					<p>
						<c:choose>
							<c:when test="<%= layoutTypePortlet.isCustomizedView() %>">
								<liferay-ui:message key="you-can-customize-this-page" />

								<span class="text-muted">
									<liferay-ui:message key="customizable-user-help" />
								</span>
							</c:when>
							<c:otherwise>
								<liferay-ui:message key="this-is-the-default-page-without-your-customizations" />

								<c:if test="<%= hasUpdateLayoutPermission %>">
									<span class="text-muted">
										<liferay-ui:message key="customizable-admin-help" />
									</span>
								</c:if>
							</c:otherwise>
						</c:choose>
					</p>
				</li>

				<c:if test="<%= hasUpdateLayoutPermission %>">
					<li class="control-menu-nav-item">
						<aui:input id='<%= portletNamespace + "manageCustomization" %>' inlineField="<%= true %>" label="<%= StringPool.BLANK %>" labelOff='<%= LanguageUtil.get(resourceBundle, "hide-customizable-zones") %>' labelOn='<%= LanguageUtil.get(resourceBundle, "view-customizable-zones") %>' name="manageCustomization" type="toggle-switch" useNamespace="<%= false %>" wrappedField="<%= true %>" />

						<div class="hide layout-customizable-controls-container" id="<%= portletNamespace %>layoutCustomizableControls">
							<div class="layout-customizable-controls">
								<span title="<liferay-ui:message key="customizable-help" />">
									<aui:input cssClass="layout-customizable-checkbox" helpMessage="customizable-help" id="TypeSettingsProperties--[COLUMN_ID]-customizable--" label="" labelOff="not-customizable" labelOn="customizable" name="TypeSettingsProperties--[COLUMN_ID]-customizable--" type="toggle-switch" useNamespace="<%= false %>" />
								</span>
							</div>
						</div>
					</li>

					<aui:script use="liferay-layout-customization-settings">
						var layoutCustomizationSettings = new Liferay.LayoutCustomizationSettings(
							{
								namespace: '<%= portletNamespace %>'
							}
						);

						Liferay.once(
							'screenLoad',
							function() {
								layoutCustomizationSettings.destroy();
							}
						);
					</aui:script>
				</c:if>

				<%
				String toggleCustomizedViewMessage = "view-page-without-my-customizations";

				if (!layoutTypePortlet.isCustomizedView()) {
					toggleCustomizedViewMessage = "view-my-customized-page";
				}
				else if (layoutTypePortlet.isDefaultUpdated()) {
					toggleCustomizedViewMessage = "the-defaults-for-the-current-page-have-been-updated-click-here-to-see-them";
				}

				PortletURL resetCustomizationViewURL = PortletURLFactoryUtil.create(request, LayoutAdminPortletKeys.GROUP_PAGES, PortletRequest.ACTION_PHASE);

				resetCustomizationViewURL.setParameter(ActionRequest.ACTION_NAME, "/layout/reset_customization_view");

				String resetCustomizationsViewURLString = "javascript:if (confirm('" + UnicodeLanguageUtil.get(resourceBundle, "are-you-sure-you-want-to-reset-your-customizations-to-default") + "')){submitForm(document.hrefFm, '" + HtmlUtil.escapeJS(resetCustomizationViewURL.toString()) + "');}";

				PortletURL toggleCustomizationViewPortletURL = PortletURLFactoryUtil.create(request, LayoutAdminPortletKeys.GROUP_PAGES, PortletRequest.ACTION_PHASE);

				toggleCustomizationViewPortletURL.setParameter(ActionRequest.ACTION_NAME, "/layout/toggle_customized_view");

				String toggleCustomizationViewURL = HttpUtil.addParameter(toggleCustomizationViewPortletURL.toString(), "customized_view", !layoutTypePortlet.isCustomizedView());
				%>

				<li class="control-menu-nav-item hidden-xs">
					<liferay-ui:icon-menu
						direction="left-side"
						icon="<%= StringPool.BLANK %>"
						markupView="lexicon"
						message="<%= StringPool.BLANK %>"
						showWhenSingleIcon="<%= true %>"
					>
						<liferay-ui:icon
							message="<%= toggleCustomizedViewMessage %>"
							url="<%= toggleCustomizationViewURL %>"
						/>

						<c:if test="<%= layoutTypePortlet.isCustomizedView() %>">
							<liferay-ui:icon
								message="reset-my-customizations"
								url="<%= resetCustomizationsViewURLString %>"
							/>
						</c:if>
					</liferay-ui:icon-menu>
				</li>
				<li class="control-menu-nav-item visible-xs">
					<div class="btn-group dropdown">
						<aui:a cssClass="btn btn-primary" href="<%= toggleCustomizationViewURL %>" label="<%= toggleCustomizedViewMessage %>" />

						<c:if test="<%= layoutTypePortlet.isCustomizedView() %>">
							<button aria-expanded="false" class="btn btn-primary dropdown-toggle" data-toggle="dropdown" type="button">
								<span class="caret"></span>

								<span class="sr-only"><liferay-ui:message key="toggle-dropdown" /></span>
							</button>

							<ul class="dropdown-menu" role="menu">
								<li>
									<aui:a href="<%= resetCustomizationsViewURLString %>" label="reset-my-customizations" />
								</li>
							</ul>
						</c:if>
					</div>
				</li>

				<aui:script>
					$('#<%= portletNamespace %>customizationButton, #<%= portletNamespace %>closeCustomizationOptions').on(
						'click',
						function(event) {
							$('#<%= portletNamespace %>customizationBar .control-menu-level-2').toggleClass('open');
						}
					);
				</aui:script>
			</ul>
		</div>
	</div>
</li>