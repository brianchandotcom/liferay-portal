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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.asset.model.AssetCategoryConstants;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * <p>
 * This class is intended to handle the settings associated to one asset
 * vocabulary model instead of handling the properties using generic
 * UnicodeProperties class.
 * </p>
 *
 * @author José Manuel Navarro
 */
public class AssetVocabularySettingsModelHelper extends UnicodeProperties {

	public static final long[] DEFAULT_SELECTED_CLASSNAME_IDS =
		new long[] {AssetCategoryConstants.ALL_CLASS_NAME_IDS};

	public AssetVocabularySettingsModelHelper() {
		super(true);
	}

	public AssetVocabularySettingsModelHelper(String properties) {
		this();

		fastLoad(properties);
	}

	public long[] getClassNameIds() {
		String propertyValue = getProperty(SELECTED_CLASS_NAME_IDS);

		if (Validator.isNull(propertyValue)) {
			return DEFAULT_SELECTED_CLASSNAME_IDS;
		}

		return StringUtil.split(propertyValue, 0L);
	}

	public long[] getRequiredClassNameIds() {
		String propertyValue = getProperty(REQUIRED_CLASS_NAME_IDS);

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
		String propertyValue = getProperty(MULTI_VALUED);

		return GetterUtil.getBoolean(propertyValue, true);
	}

	public void setClassNameIds(long[] classNameIds, boolean[] areRequired) {

		Set<Long> selectedClassNameIds = new LinkedHashSet<Long>();
		Set<Long> requiredClassNameIds = new LinkedHashSet<Long>();

		for (int i = 0; i < classNameIds.length; ++i) {
			long classNameId = classNameIds[i];
			boolean required = areRequired[i];

			if (classNameId == AssetCategoryConstants.ALL_CLASS_NAME_IDS) {
				selectedClassNameIds.clear();
				selectedClassNameIds.add(classNameId);

				if (required) {
					requiredClassNameIds.clear();
					requiredClassNameIds.add(classNameId);
				}

				break;
			}
			else {
				selectedClassNameIds.add(classNameId);

				if (required) {
					requiredClassNameIds.add(classNameId);
				}
			}
		}

		setProperty(
			SELECTED_CLASS_NAME_IDS, StringUtil.merge(selectedClassNameIds));
		setProperty(
			REQUIRED_CLASS_NAME_IDS, StringUtil.merge(requiredClassNameIds));
	}

	public void setMultiValued(boolean multiValued) {
		setProperty(MULTI_VALUED, String.valueOf(multiValued));
	}

	protected boolean isClassNameIdSpecified(
		long classNameId, long[] classNameIds) {

		if (classNameIds.length == 0) {
			return false;
		}

		if ((classNameIds[0] !=
				AssetCategoryConstants.ALL_CLASS_NAME_IDS) &&
			!ArrayUtil.contains(classNameIds, classNameId)) {

			return false;
		}

		return true;
	}

	private static final String MULTI_VALUED = "multiValued";

	private static final String REQUIRED_CLASS_NAME_IDS =
		"requiredClassNameIds";

	private static final String SELECTED_CLASS_NAME_IDS =
		"selectedClassNameIds";

}