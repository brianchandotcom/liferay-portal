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

package com.liferay.integration.point.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.xstream.XStreamConverter;
import com.liferay.exportimport.kernel.xstream.XStreamConverterRegistryUtil;
import com.liferay.portal.kernel.format.PhoneNumberFormat;
import com.liferay.portal.kernel.format.PhoneNumberFormatUtil;
import com.liferay.portal.kernel.sanitizer.Sanitizer;
import com.liferay.portal.kernel.sanitizer.SanitizerUtil;
import com.liferay.portal.kernel.security.auth.AuthToken;
import com.liferay.portal.kernel.security.auth.AuthTokenUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ProxyFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Dante Wang
 */
@RunWith(Arquillian.class)
public class IntegrationPointTest {

	@BeforeClass
	public static void setUpClass() {
		Bundle bundle = FrameworkUtil.getBundle(IntegrationPointTest.class);

		_bundleContext = bundle.getBundleContext();
	}

	@After
	public void tearDown() {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}
	}

	@Test
	public void testServiceProxyFactoryIntegrationPoint() {
		AuthToken authToken = ProxyFactory.newDummyInstance(AuthToken.class);

		_serviceRegistration = _bundleContext.registerService(
			AuthToken.class, authToken,
			MapUtil.singletonDictionary("service.ranking", Integer.MAX_VALUE));

		Assert.assertSame(
			authToken,
			ReflectionTestUtil.getFieldValue(
				AuthTokenUtil.class, "_authToken"));
	}

	@Test
	public void testServiceTrackerCustomizerIntegrationPoint() {
		XStreamConverter xStreamConverter = ProxyFactory.newDummyInstance(
			XStreamConverter.class);

		_serviceRegistration = _bundleContext.registerService(
			XStreamConverter.class, xStreamConverter,
			new HashMapDictionary<>());

		Set<XStreamConverter> xStreamConverters =
			XStreamConverterRegistryUtil.getXStreamConverters();

		Assert.assertTrue(
			"Mock XStreamConverter not found in " + xStreamConverters,
			xStreamConverters.removeIf(item -> xStreamConverter == item));
	}

	@Test
	public void testServiceTrackerIntegrationPoint() {
		PhoneNumberFormat phoneNumberFormat = ProxyFactory.newDummyInstance(
			PhoneNumberFormat.class);

		_serviceRegistration = _bundleContext.registerService(
			PhoneNumberFormat.class, phoneNumberFormat,
			MapUtil.singletonDictionary("service.ranking", Integer.MAX_VALUE));

		Assert.assertSame(
			phoneNumberFormat, PhoneNumberFormatUtil.getPhoneNumberFormat());
	}

	@Test
	public void testServiceTrackerListIntegrationPoint() {
		Sanitizer sanitizer = ProxyFactory.newDummyInstance(Sanitizer.class);

		_serviceRegistration = _bundleContext.registerService(
			Sanitizer.class, sanitizer, new HashMapDictionary<>());

		List<Sanitizer> sanitizers = new ArrayList<>(
			ReflectionTestUtil.getFieldValue(
				SanitizerUtil.class, "_sanitizers"));

		Assert.assertTrue(
			"Mock sanitizer not found in " + sanitizers,
			sanitizers.removeIf(item -> sanitizer == item));
	}

	private static BundleContext _bundleContext;

	private ServiceRegistration<?> _serviceRegistration;

}