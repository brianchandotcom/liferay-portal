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
ContentDashboardAdminInfoPanelDisplayContext contentDashboardAdminInfoPanelDisplayContext = (ContentDashboardAdminInfoPanelDisplayContext)request.getAttribute(ContentDashboardWebKeys.CONTENT_DASHBOARD_ADMIN_INFO_PANEL_DISPLAY_CONTEXT);
%>

<div class="sidebar-header">
	<h4 class="sidebar-title"><liferay-ui:message key="content" /></h4>
</div>

<liferay-ui:tabs
	cssClass="navbar-no-collapse"
	names="details"
	refresh="<%= false %>"
	type="dropdown"
>
	<liferay-ui:section>
		<div class="sidebar-body">
			<dl class="sidebar-block">
				<dt class="sidebar-dt">
					<liferay-ui:message key="num-of-items" />
				</dt>
				<dd class="sidebar-dd">
					<%= contentDashboardAdminInfoPanelDisplayContext.getContentDashboardItemsCount() %>
				</dd>
			</dl>
		</div>
	</liferay-ui:section>
</liferay-ui:tabs>