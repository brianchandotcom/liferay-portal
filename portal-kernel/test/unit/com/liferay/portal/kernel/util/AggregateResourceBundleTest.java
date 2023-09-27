/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import java.util.ListResourceBundle;
import java.util.ResourceBundle;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Carlos Sierra Andrés
 */
public class AggregateResourceBundleTest {

	@Test
	public void testGetKeyFromFirstBundle() {
		ResourceBundle resourceBundleA = createResourceBundle("keyA", "valueA");
		ResourceBundle resourceBundleB = createResourceBundle("keyB", "valueB");

		AggregateResourceBundle aggregateResourceBundle =
			new AggregateResourceBundle(resourceBundleA, resourceBundleB);

		Assert.assertEquals(
			"valueA", aggregateResourceBundle.getString("keyA"));
		Assert.assertEquals(
			SetUtil.fromArray("keyA", "keyB"),
			aggregateResourceBundle.keySet());
	}

	@Test
	public void testGetKeyFromSecondBundle() {
		ResourceBundle resourceBundleA = createResourceBundle("keyA", "valueA");
		ResourceBundle resourceBundleB = createResourceBundle("keyB", "valueB");

		AggregateResourceBundle aggregateResourceBundle =
			new AggregateResourceBundle(resourceBundleA, resourceBundleB);

		Assert.assertEquals(
			"valueB", aggregateResourceBundle.getString("keyB"));
	}

	@Test
	public void testKeySet() {
		ResourceBundle resourceBundleA = createResourceBundle("keyA", "valueA");
		ResourceBundle resourceBundleB = createResourceBundle("keyB", "valueB");

		AggregateResourceBundle aggregateResourceBundle =
			new AggregateResourceBundle(resourceBundleA, resourceBundleB);

		Assert.assertEquals(
			SetUtil.fromArray("keyA", "keyB"),
			aggregateResourceBundle.keySet());
	}

	@Test
	public void testOverriddenKeys() {
		ResourceBundle resourceBundleA = createResourceBundle("keyA", "valueA");
		ResourceBundle resourceBundleB = createResourceBundle("keyA", "valueB");

		AggregateResourceBundle aggregateResourceBundle =
			new AggregateResourceBundle(resourceBundleA, resourceBundleB);

		Assert.assertEquals(
			"valueA", aggregateResourceBundle.getString("keyA"));

		aggregateResourceBundle = new AggregateResourceBundle(
			resourceBundleB, resourceBundleA);

		Assert.assertEquals(
			"valueB", aggregateResourceBundle.getString("keyA"));
	}

	protected ResourceBundle createResourceBundle(
		final String... keysAndValues) {

		if ((keysAndValues.length % 2) != 0) {
			throw new RuntimeException(
				"Keys and values length is not an even number");
		}

		return new ListResourceBundle() {

			@Override
			protected Object[][] getContents() {
				Object[][] contents = new Object[keysAndValues.length / 2][];

				for (int i = 0; i < contents.length; i++) {
					contents[i] = new Object[] {
						keysAndValues[i * 2], keysAndValues[(i * 2) + 1]
					};
				}

				return contents;
			}

		};
	}

}