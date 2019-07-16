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

package com.liferay.portal.repository.liferayrepository;

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
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.RepositoryFactory;
import com.liferay.portal.kernel.service.RepositoryLocalService;
import com.liferay.portal.kernel.service.RepositoryService;
import com.liferay.portal.kernel.service.ResourceLocalService;

/**
 * @author Adolfo Pérez
 */
public class LiferayRepositoryFactory implements RepositoryFactory {

	@Override
	public LocalRepository createLocalRepository(long repositoryId) {
		return createLocalRepositoryInstance(
			getRepositoryLocation(repositoryId));
	}

	@Override
	public Repository createRepository(long repositoryId) {
		return createRepositoryInstance(getRepositoryLocation(repositoryId));
	}

	protected LocalRepository createLocalRepositoryInstance(
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

	protected Repository createRepositoryInstance(long[] repositoryLocation) {
		long groupId = repositoryLocation[0];
		long repositoryId = repositoryLocation[1];
		long dlFolderId = repositoryLocation[2];

		return new LiferayRepository(
			_repositoryLocalService, _repositoryService,
			_dlAppHelperLocalService, _dlFileEntryLocalService,
			_dlFileEntryService, _dlFileEntryTypeLocalService,
			_dlFileShortcutLocalService, _dlFileShortcutService,
			_dlFileVersionLocalService, _dlFileVersionService,
			_dlFolderLocalService, _dlFolderService, _resourceLocalService,
			groupId, repositoryId, dlFolderId);
	}

	protected long[] getRepositoryLocation(long repositoryId) {
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

	@BeanReference(type = DLAppHelperLocalService.class)
	private DLAppHelperLocalService _dlAppHelperLocalService;

	@BeanReference(type = DLFileEntryLocalService.class)
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@BeanReference(type = DLFileEntryService.class)
	private DLFileEntryService _dlFileEntryService;

	@BeanReference(type = DLFileEntryTypeLocalService.class)
	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;

	@BeanReference(type = DLFileShortcutLocalService.class)
	private DLFileShortcutLocalService _dlFileShortcutLocalService;

	@BeanReference(type = DLFileShortcutService.class)
	private DLFileShortcutService _dlFileShortcutService;

	@BeanReference(type = DLFileVersionLocalService.class)
	private DLFileVersionLocalService _dlFileVersionLocalService;

	@BeanReference(type = DLFileVersionService.class)
	private DLFileVersionService _dlFileVersionService;

	@BeanReference(type = DLFolderLocalService.class)
	private DLFolderLocalService _dlFolderLocalService;

	@BeanReference(type = DLFolderService.class)
	private DLFolderService _dlFolderService;

	@BeanReference(type = RepositoryLocalService.class)
	private RepositoryLocalService _repositoryLocalService;

	@BeanReference(type = RepositoryService.class)
	private RepositoryService _repositoryService;

	@BeanReference(type = ResourceLocalService.class)
	private ResourceLocalService _resourceLocalService;

}