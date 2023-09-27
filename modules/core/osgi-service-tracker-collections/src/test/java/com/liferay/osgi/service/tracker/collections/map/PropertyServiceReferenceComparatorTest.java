/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osgi.service.tracker.collections.map;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceReference;

/**
 * @author Carlos Sierra Andrés
 */
public class PropertyServiceReferenceComparatorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testCompare() {
		PropertyServiceReferenceComparator<Object>
			propertyServiceReferenceComparator =
				new PropertyServiceReferenceComparator<>("ranking");

		ServiceReference<Object> serviceReference1 = new TestServiceReference<>(
			"ranking", 1);
		ServiceReference<Object> serviceReference2 = new TestServiceReference<>(
			"ranking", 2);

		int compare = propertyServiceReferenceComparator.compare(
			serviceReference2, serviceReference1);

		Assert.assertTrue(compare < 0);

		Assert.assertEquals(
			propertyServiceReferenceComparator.compare(
				serviceReference1, serviceReference2),
			-propertyServiceReferenceComparator.compare(
				serviceReference2, serviceReference1));

		ServiceReference<Object> serviceReference3 = new TestServiceReference<>(
			"ranking", 1);

		Assert.assertEquals(
			0,
			propertyServiceReferenceComparator.compare(
				serviceReference1, serviceReference3));
	}

	@Test
	public void testCompareIsTransitiveWhenServiceReferencePropertiesAreNull() {
		PropertyServiceReferenceComparator<Object>
			propertyServiceReferenceComparator =
				new PropertyServiceReferenceComparator<>("ranking");

		ServiceReference<Object> serviceReference1 = new TestServiceReference<>(
			"ranking", -1);
		ServiceReference<Object> serviceReference2 = new TestServiceReference<>(
			"ranking", 0);
		ServiceReference<Object> serviceReference3 = new TestServiceReference<>(
			"ranking", 1);
		ServiceReference<Object> serviceReference4 =
			new TestServiceReference<>();

		int compare1 = propertyServiceReferenceComparator.compare(
			serviceReference4, serviceReference1);

		Assert.assertTrue(compare1 > 0);

		int compare2 = propertyServiceReferenceComparator.compare(
			serviceReference1, serviceReference2);

		Assert.assertTrue(compare2 > 0);

		int compare3 = propertyServiceReferenceComparator.compare(
			serviceReference4, serviceReference2);

		Assert.assertTrue(compare3 > 0);

		int compare4 = propertyServiceReferenceComparator.compare(
			serviceReference4, serviceReference2);

		Assert.assertTrue(compare4 > 0);

		int compare5 = propertyServiceReferenceComparator.compare(
			serviceReference2, serviceReference3);

		Assert.assertTrue(compare5 > 0);

		int compare6 = propertyServiceReferenceComparator.compare(
			serviceReference4, serviceReference3);

		Assert.assertTrue(compare6 > 0);
	}

	@Test
	public void testCompareIsTransitiveWhenServiceReferencesAreNull() {
		PropertyServiceReferenceComparator<Object>
			propertyServiceReferenceComparator =
				new PropertyServiceReferenceComparator<>("ranking");

		ServiceReference<Object> serviceReference1 = new TestServiceReference<>(
			"ranking", -1);
		ServiceReference<Object> serviceReference2 = new TestServiceReference<>(
			"ranking", 0);
		ServiceReference<Object> serviceReference3 = new TestServiceReference<>(
			"ranking", 1);

		int compare1 = propertyServiceReferenceComparator.compare(
			null, serviceReference1);

		Assert.assertTrue(compare1 > 0);

		int compare2 = propertyServiceReferenceComparator.compare(
			serviceReference1, serviceReference2);

		Assert.assertTrue(compare2 > 0);

		int compare3 = propertyServiceReferenceComparator.compare(
			null, serviceReference2);

		Assert.assertTrue(compare3 > 0);

		int compare4 = propertyServiceReferenceComparator.compare(
			null, serviceReference2);

		Assert.assertTrue(compare4 > 0);

		int compare5 = propertyServiceReferenceComparator.compare(
			serviceReference2, serviceReference3);

		Assert.assertTrue(compare5 > 0);

		int compare6 = propertyServiceReferenceComparator.compare(
			null, serviceReference3);

		Assert.assertTrue(compare6 > 0);
	}

	@Test
	public void testCompareWhenServiceReferencePropertiesAreNull() {
		PropertyServiceReferenceComparator<Object>
			propertyServiceReferenceComparator =
				new PropertyServiceReferenceComparator<>("ranking");

		ServiceReference<Object> serviceReference1 = new TestServiceReference<>(
			"ranking", 1);
		ServiceReference<Object> serviceReference2 =
			new TestServiceReference<>();

		int compare = propertyServiceReferenceComparator.compare(
			serviceReference1, serviceReference2);

		Assert.assertTrue(compare < 0);

		Assert.assertEquals(
			propertyServiceReferenceComparator.compare(
				serviceReference1, serviceReference2),
			-propertyServiceReferenceComparator.compare(
				serviceReference2, serviceReference1));

		ServiceReference<Object> serviceReference3 =
			new TestServiceReference<>();

		Assert.assertEquals(
			0,
			propertyServiceReferenceComparator.compare(
				serviceReference2, serviceReference3));
		Assert.assertEquals(
			propertyServiceReferenceComparator.compare(
				serviceReference2, serviceReference3),
			-propertyServiceReferenceComparator.compare(
				serviceReference3, serviceReference2));
	}

	@Test
	public void testCompareWhenServiceReferencesAreNull() {
		PropertyServiceReferenceComparator<Object>
			propertyServiceReferenceComparator =
				new PropertyServiceReferenceComparator<>("ranking");

		ServiceReference<Object> serviceReference1 = new TestServiceReference<>(
			"ranking", 1);

		Assert.assertEquals(
			0, propertyServiceReferenceComparator.compare(null, null));

		int compare = propertyServiceReferenceComparator.compare(
			serviceReference1, null);

		Assert.assertTrue(compare < 0);

		Assert.assertEquals(
			propertyServiceReferenceComparator.compare(serviceReference1, null),
			-propertyServiceReferenceComparator.compare(
				null, serviceReference1));
	}

	private static class TestServiceReference<S>
		implements ServiceReference<S> {

		public TestServiceReference(Object... arguments) {
			Map<String, Object> properties = new HashMap<>();

			for (int i = 0; i < arguments.length; i += 2) {
				String key = String.valueOf(arguments[i]);
				Object value = arguments[i + 1];

				properties.put(key, value);
			}

			_properties = properties;
		}

		@Override
		public int compareTo(Object object) {
			if (object == null) {
				return 1;
			}

			String s = toString();

			return s.compareTo(object.toString());
		}

		@Override
		public Bundle getBundle() {
			throw new UnsupportedOperationException();
		}

		@Override
		public Object getProperty(String key) {
			return _properties.get(key);
		}

		@Override
		public String[] getPropertyKeys() {
			Set<String> keys = _properties.keySet();

			return keys.toArray(new String[0]);
		}

		@Override
		public Bundle[] getUsingBundles() {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean isAssignableTo(Bundle bundle, String className) {
			throw new UnsupportedOperationException();
		}

		private final Map<String, Object> _properties;

	}

}