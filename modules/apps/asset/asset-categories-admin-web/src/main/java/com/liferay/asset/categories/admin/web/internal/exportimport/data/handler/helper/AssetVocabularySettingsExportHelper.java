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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.asset.util.AssetVocabularySettingsHelper;

import java.util.Locale;

/**
 * @author Rafael Praxedes
 */
public class AssetVocabularySettingsExportHelper
	extends AssetVocabularySettingsHelper {

	public AssetVocabularySettingsExportHelper(
		String settings, JSONFactory jsonFactory, Locale locale) {

		super(settings);

		_jsonFactory = jsonFactory;
		_locale = locale;
	}

	public String getSettingsMetadata() throws PortalException {
		JSONObject settingsMetadataJSONObject =
			_createSettingsMetadataJSONObject();

		return settingsMetadataJSONObject.toString();
	}

	protected String getSettings() {
		return super.toString();
	}

	private JSONObject _createSettingsMetadataJSONObject()
		throws PortalException {

		JSONObject settingsMetadataJSONObject = _jsonFactory.createJSONObject();

		if (Validator.isNotNull(getSettings())) {
			String[] classNameIdsAndClassTypePKs = ArrayUtil.append(
				getRequiredClassNameIdsAndClassTypePKs(),
				getClassNameIdsAndClassTypePKs());

			for (String classNameIdAndClassTypePK :
					classNameIdsAndClassTypePKs) {

				long classNameId = getClassNameId(classNameIdAndClassTypePK);

				if (classNameId == AssetCategoryConstants.ALL_CLASS_NAME_ID) {
					continue;
				}

				_putClassTypeJSONObject(
					_getClassTypeJSONObject(
						settingsMetadataJSONObject, classNameId),
					classNameId, getClassTypePK(classNameIdAndClassTypePK));
			}
		}

		return settingsMetadataJSONObject;
	}

	private JSONObject _getClassTypeJSONObject(
		JSONObject settingsMetadataJSONObject, long classNameId) {

		JSONObject classTypeJSONObject = null;

		JSONObject metadataJSONObject =
			settingsMetadataJSONObject.getJSONObject(
				String.valueOf(classNameId));

		if (metadataJSONObject != null) {
			classTypeJSONObject = metadataJSONObject.getJSONObject(
				"classTypes");
		}
		else {
			metadataJSONObject = _jsonFactory.createJSONObject();

			settingsMetadataJSONObject.put(
				String.valueOf(classNameId), metadataJSONObject);

			metadataJSONObject.put(
				"className", PortalUtil.getClassName(classNameId));

			classTypeJSONObject = _jsonFactory.createJSONObject();

			metadataJSONObject.put("classTypes", classTypeJSONObject);
		}

		return classTypeJSONObject;
	}

	private void _putClassTypeJSONObject(
			JSONObject classTypeJSONObject, long classNameId, long classTypePK)
		throws PortalException {

		if (classTypePK == AssetCategoryConstants.ALL_CLASS_TYPE_PK) {
			return;
		}

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.
				getAssetRendererFactoryByClassNameId(classNameId);

		ClassTypeReader classTypeReader =
			assetRendererFactory.getClassTypeReader();

		ClassType classType = classTypeReader.getClassType(
			classTypePK, _locale);

		classTypeJSONObject.put(
			String.valueOf(classTypePK), classType.getName());
	}

	private final JSONFactory _jsonFactory;
	private final Locale _locale;

}