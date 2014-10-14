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

package com.liferay.polls.service.impl;

import com.liferay.polls.exception.QuestionExpirationDateException;
import com.liferay.polls.model.PollsChoice;
import com.liferay.polls.model.PollsChoiceModel;
import com.liferay.polls.model.PollsQuestion;
import com.liferay.polls.service.base.PollsQuestionServiceBaseImpl;
import com.liferay.polls.service.permission.PollsPermission;
import com.liferay.polls.service.permission.PollsQuestionPermission;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PredicateFilter;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 */
public class PollsQuestionServiceImpl extends PollsQuestionServiceBaseImpl {

	@Override
	public PollsQuestion addQuestion(
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire,
			List<Map<String, Object>> choices, ServiceContext serviceContext)
		throws PortalException {

		PollsPermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			ActionKeys.ADD_QUESTION);

		PollsQuestion pollsQuestion =
			pollsQuestionLocalService.createPollsQuestion();

		pollsQuestion.setTitleMap(titleMap);
		pollsQuestion.setDescriptionMap(descriptionMap);

		if (!neverExpire) {
			Date date = PortalUtil.getDate(
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute,
				QuestionExpirationDateException.class);

			pollsQuestion.setExpirationDate(date);
		}

		for (Map<String, Object> choiceMap : choices) {
			PollsChoice choice = pollsQuestion.createChoice();

			choice.setModelAttributes(choiceMap);

			pollsQuestion.addChoice(choice);
		}

		pollsQuestion.persist();

		return pollsQuestion;
	}

	@Override
	public void deleteQuestion(long questionId) throws PortalException {
		_pollsQuestionPermission.check(
			getPermissionChecker(), questionId, ActionKeys.DELETE);

		pollsQuestionLocalService.deleteQuestion(questionId);
	}

	@Override
	public PollsQuestion getQuestion(long questionId) throws PortalException {
		_pollsQuestionPermission.check(
			getPermissionChecker(), questionId, ActionKeys.VIEW);

		return pollsQuestionLocalService.getQuestion(questionId);
	}

	@Override
	public PollsQuestion updateQuestion(
			long questionId, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, List<Map<String, Object>> choices,
			ServiceContext serviceContext)
		throws PortalException {

		_pollsQuestionPermission.check(
			getPermissionChecker(), questionId, ActionKeys.UPDATE);

		PollsQuestion pollsQuestion =
			pollsQuestionLocalService.getPollsQuestion(questionId);

		pollsQuestion.setTitleMap(titleMap);
		pollsQuestion.setDescriptionMap(descriptionMap);

		Date date;

		if (neverExpire) {
			date = null;
		}
		else {
			date = PortalUtil.getDate(
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute,
				QuestionExpirationDateException.class);
		}

		pollsQuestion.setExpirationDate(date);

		List<PollsChoice> currentChoices = pollsQuestion.getChoices();

		List<String> choiceNames = ListUtil.toList(
			currentChoices, PollsChoice.CHOICE_NAME_ACCESSOR);

		for (Map<String, Object> choiceMap : choices) {
			int index = choiceNames.indexOf(choiceMap.get("name"));

			PollsChoice choice;

			if (index == -1) {
				choice = pollsQuestion.createChoice();

				pollsQuestion.addChoice(choice);
			}
			else {
				choice = currentChoices.get(index);
			}

			choice.setModelAttributes(choiceMap);

			choice.persist();
		}

		pollsQuestion.persist();

		return pollsQuestion;
	}
	
	@BeanReference(type = PollsQuestionPermission.class)
	private PollsQuestionPermission _pollsQuestionPermission;

}