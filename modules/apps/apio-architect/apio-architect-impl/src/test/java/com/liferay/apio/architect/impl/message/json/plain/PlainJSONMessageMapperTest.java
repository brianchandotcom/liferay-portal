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

package com.liferay.apio.architect.impl.message.json.plain;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import com.liferay.apio.architect.test.util.json.MessageMapperTesterBuilder;

import java.nio.file.Paths;

import org.junit.Test;

/**
 * @author Javier Gamarra
 * @author Alejandro Hernández
 */
public class PlainJSONMessageMapperTest {

	@Test
	public void testPlainJSONMessageMappers() {
		MessageMapperTesterBuilder.path(
			Paths.get("src", "test", "resources", "plain")
		).mediaType(
			APPLICATION_JSON
		).validatePageMessageMapper(
			new PlainJSONPageMessageMapper<>()
		).validateSingleModelMessageMapper(
			new PlainJSONSingleModelMessageMapper<>()
		);
	}

}