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

package com.liferay.headless.foundation.dto.v1_0.parsing;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.headless.foundation.dto.v1_0.MyDTO;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;

import java.text.SimpleDateFormat;

import org.junit.Assert;
import org.junit.BeforeClass;

/**
 * @author Rubén Pulido
 */
public abstract class BaseJSONParserTestCase {

	@BeforeClass
	public static void setUpClass() {
		_objectMapper = new ObjectMapper();

		_objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

		_objectMapper.setDateFormat(
			new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"));
	}

	protected void assertJacksonSerializationMatches(
		MyDTO myDTO, String fileContent) {

		try {
			MyDTO expectedMyDTO = _objectMapper.readValue(
				fileContent, MyDTO.class);

			Assert.assertEquals(
				_objectMapper.writeValueAsString(expectedMyDTO),
				_objectMapper.writeValueAsString(myDTO));
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	protected String getFileContent(String fileName) throws Exception {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

		return StringUtil.read(inputStream);
	}

	private static ObjectMapper _objectMapper;

}