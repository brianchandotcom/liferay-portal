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

String referringPortletResource = ParamUtil.getString(request, "referringPortletResource");

MBMessage message = (MBMessage)request.getAttribute(WebKeys.MESSAGE_BOARDS_MESSAGE);

long messageId = BeanParamUtil.getLong(message, request, "messageId");

long categoryId = MBUtil.getCategoryId(request, message);
long threadId = BeanParamUtil.getLong(message, request, "threadId");
long parentMessageId = BeanParamUtil.getLong(message, request, "parentMessageId", MBMessageConstants.DEFAULT_PARENT_MESSAGE_ID);

String subject = BeanParamUtil.getString(message, request, "subject");

MBThread thread = null;

MBMessage curParentMessage = null;
String parentAuthor = null;

if (threadId > 0) {
	try {
		curParentMessage = MBMessageLocalServiceUtil.getMessage(parentMessageId);

		if (Validator.isNull(subject)) {
			if (curParentMessage.getSubject().startsWith("RE: ")) {
				subject = curParentMessage.getSubject();
			}
			else {
				subject = "RE: " + curParentMessage.getSubject();
			}
		}

		parentAuthor = curParentMessage.isAnonymous() ? LanguageUtil.get(request, "anonymous") : HtmlUtil.escape(PortalUtil.getUserName(curParentMessage));
	}
	catch (Exception e) {
	}
}

String body = BeanParamUtil.getString(message, request, "body");
boolean preview = ParamUtil.getBoolean(request, "preview");
boolean quote = ParamUtil.getBoolean(request, "quote");
boolean splitThread = ParamUtil.getBoolean(request, "splitThread");

List<FileEntry> existingAttachmentsFileEntries = new ArrayList<FileEntry>();

if (message != null) {
	existingAttachmentsFileEntries = message.getAttachmentsFileEntries();
}

boolean allowPingbacks = PropsValues.MESSAGE_BOARDS_PINGBACK_ENABLED && BeanParamUtil.getBoolean(message, request, "allowPingbacks", true);

if (Validator.isNull(redirect)) {
	PortletURL viewMessageURL = renderResponse.createRenderURL();

	viewMessageURL.setParameter("mvcRenderCommandName", "/message_boards/view_message");
	viewMessageURL.setParameter("messageId", String.valueOf(messageId));

	redirect = viewMessageURL.toString();
}

if (curParentMessage != null) {

	MBUtil.addPortletBreadcrumbEntries(curParentMessage, request, renderResponse);

	if (!layout.isTypeControlPanel()) {
		PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "reply"), currentURL);
	}
}
else if (message != null) {
	MBUtil.addPortletBreadcrumbEntries(message, request, renderResponse);

	if (!layout.isTypeControlPanel()) {
		PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "edit"), currentURL);
	}
}
else {
	MBUtil.addPortletBreadcrumbEntries(categoryId, request, renderResponse);

	if (!layout.isTypeControlPanel()) {
		PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "add-message"), currentURL);
	}
}

boolean portletTitleBasedNavigation = GetterUtil.getBoolean(portletConfig.getInitParameter("portlet-title-based-navigation"));
%>

