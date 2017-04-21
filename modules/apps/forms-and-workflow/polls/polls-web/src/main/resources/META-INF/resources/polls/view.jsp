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

<%@ include file="/polls/init.jsp" %>

<%
boolean showAddPollButton = PollsResourcePermissionChecker.contains(permissionChecker, scopeGroupId, ActionKeys.ADD_QUESTION);

PortletURL portletURL = pollsDisplayContext.getBasePortletURL();

portletURL.setParameter("mvcRenderCommandName", "/polls/view");
%>

<liferay-util:include page="/polls/navigation_bar.jsp" servletContext="<%= application %>" />

<liferay-util:include page="/polls/management_bar.jsp" servletContext="<%= application %>" />

<div class="container-fluid-1280 main-content-body">
	<aui:form method="post" name="fm">
		<liferay-ui:error exception="<%= DuplicateVoteException.class %>" message="you-may-only-vote-once" />
		<liferay-ui:error exception="<%= NoSuchChoiceException.class %>" message="please-select-an-option" />

		<liferay-ui:search-container
			emptyResultsMessage="no-entries-were-found"
			iteratorURL="<%= portletURL %>"
			orderByComparator="<%= pollsDisplayContext.getPollsQuestionOrderByComparator() %>"
			searchTerms="<%= new DisplayTerms(renderRequest) %>"
		>
			<liferay-ui:search-container-results>
				<%@ include file="/polls/question_search_results.jspf" %>
			</liferay-ui:search-container-results>

			<liferay-ui:search-container-row
				className="com.liferay.polls.model.PollsQuestion"
				modelVar="question"
			>

				<%
				PortletURL rowURL = renderResponse.createRenderURL();

				rowURL.setParameter("mvcRenderCommandName", "/polls/view_question");
				rowURL.setParameter("redirect", currentURL);
				rowURL.setParameter("questionId", String.valueOf(question.getQuestionId()));
				%>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					href="<%= rowURL %>"
					name="title"
					value="<%= HtmlUtil.escape(question.getTitle(locale)) %>"
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
							value='<%= LanguageUtil.get(request, "never") %>'
						/>
					</c:otherwise>
				</c:choose>

				<c:choose>
					<c:when test="<%= question.getExpirationDate() != null %>">
						<c:choose>
							<c:when test="<%= question.getExpirationDate().before(new Date()) %>">
								<liferay-ui:search-container-column-text
									href="<%= rowURL %>"
									name="expiration-date"
									value='<%= LanguageUtil.get(request, "expired") %>'
								/>
							</c:when>
							<c:otherwise>
								<liferay-ui:search-container-column-date
									href="<%= rowURL %>"
									name="expiration-date"
									value="<%= question.getExpirationDate() %>"
								/>
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
						<liferay-ui:search-container-column-text
							href="<%= rowURL %>"
							name="expiration-date"
							value='<%= LanguageUtil.get(request, "never") %>'
						/>
					</c:otherwise>
				</c:choose>

				<liferay-ui:search-container-column-jsp
					path="/polls/question_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator displayStyle="list" markupView="lexicon" searchContainer="<%= searchContainer %>" />
		</liferay-ui:search-container>
	</aui:form>
</div>

<c:if test="<%= showAddPollButton %>">
	<portlet:renderURL var="editQuestionURL">
		<portlet:param name="mvcRenderCommandName" value="/polls/edit_question" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
	</portlet:renderURL>

	<liferay-frontend:add-menu>
		<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add-poll") %>' url="<%= editQuestionURL.toString() %>" />
	</liferay-frontend:add-menu>
</c:if>