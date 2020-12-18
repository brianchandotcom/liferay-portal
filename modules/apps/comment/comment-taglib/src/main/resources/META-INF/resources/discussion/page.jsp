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

				contentURL = HttpUtil.removeParameter(contentURL, namespace + "skipEditorLoading");
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

												<aui:input name="postReplyBody0" type="hidden" />

												<aui:button-row>
													<aui:button cssClass="btn-comment btn-primary btn-sm" disabled="<%= true %>" id="postReplyButton0" onClick='<%= randomNamespace + "postReply(0);" %>' value='<%= themeDisplay.isSignedIn() ? "reply" : "reply-as" %>' />
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
														<use xlink:href="<%= themeDisplay.getPathThemeImages() %>/clay/icons.svg#info-circle" />
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
		PortletURL loginURL = PortletURLFactoryUtil.create(request, PortletKeys.FAST_LOGIN, PortletRequest.RENDER_PHASE);

		loginURL.setParameter("saveLastPath", Boolean.FALSE.toString());
		loginURL.setParameter("mvcRenderCommandName", "/login/login");
		loginURL.setPortletMode(PortletMode.VIEW);
		loginURL.setWindowState(LiferayWindowState.POP_UP);
		%>

		<aui:script require="frontend-js-web/liferay/util/run_scripts_in_element.es as runScriptsInElement">
			var Util = Liferay.Util;

			window['<%= namespace + randomNamespace %>0ReplyOnChange'] = function (html) {
				Util.toggleDisabled(
					'#<%= namespace + randomNamespace %>postReplyButton0',
					html.trim() === ''
				);
			};

			var form =
				document[
					'<%= namespace + randomNamespace + HtmlUtil.escapeJS(discussionTaglibHelper.getFormName()) %>'
				];

			window['<%= randomNamespace %>afterLogin'] = function (
				emailAddress,
				anonymousAccount
			) {
				Util.setFormValues(form, {
					emailAddress: emailAddress,
				});

				<%= namespace + randomNamespace %>sendMessage(form, !anonymousAccount);
			};

			window['<%= randomNamespace %>deleteMessage'] = function (i) {
				var commentIdElement = Util.getFormElement(form, 'commentId' + i);

				if (commentIdElement) {
					Util.setFormValues(form, {
						commentId: commentIdElement.value,
						<%= Constants.CMD %>: '<%= Constants.DELETE %>',
					});

					<%= namespace + randomNamespace %>sendMessage(form);
				}
			};

			window['<%= randomNamespace %>hideEl'] = function (elementId) {
				var element = document.getElementById(elementId);

				if (element) {
					element.style.display = 'none';
				}
			};

			window['<%= randomNamespace %>hideEditor'] = function (editorName, formId) {
				var editor = window['<%= namespace %>' + editorName];

				if (editor) {
					editor.destroy();
				}

				<%= randomNamespace %>hideEl(formId);
			};

			window['<%= randomNamespace %>postReply'] = function (i) {
				var editorInstance =
					window['<%= namespace + randomNamespace %>postReplyBody' + i];

				var parentCommentIdElement = Util.getFormElement(
					form,
					'parentCommentId' + i
				);

				if (parentCommentIdElement) {
					Util.setFormValues(form, {
						body: editorInstance.getHTML(),
						parentCommentId: parentCommentIdElement.value,
						<%= Constants.CMD %>: '<%= Constants.ADD %>',
					});
				}

				if (!themeDisplay.isSignedIn()) {
					window.namespace = '<%= namespace %>';
					window.randomNamespace = '<%= randomNamespace %>';

					Util.openWindow({
						dialog: {
							height: 450,
							width: 560,
						},
						id: '<%= namespace %>signInDialog',
						title: '<%= UnicodeLanguageUtil.get(resourceBundle, "sign-in") %>',
						uri: '<%= loginURL.toString() %>',
					});
				}
				else {
					<%= namespace + randomNamespace %>sendMessage(form);

					editorInstance.dispose();
				}
			};

			window['<%= randomNamespace %>scrollIntoView'] = function (commentId) {
				document
					.getElementById('<%= randomNamespace %>messageScroll' + commentId)
					.scrollIntoView();
			};

			window['<%= namespace + randomNamespace %>sendMessage'] = function (
				form,
				refreshPage
			) {
				var commentButtons = form.querySelectorAll('.btn-comment');

				Util.toggleDisabled(commentButtons, true);

				var formData = new FormData(form);

				formData.append('doAsUserId', themeDisplay.getDoAsUserIdEncoded());

				Liferay.Util.fetch(form.action, {
					body: formData,
					method: 'POST',
				})
					.then(function (response) {
						var promise;

						var contentType = response.headers.get('content-type');

						if (contentType && contentType.indexOf('application/json') !== -1) {
							promise = response.json();
						}
						else {
							promise = response.text();
						}

						return promise;
					})
					.then(function (response) {
						var exception = response.exception;

						if (!exception) {
							Liferay.onceAfter(
								'<%= portletDisplay.getId() %>:messagePosted',
								function (event) {
									<%= randomNamespace %>onMessagePosted(
										response,
										refreshPage
									);
								}
							);

							Liferay.fire(
								'<%= portletDisplay.getId() %>:messagePosted',
								response
							);
						}
						else {
							var errorKey =
								'<%= UnicodeLanguageUtil.get(resourceBundle, "your-request-failed-to-complete") %>';

							if (exception.indexOf('DiscussionMaxCommentsException') > -1) {
								errorKey =
									'<%= UnicodeLanguageUtil.get(resourceBundle, "maximum-number-of-comments-has-been-reached") %>';
							}
							else if (exception.indexOf('MessageBodyException') > -1) {
								errorKey =
									'<%= UnicodeLanguageUtil.get(resourceBundle, "please-enter-a-valid-message") %>';
							}
							else if (exception.indexOf('NoSuchMessageException') > -1) {
								errorKey =
									'<%= UnicodeLanguageUtil.get(resourceBundle, "the-message-could-not-be-found") %>';
							}
							else if (exception.indexOf('PrincipalException') > -1) {
								errorKey =
									'<%= UnicodeLanguageUtil.get(resourceBundle, "you-do-not-have-the-required-permissions") %>';
							}
							else if (exception.indexOf('RequiredMessageException') > -1) {
								errorKey =
									'<%= UnicodeLanguageUtil.get(resourceBundle, "you-cannot-delete-a-root-message-that-has-more-than-one-immediate-reply") %>';
							}

							<%= randomNamespace %>showStatusMessage({
								id: '<%= randomNamespace %>',
								message: errorKey,
								type: 'danger',
							});
						}

						Util.toggleDisabled(commentButtons, false);
					})
					.catch(function () {
						<%= randomNamespace %>showStatusMessage({
							id: '<%= randomNamespace %>',
							message:
								'<%= UnicodeLanguageUtil.get(resourceBundle, "your-request-failed-to-complete") %>',
							type: 'danger',
						});

						Util.toggleDisabled(commentButtons, false);
					});
			};

			window['<%= randomNamespace %>showEl'] = function (elementId) {
				var element = document.getElementById(elementId);

				if (element) {
					element.style.display = '';
				}
			};

			window['<%= randomNamespace %>showEditor'] = function (formId, options) {
				var element = window['<%= namespace %>' + options.name];
				var editorWrapper =
					element && element.querySelector('#' + formId + ' .editor-wrapper');

				if (!editorWrapper || editorWrapper.childNodes.length === 0) {

					<%
					String editorURL = GetterUtil.getString(request.getAttribute("liferay-comment:discussion:editorURL"));

					editorURL = HttpUtil.addParameter(editorURL, "namespace", namespace);
					%>

					Liferay.Util.fetch('<%= editorURL %>', {
						body: Util.objectToFormData(Util.ns('<%= namespace %>', options)),
						method: 'POST',
					})
						.then(function (response) {
							return response.text();
						})
						.then(function (response) {
							var editorWrapper = document.querySelector(
								'#' + formId + ' .editor-wrapper'
							);

							if (editorWrapper) {
								editorWrapper.innerHTML = response;

								runScriptsInElement.default(editorWrapper);
							}

							Util.toggleDisabled(
								'#' + options.name.replace('Body', 'Button'),
								options.contents === ''
							);

							<%= randomNamespace %>showEl(formId);
						})
						.catch(function () {
							<%= randomNamespace %>showStatusMessage({
								id: '<%= randomNamespace %>',
								message:
									'<%= UnicodeLanguageUtil.get(resourceBundle, "your-request-failed-to-complete") %>',
								type: 'danger',
							});
						});
				}
			};

			window['<%= randomNamespace %>showPostReplyEditor'] = function (index) {
				<%= randomNamespace %>showEditor(
					'<%= namespace + randomNamespace %>' + 'postReplyForm' + index,
					{
						name: '<%= randomNamespace %>' + 'postReplyBody' + index,
						onChangeMethod: '<%= randomNamespace %>' + index + 'ReplyOnChange',
						placeholder: 'type-your-comment-here',
					}
				);

				<%= randomNamespace %>hideEditor(
					'<%= randomNamespace %>' + 'editReplyBody' + index,
					'<%= namespace + randomNamespace %>' + 'editForm' + index
				);

				<%= randomNamespace %>showEl(
					'<%= namespace + randomNamespace %>' + 'discussionMessage' + index
				);
			};

			window.<%= randomNamespace %>showStatusMessage = Liferay.lazyLoad(
				'frontend-js-web/liferay/toast/commands/OpenToast.es',
				function (toastCommands, data) {
					toastCommands.openToast(data);
				}
			);

			window['<%= randomNamespace %>showEditReplyEditor'] = function (index) {
				var discussionId =
					'<%= namespace + randomNamespace %>' + 'discussionMessage' + index;

				var discussionIdElement = document.getElementById(discussionId);

				if (discussionIdElement) {
					<%= randomNamespace %>showEditor(
						'<%= namespace + randomNamespace %>' + 'editForm' + index,
						{
							contents: discussionIdElement.innerHTML,
							name: '<%= randomNamespace %>' + 'editReplyBody' + index,
							onChangeMethod:
								'<%= randomNamespace %>' + index + 'EditOnChange',
						}
					);

					<%= randomNamespace %>hideEditor(
						'<%= randomNamespace %>' + 'postReplyBody' + index,
						'<%= namespace + randomNamespace %>' + 'postReplyForm' + index
					);

					<%= randomNamespace %>hideEl(discussionId);
				}
			};

			window['<%= randomNamespace %>subscribeToComments'] = function (subscribe) {
				Util.setFormValues(form, {
					<%= randomNamespace %>className:
						'<%= discussionTaglibHelper.getSubscriptionClassName() %>',
					<%= Constants.CMD %>: subscribe
						? '<%= Constants.SUBSCRIBE_TO_COMMENTS %>'
						: '<%= Constants.UNSUBSCRIBE_FROM_COMMENTS %>',
				});

				<%= namespace + randomNamespace %>sendMessage(form);
			};

			window['<%= randomNamespace %>updateMessage'] = function (i, pending) {
				var editorInstance =
					window['<%= namespace + randomNamespace %>editReplyBody' + i];

				var commentIdElement = Util.getFormElement(form, 'commentId' + i);

				if (commentIdElement) {
					if (pending) {
						Util.setFormValues(form, {
							workflowAction: '<%= WorkflowConstants.ACTION_SAVE_DRAFT %>',
						});
					}

					Util.setFormValues(form, {
						body: editorInstance.getHTML(),
						commentId: commentIdElement.value,
						<%= Constants.CMD %>: '<%= Constants.UPDATE %>',
					});

					<%= namespace + randomNamespace %>sendMessage(form);
				}

				editorInstance.dispose();
			};

			<%
			String messageId = ParamUtil.getString(request, "messageId");
			%>

			<c:if test="<%= Validator.isNotNull(messageId) %>">
				<%= randomNamespace %>scrollIntoView(<%= messageId %>);
			</c:if>

			var moreCommentsTrigger = document.getElementById(
				'<%= namespace %>moreCommentsTrigger'
			);

			var indexElement = Util.getFormElement(form, 'index');
			var rootIndexPageElement = Util.getFormElement(form, 'rootIndexPage');

			if (moreCommentsTrigger && indexElement && rootIndexPageElement) {
				moreCommentsTrigger.addEventListener('click', function (event) {
					var data = Util.ns('<%= namespace %>', {
						className: '<%= discussionTaglibHelper.getClassName() %>',
						classPK: '<%= discussionTaglibHelper.getClassPK() %>',
						hideControls: '<%= discussionTaglibHelper.isHideControls() %>',
						index: indexElement.value,
						randomNamespace: '<%= randomNamespace %>',
						ratingsEnabled: '<%= discussionTaglibHelper.isRatingsEnabled() %>',
						rootIndexPage: rootIndexPageElement.value,
						userId: '<%= discussionTaglibHelper.getUserId() %>',
					});

					<%
					String paginationURL = HttpUtil.addParameter(discussionTaglibHelper.getPaginationURL(), "namespace", namespace);

					paginationURL = HttpUtil.addParameter(paginationURL, "skipEditorLoading", "true");
					%>

					Liferay.Util.fetch('<%= paginationURL %>', {
						body: Util.objectToFormData(data),
						method: 'POST',
					})
						.then(function (response) {
							return response.text();
						})
						.then(function (response) {
							var moreCommentsContainer = document.getElementById(
								'<%= namespace %>moreCommentsContainer'
							);

							if (moreCommentsContainer) {
								var newCommentsContainer = document.createElement('div');

								newCommentsContainer.innerHTML = response;

								moreCommentsContainer.insertAdjacentElement(
									'beforebegin',
									newCommentsContainer
								);

								runScriptsInElement.default(newCommentsContainer);
							}
						})
						.catch(function () {
							<%= randomNamespace %>showStatusMessage({
								id: '<%= randomNamespace %>',
								message:
									'<%= UnicodeLanguageUtil.get(resourceBundle, "your-request-failed-to-complete") %>',
								type: 'danger',
							});
						});
				});
			}
		</aui:script>

		<aui:script use="aui-popover,event-outside">
			window['<%= randomNamespace %>onMessagePosted'] = function (
				response,
				refreshPage
			) {
				Liferay.onceAfter(
					'<%= portletDisplay.getId() %>:portletRefreshed',
					function (event) {
						var randomNamespaceNodes = document.querySelectorAll(
							'input[id^="<%= namespace %>"][id$="randomNamespace"]'
						);

						Array.prototype.forEach.call(randomNamespaceNodes, function (
							node,
							index
						) {
							var randomId = node.value;

							if (index === 0) {
								<%= randomNamespace %>showStatusMessage({
									id: randomId,
									message:
										'<%= UnicodeLanguageUtil.get(resourceBundle, "your-request-completed-successfully") %>',
									type: 'success',
								});
							}

							var currentMessageSelector =
								randomId + 'message_' + response.commentId;

							var targetNode = document.getElementById(
								currentMessageSelector
							);

							if (targetNode) {
								location.hash = '#' + currentMessageSelector;
							}
						});
					}
				);

				if (response.commentId) {
					var messageTextNode = document.querySelector(
						'input[name^="<%= namespace + randomNamespace %>body"]'
					);

					if (messageTextNode) {
						Liferay.fire('messagePosted', {
							className: '<%= discussionTaglibHelper.getClassName() %>',
							classPK: '<%= discussionTaglibHelper.getClassPK() %>',
							commentId: response.commentId,
							text: messageTextNode.value,
						});
					}
				}

				if (refreshPage) {
					window.location.reload();
				}
				else {
					var portletNodeId = '#p_p_id_<%= portletDisplay.getId() %>_';

					var portletNode = A.one(portletNodeId);

					Liferay.Portlet.refresh(
						portletNodeId,
						A.merge(
							Liferay.Util.ns('<%= namespace %>', {
								className: '<%= discussionTaglibHelper.getClassName() %>',
								classPK: '<%= discussionTaglibHelper.getClassPK() %>',
								skipEditorLoading: true,
							}),
							portletNode.refreshURLData || {}
						)
					);
				}
			};

			var discussionContainer = A.one('#<%= namespace %>discussionContainer');

			var popover = new A.Popover({
				constrain: true,
				cssClass: 'lfr-discussion-reply',
				position: 'top',
				visible: false,
				width: 400,
				zIndex: Liferay.zIndex.OVERLAY,
			}).render(discussionContainer);

			var handle;

			var boundingBox = popover.get('boundingBox');

			discussionContainer.delegate(
				'click',
				function (event) {
					event.preventDefault();
					event.stopPropagation();

					if (handle) {
						handle.detach();

						handle = null;
					}

					handle = boundingBox.once('clickoutside', popover.hide, popover);

					popover.hide();

					var currentTarget = event.currentTarget;

					popover.set('align.node', currentTarget);
					popover.set('bodyContent', currentTarget.attr('data-inreply-content'));
					popover.set('headerContent', currentTarget.attr('data-inreply-title'));

					popover.show();
				},
				'.lfr-discussion-parent-link'
			);
		</aui:script>
	</c:if>
</section>