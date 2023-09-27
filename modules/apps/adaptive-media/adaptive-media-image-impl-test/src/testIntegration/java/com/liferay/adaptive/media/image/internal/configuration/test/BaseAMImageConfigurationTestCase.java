/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.image.internal.configuration.test;

import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationHelper;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;

import java.util.Collection;

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
		AMImageConfigurationEntry amImageConfigurationEntry) {

		Assert.assertNotNull(amImageConfigurationEntry);
		Assert.assertFalse(amImageConfigurationEntry.isEnabled());
	}

	protected void assertEnabled(
		AMImageConfigurationEntry amImageConfigurationEntry) {

		Assert.assertNotNull(amImageConfigurationEntry);
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