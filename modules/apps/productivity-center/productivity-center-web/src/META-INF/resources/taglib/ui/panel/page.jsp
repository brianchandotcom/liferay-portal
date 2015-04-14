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

<%@ include file="/taglib/ui/panel/init.jsp" %>

<%
PanelCategory panelCategory = (PanelCategory)request.getAttribute("productivity-center-ui:panel:panelCategory");
PanelCategoryRegistry panelCategoryRegistry = (PanelCategoryRegistry)request.getAttribute(ProductivityCenterWebKeys.PANEL_CATEGORY_REGISTRY);
%>

<liferay-ui:panel-container
	accordion="<%= true %>"
	extended="<%= true %>"
	id="userPersonalPanelMenuAddContentPanelContainer"
	persistState="<%= true %>"
>

	<%
	for (PanelCategory childPanelCategory : panelCategoryRegistry.getPanelCategories(panelCategory)) {
	%>

		<productivity-center-ui:panel-category panelCategory="<%= childPanelCategory %>" servletContext="<%= application %>" />

	<%
	}
	%>

</liferay-ui:panel-container>