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

import com.liferay.polls.model.PollsQuestion;
import com.liferay.polls.service.base.PollsQuestionLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.model.GroupedModel;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.SystemEventConstants;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 */
public class PollsQuestionLocalServiceImpl
	extends PollsQuestionLocalServiceBaseImpl {

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

		GroupedModel groupedModel = (GroupedModel)question;

		resourceLocalService.addResources(
			groupedModel.getCompanyId(), groupedModel.getGroupId(),
			groupedModel.getUserId(), PollsQuestion.class.getName(),
			question.getQuestionId(), false, addGroupPermissions,
			addGuestPermissions);
	}

	@Override
	public void addQuestionResources(
			PollsQuestion question, String[] groupPermissions,
			String[] guestPermissions)
		throws PortalException {

		GroupedModel groupedModel = (GroupedModel)question;

		resourceLocalService.addModelResources(
			groupedModel.getCompanyId(), groupedModel.getGroupId(),
			groupedModel.getUserId(), PollsQuestion.class.getName(),
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
		type = SystemEventConstants.TYPE_DELETE)
	public void deleteQuestion(PollsQuestion question) throws PortalException {
		GroupedModel pollsQuestion = (GroupedModel)question;

		// Question

		pollsQuestionPersistence.remove(question);

		// Resources

		resourceLocalService.deleteResource(
			pollsQuestion.getCompanyId(), PollsQuestion.class.getName(),
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

}