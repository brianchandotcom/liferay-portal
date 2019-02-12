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

package com.liferay.petra.concurrent;

import com.liferay.petra.memory.FinalizeManager;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.lang.ref.Reference;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class ConcurrentReferenceKeyHashMapTest
	extends BaseConcurrentReferenceHashMapTestCase {

	@ClassRule
	@Rule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testAutoRemove() throws InterruptedException {
		testAutoRemove(
			new ConcurrentReferenceKeyHashMap<String, Object>(
				FinalizeManager.SOFT_REFERENCE_FACTORY),
			true);
		testAutoRemove(
			new ConcurrentReferenceKeyHashMap<String, Object>(
				FinalizeManager.WEAK_REFERENCE_FACTORY),
			false);
	}

	@Test
	public void testConstructor() {
		ConcurrentMap<Reference<String>, Object> innerConcurrentMap =
			new ConcurrentHashMap<>();

		ConcurrentReferenceKeyHashMap<String, Object>
			concurrentReferenceKeyHashMap = new ConcurrentReferenceKeyHashMap<>(
				innerConcurrentMap, FinalizeManager.WEAK_REFERENCE_FACTORY);

		Assert.assertSame(
			innerConcurrentMap,
			concurrentReferenceKeyHashMap.innerConcurrentMap);

		new ConcurrentReferenceKeyHashMap<String, Object>(
			10, FinalizeManager.WEAK_REFERENCE_FACTORY);
		new ConcurrentReferenceKeyHashMap<String, Object>(
			10, 0.75F, 4, FinalizeManager.WEAK_REFERENCE_FACTORY);
	}

	@Test
	public void testPutAll() {
		ConcurrentReferenceKeyHashMap<String, Object>
			concurrentReferenceKeyHashMap = new ConcurrentReferenceKeyHashMap<>(
				FinalizeManager.WEAK_REFERENCE_FACTORY);

		Map<String, Object> dataMap = createDataMap();

		concurrentReferenceKeyHashMap.putAll(dataMap);

		Assert.assertEquals(dataMap, concurrentReferenceKeyHashMap);
	}

}