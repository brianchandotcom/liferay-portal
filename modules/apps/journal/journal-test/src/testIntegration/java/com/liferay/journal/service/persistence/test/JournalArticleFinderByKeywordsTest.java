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

package com.liferay.journal.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.journal.constants.JournalArticleConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.persistence.JournalArticleFinder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rubén Pulido
 */
@RunWith(Arquillian.class)
public class JournalArticleFinderByKeywordsTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			new TransactionalTestRule(
				Propagation.SUPPORTS, "com.liferay.journal.service"));

	@Before
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void setUp() throws Exception {
		Locale defaultLocale = LocaleUtil.getSiteDefault();

		DDMForm ddmForm = DDMStructureTestUtil.getSampleDDMForm(
			new Locale[] {defaultLocale}, defaultLocale);

		_group = GroupTestUtil.addGroup();

		_ddmStructure = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName(), ddmForm,
			defaultLocale);

		_journalArticles.add(
			_addJournalArticle(_ddmStructure, RandomTestUtil.randomString(10)));
		_journalArticles.add(
			_addJournalArticle(_ddmStructure, RandomTestUtil.randomString(10)));
	}

	@Test
	public void testFindByKeywords() {
		JournalArticle journalArticle = _journalArticles.get(0);

		_testFindByKeywords(1, journalArticle.getTitle());

		_testFindByKeywords(_journalArticles.size(), null);
	}

	private JournalArticle _addJournalArticle(
			DDMStructure ddmStructure, String title)
		throws Exception {

		User user = TestPropsValues.getUser();

		Locale defaultLocale = LocaleUtil.getSiteDefault();

		DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
			_group.getGroupId(), ddmStructure.getStructureId(),
			_portal.getClassNameId(JournalArticle.class));

		Calendar displayCalendar = CalendarFactoryUtil.getCalendar(
			user.getTimeZone());

		return _journalArticleLocalService.addArticle(
			null, user.getUserId(), _group.getGroupId(), 0,
			JournalArticleConstants.CLASS_NAME_ID_DEFAULT, 0, StringPool.BLANK,
			true, JournalArticleConstants.VERSION_DEFAULT,
			HashMapBuilder.put(
				defaultLocale, title
			).build(),
			HashMapBuilder.put(
				defaultLocale, RandomTestUtil.randomString()
			).build(),
			HashMapBuilder.put(
				defaultLocale, RandomTestUtil.randomString()
			).build(),
			DDMStructureTestUtil.getSampleStructuredContent(
				HashMapBuilder.put(
					defaultLocale, RandomTestUtil.randomString()
				).build(),
				LocaleUtil.toLanguageId(defaultLocale)),
			ddmStructure.getStructureKey(), ddmTemplate.getTemplateKey(), null,
			displayCalendar.get(Calendar.MONTH),
			displayCalendar.get(Calendar.DATE),
			displayCalendar.get(Calendar.YEAR),
			displayCalendar.get(Calendar.HOUR_OF_DAY),
			displayCalendar.get(Calendar.MINUTE), 0, 0, 0, 0, 0, true, 0, 0, 0,
			0, 0, true, true, false, null, null, null, null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	private void _testFindByKeywords(int expectedCount, String keywords) {
		List<JournalArticle> journalArticles =
			_journalArticleFinder.findByKeywords(
				_group.getCompanyId(), _group.getGroupId(), new ArrayList<>(),
				0, keywords, null, _ddmStructure.getStructureKey(), null, null,
				null, null, WorkflowConstants.STATUS_APPROVED, -1, -1, null);

		Assert.assertEquals(
			journalArticles.toString(), expectedCount, journalArticles.size());

		int count = _journalArticleFinder.countByKeywords(
			_group.getCompanyId(), _group.getGroupId(), new ArrayList<>(), 0,
			keywords, null, _ddmStructure.getStructureKey(), null, null, null,
			null, WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals(expectedCount, count);
	}

	@Inject
	private static JournalArticleFinder _journalArticleFinder;

	@Inject
	private static JournalArticleLocalService _journalArticleLocalService;

	@Inject
	private static Portal _portal;

	private DDMStructure _ddmStructure;

	@DeleteAfterTestRun
	private Group _group;

	private final List<JournalArticle> _journalArticles = new ArrayList<>();

}