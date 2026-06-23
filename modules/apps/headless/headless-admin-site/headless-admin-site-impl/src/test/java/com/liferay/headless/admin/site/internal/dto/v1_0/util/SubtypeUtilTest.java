/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.util;

import com.liferay.headless.admin.site.dto.v1_0.ItemExternalReference;
import com.liferay.headless.admin.site.internal.util.LogUtil;
import com.liferay.info.item.InfoItemServiceRegistryUtil;
import com.liferay.info.item.provider.InfoItemFormVariationsProvider;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Lourdes Fernández Besada
 */
public class SubtypeUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@AfterClass
	public static void tearDownClass() {
		_infoItemServiceRegistryUtilMockedStatic.close();
		_logUtilMockedStatic.close();
	}

	@Test
	@TestInfo("LPD-95298")
	public void testGetClassTypeKey() {
		String className = RandomTestUtil.randomString();
		String externalReferenceCode = RandomTestUtil.randomString();
		long groupId = RandomTestUtil.randomLong();

		ItemExternalReference itemExternalReference =
			new ItemExternalReference();

		itemExternalReference.setExternalReferenceCode(externalReferenceCode);

		_infoItemServiceRegistryUtilMockedStatic.when(
			() -> InfoItemServiceRegistryUtil.getFirstInfoItemService(
				InfoItemFormVariationsProvider.class, className)
		).thenReturn(
			Mockito.mock(InfoItemFormVariationsProvider.class)
		);

		Assert.assertEquals(
			externalReferenceCode,
			SubtypeUtil.getClassTypeKey(
				className, groupId, itemExternalReference));

		_logUtilMockedStatic.verify(
			() -> LogUtil.logOptionalReference(
				Mockito.eq(className), Mockito.eq(externalReferenceCode),
				Mockito.any(), Mockito.eq(groupId)),
			Mockito.never());

		_infoItemServiceRegistryUtilMockedStatic.when(
			() -> InfoItemServiceRegistryUtil.getFirstInfoItemService(
				InfoItemFormVariationsProvider.class, className)
		).thenReturn(
			null
		);

		Assert.assertEquals(
			externalReferenceCode,
			SubtypeUtil.getClassTypeKey(
				className, groupId, itemExternalReference));

		_logUtilMockedStatic.verify(
			() -> LogUtil.logOptionalReference(
				Mockito.eq(className), Mockito.eq(externalReferenceCode),
				Mockito.any(), Mockito.eq(groupId)));
	}

	private static final MockedStatic<InfoItemServiceRegistryUtil>
		_infoItemServiceRegistryUtilMockedStatic = Mockito.mockStatic(
			InfoItemServiceRegistryUtil.class);
	private static final MockedStatic<LogUtil> _logUtilMockedStatic =
		Mockito.mockStatic(LogUtil.class);

}