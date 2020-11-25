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

import com.liferay.info.item.provider.filter.InfoItemServiceFilter;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Cristina González
 */
public class InfoItemReferenceTest {

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

		Assert.assertEquals(12354L, infoItemReference.getClassPK());
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
			public InfoItemServiceFilter getInfoServiceFilter() {
				return null;
			}

			@Override
			public Optional<String> getVersionOptional() {
				return Optional.empty();
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