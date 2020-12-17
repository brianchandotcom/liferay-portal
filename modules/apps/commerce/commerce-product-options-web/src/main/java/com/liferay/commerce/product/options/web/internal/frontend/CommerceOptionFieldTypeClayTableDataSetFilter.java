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

package com.liferay.commerce.product.options.web.internal.frontend;

import com.liferay.commerce.product.configuration.CPOptionConfiguration;
import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.util.DDMFormFieldTypeUtil;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.frontend.taglib.clay.data.set.filter.BaseRadioClayDataSetFilter;
import com.liferay.frontend.taglib.clay.data.set.filter.ClayDataSetFilter;
import com.liferay.frontend.taglib.clay.data.set.filter.RadioClayDataSetFilterItem;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.settings.SystemSettingsLocator;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	enabled = false, immediate = true,
	property = "clay.data.set.display.name=" + CommerceOptionDataSetConstants.COMMERCE_DATA_SET_KEY_OPTIONS,
	service = ClayDataSetFilter.class
)
public class CommerceOptionFieldTypeClayTableDataSetFilter
	extends BaseRadioClayDataSetFilter {

	public String getDDMFormFieldTypeLabel(
		DDMFormFieldType ddmFormFieldType, Locale locale) {

		Map<String, Object> ddmFormFieldTypeProperties =
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldTypeProperties(
				ddmFormFieldType.getName());

		String label = MapUtil.getString(
			ddmFormFieldTypeProperties, "ddm.form.field.type.label");

		try {
			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				"content.Language", locale, ddmFormFieldType.getClass());

			if (Validator.isNotNull(label)) {
				return LanguageUtil.get(resourceBundle, label);
			}
		}
		catch (MissingResourceException missingResourceException) {
		}

		return ddmFormFieldType.getName();
	}

	public List<DDMFormFieldType> getDDMFormFieldTypes() {
		List<DDMFormFieldType> ddmFormFieldTypes =
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldTypes();

		CPOptionConfiguration cpOptionConfiguration = null;

		try {
			cpOptionConfiguration = _configurationProvider.getConfiguration(
				CPOptionConfiguration.class,
				new SystemSettingsLocator(CPConstants.CP_OPTION_SERVICE_NAME));
		}
		catch (ConfigurationException e) {
			e.printStackTrace();
		}

		String[] ddmFormFieldTypesAllowed =
			cpOptionConfiguration.ddmFormFieldTypesAllowed();

		return DDMFormFieldTypeUtil.getDDMFormFieldTypesAllowed(
			ddmFormFieldTypes, ddmFormFieldTypesAllowed);
	}

	@Override
	public String getId() {
		return "fieldType";
	}

	@Override
	public String getLabel() {
		return "type";
	}

	@Override
	public List<RadioClayDataSetFilterItem> getRadioClayDataSetFilterItems(
		Locale locale) {

		List<RadioClayDataSetFilterItem> radioClayDataSetFilterItems =
			new ArrayList<>();

		for (DDMFormFieldType ddmFormFieldType : getDDMFormFieldTypes()) {
			radioClayDataSetFilterItems.add(
				new RadioClayDataSetFilterItem(
					getDDMFormFieldTypeLabel(ddmFormFieldType, locale),
					ddmFormFieldType.getName()));
		}

		return radioClayDataSetFilterItems;
	}

	@Reference
	protected ConfigurationProvider _configurationProvider;

	@Reference
	protected DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;

}