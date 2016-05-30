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

<%
KBSuggestionListDisplayContext kbSuggestionListDisplayContext = new KBSuggestionListDisplayContext(request, templatePath, scopeGroupId);

int previousStatus = KnowledgeBaseUtil.getPreviousStatus(kbComment.getStatus());
int nextStatus = KnowledgeBaseUtil.getNextStatus(kbComment.getStatus());
%>

<c:if test="<%= KBArticlePermission.contains(permissionChecker, kbArticle, KBActionKeys.UPDATE) %>">
	<aui:button-row>
		<c:if test="<%= previousStatus != KBCommentConstants.STATUS_NONE %>">
			<liferay-portlet:actionURL name="updateKBCommentStatus" varImpl="previousStatusURL">
				<portlet:param name="kbCommentId" value="<%= String.valueOf(kbComment.getKbCommentId()) %>" />
				<portlet:param name="kbCommentStatus" value="<%= String.valueOf(previousStatus) %>" />
			</liferay-portlet:actionURL>

			<aui:button cssClass="btn-lg" href="<%= kbSuggestionListDisplayContext.getViewSuggestionURL(previousStatusURL) %>" name="previousStatusButton" type="submit" value="<%= KnowledgeBaseUtil.getStatusTransitionLabel(previousStatus) %>" />
		</c:if>

		<c:if test="<%= nextStatus != KBCommentConstants.STATUS_NONE %>">
			<liferay-portlet:actionURL name="updateKBCommentStatus" varImpl="nextStatusURL">
				<portlet:param name="kbCommentId" value="<%= String.valueOf(kbComment.getKbCommentId()) %>" />
				<portlet:param name="kbCommentStatus" value="<%= String.valueOf(nextStatus) %>" />
			</liferay-portlet:actionURL>

			<aui:button cssClass="btn-lg" href="<%= kbSuggestionListDisplayContext.getViewSuggestionURL(nextStatusURL) %>" name="previousStatusButton" type="submit" value="<%= KnowledgeBaseUtil.getStatusTransitionLabel(nextStatus) %>" />
		</c:if>

		<c:if test="<%= KBCommentPermission.contains(permissionChecker, kbComment, KBActionKeys.DELETE) %>">
			<liferay-portlet:actionURL name="deleteKBComment" varImpl="deleteURL">
				<portlet:param name="kbCommentId" value="<%= String.valueOf(kbComment.getKbCommentId()) %>" />
			</liferay-portlet:actionURL>

			<aui:button cssClass="btn-lg" href="<%= kbSuggestionListDisplayContext.getViewSuggestionURL(deleteURL) %>" name="previousStatusButton" value="<%= Constants.DELETE %>" />
		</c:if>
	</aui:button-row>
</c:if>