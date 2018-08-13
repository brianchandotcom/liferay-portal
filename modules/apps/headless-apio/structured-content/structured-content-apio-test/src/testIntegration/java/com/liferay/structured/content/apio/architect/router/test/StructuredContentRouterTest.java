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

package com.liferay.structured.content.apio.architect.router.test;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;
import com.liferay.structured.content.apio.architect.model.JournalArticleWrapper;
import com.liferay.structured.content.apio.architect.router.StructuredContentRouter;
import com.liferay.structured.content.apio.architect.util.test.PaginationTestUtil;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class StructuredContentRouterTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testGetJournalArticle() throws Exception {

		//Given: A Journal article has been created

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		Map<Locale, String> keywordsMap = new HashMap<>();

		String keywords = "keywords";

		keywordsMap.put(LocaleUtil.getDefault(), keywords);
		keywordsMap.put(LocaleUtil.GERMANY, keywords);
		keywordsMap.put(LocaleUtil.SPAIN, keywords);

		String articleId = "Article.Id";

		String content = DDMStructureTestUtil.getSampleStructuredContent(
			keywordsMap, LocaleUtil.toLanguageId(LocaleUtil.getDefault()));

		DDMForm ddmForm = DDMStructureTestUtil.getSampleDDMForm(
			_locales, LocaleUtil.getDefault());

		long ddmGroupId = GetterUtil.getLong(
			serviceContext.getAttribute("ddmGroupId"), _group.getGroupId());

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			ddmGroupId, JournalArticle.class.getName(), ddmForm,
			LocaleUtil.getDefault());

		DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
			ddmGroupId, ddmStructure.getStructureId(),
			PortalUtil.getClassNameId(JournalArticle.class));

		Calendar displayCal = CalendarFactoryUtil.getCalendar(
			TestPropsValues.getUser().getTimeZone());

		JournalArticle journalArticle = _journalArticleLocalService.addArticle(
			serviceContext.getUserId(), _group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, 0, articleId, true,
			JournalArticleConstants.VERSION_DEFAULT, keywordsMap, keywordsMap,
			content, ddmStructure.getStructureKey(),
			ddmTemplate.getTemplateKey(), null, displayCal.get(Calendar.MONTH),
			displayCal.get(Calendar.DATE), displayCal.get(Calendar.YEAR),
			displayCal.get(Calendar.HOUR_OF_DAY),
			displayCal.get(Calendar.MINUTE), 0, 0, 0, 0, 0, true, 0, 0, 0, 0, 0,
			true, true, false, null, null, null, null, serviceContext);

		//When: The Journal Articles are requested
		PageItems<JournalArticleWrapper> pageItems =
			_structuredContentRouter.getPageItems(
				PaginationTestUtil.of(10, 1), _group.getGroupId(),
				_getThemeDisplay(_group));

		//Then: The Article is returned

		Assert.assertEquals(1, pageItems.getTotalCount());

		Collection<JournalArticleWrapper> items = pageItems.getItems();

		Assert.assertTrue("Items " + items, items.contains(journalArticle));
	}

	private ThemeDisplay _getThemeDisplay(Group group) throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		Company company = CompanyLocalServiceUtil.getCompanyById(
			group.getCompanyId());

		themeDisplay.setCompany(company);

		themeDisplay.setScopeGroupId(group.getGroupId());

		return themeDisplay;
	}

	private static final Locale[] _locales =
		{LocaleUtil.US, LocaleUtil.GERMANY, LocaleUtil.SPAIN};

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private JournalArticleLocalService _journalArticleLocalService;

	@Inject
	private StructuredContentRouter _structuredContentRouter;

}