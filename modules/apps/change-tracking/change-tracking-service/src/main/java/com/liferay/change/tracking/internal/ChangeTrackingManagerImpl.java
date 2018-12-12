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

package com.liferay.change.tracking.internal;

import com.liferay.change.tracking.ChangeTrackingManager;
import com.liferay.change.tracking.constants.ChangeTrackingConstants;
import com.liferay.change.tracking.constants.ChangeTrackingPortletKeys;
import com.liferay.change.tracking.model.ChangeTrackingCollection;
import com.liferay.change.tracking.model.ChangeTrackingEntry;
import com.liferay.change.tracking.service.ChangeTrackingCollectionLocalService;
import com.liferay.change.tracking.service.ChangeTrackingEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 */
@Component(immediate = true, service = ChangeTrackingManager.class)
public class ChangeTrackingManagerImpl implements ChangeTrackingManager {

	@Override
	public void checkoutChangeTrackingCollection(
		long companyId, long userId, long collectionId) {

		if (!isChangeTrackingEnabled(companyId)) {
			return;
		}

		Optional<ChangeTrackingCollection> collectionOptional =
			getChangeTrackingCollection(collectionId);

		if (!collectionOptional.isPresent()) {
			_log.error(
				"Unable to checkout change collection with id " + collectionId);

			return;
		}

		try {
			TransactionInvokerUtil.invoke(
				_transactionConfig,
				() -> {
					_updateUserCollection(userId, collectionId);

					return null;
				});
		}
		catch (Throwable t) {
			_log.error("Unable to change user's active collection set", t);
		}
	}

	@Override
	public Optional<ChangeTrackingCollection> createChangeTrackingCollection(
		long companyId, long userId, String name, String description) {

		if (ChangeTrackingConstants.PRODUCTION_CHANGE_COLLECTION_NAME.equals(
				name) ||
			!isChangeTrackingEnabled(companyId)) {

			return Optional.empty();
		}

		return _createCollection(companyId, userId, name, description);
	}

	@Override
	public void deleteChangeTrackingCollection(long userId, long collectionId) {
		Optional<ChangeTrackingCollection> collectionOptional =
			getChangeTrackingCollection(collectionId);

		if (!collectionOptional.isPresent()) {
			_log.error(
				"Unable to delete change collection with id " + collectionId);

			return;
		}

		_changeTrackingCollectionLocalService.deleteChangeTrackingCollection(
			collectionOptional.get());
	}

	@Override
	public void disableChangeTracking(long companyId, long userId) {
		if (!isChangeTrackingEnabled(companyId)) {
			return;
		}

		try {
			TransactionInvokerUtil.invoke(
				_transactionConfig,
				() -> {
					_changeTrackingCollectionLocalService.
						deleteCompanyChangeTrackingCollections(companyId);

					_productionCollection = null;

					return null;
				});
		}
		catch (Throwable t) {
			_log.error("Unable to disable change tracking", t);
		}
	}

	@Override
	public void enableChangeTracking(long companyId, long userId) {
		if (isChangeTrackingEnabled(companyId)) {
			return;
		}

		try {
			TransactionInvokerUtil.invoke(
				_transactionConfig,
				() -> {
					Optional<ChangeTrackingCollection> collectionOptional =
						_createCollection(
							companyId, userId,
							ChangeTrackingConstants.
								PRODUCTION_CHANGE_COLLECTION_NAME,
							StringPool.BLANK);

					collectionOptional.ifPresent(
						collection -> {
							_productionCollection = collectionOptional.orElse(
								null);

							checkoutChangeTrackingCollection(
								companyId, userId,
								collection.getChangeTrackingCollectionId());
						});

					return null;
				});
		}
		catch (Throwable t) {
			_log.error("Unable to enable change tracking", t);
		}
	}

	@Override
	public Optional<ChangeTrackingCollection> getActiveChangeTrackingCollection(
		long companyId, long userId) {

		if (!isChangeTrackingEnabled(companyId)) {
			return Optional.empty();
		}

		long recentCollectionId = _getUserCollectionId(userId);

		if (recentCollectionId == 0L) {
			Optional<ChangeTrackingCollection> productionCollectionOptional =
				getProductionChangeTrackingCollection(companyId);

			recentCollectionId = productionCollectionOptional.map(
				ChangeTrackingCollection::getChangeTrackingCollectionId
			).orElse(
				0L
			);

			checkoutChangeTrackingCollection(
				companyId, userId, recentCollectionId);
		}

		return getChangeTrackingCollection(recentCollectionId);
	}

	@Override
	public Optional<ChangeTrackingCollection> getChangeTrackingCollection(
		long collectionId) {

		ChangeTrackingCollection collection =
			_changeTrackingCollectionLocalService.fetchChangeTrackingCollection(
				collectionId);

		return Optional.ofNullable(collection);
	}

	@Override
	public List<ChangeTrackingCollection> getChangeTrackingCollections(
		long companyId) {

		if (!isChangeTrackingEnabled(companyId)) {
			return Collections.emptyList();
		}

		return _changeTrackingCollectionLocalService.
			getChangeTrackingCollections(companyId);
	}

	@Override
	public List<ChangeTrackingEntry> getChangeTrackingEntries(
		long collectionId) {

		return _changeTrackingEntryLocalService.
			getChangeTrackingCollectionChangeTrackingEntries(collectionId);
	}

