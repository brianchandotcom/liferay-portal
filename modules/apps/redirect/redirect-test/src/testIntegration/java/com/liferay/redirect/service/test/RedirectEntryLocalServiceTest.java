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

package com.liferay.redirect.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.redirect.model.RedirectEntry;
import com.liferay.redirect.service.RedirectEntryLocalService;

import java.time.Instant;

import java.util.Date;
import java.util.Dictionary;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class RedirectEntryLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testFetchExpiredRedirectEntry() throws Exception {
		_withRedirectEnabled(
			() -> {
				Instant instant = Instant.now();

				_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
					TestPropsValues.getGroupId(), "destinationURL", "sourceURL",
					true, Date.from(instant.minusSeconds(3600)),
					ServiceContextTestUtil.getServiceContext());

				Assert.assertNull(
					_redirectEntryLocalService.fetchRedirectEntry(
						TestPropsValues.getGroupId(), "sourceURL"));
			});
	}

	@Test
	public void testFetchNotExpiredRedirectEntry() throws Exception {
		_withRedirectEnabled(
			() -> {
				Instant instant = Instant.now();

				_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
					TestPropsValues.getGroupId(), "destinationURL", "sourceURL",
					true, Date.from(instant.plusSeconds(3600)),
					ServiceContextTestUtil.getServiceContext());

				Assert.assertEquals(
					_redirectEntry,
					_redirectEntryLocalService.fetchRedirectEntry(
						TestPropsValues.getGroupId(), "sourceURL"));
			});
	}

	@Test
	public void testFetchRedirectEntry() throws Exception {
		_withRedirectEnabled(
			() -> {
				_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
					TestPropsValues.getGroupId(), "destinationURL", "sourceURL",
					true, null, ServiceContextTestUtil.getServiceContext());

				Assert.assertEquals(
					_redirectEntry,
					_redirectEntryLocalService.fetchRedirectEntry(
						TestPropsValues.getGroupId(), "sourceURL"));
			});
	}

	private void _withRedirectEnabled(UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		Dictionary<String, Object> dictionary = new HashMapDictionary<>();

		dictionary.put("enabled", true);

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(_PID, dictionary)) {

			unsafeRunnable.run();
		}
	}

	private static final String _PID =
		"com.liferay.redirect.web.internal.configuration." +
			"FFRedirectConfiguration";

	@DeleteAfterTestRun
	private RedirectEntry _redirectEntry;

	@Inject
	private RedirectEntryLocalService _redirectEntryLocalService;

}