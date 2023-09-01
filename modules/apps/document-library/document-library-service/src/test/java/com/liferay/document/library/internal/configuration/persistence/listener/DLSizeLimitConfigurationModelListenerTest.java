/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.configuration.persistence.listener;

import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Adolfo Pérez
 */
public class DLSizeLimitConfigurationModelListenerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

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