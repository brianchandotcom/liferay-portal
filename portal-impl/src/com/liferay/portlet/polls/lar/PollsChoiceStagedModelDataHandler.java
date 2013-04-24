/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.polls.lar;

import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.polls.model.PollsChoice;
import com.liferay.portlet.polls.model.PollsQuestion;
import com.liferay.portlet.polls.service.PollsChoiceLocalServiceUtil;
import com.liferay.portlet.polls.service.PollsQuestionLocalServiceUtil;
import com.liferay.portlet.polls.service.persistence.PollsChoiceFinderUtil;

import java.util.Map;

/**
 * @author Shinn Lok
 */
public class PollsChoiceStagedModelDataHandler
	extends BaseStagedModelDataHandler<PollsChoice> {

	public static final String[] CLASS_NAMES = {PollsChoice.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, PollsChoice choice)
		throws Exception {

		PollsQuestion question = PollsQuestionLocalServiceUtil.getQuestion(
			choice.getQuestionId());

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, question);

		Element choiceElement = portletDataContext.getExportDataElement(choice);

		portletDataContext.addClassedModel(
			choiceElement, ExportImportPathUtil.getModelPath(choice), choice,
			PollsPortletDataHandler.NAMESPACE);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, PollsChoice choice)
		throws Exception {

		long userId = portletDataContext.getUserId(choice.getUserUuid());

		String questionPath = ExportImportPathUtil.getModelPath(
			portletDataContext, PollsQuestion.class.getName(),
			choice.getQuestionId());

		PollsQuestion question =
			(PollsQuestion)portletDataContext.getZipEntryAsObject(questionPath);

		StagedModelDataHandlerUtil.importStagedModel(
			portletDataContext, question);

		Map<Long, Long> questionIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				PollsQuestion.class);

		long questionId = MapUtil.getLong(
			questionIds, choice.getQuestionId(), choice.getQuestionId());

		PollsChoice importedChoice = null;

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			choice, PollsPortletDataHandler.NAMESPACE);

		if (portletDataContext.isDataStrategyMirror()) {
			PollsChoice existingChoice = PollsChoiceFinderUtil.fetchByUUID_G(
				choice.getUuid(), portletDataContext.getScopeGroupId());

			if (existingChoice == null) {
				serviceContext.setUuid(choice.getUuid());

				importedChoice = PollsChoiceLocalServiceUtil.addChoice(
					userId, questionId, choice.getName(),
					choice.getDescription(), serviceContext);
			}
			else {
				importedChoice = PollsChoiceLocalServiceUtil.updateChoice(
					existingChoice.getChoiceId(), questionId, choice.getName(),
					choice.getDescription(), serviceContext);
			}
		}
		else {
			importedChoice = PollsChoiceLocalServiceUtil.addChoice(
				userId, questionId, choice.getName(), choice.getDescription(),
				serviceContext);
		}

		portletDataContext.importClassedModel(
			choice, importedChoice, PollsPortletDataHandler.NAMESPACE);
	}

}