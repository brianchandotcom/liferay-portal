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
String redirect = ParamUtil.getString(request, "redirect");

PollsQuestion question = (PollsQuestion)request.getAttribute(PollsWebKeys.POLLS_QUESTION);

question = question.toEscapedModel();

List<PollsChoice> choices = PollsChoiceLocalServiceUtil.getChoices(question.getQuestionId());

boolean hasVoted = PollsUtil.hasVoted(request, question.getQuestionId());

boolean viewResults = ParamUtil.getBoolean(request, "viewResults");

if (viewResults && !PollsQuestionPermissionChecker.contains(permissionChecker, question, ActionKeys.UPDATE)) {
	viewResults = false;
}

renderResponse.setTitle(question.getTitle(locale));

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);
%>

<portlet:actionURL var="viewQuestionActionURL">
	<portlet:param name="struts_action" value="/polls/view_question" />
</portlet:actionURL>

<aui:form action="<%= viewQuestionActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<portlet:renderURL var="viewQuestionRenderURL">
		<portlet:param name="struts_action" value="/polls/view_question" />
		<portlet:param name="redirect" value="<%= redirect %>" />
		<portlet:param name="questionId" value="<%= String.valueOf(question.getQuestionId()) %>" />
	</portlet:renderURL>

	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.VOTE %>" />
	<aui:input name="redirect" type="hidden" value="<%= viewQuestionRenderURL %>" />
	<aui:input name="questionId" type="hidden" value="<%= question.getQuestionId() %>" />

	<liferay-ui:error exception="<%= DuplicateVoteException.class %>" message="you-may-only-vote-once" />
	<liferay-ui:error exception="<%= NoSuchChoiceException.class %>" message="please-select-an-option" />

	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>
			<span>
				<%= StringUtil.replace(question.getDescription(locale), CharPool.NEW_LINE, "<br />") %>
			</span>

			<br /><br />

			<c:choose>
				<c:when test="<%= !viewResults && !question.isExpired() && !hasVoted && PollsQuestionPermissionChecker.contains(permissionChecker, question, ActionKeys.ADD_VOTE) %>">
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

					<c:if test="<%= PollsQuestionPermissionChecker.contains(permissionChecker, question, ActionKeys.UPDATE) %>">
						<portlet:renderURL var="viewResultsURL">
							<portlet:param name="struts_action" value="/polls/view_question" />
							<portlet:param name="redirect" value="<%= redirect %>" />
							<portlet:param name="questionId" value="<%= String.valueOf(question.getQuestionId()) %>" />
							<portlet:param name="viewResults" value="1" />
						</portlet:renderURL>

						<liferay-ui:icon
							iconCssClass="icon-search"
							label="<%= true %>"
							message="view-results"
							url="<%= viewResultsURL %>"
						/>
					</c:if>

					<aui:button-row>
						<aui:button cssClass="btn-lg" type="submit" value="vote[action]" />

						<aui:button cssClass="btn-lg" href="<%= redirect %>" type="cancel" />
					</aui:button-row>

					<%
					PortalUtil.addPortletBreadcrumbEntry(request, HtmlUtil.unescape(question.getTitle(locale)), currentURL);
					%>

				</c:when>
				<c:otherwise>
					<%@ include file="/html/portlet/polls/view_question_results.jspf" %>

					<portlet:renderURL var="viewQuestionURL">
						<portlet:param name="struts_action" value="/polls/view_question" />
						<portlet:param name="redirect" value="<%= redirect %>" />
						<portlet:param name="questionId" value="<%= String.valueOf(question.getQuestionId()) %>" />
					</portlet:renderURL>

					<div class="button-holder">
						<c:choose>
							<c:when test="<%= !question.isExpired() && !hasVoted && PollsQuestionPermissionChecker.contains(permissionChecker, question, ActionKeys.ADD_VOTE) %>">
								<aui:button href="<%= viewQuestionURL %>" value="back-to-vote" />
							</c:when>
						</c:choose>
					</div>

					<%
					PortalUtil.addPortletBreadcrumbEntry(request, HtmlUtil.unescape(question.getTitle(locale)), viewQuestionURL.toString());
					PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "results"), currentURL);
					%>

				</c:otherwise>
			</c:choose>
		</aui:fieldset>
	</aui:fieldset-group>
</aui:form>