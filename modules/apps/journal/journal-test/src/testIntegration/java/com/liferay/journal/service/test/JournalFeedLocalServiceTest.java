/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.journal.constants.JournalFeedConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFeed;
import com.liferay.journal.service.JournalFeedLocalService;
import com.liferay.journal.util.comparator.FeedNameComparator;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.rss.util.RSSUtil;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jürgen Kappler
 */
@RunWith(Arquillian.class)
public class JournalFeedLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_ddmStructure = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName());

		long journalArticleClassNameId = _classNameLocalService.getClassNameId(
			JournalArticle.class.getName());

		_ddmTemplate = DDMTemplateTestUtil.addTemplate(
			_group.getGroupId(), _ddmStructure.getStructureId(),
			journalArticleClassNameId);

		_layout = LayoutTestUtil.addTypePortletLayout(
			_group.getGroupId(), false);
	}

	@Test
	public void testAddJournalFeed() throws Exception {
		String description = RandomTestUtil.randomString();
		String feedId = StringUtil.toUpperCase(RandomTestUtil.randomString());
		String name = RandomTestUtil.randomString();

		JournalFeed journalFeed = _addJournalFeed(
			_ddmStructure, _ddmTemplate, description, feedId, _group, _layout,
			name);

		Assert.assertNotNull(journalFeed);
		Assert.assertEquals(description, journalFeed.getDescription());
		Assert.assertEquals(feedId, journalFeed.getFeedId());
		Assert.assertEquals(name, journalFeed.getName());
	}

	@Test
	public void testSearchCountWithKeywords() throws Exception {
		String description = RandomTestUtil.randomString();
		String feedId = StringUtil.toUpperCase(RandomTestUtil.randomString());
		String name = RandomTestUtil.randomString();

		_addJournalFeed(
			_ddmStructure, _ddmTemplate, description,
			RandomTestUtil.randomString(), _group, _layout,
			RandomTestUtil.randomString());
		_addJournalFeed(
			_ddmStructure, _ddmTemplate, RandomTestUtil.randomString(), feedId,
			_group, _layout, RandomTestUtil.randomString());
		_addJournalFeed(
			_ddmStructure, _ddmTemplate, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), _group, _layout, name);

		String keywords = StringBundler.concat(
			description, StringPool.SPACE, feedId, StringPool.SPACE, name);

		Assert.assertEquals(
			3,
			_journalFeedLocalService.searchCount(
				TestPropsValues.getCompanyId(), _group.getGroupId(), keywords));
	}

	@Test
	public void testSearchCountWithNoKeywords() throws Exception {
		_addJournalFeed(
			_ddmStructure, _ddmTemplate, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), _group, _layout,
			RandomTestUtil.randomString());
		_addJournalFeed(
			_ddmStructure, _ddmTemplate, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), _group, _layout,
			RandomTestUtil.randomString());

		Assert.assertEquals(
			2,
			_journalFeedLocalService.searchCount(
				TestPropsValues.getCompanyId(), _group.getGroupId(), null));
	}

	@Test
	public void testSearchWithKeywords() throws Exception {
		String description = RandomTestUtil.randomString();
		String feedId = StringUtil.toUpperCase(RandomTestUtil.randomString());
		String name = RandomTestUtil.randomString();

		JournalFeed journalFeed1 = _addJournalFeed(
			_ddmStructure, _ddmTemplate, description,
			RandomTestUtil.randomString(), _group, _layout,
			RandomTestUtil.randomString());
		JournalFeed journalFeed2 = _addJournalFeed(
			_ddmStructure, _ddmTemplate, RandomTestUtil.randomString(), feedId,
			_group, _layout, RandomTestUtil.randomString());
		JournalFeed journalFeed3 = _addJournalFeed(
			_ddmStructure, _ddmTemplate, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), _group, _layout, name);

		String keywords = StringBundler.concat(
			description, StringPool.SPACE, feedId, StringPool.SPACE, name);

		List<JournalFeed> journalFeeds = _journalFeedLocalService.search(
			TestPropsValues.getCompanyId(), _group.getGroupId(), keywords,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, new FeedNameComparator());

		Assert.assertEquals(journalFeeds.toString(), 3, journalFeeds.size());

		Assert.assertTrue(journalFeeds.contains(journalFeed1));
		Assert.assertTrue(journalFeeds.contains(journalFeed2));
		Assert.assertTrue(journalFeeds.contains(journalFeed3));
	}

	@Test
	public void testSearchWithNoKeywords() throws Exception {
		JournalFeed journalFeed1 = _addJournalFeed(
			_ddmStructure, _ddmTemplate, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), _group, _layout,
			RandomTestUtil.randomString());
		JournalFeed journalFeed2 = _addJournalFeed(
			_ddmStructure, _ddmTemplate, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), _group, _layout,
			RandomTestUtil.randomString());

		List<JournalFeed> journalFeeds = _journalFeedLocalService.search(
			TestPropsValues.getCompanyId(), _group.getGroupId(), null,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, new FeedNameComparator());

		Assert.assertEquals(journalFeeds.toString(), 2, journalFeeds.size());

		Assert.assertTrue(journalFeeds.contains(journalFeed1));
		Assert.assertTrue(journalFeeds.contains(journalFeed2));
	}

	private JournalFeed _addJournalFeed(
			DDMStructure ddmStructure, DDMTemplate ddmTemplate,
			String description, String feedId, Group group, Layout layout,
			String name)
		throws Exception {

		String friendlyURL = StringBundler.concat(
			PortalUtil.getPathFriendlyURLPublic(), group.getFriendlyURL(),
			layout.getFriendlyURL());

		return _journalFeedLocalService.addFeed(
			TestPropsValues.getUserId(), group.getGroupId(), feedId, false,
			name, description, ddmStructure.getStructureId(),
			ddmTemplate.getTemplateKey(), ddmTemplate.getTemplateKey(), 0,
			"modified-date", "as", friendlyURL, StringPool.BLANK,
			JournalFeedConstants.WEB_CONTENT_DESCRIPTION,
			RSSUtil.getFeedTypeFormat(RSSUtil.FEED_TYPE_DEFAULT),
			RSSUtil.getFeedTypeVersion(RSSUtil.FEED_TYPE_DEFAULT),
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));
	}

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@DeleteAfterTestRun
	private DDMStructure _ddmStructure;

	@DeleteAfterTestRun
	private DDMTemplate _ddmTemplate;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private JournalFeedLocalService _journalFeedLocalService;

	@DeleteAfterTestRun
	private Layout _layout;

}