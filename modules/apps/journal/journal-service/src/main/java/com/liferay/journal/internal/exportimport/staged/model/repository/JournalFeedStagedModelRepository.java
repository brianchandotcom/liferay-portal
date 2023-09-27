/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.exportimport.staged.model.repository;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.journal.model.JournalFeed;
import com.liferay.journal.service.JournalFeedLocalService;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Akos Thurzo
 */
@Component(
	property = "model.class.name=com.liferay.journal.model.JournalFeed",
	service = StagedModelRepository.class
)
public class JournalFeedStagedModelRepository
	implements StagedModelRepository<JournalFeed> {

	@Override
	public JournalFeed addStagedModel(
			PortletDataContext portletDataContext, JournalFeed journalFeed)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteStagedModel(JournalFeed journalFeed)
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
	public JournalFeed fetchMissingReference(String uuid, long groupId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public JournalFeed fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public List<JournalFeed> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		throw new UnsupportedOperationException();
	}

	@Override
	public JournalFeed getStagedModel(long id) throws PortalException {
		return _journalFeedLocalService.getJournalFeed(id);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext, JournalFeed journalFeed)
		throws PortletDataException {

		throw new UnsupportedOperationException();
	}

	@Override
	public JournalFeed saveStagedModel(JournalFeed journalFeed)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public JournalFeed updateStagedModel(
			PortletDataContext portletDataContext, JournalFeed journalFeed)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Reference
	private JournalFeedLocalService _journalFeedLocalService;

}