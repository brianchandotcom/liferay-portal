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

package com.liferay.journal.info.display.contributor.field.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.display.page.constants.AssetDisplayPageConstants;
import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Jürgen Kappler
 */
@RunWith(Arquillian.class)
public class JournalArticleDisplayPageURLDisplayContributorFieldTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user = UserTestUtil.addUser(_group.getGroupId());

		_company = _companyLocalService.getCompany(_group.getCompanyId());
		_layout = LayoutTestUtil.addLayout(_group);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());

		_serviceContext.setRequest(_getMockHttpServletRequest());

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);
	}

	@Test
	public void testCannotSelectDisplayPageURLIfNoneExists() throws Exception {
		InfoDisplayContributor<JournalArticle> infoDisplayContributor =
			_infoDisplayContributorTracker.getInfoDisplayContributor(
				JournalArticle.class.getName());

		JournalArticle journalArticle = _getJournalArticle();

		Map<String, Object> fieldsValues =
			infoDisplayContributor.getInfoDisplayFieldsValues(
				journalArticle, LocaleUtil.ENGLISH);

		Assert.assertNull(fieldsValues.get("displayPageURL"));
	}

	@Test
	public void testCanSelectDefaultDisplayPageURL() throws Exception {
		InfoDisplayContributor<JournalArticle> infoDisplayContributor =
			_infoDisplayContributorTracker.getInfoDisplayContributor(
				JournalArticle.class.getName());

		JournalArticle article = _getJournalArticle();

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_getLayoutPageTemplateEntry(article, true);

		_assetDisplayPageEntryLocalService.addAssetDisplayPageEntry(
			_user.getUserId(), _group.getGroupId(),
			_portal.getClassNameId(JournalArticle.class.getName()),
			article.getResourcePrimKey(),
			layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
			AssetDisplayPageConstants.TYPE_DEFAULT, _serviceContext);

		Map<String, Object> fieldsValues =
			infoDisplayContributor.getInfoDisplayFieldsValues(
				article, LocaleUtil.getDefault());

		Assert.assertTrue(fieldsValues.containsKey("displayPageURL"));

		String expectedDisplayPageURL = _buildFriendlyURL(
			infoDisplayContributor, article);

		Assert.assertEquals(
			expectedDisplayPageURL, fieldsValues.get("displayPageURL"));
	}

	@Test
	public void testCanSelectSpecificDisplayPageURL() throws Exception {
		InfoDisplayContributor<JournalArticle> infoDisplayContributor =
			_infoDisplayContributorTracker.getInfoDisplayContributor(
				JournalArticle.class.getName());

		JournalArticle article = _getJournalArticle();

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_getLayoutPageTemplateEntry(article, false);

		_assetDisplayPageEntryLocalService.addAssetDisplayPageEntry(
			_user.getUserId(), _group.getGroupId(),
			_portal.getClassNameId(JournalArticle.class.getName()),
			article.getResourcePrimKey(),
			layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
			AssetDisplayPageConstants.TYPE_SPECIFIC, _serviceContext);

		Map<String, Object> fieldsValues =
			infoDisplayContributor.getInfoDisplayFieldsValues(
				article, LocaleUtil.getDefault());

		Assert.assertTrue(fieldsValues.containsKey("displayPageURL"));

		String expectedDisplayPageURL = _buildFriendlyURL(
			infoDisplayContributor, article);

		Assert.assertEquals(
			expectedDisplayPageURL, fieldsValues.get("displayPageURL"));
	}

	private String _buildFriendlyURL(
			InfoDisplayContributor infoDisplayContributor,
			JournalArticle article)
		throws PortalException {

		StringBundler sb = new StringBundler(4);

		Map<Locale, String> friendlyURLMap = article.getFriendlyURLMap();

		sb.append("/web");
		sb.append(_group.getFriendlyURL());
		sb.append(infoDisplayContributor.getInfoURLSeparator());
		sb.append(friendlyURLMap.get(LocaleUtil.getDefault()));

		return sb.toString();
	}

	private JournalArticle _getJournalArticle() throws Exception {
		Map<Locale, String> titleMap = HashMapBuilder.put(
			LocaleUtil.getDefault(), "title"
		).build();

		Map<Locale, String> contentMap = HashMapBuilder.put(
			LocaleUtil.getDefault(), "content"
		).build();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		return JournalTestUtil.addArticle(
			_group.getGroupId(), 0,
			PortalUtil.getClassNameId(JournalArticle.class), titleMap, null,
			contentMap, LocaleUtil.getSiteDefault(), false, true,
			serviceContext);
	}

	private LayoutPageTemplateEntry _getLayoutPageTemplateEntry(
			JournalArticle article, boolean defaultLayoutPageTemplateEntry)
		throws PortalException {

		DDMStructure ddmStructure = article.getDDMStructure();

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryService.addLayoutPageTemplateEntry(
				_group.getGroupId(), RandomTestUtil.randomLong(),
				RandomTestUtil.randomString(),
				LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE,
				_portal.getClassNameId(JournalArticle.class.getName()),
				ddmStructure.getStructureId(), _serviceContext);

		if (defaultLayoutPageTemplateEntry) {
			layoutPageTemplateEntry =
				_layoutPageTemplateEntryService.updateLayoutPageTemplateEntry(
					layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
					true);
		}

		return layoutPageTemplateEntry;
	}

	private MockHttpServletRequest _getMockHttpServletRequest()
		throws PortalException {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());

		return mockHttpServletRequest;
	}

	private ThemeDisplay _getThemeDisplay() throws PortalException {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(_company);
		themeDisplay.setLayout(_layout);
		themeDisplay.setLayoutSet(_layout.getLayoutSet());
		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	@Inject
	private AssetDisplayPageEntryLocalService
		_assetDisplayPageEntryLocalService;

	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private InfoDisplayContributorTracker _infoDisplayContributorTracker;

	private Layout _layout;

	@Inject
	private LayoutPageTemplateEntryService _layoutPageTemplateEntryService;

	@Inject
	private Portal _portal;

	private ServiceContext _serviceContext;

	@DeleteAfterTestRun
	private User _user;

}