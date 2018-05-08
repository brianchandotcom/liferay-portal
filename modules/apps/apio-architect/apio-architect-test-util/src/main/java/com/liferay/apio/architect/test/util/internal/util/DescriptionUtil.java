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

package com.liferay.apio.architect.test.util.internal.util;

import java.util.function.Consumer;

import org.hamcrest.Description;
import org.hamcrest.StringDescription;

/**
 * Provides utility functions hamcrest {@code Description}.
 *
 * <p>
 * This class shouldn't be instantiated.
 * </p>
 *
 * @author Alejandro Hernández
 */
public class DescriptionUtil {

	/**
	 * Appends and indents a field's description inside the provided
	 * description.
	 *
	 * @param description the provided description
	 * @param key the field's key
	 * @param consumer the consumer that updates the field's description
	 */
	public static void indentDescription(
		Description description, String key, Consumer<Description> consumer) {

		Description stringDescription = new StringDescription();

		consumer.accept(stringDescription);

		String string = stringDescription.toString();

		String trimmed = string.trim();

		description.appendText(
			"  " + key + ": "
		).appendText(
			trimmed.replaceAll("\n", "\n  ")
		).appendText(
			"\n"
		);
	}

	private DescriptionUtil() {
		throw new UnsupportedOperationException();
	}

}