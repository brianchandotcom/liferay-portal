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
	group = (Group)request.getAttribute("view_tree.jspf-site");

	GroupSearchTerms searchTerms = (GroupSearchTerms)request.getAttribute("view_tree.jspf-searchTerms");

	List<String> organizationNames = SitesUtil.getOrganizationNames(group, user);

	organizationUser = !organizationNames.isEmpty();

	List<String> userGroupNames = SitesUtil.getUserGroupNames(group, user);

	userGroupUser = !userGroupNames.isEmpty();
}

boolean hasUpdatePermission = GroupPermissionUtil.contains(permissionChecker, group, ActionKeys.UPDATE);

boolean view = false;

if (row == null) {
	view = true;
}
%>

<liferay-ui:icon-menu direction="down" icon="<%= StringPool.BLANK %>" message="<%= StringPool.BLANK %>" showExpanded="<%= view %>" showWhenSingleIcon="<%= true %>">

	<%
	ThemeDisplay siteThemeDisplay = (ThemeDisplay)themeDisplay.clone();

	siteThemeDisplay.setScopeGroupId(group.getGroupId());

	PortletURL siteAdministrationURL = PortalUtil.getSiteAdministrationURL(renderResponse, siteThemeDisplay);
	%>

	<c:if test="<%= siteAdministrationURL != null %>">
		<liferay-ui:icon
			iconCssClass="icon-cog"
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
				<portlet:param name="sitesListView" value="<%= SiteConstants.LIST_VIEW_TREE %>" />
			</liferay-portlet:renderURL>

			<liferay-ui:icon
				iconCssClass="icon-search"
				message="view-child-sites"
				url="<%= viewSubsitesURL %>"
			/>
		</c:if>

		<c:if test="<%= !group.isCompany() && (PortalPermissionUtil.contains(permissionChecker, ActionKeys.ADD_COMMUNITY) || GroupPermissionUtil.contains(permissionChecker, group, ActionKeys.ADD_COMMUNITY)) %>">
			<liferay-portlet:renderURL varImpl="addSiteURL">
				<portlet:param name="mvcPath" value="/edit_site.jsp" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="parentGroupSearchContainerPrimaryKeys" value="<%= String.valueOf(group.getGroupId()) %>" />
				<portlet:param name="showPrototypes" value="<%= Boolean.TRUE.toString() %>" />
			</liferay-portlet:renderURL>

			<liferay-ui:icon
				iconCssClass="icon-plus"
				message="add-child-site"
				method="get"
				url="<%= addSiteURL.toString() %>"
			/>
		</c:if>
	</c:if>

	<c:if test="<%= group.isCompany() && hasUpdatePermission %>">
		<liferay-portlet:renderURL portletName="<%= PortletKeys.LAYOUTS_ADMIN %>" var="exportURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
			<portlet:param name="struts_action" value="/layouts_admin/export_layouts" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.EXPORT %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
			<portlet:param name="rootNodeName" value="<%= group.getDescriptiveName(locale) %>" />
		</liferay-portlet:renderURL>

		<%
		String taglibExportURL = "javascript:Liferay.Util.openWindow({id: '" + renderResponse.getNamespace() + "exportDialog', title: '" + HtmlUtil.escapeJS(LanguageUtil.get(request, "export")) + "', uri: '" + HtmlUtil.escapeJS(exportURL.toString()) + "'});";
		%>

		<liferay-ui:icon
			iconCssClass="icon-arrow-down"
			message="export"
			url="<%= taglibExportURL %>"
		/>

		<liferay-portlet:renderURL portletName="<%= PortletKeys.LAYOUTS_ADMIN %>" var="importURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
			<portlet:param name="struts_action" value="/layouts_admin/import_layouts" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.VALIDATE %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
			<portlet:param name="rootNodeName" value="<%= group.getDescriptiveName(locale) %>" />
		</liferay-portlet:renderURL>

		<%
		String taglibImportURL = "javascript:Liferay.Util.openWindow({id: '" + renderResponse.getNamespace() + "importDialog', title: '" + HtmlUtil.escapeJS(LanguageUtil.get(request, "import")) + "', uri: '" + HtmlUtil.escapeJS(importURL.toString()) + "'});";
		%>

		<liferay-ui:icon
			iconCssClass="icon-arrow-up"
			message="import"
			url="<%= taglibImportURL %>"
		/>

		<c:if test="<%= group.isStaged() %>">
			<liferay-portlet:renderURL portletName="<%= PortletKeys.LAYOUTS_ADMIN %>" var="publishURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
				<portlet:param name="struts_action" value="/layouts_admin/publish_layouts" />
				<portlet:param name="<%= Constants.CMD %>" value="<%= group.isStagedRemotely() ? Constants.PUBLISH_TO_REMOTE : Constants.PUBLISH_TO_LIVE %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
				<portlet:param name="rootNodeName" value="<%= group.getDescriptiveName(locale) %>" />
			</liferay-portlet:renderURL>

			<%
			String taglibPublishURL = "javascript:Liferay.Util.openWindow({id: '" + renderResponse.getNamespace() + "publishDialog', title: '" + HtmlUtil.escapeJS(LanguageUtil.get(request, "publish")) + "', uri: '" + HtmlUtil.escapeJS(publishURL.toString()) + "'});";
			%>

			<liferay-ui:icon
				iconCssClass="icon-share-alt"
				message="publish"
				url="<%= taglibPublishURL %>"
			/>
		</c:if>
	</c:if>

	<c:if test="<%= group.getPublicLayoutsPageCount() > 0 %>">
		<liferay-ui:icon
			iconCssClass="icon-search"
			message="go-to-public-pages"
			target="_blank"
			url="<%= group.getDisplayURL(themeDisplay, false) %>"
		/>
	</c:if>

	<c:if test="<%= group.getPrivateLayoutsPageCount() > 0 %>">
		<liferay-ui:icon
			iconCssClass="icon-search"
			message="go-to-private-pages"
			target="_blank"
			url="<%= group.getDisplayURL(themeDisplay, true) %>"
		/>
	</c:if>

	<c:if test="<%= !group.isCompany() && (!(organizationUser || userGroupUser) && ((group.getType() == GroupConstants.TYPE_SITE_OPEN) || (group.getType() == GroupConstants.TYPE_SITE_RESTRICTED)) && GroupLocalServiceUtil.hasUserGroup(user.getUserId(), group.getGroupId())) && !SiteMembershipPolicyUtil.isMembershipRequired(user.getUserId(), group.getGroupId()) %>">
		<portlet:actionURL name="editGroupAssignments" var="leaveURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(group.getGroupId()) %>" />
			<portlet:param name="removeUserIds" value="<%= String.valueOf(user.getUserId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon
			iconCssClass="icon-external-link-sign"
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
					iconCssClass="icon-ok-sign"
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