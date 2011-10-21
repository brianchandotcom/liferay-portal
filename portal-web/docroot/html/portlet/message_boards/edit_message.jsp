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
String redirect = ParamUtil.getString(request, "redirect");

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

		parentAuthor = curParentMessage.isAnonymous() ? LanguageUtil.get(pageContext, "anonymous") : HtmlUtil.escape(PortalUtil.getUserName(curParentMessage.getUserId(), curParentMessage.getUserName()));
	}
	catch (Exception e) {
	}
}

String body = BeanParamUtil.getString(message, request, "body");
boolean attachments = BeanParamUtil.getBoolean(message, request, "attachments");
boolean preview = ParamUtil.getBoolean(request, "preview");
boolean quote = ParamUtil.getBoolean(request, "quote");
boolean splitThread = ParamUtil.getBoolean(request, "splitThread");

String[] existingAttachments = new String[0];

if ((message != null) && message.isAttachments()) {
	existingAttachments = DLStoreUtil.getFileNames(message.getCompanyId(), CompanyConstants.SYSTEM, message.getAttachmentsDir());
}

boolean allowPingbacks = PropsValues.MESSAGE_BOARDS_PINGBACK_ENABLED && BeanParamUtil.getBoolean(message, request, "allowPingbacks", true);

if (Validator.isNull(redirect)) {
	PortletURL viewMessageURL = renderResponse.createRenderURL();

	viewMessageURL.setParameter("struts_action", "/message_boards/view_message");
	viewMessageURL.setParameter("messageId", String.valueOf(messageId));

	redirect = viewMessageURL.toString();
}
%>

<liferay-ui:header
	backURL="<%= redirect %>"
	localizeTitle="<%= (message == null) %>"
	title='<%= (message == null) ? "new-message" : message.getSubject() %>'
/>

<c:if test="<%= preview %>">
	<liferay-ui:message key="preview" />:

	<%
	MBMessage temp = null;

	if (message != null) {
		temp = message;

		message = new MBMessageImpl();

		message.setMessageId(temp.getMessageId());
		message.setCompanyId(temp.getCompanyId());
		message.setUserId(temp.getUserId());
		message.setUserName(temp.getUserName());
		message.setCreateDate(temp.getCreateDate());
		message.setModifiedDate(temp.getModifiedDate());
		message.setThreadId(temp.getThreadId());
		message.setSubject(subject);
		message.setBody(body);
		message.setFormat(messageFormat);
		message.setAttachments(temp.isAttachments());
		message.setAnonymous(temp.isAnonymous());
	}
	else {
		message = new MBMessageImpl();

		message.setMessageId(messageId);
		message.setCompanyId(user.getCompanyId());
		message.setUserId(user.getUserId());
		message.setUserName(user.getFullName());
		message.setCreateDate(new Date());
		message.setModifiedDate(new Date());
		message.setThreadId(threadId);
		message.setSubject(subject);
		message.setBody(body);
		message.setFormat(messageFormat);
		message.setAttachments(attachments);
		message.setAnonymous(BeanParamUtil.getBoolean(message, request, "anonymous"));
	}

	boolean editable = false;

	MBCategory category = null;

	int depth = 0;

	String className = "portlet-section-body results-row";
	String classHoverName = "portlet-section-body-hover results-row hover";

	request.setAttribute("edit_message.jsp-assetTagNames", ParamUtil.getString(request, "assetTagNames"));
	%>

	<%@ include file="/html/portlet/message_boards/view_thread_message.jspf" %>

	<%
	request.removeAttribute("edit_message.jsp-assetTagNames");

	message = temp;
	%>

	<br />
</c:if>

<portlet:actionURL var="editMessageURL">
	<portlet:param name="struts_action" value="/message_boards/edit_message" />
</portlet:actionURL>

