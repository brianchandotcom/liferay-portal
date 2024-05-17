/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.exception;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Pedro Leite
 */
public class ObjectEntryValuesExceptionTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testExceedsMaxFileSizeException() {
		Long maxFileSize = RandomTestUtil.randomLong();
		String objectFieldName = RandomTestUtil.randomString();

		ObjectEntryValuesException.ExceedsMaxFileSize
			objectEntryValuesExceptionExceedsMaxFileSize =
				new ObjectEntryValuesException.ExceedsMaxFileSize(
					maxFileSize, objectFieldName);

		Assert.assertEquals(
			Arrays.asList(maxFileSize, objectFieldName),
			objectEntryValuesExceptionExceedsMaxFileSize.getArguments());
		Assert.assertEquals(
			(long)maxFileSize,
			objectEntryValuesExceptionExceedsMaxFileSize.getMaxFileSize());
		Assert.assertEquals(
			String.format(
				"File exceeds the maximum permitted size of %s MB for object " +
					"field \"%s\"",
				maxFileSize, objectFieldName),
			objectEntryValuesExceptionExceedsMaxFileSize.getMessage());
		Assert.assertEquals(
			"file-exceeds-the-maximum-permitted-size-of-x-mb-for-object-" +
				"field-x",
			objectEntryValuesExceptionExceedsMaxFileSize.getMessageKey());
		Assert.assertEquals(
			objectFieldName,
			objectEntryValuesExceptionExceedsMaxFileSize.getObjectFieldName());
	}

}