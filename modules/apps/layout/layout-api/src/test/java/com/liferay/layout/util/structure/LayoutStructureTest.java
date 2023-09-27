/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.util.structure;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.uuid.PortalUUIDImpl;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Eudaldo Alonso
 */
public class LayoutStructureTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		PortalUUIDUtil portalUUIDUtil = new PortalUUIDUtil();

		portalUUIDUtil.setPortalUUID(new PortalUUIDImpl());
	}

	@Test
	public void testAddLayoutStructureItemAddFragmentStyledLayoutStructureItem() {
		LayoutStructure layoutStructure = LayoutStructure.of(StringPool.BLANK);

		Assert.assertTrue(
			MapUtil.isEmpty(layoutStructure.getFragmentLayoutStructureItems()));
		Assert.assertTrue(
			ListUtil.isEmpty(
				layoutStructure.getFormStyledLayoutStructureItems()));

		FragmentStyledLayoutStructureItem fragmentStyledLayoutStructureItem =
			new FragmentStyledLayoutStructureItem(
				layoutStructure.getMainItemId());

		long fragmentEntryLinkId = RandomTestUtil.randomLong();

		fragmentStyledLayoutStructureItem.setFragmentEntryLinkId(
			fragmentEntryLinkId);

		layoutStructure.addLayoutStructureItem(
			fragmentStyledLayoutStructureItem);

		Assert.assertFalse(
			MapUtil.isEmpty(layoutStructure.getFragmentLayoutStructureItems()));
		Assert.assertNotNull(
			layoutStructure.getLayoutStructureItemByFragmentEntryLinkId(
				fragmentEntryLinkId));
		Assert.assertTrue(
			ListUtil.isEmpty(
				layoutStructure.getFormStyledLayoutStructureItems()));
	}

}