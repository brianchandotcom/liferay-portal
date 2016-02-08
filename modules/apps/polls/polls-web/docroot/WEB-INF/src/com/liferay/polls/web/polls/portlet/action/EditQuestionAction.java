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

package com.liferay.polls.web.polls.portlet.action;

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
import com.liferay.polls.service.PollsQuestionServiceUtil;
import com.liferay.polls.service.persistence.PollsChoiceUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.struts.PortletAction;

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
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 */
public class EditQuestionAction extends PortletAction {

	public static final String CHOICE_DESCRIPTION_PREFIX = "choiceDescription";

	public static final String CHOICE_NAME_PREFIX = "choiceName";

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

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
				String redirect = PortalUtil.escapeRedirect(
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

				setForward(actionRequest, "portlet.polls.error");
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

	@Override
	public ActionForward render(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		try {
			ActionUtil.getQuestion(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchQuestionException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return actionMapping.findForward("portlet.polls.error");
			}
			else {
				throw e;
			}
		}

		return actionMapping.findForward(
			getForward(renderRequest, "portlet.polls.edit_question"));
	}

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

		Layout layout = LayoutLocalServiceUtil.getLayout(
			themeDisplay.getRefererPlid());

		PortletPreferences portletPreferences = getStrictPortletSetup(
			layout, referringPortletResource);

		portletPreferences.setValue(
			"questionId", String.valueOf(question.getQuestionId()));

		portletPreferences.store();

		SessionMessages.add(
			portletRequest,
			PortalUtil.getPortletId(portletRequest) +
				SessionMessages.KEY_SUFFIX_REFRESH_PORTLET,
			referringPortletResource);
	}

	protected void deleteQuestion(ActionRequest actionRequest)
		throws Exception {

		long questionId = ParamUtil.getLong(actionRequest, "questionId");

		PollsQuestionServiceUtil.deleteQuestion(questionId);
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
				try {
					String id = param.substring(
						CHOICE_DESCRIPTION_PREFIX.length(),
						param.indexOf(CharPool.UNDERLINE));

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
				catch (Exception e) {
				}
			}
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			PollsQuestion.class.getName(), actionRequest);

		if (questionId <= 0) {

			// Add question

			PollsQuestion question = PollsQuestionServiceUtil.addQuestion(
				titleMap, descriptionMap, expirationDateMonth,
				expirationDateDay, expirationDateYear, expirationDateHour,
				expirationDateMinute, neverExpire, choices, serviceContext);

			// Poll display

			addAndStoreSelection(actionRequest, question);
		}
		else {

			// Update question

			PollsQuestionServiceUtil.updateQuestion(
				questionId, titleMap, descriptionMap, expirationDateMonth,
				expirationDateDay, expirationDateYear, expirationDateHour,
				expirationDateMinute, neverExpire, choices, serviceContext);
		}
	}

}