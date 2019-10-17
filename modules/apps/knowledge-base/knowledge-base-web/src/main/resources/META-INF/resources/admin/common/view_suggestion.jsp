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
KBComment kbComment = KBCommentServiceUtil.getKBComment(ParamUtil.getLong(request, "kbCommentId"));

String kbCommentTitle = StringUtil.shorten(kbComment.getContent(), 100);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(kbCommentTitle);
%>

<div class="card panel" id="<portlet:namespace /><%= kbComment.getKbCommentId() %>">
	<div class="panel-heading">
		<div class="card-row card-row-padded">
			<div class="card-col-field">
				<div class="list-group-card-icon">
					<liferay-ui:user-portrait
						userId="<%= kbComment.getUserId() %>"
					/>
				</div>
			</div>

			<div class="card-col-content card-col-gutters">

				<%
				Date modifiedDate = kbComment.getModifiedDate();

				String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - modifiedDate.getTime(), true);
				%>

				<h5 class="text-default">
					<liferay-ui:message arguments="<%= new String[] {HtmlUtil.escape(kbComment.getUserName()), modifiedDateDescription} %>" key="x-suggested-x-ago" />
				</h5>

				<h4>
					<%= HtmlUtil.escape(kbCommentTitle) %>
				</h4>

				<h5>
					<span class="kb-comment-status text-default">
						<liferay-ui:message key="<%= KBUtil.getStatusLabel(kbComment.getStatus()) %>" />
					</span>

					<%
					KBArticle kbArticle = KBArticleServiceUtil.getLatestKBArticle(kbComment.getClassPK(), WorkflowConstants.STATUS_ANY);

					KBArticleURLHelper kbArticleURLHelper = new KBArticleURLHelper(renderRequest, renderResponse, templatePath);

					PortletURL viewKBArticleURL = kbArticleURLHelper.createViewWithRedirectURL(kbArticle, currentURL);
					%>

					<a href="<%= viewKBArticleURL.toString() %>"><%= HtmlUtil.escape(kbArticle.getTitle()) %></a>
				</h5>
			</div>
		</div>
	</div>

	<div class="divider"></div>

	<div class="panel-body">
		<div class="card-row card-row-padded text-default">
			<%= HtmlUtil.replaceNewLine(HtmlUtil.escape(kbComment.getContent())) %>
		</div>
	</div>
</div>

<%
int previousStatus = KBUtil.getPreviousStatus(kbComment.getStatus());
int nextStatus = KBUtil.getNextStatus(kbComment.getStatus());
%>

<c:if test="<%= KBArticlePermission.contains(permissionChecker, kbArticle, KBActionKeys.UPDATE) || KBCommentPermission.contains(permissionChecker, kbComment, KBActionKeys.DELETE) %>">
	<aui:button-row>
		<c:if test="<%= KBArticlePermission.contains(permissionChecker, kbArticle, KBActionKeys.UPDATE) %>">
			<c:if test="<%= previousStatus != KBCommentConstants.STATUS_NONE %>">
				<liferay-portlet:actionURL name="updateKBCommentStatus" varImpl="previousStatusURL">
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="kbCommentId" value="<%= String.valueOf(kbComment.getKbCommentId()) %>" />
					<portlet:param name="kbCommentStatus" value="<%= String.valueOf(previousStatus) %>" />
				</liferay-portlet:actionURL>

				<aui:button href="<%= previousStatusURL.toString() %>" name="previousStatusButton" type="submit" value="<%= KBUtil.getStatusTransitionLabel(previousStatus) %>" />
			</c:if>

			<c:if test="<%= nextStatus != KBCommentConstants.STATUS_NONE %>">
				<liferay-portlet:actionURL name="updateKBCommentStatus" varImpl="nextStatusURL">
					<portlet:param name="redirect" value="<%= currentURL %>" />
					<portlet:param name="kbCommentId" value="<%= String.valueOf(kbComment.getKbCommentId()) %>" />
					<portlet:param name="kbCommentStatus" value="<%= String.valueOf(nextStatus) %>" />
				</liferay-portlet:actionURL>

				<aui:button href="<%= nextStatusURL.toString() %>" name="nextStatusButton" type="submit" value="<%= KBUtil.getStatusTransitionLabel(nextStatus) %>" />
			</c:if>
		</c:if>

		<c:if test="<%= KBCommentPermission.contains(permissionChecker, kbComment, KBActionKeys.DELETE) %>">
			<liferay-portlet:actionURL name="deleteKBComment" varImpl="deleteURL">
				<portlet:param name="redirect" value="<%= redirect %>" />
				<portlet:param name="kbCommentId" value="<%= String.valueOf(kbComment.getKbCommentId()) %>" />
			</liferay-portlet:actionURL>

			<aui:button href="<%= deleteURL.toString() %>" name="deleteButton" value="<%= Constants.DELETE %>" />
		</c:if>
	</aui:button-row>
</c:if>

<aui:script sandbox="<%= true %>">
	var deleteButtonElement = document.getElementById(
		'<portlet:namespace />deleteButton'
	);

	if (deleteButtonElement) {
		deleteButtonElement.addEventListener('click', function(event) {
			if (
				!confirm(
					'<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />'
				)
			) {
				event.preventDefault();
			}
		});
	}
</aui:script>