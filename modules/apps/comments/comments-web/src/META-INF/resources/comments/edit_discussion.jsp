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

<%@ include file="/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

Comment comment = (Comment)request.getAttribute(WebKeys.COMMENT);

long commentId = BeanParamUtil.getLong(comment, request, "commentId");

long parentCommentId = comment.getParentCommentId();

Comment parentComment = CommentManagerUtil.fetchComment(parentCommentId);

if ((parentComment != null) && parentComment.isRoot()) {
	parentComment = null;
}

WorkflowableComment workflowableComment = null;

boolean approved = true;
boolean pending = false;

if (comment instanceof WorkflowableComment) {
	workflowableComment = (WorkflowableComment)comment;

	approved = workflowableComment.getStatus() == WorkflowConstants.STATUS_APPROVED;
	pending = workflowableComment.getStatus() == WorkflowConstants.STATUS_PENDING;
}
%>

<liferay-ui:header
	backURL="<%= redirect %>"
	title='<%= (comment == null) ? "new-message" : "edit-message" %>'
/>

<portlet:actionURL var="editMessageURL">
	<portlet:param name="struts_action" value="/message_boards/edit_discussion" />
</portlet:actionURL>

<aui:form action="<%= editMessageURL %>" enctype="multipart/form-data" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="commentId" type="hidden" value="<%= commentId %>" />
	<aui:input name="parentCommentId" type="hidden" value="<%= parentCommentId %>" />
	<aui:input name="workflowAction" type="hidden" value="<%= String.valueOf(pending ? WorkflowConstants.ACTION_SAVE_DRAFT : WorkflowConstants.ACTION_PUBLISH) %>" />

	<liferay-ui:error exception="<%= CaptchaConfigurationException.class %>" message="a-captcha-error-occurred-please-contact-an-administrator" />
	<liferay-ui:error exception="<%= CaptchaTextException.class %>" message="text-verification-failed" />
	<liferay-ui:error exception="<%= DiscussionMaxCommentsException.class %>" message="maximum-number-of-comments-has-been-reached" />
	<liferay-ui:error exception="<%= MessageBodyException.class %>" message="please-enter-a-valid-message" />

	<aui:model-context bean="<%= comment %>" model="<%= comment.getModelClass() %>" />

	<aui:fieldset>
		<c:if test="<%= workflowableComment != null %>">
			<aui:workflow-status model="<%= CommentConstants.getDiscussionClass() %>" status="<%= workflowableComment.getStatus() %>" />
		</c:if>

		<aui:input autoFocus="<%= (windowState.equals(WindowState.MAXIMIZED) && !themeDisplay.isFacebook()) %>" name="body" style='<%= "height: " + ModelHintsConstants.TEXTAREA_DISPLAY_HEIGHT + "px; width: " + ModelHintsConstants.TEXTAREA_DISPLAY_WIDTH + "px;" %>' type="textarea" wrap="soft" />
	</aui:fieldset>

	<c:if test="<%= parentComment != null %>">
		<liferay-ui:message key="replying-to" />:

		<%
		request.setAttribute(WebKeys.COMMENT, parentComment);
		%>

		<liferay-util:include page="/comments/asset/discussion_full_content.jsp" />
	</c:if>

	<c:if test="<%= pending %>">
		<div class="alert alert-info">
			<liferay-ui:message key="there-is-a-publication-workflow-in-process" />
		</div>
	</c:if>

	<aui:button-row>
		<c:if test="<%= (comment == null) || !approved %>">
			<aui:button type="submit" />
		</c:if>

		<c:if test="<%= (workflowableComment != null) && approved && WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(workflowableComment.getCompanyId(), workflowableComment.getGroupId(), CommentConstants.getDiscussionClassName()) %>">
			<div class="alert alert-info">
				<%= LanguageUtil.format(request, "this-x-is-approved.-publishing-these-changes-will-cause-it-to-be-unpublished-and-go-through-the-approval-process-again", ResourceActionsUtil.getModelResource(locale, comment.getModelClassName()), false) %>
			</div>
		</c:if>

		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>