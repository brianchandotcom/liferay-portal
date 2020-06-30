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

package com.liferay.portal.search.internal.index;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author André de Oliveira
 */
public class IndexStatusManagerImplRequireIndexReadWriteTest {

	@Test
	public void testBookendsLikeSetupAndTeardown() {
		indexStatusManagerImpl.requireIndexReadWrite(true);

		Assert.assertFalse(indexStatusManagerImpl.isIndexReadOnly());

		indexStatusManagerImpl.setIndexReadOnly(false);

		Assert.assertFalse(indexStatusManagerImpl.isIndexReadOnly());

		indexStatusManagerImpl.requireIndexReadWrite(false);

		Assert.assertFalse(indexStatusManagerImpl.isIndexReadOnly());
	}

	@Test
	public void testReadOnlySetAfterBookends() {
		indexStatusManagerImpl.requireIndexReadWrite(true);

		Assert.assertFalse(indexStatusManagerImpl.isIndexReadOnly());

		indexStatusManagerImpl.requireIndexReadWrite(false);

		Assert.assertFalse(indexStatusManagerImpl.isIndexReadOnly());

		indexStatusManagerImpl.setIndexReadOnly(true);

		Assert.assertTrue(indexStatusManagerImpl.isIndexReadOnly());
	}

	@Test
	public void testReadOnlySetBeforeBookends() {
		indexStatusManagerImpl.setIndexReadOnly(true);

		Assert.assertTrue(indexStatusManagerImpl.isIndexReadOnly());

		indexStatusManagerImpl.requireIndexReadWrite(true);

		Assert.assertFalse(indexStatusManagerImpl.isIndexReadOnly());

		indexStatusManagerImpl.requireIndexReadWrite(false);

		Assert.assertFalse(indexStatusManagerImpl.isIndexReadOnly());
	}

	@Test
	public void testReadOnlySetBetweenBookends() {
		indexStatusManagerImpl.requireIndexReadWrite(true);

		Assert.assertFalse(indexStatusManagerImpl.isIndexReadOnly());

		indexStatusManagerImpl.setIndexReadOnly(true);

		Assert.assertFalse(indexStatusManagerImpl.isIndexReadOnly());

		indexStatusManagerImpl.requireIndexReadWrite(false);

		Assert.assertFalse(indexStatusManagerImpl.isIndexReadOnly());
	}

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	protected IndexStatusManagerImpl indexStatusManagerImpl =
		new IndexStatusManagerImpl();

}