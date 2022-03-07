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

package com.liferay.document.library.internal.configuration.persistence.listener;

import com.liferay.document.library.internal.configuration.admin.service.DLSizeLimitManagedServiceFactory;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Adolfo Pérez
 */
public class DLSizeLimitConfigurationModelListenerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_dlSizeLimitConfigurationModelListener.
			setDLSizeLimitManagedServiceFactory(
				Mockito.mock(DLSizeLimitManagedServiceFactory.class));
	}

	@Test
	public void testEmptyConfigurationValue() throws Exception {
		_dlSizeLimitConfigurationModelListener.onBeforeSave(
			RandomTestUtil.randomString(),
			HashMapDictionaryBuilder.<String, Object>put(
				"mimeTypeSizeLimit", new String[0]
			).build());
	}

	@Test(expected = ConfigurationModelListenerException.class)
	public void testInvalidMimeType() throws Exception {
		_dlSizeLimitConfigurationModelListener.onBeforeSave(
			RandomTestUtil.randomString(),
			HashMapDictionaryBuilder.<String, Object>put(
				"mimeTypeSizeLimit", new String[] {" type : 12345 "}
			).build());
	}

	@Test
	public void testNullConfigurationValue() throws Exception {
		_dlSizeLimitConfigurationModelListener.onBeforeSave(
			RandomTestUtil.randomString(), new HashMapDictionary<>());
	}

	@Test
	public void testValidMimeType() throws Exception {
		_dlSizeLimitConfigurationModelListener.onBeforeSave(
			RandomTestUtil.randomString(),
			HashMapDictionaryBuilder.<String, Object>put(
				"mimeTypeSizeLimit", new String[] {"image/png:12345"}
			).build());
	}

	@Test
	public void testValidMimeTypeWithBlanks() throws Exception {
		_dlSizeLimitConfigurationModelListener.onBeforeSave(
			RandomTestUtil.randomString(),
			HashMapDictionaryBuilder.<String, Object>put(
				"mimeTypeSizeLimit", new String[] {" image/png : 12345 "}
			).build());
	}

	@Test
	public void testValidSimpleMimeType() throws Exception {
		_dlSizeLimitConfigurationModelListener.onBeforeSave(
			RandomTestUtil.randomString(),
			HashMapDictionaryBuilder.<String, Object>put(
				"mimeTypeSizeLimit", new String[] {"image/png:12345"}
			).build());
	}

	private final DLSizeLimitConfigurationModelListener
		_dlSizeLimitConfigurationModelListener =
			new DLSizeLimitConfigurationModelListener();

}