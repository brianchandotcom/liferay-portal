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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Repository;
import com.liferay.portal.model.RepositoryEntry;
import com.liferay.portal.service.RepositoryEntryLocalService;
import com.liferay.portal.service.RepositoryLocalService;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalService;
import com.liferay.portlet.exportimport.lar.ExportImportPathUtil;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandler;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandlerUtil;
import com.liferay.portlet.exportimport.lar.StagedModelModifiedDateComparator;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mate Thurzo
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class RepositoryStagedModelDataHandler
	extends BaseStagedModelDataHandler<Repository> {

	public static final String[] CLASS_NAMES = {Repository.class.getName()};

	@Override
	public void deleteStagedModel(Repository repository)
		throws PortalException {

		_repositoryLocalService.deleteRepository(repository.getRepositoryId());
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		Repository repository = fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (repository != null) {
			deleteStagedModel(repository);
		}
	}

	@Override
	public Repository fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _repositoryLocalService.fetchRepositoryByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<Repository> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _repositoryLocalService.getRepositoriesByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<Repository>());
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(Repository repository) {
		return repository.getName();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, Repository repository)
		throws Exception {

		Element repositoryElement = portletDataContext.getExportDataElement(
			repository);

		Folder folder = _dlAppLocalService.getFolder(
			repository.getDlFolderId());

		if (folder.getModel() instanceof DLFolder) {
			DLFolder dlFolder = (DLFolder)folder.getModel();

			repositoryElement.addAttribute(
				"hidden", String.valueOf(dlFolder.isHidden()));
		}

		portletDataContext.addClassedModel(
			repositoryElement, ExportImportPathUtil.getModelPath(repository),
			repository);

		List<RepositoryEntry> repositoryEntries =
			_repositoryEntryLocalService.getRepositoryEntries(
				repository.getRepositoryId());

		for (RepositoryEntry repositoryEntry : repositoryEntries) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, repository, repositoryEntry,
				PortletDataContext.REFERENCE_TYPE_CHILD);
		}
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, Repository repository)
		throws Exception {

		long userId = portletDataContext.getUserId(repository.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			repository);

		Repository importedRepository = null;

		Element repositoryElement =
			portletDataContext.getImportDataStagedModelElement(repository);

		try {
			boolean hidden = GetterUtil.getBoolean(
				repositoryElement.attributeValue("hidden"));

			if (portletDataContext.isDataStrategyMirror()) {
				Repository existingRepository =
					fetchStagedModelByUuidAndGroupId(
						repository.getUuid(),
						portletDataContext.getScopeGroupId());

				if (existingRepository == null) {
					existingRepository =
						_repositoryLocalService.fetchRepository(
							portletDataContext.getScopeGroupId(),
							repository.getName());
				}

				if (existingRepository == null) {
					serviceContext.setUuid(repository.getUuid());

					importedRepository = _repositoryLocalService.addRepository(
						userId, portletDataContext.getScopeGroupId(),
						repository.getClassNameId(),
						DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
						repository.getName(), repository.getDescription(),
						repository.getPortletId(),
						repository.getTypeSettingsProperties(), hidden,
						serviceContext);
				}
				else {
					_repositoryLocalService.updateRepository(
						existingRepository.getRepositoryId(),
						repository.getName(), repository.getDescription());

					importedRepository = existingRepository;
				}
			}
			else {
				importedRepository = _repositoryLocalService.addRepository(
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

		portletDataContext.importClassedModel(repository, importedRepository);

		StagedModelDataHandlerUtil.importReferenceStagedModels(
			portletDataContext, repository, RepositoryEntry.class);
	}

	@Override
	protected void importReferenceStagedModels(
		PortletDataContext portletDataContext, Repository stagedModel) {
	}

	@Reference(unbind = "-")
	protected void setDLAppLocalService(DLAppLocalService dlAppLocalService) {
		_dlAppLocalService = dlAppLocalService;
	}

	@Reference(unbind = "-")
	protected void setRepositoryEntryLocalService(
		RepositoryEntryLocalService repositoryEntryLocalService) {

		_repositoryEntryLocalService = repositoryEntryLocalService;
	}

	@Reference(unbind = "-")
	protected void setRepositoryLocalService(
		RepositoryLocalService repositoryLocalService) {

		_repositoryLocalService = repositoryLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RepositoryStagedModelDataHandler.class);

	private volatile DLAppLocalService _dlAppLocalService;
	private volatile RepositoryEntryLocalService _repositoryEntryLocalService;
	private volatile RepositoryLocalService _repositoryLocalService;

}