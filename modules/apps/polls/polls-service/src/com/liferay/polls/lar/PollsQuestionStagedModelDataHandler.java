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

package com.liferay.polls.lar;

import com.liferay.polls.model.PollsQuestion;
import com.liferay.polls.repository.PollsQuestionRepository;
import com.liferay.polls.service.PollsQuestionLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;
import java.util.Map;

/**
 * @author Shinn Lok
 * @author Mate Thurzo
 */
public class PollsQuestionStagedModelDataHandler
	extends BaseStagedModelDataHandler<PollsQuestion> {

	public static final String[] CLASS_NAMES = {PollsQuestion.class.getName()};

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		PollsQuestion question = fetchStagedModelByUuidAndGroupId(
			uuid, groupId);

		if (question != null) {
			PollsQuestionLocalServiceUtil.deleteQuestion(question);
		}
	}

	@Override
	public PollsQuestion fetchStagedModelByUuidAndCompanyId(
		String uuid, long companyId) {

		List<PollsQuestion> questions =
			PollsQuestionLocalServiceUtil.getPollsQuestionsByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<PollsQuestion>());

		if (ListUtil.isEmpty(questions)) {
			return null;
		}

		return questions.get(0);
	}

	@Override
	public PollsQuestion fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return PollsQuestionLocalServiceUtil.
					fetchPollsQuestionByUuidAndGroupId(uuid, groupId);
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
			questionElement,
			ExportImportPathUtil.getModelPath(question),
			question);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long questionId)
		throws Exception {

		PollsQuestion existingQuestion = fetchMissingReference(uuid, groupId);

		Map<Long, Long> questionIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				PollsQuestion.class);

		questionIds.put(questionId, existingQuestion.getQuestionId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, PollsQuestion question)
		throws Exception {

		PollsQuestion importedQuestion = null;

		long scopeGroupId = portletDataContext.getScopeGroupId();

		if (portletDataContext.isDataStrategyMirror()) {
			importedQuestion =
				fetchStagedModelByUuidAndGroupId(
					question.getUuid(), scopeGroupId);
		}

		if (importedQuestion == null) {
			importedQuestion = new PollsQuestion();

			//TODO: This should be executed in a context
			importedQuestion.setCompanyId(
				portletDataContext.getCompanyId());
			importedQuestion.setGroupId(
				scopeGroupId);
		}

		importedQuestion.setModelAttributes(
			question.getModelAttributes());

		_pollsQuestionRepository.persist(importedQuestion);

		portletDataContext.importClassedModel(question, importedQuestion);
	}

	public void setPollsQuestionRepository(PollsQuestionRepository pollsQuestionRepository) {
		_pollsQuestionRepository = pollsQuestionRepository;
	}

	protected PollsQuestionRepository _pollsQuestionRepository;

}