<div <%= portletTitleBasedNavigation ? "class=\"container-fluid-1280\"" : StringPool.BLANK %>>
	<c:if test="<%= Validator.isNull(referringPortletResource) %>">
		<liferay-util:include page="/message_boards/top_links.jsp" servletContext="<%= application %>" />
	</c:if>

	<liferay-ui:header
		backURL="<%= redirect %>"
		localizeTitle="<%= (message == null) %>"
		title='<%= (curParentMessage != null) ? LanguageUtil.format(request, "reply-to-x", curParentMessage.getSubject(), false) : (message == null) ? "add-message" : LanguageUtil.format(request, "edit-x", message.getSubject(), false) %>'
	/>

	<c:if test="<%= preview %>">
		<liferay-ui:message key="preview" />:

		<%
		MBMessage previewMessage = message;

		if (message == null) {
			previewMessage = new MBMessageImpl();

			previewMessage.setMessageId(messageId);
			previewMessage.setCompanyId(user.getCompanyId());
			previewMessage.setUserId(user.getUserId());
			previewMessage.setUserName(user.getFullName());
			previewMessage.setCreateDate(new Date());
			previewMessage.setModifiedDate(new Date());
			previewMessage.setThreadId(threadId);
			previewMessage.setFormat(messageFormat);
			previewMessage.setAnonymous(ParamUtil.getBoolean(request, "anonymous"));
		}

		previewMessage.setSubject(subject);
		previewMessage.setBody(body);

		MBCategory category = null;

		int depth = 0;

		request.setAttribute("edit_message.jsp-assetTagNames", ParamUtil.getString(request, "assetTagNames"));
		request.setAttribute("edit_message.jsp-category", category);
		request.setAttribute("edit_message.jsp-depth", depth);
		request.setAttribute("edit_message.jsp-editable", Boolean.FALSE);
		request.setAttribute("edit_message.jsp-message", previewMessage);
		request.setAttribute("edit-message.jsp-showDeletedAttachmentsFileEntries", Boolean.TRUE);
		request.setAttribute("edit-message.jsp-showPermanentLink", Boolean.TRUE);
		request.setAttribute("edit-message.jsp-showRecentPosts", Boolean.TRUE);
		request.setAttribute("edit_message.jsp-thread", thread);
		%>

		<liferay-util:include page="/message_boards/view_thread_message.jsp" servletContext="<%= application %>" />

		<%
		request.removeAttribute("edit_message.jsp-assetTagNames");
		%>

		<br />
	</c:if>

	<portlet:actionURL name="/message_boards/edit_message" var="editMessageURL">
		<portlet:param name="mvcRenderCommandName" value="/message_boards/edit_message" />
	</portlet:actionURL>

	<aui:form action="<%= editMessageURL %>" enctype="multipart/form-data" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveMessage(false);" %>'>
		<aui:input name="<%= Constants.CMD %>" type="hidden" />
		<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
		<aui:input name="messageId" type="hidden" value="<%= messageId %>" />
		<aui:input name="mbCategoryId" type="hidden" value="<%= categoryId %>" />
		<aui:input name="threadId" type="hidden" value="<%= threadId %>" />
		<aui:input name="parentMessageId" type="hidden" value="<%= parentMessageId %>" />
		<aui:input name="preview" type="hidden" />
		<aui:input name="workflowAction" type="hidden" value="<%= String.valueOf(WorkflowConstants.ACTION_SAVE_DRAFT) %>" />

		<liferay-ui:error exception="<%= AntivirusScannerException.class %>">

			<%
			AntivirusScannerException ase = (AntivirusScannerException)errorException;
			%>

			<liferay-ui:message key="<%= ase.getMessageKey() %>" />
		</liferay-ui:error>

		<liferay-ui:error exception="<%= CaptchaConfigurationException.class %>" message="a-captcha-error-occurred-please-contact-an-administrator" />
		<liferay-ui:error exception="<%= CaptchaMaxChallengesException.class %>" message="maximum-number-of-captcha-attempts-exceeded" />
		<liferay-ui:error exception="<%= CaptchaTextException.class %>" message="text-verification-failed" />
		<liferay-ui:error exception="<%= DuplicateFileEntryException.class %>" message="please-enter-a-unique-document-name" />

		<liferay-ui:error exception="<%= LiferayFileItemException.class %>">
			<liferay-ui:message arguments="<%= TextFormatter.formatStorageSize(LiferayFileItem.THRESHOLD_SIZE, locale) %>" key="please-enter-valid-content-with-valid-content-size-no-larger-than-x" translateArguments="<%= false %>" />
		</liferay-ui:error>

		<liferay-ui:error exception="<%= FileExtensionException.class %>">
			<liferay-ui:message key="document-names-must-end-with-one-of-the-following-extensions" /><%= StringUtil.merge(PrefsPropsUtil.getStringArray(PropsKeys.DL_FILE_EXTENSIONS, StringPool.COMMA), StringPool.COMMA_AND_SPACE) %>.
		</liferay-ui:error>

		<liferay-ui:error exception="<%= FileNameException.class %>" message="please-enter-a-file-with-a-valid-file-name" />

		<%
		long uploadServletRequestImplMaxSize = PrefsPropsUtil.getLong(PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE);
		%>

		<liferay-ui:error exception="<%= FileSizeException.class %>">

			<%
			long fileMaxSize = PrefsPropsUtil.getLong(PropsKeys.DL_FILE_MAX_SIZE);

			if (fileMaxSize == 0) {
				fileMaxSize = uploadServletRequestImplMaxSize;
			}
			%>

			<liferay-ui:message arguments="<%= TextFormatter.formatStorageSize(fileMaxSize, locale) %>" key="please-enter-a-file-with-a-valid-file-size-no-larger-than-x" translateArguments="<%= false %>" />
		</liferay-ui:error>

		<liferay-ui:error exception="<%= LockedThreadException.class %>" message="thread-is-locked" />
		<liferay-ui:error exception="<%= MessageBodyException.class %>" message="please-enter-a-valid-message" />
		<liferay-ui:error exception="<%= MessageSubjectException.class %>" message="please-enter-a-valid-subject" />

		<liferay-ui:error exception="<%= UploadRequestSizeException.class %>">
			<liferay-ui:message arguments="<%= TextFormatter.formatStorageSize(uploadServletRequestImplMaxSize, locale) %>" key="request-is-larger-than-x-and-could-not-be-processed" translateArguments="<%= false %>" />
		</liferay-ui:error>

		<liferay-ui:asset-categories-error />

		<liferay-ui:asset-tags-error />

		<aui:model-context bean="<%= message %>" model="<%= MBMessage.class %>" />

		<aui:fieldset>
			<c:if test="<%= message != null %>">
				<aui:workflow-status showIcon="<%= false %>" showLabel="<%= false %>" status="<%= message.getStatus() %>" />
			</c:if>

			<aui:input autoFocus="<%= (windowState.equals(WindowState.MAXIMIZED) && !themeDisplay.isFacebook()) %>" name="subject" value="<%= subject %>" />

			<aui:field-wrapper label="body">
				<c:choose>
					<c:when test='<%= ((messageId != 0) && message.isFormatBBCode()) || ((messageId == 0) && messageFormat.equals("bbcode")) %>'>
						<%@ include file="/message_boards/bbcode_editor.jspf" %>
					</c:when>
					<c:otherwise>
						<%@ include file="/message_boards/html_editor.jspf" %>
					</c:otherwise>
				</c:choose>
				<aui:input name="body" type="hidden" />
			</aui:field-wrapper>

			<liferay-ui:custom-attributes-available className="<%= MBMessage.class.getName() %>">
				<liferay-ui:custom-attribute-list
					className="<%= MBMessage.class.getName() %>"
					classPK="<%= messageId %>"
					editable="<%= true %>"
					label="<%= true %>"
				/>
			</liferay-ui:custom-attributes-available>

			<c:if test="<%= curParentMessage == null %>">

				<%
				boolean disabled = false;
				boolean question = threadAsQuestionByDefault;

				if (message != null) {
					thread = MBThreadLocalServiceUtil.getThread(threadId);

					if (thread.isQuestion() || message.isAnswer()) {
						question = true;
					}
				}
				else {
					MBCategory category = MBCategoryLocalServiceUtil.getCategory(categoryId);

					if ((category != null) && category.getDisplayStyle().equals("question")) {
						disabled = true;
						question = true;
					}
				}
				%>

				<aui:input disabled="<%= disabled %>" helpMessage="message-boards-message-question-help" label="mark-as-a-question" name="question" type="checkbox" value="<%= question %>" />
			</c:if>

			<c:if test="<%= (message == null) && themeDisplay.isSignedIn() && allowAnonymousPosting %>">
				<aui:input helpMessage="message-boards-message-anonymous-help" name="anonymous" type="checkbox" />
			</c:if>

			<c:if test="<%= (message == null) && themeDisplay.isSignedIn() && !SubscriptionLocalServiceUtil.isSubscribed(themeDisplay.getCompanyId(), user.getUserId(), MBThread.class.getName(), threadId) && !SubscriptionLocalServiceUtil.isSubscribed(themeDisplay.getCompanyId(), user.getUserId(), MBCategory.class.getName(), categoryId) %>">
				<aui:input helpMessage="message-boards-message-subscribe-me-help" label="subscribe-me" name="subscribe" type='<%= (mbGroupServiceSettings.isEmailMessageAddedEnabled() || mbGroupServiceSettings.isEmailMessageUpdatedEnabled()) ? "checkbox" : "hidden" %>' value="<%= subscribeByDefault %>" />
			</c:if>

			<c:if test="<%= (priorities.length > 0) && MBCategoryPermission.contains(permissionChecker, scopeGroupId, categoryId, ActionKeys.UPDATE_THREAD_PRIORITY) %>">

				<%
				double threadPriority = BeanParamUtil.getDouble(message, request, "priority");
				%>

				<aui:select name="priority">
					<aui:option value="" />

					<%
					for (int i = 0; i < priorities.length; i++) {
						String[] priority = StringUtil.split(priorities[i], StringPool.PIPE);

						try {
							String priorityName = priority[0];
							String priorityImage = priority[1];
							double priorityValue = GetterUtil.getDouble(priority[2]);

							if (priorityValue > 0) {
					%>

								<aui:option label="<%= HtmlUtil.escape(priorityName) %>" selected="<%= (threadPriority == priorityValue) %>" value="<%= priorityValue %>" />

					<%
							}
						}
						catch (Exception e) {
						}
					}
					%>

				</aui:select>
			</c:if>

			<c:if test="<%= PropsValues.MESSAGE_BOARDS_PINGBACK_ENABLED %>">
				<aui:input helpMessage="to-allow-pingbacks,-please-also-ensure-the-entry's-guest-view-permission-is-enabled" label="allow-pingbacks" name="allowPingbacks" value="<%= allowPingbacks %>" />
			</c:if>

			<c:if test="<%= message == null %>">
				<aui:field-wrapper label="permissions">
					<liferay-ui:input-permissions
						modelName="<%= MBMessage.class.getName() %>"
					/>
				</aui:field-wrapper>
			</c:if>

			<c:if test="<%= MBCategoryPermission.contains(permissionChecker, scopeGroupId, categoryId, ActionKeys.ADD_FILE) %>">
				<liferay-ui:panel cssClass="message-attachments" defaultState="closed" extended="<%= false %>" id="mbMessageAttachmentsPanel" persistState="<%= true %>" title="attachments">
					<c:if test="<%= existingAttachmentsFileEntries.size() > 0 %>">
						<ul>

							<%
							for (int i = 0; i < existingAttachmentsFileEntries.size(); i++) {
								FileEntry fileEntry = existingAttachmentsFileEntries.get(i);

								String taglibDeleteAttachment = "javascript:" + renderResponse.getNamespace() + "trashAttachment(" + (i + 1) + ", '" + Constants.MOVE_TO_TRASH + "');";

								if (!TrashUtil.isTrashEnabled(scopeGroupId)) {
									taglibDeleteAttachment = "javascript:" + renderResponse.getNamespace() + "deleteAttachment(" + (i + 1) + ");";
								}
							%>

								<li class="message-attachment">
									<span id="<portlet:namespace />existingFile<%= i + 1 %>">
										<aui:input id='<%= "existingPath" + (i + 1) %>' name='<%= "existingPath" + (i + 1) %>' type="hidden" value="<%= fileEntry.getFileEntryId() %>" />

										<%
										AssetRendererFactory<?> assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(DLFileEntry.class.getName());

										AssetRenderer<?> assetRenderer = assetRendererFactory.getAssetRenderer(fileEntry.getFileEntryId());
										%>

										<liferay-ui:icon
											iconCssClass="<%= assetRenderer.getIconCssClass() %>"
											label="<%= true %>"
											message="<%= fileEntry.getTitle() %>"
										/>
									</span>

									<aui:input cssClass="hide" label="" name='<%= "msgFile" + (i + 1) %>' size="70" title="message-attachment" type="file" />

									<liferay-ui:icon-delete
										id='<%= "removeExisting" + (i + 1) %>'
										label="<%= true %>"
										message='<%= TrashUtil.isTrashEnabled(scopeGroupId) ? "remove" : "delete" %>'
										method="get"
										trash="<%= TrashUtil.isTrashEnabled(scopeGroupId) %>"
										url="<%= taglibDeleteAttachment %>"
									/>

									<c:if test="<%= TrashUtil.isTrashEnabled(scopeGroupId) %>">

										<%
										StringBundler sb = new StringBundler(7);

										sb.append("javascript:");
										sb.append(renderResponse.getNamespace());
										sb.append("trashAttachment(");
										sb.append(i + 1);
										sb.append(", '");
										sb.append(Constants.RESTORE);
										sb.append("');");
										%>

										<span class="hide" id="<portlet:namespace />undoFile<%= i + 1 %>">
											<aui:input id='<%= "undoPath" + (i + 1) %>' name='<%= "undoPath" + (i + 1) %>' type="hidden" value="<%= fileEntry.getFileEntryId() %>" />

											<span class="undo">(<liferay-ui:message key="marked-as-removed" />)</span> <a class="trash-undo-link" href="<%= sb.toString() %>" id="<portlet:namespace />undo"><liferay-ui:message key="undo" /></a>
										</span>
									</c:if>
								</li>

							<%
							}
							%>

						</ul>
					</c:if>

					<%
					for (int i = existingAttachmentsFileEntries.size() + 1; i <= 5; i++) {
					%>

						<div>
							<aui:input label="" name='<%= "msgFile" + i %>' size="70" title="message-attachment" type="file" />
						</div>

					<%
					}
					%>

				</liferay-ui:panel>
			</c:if>

			<c:if test="<%= (curParentMessage == null) || childrenMessagesTaggable %>">
				<liferay-ui:panel defaultState="closed" extended="<%= false %>" id="mbMessageCategorizationPanel" persistState="<%= true %>" title="categorization">
					<aui:input name="tags" type="assetTags" />
				</liferay-ui:panel>
			</c:if>

			<liferay-ui:panel defaultState="closed" extended="<%= false %>" id="mbMessageAssetLinksPanel" persistState="<%= true %>" title="related-assets">
				<aui:fieldset>
					<liferay-ui:input-asset-links
						className="<%= MBMessage.class.getName() %>"
						classPK="<%= (message != null) ? message.getMessageId() : 0 %>"
					/>
				</aui:fieldset>
			</liferay-ui:panel>
		</aui:fieldset>

		<c:if test="<%= (message == null) && PropsValues.CAPTCHA_CHECK_PORTLET_MESSAGE_BOARDS_EDIT_MESSAGE %>">
			<portlet:resourceURL id="/message_boards/captcha" var="captchaURL" />

			<liferay-ui:captcha url="<%= captchaURL %>" />
		</c:if>

		<%
		boolean pending = false;

		if (message != null) {
			pending = message.isPending();
		}
		%>

		<c:if test="<%= pending %>">
			<div class="alert alert-info">
				<liferay-ui:message key="there-is-a-publication-workflow-in-process" />
			</div>
		</c:if>

		<aui:button-row>

			<%
			String saveButtonLabel = "save";

			if ((message == null) || message.isDraft() || message.isApproved()) {
				saveButtonLabel = "save-as-draft";
			}

			String publishButtonLabel = "publish";

			if (WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(themeDisplay.getCompanyId(), scopeGroupId, MBMessage.class.getName())) {
				publishButtonLabel = "submit-for-publication";
			}
			%>

			<c:if test="<%= (message != null) && message.isApproved() && WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(message.getCompanyId(), message.getGroupId(), MBMessage.class.getName()) %>">
				<div class="alert alert-info">
					<liferay-ui:message arguments="<%= ResourceActionsUtil.getModelResource(locale, MBMessage.class.getName()) %>" key="this-x-is-approved.-publishing-these-changes-will-cause-it-to-be-unpublished-and-go-through-the-approval-process-again" translateArguments="<%= false %>" />
				</div>
			</c:if>

			<c:if test="<%= themeDisplay.isSignedIn() %>">
				<aui:button name="saveButton" onClick='<%= renderResponse.getNamespace() + "saveMessage(true);" %>' value="<%= saveButtonLabel %>" />
			</c:if>

			<aui:button onClick='<%= renderResponse.getNamespace() + "previewMessage();" %>' value="preview" />

			<aui:button disabled="<%= pending %>" name="publishButton" type="submit" value="<%= publishButtonLabel %>" />

			<aui:button href="<%= redirect %>" type="cancel" />
		</aui:button-row>

		<c:if test="<%= curParentMessage != null %>">
			<br /><br />

			<liferay-ui:message key="replying-to" />:

			<%
			message = curParentMessage;
			MBCategory category = null;

			int depth = 0;

			request.setAttribute("edit_message.jsp-category", category);
			request.setAttribute("edit_message.jsp-depth", depth);
			request.setAttribute("edit_message.jsp-editable", Boolean.FALSE);
			request.setAttribute("edit_message.jsp-message", message);
			request.setAttribute("edit-message.jsp-showDeletedAttachmentsFileEntries", Boolean.TRUE);
			request.setAttribute("edit-message.jsp-showPermanentLink", Boolean.TRUE);
			request.setAttribute("edit-message.jsp-showRecentPosts", Boolean.TRUE);
			request.setAttribute("edit_message.jsp-thread", thread);
			%>

			<liferay-util:include page="/message_boards/view_thread_message.jsp" servletContext="<%= application %>" />
		</c:if>
	</aui:form>
