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
String redirect = ParamUtil.getString(request, "redirect");

String referringPortletResource = ParamUtil.getString(request, "referringPortletResource");

PollsQuestion question = (PollsQuestion)request.getAttribute(PollsWebKeys.POLLS_QUESTION);

long questionId = BeanParamUtil.getLong(question, request, "questionId");

boolean neverExpire = ParamUtil.getBoolean(request, "neverExpire", true);

if (question != null) {
	if (question.getExpirationDate() != null) {
		neverExpire = false;
	}
}

List choices = new ArrayList();

int oldChoicesCount = 0;

if (question != null) {
	choices = PollsChoiceLocalServiceUtil.getChoices(questionId);

	oldChoicesCount = choices.size();
}

int choicesCount = ParamUtil.getInteger(request, "choicesCount", choices.size());

if (choicesCount < 2) {
	choicesCount = 2;
}

int choiceName = ParamUtil.getInteger(request, "choiceName");

boolean deleteChoice = false;

if (choiceName > 0) {
	deleteChoice = true;
}

boolean showHeader = ParamUtil.getBoolean(request, "showHeader", true);

if (showHeader) {
	renderResponse.setTitle(question == null ? LanguageUtil.get(request, "new-poll") : question.getTitle(locale));
}

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);
%>

<liferay-portlet:actionURL name="/polls/edit_question" refererPlid="<%= themeDisplay.getRefererPlid() %>" var="editQuestionURL">
	<portlet:param name="mvcPath" value="/polls/edit_question.jsp" />
</liferay-portlet:actionURL>

<aui:form action="<%= editQuestionURL %>" cssClass="container-fluid-1280" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveQuestion();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="" />
	<aui:input name="redirect" type="hidden" value="<%= redirect %>" />
	<aui:input name="referringPortletResource" type="hidden" value="<%= referringPortletResource %>" />
	<aui:input name="questionId" type="hidden" value="<%= questionId %>" />
	<aui:input name="neverExpire" type="hidden" value="<%= neverExpire %>" />
	<aui:input name="choicesCount" type="hidden" value="<%= choicesCount %>" />
	<aui:input name="choiceName" type="hidden" value="" />

	<liferay-ui:error exception="<%= QuestionChoiceException.class %>" message="please-enter-valid-choices" />
	<liferay-ui:error exception="<%= QuestionDescriptionException.class %>" message="please-enter-a-valid-description" />
	<liferay-ui:error exception="<%= QuestionExpirationDateException.class %>" message="please-enter-a-valid-expiration-date" />
	<liferay-ui:error exception="<%= QuestionTitleException.class %>" message="please-enter-a-valid-title" />

	<aui:model-context bean="<%= question %>" model="<%= PollsQuestion.class %>" />

	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset>
			<aui:input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) || windowState.equals(LiferayWindowState.POP_UP) %>" name="title" />

			<aui:input label="polls-question" name="description" />

			<aui:input dateTogglerCheckboxLabel="never-expire" disabled="<%= neverExpire %>" name="expirationDate" />

			<aui:field-wrapper cssClass="form-group input-choices-wrapper" label="choices">

				<%
				for (int i = 1; i <= choicesCount; i++) {
					char c = (char)(96 + i);

					PollsChoice choice = null;

					String paramName = null;

					if (deleteChoice && (i >= choiceName)) {
						paramName = EditQuestionMVCActionCommand.CHOICE_DESCRIPTION_PREFIX + ((char)(96 + i + 1));
					}
					else {
						paramName = EditQuestionMVCActionCommand.CHOICE_DESCRIPTION_PREFIX + c;
					}

					if ((question != null) && ((i - 1) < choices.size())) {
						choice = (PollsChoice)choices.get(i - 1);
					}
				%>

					<div class="input-group-default">
						<aui:model-context bean="<%= choice %>" model="<%= PollsChoice.class %>" />

						<aui:input name="<%= EditQuestionMVCActionCommand.CHOICE_NAME_PREFIX + c %>" type="hidden" value="<%= c %>" />

						<aui:input fieldParam="<%= paramName %>" label="<%= c + StringPool.PERIOD %>" name="description" />

						<c:if test="<%= (choicesCount > 2) && (i == choicesCount) %>">
							<div class="input-group-addon">
								<aui:button cssClass="btn-delete" onClick='<%= renderResponse.getNamespace() + "deletePollChoice(" + i + ");" %>' value="delete" />
							</div>
						</c:if>
					</div>

				<%
				}
				%>

				<div class="button-holder">
					<aui:button cssClass="add-choice" onClick='<%= renderResponse.getNamespace() + "addPollChoice();" %>' value="add-choice" />
				</div>
			</aui:field-wrapper>
		</aui:fieldset>

		<c:if test="<%= question == null %>">
			<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="permissions">
				<liferay-ui:input-permissions
					modelName="<%= PollsQuestion.class.getName() %>"
				/>
			</aui:fieldset>
		</c:if>
	</aui:fieldset-group>

	<aui:button-row>
		<aui:button type="submit" />

		<aui:button href="<%= redirect %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />addPollChoice() {
		var form = AUI.$(<portlet:namespace />fm);

		var neverExpire = form.fm('fmexpirationDate').prop('checked');

		form.fm('choicesCount').val('<%= choicesCount + 1 %>');
		form.fm('neverExpire').val(neverExpire);

		<liferay-portlet:renderURL allowEmptyParam="<%= true %>" var="addPollChoiceURL">
			<liferay-portlet:param name="mvcRenderCommandName" value="/polls/edit_question" />
			<liferay-portlet:param name="<%= EditQuestionMVCActionCommand.CHOICE_DESCRIPTION_PREFIX + (char)(96 + choicesCount + 1) %>" value="" />
		</liferay-portlet:renderURL>

		submitForm(form, '<%= addPollChoiceURL %>');
	}

	function <portlet:namespace />deletePollChoice(choiceName) {
		var form = AUI.$(<portlet:namespace />fm);

		var neverExpire = form.fm('fmexpirationDate').prop('checked');

		form.fm('choicesCount').val('<%= choicesCount - 1 %>');
		form.fm('choiceName').val('<%= choiceName %>');
		form.fm('neverExpire').val(neverExpire);

		<liferay-portlet:renderURL allowEmptyParam="<%= true %>" var="deletePollChoiceURL">
			<liferay-portlet:param name="mvcRenderCommandName" value="/polls/edit_question" />
			<liferay-portlet:param name="<%= EditQuestionMVCActionCommand.CHOICE_DESCRIPTION_PREFIX + (char)(96 + choicesCount - 1) %>" value="" />
		</liferay-portlet:renderURL>

		submitForm(form, '<%= deletePollChoiceURL %>');
	}

	function <portlet:namespace />saveQuestion() {
		var form = AUI.$(<portlet:namespace />fm);

		var neverExpire = form.fm('fmexpirationDate').prop('checked');

		form.fm('<%= Constants.CMD %>').val('<%= (question == null) ? Constants.ADD : Constants.UPDATE %>');
		form.fm('neverExpire').val(neverExpire);

		submitForm(form);
	}
</aui:script>

<%
if (question != null) {
	PortalUtil.addPortletBreadcrumbEntry(request, question.getTitle(locale), null);
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "edit"), currentURL);
}
else {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "add-poll"), currentURL);
}
%>