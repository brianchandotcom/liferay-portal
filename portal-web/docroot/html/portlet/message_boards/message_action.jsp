<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/message_boards/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

Object[] objArray = (Object[])row.getObject();

MBMessage message = (MBMessage)objArray[0];

Set<Long> threadSubscriptionClassPKs = null;

if (portletName.equals(PortletKeys.MESSAGE_BOARDS)) {
	threadSubscriptionClassPKs = (Set<Long>)objArray[1];
}

MBCategory category = message.getCategory();
MBThread thread = message.getThread();
%>

<liferay-ui:icon-menu>
	<c:if test="<%= MBMessagePermission.contains(permissionChecker, message, ActionKeys.UPDATE) && !thread.isLocked() %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="struts_action" value="/message_boards/edit_message" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="messageId" value="<%= String.valueOf(message.getMessageId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			image="edit"
			url="<%= editURL %>"
		/>
	</c:if>

	<c:if test="<%= MBMessagePermission.contains(permissionChecker, message, ActionKeys.PERMISSIONS) && !thread.isLocked() %>">
		<liferay-security:permissionsURL
			modelResource="<%= MBMessage.class.getName() %>"
			modelResourceDescription="<%= message.getSubject() %>"
			resourcePrimKey="<%= String.valueOf(message.getMessageId()) %>"
			var="permissionsURL"
		/>

		<liferay-ui:icon
			image="permissions"
			url="<%= permissionsURL %>"
		/>
	</c:if>

	<c:if test="<%= portletName.equals(PortletKeys.MESSAGE_BOARDS) %>">
		<c:if test="<%= PortalUtil.isRSSFeedsEnabled() && MBMessagePermission.contains(permissionChecker, message, ActionKeys.VIEW) %>">

			<%
			rssURL.setParameter("p_l_id", String.valueOf(plid));
			rssURL.setParameter("mbCategoryId", StringPool.BLANK);
			rssURL.setParameter("threadId", String.valueOf(message.getThreadId()));
			%>

			<liferay-ui:icon
				image="rss"
				method="get"
				target="_blank"
				url="<%= rssURL.toString() %>"
			/>
		</c:if>

		<c:if test="<%= MBMessagePermission.contains(permissionChecker, message, ActionKeys.SUBSCRIBE) %>">
			<c:choose>
				<c:when test="<%= (threadSubscriptionClassPKs != null) && threadSubscriptionClassPKs.contains(message.getThreadId()) %>">
					<portlet:actionURL var="unsubscribeURL">
						<portlet:param name="struts_action" value="/message_boards/edit_message" />
						<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.UNSUBSCRIBE %>" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="messageId" value="<%= String.valueOf(message.getMessageId()) %>" />
					</portlet:actionURL>

					<liferay-ui:icon
						image="unsubscribe"
						url="<%= unsubscribeURL %>"
					/>
				</c:when>
				<c:otherwise>
					<portlet:actionURL var="subscribeURL">
						<portlet:param name="struts_action" value="/message_boards/edit_message" />
						<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.SUBSCRIBE %>" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="messageId" value="<%= String.valueOf(message.getMessageId()) %>" />
					</portlet:actionURL>

					<liferay-ui:icon
						image="subscribe"
						url="<%= subscribeURL %>"
					/>
				</c:otherwise>
			</c:choose>
		</c:if>
	</c:if>

	<c:if test="<%= MBCategoryPermission.contains(permissionChecker, message.getGroupId(), message.getCategoryId(), ActionKeys.LOCK_THREAD) %>">
		<c:choose>
			<c:when test="<%= thread.isLocked() %>">
				<portlet:actionURL var="unlockThreadURL">
					<portlet:param name="struts_action" value="/message_boards/edit_message" />
					<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.UNLOCK %>" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="threadId" value="<%= String.valueOf(message.getThreadId()) %>" />
				</portlet:actionURL>

				<liferay-ui:icon
					image="unlock"
					message="unlock-thread"
					url="<%= unlockThreadURL %>"
				/>
			</c:when>
			<c:otherwise>
				<portlet:actionURL var="lockThreadURL">
					<portlet:param name="struts_action" value="/message_boards/edit_message" />
					<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.LOCK %>" />
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="messageId" value="<%= String.valueOf(message.getMessageId()) %>" />
					<portlet:param name="threadId" value="<%= String.valueOf(message.getThreadId()) %>" />
				</portlet:actionURL>

				<liferay-ui:icon
					image="lock"
					message="lock-thread"
					url="<%= lockThreadURL %>"
				/>
			</c:otherwise>
		</c:choose>
	</c:if>

	<c:if test="<%= MBCategoryPermission.contains(permissionChecker, message.getGroupId(), message.getCategoryId(), ActionKeys.MOVE_THREAD) %>">
		<portlet:renderURL var="moveThreadURL">
			<portlet:param name="struts_action" value="/message_boards/move_thread" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="mbCategoryId" value="<%= String.valueOf(category.getCategoryId()) %>" />
			<portlet:param name="threadId" value="<%= String.valueOf(message.getThreadId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			image="forward"
			message="move-thread"
			url="<%= moveThreadURL %>"
		/>
	</c:if>

	<c:if test="<%= MBMessagePermission.contains(permissionChecker, message, ActionKeys.DELETE) && !thread.isLocked() %>">
		<portlet:actionURL var="deleteURL">
			<portlet:param name="struts_action" value="/message_boards/delete_thread" />
			<portlet:param name="<%= Constants.CMD %>" value="<%= TrashUtil.isTrashEnabled(themeDisplay.getScopeGroupId()) ? Constants.MOVE_TO_TRASH : Constants.DELETE %>" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="threadId" value="<%= String.valueOf(message.getThreadId()) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete
			trash="<%= TrashUtil.isTrashEnabled(themeDisplay.getScopeGroupId()) %>"
			url="<%= deleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>