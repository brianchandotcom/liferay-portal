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
List<Integer> breadcrumbEntryTypes = new ArrayList<Integer>();

if (showCurrentGroup) {
	breadcrumbEntryTypes.add(BreadcrumbUtil.ENTRY_TYPE_CURRENT_GROUP);
}

if (showGuestGroup) {
	breadcrumbEntryTypes.add(BreadcrumbUtil.ENTRY_TYPE_GUEST_GROUP);
}

if (showLayout) {
	breadcrumbEntryTypes.add(BreadcrumbUtil.ENTRY_TYPE_LAYOUT);
}

if (showParentGroups) {
	breadcrumbEntryTypes.add(BreadcrumbUtil.ENTRY_TYPE_PARENT_GROUP);
}

if (showPortletBreadcrumb) {
	breadcrumbEntryTypes.add(BreadcrumbUtil.ENTRY_TYPE_PORTLET);
}

List<BreadcrumbEntry> breadcrumbEntries = BreadcrumbUtil.getBreadcrumbEntries(request, ArrayUtil.toIntArray(breadcrumbEntryTypes));
%>

<liferay-ui:ddm-template-renderer displayStyle="<%= displayStyle %>" displayStyleGroupId="<%= displayStyleGroupId %>" entries="<%= breadcrumbEntries %>">
	<liferay-ui:breadcrumb
		displayStyle="<%= displayStyle %>"
		showCurrentGroup="<%= showCurrentGroup %>"
		showGuestGroup="<%= showGuestGroup %>"
		showLayout="<%= showLayout %>"
		showParentGroups="<%= showParentGroups %>"
		showPortletBreadcrumb="<%= showPortletBreadcrumb %>"
	/>
</liferay-ui:ddm-template-renderer>