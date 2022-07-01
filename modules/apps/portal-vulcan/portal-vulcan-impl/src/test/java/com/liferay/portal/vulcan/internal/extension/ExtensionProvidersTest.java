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

package com.liferay.portal.vulcan.internal.extension;

import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.vulcan.extension.ExtensionProvider;

import java.io.Serializable;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Javier de Arcos
 */
public class ExtensionProvidersTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		_extensionProviders = new ExtensionProviders(
			_CLASS_NAME_TEST,
			Arrays.asList(
				_mockedExtensionProvider1, _mockedExtensionProvider2));
	}

	@Test
	public void testGetExtendedProperties() throws Exception {
		Map<String, Serializable> testMap1 = Collections.singletonMap(
			"test1", "test");
		Map<String, Serializable> testMap2 = Collections.singletonMap(
			"test2", 5);

		Mockito.when(
			_mockedExtensionProvider1.getExtendedProperties(
				Mockito.anyLong(), Mockito.anyObject())
		).thenReturn(
			testMap1
		);
		Mockito.when(
			_mockedExtensionProvider2.getExtendedProperties(
				Mockito.anyLong(), Mockito.anyObject())
		).thenReturn(
			testMap2
		);

		Map<String, Serializable> extendedProperties =
			_extensionProviders.getExtendedProperties(
				_COMPANY_ID_TEST, _OBJECT_TEST);

		Mockito.verify(
			_mockedExtensionProvider1
		).getExtendedProperties(
			Mockito.eq(_COMPANY_ID_TEST), Mockito.eq(_OBJECT_TEST)
		);
		Mockito.verify(
			_mockedExtensionProvider2
		).getExtendedProperties(
			Mockito.eq(_COMPANY_ID_TEST), Mockito.eq(_OBJECT_TEST)
		);

		Assert.assertEquals(
			extendedProperties.toString(), 2, extendedProperties.size());
		Assert.assertEquals("test", extendedProperties.get("test1"));
		Assert.assertEquals(5, extendedProperties.get("test2"));
	}

	@Test
	public void testGetFilteredPropertyNames() {
		Set<String> testSet1 = Collections.singleton("test1");
		Set<String> testSet2 = Collections.singleton("test2");

		Mockito.doReturn(
			testSet1
		).when(
			_mockedExtensionProvider1
		).getFilteredPropertyNames(
			Mockito.anyLong(), Mockito.anyObject()
		);
		Mockito.doReturn(
			testSet2
		).when(
			_mockedExtensionProvider2
		).getFilteredPropertyNames(
			Mockito.anyLong(), Mockito.anyObject()
		);

		Set<String> filteredProperties =
			_extensionProviders.getFilteredPropertyNames(
				_COMPANY_ID_TEST, _OBJECT_TEST);

		Mockito.verify(
			_mockedExtensionProvider1
		).getFilteredPropertyNames(
			Mockito.eq(_COMPANY_ID_TEST), Mockito.eq(_OBJECT_TEST)
		);
		Mockito.verify(
			_mockedExtensionProvider2
		).getFilteredPropertyNames(
			Mockito.eq(_COMPANY_ID_TEST), Mockito.eq(_OBJECT_TEST)
		);

		Assert.assertEquals(
			filteredProperties.toString(), 2, filteredProperties.size());
		Assert.assertTrue(filteredProperties.contains("test1"));
		Assert.assertTrue(filteredProperties.contains("test2"));
	}

	@Test
	public void testSetExtendedProperties() throws Exception {
		Map<String, Serializable> testExtendedProperties =
			HashMapBuilder.<String, Serializable>put(
				"test1", "test"
			).put(
				"test2", 5
			).build();

		Mockito.when(
			_mockedExtensionProvider1.getExtendedPropertyDefinitions(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			Collections.singletonMap("test1", null)
		);
		Mockito.when(
			_mockedExtensionProvider2.getExtendedPropertyDefinitions(
				Mockito.anyLong(), Mockito.anyString())
		).thenReturn(
			Collections.singletonMap("test2", null)
		);

		_extensionProviders.setExtendedProperties(
			_COMPANY_ID_TEST, _OBJECT_TEST, testExtendedProperties);

		Mockito.verify(
			_mockedExtensionProvider1
		).getExtendedPropertyDefinitions(
			Mockito.eq(_COMPANY_ID_TEST), Mockito.eq(_CLASS_NAME_TEST)
		);
		Mockito.verify(
			_mockedExtensionProvider2
		).getExtendedPropertyDefinitions(
			Mockito.eq(_COMPANY_ID_TEST), Mockito.eq(_CLASS_NAME_TEST)
		);
		Mockito.verify(
			_mockedExtensionProvider1
		).setExtendedProperties(
			Mockito.eq(_COMPANY_ID_TEST), Mockito.eq(_OBJECT_TEST),
			Mockito.eq(Collections.singletonMap("test1", "test"))
		);
		Mockito.verify(
			_mockedExtensionProvider2
		).setExtendedProperties(
			Mockito.eq(_COMPANY_ID_TEST), Mockito.eq(_OBJECT_TEST),
			Mockito.eq(Collections.singletonMap("test2", 5))
		);
	}

	private static final String _CLASS_NAME_TEST =
		"com.liferay.test.model.TestModel";

	private static final long _COMPANY_ID_TEST = 11111;

	private static final Object _OBJECT_TEST = new Object();

	private ExtensionProviders _extensionProviders;

	@Mock
	private ExtensionProvider _mockedExtensionProvider1;

	@Mock
	private ExtensionProvider _mockedExtensionProvider2;

}