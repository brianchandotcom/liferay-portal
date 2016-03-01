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
List<ProductNavigationControlMenuCategory> productNavigationControlMenuCategories = (List<ProductNavigationControlMenuCategory>)request.getAttribute("liferay-product-navigation:control-menu:control-menu-categories");
ProductNavigationControlMenuEntryRegistry productNavigationControlMenuEntryRegistry = (ProductNavigationControlMenuEntryRegistry)request.getAttribute("liferay-product-navigation:control-menu:control-menu-entry-registry");
%>

<c:if test="<%= !productNavigationControlMenuCategories.isEmpty() %>">
	<div class="control-menu control-menu-level-1" data-qa-id="controlMenu" id="<portlet:namespace/>ControlMenu">
		<div class="container-fluid-1280">
			<ul class="control-menu-level-1-nav control-menu-nav" data-namespace="<portlet:namespace />" data-qa-id="header" id="<portlet:namespace />controlMenu">

				<%
				for (ProductNavigationControlMenuCategory productNavigationControlMenuCategory : productNavigationControlMenuCategories) {
				%>

					<li class="control-menu-nav-item <%= productNavigationControlMenuCategory.getKey() %>-control-group">
						<ul class="control-menu-nav">

							<%
							List<ProductNavigationControlMenuEntry> productNavigationControlMenuEntries = productNavigationControlMenuEntryRegistry.getProductNavigationControlMenuEntries(productNavigationControlMenuCategory, request);

							for (ProductNavigationControlMenuEntry productNavigationControlMenuEntry : productNavigationControlMenuEntries) {
								if (productNavigationControlMenuEntry.includeIcon(request, new PipingServletResponse(pageContext))) {
									continue;
								}
							%>

								<liferay-ui:icon
									data="<%= productNavigationControlMenuEntry.getData(request) %>"
									icon="<%= productNavigationControlMenuEntry.getIconCssClass(request) %>"
									label="<%= false %>"
									linkCssClass='<%= "control-menu-icon " + productNavigationControlMenuEntry.getLinkCssClass(request) %>'
									markupView="lexicon"
									message="<%= productNavigationControlMenuEntry.getLabel(locale) %>"
									url="<%= productNavigationControlMenuEntry.getURL(request) %>"
								/>

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
				List<ProductNavigationControlMenuEntry> productNavigationControlMenuEntries = productNavigationControlMenuEntryRegistry.getProductNavigationControlMenuEntries(productNavigationControlMenuCategory, request);

				for (ProductNavigationControlMenuEntry productNavigationControlMenuEntry : productNavigationControlMenuEntries) {
					productNavigationControlMenuEntry.includeBody(request, new PipingServletResponse(pageContext));
				}
			}
			%>

		</div>

		<div id="controlMenuAlertsContainer"></div>
	</div>

	<aui:script use="liferay-product-navigation-control-menu">
		Liferay.ControlMenu.init('#<portlet:namespace />controlMenu');

		var panelEntryBodies = $('#<portlet:namespace/>ControlMenu [data-toggle="sidenav"]').toArray().map(
			function(item) {
				return $(item.getAttribute('data-target').split(',')[0]);
			}
		);

		panelEntryBodies.forEach(
			function(item) {
				item.on(
					'openStart.lexicon.sidenav',
					function(event) {
						var itemId = event.target.getAttribute('id');

						panelEntryBodies.forEach(
							function(item) {
								var panelId = item.attr('id');

								if (panelId !== itemId) {
									$('#<portlet:namespace/>ControlMenu [data-toggle="sidenav"][data-target*="' + panelId + '"]').sideNavigation('hide');
								}
							}
						);
					}
				);
			}
		);
	</aui:script>
</c:if>