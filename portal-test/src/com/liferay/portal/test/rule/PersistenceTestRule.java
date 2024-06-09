/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.test.rule;

import com.liferay.osgi.service.tracker.collections.EagerServiceTrackerCustomizer;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.ModelListenerRegistrationUtil;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AbstractTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.runner.Description;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

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

		serviceTrackerBuckets.clear();

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

		serviceTrackerBuckets.putAll(
			ReflectionTestUtil.getFieldValue(
				_excludedModelListeners, "_serviceTrackerBuckets"));

		CacheRegistryUtil.setActive(false);

		UserTestUtil.setUser(TestPropsValues.getUser());

		return copiedServiceTrackerBuckets;
	}

	@Override
	protected void afterClass(Description description, Object object) {
	}

	private PersistenceTestRule() {
	}

	private static final ServiceTrackerMap<String, List<ModelListener<?>>>
		_excludedModelListeners;

	static {
		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		_excludedModelListeners = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext,
			(Class<ModelListener<?>>)(Class<?>)ModelListener.class,
			"(persistence.test.rule.aware=true)",
			(serviceReference, emitter) -> {
				ModelListener<?> modelListener = bundleContext.getService(
					serviceReference);

				Class<?> modelClass = modelListener.getModelClass();

				if (modelClass != null) {
					emitter.emit(modelClass.getName());
				}
			},
			new EagerServiceTrackerCustomizer
				<ModelListener<?>, ModelListener<?>>() {

				@Override
				public ModelListener<?> addingService(
					ServiceReference<ModelListener<?>> serviceReference) {

					return bundleContext.getService(serviceReference);
				}

				@Override
				public void modifiedService(
					ServiceReference<ModelListener<?>> serviceReference,
					ModelListener<?> modelListener) {
				}

				@Override
				public void removedService(
					ServiceReference<ModelListener<?>> serviceReference,
					ModelListener<?> modelListener) {

					bundleContext.ungetService(serviceReference);
				}

			},
			(serviceReference1, serviceReference2) -> {
				ModelListener<?> modelListener1 = bundleContext.getService(
					serviceReference1);

				Class<?> clazz1 = modelListener1.getClass();

				String name1 = clazz1.getName();

				ModelListener<?> modelListener2 = bundleContext.getService(
					serviceReference2);

				Class<?> clazz2 = modelListener2.getClass();

				String name2 = clazz2.getName();

				return name1.compareTo(name2);
			});
	}

}