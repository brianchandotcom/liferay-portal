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

import com.liferay.apio.architect.impl.message.json.FormMessageMapper;
import com.liferay.apio.architect.impl.writer.FormWriter;
import com.liferay.apio.architect.test.util.form.MockFormCreator;

/**
 * Provides methods that test {@link FormMessageMapper} objects.
 *
 * <p>
 * This class shouldn't be instantiated.
 * </p>
 *
 * @author Alejandro Hernández
 */
public class MockFormWriter {

	/**
	 * Writes a {@link com.liferay.apio.architect.form.Form} object.
	 *
	 * @param  formMessageMapper the {@code FormMessageMapper} to use for
	 *         writing the JSON object
	 * @return the {@code String} containing the JSON Object.
	 * @review
	 */
	public static String write(FormMessageMapper formMessageMapper) {
		FormWriter formWriter = FormWriter.create(
			builder -> builder.form(
				MockFormCreator.createForm("f", "s")
			).formMessageMapper(
				formMessageMapper
			).requestInfo(
				getRequestInfo()
			).build());

		return formWriter.write();
	}

	private MockFormWriter() {
		throw new UnsupportedOperationException();
	}

}