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

package com.liferay.portlet.dynamicdatamapping.registry.properties;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFieldTypeProperty;
import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFieldTypePropertyEditor;
import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFieldTypePropertyJSONTransformer;
import com.liferay.portlet.dynamicdatamapping.registry.LocalizedValueDDMFormFieldTypePropertyJSONTransformer;

/**
 * @author Marcellus Tavares
 */
public class PredefinedValueDDMFormFieldTypeProperty
	implements DDMFormFieldTypeProperty {

	@Override
	public DDMFormFieldTypePropertyEditor getDDMFormFieldTypePropertyEditor() {
		return new DDMFormFieldTypePropertyEditor() {

			@Override
			public String getEditorType() {
				return "Text";
			}

			@Override
			public JSONObject getOptions() {
				JSONObject options = JSONFactoryUtil.createJSONObject();

				options.put("label", "predefined-value");

				return options;
			}

		};
	}

	@Override
	public DDMFormFieldTypePropertyJSONTransformer
		getDDMFormFieldTypePropertyJSONTransformer() {

		return new LocalizedValueDDMFormFieldTypePropertyJSONTransformer();
	}

	@Override
	public String getName() {
		return "predefinedValue";
	}

	@Override
	public boolean isAdvanced() {
		return true;
	}

	@Override
	public boolean isVisible() {
		return true;
	}

}