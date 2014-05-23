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

<%@ include file="/html/portlet/wiki/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

WikiNode node = (WikiNode)row.getObject();
%>

<liferay-ui:icon-menu icon="<%= StringPool.BLANK %>" message="<%= StringPool.BLANK %>">
	<c:if test="<%= WikiNodePermission.contains(permissionChecker, node, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="struts_action" value="/wiki/edit_node" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="nodeId" value="<%= String.valueOf(node.getNodeId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			iconCssClass="icon-edit"
			message="edit"
			url="<%= editURL %>"
		/>
	</c:if>

	<c:if test="<%= WikiNodePermission.contains(permissionChecker, node, ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="<%= WikiNode.class.getName() %>"
			modelResourceDescription="<%= node.getName() %>"
			resourcePrimKey="<%= String.valueOf(node.getNodeId()) %>"
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

	<c:if test="<%= WikiNodePermission.contains(permissionChecker, node, ActionKeys.IMPORT) %>">
		<portlet:renderURL var="importURL">
			<portlet:param name="struts_action" value="/wiki/import_pages" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="nodeId" value="<%= String.valueOf(node.getNodeId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			iconCssClass="icon-long-arrow-up"
			message="import-pages"
			url="<%= importURL %>"
		/>
	</c:if>

	<c:if test="<%= enableRSS %>">
		<liferay-ui:rss
			delta="<%= rssDelta %>"
			displayStyle="<%= rssDisplayStyle %>"
			feedType="<%= rssFeedType %>"
			url='<%= themeDisplay.getPathMain() + "/wiki/rss?p_l_id=" + plid + "&nodeId=" + node.getNodeId() %>'
		/>
	</c:if>

	<c:if test="<%= WikiNodePermission.contains(permissionChecker, node, ActionKeys.SUBSCRIBE) && (wikiSettings.getEmailPageAddedEnabled() || wikiSettings.getEmailPageUpdatedEnabled()) %>">
		<c:choose>
			<c:when test="<%= SubscriptionLocalServiceUtil.isSubscribed(user.getCompanyId(), user.getUserId(), WikiNode.class.getName(), node.getNodeId()) %>">
				<portlet:actionURL var="unsubscribeURL">
					<portlet:param name="struts_action" value="/wiki/edit_node" />
					<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.UNSUBSCRIBE %>" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="nodeId" value="<%= String.valueOf(node.getNodeId()) %>" />
				</portlet:actionURL>

				<liferay-ui:icon
					iconCssClass="icon-remove-sign"
					message="unsubscribe"
					url="<%= unsubscribeURL %>"
				/>
			</c:when>
			<c:otherwise>
				<portlet:actionURL var="subscribeURL">
					<portlet:param name="struts_action" value="/wiki/edit_node" />
					<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.SUBSCRIBE %>" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="nodeId" value="<%= String.valueOf(node.getNodeId()) %>" />
				</portlet:actionURL>

				<liferay-ui:icon
					iconCssClass="icon-ok-sign"
					message="subscribe"
					url="<%= subscribeURL %>"
				/>
			</c:otherwise>
		</c:choose>
	</c:if>

	<c:if test="<%= WikiNodePermission.contains(permissionChecker, node, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="viewDeletedAttachmentsURL">
			<portlet:param name="struts_action" value="/wiki/view_node_deleted_attachments" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="nodeId" value="<%= String.valueOf(node.getNodeId()) %>" />
			<portlet:param name="viewTrashAttachments" value="<%= Boolean.TRUE.toString() %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			iconCssClass="icon-paperclip"
			message="view-removed-attachments"
			url="<%= viewDeletedAttachmentsURL %>"
		/>
	</c:if>

	<c:if test="<%= WikiNodePermission.contains(permissionChecker, node, ActionKeys.DELETE) %>">
		<portlet:actionURL var="deleteURL">
			<portlet:param name="struts_action" value="/wiki/edit_node" />
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