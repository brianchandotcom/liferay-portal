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

package com.liferay.portlet.documentlibrary.lar;

import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Repository;
import com.liferay.portal.model.RepositoryEntry;
import com.liferay.portal.service.RepositoryEntryLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.persistence.RepositoryEntryUtil;

import java.util.Map;

/**
 * @author Mate Thurzo
 */
public class RepositoryEntryStagedModelDataHandler
	extends BaseStagedModelDataHandler<RepositoryEntry> {

	public static final String[] CLASS_NAMES =
		{RepositoryEntry.class.getName()};

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
			ExportImportPathUtil.getModelPath(repositoryEntry), repositoryEntry,
			DLPortletDataHandler.NAMESPACE);
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
			repositoryEntry, DLPortletDataHandler.NAMESPACE);

		RepositoryEntry importedRepositoryEntry = null;

		if (portletDataContext.isDataStrategyMirror()) {
			RepositoryEntry existingRepositoryEntry =
				RepositoryEntryUtil.fetchByUUID_G(
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
			repositoryEntry, importedRepositoryEntry,
			DLPortletDataHandler.NAMESPACE);
	}

}