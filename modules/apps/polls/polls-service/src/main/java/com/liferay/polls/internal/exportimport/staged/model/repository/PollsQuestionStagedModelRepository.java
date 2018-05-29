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

package com.liferay.polls.internal.exportimport.staged.model.repository;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.polls.model.PollsQuestion;
import com.liferay.polls.service.PollsQuestionLocalService;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Akos Thurzo
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.polls.model.PollsQuestion",
	service = StagedModelRepository.class
)
public class PollsQuestionStagedModelRepository
	implements StagedModelRepository<PollsQuestion> {

	@Override
	public PollsQuestion addStagedModel(
			PortletDataContext portletDataContext, PollsQuestion pollsQuestion)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteStagedModel(PollsQuestion pollsQuestion)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public PollsQuestion fetchMissingReference(String uuid, long groupId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public PollsQuestion fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public List<PollsQuestion> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		throw new UnsupportedOperationException();
	}

	@Override
	public PollsQuestion getStagedModel(long questionId)
		throws PortalException {

		return _pollsQuestionLocalService.getPollsQuestion(questionId);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext, PollsQuestion pollsQuestion)
		throws PortletDataException {

		throw new UnsupportedOperationException();
	}

	@Override
	public PollsQuestion saveStagedModel(PollsQuestion pollsQuestion)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public PollsQuestion updateStagedModel(
			PortletDataContext portletDataContext, PollsQuestion pollsQuestion)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Reference
	private PollsQuestionLocalService _pollsQuestionLocalService;

}