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

package com.liferay.data.engine.internal.io;

import com.liferay.data.engine.io.DEDataLayoutDeserializerApplyRequest;
import com.liferay.data.engine.io.DEDataLayoutDeserializerApplyResponse;
import com.liferay.data.engine.model.DEDataLayout;
import com.liferay.data.engine.model.DEDataLayoutPage;
import com.liferay.portal.json.JSONFactoryImpl;

import java.util.Queue;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Jeyvison Nascimento
 */
public class DEDataLayoutJSONDeserializerTest extends BaseTestCase {

	@Before
	public void setUp() {
		_deDataLayoutJSONDeserializer = new DEDataLayoutJSONDeserializer();

		_deDataLayoutJSONDeserializer.jsonFactory = new JSONFactoryImpl();
	}

	@Test
	public void testDeserialize() throws Exception {
		DEDataLayoutDeserializerApplyRequest
			deDataLayoutDeserializerApplyRequest =
				DEDataLayoutDeserializerApplyRequest.Builder.of(
					read("data-layout-page-serializer.json"));

		DEDataLayoutDeserializerApplyResponse
			deDataLayoutDeserializerApplyResponse =
				_deDataLayoutJSONDeserializer.apply(
					deDataLayoutDeserializerApplyRequest);

		DEDataLayout deDataLayout =
			deDataLayoutDeserializerApplyResponse.getDEDataLayout();

		Queue<DEDataLayoutPage> deDataLayoutPages =
			deDataLayout.getDEDataLayoutPages();

		Assert.assertFalse(deDataLayoutPages.isEmpty());
	}

	private DEDataLayoutJSONDeserializer _deDataLayoutJSONDeserializer;

}