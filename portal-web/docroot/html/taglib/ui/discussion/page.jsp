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
String randomNamespace = StringUtil.randomId() + StringPool.UNDERLINE;

DiscussionRequestHelper discussionRequestHelper = new DiscussionRequestHelper(request);

CommentSectionDisplayContext commentSectionDisplayContext = new MBCommentSectionDisplayContext(discussionRequestHelper);

boolean assetEntryVisible = discussionRequestHelper.isAssetEntryVisible();
String className = discussionRequestHelper.getClassName();
long classPK = discussionRequestHelper.getClassPK();
String formAction = discussionRequestHelper.getFormAction();
String formName = discussionRequestHelper.getFormName();
boolean hideControls = discussionRequestHelper.isHideControls();
String paginationURL = discussionRequestHelper.getPaginationURL();
String permissionClassName = discussionRequestHelper.getPermissionClassName();
long permissionClassPK = discussionRequestHelper.getPermissionClassPK();
boolean ratingsEnabled = discussionRequestHelper.isRatingsEnabled();
String redirect = discussionRequestHelper.getRedirect();
long userId = discussionRequestHelper.getUserId();

MBMessageDisplay messageDisplay = MBMessageLocalServiceUtil.getDiscussionMessageDisplay(userId, scopeGroupId, className, classPK, WorkflowConstants.STATUS_ANY, new MessageThreadComparator());

MBThread thread = messageDisplay.getThread();
MBTreeWalker treeWalker = messageDisplay.getTreeWalker();
MBMessage rootMessage = treeWalker.getRoot();
List<MBMessage> messages = treeWalker.getMessages();
int messagesCount = messages.size();
%>

