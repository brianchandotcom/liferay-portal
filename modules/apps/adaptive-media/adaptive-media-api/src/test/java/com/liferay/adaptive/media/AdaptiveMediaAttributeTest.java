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

import com.liferay.adaptive.media.exception.AdaptiveMediaRuntimeException;
import com.liferay.portal.kernel.security.RandomUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Adolfo Pérez
 */
public class AdaptiveMediaAttributeTest {

	@Test
	public void testAllPublicAttributesAreSupported() {
		Collection<AdaptiveMediaAttribute<?, ?>> adaptiveMediaAttributes =
			Arrays.asList(
				AdaptiveMediaAttribute.getConfigurationUuid(),
				AdaptiveMediaAttribute.getContentLength(),
				AdaptiveMediaAttribute.getContentType(),
				AdaptiveMediaAttribute.getFileName());

		Map<String, AdaptiveMediaAttribute<?, ?>>
			allowedAdaptiveMediaAttributesMap =
				AdaptiveMediaAttribute.getAllowedAdaptiveMediaAttributes();

		Collection<AdaptiveMediaAttribute<?, ?>>
			allowedAdaptiveMediaAttributes =
				allowedAdaptiveMediaAttributesMap.values();

		Assert.assertTrue(
			allowedAdaptiveMediaAttributes.containsAll(
				adaptiveMediaAttributes));
	}

	@Test
	public void testConfigurationUuidRecognizesAnyString() {
		AdaptiveMediaAttribute<?, String> configurationUuid =
			AdaptiveMediaAttribute.getConfigurationUuid();

		String value = StringUtil.randomString();

		Assert.assertEquals(value, configurationUuid.convert(value));
	}

	@Test(
		expected = AdaptiveMediaRuntimeException.AdaptiveMediaAttributeFormatException.class
	)
	public void testContentLengthFailsForNonIntegers() {
		AdaptiveMediaAttribute<?, Integer> contentLength =
			AdaptiveMediaAttribute.getContentLength();

		contentLength.convert(StringUtil.randomString());
	}

	@Test
	public void testContentLengthRecognizesIntegers() {
		AdaptiveMediaAttribute<?, Integer> contentLength =
			AdaptiveMediaAttribute.getContentLength();

		Integer value = RandomUtil.nextInt(Integer.MAX_VALUE);

		Assert.assertEquals(
			value, contentLength.convert(String.valueOf(value)));
	}

	@Test
	public void testContentTypeRecognizesAnyString() {
		AdaptiveMediaAttribute<?, String> contentType =
			AdaptiveMediaAttribute.getContentType();

		String value = StringUtil.randomString();

		Assert.assertEquals(value, contentType.convert(value));
	}

	@Test
	public void testFileNameRecognizesAnyString() {
		AdaptiveMediaAttribute<?, String> fileName =
			AdaptiveMediaAttribute.getFileName();

		String value = StringUtil.randomString();

		Assert.assertEquals(value, fileName.convert(value));
	}

}