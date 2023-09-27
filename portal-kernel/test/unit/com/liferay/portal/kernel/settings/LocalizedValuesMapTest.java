/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.settings;

import com.liferay.portal.kernel.util.LocaleUtil;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Iván Zaera
 */
public class LocalizedValuesMapTest {

	@Test
	public void testGetReturnsDefaultValueForUnknownLocale() {
		LocalizedValuesMap localizedValuesMap = new LocalizedValuesMap(
			"defaultValue");

		Assert.assertEquals(
			"defaultValue", localizedValuesMap.get(LocaleUtil.BRAZIL));
	}

	@Test
	public void testGetReturnsValueForKnownLocale() {
		LocalizedValuesMap localizedValuesMap = new LocalizedValuesMap(
			"defaultValue");

		localizedValuesMap.put(LocaleUtil.CANADA, "Hello");

		Assert.assertEquals("Hello", localizedValuesMap.get(LocaleUtil.CANADA));
	}

}