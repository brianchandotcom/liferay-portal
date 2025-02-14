/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.odata.entity;

import com.liferay.portal.odata.entity.StringEntityField;

import java.util.Locale;
import java.util.function.Function;

/**
 * @author Carlos Correa
 */
public class ReferenceStringEntityField extends StringEntityField {

	public ReferenceStringEntityField(
		String fieldName, Function<Locale, String> filterableFunction,
		String referenceFieldName) {

		super(fieldName, locale -> referenceFieldName, filterableFunction);

		_referenceFieldName = referenceFieldName;
	}

	public String getReferenceFieldName() {
		return _referenceFieldName;
	}

	private final String _referenceFieldName;

}