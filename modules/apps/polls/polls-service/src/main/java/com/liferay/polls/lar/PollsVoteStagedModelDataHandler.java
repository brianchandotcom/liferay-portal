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

import com.liferay.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.polls.exception.DuplicateVoteException;
import com.liferay.polls.model.PollsChoice;
import com.liferay.polls.model.PollsQuestion;
import com.liferay.polls.model.PollsVote;
import com.liferay.polls.service.PollsVoteLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.exportimport.lar.ExportImportPathUtil;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandler;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandlerUtil;
import com.liferay.portlet.exportimport.lar.StagedModelModifiedDateComparator;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shinn Lok
 * @author Mate Thurzo
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class PollsVoteStagedModelDataHandler
	extends BaseStagedModelDataHandler<PollsVote> {

	public static final String[] CLASS_NAMES = {PollsVote.class.getName()};

	@Override
	public void deleteStagedModel(PollsVote vote) {
		_pollsVoteLocalService.deletePollsVote(vote);
	}

	@Override
	public void deleteStagedModel(
		String uuid, long groupId, String className, String extraData) {

		PollsVote vote = fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (vote != null) {
			deleteStagedModel(vote);
		}
	}

	@Override
	public PollsVote fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _pollsVoteLocalService.fetchPollsVoteByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<PollsVote> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _pollsVoteLocalService.getPollsVotesByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<PollsVote>());
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, PollsVote vote)
		throws Exception {

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, vote, vote.getChoice(),
			PortletDataContext.REFERENCE_TYPE_STRONG);

		Element voteElement = portletDataContext.getExportDataElement(vote);

		portletDataContext.addClassedModel(
			voteElement, ExportImportPathUtil.getModelPath(vote), vote);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long voteId)
		throws Exception {

		PollsVote existingVote = fetchMissingReference(uuid, groupId);

		Map<Long, Long> voteIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				PollsVote.class);

		voteIds.put(voteId, existingVote.getVoteId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, PollsVote vote)
		throws Exception {

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
			vote);

		serviceContext.setCreateDate(vote.getCreateDate());

		if (portletDataContext.isDataStrategyMirror()) {
			PollsVote existingVote = fetchStagedModelByUuidAndGroupId(
				vote.getUuid(), portletDataContext.getScopeGroupId());

			if (existingVote == null) {
				serviceContext.setUuid(vote.getUuid());
			}
		}

		try {
			_pollsVoteLocalService.addVote(
				vote.getUserId(), questionId, choiceId, serviceContext);
		}
		catch (DuplicateVoteException dve) {
		}
	}

	@Reference(unbind = "-")
	protected void setPollsVoteLocalService(
		PollsVoteLocalService pollsVoteLocalService) {

		_pollsVoteLocalService = pollsVoteLocalService;
	}

	private volatile PollsVoteLocalService _pollsVoteLocalService;

}