/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.internal.type.test;

import com.liferay.object.field.business.type.ObjectFieldBusinessType;
import com.liferay.portal.vulcan.extension.PropertyDefinition;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Richard Jeremias
 */
@Component(
	property = "object.field.business.type.key=test",
	service = ObjectFieldBusinessType.class
)
public class TestObjectFieldBusinessType implements ObjectFieldBusinessType {

	@Override
	public String getDBType() {
		return null;
	}

	@Override
	public String getDDMFormFieldTypeName() {
		return null;
	}

	@Override
	public String getLabel(Locale locale) {
		return null;
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public PropertyDefinition.PropertyType getPropertyType() {
		return null;
	}

}