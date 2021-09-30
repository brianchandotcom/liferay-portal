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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.exception.NoSuchRepositoryEntryException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.RepositoryEntry;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.service.base.RepositoryEntryLocalServiceBaseImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Michael C. Han
 * @author Máté Thurzó
 */
public class RepositoryEntryLocalServiceImpl
	extends RepositoryEntryLocalServiceBaseImpl {

	@Override
	public RepositoryEntry addRepositoryEntry(
			long userId, long groupId, long repositoryId, String mappedId,
			ServiceContext serviceContext)
		throws PortalException {

		User user = _userPersistence.findByPrimaryKey(userId);

		long repositoryEntryId = counterLocalService.increment();

		RepositoryEntry repositoryEntry = repositoryEntryPersistence.create(
			repositoryEntryId);

		repositoryEntry.setUuid(serviceContext.getUuid());
		repositoryEntry.setGroupId(groupId);
		repositoryEntry.setCompanyId(user.getCompanyId());
		repositoryEntry.setUserId(userId);
		repositoryEntry.setUserName(user.getFullName());
		repositoryEntry.setRepositoryId(repositoryId);
		repositoryEntry.setMappedId(mappedId);

		return repositoryEntryPersistence.update(repositoryEntry);
	}

	@Override
	public void deleteRepositoryEntries(
			long repositoryId, Iterable<String> mappedIds)
		throws PortalException {

		for (String mappedId : mappedIds) {
			try {
				deleteRepositoryEntry(repositoryId, mappedId);
			}
			catch (NoSuchRepositoryEntryException
						noSuchRepositoryEntryException) {

				// LPS-52675

				if (_log.isDebugEnabled()) {
					_log.debug(
						noSuchRepositoryEntryException,
						noSuchRepositoryEntryException);
				}
			}
		}
	}

	@Override
	public void deleteRepositoryEntry(long repositoryId, String mappedId)
		throws PortalException {

		repositoryEntryPersistence.removeByR_M(repositoryId, mappedId);
	}

	@Override
	public List<RepositoryEntry> getRepositoryEntries(long repositoryId) {
		return repositoryEntryPersistence.findByRepositoryId(repositoryId);
	}

	@Override
	public RepositoryEntry getRepositoryEntry(
			long userId, long groupId, long repositoryId, String objectId)
		throws PortalException {

		RepositoryEntry repositoryEntry = repositoryEntryPersistence.fetchByR_M(
			repositoryId, objectId);

		if (repositoryEntry != null) {
			return repositoryEntry;
		}

		return repositoryEntryLocalService.addRepositoryEntry(
			userId, groupId, repositoryId, objectId, new ServiceContext());
	}

	@Override
	public RepositoryEntry getRepositoryEntry(String uuid, long groupId)
		throws PortalException {

		return repositoryEntryPersistence.findByUUID_G(uuid, groupId);
	}

	@Override
	public RepositoryEntry updateRepositoryEntry(
			long repositoryEntryId, String mappedId)
		throws PortalException {

		RepositoryEntry repositoryEntry =
			repositoryEntryPersistence.findByPrimaryKey(repositoryEntryId);

		repositoryEntry.setMappedId(mappedId);

		return repositoryEntryPersistence.update(repositoryEntry);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RepositoryEntryLocalServiceImpl.class);

	@BeanReference(type = UserPersistence.class)
	private UserPersistence _userPersistence;

}