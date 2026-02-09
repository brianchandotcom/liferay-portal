<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/applications_menu/init.jsp" %>

<%
ApplicationsMenuDisplayContext applicationsMenuDisplayContext = new ApplicationsMenuDisplayContext(request);
%>

<div class="applications-menu-container c-slideout-container<%= applicationsMenuDisplayContext.isVisible() ? " c-slideout-push-start" : "" %>" id="com_liferay_application_list_taglib_applications_menu_container">
	<div class="c-slideout c-slideout-fixed c-slideout-push c-slideout-start<%= applicationsMenuDisplayContext.isVisible() ? " c-slideout-shown" : "" %>">
		<section aria-labelledby="com_liferay_application_list_taglib_applications_menu_label" class="sidebar sidebar-light<%= applicationsMenuDisplayContext.isVisible() ? " c-slideout-show" : "" %>" id="com_liferay_application_list_taglib_applications_menu" tabindex="-1">
			<div class="c-focus-trap">
				<div class="sidebar-header">
					<div class="autofit-row">
						<div class="autofit-col autofit-col-expand">
							<span class="component-title">
								<%-- TODO: replace the icon below with the panel icon --%>
								<clay:icon symbol="grid" /><span class="c-px-2"><%= applicationsMenuDisplayContext.getPanelCategoryLabel() %></span>
							</span>
						</div>

						<div class="autofit-col">
							<button aria-controls="com_liferay_application_list_taglib_applications_menu" class="close lfr-portal-tooltip" title="<%= LanguageUtil.get(request, "close-product-menu") %>" type="button">
								<clay:icon
									symbol="times"
								/>
							</button>
						</div>
					</div>
				</div>

				<div class="sidebar-body">
					<clay:vertical-nav
						active="<%= applicationsMenuDisplayContext.getPortletId() %>"
						defaultExpandedKeys="<%= applicationsMenuDisplayContext.getExpandedKeys() %>"
						verticalNavItems="<%= applicationsMenuDisplayContext.getVerticalNavItems() %>"
					/>
				</div>
			</div>
		</section>
	</div>

	<react:component
		module="{ApplicationsMenu} from application-list-taglib"
		props="<%= applicationsMenuDisplayContext.getProps() %>"
	/>
</div>