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

<%@ include file="/panel_category_body/init.jsp" %>

<c:if test="<%= !panelApps.isEmpty() %>">
	<ul aria-labelledby="<%= id %>" class="nav nav-equal-height nav-stacked" role="menu">

		<%
		for (PanelApp panelApp : panelApps) {
		%>

			<c:if test="<%= !panelApp.include(request, response) %>">

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
							id='<%= "portlet_" + panelApp.getPortletId() %>'
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

		<%
		}
		%>

	</ul>
</c:if>

<liferay-application-list:panel
	panelCategory="<%= panelCategory %>"
/>