	public Optional<ChangeTrackingCollection>
		getProductionChangeTrackingCollection(long companyId) {

		if (_productionCollection == null) {
			_productionCollection =
				_changeTrackingCollectionLocalService.
					fetchChangeTrackingCollection(
						companyId,
						ChangeTrackingConstants.
							PRODUCTION_CHANGE_COLLECTION_NAME);
		}

		return Optional.ofNullable(_productionCollection);
	}

	@Override
	public boolean isChangeTrackingEnabled(long companyId) {
		Optional<ChangeTrackingCollection> productionCollection =
			getProductionChangeTrackingCollection(companyId);

		return productionCollection.isPresent();
	}

	@Override
	public boolean isChangeTrackingSupported(
		long companyId, Class<BaseModel> clazz) {

		return false;
	}

	@Override
	public void publishChangeTrackingCollection(
		long companyId, long userId, long collectionId) {

		if (!isChangeTrackingEnabled(companyId)) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to publish change collection because change " +
						"tracking is not enabled in company " + companyId);
			}

			return;
		}

		List<ChangeTrackingEntry> entries = getChangeTrackingEntries(
			collectionId);

		if (ListUtil.isEmpty(entries)) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to find any change entries with change " +
						"collection id " + collectionId);
			}

			return;
		}

		try {
			TransactionInvokerUtil.invoke(
				_transactionConfig,
				() -> {
					_publishChangeEntries(
						companyId, userId, collectionId, entries);

					return null;
				});
		}
		catch (Throwable t) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to publish change entries to change collection " +
						collectionId,
					t);
			}
		}
	}

	private void _copyEntriesFromProduction(
		ChangeTrackingCollection collection) {

		Optional<ChangeTrackingCollection> productionCollectionOptional =
			getProductionChangeTrackingCollection(collection.getCompanyId());

		List<ChangeTrackingEntry> productionEntries =
			productionCollectionOptional.map(
				ChangeTrackingCollection::getChangeTrackingCollectionId
			).map(
				this::getChangeTrackingEntries
			).orElse(
				Collections.emptyList()
			);

		for (ChangeTrackingEntry entry : productionEntries) {
			_changeTrackingCollectionLocalService.
				addChangeTrackingEntryChangeTrackingCollection(
					entry.getChangeTrackingEntryId(), collection);
		}
	}

	private Optional<ChangeTrackingCollection> _createCollection(
		long companyId, long userId, String name, String description) {

		ChangeTrackingCollection collection = null;

		try {
			collection = TransactionInvokerUtil.invoke(
				_transactionConfig,
				() -> {
					ChangeTrackingCollection changeTrackingCollection =
						_changeTrackingCollectionLocalService.
							addChangeTrackingCollection(
								companyId, userId, name, description,
								new ServiceContext());

					_copyEntriesFromProduction(changeTrackingCollection);

					return changeTrackingCollection;
				});
		}
		catch (Throwable t) {
			_log.error(
				"Unable to create change collection with name " + name, t);
		}

		return Optional.ofNullable(collection);
	}

	private long _getUserCollectionId(long userId) {
		User user = _userLocalService.fetchUser(userId);

		if (user == null) {
			_log.error("Unable to find user with id " + userId);

			return 0L;
		}

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(
				userId, !user.isDefaultUser());

		return GetterUtil.getLong(
			portalPreferences.getValue(
				ChangeTrackingPortletKeys.CHANGE_LISTS,
				_RECENT_CHANGE_COLLECTION_ID));
	}

	private void _publishChangeEntries(
		long companyId, long userId, long collectionId,
		List<ChangeTrackingEntry> entries) {

		Optional<ChangeTrackingCollection> productionCollectionOptional =
			getProductionChangeTrackingCollection(companyId);

		if (!productionCollectionOptional.isPresent()) {
			return;
		}

		long productionCollectionId = productionCollectionOptional.map(
			ChangeTrackingCollection::getChangeTrackingCollectionId
		).get();

		_changeTrackingEntryLocalService.
			addChangeTrackingCollectionChangeTrackingEntries(
				productionCollectionId, entries);

		Optional<ChangeTrackingCollection> changeTrackingCollectionOptional =
			getChangeTrackingCollection(collectionId);

		if (!changeTrackingCollectionOptional.isPresent()) {
			return;
		}

		try {
			_changeTrackingCollectionLocalService.updateStatus(
				userId, changeTrackingCollectionOptional.get(),
				WorkflowConstants.STATUS_APPROVED, new ServiceContext());
		}
		catch (PortalException pe) {
			_log.error(
				"Unable to update the status of the published change " +
					"collection",
				pe);
		}
	}

	private void _updateUserCollection(long userId, long collectionId) {
		User user = _userLocalService.fetchUser(userId);

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(
				userId, !user.isDefaultUser());

		portalPreferences.setValue(
			ChangeTrackingPortletKeys.CHANGE_LISTS,
			_RECENT_CHANGE_COLLECTION_ID, String.valueOf(collectionId));
	}

	private static final String _RECENT_CHANGE_COLLECTION_ID =
		"recentChangeCollectionId";

	private static final Log _log = LogFactoryUtil.getLog(
		ChangeTrackingManagerImpl.class);

	@Reference
	private ChangeTrackingCollectionLocalService
		_changeTrackingCollectionLocalService;

	@Reference
	private ChangeTrackingEntryLocalService _changeTrackingEntryLocalService;

	private ChangeTrackingCollection _productionCollection;
	private final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@Reference
	private UserLocalService _userLocalService;

}