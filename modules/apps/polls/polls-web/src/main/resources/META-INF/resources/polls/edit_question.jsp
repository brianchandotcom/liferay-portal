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

String choicesAction = ParamUtil.getString(request, "choicesAction", StringPool.BLANK);

int choiceName = ParamUtil.getInteger(request, "choiceName");

boolean showHeader = ParamUtil.getBoolean(request, "showHeader", true);

List<PollsChoice> choices = new ArrayList<>();

if (question != null) {
	choices = PollsChoiceLocalServiceUtil.getChoices(questionId);

	if (question.getExpirationDate() != null) {
		neverExpire = false;
	}
}

int choicesCount = ParamUtil.getInteger(request, "choicesCount", choices.size());

if (choicesCount < 2) {
	choicesCount = 2;
}

boolean addChoice = false;
boolean deleteChoice = false;

if (StringUtil.equals(choicesAction, "addChoice")) {
	addChoice = true;
}
else if (StringUtil.equals(choicesAction, "deleteChoice")) {
	deleteChoice = true;
}

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
	<aui:input name="choicesAction" type="hidden" value="" />
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
						paramName = EditQuestionMVCActionCommand.CHOICE_DESCRIPTION_PREFIX + ((char)(c + 1));
					}
					else {
						paramName = EditQuestionMVCActionCommand.CHOICE_DESCRIPTION_PREFIX + c;
					}

					Map<Locale, String> localeChoiceDescriptionMap = LocalizationUtil.getLocalizationMap(renderRequest, paramName);

					String value = GetterUtil.getString(LocalizationUtil.updateLocalization(localeChoiceDescriptionMap, "", "Description", LocaleUtil.toLanguageId(locale)));

					if ((question != null) && !addChoice && !deleteChoice) {
						choice = (PollsChoice)choices.get(i - 1);

						value = choice.getDescription();
					}
				%>

					<div class="form-group poll-choice-group">
						<aui:input name="<%= EditQuestionMVCActionCommand.CHOICE_NAME_PREFIX + c %>" type="hidden" value="<%= c %>" />

						<div class="poll-choice-input">
							<aui:input ignoreRequestValue="<%= true %>" label="<%= c + StringPool.PERIOD %>" localized="<%= true %>" name="<%= EditQuestionMVCActionCommand.CHOICE_DESCRIPTION_PREFIX + c %>" type="text" value="<%= value %>" />
						</div>

						<c:if test="<%= choicesCount > 2 %>">
							<div class="delete-poll-choice">
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
		var form = document.getElementById('<portlet:namespace />fm');

		if (form) {
			var choicesAction = form.querySelector(
				'#<portlet:namespace />choicesAction'
			);

			if (choicesAction) {
				choicesAction.setAttribute('value', 'addChoice');
			}

			var choicesCount = form.querySelector(
				'#<portlet:namespace />choicesCount'
			);

			if (choicesCount) {
				choicesCount.setAttribute('value', '<%= choicesCount + 1 %>');
			}

			var expirationDate = form.querySelector(
				'#<portlet:namespace />fmexpirationDate'
			);
			var neverExpire = form.querySelector(
				'#<portlet:namespace />neverExpire'
			);

			if (expirationDate && neverExpire) {
				neverExpire.setAttribute('value', expirationDate.checked);
			}

			<liferay-portlet:renderURL allowEmptyParam="<%= true %>" var="addPollChoiceURL">
				<liferay-portlet:param name="mvcRenderCommandName" value="/polls/edit_question" />
				<liferay-portlet:param name="<%= EditQuestionMVCActionCommand.CHOICE_DESCRIPTION_PREFIX + (char)(96 + choicesCount + 1) %>" value="" />
			</liferay-portlet:renderURL>

			submitForm(form, '<%= addPollChoiceURL %>');
		}
	}

	function <portlet:namespace />deletePollChoice(pollName) {
		var form = document.getElementById('<portlet:namespace />fm');

		if (form) {
			var choicesAction = form.querySelector(
				'#<portlet:namespace />choicesAction'
			);

			if (choicesAction) {
				choicesAction.setAttribute('value', 'deleteChoice');
			}

			var choicesCount = form.querySelector(
				'#<portlet:namespace />choicesCount'
			);

			if (choicesCount) {
				choicesCount.setAttribute('value', '<%= choicesCount - 1 %>');
			}

			var choiceName = form.querySelector('#<portlet:namespace />choiceName');

			if (choiceName) {
				choiceName.setAttribute('value', pollName);
			}

			var expirationDate = form.querySelector(
				'#<portlet:namespace />fmexpirationDate'
			);
			var neverExpire = form.querySelector(
				'#<portlet:namespace />neverExpire'
			);

			if (expirationDate && neverExpire) {
				neverExpire.setAttribute('value', expirationDate.checked);
			}

			<liferay-portlet:renderURL allowEmptyParam="<%= true %>" var="deletePollChoiceURL">
				<liferay-portlet:param name="mvcRenderCommandName" value="/polls/edit_question" />
				<liferay-portlet:param name="<%= EditQuestionMVCActionCommand.CHOICE_DESCRIPTION_PREFIX + (char)(96 + choicesCount - 1) %>" value="" />
			</liferay-portlet:renderURL>

			submitForm(form, '<%= deletePollChoiceURL %>');
		}
	}

	function <portlet:namespace />saveQuestion() {
		var form = document.getElementById('<portlet:namespace />fm');

		if (form) {
			var cmd = form.querySelector(
				'#<portlet:namespace /><%= Constants.CMD %>'
			);

			if (cmd) {
				cmd.setAttribute(
					'value',
					'<%= (question == null) ? Constants.ADD : Constants.UPDATE %>'
				);
			}

			var expirationDate = form.querySelector(
				'#<portlet:namespace />fmexpirationDate'
			);
			var neverExpire = form.querySelector(
				'#<portlet:namespace />neverExpire'
			);

			if (expirationDate && neverExpire) {
				neverExpire.setAttribute('value', expirationDate.checked);
			}

			submitForm(form);
		}
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