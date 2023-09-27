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
public class GroupKeyInfoItemIdentifierTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testEquals() {
		GroupKeyInfoItemIdentifier groupKeyInfoItemIdentifier1 =
			new GroupKeyInfoItemIdentifier(12345L, "key");

		GroupKeyInfoItemIdentifier groupKeyInfoItemIdentifier2 =
			new GroupKeyInfoItemIdentifier(12345L, "key");

		Assert.assertEquals(
			groupKeyInfoItemIdentifier1, groupKeyInfoItemIdentifier2);
	}

	@Test
	public void testEqualsWithDifferentGroupId() {
		GroupKeyInfoItemIdentifier groupKeyInfoItemIdentifier1 =
			new GroupKeyInfoItemIdentifier(12345L, "key");

		GroupKeyInfoItemIdentifier groupKeyInfoItemIdentifier2 =
			new GroupKeyInfoItemIdentifier(22345L, "key");

		Assert.assertNotEquals(
			groupKeyInfoItemIdentifier1, groupKeyInfoItemIdentifier2);
	}

	@Test
	public void testEqualsWithDifferentKey() {
		GroupKeyInfoItemIdentifier groupKeyInfoItemIdentifier1 =
			new GroupKeyInfoItemIdentifier(12345L, "key1");

		GroupKeyInfoItemIdentifier groupKeyInfoItemIdentifier2 =
			new GroupKeyInfoItemIdentifier(12345L, "key2");

		Assert.assertNotEquals(
			groupKeyInfoItemIdentifier1, groupKeyInfoItemIdentifier2);
	}

	@Test
	public void testGetGroupId() {
		GroupKeyInfoItemIdentifier groupKeyInfoItemIdentifier =
			new GroupKeyInfoItemIdentifier(12345L, "key");

		Assert.assertEquals(12345L, groupKeyInfoItemIdentifier.getGroupId());
	}

	@Test
	public void testGetKey() {
		GroupKeyInfoItemIdentifier groupKeyInfoItemIdentifier =
			new GroupKeyInfoItemIdentifier(12345L, "key");

		Assert.assertEquals("key", groupKeyInfoItemIdentifier.getKey());
	}

	@Test
	public void testHashCode() {
		GroupKeyInfoItemIdentifier groupKeyInfoItemIdentifier1 =
			new GroupKeyInfoItemIdentifier(12345L, "key");

		GroupKeyInfoItemIdentifier groupKeyInfoItemIdentifier2 =
			new GroupKeyInfoItemIdentifier(12345L, "key");

		Assert.assertEquals(
			groupKeyInfoItemIdentifier1.hashCode(),
			groupKeyInfoItemIdentifier2.hashCode());
	}

	@Test
	public void testHashCodeWithDifferentGroupId() {
		GroupKeyInfoItemIdentifier groupKeyInfoItemIdentifier1 =
			new GroupKeyInfoItemIdentifier(12345L, "key");

		GroupKeyInfoItemIdentifier groupKeyInfoItemIdentifier2 =
			new GroupKeyInfoItemIdentifier(22345L, "key");

		Assert.assertNotEquals(
			groupKeyInfoItemIdentifier1.hashCode(),
			groupKeyInfoItemIdentifier2.hashCode());
	}

	@Test
	public void testHashCodeWithDifferentKey() {
		GroupKeyInfoItemIdentifier groupKeyInfoItemIdentifier1 =
			new GroupKeyInfoItemIdentifier(12345L, "key1");

		GroupKeyInfoItemIdentifier groupKeyInfoItemIdentifier2 =
			new GroupKeyInfoItemIdentifier(12345L, "key2");

		Assert.assertNotEquals(
			groupKeyInfoItemIdentifier1.hashCode(),
			groupKeyInfoItemIdentifier2.hashCode());
	}

	@Test
	public void testToString() {
		GroupKeyInfoItemIdentifier groupKeyInfoItemIdentifier =
			new GroupKeyInfoItemIdentifier(12345L, "key");

		Assert.assertEquals(
			"{className=com.liferay.info.item.GroupKeyInfoItemIdentifier, " +
				"_groupId=12345, _key=key}",
			groupKeyInfoItemIdentifier.toString());
	}

}