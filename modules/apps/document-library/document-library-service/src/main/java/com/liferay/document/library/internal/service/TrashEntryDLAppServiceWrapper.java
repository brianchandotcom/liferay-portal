/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.service;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceWrapper;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.NoSuchGroupException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.InvalidRepositoryIdException;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.RepositoryProvider;
import com.liferay.portal.kernel.repository.capabilities.TrashCapability;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.trash.TrashHelper;
import com.liferay.trash.service.TrashEntryLocalService;
import com.liferay.trash.service.TrashEntryService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = ServiceWrapper.class)
public class TrashEntryDLAppServiceWrapper extends DLAppServiceWrapper {

	@Override
	public void deleteFolder(long folderId) throws PortalException {
		Repository repository = _repositoryProvider.getFolderRepository(
			folderId);

		Folder folder = repository.getFolder(folderId);

		if (repository.isCapabilityProvided(TrashCapability.class)) {
			TrashCapability trashCapability = repository.getCapability(
				TrashCapability.class);

			if (trashCapability.isInTrash(folder)) {
				_trashEntryService.deleteEntry(
					DLFolderConstants.getClassName(), folder.getFolderId());

				return;
			}
		}

		super.deleteFolder(folderId);
	}

	@Override
	public void deleteFolder(
			long repositoryId, long parentFolderId, String name)
		throws PortalException {

		Repository repository = _getRepository(repositoryId);

		Folder folder = repository.getFolder(parentFolderId, name);

		if (repository.isCapabilityProvided(TrashCapability.class)) {
			TrashCapability trashCapability = repository.getCapability(
				TrashCapability.class);

			if (trashCapability.isInTrash(folder)) {
				_trashEntryService.deleteEntry(
					DLFolderConstants.getClassName(), folder.getFolderId());

				return;
			}
		}

		super.deleteFolder(repositoryId, parentFolderId, name);
	}

	private Repository _getRepository(long repositoryId)
		throws PortalException {

		try {
			return _repositoryProvider.getRepository(repositoryId);
		}
		catch (InvalidRepositoryIdException invalidRepositoryIdException) {
			throw new NoSuchGroupException(
				StringBundler.concat(
					"No Group exists with the key {repositoryId=", repositoryId,
					"}"),
				invalidRepositoryIdException);
		}
	}

	@Reference
	private RepositoryProvider _repositoryProvider;

	@Reference
	private TrashEntryLocalService _trashEntryLocalService;

	@Reference
	private TrashEntryService _trashEntryService;

	@Reference
	private TrashHelper _trashHelper;

}