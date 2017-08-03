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

<%@ include file="/designer/init.jsp" %>

<%
PortletURL portletURL = kaleoDesignerDisplayContext.getBasePortletURL();

portletURL.setParameter("mvcPath", "/designer/view.jsp");
%>

<liferay-frontend:management-bar
	includeCheckBox="<%= false %>"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-display-buttons
			displayViews="<%= kaleoDesignerDisplayContext.getKaleoDraftDefinitionDisplayViews() %>"
			portletURL="<%= portletURL %>"
			selectedDisplayStyle="<%= kaleoDesignerDisplayContext.getKaleoDraftDefinitionDisplayStyle() %>"
		/>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>
		<liferay-frontend:management-bar-navigation
			navigationKeys='<%= new String[] {"all"} %>'
			portletURL="<%= portletURL %>"
		/>

		<liferay-frontend:management-bar-sort
			orderByCol="<%= kaleoDesignerDisplayContext.getOrderByCol() %>"
			orderByType="<%= kaleoDesignerDisplayContext.getOrderByType() %>"
			orderColumns="<%= kaleoDesignerDisplayContext.getOrderColumns() %>"
			portletURL="<%= portletURL %>"
		/>
	</liferay-frontend:management-bar-filters>
</liferay-frontend:management-bar>