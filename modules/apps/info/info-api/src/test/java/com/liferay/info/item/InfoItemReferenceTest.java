/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.item;

import com.liferay.info.item.provider.filter.InfoItemServiceFilter;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Cristina González
 */
public class InfoItemReferenceTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testEquals() {
		InfoItemReference infoItemReference1 = new InfoItemReference(
			"className", 12354L);

		InfoItemReference infoItemReference2 = new InfoItemReference(
			"className", 12354L);

		Assert.assertEquals(infoItemReference1, infoItemReference2);
	}

	@Test
	public void testEqualsWithDifferentClassName() {
		InfoItemReference infoItemReference1 = new InfoItemReference(
			"className1", 12354L);

		InfoItemReference infoItemReference2 = new InfoItemReference(
			"className2", 12354L);

		Assert.assertNotEquals(infoItemReference1, infoItemReference2);
	}

	@Test
	public void testEqualsWithDifferentClassPK() {
		InfoItemReference infoItemReference1 = new InfoItemReference(
			"className", 12354L);

		InfoItemReference infoItemReference2 = new InfoItemReference(
			"className", 22354L);

		Assert.assertNotEquals(infoItemReference1, infoItemReference2);
	}

	@Test
	public void testGetClassName() {
		InfoItemReference infoItemReference = new InfoItemReference(
			"className", 12354L);

		Assert.assertEquals("className", infoItemReference.getClassName());
	}

	@Test
	public void testGetClassPK() {
		InfoItemReference infoItemReference = new InfoItemReference(
			"className", 12354L);

		InfoItemIdentifier infoItemIdentifier =
			infoItemReference.getInfoItemIdentifier();

		Assert.assertTrue(
			infoItemIdentifier instanceof ClassPKInfoItemIdentifier);

		ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
			(ClassPKInfoItemIdentifier)
				infoItemReference.getInfoItemIdentifier();

		Assert.assertEquals(12354L, classPKInfoItemIdentifier.getClassPK());
	}

	@Test
	public void testGetInfoItemIdentifier() {
		InfoItemReference infoItemReference = new InfoItemReference(
			"className", 12354L);

		Assert.assertEquals(
			new ClassPKInfoItemIdentifier(12354L),
			infoItemReference.getInfoItemIdentifier());
	}

	@Test
	public void testGetInfoItemReference() {
		InfoItemIdentifier infoItemIdentifier = new InfoItemIdentifier() {

			@Override
			public InfoItemServiceFilter getInfoItemServiceFilter() {
				return null;
			}

			@Override
			public String getVersion() {
				return null;
			}

			@Override
			public void setVersion(String version) {
			}

		};

		InfoItemReference infoItemReference = new InfoItemReference(
			"className", infoItemIdentifier);

		Assert.assertEquals(
			infoItemIdentifier, infoItemReference.getInfoItemIdentifier());
	}

	@Test
	public void testHashCode() {
		InfoItemReference infoItemReference1 = new InfoItemReference(
			"className", 12354L);

		InfoItemReference infoItemReference2 = new InfoItemReference(
			"className", 12354L);

		Assert.assertEquals(
			infoItemReference1.hashCode(), infoItemReference2.hashCode());
	}

	@Test
	public void testHashCodeWithDifferentClassName() {
		InfoItemReference infoItemReference1 = new InfoItemReference(
			"className1", 12354L);

		InfoItemReference infoItemReference2 = new InfoItemReference(
			"className2", 12354L);

		Assert.assertNotEquals(
			infoItemReference1.hashCode(), infoItemReference2.hashCode());
	}

	@Test
	public void testHashCodeWithDifferentClassPK() {
		InfoItemReference infoItemReference1 = new InfoItemReference(
			"className", 12354L);

		InfoItemReference infoItemReference2 = new InfoItemReference(
			"className", 22354L);

		Assert.assertNotEquals(
			infoItemReference1.hashCode(), infoItemReference2.hashCode());
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