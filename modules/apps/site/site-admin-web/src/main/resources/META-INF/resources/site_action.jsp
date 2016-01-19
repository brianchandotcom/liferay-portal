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
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Group group = null;

boolean organizationUser = false;
boolean userGroupUser = false;

if (row != null) {
	group = (Group)row.getObject();

	organizationUser = GetterUtil.getBoolean(row.getParameter("organizationUser"));
	userGroupUser = GetterUtil.getBoolean(row.getParameter("userGroupUser"));
}
else {
	group = (Group)request.getAttribute("view_entries.jspf-site");

	List<String> organizationNames = SitesUtil.getOrganizationNames(group, user);

	organizationUser = !organizationNames.isEmpty();

	List<String> userGroupNames = SitesUtil.getUserGroupNames(group, user);

	userGroupUser = !userGroupNames.isEmpty();
}

boolean hasUpdatePermission = GroupPermissionUtil.contains(permissionChecker, group, ActionKeys.UPDATE);
%>

<liferay-ui:icon-menu direction="left-side" icon="<%= StringPool.BLANK %>" markupView="lexicon" message="<%= StringPool.BLANK %>" showWhenSingleIcon="<%= true %>">

	<%
	PortletURL siteAdministrationURL = null;

	PanelCategoryHelper panelCategoryHelper = (PanelCategoryHelper)request.getAttribute(ApplicationListWebKeys.PANEL_CATEGORY_HELPER);

	String portletId = panelCategoryHelper.getFirstPortletId(PanelCategoryKeys.SITE_ADMINISTRATION, permissionChecker, group);

	if (Validator.isNotNull(portletId)) {
		siteAdministrationURL = PortalUtil.getControlPanelPortletURL(request, group, portletId, 0, 0, PortletRequest.RENDER_PHASE);
	}
	%>

	<c:if test="<%= siteAdministrationURL != null %>">
		<liferay-ui:icon
			message="site-administration"
			method="get"
			url="<%= siteAdministrationURL.toString() %>"
		/>
	</c:if>

	<c:if test="<%= hasUpdatePermission %>">

		<%
		int childSitesCount = GroupLocalServiceUtil.getGroupsCount(company.getCompanyId(), group.getGroupId(), true);
		%>

		<c:if test="<%= (childSitesCount > 0) && (row != null) %>">
			<liferay-portlet:renderURL var="viewSubsitesURL">
				<portlet:param name="backURL" value="<%= StringPool.SLASH + currentURL %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
			</liferay-portlet:renderURL>

			<liferay-ui:icon
				message="view-child-sites"
				url="<%= viewSubsitesURL %>"
			/>
		</c:if>

		<c:if test="<%= !group.isCompany() && (PortalPermissionUtil.contains(permissionChecker, ActionKeys.ADD_COMMUNITY) || GroupPermissionUtil.contains(permissionChecker, group, ActionKeys.ADD_COMMUNITY)) %>">
			<liferay-portlet:renderURL varImpl="addSiteURL">
				<portlet:param name="mvcPath" value="/edit_site.jsp" />
				<portlet:param name="parentGroupSearchContainerPrimaryKeys" value="<%= String.valueOf(group.getGroupId()) %>" />
			</liferay-portlet:renderURL>

			<liferay-ui:icon
				message="add-child-site"
				method="get"
				url="<%= addSiteURL.toString() %>"
			/>
		</c:if>
	</c:if>

	<c:if test="<%= group.isCompany() && hasUpdatePermission %>">
		<liferay-portlet:renderURL portletName="<%= ExportImportPortletKeys.EXPORT %>" var="exportURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
			<portlet:param name="mvcRenderCommandName" value="exportLayouts" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.EXPORT %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
			<portlet:param name="rootNodeName" value="<%= group.getDescriptiveName(locale) %>" />
			<portlet:param name="showHeader" value="<%= Boolean.FALSE.toString() %>" />
		</liferay-portlet:renderURL>

		<liferay-ui:icon
			message="export"
			method="get"
			url="<%= exportURL.toString() %>"
			useDialog="<%= true %>"
		/>

		<liferay-portlet:renderURL portletName="<%= ExportImportPortletKeys.IMPORT %>" var="importURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
			<portlet:param name="mvcRenderCommandName" value="importLayouts" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.VALIDATE %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
			<portlet:param name="rootNodeName" value="<%= group.getDescriptiveName(locale) %>" />
			<portlet:param name="showHeader" value="<%= Boolean.FALSE.toString() %>" />
		</liferay-portlet:renderURL>

		<liferay-ui:icon
			message="import"
			method="get"
			url="<%= importURL.toString() %>"
			useDialog="<%= true %>"
		/>

		<c:if test="<%= group.isStaged() %>">
			<liferay-portlet:renderURL portletName="<%= PortletKeys.EXPORT_IMPORT %>" var="publishURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
				<portlet:param name="mvcRenderCommandName" value="publishLayouts" />
				<portlet:param name="<%= Constants.CMD %>" value="<%= group.isStagedRemotely() ? Constants.PUBLISH_TO_REMOTE : Constants.PUBLISH_TO_LIVE %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
				<portlet:param name="rootNodeName" value="<%= group.getDescriptiveName(locale) %>" />
			</liferay-portlet:renderURL>

			<liferay-ui:icon
				message="publish"
				method="get"
				url="<%= publishURL.toString() %>"
				useDialog="<%= true %>"
			/>
		</c:if>
	</c:if>

	<c:if test="<%= group.getPublicLayoutsPageCount() > 0 %>">
		<liferay-ui:icon
			message="go-to-public-pages"
			target="_blank"
			url="<%= group.getDisplayURL(themeDisplay, false) %>"
		/>
	</c:if>

	<c:if test="<%= group.getPrivateLayoutsPageCount() > 0 %>">
		<liferay-ui:icon
			message="go-to-private-pages"
			target="_blank"
			url="<%= group.getDisplayURL(themeDisplay, true) %>"
		/>
	</c:if>

	<c:if test="<%= !group.isCompany() && !(organizationUser || userGroupUser) && ((group.getType() == GroupConstants.TYPE_SITE_OPEN) || (group.getType() == GroupConstants.TYPE_SITE_RESTRICTED)) && GroupLocalServiceUtil.hasUserGroup(user.getUserId(), group.getGroupId()) && !SiteMembershipPolicyUtil.isMembershipRequired(user.getUserId(), group.getGroupId()) %>">
		<portlet:actionURL name="editGroupAssignments" var="leaveURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
			<portlet:param name="removeUserIds" value="<%= String.valueOf(user.getUserId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon
			message="leave"
			url="<%= leaveURL %>"
		/>
	</c:if>

	<c:if test="<%= !group.isCompany() && hasUpdatePermission %>">
		<c:choose>
			<c:when test="<%= group.isActive() %>">
				<portlet:actionURL name="deactivate" var="activateURL">
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
				</portlet:actionURL>

				<liferay-ui:icon-deactivate url="<%= activateURL %>" />
			</c:when>
			<c:otherwise>
				<portlet:actionURL name="activate" var="activateURL">
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
				</portlet:actionURL>

				<liferay-ui:icon
					message="activate"
					url="<%= activateURL %>"
				/>
			</c:otherwise>
		</c:choose>
	</c:if>

	<c:if test="<%= !group.isCompany() && GroupPermissionUtil.contains(permissionChecker, group, ActionKeys.DELETE) && !PortalUtil.isSystemGroup(group.getGroupKey()) %>">
		<portlet:actionURL name="deleteGroups" var="deleteURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete url="<%= deleteURL %>" />
	</c:if>
</liferay-ui:icon-menu>