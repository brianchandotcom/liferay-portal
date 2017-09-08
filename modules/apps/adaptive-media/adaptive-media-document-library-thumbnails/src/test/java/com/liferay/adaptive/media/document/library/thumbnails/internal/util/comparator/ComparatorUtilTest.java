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

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Adolfo Pérez
 */
public class ComparatorUtilTest {

	@Test
	public void testCompareEqualDistance() {
		Map<String, String> properties1 = MapUtil.fromArray("property", "-5");
		Map<String, String> properties2 = MapUtil.fromArray("property", "5");

		AMConfigurationPropertiesComparator<Integer>
			amConfigurationPropertiesComparator = ComparatorUtil.distanceTo(
				"property", 0);

		Assert.assertEquals(
			0,
			amConfigurationPropertiesComparator.compare(
				properties1, properties2));
	}

	@Test
	public void testCompareNonEqualDistance() {
		Map<String, String> properties1 = MapUtil.fromArray("property", "-2");
		Map<String, String> properties2 = MapUtil.fromArray("property", "5");

		AMConfigurationPropertiesComparator<Integer>
			amConfigurationPropertiesComparator = ComparatorUtil.distanceTo(
				"property", 0);

		Assert.assertEquals(
			-1,
			amConfigurationPropertiesComparator.compare(
				properties1, properties2));
	}

}