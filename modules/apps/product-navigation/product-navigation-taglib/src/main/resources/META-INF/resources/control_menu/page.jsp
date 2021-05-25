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

<%@ include file="/control_menu/init.jsp" %>

<%
boolean applicationsMenuApp = GetterUtil.getBoolean(request.getAttribute("liferay-product-navigation:control-menu:applicationsMenuApp"));

ProductNavigationControlMenuCategoryRegistry productNavigationControlMenuCategoryRegistry = ServletContextUtil.getProductNavigationControlMenuCategoryRegistry();

List<ProductNavigationControlMenuCategory> productNavigationControlMenuCategories = productNavigationControlMenuCategoryRegistry.getProductNavigationControlMenuCategories(ProductNavigationControlMenuCategoryKeys.ROOT);

ProductNavigationControlMenuEntryRegistry productNavigationControlMenuEntryRegistry = ServletContextUtil.getProductNavigationControlMenuEntryRegistry();

boolean hasControlMenuEntries = false;

Map<ProductNavigationControlMenuCategory, List<ProductNavigationControlMenuEntry>> productNavigationControlMenuEntriesMap = new LinkedHashMap<>();

for (ProductNavigationControlMenuCategory productNavigationControlMenuCategory : productNavigationControlMenuCategories) {
	List<ProductNavigationControlMenuEntry> productNavigationControlMenuEntries = productNavigationControlMenuEntryRegistry.getProductNavigationControlMenuEntries(productNavigationControlMenuCategory, request);

	productNavigationControlMenuEntriesMap.put(productNavigationControlMenuCategory, productNavigationControlMenuEntries);

	if (!productNavigationControlMenuEntries.isEmpty()) {
		hasControlMenuEntries = true;
	}
}
%>

<c:if test="<%= hasControlMenuEntries %>">
	<div class="cadmin control-menu-container">
		<liferay-util:dynamic-include key="com.liferay.product.navigation.taglib#/page.jsp#pre" />

		<div class="control-menu control-menu-level-1 control-menu-level-1-<%= applicationsMenuApp ? "light" : "dark" %> d-print-none" data-qa-id="controlMenu" id="<portlet:namespace />ControlMenu">
			<clay:container-fluid>
				<h1 class="sr-only"><liferay-ui:message key="admin-header" /></h1>

				<ul class="control-menu-level-1-nav control-menu-nav" data-namespace="<portlet:namespace />" data-qa-id="header" id="<portlet:namespace />controlMenu">

					<%
					for (Map.Entry<ProductNavigationControlMenuCategory, List<ProductNavigationControlMenuEntry>> entry : productNavigationControlMenuEntriesMap.entrySet()) {
						ProductNavigationControlMenuCategory productNavigationControlMenuCategory = entry.getKey();
					%>

						<li class="control-menu-nav-category <%= productNavigationControlMenuCategory.getKey() %>-control-group">
							<ul class="control-menu-nav">

								<%
								for (ProductNavigationControlMenuEntry productNavigationControlMenuEntry : entry.getValue()) {
									if (productNavigationControlMenuEntry.includeIcon(request, PipingServletResponseFactory.createPipingServletResponse(pageContext))) {
										continue;
									}
								%>

									<li class="control-menu-nav-item">
										<liferay-ui:icon
											data="<%= productNavigationControlMenuEntry.getData(request) %>"
											icon="<%= productNavigationControlMenuEntry.getIcon(request) %>"
											iconCssClass="<%= productNavigationControlMenuEntry.getIconCssClass(request) %>"
											label="<%= false %>"
											linkCssClass='<%= "control-menu-icon " + productNavigationControlMenuEntry.getLinkCssClass(request) %>'
											markupView="<%= productNavigationControlMenuEntry.getMarkupView(request) %>"
											message="<%= productNavigationControlMenuEntry.getLabel(locale) %>"
											method="get"
											url="<%= productNavigationControlMenuEntry.getURL(request) %>"
										/>
									</li>

								<%
								}
								%>

							</ul>
						</li>

					<%
					}
					%>

				</ul>
			</clay:container-fluid>

			<div class="control-menu-body">

				<%
				for (ProductNavigationControlMenuCategory productNavigationControlMenuCategory : productNavigationControlMenuCategories) {
					List<ProductNavigationControlMenuEntry> productNavigationControlMenuEntries = productNavigationControlMenuEntriesMap.get(productNavigationControlMenuCategory);

					for (ProductNavigationControlMenuEntry productNavigationControlMenuEntry : productNavigationControlMenuEntries) {
						productNavigationControlMenuEntry.includeBody(request, PipingServletResponseFactory.createPipingServletResponse(pageContext));
					}
				}
				%>

			</div>

			<div id="controlMenuAlertsContainer"></div>
		</div>

		<liferay-util:dynamic-include key="com.liferay.product.navigation.taglib#/page.jsp#post" />
	</div>

	<aui:script use="liferay-product-navigation-control-menu">
		Liferay.ControlMenu.init('#<portlet:namespace />controlMenu');

		var sidenavToggles = document.querySelectorAll(
			'#<portlet:namespace />ControlMenu [data-toggle="liferay-sidenav"]'
		);

		var sidenavInstances = Array.from(sidenavToggles).map((toggle) => {
			return Liferay.SideNavigation.instance(toggle);
		});

		sidenavInstances.forEach((instance) => {
			instance.on('openStart.lexicon.sidenav', (event, source) => {
				sidenavInstances.forEach((sidenav) => {
					if (sidenav !== source) {
						sidenav.hide();
					}
				});
			});
		});
	</aui:script>
</c:if>