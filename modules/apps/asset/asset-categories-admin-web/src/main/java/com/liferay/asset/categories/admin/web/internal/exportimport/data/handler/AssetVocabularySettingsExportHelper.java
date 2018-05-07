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

package com.liferay.asset.categories.admin.web.internal.exportimport.data.handler;

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
			createSettingsMetadataJSONObject();

		return settingsMetadataJSONObject.toJSONString();
	}

	protected JSONObject createSettingsMetadataJSONObject()
		throws PortalException {

		JSONObject settingsMetadataJSONObject = _jsonFactory.createJSONObject();

		String settings = getSettings();

		if (Validator.isNotNull(settings)) {
			String[] classNameIdsAndClassTypePKs = ArrayUtil.append(
				getRequiredClassNameIdsAndClassTypePKs(),
				getClassNameIdsAndClassTypePKs());

			for (String classNameIdAndClassTypePK :
					classNameIdsAndClassTypePKs) {

				long classNameId = getClassNameId(classNameIdAndClassTypePK);

				if (classNameId == AssetCategoryConstants.ALL_CLASS_NAME_ID) {
					continue;
				}

				long classTypePK = getClassTypePK(classNameIdAndClassTypePK);

				JSONObject classTypeJSONObject = getClassTypeJSONObject(
					settingsMetadataJSONObject, classNameId);

				putClassTypeJSONObject(
					classTypeJSONObject, classNameId, classTypePK);
			}
		}

		return settingsMetadataJSONObject;
	}

	protected JSONObject getClassTypeJSONObject(
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

	protected String getSettings() {
		return super.toString();
	}

	protected void putClassTypeJSONObject(
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