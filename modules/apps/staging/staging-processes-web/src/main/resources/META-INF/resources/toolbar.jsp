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
String mvcRenderCommandName = ParamUtil.getString(request, "mvcRenderCommandName");

String tabs1 = ParamUtil.getString(request, "tabs1");

String navigation = ParamUtil.getString(request, "navigation", "all");
String orderByCol = ParamUtil.getString(request, "orderByCol");
String orderByType = ParamUtil.getString(request, "orderByType");
String searchContainerId = ParamUtil.getString(request, "searchContainerId");

boolean hasPublishStagingPermission = GroupPermissionUtil.contains(permissionChecker, scopeGroupId, ActionKeys.PUBLISH_STAGING);

PortletURL portletURL = liferayPortletResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", mvcRenderCommandName);
portletURL.setParameter("tabs1", tabs1);
portletURL.setParameter("displayStyle", stagingProcessesWebToolbarDisplayContext.getDisplayStyle());
portletURL.setParameter("navigation", navigation);
portletURL.setParameter("orderByCol", orderByCol);
portletURL.setParameter("orderByType", orderByType);
portletURL.setParameter("searchContainerId", String.valueOf(searchContainerId));
%>

<clay:management-toolbar-v2
	actionDropdownItems="<%= stagingProcessesWebToolbarDisplayContext.getActionDropdownItems(hasPublishStagingPermission) %>"
	creationMenu="<%= stagingProcessesWebToolbarDisplayContext.getCreationMenu(hasPublishStagingPermission) %>"
	filterDropdownItems="<%= stagingProcessesWebToolbarDisplayContext.getFilterDropdownItems() %>"
	searchContainerId="<%= searchContainerId %>"
	selectable="<%= hasPublishStagingPermission %>"
	showCreationMenu='<%= tabs1.equals("processes") && hasPublishStagingPermission %>'
	showSearch="<%= false %>"
	sortingOrder="<%= stagingProcessesWebToolbarDisplayContext.getSortingOrder() %>"
	sortingURL="<%= stagingProcessesWebToolbarDisplayContext.getSortingURL() %>"
	viewTypeItems="<%= stagingProcessesWebToolbarDisplayContext.getViewTypeItems() %>"
/>