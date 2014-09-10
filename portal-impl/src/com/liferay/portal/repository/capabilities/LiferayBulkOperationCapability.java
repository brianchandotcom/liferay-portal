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

package com.liferay.portal.repository.capabilities;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.capabilities.BulkOperationCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.repository.model.RepositoryModelOperation;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.repository.liferayrepository.model.LiferayFolder;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;

/**
 * @author Adolfo Pérez
 */
public class LiferayBulkOperationCapability implements BulkOperationCapability {

	public LiferayBulkOperationCapability(long repositoryId) {
		_repositoryId = repositoryId;
	}

	@Override
	public void execute(RepositoryModelOperation repositoryModelOperation)
		throws PortalException {

		executeOnAllFileEntries(repositoryModelOperation);
		executeOnAllFolders(repositoryModelOperation);
	}

	protected void executeOnAllFileEntries(
			RepositoryModelOperation repositoryModelOperation)
		throws PortalException {

		ActionableDynamicQuery actionableDynamicQuery =
			DLFileEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			new RepositoryModelAddCriteriaMethod());

		actionableDynamicQuery.setPerformActionMethod(
			new FileEntryPerformActionMethod(repositoryModelOperation));

		actionableDynamicQuery.performActions();
	}

	protected void executeOnAllFolders(
			RepositoryModelOperation repositoryModelOperation)
		throws PortalException {

		ActionableDynamicQuery actionableDynamicQuery =
			DLFolderLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			new RepositoryModelAddCriteriaMethod());

		actionableDynamicQuery.setPerformActionMethod(
			new FolderPerformActionMethod(repositoryModelOperation));

		actionableDynamicQuery.performActions();
	}

	private long _repositoryId;

	private static class FileEntryPerformActionMethod
		implements ActionableDynamicQuery.PerformActionMethod {

		public FileEntryPerformActionMethod(
			RepositoryModelOperation repositoryModelOperation) {

			_repositoryModelOperation = repositoryModelOperation;
		}

		@Override
		public void performAction(Object object) throws PortalException {
			DLFileEntry dlFileEntry = (DLFileEntry)object;

			FileEntry fileEntry = new LiferayFileEntry(dlFileEntry);

			fileEntry.execute(_repositoryModelOperation);
		}

		private RepositoryModelOperation _repositoryModelOperation;

	}

	private static class FolderPerformActionMethod
		implements ActionableDynamicQuery.PerformActionMethod {

		public FolderPerformActionMethod(
			RepositoryModelOperation repositoryModelOperation) {

			_repositoryModelOperation = repositoryModelOperation;
		}

		@Override
		public void performAction(Object object) throws PortalException {
			DLFolder dlFolder = (DLFolder)object;

			Folder folder = new LiferayFolder(dlFolder);

			folder.execute(_repositoryModelOperation);
		}

		private final RepositoryModelOperation _repositoryModelOperation;

	}

	private class RepositoryModelAddCriteriaMethod
		implements ActionableDynamicQuery.AddCriteriaMethod {

		@Override
		public void addCriteria(DynamicQuery dynamicQuery) {
			dynamicQuery.add(
				RestrictionsFactoryUtil.eq("repositoryId", _repositoryId));
		}

	}

}