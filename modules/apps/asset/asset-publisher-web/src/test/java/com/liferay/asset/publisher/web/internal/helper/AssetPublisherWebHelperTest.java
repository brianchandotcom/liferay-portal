/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.publisher.web.internal.helper;

import com.liferay.asset.publisher.web.internal.model.TestClassType;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.upgrade.MockPortletPreferences;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Lourdes Fernández Besada
 */
public class AssetPublisherWebHelperTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetClassTypeIds() {
		AssetPublisherWebHelper assetPublisherWebHelper =
			new AssetPublisherWebHelper();

		MockPortletPreferences portletPreferences =
			new MockPortletPreferences();

		String className = RandomTestUtil.randomString();

		portletPreferences.setValue(
			"anyClassType" + className, Boolean.FALSE.toString());

		Long[] classTypeIds = {
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong()
		};

		portletPreferences.setValue(
			"classTypeIds" + className,
			StringUtil.merge(classTypeIds, StringPool.COMMA));

		ArrayUtil.reverse(classTypeIds);

		Assert.assertArrayEquals(
			classTypeIds,
			assetPublisherWebHelper.getClassTypeIds(
				portletPreferences, className,
				ListUtil.fromArray(
					new TestClassType(
						classTypeIds[0], RandomTestUtil.randomString()),
					new TestClassType(
						RandomTestUtil.randomLong(),
						RandomTestUtil.randomString()),
					new TestClassType(
						classTypeIds[1], RandomTestUtil.randomString()))));
	}

}