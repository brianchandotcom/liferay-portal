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

	private void _moveCTEntries(
		CTPersistence<T> ctPersistence, Map<Serializable, CTEntry> ctEntries,
		long fromCTCollectionId, long toCTCollectionId) {

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(fromCTCollectionId)) {

			Map<Serializable, T> ctModels = ctPersistence.fetchByPrimaryKeys(
				ctEntries.keySet());

			if ((ctEntries == _modificationCTEntries) &&
				(ctEntries.size() != ctModels.size())) {

				throw new SystemException(
					StringBundler.concat(
						"Size mismatch between ", ctEntries, " and ", ctModels,
						" for ", _ctService.getModelClass()));
			}

			for (Map.Entry<Serializable, T> entry : ctModels.entrySet()) {
				Serializable primaryKey = entry.getKey();

				CTEntry ctEntry = ctEntries.get(primaryKey);

				T ctModel = entry.getValue();

				long mvccVersion = ctModel.getMvccVersion();

				if ((fromCTCollectionId == _targetCTCollectionId) &&
					(mvccVersion != ctEntry.getModelMvccVersion())) {

					throw new SystemException(
						StringBundler.concat(
							"MVCC version mismatch between ", ctEntry, " and ",
							ctModel, " for ", _ctService.getModelClass()));
				}

				ctModel.setCtCollectionId(toCTCollectionId);

				ctModel = ctPersistence.updateCTModel(ctModel, true);

				ctPersistence.clearCache(ctModel);

				if (toCTCollectionId == _sourceCTCollectionId) {
					ctEntry.setModelMvccVersion(mvccVersion + 1);

					ctEntries.put(
						primaryKey,
						_ctEntryLocalService.updateCTEntry(ctEntry));
				}
			}

			Session session = ctPersistence.getCurrentSession();

			session.flush();
			session.clear();
		}
	}

	private Void _publish(CTPersistence<T> ctPersistence) {

		// Order matters to avoid causing constraint violations

		if (_deletionCTEntries != null) {
			_moveCTEntries(
				ctPersistence, _deletionCTEntries, _targetCTCollectionId,
				_sourceCTCollectionId);
		}

		if (_modificationCTEntries != null) {
			long tempCTCollectionId = -_sourceCTCollectionId;

			_moveCTEntries(
				ctPersistence, _modificationCTEntries, _sourceCTCollectionId,
				tempCTCollectionId);

			_moveCTEntries(
				ctPersistence, _modificationCTEntries, _targetCTCollectionId,
				_sourceCTCollectionId);

			_moveCTEntries(
				ctPersistence, _modificationCTEntries, tempCTCollectionId,
				_targetCTCollectionId);
		}

		if (_additionCTEntries != null) {
			_moveCTEntries(
				ctPersistence, _additionCTEntries, _sourceCTCollectionId,
				_targetCTCollectionId);
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