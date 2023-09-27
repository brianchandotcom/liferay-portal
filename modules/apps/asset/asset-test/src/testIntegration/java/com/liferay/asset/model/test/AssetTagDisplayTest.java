/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.model.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetTagDisplay;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Igor Spasic
 */
@RunWith(Arquillian.class)
public class AssetTagDisplayTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGetPage() {
		AssetTagDisplay assetTagDisplay = new AssetTagDisplay();

		assetTagDisplay.setStart(0);
		assetTagDisplay.setEnd(20);

		Assert.assertEquals(1, assetTagDisplay.getPage());

		assetTagDisplay.setStart(20);
		assetTagDisplay.setEnd(40);

		Assert.assertEquals(2, assetTagDisplay.getPage());

		assetTagDisplay.setEnd(0);

		Assert.assertEquals(0, assetTagDisplay.getPage());
	}

}