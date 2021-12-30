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

package com.liferay.document.library.repository.portlet.repository.internal;

import com.liferay.document.library.kernel.service.DLAppHelperLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryService;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.document.library.kernel.service.DLFileShortcutLocalService;
import com.liferay.document.library.kernel.service.DLFileShortcutService;
import com.liferay.document.library.kernel.service.DLFileVersionLocalService;
import com.liferay.document.library.kernel.service.DLFileVersionService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.document.library.kernel.service.DLFolderService;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.RepositoryFactory;
import com.liferay.portal.kernel.service.RepositoryLocalService;
import com.liferay.portal.kernel.service.RepositoryService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.repository.liferayrepository.LiferayLocalRepository;
import com.liferay.portal.repository.portletrepository.PortletRepository;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(
	property = "repository.target.class.name=com.liferay.portal.repository.portletrepository.PortletRepository",
	service = RepositoryFactory.class
)
public class PortletRepositoryFactory implements RepositoryFactory {

	@Override
	public LocalRepository createLocalRepository(long repositoryId) {
		return _createLocalRepositoryInstance(
			_getRepositoryLocation(repositoryId));
	}

	@Override
	public Repository createRepository(long repositoryId) {
		return _createRepositoryInstance(_getRepositoryLocation(repositoryId));
	}

	private LocalRepository _createLocalRepositoryInstance(
		long[] repositoryLocation) {

		long groupId = repositoryLocation[0];
		long repositoryId = repositoryLocation[1];
		long dlFolderId = repositoryLocation[2];

		return new LiferayLocalRepository(
			_repositoryLocalService, _repositoryService,
			_dlAppHelperLocalService, _dlFileEntryLocalService,
			_dlFileEntryService, _dlFileEntryTypeLocalService,
			_dlFileShortcutLocalService, _dlFileShortcutService,
			_dlFileVersionLocalService, _dlFileVersionService,
			_dlFolderLocalService, _dlFolderService, _resourceLocalService,
			groupId, repositoryId, dlFolderId);
	}

	private Repository _createRepositoryInstance(long[] repositoryLocation) {
		long groupId = repositoryLocation[0];
		long repositoryId = repositoryLocation[1];
		long dlFolderId = repositoryLocation[2];

		return new PortletRepository(
			_repositoryLocalService, _repositoryService,
			_dlAppHelperLocalService, _dlFileEntryLocalService,
			_dlFileEntryService, _dlFileEntryTypeLocalService,
			_dlFileShortcutLocalService, _dlFileShortcutService,
			_dlFileVersionLocalService, _dlFileVersionService,
			_dlFolderLocalService, _dlFolderService, _resourceLocalService,
			groupId, repositoryId, dlFolderId);
	}

	private long[] _getRepositoryLocation(long repositoryId) {
		long dlFolderId = 0;
		long groupId = 0;

		com.liferay.portal.kernel.model.Repository repository =
			_repositoryLocalService.fetchRepository(repositoryId);

		if (repository == null) {
			groupId = repositoryId;
		}
		else {
			groupId = repository.getGroupId();
			dlFolderId = repository.getDlFolderId();
		}

		return new long[] {groupId, repositoryId, dlFolderId};
	}

	@Reference
	private DLAppHelperLocalService _dlAppHelperLocalService;

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Reference
	private DLFileEntryService _dlFileEntryService;

	@Reference
	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;

	@Reference
	private DLFileShortcutLocalService _dlFileShortcutLocalService;

	@Reference
	private DLFileShortcutService _dlFileShortcutService;

	@Reference
	private DLFileVersionLocalService _dlFileVersionLocalService;

	@Reference
	private DLFileVersionService _dlFileVersionService;

	@Reference
	private DLFolderLocalService _dlFolderLocalService;

	@Reference
	private DLFolderService _dlFolderService;

	@Reference
	private RepositoryLocalService _repositoryLocalService;

	@Reference
	private RepositoryService _repositoryService;

	@Reference
	private ResourceLocalService _resourceLocalService;

}