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

package com.liferay.document.library.lar;

import com.liferay.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Repository;
import com.liferay.portal.model.RepositoryEntry;
import com.liferay.portal.service.RepositoryEntryLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.exportimport.lar.ExportImportPathUtil;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandler;
import com.liferay.portlet.exportimport.lar.StagedModelModifiedDateComparator;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Mate Thurzo
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class RepositoryEntryStagedModelDataHandler
	extends BaseStagedModelDataHandler<RepositoryEntry> {

	public static final String[] CLASS_NAMES =
		{RepositoryEntry.class.getName()};

	@Override
	public void deleteStagedModel(RepositoryEntry repositoryEntry) {
		RepositoryEntryLocalServiceUtil.deleteRepositoryEntry(repositoryEntry);
	}

	@Override
	public void deleteStagedModel(
		String uuid, long groupId, String className, String extraData) {

		RepositoryEntry repositoryEntry = fetchStagedModelByUuidAndGroupId(
			uuid, groupId);

		if (repositoryEntry != null) {
			deleteStagedModel(repositoryEntry);
		}
	}

	@Override
	public RepositoryEntry fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return RepositoryEntryLocalServiceUtil.
			fetchRepositoryEntryByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<RepositoryEntry> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return RepositoryEntryLocalServiceUtil.
			getRepositoryEntriesByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<RepositoryEntry>());
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			RepositoryEntry repositoryEntry)
		throws Exception {

		Element repositoryEntryElement =
			portletDataContext.getExportDataElement(repositoryEntry);

		portletDataContext.addClassedModel(
			repositoryEntryElement,
			ExportImportPathUtil.getModelPath(repositoryEntry),
			repositoryEntry);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			RepositoryEntry repositoryEntry)
		throws Exception {

		long userId = portletDataContext.getUserId(
			repositoryEntry.getUserUuid());

		Map<Long, Long> repositoryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Repository.class);

		long repositoryId = MapUtil.getLong(
			repositoryIds, repositoryEntry.getRepositoryId(),
			repositoryEntry.getRepositoryId());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			repositoryEntry);

		RepositoryEntry importedRepositoryEntry = null;

		if (portletDataContext.isDataStrategyMirror()) {
			RepositoryEntry existingRepositoryEntry =
				fetchStagedModelByUuidAndGroupId(
					repositoryEntry.getUuid(),
					portletDataContext.getScopeGroupId());

			if (existingRepositoryEntry == null) {
				serviceContext.setUuid(repositoryEntry.getUuid());

				importedRepositoryEntry =
					RepositoryEntryLocalServiceUtil.addRepositoryEntry(
						userId, portletDataContext.getScopeGroupId(),
						repositoryId, repositoryEntry.getMappedId(),
						serviceContext);
			}
			else {
				importedRepositoryEntry =
					RepositoryEntryLocalServiceUtil.updateRepositoryEntry(
						existingRepositoryEntry.getRepositoryEntryId(),
						repositoryEntry.getMappedId());
			}
		}
		else {
			importedRepositoryEntry =
				RepositoryEntryLocalServiceUtil.addRepositoryEntry(
					userId, portletDataContext.getScopeGroupId(), repositoryId,
					repositoryEntry.getMappedId(), serviceContext);
		}

		portletDataContext.importClassedModel(
			repositoryEntry, importedRepositoryEntry);
	}

}