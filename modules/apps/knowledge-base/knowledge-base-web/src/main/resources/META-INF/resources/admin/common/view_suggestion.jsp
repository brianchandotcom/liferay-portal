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

String kbCommentTitle = StringUtil.shorten(HtmlUtil.escape(kbComment.getContent()), 100);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);

renderResponse.setTitle(kbCommentTitle);
%>

<div class="card list-group-card panel" id="<portlet:namespace /><%= kbComment.getKbCommentId() %>">
	<div class="panel-heading">
		<div class="card-row card-row-padded">
			<div class="card-col-field">
				<div class="list-group-card-icon">
					<liferay-ui:user-portrait cssClass="user-icon-lg" userId="<%= kbComment.getUserId() %>" />
				</div>
			</div>

			<div class="card-col-content card-col-gutters">

				<%
				Date modifiedDate = kbComment.getModifiedDate();

				String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - modifiedDate.getTime(), true);
				%>

				<h5 class="text-default">
					<liferay-ui:message arguments="<%= new String[] {kbComment.getUserName(), modifiedDateDescription} %>" key="x-suggested-x-ago" />
				</h5>

				<h4>
					<%= kbCommentTitle %>
				</h4>

				<h5>

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
		<div class="text-default">
			<%= HtmlUtil.replaceNewLine(HtmlUtil.escape(kbComment.getContent())) %>
		</div>
	</div>
</div>

<%@ include file="/admin/common/suggestion_action_buttons.jsp" %>