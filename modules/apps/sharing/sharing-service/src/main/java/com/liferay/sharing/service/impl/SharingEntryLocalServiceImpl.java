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
import com.liferay.portal.kernel.util.OrderByComparator;
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
 * Provides the local service for accessing, adding, checking, deleting, and
 * updating sharing entries.
 *
 * <p>
 * This service does not perform any kind of permission checking and assumes
 * that the user has permission to share the resource via any sharing entry
 * action. Permission checks are done in {@link SharingEntryServiceImpl}.
 * </p>
 *
 * <p>
 * This service does, however, perform some validation and integrity checks to
 * ensure that the sharing entries are valid and consistent.
 * </p>
 *
 * @author Sergio González
 */
public class SharingEntryLocalServiceImpl
	extends SharingEntryLocalServiceBaseImpl {

	/**
	 * Adds a new sharing entry in the database or updates an existing one.
	 *
	 * @param  fromUserId the ID of the user sharing the resource
	 * @param  toUserId the ID of the user the resource is shared with
	 * @param  classNameId the resource's class name ID
	 * @param  classPK the class primary key of the resource
	 * @param  groupId the primary key of the resource's group
	 * @param  shareable whether the user specified by {@code toUserId} can
	 *         share the resource
	 * @param  sharingEntryActions the sharing entry actions
	 * @param  expirationDate the date when the sharing entry expires
	 * @param  serviceContext the service context
	 * @return the sharing entry
	 * @throws PortalException if the sharing entry actions are invalid (e.g.,
	 *         empty, don't contain {@code SharingEntryAction#VIEW}, or contain
	 *         a {@code null} value), if the to/from user IDs are the same, or
	 *         if the expiration date is a past value
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
	 * Adds a new sharing entry in the database.
	 *
	 * @param  fromUserId the ID of the user sharing the resource
	 * @param  toUserId the ID of the user the resource is shared with
	 * @param  classNameId the resource's class name ID
	 * @param  classPK the class primary key of the resource
	 * @param  groupId the primary key of the resource's group
	 * @param  shareable whether the user specified by {@code toUserId} can
	 *         share the resource
	 * @param  sharingEntryActions the sharing entry actions
	 * @param  expirationDate the date when the sharing entry expires
	 * @param  serviceContext the service context
	 * @return the sharing entry
	 * @throws PortalException if a sharing entry already exists for the to/from
	 *         user IDs, if the sharing entry actions are invalid (e.g., empty,
	 *         don't contain {@code SharingEntryAction#VIEW}, or contain a
	 *         {@code null} value), if the to/from user IDs are the same, or if
	 *         the expiration date is a past value
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
	 * Deletes the sharing entries whose expiration date is before the current
	 * date.
	 */
	@Override
	public void deleteExpiredEntries() {
		sharingEntryPersistence.removeByExpirationDate(DateUtil.newDate());
	}

	/**
	 * Deletes the group's sharing entries.
	 *
	 * @param groupId the group's ID
	 */
	@Override
	public void deleteGroupSharingEntries(long groupId) {
		List<SharingEntry> sharingEntries =
			sharingEntryPersistence.findByGroupId(groupId);

		for (SharingEntry sharingEntry : sharingEntries) {
			deleteSharingEntry(sharingEntry);
		}
	}

	/**
	 * Deletes the resource's sharing entries. The class name ID and class
	 * primary key identify the resource's type and instance, respectively.
	 *
	 * @param classNameId the resource's class name ID
	 * @param classPK the class primary key of the resource
	 */
	@Override
	public void deleteSharingEntries(long classNameId, long classPK) {
		List<SharingEntry> sharingEntries = sharingEntryPersistence.findByC_C(
			classNameId, classPK);

		for (SharingEntry sharingEntry : sharingEntries) {
			deleteSharingEntry(sharingEntry);
		}
	}

	/**
	 * Deletes the sharing entry that matches the sharing entry ID.
	 *
	 * @param  sharingEntryId the sharing entry's ID
	 * @return the deleted sharing entry
	 */
	@Override
	public SharingEntry deleteSharingEntry(long sharingEntryId)
		throws PortalException {

		SharingEntry sharingEntry = getSharingEntry(sharingEntryId);

		return deleteSharingEntry(sharingEntry);
	}

	/**
	 * Deletes the sharing entry for the resource and users. The class name ID
	 * and class primary key identify the resource's type and instance,
	 * respectively.
	 *
	 * @param  fromUserId the ID of the user sharing the resource
	 * @param  toUserId the ID of the user the resource is shared with
	 * @param  classNameId the resource's class name ID
	 * @param  classPK the class primary key of the resource
	 * @return the deleted sharing entry
	 */
	@Override
	public SharingEntry deleteSharingEntry(
			long fromUserId, long toUserId, long classNameId, long classPK)
		throws PortalException {

		SharingEntry sharingEntry = sharingEntryPersistence.findByFU_TU_C_C(
			fromUserId, toUserId, classNameId, classPK);

		return deleteSharingEntry(sharingEntry);
	}

	/**
	 * Deletes the sharing entry.
	 *
	 * @param  sharingEntry the sharing entry to delete
	 * @return the deleted sharing entry
	 */
	@Override
	public SharingEntry deleteSharingEntry(SharingEntry sharingEntry) {
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

	/**
	 * Deletes the sharing entries for resources shared with the user.
	 *
	 * @param toUserId the user's ID
	 */
	@Override
	public void deleteToUserSharingEntries(long toUserId) {
		List<SharingEntry> sharingEntries =
			sharingEntryPersistence.findByToUserId(toUserId);

		for (SharingEntry sharingEntry : sharingEntries) {
			deleteSharingEntry(sharingEntry);
		}
	}

	/**
	 * Returns the list of sharing entries for resources shared by the user.
	 *
	 * @param  fromUserId the user's ID
	 * @return the list of sharing entries
	 */
	@Override
	public List<SharingEntry> getFromUserSharingEntries(long fromUserId) {
		return sharingEntryPersistence.findByFromUserId(fromUserId);
	}

	/**
	 * Returns the list of sharing entries for the resource shared by the user.
	 * The class name ID and class primary key identify the resource's type and
	 * instance, respectively.
	 *
	 * @param  fromUserId the user's ID
	 * @param  classNameId the resource's class name ID
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
	 * Returns the range of sharing entries for the resource shared by the user.
	 * The class name ID and class primary key identify the resource's type and
	 * instance, respectively.
	 *
	 * @param  fromUserId the user's ID
	 * @param  classNameId the resource's class name ID
	 * @param  classPK the primary key of the resource
	 * @param  start the range's lower bound
	 * @param  end the range's upper bound (not inclusive)
	 * @return the range of sharing entries
	 */
	@Override
	public List<SharingEntry> getFromUserSharingEntries(
		long fromUserId, long classNameId, long classPK, int start, int end) {

		return sharingEntryPersistence.findByFU_C_C(
			fromUserId, classNameId, classPK, start, end);
	}

	/**
	 * Returns the number of sharing entries for resources shared by the user.
	 *
	 * @param  fromUserId the user's ID
	 * @return the number of sharing entries
	 */
	@Override
	public int getFromUserSharingEntriesCount(long fromUserId) {
		return sharingEntryPersistence.countByFromUserId(fromUserId);
	}

	/**
	 * Returns the number of sharing entries for the resource shared by the
	 * user. The class name ID and class primary key identify the resource's
	 * type and instance, respectively.
	 *
	 * @param  fromUserId the user's ID
	 * @param  classNameId the resource's class name ID
	 * @param  classPK the class primary key of the resource
	 * @return the number of sharing entries
	 */
	@Override
	public int getFromUserSharingEntriesCount(
		long fromUserId, long classNameId, long classPK) {

		return sharingEntryPersistence.countByFU_C_C(
			fromUserId, classNameId, classPK);
	}

	/**
	 * Returns the the group's sharing entries.
	 *
	 * @param  groupId the primary key of the group
	 * @return the sharing entries
	 */
	@Override
	public List<SharingEntry> getGroupSharingEntries(long groupId) {
		return sharingEntryPersistence.findByGroupId(groupId);
	}

	/**
	 * Returns the resource's sharing entries. The class name ID and class
	 * primary key identify the resource's type and instance, respectively.
	 *
	 * @param  classNameId the resource's class name ID
	 * @param  classPK the class primary key of the resource
	 * @return the sharing entries
	 */
	@Override
	public List<SharingEntry> getSharingEntries(
		long classNameId, long classPK) {

		return sharingEntryPersistence.findByC_C(classNameId, classPK);
	}

	/**
	 * Returns the sharing entries for the resource shared with the user. The
	 * class name ID and class primary key identify the resource's type and
	 * instance, respectively.
	 *
	 * @param  toUserId the user's ID
	 * @param  classNameId the resource's class name ID
	 * @param  classPK the class primary key of the resource
	 * @return the sharing entries
	 */
	@Override
	public List<SharingEntry> getSharingEntries(
		long toUserId, long classNameId, long classPK) {

		return sharingEntryPersistence.findByTU_C_C(
			toUserId, classNameId, classPK);
	}

	/**
	 * Returns the sharing entries for the resource shared with the user. The
	 * class name ID and class primary key identify the resource's type and
	 * instance, respectively.
	 *
	 * @param  toUserId the user's ID
	 * @param  classNameId the resource's class name ID
	 * @param  classPK the class primary key of the resource
	 * @return the sharing entries
	 */
	@Override
	public List<SharingEntry> getToUserClassPKSharingEntries(
		long toUserId, long classNameId, long classPK) {

		return sharingEntryPersistence.findByTU_C_C(
			toUserId, classNameId, classPK);
	}

	/**
	 * Returns the list of sharing entries for resources shared with the user.
	 *
	 * @param  toUserId the user's ID
	 * @return the list of sharing entries
	 */
	@Override
	public List<SharingEntry> getToUserSharingEntries(long toUserId) {
		return sharingEntryPersistence.findByToUserId(toUserId);
	}

	/**
	 * Returns the range of sharing entries for resources shared with the user.
	 *
	 * @param  toUserId the user's ID
	 * @param  start the range's lower bound
	 * @param  end the range's upper bound (not inclusive)
	 * @return the range of sharing entries
	 */
	@Override
	public List<SharingEntry> getToUserSharingEntries(
		long toUserId, int start, int end) {

		return sharingEntryPersistence.findByToUserId(toUserId, start, end);
	}

	/**
	 * Returns the list of sharing entries for the type of resource shared with
	 * the user. The class name ID identifies the resource type.
	 *
	 * @param  toUserId the user's ID
	 * @param  classNameId the class name ID of the resources
	 * @return the list of sharing entries
	 */
	@Override
	public List<SharingEntry> getToUserSharingEntries(
		long toUserId, long classNameId) {

		return sharingEntryPersistence.findByTU_C(toUserId, classNameId);
	}

	/**
	 * Returns the number of sharing entries for resources shared with the user.
	 *
	 * @param  toUserId the user's ID
	 * @return the number of sharing entries
	 */
	@Override
	public int getToUserSharingEntriesCount(long toUserId) {
		return sharingEntryPersistence.countByToUserId(toUserId);
	}

	/**
	 * Returns the ordered range of sharing entries for the type of resource
	 * shared with the user. Because it's possible for several users to share
	 * the same resource with the user, this method returns only one sharing
	 * entry per resource. The class name ID identifies the resource type.
	 *
	 * @param  toUserId the user's ID
	 * @param  classNameId the class name ID of the resources
	 * @param  start the ordered range's lower bound
	 * @param  end the ordered range's upper bound (not inclusive)
	 * @param  orderByComparator the comparator that orders the sharing entries
	 * @return the ordered range of sharing entries
	 */
	@Override
	public List<SharingEntry> getUniqueToUserSharingEntries(
		long toUserId, long classNameId, int start, int end,
		OrderByComparator<SharingEntry> orderByComparator) {

		return sharingEntryFinder.findByToUserId(
			toUserId, classNameId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of sharing entries for the type of resource shared
	 * with the user. Because it's possible for several users to share the same
	 * resource with the user, this method counts only one sharing entry per
	 * resource. The class name ID identifies the resource type.
	 *
	 * @param  toUserId the user's ID
	 * @param  classNameId the class name ID of the resources
	 * @return the number of sharing entries
	 */
	@Override
	public int getUniqueToUserSharingEntriesCount(
		long toUserId, long classNameId) {

		return sharingEntryFinder.countByToUserId(toUserId, classNameId);
	}

	/**
	 * Returns {@code true} if the resource with the sharing entry action has
	 * been shared with a user who can also share that resource. The class name
	 * ID and class primary key identify the resource's type and instance,
	 * respectively.
	 *
	 * @param  toUserId the user's ID
	 * @param  classNameId the resource's class name ID
	 * @param  classPK the class primary key of the shared resource
	 * @param  sharingEntryAction the sharing entry action
	 * @return {@code true} if the resource with the sharing entry action has
	 *         been shared with a user who can also share that resource; {@code
	 *         false} otherwise
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
	 * Returns {@code true} if the resource with the sharing entry action has
	 * been shared with the user. The class name ID and class primary key
	 * identify the resource's type and instance, respectively.
	 *
	 * @param  toUserId the user's ID
	 * @param  classNameId the resource's class name ID
	 * @param  classPK the class primary key of the shared resource
	 * @param  sharingEntryAction the sharing entry action
	 * @return {@code true} if the resource with the sharing entry action has
	 *         been shared with the user; {@code false} otherwise
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
	 * Returns {@code true} if the sharing entry has the sharing entry action.
	 *
	 * @param  sharingEntry the sharing entry
	 * @param  sharingEntryAction the sharing entry action
	 * @return {@code true} if the sharing entry has the sharing entry action;
	 *         {@code false} otherwise
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
	 * Updates the sharing entry in the database.
	 *
	 * @param  sharingEntryId the primary key of the sharing entry
	 * @param  sharingEntryActions the sharing entry actions
	 * @param  shareable whether the user the resource is shared with can also
	 *         share it
	 * @param  expirationDate the date when the sharing entry expires
	 * @param  serviceContext the service context
	 * @return the sharing entry
	 * @throws PortalException if the sharing entry does not exist, if the
	 *         sharing entry actions are invalid (e.g., empty, don't contain
	 *         {@code SharingEntryAction#VIEW}, or contain a {@code null}
	 *         value), or if the expiration date is a past value
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