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

package com.liferay.object.internal.field.filter.parser;

import com.liferay.object.constants.ObjectViewFilterColumnConstants;
import com.liferay.object.exception.ObjectViewFilterColumnException;
import com.liferay.object.model.ObjectViewFilterColumn;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Feliphe Marinho
 */
public class ListObjectFieldFilterParserTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testValidate() throws PortalException {
		ObjectViewFilterColumn objectViewFilterColumn = Mockito.mock(
			ObjectViewFilterColumn.class);

		Mockito.when(
			objectViewFilterColumn.getFilterType()
		).thenReturn(
			ObjectViewFilterColumnConstants.FILTER_TYPE_EXCLUDES
		);

		Mockito.when(
			objectViewFilterColumn.getJSON()
		).thenReturn(
			"{\"includes\": [0, 1]}"
		);

		try {
			_listObjectFieldFilterParser.validate(0L, objectViewFilterColumn);

			Assert.fail();
		}
		catch (ObjectViewFilterColumnException
					objectViewFilterColumnException) {

			Assert.assertEquals(
				"JSON array is null for filter type excludes",
				objectViewFilterColumnException.getMessage());
		}

		Mockito.when(
			objectViewFilterColumn.getJSON()
		).thenReturn(
			"{\"excludes\": [\"brazil\"]}"
		);

		Mockito.when(
			objectViewFilterColumn.getObjectFieldName()
		).thenReturn(
			"status"
		);

		try {
			_listObjectFieldFilterParser.validate(0L, objectViewFilterColumn);

			Assert.fail();
		}
		catch (ObjectViewFilterColumnException
					objectViewFilterColumnException) {

			Assert.assertEquals(
				"JSON array is invalid for filter type excludes",
				objectViewFilterColumnException.getMessage());
		}

		Mockito.when(
			objectViewFilterColumn.getJSON()
		).thenReturn(
			"{\"excludes\": [0, 1]}"
		);

		_listObjectFieldFilterParser.validate(0L, objectViewFilterColumn);
	}

	private final ListObjectFieldFilterParser _listObjectFieldFilterParser =
		new ListObjectFieldFilterParser();

}