/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.exportimport.staged.model.repository;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalFolderLocalService;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Akos Thurzo
 */
@Component(
	property = "model.class.name=com.liferay.journal.model.JournalFolder",
	service = StagedModelRepository.class
)
public class JournalFolderStagedModelRepository
	implements StagedModelRepository<JournalFolder> {

	@Override
	public JournalFolder addStagedModel(
			PortletDataContext portletDataContext, JournalFolder journalFolder)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteStagedModel(JournalFolder journalFolder)
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
	public JournalFolder fetchMissingReference(String uuid, long groupId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public JournalFolder fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public List<JournalFolder> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		throw new UnsupportedOperationException();
	}

	@Override
	public JournalFolder getStagedModel(long folderId) throws PortalException {
		return _journalFolderLocalService.getJournalFolder(folderId);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext, JournalFolder journalFolder)
		throws PortletDataException {

		throw new UnsupportedOperationException();
	}

	@Override
	public JournalFolder saveStagedModel(JournalFolder journalFolder)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public JournalFolder updateStagedModel(
			PortletDataContext portletDataContext, JournalFolder journalFolder)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Reference
	private JournalFolderLocalService _journalFolderLocalService;

}