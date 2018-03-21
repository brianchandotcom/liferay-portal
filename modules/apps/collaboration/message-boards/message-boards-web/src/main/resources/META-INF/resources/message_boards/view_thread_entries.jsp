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
SearchContainer entriesSearchContainer = (SearchContainer)request.getAttribute("view.jsp-threadEntriesSearchContainer");
%>

<liferay-ui:search-container
	searchContainer="<%= entriesSearchContainer %>"
>
	<liferay-ui:search-container-row
		className="Object"
		escapedModel="<%= true %>"
		keyProperty="categoryId"
		modelVar="result"
	>
		<%@ include file="/message_boards/cast_result.jspf" %>

		<%
		MBMessage message = MBMessageLocalServiceUtil.fetchMBMessage(thread.getRootMessageId());

		if (message == null) {
			_log.error("Thread requires missing root message id " + thread.getRootMessageId());

			row.setSkip(true);
		}

		if (message != null) {
			message = message.toEscapedModel();

			row.setPrimaryKey(String.valueOf(thread.getThreadId()));
			row.setRestricted(!MBMessagePermission.contains(permissionChecker, message, ActionKeys.VIEW));
		}
		%>

		<liferay-portlet:renderURL varImpl="rowURL">
			<portlet:param name="mvcRenderCommandName" value="/message_boards/view_message" />
			<portlet:param name="messageId" value="<%= (message != null) ? String.valueOf(message.getMessageId()) : String.valueOf(MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID) %>" />
		</liferay-portlet:renderURL>

		<liferay-ui:search-container-column-text>
			<liferay-ui:user-portrait
				userId="<%= thread.getLastPostByUserId() %>"
			/>
		</liferay-ui:search-container-column-text>

		<liferay-ui:search-container-column-text
			colspan="<%= 2 %>"
		>
			<c:choose>
				<c:when test="<%= ((message != null) && (thread.getMessageCount() == 1)) %>">

					<%
					String messageUserName = "anonymous";

					if (!message.isAnonymous()) {
						messageUserName = message.getUserName();
					}

					Date modifiedDate = message.getModifiedDate();

					String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - modifiedDate.getTime(), true);
					%>

					<h5 class="text-default">
						<liferay-ui:message arguments="<%= new String[] {messageUserName, modifiedDateDescription} %>" key="x-modified-x-ago" />
					</h5>
				</c:when>
				<c:otherwise>

					<%
					String messageUserName = "anonymous";

					if (thread.getLastPostByUserId() != 0) {
						messageUserName = HtmlUtil.escape(PortalUtil.getUserName(thread.getLastPostByUserId(), StringPool.BLANK));
					}

					Date lastPostDate = thread.getLastPostDate();

					String lastPostDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - lastPostDate.getTime(), true);
					%>

					<h5 class="text-default">
						<liferay-ui:message arguments="<%= new String[] {messageUserName, lastPostDateDescription} %>" key="x-replied-x-ago" />
					</h5>
				</c:otherwise>
			</c:choose>

			<h4>
				<aui:a href="<%= rowURL.toString() %>">
					<c:if test="<%= (message != null) %>">
						<c:choose>
							<c:when test="<%= !MBThreadFlagLocalServiceUtil.hasThreadFlag(themeDisplay.getUserId(), thread) %>">
								<strong><%= message.getSubject() %></strong>
							</c:when>
							<c:otherwise>
								<%= message.getSubject() %>
							</c:otherwise>
						</c:choose>
					</c:if>
				</aui:a>

				<%
				String[] threadPriority = MBUtil.getThreadPriority(mbGroupServiceSettings, themeDisplay.getLanguageId(), thread.getPriority());
				%>

				<c:if test="<%= (threadPriority != null) && (thread.getPriority() > 0) %>">
					<span class="text-default <%= threadPriority[1] %>" title="<%= HtmlUtil.escapeAttribute(threadPriority[0]) %>"></span>
				</c:if>

				<c:if test="<%= thread.isQuestion() %>">
					<aui:icon cssClass="icon-monospaced" image="question-circle" markupView="lexicon" message="question" />
				</c:if>
			</h4>

			<c:if test="<%= ((message != null) && !message.isApproved()) %>">
				<span class="h6">
					<aui:workflow-status bean="<%= message %>" markupView="lexicon" model="<%= MBMessage.class %>" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= message.getStatus() %>" />
				</span>
			</c:if>

			<%
			int repliesCount = Math.max(thread.getMessageCount() - 1, 0);
			int viewCount = thread.getViewCount();
			%>

			<span class="h6 text-default">
				<liferay-ui:message arguments="<%= repliesCount %>" key='<%= repliesCount == 1 ? "x-reply" : "x-replies" %>' />
			</span>
			<span class="h6 text-default">
				<liferay-ui:message arguments="<%= viewCount %>" key='<%= viewCount == 1 ? "x-view" : "x-views" %>' />
			</span>

			<c:if test="<%= thread.isQuestion() %>">

				<%
				int threadAnswersCount = MBMessageServiceUtil.getThreadAnswersCount(thread.getGroupId(), thread.getCategoryId(), thread.getThreadId());
				%>

				<span class="h6">
					<%= threadAnswersCount %>

					<liferay-ui:message key='<%= threadAnswersCount == 1 ? "answer" : "answers" %>' />
				</span>
			</c:if>

			<c:if test="<%= thread.isLocked() %>">
				<span class="h6">
					<liferay-ui:message key="locked" />
				</span>
			</c:if>
		</liferay-ui:search-container-column-text>

		<%
		row.setObject(new Object[] {message});
		%>

		<c:if test="<%= (message != null) %>">
			<liferay-ui:search-container-column-jsp
				path="/message_boards/message_action.jsp"
			/>
		</c:if>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		displayStyle="descriptive"
		markupView="lexicon"
		resultRowSplitter="<%= new MBResultRowSplitter() %>"
	/>
</liferay-ui:search-container>

<%!
private static Log _log = LogFactoryUtil.getLog("com_liferay_message_boards_web.message_boards.view_thread_entries_jsp");
%>