/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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