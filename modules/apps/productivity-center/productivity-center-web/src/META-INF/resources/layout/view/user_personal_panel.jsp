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

<%@ include file="/layout/view/init.jsp" %>

<c:choose>
	<c:when test="<%= themeDisplay.isStatePopUp() %>">
		<productivity-center-ui:panel-content portletId="<%= themeDisplay.getPpid() %>" servletContext="<%= application %>" />
	</c:when>
	<c:otherwise>
		<aui:container cssClass="panel-manage-frontpage">
			<aui:row>
				<aui:col cssClass="panel-page-menu" width="<%= 25 %>">
					<liferay-portlet:runtime portletName="<%= ProductivityCenterPortletKeys.PRODUCTIVITY_CENTER %>" />
				</aui:col>
				<aui:col width="<%= 75 %>">
					<productivity-center-ui:panel-content portletId="<%= themeDisplay.getPpid() %>" servletContext="<%= application %>" />
				</aui:col>
			</aui:row>
		</aui:container>
	</c:otherwise>
</c:choose>