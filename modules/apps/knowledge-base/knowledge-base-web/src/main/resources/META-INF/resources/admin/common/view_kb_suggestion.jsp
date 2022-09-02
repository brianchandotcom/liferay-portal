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
ViewKBSuggestionDisplayContext viewKBSuggestionDisplayContext = new ViewKBSuggestionDisplayContext(request, renderRequest, renderResponse, rootPortletId);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(viewKBSuggestionDisplayContext.getRedirect());

renderResponse.setTitle(viewKBSuggestionDisplayContext.getKBCommentTitle());
%>

<clay:container-fluid
	cssClass="<%= viewKBSuggestionDisplayContext.getContainerCssClass() %>"
>
	<div class="card panel" id="<portlet:namespace /><%= viewKBSuggestionDisplayContext.getKBCommentId() %>">
		<div class="panel-heading">
			<div class="card-body">
				<div class="card-col-field">
					<div class="list-group-card-icon">
						<liferay-ui:user-portrait
							userId="<%= viewKBSuggestionDisplayContext.getKBCommentUserId() %>"
						/>
					</div>
				</div>

				<div class="card-col-content card-col-gutters">
					<h5 class="text-default">
						<%= HtmlUtil.escape(viewKBSuggestionDisplayContext.getModifiedDateLabel()) %>
					</h5>

					<h4>
						<%= HtmlUtil.escape(viewKBSuggestionDisplayContext.getKBCommentTitle()) %>
					</h4>

					<h5>
						<span class="kb-comment-status text-default">
							<liferay-ui:message key="<%= viewKBSuggestionDisplayContext.getKBCommentStatusLabel() %>" />
						</span>

						<a href="<%= viewKBSuggestionDisplayContext.getKBArticleURL() %>">
							<%= HtmlUtil.escape(viewKBSuggestionDisplayContext.getKBArticleTitle()) %>
						</a>
					</h5>
				</div>
			</div>
		</div>

		<div class="divider"></div>

		<div class="panel-body">
			<div class="card-body text-default">
				<%= HtmlUtil.replaceNewLine(HtmlUtil.escape(viewKBSuggestionDisplayContext.getKBCommentContent())) %>
			</div>
		</div>
	</div>

	<c:if test="<%= viewKBSuggestionDisplayContext.isKBCommentActionsVisible() %>">
		<aui:button-row>
			<c:if test="<%= viewKBSuggestionDisplayContext.canTransitionToPreviousStatus() %>">
				<aui:button href="<%= viewKBSuggestionDisplayContext.getPreviousStatusTransitionURL() %>" name="previousStatusButton" type="submit" value="<%= viewKBSuggestionDisplayContext.getPreviousStatusTransitionLabel() %>" />
			</c:if>

			<c:if test="<%= viewKBSuggestionDisplayContext.canTransitionToNextStatus() %>">
				<aui:button href="<%= viewKBSuggestionDisplayContext.getNextStatusTransitionURL() %>" name="nextStatusButton" type="submit" value="<%= viewKBSuggestionDisplayContext.getNextStatusTransitionLabel() %>" />
			</c:if>

			<c:if test="<%= viewKBSuggestionDisplayContext.hasDeleteKBCommentPermission() %>">
				<aui:button href="<%= viewKBSuggestionDisplayContext.getDeleteKBCommentURL() %>" name="deleteButton" value="<%= Constants.DELETE %>" />
			</c:if>
		</aui:button-row>
	</c:if>
</clay:container-fluid>

<script>
	var deleteButtonElement = document.getElementById(
		'<portlet:namespace />deleteButton'
	);

	if (deleteButtonElement) {
		deleteButtonElement.addEventListener('click', (event) => {
			if (
				!confirm(
					'<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />'
				)
			) {
				event.preventDefault();
			}
		});
	}
</script>