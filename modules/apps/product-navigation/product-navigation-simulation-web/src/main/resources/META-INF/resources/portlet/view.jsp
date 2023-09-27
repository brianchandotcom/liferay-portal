<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/portlet/init.jsp" %>

<%
PanelAppRegistry panelAppRegistry = (PanelAppRegistry)request.getAttribute(ApplicationListWebKeys.PANEL_APP_REGISTRY);
PanelCategoryRegistry panelCategoryRegistry = (PanelCategoryRegistry)request.getAttribute(ApplicationListWebKeys.PANEL_CATEGORY_REGISTRY);

PanelCategory panelCategory = panelCategoryRegistry.getPanelCategory(ProductNavigationSimulationConstants.SIMULATION_PANEL_CATEGORY_KEY);
PanelCategoryHelper panelCategoryHelper = new PanelCategoryHelper(panelAppRegistry, panelCategoryRegistry);
%>

<div class="pb-3 px-3 simulation-menu" data-qa-id="simulationMenuBody" id="<portlet:namespace />simulationPanelContainer">
	<div aria-multiselectable="true" class="panel-group" role="tablist">

		<%
		for (PanelApp panelApp : panelCategoryHelper.getAllPanelApps(panelCategory.getKey())) {
		%>

			<div class="mb-3 panel">
				<div class="panel-heading" id="<portlet:namespace /><%= AUIUtil.normalizeId(panelApp.getKey()) %>Header" role="tab">
					<a aria-controls="<portlet:namespace /><%= AUIUtil.normalizeId(panelApp.getKey()) %>Collapse" aria-expanded="<%= true %>" class="collapse-icon collapse-icon-middle list-group-heading panel-header pl-0 text-decoration-none" data-toggle="liferay-collapse" href="#<portlet:namespace /><%= AUIUtil.normalizeId(panelApp.getKey()) %>Collapse" role="button">
						<span class="category-name font-weight-semi-bold text-truncate text-uppercase"><%= panelApp.getLabel(locale) %></span>

						<clay:icon
							cssClass="collapse-icon-closed"
							symbol="angle-right"
						/>

						<clay:icon
							cssClass="collapse-icon-open"
							symbol="angle-down"
						/>
					</a>
				</div>

				<div aria-expanded="<%= true %>" aria-labelledby="<portlet:namespace /><%= AUIUtil.normalizeId(panelApp.getKey()) %>Header" class="collapse panel-collapse show" id="<portlet:namespace /><%= AUIUtil.normalizeId(panelApp.getKey()) %>Collapse" role="tabpanel">
					<div class="simulation-app-panel-body">

						<%
						panelApp.include(request, response);
						%>

					</div>
				</div>
			</div>

		<%
		}
		%>

	</div>
</div>