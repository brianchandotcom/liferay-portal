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

package com.liferay.adaptive.media.image.internal.configuration.test;

import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationHelper;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;

import java.util.Collection;
import java.util.Optional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

/**
 * @author Alejandro Hernández
 */
public abstract class BaseAMImageConfigurationTestCase {

	@Before
	public void setUp() throws Exception {
		_deleteAllConfigurationEntries();
	}

	@After
	public void tearDown() throws Exception {
		_deleteAllConfigurationEntries();
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

	protected abstract AMImageConfigurationHelper
		getAMImageConfigurationHelper();

	@FunctionalInterface
	protected interface CheckedRunnable {

		public void run() throws Exception;

	}

	private void _deleteAllConfigurationEntries() throws Exception {
		AMImageConfigurationHelper amImageConfigurationHelper =
			getAMImageConfigurationHelper();

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

	@Inject
	private MessageBus _messageBus;

}