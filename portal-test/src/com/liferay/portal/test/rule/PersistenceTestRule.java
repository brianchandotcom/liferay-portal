/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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