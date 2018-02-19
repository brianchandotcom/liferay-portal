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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.kernel.test.rule.NewEnvTestRule;

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
public class ConcurrentReferenceValueHashMapTest
	extends BaseConcurrentReferenceHashMapTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			CodeCoverageAssertor.INSTANCE, NewEnvTestRule.INSTANCE);

	@NewEnv(type = NewEnv.Type.CLASSLOADER)
	@Test
	public void testAutoRemove() throws InterruptedException {
		System.setProperty(
			FinalizeManager.class.getName() + ".thread.enabled",
			StringPool.FALSE);

		testAutoRemove(
			new ConcurrentReferenceValueHashMap<String, Object>(
				FinalizeManager.SOFT_REFERENCE_FACTORY),
			true);
		testAutoRemove(
			new ConcurrentReferenceValueHashMap<String, Object>(
				FinalizeManager.WEAK_REFERENCE_FACTORY),
			false);
	}

	@Test
	public void testConstructor() {
		ConcurrentMap<String, Reference<Object>> innerConcurrentMap =
			new ConcurrentHashMap<>();

		ConcurrentReferenceValueHashMap<String, Object>
			concurrentReferenceValueHashMap =
				new ConcurrentReferenceValueHashMap<>(
					innerConcurrentMap, FinalizeManager.WEAK_REFERENCE_FACTORY);

		Assert.assertSame(
			innerConcurrentMap,
			concurrentReferenceValueHashMap.innerConcurrentMap);

		Map<String, Object> dataMap = createDataMap();

		concurrentReferenceValueHashMap = new ConcurrentReferenceValueHashMap<>(
			dataMap, FinalizeManager.WEAK_REFERENCE_FACTORY);

		Assert.assertEquals(dataMap, concurrentReferenceValueHashMap);

		new ConcurrentReferenceValueHashMap<String, Object>(
			10, FinalizeManager.WEAK_REFERENCE_FACTORY);
		new ConcurrentReferenceValueHashMap<String, Object>(
			10, 0.75F, 4, FinalizeManager.WEAK_REFERENCE_FACTORY);
	}

}