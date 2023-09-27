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