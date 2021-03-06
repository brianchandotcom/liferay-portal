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

package com.liferay.info.item;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Cristina González
 */
public class GroupUrlTitleInfoItemIdentifierTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testEquals() {
		GroupUrlTitleInfoItemIdentifier groupUrlTitleInfoItemIdentifier1 =
			new GroupUrlTitleInfoItemIdentifier(12345L, "urlTitle");

		GroupUrlTitleInfoItemIdentifier groupUrlTitleInfoItemIdentifier2 =
			new GroupUrlTitleInfoItemIdentifier(12345L, "urlTitle");

		Assert.assertEquals(
			groupUrlTitleInfoItemIdentifier1, groupUrlTitleInfoItemIdentifier2);
	}

	@Test
	public void testEqualsWithDifferentGroupId() {
		GroupUrlTitleInfoItemIdentifier groupUrlTitleInfoItemIdentifier1 =
			new GroupUrlTitleInfoItemIdentifier(12345L, "urlTitle");

		GroupUrlTitleInfoItemIdentifier groupUrlTitleInfoItemIdentifier2 =
			new GroupUrlTitleInfoItemIdentifier(22345L, "urlTitle");

		Assert.assertNotEquals(
			groupUrlTitleInfoItemIdentifier1, groupUrlTitleInfoItemIdentifier2);
	}

	@Test
	public void testEqualsWithDifferentUrlTitle() {
		GroupUrlTitleInfoItemIdentifier groupUrlTitleInfoItemIdentifier1 =
			new GroupUrlTitleInfoItemIdentifier(12345L, "urlTitle1");

		GroupUrlTitleInfoItemIdentifier groupUrlTitleInfoItemIdentifier2 =
			new GroupUrlTitleInfoItemIdentifier(12345L, "urlTitle2");

		Assert.assertNotEquals(
			groupUrlTitleInfoItemIdentifier1, groupUrlTitleInfoItemIdentifier2);
	}

	@Test
	public void testGetGroupId() {
		GroupUrlTitleInfoItemIdentifier groupUrlTitleInfoItemIdentifier =
			new GroupUrlTitleInfoItemIdentifier(12345L, "urlTitle");

		Assert.assertEquals(
			12345L, groupUrlTitleInfoItemIdentifier.getGroupId());
	}

	@Test
	public void testGetUrlTitle() {
		GroupUrlTitleInfoItemIdentifier groupUrlTitleInfoItemIdentifier =
			new GroupUrlTitleInfoItemIdentifier(12345L, "urlTitle");

		Assert.assertEquals(
			"urlTitle", groupUrlTitleInfoItemIdentifier.getUrlTitle());
	}

	@Test
	public void testHashCode() {
		GroupUrlTitleInfoItemIdentifier groupUrlTitleInfoItemIdentifier1 =
			new GroupUrlTitleInfoItemIdentifier(12345L, "urlTitle");

		GroupUrlTitleInfoItemIdentifier groupUrlTitleInfoItemIdentifier2 =
			new GroupUrlTitleInfoItemIdentifier(12345L, "urlTitle");

		Assert.assertEquals(
			groupUrlTitleInfoItemIdentifier1.hashCode(),
			groupUrlTitleInfoItemIdentifier2.hashCode());
	}

	@Test
	public void testHashCodeWithDifferentGroupId() {
		GroupUrlTitleInfoItemIdentifier groupUrlTitleInfoItemIdentifier1 =
			new GroupUrlTitleInfoItemIdentifier(12345L, "urlTitle");

		GroupUrlTitleInfoItemIdentifier groupUrlTitleInfoItemIdentifier2 =
			new GroupUrlTitleInfoItemIdentifier(22345L, "urlTitle");

		Assert.assertNotEquals(
			groupUrlTitleInfoItemIdentifier1.hashCode(),
			groupUrlTitleInfoItemIdentifier2.hashCode());
	}

	@Test
	public void testHashCodeWithDifferentUrlTitle() {
		GroupUrlTitleInfoItemIdentifier groupUrlTitleInfoItemIdentifier1 =
			new GroupUrlTitleInfoItemIdentifier(12345L, "urlTitle1");

		GroupUrlTitleInfoItemIdentifier groupUrlTitleInfoItemIdentifier2 =
			new GroupUrlTitleInfoItemIdentifier(12345L, "urlTitle2");

		Assert.assertNotEquals(
			groupUrlTitleInfoItemIdentifier1.hashCode(),
			groupUrlTitleInfoItemIdentifier2.hashCode());
	}

	@Test
	public void testToString() {
		GroupUrlTitleInfoItemIdentifier groupUrlTitleInfoItemIdentifier =
			new GroupUrlTitleInfoItemIdentifier(12345L, "urlTitle");

		Assert.assertEquals(
			"{className=com.liferay.info.item.GroupKeyInfoItemIdentifier, " +
				"_groupId=12345, _urlTitle=urlTitle}",
			groupUrlTitleInfoItemIdentifier.toString());
	}

}