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
		pagesCount = selGroup.getPrivateLayoutsPageCount();
	}
}
else {
	if (group != null) {
		pagesCount = selGroup.getPublicLayoutsPageCount();
	}
}
%>

<aui:nav-bar>
	<aui:nav cssClass="navbar-nav">
		<c:if test="<%= pagesCount > 0 %>">
			<aui:nav-item href="<%= selGroup.getDisplayURL(themeDisplay, privateLayout) %>" iconCssClass="icon-file" label="view-pages" target="_blank" />
		</c:if>
	</aui:nav>
</aui:nav-bar>

<portlet:actionURL name="editLayoutSet" var="editLayoutSetURL">
	<portlet:param name="mvcPath" value="/view.jsp" />
</portlet:actionURL>

<aui:form action="<%= editLayoutSetURL %>" cssClass="edit-layoutset-form" enctype="multipart/form-data" method="post" name="fm">
	<aui:input name="redirect" type="hidden" value="<%= redirectURL.toString() %>" />
	<aui:input name="groupId" type="hidden" value="<%= selGroup.getGroupId() %>" />
	<aui:input name="liveGroupId" type="hidden" value="<%= liveGroupId %>" />
	<aui:input name="stagingGroupId" type="hidden" value="<%= layoutsAdminDisplayContext.getStagingGroupId() %>" />
	<aui:input name="selPlid" type="hidden" value="<%= layoutsAdminDisplayContext.getSelPlid() %>" />
	<aui:input name="privateLayout" type="hidden" value="<%= privateLayout %>" />
	<aui:input name="layoutSetId" type="hidden" value="<%= selLayoutSet.getLayoutSetId() %>" />
	<aui:input name="<%= PortletDataHandlerKeys.SELECTED_LAYOUTS %>" type="hidden" />

	<liferay-ui:form-navigator
		formModelBean="<%= selLayoutSet %>"
		id="<%= FormNavigatorConstants.FORM_NAVIGATOR_ID_LAYOUT_SET %>"
		markupView="lexicon"
		showButtons="<%= GroupPermissionUtil.contains(permissionChecker, selGroup, ActionKeys.MANAGE_LAYOUTS) && SitesUtil.isLayoutSetPrototypeUpdateable(selLayoutSet) %>"
	/>
</aui:form>