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
public class ClassPKInfoItemIdentifierTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testEquals() {
		ClassPKInfoItemIdentifier classPKInfoItemIdentifier1 =
			new ClassPKInfoItemIdentifier(12345L);

		ClassPKInfoItemIdentifier classPKInfoItemIdentifier2 =
			new ClassPKInfoItemIdentifier(12345L);

		Assert.assertEquals(
			classPKInfoItemIdentifier1, classPKInfoItemIdentifier2);
	}

	@Test
	public void testEqualsWithDifferentClassPK() {
		ClassPKInfoItemIdentifier classPKInfoItemIdentifier1 =
			new ClassPKInfoItemIdentifier(12345L);

		ClassPKInfoItemIdentifier classPKInfoItemIdentifier2 =
			new ClassPKInfoItemIdentifier(22345L);

		Assert.assertNotEquals(
			classPKInfoItemIdentifier1, classPKInfoItemIdentifier2);
	}

	@Test
	public void testGetClassPK() {
		ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
			new ClassPKInfoItemIdentifier(12345L);

		Assert.assertEquals(12345L, classPKInfoItemIdentifier.getClassPK());
	}

	@Test
	public void testHashCode() {
		ClassPKInfoItemIdentifier classPKInfoItemIdentifier1 =
			new ClassPKInfoItemIdentifier(12345L);

		ClassPKInfoItemIdentifier classPKInfoItemIdentifier2 =
			new ClassPKInfoItemIdentifier(12345L);

		Assert.assertEquals(
			classPKInfoItemIdentifier1.hashCode(),
			classPKInfoItemIdentifier2.hashCode());
	}

	@Test
	public void testHashCodeWithDifferentClassPK() {
		ClassPKInfoItemIdentifier classPKInfoItemIdentifier1 =
			new ClassPKInfoItemIdentifier(12345L);

		ClassPKInfoItemIdentifier classPKInfoItemIdentifier2 =
			new ClassPKInfoItemIdentifier(22345L);

		Assert.assertNotEquals(
			classPKInfoItemIdentifier1.hashCode(),
			classPKInfoItemIdentifier2.hashCode());
	}

	@Test
	public void testToString() {
		ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
			new ClassPKInfoItemIdentifier(12345L);

		Assert.assertEquals(
			"{className=com.liferay.info.item.ClassPKInfoItemIdentifier, " +
				"classPK=12345}",
			classPKInfoItemIdentifier.toString());
	}

}