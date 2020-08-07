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

package com.liferay.portal.search.elasticsearch7.internal.query;

import com.liferay.portal.search.internal.query.MultiMatchQueryImpl;
import com.liferay.portal.search.query.MultiMatchQuery;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * @author Bryan Engler
 */
public class MultiMatchQueryTranslatorImplTest {

	@Test
	public void testTranslate() {
		MultiMatchQueryTranslatorImpl multiMatchQueryTranslatorImpl =
			new MultiMatchQueryTranslatorImpl();

		Map<String, Float> fields = new HashMap<>();

		fields.put("test", null);

		MultiMatchQuery multiMatchQuery = new MultiMatchQueryImpl(
			"test", fields);

		multiMatchQueryTranslatorImpl.translate(multiMatchQuery);
	}

}