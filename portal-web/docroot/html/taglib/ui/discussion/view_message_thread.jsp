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

<%@ include file="/html/taglib/ui/discussion/init.jsp" %>

<%
Comment comment = (Comment)request.getAttribute("liferay-ui:discussion:currentComment");
Discussion discussion = (Discussion)request.getAttribute("liferay-ui:discussion:discussion");

int index = GetterUtil.getInteger(request.getAttribute("liferay-ui:discussion:index"));

index++;

request.setAttribute("liferay-ui:discussion:index", new Integer(index));

String randomNamespace = (String)request.getAttribute("liferay-ui:discussion:randomNamespace");

DiscussionRequestHelper discussionRequestHelper = new DiscussionRequestHelper(request);
DiscussionTaglibHelper discussionTaglibHelper = new DiscussionTaglibHelper(request);

DiscussionPermission discussionPermission = CommentManagerUtil.getDiscussionPermission(discussionRequestHelper.getPermissionChecker());

CommentTreeDisplayContext commentTreeDisplayContext = CommentDisplayContextProviderUtil.getCommentTreeDisplayContext(request, response, discussionPermission, comment);

Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
%>

<c:if test="<%= commentTreeDisplayContext.isDiscussionVisible() %>">
	<article class="lfr-discussion">
		<div id="<%= randomNamespace %>messageScroll<%= comment.getCommentId() %>">
			<a name="<%= randomNamespace %>message_<%= comment.getCommentId() %>"></a>

			<aui:input name='<%= "messageId" + index %>' type="hidden" value="<%= comment.getCommentId() %>" />
			<aui:input name='<%= "parentMessageId" + index %>' type="hidden" value="<%= comment.getCommentId() %>" />
		</div>

		<div class="lfr-discussion-details">
			<liferay-ui:user-display
				author="<%= discussionTaglibHelper.getUserId() == comment.getUserId() %>"
				displayStyle="2"
				showUserName="<%= false %>"
				userId="<%= comment.getUserId() %>"
			/>
		</div>

		<div class="lfr-discussion-body">
			<c:if test="<%= commentTreeDisplayContext.isWorkflowStatusVisible() %>">

				<%
				WorkflowableComment workflowableComment = (WorkflowableComment)comment;
				%>

				<aui:model-context bean="<%= workflowableComment %>" model="<%= workflowableComment.getModelClass() %>" />

				<div>
					<aui:workflow-status model="<%= CommentConstants.getDiscussionClass() %>" status="<%= workflowableComment.getStatus() %>" />
				</div>
			</c:if>

			<div class="lfr-discussion-message">
				<header class="lfr-discussion-message-author">

					<%
					User messageUser = comment.getUser();
					%>

					<aui:a href="<%= (messageUser != null) ? messageUser.getDisplayURL(themeDisplay) : null %>">
						<%= HtmlUtil.escape(comment.getUserName()) %>

						<c:if test="<%= comment.getUserId() == user.getUserId() %>">
							(<liferay-ui:message key="you" />)
						</c:if>
					</aui:a>

					<%
					Comment rootComment = discussion.getRootComment();

					Date createDate = comment.getCreateDate();

					String createDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - createDate.getTime(), true);
					%>

					<c:choose>
						<c:when test="<%= comment.getParentCommentId() == rootComment.getCommentId() %>">
							<liferay-ui:message arguments="<%= createDateDescription %>" key="x-ago" translateArguments="<%= false %>" />
						</c:when>
						<c:otherwise>

							<%
							Comment parentComment = comment.getParentComment();
							%>

							<liferay-util:buffer var="parentCommentUserBuffer">

								<%
								User parentMessageUser = parentComment.getUser();

								boolean male = (parentMessageUser == null) ? true : parentMessageUser.isMale();
								long portraitId = (parentMessageUser == null) ? 0 : parentMessageUser.getPortraitId();
								String userUuid = (parentMessageUser == null) ? null : parentMessageUser.getUserUuid();
								%>

								<span>
									<div class="lfr-discussion-reply-user-avatar">
										<img alt="<%= HtmlUtil.escapeAttribute(parentComment.getUserName()) %>" class="user-status-avatar-image" src="<%= UserConstants.getPortraitURL(themeDisplay.getPathImage(), male, portraitId, userUuid) %>" width="30" />
									</div>

									<div class="lfr-discussion-reply-user-name">
										<%= parentComment.getUserName() %>
									</div>

									<div class="lfr-discussion-reply-creation-date">
										<%= dateFormatDateTime.format(parentComment.getCreateDate()) %>
									</div>
								</span>
							</liferay-util:buffer>

							<liferay-util:buffer var="parentCommentBodyBuffer">
								<a class="lfr-discussion-parent-link" data-metadata="<%= HtmlUtil.escape(parentComment.getBody()) %>" data-title="<%= HtmlUtil.escape(parentCommentUserBuffer) %>">
									<%= HtmlUtil.escape(parentComment.getUserName()) %>
								</a>
							</liferay-util:buffer>

							<%= LanguageUtil.format(request, "x-ago-in-reply-to-x", new Object[] {createDateDescription, parentCommentBodyBuffer}, false) %>
						</c:otherwise>
					</c:choose>

					<%
					Date modifiedDate = comment.getModifiedDate();
					%>

					<c:if test="<%= createDate.before(modifiedDate) %>">
						<strong onmouseover="Liferay.Portal.ToolTip.show(this, '<%= HtmlUtil.escapeJS(dateFormatDateTime.format(modifiedDate)) %>');">
							- <liferay-ui:message key="edited" />
						</strong>
					</c:if>
				</header>

				<div class="lfr-discussion-message-body" id='<portlet:namespace /><%= randomNamespace + "discussionMessage" + index %>'>
					<%= comment.getTranslatedBody() %>
				</div>

				<c:if test="<%= commentTreeDisplayContext.isEditControlsVisible() %>">
					<div class="lfr-discussion-form lfr-discussion-form-edit" id="<%= namespace + randomNamespace %>editForm<%= index %>" style='<%= "display: none; max-width: " + ModelHintsConstants.TEXTAREA_DISPLAY_WIDTH + "px;" %>'>
						<liferay-ui:input-editor autoCreate="<%= false %>" configKey="commentsEditor" contents="<%= comment.getBody() %>" editorName='<%= PropsUtil.get("editor.wysiwyg.portal-web.docroot.html.taglib.ui.discussion.jsp") %>' name='<%= randomNamespace + "editReplyBody" + index %>' onChangeMethod='<%= randomNamespace + index + "EditOnChange" %>' showSource="<%= false %>" 			/>

						<aui:input name='<%= "editReplyBody" + index %>' type="hidden" value="<%= comment.getBody() %>" />

						<aui:button-row>
							<aui:button name='<%= randomNamespace + "editReplyButton" + index %>' onClick='<%= randomNamespace + "updateMessage(" + index + ");" %>' value="<%= commentTreeDisplayContext.getPublishButtonLabel(locale) %>" />

							<%
							String taglibCancel = randomNamespace + "showEl('" + namespace + randomNamespace + "discussionMessage" + index + "');" + randomNamespace + "hideEditor('" + namespace + randomNamespace + "editReplyBody" + index + "','" + namespace + randomNamespace + "editForm" + index + "');";
							%>

							<aui:button onClick="<%= taglibCancel %>" type="cancel" />
						</aui:button-row>

						<aui:script>
							window['<%= namespace + randomNamespace + index %>EditOnChange'] = function(html) {
								Liferay.Util.toggleDisabled('#<%= namespace + randomNamespace %>editReplyButton<%= index %>', html.trim() === '');
							};
						</aui:script>
					</div>
				</c:if>
			</div>

			<div class="lfr-discussion-controls">
				<c:if test="<%= commentTreeDisplayContext.isRatingsVisible() %>">
					<liferay-ui:ratings
						className="<%= CommentConstants.getDiscussionClassName() %>"
						classPK="<%= comment.getCommentId() %>"
						ratingsEntry="<%= comment.getRatingsEntry() %>"
						ratingsStats="<%= comment.getRatingsStats() %>"
					/>
				</c:if>

				<c:if test="<%= commentTreeDisplayContext.isActionControlsVisible() %>">
					<c:if test="<%= commentTreeDisplayContext.isReplyActionControlVisible() %>">

						<%
						String taglibPostReplyURL = "javascript:"
							+ randomNamespace + "showEditor('" + namespace + randomNamespace + "postReplyBody" + index + "','" + namespace + randomNamespace + "postReplyForm" + index + "'); "
							+ randomNamespace + "hideEditor('" + namespace + randomNamespace + "editReplyBody" + index + "','" + namespace + randomNamespace + "editForm" + index + "');" + randomNamespace + "showEl('" + namespace + randomNamespace + "discussionMessage" + index + "')";
						%>

						<c:if test="<%= !discussion.isMaxCommentsLimitExceeded() %>">
							<c:choose>
								<c:when test="<%= commentTreeDisplayContext.isReplyButtonVisible() %>">
									<liferay-ui:icon
										label="<%= true %>"
										message="reply"
										url="<%= taglibPostReplyURL %>"
									/>
								</c:when>
								<c:otherwise>
									<liferay-ui:icon
										label="<%= true %>"
										message="please-sign-in-to-reply"
										url="<%= themeDisplay.getURLSignIn() %>"
									/>
								</c:otherwise>
							</c:choose>
						</c:if>
					</c:if>

					<ul class="lfr-discussion-actions">
						<c:if test="<%= index > 0 %>">

							<c:if test="<%= commentTreeDisplayContext.isEditActionControlVisible() %>">

								<%
								String taglibEditURL = "javascript:"
									+ randomNamespace + "showEditor('" + namespace + randomNamespace + "editReplyBody" + index + "','" + namespace + randomNamespace + "editForm" + index + "'); "
									+ randomNamespace + "hideEditor('" + namespace + randomNamespace + "postReplyBody" + index + "','" + namespace + randomNamespace + "postReplyForm" + index + "');" + randomNamespace + "hideEl('" + namespace + randomNamespace + "discussionMessage" + index + "');";
								%>

								<li class="lfr-discussion-edit">
									<liferay-ui:icon
										iconCssClass="icon-edit"
										label="<%= true %>"
										message="edit"
										url="<%= taglibEditURL %>"
									/>
								</li>
							</c:if>

							<c:if test="<%= commentTreeDisplayContext.isDeleteActionControlVisible() %>">

								<%
								String taglibDeleteURL = "javascript:" + randomNamespace + "deleteMessage(" + index + ");";
								%>

								<li class="lfr-discussion-delete">
									<liferay-ui:icon-delete
										label="<%= true %>"
										url="<%= taglibDeleteURL %>"
									/>
								</li>
							</c:if>
						</c:if>
					</ul>
				</c:if>
			</div>
		</div>

		<div class="lfr-discussion-form-container">
			<div class="lfr-discussion lfr-discussion-form-reply" id='<portlet:namespace /><%= randomNamespace + "postReplyForm" + index %>' style="display: none;">
				<div class="lfr-discussion-details">
					<liferay-ui:user-display
						displayStyle="2"
						showUserName="<%= false %>"
						userId="<%= user.getUserId() %>"
					/>
				</div>

				<div class="lfr-discussion-body">
					<liferay-ui:input-editor autoCreate="<%= false %>" configKey="commentsEditor" contents="" editorName='<%= PropsUtil.get("editor.wysiwyg.portal-web.docroot.html.taglib.ui.discussion.jsp") %>' name='<%= randomNamespace + "postReplyBody" + index %>' onChangeMethod='<%= randomNamespace + index + "ReplyOnChange" %>' placeholder="type-your-comment-here" showSource="<%= false %>" />

					<aui:input name='<%= "postReplyBody" + index %>' type="hidden" />

					<aui:button-row>
						<aui:button cssClass="btn-comment btn-primary" disabled="<%= true %>" id='<%= randomNamespace + "postReplyButton" + index %>' onClick='<%= randomNamespace + "postReply(" + index + ");" %>' value='<%= themeDisplay.isSignedIn() ? "reply" : "reply-as" %>' />

						<%
						String taglibCancel = randomNamespace + "hideEditor('" + namespace + randomNamespace + "postReplyBody" + index + "','" + namespace + randomNamespace + "postReplyForm" + index + "')";
						%>

						<aui:button cssClass="btn-comment" onClick="<%= taglibCancel %>" type="cancel" />
					</aui:button-row>

					<aui:script>
						window['<%= namespace + randomNamespace + index %>ReplyOnChange'] = function(html) {
							Liferay.Util.toggleDisabled('#<%= namespace + randomNamespace %>postReplyButton<%= index %>', html.trim() == '');
						};
					</aui:script>
				</div>
			</div>
		</div>

		<%
		for (Comment curComment : comment.getThreadComments()) {
			request.setAttribute("liferay-ui:discussion:currentComment", curComment);
		%>

			<liferay-util:include page="/html/taglib/ui/discussion/view_message_thread.jsp" />

		<%
		}
		%>

	</article>
</c:if>