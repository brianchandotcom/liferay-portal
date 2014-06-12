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

<aui:form method="post" name="fm">

	<%
	PortletURL portletURL = renderResponse.createRenderURL();

	portletURL.setParameter("struts_action", "/polls/view");
	%>

	<liferay-ui:search-container
		iteratorURL="<%= portletURL %>"
		total="<%= PollsQuestionLocalServiceUtil.getQuestionsCount(scopeGroupId) %>"
	>

		<%
		boolean showAddPollButton = PollsPermission.contains(permissionChecker, scopeGroupId, ActionKeys.ADD_QUESTION);
		boolean showPermissionsButton = PollsPermission.contains(permissionChecker, scopeGroupId, ActionKeys.PERMISSIONS);
		%>

		<c:if test="<%= showAddPollButton || showPermissionsButton %>">
			<aui:nav-bar>
				<aui:nav cssClass="navbar-nav">
					<c:if test="<%= showAddPollButton %>">
						<portlet:renderURL var="editQuestionURL">
							<portlet:param name="struts_action" value="/polls/edit_question" />
							<portlet:param name="redirect" value="<%= currentURL %>" />
						</portlet:renderURL>

						<aui:nav-item href="<%= editQuestionURL %>" iconCssClass="icon-plus" label="add-poll" />
					</c:if>

					<c:if test="<%= showPermissionsButton %>">
						<liferay-security:permissionsURL
							modelResource="com.liferay.portlet.polls"
							modelResourceDescription="<%= HtmlUtil.escape(themeDisplay.getScopeGroupName()) %>"
							resourcePrimKey="<%= String.valueOf(scopeGroupId) %>"
							var="permissionsURL"
							windowState="<%= LiferayWindowState.POP_UP.toString() %>"
						/>

						<aui:nav-item href="<%= permissionsURL %>" iconCssClass="icon-lock" label="permissions" useDialog="<%= true %>" />
					</c:if>
				</aui:nav>
			</aui:nav-bar>
		</c:if>

		<liferay-ui:search-container-results
			results="<%= PollsQuestionLocalServiceUtil.getQuestions(scopeGroupId, searchContainer.getStart(), searchContainer.getEnd()) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portlet.polls.model.PollsQuestion"
			modelVar="question"
		>

			<%
			PortletURL rowURL = renderResponse.createRenderURL();

			rowURL.setParameter("struts_action", "/polls/view_question");
			rowURL.setParameter("redirect", currentURL);
			rowURL.setParameter("questionId", String.valueOf(question.getQuestionId()));
			%>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="title"
				value="<%= question.getTitle(locale) %>"
			/>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="num-of-votes"
				value="<%= String.valueOf(PollsVoteLocalServiceUtil.getQuestionVotesCount(question.getQuestionId())) %>"
			/>

			<c:choose>
				<c:when test="<%= question.getLastVoteDate() != null %>">
					<liferay-ui:search-container-column-date
						href="<%= rowURL %>"
						name="last-vote-date"
						value="<%= question.getLastVoteDate() %>"
					/>
				</c:when>
				<c:otherwise>
					<liferay-ui:search-container-column-text
						href="<%= rowURL %>"
						name="last-vote-date"
						value='<%= LanguageUtil.get(pageContext, "never") %>'
					/>
				</c:otherwise>
			</c:choose>

			<c:choose>
				<c:when test="<%= question.getExpirationDate() != null %>">
					<liferay-ui:search-container-column-date
						href="<%= rowURL %>"
						name="expiration-date"
						value="<%= question.getExpirationDate() %>"
					/>
				</c:when>
				<c:otherwise>
					<liferay-ui:search-container-column-text
						href="<%= rowURL %>"
						name="expiration-date"
						value='<%= LanguageUtil.get(pageContext, "never") %>'
					/>
				</c:otherwise>
			</c:choose>

			<liferay-ui:search-container-column-jsp
				cssClass="entry-action"
				path="/html/portlet/polls/question_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator searchContainer="<%= searchContainer %>" />
	</liferay-ui:search-container>
</aui:form>