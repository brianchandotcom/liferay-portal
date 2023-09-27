/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.field.filter.parser;

import com.liferay.object.constants.ObjectViewFilterColumnConstants;
import com.liferay.object.exception.ObjectViewFilterColumnException;
import com.liferay.object.model.ObjectViewFilterColumn;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Feliphe Marinho
 */
public class ListObjectFieldFilterContributorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Ignore
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
			_listObjectFieldFilterContributor.validate();

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
			_listObjectFieldFilterContributor.validate();

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

		_listObjectFieldFilterContributor.validate();
	}

	private final ListObjectFieldFilterContributor
		_listObjectFieldFilterContributor =
			new ListObjectFieldFilterContributor();

}