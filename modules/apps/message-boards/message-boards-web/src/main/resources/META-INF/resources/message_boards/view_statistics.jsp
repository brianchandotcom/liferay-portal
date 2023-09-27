<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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