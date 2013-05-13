/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.model.impl;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Juan Fernández
 */
public class AssetVocabularyImpl extends AssetVocabularyBaseImpl {

	public AssetVocabularyImpl() {
	}

	@Override
	public List<AssetCategory> getCategories() throws SystemException {
		return AssetCategoryLocalServiceUtil.getVocabularyCategories(
			getVocabularyId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	@Override
	public String getSettings() {
		if (_settingsProperties == null) {
			return super.getSettings();
		}
		else {
			return _settingsProperties.toString();
		}
	}

	@Override
	public UnicodeProperties getSettingsProperties() {
		if (_settingsProperties == null) {
			_settingsProperties = new UnicodeProperties(true);

			_settingsProperties.fastLoad(super.getSettings());
		}

		return _settingsProperties;
	}

	@Override
	public String getTitle(String languageId) {
		String value = super.getTitle(languageId);

		if (Validator.isNull(value)) {
			value = getName();
		}

		return value;
	}

	@Override
	public String getTitle(String languageId, boolean useDefault) {
		String value = super.getTitle(languageId, useDefault);

		if (Validator.isNull(value)) {
			value = getName();
		}

		return value;
	}

	@Override
	public boolean isMultiValued() {
		if (Validator.isNull(_settingsProperties)) {
			_settingsProperties = getSettingsProperties();
		}

		return GetterUtil.getBoolean(
			_settingsProperties.getProperty("multiValued"), true);
	}

	@Override
	public boolean isRequired(long classNameId) {
		if (Validator.isNull(_settingsProperties)) {
			_settingsProperties = getSettingsProperties();
		}

		long[] requiredClassNameIds = StringUtil.split(
			_settingsProperties.getProperty("requiredClassNameIds"), 0L);

		return ArrayUtil.contains(requiredClassNameIds, classNameId);
	}

	@Override
	public void setSettings(String settings) {
		_settingsProperties = null;

		super.setSettings(settings);
	}

	@Override
	public void setSettingsProperties(UnicodeProperties settingsProperties) {
		_settingsProperties = settingsProperties;

		super.setSettings(settingsProperties.toString());
	}

	private UnicodeProperties _settingsProperties;

}