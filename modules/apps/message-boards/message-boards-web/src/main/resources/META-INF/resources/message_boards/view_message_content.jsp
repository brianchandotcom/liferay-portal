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

<%@ include file="/message_boards/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

String backURL = redirect;

MBMessageDisplay messageDisplay = (MBMessageDisplay)request.getAttribute(WebKeys.MESSAGE_BOARDS_MESSAGE_DISPLAY);

MBMessage message = messageDisplay.getMessage();

MBCategory category = messageDisplay.getCategory();

MBThread thread = messageDisplay.getThread();

MBThread previousThread = messageDisplay.getPreviousThread();
MBThread nextThread = messageDisplay.getNextThread();

if (Validator.isNull(redirect)) {
	PortletURL backPortletURL = renderResponse.createRenderURL();

	backPortletURL.setParameter("mvcRenderCommandName", "/message_boards/view");
	backPortletURL.setParameter("mbCategoryId", (category != null) ? String.valueOf(category.getCategoryId()) : String.valueOf(MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID));

	backURL = backPortletURL.toString();
}

boolean portletTitleBasedNavigation = GetterUtil.getBoolean(portletConfig.getInitParameter("portlet-title-based-navigation"));

if (portletTitleBasedNavigation) {
	portletDisplay.setShowBackIcon(true);
	portletDisplay.setURLBack(backURL);

	renderResponse.setTitle(message.getSubject());
}
%>

<c:if test="<%= !portletTitleBasedNavigation %>">
	<liferay-ui:header
		backURL="<%= backURL %>"
		localizeTitle="<%= false %>"
		title="<%= message.getSubject() %>"
	/>
</c:if>

