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

package com.liferay.portal.test.rule;

import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.model.ModelListenerRegistrationUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AbstractTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;

import java.util.HashMap;
import java.util.Map;

import org.junit.runner.Description;

/**
 * @author Shuyang Zhou
 */
public class PersistenceTestRule extends AbstractTestRule<Object, Object> {

	public static final PersistenceTestRule INSTANCE =
		new PersistenceTestRule();

	@Override
	public void afterMethod(
		Description description, Object copiedServiceTrackerBuckets,
		Object target) {

		CacheRegistryUtil.setActive(true);

		Object modelListeners = ReflectionTestUtil.getFieldValue(
			ModelListenerRegistrationUtil.class, "_modelListeners");

		Map<Object, Object> serviceTrackerBuckets =
			ReflectionTestUtil.getFieldValue(
				modelListeners, "_serviceTrackerBuckets");

		serviceTrackerBuckets.putAll(
			(Map<Object, Object>)copiedServiceTrackerBuckets);
	}

	@Override
	public Object beforeClass(Description description) {
		return null;
	}

	@Override
	public Object beforeMethod(Description description, Object target)
		throws Exception {

		Object modelListeners = ReflectionTestUtil.getFieldValue(
			ModelListenerRegistrationUtil.class, "_modelListeners");

		Map<Object, Object> serviceTrackerBuckets =
			ReflectionTestUtil.getFieldValue(
				modelListeners, "_serviceTrackerBuckets");

		Map<Object, Object> copiedServiceTrackerBuckets = new HashMap<>(
			serviceTrackerBuckets);

		serviceTrackerBuckets.clear();

		CacheRegistryUtil.setActive(false);

		UserTestUtil.setUser(TestPropsValues.getUser());

		return copiedServiceTrackerBuckets;
	}

	@Override
	protected void afterClass(Description description, Object object) {
	}

	private PersistenceTestRule() {
	}

}