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

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portlet.dynamicdatamapping.model.LocalizedValue;
import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFieldTypeSetting;
import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFieldTypeSettingEditor;
import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFieldTypeSettingJSONConverter;
import com.liferay.portlet.dynamicdatamapping.registry.LocalizedValueDDMFormFieldTypeSettingJSONConverter;

/**
 * @author Marcellus Tavares
 */
public class TipDDMFormFieldTypeSetting implements DDMFormFieldTypeSetting {

	@Override
	public DDMFormFieldTypeSettingEditor getDDMFormFieldTypeSettingEditor() {
		return new DDMFormFieldTypeSettingEditor() {

			@Override
			public String getEditorType() {
				return "Text";
			}

			public JSONObject getOptions() {
				JSONObject options = JSONFactoryUtil.createJSONObject();

				options.put("label", "help-text");

				return options;
			}
		};
	}

	@Override
	public DDMFormFieldTypeSettingJSONConverter<LocalizedValue, JSONObject>
		getDDMFormFieldTypeSettingJSONConverter() {

		return new LocalizedValueDDMFormFieldTypeSettingJSONConverter();
	}

	@Override
	public String getName() {
		return "tip";
	}

	@Override
	public boolean isAdvanced() {
		return false;
	}

	@Override
	public boolean isVisible() {
		return true;
	}

}