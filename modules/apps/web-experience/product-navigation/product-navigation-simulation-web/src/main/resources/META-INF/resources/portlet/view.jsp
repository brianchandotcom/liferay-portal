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

<%@ include file="/portlet/init.jsp" %>

<%
PanelCategoryRegistry panelCategoryRegistry = (PanelCategoryRegistry)request.getAttribute(ApplicationListWebKeys.PANEL_CATEGORY_REGISTRY);
PanelAppRegistry panelAppRegistry = (PanelAppRegistry)request.getAttribute(ApplicationListWebKeys.PANEL_APP_REGISTRY);

PanelCategoryHelper panelCategoryHelper = new PanelCategoryHelper(panelAppRegistry, panelCategoryRegistry);

PanelCategory panelCategory = panelCategoryRegistry.getPanelCategory(SimulationPanelCategory.SIMULATION);
%>

<div class="simulation-menu" data-qa-id="simulationMenuBody" id="<portlet:namespace />simulationPanelContainer">
	<div id="<portlet:namespace />simulationCategoriesContainer">
		<div aria-multiselectable="true" class="panel-group" id="<portlet:namespace />SimulationAccordion" role="tablist">

			<%
			for (PanelApp panelApp : panelCategoryHelper.getAllPanelApps(panelCategory.getKey())) {
				String panelAppKey = panelApp.getKey().replaceAll("[\\W]", "_");
			%>

				<div class="panel">
					<div class="panel-heading" id="<%= renderResponse.getNamespace() + panelAppKey + "Header" %>" role="tab">
						<div class="panel-title">
							<div aria-controls="<%= "#" + renderResponse.getNamespace() + panelAppKey + "Collapse" %>" aria-expanded="<%= true %>" class="collapse-icon collapse-icon-middle collapsed panel-toggler" data-parent="#<portlet:namespace />SimulationAccordion" data-toggle="collapse" href="<%= "#" + renderResponse.getNamespace() + panelAppKey + "Collapse" %>" role="button">
								<span class="category-name truncate-text"><%= panelApp.getLabel(locale) %></span>

								<aui:icon cssClass="collapse-icon-closed" image="angle-right" markupView="lexicon" />

								<aui:icon cssClass="collapse-icon-open" image="angle-down" markupView="lexicon" />
							</div>
						</div>
					</div>

					<div aria-expanded="<%= true %>" aria-labelledby="<%= renderResponse.getNamespace() + panelAppKey + "Header" %>" class="collapse in panel-collapse" id="<%= renderResponse.getNamespace() + panelAppKey + "Collapse" %>" role="tabpanel">
						<div class="simulation-app-panel-body">
							<liferay-application-list:panel-app label="" panelApp="<%= panelApp %>" />
						</div>
					</div>
				</div>

			<%
			}
			%>

		</div>
	</div>
</div>