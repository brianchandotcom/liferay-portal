<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/side_navigation/init.jsp" %>

<%
SideNavigationDisplayContext sideNavigationDisplayContext = new SideNavigationDisplayContext(request);
%>

<div class="side-navigation-container c-slideout-container<%= sideNavigationDisplayContext.isVisible() ? " c-slideout-push-start" : "" %>" id="com_liferay_application_list_taglib_side_navigation_container">
	<div class="c-slideout c-slideout-fixed c-slideout-push c-slideout-start<%= sideNavigationDisplayContext.isVisible() ? " c-slideout-shown" : "" %>">
		<section aria-labelledby="com_liferay_application_list_taglib_side_navigation_label" class="sidebar sidebar-light<%= sideNavigationDisplayContext.isVisible() ? " c-slideout-show" : "" %>" data-qa-id="sideNavigation" id="com_liferay_application_list_taglib_side_navigation">
			<div class="c-focus-trap">
				<div class="sidebar-header">
					<div class="autofit-row">
						<div class="autofit-col autofit-col-expand">
							<span class="component-title" id="com_liferay_application_list_taglib_side_navigation_label">
								<%-- TODO: replace the icon below with the panel icon --%>
								<clay:icon symbol="grid" /><span class="c-px-2" data-qa-id="sideNavigationLabel"><%= sideNavigationDisplayContext.getPanelCategoryLabel() %></span>
							</span>
						</div>

						<div class="autofit-col">
							<button aria-controls="com_liferay_application_list_taglib_side_navigation" class="close lfr-portal-tooltip" title="<%= LanguageUtil.get(request, "close-product-menu") %>" type="button">
								<clay:icon
									symbol="times"
								/>
							</button>
						</div>
					</div>
				</div>

				<div class="c-px-0 sidebar-body">
					<clay:vertical-nav
						active="<%= sideNavigationDisplayContext.getPortletId() %>"
						defaultExpandedKeys="<%= sideNavigationDisplayContext.getExpandedKeys() %>"
						verticalNavItems="<%= sideNavigationDisplayContext.getVerticalNavItems() %>"
					/>
				</div>
			</div>
		</section>
	</div>

	<react:component
		module="{SideNavigation} from application-list-taglib"
		props="<%= sideNavigationDisplayContext.getProps() %>"
	/>
</div>