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
MBMessageDisplay messageDisplay = (MBMessageDisplay)request.getAttribute(WebKeys.MESSAGE_BOARDS_MESSAGE_DISPLAY);

MBMessage message = messageDisplay.getMessage();

MBCategory category = messageDisplay.getCategory();

MBThread thread = messageDisplay.getThread();
%>

<div id="<portlet:namespace />addAnswerFlagDiv" style="display: none;">
	<liferay-ui:icon
		iconCssClass="icon-check"
		label="<%= true %>"
		message="answer"
	/>

	<c:if test="<%= !MBMessagePermission.contains(permissionChecker, message.getRootMessageId(), ActionKeys.UPDATE) %>">

		<%
		String taglibHREF = "javascript:" + renderResponse.getNamespace() + "deleteAnswerFlag('@MESSAGE_ID@');";
		%>

		(<aui:a href="<%= taglibHREF %>"><liferay-ui:message key="unmark" /></aui:a>)
	</c:if>
</div>

<div id="<portlet:namespace />deleteAnswerFlagDiv" style="display: none;">

	<%
	String taglibMarkAsAnAnswerURL = "javascript:" + renderResponse.getNamespace() + "addAnswerFlag('@MESSAGE_ID@');";
	%>

	<liferay-ui:icon
		iconCssClass="icon-check"
		label="<%= true %>"
		message="mark-as-an-answer"
		url="<%= taglibMarkAsAnAnswerURL %>"
	/>
</div>

<c:choose>
	<c:when test="<%= includeFormTag %>">
		<aui:form>
			<aui:input name="breadcrumbsCategoryId" type="hidden" value="<%= category.getCategoryId() %>" />
			<aui:input name="breadcrumbsMessageId" type="hidden" value="<%= message.getMessageId() %>" />
			<aui:input name="threadId" type="hidden" value="<%= message.getThreadId() %>" />

			<liferay-util:include page="/message_boards/view_message_content.jsp" servletContext="<%= application %>" />
		</aui:form>
	</c:when>
	<c:otherwise>
		<liferay-util:include page="/message_boards/view_message_content.jsp" servletContext="<%= application %>" />
	</c:otherwise>
</c:choose>

<c:if test="<%= MBCategoryPermission.contains(permissionChecker, scopeGroupId, message.getCategoryId(), ActionKeys.REPLY_TO_MESSAGE) && !thread.isLocked() %>">
	<div class="hide" id="<portlet:namespace />addQuickReplyDiv">
		<%@ include file="/message_boards/edit_message_quick.jspf" %>
	</div>
</c:if>

<aui:script>
	function <portlet:namespace />addAnswerFlag(messageId) {
		var $ = AUI.$;

		Liferay.Service(
			'/mbmessage/update-answer',
			{
				answer: true,
				cascade: false,
				messageId: messageId
			}
		);

		var html = $('#<portlet:namespace />addAnswerFlagDiv').html();

		html = '<div class="answer" id="<portlet:namespace />deleteAnswerFlag_' + messageId + '">' + html + '</div>';
		html = html.replace(/@MESSAGE_ID@/g, messageId);

		var tags = $('#<portlet:namespace />message_' + messageId).find('div.tags');

		tags.html(html);

		$('#<portlet:namespace />addAnswerFlag_' + messageId).addClass('hide');
		$('#<portlet:namespace />deleteAnswerFlag_' + messageId).removeClass('hide');
	}

	function <portlet:namespace />addQuickReply(cmd, messageId) {
		var addQuickReplyDiv = AUI.$('#<portlet:namespace />addQuickReplyDiv');

		if (cmd == 'reply') {
			addQuickReplyDiv.removeClass('hide');

			addQuickReplyDiv.find('#<portlet:namespace />parentMessageId').val(messageId);

			var editorInput = addQuickReplyDiv.find('textarea');

			var editorInstance = window[editorInput.attr('id')];

			if (editorInstance) {
				setTimeout(AUI._.bind(editorInstance.focus, editorInstance), 50);
			}
		}
		else {
			addQuickReplyDiv.addClass('hide');
		}
	}

	function <portlet:namespace />deleteAnswerFlag(messageId) {
		var $ = AUI.$;

		Liferay.Service(
			'/mbmessage/update-answer',
			{
				answer: false,
				cascade: false,
				messageId: messageId
			}
		);

		var html = $('#<portlet:namespace />deleteAnswerFlagDiv').html();

		html = '<li id="<portlet:namespace />addAnswerFlag_' + messageId + '">' + html + '</li>';
		html = html.replace(/@MESSAGE_ID@/g, messageId);

		var editControls = $('#<portlet:namespace />message_' + messageId).find('ul.edit-controls');

		editControls.prepend(html);

		$('#<portlet:namespace />deleteAnswerFlag_' + messageId).addClass('hide');

		$('#<portlet:namespace />addAnswerFlag_' + messageId).removeClass('hide');
	}

	<c:if test="<%= thread.getRootMessageId() != message.getMessageId() %>">
		document.getElementById('<portlet:namespace />message_' + <%= message.getMessageId() %>).scrollIntoView(true);
	</c:if>
</aui:script>

<%
MBThreadFlagLocalServiceUtil.addThreadFlag(themeDisplay.getUserId(), thread, new ServiceContext());

message = messageDisplay.getMessage();

PortalUtil.setPageSubtitle(message.getSubject(), request);
PortalUtil.setPageDescription(message.getSubject(), request);

List<AssetTag> assetTags = AssetTagLocalServiceUtil.getTags(MBMessage.class.getName(), message.getMessageId());

PortalUtil.setPageKeywords(ListUtil.toString(assetTags, AssetTag.NAME_ACCESSOR), request);

MBBreadcrumbUtil.addPortletBreadcrumbEntries(message, request, renderResponse);
%>