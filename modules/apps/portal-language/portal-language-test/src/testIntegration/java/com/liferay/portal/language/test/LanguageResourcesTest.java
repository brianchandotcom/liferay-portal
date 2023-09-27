/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.language.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Preston Crary
 */
@RunWith(Arquillian.class)
public class LanguageResourcesTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
		Bundle bundle = FrameworkUtil.getBundle(LanguageResourcesTest.class);

		_bundleContext = bundle.getBundleContext();

		_locale = LocaleUtil.getDefault();

		_languageId = _language.getLanguageId(_locale);
	}

	@After
	public void tearDown() {
		_unregister(_serviceRegistration1);
		_unregister(_serviceRegistration2);
		_unregister(_serviceRegistration3);
	}

	@Test
	public void testGet() {

		// Default locale

		Assert.assertEquals("Year", _language.get(_locale, "year", null));

		// Supported locale

		Assert.assertEquals(
			"Année", _language.get(LocaleUtil.FRANCE, "year", null));

		// Unsupported locale

		Assert.assertEquals(
			"Year", _language.get(new Locale("ps", "AF"), "year", null));
	}

	@Test
	public void testLanguageResourceServiceTrackerCustomizer() {
		_assertValue(null);

		_serviceRegistration1 = _register(_VALUE_1, 0);

		_assertValue(_VALUE_1);

		_serviceRegistration2 = _register(_VALUE_2, 0);

		_assertValue(_VALUE_1);

		_serviceRegistration3 = _register(_VALUE_3, 0);

		_assertValue(_VALUE_1);

		_serviceRegistration1 = _unregister(_serviceRegistration1);

		_assertValue(_VALUE_2);

		_serviceRegistration2 = _unregister(_serviceRegistration2);

		_assertValue(_VALUE_3);

		_serviceRegistration3 = _unregister(_serviceRegistration3);

		_assertValue(null);
	}

	@Test
	public void testLanguageResourceServiceTrackerCustomizerServiceRanking() {
		_assertValue(null);

		_serviceRegistration2 = _register(_VALUE_2, 2);

		_assertValue(_VALUE_2);

		_serviceRegistration3 = _register(_VALUE_3, 3);

		_assertValue(_VALUE_3);

		_serviceRegistration1 = _register(_VALUE_1, 1);

		_assertValue(_VALUE_3);

		_serviceRegistration3 = _unregister(_serviceRegistration3);

		_assertValue(_VALUE_2);

		_serviceRegistration2 = _unregister(_serviceRegistration2);

		_assertValue(_VALUE_1);

		_serviceRegistration1 = _unregister(_serviceRegistration1);

		_assertValue(null);
	}

	private void _assertValue(String expectedValue) {
		Assert.assertEquals(
			expectedValue,
			_language.get(_locale, TestResourceBundle.class.getName(), null));
	}

	private ServiceRegistration<?> _register(String value, int serviceRanking) {
		return _bundleContext.registerService(
			ResourceBundle.class, new TestResourceBundle(value),
			HashMapDictionaryBuilder.<String, Object>put(
				Constants.SERVICE_RANKING, serviceRanking
			).put(
				"language.id", _languageId
			).build());
	}

	private ServiceRegistration<?> _unregister(
		ServiceRegistration<?> serviceRegistration) {

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}

		return null;
	}

	private static final String _VALUE_1 = "value 1";

	private static final String _VALUE_2 = "value 2";

	private static final String _VALUE_3 = "value 3";

	@Inject
	private static Language _language;

	private BundleContext _bundleContext;
	private String _languageId;
	private Locale _locale;
	private ServiceRegistration<?> _serviceRegistration1;
	private ServiceRegistration<?> _serviceRegistration2;
	private ServiceRegistration<?> _serviceRegistration3;

	private static class TestResourceBundle extends ResourceBundle {

		@Override
		public Enumeration<String> getKeys() {
			return Collections.enumeration(handleKeySet());
		}

		@Override
		protected Object handleGetObject(String key) {
			if (key.equals(TestResourceBundle.class.getName())) {
				return _value;
			}

			return null;
		}

		@Override
		protected Set<String> handleKeySet() {
			return Collections.singleton(TestResourceBundle.class.getName());
		}

		private TestResourceBundle(String value) {
			_value = value;
		}

		private final String _value;

	}

}