/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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