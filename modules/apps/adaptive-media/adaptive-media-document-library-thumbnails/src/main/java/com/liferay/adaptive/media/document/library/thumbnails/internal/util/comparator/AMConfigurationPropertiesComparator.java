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

package com.liferay.adaptive.media.document.library.thumbnails.internal.util.comparator;

import com.liferay.portal.kernel.util.StringPool;

import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Adolfo Pérez
 */
public class AMConfigurationPropertiesComparator<T>
	implements Comparator<Map<String, String>> {

	public AMConfigurationPropertiesComparator(
		String attributeName, Function<String, T> function,
		Comparator<T> comparator) {

		_attributeName = attributeName;
		_function = function;
		_comparator = comparator;
	}

	@Override
	public int compare(
		Map<String, String> properties1, Map<String, String> properties2) {

		String string1 = properties1.getOrDefault(
			_attributeName, StringPool.BLANK);

		String string2 = properties2.getOrDefault(
			_attributeName, StringPool.BLANK);

		return _comparator.compare(
			_function.apply(string1), _function.apply(string2));
	}

	private final String _attributeName;
	private final Comparator<T> _comparator;
	private final Function<String, T> _function;

}