<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
DepotAdminViewDepotDashboardDisplayContext depotAdminViewDepotDashboardDisplayContext = (DepotAdminViewDepotDashboardDisplayContext)request.getAttribute(DepotAdminViewDepotDashboardDisplayContext.class.getName());

boolean panelsShown = false;
%>

<clay:container-fluid
	cssClass="lfr-depot-dashboard-container"
>
	<liferay-site-navigation:breadcrumb
		breadcrumbEntries="<%= BreadcrumbEntriesUtil.getBreadcrumbEntries(request, true, false, false, true, true) %>"
	/>

	<%
	for (PanelCategory panelCategory : depotAdminViewDepotDashboardDisplayContext.getPanelCategories()) {
		Collection<PanelApp> panelApps = depotAdminViewDepotDashboardDisplayContext.getPanelApps(panelCategory);

		panelsShown = panelsShown || !panelApps.isEmpty();
	%>

		<c:if test="<%= !panelApps.isEmpty() %>">
			<div class="card-section-header">
				<%= panelCategory.getLabel(locale) %>
			</div>

			<ul class="card-page card-page-equal-height">

				<%
				for (PanelApp panelApp : panelApps) {
				%>

					<li class="card-page-item card-page-item-asset">
						<clay:navigation-card
							navigationCard="<%= depotAdminViewDepotDashboardDisplayContext.getDepotDashboardApplicationNavigationCard(panelApp, locale, !depotAdminViewDepotDashboardDisplayContext.isPrimaryPanelCategory(panelCategory)) %>"
						/>
					</li>

				<%
				}
				%>

			</ul>
		</c:if>

	<%
	}
	%>

	<c:if test="<%= !panelsShown %>">
		<clay:alert
			displayType="info"
			message="you-do-not-have-access-to-any-applications-in-this-asset-library"
		/>
	</c:if>
</clay:container-fluid>