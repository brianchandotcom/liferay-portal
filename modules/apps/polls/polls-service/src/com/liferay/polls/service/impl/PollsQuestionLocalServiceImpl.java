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

import com.liferay.polls.exception.QuestionChoiceException;
import com.liferay.polls.exception.QuestionDescriptionException;
import com.liferay.polls.exception.QuestionExpirationDateException;
import com.liferay.polls.exception.QuestionTitleException;
import com.liferay.polls.model.PollsChoice;
import com.liferay.polls.model.PollsQuestion;
import com.liferay.polls.service.base.PollsQuestionLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.SystemEventConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 */
public class PollsQuestionLocalServiceImpl
	extends PollsQuestionLocalServiceBaseImpl {

	@Override
	public PollsQuestion addQuestion(
			long userId, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, List<PollsChoice> choices,
			ServiceContext serviceContext)
		throws PortalException {

		// Question

		User user = userPersistence.findByPrimaryKey(userId);
		long groupId = serviceContext.getScopeGroupId();

		Date expirationDate = null;

		if (!neverExpire) {
			expirationDate = PortalUtil.getDate(
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, user.getTimeZone(),
				QuestionExpirationDateException.class);
		}

		validate(titleMap, descriptionMap, choices);

		long questionId = counterLocalService.increment();

		PollsQuestion question = pollsQuestionPersistence.create(questionId);

		question.setUuid(serviceContext.getUuid());
		question.setGroupId(groupId);
		question.setCompanyId(user.getCompanyId());
		question.setUserId(user.getUserId());
		question.setUserName(user.getFullName());
		question.setTitleMap(titleMap);
		question.setDescriptionMap(descriptionMap);
		question.setExpirationDate(expirationDate);

		pollsQuestionPersistence.update(question);

		// Resources

		if (serviceContext.isAddGroupPermissions() ||
			serviceContext.isAddGuestPermissions()) {

			addQuestionResources(
				question, serviceContext.isAddGroupPermissions(),
				serviceContext.isAddGuestPermissions());
		}
		else {
			addQuestionResources(
				question, serviceContext.getGroupPermissions(),
				serviceContext.getGuestPermissions());
		}

		// Choices

		if (choices != null) {
			for (PollsChoice choice : choices) {
				pollsChoiceLocalService.addChoice(
					userId, questionId, choice.getName(),
					choice.getDescription(), serviceContext);
			}
		}

		return question;
	}

	@Override
	public void addQuestionResources(
			long questionId, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException {

		PollsQuestion question = pollsQuestionPersistence.findByPrimaryKey(
			questionId);

		addQuestionResources(
			question, addGroupPermissions, addGuestPermissions);
	}

	@Override
	public void addQuestionResources(
			long questionId, String[] groupPermissions,
			String[] guestPermissions)
		throws PortalException {

		PollsQuestion question = pollsQuestionPersistence.findByPrimaryKey(
			questionId);

		addQuestionResources(question, groupPermissions, guestPermissions);
	}

	@Override
	public void addQuestionResources(
			PollsQuestion question, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException {

		resourceLocalService.addResources(
			question.getCompanyId(), question.getGroupId(),
			question.getUserId(), PollsQuestion.class.getName(),
			question.getQuestionId(), false, addGroupPermissions,
			addGuestPermissions);
	}

	@Override
	public void addQuestionResources(
			PollsQuestion question, String[] groupPermissions,
			String[] guestPermissions)
		throws PortalException {

		resourceLocalService.addModelResources(
			question.getCompanyId(), question.getGroupId(),
			question.getUserId(), PollsQuestion.class.getName(),
			question.getQuestionId(), groupPermissions, guestPermissions);
	}

	@Override
	public void deleteQuestion(long questionId) throws PortalException {
		PollsQuestion question = pollsQuestionPersistence.findByPrimaryKey(
			questionId);

		pollsQuestionLocalService.deleteQuestion(question);
	}

	@Override
	@SystemEvent(
		action = SystemEventConstants.ACTION_SKIP,
		type = SystemEventConstants.TYPE_DELETE
	)
	public void deleteQuestion(PollsQuestion question) throws PortalException {

		// Question

		pollsQuestionPersistence.remove(question);

		// Resources

		resourceLocalService.deleteResource(
			question.getCompanyId(), PollsQuestion.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, question.getQuestionId());

		// Choices

		pollsChoicePersistence.removeByQuestionId(question.getQuestionId());

		// Votes

		pollsVotePersistence.removeByQuestionId(question.getQuestionId());
	}

	@Override
	public void deleteQuestions(long groupId) throws PortalException {
		for (PollsQuestion question :
				pollsQuestionPersistence.findByGroupId(groupId)) {

			pollsQuestionLocalService.deleteQuestion(question);
		}
	}

	@Override
	public PollsQuestion getQuestion(long questionId) throws PortalException {
		return pollsQuestionPersistence.findByPrimaryKey(questionId);
	}

	@Override
	public List<PollsQuestion> getQuestions(long groupId) {
		return pollsQuestionPersistence.findByGroupId(groupId);
	}

	@Override
	public List<PollsQuestion> getQuestions(long groupId, int start, int end) {
		return pollsQuestionPersistence.findByGroupId(groupId, start, end);
	}

	@Override
	public int getQuestionsCount(long groupId) {
		return pollsQuestionPersistence.countByGroupId(groupId);
	}

	@Override
	public PollsQuestion updateQuestion(
			long userId, long questionId, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, List<PollsChoice> choices,
			ServiceContext serviceContext)
		throws PortalException {

		// Question

		User user = userPersistence.findByPrimaryKey(userId);

		Date expirationDate = null;

		if (!neverExpire) {
			expirationDate = PortalUtil.getDate(
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, user.getTimeZone(),
				QuestionExpirationDateException.class);
		}

		validate(titleMap, descriptionMap, choices);

		PollsQuestion question = pollsQuestionPersistence.findByPrimaryKey(
			questionId);

		question.setTitleMap(titleMap);
		question.setDescriptionMap(descriptionMap);
		question.setExpirationDate(expirationDate);

		pollsQuestionPersistence.update(question);

		// Choices

		if (choices == null) {
			return question;
		}

		int oldChoicesCount = pollsChoicePersistence.countByQuestionId(
			questionId);

		if (oldChoicesCount > choices.size()) {
			throw new QuestionChoiceException();
		}

		for (PollsChoice choice : choices) {
			String choiceName = choice.getName();
			String choiceDescription = choice.getDescription();

			choice = pollsChoicePersistence.fetchByQ_N(questionId, choiceName);

			if (choice == null) {
				pollsChoiceLocalService.addChoice(
					userId, questionId, choiceName, choiceDescription,
					new ServiceContext());
			}
			else {
				pollsChoiceLocalService.updateChoice(
					choice.getChoiceId(), questionId, choiceName,
					choiceDescription, new ServiceContext());
			}
		}

		return question;
	}

	protected void validate(
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			List<PollsChoice> choices)
		throws PortalException {

		Locale locale = LocaleUtil.getSiteDefault();

		String title = titleMap.get(locale);

		if (Validator.isNull(title)) {
			throw new QuestionTitleException();
		}

		String description = descriptionMap.get(locale);

		if (Validator.isNull(description)) {
			throw new QuestionDescriptionException();
		}

		if ((choices != null) && (choices.size() < 2)) {
			throw new QuestionChoiceException();
		}

		if (choices != null) {
			for (PollsChoice choice : choices) {
				String choiceDescription = choice.getDescription(locale);

				if (Validator.isNull(choiceDescription)) {
					throw new QuestionChoiceException();
				}
			}
		}
	}

}