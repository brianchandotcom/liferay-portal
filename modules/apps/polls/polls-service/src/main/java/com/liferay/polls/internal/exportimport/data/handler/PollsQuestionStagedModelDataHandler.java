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

package com.liferay.polls.internal.exportimport.data.handler;

import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.polls.model.PollsChoice;
import com.liferay.polls.model.PollsQuestion;
import com.liferay.polls.model.PollsVote;
import com.liferay.polls.service.PollsQuestionLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.xml.Element;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shinn Lok
 * @author Máté Thurzó
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class PollsQuestionStagedModelDataHandler
	extends BaseStagedModelDataHandler<PollsQuestion> {

	public static final String[] CLASS_NAMES = {PollsQuestion.class.getName()};

	@Override
	public void deleteStagedModel(PollsQuestion question)
		throws PortalException {

		_pollsQuestionLocalService.deleteQuestion(question);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		PollsQuestion question = fetchStagedModelByUuidAndGroupId(
			uuid, groupId);

		if (question != null) {
			deleteStagedModel(question);
		}
	}

	@Override
	public PollsQuestion fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _pollsQuestionLocalService.fetchPollsQuestionByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<PollsQuestion> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _pollsQuestionLocalService.getPollsQuestionsByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<PollsQuestion>());
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(PollsQuestion question) {
		return question.getTitle();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, PollsQuestion question)
		throws Exception {

		Element questionElement = portletDataContext.getExportDataElement(
			question);

		portletDataContext.addClassedModel(
			questionElement, ExportImportPathUtil.getModelPath(question),
			question);

		for (PollsChoice choice : question.getChoices()) {
			Element choiceElement = portletDataContext.getExportDataElement(
				choice);

			portletDataContext.addClassedModel(
				choiceElement, ExportImportPathUtil.getModelPath(choice),
				choice);
		}

		if (portletDataContext.getBooleanParameter(
				PollsPortletDataHandler.NAMESPACE, "votes")) {

			for (PollsVote vote : question.getVotes()) {
				Element voteElement = portletDataContext.getExportDataElement(
					vote);

				portletDataContext.addClassedModel(
					voteElement, ExportImportPathUtil.getModelPath(vote), vote);
			}
		}
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long questionId)
		throws Exception {

		PollsQuestion existingQuestion = fetchMissingReference(uuid, groupId);

		if (existingQuestion == null) {
			return;
		}

		Map<Long, Long> questionIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				PollsQuestion.class);

		questionIds.put(questionId, existingQuestion.getQuestionId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, PollsQuestion question)
		throws Exception {

		long userId = portletDataContext.getUserId(question.getUserUuid());

		int expirationMonth = 0;
		int expirationDay = 0;
		int expirationYear = 0;
		int expirationHour = 0;
		int expirationMinute = 0;
		boolean neverExpire = true;

		Date expirationDate = question.getExpirationDate();

		if (expirationDate != null) {
			Calendar expirationCal = CalendarFactoryUtil.getCalendar();

			expirationCal.setTime(expirationDate);

			expirationMonth = expirationCal.get(Calendar.MONTH);
			expirationDay = expirationCal.get(Calendar.DATE);
			expirationYear = expirationCal.get(Calendar.YEAR);
			expirationHour = expirationCal.get(Calendar.HOUR);
			expirationMinute = expirationCal.get(Calendar.MINUTE);

			neverExpire = false;

			if (expirationCal.get(Calendar.AM_PM) == Calendar.PM) {
				expirationHour += 12;
			}
		}

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			question);

		PollsQuestion importedQuestion = null;

		if (portletDataContext.isDataStrategyMirror()) {
			PollsQuestion existingQuestion = fetchStagedModelByUuidAndGroupId(
				question.getUuid(), portletDataContext.getScopeGroupId());

			if (existingQuestion == null) {
				serviceContext.setUuid(question.getUuid());

				importedQuestion = _pollsQuestionLocalService.addQuestion(
					userId, question.getTitleMap(),
					question.getDescriptionMap(), expirationMonth,
					expirationDay, expirationYear, expirationHour,
					expirationMinute, neverExpire, null, serviceContext);
			}
			else {
				importedQuestion = _pollsQuestionLocalService.updateQuestion(
					userId, existingQuestion.getQuestionId(),
					question.getTitleMap(), question.getDescriptionMap(),
					expirationMonth, expirationDay, expirationYear,
					expirationHour, expirationMinute, neverExpire, null,
					serviceContext);
			}
		}
		else {
			importedQuestion = _pollsQuestionLocalService.addQuestion(
				userId, question.getTitleMap(), question.getDescriptionMap(),
				expirationMonth, expirationDay, expirationYear, expirationHour,
				expirationMinute, neverExpire, null, serviceContext);
		}

		portletDataContext.importClassedModel(question, importedQuestion);
	}

	@Reference(unbind = "-")
	protected void setPollsQuestionLocalService(
		PollsQuestionLocalService pollsQuestionLocalService) {

		_pollsQuestionLocalService = pollsQuestionLocalService;
	}

	private PollsQuestionLocalService _pollsQuestionLocalService;

}