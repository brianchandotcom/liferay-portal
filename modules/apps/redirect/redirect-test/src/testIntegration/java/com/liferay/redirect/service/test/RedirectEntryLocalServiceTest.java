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
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.redirect.exception.DuplicateRedirectEntrySourceURLException;
import com.liferay.redirect.model.RedirectEntry;
import com.liferay.redirect.model.RedirectNotFoundEntry;
import com.liferay.redirect.service.RedirectEntryLocalService;
import com.liferay.redirect.service.RedirectNotFoundEntryLocalService;
import com.liferay.redirect.test.util.RedirectTestUtil;

import java.time.Instant;

import java.util.Date;

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
	public void testAddRedirectEntryDeletesRedirectNotFoundEntry()
		throws Exception {

		RedirectTestUtil.withRedirectEnabled(
			() -> {
				_redirectNotFoundEntry =
					_redirectNotFoundEntryLocalService.
						addOrUpdateRedirectNotFoundEntry(
							_groupLocalService.getGroup(
								TestPropsValues.getGroupId()),
							"sourceURL");

				_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
					TestPropsValues.getGroupId(), "destinationURL", null, false,
					"sourceURL", ServiceContextTestUtil.getServiceContext());

				Assert.assertNull(
					_redirectNotFoundEntryLocalService.
						fetchRedirectNotFoundEntry(
							TestPropsValues.getGroupId(), "sourceURL"));
			});
	}

	@Test(expected = DuplicateRedirectEntrySourceURLException.class)
	public void testAddRedirectEntryFailsWhenDuplicateSourceURL()
		throws Exception {

		RedirectTestUtil.withRedirectEnabled(
			() -> {
				_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
					TestPropsValues.getGroupId(), "destinationURL", null, false,
					"sourceURL", ServiceContextTestUtil.getServiceContext());

				_redirectEntryLocalService.addRedirectEntry(
					TestPropsValues.getGroupId(), "destinationURL", null, false,
					"sourceURL", ServiceContextTestUtil.getServiceContext());
			});
	}

	@Test(expected = DuplicateRedirectEntrySourceURLException.class)
	public void testAddRedirectEntryFailsWhenDuplicateSourceURLAndDifferentType()
		throws Exception {

		RedirectTestUtil.withRedirectEnabled(
			() -> {
				_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
					TestPropsValues.getGroupId(), "destinationURL", null, true,
					"sourceURL", ServiceContextTestUtil.getServiceContext());

				_redirectEntryLocalService.addRedirectEntry(
					TestPropsValues.getGroupId(), "destinationURL", null, false,
					"sourceURL", ServiceContextTestUtil.getServiceContext());
			});
	}

	@Test(expected = DuplicateRedirectEntrySourceURLException.class)
	public void testAddRedirectEntryFailsWhenDuplicateSourceURLAndExpirationDate()
		throws Exception {

		RedirectTestUtil.withRedirectEnabled(
			() -> {
				_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
					TestPropsValues.getGroupId(), "destinationURL", new Date(),
					true, "sourceURL",
					ServiceContextTestUtil.getServiceContext());

				_redirectEntryLocalService.addRedirectEntry(
					TestPropsValues.getGroupId(), "destinationURL", new Date(),
					false, "sourceURL",
					ServiceContextTestUtil.getServiceContext());
			});
	}

	@Test
	public void testFetchExpiredRedirectEntry() throws Exception {
		RedirectTestUtil.withRedirectEnabled(
			() -> {
				Instant instant = Instant.now();

				_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
					TestPropsValues.getGroupId(), "destinationURL",
					Date.from(instant.minusSeconds(3600)), false, "sourceURL",
					ServiceContextTestUtil.getServiceContext());

				Assert.assertNull(
					_redirectEntryLocalService.fetchRedirectEntry(
						TestPropsValues.getGroupId(), "sourceURL"));
			});
	}

	@Test
	public void testFetchNotExpiredRedirectEntry() throws Exception {
		RedirectTestUtil.withRedirectEnabled(
			() -> {
				Instant instant = Instant.now();

				_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
					TestPropsValues.getGroupId(), "destinationURL",
					Date.from(instant.plusSeconds(3600)), false, "sourceURL",
					ServiceContextTestUtil.getServiceContext());

				Assert.assertEquals(
					_redirectEntry,
					_redirectEntryLocalService.fetchRedirectEntry(
						TestPropsValues.getGroupId(), "sourceURL"));
			});
	}

	@Test
	public void testFetchRedirectEntry() throws Exception {
		RedirectTestUtil.withRedirectEnabled(
			() -> {
				_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
					TestPropsValues.getGroupId(), "destinationURL", null, false,
					"sourceURL", ServiceContextTestUtil.getServiceContext());

				Assert.assertEquals(
					_redirectEntry,
					_redirectEntryLocalService.fetchRedirectEntry(
						TestPropsValues.getGroupId(), "sourceURL"));
			});
	}

	@Inject
	private GroupLocalService _groupLocalService;

	@DeleteAfterTestRun
	private RedirectEntry _redirectEntry;

	@Inject
	private RedirectEntryLocalService _redirectEntryLocalService;

	@DeleteAfterTestRun
	private RedirectNotFoundEntry _redirectNotFoundEntry;

	@Inject
	private RedirectNotFoundEntryLocalService
		_redirectNotFoundEntryLocalService;

}