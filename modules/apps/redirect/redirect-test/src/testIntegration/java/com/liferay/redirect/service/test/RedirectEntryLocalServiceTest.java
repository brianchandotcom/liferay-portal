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
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.redirect.exception.CircularRedirectEntryException;
import com.liferay.redirect.exception.DuplicateRedirectEntrySourceURLException;
import com.liferay.redirect.model.RedirectEntry;
import com.liferay.redirect.model.RedirectNotFoundEntry;
import com.liferay.redirect.service.RedirectEntryLocalService;
import com.liferay.redirect.service.RedirectNotFoundEntryLocalService;
import com.liferay.redirect.test.util.RedirectTestUtil;

import java.time.Instant;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
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

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test(
		expected = CircularRedirectEntryException.MustNotFormALoopWithAnotherRedirectEntry.class
	)
	public void testAddRedirectEntriesLoopFailsWhenUpdateIntermediateEntry()
		throws Exception {

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "groupBaseURL/intermediateURL", null, false,
			"sourceURL", ServiceContextTestUtil.getServiceContext());

		_chainedRedirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "groupBaseURL/sourceURL", null, "groupBaseURL",
			false, "destinationURL", false,
			ServiceContextTestUtil.getServiceContext());

		_intermediateRedirectEntry =
			_redirectEntryLocalService.addRedirectEntry(
				_group.getGroupId(), "groupBaseURL/destinationURL", null,
				"groupBaseURL", false, "intermediateURL", true,
				ServiceContextTestUtil.getServiceContext());
	}

	@Test
	public void testAddRedirectEntriesWithIndirectRedirectionLoop()
		throws Exception {

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "groupBaseURL/intermediateURL", null, false,
			"sourceURL", ServiceContextTestUtil.getServiceContext());

		_chainedRedirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "groupBaseURL/sourceURL", null, "groupBaseURL",
			false, "destinationURL", false,
			ServiceContextTestUtil.getServiceContext());

		_intermediateRedirectEntry =
			_redirectEntryLocalService.addRedirectEntry(
				_group.getGroupId(), "groupBaseURL/destinationURL", null,
				"groupBaseURL", false, "intermediateURL", false,
				ServiceContextTestUtil.getServiceContext());

		_redirectEntry = _redirectEntryLocalService.fetchRedirectEntry(
			_redirectEntry.getRedirectEntryId());

		_redirectEntry = _redirectEntryLocalService.fetchRedirectEntry(
			_redirectEntry.getRedirectEntryId());

		Assert.assertEquals("sourceURL", _redirectEntry.getSourceURL());

		Assert.assertEquals(
			"groupBaseURL/intermediateURL", _redirectEntry.getDestinationURL());

		_intermediateRedirectEntry =
			_redirectEntryLocalService.fetchRedirectEntry(
				_intermediateRedirectEntry.getRedirectEntryId());

		Assert.assertEquals(
			"intermediateURL", _intermediateRedirectEntry.getSourceURL());

		Assert.assertEquals(
			"groupBaseURL/destinationURL",
			_intermediateRedirectEntry.getDestinationURL());

		_chainedRedirectEntry = _redirectEntryLocalService.fetchRedirectEntry(
			_chainedRedirectEntry.getRedirectEntryId());

		Assert.assertEquals(
			"destinationURL", _chainedRedirectEntry.getSourceURL());

		Assert.assertEquals(
			"groupBaseURL/sourceURL",
			_chainedRedirectEntry.getDestinationURL());
	}

	@Test
	public void testAddRedirectEntryDeletesRedirectNotFoundEntry()
		throws Exception {

		_redirectNotFoundEntry =
			_redirectNotFoundEntryLocalService.addOrUpdateRedirectNotFoundEntry(
				_groupLocalService.getGroup(_group.getGroupId()), "sourceURL");

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "destinationURL", null, false, "sourceURL",
			ServiceContextTestUtil.getServiceContext());

		Assert.assertNull(
			_redirectNotFoundEntryLocalService.fetchRedirectNotFoundEntry(
				_group.getGroupId(), "sourceURL"));
	}

	@Test
	public void testAddRedirectEntryDoesNotFixAChainByDestinationURL()
		throws Exception {

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "destinationURL", null, false, "sourceURL",
			ServiceContextTestUtil.getServiceContext());

		_chainedRedirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "groupBaseURL/sourceURL", null, "groupBaseURL",
			false, "anotherSourceURL", false,
			ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(
			"anotherSourceURL", _chainedRedirectEntry.getSourceURL());

		Assert.assertEquals(
			"groupBaseURL/sourceURL",
			_chainedRedirectEntry.getDestinationURL());
	}

	@Test
	public void testAddRedirectEntryDoesNotFixAChainBySourceURL()
		throws Exception {

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "groupBaseURL/intermediateDestinationURL",
			null, false, "sourceURL",
			ServiceContextTestUtil.getServiceContext());

		_chainedRedirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "finalDestinationURL", null, "groupBaseURL",
			false, "intermediateDestinationURL", false,
			ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(
			"intermediateDestinationURL", _chainedRedirectEntry.getSourceURL());

		Assert.assertEquals(
			"finalDestinationURL", _chainedRedirectEntry.getDestinationURL());

		_redirectEntry = _redirectEntryLocalService.fetchRedirectEntry(
			_redirectEntry.getRedirectEntryId());

		Assert.assertEquals("sourceURL", _redirectEntry.getSourceURL());

		Assert.assertEquals(
			"groupBaseURL/intermediateDestinationURL",
			_redirectEntry.getDestinationURL());
	}

	@Test(
		expected = CircularRedirectEntryException.MustNotFormALoopWithAnotherRedirectEntry.class
	)
	public void testAddRedirectEntryFailsWhenCreateDirectRedirectionLoop()
		throws Exception {

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "groupBaseURL/destinationURL", null, false,
			"sourceURL", ServiceContextTestUtil.getServiceContext());

		_chainedRedirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "groupBaseURL/sourceURL", null, "groupBaseURL",
			false, "destinationURL", false,
			ServiceContextTestUtil.getServiceContext());
	}

	@Test(expected = DuplicateRedirectEntrySourceURLException.class)
	public void testAddRedirectEntryFailsWhenDuplicateSourceURL()
		throws Exception {

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "destinationURL", null, false, "sourceURL",
			ServiceContextTestUtil.getServiceContext());

		_redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "destinationURL", null, false, "sourceURL",
			ServiceContextTestUtil.getServiceContext());
	}

	@Test(expected = DuplicateRedirectEntrySourceURLException.class)
	public void testAddRedirectEntryFailsWhenDuplicateSourceURLAndDifferentType()
		throws Exception {

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "destinationURL", null, true, "sourceURL",
			ServiceContextTestUtil.getServiceContext());

		_redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "destinationURL", null, false, "sourceURL",
			ServiceContextTestUtil.getServiceContext());
	}

	@Test(expected = DuplicateRedirectEntrySourceURLException.class)
	public void testAddRedirectEntryFailsWhenDuplicateSourceURLAndExpirationDate()
		throws Exception {

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "destinationURL", new Date(), true,
			"sourceURL", ServiceContextTestUtil.getServiceContext());

		_redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "destinationURL", new Date(), false,
			"sourceURL", ServiceContextTestUtil.getServiceContext());
	}

	@Test
	public void testAddRedirectEntryFixesAChainByDestinationURL()
		throws Exception {

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "destinationURL", null, false, "sourceURL",
			ServiceContextTestUtil.getServiceContext());

		_chainedRedirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "groupBaseURL/sourceURL", null, "groupBaseURL",
			false, "anotherSourceURL", true,
			ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(
			"anotherSourceURL", _chainedRedirectEntry.getSourceURL());

		Assert.assertEquals(
			"destinationURL", _chainedRedirectEntry.getDestinationURL());
	}

	@Test
	public void testAddRedirectEntryFixesAChainBySourceURL() throws Exception {
		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "groupBaseURL/intermediateDestinationURL",
			null, false, "sourceURL",
			ServiceContextTestUtil.getServiceContext());

		_chainedRedirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "finalDestinationURL", null, "groupBaseURL",
			false, "intermediateDestinationURL", true,
			ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(
			"intermediateDestinationURL", _chainedRedirectEntry.getSourceURL());

		Assert.assertEquals(
			"finalDestinationURL", _chainedRedirectEntry.getDestinationURL());

		_redirectEntry = _redirectEntryLocalService.fetchRedirectEntry(
			_redirectEntry.getRedirectEntryId());

		Assert.assertEquals("sourceURL", _redirectEntry.getSourceURL());

		Assert.assertEquals(
			"finalDestinationURL", _redirectEntry.getDestinationURL());
	}

	@Test(
		expected = CircularRedirectEntryException.DestinationURLMustNotBeEqualToSourceURL.class
	)
	public void testAddRedirectEntrySameDestinationAndSourceURL()
		throws Exception {

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "groupBaseURL/sourceURL", null, "groupBaseURL",
			false, "sourceURL", false,
			ServiceContextTestUtil.getServiceContext());
	}

	@Test
	public void testFetchExpiredRedirectEntry() throws Exception {
		Instant instant = Instant.now();

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "destinationURL",
			Date.from(instant.minusSeconds(3600)), false, "sourceURL",
			ServiceContextTestUtil.getServiceContext());

		Assert.assertNull(
			_redirectEntryLocalService.fetchRedirectEntry(
				_group.getGroupId(), "sourceURL"));
	}

	@Test
	public void testFetchNotExpiredRedirectEntry() throws Exception {
		Instant instant = Instant.now();

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "destinationURL",
			Date.from(instant.plusSeconds(3600)), false, "sourceURL",
			ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(
			_redirectEntry,
			_redirectEntryLocalService.fetchRedirectEntry(
				_group.getGroupId(), "sourceURL"));
	}

	@Test
	public void testFetchRedirectEntry() throws Exception {
		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "destinationURL", null, false, "sourceURL",
			ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(
			_redirectEntry,
			_redirectEntryLocalService.fetchRedirectEntry(
				_group.getGroupId(), "sourceURL"));
	}

	@Test
	public void testFetchRedirectEntryDoesNotUpdateTheLastOccurrenceDate()
		throws Exception {

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "destinationURL", null, false, "sourceURL",
			ServiceContextTestUtil.getServiceContext());

		Assert.assertNull(_redirectEntry.getLastOccurrenceDate());

		_redirectEntry = _redirectEntryLocalService.fetchRedirectEntry(
			_group.getGroupId(), "sourceURL");

		Assert.assertNull(_redirectEntry.getLastOccurrenceDate());
	}

	@Test
	public void testFetchRedirectEntryUpdatesTheLastOccurrenceDate()
		throws Exception {

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "destinationURL", null, false, "sourceURL",
			ServiceContextTestUtil.getServiceContext());

		Assert.assertNull(_redirectEntry.getLastOccurrenceDate());

		_redirectEntry = _redirectEntryLocalService.fetchRedirectEntry(
			_group.getGroupId(), "sourceURL", true);

		Date lastOccurrenceDate = _redirectEntry.getLastOccurrenceDate();

		Assert.assertTrue(lastOccurrenceDate.before(DateUtil.newDate()));
	}

	@Test
	public void testFetchRedirectEntryUpdatesTheLastOccurrenceDateOnceADay()
		throws Exception {

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "destinationURL", null, false, "sourceURL",
			ServiceContextTestUtil.getServiceContext());

		Assert.assertNull(_redirectEntry.getLastOccurrenceDate());

		_redirectEntry = _redirectEntryLocalService.fetchRedirectEntry(
			_group.getGroupId(), "sourceURL", true);

		Date lastOccurrenceDate = _redirectEntry.getLastOccurrenceDate();

		_redirectEntry = _redirectEntryLocalService.fetchRedirectEntry(
			_group.getGroupId(), "sourceURL", true);

		Assert.assertEquals(
			lastOccurrenceDate, _redirectEntry.getLastOccurrenceDate());
	}

	@Test
	public void testFetchRedirectEntryWhenDisabled() throws Exception {
		RedirectTestUtil.withRedirectDisabled(
			() -> {
				_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
					_group.getGroupId(), "destinationURL", null, false,
					"sourceURL", ServiceContextTestUtil.getServiceContext());

				Assert.assertNull(
					_redirectEntryLocalService.fetchRedirectEntry(
						_group.getGroupId(), "sourceURL"));
			});
	}

	@Test
	public void testUpdateRedirectEntryDoesNotFixAChainByDestinationURL()
		throws Exception {

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "destinationURL", null, false, "sourceURL",
			ServiceContextTestUtil.getServiceContext());

		_chainedRedirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "groupBaseURL/sourceURL", null, "groupBaseURL",
			false, "anotherSourceURL", false,
			ServiceContextTestUtil.getServiceContext());

		_chainedRedirectEntry = _redirectEntryLocalService.updateRedirectEntry(
			_chainedRedirectEntry.getRedirectEntryId(),
			"groupBaseURL/sourceURL", null, "groupBaseURL", false,
			"anotherSourceURL", false);

		Assert.assertEquals(
			"anotherSourceURL", _chainedRedirectEntry.getSourceURL());

		Assert.assertEquals(
			"groupBaseURL/sourceURL",
			_chainedRedirectEntry.getDestinationURL());
	}

	@Test
	public void testUpdateRedirectEntryDoesNotFixAChainBySourceURL()
		throws Exception {

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "groupBaseURL/intermediateDestinationURL",
			null, false, "sourceURL",
			ServiceContextTestUtil.getServiceContext());

		_chainedRedirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "finalDestinationURL", null, "groupBaseURL",
			false, "intermediateDestinationURL", false,
			ServiceContextTestUtil.getServiceContext());

		_chainedRedirectEntry = _redirectEntryLocalService.updateRedirectEntry(
			_chainedRedirectEntry.getRedirectEntryId(), "finalDestinationURL",
			null, "groupBaseURL", false, "intermediateDestinationURL", false);

		Assert.assertEquals(
			"intermediateDestinationURL", _chainedRedirectEntry.getSourceURL());

		Assert.assertEquals(
			"finalDestinationURL", _chainedRedirectEntry.getDestinationURL());

		_redirectEntry = _redirectEntryLocalService.fetchRedirectEntry(
			_redirectEntry.getRedirectEntryId());

		Assert.assertEquals("sourceURL", _redirectEntry.getSourceURL());

		Assert.assertEquals(
			"groupBaseURL/intermediateDestinationURL",
			_redirectEntry.getDestinationURL());
	}

	@Test
	public void testUpdateRedirectEntryFixesAChainByDestinationURL()
		throws Exception {

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "destinationURL", null, false, "sourceURL",
			ServiceContextTestUtil.getServiceContext());

		_chainedRedirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "groupBaseURL/sourceURL", null, "groupBaseURL",
			false, "anotherSourceURL", false,
			ServiceContextTestUtil.getServiceContext());

		_chainedRedirectEntry = _redirectEntryLocalService.updateRedirectEntry(
			_chainedRedirectEntry.getRedirectEntryId(),
			"groupBaseURL/sourceURL", null, "groupBaseURL", false,
			"anotherSourceURL", true);

		Assert.assertEquals(
			"anotherSourceURL", _chainedRedirectEntry.getSourceURL());

		Assert.assertEquals(
			"destinationURL", _chainedRedirectEntry.getDestinationURL());
	}

	@Test
	public void testUpdateRedirectEntryFixesAChainBySourceURL()
		throws Exception {

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "groupBaseURL/intermediateDestinationURL",
			null, false, "sourceURL",
			ServiceContextTestUtil.getServiceContext());

		_chainedRedirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "finalDestinationURL", null, "groupBaseURL",
			false, "intermediateDestinationURL", false,
			ServiceContextTestUtil.getServiceContext());

		_chainedRedirectEntry = _redirectEntryLocalService.updateRedirectEntry(
			_chainedRedirectEntry.getRedirectEntryId(), "finalDestinationURL",
			null, "groupBaseURL", false, "intermediateDestinationURL", true);

		Assert.assertEquals(
			"intermediateDestinationURL", _chainedRedirectEntry.getSourceURL());

		Assert.assertEquals(
			"finalDestinationURL", _chainedRedirectEntry.getDestinationURL());

		_redirectEntry = _redirectEntryLocalService.fetchRedirectEntry(
			_redirectEntry.getRedirectEntryId());

		Assert.assertEquals("sourceURL", _redirectEntry.getSourceURL());

		Assert.assertEquals(
			"finalDestinationURL", _redirectEntry.getDestinationURL());
	}

	@Test(
		expected = CircularRedirectEntryException.DestinationURLMustNotBeEqualToSourceURL.class
	)
	public void testUpdateRedirectEntrySameDestinationAndSourceURL()
		throws Exception {

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "groupBaseURL/destinationURL", null,
			"groupBaseURL", false, "sourceURL", false,
			ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals("sourceURL", _redirectEntry.getSourceURL());
		Assert.assertEquals(
			"groupBaseURL/destinationURL", _redirectEntry.getDestinationURL());

		_redirectEntry = _redirectEntryLocalService.updateRedirectEntry(
			_redirectEntry.getRedirectEntryId(), "groupBaseURL/sourceURL", null,
			"groupBaseURL", false, "sourceURL", false);
	}

	@DeleteAfterTestRun
	private RedirectEntry _chainedRedirectEntry;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private GroupLocalService _groupLocalService;

	@DeleteAfterTestRun
	private RedirectEntry _intermediateRedirectEntry;

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