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

<%@ include file="/html/portlet/message_boards/init.jsp" %>

<%
MBCategory category = (MBCategory)request.getAttribute("edit_message.jsp-category");
String className = (String)request.getAttribute("edit_message.jsp-className");
Integer depth = (Integer)request.getAttribute("edit_message.jsp-depth");
Boolean editable = (Boolean)request.getAttribute("edit_message.jsp-editable");
MBMessage message = (MBMessage)request.getAttribute("edit_message.jsp-message");
Boolean showDeletedAttachmentsFileEntries = (Boolean)request.getAttribute("edit-message.jsp-showDeletedAttachmentsFileEntries");
Boolean showPermanentLink = (Boolean)request.getAttribute("edit-message.jsp-showPermanentLink");
Boolean showRecentPosts = (Boolean)request.getAttribute("edit-message.jsp-showRecentPosts");
MBThread thread = (MBThread)request.getAttribute("edit_message.jsp-thread");
%>

<div class="message-container" style="margin-left: <%= depth * 10 %>px;">
	<a id="<portlet:namespace />message_<%= message.getMessageId() %>"></a>

	<table class="thread-message-view <%= className %>" id="<portlet:namespace />message_<%= message.getMessageId() %>">
	<tr>
		<td class="lfr-top user-info" <%= editable ? "rowspan=\"2\"" : StringPool.BLANK %>>
			<c:choose>
				<c:when test="<%= message.isAnonymous() %>">
					<liferay-ui:message key="anonymous" />
				</c:when>
				<c:otherwise>
					<liferay-ui:user-display
						displayStyle="2"
						userId="<%= message.getUserId() %>"
						userName="<%= HtmlUtil.escape(message.getUserName()) %>"
					>

						<%
						MBStatsUser statsUser = MBStatsUserLocalServiceUtil.getStatsUser(scopeGroupId, message.getUserId());

						int posts = statsUser.getMessageCount();
						String[] ranks = MBUtil.getUserRank(mbSettings, themeDisplay.getLanguageId(), statsUser);
						%>

						<c:if test="<%= Validator.isNotNull(ranks[1]) %>">
							<div class="thread-user-role thread-user-role-<%= StringUtil.toLowerCase(ranks[1]) %>"><%= ranks[1] %></div>
						</c:if>

						<c:if test="<%= Validator.isNotNull(ranks[0]) %>">
							<div class="thread-user-rank">
								<span><liferay-ui:message key="rank" />:</span> <%= ranks[0] %>
							</div>
						</c:if>

						<div class="thread-user-post-count">
							<span><liferay-ui:message key="posts" />:</span> <%= posts %>
						</div>

						<div class="thread-user-join-date">
							<span><liferay-ui:message key="join-date" />:</span> <%= dateFormatDate.format(userDisplay.getCreateDate()) %>
						</div>

						<c:if test="<%= showRecentPosts %>">
							<portlet:renderURL var="recentPostsURL">
								<portlet:param name="struts_action" value="/message_boards/view" />
								<portlet:param name="topLink" value="recent-posts" />
								<portlet:param name="groupThreadsUserId" value="<%= String.valueOf(userDisplay.getUserId()) %>" />
							</portlet:renderURL>

							<liferay-ui:icon
								iconCssClass="icon-search"
								label="<%= true %>"
								message="recent-posts"
								method="get"
								url="<%= recentPostsURL.toString() %>"
							/>
						</c:if>

						<c:if test="<%= (userDisplay != null) && (user.getUserId() != userDisplay.getUserId()) && !PortalUtil.isGroupAdmin(userDisplay, scopeGroupId) && MBPermission.contains(permissionChecker, scopeGroupId, ActionKeys.BAN_USER) %>">
							<br />

							<c:choose>
								<c:when test="<%= MBBanLocalServiceUtil.hasBan(scopeGroupId, userDisplay.getUserId()) %>">
									<portlet:actionURL var="unbanUserURL">
										<portlet:param name="struts_action" value="/message_boards/ban_user" />
										<portlet:param name="<%= Constants.CMD %>" value="unban" />
										<portlet:param name="redirect" value="<%= currentURL %>" />
										<portlet:param name="banUserId" value="<%= String.valueOf(userDisplay.getUserId()) %>" />
									</portlet:actionURL>

									<liferay-ui:icon
										iconCssClass="icon-ok-sign"
										label="<%= true %>"
										message="unban-this-user"
										url="<%= unbanUserURL.toString() %>"
									/>
								</c:when>
								<c:otherwise>
									<portlet:actionURL var="banUserURL">
										<portlet:param name="struts_action" value="/message_boards/ban_user" />
										<portlet:param name="<%= Constants.CMD %>" value="ban" />
										<portlet:param name="redirect" value="<%= currentURL %>" />
										<portlet:param name="banUserId" value="<%= String.valueOf(userDisplay.getUserId()) %>" />
									</portlet:actionURL>

									<liferay-ui:icon
										iconCssClass="icon-ban-circle"
										label="<%= true %>"
										message="ban-this-user"
										url="<%= banUserURL.toString() %>"
									/>
								</c:otherwise>
							</c:choose>
						</c:if>
					</liferay-ui:user-display>
				</c:otherwise>
			</c:choose>
		</td>
		<td class="lfr-top">
			<div class="thread-top float-container">
				<div class="subject">
					<c:choose>
						<c:when test="<%= showPermanentLink %>">
							<a href="#<portlet:namespace />message_<%= message.getMessageId() %>" title="<liferay-ui:message key="permanent-link-to-this-item" />"><strong><%= HtmlUtil.escape(message.getSubject()) %></strong></a>
						</c:when>
						<c:otherwise>
							<strong><%= HtmlUtil.escape(message.getSubject()) %></strong>
						</c:otherwise>
					</c:choose>

					<br />

					<%
					String assetTagNames = (String)request.getAttribute("edit_message.jsp-assetTagNames");
					%>

					<div class="tags">
						<liferay-ui:asset-tags-summary
							assetTagNames="<%= assetTagNames %>"
							className="<%= MBMessage.class.getName() %>"
							classPK="<%= message.getMessageId() %>"
						/>
					</div>

					<div class="answer <%= !message.isRoot() && message.isAnswer() ? "" : "hide" %>" id="<portlet:namespace />deleteAnswerFlag_<%= message.getMessageId() %>">
						<liferay-ui:icon
							iconCssClass="icon-check"
							label="<%= true %>"
							message="answer"
						/>

						<c:if test="<%= (message.getRootMessageId() != MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID) && MBMessagePermission.contains(permissionChecker, message.getRootMessageId(), ActionKeys.UPDATE) %>">
							(<a href="javascript:<portlet:namespace />deleteAnswerFlag('<%= message.getMessageId() %>');"><liferay-ui:message key="unmark" /></a>)
						</c:if>
					</div>

					<%= dateFormatDateTime.format(message.getModifiedDate()) %>

					<c:if test="<%= (message != null) && !message.isApproved() %>">
						<aui:model-context bean="<%= message %>" model="<%= MBMessage.class %>" />

						<aui:workflow-status status="<%= message.getStatus() %>" />
					</c:if>

					<%
					MBMessage parentMessage = null;

					try {
						parentMessage = MBMessageLocalServiceUtil.getMessage(message.getParentMessageId());
					}
					catch (Exception e) {}
					%>

					<c:if test="<%= parentMessage != null %>">

						<%
						PortletURL parentMessageURL = renderResponse.createRenderURL();

						parentMessageURL.setParameter("struts_action", "/message_boards/view_message");
						parentMessageURL.setParameter("messageId", String.valueOf(parentMessage.getMessageId()));

						String author = parentMessage.isAnonymous() ? LanguageUtil.get(pageContext, "anonymous") : HtmlUtil.escape(PortalUtil.getUserName(parentMessage.getUserId(), parentMessage.getUserName()));
						%>

						<%= LanguageUtil.format(pageContext, "posted-as-a-reply-to", author, false) %>
					</c:if>
				</div>

				<c:if test="<%= editable %>">

					<%
					boolean showAnswerFlag = false;
					boolean hasReplyPermission = MBCategoryPermission.contains(permissionChecker, scopeGroupId, message.getCategoryId(), ActionKeys.REPLY_TO_MESSAGE);

					if (!message.isRoot()) {
						MBMessage rootMessage = MBMessageLocalServiceUtil.getMessage(thread.getRootMessageId());

						showAnswerFlag = MBMessagePermission.contains(permissionChecker, rootMessage, ActionKeys.UPDATE) && !message.isAnswer() && (thread.isQuestion() || MBThreadLocalServiceUtil.hasAnswerMessage(thread.getThreadId()));
					}
					%>

					<c:if test="<%= showAnswerFlag || hasReplyPermission %>">
						<ul class="edit-controls unstyled">
							<li class="<%= showAnswerFlag ? "" : "hide" %>" id="<portlet:namespace />addAnswerFlag_<%= message.getMessageId() %>">

								<%
								String taglibMarkAsAnswerURL = "javascript:" + renderResponse.getNamespace() + "addAnswerFlag('" + message.getMessageId() + "');";
								%>

								<liferay-ui:icon
									iconCssClass="icon-check"
									label="<%= true %>"
									message="mark-as-an-answer"
									url="<%= taglibMarkAsAnswerURL %>"
								/>
							</li>

							<c:if test="<%= hasReplyPermission && !thread.isLocked() %>">
								<li>
									<portlet:renderURL var="replyURL">
										<portlet:param name="struts_action" value="/message_boards/edit_message" />
										<portlet:param name="redirect" value="<%= currentURL %>" />
										<portlet:param name="mbCategoryId" value="<%= String.valueOf(message.getCategoryId()) %>" />
										<portlet:param name="threadId" value="<%= String.valueOf(message.getThreadId()) %>" />
										<portlet:param name="parentMessageId" value="<%= String.valueOf(message.getMessageId()) %>" />
									</portlet:renderURL>

									<liferay-ui:icon
										iconCssClass="icon-reply"
										label="<%= true %>"
										message="reply"
										url="<%= replyURL %>"
									/>
								</li>
								<li>
									<portlet:renderURL var="quoteURL">
										<portlet:param name="struts_action" value="/message_boards/edit_message" />
										<portlet:param name="redirect" value="<%= currentURL %>" />
										<portlet:param name="mbCategoryId" value="<%= String.valueOf(message.getCategoryId()) %>" />
										<portlet:param name="threadId" value="<%= String.valueOf(message.getThreadId()) %>" />
										<portlet:param name="parentMessageId" value="<%= String.valueOf(message.getMessageId()) %>" />
										<portlet:param name="quote" value="<%= Boolean.TRUE.toString() %>" />
									</portlet:renderURL>

									<liferay-ui:icon
										iconCssClass="icon-quote-left"
										label="<%= true %>"
										message="reply-with-quote"
										url="<%= quoteURL %>"
									/>
								</li>
								<li>

									<%
									String taglibQuickReplyURL = "javascript:" + renderResponse.getNamespace() + "addQuickReply('reply', '" + message.getMessageId() + "');";
									%>

									<liferay-ui:icon
										iconCssClass="icon-long-arrow-down"
										label="<%= true %>"
										message="quick-reply"
										url="<%= taglibQuickReplyURL %>"
									/>
								</li>
							</c:if>
						</ul>
					</c:if>
				</c:if>
			</div>

			<div class="thread-body">

				<%
				String msgBody = message.getBody();

				if (message.isFormatBBCode()) {
					msgBody = MBUtil.getBBCodeHTML(msgBody, themeDisplay.getPathThemeImages());
				}
				%>

				<%= msgBody %>

				<liferay-ui:custom-attributes-available className="<%= MBMessage.class.getName() %>">
					<div class="custom-attributes">
						<liferay-ui:custom-attribute-list
							className="<%= MBMessage.class.getName() %>"
							classPK="<%= (message != null) ? message.getMessageId() : 0 %>"
							editable="<%= false %>"
							label="<%= true %>"
						/>
					</div>
				</liferay-ui:custom-attributes-available>

				<c:if test="<%= message.getMessageId() > 0 %>">

					<%
					int attachmentsFileEntriesCount = message.getAttachmentsFileEntriesCount();
					int deletedAttachmentsFileEntriesCount = message.getDeletedAttachmentsFileEntriesCount();
					%>

					<c:if test="<%= ((attachmentsFileEntriesCount > 0) || ((deletedAttachmentsFileEntriesCount > 0) && TrashUtil.isTrashEnabled(scopeGroupId) && MBMessagePermission.contains(permissionChecker, message, ActionKeys.UPDATE))) %>">
						<div class="message-attachments">
							<h3><liferay-ui:message key="attachments" />:</h3>

							<%
							List<FileEntry> attachmentsFileEntries = message.getAttachmentsFileEntries();

							for (FileEntry fileEntry : attachmentsFileEntries) {
								if (MimeTypesUtil.isWebImage(fileEntry.getMimeType())) {
							%>

									<div>
										<img alt="<liferay-ui:message key="attachment" />" src="<%= PortletFileRepositoryUtil.getPortletFileEntryURL(themeDisplay, fileEntry, StringPool.BLANK) %>" />
									</div>

									<br />

							<%
								}
							}
							%>

							<ul>

								<%
								for (FileEntry fileEntry : attachmentsFileEntries) {
								%>

									<li class="message-attachment">

										<%
										StringBundler sb = new StringBundler(4);

										sb.append(fileEntry.getTitle());
										sb.append(StringPool.OPEN_PARENTHESIS);
										sb.append(TextFormatter.formatStorageSize(fileEntry.getSize(), locale));
										sb.append(StringPool.CLOSE_PARENTHESIS);

										AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(DLFileEntry.class.getName());

										AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(fileEntry.getFileEntryId());
										%>

										<liferay-ui:icon
											iconCssClass="<%= assetRenderer.getIconCssClass() %>"
											label="<%= true %>"
											message="<%= sb.toString() %>"
											url="<%= PortletFileRepositoryUtil.getPortletFileEntryURL(themeDisplay, fileEntry, StringPool.BLANK) %>"
										/>
									</li>

								<%
								}
								%>

								<c:if test="<%= showDeletedAttachmentsFileEntries && (deletedAttachmentsFileEntriesCount > 0) && TrashUtil.isTrashEnabled(scopeGroupId) && MBMessagePermission.contains(permissionChecker, message, ActionKeys.UPDATE) %>">
									<li class="message-attachment">
										<portlet:renderURL var="viewTrashAttachmentsURL">
											<portlet:param name="struts_action" value="/message_boards/view_deleted_message_attachments" />
											<portlet:param name="redirect" value="<%= currentURL %>" />
											<portlet:param name="messageId" value="<%= String.valueOf(message.getMessageId()) %>" />
										</portlet:renderURL>

										<liferay-ui:icon
											iconCssClass="icon-paperclip"
											label="<%= true %>"
											message='<%= LanguageUtil.format(pageContext, (deletedAttachmentsFileEntriesCount == 1) ? "x-recently-removed-attachment" : "x-recently-removed-attachments", deletedAttachmentsFileEntriesCount, false) %>'
											url="<%= viewTrashAttachmentsURL %>"
										/>
									</li>
								</c:if>
							</ul>
						</div>
					</c:if>
				</c:if>
			</div>
		</td>
	</tr>

	<c:if test="<%= editable %>">
		<tr>
			<td class="thread-bottom">
				<c:if test="<%= enableRatings %>">
					<liferay-ui:ratings
						className="<%= MBMessage.class.getName() %>"
						classPK="<%= message.getMessageId() %>"
						type="thumbs"
					/>
				</c:if>

				<c:if test="<%= enableFlags %>">
					<liferay-ui:flags
						className="<%= MBMessage.class.getName() %>"
						classPK="<%= message.getMessageId() %>"
						contentTitle="<%= message.getSubject() %>"
						reportedUserId="<%= message.getUserId() %>"
					/>
				</c:if>

				<br />

				<div class="entry-links">
					<liferay-ui:asset-links
						className="<%= MBMessage.class.getName() %>"
						classPK="<%= message.getMessageId() %>"
					/>
				</div>

				<ul class="edit-controls unstyled">
					<li>

						<%
						String topHREF = "#" + renderResponse.getNamespace() + "message_0";
						%>

						<liferay-ui:icon
							iconCssClass="icon-long-arrow-up"
							label="<%= true %>"
							message="top"
							url="<%= topHREF %>"
						/>
					</li>

					<c:if test="<%= !thread.isLocked() && MBMessagePermission.contains(permissionChecker, message, ActionKeys.UPDATE) %>">
						<li>
							<portlet:renderURL var="editURL">
								<portlet:param name="struts_action" value="/message_boards/edit_message" />
								<portlet:param name="redirect" value="<%= currentURL %>" />
								<portlet:param name="messageId" value="<%= String.valueOf(message.getMessageId()) %>" />
							</portlet:renderURL>

							<liferay-ui:icon
								iconCssClass="icon-edit"
								label="<%= true %>"
								message="edit"
								url="<%= editURL %>"
							/>
						</li>
					</c:if>

					<c:if test="<%= !thread.isLocked() && !message.isRoot() && MBMessagePermission.contains(permissionChecker, message, ActionKeys.PERMISSIONS) %>">
						<li>
							<liferay-security:permissionsURL
								modelResource="<%= MBMessage.class.getName() %>"
								modelResourceDescription="<%= HtmlUtil.escape(message.getSubject()) %>"
								resourcePrimKey="<%= String.valueOf(message.getMessageId()) %>"
								var="permissionsURL"
								windowState="<%= LiferayWindowState.POP_UP.toString() %>"
							/>

							<liferay-ui:icon
								iconCssClass="icon-lock"
								label="<%= true %>"
								message="permissions"
								method="get"
								url="<%= permissionsURL %>"
								useDialog="<%= true %>"
							/>
						</li>
					</c:if>

					<c:if test="<%= (message.getParentMessageId() != MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID) && MBCategoryPermission.contains(permissionChecker, scopeGroupId, category.getCategoryId(), ActionKeys.MOVE_THREAD) %>">
						<li>
							<portlet:renderURL var="splitThreadURL">
								<portlet:param name="struts_action" value="/message_boards/split_thread" />
								<portlet:param name="redirect" value="<%= currentURL %>" />
								<portlet:param name="messageId" value="<%= String.valueOf(message.getMessageId()) %>" />
								<portlet:param name="splitThread" value="<%= Boolean.TRUE.toString() %>" />
							</portlet:renderURL>

							<liferay-ui:icon
								iconCssClass="icon-unlink"
								label="<%= true %>"
								message="split-thread"
								url="<%= splitThreadURL %>"
							/>
						</li>
					</c:if>

					<c:if test="<%= !thread.isLocked() && (thread.getMessageCount() > 1) && MBMessagePermission.contains(permissionChecker, message, ActionKeys.DELETE) %>">
						<li>

							<%
							PortletURL categoryURL = renderResponse.createRenderURL();

							categoryURL.setParameter("struts_action", "/message_boards/view");
							categoryURL.setParameter("mbCategoryId", String.valueOf(message.getCategoryId()));
							%>

							<portlet:actionURL var="deleteURL">
								<portlet:param name="struts_action" value="/message_boards/edit_message" />
								<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.DELETE %>" />
								<portlet:param name="redirect" value="<%= categoryURL.toString() %>" />
								<portlet:param name="messageId" value="<%= String.valueOf(message.getMessageId()) %>" />
							</portlet:actionURL>

							<liferay-ui:icon-delete
								label="<%= true %>"
								url="<%= deleteURL %>"
							/>
						</li>
					</c:if>
				</ul>
			</td>
		</tr>
	</c:if>

	</table>
</div>