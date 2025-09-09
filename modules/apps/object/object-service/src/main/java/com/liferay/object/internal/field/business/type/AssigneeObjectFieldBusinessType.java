/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.field.business.type;

import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.dynamic.data.mapping.form.field.type.constants.ObjectDDMFormFieldTypeConstants;
import com.liferay.object.field.business.type.ObjectFieldBusinessType;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.vulcan.extension.PropertyDefinition;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Igor Franca
 */
@Component(
	property = "object.field.business.type.key=" + ObjectFieldConstants.BUSINESS_TYPE_ASSIGNEE,
	service = ObjectFieldBusinessType.class
)
public class AssigneeObjectFieldBusinessType
	implements ObjectFieldBusinessType {

	@Override
	public String getDBType() {
		return ObjectFieldConstants.DB_TYPE_LONG;
	}

	@Override
	public String getDDMFormFieldTypeName() {
		return ObjectDDMFormFieldTypeConstants.ASSIGNEE;
	}

	@Override
	public String getDescription(Locale locale) {
		return LanguageUtil.get(
			ResourceBundleUtil.getModuleAndPortalResourceBundle(
				locale, getClass()),
			"assigns-the-entry-to-a-user-or-role");
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(
			ResourceBundleUtil.getModuleAndPortalResourceBundle(
				locale, getClass()),
			"assignee");
	}

	@Override
	public String getName() {
		return ObjectFieldConstants.BUSINESS_TYPE_ASSIGNEE;
	}

	@Override
	public PropertyDefinition.PropertyType getPropertyType() {
		return PropertyDefinition.PropertyType.LONG;
	}

	@Override
	public boolean isLocalizationSupported(ObjectField objectField) {
		return false;
	}

	@Override
	public boolean isVisible(ObjectDefinition objectDefinition) {
		return FeatureFlagManagerUtil.isEnabled(
			objectDefinition.getCompanyId(), "LPD-6233");
	}

}