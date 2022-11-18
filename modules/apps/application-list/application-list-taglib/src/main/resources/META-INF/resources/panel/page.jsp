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

<%@ include file="/panel/init.jsp" %>

<%
List<PanelCategory> childPanelCategories = (List<PanelCategory>)request.getAttribute("liferay-application-list:panel:childPanelCategories");
PanelCategory panelCategory = (PanelCategory)request.getAttribute("liferay-application-list:panel:panelCategory");
%>

<c:if test="<%= !childPanelCategories.isEmpty() %>">
	<div class="list-group">

		<%
		for (PanelCategory childPanelCategory : childPanelCategories) {
			boolean include = childPanelCategory.include(request, PipingServletResponseFactory.createPipingServletResponse(pageContext));
		%>

			<c:if test="<%= !include %>">
				<liferay-application-list:panel-category
					panelCategory="<%= childPanelCategory %>"
					showOpen="<%= childPanelCategories.size() == 1 %>"
				/>
			</c:if>

		<%
		}
		%>

	</div>

	<%
	PanelAppRegistry panelAppRegistry = (PanelAppRegistry)request.getAttribute(ApplicationListWebKeys.PANEL_APP_REGISTRY);

	for (PanelApp panelApp : panelAppRegistry.getPanelApps(panelCategory.getKey())) {
	%>

		<c:if test="<%= panelApp.isShow(permissionChecker, themeDisplay.getScopeGroup()) %>">
			<div class="list-group">
				<div class="list-group-heading panel-app-root panel-header <%= Objects.equals(themeDisplay.getPpid(), panelApp.getPortletId()) ? "active" : StringPool.BLANK %>">

					<%
					boolean include = panelApp.include(request, response);
					%>

					<c:if test="<%= !include %>">

						<%
						String url = PanelAppUtil.getURL(request, panelApp);
						%>

						<c:if test="<%= Validator.isNotNull(url) %>">

							<%
							String label = PanelAppUtil.getLabel(request, panelApp);
							%>

							<li class="<%= PanelAppUtil.isActive(request, panelApp) ? "active" : StringPool.BLANK %> nav-item" role="presentation">
								<aui:a
									ariaRole="menuitem"
									cssClass="nav-link"
									data='<%=
										HashMapBuilder.<String, Object>put(
											"qa-id", "app"
										).put(
											"title", label
										).build()
									%>'
									href="<%= url %>"
									id="portlet_<%= panelApp.getPortletId() %>"
								>
									<%= label %>

									<c:if test="<%= panelApp.getNotificationsCount(user) > 0 %>">
										<clay:badge
											cssClass="float-right"
											displayType="danger"
											label="<%= String.valueOf(panelApp.getNotificationsCount(user)) %>"
										/>
									</c:if>
								</aui:a>
							</li>
						</c:if>
					</c:if>
				</div>
			</div>
		</c:if>

	<%
	}
	%>

</c:if>