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

package com.liferay.dynamic.data.mapping.render;

import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;
import java.util.Locale;

/**
 * @author Marcellus Tavares
 */
public abstract class BaseDDMFormFieldValueRenderer
	implements DDMFormFieldValueRenderer {

	@Override
	public String render(DDMFormFieldValue ddmFormFieldValue, Locale locale) {
		ValueAccessor valueAccessor = getValueAccessor(locale);

		return valueAccessor.get(ddmFormFieldValue);
	}

	@Override
	public String render(
		List<DDMFormFieldValue> ddmFormFieldValues, Locale locale) {

		return ListUtil.toString(
			ddmFormFieldValues, getValueAccessor(locale),
			StringPool.COMMA_AND_SPACE);
	}

	protected abstract ValueAccessor getValueAccessor(Locale locale);

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getValueAccessor(Locale)}
	 */
	@Deprecated
	protected ValueAccessor getValueAcessor(Locale locale) {
		return getValueAccessor(locale);
	}

}