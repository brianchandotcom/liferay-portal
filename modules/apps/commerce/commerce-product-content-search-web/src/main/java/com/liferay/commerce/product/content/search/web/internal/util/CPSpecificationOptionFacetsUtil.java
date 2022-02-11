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

package com.liferay.commerce.product.content.search.web.internal.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author Alessio Antonio Rendina
 */
public class CPSpecificationOptionFacetsUtil {

	public static <T> void copy(Supplier<Optional<T>> from, Consumer<T> to) {
		Optional<T> optional = from.get();

		optional.ifPresent(to);
	}

	public static String getCPSpecificationOptionKeyFromIndexFieldName(
		String fieldName) {

		if (Validator.isNull(fieldName)) {
			return StringPool.BLANK;
		}

		String[] fieldNameParts = StringUtil.split(
			fieldName, StringPool.UNDERLINE);

		return fieldNameParts[3];
	}

	public static String getIndexFieldName(String key, String languageId) {
		return StringBundler.concat(
			languageId, "_SPECIFICATION_", key, "_VALUE_NAME");
	}

	public static Optional<String> maybe(String s) {
		s = StringUtil.trim(s);

		if (Validator.isBlank(s)) {
			return Optional.empty();
		}

		return Optional.of(s);
	}

}