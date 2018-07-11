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

package com.liferay.sharing.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.sharing.constants.SharingEntryActionKey;
import com.liferay.sharing.exception.InvalidSharingEntryActionKeyException;
import com.liferay.sharing.exception.InvalidSharingEntryUserException;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.service.base.SharingEntryLocalServiceBaseImpl;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Sergio González
 */
public class SharingEntryLocalServiceImpl
	extends SharingEntryLocalServiceBaseImpl {

	public SharingEntry addSharingEntry(
			long fromUserId, long toUserId, String className, long classPK,
			long groupId,
			Collection<SharingEntryActionKey> sharingEntryActionKeys,
			ServiceContext serviceContext)
		throws PortalException {

		_validateSharingEntryActionKeys(sharingEntryActionKeys);

		_validateUsers(fromUserId, toUserId);

		long sharingEntryId = counterLocalService.increment();

		SharingEntry sharingEntry = sharingEntryPersistence.create(
			sharingEntryId);

		sharingEntry.setUuid(serviceContext.getUuid());

		Group group = _groupLocalService.getGroup(groupId);

		sharingEntry.setCompanyId(group.getCompanyId());

		sharingEntry.setGroupId(groupId);
		sharingEntry.setFromUserId(fromUserId);
		sharingEntry.setToUserId(toUserId);
		sharingEntry.setClassName(className);
		sharingEntry.setClassPK(classPK);

		Stream<SharingEntryActionKey> sharingEntryActionKeyStream =
			sharingEntryActionKeys.stream();

		sharingEntryActionKeyStream.map(
			SharingEntryActionKey::getBitwiseVaue
		).reduce(
			(bitwiseValue1, bitwiseValue2) -> bitwiseValue1 | bitwiseValue2
		).ifPresent(
			actionIds -> sharingEntry.setActionIds(actionIds)
		);

		return sharingEntryPersistence.update(sharingEntry);
	}

	public int countSharingEntriesFromUserId(long fromUserId) {
		return sharingEntryPersistence.countByFromUserId(fromUserId);
	}

	public int countSharingEntriesToUserId(long toUserId) {
		return sharingEntryPersistence.countByToUserId(toUserId);
	}

	public SharingEntry deleteSharingEntry(
			long toUserId, String className, long classPK)
		throws PortalException {

		SharingEntry sharingEntry = sharingEntryPersistence.findByTU_CN_PK(
			toUserId, className, classPK);

		return sharingEntryPersistence.remove(sharingEntry);
	}

	public List<SharingEntry> getSharingEntries(
		String className, long classPK) {

		return sharingEntryPersistence.findByCN_PK(className, classPK);
	}

	public List<SharingEntry> getSharingEntriesFromUserId(long fromUserId) {
		return sharingEntryPersistence.findByFromUserId(fromUserId);
	}

	public List<SharingEntry> getSharingEntriesToUserId(long toUserId) {
		return sharingEntryPersistence.findByToUserId(toUserId);
	}

	public List<SharingEntry> getSharingEntriesToUserId(
		long toUserId, String className) {

		return sharingEntryPersistence.findByTU_CN(toUserId, className);
	}

	public SharingEntry getSharingEntry(
			long toUserId, String className, long classPK)
		throws PortalException {

		return sharingEntryPersistence.findByTU_CN_PK(
			toUserId, className, classPK);
	}

	public boolean hasSharingPermission(
		long toUserId, String className, long classPK,
		SharingEntryActionKey sharingEntryActionKey) {

		SharingEntry sharingEntry = sharingEntryPersistence.fetchByTU_CN_PK(
			toUserId, className, classPK);

		if (sharingEntry == null) {
			return false;
		}

		long actionIds = sharingEntry.getActionIds();

		if ((actionIds & sharingEntryActionKey.getBitwiseVaue()) != 0) {
			return true;
		}

		return false;
	}

	private void _validateSharingEntryActionKeys(
			Collection<SharingEntryActionKey> sharedEntryActionKeys)
		throws InvalidSharingEntryActionKeyException {

		if (sharedEntryActionKeys.isEmpty()) {
			throw new InvalidSharingEntryActionKeyException();
		}

		for (SharingEntryActionKey curSharingEntryActionKey :
				sharedEntryActionKeys) {

			if (curSharingEntryActionKey == null) {
				throw new InvalidSharingEntryActionKeyException();
			}
		}
	}

	private void _validateUsers(long fromUserId, long toUserId)
		throws InvalidSharingEntryUserException {

		if (fromUserId == toUserId) {
			throw new InvalidSharingEntryUserException();
		}
	}

	@ServiceReference(type = GroupLocalService.class)
	private GroupLocalService _groupLocalService;

}