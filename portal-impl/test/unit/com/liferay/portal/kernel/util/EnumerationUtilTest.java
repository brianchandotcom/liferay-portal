/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

import org.junit.Test;

import org.testng.Assert;

/**
 * @author Carlos Sierra Andrés
 */
public class EnumerationUtilTest {

	@Test
	public void testHasMoreElements() {
		ArrayList<String> list1 = new ArrayList<String>() { {
			add("1");
			add("2");
		}};
		ArrayList<String> list2 = new ArrayList<String>() { {
			add("3");
		}};

		Enumeration<String> empty = Collections.emptyEnumeration();
		Enumeration<String> empty2 = Collections.emptyEnumeration();

		Enumeration composedEnumeration = EnumerationUtil.compose(
			empty, Collections.enumeration(list1), empty, empty2,
			Collections.enumeration(list2));

		Assert.assertTrue(composedEnumeration.hasMoreElements());
		composedEnumeration.nextElement();
		Assert.assertTrue(composedEnumeration.hasMoreElements());
		composedEnumeration.nextElement();
		Assert.assertTrue(composedEnumeration.hasMoreElements());
		composedEnumeration.nextElement();
		Assert.assertFalse(composedEnumeration.hasMoreElements());
	}

	@Test
	public void testNextElement() {
		ArrayList<String> list1 = new ArrayList<String>() { {
			add("1");
			add("2");
		}};
		ArrayList<String> list2 = new ArrayList<String>() { {
			add("3");
		}};

		Enumeration<String> empty = Collections.emptyEnumeration();
		Enumeration<String> empty2 = Collections.emptyEnumeration();

		Enumeration composedEnumeration = EnumerationUtil.compose(
			empty, Collections.enumeration(list1), empty, empty2,
			Collections.enumeration(list2));

		Assert.assertEquals("1", composedEnumeration.nextElement());
		Assert.assertEquals("2", composedEnumeration.nextElement());
		Assert.assertEquals("3", composedEnumeration.nextElement());
	}

}