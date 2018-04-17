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
FragmentEntryLinkDisplayContext fragmentEntryLinkDisplayContext = new FragmentEntryLinkDisplayContext(renderRequest, renderResponse);

FragmentEntry fragmentEntry = fragmentEntryLinkDisplayContext.getFragmentEntry();

StringBundler titleSB = new StringBundler();

titleSB.append(LanguageUtil.get(request, "usages-and-propagation"));
titleSB.append(StringPool.SPACE);
titleSB.append(StringPool.OPEN_PARENTHESIS);
titleSB.append(fragmentEntry.getName());
titleSB.append(StringPool.CLOSE_PARENTHESIS);

portletDisplay.setTitle(titleSB.toString());
portletDisplay.setURLBack(ParamUtil.getString(request, "redirect"));
portletDisplay.setShowBackIcon(true);
%>

<div class="container-fluid-1280 d-flex mt-3">
	<div class="col-md-2">
		<ul class="nav nav-nested">
			<li class="nav-item">
				<span class="text-uppercase">
					<liferay-ui:message key="site-pages" />
				</span>

				<ul class="nav nav-stacked">
					<li>

						<%
						PortletURL allNavigationURL = currentURLObj;

						allNavigationURL.setParameter("navigation", "all");
						%>

						<aui:a cssClass="nav-link" href="<%= allNavigationURL.toString() %>">
							<liferay-ui:message key="all" />
							&nbsp;(<%= fragmentEntryLinkDisplayContext.getUsageCount("all") %>)
						</aui:a>
					</li>
					<li>

						<%
						PortletURL pagesNavigationURL = currentURLObj;

						pagesNavigationURL.setParameter("navigation", "pages");
						%>

						<aui:a cssClass="nav-link" href="<%= pagesNavigationURL.toString() %>">
							<liferay-ui:message key="pages" />
							&nbsp;(<%= fragmentEntryLinkDisplayContext.getUsageCount("pages") %>)
						</aui:a>
					</li>
					<li>

						<%
						PortletURL pageTemplatesNavigationURL = currentURLObj;

						pageTemplatesNavigationURL.setParameter("navigation", "page-templates");
						%>

						<aui:a cssClass="nav-link" href="<%= pageTemplatesNavigationURL.toString() %>">
							<liferay-ui:message key="page-templates" />
							&nbsp;(<%= fragmentEntryLinkDisplayContext.getUsageCount("page-templates") %>)
						</aui:a>
					</li>
				</ul>
			</li>
		</ul>
	</div>

	<div class="card col-md-9 pt-2">
		<h3>
			<liferay-ui:message key="<%= fragmentEntryLinkDisplayContext.getNavigation() %>" />&nbsp;(<%= fragmentEntryLinkDisplayContext.getUsageCount(fragmentEntryLinkDisplayContext.getNavigation()) %>)
		</h3>

		<clay:management-toolbar
			actionItems="<%= fragmentEntryLinkDisplayContext.getActionItemsDropdownItemList() %>"
			clearResultsURL="<%= currentURL %>"
			componentId="fragmentEntryLinksManagementToolbar"
			disabled="<%= fragmentEntry.getUsageCount() > 0 %>"
			namespace="<%= renderResponse.getNamespace() %>"
			searchActionURL="<%= currentURL %>"
			searchContainerId="fragmentEntryLinks"
			searchFormName="searchFm"
			showCreationMenu="<%= false %>"
			showSearch="<%= true %>"
			sortingOrder="<%= fragmentEntryLinkDisplayContext.getOrderByType() %>"
			sortingURL="<%= currentURL %>"
			totalItems="<%= fragmentEntry.getUsageCount() %>"
		/>
	</div>
</div>