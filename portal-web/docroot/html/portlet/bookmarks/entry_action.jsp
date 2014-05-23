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

<%@ include file="/html/portlet/bookmarks/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

BookmarksEntry entry = null;

String cssClass = StringPool.BLANK;

boolean view = false;

if (row != null) {
	Object result = row.getObject();

	if (result instanceof AssetEntry) {
		AssetEntry assetEntry = (AssetEntry)result;

		entry = BookmarksEntryServiceUtil.getEntry(assetEntry.getClassPK());
	}
	else {
		entry = (BookmarksEntry)result;
	}
}
else {
	entry = (BookmarksEntry)request.getAttribute("view_entry.jsp-entry");

	cssClass = "nav nav-list unstyled well";

	view = true;
}
%>

<liferay-ui:icon-menu cssClass="<%= cssClass %>" icon="<%= StringPool.BLANK %>" message="<%= StringPool.BLANK %>" showExpanded="<%= view %>" showWhenSingleIcon="<%= view %>">
	<c:if test="<%= BookmarksEntryPermission.contains(permissionChecker, entry, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="struts_action" value="/bookmarks/edit_entry" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="backURL" value="<%= currentURL %>" />
			<portlet:param name="entryId" value="<%= String.valueOf(entry.getEntryId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			iconCssClass="icon-edit"
			message="edit"
			url="<%= editURL %>"
		/>
	</c:if>

	<c:if test="<%= BookmarksEntryPermission.contains(permissionChecker, entry, ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= BookmarksEntry.class.getName() %>"
			modelResourceDescription="<%= entry.getName() %>"
			resourcePrimKey="<%= String.valueOf(entry.getEntryId()) %>"
			var="permissionsURL"
			windowState="<%= LiferayWindowState.POP_UP.toString() %>"
		/>

		<liferay-ui:icon
			iconCssClass="icon-lock"
			message="permissions"
			method="get"
			url="<%= permissionsURL %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<c:if test="<%= BookmarksEntryPermission.contains(permissionChecker, entry, ActionKeys.SUBSCRIBE) && (bookmarksSettings.getEmailEntryAddedEnabled() || bookmarksSettings.getEmailEntryUpdatedEnabled()) %>">
		<c:choose>
			<c:when test="<%= SubscriptionLocalServiceUtil.isSubscribed(user.getCompanyId(), user.getUserId(), BookmarksEntry.class.getName(), entry.getEntryId()) %>">
				<portlet:actionURL var="unsubscribeURL">
					<portlet:param name="struts_action" value="/bookmarks/edit_entry" />
					<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.UNSUBSCRIBE %>" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="entryId" value="<%= String.valueOf(entry.getEntryId()) %>" />
				</portlet:actionURL>

				<liferay-ui:icon
					iconCssClass="icon-remove-sign"
					message="unsubscribe"
					url="<%= unsubscribeURL %>"
				/>
			</c:when>
			<c:otherwise>
				<portlet:actionURL var="subscribeURL">
					<portlet:param name="struts_action" value="/bookmarks/edit_entry" />
					<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.SUBSCRIBE %>" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="entryId" value="<%= String.valueOf(entry.getEntryId()) %>" />
				</portlet:actionURL>

				<liferay-ui:icon
					iconCssClass="icon-ok-sign"
					message="subscribe"
					url="<%= subscribeURL %>"
				/>
			</c:otherwise>
		</c:choose>
	</c:if>

	<c:if test="<%= BookmarksEntryPermission.contains(permissionChecker, entry, ActionKeys.DELETE) %>">
		<portlet:renderURL var="redirectURL">
			<portlet:param name="struts_action" value="/bookmarks/view" />
			<portlet:param name="folderId" value="<%= String.valueOf(entry.getFolderId()) %>" />
		</portlet:renderURL>

		<portlet:actionURL var="deleteURL">
			<portlet:param name="struts_action" value="/bookmarks/edit_entry" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= TrashUtil.isTrashEnabled(scopeGroupId) ? Constants.MOVE_TO_TRASH : Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= view ? redirectURL : currentURL %>" />
			<portlet:param name="entryId" value="<%= String.valueOf(entry.getEntryId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			trash="<%= TrashUtil.isTrashEnabled(scopeGroupId) %>"
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>