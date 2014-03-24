/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.portal.kernel.upgrade.MockPortletPreferences;
import com.liferay.portal.kernel.util.StringPool;

import javax.portlet.PortletPreferences;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Iván Zaera
 */
public class UpgradeShoppingPreferencesTest {

	@Test
	public void testUpgradeCcTypes() throws Exception {
		_portletPreferences.setValue("ccTypes", "amex,visa,mastercard");

		_upgradeShoppingPreferences.upgradeCcTypes(_portletPreferences);

		String[] ccTypes = _portletPreferences.getValues(
			"ccTypes", StringPool.EMPTY_ARRAY);

		Assert.assertArrayEquals(
			new String[] {"amex", "visa", "mastercard" }, ccTypes);

		Assert.assertEquals(
			"amex", _portletPreferences.getValue("ccTypes", StringPool.BLANK));
	}

	@Test
	public void testUpgradeInsurance() throws Exception {
		_portletPreferences.setValue("insurance", "1,2,3,4,5");

		_upgradeShoppingPreferences.upgradeInsurance(_portletPreferences);

		String[] insurance = _portletPreferences.getValues(
			"insurance", StringPool.EMPTY_ARRAY);

		Assert.assertArrayEquals(
			new String[] {"1", "2", "3", "4", "5" }, insurance);

		Assert.assertEquals(
			"1", _portletPreferences.getValue("insurance", StringPool.BLANK));
	}

	@Test
	public void testUpgradeShipping() throws Exception {
		_portletPreferences.setValue("shipping", "1,2,3,4");

		_upgradeShoppingPreferences.upgradeShipping(_portletPreferences);

		String[] shipping = _portletPreferences.getValues(
			"shipping", StringPool.EMPTY_ARRAY);

		Assert.assertArrayEquals(new String[] {"1", "2", "3", "4" }, shipping);

		Assert.assertEquals(
			"1", _portletPreferences.getValue("shipping", StringPool.BLANK));
	}

	private PortletPreferences _portletPreferences =
		new MockPortletPreferences();
	private UpgradeShoppingPreferences _upgradeShoppingPreferences =
		new UpgradeShoppingPreferences();

}