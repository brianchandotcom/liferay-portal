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

package com.liferay.journal.internal.exportimport.staged.model.repository;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
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
	property = "model.class.name=com.liferay.journal.model.JournalArticle",
	service = StagedModelRepository.class
)
public class JournalArticleStagedModelRepository
	implements StagedModelRepository<JournalArticle> {

	@Override
	public JournalArticle addStagedModel(
			PortletDataContext portletDataContext,
			JournalArticle journalArticle)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteStagedModel(JournalArticle journalArticle)
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
	public JournalArticle fetchMissingReference(String uuid, long groupId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public JournalArticle fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public List<JournalArticle> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		throw new UnsupportedOperationException();
	}

	@Override
	public JournalArticle getStagedModel(long id) throws PortalException {
		return _journalArticleLocalService.getArticle(id);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext,
			JournalArticle journalArticle)
		throws PortletDataException {

		throw new UnsupportedOperationException();
	}

	@Override
	public JournalArticle saveStagedModel(JournalArticle journalArticle)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public JournalArticle updateStagedModel(
			PortletDataContext portletDataContext,
			JournalArticle journalArticle)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

}