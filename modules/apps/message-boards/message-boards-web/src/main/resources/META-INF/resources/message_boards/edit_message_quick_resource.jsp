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
MBMessageDisplay messageDisplay = ActionUtil.getMessageDisplay(request);

MBMessage message = messageDisplay.getMessage();

long categoryId = message.getCategoryId();
long threadId = message.getThreadId();
long parentMessageId = message.getMessageId();
String subject = ParamUtil.getString(request, "subject");
double priority = message.getPriority();

if (threadId > 0) {
	try {
		if (Validator.isNull(subject)) {
			if (message.getSubject().startsWith(MBMessageConstants.MESSAGE_SUBJECT_PREFIX_RE)) {
				subject = message.getSubject();
			}
			else {
				subject = MBMessageConstants.MESSAGE_SUBJECT_PREFIX_RE + message.getSubject();
			}
		}
	}
	catch (Exception e) {
	}
}

String editorName = MBUtil.getEditorName(messageFormat);

boolean quote = ParamUtil.getBoolean(request, "quote");

String quoteText = null;

if (quote) {
	if (messageFormat.equals("bbcode")) {
		quoteText = MBUtil.getBBCodeQuoteBody(request, message);
	}
	else {
		quoteText = MBUtil.getHtmlQuoteBody(request, message);
	}
}

String redirect = ParamUtil.getString(request, "redirect");

boolean showPermanentLink = GetterUtil.getBoolean(request.getAttribute("edit-message.jsp-showPermanentLink"));
%>

<div class="panel-heading">
	<div class="card-row card-row-padded">
		<div class="card-col-field">
			<div class="list-group-card-icon">
				<liferay-ui:user-portrait
					userId="<%= themeDisplay.getUserId() %>"
				/>
			</div>
		</div>

		<div class="card-col-content card-col-gutters">

			<%
			String userName = PortalUtil.getUserName(themeDisplay.getUserId(), StringPool.BLANK);

			if (Validator.isNull(userName) && !themeDisplay.isSignedIn()) {
				userName = LanguageUtil.get(resourceBundle, "anonymous");
			}

			String userDisplayText = LanguageUtil.format(request, "x-replying", new Object[] {HtmlUtil.escape(userName)});
			%>

			<h5 class="message-user-display text-default" title="<%= userDisplayText %>">
				<%= userDisplayText %>
			</h5>

			<h4 title="<%= HtmlUtil.escape(message.getSubject()) %>">
				<c:choose>
					<c:when test="<%= showPermanentLink %>">
						<a href="#<portlet:namespace />message_<%= message.getMessageId() %>" title="<liferay-ui:message key="permanent-link-to-this-item" />">
							<%= HtmlUtil.escape(message.getSubject()) %>
						</a>
					</c:when>
					<c:otherwise>
						<%= HtmlUtil.escape(message.getSubject()) %>
					</c:otherwise>
				</c:choose>
			</h4>

			<%
			MBStatsUser statsUser = MBStatsUserLocalServiceUtil.getStatsUser(scopeGroupId, themeDisplay.getUserId());

			String[] ranks = MBUserRankUtil.getUserRank(mbGroupServiceSettings, themeDisplay.getLanguageId(), statsUser);
			%>

			<c:if test="<%= Validator.isNotNull(ranks[1]) %>">
				<span class="h5 text-default" title="<%= HtmlUtil.escape(ranks[1]) %>">
					<%= HtmlUtil.escape(ranks[1]) %>
				</span>
			</c:if>

			<c:if test="<%= Validator.isNotNull(ranks[0]) %>">
				<span class="h5 text-default" title="<%= HtmlUtil.escape(ranks[0]) %>">
					<%= HtmlUtil.escape(ranks[0]) %>
				</span>
			</c:if>
		</div>
	</div>
</div>

<div class="divider"></div>

