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

<%@ include file="/announcements/init.jsp" %>

<c:if test="<%= announcementsDisplayContext.isTabs1Visible() %>">
	<liferay-ui:tabs
		names="<%= announcementsDisplayContext.getTabs1Names() %>"
		url="<%= announcementsDisplayContext.getTabs1PortletURL() %>"
	/>
</c:if>

<c:if test="<%= PortletPermissionUtil.hasControlPanelAccessPermission(permissionChecker, scopeGroupId, AnnouncementsPortletKeys.ANNOUNCEMENTS_ADMIN) %>">
	<div class="btn-group c-mb-4">
		<portlet:renderURL var="addEntryURL">
			<portlet:param name="mvcRenderCommandName" value="/announcements/edit_entry" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="alert" value="<%= String.valueOf(portletName.equals(AnnouncementsPortletKeys.ALERTS)) %>" />
		</portlet:renderURL>

		<div class="btn-group-item">
			<aui:button href="<%= addEntryURL %>" icon="icon-plus" value='<%= portletName.equals(AnnouncementsPortletKeys.ALERTS) ? "add-alert" : "add-announcement" %>' />
		</div>
	</div>
</c:if>

<%@ include file="/announcements/view_entries.jspf" %>