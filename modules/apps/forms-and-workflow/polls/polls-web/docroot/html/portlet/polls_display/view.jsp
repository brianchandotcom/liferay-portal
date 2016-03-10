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

<%@ include file="/html/portlet/polls_display/init.jsp" %>

<%
PollsQuestion question = (PollsQuestion)request.getAttribute(PollsWebKeys.POLLS_QUESTION);
%>

<%@ include file="/html/portlet/polls_display/view_options.jspf" %>

<c:choose>
	<c:when test="<%= question == null %>">

		<%
		renderRequest.setAttribute(WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.TRUE);
		%>

		<c:choose>
			<c:when test="<%= !layout.isLayoutPrototypeLinkActive() %>">
				<div class="alert alert-info main-content-body portlet-configuration">
					<a href="<%= portletDisplay.getURLConfiguration() %>" onClick="<%= portletDisplay.getURLConfigurationJS() %>">
						<liferay-ui:message key="please-configure-this-portlet-to-make-it-visible-to-all-users" />
					</a>
				</div>
			</c:when>
			<c:otherwise>
				<div class="alert alert-info main-content-body">
					<liferay-ui:message key="please-configure-this-portlet-to-make-it-visible-to-all-users" />
				</div>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>

		<%
		String redirect = StringPool.BLANK;

		question = question.toEscapedModel();

		List<PollsChoice> choices = question.getChoices();

		boolean hasVoted = PollsUtil.hasVoted(request, question.getQuestionId());
		%>

		<portlet:actionURL var="voteQuestionURL">
			<portlet:param name="struts_action" value="/polls_display/vote_question" />
		</portlet:actionURL>

		<aui:form action="<%= voteQuestionURL %>" method="post" name="fm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.VOTE %>" />
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
			<aui:input name="questionId" type="hidden" value="<%= question.getQuestionId() %>" />
			<aui:input name="successMessage" type="hidden" value='<%= LanguageUtil.get(request, "thank-you-for-your-vote") %>' />

			<liferay-ui:error exception="<%= DuplicateVoteException.class %>" message="you-may-only-vote-once" />
			<liferay-ui:error exception="<%= NoSuchChoiceException.class %>" message="please-select-an-option" />

			<%= StringUtil.replace(question.getDescription(locale), CharPool.NEW_LINE, "<br />") %>

			<c:choose>
				<c:when test="<%= !question.isExpired() && !hasVoted && PollsQuestionPermissionChecker.contains(permissionChecker, question, ActionKeys.ADD_VOTE) %>">
					<aui:fieldset>
						<aui:field-wrapper>

							<%
							for (PollsChoice choice : choices) {
								choice = choice.toEscapedModel();
							%>

								<aui:input label='<%= "<strong>" + choice.getName() + ".</strong> " + choice.getDescription(locale) %>' name="choiceId" type="radio" value="<%= choice.getChoiceId() %>" />

							<%
							}
							%>

						</aui:field-wrapper>

						<aui:button type="submit" value="vote[action]" />
					</aui:fieldset>
				</c:when>
				<c:otherwise>
					<%@ include file="/html/portlet/polls/view_question_results.jspf" %>

					<c:if test="<%= !themeDisplay.isSignedIn() && !question.isExpired() && !PollsQuestionPermissionChecker.contains(permissionChecker, question, ActionKeys.ADD_VOTE) %>">
						<div class="alert alert-info">
							<a href="<%= themeDisplay.getURLSignIn() %>" target="_top"><liferay-ui:message key="please-sign-in-to-vote" /></a>
						</div>
					</c:if>
				</c:otherwise>
			</c:choose>
		</aui:form>
	</c:otherwise>
</c:choose>