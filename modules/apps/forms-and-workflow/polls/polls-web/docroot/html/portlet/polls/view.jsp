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

<%@ include file="/html/portlet/polls/init.jsp" %>

<%
boolean showAddPollButton = PollsResourcePermissionChecker.contains(permissionChecker, scopeGroupId, ActionKeys.ADD_QUESTION);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("struts_action", "/polls/view");
%>


<div class="container-fluid-1280">
	<aui:form method="post" name="fm">

		<liferay-ui:search-container
			iteratorURL="<%= portletURL %>"
			total="<%= PollsQuestionLocalServiceUtil.getQuestionsCount(scopeGroupId) %>"
		>

			<liferay-ui:search-container-results
				results="<%= PollsQuestionLocalServiceUtil.getQuestions(scopeGroupId, searchContainer.getStart(), searchContainer.getEnd()) %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.polls.model.PollsQuestion"
				modelVar="question"
			>

				<%
				PortletURL rowURL = renderResponse.createRenderURL();

				rowURL.setParameter("struts_action", "/polls/view_question");
				rowURL.setParameter("redirect", currentURL);
				rowURL.setParameter("questionId", String.valueOf(question.getQuestionId()));
				%>

				<liferay-ui:search-container-column-text
					cssClass="content-column title-column"
					href="<%= rowURL %>"
					name="title"
					truncate="<%= true %>"
					value="<%= HtmlUtil.escape(question.getTitle(locale)) %>"
				/>

				<liferay-ui:search-container-column-text
					cssClass="num-of-votes-column"
					href="<%= rowURL %>"
					name="num-of-votes"
					value="<%= String.valueOf(PollsVoteLocalServiceUtil.getQuestionVotesCount(question.getQuestionId())) %>"
				/>

				<c:choose>
					<c:when test="<%= question.getLastVoteDate() != null %>">
						<liferay-ui:search-container-column-date
							cssClass="last-vote-date-column text-column"
							href="<%= rowURL %>"
							name="last-vote-date"
							value="<%= question.getLastVoteDate() %>"
						/>
					</c:when>
					<c:otherwise>
						<liferay-ui:search-container-column-text
							cssClass="last-vote-date-column text-column"
							href="<%= rowURL %>"
							name="last-vote-date"
							value='<%= LanguageUtil.get(request, "never") %>'
						/>
					</c:otherwise>
				</c:choose>

				<c:choose>
					<c:when test="<%= question.getExpirationDate() != null %>">
						<liferay-ui:search-container-column-date
							cssClass="expiration-date-column text-column"
							href="<%= rowURL %>"
							name="expiration-date"
							value="<%= question.getExpirationDate() %>"
						/>
					</c:when>
					<c:otherwise>
						<liferay-ui:search-container-column-text
							cssClass="expiration-date-column text-column"
							href="<%= rowURL %>"
							name="expiration-date"
							value='<%= LanguageUtil.get(request, "never") %>'
						/>
					</c:otherwise>
				</c:choose>

				<liferay-ui:search-container-column-jsp
					cssClass="entry-action-column"
					path="/html/portlet/polls/question_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator displayStyle="list" markupView="lexicon" searchContainer="<%= searchContainer %>" />
		</liferay-ui:search-container>
	</aui:form>
</div>

<c:if test="<%= showAddPollButton %>">
	<portlet:renderURL var="editQuestionURL">
		<portlet:param name="struts_action" value="/polls/edit_question" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
	</portlet:renderURL>

	<liferay-frontend:add-menu>
		<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add-poll") %>' url="<%= editQuestionURL.toString() %>" />
	</liferay-frontend:add-menu>
</c:if>