</div>

<aui:script>
	function <portlet:namespace />getSuggestionsContent() {
		return AUI.$(document.<portlet:namespace />fm).fm('subject').val() + ' ' + <portlet:namespace />getHTML();
	}

	function <portlet:namespace />previewMessage() {
		<c:if test="<%= (message != null) && !message.isDraft() %>">
			if (!confirm('<liferay-ui:message key="in-order-to-preview-your-changes,-the-message-is-saved-as-a-draft-and-other-users-may-not-be-able-to-see-it" />')) {
				return false;
			}
		</c:if>

		var form = AUI.$(document.<portlet:namespace />fm);

		form.fm('body').val(<portlet:namespace />getHTML());
		form.fm('preview').val('true');

		<portlet:namespace />saveMessage(true);
	}

	function <portlet:namespace />saveMessage(draft) {
		var form = AUI.$(document.<portlet:namespace />fm);

		form.fm('<%= Constants.CMD %>').val('<%= (message == null) ? Constants.ADD : Constants.UPDATE %>');
		form.fm('body').val(<portlet:namespace />getHTML());

		if (!draft) {
			form.fm('preview').val(<%= preview %>);
			form.fm('workflowAction').val(<%= WorkflowConstants.ACTION_PUBLISH %>);
		}

		submitForm(form);
	}

	<c:choose>
		<c:when test="<%= TrashUtil.isTrashEnabled(scopeGroupId) %>">
			function <portlet:namespace />trashAttachment(index, action) {
				var $ = AUI.$;

				var existingPath = $('#<portlet:namespace />existingPath' + index);
				var removeExisting = $('#<portlet:namespace />removeExisting' + index);
				var undoFile = $('#<portlet:namespace />undoFile' + index);

				if (action == '<%= Constants.MOVE_TO_TRASH %>') {
					removeExisting.addClass('hide');
					undoFile.removeClass('hide');

					existingPath.val('');
				}
				else {
					removeExisting.removeClass('hide');
					undoFile.addClass('hide');

					var undoPath = $('#<portlet:namespace />undoPath' + index);

					existingPath.val(undoPath.val());
				}
			}
		</c:when>
		<c:otherwise>
			function <portlet:namespace />deleteAttachment(index) {
				var $ = AUI.$;

				$('#<portlet:namespace />removeExisting' + index).remove();
				$('#<portlet:namespace />existingFile' + index).remove();

				var file = $('#<portlet:namespace />msgFile' + index);

				file.removeClass('hide');
				file.closest('li').addClass('deleted-input');
			}
		</c:otherwise>
	</c:choose>
</aui:script>