<%--
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
String redirect = StringPool.BLANK;

PollsQuestion question = (PollsQuestion)request.getAttribute(WebKeys.POLLS_QUESTION);

question = question.toEscapedModel();

List<PollsChoice> choices = question.getChoices();

boolean hasVoted = PollsUtil.hasVoted(request, question.getQuestionId());

if (!question.isExpired() && !hasVoted && PollsQuestionPermission.contains(permissionChecker, question, ActionKeys.ADD_VOTE)) {
	String cmd = ParamUtil.getString(request, Constants.CMD);

	if (cmd.equals(Constants.ADD)) {
		long choiceId = ParamUtil.getLong(request, "choiceId");

		try {
			PollsVoteServiceUtil.addVote(question.getQuestionId(), choiceId, new ServiceContext());

			SessionMessages.add(renderRequest, "vote_added");

			PollsUtil.saveVote(request, question.getQuestionId());

			hasVoted = true;
		}
		catch (DuplicateVoteException dve) {
			SessionErrors.add(renderRequest, dve.getClass().getName());
		}
		catch (NoSuchChoiceException nsce) {
			SessionErrors.add(renderRequest, nsce.getClass().getName());
		}
		catch (QuestionExpiredException qee) {
		}
	}
}
%>

<portlet:renderURL var="viewPollURL">
	<portlet:param name="struts_action" value="/polls_display/view" />
</portlet:renderURL>

<aui:form action="<%= viewPollURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>" />
	<aui:input name="questionId" type="hidden" value="<%= question.getQuestionId() %>" />

	<liferay-ui:success key="vote_added" message="thank-you-for-your-vote" />

	<liferay-ui:error exception="<%= DuplicateVoteException.class %>" message="you-may-only-vote-once" />
	<liferay-ui:error exception="<%= NoSuchChoiceException.class %>" message="please-select-an-option" />

	<%= StringUtil.replace(HtmlUtil.escape(question.getDescription(locale)), StringPool.NEW_LINE, "<br />") %>

	<br /><br />

	<c:choose>
		<c:when test="<%= !question.isExpired() && !hasVoted && PollsQuestionPermission.contains(permissionChecker, question, ActionKeys.ADD_VOTE) %>">
			<aui:fieldset>
				<aui:field-wrapper>

					<%
					for (PollsChoice choice : choices) {
						choice = choice.toEscapedModel();
					%>

						<aui:input inlineLabel="left" label='<%= "<strong>" + choice.getName() + ".</strong> " + choice.getDescription(locale) %>' name="choiceId" type="radio" value="<%= choice.getChoiceId() %>" />

					<%
					}
					%>

				</aui:field-wrapper>

				<aui:button type="submit" value="vote" />
			</aui:fieldset>
		</c:when>
		<c:otherwise>
			<%@ include file="/html/portlet/polls/view_question_results.jspf" %>
		</c:otherwise>
	</c:choose>
</aui:form>