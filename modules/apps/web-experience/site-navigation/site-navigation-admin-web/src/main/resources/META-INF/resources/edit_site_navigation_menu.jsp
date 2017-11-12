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

List<SiteNavigationMenuItem> siteNavigationMenuItems = SiteNavigationMenuItemLocalServiceUtil.getSiteNavigationMenuItems(siteNavigationMenu.getSiteNavigationMenuId());

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(siteNavigationMenu.getName());
%>

<portlet:actionURL name="/navigation_menu/edit_site_navigation_menu" var="editSitaNavigationMenuURL">
	<portlet:param name="mvcPath" value="/edit_site_navigation_menu.jsp" />
	<portlet:param name="groupId" value="<%= String.valueOf(scopeGroupId) %>" />
</portlet:actionURL>

<aui:form action="<%= editSitaNavigationMenuURL %>" cssClass="container-fluid-1280" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="siteNavigationMenuId" type="hidden" value="<%= siteNavigationAdminDisplayContext.getSiteNavigationMenuId() %>" />

	<aui:model-context bean="<%= siteNavigationMenu %>" model="<%= SiteNavigationMenu.class %>" />

	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>
			<c:choose>
				<c:when test="<%= siteNavigationMenuItems.isEmpty() %>">
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
				</c:when>
				<c:otherwise>

					<%
					for (SiteNavigationMenuItem siteNavigationMenuItem : siteNavigationMenuItems) {
						SiteNavigationMenuItemType siteNavigationMenuItemType = siteNavigationMenuItemTypeRegistry.getSiteNavigationMenuItemType(siteNavigationMenuItem.getType());

						request.setAttribute("edit_site_navigation_menu.jsp-siteNavigationMenuItemId", siteNavigationMenuItem.getSiteNavigationMenuItemId());
					%>

						<div class="col-md-3">
							<liferay-frontend:horizontal-card
								actionJsp="/site_navigation_menu_item_action.jsp"
								actionJspServletContext="<%= application %>"
								text="<%= siteNavigationMenuItemType.getTitle(siteNavigationMenuItem, locale) %>"
							>
								<liferay-frontend:horizontal-card-col>
									<liferay-frontend:horizontal-card-icon
										icon="<%= siteNavigationMenuItemType.getIcon() %>"
									/>
								</liferay-frontend:horizontal-card-col>
							</liferay-frontend:horizontal-card>
						</div>

					<%
					}
					%>

				</c:otherwise>
			</c:choose>
		</aui:fieldset>
	</aui:fieldset-group>
</aui:form>

<c:if test="<%= !siteNavigationMenuItems.isEmpty() %>">
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
</c:if>