/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.url.pattern.mapper;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @author Carlos Sierra Andrés
 */
public interface URLPatternMapper<T> {

	public void consumeValues(Consumer<T> consumer, String urlPath);

	public T getValue(String urlPath);

	public default Set<T> getValues(String urlPath) {
		Set<T> values = new HashSet<>(Long.SIZE);

		consumeValues(values::add, urlPath);

		return values;
	}

}