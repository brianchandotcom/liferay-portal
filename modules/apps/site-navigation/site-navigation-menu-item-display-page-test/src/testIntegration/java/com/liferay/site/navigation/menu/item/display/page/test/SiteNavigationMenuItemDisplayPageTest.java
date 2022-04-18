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

package com.liferay.site.navigation.menu.item.display.page.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.display.page.portlet.AssetDisplayPageEntryFormProcessor;
import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalService;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemClassDetails;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.journal.model.JournalArticle;
import com.liferay.layout.display.page.LayoutDisplayPageInfoItemFieldValuesProvider;
import com.liferay.layout.display.page.LayoutDisplayPageInfoItemFieldValuesProviderTracker;
import com.liferay.layout.display.page.LayoutDisplayPageMultiSelectionProvider;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.info.item.capability.DisplayPageInfoItemCapability;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.site.navigation.constants.SiteNavigationConstants;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.SiteNavigationMenuItemLocalService;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalService;
import com.liferay.site.navigation.type.SiteNavigationMenuItemType;
import com.liferay.site.navigation.type.SiteNavigationMenuItemTypeRegistry;

import java.util.Collection;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Jürgen Kappler
 */
@RunWith(Arquillian.class)
public class SiteNavigationMenuItemDisplayPageTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId(), TestPropsValues.getUserId());

		_assetVocabulary = _assetVocabularyLocalService.addVocabulary(
			TestPropsValues.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(), _serviceContext);

		_assetCategory = _assetCategoryLocalService.addCategory(
			TestPropsValues.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(), _assetVocabulary.getVocabularyId(),
			_serviceContext);
	}

	@Test
	public void testDisplayPageTypeMultiSelection() throws Exception {
		ServiceRegistration<LayoutDisplayPageMultiSelectionProvider>
			serviceRegistration = null;

		try {
			SiteNavigationMenuItemType siteNavigationMenuItemType =
				_siteNavigationMenuItemTypeRegistry.
					getSiteNavigationMenuItemType(
						JournalArticle.class.getName());

			Assert.assertFalse(siteNavigationMenuItemType.isMultiSelection());

			Bundle bundle = FrameworkUtil.getBundle(
				LayoutDisplayPageMultiSelectionProvider.class);

			BundleContext bundleContext = bundle.getBundleContext();

			serviceRegistration = bundleContext.registerService(
				LayoutDisplayPageMultiSelectionProvider.class,
				new LayoutDisplayPageMultiSelectionProvider() {

					@Override
					public String getClassName() {
						return JournalArticle.class.getName();
					}

					@Override
					public String getPluralLabel(Locale locale) {
						return LanguageUtil.get(locale, "articles");
					}

				},
				new HashMapDictionary<String, String>());

			Assert.assertTrue(siteNavigationMenuItemType.isMultiSelection());
		}
		finally {
			if (serviceRegistration != null) {
				serviceRegistration.unregister();
			}
		}
	}

	@Test
	public void testDisplayPageTypeMultiSelectionCategories() throws Exception {
		SiteNavigationMenuItemType siteNavigationMenuItemType =
			_siteNavigationMenuItemTypeRegistry.getSiteNavigationMenuItemType(
				AssetCategory.class.getName());

		Assert.assertTrue(siteNavigationMenuItemType.isMultiSelection());
	}

	@Test
	public void testLayoutDisplayPageInfoItemFieldValuesProvider()
		throws Exception {

		LayoutDisplayPageInfoItemFieldValuesProvider
			assetCategoryLayoutDisplayPageInfoItemFieldValuesProvider =
				_layoutDisplayPageInfoItemFieldValuesProviderTracker.
					getLayoutDisplayPageInfoItemFieldValuesProvider(
						AssetCategory.class.getName());

		Assert.assertNotNull(
			assetCategoryLayoutDisplayPageInfoItemFieldValuesProvider);

		InfoItemFieldValues infoItemFieldValues =
			assetCategoryLayoutDisplayPageInfoItemFieldValuesProvider.
				getInfoItemFieldValues(_assetCategory);

		Collection<InfoFieldValue<Object>> infoFieldValues =
			infoItemFieldValues.getInfoFieldValues();

		Assert.assertEquals(
			infoFieldValues.toString(), 2, infoFieldValues.size());

		Locale locale = _portal.getSiteDefaultLocale(_group.getGroupId());

		for (InfoFieldValue<Object> infoFieldValue : infoFieldValues) {
			InfoField infoField = infoFieldValue.getInfoField();

			Assert.assertTrue(
				Objects.equals(infoField.getName(), "group") ||
				Objects.equals(infoField.getName(), "vocabulary"));

			if (Objects.equals(infoField.getName(), "group")) {
				Assert.assertEquals(
					_group.getDescriptiveName(locale),
					infoFieldValue.getValue(locale));
			}
			else {
				Assert.assertEquals(
					_assetVocabulary.getTitle(locale),
					infoFieldValue.getValue(locale));
			}
		}
	}

	@Test
	public void testSiteNavigationMenuItemDisplayPageTypes() {
		for (InfoItemClassDetails infoItemClassDetails :
				_infoItemServiceTracker.getInfoItemClassDetails(
					DisplayPageInfoItemCapability.KEY)) {

			Assert.assertNotNull(
				_siteNavigationMenuItemTypeRegistry.
					getSiteNavigationMenuItemType(
						infoItemClassDetails.getClassName()));
		}
	}

	@Test
	public void testSiteNavigationMenuItemDisplayPageURL() throws Exception {
		_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
			_group.getCreatorUserId(), _group.getGroupId(), 0,
			_portal.getClassNameId(AssetCategory.class.getName()), 0,
			RandomTestUtil.randomString(),
			LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE, 0, true, 0,
			0, 0, 0, _serviceContext);

		SiteNavigationMenu siteNavigationMenu =
			_siteNavigationMenuLocalService.addSiteNavigationMenu(
				TestPropsValues.getUserId(), _group.getGroupId(), "Menu",
				SiteNavigationConstants.TYPE_DEFAULT, true, _serviceContext);

		UnicodeProperties typeSettingsUnicodeProperties =
			UnicodePropertiesBuilder.put(
				"classNameId",
				String.valueOf(
					_portal.getClassNameId(AssetCategory.class.getName()))
			).put(
				"classPK", String.valueOf(_assetCategory.getCategoryId())
			).build();

		SiteNavigationMenuItem siteNavigationMenuItem =
			_siteNavigationMenuItemLocalService.addSiteNavigationMenuItem(
				TestPropsValues.getUserId(), _group.getGroupId(),
				siteNavigationMenu.getSiteNavigationMenuId(), 0,
				AssetCategory.class.getName(),
				typeSettingsUnicodeProperties.toString(), _serviceContext);

		Assert.assertEquals(
			1,
			_siteNavigationMenuItemLocalService.getSiteNavigationMenuItemsCount(
				siteNavigationMenu.getSiteNavigationMenuId()));

		ThemeDisplay themeDisplay = _getThemeDisplay();

		String friendlyURL =
			_assetDisplayPageFriendlyURLProvider.getFriendlyURL(
				_portal.getClassName(
					GetterUtil.getLong(
						typeSettingsUnicodeProperties.get("classNameId"))),
				GetterUtil.getLong(
					typeSettingsUnicodeProperties.get("classPK")),
				themeDisplay);

		SiteNavigationMenuItemType siteNavigationMenuItemType =
			_siteNavigationMenuItemTypeRegistry.getSiteNavigationMenuItemType(
				siteNavigationMenuItem.getType());

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		Assert.assertEquals(
			friendlyURL,
			siteNavigationMenuItemType.getRegularURL(
				mockHttpServletRequest, siteNavigationMenuItem));

		SiteNavigationMenuItemType defaultSiteNavigationMenuItemType =
			new SiteNavigationMenuItemType() {

				@Override
				public String getLabel(Locale locale) {
					return null;
				}

			};

		Assert.assertEquals(
			defaultSiteNavigationMenuItemType.getStatusIcon(
				siteNavigationMenuItem),
			siteNavigationMenuItemType.getStatusIcon(siteNavigationMenuItem));
	}

	@Test
	public void testSiteNavigationMenuItemTitleUseCustomNameDisabled()
		throws Exception {

		Locale locale = _portal.getSiteDefaultLocale(_group.getGroupId());

		SiteNavigationMenuItem siteNavigationMenuItem =
			_createSiteNavigationMenuItem(locale, "{}");

		SiteNavigationMenuItemType siteNavigationMenuItemType =
			_siteNavigationMenuItemTypeRegistry.getSiteNavigationMenuItemType(
				siteNavigationMenuItem.getType());

		Assert.assertEquals(
			_assetCategory.getTitle(locale),
			siteNavigationMenuItemType.getTitle(
				siteNavigationMenuItem, locale));
	}

	@Test
	public void testSiteNavigationMenuItemTitleUsingCustomName()
		throws Exception {

		String expectedTitle = RandomTestUtil.randomString();
		Locale locale = _portal.getSiteDefaultLocale(_group.getGroupId());

		SiteNavigationMenuItem siteNavigationMenuItem =
			_createSiteNavigationMenuItem(
				locale,
				JSONUtil.put(
					LocaleUtil.toLanguageId(locale), expectedTitle
				).toJSONString());

		SiteNavigationMenuItemType siteNavigationMenuItemType =
			_siteNavigationMenuItemTypeRegistry.getSiteNavigationMenuItemType(
				siteNavigationMenuItem.getType());

		Assert.assertEquals(
			expectedTitle,
			siteNavigationMenuItemType.getTitle(
				siteNavigationMenuItem, locale));
	}

	@Test
	public void testSiteNavigationMenuItemTitleUsingCustomNameNondefaultLocale()
		throws Exception {

		String expectedTitle = RandomTestUtil.randomString();
		Locale defaultLocale = _portal.getSiteDefaultLocale(
			_group.getGroupId());

		Set<Locale> locales = LanguageUtil.getAvailableLocales();

		Stream<Locale> stream = locales.stream();

		Locale nondefaultLocale = stream.filter(
			locale -> !Objects.equals(defaultLocale, locale)
		).findFirst(
		).orElse(
			null
		);

		Assert.assertNotNull(nondefaultLocale);

		SiteNavigationMenuItem siteNavigationMenuItem =
			_createSiteNavigationMenuItem(
				defaultLocale,
				JSONUtil.put(
					LocaleUtil.toLanguageId(defaultLocale),
					RandomTestUtil.randomString()
				).put(
					LocaleUtil.toLanguageId(nondefaultLocale), expectedTitle
				).toJSONString());

		SiteNavigationMenuItemType siteNavigationMenuItemType =
			_siteNavigationMenuItemTypeRegistry.getSiteNavigationMenuItemType(
				siteNavigationMenuItem.getType());

		Assert.assertEquals(
			expectedTitle,
			siteNavigationMenuItemType.getTitle(
				siteNavigationMenuItem, nondefaultLocale));
	}

	@Test
	public void testSiteNavigationMenuItemTitleUsingCustomNameNontranslatedLocale()
		throws Exception {

		String expectedTitle = RandomTestUtil.randomString();
		Locale defaultLocale = _portal.getSiteDefaultLocale(
			_group.getGroupId());

		Set<Locale> locales = LanguageUtil.getAvailableLocales();

		Stream<Locale> stream = locales.stream();

		Locale nontranslatedLocale = stream.filter(
			locale -> !Objects.equals(defaultLocale, locale)
		).findFirst(
		).orElse(
			null
		);

		Assert.assertNotNull(nontranslatedLocale);

		SiteNavigationMenuItem siteNavigationMenuItem =
			_createSiteNavigationMenuItem(
				defaultLocale,
				JSONUtil.put(
					LocaleUtil.toLanguageId(defaultLocale), expectedTitle
				).toJSONString());

		SiteNavigationMenuItemType siteNavigationMenuItemType =
			_siteNavigationMenuItemTypeRegistry.getSiteNavigationMenuItemType(
				siteNavigationMenuItem.getType());

		Assert.assertEquals(
			expectedTitle,
			siteNavigationMenuItemType.getTitle(
				siteNavigationMenuItem, nontranslatedLocale));
	}

	@Test
	public void testSiteNavigationMenuItemWithNoDisplayPage() throws Exception {
		SiteNavigationMenu siteNavigationMenu =
			_siteNavigationMenuLocalService.addSiteNavigationMenu(
				TestPropsValues.getUserId(), _group.getGroupId(), "Menu",
				SiteNavigationConstants.TYPE_DEFAULT, true, _serviceContext);

		SiteNavigationMenuItem siteNavigationMenuItem =
			_siteNavigationMenuItemLocalService.addSiteNavigationMenuItem(
				TestPropsValues.getUserId(), _group.getGroupId(),
				siteNavigationMenu.getSiteNavigationMenuId(), 0,
				AssetCategory.class.getName(),
				UnicodePropertiesBuilder.put(
					"classNameId",
					String.valueOf(
						_portal.getClassNameId(AssetCategory.class.getName()))
				).put(
					"classPK", String.valueOf(_assetCategory.getCategoryId())
				).buildString(),
				_serviceContext);

		Assert.assertEquals(
			1,
			_siteNavigationMenuItemLocalService.getSiteNavigationMenuItemsCount(
				siteNavigationMenu.getSiteNavigationMenuId()));

		SiteNavigationMenuItemType siteNavigationMenuItemType =
			_siteNavigationMenuItemTypeRegistry.getSiteNavigationMenuItemType(
				siteNavigationMenuItem.getType());

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		ThemeDisplay themeDisplay = _getThemeDisplay();

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		Assert.assertEquals(
			themeDisplay.getURLCurrent() + StringPool.POUND,
			siteNavigationMenuItemType.getRegularURL(
				mockHttpServletRequest, siteNavigationMenuItem));

		Assert.assertEquals(
			"warning-full",
			siteNavigationMenuItemType.getStatusIcon(siteNavigationMenuItem));
	}

	private SiteNavigationMenuItem _createSiteNavigationMenuItem(
			Locale defaultLocale, String localizedNames)
		throws Exception {

		SiteNavigationMenu siteNavigationMenu =
			_siteNavigationMenuLocalService.addSiteNavigationMenu(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(),
				SiteNavigationConstants.TYPE_DEFAULT, true, _serviceContext);

		return _siteNavigationMenuItemLocalService.addSiteNavigationMenuItem(
			TestPropsValues.getUserId(), _group.getGroupId(),
			siteNavigationMenu.getSiteNavigationMenuId(), 0,
			AssetCategory.class.getName(),
			UnicodePropertiesBuilder.create(
				true
			).put(
				Field.DEFAULT_LANGUAGE_ID,
				LocaleUtil.toLanguageId(defaultLocale)
			).put(
				"className", AssetCategory.class.getName()
			).put(
				"classNameId",
				String.valueOf(
					_portal.getClassNameId(AssetCategory.class.getName()))
			).put(
				"classPK", String.valueOf(_assetCategory.getCategoryId())
			).put(
				"localizedNames", localizedNames
			).put(
				"title", _assetCategory.getTitle(defaultLocale)
			).put(
				"type",
				ResourceActionsUtil.getModelResource(
					defaultLocale, AssetCategory.class.getName())
			).put(
				"useCustomName",
				String.valueOf(!Objects.equals("{}", localizedNames))
			).buildString(),
			_serviceContext);
	}

	private ThemeDisplay _getThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(_group.getCompanyId()));
		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	private AssetCategory _assetCategory;

	@Inject
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Inject
	private AssetDisplayPageEntryFormProcessor
		_assetDisplayPageEntryFormProcessor;

	@Inject
	private AssetDisplayPageEntryLocalService
		_assetDisplayPageEntryLocalService;

	@Inject
	private AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider;

	private AssetVocabulary _assetVocabulary;

	@Inject
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Inject
	private LayoutDisplayPageInfoItemFieldValuesProviderTracker
		_layoutDisplayPageInfoItemFieldValuesProviderTracker;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject
	private Portal _portal;

	private ServiceContext _serviceContext;

	@Inject
	private SiteNavigationMenuItemLocalService
		_siteNavigationMenuItemLocalService;

	@Inject
	private SiteNavigationMenuItemTypeRegistry
		_siteNavigationMenuItemTypeRegistry;

	@Inject
	private SiteNavigationMenuLocalService _siteNavigationMenuLocalService;

}