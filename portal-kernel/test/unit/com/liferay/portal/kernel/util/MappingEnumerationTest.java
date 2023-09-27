/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class MappingEnumerationTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testConstructor() {
		Enumeration<Integer> enumeration = Collections.enumeration(
			Collections.singleton(-1));

		MappingEnumeration<Integer, String> mappingEnumeration =
			new MappingEnumeration<>(enumeration, _mapper);

		Assert.assertSame(enumeration, mappingEnumeration.enumeration);

		Assert.assertSame(_mapper, mappingEnumeration.mapper);
		Assert.assertNull(mappingEnumeration.nextElement);

		enumeration = Collections.enumeration(Collections.singleton(1));

		mappingEnumeration = new MappingEnumeration<>(enumeration, _mapper);

		Assert.assertSame(enumeration, mappingEnumeration.enumeration);

		Assert.assertSame(_mapper, mappingEnumeration.mapper);
		Assert.assertEquals("1", mappingEnumeration.nextElement);
	}

	@Test
	public void testEnumeration() {
		Enumeration<String> enumeration = new MappingEnumeration<>(
			Collections.enumeration(Arrays.asList(-1, 0, 1, 2)), _mapper);

		Assert.assertTrue(enumeration.hasMoreElements());
		Assert.assertEquals("1", enumeration.nextElement());
		Assert.assertTrue(enumeration.hasMoreElements());
		Assert.assertEquals("2", enumeration.nextElement());
		Assert.assertFalse(enumeration.hasMoreElements());

		try {
			enumeration.nextElement();

			Assert.fail();
		}
		catch (NoSuchElementException noSuchElementException) {
		}
	}

	private static final MappingEnumeration.Mapper<Integer, String> _mapper =
		new MappingEnumeration.Mapper<Integer, String>() {

			@Override
			public String map(Integer i) {
				if (i <= 0) {
					return null;
				}

				return String.valueOf(i);
			}

		};

}