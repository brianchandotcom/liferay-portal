<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
MBMessageDisplay messageDisplay = (MBMessageDisplay)request.getAttribute(WebKeys.MESSAGE_BOARDS_MESSAGE);

MBMessage message = messageDisplay.getMessage();

MBCategory category = messageDisplay.getCategory();

MBThread thread = messageDisplay.getThread();

MBThread previousThread = messageDisplay.getPreviousThread();
MBThread nextThread = messageDisplay.getNextThread();

String threadView = messageDisplay.getThreadView();

MBThreadFlag threadFlag = MBThreadFlagLocalServiceUtil.getThreadFlag(themeDisplay.getUserId(), thread);

String redirect = ParamUtil.getString(request, "redirect");
String backLabel = null;

if (Validator.isNull(redirect)) {
	PortletURL portletURL = renderResponse.createRenderURL();
	portletURL.setParameter("struts_action", "/message_boards/view");

	long mbCategoryId = MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID;
	backLabel = "message-boards-home";

	if (category != null) {
		mbCategoryId = category.getCategoryId();
		backLabel = category.getName();
	}

	portletURL.setParameter("mbCategoryId", String.valueOf(mbCategoryId));

	redirect = portletURL.toString();
}
%>

<liferay-ui:header
	backLabel='<%= backLabel %>'
	backURL="<%= redirect %>"
	localizeTitle="<%= false %>"
	title="<%= message.getSubject() %>"
/>

<table cellpadding="0" cellspacing="0" class="thread-view-controls" width="100%">
<tr>
	<td class="stretch"></td>

	<c:if test="<%= PropsValues.MESSAGE_BOARDS_THREAD_VIEWS.length > 1 %>">
		<c:if test="<%= ArrayUtil.contains(PropsValues.MESSAGE_BOARDS_THREAD_VIEWS, MBThreadConstants.THREAD_VIEW_COMBINATION) %>">
			<td class="thread-icon">

				<%
				currentURLObj.setParameter("threadView", MBThreadConstants.THREAD_VIEW_COMBINATION);
				%>

				<liferay-ui:icon
					image="../message_boards/thread_view_combination"
					message="combination-view"
					method="get"
					url="<%= currentURLObj.toString() %>"
				/>
			</td>
		</c:if>

		<c:if test="<%= ArrayUtil.contains(PropsValues.MESSAGE_BOARDS_THREAD_VIEWS, MBThreadConstants.THREAD_VIEW_FLAT) %>">
			<td class="thread-icon">

				<%
				currentURLObj.setParameter("threadView", MBThreadConstants.THREAD_VIEW_FLAT);
				%>

				<liferay-ui:icon
					image="../message_boards/thread_view_flat"
					message="flat-view"
					method="get"
					url="<%= currentURLObj.toString() %>"
				/>
			</td>
		</c:if>

		<c:if test="<%= ArrayUtil.contains(PropsValues.MESSAGE_BOARDS_THREAD_VIEWS, MBThreadConstants.THREAD_VIEW_TREE) %>">
			<td class="thread-icon">

				<%
				currentURLObj.setParameter("threadView", MBThreadConstants.THREAD_VIEW_TREE);
				%>

				<liferay-ui:icon
					image="../message_boards/thread_view_tree"
					message="tree-view"
					method="get"
					url="<%= currentURLObj.toString() %>"
				/>
			</td>
		</c:if>
	</c:if>
</tr>
</table>

