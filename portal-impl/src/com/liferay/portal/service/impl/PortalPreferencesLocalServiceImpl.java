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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.internal.service.util.PortalPreferencesCacheUtil;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.PortalPreferenceValue;
import com.liferay.portal.kernel.model.PortalPreferences;
import com.liferay.portal.kernel.model.PortletConstants;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.PortalPreferenceValueLocalService;
import com.liferay.portal.kernel.service.persistence.PortalPreferenceValuePersistence;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.base.PortalPreferencesLocalServiceBaseImpl;
import com.liferay.portlet.PortalPreferenceKey;
import com.liferay.portlet.PortalPreferencesImpl;
import com.liferay.portlet.PortalPreferencesWrapper;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.portlet.PortletPreferences;

/**
 * @author Alexander Chow
 */
public class PortalPreferencesLocalServiceImpl
	extends PortalPreferencesLocalServiceBaseImpl {

	@Override
	public PortalPreferences addPortalPreferences(
		long ownerId, int ownerType, String defaultPreferences) {

		PortalPreferences previousPortalPreferences =
			portalPreferencesPersistence.fetchByO_O(ownerId, ownerType);

		if (previousPortalPreferences != null) {
			throw new IllegalArgumentException(
				"Duplicate owner ID and owner type exists in " +
					previousPortalPreferences);
		}

		long portalPreferencesId = counterLocalService.increment();

		PortalPreferences portalPreferences =
			portalPreferencesPersistence.create(portalPreferencesId);

		portalPreferences.setOwnerId(ownerId);
		portalPreferences.setOwnerType(ownerType);

		if (Validator.isNull(defaultPreferences)) {
			defaultPreferences = PortletConstants.DEFAULT_PREFERENCES;
		}

		if (Objects.equals(
				PortletConstants.DEFAULT_PREFERENCES, defaultPreferences)) {

			PortalPreferencesCacheUtil.put(
				portalPreferencesId, Collections.emptyMap());
		}
		else {
			PortalPreferencesImpl portalPreferencesImpl =
				(PortalPreferencesImpl)PortletPreferencesFactoryUtil.fromXML(
					ownerId, ownerType, defaultPreferences);

			_updatePortalPreferences(
				portalPreferences, Collections.emptyMap(),
				portalPreferencesImpl.getPreferences());
		}

		try {
			portalPreferences = portalPreferencesPersistence.update(
				portalPreferences);
		}
		catch (SystemException systemException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Add failed, fetch {ownerId=", ownerId, ", ownerType=",
						ownerType, "}"));
			}

			portalPreferences = portalPreferencesPersistence.fetchByO_O(
				ownerId, ownerType, false);

			if (portalPreferences == null) {
				throw systemException;
			}
		}

		return portalPreferences;
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	public PortalPreferences deletePortalPreferences(long portalPreferencesId)
		throws PortalException {

		_portalPreferenceValuePersistence.removeByPortalPreferencesId(
			portalPreferencesId);

		return super.deletePortalPreferences(portalPreferencesId);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	public PortalPreferences deletePortalPreferences(
		PortalPreferences portalPreferences) {

		_portalPreferenceValuePersistence.removeByPortalPreferencesId(
			portalPreferences.getPortalPreferencesId());

		return super.deletePortalPreferences(portalPreferences);
	}

	@Override
	public PortalPreferences fetchPortalPreferences(
		long ownerId, int ownerType) {

		return portalPreferencesPersistence.fetchByO_O(ownerId, ownerType);
	}

	@Override
	public PortletPreferences getPreferences(long ownerId, int ownerType) {
		return getPreferences(ownerId, ownerType, null);
	}

	@Override
	public PortletPreferences getPreferences(
		long ownerId, int ownerType, String defaultPreferences) {

		PortalPreferences portalPreferences =
			portalPreferencesPersistence.fetchByO_O(ownerId, ownerType);

		if (portalPreferences == null) {
			portalPreferences =
				portalPreferencesLocalService.addPortalPreferences(
					ownerId, ownerType, defaultPreferences);
		}

		PortalPreferencesImpl portalPreferencesImpl =
			(PortalPreferencesImpl)
				_portalPreferenceValueLocalService.getPortalPreferences(
					portalPreferences, false);

		return new PortalPreferencesWrapper(portalPreferencesImpl);
	}

	@Override
	public PortalPreferences updatePreferences(
		long ownerId, int ownerType,
		com.liferay.portal.kernel.portlet.PortalPreferences portalPreferences) {

		PortalPreferencesImpl portalPreferencesImpl =
			(PortalPreferencesImpl)portalPreferences;

		return _updatePortalPreferences(
			ownerId, ownerType, portalPreferencesImpl.getPreferences());
	}

	@Override
	public PortalPreferences updatePreferences(
		long ownerId, int ownerType, String xml) {

		PortalPreferencesImpl portalPreferencesImpl =
			(PortalPreferencesImpl)PortletPreferencesFactoryUtil.fromXML(
				ownerId, ownerType, xml);

		return _updatePortalPreferences(
			ownerId, ownerType, portalPreferencesImpl.getPreferences());
	}

	private PortalPreferences _updatePortalPreferences(
		long ownerId, int ownerType,
		Map<PortalPreferenceKey, String[]> preferencesMap) {

		PortalPreferences portalPreferencesModel =
			portalPreferencesPersistence.fetchByO_O(ownerId, ownerType);

		Map<PortalPreferenceKey, List<PortalPreferenceValue>>
			portalPreferenceValuesMap = Collections.emptyMap();

		if (portalPreferencesModel == null) {
			long portalPreferencesId = counterLocalService.increment();

			portalPreferencesModel = portalPreferencesPersistence.create(
				portalPreferencesId);

			portalPreferencesModel.setOwnerId(ownerId);
			portalPreferencesModel.setOwnerType(ownerType);

			portalPreferencesModel = portalPreferencesPersistence.update(
				portalPreferencesModel);
		}
		else {
			portalPreferenceValuesMap =
				PortalPreferenceValueLocalServiceImpl.
					getPortalPreferenceValuesMap(
						_portalPreferenceValuePersistence,
						portalPreferencesModel.getPortalPreferencesId(), true);
		}

		_updatePortalPreferences(
			portalPreferencesModel, portalPreferenceValuesMap, preferencesMap);

		return portalPreferencesModel;
	}

	private void _updatePortalPreferences(
		PortalPreferences portalPreferences,
		Map<PortalPreferenceKey, List<PortalPreferenceValue>>
			portalPreferenceValueMap,
		Map<PortalPreferenceKey, String[]> preferencesMap) {

		List<Map.Entry<List<PortalPreferenceValue>, PortalPreference>>
			preferenceEntries = new ArrayList<>(preferencesMap.size());

		int newCount = 0;

		for (Map.Entry<PortalPreferenceKey, String[]> entry :
				preferencesMap.entrySet()) {

			String[] values = entry.getValue();

			if (values == null) {
				continue;
			}

			int size = 0;

			List<PortalPreferenceValue> portalPreferenceValues =
				portalPreferenceValueMap.remove(entry.getKey());

			if (portalPreferenceValues != null) {
				size = portalPreferenceValues.size();
			}

			if (values.length > size) {
				newCount += values.length - size;
			}

			preferenceEntries.add(
				new AbstractMap.SimpleImmutableEntry<>(
					portalPreferenceValues,
					new PortalPreference(entry.getKey(), values)));
		}

		for (List<PortalPreferenceValue> portalPreferenceValues :
				portalPreferenceValueMap.values()) {

			for (PortalPreferenceValue portalPreferenceValue :
					portalPreferenceValues) {

				_portalPreferenceValuePersistence.remove(portalPreferenceValue);
			}
		}

		long batchCounter = 0;

		if (newCount > 0) {
			batchCounter = counterLocalService.increment(
				PortalPreferenceValue.class.getName(), newCount);

			batchCounter -= newCount;
		}

		for (Map.Entry<List<PortalPreferenceValue>, PortalPreference> entry :
				preferenceEntries) {

			List<PortalPreferenceValue> portalPreferenceValues = entry.getKey();

			PortalPreference portalPreference = entry.getValue();

			PortalPreferenceKey portalPreferenceKey =
				portalPreference._portalPreferenceKey;
			String[] newValues = portalPreference._values;

			int oldSize = 0;

			if (portalPreferenceValues != null) {
				oldSize = portalPreferenceValues.size();
			}

			for (int i = 0; i < newValues.length; i++) {
				String value = newValues[i];

				if (oldSize > i) {
					PortalPreferenceValue portalPreferenceValue =
						portalPreferenceValues.get(i);

					if (!Objects.equals(
							newValues[i], portalPreferenceValue.getValue())) {

						portalPreferenceValue.setValue(value);

						_portalPreferenceValuePersistence.update(
							portalPreferenceValue);
					}
				}
				else {
					PortalPreferenceValue portalPreferenceValue =
						_portalPreferenceValuePersistence.create(
							++batchCounter);

					portalPreferenceValue.setPortalPreferencesId(
						portalPreferences.getPortalPreferencesId());
					portalPreferenceValue.setIndex(i);
					portalPreferenceValue.setKey(portalPreferenceKey.getKey());
					portalPreferenceValue.setNamespace(
						portalPreferenceKey.getNamespace());
					portalPreferenceValue.setValue(value);

					_portalPreferenceValuePersistence.update(
						portalPreferenceValue);
				}
			}

			for (int i = newValues.length; i < oldSize; i++) {
				_portalPreferenceValuePersistence.remove(
					portalPreferenceValues.get(i));
			}
		}

		PortalPreferencesCacheUtil.put(
			portalPreferences.getPortalPreferencesId(), preferencesMap);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortalPreferencesLocalServiceImpl.class);

	@BeanReference(type = PortalPreferenceValueLocalService.class)
	private PortalPreferenceValueLocalService
		_portalPreferenceValueLocalService;

	@BeanReference(type = PortalPreferenceValuePersistence.class)
	private PortalPreferenceValuePersistence _portalPreferenceValuePersistence;

	private static class PortalPreference {

		private PortalPreference(
			PortalPreferenceKey portalPreferenceKey, String[] values) {

			_portalPreferenceKey = portalPreferenceKey;
			_values = values;
		}

		private final PortalPreferenceKey _portalPreferenceKey;
		private final String[] _values;

	}

}