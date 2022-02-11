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

<%@ include file="/message_boards/init.jsp" %>

<%
MBViewStatisticsDisplayContext mbViewStatisticsDisplayContext = new MBViewStatisticsDisplayContext(renderRequest, renderResponse);

MBCategoryDisplay categoryDisplay = mbViewStatisticsDisplayContext.getMBCategoryDisplay();
PortletURL portletURL = mbViewStatisticsDisplayContext.getPortletURL();
%>

<%@ include file="/message_boards/nav.jspf" %>

<c:choose>
	<c:when test="<%= mbViewStatisticsDisplayContext.isMBAdmin() %>">
		<clay:container-fluid>
			<%@ include file="/message_boards/view_statistics_panel.jspf" %>
		</clay:container-fluid>
	</c:when>
	<c:otherwise>
		<div class="main-content-body mt-4">
			<h3><liferay-ui:message key="statistics" /></h3>

			<%@ include file="/message_boards/view_statistics_panel.jspf" %>
		</div>
	</c:otherwise>
</c:choose>

<%
PortalUtil.setPageSubtitle(LanguageUtil.get(request, "statistics"), request);
PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, TextFormatter.format("statistics", TextFormatter.O)), portletURL.toString());
%>