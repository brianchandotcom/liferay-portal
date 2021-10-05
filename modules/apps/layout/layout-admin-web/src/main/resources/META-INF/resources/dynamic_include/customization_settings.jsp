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

<div id="<%= portletNamespace %>customizationBar">
	<div class="control-menu-level-2 py-2">
		<clay:container-fluid>
			<div class="control-menu-level-2-heading d-block d-md-none">
				<liferay-ui:message key="customization-options" />

				<button aria-label="<%= LanguageUtil.get(request, "close") %>" class="close" id="<%= portletNamespace %>closeCustomizationOptions" type="button">
					<aui:icon image="times" markupView="lexicon" />
				</button>
			</div>

			<ul class="control-menu-level-2-nav control-menu-nav flex-column flex-md-row">
				<li class="control-menu-nav-item flex-shrink-1 mb-0">
					<span class="text-info">
						<liferay-ui:icon
							data='<%=
								HashMapBuilder.<String, Object>put(
									"qa-id", "customizations"
								).build()
							%>'
							icon="info-circle"
							label="<%= false %>"
							markupView="lexicon"
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
					<li class="control-menu-nav-item flex-shrink-0 mb-0 ml-2">
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

				<%
				String toggleCustomizedViewMessage = "view-page-without-my-customizations";

				if (!layoutTypePortlet.isCustomizedView()) {
					toggleCustomizedViewMessage = "view-my-customized-page";
				}
				else if (layoutTypePortlet.isDefaultUpdated()) {
					toggleCustomizedViewMessage = "the-defaults-for-the-current-page-have-been-updated-click-here-to-see-them";
				}

				toggleCustomizedViewMessage = LanguageUtil.get(resourceBundle, toggleCustomizedViewMessage);

				String resetCustomizationViewURL = PortletURLBuilder.create(
					PortletURLFactoryUtil.create(request, LayoutAdminPortletKeys.GROUP_PAGES, PortletRequest.ACTION_PHASE)
				).setActionName(
					"/layout_admin/reset_customization_view"
				).buildString();

				String resetCustomizationsViewURLString = "javascript:if (confirm('" + UnicodeLanguageUtil.get(resourceBundle, "are-you-sure-you-want-to-reset-your-customizations-to-default") + "')){submitForm(document.hrefFm, '" + HtmlUtil.escapeJS(resetCustomizationViewURL) + "');}";

				String toggleCustomizationViewURL = HttpUtil.addParameter(
					PortletURLBuilder.create(
						PortletURLFactoryUtil.create(request, LayoutAdminPortletKeys.GROUP_PAGES, PortletRequest.ACTION_PHASE)
					).setActionName(
						"/layout_admin/toggle_customized_view"
					).buildString(),
					"customized_view", !layoutTypePortlet.isCustomizedView());
				%>

				<li class="control-menu-nav-item d-md-block d-none flex-shrink-0 ml-2">
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
				<li class="control-menu-nav-item d-block d-md-none flex-shrink-0 mb-0 ml-2 mt-3">
					<div class="btn-group dropdown flex-nowrap">
						<aui:a cssClass="btn btn-primary text-white" href="<%= toggleCustomizationViewURL %>" label="<%= toggleCustomizedViewMessage %>" />

						<c:if test="<%= layoutTypePortlet.isCustomizedView() %>">
							<button aria-expanded="false" class="btn btn-primary dropdown-toggle flex-grow-0 h-auto" data-toggle="dropdown" type="button">
								<span class="caret"></span>

								<span class="sr-only"><liferay-ui:message key="toggle-dropdown" /></span>
							</button>

							<ul class="dropdown-menu" role="menu">
								<li>
									<aui:a cssClass="dropdown-item" href="<%= resetCustomizationsViewURLString %>" label="reset-my-customizations" />
								</li>
							</ul>
						</c:if>
					</div>
				</li>

				<aui:script>
					const closeCustomizationOptions = document.getElementById(
						'<%= portletNamespace %>closeCustomizationOptions'
					);
					const controlMenu = document.querySelector(
						'#<%= portletNamespace %>customizationBar .control-menu-level-2'
					);

					if (closeCustomizationOptions && controlMenu) {
						closeCustomizationOptions.addEventListener('click', (event) => {
							controlMenu.classList.toggle('open');
						});
					}

					const customizationButton = document.getElementById(
						'<%= portletNamespace %>customizationButton'
					);

					if (customizationButton && controlMenu) {
						customizationButton.addEventListener('click', (event) => {
							controlMenu.classList.toggle('open');
						});
					}
				</aui:script>
			</ul>
		</clay:container-fluid>
	</div>
</div>