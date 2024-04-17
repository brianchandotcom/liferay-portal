/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.db.partition.migration.validator.util;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import com.liferay.portal.kernel.version.Version;

import java.io.IOException;

/**
 * @author Luis Ortiz
 */
public class VersionDeserializer extends StdDeserializer<Version> {

	public VersionDeserializer() {
		this(null);
	}

	@Override
	public Version deserialize(
			JsonParser jsonParser,
			DeserializationContext deserializationContext)
		throws IOException, JacksonException {

		JsonNode jsonNode = jsonParser.getCodec(
		).readTree(
			jsonParser
		);

		int major = (Integer)jsonNode.get(
			"major"
		).numberValue();
		int micro = (Integer)jsonNode.get(
			"micro"
		).numberValue();
		int minor = (Integer)jsonNode.get(
			"minor"
		).numberValue();
		String qualifier = jsonNode.get(
			"qualifier"
		).asText();

		return new Version(major, minor, micro, qualifier);
	}

	protected VersionDeserializer(Class<?> vc) {
		super(vc);
	}

}