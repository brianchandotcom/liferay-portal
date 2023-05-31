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

<%@ include file="/dynamic_include/init.jsp" %>

<%
String portletNamespace = PortalUtil.getPortletNamespace(LayoutAdminPortletKeys.GROUP_PAGES);

boolean hasUpdateLayoutPermission = GetterUtil.getBoolean(request.getAttribute(CustomizationSettingsControlMenuJSPDynamicInclude.CUSTOMIZATION_SETTINGS_LAYOUT_UPDATE_PERMISSION));
%>

<link href="<%= PortalUtil.getStaticResourceURL(request, PortalUtil.getPathProxy() + application.getContextPath() + "/css/customization_settings.css") %>" rel="stylesheet" type="text/css" />

<liferay-ui:success key='<%= LayoutAdminPortletKeys.GROUP_PAGES + "requestProcessed" %>' message="your-request-completed-successfully" />

<div id="<%= portletNamespace %>customizationBar">
	<div class="control-menu-level-2">
		<clay:container-fluid>
			<ul class="control-menu-level-2-nav control-menu-nav flex-column flex-md-row">
				<li class="c-mb-0 control-menu-nav-item flex-shrink-1">
					<span class="text-info">
						<clay:icon
							data='<%=
								HashMapBuilder.<String, Object>put(
									"qa-id", "customizations"
								).build()
							%>'
							symbol="info-circle"
						/>

						<c:choose>
							<c:when test="<%= layoutTypePortlet.isCustomizedView() %>">
								<strong>
									<liferay-ui:message key="you-can-customize-this-page" />
								</strong>

								<liferay-ui:message key="customizable-user-help" />
							</c:when>
							<c:otherwise>
								<liferay-ui:message key="this-is-the-default-page-without-your-customizations" />

								<c:if test="<%= hasUpdateLayoutPermission %>">
									<liferay-ui:message key="customizable-admin-help" />
								</c:if>
							</c:otherwise>
						</c:choose>
					</span>
				</li>

				<c:if test="<%= hasUpdateLayoutPermission %>">
					<li class="c-mb-0 c-ml-2 control-menu-nav-item flex-shrink-0">
						<aui:input id='<%= portletNamespace + "manageCustomization" %>' inlineField="<%= true %>" label="<%= StringPool.BLANK %>" labelOff='<%= LanguageUtil.get(resourceBundle, "hide-customizable-zones") %>' labelOn='<%= LanguageUtil.get(resourceBundle, "view-customizable-zones") %>' name="manageCustomization" type="toggle-switch" useNamespace="<%= false %>" wrappedField="<%= true %>" />

						<div class="hide layout-customizable-controls-container" id="<%= portletNamespace %>layoutCustomizableControls">
							<div class="layout-customizable-controls">
								<span title="<liferay-ui:message key="customizable-help" />">
									<aui:input cssClass="layout-customizable-checkbox" helpMessage="customizable-help" id="TypeSettingsProperties--[COLUMN_ID]-customizable--" label="" labelOff="not-customizable" labelOn="customizable" name="TypeSettingsProperties--[COLUMN_ID]-customizable--" type="toggle-switch" useNamespace="<%= false %>" />
								</span>
							</div>
						</div>
					</li>

					<liferay-frontend:component
						module="js/LayoutCustomizationSettings"
					/>
				</c:if>

				<li class="c-ml-2 control-menu-nav-item d-md-block d-none flex-shrink-0">

					<%
					CustomizationSettingsActionDropdownItemsProvider customizationSettingsActionDropdownItemsProvider = new CustomizationSettingsActionDropdownItemsProvider(request);
					%>

					<clay:dropdown-actions
						aria-label='<%= LanguageUtil.get(request, "show-actions") %>'
						dropdownItems="<%= customizationSettingsActionDropdownItemsProvider.getActionDropdownItems() %>"
						propsTransformer="js/CustomizationSettingsActionDropdownPropsTransformer"
					/>
				</li>
			</ul>
		</clay:container-fluid>
	</div>
</div>