<div class="thread-controls">
	<c:if test="<%= PropsValues.MESSAGE_BOARDS_THREAD_PREVIOUS_AND_NEXT_NAVIGATION_ENABLED %>">
		<div class="thread-navigation">
			<liferay-ui:message key="threads" />

			[

			<c:choose>
				<c:when test="<%= previousThread != null %>">
					<portlet:renderURL var="previousThreadURL">
						<portlet:param name="struts_action" value="/message_boards/view_message" />
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
						<portlet:param name="struts_action" value="/message_boards/view_message" />
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
		<table class="lfr-table">
		<tr>
			<c:if test="<%= MBCategoryPermission.contains(permissionChecker, scopeGroupId, (category != null) ? category.getCategoryId() : MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID, ActionKeys.ADD_MESSAGE) %>">
				<td>
					<portlet:renderURL var="addMessageURL">
						<portlet:param name="struts_action" value="/message_boards/edit_message" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="mbCategoryId" value="<%= (category != null) ? String.valueOf(category.getCategoryId()) : String.valueOf(MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) %>" />
					</portlet:renderURL>

					<liferay-ui:icon
						image="post"
						label="<%= true %>"
						message="post-new-thread"
						url="<%= addMessageURL %>"
					/>
				</td>
			</c:if>

			<c:if test="<%= MBMessagePermission.contains(permissionChecker, message, ActionKeys.SUBSCRIBE) %>">
				<td>
					<c:choose>
						<c:when test="<%= SubscriptionLocalServiceUtil.isSubscribed(user.getCompanyId(), user.getUserId(), MBThread.class.getName(), message.getThreadId()) %>">
							<portlet:actionURL var="unsubscribeURL">
								<portlet:param name="struts_action" value="/message_boards/edit_message" />
								<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.UNSUBSCRIBE %>" />
								<portlet:param name="redirect" value="<%= currentURL %>" />
								<portlet:param name="messageId" value="<%= String.valueOf(message.getMessageId()) %>" />
							</portlet:actionURL>

							<liferay-ui:icon
								image="unsubscribe"
								label="<%= true %>"
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
								label="<%= true %>"
								url="<%= subscribeURL %>"
							/>
						</c:otherwise>
					</c:choose>
				</td>
			</c:if>

			<c:if test="<%= MBCategoryPermission.contains(permissionChecker, scopeGroupId, (category != null) ? category.getCategoryId() : MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID, ActionKeys.LOCK_THREAD) %>">
				<td>
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
								label="<%= true %>"
								message="unlock-thread"
								url="<%= unlockThreadURL %>"
							/>
						</c:when>
						<c:otherwise>
							<portlet:actionURL var="lockThreadURL">
								<portlet:param name="struts_action" value="/message_boards/edit_message" />
								<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.LOCK %>" />
								<portlet:param name="redirect" value="<%= currentURL %>" />
								<portlet:param name="threadId" value="<%= String.valueOf(message.getThreadId()) %>" />
							</portlet:actionURL>

							<liferay-ui:icon
								image="lock"
								label="<%= true %>"
								message="lock-thread"
								url="<%= lockThreadURL %>"
							/>
						</c:otherwise>
					</c:choose>
				</td>
			</c:if>

			<c:if test="<%= MBCategoryPermission.contains(permissionChecker, scopeGroupId, (category != null) ? category.getCategoryId() : MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID, ActionKeys.MOVE_THREAD) %>">
				<td>
					<portlet:renderURL var="editThreadURL">
						<portlet:param name="struts_action" value="/message_boards/move_thread" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
						<portlet:param name="mbCategoryId" value="<%= (category != null) ? String.valueOf(category.getCategoryId()) : String.valueOf(MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) %>" />
						<portlet:param name="threadId" value="<%= String.valueOf(message.getThreadId()) %>" />
					</portlet:renderURL>

					<liferay-ui:icon
						image="forward"
						label="<%= true %>"
						message="move-thread"
						url="<%= editThreadURL %>"
					/>
				</td>
			</c:if>
		</tr>
		</table>
	</div>

	<div class="clear"></div>
</div>

<div>

	<%
	MBTreeWalker treeWalker = messageDisplay.getTreeWalker();

	List<MBMessage> messages = null;

	if (treeWalker != null) {
		messages = new ArrayList<MBMessage>();

		messages.addAll(treeWalker.getMessages());

		messages = ListUtil.sort(messages, new MessageCreateDateComparator(true));
	}

	AssetUtil.addLayoutTags(request, AssetTagLocalServiceUtil.getTags(MBMessage.class.getName(), thread.getRootMessageId()));
	%>

	<div class="message-scroll" id="<portlet:namespace />message_0"></div>

	<c:if test='<%= threadView.equals(MBThreadConstants.THREAD_VIEW_COMBINATION) && (messages.size() > 1) %>'>
		<liferay-ui:toggle-area id="toggle_id_message_boards_view_message_thread">
			<table class="toggle_id_message_boards_view_message_thread">

			<%
			request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER, treeWalker);
			request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_SEL_MESSAGE, message);
			request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_CUR_MESSAGE, treeWalker.getRoot());
			request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_CATEGORY, category);
			request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_THREAD, thread);
			request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_THREAD_FLAG, threadFlag);
			request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_LAST_NODE, Boolean.valueOf(false));
			request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_DEPTH, new Integer(0));
			%>

			<liferay-util:include page="/html/portlet/message_boards/view_thread_shortcut.jsp" />

			</table>
		</liferay-ui:toggle-area>
	</c:if>

	<c:choose>
		<c:when test='<%= threadView.equals(MBThreadConstants.THREAD_VIEW_TREE) %>'>

			<%
			request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER, treeWalker);
			request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_SEL_MESSAGE, message);
			request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_CUR_MESSAGE, treeWalker.getRoot());
			request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_CATEGORY, category);
			request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_THREAD, thread);
			request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_LAST_NODE, Boolean.valueOf(false));
			request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_DEPTH, new Integer(0));
			%>

			<liferay-util:include page="/html/portlet/message_boards/view_thread_tree.jsp" />
		</c:when>
		<c:otherwise>
			<%@ include file="/html/portlet/message_boards/view_thread_flat.jspf" %>
		</c:otherwise>
	</c:choose>
</div>