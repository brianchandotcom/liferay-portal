/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.util.Locale;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Akhash R
 */
public class UniqueUtilTest {

	@BeforeClass
	public static void setUpClass() {
		Language mockLanguage = Mockito.mock(Language.class);

		Mockito.when(
			mockLanguage.get(Mockito.any(Locale.class), Mockito.eq("copy"))
		).thenReturn(
			"Copy"
		);

		ReflectionTestUtil.setFieldValue(
			LanguageUtil.class, "_language", mockLanguage);
	}

	@Test
	public void testGetCopyName() throws PortalException {
		_testGetCopyName();
		_testGetCopyNameWithMultipleAttempts();
	}

	private void _testGetCopyName() throws PortalException {
		String name1 = RandomTestUtil.randomString();

		Assert.assertEquals(
			name1 + " (Copy)", UniqueUtil.getCopyName(name1, name2 -> true));
	}

	private void _testGetCopyNameWithMultipleAttempts() throws PortalException {
		String name1 = RandomTestUtil.randomString();

		Assert.assertEquals(
			name1 + " (Copy 3)",
			UniqueUtil.getCopyName(name1, name2 -> name2.endsWith("3)")));
	}

}