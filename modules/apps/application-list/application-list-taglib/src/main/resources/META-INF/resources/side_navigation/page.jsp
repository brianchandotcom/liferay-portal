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
				<div class="c-mt-2 c-mx-1 c-px-2 sidebar-header">
					<div class="autofit-row">
						<div class="align-items-center autofit-col autofit-col-expand d-flex flex-row">
							<span class="border-0 c-mr-1 sticker sticker-outline">
								<span class="sticker-overlay">
									<img alt="" class="c-mx-1" data-qa-id="sideNavigationProductIcon" src="<%= sideNavigationDisplayContext.getPanelCategoryImageUrl() %>" />
								</span>
							</span>
							<span class="align-items-center c-my-0 component-title d-flex" id="com_liferay_application_list_taglib_side_navigation_label">
								<span class="c-ml-2 text-5" id="sideNavigationLabel"><%= sideNavigationDisplayContext.getPanelCategoryLabel() %></span>
							</span>

							<clay:button
								aria-label='<%= LanguageUtil.get(request, "go-to-other-site") %>'
								borderless="<%= true %>"
								cssClass="c-ml-auto"
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
					<div class="c-px-4">
						<div class="form-group">
							<div class="input-group">
								<div class="input-group-item">
									<input aria-label="<%= LanguageUtil.get(request, "search") %>" class="form-control input-group-inset input-group-inset-before" id="com_liferay_application_list_taglib_side_navigation_search_input" placeholder="<%= LanguageUtil.get(request, "search") %>" type="text" value="" />

									<div class="input-group-inset-item input-group-inset-item-before">
										<clay:icon
											cssClass="c-ml-3 c-mr-1"
											symbol="search"
										/>
									</div>
								</div>
							</div>
						</div>
					</div>

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