<section>
	<div class="hide lfr-message-response" id="<portlet:namespace />discussionStatusMessages"></div>

	<c:if test="<%= messageDisplay.isDiscussionMaxComments() %>">
		<div class="alert alert-warning">
			<liferay-ui:message key="maximum-number-of-comments-has-been-reached" />
		</div>
	</c:if>

	<c:if test="<%= commentSectionDisplayContext.isDiscussionVisible() %>">
		<div class="taglib-discussion" id="<portlet:namespace />discussionContainer">
			<aui:form action="<%= formAction %>" method="post" name="<%= formName %>">
				<aui:input name="randomNamespace" type="hidden" value="<%= randomNamespace %>" />
				<aui:input id="<%= randomNamespace + Constants.CMD %>" name="<%= Constants.CMD %>" type="hidden" />
				<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
				<aui:input name="contentURL" type="hidden" value="<%= PortalUtil.getCanonicalURL(redirect, themeDisplay, layout) %>" />
				<aui:input name="assetEntryVisible" type="hidden" value="<%= assetEntryVisible %>" />
				<aui:input name="className" type="hidden" value="<%= className %>" />
				<aui:input name="classPK" type="hidden" value="<%= classPK %>" />
				<aui:input name="permissionClassName" type="hidden" value="<%= permissionClassName %>" />
				<aui:input name="permissionClassPK" type="hidden" value="<%= permissionClassPK %>" />
				<aui:input name="permissionOwnerId" type="hidden" value="<%= String.valueOf(userId) %>" />
				<aui:input name="messageId" type="hidden" />
				<aui:input name="threadId" type="hidden" value="<%= thread.getThreadId() %>" />
				<aui:input name="parentMessageId" type="hidden" />
				<aui:input name="body" type="hidden" />
				<aui:input name="workflowAction" type="hidden" value="<%= String.valueOf(WorkflowConstants.ACTION_PUBLISH) %>" />
				<aui:input name="ajax" type="hidden" value="<%= true %>" />

				<%
				MBMessage message = rootMessage;
				%>

				<c:if test="<%= !hideControls && MBDiscussionPermission.contains(permissionChecker, company.getCompanyId(), scopeGroupId, permissionClassName, permissionClassPK, userId, ActionKeys.ADD_DISCUSSION) %>">
					<aui:fieldset cssClass="add-comment" id='<%= randomNamespace + "messageScroll0" %>'>
						<c:if test="<%= !messageDisplay.isDiscussionMaxComments() %>">
							<div id="<%= randomNamespace %>messageScroll<%= message.getMessageId() %>">
								<aui:input name="messageId0" type="hidden" value="<%= message.getMessageId() %>" />
								<aui:input name="parentMessageId0" type="hidden" value="<%= message.getMessageId() %>" />
							</div>
						</c:if>

						<%
						boolean subscribed = SubscriptionLocalServiceUtil.isSubscribed(company.getCompanyId(), user.getUserId(), className, classPK);

						String subscriptionURL = "javascript:" + randomNamespace + "subscribeToComments(" + !subscribed + ");";
						%>

						<c:if test="<%= themeDisplay.isSignedIn() %>">
							<c:choose>
								<c:when test="<%= subscribed %>">
									<liferay-ui:icon
										cssClass="subscribe-link"
										iconCssClass="icon-remove-sign"
										label="<%= true %>"
										message="unsubscribe-from-comments"
										url="<%= subscriptionURL %>"
									/>
								</c:when>
								<c:otherwise>
									<liferay-ui:icon
										cssClass="subscribe-link"
										iconCssClass="icon-ok-sign"
										label="<%= true %>"
										message="subscribe-to-comments"
										url="<%= subscriptionURL %>"
									/>
								</c:otherwise>
							</c:choose>
						</c:if>

						<c:if test="<%= !messageDisplay.isDiscussionMaxComments() %>">
							<aui:input name="emailAddress" type="hidden" />

							<c:choose>
								<c:when test="<%= themeDisplay.isSignedIn() || !SSOUtil.isLoginRedirectRequired(themeDisplay.getCompanyId()) %>">
									<aui:row fluid="<%= true %>">
										<div class="lfr-discussion-details">
											<liferay-ui:user-display
												displayStyle="2"
												showUserName="<%= false %>"
												userId="<%= user.getUserId() %>"
											/>
										</div>

										<div class="lfr-discussion-body">
											<liferay-ui:input-editor configKey="commentsEditor" contents="" editorName='<%= PropsUtil.get("editor.wysiwyg.portal-web.docroot.html.taglib.ui.discussion.jsp") %>' name='<%= randomNamespace + "postReplyBody0" %>' onChangeMethod='<%= randomNamespace + "0OnChange" %>' placeholder="type-your-comment-here" />

											<aui:input name="postReplyBody0" type="hidden" />

											<aui:button-row>
												<aui:button cssClass="btn-comment btn-primary" disabled="<%= true %>" id='<%= randomNamespace + "postReplyButton0" %>' onClick='<%= randomNamespace + "postReply(0);" %>' value='<%= themeDisplay.isSignedIn() ? "reply" : "reply-as" %>' />
											</aui:button-row>
										</div>
									</aui:row>
								</c:when>
								<c:otherwise>
									<liferay-ui:icon
										iconCssClass="icon-reply"
										label="<%= true %>"
										message="please-sign-in-to-comment"
										url="<%= themeDisplay.getURLSignIn() %>"
									/>
								</c:otherwise>
							</c:choose>
						</c:if>
					</aui:fieldset>
				</c:if>

				<c:if test="<%= messagesCount > 1 %>">
					<a name="<%= randomNamespace %>messages_top"></a>

					<aui:row>

						<%
						List<Long> classPKs = new ArrayList<Long>();

						for (MBMessage curMessage : messages) {
							if (!curMessage.isRoot()) {
								classPKs.add(curMessage.getMessageId());
							}
						}

						List<RatingsEntry> ratingsEntries = RatingsEntryLocalServiceUtil.getEntries(themeDisplay.getUserId(), MBDiscussion.class.getName(), classPKs);
						List<RatingsStats> ratingsStatsList = RatingsStatsLocalServiceUtil.getStats(MBDiscussion.class.getName(), classPKs);

						int[] range = treeWalker.getChildrenRange(rootMessage);

						int index = 0;
						int rootIndexPage = 0;
						boolean moreCommentsPagination = false;

						for (int j = range[0]; j < range[1]; j++) {
							index = GetterUtil.getInteger(request.getAttribute("liferay-ui:discussion:index"), 1);

							rootIndexPage = j;

							if ((index + 1) > PropsValues.DISCUSSION_COMMENTS_DELTA_VALUE) {
								moreCommentsPagination = true;

								break;
							}

							message = (MBMessage)messages.get(j);

							request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER, treeWalker);
							request.setAttribute(WebKeys.MESSAGE_BOARDS_TREE_WALKER_CUR_MESSAGE, message);

							request.setAttribute("liferay-ui:discussion:messageDisplay", messageDisplay);
							request.setAttribute("liferay-ui:discussion:randomNamespace", randomNamespace);
							request.setAttribute("liferay-ui:discussion:ratingsEntries", ratingsEntries);
							request.setAttribute("liferay-ui:discussion:ratingsStatsList", ratingsStatsList);
							request.setAttribute("liferay-ui:discussion:rootMessage", rootMessage);
						%>

							<liferay-util:include page="/html/taglib/ui/discussion/view_message_thread.jsp" />

						<%
						}
						%>

						<c:if test="<%= moreCommentsPagination %>">
							<div id="<%= namespace %>moreCommentsPage"></div>

							<a class="btn btn-default" href="javascript:;" id="<%= namespace %>moreComments"><liferay-ui:message key="more-comments" /></a>

							<aui:input name="rootIndexPage" type="hidden" value="<%= String.valueOf(rootIndexPage) %>" />
							<aui:input name="index" type="hidden" value="<%= String.valueOf(index) %>" />
						</c:if>
					</aui:row>
				</c:if>
			</aui:form>
		</div>

		<%
		PortletURL loginURL = PortletURLFactoryUtil.create(request, PortletKeys.FAST_LOGIN, themeDisplay.getPlid(), PortletRequest.RENDER_PHASE);

		loginURL.setParameter("saveLastPath", Boolean.FALSE.toString());
		loginURL.setParameter("struts_action", "/login/login");
		loginURL.setPortletMode(PortletMode.VIEW);
		loginURL.setWindowState(LiferayWindowState.POP_UP);
		%>

		<aui:script>
			function <%= namespace + randomNamespace %>0OnChange(html) {
				Liferay.Util.toggleDisabled('#<%= namespace + randomNamespace %>postReplyButton0', !html);
			}

			function <%= randomNamespace %>afterLogin(emailAddress, anonymousAccount) {
				var form = AUI.$('#<%= namespace %><%= HtmlUtil.escapeJS(formName) %>');

				form.fm('emailAddress').val(emailAddress);

				<portlet:namespace />sendMessage(form, !anonymousAccount);
			}

			function <%= randomNamespace %>deleteMessage(i) {
				var form = AUI.$('#<%= namespace %><%= HtmlUtil.escapeJS(formName) %>');

				var messageId = form.fm('messageId' + i).val();

				form.fm('<%= randomNamespace %><%= Constants.CMD %>').val('<%= Constants.DELETE %>');
				form.fm('messageId').val(messageId);

				<portlet:namespace />sendMessage(form);
			}

			function <%= randomNamespace %>hideEl(elId) {
				AUI.$('#' + elId).css('display', 'none');
			}

			function <%= randomNamespace %>hideEditor(editorName, formId) {
				if (window[editorName]) {
					window[editorName].dispose();
				}

				<%= randomNamespace %>hideEl(formId);
			}

			function <portlet:namespace />onMessagePosted(response, refreshPage) {
				Liferay.after(
					'<%= portletDisplay.getId() %>:portletRefreshed',
					function(event) {
						<portlet:namespace />showStatusMessage('success', '<%= UnicodeLanguageUtil.get(request, "your-request-processed-successfully") %>');

						location.hash = '#' + AUI.$('#<portlet:namespace />randomNamespace').val() + 'message_' + response.messageId;
					}
				);

				if (refreshPage) {
					window.location.reload();
				}
				else {
					Liferay.Portlet.refresh('#p_p_id_<%= portletDisplay.getId() %>_');
				}
			}

			function <%= randomNamespace %>postReply(i) {
				var form = AUI.$('#<%= namespace %><%= HtmlUtil.escapeJS(formName) %>');

				var editorInstance = window['<%= namespace + randomNamespace %>postReplyBody' + i];

				var parentMessageId = form.fm('parentMessageId' + i).val();

				form.fm('<%= randomNamespace %><%= Constants.CMD %>').val('<%= Constants.ADD %>');
				form.fm('parentMessageId').val(parentMessageId);
				form.fm('body').val(editorInstance.getHTML());

				if (!themeDisplay.isSignedIn()) {
					window.namespace = '<%= namespace %>';
					window.randomNamespace = '<%= randomNamespace %>';

					Liferay.Util.openWindow(
						{
							dialog: {
								height: 460,
								width: 770
							},
							id: '<%= namespace %>signInDialog',
							title: '<%= UnicodeLanguageUtil.get(request, "sign-in") %>',
							uri: '<%= loginURL.toString() %>'
						}
					);
				}
				else {
					<portlet:namespace />sendMessage(form);

					editorInstance.dispose();
				}
			}

			function <%= randomNamespace %>scrollIntoView(messageId) {
				document.getElementById('<%= randomNamespace %>messageScroll' + messageId).scrollIntoView();
			}

			function <portlet:namespace />sendMessage(form, refreshPage) {
				var Util = Liferay.Util;

				form = AUI.$(form);

				var commentButtonList = form.find('.btn-comment');

				form.ajaxSubmit(
					{
						beforeSubmit: function() {
							Util.toggleDisabled(commentButtonList, true);
						},
						complete: function() {
							Util.toggleDisabled(commentButtonList, false);
						},
						error: function() {
							<portlet:namespace />showStatusMessage('error', '<%= UnicodeLanguageUtil.get(request, "your-request-failed-to-complete") %>');
						},
						success: function(response) {
							var exception = response.exception;

							if (!exception) {
								Liferay.after(
									'<%= portletDisplay.getId() %>:messagePosted',
									function(event) {
										<portlet:namespace />onMessagePosted(response, refreshPage);
									}
								);

								Liferay.fire('<%= portletDisplay.getId() %>:messagePosted', response);
							}
							else {
								var errorKey = '<%= UnicodeLanguageUtil.get(request, "your-request-failed-to-complete") %>';

								if (exception.indexOf('DiscussionMaxCommentsException') > -1) {
									errorKey = '<%= UnicodeLanguageUtil.get(request, "maximum-number-of-comments-has-been-reached") %>';
								}
								else if (exception.indexOf('MessageBodyException') > -1) {
									errorKey = '<%= UnicodeLanguageUtil.get(request, "please-enter-a-valid-message") %>';
								}
								else if (exception.indexOf('NoSuchMessageException') > -1) {
									errorKey = '<%= UnicodeLanguageUtil.get(request, "the-message-could-not-be-found") %>';
								}
								else if (exception.indexOf('PrincipalException') > -1) {
									errorKey = '<%= UnicodeLanguageUtil.get(request, "you-do-not-have-the-required-permissions") %>';
								}
								else if (exception.indexOf('RequiredMessageException') > -1) {
									errorKey = '<%= UnicodeLanguageUtil.get(request, "you-cannot-delete-a-root-message-that-has-more-than-one-immediate-reply") %>';
								}

								<portlet:namespace />showStatusMessage('error', errorKey);
							}
						}
					}
				);
			}

			function <%= randomNamespace %>showEl(elId) {
				AUI.$('#' + elId).css('display', '');
			}

			function <%= randomNamespace %>showEditor(editorName, formId) {
				window[editorName].create();

				var html = window[editorName].getHTML();

				Liferay.Util.toggleDisabled('#' + editorName.replace('Body', 'Button'), (html === ''));

				<%= randomNamespace %>showEl(formId);
			}

			function <portlet:namespace />showStatusMessage(type, message) {
				var messageContainer = AUI.$('#<portlet:namespace />discussionStatusMessages');

				messageContainer.removeClass('alert-danger alert-success');

				messageContainer.addClass('alert alert-' + type);

				messageContainer.html(message);

				messageContainer.removeClass('hide');
			}

			function <%= randomNamespace %>subscribeToComments(subscribe) {
				var form = AUI.$('#<%= namespace %><%= HtmlUtil.escapeJS(formName) %>');

				var cmd = '<%= Constants.UNSUBSCRIBE_FROM_COMMENTS %>';

				if (subscribe) {
					cmd = '<%= Constants.SUBSCRIBE_TO_COMMENTS %>';
				}

				form.fm('<%= randomNamespace %><%= Constants.CMD %>').val(cmd);

				<portlet:namespace />sendMessage(form);
			}

			function <%= randomNamespace %>updateMessage(i, pending) {
				var form = AUI.$('#<%= namespace %><%= HtmlUtil.escapeJS(formName) %>');

				var editorInstance = window['<%= namespace + randomNamespace %>editReplyBody' + i];

				var messageId = form.fm('messageId' + i).val();

				if (pending) {
					form.fm('workflowAction').val('<%= WorkflowConstants.ACTION_SAVE_DRAFT %>');
				}

				form.fm('<%= randomNamespace %><%= Constants.CMD %>').val('<%= Constants.UPDATE %>');
				form.fm('messageId').val(messageId);
				form.fm('body').val(editorInstance.getHTML());

				<portlet:namespace />sendMessage(form);

				editorInstance.dispose();
			}
		</aui:script>

		<aui:script sandbox="<%= true %>">
			$('#<%= namespace %>moreComments').on(
				'click',
				function(event) {
					var form = $('#<%= namespace %><%= HtmlUtil.escapeJS(formName) %>');

					var data = Liferay.Util.ns(
						'<portlet:namespace />',
						{
							className: '<%= className %>',
							classPK: <%= classPK %>,
							hideControls: '<%= hideControls %>',
							index: form.fm('index').val(),
							permissionClassName: '<%= permissionClassName %>',
							permissionClassPK: '<%= permissionClassPK %>',
							randomNamespace: '<%= randomNamespace %>',
							ratingsEnabled: '<%= ratingsEnabled %>',
							rootIndexPage: form.fm('rootIndexPage').val(),
							userId: '<%= userId %>'
						}
					);

					$.ajax(
						'<%= paginationURL %>',
						{
							data: data,
							error: function() {
								<portlet:namespace />showStatusMessage('danger', '<%= UnicodeLanguageUtil.get(request, "your-request-failed-to-complete") %>');
							},
							success: function(data) {
								$('#<%= namespace %>moreCommentsPage').append(data);
							}
						}
					);
				}
			);
		</aui:script>

		<aui:script use="aui-popover,event-outside">
			var discussionContainer = A.one('#<portlet:namespace />discussionContainer');

			var popover = new A.Popover(
				{
					constrain: true,
					cssClass: 'lfr-discussion-reply',
					position: 'top',
					visible: false,
					width: 400,
					zIndex: Liferay.zIndex.TOOLTIP
				}
			).render(discussionContainer);

			var handle;

			var boundingBox = popover.get('boundingBox');

			discussionContainer.delegate(
				'click',
				function(event) {
					event.stopPropagation();

					if (handle) {
						handle.detach();

						handle = null;
					}

					handle = boundingBox.once('clickoutside', popover.hide, popover);

					popover.hide();

					var currentTarget = event.currentTarget;

					popover.set('align.node', currentTarget);
					popover.set('bodyContent', currentTarget.attr('data-metaData'));
					popover.set('headerContent', currentTarget.attr('data-title'));

					popover.show();
				},
				'.lfr-discussion-parent-link'
			);
		</aui:script>
	</c:if>
</section>