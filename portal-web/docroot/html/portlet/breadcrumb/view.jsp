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

<%@ include file="/html/portlet/breadcrumb/init.jsp" %>

<%
long portletDisplayTemplateId = PortletDisplayTemplateUtil.getPortletDisplayTemplateDDMTemplateId(displayStyleGroupId, displayStyle);
%>

<c:choose>
	<c:when test="<%= portletDisplayTemplateId > 0 %>">
		<%= PortletDisplayTemplateUtil.renderDDMTemplate(pageContext, portletDisplayTemplateId, BreadcrumbUtil.getEntries(request, 0)) %>
	</c:when>
	<c:otherwise>
		<liferay-ui:breadcrumb
			displayStyle="<%= displayStyle %>"
			showCurrentGroup="<%= showCurrentGroup %>"
			showCurrentPortlet="<%= showCurrentPortlet %>"
			showGuestGroup="<%= showGuestGroup %>"
			showLayout="<%= showLayout %>"
			showParentGroups="<%= showParentGroups %>"
			showPortletBreadcrumb="<%= showPortletBreadcrumb %>"
		/>
	</c:otherwise>
</c:choose>