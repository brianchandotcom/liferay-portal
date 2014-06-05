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

package com.liferay.portlet.asset.util;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.asset.model.AssetCategoryConstants;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author José Manuel Navarro
 */
public class AssetVocabularySettingsHelper {

	public static final long[] DEFAULT_SELECTED_CLASSNAME_IDS =
		{AssetCategoryConstants.ALL_CLASS_NAME_IDS};

	public AssetVocabularySettingsHelper() {
		super();

		_properties = new UnicodeProperties(true);
	}

	public AssetVocabularySettingsHelper(String propertiesString) {
		this();

		_properties.fastLoad(propertiesString);
	}

	public long[] getClassNameIds() {
		String propertyValue = _properties.getProperty(
			_KEY_SELECTED_CLASS_NAME_AND_TYPE_IDS);

		if (Validator.isNull(propertyValue)) {
			return DEFAULT_SELECTED_CLASSNAME_IDS;
		}

		return StringUtil.split(propertyValue, 0L);
	}

	public long[] getRequiredClassNameIds() {
		String propertyValue = _properties.getProperty(
			_KEY_REQUIRED_CLASS_NAME_AND_TYPE_IDS);

		if (Validator.isNull(propertyValue)) {
			return new long[0];
		}

		return StringUtil.split(propertyValue, 0L);
	}

	public boolean hasClassNameId(long classNameId) {
		return isClassNameIdSpecified(classNameId, getClassNameIds());
	}

	public boolean isClassNameIdRequired(long classNameId) {
		return isClassNameIdSpecified(classNameId, getRequiredClassNameIds());
	}

	public boolean isMultiValued() {
		String value = _properties.getProperty(_KEY_MULTI_VALUED);

		return GetterUtil.getBoolean(value, true);
	}

	public void setClassNameAndTypeIds(
		long[] classNameIds, long[] classTypeIds, boolean[] requireds) {

		Set<String> requiredClassNameIds = new LinkedHashSet<String>();
		Set<String> selectedClassNameIds = new LinkedHashSet<String>();

		for (int i = 0; i < classNameIds.length; ++i) {
			long classNameId = classNameIds[i];
			long classTypeId = classTypeIds[i];
			boolean required = requireds[i];

			String classNameAndTypeId = getClassNameAndTypeId(
				classNameId, classTypeId);

			if (classNameAndTypeId.equals(
					AssetCategoryConstants.ALL_CLASS_NAMES_AND_TYPES_ID)) {

				if (required) {
					requiredClassNameIds.clear();

					requiredClassNameIds.add(classNameAndTypeId);
				}

				selectedClassNameIds.clear();

				selectedClassNameIds.add(classNameAndTypeId);

				break;
			}
			else {
				if (required) {
					requiredClassNameIds.add(classNameAndTypeId);
				}

				selectedClassNameIds.add(classNameAndTypeId);
			}
		}

		_properties.setProperty(
			_KEY_REQUIRED_CLASS_NAME_AND_TYPE_IDS,
			StringUtil.merge(requiredClassNameIds));
		_properties.setProperty(
			_KEY_SELECTED_CLASS_NAME_AND_TYPE_IDS,
			StringUtil.merge(selectedClassNameIds));
	}

	public void setMultiValued(boolean multiValued) {
		_properties.setProperty(_KEY_MULTI_VALUED, String.valueOf(multiValued));
	}

	@Override
	public String toString() {
		return _properties.toString();
	}

	protected boolean isClassNameIdSpecified(
		long classNameId, long[] classNameIds) {

		if (classNameIds.length == 0) {
			return false;
		}

		if (classNameIds[0] == AssetCategoryConstants.ALL_CLASS_NAME_IDS) {
			return true;
		}

		return ArrayUtil.contains(classNameIds, classNameId);
	}

	protected String getClassNameAndTypeId(long classNameId, long classTypeId) {
		return String.valueOf(classNameId).concat(StringPool.COLON).concat(
			String.valueOf(classTypeId));
	}

	private static final String _KEY_MULTI_VALUED = "multiValued";

	private static final String _KEY_REQUIRED_CLASS_NAME_AND_TYPE_IDS =
		"requiredClassNameIds";

	private static final String _KEY_SELECTED_CLASS_NAME_AND_TYPE_IDS =
		"selectedClassNameIds";

	private UnicodeProperties _properties;

}