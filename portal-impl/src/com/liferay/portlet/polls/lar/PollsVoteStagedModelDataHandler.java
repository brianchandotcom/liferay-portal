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
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.lar.StagedModelPathUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.polls.DuplicateVoteException;
import com.liferay.portlet.polls.model.PollsChoice;
import com.liferay.portlet.polls.model.PollsQuestion;
import com.liferay.portlet.polls.model.PollsVote;
import com.liferay.portlet.polls.service.PollsVoteLocalServiceUtil;
import com.liferay.portlet.polls.service.persistence.PollsVoteUtil;

import java.util.Map;

/**
 * @author Shinn Lok
 * @author Mate Thurzo
 */
public class PollsVoteStagedModelDataHandler
	extends BaseStagedModelDataHandler<PollsVote> {

	@Override
	public String getClassName() {
		return PollsVote.class.getName();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, Element[] elements,
			PollsVote vote)
		throws Exception {

		Element questionsElement = elements[0];
		Element choicesElement = elements[1];

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext,
			new Element[] {questionsElement, choicesElement}, vote.getChoice());

		Element votesElement = elements[2];

		Element voteElement = votesElement.addElement("vote");

		portletDataContext.addClassedModel(
			voteElement, StagedModelPathUtil.getPath(vote), vote,
			PollsPortletDataHandler.NAMESPACE);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, Element element,
			PollsVote vote)
		throws Exception {

		String choicePath = StagedModelPathUtil.getPath(
			portletDataContext, PollsChoice.class.getName(),
			vote.getChoiceId());

		PollsChoice choice =
			(PollsChoice)portletDataContext.getZipEntryAsObject(choicePath);

		StagedModelDataHandlerUtil.importStagedModel(
			portletDataContext, element, choice);

		Map<Long, Long> questionIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				PollsQuestion.class);

		long questionId = MapUtil.getLong(
			questionIds, vote.getQuestionId(), vote.getQuestionId());

		Map<Long, Long> choiceIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				PollsChoice.class);

		long choiceId = MapUtil.getLong(
			choiceIds, vote.getChoiceId(), vote.getChoiceId());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			vote, PollsPortletDataHandler.NAMESPACE);

		serviceContext.setCreateDate(vote.getVoteDate());

		if (portletDataContext.isDataStrategyMirror()) {
			PollsVote existingVote = PollsVoteUtil.fetchByUUID_G(
				vote.getUuid(), portletDataContext.getScopeGroupId());

			if (existingVote == null) {
				serviceContext.setUuid(vote.getUuid());
			}
		}

		try {
			PollsVoteLocalServiceUtil.addVote(
				vote.getUserId(), questionId, choiceId, serviceContext);
		}
		catch (DuplicateVoteException dve) {
		}
	}

}