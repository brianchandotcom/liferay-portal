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

package com.liferay.portlet.asset.model;

import com.liferay.asset.kernel.model.AssetCategoryDisplay;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Igor Spasic
 */
public class AssetCategoryDisplayTest {

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