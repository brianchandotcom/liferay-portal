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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.sharing.exception.InvalidSharingEntryActionException;
import com.liferay.sharing.exception.InvalidSharingEntryExpirationDateException;
import com.liferay.sharing.exception.InvalidSharingEntryUserException;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.security.permission.SharingEntryAction;
import com.liferay.sharing.service.base.SharingEntryLocalServiceBaseImpl;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

/**
 * Provides the local service for accessing, adding, checking, deleting and
 * updating sharing entries.
 *
 * <p>
 * This service does not perform any kind of permission checking and assumes
 * that the user has permission to share the resource with another user with
 * any sharing entry action. Permission checks are done in
 * {@link SharingEntryServiceImpl}.
 * </p>
 *
 * <p>
 * However, this service performs some validations and integrity checks that
 * ensure that the sharing entries are valid and consistent.
 * </p>
 *
 * @author Sergio González
 * @review
 */
public class SharingEntryLocalServiceImpl
	extends SharingEntryLocalServiceBaseImpl {

	/**
	 * Adds a sharing entry in the database if it does not exist or it updates
	 * it if it exists.
	 *
	 * @param  fromUserId the user id sharing the resource
	 * @param  toUserId the user id whose resource was shared
	 * @param  classNameId the class name ID of the resource being shared
	 * @param  classPK the primary key of the resource being shared
	 * @param  groupId the primary key of the group containing the resource
	 *         being shared
	 * @param  shareable whether the to user id can share the resource as well
	 * @param  sharingEntryActions the sharing entry actions
	 * @param  expirationDate the date when the sharing entry expires
	 * @return the sharing entry
	 * @param  serviceContext the service context to be applied
	 * @throws PortalException if sharing entry actions are invalid (it is
	 *         empty, it doesn't contain {@link SharingEntryAction#VIEW,} or
	 *         it contains a <code>null</code> value) or from user id and to
	 *         user id are the same or the expiration date is a value in the
	 *         past.
	 */
	@Override
	public SharingEntry addOrUpdateSharingEntry(
			long fromUserId, long toUserId, long classNameId, long classPK,
			long groupId, boolean shareable,
			Collection<SharingEntryAction> sharingEntryActions,
			Date expirationDate, ServiceContext serviceContext)
		throws PortalException {

		SharingEntry sharingEntry = sharingEntryPersistence.fetchByFU_TU_C_C(
			fromUserId, toUserId, classNameId, classPK);

		if (sharingEntry == null) {
			return sharingEntryLocalService.addSharingEntry(
				fromUserId, toUserId, classNameId, classPK, groupId, shareable,
				sharingEntryActions, expirationDate, serviceContext);
		}

		return sharingEntryLocalService.updateSharingEntry(
			sharingEntry.getSharingEntryId(), sharingEntryActions, shareable,
			expirationDate, serviceContext);
	}

	/**
	 * Adds a sharing entry in the database.
	 *
	 * @param  fromUserId the user id sharing the resource
	 * @param  toUserId the user id whose resource was shared
	 * @param  classNameId the class name ID of the resource being shared
	 * @param  classPK the primary key of the resource being shared
	 * @param  groupId the primary key of the group containing the resource
	 *         being shared
	 * @param  shareable whether the to user id can share the resource as well
	 * @param  sharingEntryActions the sharing entry actions
	 * @param  expirationDate the date when the sharing entry expires
	 * @return the sharing entry
	 * @param  serviceContext the service context to be applied
	 * @throws PortalException if there is already a sharing entry for the same
	 *         from user id, to user id and resource or the sharing entry
	 *         actions are invalid (it is empty, it doesn't contain
	 *         {@link SharingEntryAction#VIEW,} or it contains a
	 *         <code>null</code> value) or from user id and to user id are the
	 *         same or the expiration date is a value in the past.
	 */
	@Override
	public SharingEntry addSharingEntry(
			long fromUserId, long toUserId, long classNameId, long classPK,
			long groupId, boolean shareable,
			Collection<SharingEntryAction> sharingEntryActions,
			Date expirationDate, ServiceContext serviceContext)
		throws PortalException {

		_validateSharingEntryActions(sharingEntryActions);

		_validateUsers(fromUserId, toUserId);

		_validateExpirationDate(expirationDate);

		long sharingEntryId = counterLocalService.increment();

		SharingEntry sharingEntry = sharingEntryPersistence.create(
			sharingEntryId);

		sharingEntry.setUuid(serviceContext.getUuid());

		Group group = _groupLocalService.getGroup(groupId);

		sharingEntry.setCompanyId(group.getCompanyId());

		sharingEntry.setGroupId(groupId);
		sharingEntry.setFromUserId(fromUserId);
		sharingEntry.setToUserId(toUserId);
		sharingEntry.setClassNameId(classNameId);
		sharingEntry.setClassPK(classPK);
		sharingEntry.setShareable(shareable);
		sharingEntry.setExpirationDate(expirationDate);

		Stream<SharingEntryAction> sharingEntryActionStream =
			sharingEntryActions.stream();

		sharingEntryActionStream.map(
			SharingEntryAction::getBitwiseValue
		).reduce(
			(bitwiseValue1, bitwiseValue2) -> bitwiseValue1 | bitwiseValue2
		).ifPresent(
			actionIds -> sharingEntry.setActionIds(actionIds)
		);

		SharingEntry newSharingEntry = sharingEntryPersistence.update(
			sharingEntry);

		String className = _portal.getClassName(classNameId);

		Indexer<Object> indexer = _indexerRegistry.getIndexer(className);

		if (indexer != null) {
			indexer.reindex(className, classPK);
		}

		return newSharingEntry;
	}

	/**
	 * Returns the number of sharing entries that have been shared by a user.
	 *
	 * @param  fromUserId the user id sharing the resource
	 * @return the number of sharing entries
	 */
	@Override
	public int countFromUserSharingEntries(long fromUserId) {
		return sharingEntryPersistence.countByFromUserId(fromUserId);
	}

	/**
	 * Returns the number of sharing entries of a resource that have been shared
	 * by a user.
	 *
	 * @param  fromUserId the user id sharing the resource
	 * @param  classNameId the class name ID of the resource
	 * @param  classPK the primary key of the resource
	 * @return the number of sharing entries
	 */
	@Override
	public int countFromUserSharingEntries(
		long fromUserId, long classNameId, long classPK) {

		return sharingEntryPersistence.countByFU_C_C(
			fromUserId, classNameId, classPK);
	}

	/**
	 * Returns the number of sharing entries that have been shared to a user.
	 *
	 * @param  toUserId the user id who was shared the resource
	 * @return the number of sharing entries
	 */
	@Override
	public int countToUserSharingEntries(long toUserId) {
		return sharingEntryPersistence.countByToUserId(toUserId);
	}

	/**
	 * Deletes all sharing entries whose expiration date is before the current
	 * date.
	 */
	@Override
	public void deleteExpiredEntries() {
		sharingEntryPersistence.removeByExpirationDate(DateUtil.newDate());
	}

	/**
	 * Deletes all sharing entries that belong to a group.
	 */
	@Override
	public void deleteGroupSharingEntries(long groupId) {
		List<SharingEntry> sharingEntries =
			sharingEntryPersistence.findByGroupId(groupId);

		for (SharingEntry sharingEntry : sharingEntries) {
			_deleteSharingEntry(sharingEntry);
		}
	}

	/**
	 * Deletes all sharing entries of a resource.
	 *
	 * @param  classNameId the class name ID of the resource
	 * @param  classPK the primary key of the resource
	 */
	@Override
	public void deleteSharingEntries(long classNameId, long classPK) {
		List<SharingEntry> sharingEntries = sharingEntryPersistence.findByC_C(
			classNameId, classPK);

		for (SharingEntry sharingEntry : sharingEntries) {
			_deleteSharingEntry(sharingEntry);
		}
	}

	/**
	 * Deletes the sharing entry of a user to another user for a resource.
	 *
	 * @param  classNameId the class name ID of the resource
	 * @param  classPK the primary key of the resource
	 * @return the deleted sharing entry
	 */
	@Override
	public SharingEntry deleteSharingEntry(
			long fromUserId, long toUserId, long classNameId, long classPK)
		throws PortalException {

		SharingEntry sharingEntry = sharingEntryPersistence.findByFU_TU_C_C(
			fromUserId, toUserId, classNameId, classPK);

		return _deleteSharingEntry(sharingEntry);
	}

	/**
	 * Deletes all sharing entries shared to a user.
	 *
	 * @param  toUserId the user id who was shared the resource
	 */
	@Override
	public void deleteToUserSharingEntries(long toUserId) {
		List<SharingEntry> sharingEntries =
			sharingEntryPersistence.findByToUserId(toUserId);

		for (SharingEntry sharingEntry : sharingEntries) {
			_deleteSharingEntry(sharingEntry);
		}
	}

	/**
	 * Returns a list of all the sharing entries that has been shared by a user.
	 *
	 * @param  fromUserId the user id sharing the resource
	 * @return the list of sharing entries
	 */
	@Override
	public List<SharingEntry> getFromUserSharingEntries(long fromUserId) {
		return sharingEntryPersistence.findByFromUserId(fromUserId);
	}

	/**
	 * Returns a list of all the sharing entries of a resource that has been
	 * shared by a user
	 *
	 * @param  fromUserId the user id sharing the resource
	 * @param  classNameId the class name ID of the resource
	 * @param  classPK the primary key of the resource
	 * @return the list of sharing entries
	 */
	@Override
	public List<SharingEntry> getFromUserSharingEntries(
		long fromUserId, long classNameId, long classPK) {

		return sharingEntryPersistence.findByFU_C_C(
			fromUserId, classNameId, classPK);
	}

	/**
	 * Returns a range of all the sharing entries of a resource that has been
	 * shared by a user
	 *
	 * @param  fromUserId the user id sharing the resource
	 * @param  classNameId the class name ID of the resource
	 * @param  classPK the primary key of the resource
	 * @param  start the lower bound of the range of results
	 * @param  end the upper bound of the range of results (not inclusive)
	 * @return the range of sharing entries
	 */
	@Override
	public List<SharingEntry> getFromUserSharingEntries(
		long fromUserId, long classNameId, long classPK, int start, int end) {

		return sharingEntryPersistence.findByFU_C_C(
			fromUserId, classNameId, classPK, start, end);
	}

	/**
	 * Returns a list of all the sharing entries of a group.
	 *
	 * @param  groupId the primary key of the group
	 * @return the list of sharing entries
	 */
	@Override
	public List<SharingEntry> getGroupSharingEntries(long groupId) {
		return sharingEntryPersistence.findByGroupId(groupId);
	}

	/**
	 * Returns a list of all the sharing entries of a resource.
	 *
	 * @param  classNameId the class name ID of the resource
	 * @param  classPK the primary key of the resource
	 * @return the list of sharing entries
	 */
	@Override
	public List<SharingEntry> getSharingEntries(
		long classNameId, long classPK) {

		return sharingEntryPersistence.findByC_C(classNameId, classPK);
	}

	/**
	 * Returns a list of all the sharing entries of a resource that has been
	 * shared to a user.
	 *
	 * @param  toUserId the user id that has been shared the resource
	 * @param  classNameId the class name ID of the resource
	 * @param  classPK the primary key of the resource
	 * @return the list of sharing entries
	 */
	@Override
	public List<SharingEntry> getSharingEntries(
		long toUserId, long classNameId, long classPK) {

		return sharingEntryPersistence.findByTU_C_C(
			toUserId, classNameId, classPK);
	}

	/**
	 * Returns a list of all the sharing entries that has been shared to a user.
	 *
	 * @param  toUserId the user id that has been shared the resource
	 * @return the range of sharing entries
	 */
	@Override
	public List<SharingEntry> getToUserSharingEntries(long toUserId) {
		return sharingEntryPersistence.findByToUserId(toUserId);
	}

	/**
	 * Returns a range of all the sharing entries that has been shared to a
	 * user.
	 *
	 * @param  toUserId the user id that has been shared the resource
	 * @return the range of sharing entries
	 */
	@Override
	public List<SharingEntry> getToUserSharingEntries(
		long toUserId, int start, int end) {

		return sharingEntryPersistence.findByToUserId(toUserId, start, end);
	}

	/**
	 * Returns a list of sharing entries of a specific class name id that has
	 * been shared to a user.
	 *
	 * @param  toUserId the user id that has been shared the resource
	 * @param  classNameId the class name ID of the shared resource
	 * @return the list of sharing entries
	 */
	@Override
	public List<SharingEntry> getToUserSharingEntries(
		long toUserId, long classNameId) {

		return sharingEntryPersistence.findByTU_C(toUserId, classNameId);
	}

	/**
	 * Returns <code>true</code> if the to user id has been shared a resource
	 * with a sharing entry action and, in addition, he can share the resource
	 * as well.
	 *
	 * @param  toUserId the user id that has been shared the resource
	 * @param  classNameId the class name ID of the shared resource
	 * @param  classPK the primary key of the shared resource
	 * @param  sharingEntryAction the sharing entry action
	 * @return <code>true</code> if the user has been shared a resource with a
	 *         sharing entry action and he can, in additino, share the resource
	 *         as well; <code>false</code> otherwise
	 */
	@Override
	public boolean hasShareableSharingPermission(
		long toUserId, long classNameId, long classPK,
		SharingEntryAction sharingEntryAction) {

		List<SharingEntry> sharingEntries =
			sharingEntryPersistence.findByTU_C_C(
				toUserId, classNameId, classPK);

		for (SharingEntry sharingEntry : sharingEntries) {
			if (!sharingEntry.isShareable()) {
				continue;
			}

			if (hasSharingPermission(sharingEntry, sharingEntryAction)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Returns <code>true</code> if the to user id has been shared a resource
	 * with a sharing entry action
	 *
	 * @param  toUserId the user id that has been shared the resource
	 * @param  classNameId the class name ID of the shared resource
	 * @param  classPK the primary key of the shared resource
	 * @param  sharingEntryAction the sharing entry action
	 * @return <code>true</code> if the user has been shared a resource with a
	 *         sharing entry action; <code>false</code> otherwise
	 */
	@Override
	public boolean hasSharingPermission(
		long toUserId, long classNameId, long classPK,
		SharingEntryAction sharingEntryAction) {

		List<SharingEntry> sharingEntries =
			sharingEntryPersistence.findByTU_C_C(
				toUserId, classNameId, classPK);

		for (SharingEntry sharingEntry : sharingEntries) {
			if (hasSharingPermission(sharingEntry, sharingEntryAction)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Returns <code>true</code> if the sharing entry has certain sharing entry
	 * action
	 *
	 * @param  sharingEntry the sharing entry
	 * @param  sharingEntryAction the sharing entry action
	 * @return <code>true</code> if the sharing entry has the sharing entry
	 *         action; <code>false</code> otherwise
	 */
	@Override
	public boolean hasSharingPermission(
		SharingEntry sharingEntry, SharingEntryAction sharingEntryAction) {

		long actionIds = sharingEntry.getActionIds();

		if ((actionIds & sharingEntryAction.getBitwiseValue()) != 0) {
			return true;
		}

		return false;
	}

	/**
	 * Updates a sharing entry in the database.
	 *
	 * @param  sharingEntryId the primary key of the sharing entry
	 * @param  sharingEntryActions the sharing entry actions
	 * @param  shareable whether the to user id can share the resource as well
	 * @param  expirationDate the date when the sharing entry expires
	 * @return the sharing entry
	 * @param  serviceContext the service context to be applied
	 * @throws PortalException if the sharing entry does not exist or sharing
	 *         entry actions are invalid (it is empty, it doesn't contain
	 *         {@link SharingEntryAction#VIEW,} or it contains a
	 *         <code>null</code> value) or the expiration date is a value in the
	 *         past.
	 */
	@Override
	public SharingEntry updateSharingEntry(
			long sharingEntryId,
			Collection<SharingEntryAction> sharingEntryActions,
			boolean shareable, Date expirationDate,
			ServiceContext serviceContext)
		throws PortalException {

		SharingEntry sharingEntry = sharingEntryPersistence.findByPrimaryKey(
			sharingEntryId);

		_validateSharingEntryActions(sharingEntryActions);

		_validateExpirationDate(expirationDate);

		sharingEntry.setShareable(shareable);
		sharingEntry.setExpirationDate(expirationDate);

		Stream<SharingEntryAction> sharingEntryActionStream =
			sharingEntryActions.stream();

		sharingEntryActionStream.map(
			SharingEntryAction::getBitwiseValue
		).reduce(
			(bitwiseValue1, bitwiseValue2) -> bitwiseValue1 | bitwiseValue2
		).ifPresent(
			actionIds -> sharingEntry.setActionIds(actionIds)
		);

		return sharingEntryPersistence.update(sharingEntry);
	}

	private SharingEntry _deleteSharingEntry(SharingEntry sharingEntry) {
		String className = sharingEntry.getClassName();
		long classPK = sharingEntry.getClassPK();

		SharingEntry deletedSharingEntry = sharingEntryPersistence.remove(
			sharingEntry);

		Indexer<Object> indexer = _indexerRegistry.getIndexer(className);

		if (indexer != null) {
			try {
				indexer.reindex(className, classPK);
			}
			catch (SearchException se) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						StringBundler.concat(
							"Unable to index sharing entry for class name ",
							className, " and primary key ",
							String.valueOf(classPK)),
						se);
				}
			}
		}

		return deletedSharingEntry;
	}

	private void _validateExpirationDate(Date expirationDate)
		throws InvalidSharingEntryExpirationDateException {

		if ((expirationDate != null) &&
			expirationDate.before(DateUtil.newDate())) {

			throw new InvalidSharingEntryExpirationDateException(
				"Expiration date is in the past");
		}
	}

	private void _validateSharingEntryActions(
			Collection<SharingEntryAction> sharedEntryActions)
		throws InvalidSharingEntryActionException {

		if (sharedEntryActions.isEmpty()) {
			throw new InvalidSharingEntryActionException(
				"Shared entry actions is empty");
		}

		for (SharingEntryAction curSharingEntryAction : sharedEntryActions) {
			if (curSharingEntryAction == null) {
				throw new InvalidSharingEntryActionException(
					"Shared entry actions contains a null value");
			}
		}

		if (!sharedEntryActions.contains(SharingEntryAction.VIEW)) {
			throw new InvalidSharingEntryActionException(
				"Shared entry actions must contain VIEW shared entry action");
		}
	}

	private void _validateUsers(long fromUserId, long toUserId)
		throws InvalidSharingEntryUserException {

		if (fromUserId == toUserId) {
			throw new InvalidSharingEntryUserException(
				"From user cannot be the same as to user");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SharingEntryLocalServiceImpl.class);

	@ServiceReference(type = GroupLocalService.class)
	private GroupLocalService _groupLocalService;

	@ServiceReference(type = IndexerRegistry.class)
	private IndexerRegistry _indexerRegistry;

	@ServiceReference(type = Portal.class)
	private Portal _portal;

}