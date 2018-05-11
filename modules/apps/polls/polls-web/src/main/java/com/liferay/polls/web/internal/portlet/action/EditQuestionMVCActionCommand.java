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

package com.liferay.polls.web.internal.portlet.action;

import com.liferay.petra.string.CharPool;
import com.liferay.polls.constants.PollsPortletKeys;
import com.liferay.polls.exception.DuplicateVoteException;
import com.liferay.polls.exception.NoSuchChoiceException;
import com.liferay.polls.exception.NoSuchQuestionException;
import com.liferay.polls.exception.QuestionChoiceException;
import com.liferay.polls.exception.QuestionDescriptionException;
import com.liferay.polls.exception.QuestionExpirationDateException;
import com.liferay.polls.exception.QuestionExpiredException;
import com.liferay.polls.exception.QuestionTitleException;
import com.liferay.polls.model.PollsChoice;
import com.liferay.polls.model.PollsQuestion;
import com.liferay.polls.service.PollsQuestionService;
import com.liferay.polls.service.persistence.PollsChoiceUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.WindowState;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Peter Fellwock
 */
@Component(
	property = {
		"javax.portlet.name=" + PollsPortletKeys.POLLS,
		"javax.portlet.name=" + PollsPortletKeys.POLLS_DISPLAY,
		"mvc.command.name=/polls/edit_question"
	},
	service = MVCActionCommand.class
)
public class EditQuestionMVCActionCommand extends BaseMVCActionCommand {

	public static final String CHOICE_DESCRIPTION_PREFIX = "choiceDescription";

	public static final String CHOICE_NAME_PREFIX = "choiceName";

	protected void addAndStoreSelection(
			PortletRequest portletRequest, PollsQuestion question)
		throws Exception {

		String referringPortletResource = ParamUtil.getString(
			portletRequest, "referringPortletResource");

		if (Validator.isNull(referringPortletResource)) {
			return;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.getStrictPortletSetup(
				themeDisplay.getLayout(), themeDisplay.getPpid());

		portletPreferences.setValue(
			"questionId", String.valueOf(question.getQuestionId()));

		portletPreferences.store();

		SessionMessages.add(
			portletRequest,
			portal.getPortletId(portletRequest) +
				SessionMessages.KEY_SUFFIX_REFRESH_PORTLET,
			referringPortletResource);
	}

	protected void deleteQuestion(ActionRequest actionRequest)
		throws Exception {

		long questionId = ParamUtil.getLong(actionRequest, "questionId");

		_pollsQuestionService.deleteQuestion(questionId);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		PortletConfig portletConfig = (PortletConfig)actionRequest.getAttribute(
			JavaConstants.JAVAX_PORTLET_CONFIG);

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (Validator.isNull(cmd)) {
				return;
			}
			else if (cmd.equals(Constants.ADD) ||
					 cmd.equals(Constants.UPDATE) ||
					 cmd.equals(Constants.VOTE)) {

				updateQuestion(portletConfig, actionRequest, actionResponse);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteQuestion(actionRequest);
			}

			WindowState windowState = actionRequest.getWindowState();

			if (windowState.equals(LiferayWindowState.POP_UP)) {
				String redirect = portal.escapeRedirect(
					ParamUtil.getString(actionRequest, "redirect"));

				if (Validator.isNotNull(redirect)) {
					actionResponse.sendRedirect(redirect);
				}
			}
			else {
				sendRedirect(actionRequest, actionResponse);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchQuestionException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter(
					"mvcPath", "/polls/error.jsp");
			}
			else if (e instanceof DuplicateVoteException ||
					 e instanceof NoSuchChoiceException ||
					 e instanceof QuestionChoiceException ||
					 e instanceof QuestionDescriptionException ||
					 e instanceof QuestionExpirationDateException ||
					 e instanceof QuestionTitleException) {

				SessionErrors.add(actionRequest, e.getClass());

				hideDefaultErrorMessage(actionRequest);
			}
			else if (e instanceof QuestionExpiredException) {
			}
			else {
				throw e;
			}
		}
	}

	@Reference(unbind = "-")
	protected void setPollsQuestionService(
		PollsQuestionService pollsQuestionService) {

		_pollsQuestionService = pollsQuestionService;
	}

	protected void updateQuestion(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		long questionId = ParamUtil.getLong(actionRequest, "questionId");

		Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "title");
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "description");

		int expirationDateMonth = ParamUtil.getInteger(
			actionRequest, "expirationDateMonth");
		int expirationDateDay = ParamUtil.getInteger(
			actionRequest, "expirationDateDay");
		int expirationDateYear = ParamUtil.getInteger(
			actionRequest, "expirationDateYear");
		int expirationDateHour = ParamUtil.getInteger(
			actionRequest, "expirationDateHour");
		int expirationDateMinute = ParamUtil.getInteger(
			actionRequest, "expirationDateMinute");
		int expirationDateAmPm = ParamUtil.getInteger(
			actionRequest, "expirationDateAmPm");
		boolean neverExpire = ParamUtil.getBoolean(
			actionRequest, "neverExpire");

		if (expirationDateAmPm == Calendar.PM) {
			expirationDateHour += 12;
		}

		List<PollsChoice> choices = new ArrayList<>();

		Set<String> readParameters = new HashSet<>();

		Enumeration<String> enu = actionRequest.getParameterNames();

		while (enu.hasMoreElements()) {
			String param = enu.nextElement();

			if (param.startsWith(CHOICE_DESCRIPTION_PREFIX)) {
				int endIndex = param.indexOf(CharPool.UNDERLINE);

				if (endIndex < 0) {
					endIndex = param.length();
				}

				String id = param.substring(
					CHOICE_DESCRIPTION_PREFIX.length(), endIndex);

				if (readParameters.contains(id)) {
					continue;
				}

				String choiceName = ParamUtil.getString(
					actionRequest, CHOICE_NAME_PREFIX + id);

				Map<Locale, String> localeChoiceDescriptionMap =
					LocalizationUtil.getLocalizationMap(
						actionRequest, CHOICE_DESCRIPTION_PREFIX + id);

				PollsChoice choice = PollsChoiceUtil.create(0);

				choice.setName(choiceName);
				choice.setDescriptionMap(localeChoiceDescriptionMap);

				choices.add(choice);

				readParameters.add(id);
			}
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			PollsQuestion.class.getName(), actionRequest);

		if (questionId <= 0) {

			// Add question

			PollsQuestion question = _pollsQuestionService.addQuestion(
				titleMap, descriptionMap, expirationDateMonth,
				expirationDateDay, expirationDateYear, expirationDateHour,
				expirationDateMinute, neverExpire, choices, serviceContext);

			// Poll display

			addAndStoreSelection(actionRequest, question);
		}
		else {

			// Update question

			_pollsQuestionService.updateQuestion(
				questionId, titleMap, descriptionMap, expirationDateMonth,
				expirationDateDay, expirationDateYear, expirationDateHour,
				expirationDateMinute, neverExpire, choices, serviceContext);
		}
	}

	@Reference
	protected Portal portal;

	private PollsQuestionService _pollsQuestionService;

}