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

package com.liferay.adaptive.media;

import com.liferay.adaptive.media.exception.AMRuntimeException;
import com.liferay.portal.kernel.security.RandomUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Adolfo Pérez
 */
public class AMAttributeTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testConfigurationUuidRecognizesAnyString() {
		AMAttribute<?, String> configurationUuidAMAttribute =
			AMAttribute.getConfigurationUuidAMAttribute();

		String value = RandomTestUtil.randomString();

		Assert.assertEquals(value, configurationUuidAMAttribute.convert(value));
	}

	@Test(expected = AMRuntimeException.AMAttributeFormatException.class)
	public void testContentLengthFailsForNonintegers() {
		AMAttribute<?, Long> contentLengthAMAttribute =
			AMAttribute.getContentLengthAMAttribute();

		contentLengthAMAttribute.convert(RandomTestUtil.randomString());
	}

	@Test
	public void testContentLengthRecognizesIntegers() {
		AMAttribute<?, Long> contentLengthAMAttribute =
			AMAttribute.getContentLengthAMAttribute();

		long value = RandomUtil.nextInt(Integer.MAX_VALUE);

		Assert.assertEquals(
			value,
			(long)contentLengthAMAttribute.convert(String.valueOf(value)));
	}

	@Test
	public void testContentTypeRecognizesAnyString() {
		AMAttribute<?, String> contentTypeAMAttribute =
			AMAttribute.getContentTypeAMAttribute();

		String value = RandomTestUtil.randomString();

		Assert.assertEquals(value, contentTypeAMAttribute.convert(value));
	}

	@Test
	public void testFileNameRecognizesAnyString() {
		AMAttribute<?, String> fileNameAMAttribute =
			AMAttribute.getFileNameAMAttribute();

		String value = RandomTestUtil.randomString();

		Assert.assertEquals(value, fileNameAMAttribute.convert(value));
	}

	@Test
	public void testGetAllowedAMAttributes() {
		Collection<AMAttribute<?, ?>> amAttributes = Arrays.asList(
			AMAttribute.getConfigurationUuidAMAttribute(),
			AMAttribute.getContentLengthAMAttribute(),
			AMAttribute.getContentTypeAMAttribute(),
			AMAttribute.getFileNameAMAttribute());

		Map<String, AMAttribute<?, ?>> allowedAMAttributesMap =
			AMAttribute.getAllowedAMAttributes();

		Collection<AMAttribute<?, ?>> allowedAMAttributes =
			allowedAMAttributesMap.values();

		Assert.assertTrue(allowedAMAttributes.containsAll(amAttributes));
	}

}