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
			_friendlyURLNormalizer.normalizeWithEncoding("anotherSourceURL"),
			_chainedRedirectEntry.getSourceURL());

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
			_friendlyURLNormalizer.normalizeWithEncoding(
				"intermediateDestinationURL"),
			_chainedRedirectEntry.getSourceURL());

		Assert.assertEquals(
			"finalDestinationURL", _chainedRedirectEntry.getDestinationURL());

		_redirectEntry = _redirectEntryLocalService.fetchRedirectEntry(
			_redirectEntry.getRedirectEntryId());

		Assert.assertEquals(
			_friendlyURLNormalizer.normalizeWithEncoding("sourceURL"),
			_redirectEntry.getSourceURL());

		Assert.assertEquals(
			"groupBaseURL/intermediateDestinationURL",
			_redirectEntry.getDestinationURL());
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

	@Test(expected = DuplicateRedirectEntrySourceURLException.class)
	public void testAddRedirectEntryFailsWhenDuplicateSourceURLDiffersUpperCaseLowerCase()
		throws Exception {

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "groupBaseURL/destinationURL", null, false,
			StringUtil.toUpperCase("sourceURL"),
			ServiceContextTestUtil.getServiceContext());

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "destinationURL", null, false,
			StringUtil.toLowerCase("sourceURL"),
			ServiceContextTestUtil.getServiceContext());
	}

	@Test(
		expected = CircularRedirectEntryException.MustNotFormALoopWithAnotherRedirectEntry.class
	)
	public void testAddRedirectEntryFailsWhenRedirectLoop() throws Exception {
		String groupBaseURL = "groupBaseURL";
		String sourceURL = "sourceURL";
		String destinationURL = "destinationURL";

		String groupSourceURL =
			groupBaseURL + "/" +
				_friendlyURLNormalizer.normalizeWithEncoding(sourceURL);

		String groupDestinationURL =
			groupBaseURL + "/" +
				_friendlyURLNormalizer.normalizeWithEncoding(destinationURL);

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), groupDestinationURL, null, false, sourceURL,
			ServiceContextTestUtil.getServiceContext());

		_chainedRedirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), groupSourceURL, null, groupBaseURL, false,
			destinationURL, false, ServiceContextTestUtil.getServiceContext());
	}

	@Test(
		expected = CircularRedirectEntryException.MustNotFormALoopWithAnotherRedirectEntry.class
	)
	public void testAddRedirectEntryFailsWhenRedirectLoopSourceURLDiffersUpperCaseLowerCase()
		throws Exception {

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "groupBaseURL/destinationURL", null, false,
			StringUtil.toUpperCase("sourceURL"),
			ServiceContextTestUtil.getServiceContext());

		_chainedRedirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(),
			"groupBaseURL/" + StringUtil.toLowerCase("sourceURL"), null,
			"groupBaseURL", false, "destinationURL", false,
			ServiceContextTestUtil.getServiceContext());
	}

	@Test(
		expected = CircularRedirectEntryException.DestinationURLMustNotBeEqualToSourceURL.class
	)
	public void testAddRedirectEntryFailsWhenSameDestinationAndSourceURL()
		throws Exception {

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "groupBaseURL/sourceURL", null, "groupBaseURL",
			false, "sourceURL", false,
			ServiceContextTestUtil.getServiceContext());
	}

	@Test(expected = LayoutFriendlyURLException.class)
	public void testAddRedirectEntryFailsWhenSourceURLDoubleSlash()
		throws Exception {

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "destinationURL", null, false, "a//a",
			ServiceContextTestUtil.getServiceContext());
	}

	@Test(expected = LayoutFriendlyURLException.class)
	public void testAddRedirectEntryFailsWhenSourceURLEndSlash()
		throws Exception {

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "destinationURL", null, false, "a/",
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
			_group.getGroupId(), "destinationURL", null, false, "/a",
			ServiceContextTestUtil.getServiceContext());
	}

	@Test(expected = LayoutFriendlyURLException.class)
	public void testAddRedirectEntryFailsWhenSourceURLTooLong()
		throws Exception {

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "destinationURL", null, false,
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

		String groupBaseURL = "groupBaseURL";
		String sourceURL = "sourceURL";
		String intermediateURL = "intermediateURL";
		String destinationURL = "destinationURL";

		String groupSourceURL =
			groupBaseURL + "/" +
				_friendlyURLNormalizer.normalizeWithEncoding(sourceURL);
		String groupIntermediateURL =
			groupBaseURL + "/" +
				_friendlyURLNormalizer.normalizeWithEncoding(intermediateURL);
		String groupDestinationURL =
			groupBaseURL + "/" +
				_friendlyURLNormalizer.normalizeWithEncoding(destinationURL);

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), groupIntermediateURL, null, false, sourceURL,
			ServiceContextTestUtil.getServiceContext());

		_chainedRedirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), groupSourceURL, null, groupBaseURL, false,
			destinationURL, false, ServiceContextTestUtil.getServiceContext());

		_intermediateRedirectEntry =
			_redirectEntryLocalService.addRedirectEntry(
				_group.getGroupId(), groupDestinationURL, null, groupBaseURL,
				false, intermediateURL, true,
				ServiceContextTestUtil.getServiceContext());
	}

	@Test
	public void testAddRedirectEntryFixesAChainByDestinationURL()
		throws Exception {

		String groupBaseURL = "groupBaseURL";
		String sourceURL = "sourceURL";
		String anotherSourceURL = "anotherSourceURL";
		String destinationURL = "destinationURL";

		String groupSourceURL =
			groupBaseURL + "/" +
				_friendlyURLNormalizer.normalizeWithEncoding(sourceURL);

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), destinationURL, null, false, sourceURL,
			ServiceContextTestUtil.getServiceContext());

		_chainedRedirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), groupSourceURL, null, groupBaseURL, false,
			anotherSourceURL, true, ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(
			_friendlyURLNormalizer.normalizeWithEncoding(anotherSourceURL),
			_chainedRedirectEntry.getSourceURL());

		Assert.assertEquals(
			destinationURL, _chainedRedirectEntry.getDestinationURL());
	}

	@Test
	public void testAddRedirectEntryFixesAChainBySourceURL() throws Exception {
		String groupBaseURL = "groupBaseURL";
		String sourceURL = "sourceURL";
		String intermediateDestinationURL = "intermediateDestinationURL";
		String finalDestinationURL = "finalDestinationURL";

		String normalizedIntermediateDestinationURL =
			_friendlyURLNormalizer.normalizeWithEncoding(
				intermediateDestinationURL);

		String groupDestinationURL =
			groupBaseURL + "/" + normalizedIntermediateDestinationURL;

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), groupDestinationURL, null, false, sourceURL,
			ServiceContextTestUtil.getServiceContext());

		_chainedRedirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), finalDestinationURL, null, groupBaseURL, false,
			intermediateDestinationURL, true,
			ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(
			normalizedIntermediateDestinationURL,
			_chainedRedirectEntry.getSourceURL());

		Assert.assertEquals(
			finalDestinationURL, _chainedRedirectEntry.getDestinationURL());

		_redirectEntry = _redirectEntryLocalService.fetchRedirectEntry(
			_redirectEntry.getRedirectEntryId());

		Assert.assertEquals(
			_friendlyURLNormalizer.normalizeWithEncoding(sourceURL),
			_redirectEntry.getSourceURL());

		Assert.assertEquals(
			finalDestinationURL, _redirectEntry.getDestinationURL());
	}

	@Test
	public void testAddRedirectEntryNotNormalizedSourceURL() throws Exception {
		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "destinationURL", null, false, "attaché",
			ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(
			_friendlyURLNormalizer.normalizeWithEncoding("attaché"),
			_redirectEntry.getSourceURL());
	}

	@Test
	public void testAddRedirectEntryWithTwoStepRedirectLoop() throws Exception {
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

		Assert.assertEquals(
			_friendlyURLNormalizer.normalizeWithEncoding("sourceURL"),
			_redirectEntry.getSourceURL());

		Assert.assertEquals(
			"groupBaseURL/intermediateURL", _redirectEntry.getDestinationURL());

		_chainedRedirectEntry = _redirectEntryLocalService.fetchRedirectEntry(
			_chainedRedirectEntry.getRedirectEntryId());

		Assert.assertEquals(
			_friendlyURLNormalizer.normalizeWithEncoding("destinationURL"),
			_chainedRedirectEntry.getSourceURL());

		Assert.assertEquals(
			"groupBaseURL/sourceURL",
			_chainedRedirectEntry.getDestinationURL());

		Assert.assertEquals(
			_friendlyURLNormalizer.normalizeWithEncoding("intermediateURL"),
			_intermediateRedirectEntry.getSourceURL());

		Assert.assertEquals(
			"groupBaseURL/destinationURL",
			_intermediateRedirectEntry.getDestinationURL());
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

		String sourceURL = "sourceURL";

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "destinationURL",
			Date.from(instant.plusSeconds(3600)), false, sourceURL,
			ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(
			_redirectEntry,
			_redirectEntryLocalService.fetchRedirectEntry(
				_group.getGroupId(),
				_friendlyURLNormalizer.normalizeWithEncoding(sourceURL)));
	}

	@Test
	public void testFetchRedirectEntry() throws Exception {
		String sourceURL = "sourceURL";

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "destinationURL", null, false, sourceURL,
			ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(
			_redirectEntry,
			_redirectEntryLocalService.fetchRedirectEntry(
				_group.getGroupId(),
				_friendlyURLNormalizer.normalizeWithEncoding(sourceURL)));
	}

	@Test
	public void testFetchRedirectEntryDoesNotUpdateTheLastOccurrenceDate()
		throws Exception {

		String sourceURL = "sourceURL";

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "destinationURL", null, false, sourceURL,
			ServiceContextTestUtil.getServiceContext());

		Assert.assertNull(_redirectEntry.getLastOccurrenceDate());

		_redirectEntry = _redirectEntryLocalService.fetchRedirectEntry(
			_group.getGroupId(),
			_friendlyURLNormalizer.normalizeWithEncoding(sourceURL));

		Assert.assertNull(_redirectEntry.getLastOccurrenceDate());
	}

	@Test
	public void testFetchRedirectEntryUpdatesTheLastOccurrenceDate()
		throws Exception {

		String sourceURL = "sourceURL";

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "destinationURL", null, false, sourceURL,
			ServiceContextTestUtil.getServiceContext());

		Assert.assertNull(_redirectEntry.getLastOccurrenceDate());

		_redirectEntry = _redirectEntryLocalService.fetchRedirectEntry(
			_group.getGroupId(),
			_friendlyURLNormalizer.normalizeWithEncoding(sourceURL), true);

		Date lastOccurrenceDate = _redirectEntry.getLastOccurrenceDate();

		Assert.assertTrue(lastOccurrenceDate.before(DateUtil.newDate()));
	}

	@Test
	public void testFetchRedirectEntryUpdatesTheLastOccurrenceDateOnceADay()
		throws Exception {

		String sourceURL = "sourceURL";

		String normalizedSourceURL =
			_friendlyURLNormalizer.normalizeWithEncoding(sourceURL);

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "destinationURL", null, false, sourceURL,
			ServiceContextTestUtil.getServiceContext());

		Assert.assertNull(_redirectEntry.getLastOccurrenceDate());

		_redirectEntry = _redirectEntryLocalService.fetchRedirectEntry(
			_group.getGroupId(), normalizedSourceURL, true);

		Date lastOccurrenceDate = _redirectEntry.getLastOccurrenceDate();

		_redirectEntry = _redirectEntryLocalService.fetchRedirectEntry(
			_group.getGroupId(), normalizedSourceURL, true);

		Assert.assertEquals(
			lastOccurrenceDate, _redirectEntry.getLastOccurrenceDate());
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
			_friendlyURLNormalizer.normalizeWithEncoding("anotherSourceURL"),
			_chainedRedirectEntry.getSourceURL());

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
			_friendlyURLNormalizer.normalizeWithEncoding(
				"intermediateDestinationURL"),
			_chainedRedirectEntry.getSourceURL());

		Assert.assertEquals(
			"finalDestinationURL", _chainedRedirectEntry.getDestinationURL());

		_redirectEntry = _redirectEntryLocalService.fetchRedirectEntry(
			_redirectEntry.getRedirectEntryId());

		Assert.assertEquals(
			_friendlyURLNormalizer.normalizeWithEncoding("sourceURL"),
			_redirectEntry.getSourceURL());

		Assert.assertEquals(
			"groupBaseURL/intermediateDestinationURL",
			_redirectEntry.getDestinationURL());
	}

	@Test(
		expected = CircularRedirectEntryException.DestinationURLMustNotBeEqualToSourceURL.class
	)
	public void testUpdateRedirectEntryFailsWhenSameDestinationAndSourceURL()
		throws Exception {

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "groupBaseURL/destinationURL", null,
			"groupBaseURL", false, "sourceURL", false,
			ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(
			_friendlyURLNormalizer.normalizeWithEncoding("sourceURL"),
			_redirectEntry.getSourceURL());
		Assert.assertEquals(
			"groupBaseURL/destinationURL", _redirectEntry.getDestinationURL());

		_redirectEntry = _redirectEntryLocalService.updateRedirectEntry(
			_redirectEntry.getRedirectEntryId(), "groupBaseURL/sourceURL", null,
			"groupBaseURL", false, "sourceURL", false);
	}

	@Test
	public void testUpdateRedirectEntryFixesAChainByDestinationURL()
		throws Exception {

		String groupBaseURL = "groupBaseURL";
		String sourceURL = "sourceURL";
		String anotherSourceURL = "anotherSourceURL";
		String destinationURL = "destinationURL";

		String groupSourceURL =
			groupBaseURL + "/" +
				_friendlyURLNormalizer.normalizeWithEncoding(sourceURL);

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), destinationURL, null, false, sourceURL,
			ServiceContextTestUtil.getServiceContext());

		_chainedRedirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), groupSourceURL, null, groupBaseURL, false,
			anotherSourceURL, false,
			ServiceContextTestUtil.getServiceContext());

		_chainedRedirectEntry = _redirectEntryLocalService.updateRedirectEntry(
			_chainedRedirectEntry.getRedirectEntryId(), groupSourceURL, null,
			groupBaseURL, false, anotherSourceURL, true);

		Assert.assertEquals(
			_friendlyURLNormalizer.normalizeWithEncoding(anotherSourceURL),
			_chainedRedirectEntry.getSourceURL());

		Assert.assertEquals(
			destinationURL, _chainedRedirectEntry.getDestinationURL());
	}

	@Test
	public void testUpdateRedirectEntryFixesAChainBySourceURL()
		throws Exception {

		String groupBaseURL = "groupBaseURL";
		String sourceURL = "sourceURL";
		String intermediateDestinationURL = "intermediateDestinationURL";
		String finalDestinationURL = "finalDestinationURL";

		String normalizedIntermediateDestinationURL =
			_friendlyURLNormalizer.normalizeWithEncoding(
				intermediateDestinationURL);

		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(),
			groupBaseURL + "/" + normalizedIntermediateDestinationURL, null,
			false, sourceURL, ServiceContextTestUtil.getServiceContext());

		_chainedRedirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), finalDestinationURL, null, groupBaseURL, false,
			intermediateDestinationURL, false,
			ServiceContextTestUtil.getServiceContext());

		_chainedRedirectEntry = _redirectEntryLocalService.updateRedirectEntry(
			_chainedRedirectEntry.getRedirectEntryId(), finalDestinationURL,
			null, groupBaseURL, false, intermediateDestinationURL, true);

		Assert.assertEquals(
			normalizedIntermediateDestinationURL,
			_chainedRedirectEntry.getSourceURL());

		Assert.assertEquals(
			finalDestinationURL, _chainedRedirectEntry.getDestinationURL());

		_redirectEntry = _redirectEntryLocalService.fetchRedirectEntry(
			_redirectEntry.getRedirectEntryId());

		Assert.assertEquals(
			_friendlyURLNormalizer.normalizeWithEncoding(sourceURL),
			_redirectEntry.getSourceURL());

		Assert.assertEquals(
			finalDestinationURL, _redirectEntry.getDestinationURL());
	}

	@Test
	public void testUpdateRedirectEntryNormalizedSourceURL() throws Exception {
		_redirectEntry = _redirectEntryLocalService.addRedirectEntry(
			_group.getGroupId(), "destinationURL", null, false, "sourceURL",
			ServiceContextTestUtil.getServiceContext());

		_redirectEntry = _redirectEntryLocalService.updateRedirectEntry(
			_redirectEntry.getRedirectEntryId(), "destinationURL", null, false,
			"attaché");

		Assert.assertEquals(
			_friendlyURLNormalizer.normalizeWithEncoding("attaché"),
			_redirectEntry.getSourceURL());
	}

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