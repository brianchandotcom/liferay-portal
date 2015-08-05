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

<%@ include file="/panel_category/init.jsp" %>

<%
PanelAppRegistry panelAppRegistry = (PanelAppRegistry)request.getAttribute(ApplicationListWebKeys.PANEL_APP_REGISTRY);
PanelCategory panelCategory = (PanelCategory)request.getAttribute("liferay-application-list:panel-category:panelCategory");
PanelCategoryRegistry panelCategoryRegistry = (PanelCategoryRegistry)request.getAttribute(ApplicationListWebKeys.PANEL_CATEGORY_REGISTRY);

List<PanelApp> panelApps = panelAppRegistry.getPanelApps(panelCategory, permissionChecker, themeDisplay.getScopeGroup());
%>

<c:if test="<%= !panelApps.isEmpty() %>">

	<%
	String panelPageCategoryId = "panel-manage-" + StringUtil.replace(panelCategory.getKey(), StringPool.PERIOD, StringPool.UNDERLINE);
	%>

	<a aria-expanded="false" class="collapse-icon collapsed list-group-heading" data-toggle="collapse" href="#<%= panelPageCategoryId %>">
		<h5><%= panelCategory.getLabel(themeDisplay.getLocale()) %></h5>
	</a>

	<%
	PanelCategoryHelper panelCategoryHelper = new PanelCategoryHelper(panelAppRegistry, panelCategoryRegistry);
	%>

	<div class="collapse <%= panelCategoryHelper.containsPortlet(themeDisplay.getPpid(), panelCategory) ? "in" : StringPool.BLANK %>" id="<%= panelPageCategoryId %>">
		<div class="list-group-item">
			<ul aria-labelledby="<%= panelPageCategoryId %>" class="category-portlets list-unstyled" role="menu">

				<%
				for (PanelApp panelApp : panelApps) {
				%>

					<liferay-application-list:panel-app
						panelApp="<%= panelApp %>"
						panelCategory="<%= panelCategory %>"
					/>

				<%
				}
				%>

			</ul>
		</div>
	</div>
</c:if>