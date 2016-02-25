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

import com.liferay.asset.kernel.model.AssetTagDisplay;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Igor Spasic
 */
public class AssetTagDisplayTest {

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