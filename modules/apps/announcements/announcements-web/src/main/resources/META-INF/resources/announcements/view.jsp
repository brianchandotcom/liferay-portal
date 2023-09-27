<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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