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

package com.liferay.structured.content.apio.internal.architect.resource;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.structured.content.apio.architect.filter.Filter;
import com.liferay.structured.content.apio.architect.filter.expression.BinaryExpression;
import com.liferay.structured.content.apio.architect.filter.expression.LiteralExpression;
import com.liferay.structured.content.apio.internal.architect.filter.expression.BinaryExpressionImpl;
import com.liferay.structured.content.apio.internal.architect.filter.expression.LiteralExpressionImpl;
import com.liferay.structured.content.apio.internal.architect.filter.expression.MemberExpressionImpl;

import java.util.Collections;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author David Arques
 */
public class StructuredContentNestedCollectionResourceTest {

	@Test
	public void testGetFilterFieldsMapWithExistingProperty() {
		Filter filter = _getFilter("title", "Title Value");

		Map<String, Object> filterFieldsMap =
			_structuredContentNestedCollectionResource.getFilterFieldsMap(
				filter);

		Assert.assertEquals("title value", filterFieldsMap.get("title"));
	}

	@Test
	public void testGetFilterFieldsMapWithNonexistingProperty() {
		Filter filter = _getFilter("title", RandomTestUtil.randomString());

		Map<String, Object> filterFieldsMap =
			_structuredContentNestedCollectionResource.getFilterFieldsMap(
				filter);

		Assert.assertNull(filterFieldsMap.get("nonexistingProperty"));
	}

	private Filter _getFilter(String fieldName, String fieldValue) {
		return new Filter(
			new BinaryExpressionImpl(
				new MemberExpressionImpl(Collections.singletonList(fieldName)),
				BinaryExpression.Operation.EQ,
				new LiteralExpressionImpl(
					fieldValue, LiteralExpression.Type.STRING)));
	}

	private static final StructuredContentNestedCollectionResource
		_structuredContentNestedCollectionResource =
			new StructuredContentNestedCollectionResource();

}