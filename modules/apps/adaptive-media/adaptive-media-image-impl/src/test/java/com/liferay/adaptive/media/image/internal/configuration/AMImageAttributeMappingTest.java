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

package com.liferay.adaptive.media.image.internal.configuration;

import com.liferay.adaptive.media.image.processor.AMImageAttribute;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Collections;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Adolfo Pérez
 */
public class AMImageAttributeMappingTest {

	@Test
	public void testCreateFromEmptyMap() {
		AMImageAttributeMapping amImageAttributeMapping =
			AMImageAttributeMapping.fromProperties(Collections.emptyMap());

		Optional<Integer> heightOptional =
			amImageAttributeMapping.getValueOptional(
				AMImageAttribute.IMAGE_HEIGHT);

		Assert.assertFalse(heightOptional.isPresent());

		Optional<Integer> widthOptional =
			amImageAttributeMapping.getValueOptional(
				AMImageAttribute.IMAGE_WIDTH);

		Assert.assertFalse(widthOptional.isPresent());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFailWhenCreatingFromNullMap() {
		AMImageAttributeMapping.fromProperties(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFailWhenGettingValueOfNullAttribute() {
		AMImageAttributeMapping amImageAttributeMapping =
			AMImageAttributeMapping.fromProperties(
				MapUtil.fromArray(
					AMImageAttribute.IMAGE_HEIGHT.getName(), "100",
					AMImageAttribute.IMAGE_WIDTH.getName(), "200"));

		amImageAttributeMapping.getValueOptional(null);
	}

	@Test
	public void testIgnoreUnknownAttributes() {
		AMImageAttributeMapping amImageAttributeMapping =
			AMImageAttributeMapping.fromProperties(
				MapUtil.fromArray("foo", StringUtil.randomString()));

		Optional<Integer> heightOptional =
			amImageAttributeMapping.getValueOptional(
				AMImageAttribute.IMAGE_HEIGHT);

		Assert.assertFalse(heightOptional.isPresent());

		Optional<Integer> widthOptional =
			amImageAttributeMapping.getValueOptional(
				AMImageAttribute.IMAGE_WIDTH);

		Assert.assertFalse(widthOptional.isPresent());
	}

	@Test
	public void testValidAttributes() {
		AMImageAttributeMapping amImageAttributeMapping =
			AMImageAttributeMapping.fromProperties(
				MapUtil.fromArray(
					AMImageAttribute.IMAGE_HEIGHT.getName(), "100",
					AMImageAttribute.IMAGE_WIDTH.getName(), "200"));

		Optional<Integer> heightOptional =
			amImageAttributeMapping.getValueOptional(
				AMImageAttribute.IMAGE_HEIGHT);

		Assert.assertEquals(Integer.valueOf(100), heightOptional.get());

		Optional<Integer> widthOptional =
			amImageAttributeMapping.getValueOptional(
				AMImageAttribute.IMAGE_WIDTH);

		Assert.assertEquals(Integer.valueOf(200), widthOptional.get());
	}

	@Test
	public void testValidSingleAttribute() {
		AMImageAttributeMapping amImageAttributeMapping =
			AMImageAttributeMapping.fromProperties(
				MapUtil.fromArray(
					AMImageAttribute.IMAGE_HEIGHT.getName(), "100"));

		Optional<Integer> heightOptional =
			amImageAttributeMapping.getValueOptional(
				AMImageAttribute.IMAGE_HEIGHT);

		Assert.assertEquals(Integer.valueOf(100), heightOptional.get());

		Optional<Integer> widthOptional =
			amImageAttributeMapping.getValueOptional(
				AMImageAttribute.IMAGE_WIDTH);

		Assert.assertFalse(widthOptional.isPresent());
	}

}