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

KBSuggestionListManagementToolbarDisplayContext kbSuggestionListManagementToolbarDisplayContext = (KBSuggestionListManagementToolbarDisplayContext)request.getAttribute("view_suggestions.jsp-kbSuggestionListManagementToolbarDisplayContext");
SearchContainer kbCommentsSearchContainer = (SearchContainer)request.getAttribute("view_suggestions.jsp-searchContainer");

KBCommentResultRowSplitter resultRowSplitter = (KBCommentResultRowSplitter)request.getAttribute("view_suggestions.jsp-resultRowSplitter");
%>

<liferay-portlet:actionURL name="deleteKBComments" varImpl="deleteKBCommentsURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</liferay-portlet:actionURL>

<aui:form action="<%= deleteKBCommentsURL %>" name="fm">
	<liferay-ui:search-container
		id="kbComments"
		searchContainer="<%= kbCommentsSearchContainer %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.knowledge.base.model.KBComment"
			keyProperty="kbCommentId"
			modelVar="kbComment"
		>
			<c:if test="<%= kbSuggestionListManagementToolbarDisplayContext != null %>">

				<%
				Map<String, Object> rowData = new HashMap<String, Object>();

				rowData.put("actions", StringUtil.merge(kbSuggestionListManagementToolbarDisplayContext.getAvailableActions(kbComment)));

				row.setData(rowData);
				%>

			</c:if>

			<liferay-ui:search-container-column-user
				showDetails="<%= false %>"
				userId="<%= kbComment.getUserId() %>"
			/>

			<liferay-ui:search-container-column-text
				colspan="<%= 2 %>"
			>

				<%
				Date modifiedDate = kbComment.getModifiedDate();

				String modifiedDateDescription = LanguageUtil.getTimeDescription(request, System.currentTimeMillis() - modifiedDate.getTime(), true);
				%>

				<span class="text-default">
					<liferay-ui:message arguments="<%= new String[] {HtmlUtil.escape(kbComment.getUserName()), modifiedDateDescription} %>" key="x-suggested-x-ago" />
				</span>

				<h2 class="h5">
					<liferay-portlet:renderURL varImpl="rowURL">
						<portlet:param name="mvcPath" value='<%= templatePath + "view_suggestion.jsp" %>' />
						<portlet:param name="kbCommentId" value="<%= String.valueOf(kbComment.getKbCommentId()) %>" />
						<portlet:param name="redirect" value="<%= currentURL %>" />
					</liferay-portlet:renderURL>

					<aui:a href="<%= rowURL.toString() %>">
						<%= StringUtil.shorten(HtmlUtil.escape(kbComment.getContent()), 100) %>
					</aui:a>
				</h2>

				<span>
					<span class="kb-comment-status text-default">
						<liferay-ui:message key="<%= KBUtil.getStatusLabel(kbComment.getStatus()) %>" />
					</span>

					<%
					KBArticle kbArticle = KBArticleServiceUtil.getLatestKBArticle(kbComment.getClassPK(), WorkflowConstants.STATUS_ANY);

					request.setAttribute(KBWebKeys.KNOWLEDGE_BASE_KB_ARTICLE, kbArticle);

					KBArticleURLHelper kbArticleURLHelper = new KBArticleURLHelper(renderRequest, renderResponse, templatePath);

					PortletURL viewKBArticleURL = kbArticleURLHelper.createViewWithRedirectURL(kbArticle, currentURL);
					%>

					<c:if test="<%= kbSuggestionListDisplayContext.isShowKBArticleTitle() %>">
						<a class="kb-article-link text-default" href="<%= viewKBArticleURL.toString() %>"><%= HtmlUtil.escape(kbArticle.getTitle()) %></a>
					</c:if>
				</span>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-jsp
				path="/admin/common/suggestion_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="descriptive"
			markupView="lexicon"
			resultRowSplitter="<%= resultRowSplitter %>"
		/>
	</liferay-ui:search-container>
</aui:form>