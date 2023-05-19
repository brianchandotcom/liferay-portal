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
String randomNamespace = StringUtil.randomId() + StringPool.UNDERLINE;

boolean skipEditorLoading = ParamUtil.getBoolean(request, "skipEditorLoading");

DiscussionRequestHelper discussionRequestHelper = new DiscussionRequestHelper(request);
DiscussionTaglibHelper discussionTaglibHelper = new DiscussionTaglibHelper(request);

DiscussionPermission discussionPermission = CommentManagerUtil.getDiscussionPermission(discussionRequestHelper.getPermissionChecker());

Discussion discussion = (Discussion)request.getAttribute("liferay-comment:discussion:discussion");

if (discussion == null) {
	discussion = CommentManagerUtil.getDiscussion(user.getUserId(), discussionRequestHelper.getScopeGroupId(), discussionTaglibHelper.getClassName(), discussionTaglibHelper.getClassPK(), new ServiceContextFunction(renderRequest));
}

DiscussionComment rootDiscussionComment = (discussion == null) ? null : discussion.getRootDiscussionComment();

CommentSectionDisplayContext commentSectionDisplayContext = CommentDisplayContextProviderUtil.getCommentSectionDisplayContext(request, response, discussionPermission, discussion);
StagingGroupHelper stagingGroupHelper = StagingGroupHelperUtil.getStagingGroupHelper();
%>

