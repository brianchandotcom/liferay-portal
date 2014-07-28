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

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.capabilities.TrashCapability;
import com.liferay.portal.kernel.repository.event.RepositoryEventListener;
import com.liferay.portal.kernel.repository.event.RepositoryEventType;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.repository.registry.BaseRepositoryRegistryPlugin;
import com.liferay.portal.kernel.repository.registry.CapabilityRegistry;
import com.liferay.portal.kernel.repository.registry.RepositoryCreator;
import com.liferay.portal.kernel.repository.registry.RepositoryCreatorRegistry;
import com.liferay.portal.kernel.repository.registry.RepositoryEventRegistry;
import com.liferay.portal.repository.capabilities.LiferayTrashCapability;
import com.liferay.portal.service.ClassNameLocalService;

/**
 * @author Adolfo Pérez
 */
public class LiferayRepositoryRegistryPlugin
	extends BaseRepositoryRegistryPlugin {

	public long getClassNameId() {
		if (_classNameId == 0) {
			_classNameId = _classNameLocalService.getClassNameId(
				LiferayRepository.class);
		}

		return _classNameId;
	}

	@Override
	public void registerCapabilities(CapabilityRegistry capabilityRegistry) {
		capabilityRegistry.addPublicCapability(
			TrashCapability.class, _trashCapability);
	}

	@Override
	public void registerRepositoryCreator(
		RepositoryCreatorRegistry repositoryCreatorRegistry) {

		repositoryCreatorRegistry.setRepositoryCreator(_repositoryCreator);
	}

	@Override
	public void registerRepositoryEventListeners(
		RepositoryEventRegistry repositoryEventRegistry) {

		repositoryEventRegistry.registerRepositoryEventListener(
			RepositoryEventType.Delete.class, FileEntry.class,
			new DeleteFileEntryRepositoryEventListener());

		repositoryEventRegistry.registerRepositoryEventListener(
			RepositoryEventType.Delete.class, Folder.class,
			new DeleteFolderRepositoryEventListener());
	}

	public void setRepositoryCreator(RepositoryCreator repositoryCreator) {
		_repositoryCreator = repositoryCreator;
	}

	private long _classNameId;

	@BeanReference(type = ClassNameLocalService.class)
	private ClassNameLocalService _classNameLocalService;

	private RepositoryCreator _repositoryCreator;
	private TrashCapability _trashCapability = new LiferayTrashCapability();

	private class DeleteFileEntryRepositoryEventListener
		implements RepositoryEventListener
			<RepositoryEventType.Delete, FileEntry> {

		@Override
		public void execute(FileEntry fileEntry) throws PortalException {
			_trashCapability.deleteTrashEntry(fileEntry);
		}

	}

	private class DeleteFolderRepositoryEventListener
		implements RepositoryEventListener<RepositoryEventType.Delete, Folder> {

		@Override
		public void execute(Folder folder) throws PortalException {
			_trashCapability.deleteTrashEntry(folder);
		}

	}

}