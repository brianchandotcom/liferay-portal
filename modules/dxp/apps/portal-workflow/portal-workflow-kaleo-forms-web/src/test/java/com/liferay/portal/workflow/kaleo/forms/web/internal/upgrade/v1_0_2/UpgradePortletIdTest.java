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

package com.liferay.portal.workflow.kaleo.forms.web.internal.upgrade.v1_0_2;

import com.liferay.portal.kernel.test.util.RandomTestUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Inácio Nery
 */
@RunWith(PowerMockRunner.class)
public class UpgradePortletIdTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		_upgradePortletId = new UpgradePortletId();
	}

	@Test
	public void testOldTypeSettingsShouldBeUpdate1() {
		String oldTypeSettings =
			"column-1=1_WAR_kaleoformsportlet,\ncolumn-2=" + _PORTLET_KEY;

		String newTypeSettings = _upgradePortletId.getNewTypeSettings(
			oldTypeSettings, "1_WAR_kaleoformsportlet");

		String expectedTypeSettings = "column-2=" + _PORTLET_KEY + "\n";

		Assert.assertEquals(expectedTypeSettings, newTypeSettings);
	}

	@Test
	public void testOldTypeSettingsShouldBeUpdate2() {
		String oldTypeSettings =
			"column-1=" + _PORTLET_KEY + ",\ncolumn-2=1_WAR_kaleoformsportlet";

		String newTypeSettings = _upgradePortletId.getNewTypeSettings(
			oldTypeSettings, "1_WAR_kaleoformsportlet");

		String expectedTypeSettings = "column-1=" + _PORTLET_KEY + ",\n";

		Assert.assertEquals(expectedTypeSettings, newTypeSettings);
	}

	private static final String _PORTLET_KEY = RandomTestUtil.randomString();

	private UpgradePortletId _upgradePortletId;

}