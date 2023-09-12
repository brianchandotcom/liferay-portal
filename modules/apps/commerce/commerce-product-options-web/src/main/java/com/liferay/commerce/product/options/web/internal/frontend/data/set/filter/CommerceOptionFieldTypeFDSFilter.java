/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.options.web.internal.frontend.data.set.filter;

import com.liferay.commerce.product.configuration.CPOptionConfiguration;
import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.options.web.internal.constants.CommerceOptionFDSNames;
import com.liferay.commerce.product.util.DDMFormFieldTypeUtil;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesRegistry;
import com.liferay.frontend.data.set.filter.BaseSelectionFDSFilter;
import com.liferay.frontend.data.set.filter.FDSFilter;
import com.liferay.frontend.data.set.filter.SelectionFDSFilterItem;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.settings.SystemSettingsLocator;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = "frontend.data.set.name=" + CommerceOptionFDSNames.OPTIONS,
	service = FDSFilter.class
)
public class CommerceOptionFieldTypeFDSFilter extends BaseSelectionFDSFilter {

	public String getDDMFormFieldTypeLabel(
		DDMFormFieldType ddmFormFieldType, Locale locale) {

		String label = MapUtil.getString(
			_ddmFormFieldTypeServicesRegistry.getDDMFormFieldTypeProperties(
				ddmFormFieldType.getName()),
			"ddm.form.field.type.label");

		try {
			if (Validator.isNotNull(label)) {
				ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
					"content.Language", locale, ddmFormFieldType.getClass());

				return _language.get(resourceBundle, label);
			}
		}
		catch (MissingResourceException missingResourceException) {
			if (_log.isDebugEnabled()) {
				_log.debug(missingResourceException);
			}
		}

		return ddmFormFieldType.getName();
	}

	public List<DDMFormFieldType> getDDMFormFieldTypes() {
		List<DDMFormFieldType> ddmFormFieldTypes =
			_ddmFormFieldTypeServicesRegistry.getDDMFormFieldTypes();

		CPOptionConfiguration cpOptionConfiguration = null;

		try {
			cpOptionConfiguration = _configurationProvider.getConfiguration(
				CPOptionConfiguration.class,
				new SystemSettingsLocator(CPConstants.SERVICE_NAME_CP_OPTION));
		}
		catch (ConfigurationException configurationException) {
			_log.error(configurationException);
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
	public List<SelectionFDSFilterItem> getSelectionFDSFilterItems(
		Locale locale) {

		List<SelectionFDSFilterItem> selectionFDSFilterItems =
			new ArrayList<>();

		for (DDMFormFieldType ddmFormFieldType : getDDMFormFieldTypes()) {
			selectionFDSFilterItems.add(
				new SelectionFDSFilterItem(
					getDDMFormFieldTypeLabel(ddmFormFieldType, locale),
					ddmFormFieldType.getName()));
		}

		return selectionFDSFilterItems;
	}

	@Override
	public boolean isMultiple() {
		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOptionFieldTypeFDSFilter.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private DDMFormFieldTypeServicesRegistry _ddmFormFieldTypeServicesRegistry;

	@Reference
	private Language _language;

}