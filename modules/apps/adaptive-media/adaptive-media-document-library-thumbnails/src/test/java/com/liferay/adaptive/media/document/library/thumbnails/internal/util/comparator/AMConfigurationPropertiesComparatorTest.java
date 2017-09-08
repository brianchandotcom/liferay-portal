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

package com.liferay.adaptive.media.document.library.thumbnails.internal.util.comparator;

import com.liferay.portal.kernel.util.MapUtil;

import java.util.Comparator;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Adolfo Pérez
 */
public class AMConfigurationPropertiesComparatorTest {

	@Test
	public void testCompareEqualConfigurationProperties() {
		Map<String, String> properties1 = MapUtil.fromArray(
			"property", "value");
		Map<String, String> properties2 = MapUtil.fromArray(
			"property", "value");

		Comparator<Map<String, String>> amConfigurationPropertiesComparator =
			new AMConfigurationPropertiesComparator<>(
				"property", s -> s, String::compareTo);

		Assert.assertEquals(
			0,
			amConfigurationPropertiesComparator.compare(
				properties1, properties2));
	}

	@Test
	public void testCompareNonEqualConfigurationProperties() {
		Map<String, String> properties1 = MapUtil.fromArray(
			"property", "value1");
		Map<String, String> properties2 = MapUtil.fromArray(
			"property", "value2");

		Comparator<Map<String, String>> amConfigurationPropertiesComparator =
			new AMConfigurationPropertiesComparator<>(
				"property", s -> s, String::compareTo);

		Assert.assertEquals(
			-1,
			amConfigurationPropertiesComparator.compare(
				properties1, properties2));
	}

}