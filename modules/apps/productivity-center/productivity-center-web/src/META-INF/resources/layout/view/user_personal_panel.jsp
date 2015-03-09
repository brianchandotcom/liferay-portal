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

<%@ include file="/layout/view/init.jsp" %>

<%
String portletId = ParamUtil.getString(request, "p_p_id");

PanelCategoryRegistry panelCategoryRegistry = (PanelCategoryRegistry)request.getAttribute(ProductivityCenterWebKeys.PANEL_CATEGORY_REGISTRY);
PanelCategory panelCategory = panelCategoryRegistry.getPanelCategory(PanelCategoryKeys.USER_PERSONAL_PANEL);
%>

<aui:container>
	<aui:row>
		<aui:col width="<%= 25 %>">
			<productivity-center-ui:panel panelCategory="<%= panelCategory %>" servletContext="<%= application %>" />
		</aui:col>
		<aui:col width="<%= 75 %>">
			<productivity-center-ui:panel-content portletId="<%= portletId %>" servletContext="<%= application %>" />
		</aui:col>
	</aui:row>
</aui:container>