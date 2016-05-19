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
KBSuggestionListDisplayContext kbSuggestionListDisplayContext = (KBSuggestionListDisplayContext)request.getAttribute(KBWebKeys.KNOWLEDGE_BASE_KB_SUGGESTION_LIST_DISPLAY_CONTEXT);
%>

<liferay-portlet:renderURL varImpl="iteratorURL" />

<%
kbSuggestionListDisplayContext.getViewSuggestionURL(iteratorURL);
%>

<div id="<portlet:namespace />kbArticleCommentsWrapper">
	<liferay-ui:search-container
		emptyResultsMessage="no-suggestions-were-found"
		iteratorURL="<%= iteratorURL %>"
		total="<%= kbSuggestionListDisplayContext.getKBCommentsCount() %>"
	>
		<liferay-ui:search-container-results
			results="<%= kbSuggestionListDisplayContext.getKBComments(searchContainer) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.knowledge.base.model.KBComment"
			modelVar="kbComment"
		>
			<liferay-ui:search-container-column-user
				cssClass="user-icon-lg"
				showDetails="<%= false %>"
				userId="<%= kbComment.getUserId() %>"
			/>

			<liferay-ui:search-container-column-text colspan="<%= 2 %>">

				<%
				Date modifiedDate = kbComment.getModifiedDate();

				String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - modifiedDate.getTime(), true);
				%>

				<h5 class="text-default">
					<liferay-ui:message arguments="<%= new String[] {kbComment.getUserName(), modifiedDateDescription} %>" key="x-suggested-x-ago" />
				</h5>

				<h4>
					<%= StringUtil.shorten(HtmlUtil.replaceNewLine(HtmlUtil.escape(kbComment.getContent())), 100) %>
				</h4>

				<h5 class="text-default">

					<%
					KBArticle kbArticle = KBArticleServiceUtil.getLatestKBArticle(kbComment.getClassPK(), WorkflowConstants.STATUS_ANY);

					request.setAttribute(KBWebKeys.KNOWLEDGE_BASE_KB_ARTICLE, kbArticle);

					KBArticleURLHelper kbArticleURLHelper = new KBArticleURLHelper(renderRequest, renderResponse, templatePath);

					PortletURL viewKBArticleURL = kbArticleURLHelper.createViewWithRedirectURL(kbArticle, currentURL);
					%>

					<c:if test="<%= kbSuggestionListDisplayContext.isShowKBArticleTitle() %>">
						<a href="<%= viewKBArticleURL.toString() %>"><%= HtmlUtil.escape(kbArticle.getTitle()) %></a>
					</c:if>
				</h5>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-jsp
				path="/admin/common/suggestion_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="descriptive" markupView="lexicon" resultRowSplitter="<%= new KBCommentResultRowSplitter(kbSuggestionListDisplayContext, resourceBundle) %>" />
	</liferay-ui:search-container>
</div>

<aui:script use="aui-base">
	A.one('#<portlet:namespace />kbArticleCommentsWrapper').delegate(
		'click',
		function(e) {
			if (confirm('<liferay-ui:message key="are-you-sure-you-want-to-delete-this" />')) {
				location.href = this.getData('href');
			}
		},
		'.kb-suggestion-actions .kb-suggestion-delete'
	);
</aui:script>