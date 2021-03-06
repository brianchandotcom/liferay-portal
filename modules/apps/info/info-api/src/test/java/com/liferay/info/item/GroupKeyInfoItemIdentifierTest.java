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