<div class="panel-body">
	<div class="card-row card-row-padded message-content" id="<%= liferayPortletResponse.getNamespace() + "addQuickReply" + parentMessageId %>">
		<portlet:actionURL name="/message_boards/edit_message" var="editMessageURL" />

		<aui:form action="<%= editMessageURL %>" method="post" name='<%= "addQuickReplyFm" + parentMessageId %>' onSubmit='<%= "event.preventDefault(); " %>'>
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>" />
			<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
			<aui:input name="messageId" type="hidden" value="<%= 0 %>" />
			<aui:input name="mbCategoryId" type="hidden" value="<%= categoryId %>" />
			<aui:input name="threadId" type="hidden" value="<%= threadId %>" />
			<aui:input name="parentMessageId" type="hidden" value="<%= parentMessageId %>" />
			<aui:input name="subject" type="hidden" value="<%= subject %>" />
			<aui:input name="priority" type="hidden" value="<%= priority %>" />
			<aui:input name="propagatePermissions" type="hidden" value="<%= true %>" />
			<aui:input name="workflowAction" type="hidden" value="<%= String.valueOf(WorkflowConstants.ACTION_PUBLISH) %>" />

			<aui:model-context bean="<%= message %>" model="<%= MBMessage.class %>" />

			<liferay-ui:input-editor
				allowBrowseDocuments="<%= false %>"
				autoCreate="<%= true %>"
				configKey="replyMBEditor"
				contents="<%= quoteText %>"
				cssClass='<%= editorName.startsWith("alloyeditor") ? "form-control" : StringPool.BLANK %>'
				editorName="<%= editorName %>"
				name='<%= "replyMessageBody" + parentMessageId %>'
				onChangeMethod='<%= "replyMessageOnChange" + parentMessageId %>'
				placeholder='<%= LanguageUtil.get(request, "type-your-reply") %>'
				showSource="<%= false %>"
				skipEditorLoading="<%= true %>"
			/>

			<aui:input name="body" type="hidden" />

			<c:if test="<%= captchaConfiguration.messageBoardsEditMessageCaptchaEnabled() %>">
				<portlet:resourceURL id="/message_boards/captcha" var="captchaURL" />

				<liferay-captcha:captcha
					url="<%= captchaURL %>"
				/>
			</c:if>

			<aui:button cssClass="advanced-reply btn btn-link btn-sm" value="advanced-reply" />

			<c:if test="<%= themeDisplay.isSignedIn() && !SubscriptionLocalServiceUtil.isSubscribed(themeDisplay.getCompanyId(), user.getUserId(), MBThread.class.getName(), threadId) && !SubscriptionLocalServiceUtil.isSubscribed(themeDisplay.getCompanyId(), user.getUserId(), MBCategory.class.getName(), categoryId) %>">
				<aui:input helpMessage="message-boards-message-subscribe-me-help" label="subscribe-me" name="subscribe" type='<%= (mbGroupServiceSettings.isEmailMessageAddedEnabled() || mbGroupServiceSettings.isEmailMessageUpdatedEnabled()) ? "checkbox" : "hidden" %>' value="<%= subscribeByDefault %>" />
			</c:if>

			<liferay-expando:custom-attributes-available
				className="<%= MBMessage.class.getName() %>"
			>
				<liferay-expando:custom-attribute-list
					className="<%= MBMessage.class.getName() %>"
					classPK="<%= (message != null) ? message.getMessageId() : 0 %>"
					editable="<%= true %>"
					label="<%= true %>"
				/>
			</liferay-expando:custom-attributes-available>

			<aui:button-row>

				<%
				String publishButtonLabel = "publish";

				if (WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(themeDisplay.getCompanyId(), scopeGroupId, MBMessage.class.getName())) {
					publishButtonLabel = "submit-for-publication";
				}
				%>

				<aui:button name='<%= "replyMessageButton" + parentMessageId %>' type="submit" value="<%= publishButtonLabel %>" />

				<%
				String taglibCancelReply = "javascript:" + liferayPortletResponse.getNamespace() + "hideReplyMessage('" + parentMessageId + "');";
				%>

				<aui:button onClick="<%= taglibCancelReply %>" type="cancel" />
			</aui:button-row>
		</aui:form>

		<portlet:renderURL var="advancedReplyURL">
			<portlet:param name="mvcRenderCommandName" value="/message_boards/edit_message" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="mbCategoryId" value="<%= String.valueOf(message.getCategoryId()) %>" />
			<portlet:param name="threadId" value="<%= String.valueOf(message.getThreadId()) %>" />
			<portlet:param name="parentMessageId" value="<%= String.valueOf(message.getMessageId()) %>" />
			<portlet:param name="priority" value="<%= String.valueOf(message.getPriority()) %>" />
		</portlet:renderURL>

		<aui:form action="<%= advancedReplyURL %>" cssClass="hide" method="post" name='<%= "advancedReplyFm" + parentMessageId %>'>
			<aui:input name="body" type="hidden" />
		</aui:form>
	</div>
</div>

<aui:script require='<%= npmResolvedPackageName + "/message_boards/js/MBPortlet.es as MBPortlet" %>'>
	new MBPortlet.default({
		constants: {
			ACTION_PUBLISH: '<%= WorkflowConstants.ACTION_PUBLISH %>',
			CMD: '<%= Constants.CMD %>'
		},
		currentAction: '<%= Constants.ADD %>',
		namespace: '<portlet:namespace />',
		replyToMessageId: '<%= parentMessageId %>',
		rootNode: '#<portlet:namespace />addQuickReply<%= parentMessageId %>'
	});
</aui:script>

<aui:script>
	window[
		'<portlet:namespace />replyMessageOnChange' + <%= parentMessageId %>
	] = function(html) {
		Liferay.Util.toggleDisabled(
			'#<portlet:namespace />replyMessageButton<%= parentMessageId %>',
			html === ''
		);
	};
</aui:script>