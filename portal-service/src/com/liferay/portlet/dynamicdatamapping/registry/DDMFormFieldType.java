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

import com.liferay.portlet.dynamicdatamapping.registry.settings.DataTypeDDMFormFieldTypeSetting;
import com.liferay.portlet.dynamicdatamapping.registry.settings.IndexTypeDDMFormFieldTypeSetting;
import com.liferay.portlet.dynamicdatamapping.registry.settings.LabelDDMFormFieldTypeSetting;
import com.liferay.portlet.dynamicdatamapping.registry.settings.LocalizableDDMFormFieldTypeSetting;
import com.liferay.portlet.dynamicdatamapping.registry.settings.NameDDMFormFieldTypeSetting;
import com.liferay.portlet.dynamicdatamapping.registry.settings.PredefinedValueDDMFormFieldTypeSetting;
import com.liferay.portlet.dynamicdatamapping.registry.settings.ReadOnlyDDMFormFieldTypeSetting;
import com.liferay.portlet.dynamicdatamapping.registry.settings.RepeatableDDMFormFieldTypeSetting;
import com.liferay.portlet.dynamicdatamapping.registry.settings.RequiredDDMFormFieldTypeSetting;
import com.liferay.portlet.dynamicdatamapping.registry.settings.ShowLabelDDMFormFieldTypeSetting;
import com.liferay.portlet.dynamicdatamapping.registry.settings.TipDDMFormFieldTypeSetting;
import com.liferay.portlet.dynamicdatamapping.registry.settings.TypeDDMFormFieldTypeSetting;

import java.util.Locale;

/**
 * @author Marcellus Tavares
 */
public interface DDMFormFieldType {

	public static final DDMFormFieldTypeSetting[] REQUIRED_PROPERTIES = {
		new DataTypeDDMFormFieldTypeSetting(),
		new IndexTypeDDMFormFieldTypeSetting(),
		new LabelDDMFormFieldTypeSetting(),
		new LocalizableDDMFormFieldTypeSetting(),
		new NameDDMFormFieldTypeSetting(),
		new PredefinedValueDDMFormFieldTypeSetting(),
		new ReadOnlyDDMFormFieldTypeSetting(),
		new RepeatableDDMFormFieldTypeSetting(),
		new RequiredDDMFormFieldTypeSetting(),
		new ShowLabelDDMFormFieldTypeSetting(),
		new TipDDMFormFieldTypeSetting(), new TypeDDMFormFieldTypeSetting()
	};

	public DDMFormFieldRenderer getDDMFormFieldRenderer();

	public DDMFormFieldValueAccessor<?> getDDMFormFieldValueAccessor(
		Locale locale);

	public DDMFormFieldValueParameterSerializer
		getDDMFormFieldValueParameterSerializer();

	public DDMFormFieldValueRendererAccessor
		getDDMFormFieldValueRendererAccessor(Locale locale);

	public String getName();

	public DDMFormFieldTypeSetting[] getOptionalSettings();

}