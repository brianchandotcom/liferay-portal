<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String tabs1 = ParamUtil.getString(renderRequest, "tabs1", "assigned-to-me");
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
						navigationItem.setLabel(LanguageUtil.get(httpServletRequest, "assigned-to-me"));
					});

				add(
					navigationItem -> {
						navigationItem.setActive(tabs1.equals("assigned-to-my-roles"));
						navigationItem.setHref(renderResponse.createRenderURL(), "mvcPath", "/view.jsp", "tabs1", "assigned-to-my-roles");
						navigationItem.setLabel(LanguageUtil.get(httpServletRequest, "assigned-to-my-roles"));
					});
			}
		}
	%>'
/>

<clay:management-toolbar
	clearResultsURL="<%= workflowTaskDisplayContext.getClearResultsURL() %>"
	filterDropdownItems="<%= workflowTaskDisplayContext.getFilterOptions() %>"
	itemsTotal="<%= workflowTaskDisplayContext.getTotalItems() %>"
	searchActionURL="<%= workflowTaskDisplayContext.getSearchURL() %>"
	searchContainerId="workflowTasks"
	searchFormName="fm1"
	selectable="<%= false %>"
	sortingOrder="<%= workflowTaskDisplayContext.getOrderByType() %>"
	sortingURL="<%= workflowTaskDisplayContext.getSortingURL() %>"
	viewTypeItems="<%= workflowTaskDisplayContext.getViewTypes() %>"
/>