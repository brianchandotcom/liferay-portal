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

<%@ include file="/discussion/init.jsp" %>

<%
int depth = GetterUtil.getInteger(request.getAttribute("liferay-comment:discussion:depth"));
Discussion discussion = (Discussion)request.getAttribute("liferay-comment:discussion:discussion");
DiscussionComment discussionComment = (DiscussionComment)request.getAttribute("liferay-comment:discussion:discussionComment");

int index = GetterUtil.getInteger(request.getAttribute("liferay-comment:discussion:index"));

index++;

request.setAttribute("liferay-comment:discussion:index", Integer.valueOf(index));

String randomNamespace = (String)request.getAttribute("liferay-comment:discussion:randomNamespace");

DiscussionComment rootDiscussionComment = discussion.getRootDiscussionComment();

DiscussionRequestHelper discussionRequestHelper = new DiscussionRequestHelper(request);

DiscussionPermission discussionPermission = CommentManagerUtil.getDiscussionPermission(discussionRequestHelper.getPermissionChecker());

CommentTreeDisplayContext commentTreeDisplayContext = CommentDisplayContextProviderUtil.getCommentTreeDisplayContext(request, response, discussionPermission, discussionComment);

Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
%>

<c:if test="<%= commentTreeDisplayContext.isDiscussionVisible() %>">
	<article class="lfr-discussion <%= (rootDiscussionComment.getCommentId() == discussionComment.getParentCommentId()) ? "card-tab-group" : "card-tab" %>">
		<div class="card list-group-card panel">
			<div class="panel-body">
				<div class="card-row">
					<div class="card-col-content">
						<div id="<%= randomNamespace %>messageScroll<%= discussionComment.getCommentId() %>">
							<a id="<%= randomNamespace %>message_<%= discussionComment.getCommentId() %>" name="<%= randomNamespace %>message_<%= discussionComment.getCommentId() %>"></a>

							<aui:input name='<%= "commentId" + index %>' type="hidden" value="<%= discussionComment.getCommentId() %>" />
							<aui:input name='<%= "parentCommentId" + index %>' type="hidden" value="<%= discussionComment.getCommentId() %>" />
						</div>

						<div class="lfr-discussion-details">
							<liferay-ui:user-portrait
								userId="<%= discussionComment.getUserId() %>"
								userName="<%= discussionComment.getUserName() %>"
							/>
						</div>

						<div class="lfr-discussion-body">
							<c:if test="<%= commentTreeDisplayContext.isWorkflowStatusVisible() %>">

								<%
								WorkflowableComment workflowableComment = (WorkflowableComment)discussionComment;
								%>

								<aui:model-context bean="<%= workflowableComment %>" model="<%= workflowableComment.getModelClass() %>" />

								<div>
									<aui:workflow-status model="<%= CommentConstants.getDiscussionClass() %>" status="<%= workflowableComment.getStatus() %>" />
								</div>
							</c:if>

							<div class="lfr-discussion-message">
								<header class="lfr-discussion-message-author">

									<%
									User messageUser = discussionComment.getUser();
									%>

									<aui:a cssClass="author-link" href="<%= ((messageUser != null) && messageUser.isActive()) ? messageUser.getDisplayURL(themeDisplay) : null %>">
										<%= HtmlUtil.escape(discussionComment.getUserName()) %>

										<c:if test="<%= discussionComment.getUserId() == user.getUserId() %>">
											(<liferay-ui:message key="you" />)
										</c:if>
									</aui:a>

									<%
									Date createDate = discussionComment.getCreateDate();

									String createDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - createDate.getTime(), true);
									%>

									<span class="small">
										<c:choose>
											<c:when test="<%= discussionComment.getParentCommentId() == rootDiscussionComment.getCommentId() %>">
												<liferay-ui:message arguments="<%= createDateDescription %>" key="x-ago" translateArguments="<%= false %>" />
											</c:when>
											<c:otherwise>

												<%
												DiscussionComment parentDiscussionComment = discussionComment.getParentComment();
												%>

												<liferay-util:buffer
													var="parentCommentUserBuffer"
												>

													<%
													User parentMessageUser = parentDiscussionComment.getUser();
													%>

													<span>
														<div class="lfr-discussion-reply-user-avatar">
															<liferay-ui:user-portrait
																user="<%= parentMessageUser %>"
															/>
														</div>

														<div class="lfr-discussion-reply-user-name">
															<%= HtmlUtil.escape(parentDiscussionComment.getUserName()) %>
														</div>

														<div class="lfr-discussion-reply-creation-date">
															<%= dateFormatDateTime.format(parentDiscussionComment.getCreateDate()) %>
														</div>
													</span>
												</liferay-util:buffer>

												<liferay-util:buffer
													var="parentCommentBodyBuffer"
												>
													<a class="lfr-discussion-parent-link" data-metadata="<%= HtmlUtil.escape(parentDiscussionComment.getBody()) %>" data-title="<%= HtmlUtil.escape(parentCommentUserBuffer) %>">
														<%= HtmlUtil.escape(parentDiscussionComment.getUserName()) %>
													</a>
												</liferay-util:buffer>

												<liferay-ui:message arguments="<%= new Object[] {createDateDescription, parentCommentBodyBuffer} %>" key="x-ago-in-reply-to-x" translateArguments="<%= false %>" />
											</c:otherwise>
										</c:choose>

										<%
										Date modifiedDate = discussionComment.getModifiedDate();
										%>

										<c:if test="<%= createDate.before(modifiedDate) %>">
											<strong onmouseover="Liferay.Portal.ToolTip.show(this, '<%= HtmlUtil.escapeJS(dateFormatDateTime.format(modifiedDate)) %>');">
												- <liferay-ui:message key="edited" />
											</strong>
										</c:if>
									</span>
								</header>

								<div class="lfr-discussion-message-body" id="<%= namespace + randomNamespace + "discussionMessage" + index %>">
									<%= discussionComment.getTranslatedBody(themeDisplay.getPathThemeImages()) %>
								</div>

								<c:if test="<%= commentTreeDisplayContext.isEditControlsVisible() %>">
									<div class="lfr-discussion-form lfr-discussion-form-edit" id="<%= namespace + randomNamespace %>editForm<%= index %>" style="<%= "display: none; max-width: " + ModelHintsConstants.TEXTAREA_DISPLAY_WIDTH + "px;" %>">
										<div class="editor-wrapper"></div>

										<aui:input name='<%= "editReplyBody" + index %>' type="hidden" value="<%= discussionComment.getBody() %>" />

										<aui:button-row>
											<aui:button name='<%= randomNamespace + "editReplyButton" + index %>' onClick='<%= randomNamespace + "updateMessage(" + index + ");" %>' value="<%= commentTreeDisplayContext.getPublishButtonLabel(locale) %>" />

											<%
											String taglibCancel = randomNamespace + "showEl('" + namespace + randomNamespace + "discussionMessage" + index + "');" + randomNamespace + "hideEditor('" + randomNamespace + "editReplyBody" + index + "', '" + namespace + randomNamespace + "editForm" + index + "');";
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
										classPK="<%= discussionComment.getCommentId() %>"
										inTrash="<%= false %>"
										ratingsEntry="<%= discussionComment.getRatingsEntry() %>"
										ratingsStats="<%= discussionComment.getRatingsStats() %>"
									/>
								</c:if>

								<c:if test="<%= commentTreeDisplayContext.isActionControlsVisible() && commentTreeDisplayContext.isReplyActionControlVisible() %>">
									<c:if test="<%= !discussion.isMaxCommentsLimitExceeded() %>">
										<c:choose>
											<c:when test="<%= commentTreeDisplayContext.isReplyButtonVisible() %>">
												<div class="reply-action">
													<liferay-ui:icon
														label="<%= true %>"
														message="reply"
														url='<%= "javascript:" + randomNamespace + "showPostReplyEditor(" + index + ");" %>'
													/>
												</div>
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
							</div>
						</div>
					</div>

					<c:if test="<%= commentTreeDisplayContext.isActionControlsVisible() && (index > 0) %>">
						<div class="card-col-field">
							<liferay-ui:icon-menu
								direction="left-side"
								icon="<%= StringPool.BLANK %>"
								markupView="lexicon"
								message="<%= StringPool.BLANK %>"
								showWhenSingleIcon="<%= true %>"
							>
								<c:if test="<%= commentTreeDisplayContext.isEditActionControlVisible() %>">
									<liferay-ui:icon
										message="edit"
										url='<%= "javascript:" + randomNamespace + "showEditReplyEditor(" + index + ");" %>'
									/>
								</c:if>

								<c:if test="<%= commentTreeDisplayContext.isDeleteActionControlVisible() %>">
									<liferay-ui:icon-delete
										label="<%= true %>"
										url='<%= "javascript:" + randomNamespace + "deleteMessage(" + index + ");" %>'
									/>
								</c:if>
							</liferay-ui:icon-menu>
						</div>
					</c:if>
				</div>
			</div>
		</div>

		<div class="card-tab lfr-discussion lfr-discussion-form-reply" id="<%= namespace + randomNamespace + "postReplyForm" + index %>" style="display: none;">
			<div class="card list-group-card panel">
				<div class="panel-body">
					<div class="lfr-discussion-details">
						<liferay-ui:user-portrait
							user="<%= user %>"
						/>
					</div>

					<div class="lfr-discussion-body">
						<div class="editor-wrapper"></div>

						<aui:input name='<%= "postReplyBody" + index %>' type="hidden" />

						<aui:button-row>
							<aui:button cssClass="btn-comment btn-primary" disabled="<%= true %>" id='<%= randomNamespace + "postReplyButton" + index %>' onClick='<%= randomNamespace + "postReply(" + index + ");" %>' value='<%= themeDisplay.isSignedIn() ? "reply" : "reply-as" %>' />

							<%
							String taglibCancel = randomNamespace + "hideEditor('" + randomNamespace + "postReplyBody" + index + "', '" + namespace + randomNamespace + "postReplyForm" + index + "')";
							%>

							<aui:button cssClass="btn-comment" onClick="<%= taglibCancel %>" type="cancel" />
						</aui:button-row>

						<aui:script>
							window['<%= namespace + randomNamespace + index %>ReplyOnChange'] = function(html) {
								Liferay.Util.toggleDisabled('#<%= namespace + randomNamespace %>postReplyButton<%= index %>', html.trim() === '');
							};
						</aui:script>
					</div>
				</div>
			</div>
		</div>

		<%
		for (DiscussionComment curDiscussionComment : discussionComment.getDescendantComments()) {
			request.setAttribute("liferay-comment:discussion:depth", depth + 1);
			request.setAttribute("liferay-comment:discussion:discussionComment", curDiscussionComment);
		%>

			<liferay-util:include page="/discussion/view_message_thread.jsp" servletContext="<%= application %>" />

		<%
		}
		%>

	</article>
</c:if>