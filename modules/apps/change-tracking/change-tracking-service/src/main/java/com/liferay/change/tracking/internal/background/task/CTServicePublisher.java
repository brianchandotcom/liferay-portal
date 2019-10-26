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

package com.liferay.change.tracking.internal.background.task;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.petra.lang.SafeClosable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Preston Crary
 */
public class CTServicePublisher<T extends CTModel<T>> {

	public CTServicePublisher(
		CTEntryLocalService ctEntryLocalService, CTService<T> ctService,
		long sourceCTCollectionId, long targetCTCollectionId) {

		_ctEntryLocalService = ctEntryLocalService;
		_ctService = ctService;
		_sourceCTCollectionId = sourceCTCollectionId;
		_targetCTCollectionId = targetCTCollectionId;
	}

	public void addCTEntry(CTEntry ctEntry) {
		long modelClassPK = ctEntry.getModelClassPK();
		int changeType = ctEntry.getChangeType();

		if (changeType == CTConstants.CT_CHANGE_TYPE_ADDITION) {
			if (_additionCTEntries == null) {
				_additionCTEntries = new HashMap<>();
			}

			_additionCTEntries.put(modelClassPK, ctEntry);
		}
		else if (changeType == CTConstants.CT_CHANGE_TYPE_DELETION) {
			if (_deletionCTEntries == null) {
				_deletionCTEntries = new HashMap<>();
			}

			_deletionCTEntries.put(modelClassPK, ctEntry);
		}
		else {
			if (_modificationCTEntries == null) {
				_modificationCTEntries = new HashMap<>();
			}

			_modificationCTEntries.put(modelClassPK, ctEntry);
		}
	}

	public void publish() {
		_ctService.updateWithUnsafeFunction(this::_publish);
	}

	private static void _flushAndClear(CTPersistence<?> ctPersistence) {
		Session session = ctPersistence.getCurrentSession();

		session.flush();
		session.clear();
	}

	private static <T extends CTModel<T>> void _updateCTCollectionId(
		CTPersistence<T> ctPersistence, long ctCollectionId, T ctModel) {

		ctModel.setCtCollectionId(ctCollectionId);

		ctModel = ctPersistence.updateCTModel(ctModel, true);

		ctPersistence.clearCache(ctModel);
	}

	private void _checkModificationSize(
		Map<Serializable, CTEntry> ctEntries, Map<Serializable, T> ctModels) {

		if (ctEntries.size() != ctModels.size()) {
			throw new SystemException(
				StringBundler.concat(
					"Size mismatch between ", ctEntries, " and ", ctModels,
					" for ", _ctService.getModelClass()));
		}
	}

	private Void _publish(CTPersistence<T> ctPersistence) {

		// Order matters to avoid causing constraint violations

		if (_deletionCTEntries != null) {
			try (SafeClosable safeClosable =
					CTCollectionThreadLocal.setCTCollectionId(
						_targetCTCollectionId)) {

				Map<Serializable, T> ctModels =
					ctPersistence.fetchByPrimaryKeys(
						_deletionCTEntries.keySet());

				for (Map.Entry<Serializable, T> entry : ctModels.entrySet()) {
					Serializable primaryKey = entry.getKey();

					CTEntry ctEntry = _deletionCTEntries.get(primaryKey);

					T ctModel = entry.getValue();

					long mvccVersion = ctModel.getMvccVersion();

					if (mvccVersion != ctEntry.getModelMvccVersion()) {
						throw new SystemException(
							StringBundler.concat(
								"MVCC version mismatch between ", ctEntry,
								" and ", ctModel, " for ",
								_ctService.getModelClass()));
					}

					_updateCTCollectionId(
						ctPersistence, _sourceCTCollectionId, ctModel);

					ctEntry.setModelMvccVersion(mvccVersion + 1);

					_deletionCTEntries.put(
						primaryKey,
						_ctEntryLocalService.updateCTEntry(ctEntry));
				}

				_flushAndClear(ctPersistence);
			}
		}

		if (_modificationCTEntries != null) {
			Map<Serializable, T> sourceCTModels = null;

			long tempCTCollectionId = -_sourceCTCollectionId;

			try (SafeClosable safeClosable =
					CTCollectionThreadLocal.setCTCollectionId(
						_sourceCTCollectionId)) {

				sourceCTModels = ctPersistence.fetchByPrimaryKeys(
					_modificationCTEntries.keySet());

				_checkModificationSize(_modificationCTEntries, sourceCTModels);

				for (T ctModel : sourceCTModels.values()) {
					_updateCTCollectionId(
						ctPersistence, tempCTCollectionId, ctModel);
				}

				_flushAndClear(ctPersistence);
			}

			try (SafeClosable safeClosable =
					CTCollectionThreadLocal.setCTCollectionId(
						_targetCTCollectionId)) {

				Map<Serializable, T> ctModels =
					ctPersistence.fetchByPrimaryKeys(
						_modificationCTEntries.keySet());

				_checkModificationSize(_modificationCTEntries, ctModels);

				for (Map.Entry<Serializable, T> entry : ctModels.entrySet()) {
					Serializable primaryKey = entry.getKey();

					CTEntry ctEntry = _modificationCTEntries.get(primaryKey);

					T ctModel = entry.getValue();

					long mvccVersion = ctModel.getMvccVersion();

					if (mvccVersion != ctEntry.getModelMvccVersion()) {
						throw new SystemException(
							StringBundler.concat(
								"MVCC version mismatch between ", ctEntry,
								" and ", ctModel, " for ",
								_ctService.getModelClass()));
					}

					_updateCTCollectionId(
						ctPersistence, _sourceCTCollectionId, ctModel);

					ctEntry.setModelMvccVersion(mvccVersion + 1);

					_modificationCTEntries.put(
						primaryKey,
						_ctEntryLocalService.updateCTEntry(ctEntry));
				}

				_flushAndClear(ctPersistence);
			}

			try (SafeClosable safeClosable =
					CTCollectionThreadLocal.setCTCollectionId(
						tempCTCollectionId)) {

				for (T ctModel : sourceCTModels.values()) {
					_updateCTCollectionId(
						ctPersistence, _targetCTCollectionId, ctModel);
				}

				_flushAndClear(ctPersistence);
			}
		}

		if (_additionCTEntries != null) {
			try (SafeClosable safeClosable =
					CTCollectionThreadLocal.setCTCollectionId(
						_sourceCTCollectionId)) {

				Map<Serializable, T> ctModels =
					ctPersistence.fetchByPrimaryKeys(
						_additionCTEntries.keySet());

				for (T ctModel : ctModels.values()) {
					_updateCTCollectionId(
						ctPersistence, _targetCTCollectionId, ctModel);
				}

				_flushAndClear(ctPersistence);
			}
		}

		return null;
	}

	private Map<Serializable, CTEntry> _additionCTEntries;
	private final CTEntryLocalService _ctEntryLocalService;
	private final CTService<T> _ctService;
	private Map<Serializable, CTEntry> _deletionCTEntries;
	private Map<Serializable, CTEntry> _modificationCTEntries;
	private final long _sourceCTCollectionId;
	private final long _targetCTCollectionId;

}