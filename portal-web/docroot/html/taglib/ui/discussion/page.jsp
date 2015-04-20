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

DiscussionTaglibHelper discussionTaglibHelper = new DiscussionTaglibHelper(request);
DiscussionRequestHelper discussionRequestHelper = new DiscussionRequestHelper(request);

CommentSectionDisplayContext commentSectionDisplayContext = new MBCommentSectionDisplayContext(discussionTaglibHelper, discussionRequestHelper);

Comment rootComment = commentSectionDisplayContext.getRootComment();
%>

<section>
	<div class="hide lfr-message-response" id="<portlet:namespace />discussionStatusMessages"></div>

	<c:if test="<%= commentSectionDisplayContext.isDiscussionMaxComments() %>">
		<div class="alert alert-warning">
			<liferay-ui:message key="maximum-number-of-comments-has-been-reached" />
		</div>
	</c:if>

	<c:if test="<%= commentSectionDisplayContext.isDiscussionVisible() %>">
		<div class="taglib-discussion" id="<portlet:namespace />discussionContainer">
			<aui:form action="<%= discussionTaglibHelper.getFormAction() %>" method="post" name="<%= discussionTaglibHelper.getFormName() %>">
				<aui:input name="randomNamespace" type="hidden" value="<%= randomNamespace %>" />
				<aui:input id="<%= randomNamespace + Constants.CMD %>" name="<%= Constants.CMD %>" type="hidden" />
				<aui:input name="redirect" type="hidden" value="<%= discussionTaglibHelper.getRedirect() %>" />
				<aui:input name="contentURL" type="hidden" value="<%= PortalUtil.getCanonicalURL(discussionTaglibHelper.getRedirect(), themeDisplay, layout) %>" />
				<aui:input name="assetEntryVisible" type="hidden" value="<%= discussionTaglibHelper.isAssetEntryVisible() %>" />
				<aui:input name="className" type="hidden" value="<%= discussionTaglibHelper.getClassName() %>" />
				<aui:input name="classPK" type="hidden" value="<%= discussionTaglibHelper.getClassPK() %>" />
				<aui:input name="permissionClassName" type="hidden" value="<%= discussionTaglibHelper.getPermissionClassName() %>" />
				<aui:input name="permissionClassPK" type="hidden" value="<%= discussionTaglibHelper.getPermissionClassPK() %>" />
				<aui:input name="permissionOwnerId" type="hidden" value="<%= String.valueOf(discussionTaglibHelper.getUserId()) %>" />
				<aui:input name="messageId" type="hidden" />
				<aui:input name="threadId" type="hidden" value="<%= commentSectionDisplayContext.getThreadId() %>" />
				<aui:input name="parentMessageId" type="hidden" />
				<aui:input name="body" type="hidden" />
				<aui:input name="workflowAction" type="hidden" value="<%= String.valueOf(WorkflowConstants.ACTION_PUBLISH) %>" />
				<aui:input name="ajax" type="hidden" value="<%= true %>" />

				<%
				Comment comment = rootComment;
				%>

				<c:if test="<%= commentSectionDisplayContext.isControlsVisible() %>">
					<aui:fieldset cssClass="add-comment" id='<%= randomNamespace + "messageScroll0" %>'>
						<c:if test="<%= !commentSectionDisplayContext.isDiscussionMaxComments() %>">
							<div id="<%= randomNamespace %>messageScroll<%= rootComment.getCommentId() %>">
								<aui:input name="messageId0" type="hidden" value="<%= rootComment.getCommentId() %>" />
								<aui:input name="parentMessageId0" type="hidden" value="<%= rootComment.getCommentId() %>" />
							</div>
						</c:if>

						<%
						boolean subscribed = SubscriptionLocalServiceUtil.isSubscribed(company.getCompanyId(), user.getUserId(), discussionTaglibHelper.getClassName(), discussionTaglibHelper.getClassPK());

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

						<c:if test="<%= !commentSectionDisplayContext.isDiscussionMaxComments() %>">
							<aui:input name="emailAddress" type="hidden" />

							<c:choose>
								<c:when test="<%= themeDisplay.isSignedIn() || !SSOUtil.isLoginRedirectRequired(discussionRequestHelper.getCompanyId()) %>">
									<aui:row fluid="<%= true %>">
										<div class="lfr-discussion-details">
											<liferay-ui:user-display
												displayStyle="2"
												showUserName="<%= false %>"
												userId="<%= discussionTaglibHelper.getUserId() %>"
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

				<c:if test="<%= commentSectionDisplayContext.isMessageThreadVisible() %>">
					<a name="<%= randomNamespace %>messages_top"></a>

					<aui:row>

						<%
						int index = 0;
						int rootIndexPage = 0;
						boolean moreCommentsPagination = false;

						CommentIterator commentIterator = rootComment.getThreadCommentsIterator();

						while (commentIterator.hasNext()) {
							index = GetterUtil.getInteger(request.getAttribute("liferay-ui:discussion:index"), 1);

							rootIndexPage = commentIterator.getIndexPage();

							if ((index + 1) > PropsValues.DISCUSSION_COMMENTS_DELTA_VALUE) {
								moreCommentsPagination = true;

								break;
							}

							comment = commentIterator.next();

							request.setAttribute("liferay-ui:discussion:commentSectionDisplayContext", commentSectionDisplayContext);
							request.setAttribute("liferay-ui:discussion:currentComment", comment);
							request.setAttribute("liferay-ui:discussion:randomNamespace", randomNamespace);
							request.setAttribute("liferay-ui:discussion:rootComment", rootComment);
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
				var form = AUI.$('#<%= namespace %><%= HtmlUtil.escapeJS(discussionTaglibHelper.getFormName()) %>');

				form.fm('emailAddress').val(emailAddress);

				<portlet:namespace />sendMessage(form, !anonymousAccount);
			}

			function <%= randomNamespace %>deleteMessage(i) {
				var form = AUI.$('#<%= namespace %><%= HtmlUtil.escapeJS(discussionTaglibHelper.getFormName()) %>');

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
				var form = AUI.$('#<%= namespace %><%= HtmlUtil.escapeJS(discussionTaglibHelper.getFormName()) %>');

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
				var form = AUI.$('#<%= namespace %><%= HtmlUtil.escapeJS(discussionTaglibHelper.getFormName()) %>');

				var cmd = '<%= Constants.UNSUBSCRIBE_FROM_COMMENTS %>';

				if (subscribe) {
					cmd = '<%= Constants.SUBSCRIBE_TO_COMMENTS %>';
				}

				form.fm('<%= randomNamespace %><%= Constants.CMD %>').val(cmd);

				<portlet:namespace />sendMessage(form);
			}

			function <%= randomNamespace %>updateMessage(i, pending) {
				var form = AUI.$('#<%= namespace %><%= HtmlUtil.escapeJS(discussionTaglibHelper.getFormName()) %>');

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
					var form = $('#<%= namespace %><%= HtmlUtil.escapeJS(discussionTaglibHelper.getFormName()) %>');

					var data = Liferay.Util.ns(
						'<portlet:namespace />',
						{
							className: '<%= discussionTaglibHelper.getClassName() %>',
							classPK: <%= discussionTaglibHelper.getClassPK() %>,
							hideControls: '<%= discussionTaglibHelper.isHideControls() %>',
							index: form.fm('index').val(),
							permissionClassName: '<%= discussionTaglibHelper.getPermissionClassName() %>',
							permissionClassPK: '<%= discussionTaglibHelper.getPermissionClassPK() %>',
							randomNamespace: '<%= randomNamespace %>',
							ratingsEnabled: '<%= discussionTaglibHelper.isRatingsEnabled() %>',
							rootIndexPage: form.fm('rootIndexPage').val(),
							userId: '<%= discussionTaglibHelper.getUserId() %>'
						}
					);

					$.ajax(
						'<%= discussionTaglibHelper.getPaginationURL() %>',
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