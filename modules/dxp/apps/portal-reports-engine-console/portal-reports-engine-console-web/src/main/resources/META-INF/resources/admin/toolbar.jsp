<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
String tabs1 = ParamUtil.getString(renderRequest, "tabs1", "reports");

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/admin/view.jsp");
portletURL.setParameter("tabs1", tabs1);
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
	namespace="<%= liferayPortletResponse.getNamespace() %>"
	searchActionURL="<%= reportsEngineDisplayContext.getSearchURL() %>"
	searchContainerId="reportsEngine"
	searchFormName="fm1"
	selectable="<%= false %>"
	sortingOrder="<%= reportsEngineDisplayContext.getOrderByType() %>"
	sortingURL="<%= reportsEngineDisplayContext.getSortingURL() %>"
	viewTypeItems="<%= reportsEngineDisplayContext.getViewTypes() %>"
/>