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
						<div class="align-items-center autofit-col autofit-col-expand d-flex flex-row">
							<span class="border-0 sticker sticker-outline">
								<span class="sticker-overlay">
									<img alt="" class="c-mx-1" data-qa-id="sideNavigationProductIcon" src="<%= sideNavigationDisplayContext.getPanelCategoryImageUrl() %>" />
								</span>
							</span>
							<span class="c-px-2 component-title flex-grow-1" id="com_liferay_application_list_taglib_side_navigation_label">
								<span data-qa-id="sideNavigationLabel"><%= sideNavigationDisplayContext.getPanelCategoryLabel() %></span>
							</span>

							<clay:button
								aria-label='<%= LanguageUtil.get(request, "go-to-other-site") %>'
								borderless="<%= true %>"
								data-qa-id="sideNavigationSiteSelectorButton"
								displayType="secondary"
								icon="sites"
								monospaced="<%= true %>"
								small="<%= true %>"
								title='<%= LanguageUtil.get(request, "go-to-other-site") %>'
							/>
						</div>

						<div class="align-items-center autofit-col d-flex flex-row">
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
						displayType="primary"
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