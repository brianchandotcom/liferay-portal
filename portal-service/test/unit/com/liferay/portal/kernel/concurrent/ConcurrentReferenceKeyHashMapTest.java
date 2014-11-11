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

package com.liferay.portal.kernel.concurrent;

import com.liferay.portal.kernel.memory.FinalizeManager;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.NewEnv;
import com.liferay.portal.kernel.test.NewEnvMethodRule;
import com.liferay.portal.kernel.util.StringPool;

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
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@NewEnv(type = NewEnv.Type.CLASSLOADER)
	@Test
	public void testAutoRemove() throws InterruptedException {
		System.setProperty(
			FinalizeManager.class.getName() + ".thread.enabled",
			StringPool.FALSE);

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
			new ConcurrentHashMap<Reference<String>, Object>();

		ConcurrentReferenceKeyHashMap<String, Object>
			concurrentReferenceKeyHashMap =
				new ConcurrentReferenceKeyHashMap<String, Object>(
					innerConcurrentMap, FinalizeManager.WEAK_REFERENCE_FACTORY);

		Assert.assertSame(
			innerConcurrentMap,
			concurrentReferenceKeyHashMap.innerConcurrentMap);

		Map<String, Object> dataMap = createDataMap();

		concurrentReferenceKeyHashMap =
			new ConcurrentReferenceKeyHashMap<String, Object>(
				dataMap, FinalizeManager.WEAK_REFERENCE_FACTORY);

		Assert.assertEquals(dataMap, concurrentReferenceKeyHashMap);

		new ConcurrentReferenceKeyHashMap<String, Object>(
			10, FinalizeManager.WEAK_REFERENCE_FACTORY);
		new ConcurrentReferenceKeyHashMap<String, Object>(
			10, 0.75F, 4, FinalizeManager.WEAK_REFERENCE_FACTORY);
	}

	@Rule
	public final NewEnvMethodRule newEnvMethodRule = new NewEnvMethodRule();

}