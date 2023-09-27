<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/discussion/init.jsp" %>

<%
int index = GetterUtil.getInteger(request.getAttribute("liferay-comment:discussion:index"));
int initialIndex = GetterUtil.getInteger(request.getAttribute("liferay-comment:discussion:index"));
String originalNamespace = ParamUtil.getString(request, "namespace");
String randomNamespace = GetterUtil.getString(request.getAttribute("liferay-comment:discussion:randomNamespace"));
int rootIndexPage = GetterUtil.getInteger(request.getAttribute("liferay-comment:discussion:rootIndexPage"));

DiscussionRequestHelper discussionRequestHelper = new DiscussionRequestHelper(request);
DiscussionTaglibHelper discussionTaglibHelper = new DiscussionTaglibHelper(request);

Discussion discussion = CommentManagerUtil.getDiscussion(discussionTaglibHelper.getUserId(), discussionRequestHelper.getScopeGroupId(), discussionTaglibHelper.getClassName(), discussionTaglibHelper.getClassPK(), new ServiceContextFunction(request));

DiscussionComment rootDiscussionComment = (discussion == null) ? null : discussion.getRootDiscussionComment();

DiscussionCommentIterator discussionCommentIterator = (rootDiscussionComment == null) ? null : rootDiscussionComment.getThreadDiscussionCommentIterator(rootIndexPage - 1);
%>

<c:if test="<%= discussionCommentIterator != null %>">

	<%
	while (discussionCommentIterator.hasNext()) {
		rootIndexPage = discussionCommentIterator.getIndexPage();

		if (index >= (initialIndex + PropsValues.DISCUSSION_COMMENTS_DELTA_VALUE)) {
			break;
		}

		request.setAttribute("aui:form:portletNamespace", originalNamespace + randomNamespace);
		request.setAttribute("liferay-comment:discussion:depth", 0);
		request.setAttribute("liferay-comment:discussion:discussion", discussion);
		request.setAttribute("liferay-comment:discussion:discussionComment", discussionCommentIterator.next());
	%>

		<liferay-util:include page="/discussion/view_message_thread.jsp" servletContext="<%= application %>" />

	<%
		index = GetterUtil.getInteger(request.getAttribute("liferay-comment:discussion:index"));
	}
	%>

</c:if>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"hideMoreComments", (rootDiscussionComment != null) && (discussion.getDiscussionCommentsCount() <= index)
		).put(
			"index", index
		).put(
			"originalNamespace", originalNamespace
		).put(
			"randomNamespace", randomNamespace
		).put(
			"rootIndexPage", rootIndexPage
		).build()
	%>'
	module="discussion/js/PageResources"
/>