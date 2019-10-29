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

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;

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

	private static <T extends CTModel<T>> boolean _handleIgnorableChanges(
		CTPersistence<T> ctPersistence, Map<Serializable, T> sourceCTModels,
		T targetCTModel) {

		if (sourceCTModels == null) {
			return false;
		}

		T sourceCTModel = sourceCTModels.get(targetCTModel.getPrimaryKey());

		Map<String, Function<T, Object>> attributeGetterFunctions =
			sourceCTModel.getAttributeGetterFunctions();

		for (Map.Entry<String, Function<T, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();

			if (_ctControlColumnNames.contains(attributeName)) {
				continue;
			}

			Function<T, Object> function = entry.getValue();

			Object sourceValue = function.apply(sourceCTModel);
			Object targetValue = function.apply(targetCTModel);

			if (Objects.equals(sourceValue, targetValue)) {
				continue;
			}

			Set<String> ctIgnoredAttributeNames =
				ctPersistence.getCTIgnoredAttributeNames();

			if (ctIgnoredAttributeNames.contains(attributeName)) {
				Map<String, BiConsumer<T, Object>> attributeSetterBiConsumers =
					sourceCTModel.getAttributeSetterBiConsumers();

				BiConsumer<T, Object> biConsumer =
					attributeSetterBiConsumers.get(attributeName);

				biConsumer.accept(sourceCTModel, targetValue);

				continue;
			}

			Set<String> ctMergeableAttributeNames =
				ctPersistence.getCTMergeableAttributeNames();

			if (ctMergeableAttributeNames.contains(attributeName)) {
				continue;
			}

			return false;
		}

		return true;
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

		Map<Serializable, T> addedCTModels = null;
		Map<Serializable, T> mergedCTModels = null;

		long tempCTCollectionId = -_sourceCTCollectionId;

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(
					_sourceCTCollectionId)) {

			if (_additionCTEntries != null) {
				addedCTModels = ctPersistence.fetchByPrimaryKeys(
					_additionCTEntries.keySet());

				for (T ctModel : addedCTModels.values()) {
					_updateCTCollectionId(
						ctPersistence, tempCTCollectionId, ctModel);
				}
			}

			if (_modificationCTEntries != null) {
				mergedCTModels = ctPersistence.fetchByPrimaryKeys(
					_modificationCTEntries.keySet());

				_checkModificationSize(_modificationCTEntries, mergedCTModels);

				for (T ctModel : mergedCTModels.values()) {
					_updateCTCollectionId(
						ctPersistence, tempCTCollectionId, ctModel);
				}
			}

			_flushAndClear(ctPersistence);
		}

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(
					_targetCTCollectionId)) {

			if (_deletionCTEntries != null) {
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

					_ctEntryLocalService.updateCTEntry(ctEntry);
				}
			}

			if (_modificationCTEntries != null) {
				Map<Serializable, T> ctModels =
					ctPersistence.fetchByPrimaryKeys(
						_modificationCTEntries.keySet());

				_checkModificationSize(_modificationCTEntries, ctModels);

				for (Map.Entry<Serializable, T> entry : ctModels.entrySet()) {
					Serializable primaryKey = entry.getKey();

					CTEntry ctEntry = _modificationCTEntries.get(primaryKey);

					T ctModel = entry.getValue();

					long mvccVersion = ctModel.getMvccVersion();

					if (!_handleIgnorableChanges(
							ctPersistence, mergedCTModels, ctModel) &&
						(mvccVersion != ctEntry.getModelMvccVersion())) {

						throw new SystemException(
							StringBundler.concat(
								"MVCC version mismatch between ", ctEntry,
								" and ", ctModel, " for ",
								_ctService.getModelClass()));
					}

					_updateCTCollectionId(
						ctPersistence, _sourceCTCollectionId, ctModel);

					ctEntry.setModelMvccVersion(mvccVersion + 1);

					_ctEntryLocalService.updateCTEntry(ctEntry);
				}
			}

			_flushAndClear(ctPersistence);
		}

		try (SafeClosable safeClosable =
				CTCollectionThreadLocal.setCTCollectionId(tempCTCollectionId)) {

			if (addedCTModels != null) {
				for (Map.Entry<Serializable, T> entry :
						addedCTModels.entrySet()) {

					T ctModel = entry.getValue();

					long mvccVersion = ctModel.getMvccVersion();

					_updateCTCollectionId(
						ctPersistence, _targetCTCollectionId, ctModel);

					CTEntry ctEntry = _additionCTEntries.get(entry.getKey());

					ctEntry.setModelMvccVersion(mvccVersion + 1);

					_ctEntryLocalService.updateCTEntry(ctEntry);
				}
			}

			if (mergedCTModels != null) {
				for (T ctModel : mergedCTModels.values()) {
					_updateCTCollectionId(
						ctPersistence, _targetCTCollectionId, ctModel);
				}
			}

			_flushAndClear(ctPersistence);
		}

		return null;
	}

	private static final Set<String> _ctControlColumnNames = new HashSet<>(
		Arrays.asList("ctCollectionId", "mvccVersion"));

	private Map<Serializable, CTEntry> _additionCTEntries;
	private final CTEntryLocalService _ctEntryLocalService;
	private final CTService<T> _ctService;
	private Map<Serializable, CTEntry> _deletionCTEntries;
	private Map<Serializable, CTEntry> _modificationCTEntries;
	private final long _sourceCTCollectionId;
	private final long _targetCTCollectionId;

}