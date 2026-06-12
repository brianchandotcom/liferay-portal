/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.util;

import com.liferay.object.field.business.type.ObjectFieldBusinessType;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
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
	public void testGetObjectFieldBusinessTypeMaps() {
		List<ObjectFieldBusinessType> objectFieldBusinessTypes = Arrays.asList(
			_mockObjectFieldBusinessType("Apple", "Maca"),
			_mockObjectFieldBusinessType("Banana", "Banana"),
			_mockObjectFieldBusinessType("Pineapple", "Abacaxi"));

		List<Map<String, String>> objectFieldBusinessTypeMaps =
			ObjectFieldBusinessTypeUtil.getObjectFieldBusinessTypeMaps(
				LocaleUtil.US, objectFieldBusinessTypes);

		Map<String, String> objectFieldBusinessTypeMap0 =
			objectFieldBusinessTypeMaps.get(0);
		Map<String, String> objectFieldBusinessTypeMap1 =
			objectFieldBusinessTypeMaps.get(1);
		Map<String, String> objectFieldBusinessTypeMap2 =
			objectFieldBusinessTypeMaps.get(2);

		Assert.assertEquals("Apple", objectFieldBusinessTypeMap0.get("label"));
		Assert.assertEquals("Banana", objectFieldBusinessTypeMap1.get("label"));
		Assert.assertEquals(
			"Pineapple", objectFieldBusinessTypeMap2.get("label"));

		objectFieldBusinessTypeMaps =
			ObjectFieldBusinessTypeUtil.getObjectFieldBusinessTypeMaps(
				LocaleUtil.BRAZIL, objectFieldBusinessTypes);

		objectFieldBusinessTypeMap0 = objectFieldBusinessTypeMaps.get(0);
		objectFieldBusinessTypeMap1 = objectFieldBusinessTypeMaps.get(1);
		objectFieldBusinessTypeMap2 = objectFieldBusinessTypeMaps.get(2);

		Assert.assertEquals(
			"Abacaxi", objectFieldBusinessTypeMap0.get("label"));
		Assert.assertEquals("Banana", objectFieldBusinessTypeMap1.get("label"));
		Assert.assertEquals("Maca", objectFieldBusinessTypeMap2.get("label"));
	}

	private ObjectFieldBusinessType _mockObjectFieldBusinessType(
		String enLabel, String ptLabel) {

		ObjectFieldBusinessType objectFieldBusinessType = Mockito.mock(
			ObjectFieldBusinessType.class);

		Mockito.when(
			objectFieldBusinessType.getDBType()
		).thenReturn(
			RandomTestUtil.randomString()
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
			RandomTestUtil.randomString()
		);

		return objectFieldBusinessType;
	}

}