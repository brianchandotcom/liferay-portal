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

package com.liferay.portlet.dynamicdatamapping.registry.settings;

import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFieldTypeSetting;
import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFieldTypeSettingEditor;
import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFieldTypeSettingJSONConverter;
import com.liferay.portlet.dynamicdatamapping.registry.StringDDMFormFieldTypeSettingJSONConverter;

/**
 * @author Marcellus Tavares
 */
public class DataTypeDDMFormFieldTypeSetting
	implements DDMFormFieldTypeSetting {

	@Override
	public DDMFormFieldTypeSettingEditor getDDMFormFieldTypeSettingEditor() {
		throw new UnsupportedOperationException();
	}

	@Override
	public DDMFormFieldTypeSettingJSONConverter<String, String>
		getDDMFormFieldTypeSettingJSONConverter() {

		return new StringDDMFormFieldTypeSettingJSONConverter();
	}

	@Override
	public String getName() {
		return "dataType";
	}

	@Override
	public boolean isAdvanced() {
		return true;
	}

	@Override
	public boolean isVisible() {
		return false;
	}

}