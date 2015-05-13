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

package com.liferay.portlet.dynamicdatamapping.registry;

import com.liferay.portlet.dynamicdatamapping.registry.properties.DataTypeDDMFormFieldTypeProperty;
import com.liferay.portlet.dynamicdatamapping.registry.properties.IndexTypeDDMFormFieldTypeProperty;
import com.liferay.portlet.dynamicdatamapping.registry.properties.LabelDDMFormFieldTypeProperty;
import com.liferay.portlet.dynamicdatamapping.registry.properties.LocalizableDDMFormFieldTypeProperty;
import com.liferay.portlet.dynamicdatamapping.registry.properties.NameDDMFormFieldTypeProperty;
import com.liferay.portlet.dynamicdatamapping.registry.properties.PredefinedValueDDMFormFieldTypeProperty;
import com.liferay.portlet.dynamicdatamapping.registry.properties.ReadOnlyDDMFormFieldTypeProperty;
import com.liferay.portlet.dynamicdatamapping.registry.properties.RepeatableDDMFormFieldTypeProperty;
import com.liferay.portlet.dynamicdatamapping.registry.properties.RequiredDDMFormFieldTypeProperty;
import com.liferay.portlet.dynamicdatamapping.registry.properties.ShowLabelDDMFormFieldTypeProperty;
import com.liferay.portlet.dynamicdatamapping.registry.properties.TipDDMFormFieldTypeProperty;
import com.liferay.portlet.dynamicdatamapping.registry.properties.TypeDDMFormFieldTypeProperty;

import java.util.Locale;

/**
 * @author Marcellus Tavares
 */
public interface DDMFormFieldType {

	public static final DDMFormFieldTypeProperty[] REQUIRED_PROPERTIES = {
		new DataTypeDDMFormFieldTypeProperty(),
		new IndexTypeDDMFormFieldTypeProperty(),
		new LabelDDMFormFieldTypeProperty(),
		new LocalizableDDMFormFieldTypeProperty(),
		new NameDDMFormFieldTypeProperty(),
		new PredefinedValueDDMFormFieldTypeProperty(),
		new ReadOnlyDDMFormFieldTypeProperty(),
		new RepeatableDDMFormFieldTypeProperty(),
		new RequiredDDMFormFieldTypeProperty(),
		new ShowLabelDDMFormFieldTypeProperty(),
		new TipDDMFormFieldTypeProperty(), new TypeDDMFormFieldTypeProperty()
	};

	public DDMFormFieldRenderer getDDMFormFieldRenderer();

	public DDMFormFieldValueAccessor<?> getDDMFormFieldValueAccessor(
		Locale locale);

	public DDMFormFieldValueParameterSerializer
		getDDMFormFieldValueParameterSerializer();

	public DDMFormFieldValueRendererAccessor
		getDDMFormFieldValueRendererAccessor(Locale locale);

	public String getName();

	public DDMFormFieldTypeProperty[] getOptionalProperties();

}