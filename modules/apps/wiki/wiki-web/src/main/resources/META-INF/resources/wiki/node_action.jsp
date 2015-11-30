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

<%@ include file="/wiki/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

WikiNode node = (WikiNode)row.getObject();
%>

<liferay-ui:icon-menu direction="left-side" icon="<%= StringPool.BLANK %>" markupView="lexicon" message="<%= StringPool.BLANK %>" showWhenSingleIcon="<%= true %>">
	<c:if test="<%= WikiNodePermissionChecker.contains(permissionChecker, node, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="mvcRenderCommandName" value="/wiki/edit_node" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="nodeId" value="<%= String.valueOf(node.getNodeId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="edit"
			url="<%= editURL %>"
		/>
	</c:if>

	<c:if test="<%= WikiNodePermissionChecker.contains(permissionChecker, node, ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= WikiNode.class.getName() %>"
			modelResourceDescription="<%= node.getName() %>"
			resourcePrimKey="<%= String.valueOf(node.getNodeId()) %>"
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

	<c:if test="<%= WikiNodePermissionChecker.contains(permissionChecker, node, ActionKeys.IMPORT) %>">
		<portlet:renderURL var="importURL">
			<portlet:param name="mvcRenderCommandName" value="/wiki/import_pages" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="nodeId" value="<%= String.valueOf(node.getNodeId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="import-pages"
			url="<%= importURL %>"
		/>
	</c:if>

	<c:if test="<%= wikiGroupServiceOverriddenConfiguration.enableRss() %>">
		<liferay-ui:rss
			delta="<%= GetterUtil.getInteger(wikiGroupServiceOverriddenConfiguration.rssDelta()) %>"
			displayStyle="<%= wikiGroupServiceOverriddenConfiguration.rssDisplayStyle() %>"
			feedType="<%= wikiGroupServiceOverriddenConfiguration.rssFeedType() %>"
			url='<%= themeDisplay.getPathMain() + "/wiki/rss?nodeId=" + node.getNodeId() %>'
		/>
	</c:if>

	<c:if test="<%= WikiNodePermissionChecker.contains(permissionChecker, node, ActionKeys.SUBSCRIBE) && (wikiGroupServiceOverriddenConfiguration.emailPageAddedEnabled() || wikiGroupServiceOverriddenConfiguration.emailPageUpdatedEnabled()) %>">
		<c:choose>
			<c:when test="<%= SubscriptionLocalServiceUtil.isSubscribed(user.getCompanyId(), user.getUserId(), WikiNode.class.getName(), node.getNodeId()) %>">
				<portlet:actionURL name="/wiki/edit_node" var="unsubscribeURL">
					<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.UNSUBSCRIBE %>" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="nodeId" value="<%= String.valueOf(node.getNodeId()) %>" />
				</portlet:actionURL>

				<liferay-ui:icon
					message="unsubscribe"
					url="<%= unsubscribeURL %>"
				/>
			</c:when>
			<c:otherwise>
				<portlet:actionURL name="/wiki/edit_node" var="subscribeURL">
					<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.SUBSCRIBE %>" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="nodeId" value="<%= String.valueOf(node.getNodeId()) %>" />
				</portlet:actionURL>

				<liferay-ui:icon
					message="subscribe"
					url="<%= subscribeURL %>"
				/>
			</c:otherwise>
		</c:choose>
	</c:if>

	<c:if test="<%= WikiNodePermissionChecker.contains(permissionChecker, node, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="viewDeletedAttachmentsURL">
			<portlet:param name="mvcRenderCommandName" value="/wiki/view_node_deleted_attachments" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="nodeId" value="<%= String.valueOf(node.getNodeId()) %>" />
			<portlet:param name="viewTrashAttachments" value="<%= Boolean.TRUE.toString() %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			message="view-removed-attachments"
			url="<%= viewDeletedAttachmentsURL %>"
		/>
	</c:if>

	<c:if test="<%= WikiNodePermissionChecker.contains(permissionChecker, node, ActionKeys.DELETE) && (WikiNodeServiceUtil.getNodesCount(scopeGroupId) > 1) %>">
		<portlet:actionURL name="/wiki/edit_node" var="deleteURL">
			<portlet:param name="<%= Constants.CMD %>" value="<%= (TrashUtil.isTrashEnabled(scopeGroupId)) ? Constants.MOVE_TO_TRASH : Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="nodeId" value="<%= String.valueOf(node.getNodeId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			trash="<%= TrashUtil.isTrashEnabled(scopeGroupId) %>"
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>