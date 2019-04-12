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

package com.liferay.portal.kernel.atom;

import com.liferay.portal.kernel.atom.bundle.atomcollectionadapterregistryutil.TestAtomCollectionAdapter;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SyntheticBundleClassTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Peter Fellwock
 */
public class AtomCollectionAdapterRegistryUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			new SyntheticBundleClassTestRule(
				"bundle.atomcollectionadapterregistryutil"));

	@Test
	public void testGetAtomCollectionAdapter() {
		AtomCollectionAdapter<?> atomCollectionAdapter =
			AtomCollectionAdapterRegistryUtil.getAtomCollectionAdapter(
				TestAtomCollectionAdapter.COLLECTION_NAME);

		Class<?> clazz = atomCollectionAdapter.getClass();

		Assert.assertEquals(
			TestAtomCollectionAdapter.class.getName(), clazz.getName());
	}

	@Test
	public void testGetAtomCollectionAdapters() {
		List<AtomCollectionAdapter<?>> atomCollectionAdapters =
			AtomCollectionAdapterRegistryUtil.getAtomCollectionAdapters();

		Assert.assertTrue(
			TestAtomCollectionAdapter.COLLECTION_NAME + " not found in " +
				atomCollectionAdapters,
			atomCollectionAdapters.removeIf(
				atomCollectionAdapter ->
					TestAtomCollectionAdapter.COLLECTION_NAME.equals(
						atomCollectionAdapter.getCollectionName())));
	}

}