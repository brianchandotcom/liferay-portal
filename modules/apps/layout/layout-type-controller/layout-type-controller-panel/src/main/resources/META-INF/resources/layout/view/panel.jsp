<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/layout/init.jsp" %>

<c:choose>
	<c:when test="<%= !themeDisplay.isStatePopUp() %>">
		<clay:container-fluid
			cssClass="mt-3"
			id="main-content"
		>
			<clay:row>

				<%
				String panelBodyCssClass = "panel-page-body";

				if (!layoutTypePortlet.hasStateMax()) {
					panelBodyCssClass += " panel-page-frontpage";
				}
				else {
					panelBodyCssClass += " panel-page-application";
				}
				%>

				<clay:col
					cssClass="panel-page-menu"
					md="3"
				>

					<%
					PortletCategory portletCategory = (PortletCategory)WebAppPool.get(company.getCompanyId(), WebKeys.PORTLET_CATEGORY);

					portletCategory = PortletCategoryUtil.getRelevantPortletCategory(permissionChecker, user.getCompanyId(), layout, portletCategory, layoutTypePortlet);

					List<PortletCategory> portletCategories = ListUtil.fromCollection(portletCategory.getCategories());

					portletCategories = ListUtil.sort(portletCategories, new PortletCategoryComparator(locale));

					for (PortletCategory curPortletCategory : portletCategories) {
					%>

						<c:if test="<%= !curPortletCategory.isHidden() %>">

							<%
							request.setAttribute(WebKeys.PORTLET_CATEGORY, curPortletCategory);
							%>

							<liferay-util:include page="/layout/view/view_category.jsp" servletContext="<%= application %>" />
						</c:if>

					<%
					}
					%>

				</clay:col>

				<clay:col
					cssClass="<%= panelBodyCssClass %>"
					md="9"
				>
					<%@ include file="/layout/view/panel_description.jspf" %>
				</clay:col>
			</clay:row>
		</clay:container-fluid>
	</c:when>
	<c:otherwise>
		<%@ include file="/layout/view/panel_description.jspf" %>
	</c:otherwise>
</c:choose>

<liferay-layout:layout-common />