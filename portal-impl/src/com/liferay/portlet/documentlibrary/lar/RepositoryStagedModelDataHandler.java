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
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Repository;
import com.liferay.portal.model.RepositoryEntry;
import com.liferay.portal.repository.liferayrepository.LiferayRepository;
import com.liferay.portal.service.RepositoryLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.persistence.RepositoryEntryUtil;
import com.liferay.portal.service.persistence.RepositoryUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;

import java.util.List;

/**
 * @author Mate Thurzo
 */
public class RepositoryStagedModelDataHandler
	extends BaseStagedModelDataHandler<Repository> {

	public static final String[] CLASS_NAMES = {Repository.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, Repository repository)
		throws Exception {

		Element repositoryElement = portletDataContext.getExportDataElement(
			repository);

		Folder folder = DLAppLocalServiceUtil.getFolder(
			repository.getDlFolderId());

		if (folder.getModel() instanceof DLFolder) {
			DLFolder dlFolder = (DLFolder)folder.getModel();

			repositoryElement.addAttribute(
				"hidden", String.valueOf(dlFolder.isHidden()));
		}

		portletDataContext.addClassedModel(
			repositoryElement, ExportImportPathUtil.getModelPath(repository),
			repository, DLPortletDataHandler.NAMESPACE);

		List<RepositoryEntry> repositoryEntries =
			RepositoryEntryUtil.findByRepositoryId(
				repository.getRepositoryId());

		for (RepositoryEntry repositoryEntry : repositoryEntries) {
			StagedModelDataHandlerUtil.exportStagedModel(
				portletDataContext, repositoryEntry);

			portletDataContext.addReferenceElement(
				repository, repositoryElement, repositoryEntry, false);
		}
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, Repository repository)
		throws Exception {

		long userId = portletDataContext.getUserId(repository.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			repository, DLPortletDataHandler.NAMESPACE);

		Repository importedRepository = null;

		Element repositoryElement =
			portletDataContext.getImportDataStagedModelElement(repository);

		try {
			boolean hidden = GetterUtil.getBoolean(
				repositoryElement.attributeValue("hidden"));

			if (portletDataContext.isDataStrategyMirror()) {
				Repository existingRepository = RepositoryUtil.fetchByUUID_G(
					repository.getUuid(), portletDataContext.getScopeGroupId());

				if (existingRepository == null) {
					existingRepository =
						RepositoryLocalServiceUtil.fetchRepository(
							portletDataContext.getScopeGroupId(),
							repository.getName());
				}

				long classNameId = 0;

				if (existingRepository != null) {
					classNameId = existingRepository.getClassNameId();
				}

				if ((existingRepository == null) ||
					(classNameId !=
						PortalUtil.getClassNameId(LiferayRepository.class))) {

					serviceContext.setUuid(repository.getUuid());

					importedRepository =
						RepositoryLocalServiceUtil.addRepository(
							userId, portletDataContext.getScopeGroupId(),
							repository.getClassNameId(),
							DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
							repository.getName(), repository.getDescription(),
							repository.getPortletId(),
							repository.getTypeSettingsProperties(), hidden,
							serviceContext);
				}
				else {
					RepositoryLocalServiceUtil.updateRepository(
						existingRepository.getRepositoryId(),
						repository.getName(), repository.getDescription());

					importedRepository = existingRepository;
				}
			}
			else {
				importedRepository = RepositoryLocalServiceUtil.addRepository(
					userId, portletDataContext.getScopeGroupId(),
					repository.getClassNameId(),
					DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
					repository.getName(), repository.getDescription(),
					repository.getPortletId(),
					repository.getTypeSettingsProperties(), hidden,
					serviceContext);
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to connect to repository {name=" +
						repository.getName() + ", typeSettings=" +
							repository.getTypeSettingsProperties() + "}",
					e);
			}
		}

		portletDataContext.importClassedModel(
			repository, importedRepository, DLPortletDataHandler.NAMESPACE);

		List<Element> repositoryEntryElements =
			portletDataContext.getReferenceDataElements(
				repository, RepositoryEntry.class);

		for (Element repositoryEntryElement : repositoryEntryElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, repositoryEntryElement);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		RepositoryStagedModelDataHandler.class);

}