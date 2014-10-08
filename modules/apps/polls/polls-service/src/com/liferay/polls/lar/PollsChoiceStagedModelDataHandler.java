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

import com.liferay.polls.model.PollsChoice;
import com.liferay.polls.model.PollsQuestion;
import com.liferay.polls.model.impl.PollsChoiceImpl;
import com.liferay.polls.model.impl.PollsQuestionImpl;
import com.liferay.polls.service.PollsChoiceLocalServiceUtil;
import com.liferay.polls.service.PollsQuestionLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.service.ServiceContext;

import java.util.List;
import java.util.Map;

/**
 * @author Shinn Lok
 * @author Mate Thurzo
 */
public class PollsChoiceStagedModelDataHandler
	extends BaseStagedModelDataHandler<PollsChoiceImpl> {

	public static final String[] CLASS_NAMES = {PollsChoice.class.getName()};

	@Override
	public void deleteStagedModel(
		String uuid, long groupId, String className, String extraData) {

		PollsChoice pollsChoice = fetchStagedModelByUuidAndGroupId(
			uuid, groupId);

		if (pollsChoice != null) {
			PollsChoiceLocalServiceUtil.deletePollsChoice(pollsChoice);
		}
	}

	@Override
	public PollsChoiceImpl fetchStagedModelByUuidAndCompanyId(
		String uuid, long companyId) {

		List<PollsChoice> choices =
			PollsChoiceLocalServiceUtil.getPollsChoicesByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<PollsChoiceImpl>());

		if (ListUtil.isEmpty(choices)) {
			return null;
		}

		return (PollsChoiceImpl)choices.get(0);
	}

	@Override
	public PollsChoiceImpl fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return (PollsChoiceImpl)
			PollsChoiceLocalServiceUtil.fetchPollsChoiceByUuidAndGroupId(
				uuid, groupId);
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(PollsChoiceImpl choice) {
		return choice.getName();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, PollsChoiceImpl choice)
		throws Exception {

		PollsQuestion question = PollsQuestionLocalServiceUtil.getQuestion(
			choice.getQuestionId());

		PollsChoiceImpl choiceImpl = (PollsChoiceImpl) choice;
		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, choiceImpl,
			(PollsQuestionImpl)question,
			PortletDataContext.REFERENCE_TYPE_STRONG);

		Element choiceElement = portletDataContext.getExportDataElement(choice);

		portletDataContext.addClassedModel(
			choiceElement, ExportImportPathUtil.getModelPath(choiceImpl),
			choice);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long choiceId)
		throws Exception {

		PollsChoice existingChoice = fetchMissingReference(uuid, groupId);

		Map<Long, Long> choiceIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				PollsChoice.class);

		choiceIds.put(choiceId, existingChoice.getChoiceId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, PollsChoiceImpl choice)
		throws Exception {

		Map<Long, Long> questionIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				PollsQuestion.class);

		long questionId = MapUtil.getLong(
			questionIds, choice.getQuestionId(), choice.getQuestionId());

		PollsQuestion pollsQuestion =
			PollsQuestionLocalServiceUtil.getPollsQuestion(questionId);

		PollsChoiceImpl importedChoice = null;

		if (portletDataContext.isDataStrategyMirror()) {
			importedChoice = fetchStagedModelByUuidAndGroupId(
				choice.getUuid(), portletDataContext.getScopeGroupId());
		}
		if (importedChoice == null) {
			importedChoice = (PollsChoiceImpl) pollsQuestion.createChoice();

			importedChoice.setCompanyId(
				portletDataContext.getCompanyId());
			importedChoice.setGroupId(
				portletDataContext.getScopeGroupId());
		}

		importedChoice.setModelAttributes(choice.getModelAttributes());

		importedChoice.persist();

		portletDataContext.importClassedModel(choice, importedChoice);
	}

}