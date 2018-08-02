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

package com.liferay.apio.architect.test.util.internal.writer;

import static com.liferay.apio.architect.test.util.writer.MockWriterUtil.getRequestInfo;

import com.liferay.apio.architect.impl.message.json.EntryPointMessageMapper;
import com.liferay.apio.architect.impl.writer.EntryPointWriter;
import com.liferay.apio.architect.impl.writer.EntryPointWriter.Builder;

import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

/**
 * Provides methods that test {@code EntryPointMessageMapper} objects.
 *
 * <p>
 * This class shouldn't be instantiated.
 * </p>
 *
 * @author Alejandro Hernández
 * @author Zoltán Takács
 */
public class MockEntryPointWriter {

	/**
	 * Writes an {@link EntryPoint}.
	 *
	 * @param  entryPointMessageMapper the {@link EntryPointMessageMapper} to
	 *         use for writing the JSON object
	 * @return the {@code String} containing the JSON Object.
	 * @review
	 */
	public static String write(
		EntryPointMessageMapper entryPointMessageMapper) {

		EntryPointWriter entryPointWriter = Builder.entryPoint(
			() -> Arrays.asList("type1", "type2", "type3")
		).entryPointMessageMapper(
			entryPointMessageMapper
		).requestInfo(
			getRequestInfo()
		).typeFunction(
			MockEntryPointWriter::_capitalize
		).build();

		return entryPointWriter.write();
	}

	private static Optional<String> _capitalize(String resourceName) {
		String firstLetter = resourceName.substring(0, 1);

		return Optional.of(
			firstLetter.toUpperCase(Locale.getDefault()) +
				resourceName.substring(1));
	}

	private MockEntryPointWriter() {
		throw new UnsupportedOperationException();
	}

}