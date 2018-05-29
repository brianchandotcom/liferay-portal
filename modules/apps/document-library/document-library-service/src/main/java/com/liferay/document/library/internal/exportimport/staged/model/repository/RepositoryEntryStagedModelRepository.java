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

package com.liferay.document.library.internal.exportimport.staged.model.repository;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.RepositoryEntry;
import com.liferay.portal.kernel.service.RepositoryEntryLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Akos Thurzo
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.portal.kernel.model.RepositoryEntry",
	service = StagedModelRepository.class
)
public class RepositoryEntryStagedModelRepository
	implements StagedModelRepository<RepositoryEntry> {

	@Override
	public RepositoryEntry addStagedModel(
			PortletDataContext portletDataContext,
			RepositoryEntry repositoryEntry)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteStagedModel(RepositoryEntry repositoryEntry)
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
	public RepositoryEntry fetchMissingReference(String uuid, long groupId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public RepositoryEntry fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public List<RepositoryEntry> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		throw new UnsupportedOperationException();
	}

	@Override
	public RepositoryEntry getStagedModel(long repositoryEntryId)
		throws PortalException {

		return _repositoryEntryLocalService.getRepositoryEntry(
			repositoryEntryId);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext,
			RepositoryEntry repositoryEntry)
		throws PortletDataException {

		throw new UnsupportedOperationException();
	}

	@Override
	public RepositoryEntry saveStagedModel(RepositoryEntry repositoryEntry)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public RepositoryEntry updateStagedModel(
			PortletDataContext portletDataContext,
			RepositoryEntry repositoryEntry)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Reference
	private RepositoryEntryLocalService _repositoryEntryLocalService;

}