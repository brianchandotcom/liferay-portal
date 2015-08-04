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

<%@ include file="/sites/init.jsp" %>

<%
PanelAppRegistry panelAppRegistry = (PanelAppRegistry)request.getAttribute(ApplicationListWebKeys.PANEL_APP_REGISTRY);
PanelCategory panelCategory = (PanelCategory)request.getAttribute(ApplicationListWebKeys.PANEL_CATEGORY);
PanelCategoryRegistry panelCategoryRegistry = (PanelCategoryRegistry)request.getAttribute(ApplicationListWebKeys.PANEL_CATEGORY_REGISTRY);

String sitesPanelCategoryKey = PanelCategoryKeys.SITE_ADMINISTRATION;

if (Validator.isNotNull(themeDisplay.getPpid())) {
	PanelCategoryHelper panelCategoryHelper = new PanelCategoryHelper(panelAppRegistry, panelCategoryRegistry);

	for (PanelCategory curPanelCategory : panelCategoryRegistry.getChildPanelCategories(panelCategory)) {
		if (panelCategoryHelper.containsPortlet(themeDisplay.getPpid(), curPanelCategory)) {
			sitesPanelCategoryKey = curPanelCategory.getKey();

			break;
		}
	}
}
%>

<ul class="hide nav nav-tabs">

	<%
	for (PanelCategory childPanelCategory : panelCategoryRegistry.getChildPanelCategories(panelCategory)) {
		if (!childPanelCategory.hasAccessPermission(permissionChecker, themeDisplay.getScopeGroup())) {
			continue;
		}
	%>

		<li class="col-xs-4 <%= sitesPanelCategoryKey.equals(childPanelCategory.getKey()) ? "active" : StringPool.BLANK %>">
			<a aria-expanded="true" data-toggle="tab" href="#<portlet:namespace /><%= childPanelCategory.getKey() %>" id="<portlet:namespace /><%= childPanelCategory.getKey() %>TabLink">
				<div class="product-menu-tab-text">
					<%= childPanelCategory.getLabel(locale) %>
				</div>
			</a>
		</li>

	<%
	}
	%>

</ul>

<div class="tab-content">

	<%
	for (PanelCategory childPanelCategory : panelCategoryRegistry.getChildPanelCategories(panelCategory)) {
	%>

		<div class="fade in tab-pane <%= sitesPanelCategoryKey.equals(childPanelCategory.getKey()) ? "active" : StringPool.BLANK %>" id="<portlet:namespace /><%= childPanelCategory.getKey() %>">
			<liferay-application-list:panel-content panelCategory="<%= childPanelCategory %>" />
		</div>

	<%
	}
	%>

</div>