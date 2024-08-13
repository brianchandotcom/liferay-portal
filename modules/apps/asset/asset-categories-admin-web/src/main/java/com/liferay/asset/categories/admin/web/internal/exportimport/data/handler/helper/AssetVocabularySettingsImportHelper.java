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
		String[] classNameIdsAndClassTypePKs, boolean required) {

		for (String classNameIdAndClassTypePK : classNameIdsAndClassTypePKs) {
			long oldClassNameId = getClassNameId(classNameIdAndClassTypePK);

			if (!_existClassName(oldClassNameId)) {
				continue;
			}

			long newClassNameId = _getNewClassNameId(oldClassNameId);

			_classNameIds.add(_getNewClassNameId(oldClassNameId));

			long oldClassTypePK = getClassTypePK(classNameIdAndClassTypePK);

			_classTypePKs.add(
				_getNewClassTypePK(
					oldClassNameId, newClassNameId, oldClassTypePK));

			_requireds.add(required);
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
		_fillClassNameIdsAndClassTypePKs(
			getClassNameIdsAndClassTypePKs(), false);

		_fillClassNameIdsAndClassTypePKs(
			getRequiredClassNameIdsAndClassTypePKs(), true);

		setClassNameIdsAndClassTypePKs(
			ArrayUtil.toLongArray(_classNameIds),
			ArrayUtil.toLongArray(_classTypePKs),
			ArrayUtil.toBooleanArray(_requireds));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetVocabularySettingsImportHelper.class);

	private final List<Long> _classNameIds = new ArrayList<>();
	private final ClassNameLocalService _classNameLocalService;
	private final List<Long> _classTypePKs = new ArrayList<>();
	private final long[] _groupIds;
	private final Locale _locale;
	private final List<Boolean> _requireds = new ArrayList<>();
	private final JSONObject _settingsMetadataJSONObject;

}