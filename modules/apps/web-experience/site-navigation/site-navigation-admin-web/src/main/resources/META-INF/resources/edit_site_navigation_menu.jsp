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

<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

SiteNavigationMenu siteNavigationMenu = siteNavigationAdminDisplayContext.getSiteNavigationMenu();

List<SiteNavigationMenuItem> siteNavigationMenuItems = SiteNavigationMenuItemLocalServiceUtil.getSiteNavigationMenuItems(siteNavigationMenu.getSiteNavigationMenuId(), 0);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(siteNavigationMenu.getName());
%>

<c:choose>
	<c:when test="<%= siteNavigationMenuItems.isEmpty() %>">
		<div class="container-fluid-1280">
			<aui:fieldset-group markupView="lexicon">
				<aui:fieldset>
					<div class="text-center">
						<div>
							<liferay-ui:message key="this-menu-is-empty" />
						</div>

						<div>
							<liferay-ui:message key="please-add-elements" />
						</div>
					</div>

					<div class="row" id="<portlet:namespace/>siteNavigationMenuItemTypes">

						<%
						for (SiteNavigationMenuItemType siteNavigationMenuItemType : siteNavigationMenuItemTypeRegistry.getSiteNavigationMenuItemTypes()) {
						%>

							<div class="col-md-2">
								<div class="card card-type-asset">
									<div class="aspect-ratio card-item-first">
										<div class="aspect-ratio-item-center-middle aspect-ratio-item-fluid">
											<liferay-ui:icon
												icon="<%= siteNavigationMenuItemType.getIcon() %>"
												markupView="lexicon"
											/>
										</div>
									</div>

									<div class="card-body">
										<div class="card-row">
											<div class="flex-col flex-col-expand">
												<div class="card-title text-center text-truncate">
													<liferay-portlet:renderURL var="addURL">
														<portlet:param name="mvcPath" value="/add_site_navigation_menu_item.jsp" />
														<portlet:param name="redirect" value="<%= currentURL %>" />
														<portlet:param name="siteNavigationMenuId" value="<%= String.valueOf(siteNavigationMenu.getSiteNavigationMenuId()) %>" />
														<portlet:param name="type" value="<%= siteNavigationMenuItemType.getType() %>" />
													</liferay-portlet:renderURL>

													<aui:a href="<%= addURL %>" label="<%= siteNavigationMenuItemType.getLabel(locale) %>" />
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>

						<%
						}
						%>

					</div>
				</aui:fieldset>
			</aui:fieldset-group>
		</div>
	</c:when>
	<c:otherwise>
		<liferay-ui:error key="<%= InvalidSiteNavigationMenuItemOrderException.class.getName() %>" message="the-order-of-site-navigation-menu-items-is-invalid" />

		<div class="container-fluid-1280">
			<div class="row">
				<div class="col-md-9 pt-3 site-navigation-menu-container">
					<div class="hide" data-site-navigation-menu-item-id="0"></div>

					<%
					for (SiteNavigationMenuItem siteNavigationMenuItem : siteNavigationMenuItems) {
					%>

						<liferay-util:include page="/view_site_navigation_menu_item.jsp" servletContext="<%= application %>">
							<liferay-util:param name="siteNavigationMenuItemId" value="<%= String.valueOf(siteNavigationMenuItem.getSiteNavigationMenuItemId()) %>" />
						</liferay-util:include>

					<%
					}
					%>

				</div>

				<div class="col-md-3">
					<div class="hide sidebar sidebar-light" id="<portlet:namespace />sidebar">
						<div class="sidebar-header">
							<div class="sidebar-section-flex">
								<div class="flex-col flex-col-expand">
									<h4 class="sidebar-title" id="<portlet:namespace />sidebarTitle"></h4>
								</div>

								<div class="flex-col">
									<ul class="nav nav-unstyled sidebar-actions">
										<li class="nav-item">
											<a class="nav-link nav-link-monospaced sidebar-link" href="javascript:;" id="<portlet:namespace />sidebarClose" role="button">
												<aui:icon image="angle-right" markupView="lexicon" />
											</a>
										</li>
									</ul>
								</div>
							</div>
						</div>

						<div class="sidebar-body" id="<portlet:namespace />sidebarBody">
						</div>
					</div>
				</div>
			</div>
		</div>

		<liferay-frontend:add-menu>

			<%
			for (SiteNavigationMenuItemType siteNavigationMenuItemType : siteNavigationMenuItemTypeRegistry.getSiteNavigationMenuItemTypes()) {
				PortletURL addSiteNavigationMenuItemTypeURL = renderResponse.createRenderURL();

				addSiteNavigationMenuItemTypeURL.setParameter("mvcPath", "/add_site_navigation_menu_item.jsp");
				addSiteNavigationMenuItemTypeURL.setParameter("redirect", currentURL);
				addSiteNavigationMenuItemTypeURL.setParameter("siteNavigationMenuId", String.valueOf(siteNavigationMenu.getSiteNavigationMenuId()));
				addSiteNavigationMenuItemTypeURL.setParameter("type", siteNavigationMenuItemType.getType());
			%>

				<liferay-frontend:add-menu-item title="<%= siteNavigationMenuItemType.getLabel(locale) %>" url="<%= addSiteNavigationMenuItemTypeURL.toString() %>" />

			<%
			}
			%>

		</liferay-frontend:add-menu>

		<aui:script require="site-navigation-menu-web/js/SiteNavigationMenuEditor.es as siteNavigationMenuEditorModule">
			var siteNavigationMenuEditor = new siteNavigationMenuEditorModule.default(
				{
					editSiteNavigationMenuItemParentURL: '<portlet:actionURL name="/navigation_menu/edit_site_navigation_menu_item_parent"><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:actionURL>',
					menuContainerSelector: '.site-navigation-menu-container',
					menuItemSelector: '.site-navigation-menu-item',
					namespace: '<portlet:namespace />'
				}
			);

			function handlePortletDestroy() {
				if (siteNavigationMenuEditor) {
					siteNavigationMenuEditor.dispose();

					siteNavigationMenuEditor = null;
				}

				Liferay.detach('destroyPortlet', handlePortletDestroy);
			}

			Liferay.on('destroyPortlet', handlePortletDestroy);
		</aui:script>

		<aui:script use="aui-base,aui-parse-content">
			var sidebar = A.one('#<portlet:namespace />sidebar');
			var sidebarBody = A.one('#<portlet:namespace />sidebarBody');
			var sidebarTitle = A.one('#<portlet:namespace />sidebarTitle');

			A.one('.site-navigation-menu-container').delegate(
				'click',
				function(event) {
					var currentTarget = event.currentTarget;

					var data = Liferay.Util.ns(
						'<portlet:namespace />',
						{
							redirect: '<%= currentURL %>',
							siteNavigationMenuItemId: currentTarget.attr('data-site-navigation-menu-item-id')
						}
					);

					A.io.request(
						'<portlet:renderURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>"><portlet:param name="mvcPath" value="/edit_site_navigation_menu_item.jsp" /></portlet:renderURL>',
						{
							data: data,
							on: {
								success: function(event, id, obj) {
									var responseData = this.get('responseData');

									sidebarBody.plug(A.Plugin.ParseContent);

									sidebarBody.setContent(responseData);

									sidebarTitle.text(currentTarget.attr('data-title'));

									sidebar.removeClass('hide');
								}
							}
						}
					);
				},
				'.site-navigation-menu-item'
			);

			A.one('#<portlet:namespace />sidebarClose').on(
				'click',
				function(event) {
					sidebar.addClass('hide');
				}
			);
		</aui:script>
	</c:otherwise>
</c:choose>