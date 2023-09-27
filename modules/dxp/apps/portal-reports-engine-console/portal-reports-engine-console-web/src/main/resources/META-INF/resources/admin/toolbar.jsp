<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String tabs1 = ParamUtil.getString(renderRequest, "tabs1", "reports");
%>

<c:if test="<%= reportsEngineDisplayContext.isAdminPortlet() %>">
	<clay:navigation-bar
		inverted="<%= layout.isTypeControlPanel() %>"
		navigationItems='<%=
			new JSPNavigationItemList(pageContext) {
				{
					add(
						navigationItem -> {
							navigationItem.setActive(tabs1.equals("reports"));
							navigationItem.setHref(renderResponse.createRenderURL(), "mvcPath", "/admin/view.jsp", "tabs1", "reports");
							navigationItem.setLabel(LanguageUtil.get(httpServletRequest, "reports"));
						});

					if (hasAddDefinitionPermission) {
						add(
							navigationItem -> {
								navigationItem.setActive(tabs1.equals("definitions"));
								navigationItem.setHref(renderResponse.createRenderURL(), "mvcPath", "/admin/view.jsp", "tabs1", "definitions");
								navigationItem.setLabel(LanguageUtil.get(httpServletRequest, "definitions"));
							});
					}

					if (hasAddSourcePermission) {
						add(
							navigationItem -> {
								navigationItem.setActive(tabs1.equals("sources"));
								navigationItem.setHref(renderResponse.createRenderURL(), "mvcPath", "/admin/view.jsp", "tabs1", "sources");
								navigationItem.setLabel(LanguageUtil.get(httpServletRequest, "sources"));
							});
					}
				}
			}
		%>'
	/>
</c:if>

<clay:management-toolbar
	clearResultsURL="<%= reportsEngineDisplayContext.getClearResultsURL() %>"
	creationMenu="<%= reportsEngineDisplayContext.getCreationMenu() %>"
	disabled="<%= reportsEngineDisplayContext.isDisabled() %>"
	filterDropdownItems="<%= reportsEngineDisplayContext.getFilterOptions() %>"
	itemsTotal="<%= reportsEngineDisplayContext.getTotalItems() %>"
	searchActionURL="<%= reportsEngineDisplayContext.getSearchURL() %>"
	searchContainerId="reportsEngine"
	searchFormName="fm1"
	selectable="<%= false %>"
	sortingOrder="<%= reportsEngineDisplayContext.getOrderByType() %>"
	sortingURL="<%= reportsEngineDisplayContext.getSortingURL() %>"
	viewTypeItems="<%= reportsEngineDisplayContext.getViewTypes() %>"
/>