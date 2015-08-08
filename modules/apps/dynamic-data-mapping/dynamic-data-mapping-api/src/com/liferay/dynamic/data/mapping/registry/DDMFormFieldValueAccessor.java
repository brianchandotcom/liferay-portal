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

package com.liferay.dynamic.data.mapping.registry;

import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.portal.kernel.util.Accessor;

import java.util.Locale;

/**
 * @author Marcellus Tavares
 */
public abstract class DDMFormFieldValueAccessor<A>
	implements Accessor<DDMFormFieldValue, A> {

	public DDMFormFieldValueAccessor(Locale locale) {
		this.locale = locale;
	}

	public Locale getLocale() {
		return locale;
	}

	@Override
	public Class<DDMFormFieldValue> getTypeClass() {
		return DDMFormFieldValue.class;
	}

	protected Locale locale;

}