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

<c:if test="<%= JournalFolderPermission.contains(permissionChecker, scopeGroupId, journalDisplayContext.getFolderId(), ActionKeys.ADD_FOLDER) || JournalFolderPermission.contains(permissionChecker, scopeGroupId, journalDisplayContext.getFolderId(), ActionKeys.ADD_ARTICLE) %>">
	<portlet:renderURL var="viewMoreURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
		<portlet:param name="mvcPath" value="/view_more_menu_items.jsp" />
		<portlet:param name="folderId" value="<%= String.valueOf(journalDisplayContext.getFolderId()) %>" />
		<portlet:param name="eventName" value='<%= renderResponse.getNamespace() + "selectAddMenuItem" %>' />
	</portlet:renderURL>

	<liferay-frontend:add-menu
		inline="<%= true %>"
		maxItems="<%= journalDisplayContext.getMaxAddMenuItems() %>"
		viewMoreURL="<%= viewMoreURL %>"
	>
		<c:if test="<%= JournalFolderPermission.contains(permissionChecker, scopeGroupId, journalDisplayContext.getFolderId(), ActionKeys.ADD_FOLDER) %>">
			<portlet:renderURL var="addFolderURL">
				<portlet:param name="mvcPath" value="/edit_folder.jsp" />
				<portlet:param name="redirect" value="<%= currentURL %>" />
				<portlet:param name="groupId" value="<%= String.valueOf(scopeGroupId) %>" />
				<portlet:param name="parentFolderId" value="<%= String.valueOf(journalDisplayContext.getFolderId()) %>" />
			</portlet:renderURL>

			<liferay-frontend:add-menu-item
				title='<%= LanguageUtil.get(request, (journalDisplayContext.getFolder() != null) ? "subfolder" : "folder") %>'
				type="<%= AddMenuKeys.AddMenuType.PRIMARY %>"
				url="<%= addFolderURL.toString() %>"
			/>
		</c:if>

		<c:if test="<%= JournalFolderPermission.contains(permissionChecker, scopeGroupId, journalDisplayContext.getFolderId(), ActionKeys.ADD_ARTICLE) %>">

			<%
			List<DDMStructure> ddmStructures = journalDisplayContext.getDDMStructures();

			for (DDMStructure ddmStructure : ddmStructures) {
				AddMenuKeys.AddMenuType addMenuType = AddMenuKeys.AddMenuType.DEFAULT;

				if (ArrayUtil.contains(journalDisplayContext.getAddMenuFavItems(), ddmStructure.getStructureKey())) {
					addMenuType = AddMenuKeys.AddMenuType.FAVORITE;
				}
			%>

				<portlet:renderURL var="addArticleURL">
					<portlet:param name="mvcPath" value="/edit_article.jsp" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="groupId" value="<%= String.valueOf(scopeGroupId) %>" />
					<portlet:param name="folderId" value="<%= String.valueOf(journalDisplayContext.getFolderId()) %>" />
					<portlet:param name="ddmStructureKey" value="<%= ddmStructure.getStructureKey() %>" />
				</portlet:renderURL>

				<liferay-frontend:add-menu-item
					title="<%= ddmStructure.getUnambiguousName(ddmStructures, themeDisplay.getScopeGroupId(), locale) %>"
					type="<%= addMenuType %>"
					url="<%= addArticleURL.toString() %>"
				/>

			<%
			}
			%>

		</c:if>
	</liferay-frontend:add-menu>

	<portlet:renderURL var="addArticleURL">
		<portlet:param name="mvcPath" value="/edit_article.jsp" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
		<portlet:param name="groupId" value="<%= String.valueOf(scopeGroupId) %>" />
		<portlet:param name="folderId" value="<%= String.valueOf(journalDisplayContext.getFolderId()) %>" />
	</portlet:renderURL>

	<aui:script>
		Liferay.on(
			'<portlet:namespace />selectAddMenuItem',
			function(event) {
				var uri = '<%= addArticleURL %>';

				uri = Liferay.Util.addParams('<portlet:namespace />ddmStructureKey=' + event.ddmStructureKey, uri);

				location.href = uri;
			}
		);
	</aui:script>
</c:if>