<aui:form action="<%= editMessageURL %>" enctype='<%= attachments ? "multipart/form-data" : "" %>' method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveMessage(false);" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="messageId" type="hidden" value="<%= messageId %>" />
	<aui:input name="mbCategoryId" type="hidden" value="<%= categoryId %>" />
	<aui:input name="threadId" type="hidden" value="<%= threadId %>" />
	<aui:input name="parentMessageId" type="hidden" value="<%= parentMessageId %>" />
	<aui:input name="attachments" type="hidden" value="<%= attachments %>" />
	<aui:input name="preview" type="hidden" />
	<aui:input name="workflowAction" type="hidden" value="<%= String.valueOf(WorkflowConstants.ACTION_SAVE_DRAFT) %>" />

	<liferay-ui:error exception="<%= CaptchaMaxChallengesException.class %>" message="maximum-number-of-captcha-attempts-exceeded" />
	<liferay-ui:error exception="<%= CaptchaTextException.class %>" message="text-verification-failed" />
	<liferay-ui:error exception="<%= LockedThreadException.class %>" message="thread-is-locked" />
	<liferay-ui:error exception="<%= MessageBodyException.class %>" message="please-enter-a-valid-message" />
	<liferay-ui:error exception="<%= MessageSubjectException.class %>" message="please-enter-a-valid-subject" />

	<liferay-ui:error exception="<%= FileExtensionException.class %>">
		<liferay-ui:message key="document-names-must-end-with-one-of-the-following-extensions" /><%= StringUtil.merge(PrefsPropsUtil.getStringArray(PropsKeys.DL_FILE_EXTENSIONS, StringPool.COMMA), StringPool.COMMA_AND_SPACE) %>.
	</liferay-ui:error>

	<liferay-ui:error exception="<%= FileNameException.class %>" message="please-enter-a-file-with-a-valid-file-name" />

	<liferay-ui:error exception="<%= FileSizeException.class %>">

		<%
		long fileMaxSize = PrefsPropsUtil.getLong(PropsKeys.DL_FILE_MAX_SIZE);

		if (fileMaxSize == 0) {
			fileMaxSize = PrefsPropsUtil.getLong(PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE);
		}

		fileMaxSize /= 1024;
		%>

		<liferay-ui:message arguments="<%= fileMaxSize %>" key="please-enter-a-file-with-a-valid-file-size-no-larger-than-x" />
	</liferay-ui:error>

	<liferay-ui:asset-categories-error />

	<liferay-ui:asset-tags-error />

	<aui:model-context bean="<%= message %>" model="<%= MBMessage.class %>" />

	<aui:fieldset>
		<c:if test="<%= message != null %>">
			<aui:workflow-status status="<%= message.getStatus() %>" />
		</c:if>

		<aui:input name="subject" value="<%= subject %>" />

		<aui:field-wrapper label="body">
			<c:choose>
				<c:when test='<%= ((messageId != 0) && message.isFormatBBCode()) || ((messageId == 0) && messageFormat.equals("bbcode")) %>'>
					<%@ include file="/html/portlet/message_boards/bbcode_editor.jspf" %>
				</c:when>
				<c:otherwise>
					<%@ include file="/html/portlet/message_boards/html_editor.jspf" %>
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

		<c:if test="<%= attachments %>">
			<aui:field-wrapper label="attachments">
				<table class="lfr-table">

				<%
				for (int i = 0; i < existingAttachments.length; i++) {
					String existingPath = existingAttachments[i];

					String existingName = StringUtil.extractLast(existingPath, CharPool.SLASH);
				%>

					<tr>
						<td>
							<span id="<portlet:namespace />existingFile<%= i + 1 %>">
								<aui:input name='<%= "existingPath" + (i + 1) %>' type="hidden" value="<%= existingPath %>" />

								<%= existingName %>
							</span>

							<aui:input cssClass="aui-helper-hidden" label="" name='<%= "msgFile" + (i + 1) %>' size="70" type="file" />
						</td>
						<td>
							<img id="<portlet:namespace />removeExisting<%= i + 1 %>" src="<%= themeDisplay.getPathThemeImages() %>/arrows/02_x.png" />
						</td>
					</tr>

				<%
				}
				%>

				<%
				for (int i = existingAttachments.length + 1; i <= 5; i++) {
				%>

					<tr>
						<td>
							<aui:input label="" name='<%= "msgFile" + i %>' size="70" type="file" />
						</td>
						<td></td>
					</tr>

				<%
				}
				%>

				</table>
			</aui:field-wrapper>
		</c:if>

		<c:if test="<%= curParentMessage == null %>">

			<%
			boolean question = threadAsQuestionByDefault;

			if (message != null) {
				thread = MBThreadLocalServiceUtil.getThread(threadId);

				if (thread.isQuestion() || message.isAnswer()) {
					question = true;
				}
			}
			%>

			<aui:input helpMessage="message-boards-message-question-help" inlineLabel="left" label="mark-as-a-question" name="question" type="checkbox" value="<%= question %>" />
		</c:if>

		<c:if test="<%= (message == null) && themeDisplay.isSignedIn() && allowAnonymousPosting %>">
			<aui:input helpMessage="message-boards-message-anonymous-help" inlineLabel="left" name="anonymous" type="checkbox" />
		</c:if>

		<c:if test="<%= (message == null) && themeDisplay.isSignedIn() && !SubscriptionLocalServiceUtil.isSubscribed(themeDisplay.getCompanyId(), user.getUserId(), MBThread.class.getName(), threadId) && !SubscriptionLocalServiceUtil.isSubscribed(themeDisplay.getCompanyId(), user.getUserId(), MBCategory.class.getName(), categoryId) %>">
			<aui:input helpMessage="message-boards-message-subscribe-me-help" inlineLabel="left" label="subscribe-me" name="subscribe" type="checkbox" value="<%= subscribeByDefault %>" />
		</c:if>

		<c:if test="<%= (priorities.length > 0) && MBCategoryPermission.contains(permissionChecker, scopeGroupId, categoryId, ActionKeys.UPDATE_THREAD_PRIORITY) %>">

			<%
			double threadPriority = BeanParamUtil.getDouble(message, request, "priority");
			%>

			<aui:select name="priority">
				<aui:option value="" />

				<%
				for (int i = 0; i < priorities.length; i++) {
					String[] priority = StringUtil.split(priorities[i]);

					try {
						String priorityName = priority[0];
						String priorityImage = priority[1];
						double priorityValue = GetterUtil.getDouble(priority[2]);

						if (priorityValue > 0) {
				%>

							<aui:option label="<%= priorityName %>" selected="<%= (threadPriority == priorityValue) %>" value="<%= priorityValue %>" />

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
			<aui:input helpMessage="to-allow-pingbacks,-please-also-ensure-the-entry's-guest-view-permission-is-enabled" inlineLabel="left" label="allow-pingbacks" name="allowPingbacks" value="<%= allowPingbacks %>" />
		</c:if>

		<c:if test="<%= message == null %>">
			<aui:field-wrapper label="permissions">
				<liferay-ui:input-permissions
					modelName="<%= MBMessage.class.getName() %>"
				/>
			</aui:field-wrapper>
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
		<portlet:actionURL windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>" var="captchaURL">
			<portlet:param name="struts_action" value="/message_boards/captcha" />
		</portlet:actionURL>

		<liferay-ui:captcha url="<%= captchaURL %>" />
	</c:if>

	<%
	boolean pending = false;

	if (message != null) {
		pending = message.isPending();
	}
	%>

	<c:if test="<%= pending %>">
		<div class="portlet-msg-info">
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
			<div class="portlet-msg-info">
				<%= LanguageUtil.format(pageContext, "this-x-is-approved.-publishing-these-changes-will-cause-it-to-be-unpublished-and-go-through-the-approval-process-again", ResourceActionsUtil.getModelResource(locale, MBMessage.class.getName())) %>
			</div>
		</c:if>

		<c:if test="<%= MBCategoryPermission.contains(permissionChecker, scopeGroupId, categoryId, ActionKeys.ADD_FILE) %>">
			<aui:button onClick='<%= renderResponse.getNamespace() + "manageAttachments(" + !attachments + ");" %>' value='<%= ((attachments) ? "remove" : "attach") + "-files" %>' />
		</c:if>

		<c:if test="<%=themeDisplay.isSignedIn() %>">
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
		boolean editable = false;

		message = curParentMessage;
		MBCategory category = null;

		int depth = 0;

		String className = "portlet-section-body results-row";
		String classHoverName = "portlet-section-body-hover results-row hover";
		%>

		<%@ include file="/html/portlet/message_boards/view_thread_message.jspf" %>
	</c:if>
</aui:form>

<aui:script>
	function <portlet:namespace />getSuggestionsContent() {
		var content = '';

		content += document.<portlet:namespace />fm.<portlet:namespace />subject.value + ' ';
		content += <portlet:namespace />getHTML();

		return content;
	}

	function <portlet:namespace />manageAttachments(removeAttachments) {
		document.<portlet:namespace />fm.<portlet:namespace />body.value = <portlet:namespace />getHTML();
		document.<portlet:namespace />fm.<portlet:namespace />attachments.value = removeAttachments;

		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />previewMessage() {
		document.<portlet:namespace />fm.<portlet:namespace />body.value = <portlet:namespace />getHTML();
		document.<portlet:namespace />fm.<portlet:namespace />preview.value = 'true';

		<portlet:namespace />saveMessage(true);
	}

	function <portlet:namespace />saveMessage(draft) {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = "<%= (message == null) ? Constants.ADD : Constants.UPDATE %>";
		document.<portlet:namespace />fm.<portlet:namespace />body.value = <portlet:namespace />getHTML();

		if (!draft) {
			document.<portlet:namespace />fm.<portlet:namespace />preview.value = <%= preview %>;
			document.<portlet:namespace />fm.<portlet:namespace />workflowAction.value = <%= WorkflowConstants.ACTION_PUBLISH %>;
		}

		submitForm(document.<portlet:namespace />fm);
	}

	function <portlet:namespace />selectCategory(categoryId, categoryName) {
		document.<portlet:namespace />fm.<portlet:namespace />mbCategoryId.value = categoryId;

		var nameEl = document.getElementById("<portlet:namespace />categoryName");

		nameEl.href = "<portlet:renderURL><portlet:param name="struts_action" value="/message_boards/view" /></portlet:renderURL>&<portlet:namespace />mbCategoryId=" + categoryId;
		nameEl.innerHTML = categoryName + "&nbsp;";
	}

	<c:if test="<%= windowState.equals(WindowState.MAXIMIZED) && !themeDisplay.isFacebook() %>">
		Liferay.Util.focusFormField(document.<portlet:namespace />fm.<portlet:namespace />subject);
	</c:if>
</aui:script>

<aui:script use="aui-base">

	<%
	for (int i = 1; i <= existingAttachments.length; i++) {
	%>
		var removeExisting = A.one("#<portlet:namespace />removeExisting" + <%= i %>);

		if (removeExisting) {
			removeExisting.on(
				'click',
				function(event) {
					var button = event.target;
					var span = A.one("#<portlet:namespace />existingFile" + <%= i %>);
					var file = A.one("#<portlet:namespace />msgFile" + <%= i %>);

					if (button) {
						button.remove();
					}

					if (span) {
						span.remove();
					}

					if (file) {
						file.ancestor('.aui-field').show();
					}
				}
			);
		}
	<%
	}
	%>

</aui:script>

<%
if (curParentMessage != null) {
	MBUtil.addPortletBreadcrumbEntries(curParentMessage, request, renderResponse);

	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "reply"), currentURL);
}
else if (message != null) {
	MBUtil.addPortletBreadcrumbEntries(message, request, renderResponse);

	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "edit"), currentURL);
}
else {
	MBUtil.addPortletBreadcrumbEntries(categoryId, request, renderResponse);

	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, "add-message"), currentURL);
}
%>