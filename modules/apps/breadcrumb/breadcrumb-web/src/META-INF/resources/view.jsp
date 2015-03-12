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

if (breadcrumbDisplayContext.isShowCurrentGroup()) {
	breadcrumbEntryTypes.add(BreadcrumbUtil.ENTRY_TYPE_CURRENT_GROUP);
}

if (breadcrumbDisplayContext.isShowGuestGroup()) {
	breadcrumbEntryTypes.add(BreadcrumbUtil.ENTRY_TYPE_GUEST_GROUP);
}

if (breadcrumbDisplayContext.isShowLayout()) {
	breadcrumbEntryTypes.add(BreadcrumbUtil.ENTRY_TYPE_LAYOUT);
}

if (breadcrumbDisplayContext.isShowParentGroups()) {
	breadcrumbEntryTypes.add(BreadcrumbUtil.ENTRY_TYPE_PARENT_GROUP);
}

if (breadcrumbDisplayContext.isShowPortletBreadcrumb()) {
	breadcrumbEntryTypes.add(BreadcrumbUtil.ENTRY_TYPE_PORTLET);
}

List<BreadcrumbEntry> breadcrumbEntries = BreadcrumbUtil.getBreadcrumbEntries(request, ArrayUtil.toIntArray(breadcrumbEntryTypes));
%>

<liferay-ui:ddm-template-renderer displayStyle="<%= breadcrumbDisplayContext.getDisplayStyle() %>" displayStyleGroupId="<%= breadcrumbDisplayContext.getDisplayStyleGroupId() %>" entries="<%= breadcrumbEntries %>">
	<liferay-ui:breadcrumb
		displayStyle="<%= breadcrumbDisplayContext.getDisplayStyle() %>"
		showCurrentGroup="<%= breadcrumbDisplayContext.isShowCurrentGroup() %>"
		showGuestGroup="<%= breadcrumbDisplayContext.isShowGuestGroup() %>"
		showLayout="<%= breadcrumbDisplayContext.isShowLayout( %>"
		showParentGroups="<%= breadcrumbDisplayContext.isShowParentGroups() %>"
		showPortletBreadcrumb="<%= breadcrumbDisplayContext.isShowPortletBreadcrumb() %>"
	/>
</liferay-ui:ddm-template-renderer>