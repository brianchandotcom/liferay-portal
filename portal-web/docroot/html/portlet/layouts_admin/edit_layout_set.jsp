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

<%@ include file="/html/portlet/layouts_admin/init.jsp" %>

<%
Group selGroup = (Group)request.getAttribute(WebKeys.GROUP);

Group group = layoutsAdminDisplayContext.getGroup();
Group liveGroup = layoutsAdminDisplayContext.getLiveGroup();
long groupId = layoutsAdminDisplayContext.getGroupId();
long liveGroupId = layoutsAdminDisplayContext.getLiveGroupId();
boolean privateLayout = layoutsAdminDisplayContext.isPrivateLayout();
LayoutSet selLayoutSet = layoutsAdminDisplayContext.getSelLayoutSet();

PortletURL redirectURL = layoutsAdminDisplayContext.getRedirectURL();

int pagesCount = 0;

if (selGroup.isLayoutSetPrototype()) {
	privateLayout = true;
}

if (privateLayout) {
	if (group != null) {
		pagesCount = group.getPrivateLayoutsPageCount();
	}
}
else {
	if (group != null) {
		pagesCount = group.getPublicLayoutsPageCount();
	}
}
%>

<aui:nav-bar>
	<aui:nav cssClass="navbar-nav">
		<c:if test="<%= pagesCount > 0 %>">
			<aui:nav-item href="<%= group.getDisplayURL(themeDisplay, privateLayout) %>" iconCssClass="icon-file" label="view-pages" target="_blank" />
		</c:if>
		<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, group, ActionKeys.ADD_LAYOUT) %>">
			<portlet:renderURL var="addPagesURL">
				<portlet:param name="mvcPath" value="/html/portlet/layouts_admin/add_layout.jsp" />
				<portlet:param name="tabs1" value="<%= layoutsAdminDisplayContext.getTabs1() %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
				<portlet:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
			</portlet:renderURL>

			<aui:nav-item href="<%= addPagesURL %>" iconCssClass="icon-plus" label="add-page" />
		</c:if>
	</aui:nav>
</aui:nav-bar>

<portlet:actionURL name="editLayoutSet" var="editLayoutSetURL">
	<portlet:param name="mvcPath" value="/html/portlet/layouts_admin/view.jsp" />
</portlet:actionURL>

<aui:form action="<%= editLayoutSetURL %>" cssClass="edit-layoutset-form" enctype="multipart/form-data" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= redirectURL.toString() %>" />
	<aui:input name="groupId" type="hidden" value="<%= groupId %>" />
	<aui:input name="liveGroupId" type="hidden" value="<%= liveGroupId %>" />
	<aui:input name="stagingGroupId" type="hidden" value="<%= layoutsAdminDisplayContext.getStagingGroupId() %>" />
	<aui:input name="selPlid" type="hidden" value="<%= layoutsAdminDisplayContext.getSelPlid() %>" />
	<aui:input name="privateLayout" type="hidden" value="<%= privateLayout %>" />
	<aui:input name="layoutSetId" type="hidden" value="<%= selLayoutSet.getLayoutSetId() %>" />
	<aui:input name="<%= PortletDataHandlerKeys.SELECTED_LAYOUTS %>" type="hidden" />

	<liferay-ui:form-navigator
		formModelBean="<%= selLayoutSet %>"
		id="<%= FormNavigatorConstants.FORM_NAVIGATOR_ID_LAYOUT_SET %>"
		showButtons="<%= GroupPermissionUtil.contains(permissionChecker, group, ActionKeys.MANAGE_LAYOUTS) && SitesUtil.isLayoutSetPrototypeUpdateable(selLayoutSet) %>"
	/>
</aui:form>