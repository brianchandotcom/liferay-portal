<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/admin/common/init.jsp" %>

<%
ViewKBSuggestionDisplayContext viewKBSuggestionDisplayContext = new ViewKBSuggestionDisplayContext(request, renderRequest, renderResponse, rootPortletId);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(viewKBSuggestionDisplayContext.getRedirect());
portletDisplay.setURLBackTitle(portletDisplay.getTitle());

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
						<liferay-user:user-portrait
							userId="<%= viewKBSuggestionDisplayContext.getKBCommentUserId() %>"
						/>
					</div>
				</div>

				<div class="card-col-content card-col-gutters">
					<div class="h5 text-default">
						<%= HtmlUtil.escape(viewKBSuggestionDisplayContext.getModifiedDateLabel()) %>
					</div>

					<div class="h4">
						<%= HtmlUtil.escape(viewKBSuggestionDisplayContext.getKBCommentTitle()) %>
					</div>

					<div class="h5">
						<span class="kb-comment-status text-default">
							<liferay-ui:message key="<%= viewKBSuggestionDisplayContext.getKBCommentStatusLabel() %>" />
						</span>

						<a href="<%= viewKBSuggestionDisplayContext.getKBArticleURL() %>">
							<%= HtmlUtil.escape(viewKBSuggestionDisplayContext.getKBArticleTitle()) %>
						</a>
					</div>
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

<aui:script>
	var deleteButtonElement = document.getElementById(
		'<portlet:namespace />deleteButton'
	);

	if (deleteButtonElement) {
		deleteButtonElement.addEventListener('click', (event) => {
			Liferay.Util.openConfirmModal({
				message:
					'<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />',
				onConfirm: (isConfirmed) => {
					if (!isConfirmed) {
						event.preventDefault();
					}
				},
			});
		});
	}
</aui:script>