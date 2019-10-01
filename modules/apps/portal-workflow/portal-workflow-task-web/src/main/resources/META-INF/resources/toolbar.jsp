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

<%@ include file="/init.jsp" %>

<%
String tabs1 = ParamUtil.getString(renderRequest, "tabs1", "assigned-to-me");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/view.jsp");
portletURL.setParameter("tabs1", tabs1);
%>

<clay:navigation-bar
	inverted="<%= layout.isTypeControlPanel() %>"
	navigationItems='<%=
		new JSPNavigationItemList(pageContext) {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(tabs1.equals("assigned-to-me"));
						navigationItem.setHref(renderResponse.createRenderURL(), "mvcPath", "/view.jsp", "tabs1", "assigned-to-me");
						navigationItem.setLabel(LanguageUtil.get(request, "assigned-to-me"));
					});

				add(
					navigationItem -> {
						navigationItem.setActive(tabs1.equals("assigned-to-my-roles"));
						navigationItem.setHref(renderResponse.createRenderURL(), "mvcPath", "/view.jsp", "tabs1", "assigned-to-my-roles");
						navigationItem.setLabel(LanguageUtil.get(request, "assigned-to-my-roles"));
					});
			}
		}
	%>'
/>

<clay:management-toolbar
	clearResultsURL="<%= workflowTaskDisplayContext.getClearResultsURL() %>"
	filterDropdownItems="<%= workflowTaskDisplayContext.getFilterOptions() %>"
	itemsTotal="<%= workflowTaskDisplayContext.getTotalItems() %>"
	namespace="<%= renderResponse.getNamespace() %>"
	searchActionURL="<%= workflowTaskDisplayContext.getSearchURL() %>"
	searchContainerId="workflowTasks"
	searchFormName="fm1"
	selectable="<%= false %>"
	sortingOrder="<%= workflowTaskDisplayContext.getOrderByType() %>"
	sortingURL="<%= workflowTaskDisplayContext.getSortingURL() %>"
	viewTypeItems="<%= workflowTaskDisplayContext.getViewTypes() %>"
/>