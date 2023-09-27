<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/bookmarks/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

BookmarksFolder folder = null;

if (row != null) {
	folder = (BookmarksFolder)row.getObject();
}
else {
	folder = (BookmarksFolder)request.getAttribute("info_panel.jsp-folder");
}

String modelResource = null;
String modelResourceDescription = null;
String resourcePrimKey = null;

boolean showPermissionsURL = false;

if (folder != null) {
	modelResource = BookmarksFolder.class.getName();
	modelResourceDescription = folder.getName();
	resourcePrimKey = String.valueOf(folder.getFolderId());

	showPermissionsURL = BookmarksFolderPermission.contains(permissionChecker, folder, ActionKeys.PERMISSIONS);
}
else {
	modelResource = "com.liferay.bookmarks";
	modelResourceDescription = themeDisplay.getScopeGroupName();
	resourcePrimKey = String.valueOf(scopeGroupId);

	showPermissionsURL = GroupPermissionUtil.contains(permissionChecker, scopeGroupId, ActionKeys.PERMISSIONS);
}

boolean view = false;

if (row == null) {
	view = true;
}
%>

<liferay-ui:icon-menu
	direction="left-side"
	icon="<%= StringPool.BLANK %>"
	markupView="lexicon"
	message="actions"
	showWhenSingleIcon="<%= true %>"
>
	<c:if test="<%= (folder != null) && BookmarksFolderPermission.contains(permissionChecker, folder, ActionKeys.UPDATE) %>">
		<liferay-ui:icon
			message="edit"
			url='<%=
				PortletURLBuilder.create(
					PortalUtil.getControlPanelPortletURL(request, themeDisplay.getScopeGroup(), BookmarksPortletKeys.BOOKMARKS_ADMIN, 0, themeDisplay.getPlid(), PortletRequest.RENDER_PHASE)
				).setMVCRenderCommandName(
					"/bookmarks/edit_folder"
				).setRedirect(
					currentURL
				).setPortletResource(
					portletDisplay.getId()
				).setParameter(
					"folderId", folder.getFolderId()
				).setParameter(
					"mergeWithParentFolderDisabled", row == null
				).buildString()
			%>'
		/>

		<portlet:renderURL var="moveURL">
			<portlet:param name="mvcRenderCommandName" value="/bookmarks/move_entry" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="rowIdsBookmarksFolder" value="<%= String.valueOf(folder.getFolderId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="move"
			url="<%= moveURL %>"
		/>
	</c:if>

	<c:if test="<%= showPermissionsURL %>">
		<liferay-security:permissionsURL
			modelResource="<%= modelResource %>"
			modelResourceDescription="<%= HtmlUtil.escape(modelResourceDescription) %>"
			resourcePrimKey="<%= resourcePrimKey %>"
			var="permissionsURL"
			windowState="<%= LiferayWindowState.POP_UP.toString() %>"
		/>

		<liferay-ui:icon
			message="permissions"
			method="get"
			url="<%= permissionsURL %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<c:if test="<%= (folder != null) && BookmarksFolderPermission.contains(permissionChecker, folder, ActionKeys.DELETE) %>">
		<portlet:renderURL var="redirectURL">
			<c:choose>
				<c:when test="<%= folder.getParentFolderId() == BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID %>">
					<portlet:param name="mvcRenderCommandName" value="/bookmarks/view" />
				</c:when>
				<c:otherwise>
					<portlet:param name="mvcRenderCommandName" value="/bookmarks/view_folder" />
					<portlet:param name="folderId" value="<%= String.valueOf(folder.getParentFolderId()) %>" />
				</c:otherwise>
			</c:choose>
		</portlet:renderURL>

		<portlet:actionURL name="/bookmarks/edit_folder" var="deleteURL">
			<portlet:param name="<%= Constants.CMD %>" value="<%= trashHelper.isTrashEnabled(scopeGroupId) ? Constants.MOVE_TO_TRASH : Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= view ? redirectURL : currentURL %>" />
			<portlet:param name="folderId" value="<%= String.valueOf(folder.getFolderId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			trash="<%= trashHelper.isTrashEnabled(scopeGroupId) %>"
			url="<%= deleteURL %>"
		/>
	</c:if>

	<%
	boolean bookmarksAdmin = portletName.equals(BookmarksPortletKeys.BOOKMARKS_ADMIN);
	boolean hasExportImportPortletInfoPermission = GroupPermissionUtil.contains(permissionChecker, scopeGroupId, ActionKeys.EXPORT_IMPORT_PORTLET_INFO);
	boolean inStagingGroup = stagingGroupHelper.isStagingGroup(scopeGroupId);
	boolean portletStaged = stagingGroupHelper.isStagedPortlet(scopeGroupId, BookmarksPortletKeys.BOOKMARKS);
	%>

	<c:if test="<%= (folder != null) && hasExportImportPortletInfoPermission && bookmarksAdmin && inStagingGroup && portletStaged %>">
		<portlet:actionURL name="/bookmarks/publish_folder" var="publishFolderURL">
			<portlet:param name="folderId" value="<%= String.valueOf(folder.getFolderId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			confirmation="are-you-sure-you-want-to-publish-the-selected-folder"
			message="publish-to-live"
			url="<%= publishFolderURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>