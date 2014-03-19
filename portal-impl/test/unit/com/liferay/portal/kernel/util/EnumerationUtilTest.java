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

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Test;

import org.testng.Assert;

import edu.emory.mathcs.backport.java.util.Arrays;

/**
 * @author Carlos Sierra Andrés
 */
public class EnumerationUtilTest {

	@Test
	public void testHasMoreElements() {
		Enumeration<String> enumeration1 = Collections.emptyEnumeration();
		Enumeration<String> enumeration2 = Collections.emptyEnumeration();

		List<String> list1 = Arrays.asList(new String[] {"1", "2"});
		List<String> list2 = Arrays.asList(new String[] {"3"});

		Enumeration<String> compositeEnumeration = EnumerationUtil.compose(
			enumeration1, Collections.enumeration(list1), enumeration1,
			enumeration2, Collections.enumeration(list2));

		Assert.assertTrue(compositeEnumeration.hasMoreElements());

		compositeEnumeration.nextElement();

		Assert.assertTrue(compositeEnumeration.hasMoreElements());

		compositeEnumeration.nextElement();

		Assert.assertTrue(compositeEnumeration.hasMoreElements());

		compositeEnumeration.nextElement();

		Assert.assertFalse(compositeEnumeration.hasMoreElements());
	}

	@Test
	public void testNextElement() {
		Enumeration<String> enumeration1 = Collections.emptyEnumeration();
		Enumeration<String> enumeration2 = Collections.emptyEnumeration();

		List<String> list1 = Arrays.asList(new String[] {"1", "2"});
		List<String> list2 = Arrays.asList(new String[] {"3"});

		Enumeration<String> compositeEnumeration = EnumerationUtil.compose(
			enumeration1, Collections.enumeration(list1), enumeration1,
			enumeration2, Collections.enumeration(list2));

		Assert.assertEquals("1", compositeEnumeration.nextElement());
		Assert.assertEquals("2", compositeEnumeration.nextElement());
		Assert.assertEquals("3", compositeEnumeration.nextElement());
	}

	@Test(expected = NoSuchElementException.class)
	public void testNextElementThrowsException() {
		Enumeration<?> compositeEnumeration = EnumerationUtil.compose(
			Collections.emptyEnumeration());

		compositeEnumeration.nextElement();
	}

}