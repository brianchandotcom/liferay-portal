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

package com.liferay.journal.change.tracking.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.test.util.BaseTableReferenceDefinitionTestCase;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.journal.constants.JournalArticleConstants;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Calendar;
import java.util.Locale;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Preston Crary
 */
@RunWith(Arquillian.class)
public class JournalArticleResourceTableReferenceDefinitionTest
	extends BaseTableReferenceDefinitionTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		Locale defaultLocale = LocaleUtil.getSiteDefault();

		DDMForm ddmForm = DDMStructureTestUtil.getSampleDDMForm(
			new Locale[] {defaultLocale}, defaultLocale);

		_ddmStructure = DDMStructureTestUtil.addStructure(
			group.getGroupId(), JournalArticle.class.getName(), ddmForm,
			defaultLocale);

		_ddmTemplate = DDMTemplateTestUtil.addTemplate(
			group.getGroupId(), _ddmStructure.getStructureId(),
			_portal.getClassNameId(JournalArticle.class));
	}

	@Override
	protected CTModel<?> addCTModel() throws Exception {
		Locale defaultLocale = LocaleUtil.getSiteDefault();

		User user = TestPropsValues.getUser();

		Calendar displayCal = CalendarFactoryUtil.getCalendar(
			user.getTimeZone());

		return _journalArticleLocalService.addArticle(
			null, user.getUserId(), group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASS_NAME_ID_DEFAULT, 0, StringPool.BLANK,
			true, JournalArticleConstants.VERSION_DEFAULT,
			HashMapBuilder.put(
				defaultLocale, RandomTestUtil.randomString()
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
			_ddmStructure.getStructureKey(), _ddmTemplate.getTemplateKey(),
			null, displayCal.get(Calendar.MONTH), displayCal.get(Calendar.DATE),
			displayCal.get(Calendar.YEAR), displayCal.get(Calendar.HOUR_OF_DAY),
			displayCal.get(Calendar.MINUTE), 0, 0, 0, 0, 0, true, 0, 0, 0, 0, 0,
			true, true, false, null, null, null, null,
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));
	}

	@Inject
	private static JournalArticleLocalService _journalArticleLocalService;

	@Inject
	private static Portal _portal;

	private DDMStructure _ddmStructure;
	private DDMTemplate _ddmTemplate;

}