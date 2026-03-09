/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Akhash Ramprakash
 */
public class UniqueUtilTest {

	@Test
	public void testGetUniqueValue() throws PortalException {
		String languageKey = StringUtil.randomString();
		String languageValue = StringUtil.randomString();

		Language language = Mockito.mock(Language.class);

		Mockito.when(
			language.get(Mockito.any(Locale.class), Mockito.eq(languageKey))
		).thenReturn(
			languageValue
		);

		ReflectionTestUtil.setFieldValue(
			LanguageUtil.class, "_language", language);

		String value = RandomTestUtil.randomString();

		Assert.assertEquals(
			StringBundler.concat(value, " (", languageValue, ")"),
			UniqueUtil.getUniqueValue(languageKey, uniqueValue -> true, value));

		value = RandomTestUtil.randomString();

		Assert.assertEquals(
			StringBundler.concat(value, " (", languageValue, " 3)"),
			UniqueUtil.getUniqueValue(
				languageKey, uniqueValue -> uniqueValue.endsWith(" 3)"),
				value));
	}

}