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

<%@ include file="/bookmarks/init.jsp" %>

<%
String searchContainerId = ParamUtil.getString(request, "searchContainerId");

long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));

PortletURL portletURL = renderResponse.createRenderURL();

int deltaEntry = ParamUtil.getInteger(request, "deltaEntry");

if (deltaEntry > 0) {
	portletURL.setParameter("deltaEntry", String.valueOf(deltaEntry));
}

portletURL.setParameter("categoryId", StringPool.BLANK);
portletURL.setParameter("tag", StringPool.BLANK);
%>

<liferay-frontend:management-bar
	disabled="<%= BookmarksFolderServiceUtil.getFoldersAndEntriesCount(scopeGroupId, folderId) <= 0 %>"
	includeCheckBox="<%= true %>"
	searchContainerId="<%= searchContainerId %>"
>
	<liferay-frontend:management-bar-buttons>
		<liferay-frontend:management-bar-sidenav-toggler-button
			icon="info-circle"
			label="info"
		/>

		<liferay-util:include page="/bookmarks/display_style_buttons.jsp" servletContext="<%= application %>" />

		<c:if test="<%= portletName.equals(BookmarksPortletKeys.BOOKMARKS_ADMIN) %>">
			<liferay-util:include page="/bookmarks/add_button.jsp" servletContext="<%= application %>" />
		</c:if>
	</liferay-frontend:management-bar-buttons>

	<liferay-frontend:management-bar-filters>

		<%
		String[] navigationKeys = null;

		if (themeDisplay.isSignedIn()) {
			navigationKeys = new String[] {"all", "recent", "mine"};
		}
		else {
			navigationKeys = new String[] {"all", "recent"};
		}
		%>

		<liferay-frontend:management-bar-navigation
			navigationKeys="<%= navigationKeys %>"
			portletURL="<%= PortletURLUtil.clone(portletURL, liferayPortletResponse) %>"
		/>

		<c:if test="<%= bookmarksGroupServiceOverriddenConfiguration.showFoldersSearch() %>">
			<li>
				<liferay-portlet:renderURL varImpl="searchURL">
					<portlet:param name="mvcRenderCommandName" value="/bookmarks/view" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="folderId" value="<%= String.valueOf(folderId) %>" />
				</liferay-portlet:renderURL>

				<aui:form action="<%= searchURL.toString() %>" name="searchFm">
					<liferay-ui:input-search
						markupView="lexicon"
					/>
				</aui:form>
			</li>
		</c:if>
	</liferay-frontend:management-bar-filters>

	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-sidenav-toggler-button
			icon="info-circle"
			label="info"
		/>

		<c:if test="<%= !user.isDefaultUser() %>">
			<liferay-frontend:management-bar-button
				href='<%= "javascript:" + renderResponse.getNamespace() + "deleteEntries();" %>'
				icon='<%= trashHelper.isTrashEnabled(scopeGroupId) ? "trash" : "times" %>'
				label='<%= trashHelper.isTrashEnabled(scopeGroupId) ? "recycle-bin" : "delete" %>'
			/>
		</c:if>
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<aui:script>
	function <portlet:namespace />deleteEntries() {
		if (<%= trashHelper.isTrashEnabled(scopeGroupId) %> || confirm(' <%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-the-selected-entries") %>')) {
			var form = AUI.$(document.<portlet:namespace />fm);

			form.attr('method', 'post');
			form.fm('<%= Constants.CMD %>').val('<%= trashHelper.isTrashEnabled(scopeGroupId) ? Constants.MOVE_TO_TRASH : Constants.DELETE %>');

			submitForm(form, '<portlet:actionURL name="/bookmarks/edit_entry" />');
		}
	}
</aui:script>