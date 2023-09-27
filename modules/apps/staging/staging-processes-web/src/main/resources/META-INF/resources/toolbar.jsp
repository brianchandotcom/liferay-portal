<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String tabs1 = ParamUtil.getString(request, "tabs1");

String searchContainerId = ParamUtil.getString(request, "searchContainerId");

boolean hasPublishStagingPermission = GroupPermissionUtil.contains(permissionChecker, scopeGroupId, ActionKeys.PUBLISH_STAGING);
%>

<clay:management-toolbar
	actionDropdownItems="<%= stagingProcessesWebToolbarDisplayContext.getActionDropdownItems(hasPublishStagingPermission) %>"
	creationMenu="<%= stagingProcessesWebToolbarDisplayContext.getCreationMenu(hasPublishStagingPermission) %>"
	filterDropdownItems="<%= stagingProcessesWebToolbarDisplayContext.getFilterDropdownItems() %>"
	orderDropdownItems="<%= stagingProcessesWebToolbarDisplayContext.getOrderByDropDownItems() %>"
	propsTransformer="js/StagingProcessesWebToolbarPropsTransformer"
	searchContainerId="<%= searchContainerId %>"
	selectable="<%= hasPublishStagingPermission %>"
	showCreationMenu='<%= tabs1.equals("processes") && hasPublishStagingPermission %>'
	showSearch="<%= false %>"
	sortingOrder="<%= stagingProcessesWebToolbarDisplayContext.getSortingOrder() %>"
	sortingURL="<%= stagingProcessesWebToolbarDisplayContext.getSortingURL() %>"
	viewTypeItems="<%= stagingProcessesWebToolbarDisplayContext.getViewTypeItems() %>"
/>