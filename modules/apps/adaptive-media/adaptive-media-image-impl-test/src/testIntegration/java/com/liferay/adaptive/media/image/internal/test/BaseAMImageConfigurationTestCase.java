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

package com.liferay.adaptive.media.image.internal.test;

import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationHelper;
import com.liferay.adaptive.media.image.constants.AMImageDestinationNames;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 * @author Alejandro Hernández
 */
public abstract class BaseAMImageConfigurationTestCase {

	@BeforeClass
	public static void setUpClass() {
		Registry registry = RegistryUtil.getRegistry();

		Filter filter = registry.getFilter(
			"(objectClass=" + AMImageConfigurationHelper.class.getName() + ")");

		serviceTracker = registry.trackServices(filter);

		serviceTracker.open();
	}

	@AfterClass
	public static void tearDownClass() {
		serviceTracker.close();
	}

	@Before
	public void setUp() throws Exception {
		deleteAllConfigurationEntries();
	}

	@After
	public void tearDown() throws Exception {
		deleteAllConfigurationEntries();
	}

	protected static void deleteAllConfigurationEntries()
		throws IOException, PortalException {

		AMImageConfigurationHelper amImageConfigurationHelper =
			serviceTracker.getService();

		Collection<AMImageConfigurationEntry> amImageConfigurationEntries =
			amImageConfigurationHelper.getAMImageConfigurationEntries(
				TestPropsValues.getCompanyId(),
				amImageConfigurationEntry -> true);

		for (AMImageConfigurationEntry amImageConfigurationEntry :
				amImageConfigurationEntries) {

			amImageConfigurationHelper.forceDeleteAMImageConfigurationEntry(
				TestPropsValues.getCompanyId(),
				amImageConfigurationEntry.getUUID());
		}
	}

	protected void assertDisabled(
		Optional<AMImageConfigurationEntry> amImageConfigurationEntryOptional) {

		Assert.assertTrue(amImageConfigurationEntryOptional.isPresent());

		AMImageConfigurationEntry amImageConfigurationEntry =
			amImageConfigurationEntryOptional.get();

		Assert.assertFalse(amImageConfigurationEntry.isEnabled());
	}

	protected void assertEnabled(
		Optional<AMImageConfigurationEntry> amImageConfigurationEntryOptional) {

		Assert.assertTrue(amImageConfigurationEntryOptional.isPresent());

		AMImageConfigurationEntry amImageConfigurationEntry =
			amImageConfigurationEntryOptional.get();

		Assert.assertTrue(amImageConfigurationEntry.isEnabled());
	}

	protected List<Message> collectConfigurationMessages(
			CheckedRunnable runnable)
		throws Exception {

		String destinationName = AMImageDestinationNames.AM_IMAGE_CONFIGURATION;

		List<Message> messages = new ArrayList<>();

		MessageListener messageListener = messages::add;

		MessageBusUtil.registerMessageListener(
			destinationName, messageListener);

		try {
			runnable.run();
		}
		finally {
			MessageBusUtil.unregisterMessageListener(
				destinationName, messageListener);
		}

		return messages;
	}

	protected static ServiceTracker
		<AMImageConfigurationHelper, AMImageConfigurationHelper> serviceTracker;

	@FunctionalInterface
	protected interface CheckedRunnable {

		public void run() throws Exception;

	}

}