<section>
	<div class="lfr-message-response" id="<%= randomNamespace %>discussionStatusMessages"></div>

	<c:if test="<%= (discussion != null) && discussion.isMaxCommentsLimitExceeded() %>">
		<div class="alert alert-warning">
			<liferay-ui:message key="maximum-number-of-comments-has-been-reached" />
		</div>
	</c:if>

	<c:if test="<%= commentSectionDisplayContext.isDiscussionVisible() %>">
		<div class="taglib-discussion" id="<%= namespace %>discussionContainer">
			<aui:form action="<%= discussionTaglibHelper.getFormAction() %>" method="post" name="<%= discussionTaglibHelper.getFormName() %>" portletNamespace="<%= namespace + randomNamespace %>">
				<input name="p_auth" type="hidden" value="<%= AuthTokenUtil.getToken(request) %>" />
				<input name="namespace" type="hidden" value="<%= namespace + randomNamespace %>" />

				<%
				String contentURL = PortalUtil.getCanonicalURL(discussionTaglibHelper.getRedirect(), themeDisplay, layout);

				contentURL = HttpComponentsUtil.removeParameter(contentURL, namespace + "skipEditorLoading");
				%>

				<input name="contentURL" type="hidden" value="<%= contentURL %>" />

				<aui:input name="randomNamespace" type="hidden" value="<%= randomNamespace %>" />
				<aui:input id="<%= Constants.CMD %>" name="<%= Constants.CMD %>" type="hidden" />
				<aui:input name="redirect" type="hidden" value="<%= discussionTaglibHelper.getRedirect() %>" />
				<aui:input name="assetEntryVisible" type="hidden" value="<%= discussionTaglibHelper.isAssetEntryVisible() %>" />
				<aui:input name="className" type="hidden" value="<%= discussionTaglibHelper.getClassName() %>" />
				<aui:input name="classPK" type="hidden" value="<%= discussionTaglibHelper.getClassPK() %>" />
				<aui:input name="commentId" type="hidden" />
				<aui:input name="parentCommentId" type="hidden" />
				<aui:input name="body" type="hidden" />
				<aui:input name="workflowAction" type="hidden" value="<%= String.valueOf(WorkflowConstants.ACTION_PUBLISH) %>" />
				<aui:input name="ajax" type="hidden" value="<%= true %>" />

				<c:if test="<%= commentSectionDisplayContext.isControlsVisible() %>">
					<aui:fieldset cssClass="add-comment" id='<%= randomNamespace + "messageScroll0" %>'>
						<c:if test="<%= !discussion.isMaxCommentsLimitExceeded() %>">
							<div id="<%= randomNamespace %>messageScroll<%= rootDiscussionComment.getCommentId() %>">
								<aui:input name="commentId0" type="hidden" value="<%= rootDiscussionComment.getCommentId() %>" />
								<aui:input name="parentCommentId0" type="hidden" value="<%= rootDiscussionComment.getCommentId() %>" />
							</div>
						</c:if>

						<%
						Group siteGroup = themeDisplay.getSiteGroup();

						boolean canSubscribe = !stagingGroupHelper.isLocalStagingGroup(siteGroup) && !stagingGroupHelper.isRemoteStagingGroup(siteGroup) && themeDisplay.isSignedIn() && discussionPermission.hasSubscribePermission(company.getCompanyId(), siteGroup.getGroupId(), discussionTaglibHelper.getClassName(), discussionTaglibHelper.getClassPK());

						boolean subscribed = SubscriptionLocalServiceUtil.isSubscribed(company.getCompanyId(), user.getUserId(), discussionTaglibHelper.getSubscriptionClassName(), discussionTaglibHelper.getClassPK());

						String subscriptionOnClick = randomNamespace + "subscribeToComments(" + !subscribed + ");";
						%>

						<clay:content-row
							cssClass="mb-4"
							floatElements="end"
						>
							<clay:content-col
								containerElement="span"
								cssClass="text-secondary text-uppercase"
								expand="<%= true %>"
							>
								<strong><liferay-ui:message arguments="<%= discussion.getDiscussionCommentsCount() %>" key='<%= (discussion.getDiscussionCommentsCount() == 1) ? "x-comment" : "x-comments" %>' /></strong>
							</clay:content-col>

							<clay:content-col>
								<c:if test="<%= canSubscribe %>">
									<c:choose>
										<c:when test="<%= subscribed %>">
											<button aria-label="<liferay-ui:message key="unsubscribe-from-comments" />" class="btn btn-outline-primary btn-sm" onclick="<%= subscriptionOnClick %>" type="button">
												<liferay-ui:message key="unsubscribe" />
											</button>
										</c:when>
										<c:otherwise>
											<button aria-label="<liferay-ui:message key="subscribe-to-comments" />" class="btn btn-outline-primary btn-sm" onclick="<%= subscriptionOnClick %>" type="button">
												<liferay-ui:message key="subscribe" />
											</button>
										</c:otherwise>
									</c:choose>
								</c:if>
							</clay:content-col>
						</clay:content-row>

						<c:if test="<%= !discussion.isMaxCommentsLimitExceeded() %>">
							<aui:input name="emailAddress" type="hidden" />

							<c:choose>
								<c:when test="<%= commentSectionDisplayContext.isReplyButtonVisible() %>">
									<div class="lfr-discussion-reply-container">
										<clay:content-row
											noGutters="true"
										>
											<clay:content-col
												cssClass="lfr-discussion-details"
											>
												<liferay-ui:user-portrait
													user="<%= user %>"
												/>
											</clay:content-col>

											<clay:content-col
												cssClass="lfr-discussion-editor"
												expand="<%= true %>"
											>
												<liferay-editor:editor
													configKey="commentEditor"
													contents=""
													editorName="ckeditor"
													name="postReplyBody0"
													onChangeMethod="0ReplyOnChange"
													placeholder="type-your-comment-here"
													showSource="<%= false %>"
													skipEditorLoading="<%= skipEditorLoading %>"
												/>

												<aui:button-row>
													<aui:button cssClass="btn-comment btn-sm" disabled="<%= true %>" id="postReplyButton0" onClick='<%= randomNamespace + "postReply(0);" %>' primary="<%= true %>" value='<%= themeDisplay.isSignedIn() ? "reply" : "reply-as" %>' />
												</aui:button-row>
											</clay:content-col>
										</clay:content-row>
									</div>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="<%= stagingGroupHelper.isLocalStagingGroup(siteGroup) || stagingGroupHelper.isRemoteStagingGroup(siteGroup) %>">
											<div class="alert alert-info">
												<span class="alert-indicator">
													<svg class="lexicon-icon lexicon-icon-info-circle" focusable="false" role="presentation">
														<use xlink:href="<%= themeDisplay.getPathThemeSpritemap() %>#info-circle" />
													</svg>
												</span>

												<strong class="lead">INFO:</strong><liferay-ui:message key="comments-are-unavailable-in-staged-sites" />
											</div>
										</c:when>
										<c:otherwise>
											<liferay-ui:icon
												icon="reply"
												label="<%= true %>"
												markupView="lexicon"
												message="please-sign-in-to-comment"
												url="<%= themeDisplay.getURLSignIn() %>"
											/>
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
						</c:if>
					</aui:fieldset>
				</c:if>

				<c:if test="<%= commentSectionDisplayContext.isMessageThreadVisible() %>">
					<a name="<%= randomNamespace %>messages_top"></a>

					<div>

						<%
						int index = 0;
						int rootIndexPage = 0;
						boolean moreCommentsPagination = false;

						DiscussionCommentIterator discussionCommentIterator = rootDiscussionComment.getThreadDiscussionCommentIterator();

						while (discussionCommentIterator.hasNext()) {
							index = GetterUtil.getInteger(request.getAttribute("liferay-comment:discussion:index"), 1);

							rootIndexPage = discussionCommentIterator.getIndexPage();

							if ((index + 1) > PropsValues.DISCUSSION_COMMENTS_DELTA_VALUE) {
								moreCommentsPagination = true;

								break;
							}

							request.setAttribute("liferay-comment:discussion:depth", 0);
							request.setAttribute("liferay-comment:discussion:discussion", discussion);
							request.setAttribute("liferay-comment:discussion:discussionComment", discussionCommentIterator.next());
							request.setAttribute("liferay-comment:discussion:randomNamespace", randomNamespace);
						%>

							<liferay-util:include page="/discussion/view_message_thread.jsp" servletContext="<%= application %>" />

						<%
						}
						%>

						<c:if test="<%= moreCommentsPagination %>">
							<div class="lfr-discussion-more-comments" id="<%= namespace %>moreCommentsContainer">
								<button class="btn btn-secondary btn-sm" id="<%= namespace %>moreCommentsTrigger" type="button"><liferay-ui:message key="more-comments" /></button>

								<aui:input name="rootIndexPage" type="hidden" value="<%= String.valueOf(rootIndexPage) %>" />
								<aui:input name="index" type="hidden" value="<%= String.valueOf(index) %>" />
							</div>
						</c:if>
					</div>
				</c:if>
			</aui:form>
		</div>

		<%
		PortletURL loginURL = PortletURLBuilder.create(
			PortletURLFactoryUtil.create(request, PortletKeys.FAST_LOGIN, PortletRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			"/login/login"
		).setParameter(
			"saveLastPath", false
		).setPortletMode(
			PortletMode.VIEW
		).setWindowState(
			LiferayWindowState.POP_UP
		).buildPortletURL();

		String editorURL = GetterUtil.getString(request.getAttribute("liferay-comment:discussion:editorURL"));

		editorURL = HttpComponentsUtil.addParameter(editorURL, "namespace", namespace);

		String paginationURL = HttpComponentsUtil.addParameter(discussionTaglibHelper.getPaginationURL(), "namespace", namespace);

		paginationURL = HttpComponentsUtil.addParameter(paginationURL, "skipEditorLoading", "true");
		%>

		<liferay-frontend:component
			context='<%=
				HashMapBuilder.<String, Object>put(
					"constants",
					HashMapBuilder.<String, Object>put(
						"ACTION_SAVE_DRAFT", WorkflowConstants.ACTION_SAVE_DRAFT
					).put(
						"ADD", Constants.ADD
					).put(
						"DELETE", Constants.DELETE
					).put(
						"SUBSCRIBE_TO_COMMENTS", Constants.SUBSCRIBE_TO_COMMENTS
					).put(
						"UNSUBSCRIBE_FROM_COMMENTS", Constants.UNSUBSCRIBE_FROM_COMMENTS
					).put(
						"UPDATE", Constants.UPDATE
					).build()
				).put(
					"editorURL", editorURL
				).put(
					"formName", HtmlUtil.escapeJS(discussionTaglibHelper.getFormName())
				).put(
					"hideControls", discussionTaglibHelper.isHideControls()
				).put(
					"loginURL", loginURL.toString()
				).put(
					"messageId", ParamUtil.getString(request, "messageId")
				).put(
					"namespace", namespace
				).put(
					"paginationURL", paginationURL
				).put(
					"portletDisplayId", portletDisplay.getId()
				).put(
					"randomNamespace", randomNamespace
				).put(
					"ratingsEnabled", discussionTaglibHelper.isRatingsEnabled()
				).put(
					"subscriptionClassName", discussionTaglibHelper.getSubscriptionClassName()
				).put(
					"userId", discussionTaglibHelper.getUserId()
				).build()
			%>'
			module="discussion/js/Comments"
		/>
	</c:if>
</section>