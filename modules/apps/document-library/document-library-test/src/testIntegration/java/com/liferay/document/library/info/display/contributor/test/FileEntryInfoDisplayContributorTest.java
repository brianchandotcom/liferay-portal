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

package com.liferay.document.library.info.display.contributor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.display.page.constants.AssetDisplayPageConstants;
import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalService;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.test.util.DLTestUtil;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryService;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.constants.FriendlyURLResolverConstants;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FriendlyURLNormalizer;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.util.PropsValues;

import java.util.Collections;
import java.util.Locale;

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
public class FileEntryInfoDisplayContributorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testDisplayPageURLCustomLocaleAlgorithm1() throws Exception {
		int originalLocalePrependFriendlyURLStyle =
			PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE;

		try {
			PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE = 1;

			_withAndWithoutAssetEntry(
				fileEntry -> {
					_addAssetDisplayPageEntry(fileEntry);

					Locale locale = LocaleUtil.FRANCE;

					String expectedURL = StringBundler.concat(
						"/", locale.getLanguage(), "/web/",
						StringUtil.lowerCase(_group.getGroupKey()),
						FriendlyURLResolverConstants.URL_SEPARATOR_FILE_ENTRY,
						_friendlyURLNormalizer.normalizeWithPeriodsAndSlashes(
							fileEntry.getTitle()));

					Assert.assertEquals(
						expectedURL,
						_assetDisplayPageFriendlyURLProvider.getFriendlyURL(
							FileEntry.class.getName(),
							fileEntry.getFileEntryId(),
							_getThemeDisplay(locale)));
				});
		}
		finally {
			PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE =
				originalLocalePrependFriendlyURLStyle;
		}
	}

	@Test
	public void testDisplayPageURLCustomLocaleAlgorithm1DefaultLocale()
		throws Exception {

		int originalLocalePrependFriendlyURLStyle =
			PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE;

		try {
			_withAndWithoutAssetEntry(
				fileEntry -> {
					_addAssetDisplayPageEntry(fileEntry);

					Locale locale = LocaleUtil.getDefault();

					String expectedURL = StringBundler.concat(
						"/web/", StringUtil.lowerCase(_group.getGroupKey()),
						FriendlyURLResolverConstants.URL_SEPARATOR_FILE_ENTRY,
						_friendlyURLNormalizer.normalizeWithPeriodsAndSlashes(
							fileEntry.getTitle()));

					Assert.assertEquals(
						expectedURL,
						_assetDisplayPageFriendlyURLProvider.getFriendlyURL(
							FileEntry.class.getName(),
							fileEntry.getFileEntryId(),
							_getThemeDisplay(locale)));
				});
		}
		finally {
			PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE =
				originalLocalePrependFriendlyURLStyle;
		}
	}

	@Test
	public void testDisplayPageURLCustomLocaleAlgorithm2() throws Exception {
		int originalLocalePrependFriendlyURLStyle =
			PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE;

		try {
			PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE = 2;

			_withAndWithoutAssetEntry(
				fileEntry -> {
					_addAssetDisplayPageEntry(fileEntry);

					Locale locale = LocaleUtil.getDefault();

					String expectedURL = StringBundler.concat(
						"/", locale.getLanguage(), "/web/",
						StringUtil.lowerCase(_group.getGroupKey()),
						FriendlyURLResolverConstants.URL_SEPARATOR_FILE_ENTRY,
						_friendlyURLNormalizer.normalizeWithPeriodsAndSlashes(
							fileEntry.getTitle()));

					Assert.assertEquals(
						expectedURL,
						_assetDisplayPageFriendlyURLProvider.getFriendlyURL(
							FileEntry.class.getName(),
							fileEntry.getFileEntryId(),
							_getThemeDisplay(locale)));
				});
		}
		finally {
			PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE =
				originalLocalePrependFriendlyURLStyle;
		}
	}

	@Test
	public void testDisplayPageURLCustomLocaleAlgorithmDefault()
		throws Exception {

		int originalLocalePrependFriendlyURLStyle =
			PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE;

		try {
			_withAndWithoutAssetEntry(
				fileEntry -> {
					_addAssetDisplayPageEntry(fileEntry);

					String expectedURL = StringBundler.concat(
						"/web/", StringUtil.lowerCase(_group.getGroupKey()),
						FriendlyURLResolverConstants.URL_SEPARATOR_FILE_ENTRY,
						_friendlyURLNormalizer.normalizeWithPeriodsAndSlashes(
							fileEntry.getTitle()));

					Assert.assertEquals(
						expectedURL,
						_assetDisplayPageFriendlyURLProvider.getFriendlyURL(
							FileEntry.class.getName(),
							fileEntry.getFileEntryId(),
							_getThemeDisplay(LocaleUtil.getDefault())));
				});
		}
		finally {
			PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE =
				originalLocalePrependFriendlyURLStyle;
		}
	}

	@Test
	public void testDisplayPageURLFileFromDepotEntry() throws Exception {
		ServiceContextThreadLocal.pushServiceContext(
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		try {
			_depotEntry = _depotEntryLocalService.addDepotEntry(
				Collections.singletonMap(
					LocaleUtil.getDefault(), RandomTestUtil.randomString()),
				Collections.singletonMap(
					LocaleUtil.getDefault(), RandomTestUtil.randomString()),
				ServiceContextTestUtil.getServiceContext());

			DLFolder dlFolder = DLTestUtil.addDLFolder(
				_depotEntry.getGroupId());

			DLFileEntry dlFileEntry = DLTestUtil.addDLFileEntry(
				dlFolder.getFolderId());

			FileEntry fileEntry = _dlAppLocalService.getFileEntry(
				dlFileEntry.getFileEntryId());

			_addAssetDisplayPageEntry(fileEntry);

			Group depotEntryGroup = _depotEntry.getGroup();

			String expectedURL = StringBundler.concat(
				"/web/", StringUtil.lowerCase(_group.getGroupKey()),
				FriendlyURLResolverConstants.URL_SEPARATOR_X_FILE_ENTRY,
				depotEntryGroup.getFriendlyURL(), StringPool.SLASH,
				_friendlyURLNormalizer.normalizeWithPeriodsAndSlashes(
					fileEntry.getTitle()));

			Assert.assertEquals(
				expectedURL,
				_assetDisplayPageFriendlyURLProvider.getFriendlyURL(
					FileEntry.class.getName(), fileEntry.getFileEntryId(),
					_getThemeDisplay(LocaleUtil.getDefault())));
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	private void _addAssetDisplayPageEntry(FileEntry dlFileEntry)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_layoutPageTemplateCollectionService.
				addLayoutPageTemplateCollection(
					_group.getGroupId(), RandomTestUtil.randomString(), null,
					serviceContext);

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryService.addLayoutPageTemplateEntry(
				_group.getGroupId(),
				layoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId(),
				RandomTestUtil.randomString(),
				LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE, 0,
				WorkflowConstants.STATUS_DRAFT, serviceContext);

		_assetDisplayPageEntryLocalService.addAssetDisplayPageEntry(
			dlFileEntry.getUserId(), _group.getGroupId(),
			_portal.getClassNameId(FileEntry.class.getName()),
			dlFileEntry.getFileEntryId(),
			layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
			AssetDisplayPageConstants.TYPE_SPECIFIC, serviceContext);
	}

	private ThemeDisplay _getThemeDisplay(Locale locale) {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setLocale(locale);
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setServerName("localhost");
		themeDisplay.setSiteGroupId(_group.getGroupId());

		return themeDisplay;
	}

	private void _withAndWithoutAssetEntry(
			UnsafeConsumer<FileEntry, Exception> testFunction)
		throws Exception {

		ServiceContextThreadLocal.pushServiceContext(
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		try {
			DLFolder dlFolder = DLTestUtil.addDLFolder(_group.getGroupId());

			DLFileEntry dlFileEntry = DLTestUtil.addDLFileEntry(
				dlFolder.getFolderId());

			testFunction.accept(
				_dlAppLocalService.getFileEntry(dlFileEntry.getFileEntryId()));

			dlFileEntry = DLTestUtil.addDLFileEntry(dlFolder.getFolderId());

			AssetEntryLocalServiceUtil.deleteEntry(
				FileEntry.class.getName(), dlFileEntry.getFileEntryId());

			testFunction.accept(
				_dlAppLocalService.getFileEntry(dlFileEntry.getFileEntryId()));
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Inject
	private AssetDisplayPageEntryLocalService
		_assetDisplayPageEntryLocalService;

	@Inject
	private AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider;

	@DeleteAfterTestRun
	private DepotEntry _depotEntry;

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@Inject
	private FriendlyURLNormalizer _friendlyURLNormalizer;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutPageTemplateCollectionService
		_layoutPageTemplateCollectionService;

	@Inject
	private LayoutPageTemplateEntryService _layoutPageTemplateEntryService;

	@Inject
	private Portal _portal;

}