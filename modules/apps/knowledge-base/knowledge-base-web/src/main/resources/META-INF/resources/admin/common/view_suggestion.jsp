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
KBViewSuggestionDisplayContext kbViewSuggestionDisplayContext = new KBViewSuggestionDisplayContext(request, renderRequest, renderResponse, rootPortletId);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(kbViewSuggestionDisplayContext.getRedirect());

renderResponse.setTitle(kbViewSuggestionDisplayContext.getKBCommentTitle());
%>

<clay:container-fluid
	cssClass="<%= kbViewSuggestionDisplayContext.getContainerCssClass() %>"
>
	<div class="card panel" id="<portlet:namespace /><%= kbViewSuggestionDisplayContext.getKBCommentId() %>">
		<div class="panel-heading">
			<div class="card-body">
				<div class="card-col-field">
					<div class="list-group-card-icon">
						<liferay-ui:user-portrait
							userId="<%= kbViewSuggestionDisplayContext.getKBCommentUserId() %>"
						/>
					</div>
				</div>

				<div class="card-col-content card-col-gutters">
					<h5 class="text-default">
						<%= HtmlUtil.escape(kbViewSuggestionDisplayContext.getModifiedDateLabel()) %>
					</h5>

					<h4>
						<%= HtmlUtil.escape(kbViewSuggestionDisplayContext.getKBCommentTitle()) %>
					</h4>

					<h5>
						<span class="kb-comment-status text-default">
							<liferay-ui:message key="<%= kbViewSuggestionDisplayContext.getKBCommentStatusLabel() %>" />
						</span>

						<a href="<%= kbViewSuggestionDisplayContext.getKBArticleURL() %>">
							<%= HtmlUtil.escape(kbViewSuggestionDisplayContext.getKBArticleTitle()) %>
						</a>
					</h5>
				</div>
			</div>
		</div>

		<div class="divider"></div>

		<div class="panel-body">
			<div class="card-body text-default">
				<%= HtmlUtil.replaceNewLine(HtmlUtil.escape(kbViewSuggestionDisplayContext.getKBCommentContent())) %>
			</div>
		</div>
	</div>

	<c:if test="<%= kbViewSuggestionDisplayContext.isKBCommentActionsVisible() %>">
		<aui:button-row>
			<c:if test="<%= kbViewSuggestionDisplayContext.canTransitionToPreviousStatus() %>">
				<aui:button href="<%= kbViewSuggestionDisplayContext.getPreviousStatusTransitionURL() %>" name="previousStatusButton" type="submit" value="<%= kbViewSuggestionDisplayContext.getPreviousStatusTransitionLabel() %>" />
			</c:if>

			<c:if test="<%= kbViewSuggestionDisplayContext.canTransitionToNextStatus() %>">
				<aui:button href="<%= kbViewSuggestionDisplayContext.getNextStatusTransitionURL() %>" name="nextStatusButton" type="submit" value="<%= kbViewSuggestionDisplayContext.getNextStatusTransitionLabel() %>" />
			</c:if>

			<c:if test="<%= kbViewSuggestionDisplayContext.hasDeleteKBCommentPermission() %>">
				<aui:button href="<%= kbViewSuggestionDisplayContext.getDeleteKBCommentURL() %>" name="deleteButton" value="<%= Constants.DELETE %>" />
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