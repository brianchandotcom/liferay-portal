/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.service.impl;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.style.book.service.StyleBookEntryLocalService;
import com.liferay.style.book.service.persistence.StyleBookEntryPersistence;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Anderson Luiz
 */
public class StyleBookEntryLocalServiceImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetStyleBookEntries() {
		_styleBookEntryLocalService.getStyleBookEntries(
			RandomTestUtil.randomLong(), RandomTestUtil.randomString());

		Mockito.verify(
			_styleBookEntryPersistence
		).findByG_T_Head(
			Mockito.anyLong(), Mockito.anyString(), Mockito.eq(true)
		);

		Assert.assertTrue(true);
	}

	@InjectMocks
	private StyleBookEntryLocalService _styleBookEntryLocalService =
		new StyleBookEntryLocalServiceImpl();

	@Mock
	private StyleBookEntryPersistence _styleBookEntryPersistence;

}