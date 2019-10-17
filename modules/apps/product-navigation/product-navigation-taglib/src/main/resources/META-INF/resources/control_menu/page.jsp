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
	<div class="control-menu control-menu-level-1 hidden-print" data-qa-id="controlMenu" id="<portlet:namespace />ControlMenu">
		<div class="container-fluid container-fluid-max-xl">
			<h1 class="sr-only"><liferay-ui:message key="admin-header" /></h1>

			<ul class="control-menu-level-1-nav control-menu-nav" data-namespace="<portlet:namespace />" data-qa-id="header" id="<portlet:namespace />controlMenu">

				<%
				for (Map.Entry entry : productNavigationControlMenuEntriesMap.entrySet()) {
					ProductNavigationControlMenuCategory productNavigationControlMenuCategory = (ProductNavigationControlMenuCategory)entry.getKey();
				%>

					<li class="control-menu-nav-category <%= productNavigationControlMenuCategory.getKey() %>-control-group">
						<ul class="control-menu-nav">

							<%
							for (ProductNavigationControlMenuEntry productNavigationControlMenuEntry : (List<ProductNavigationControlMenuEntry>)entry.getValue()) {
								if (productNavigationControlMenuEntry.includeIcon(request, PipingServletResponse.createPipingServletResponse(pageContext))) {
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
		</div>

		<div class="control-menu-body">

			<%
			for (ProductNavigationControlMenuCategory productNavigationControlMenuCategory : productNavigationControlMenuCategories) {
				List<ProductNavigationControlMenuEntry> productNavigationControlMenuEntries = productNavigationControlMenuEntriesMap.get(productNavigationControlMenuCategory);

				for (ProductNavigationControlMenuEntry productNavigationControlMenuEntry : productNavigationControlMenuEntries) {
					productNavigationControlMenuEntry.includeBody(request, PipingServletResponse.createPipingServletResponse(pageContext));
				}
			}
			%>

		</div>

		<div id="controlMenuAlertsContainer"></div>
	</div>

	<aui:script use="liferay-product-navigation-control-menu">
		Liferay.ControlMenu.init('#<portlet:namespace />controlMenu');

		var sidenavToggles = document.querySelectorAll(
			'#<portlet:namespace />ControlMenu [data-toggle="liferay-sidenav"]'
		);

		var sidenavInstances = Array.from(sidenavToggles).map(function(toggle) {
			return Liferay.SideNavigation.instance(toggle);
		});

		sidenavInstances.forEach(function(instance) {
			instance.on('openStart.lexicon.sidenav', function(event, source) {
				sidenavInstances.forEach(function(sidenav) {
					if (sidenav !== source) {
						sidenav.hide();
					}
				});
			});
		});
	</aui:script>
</c:if>