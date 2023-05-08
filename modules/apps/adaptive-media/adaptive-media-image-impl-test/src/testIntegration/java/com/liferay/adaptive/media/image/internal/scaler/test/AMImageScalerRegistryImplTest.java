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

package com.liferay.adaptive.media.image.internal.scaler.test;

import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntry;
import com.liferay.adaptive.media.image.scaler.AMImageScaledImage;
import com.liferay.adaptive.media.image.scaler.AMImageScaler;
import com.liferay.adaptive.media.image.scaler.AMImageScalerRegistry;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.runtime.ServiceComponentRuntime;
import org.osgi.service.component.runtime.dto.ComponentDescriptionDTO;
import org.osgi.util.promise.Promise;

/**
 * @author Sergio González
 */
@RunWith(Arquillian.class)
public class AMImageScalerRegistryImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAMImageScalerRegistryLogsWhenThereIsNoDefaultAMImageScaler()
		throws Exception {

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_CLASS_NAME_ADAPTIVE_MEDIA_IMAGE_SCALER_TRACKER_IMPL,
				LoggerTestUtil.WARN)) {

			_disableAMDefaultImageScaler();

			_amImageScalerRegistry.getAMImageScaler(
				RandomTestUtil.randomString());

			_enableAMDefaultImageScaler();

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				"Unable to find default image scaler", logEntry.getMessage());
		}
	}

	@Test
	public void testAMImageScalerRegistryLogsWhenThereIsNoDefaultAMImageScalerEnabled()
		throws Exception {

		ServiceRegistration<AMImageScaler> amImageScalerServiceRegistration =
			null;

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_CLASS_NAME_ADAPTIVE_MEDIA_IMAGE_SCALER_TRACKER_IMPL,
				LoggerTestUtil.WARN)) {

			_disableAMDefaultImageScaler();

			AMImageScaler disabledAMImageScaler = new TestAMImageScaler(false);

			amImageScalerServiceRegistration = _registerAMImageScaler(
				disabledAMImageScaler, "*", 10);

			_amImageScalerRegistry.getAMImageScaler(
				RandomTestUtil.randomString());

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(logEntries.toString(), 1, logEntries.size());

			LogEntry logEntry = logEntries.get(0);

			Assert.assertEquals(
				"Unable to find default image scaler", logEntry.getMessage());
		}
		finally {
			_unregisterAMImageScaler(amImageScalerServiceRegistration);

			_enableAMDefaultImageScaler();
		}
	}

	@Test
	public void testAMImageScalerRegistryReturnsAMDefaultImageScalerWhenAsteriskMimeType()
		throws Exception {

		Assert.assertEquals(
			_amDefaultImageScaler,
			_amImageScalerRegistry.getAMImageScaler("*"));
	}

	@Test
	public void testAMImageScalerRegistryReturnsAMDefaultImageScalerWhenRandomMimeType()
		throws Exception {

		Assert.assertEquals(
			_amDefaultImageScaler,
			_amImageScalerRegistry.getAMImageScaler(
				RandomTestUtil.randomString()));
	}

	@Test
	public void testAMImageScalerRegistryReturnsAMDefaultImageScalerWhenThereIsNoAMImageScalerEnabled()
		throws Exception {

		ServiceRegistration<AMImageScaler> amImageScalerServiceRegistration =
			null;

		try {
			AMImageScaler disabledAMImageScaler = new TestAMImageScaler(false);

			amImageScalerServiceRegistration = _registerAMImageScaler(
				disabledAMImageScaler, "image/test", 10);

			Assert.assertEquals(
				_amDefaultImageScaler,
				_amImageScalerRegistry.getAMImageScaler("image/test"));
		}
		finally {
			_unregisterAMImageScaler(amImageScalerServiceRegistration);
		}
	}

	@Test
	public void testAMImageScalerRegistryReturnsAMImageScalerForMimeType()
		throws Exception {

		AMImageScaler testAMImageScaler = new TestAMImageScaler(true);

		ServiceRegistration<AMImageScaler> amImageScalerServiceRegistration =
			null;

		try {
			amImageScalerServiceRegistration = _registerAMImageScaler(
				testAMImageScaler, "image/test", 10);

			Assert.assertEquals(
				testAMImageScaler,
				_amImageScalerRegistry.getAMImageScaler("image/test"));
		}
		finally {
			_unregisterAMImageScaler(amImageScalerServiceRegistration);
		}
	}

	@Test
	public void testAMImageScalerRegistryReturnsAMImageScalerWithHigherServiceRankingIfEnabled()
		throws Exception {

		ServiceRegistration<AMImageScaler> amImageScalerServiceRegistration1 =
			null;
		ServiceRegistration<AMImageScaler> amImageScalerServiceRegistration2 =
			null;
		ServiceRegistration<AMImageScaler> amImageScalerServiceRegistration3 =
			null;
		ServiceRegistration<AMImageScaler> amImageScalerServiceRegistration4 =
			null;
		ServiceRegistration<AMImageScaler> amImageScalerServiceRegistration5 =
			null;

		try {
			AMImageScaler enabledAMImageScaler1 = new TestAMImageScaler(true);

			amImageScalerServiceRegistration1 = _registerAMImageScaler(
				enabledAMImageScaler1, "image/test", 10);

			AMImageScaler disabledAMImageScaler2 = new TestAMImageScaler(false);

			amImageScalerServiceRegistration2 = _registerAMImageScaler(
				disabledAMImageScaler2, "image/test", 20);

			AMImageScaler enabledAMImageScaler3 = new TestAMImageScaler(true);

			amImageScalerServiceRegistration3 = _registerAMImageScaler(
				enabledAMImageScaler3, "image/test", 50);

			AMImageScaler disabledAMImageScaler4 = new TestAMImageScaler(false);

			amImageScalerServiceRegistration4 = _registerAMImageScaler(
				disabledAMImageScaler4, "image/test", 30);

			AMImageScaler enabledAMImageScaler5 = new TestAMImageScaler(true);

			amImageScalerServiceRegistration5 = _registerAMImageScaler(
				enabledAMImageScaler5, "image/test", 40);

			Assert.assertEquals(
				enabledAMImageScaler3,
				_amImageScalerRegistry.getAMImageScaler("image/test"));
		}
		finally {
			_unregisterAMImageScaler(amImageScalerServiceRegistration1);
			_unregisterAMImageScaler(amImageScalerServiceRegistration2);
			_unregisterAMImageScaler(amImageScalerServiceRegistration3);
			_unregisterAMImageScaler(amImageScalerServiceRegistration4);
			_unregisterAMImageScaler(amImageScalerServiceRegistration5);
		}
	}

	@Test
	public void testAMImageScalerRegistryReturnsNullWhenNoThereIsNoDefaultImageScaler()
		throws Exception {

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_CLASS_NAME_ADAPTIVE_MEDIA_IMAGE_SCALER_TRACKER_IMPL,
				LoggerTestUtil.WARN)) {

			_disableAMDefaultImageScaler();

			Assert.assertNull(
				_amImageScalerRegistry.getAMImageScaler(
					RandomTestUtil.randomString()));
		}
		finally {
			_enableAMDefaultImageScaler();
		}
	}

	@Test
	public void testAMImageScalerRegistryReturnsNullWhenNoThereIsNoDefaultImageScalerEnabled()
		throws Exception {

		ServiceRegistration<AMImageScaler> amImageScalerServiceRegistration =
			null;

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				_CLASS_NAME_ADAPTIVE_MEDIA_IMAGE_SCALER_TRACKER_IMPL,
				LoggerTestUtil.WARN)) {

			_disableAMDefaultImageScaler();

			AMImageScaler disabledAMImageScaler = new TestAMImageScaler(false);

			amImageScalerServiceRegistration = _registerAMImageScaler(
				disabledAMImageScaler, "*", 10);

			Assert.assertNull(
				_amImageScalerRegistry.getAMImageScaler(
					RandomTestUtil.randomString()));
		}
		finally {
			_unregisterAMImageScaler(amImageScalerServiceRegistration);

			_enableAMDefaultImageScaler();
		}
	}

	private void _disableAMDefaultImageScaler() throws Exception {
		ComponentDescriptionDTO componentDescriptionDTO =
			_serviceComponentRuntime.getComponentDescriptionDTO(
				FrameworkUtil.getBundle(_amImageScalerRegistry.getClass()),
				_CLASS_NAME_ADAPTIVE_MEDIA_DEFAULT_IMAGE_SCALER);

		Promise<Void> voidPromise = _serviceComponentRuntime.disableComponent(
			componentDescriptionDTO);

		voidPromise.getValue();
	}

	private void _enableAMDefaultImageScaler() throws Exception {
		ComponentDescriptionDTO componentDescriptionDTO =
			_serviceComponentRuntime.getComponentDescriptionDTO(
				FrameworkUtil.getBundle(_amImageScalerRegistry.getClass()),
				_CLASS_NAME_ADAPTIVE_MEDIA_DEFAULT_IMAGE_SCALER);

		Promise<Void> voidPromise = _serviceComponentRuntime.enableComponent(
			componentDescriptionDTO);

		voidPromise.getValue();
	}

	private ServiceRegistration<AMImageScaler> _registerAMImageScaler(
			AMImageScaler amImageScaler, String mimeType, int serviceRanking)
		throws Exception {

		Bundle bundle = FrameworkUtil.getBundle(
			_amDefaultImageScaler.getClass());

		BundleContext bundleContext = bundle.getBundleContext();

		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put("supportedMimeTypes", mimeType);
		properties.put("service.ranking", serviceRanking);

		return bundleContext.registerService(
			AMImageScaler.class, amImageScaler, properties);
	}

	private void _unregisterAMImageScaler(
		ServiceRegistration<AMImageScaler> amImageScalerServiceRegistration) {

		if (amImageScalerServiceRegistration == null) {
			return;
		}

		amImageScalerServiceRegistration.unregister();
	}

	private static final String
		_CLASS_NAME_ADAPTIVE_MEDIA_DEFAULT_IMAGE_SCALER =
			"com.liferay.adaptive.media.image.internal.scaler." +
				"AMDefaultImageScaler";

	private static final String
		_CLASS_NAME_ADAPTIVE_MEDIA_IMAGE_SCALER_TRACKER_IMPL =
			"com.liferay.adaptive.media.image.internal.scaler." +
				"AMImageScalerRegistryImpl";

	@Inject
	private static ServiceComponentRuntime _serviceComponentRuntime;

	@Inject(filter = "component.name=*.AMDefaultImageScaler")
	private AMImageScaler _amDefaultImageScaler;

	@Inject
	private AMImageScalerRegistry _amImageScalerRegistry;

	private class TestAMImageScaler implements AMImageScaler {

		public TestAMImageScaler(boolean enabled) {
			_enabled = enabled;
		}

		@Override
		public boolean isEnabled() {
			return _enabled;
		}

		@Override
		public AMImageScaledImage scaleImage(
			FileVersion fileVersion,
			AMImageConfigurationEntry amImageConfigurationEntry) {

			return null;
		}

		private final boolean _enabled;

	}

}