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

<%@ include file="/admin/common/init.jsp" %>

<%
KBArticle kbArticle = (KBArticle)request.getAttribute(KBWebKeys.KNOWLEDGE_BASE_KB_ARTICLE);

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

KBComment kbComment = (KBComment)row.getObject();

KBSuggestionListDisplayContext kbSuggestionListDisplayContext = (KBSuggestionListDisplayContext)request.getAttribute(KBWebKeys.KNOWLEDGE_BASE_KB_SUGGESTION_LIST_DISPLAY_CONTEXT);

int previousStatus = KnowledgeBaseUtil.getPreviousStatus(kbComment.getStatus());
int nextStatus = KnowledgeBaseUtil.getNextStatus(kbComment.getStatus());
%>

<c:if test="<%= KBArticlePermission.contains(permissionChecker, kbArticle, KBActionKeys.UPDATE) %>">
	<liferay-ui:icon-menu direction="left-side" icon="<%= StringPool.BLANK %>" markupView="lexicon" message="<%= StringPool.BLANK %>" showWhenSingleIcon="<%= true %>">
		<c:if test="<%= previousStatus != KBCommentConstants.STATUS_NONE %>">
			<liferay-portlet:actionURL name="updateKBCommentStatus" varImpl="previousStatusURL">
				<portlet:param name="kbCommentId" value="<%= String.valueOf(kbComment.getKbCommentId()) %>" />
				<portlet:param name="kbCommentStatus" value="<%= String.valueOf(previousStatus) %>" />
			</liferay-portlet:actionURL>

			<liferay-ui:icon
				message="<%= KnowledgeBaseUtil.getStatusTransitionLabel(previousStatus) %>"
				url="<%= kbSuggestionListDisplayContext.getViewSuggestionURL(previousStatusURL) %>"
			/>
		</c:if>

		<c:if test="<%= nextStatus != KBCommentConstants.STATUS_NONE %>">
			<liferay-portlet:actionURL name="updateKBCommentStatus" varImpl="nextStatusURL">
				<portlet:param name="kbCommentId" value="<%= String.valueOf(kbComment.getKbCommentId()) %>" />
				<portlet:param name="kbCommentStatus" value="<%= String.valueOf(nextStatus) %>" />
			</liferay-portlet:actionURL>

			<liferay-ui:icon
				message="<%= KnowledgeBaseUtil.getStatusTransitionLabel(nextStatus) %>"
				url="<%= kbSuggestionListDisplayContext.getViewSuggestionURL(nextStatusURL) %>"
			/>
		</c:if>

		<c:if test="<%= KBCommentPermission.contains(permissionChecker, kbComment, KBActionKeys.DELETE) %>">
			<liferay-portlet:actionURL name="deleteKBComment" varImpl="deleteURL">
				<portlet:param name="kbCommentId" value="<%= String.valueOf(kbComment.getKbCommentId()) %>" />
			</liferay-portlet:actionURL>

			<liferay-ui:icon-delete
				url="<%= kbSuggestionListDisplayContext.getViewSuggestionURL(deleteURL) %>"
			/>
		</c:if>
	</liferay-ui:icon-menu>
</c:if>