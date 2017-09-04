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
import com.liferay.asset.kernel.model.AssetVocabularyDisplay;
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
public class AssetVocabularyDisplayTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGetPage() {
		AssetVocabularyDisplay assetVocabularyDisplay =
			new AssetVocabularyDisplay();

		assetVocabularyDisplay.setStart(0);
		assetVocabularyDisplay.setEnd(20);

		Assert.assertEquals(1, assetVocabularyDisplay.getPage());

		assetVocabularyDisplay.setStart(20);
		assetVocabularyDisplay.setEnd(40);

		Assert.assertEquals(2, assetVocabularyDisplay.getPage());

		assetVocabularyDisplay.setEnd(0);

		Assert.assertEquals(0, assetVocabularyDisplay.getPage());
	}

}