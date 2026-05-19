/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.util;

import com.liferay.object.field.business.type.ObjectFieldBusinessType;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Nathaly Gomes
 */
public class ObjectFieldBusinessTypeUtilTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetObjectFieldBusinessTypeMapsIsSortedByLabel() {
		List<Map<String, String>> maps =
			ObjectFieldBusinessTypeUtil.getObjectFieldBusinessTypeMaps(
				LocaleUtil.US,
				Arrays.asList(
					_mockObjectFieldBusinessType("Zip", "Zip"),
					_mockObjectFieldBusinessType("Apple", "Apple"),
					_mockObjectFieldBusinessType("Mango", "Mango")));

		Assert.assertEquals(
			"Apple",
			maps.get(
				0
			).get(
				"label"
			));
		Assert.assertEquals(
			"Mango",
			maps.get(
				1
			).get(
				"label"
			));
		Assert.assertEquals(
			"Zip",
			maps.get(
				2
			).get(
				"label"
			));
	}

	@Test
	public void testGetObjectFieldBusinessTypeMapsUsesLocaleForSorting() {
		List<ObjectFieldBusinessType> objectFieldBusinessTypes = Arrays.asList(
			_mockObjectFieldBusinessType("Zebra", "Abacaxi"),
			_mockObjectFieldBusinessType("Apple", "Melancia"));

		List<Map<String, String>> enMaps =
			ObjectFieldBusinessTypeUtil.getObjectFieldBusinessTypeMaps(
				LocaleUtil.US, objectFieldBusinessTypes);

		Assert.assertEquals(
			"Apple",
			enMaps.get(
				0
			).get(
				"label"
			));
		Assert.assertEquals(
			"Zebra",
			enMaps.get(
				1
			).get(
				"label"
			));

		List<Map<String, String>> ptMaps =
			ObjectFieldBusinessTypeUtil.getObjectFieldBusinessTypeMaps(
				LocaleUtil.BRAZIL, objectFieldBusinessTypes);

		Assert.assertEquals(
			"Abacaxi",
			ptMaps.get(
				0
			).get(
				"label"
			));
		Assert.assertEquals(
			"Melancia",
			ptMaps.get(
				1
			).get(
				"label"
			));
	}

	private ObjectFieldBusinessType _mockObjectFieldBusinessType(
		String enLabel, String ptLabel) {

		ObjectFieldBusinessType objectFieldBusinessType = Mockito.mock(
			ObjectFieldBusinessType.class);

		Mockito.when(
			objectFieldBusinessType.getDBType()
		).thenReturn(
			enLabel
		);

		Mockito.when(
			objectFieldBusinessType.getDescription(LocaleUtil.BRAZIL)
		).thenReturn(
			ptLabel
		);

		Mockito.when(
			objectFieldBusinessType.getDescription(LocaleUtil.US)
		).thenReturn(
			enLabel
		);

		Mockito.when(
			objectFieldBusinessType.getLabel(LocaleUtil.BRAZIL)
		).thenReturn(
			ptLabel
		);

		Mockito.when(
			objectFieldBusinessType.getLabel(LocaleUtil.US)
		).thenReturn(
			enLabel
		);

		Mockito.when(
			objectFieldBusinessType.getName()
		).thenReturn(
			enLabel
		);

		return objectFieldBusinessType;
	}

}