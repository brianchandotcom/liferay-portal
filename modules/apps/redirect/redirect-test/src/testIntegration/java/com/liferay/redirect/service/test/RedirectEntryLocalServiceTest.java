/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.redirect.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.LayoutFriendlyURLException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizer;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.redirect.exception.CircularRedirectEntryException;
import com.liferay.redirect.exception.DuplicateRedirectEntrySourceURLException;
import com.liferay.redirect.exception.RequiredRedirectEntrySourceURLException;
import com.liferay.redirect.model.RedirectEntry;
import com.liferay.redirect.model.RedirectNotFoundEntry;
import com.liferay.redirect.service.RedirectEntryLocalService;
import com.liferay.redirect.service.RedirectNotFoundEntryLocalService;

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
 * @author Roberto Díaz
 */
@RunWith(Arquillian.class)
public class RedirectEntryLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		UserTestUtil.setUser(TestPropsValues.getUser());

		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testAddRedirectEntryDeletesRedirectNotFoundEntry()
		throws Exception {

		_redirectNotFoundEntry =
			_redirectNotFoundEntryLocalService.addOrUpdateRedirectNotFoundEntry(
				_groupLocalService.getGroup(_group.getGroupId()), _SOURCE_URL);

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), _DESTINATION_URL, null, false, _SOURCE_URL,
			ServiceContextTestUtil.getServiceContext());

		Assert.assertNull(
			_redirectNotFoundEntryLocalService.fetchRedirectNotFoundEntry(
				_group.getGroupId(), _SOURCE_URL));
	}

	@Test
	public void testAddRedirectEntryDoesNotFixAChainByDestinationURL()
		throws Exception {

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), _DESTINATION_URL, null, false, _SOURCE_URL,
			ServiceContextTestUtil.getServiceContext());

		_chainedRedirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), _appendGroupBaseURL(_SOURCE_URL), null,
			_GROUP_BASE_URL, false, _ANOTHER_SOURCE_URL, false,
			ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(
			_friendlyURLNormalizer.normalizeWithEncoding(_ANOTHER_SOURCE_URL),
			_chainedRedirectEntry.getSourceURL());

		Assert.assertEquals(
			_appendGroupBaseURL(_SOURCE_URL),
			_chainedRedirectEntry.getDestinationURL());
	}

	@Test
	public void testAddRedirectEntryDoesNotFixAChainBySourceURL()
		throws Exception {

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(),
			_appendGroupBaseURL(_INTERMEDIATE_DESTINATION_URL), null, false,
			_SOURCE_URL, ServiceContextTestUtil.getServiceContext());

		_chainedRedirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), _FINAL_DESTINATION_URL, null, _GROUP_BASE_URL,
			false, _INTERMEDIATE_DESTINATION_URL, false,
			ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(
			_friendlyURLNormalizer.normalizeWithEncoding(
				_INTERMEDIATE_DESTINATION_URL),
			_chainedRedirectEntry.getSourceURL());

		Assert.assertEquals(
			_FINAL_DESTINATION_URL, _chainedRedirectEntry.getDestinationURL());

		_redirectEntry = _redirectEntryLocalService.fetchRedirectEntry(
			_redirectEntry.getRedirectEntryId());

		Assert.assertEquals(
			_friendlyURLNormalizer.normalizeWithEncoding(_SOURCE_URL),
			_redirectEntry.getSourceURL());

		Assert.assertEquals(
			_appendGroupBaseURL(_INTERMEDIATE_DESTINATION_URL),
			_redirectEntry.getDestinationURL());
	}

	@Test(expected = DuplicateRedirectEntrySourceURLException.class)
	public void testAddRedirectEntryFailsWhenDuplicateSourceURL()
		throws Exception {

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), _DESTINATION_URL, null, false, _SOURCE_URL,
			ServiceContextTestUtil.getServiceContext());

		_redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), _DESTINATION_URL, null, false, _SOURCE_URL,
			ServiceContextTestUtil.getServiceContext());
	}

	@Test(expected = DuplicateRedirectEntrySourceURLException.class)
	public void testAddRedirectEntryFailsWhenDuplicateSourceURLAndDifferentType()
		throws Exception {

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), _DESTINATION_URL, null, true, _SOURCE_URL,
			ServiceContextTestUtil.getServiceContext());

		_redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), _DESTINATION_URL, null, false, _SOURCE_URL,
			ServiceContextTestUtil.getServiceContext());
	}

	@Test(expected = DuplicateRedirectEntrySourceURLException.class)
	public void testAddRedirectEntryFailsWhenDuplicateSourceURLAndExpirationDate()
		throws Exception {

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), _DESTINATION_URL, new Date(), true,
			_SOURCE_URL, ServiceContextTestUtil.getServiceContext());

		_redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), _DESTINATION_URL, new Date(), false,
			_SOURCE_URL, ServiceContextTestUtil.getServiceContext());
	}

	@Test(expected = DuplicateRedirectEntrySourceURLException.class)
	public void testAddRedirectEntryFailsWhenDuplicateSourceURLDiffersUpperCaseLowerCase()
		throws Exception {

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), _appendGroupBaseURL(_DESTINATION_URL), null,
			false, StringUtil.toUpperCase(_SOURCE_URL),
			ServiceContextTestUtil.getServiceContext());

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), _DESTINATION_URL, null, false,
			StringUtil.toLowerCase(_SOURCE_URL),
			ServiceContextTestUtil.getServiceContext());
	}

	@Test(
		expected = CircularRedirectEntryException.MustNotFormALoopWithAnotherRedirectEntry.class
	)
	public void testAddRedirectEntryFailsWhenRedirectLoop() throws Exception {
		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(),
			_appendGroupBaseURL(
				_friendlyURLNormalizer.normalizeWithEncoding(_DESTINATION_URL)),
			null, false, _SOURCE_URL,
			ServiceContextTestUtil.getServiceContext());

		_chainedRedirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(),
			_appendGroupBaseURL(
				_friendlyURLNormalizer.normalizeWithEncoding(_SOURCE_URL)),
			null, _GROUP_BASE_URL, false, _DESTINATION_URL, false,
			ServiceContextTestUtil.getServiceContext());
	}

	@Test(
		expected = CircularRedirectEntryException.MustNotFormALoopWithAnotherRedirectEntry.class
	)
	public void testAddRedirectEntryFailsWhenRedirectLoopSourceURLDiffersUpperCaseLowerCase()
		throws Exception {

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), _appendGroupBaseURL(_DESTINATION_URL), null,
			false, StringUtil.toUpperCase(_SOURCE_URL),
			ServiceContextTestUtil.getServiceContext());

		_chainedRedirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(),
			_appendGroupBaseURL(StringUtil.toLowerCase(_SOURCE_URL)), null,
			_GROUP_BASE_URL, false, _DESTINATION_URL, false,
			ServiceContextTestUtil.getServiceContext());
	}

	@Test(
		expected = CircularRedirectEntryException.DestinationURLMustNotBeEqualToSourceURL.class
	)
	public void testAddRedirectEntryFailsWhenSameDestinationAndSourceURL()
		throws Exception {

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), _appendGroupBaseURL(_SOURCE_URL), null,
			_GROUP_BASE_URL, false, _SOURCE_URL, false,
			ServiceContextTestUtil.getServiceContext());
	}

	@Test(expected = LayoutFriendlyURLException.class)
	public void testAddRedirectEntryFailsWhenSourceURLDoubleSlash()
		throws Exception {

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), _DESTINATION_URL, null, false, "a//a",
			ServiceContextTestUtil.getServiceContext());
	}

	@Test(expected = LayoutFriendlyURLException.class)
	public void testAddRedirectEntryFailsWhenSourceURLEndSlash()
		throws Exception {

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), _DESTINATION_URL, null, false, "a/",
			ServiceContextTestUtil.getServiceContext());
	}

	@Test(expected = LayoutFriendlyURLException.class)
	public void testAddRedirectEntryFailsWhenSourceURLFriendlyURLMapper()
		throws Exception {

		_redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "http://www.liferay.com", null, false,
			"questions",
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	@Test(expected = LayoutFriendlyURLException.class)
	public void testAddRedirectEntryFailsWhenSourceURLLayoutFriendlyUrlKeywordsProperty()
		throws Exception {

		_redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "http://www.liferay.com", null, false,
			"tunnel-web",
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	@Test(expected = RequiredRedirectEntrySourceURLException.class)
	public void testAddRedirectEntryFailsWhenSourceURLNull() throws Exception {
		_redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "http://www.liferay.com", null, false,
			StringPool.BLANK,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	@Test(expected = LayoutFriendlyURLException.class)
	public void testAddRedirectEntryFailsWhenSourceURLOneCharacterLong()
		throws Exception {

		_redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "http://www.liferay.com", null, false, "a",
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	@Test(expected = LayoutFriendlyURLException.class)
	public void testAddRedirectEntryFailsWhenSourceURLPortalFriendlyURLSeparator()
		throws Exception {

		_redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "http://www.liferay.com", null, false,
			"-/test",
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	@Test(expected = LayoutFriendlyURLException.class)
	public void testAddRedirectEntryFailsWhenSourceURLStartSlash()
		throws Exception {

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), _DESTINATION_URL, null, false, "/a",
			ServiceContextTestUtil.getServiceContext());
	}

	@Test(expected = LayoutFriendlyURLException.class)
	public void testAddRedirectEntryFailsWhenSourceURLTooLong()
		throws Exception {

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), _DESTINATION_URL, null, false,
			StringUtil.randomString(256),
			ServiceContextTestUtil.getServiceContext());
	}

	@Test(expected = LayoutFriendlyURLException.class)
	public void testAddRedirectEntryFailsWhenSourceURLURLanguagePath()
		throws Exception {

		_redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "http://www.liferay.com", null, false,
			"es/test",
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	@Test(expected = LayoutFriendlyURLException.class)
	public void testAddRedirectEntryFailsWhenSourceURLURLSeparator()
		throws Exception {

		_redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "http://www.liferay.com", null, false, "/b/",
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	@Test(
		expected = CircularRedirectEntryException.MustNotFormALoopWithAnotherRedirectEntry.class
	)
	public void testAddRedirectEntryFailsWhenUpdateChainedRedirectEntriesCausesARedirectLoop()
		throws Exception {

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(),
			_appendGroupBaseURL(
				_friendlyURLNormalizer.normalizeWithEncoding(
					_INTERMEDIATE_URL)),
			null, false, _SOURCE_URL,
			ServiceContextTestUtil.getServiceContext());

		_chainedRedirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(),
			_appendGroupBaseURL(
				_friendlyURLNormalizer.normalizeWithEncoding(_SOURCE_URL)),
			null, _GROUP_BASE_URL, false, _DESTINATION_URL, false,
			ServiceContextTestUtil.getServiceContext());

		_intermediateRedirectEntry =
			_redirectEntryLocalService.addRedirectEntry(
				_group.getGroupId(),
				_appendGroupBaseURL(
					_friendlyURLNormalizer.normalizeWithEncoding(
						_DESTINATION_URL)),
				null, _GROUP_BASE_URL, false, _INTERMEDIATE_URL, true,
				ServiceContextTestUtil.getServiceContext());
	}

	@Test
	public void testAddRedirectEntryFixesAChainByDestinationURL()
		throws Exception {

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), _DESTINATION_URL, null, false, _SOURCE_URL,
			ServiceContextTestUtil.getServiceContext());

		_chainedRedirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(),
			_appendGroupBaseURL(
				_friendlyURLNormalizer.normalizeWithEncoding(_SOURCE_URL)),
			null, _GROUP_BASE_URL, false, _ANOTHER_SOURCE_URL, true,
			ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(
			_friendlyURLNormalizer.normalizeWithEncoding(_ANOTHER_SOURCE_URL),
			_chainedRedirectEntry.getSourceURL());

		Assert.assertEquals(
			_DESTINATION_URL, _chainedRedirectEntry.getDestinationURL());
	}

	@Test
	public void testAddRedirectEntryFixesAChainBySourceURL() throws Exception {
		String normalizedIntermediateDestinationURL =
			_friendlyURLNormalizer.normalizeWithEncoding(
				_INTERMEDIATE_DESTINATION_URL);

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(),
			_appendGroupBaseURL(normalizedIntermediateDestinationURL), null,
			false, _SOURCE_URL, ServiceContextTestUtil.getServiceContext());

		_chainedRedirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), _FINAL_DESTINATION_URL, null, _GROUP_BASE_URL,
			false, _INTERMEDIATE_DESTINATION_URL, true,
			ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(
			normalizedIntermediateDestinationURL,
			_chainedRedirectEntry.getSourceURL());

		Assert.assertEquals(
			_FINAL_DESTINATION_URL, _chainedRedirectEntry.getDestinationURL());

		_redirectEntry = _redirectEntryLocalService.fetchRedirectEntry(
			_redirectEntry.getRedirectEntryId());

		Assert.assertEquals(
			_friendlyURLNormalizer.normalizeWithEncoding(_SOURCE_URL),
			_redirectEntry.getSourceURL());

		Assert.assertEquals(
			_FINAL_DESTINATION_URL, _redirectEntry.getDestinationURL());
	}

	@Test
	public void testAddRedirectEntryNotNormalizedSourceURL() throws Exception {
		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), _DESTINATION_URL, null, false, "attaché",
			ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(
			_friendlyURLNormalizer.normalizeWithEncoding("attaché"),
			_redirectEntry.getSourceURL());
	}

	@Test
	public void testAddRedirectEntryWithTwoStepRedirectLoop() throws Exception {
		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), _appendGroupBaseURL(_INTERMEDIATE_URL), null,
			false, _SOURCE_URL, ServiceContextTestUtil.getServiceContext());

		_chainedRedirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), _appendGroupBaseURL(_SOURCE_URL), null,
			_GROUP_BASE_URL, false, _DESTINATION_URL, false,
			ServiceContextTestUtil.getServiceContext());

		_intermediateRedirectEntry =
			_redirectEntryLocalService.addRedirectEntry(
				_group.getGroupId(), _appendGroupBaseURL(_DESTINATION_URL),
				null, _GROUP_BASE_URL, false, _INTERMEDIATE_URL, false,
				ServiceContextTestUtil.getServiceContext());

		_redirectEntry = _redirectEntryLocalService.fetchRedirectEntry(
			_redirectEntry.getRedirectEntryId());

		Assert.assertEquals(
			_friendlyURLNormalizer.normalizeWithEncoding(_SOURCE_URL),
			_redirectEntry.getSourceURL());

		Assert.assertEquals(
			_appendGroupBaseURL(_INTERMEDIATE_URL),
			_redirectEntry.getDestinationURL());

		_chainedRedirectEntry = _redirectEntryLocalService.fetchRedirectEntry(
			_chainedRedirectEntry.getRedirectEntryId());

		Assert.assertEquals(
			_friendlyURLNormalizer.normalizeWithEncoding(_DESTINATION_URL),
			_chainedRedirectEntry.getSourceURL());

		Assert.assertEquals(
			_appendGroupBaseURL(_SOURCE_URL),
			_chainedRedirectEntry.getDestinationURL());

		Assert.assertEquals(
			_friendlyURLNormalizer.normalizeWithEncoding(_INTERMEDIATE_URL),
			_intermediateRedirectEntry.getSourceURL());

		Assert.assertEquals(
			_appendGroupBaseURL(_DESTINATION_URL),
			_intermediateRedirectEntry.getDestinationURL());
	}

	@Test
	public void testFetchExpiredRedirectEntry() throws Exception {
		Instant instant = Instant.now();

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), _DESTINATION_URL,
			Date.from(instant.minusSeconds(3600)), false, _SOURCE_URL,
			ServiceContextTestUtil.getServiceContext());

		Assert.assertNull(
			_redirectEntryLocalService.fetchRedirectEntry(
				_group.getGroupId(), _SOURCE_URL));
	}

	@Test
	public void testFetchNotExpiredRedirectEntry() throws Exception {
		Instant instant = Instant.now();

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), _DESTINATION_URL,
			Date.from(instant.plusSeconds(3600)), false, _SOURCE_URL,
			ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(
			_redirectEntry,
			_redirectEntryLocalService.fetchRedirectEntry(
				_group.getGroupId(),
				_friendlyURLNormalizer.normalizeWithEncoding(_SOURCE_URL)));
	}

	@Test
	public void testFetchRedirectEntry() throws Exception {
		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), _DESTINATION_URL, null, false, _SOURCE_URL,
			ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(
			_redirectEntry,
			_redirectEntryLocalService.fetchRedirectEntry(
				_group.getGroupId(),
				_friendlyURLNormalizer.normalizeWithEncoding(_SOURCE_URL)));
	}

	@Test
	public void testFetchRedirectEntryDoesNotUpdateTheLastOccurrenceDate()
		throws Exception {

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), _DESTINATION_URL, null, false, _SOURCE_URL,
			ServiceContextTestUtil.getServiceContext());

		Assert.assertNull(_redirectEntry.getLastOccurrenceDate());

		_redirectEntry = _redirectEntryLocalService.fetchRedirectEntry(
			_group.getGroupId(),
			_friendlyURLNormalizer.normalizeWithEncoding(_SOURCE_URL));

		Assert.assertNull(_redirectEntry.getLastOccurrenceDate());
	}

	@Test
	public void testFetchRedirectEntryUpdatesTheLastOccurrenceDate()
		throws Exception {

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), _DESTINATION_URL, null, false, _SOURCE_URL,
			ServiceContextTestUtil.getServiceContext());

		Assert.assertNull(_redirectEntry.getLastOccurrenceDate());

		_redirectEntry = _redirectEntryLocalService.fetchRedirectEntry(
			_group.getGroupId(),
			_friendlyURLNormalizer.normalizeWithEncoding(_SOURCE_URL), true);

		Date lastOccurrenceDate = _redirectEntry.getLastOccurrenceDate();

		Assert.assertTrue(lastOccurrenceDate.before(DateUtil.newDate()));
	}

	@Test
	public void testFetchRedirectEntryUpdatesTheLastOccurrenceDateOnceADay()
		throws Exception {

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), _DESTINATION_URL, null, false, _SOURCE_URL,
			ServiceContextTestUtil.getServiceContext());

		Assert.assertNull(_redirectEntry.getLastOccurrenceDate());

		_redirectEntry = _redirectEntryLocalService.fetchRedirectEntry(
			_group.getGroupId(),
			_friendlyURLNormalizer.normalizeWithEncoding(_SOURCE_URL), true);

		Date lastOccurrenceDate = _redirectEntry.getLastOccurrenceDate();

		_redirectEntry = _redirectEntryLocalService.fetchRedirectEntry(
			_group.getGroupId(),
			_friendlyURLNormalizer.normalizeWithEncoding(_SOURCE_URL), true);

		Assert.assertEquals(
			lastOccurrenceDate, _redirectEntry.getLastOccurrenceDate());
	}

	@Test
	public void testUpdateRedirectEntryDoesNotFixAChainByDestinationURL()
		throws Exception {

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), _DESTINATION_URL, null, false, _SOURCE_URL,
			ServiceContextTestUtil.getServiceContext());

		_chainedRedirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), _appendGroupBaseURL(_SOURCE_URL), null,
			_GROUP_BASE_URL, false, _ANOTHER_SOURCE_URL, false,
			ServiceContextTestUtil.getServiceContext());

		_chainedRedirectEntry = _redirectEntryLocalService.updateRedirectEntry(
			_chainedRedirectEntry.getRedirectEntryId(),
			_appendGroupBaseURL(_SOURCE_URL), null, _GROUP_BASE_URL, false,
			_ANOTHER_SOURCE_URL, false);

		Assert.assertEquals(
			_friendlyURLNormalizer.normalizeWithEncoding(_ANOTHER_SOURCE_URL),
			_chainedRedirectEntry.getSourceURL());

		Assert.assertEquals(
			_appendGroupBaseURL(_SOURCE_URL),
			_chainedRedirectEntry.getDestinationURL());
	}

	@Test
	public void testUpdateRedirectEntryDoesNotFixAChainBySourceURL()
		throws Exception {

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(),
			_appendGroupBaseURL(_INTERMEDIATE_DESTINATION_URL), null, false,
			_SOURCE_URL, ServiceContextTestUtil.getServiceContext());

		_chainedRedirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), _FINAL_DESTINATION_URL, null, _GROUP_BASE_URL,
			false, _INTERMEDIATE_DESTINATION_URL, false,
			ServiceContextTestUtil.getServiceContext());

		_chainedRedirectEntry = _redirectEntryLocalService.updateRedirectEntry(
			_chainedRedirectEntry.getRedirectEntryId(), _FINAL_DESTINATION_URL,
			null, _GROUP_BASE_URL, false, _INTERMEDIATE_DESTINATION_URL, false);

		Assert.assertEquals(
			_friendlyURLNormalizer.normalizeWithEncoding(
				_INTERMEDIATE_DESTINATION_URL),
			_chainedRedirectEntry.getSourceURL());

		Assert.assertEquals(
			_FINAL_DESTINATION_URL, _chainedRedirectEntry.getDestinationURL());

		_redirectEntry = _redirectEntryLocalService.fetchRedirectEntry(
			_redirectEntry.getRedirectEntryId());

		Assert.assertEquals(
			_friendlyURLNormalizer.normalizeWithEncoding(_SOURCE_URL),
			_redirectEntry.getSourceURL());

		Assert.assertEquals(
			_appendGroupBaseURL(_INTERMEDIATE_DESTINATION_URL),
			_redirectEntry.getDestinationURL());
	}

	@Test(
		expected = CircularRedirectEntryException.DestinationURLMustNotBeEqualToSourceURL.class
	)
	public void testUpdateRedirectEntryFailsWhenSameDestinationAndSourceURL()
		throws Exception {

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), _appendGroupBaseURL(_DESTINATION_URL), null,
			_GROUP_BASE_URL, false, _SOURCE_URL, false,
			ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(
			_friendlyURLNormalizer.normalizeWithEncoding(_SOURCE_URL),
			_redirectEntry.getSourceURL());
		Assert.assertEquals(
			_appendGroupBaseURL(_DESTINATION_URL),
			_redirectEntry.getDestinationURL());

		_redirectEntry = _redirectEntryLocalService.updateRedirectEntry(
			_redirectEntry.getRedirectEntryId(),
			_appendGroupBaseURL(_SOURCE_URL), null, _GROUP_BASE_URL, false,
			_SOURCE_URL, false);
	}

	@Test
	public void testUpdateRedirectEntryFixesAChainByDestinationURL()
		throws Exception {

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), _DESTINATION_URL, null, false, _SOURCE_URL,
			ServiceContextTestUtil.getServiceContext());

		String groupSourceURL = _appendGroupBaseURL(
			_friendlyURLNormalizer.normalizeWithEncoding(_SOURCE_URL));

		_chainedRedirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), groupSourceURL, null, _GROUP_BASE_URL, false,
			_ANOTHER_SOURCE_URL, false,
			ServiceContextTestUtil.getServiceContext());

		_chainedRedirectEntry = _redirectEntryLocalService.updateRedirectEntry(
			_chainedRedirectEntry.getRedirectEntryId(), groupSourceURL, null,
			_GROUP_BASE_URL, false, _ANOTHER_SOURCE_URL, true);

		Assert.assertEquals(
			_friendlyURLNormalizer.normalizeWithEncoding(_ANOTHER_SOURCE_URL),
			_chainedRedirectEntry.getSourceURL());

		Assert.assertEquals(
			_DESTINATION_URL, _chainedRedirectEntry.getDestinationURL());
	}

	@Test
	public void testUpdateRedirectEntryFixesAChainBySourceURL()
		throws Exception {

		String normalizedIntermediateDestinationURL =
			_friendlyURLNormalizer.normalizeWithEncoding(
				_INTERMEDIATE_DESTINATION_URL);

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(),
			_appendGroupBaseURL(normalizedIntermediateDestinationURL), null,
			false, _SOURCE_URL, ServiceContextTestUtil.getServiceContext());

		_chainedRedirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), _FINAL_DESTINATION_URL, null, _GROUP_BASE_URL,
			false, _INTERMEDIATE_DESTINATION_URL, false,
			ServiceContextTestUtil.getServiceContext());

		_chainedRedirectEntry = _redirectEntryLocalService.updateRedirectEntry(
			_chainedRedirectEntry.getRedirectEntryId(), _FINAL_DESTINATION_URL,
			null, _GROUP_BASE_URL, false, _INTERMEDIATE_DESTINATION_URL, true);

		Assert.assertEquals(
			normalizedIntermediateDestinationURL,
			_chainedRedirectEntry.getSourceURL());

		Assert.assertEquals(
			_FINAL_DESTINATION_URL, _chainedRedirectEntry.getDestinationURL());

		_redirectEntry = _redirectEntryLocalService.fetchRedirectEntry(
			_redirectEntry.getRedirectEntryId());

		Assert.assertEquals(
			_friendlyURLNormalizer.normalizeWithEncoding(_SOURCE_URL),
			_redirectEntry.getSourceURL());

		Assert.assertEquals(
			_FINAL_DESTINATION_URL, _redirectEntry.getDestinationURL());
	}

	@Test
	public void testUpdateRedirectEntryNormalizedSourceURL() throws Exception {
		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), _DESTINATION_URL, null, false, _SOURCE_URL,
			ServiceContextTestUtil.getServiceContext());

		_redirectEntry = _redirectEntryLocalService.updateRedirectEntry(
			_redirectEntry.getRedirectEntryId(), _DESTINATION_URL, null, false,
			"attaché");

		Assert.assertEquals(
			_friendlyURLNormalizer.normalizeWithEncoding("attaché"),
			_redirectEntry.getSourceURL());
	}

	private String _appendGroupBaseURL(String url) {
		return _GROUP_BASE_URL + StringPool.SLASH + url;
	}

	private static final String _ANOTHER_SOURCE_URL = "anotherSourceURL";

	private static final String _DESTINATION_URL = "destinationURL";

	private static final String _FINAL_DESTINATION_URL = "finalDestinationURL";

	private static final String _GROUP_BASE_URL = "groupBaseURL";

	private static final String _INTERMEDIATE_DESTINATION_URL =
		"intermediateDestinationURL";

	private static final String _INTERMEDIATE_URL = "intermediateURL";

	private static final String _SOURCE_URL = "sourceURL";

	@DeleteAfterTestRun
	private RedirectEntry _chainedRedirectEntry;

	@Inject
	private FriendlyURLNormalizer _friendlyURLNormalizer;

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