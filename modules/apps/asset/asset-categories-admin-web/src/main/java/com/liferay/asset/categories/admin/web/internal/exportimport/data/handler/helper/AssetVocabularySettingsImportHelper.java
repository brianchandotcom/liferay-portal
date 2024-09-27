/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.categories.admin.web.internal.exportimport.data.handler.helper;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetCategoryConstants;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.ClassType;
import com.liferay.asset.kernel.model.ClassTypeReader;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portlet.asset.util.AssetVocabularySettingsHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Rafael Praxedes
 */
public class AssetVocabularySettingsImportHelper
	extends AssetVocabularySettingsHelper {

	public AssetVocabularySettingsImportHelper(
		String settings, ClassNameLocalService classNameLocalService,
		long[] groupIds, Locale locale, JSONObject settingsMetadataJSONObject) {

		super(settings);

		_classNameLocalService = classNameLocalService;
		_groupIds = groupIds;
		_locale = locale;
		_settingsMetadataJSONObject = settingsMetadataJSONObject;

		_updateSettings();
	}

	public String getSettings() {
		return super.toString();
	}

	private boolean _existClassName(long classNameId) {
		if (classNameId == AssetCategoryConstants.ALL_CLASS_NAME_ID) {
			return false;
		}

		JSONObject metadataJSONObject = _getMetadataJSONObject(classNameId);

		String className = metadataJSONObject.getString("className");

		if (_classNameLocalService.fetchClassName(className) != null) {
			return true;
		}

		if (_log.isWarnEnabled()) {
			_log.warn("No class name found for " + className);
		}

		return false;
	}

	private void _fillClassNameIdsAndClassTypePKs(
		String[] classNameIdsAndClassTypePKs, List<Long> classNameIds,
		List<Long> classTypePKs, boolean depotRequired,
		List<Boolean> depotRequireds, boolean required,
		List<Boolean> requireds) {

		for (String classNameIdAndClassTypePK : classNameIdsAndClassTypePKs) {
			long oldClassNameId = getClassNameId(classNameIdAndClassTypePK);

			if (!_existClassName(oldClassNameId)) {
				continue;
			}

			long newClassNameId = _getNewClassNameId(oldClassNameId);

			classNameIds.add(_getNewClassNameId(oldClassNameId));

			long oldClassTypePK = getClassTypePK(classNameIdAndClassTypePK);

			classTypePKs.add(
				_getNewClassTypePK(
					oldClassNameId, newClassNameId, oldClassTypePK));

			depotRequireds.add(depotRequired);
			requireds.add(required);
		}
	}

	private JSONObject _getMetadataJSONObject(long classNameId) {
		return _settingsMetadataJSONObject.getJSONObject(
			String.valueOf(classNameId));
	}

	private long _getNewClassNameId(long classNameId) {
		if (classNameId == AssetCategoryConstants.ALL_CLASS_NAME_ID) {
			return AssetCategoryConstants.ALL_CLASS_NAME_ID;
		}

		JSONObject metadataJSONObject = _getMetadataJSONObject(classNameId);

		String className = metadataJSONObject.getString("className");

		return _classNameLocalService.getClassNameId(className);
	}

	private long _getNewClassTypePK(
		long oldClassNameId, long newClassNameId, long oldClassTypePK) {

		if (oldClassTypePK == AssetCategoryConstants.ALL_CLASS_TYPE_PK) {
			return AssetCategoryConstants.ALL_CLASS_TYPE_PK;
		}

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.
				getAssetRendererFactoryByClassNameId(newClassNameId);

		ClassTypeReader classTypeReader =
			assetRendererFactory.getClassTypeReader();

		List<ClassType> availableClassTypes =
			classTypeReader.getAvailableClassTypes(_groupIds, _locale);

		JSONObject metadataJSONObject = _getMetadataJSONObject(oldClassNameId);

		JSONObject classTypesJSONObject = metadataJSONObject.getJSONObject(
			"classTypes");

		String classTypeName = classTypesJSONObject.getString(
			String.valueOf(oldClassTypePK));

		for (ClassType classType : availableClassTypes) {
			String curClassTypeName = classType.getName();

			if (curClassTypeName.equals(classTypeName)) {
				return classType.getClassTypeId();
			}
		}

		if (_log.isWarnEnabled()) {
			_log.warn("No class type found for " + classTypeName);
		}

		return AssetCategoryConstants.ALL_CLASS_TYPE_PK;
	}

	private void _updateSettings() {
		List<Long> classNameIds = new ArrayList<>();
		List<Long> classTypePKs = new ArrayList<>();
		List<Boolean> depotRequireds = new ArrayList<>();
		List<Boolean> requireds = new ArrayList<>();

		_fillClassNameIdsAndClassTypePKs(
			getClassNameIdsAndClassTypePKs(), classNameIds, classTypePKs, false,
			depotRequireds, false, requireds);

		_fillClassNameIdsAndClassTypePKs(
			getDepotRequiredClassNameIdsAndClassTypePKs(), classNameIds,
			classTypePKs, true, depotRequireds, false, requireds);

		_fillClassNameIdsAndClassTypePKs(
			getRequiredClassNameIdsAndClassTypePKs(), classNameIds,
			classTypePKs, false, depotRequireds, true, requireds);

		setClassNameIdsAndClassTypePKs(
			ArrayUtil.toLongArray(classNameIds),
			ArrayUtil.toLongArray(classTypePKs),
			ArrayUtil.toBooleanArray(depotRequireds),
			ArrayUtil.toBooleanArray(requireds));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetVocabularySettingsImportHelper.class);

	private final ClassNameLocalService _classNameLocalService;
	private final long[] _groupIds;
	private final Locale _locale;
	private final JSONObject _settingsMetadataJSONObject;

}