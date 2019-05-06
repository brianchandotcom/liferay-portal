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

package com.liferay.expando.kernel.util;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.portal.kernel.util.UnicodeProperties;

/**
 * @author     Raymond Augé
 * @author     Drew Brokke
 * @deprecated As of Mueller (7.2.x), with no direct replacement
 */
@Deprecated
public class ExpandoPresetUtil {

	public static int addPresetExpando(
			ExpandoBridge expandoBridge, String preset, String name)
		throws Exception {

		int type = 0;

		UnicodeProperties properties = null;

		try {
			properties = expandoBridge.getAttributeProperties(name);
		}
		catch (Exception e) {
			properties = new UnicodeProperties();
		}

		if (preset.equals("PresetSelectionIntegerArray()")) {
			type = ExpandoColumnConstants.INTEGER_ARRAY;

			properties.setProperty(
				ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE,
				ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_SELECTION_LIST);
		}
		else if (preset.equals("PresetSelectionDoubleArray()")) {
			type = ExpandoColumnConstants.DOUBLE_ARRAY;

			properties.setProperty(
				ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE,
				ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_SELECTION_LIST);
		}
		else if (preset.equals("PresetSelectionStringArray()")) {
			type = ExpandoColumnConstants.STRING_ARRAY;

			properties.setProperty(
				ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE,
				ExpandoColumnConstants.PROPERTY_DISPLAY_TYPE_SELECTION_LIST);
		}
		else if (preset.equals("PresetTextBox()")) {
			type = ExpandoColumnConstants.STRING;

			properties.setProperty(
				ExpandoColumnConstants.PROPERTY_HEIGHT, "105");
			properties.setProperty(
				ExpandoColumnConstants.PROPERTY_WIDTH, "450");
		}
		else if (preset.equals("PresetTextBoxIndexed()")) {
			type = ExpandoColumnConstants.STRING;

			properties.setProperty(
				ExpandoColumnConstants.PROPERTY_HEIGHT, "105");
			properties.setProperty(
				ExpandoColumnConstants.PROPERTY_WIDTH, "450");
			properties.setProperty(
				ExpandoColumnConstants.INDEX_TYPE,
				String.valueOf(ExpandoColumnConstants.INDEX_TYPE_TEXT));
		}
		else if (preset.equals("PresetTextFieldSecret()")) {
			type = ExpandoColumnConstants.STRING;

			properties.setProperty(
				ExpandoColumnConstants.PROPERTY_SECRET,
				Boolean.TRUE.toString());
		}
		else {
			type = ExpandoColumnConstants.STRING;

			properties.setProperty(
				ExpandoColumnConstants.INDEX_TYPE,
				String.valueOf(ExpandoColumnConstants.INDEX_TYPE_TEXT));
		}

		expandoBridge.addAttribute(name, type);

		expandoBridge.setAttributeProperties(name, properties);

		return type;
	}

}