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