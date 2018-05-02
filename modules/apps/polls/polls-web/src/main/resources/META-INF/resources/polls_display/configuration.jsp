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

<%@ include file="/polls_display/init.jsp" %>

<%
questionId = ParamUtil.getLong(request, "questionId", questionId);

List<PollsQuestion> questions = PollsQuestionLocalServiceUtil.getQuestions(scopeGroupId);

if (scopeGroupId != themeDisplay.getCompanyGroupId()) {
	questions = ListUtil.copy(questions);

	questions.addAll(PollsQuestionLocalServiceUtil.getQuestions(themeDisplay.getCompanyGroupId()));
}
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<div class="portlet-configuration-body-content">
		<div class="container-fluid-1280">
			<liferay-ui:error exception="<%= NoSuchQuestionException.class %>" message="the-question-could-not-be-found" />

			<c:choose>
				<c:when test="<%= !questions.isEmpty() %>">
					<aui:fieldset-group markupView="lexicon">
						<aui:fieldset>
							<aui:select label="title" name="preferences--questionId--">
								<aui:option value="" />

								<%
								for (PollsQuestion question : questions) {
									question = question.toEscapedModel();
								%>

									<aui:option label="<%= question.getTitle(locale) %>" selected="<%= questionId == question.getQuestionId() %>" value="<%= question.getQuestionId() %>" />

								<%
								}
								%>

							</aui:select>
						</aui:fieldset>
					</aui:fieldset-group>
				</c:when>
				<c:otherwise>
					<div class="alert alert-info">
						<liferay-ui:message key="there-are-no-available-questions-for-selection" />
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>

	<aui:button-row>
		<aui:button disabled="<%= questions.isEmpty() %>" type="submit" />
		<aui:button type="cancel" />
	</aui:button-row>
</aui:form>