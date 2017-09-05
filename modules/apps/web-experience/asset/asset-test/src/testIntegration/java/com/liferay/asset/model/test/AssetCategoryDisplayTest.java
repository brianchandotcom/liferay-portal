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

package com.liferay.asset.model.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategoryDisplay;
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
public class AssetCategoryDisplayTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGetPage() {
		AssetCategoryDisplay assetCategoryDisplay = new AssetCategoryDisplay();

		assetCategoryDisplay.setStart(0);
		assetCategoryDisplay.setEnd(20);

		Assert.assertEquals(1, assetCategoryDisplay.getPage());

		assetCategoryDisplay.setStart(20);
		assetCategoryDisplay.setEnd(40);

		Assert.assertEquals(2, assetCategoryDisplay.getPage());

		assetCategoryDisplay.setEnd(0);

		Assert.assertEquals(0, assetCategoryDisplay.getPage());
	}

}