<c:if test="<%= !portletTitleBasedNavigation %>">
	<div class="thread-controls">
		<c:if test="<%= PropsValues.MESSAGE_BOARDS_THREAD_PREVIOUS_AND_NEXT_NAVIGATION_ENABLED %>">
			<div class="thread-navigation">
				<liferay-ui:message key="threads" />

				[

				<c:choose>
					<c:when test="<%= previousThread != null %>">
						<portlet:renderURL var="previousThreadURL">
							<portlet:param name="mvcRenderCommandName" value="/message_boards/view_message" />
							<portlet:param name="messageId" value="<%= String.valueOf(previousThread.getRootMessageId()) %>" />
						</portlet:renderURL>

						<aui:a href="<%= previousThreadURL %>" label="previous" />
					</c:when>
					<c:otherwise>
						<liferay-ui:message key="previous" />
					</c:otherwise>
				</c:choose>

				|

				<c:choose>
					<c:when test="<%= nextThread != null %>">
						<portlet:renderURL var="nextThreadURL">
							<portlet:param name="mvcRenderCommandName" value="/message_boards/view_message" />
							<portlet:param name="messageId" value="<%= String.valueOf(nextThread.getRootMessageId()) %>" />
						</portlet:renderURL>

						<aui:a href="<%= nextThreadURL %>" label="next" />
					</c:when>
					<c:otherwise>
						<liferay-ui:message key="next" />
					</c:otherwise>
				</c:choose>

				]
			</div>
		</c:if>

		<div class="thread-actions">
			<liferay-ui:icon-list>
				<c:if test="<%= MBCategoryPermission.contains(permissionChecker, scopeGroupId, (category != null) ? category.getCategoryId() : MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID, ActionKeys.ADD_MESSAGE) %>">
					<portlet:renderURL var="addMessageURL">
						<portlet:param name="mvcRenderCommandName" value="/message_boards/edit_message" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="mbCategoryId" value="<%= (category != null) ? String.valueOf(category.getCategoryId()) : String.valueOf(MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) %>" />
					</portlet:renderURL>

					<liferay-ui:icon
						iconCssClass="icon-plus"
						message="post-new-thread"
						url="<%= addMessageURL %>"
					/>
				</c:if>

				<c:if test="<%= !thread.isLocked() && MBMessagePermission.contains(permissionChecker, message, ActionKeys.PERMISSIONS) %>">

					<%
					MBMessage rootMessage = null;

					if (message.isRoot()) {
						rootMessage = message;
					}
					else {
						rootMessage = MBMessageLocalServiceUtil.getMessage(message.getRootMessageId());
					}
					%>

					<liferay-security:permissionsURL
						modelResource="<%= MBMessage.class.getName() %>"
						modelResourceDescription="<%= rootMessage.getSubject() %>"
						resourcePrimKey="<%= String.valueOf(thread.getRootMessageId()) %>"
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

				<c:if test="<%= enableRSS && MBMessagePermission.contains(permissionChecker, message, ActionKeys.VIEW) %>">
					<liferay-ui:rss
						delta="<%= rssDelta %>"
						displayStyle="<%= rssDisplayStyle %>"
						feedType="<%= rssFeedType %>"
						url="<%= MBUtil.getRSSURL(plid, 0, message.getThreadId(), 0, themeDisplay) %>"
					/>
				</c:if>

				<c:if test="<%= MBMessagePermission.contains(permissionChecker, message, ActionKeys.SUBSCRIBE) && (mbGroupServiceSettings.isEmailMessageAddedEnabled() || mbGroupServiceSettings.isEmailMessageUpdatedEnabled()) %>">
					<c:choose>
						<c:when test="<%= SubscriptionLocalServiceUtil.isSubscribed(user.getCompanyId(), user.getUserId(), MBThread.class.getName(), message.getThreadId()) %>">
							<portlet:actionURL name="/message_boards/edit_message" var="unsubscribeURL">
								<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.UNSUBSCRIBE %>" />
								<portlet:param name="redirect" value="<%= currentURL %>" />
								<portlet:param name="messageId" value="<%= String.valueOf(message.getMessageId()) %>" />
							</portlet:actionURL>

							<liferay-ui:icon
								iconCssClass="icon-remove-sign"
								message="unsubscribe"
								url="<%= unsubscribeURL %>"
							/>
						</c:when>
						<c:otherwise>
							<portlet:actionURL name="/message_boards/edit_message" var="subscribeURL">
								<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.SUBSCRIBE %>" />
								<portlet:param name="redirect" value="<%= currentURL %>" />
								<portlet:param name="messageId" value="<%= String.valueOf(message.getMessageId()) %>" />
							</portlet:actionURL>

							<liferay-ui:icon
								iconCssClass="icon-ok-sign"
								message="subscribe"
								url="<%= subscribeURL %>"
							/>
						</c:otherwise>
					</c:choose>
				</c:if>

				<c:if test="<%= MBCategoryPermission.contains(permissionChecker, scopeGroupId, (category != null) ? category.getCategoryId() : MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID, ActionKeys.LOCK_THREAD) %>">
					<c:choose>
						<c:when test="<%= thread.isLocked() %>">
							<portlet:actionURL name="/message_boards/edit_message" var="unlockThreadURL">
								<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.UNLOCK %>" />
								<portlet:param name="redirect" value="<%= currentURL %>" />
								<portlet:param name="threadId" value="<%= String.valueOf(message.getThreadId()) %>" />
							</portlet:actionURL>

							<liferay-ui:icon
								iconCssClass="icon-unlock"
								message="unlock-thread"
								url="<%= unlockThreadURL %>"
							/>
						</c:when>
						<c:otherwise>
							<portlet:actionURL name="/message_boards/edit_message" var="lockThreadURL">
								<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.LOCK %>" />
								<portlet:param name="redirect" value="<%= currentURL %>" />
								<portlet:param name="threadId" value="<%= String.valueOf(message.getThreadId()) %>" />
							</portlet:actionURL>

							<liferay-ui:icon
								iconCssClass="icon-lock"
								message="lock-thread"
								url="<%= lockThreadURL %>"
							/>
						</c:otherwise>
					</c:choose>
				</c:if>

				<c:if test="<%= MBCategoryPermission.contains(permissionChecker, scopeGroupId, (category != null) ? category.getCategoryId() : MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID, ActionKeys.MOVE_THREAD) %>">
					<portlet:renderURL var="editThreadURL">
						<portlet:param name="mvcRenderCommandName" value="/message_boards/move_thread" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="mbCategoryId" value="<%= (category != null) ? String.valueOf(category.getCategoryId()) : String.valueOf(MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) %>" />
						<portlet:param name="threadId" value="<%= String.valueOf(message.getThreadId()) %>" />
					</portlet:renderURL>

					<liferay-ui:icon
						iconCssClass="icon-move"
						message="move-thread"
						url="<%= editThreadURL %>"
					/>
				</c:if>

				<c:if test="<%= MBMessagePermission.contains(permissionChecker, message, ActionKeys.DELETE) && !thread.isLocked() %>">
					<portlet:renderURL var="parentCategoryURL">
						<portlet:param name="mvcRenderCommandName" value="/message_boards/view" />
						<portlet:param name="mbCategoryId" value="<%= (category != null) ? String.valueOf(category.getCategoryId()) : String.valueOf(MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) %>" />
					</portlet:renderURL>

					<portlet:actionURL name="/message_boards/delete_thread" var="deleteURL">
						<portlet:param name="<%= Constants.CMD %>" value="<%= TrashUtil.isTrashEnabled(themeDisplay.getScopeGroupId()) ? Constants.MOVE_TO_TRASH : Constants.DELETE %>" />
						<portlet:param name="redirect" value="<%= parentCategoryURL %>" />
						<portlet:param name="threadId" value="<%= String.valueOf(message.getThreadId()) %>" />
					</portlet:actionURL>

					<liferay-ui:icon-delete
						trash="<%= TrashUtil.isTrashEnabled(themeDisplay.getScopeGroupId()) %>"
						url="<%= deleteURL %>"
					/>
				</c:if>
			</liferay-ui:icon-list>
		</div>

		<div class="clear"></div>
	</div>
