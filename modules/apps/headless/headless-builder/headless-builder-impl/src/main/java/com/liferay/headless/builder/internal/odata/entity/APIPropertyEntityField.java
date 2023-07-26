/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.internal.odata.entity;

import com.liferay.portal.odata.entity.EntityField;

import java.util.Locale;
import java.util.function.Function;

/**
 * @author Carlos Correa
 */
public class APIPropertyEntityField extends EntityField {

	public APIPropertyEntityField(
		String internalName, String name, Type type,
		Function<Locale, String> sortableFieldNameFunction,
		Function<Locale, String> filterableFieldNameFunction,
		Function<Object, String> filterableFieldValueFunction) {

		super(
			name, type, sortableFieldNameFunction, filterableFieldNameFunction,
			filterableFieldValueFunction);

		_internalName = internalName;
	}

	public String getInternalName() {
		return _internalName;
	}

	private final String _internalName;

}