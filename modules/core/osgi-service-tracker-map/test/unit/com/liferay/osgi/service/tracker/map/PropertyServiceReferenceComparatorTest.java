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

package com.liferay.osgi.service.tracker.map;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceReference;

/**
 * @author Carlos Sierra Andrés
 */
public class PropertyServiceReferenceComparatorTest {

	@Test
	public void testCompare() {
		PropertyServiceReferenceComparator<Object> comparator =
			new PropertyServiceReferenceComparator<>("ranking");

		ServiceReference<Object> sr1 = new MockServiceReference<>(
			new HashMap<String, Object>() { {
				put("ranking", 1);
			}});
		ServiceReference<Object> sr2 = new MockServiceReference<>(
			new HashMap<String, Object>() { {
				put("ranking", 2);
			}});

		Assert.assertTrue(comparator.compare(sr2, sr1) < 0);
		Assert.assertEquals(
			comparator.compare(sr1, sr2), -comparator.compare(sr2, sr1));

		ServiceReference<Object> sr3 = new MockServiceReference<>(
			new HashMap<String, Object>() { {
				put("ranking", 1);
			}});

		Assert.assertEquals(0, comparator.compare(sr1, sr3));
	}

	@Test
	public void testCompareIsTransitiveWithNulls() {
		PropertyServiceReferenceComparator<Object> comparator =
			new PropertyServiceReferenceComparator<>("ranking");

		ServiceReference<Object> lower = new MockServiceReference<>(
			new HashMap<String, Object>() { {
				put("ranking", -1);
			}});
		ServiceReference<Object> zero = new MockServiceReference<>(
			new HashMap<String, Object>() { {
				put("ranking", 0);
			}});
		ServiceReference<Object> higher = new MockServiceReference<>(
			new HashMap<String, Object>() { {
				put("ranking", 1);
			}});

		Assert.assertTrue(comparator.compare(null, lower) > 0);
		Assert.assertTrue(comparator.compare(lower, zero) > 0);
		Assert.assertTrue(comparator.compare(null, zero) > 0);

		Assert.assertTrue(comparator.compare(null, zero) > 0);
		Assert.assertTrue(comparator.compare(zero, higher) > 0);
		Assert.assertTrue(comparator.compare(null, higher) > 0);
	}

	@Test
	public void testCompareIsTransitiveWithNullValues() {
		PropertyServiceReferenceComparator<Object> comparator =
			new PropertyServiceReferenceComparator<>("ranking");

		ServiceReference<Object> lower = new MockServiceReference<>(
			new HashMap<String, Object>() { {
				put("ranking", -1);
			}});
		ServiceReference<Object> zero = new MockServiceReference<>(
			new HashMap<String, Object>() { {
				put("ranking", 0);
			}});
		ServiceReference<Object> higher = new MockServiceReference<>(
			new HashMap<String, Object>() { {
				put("ranking", 1);
			}});
		ServiceReference<Object> nullRef = new MockServiceReference<>(
			new HashMap<String, Object>() { {
			}});

		Assert.assertTrue(comparator.compare(nullRef, lower) > 0);
		Assert.assertTrue(comparator.compare(lower, zero) > 0);
		Assert.assertTrue(comparator.compare(nullRef, zero) > 0);

		Assert.assertTrue(comparator.compare(nullRef, zero) > 0);
		Assert.assertTrue(comparator.compare(zero, higher) > 0);
		Assert.assertTrue(comparator.compare(nullRef, higher) > 0);
	}

	@Test
	public void testCompareWithNulls() {
		PropertyServiceReferenceComparator<Object> comparator =
			new PropertyServiceReferenceComparator<>("ranking");

		ServiceReference<Object> sr1 = new MockServiceReference<>(
			new HashMap<String, Object>() { {
				put("ranking", 1);
			}});

		Assert.assertEquals(0, comparator.compare(null, null));
		Assert.assertTrue(comparator.compare(sr1, null) < 0);
		Assert.assertTrue(
			comparator.compare(sr1, null) == -comparator.compare(null, sr1));
	}

	@Test
	public void testCompareWithNullValues() {
		PropertyServiceReferenceComparator<Object> comparator =
			new PropertyServiceReferenceComparator<>("ranking");

		ServiceReference<Object> sr1 = new MockServiceReference<>(
			new HashMap<String, Object>() { {
				put("ranking", 1);
			}});
		ServiceReference<Object> sr2 = new MockServiceReference<>(
			new HashMap<String, Object>() { {
			}});

		Assert.assertTrue(comparator.compare(sr1, sr2) < 0);
		Assert.assertEquals(
			comparator.compare(sr1, sr2), -comparator.compare(sr2, sr1));

		ServiceReference<Object> sr3 = new MockServiceReference<>(
			new HashMap<String, Object>() { {
			}});

		Assert.assertEquals(0, comparator.compare(sr2, sr3));
		Assert.assertEquals(
			comparator.compare(sr2, sr3), -comparator.compare(sr3, sr2));
	}

	private class MockServiceReference<S> implements ServiceReference<S> {

		public MockServiceReference(Map<String, Object> properties) {
			_properties = properties;
		}

		@Override
		public int compareTo(Object object) {
			if (object == null) {
				return 1;
			}

			return toString().compareTo(object.toString());
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
			return _properties.keySet().toArray(new String[0]);
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