</c:if>

<div class="thread-container">

	<%
	MBTreeWalker treeWalker = messageDisplay.getTreeWalker();

	AssetUtil.addLayoutTags(request, AssetTagLocalServiceUtil.getTags(MBMessage.class.getName(), thread.getRootMessageId()));
	%>

	<div class="message-scroll" id="<portlet:namespace />message_0"></div>

	<%
	boolean viewableThread = false;
	%>

	<%
	request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER, treeWalker);
	request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_CATEGORY, category);
	request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_CUR_MESSAGE, treeWalker.getRoot());
	request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_DEPTH, Integer.valueOf(0));
	request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_LAST_NODE, Boolean.valueOf(false));
	request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_SEL_MESSAGE, message);
	request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_THREAD, thread);
	request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_VIEWABLE_THREAD, Boolean.FALSE.toString());
	%>

	<liferay-util:include page="/message_boards/view_thread_tree.jsp" servletContext="<%= application %>" />

	<%
	viewableThread = GetterUtil.getBoolean((String)request.getAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_VIEWABLE_THREAD));
	%>

	<c:if test="<%= !viewableThread %>">
		<div class="alert alert-danger">
			<liferay-ui:message key="you-do-not-have-permission-to-access-the-requested-resource" />
		</div>
	</c:if>

	<%
	MBMessage rootMessage = treeWalker.getRoot();
	%>

	<c:if test="<%= MBCategoryPermission.contains(permissionChecker, scopeGroupId, rootMessage.getCategoryId(), ActionKeys.REPLY_TO_MESSAGE) && !thread.isLocked() %>">
		<portlet:renderURL var="replyURL">
			<portlet:param name="mvcRenderCommandName" value="/message_boards/edit_message" />
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="mbCategoryId" value="<%= String.valueOf(rootMessage.getCategoryId()) %>" />
			<portlet:param name="threadId" value="<%= String.valueOf(rootMessage.getThreadId()) %>" />
			<portlet:param name="parentMessageId" value="<%= String.valueOf(rootMessage.getMessageId()) %>" />
			<portlet:param name="priority" value="<%= String.valueOf(rootMessage.getPriority()) %>" />
		</portlet:renderURL>

		<div class="reply-to-main-thread-container">
			<aui:button cssClass="btn-lg btn-primary" href="<%= replyURL.toString() %>" value="reply-to-main-thread" />
		</div>
	</c:if>
</div>