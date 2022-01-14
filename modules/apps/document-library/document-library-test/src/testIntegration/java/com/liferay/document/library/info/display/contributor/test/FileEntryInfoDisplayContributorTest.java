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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.constants.FriendlyURLResolverConstants;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.util.PropsValues;

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
						fileEntry.getFileEntryId());

					ThemeDisplay themeDisplay = new ThemeDisplay();

					themeDisplay.setLocale(locale);
					themeDisplay.setScopeGroupId(_group.getGroupId());
					themeDisplay.setServerName("localhost");
					themeDisplay.setSiteGroupId(_group.getGroupId());

					Assert.assertEquals(
						expectedURL,
						_assetDisplayPageFriendlyURLProvider.getFriendlyURL(
							FileEntry.class.getName(),
							fileEntry.getFileEntryId(), themeDisplay));
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
						fileEntry.getFileEntryId());

					ThemeDisplay themeDisplay = new ThemeDisplay();

					themeDisplay.setLocale(locale);
					themeDisplay.setScopeGroupId(_group.getGroupId());
					themeDisplay.setServerName("localhost");
					themeDisplay.setSiteGroupId(_group.getGroupId());

					Assert.assertEquals(
						expectedURL,
						_assetDisplayPageFriendlyURLProvider.getFriendlyURL(
							FileEntry.class.getName(),
							fileEntry.getFileEntryId(), themeDisplay));
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
						fileEntry.getFileEntryId());

					ThemeDisplay themeDisplay = new ThemeDisplay();

					themeDisplay.setLocale(locale);
					themeDisplay.setScopeGroupId(_group.getGroupId());
					themeDisplay.setServerName("localhost");
					themeDisplay.setSiteGroupId(_group.getGroupId());

					Assert.assertEquals(
						expectedURL,
						_assetDisplayPageFriendlyURLProvider.getFriendlyURL(
							FileEntry.class.getName(),
							fileEntry.getFileEntryId(), themeDisplay));
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

					Locale locale = LocaleUtil.getDefault();

					String expectedURL = StringBundler.concat(
						"/web/", StringUtil.lowerCase(_group.getGroupKey()),
						FriendlyURLResolverConstants.URL_SEPARATOR_FILE_ENTRY,
						fileEntry.getFileEntryId());

					ThemeDisplay themeDisplay = new ThemeDisplay();

					themeDisplay.setLocale(locale);
					themeDisplay.setScopeGroupId(_group.getGroupId());
					themeDisplay.setServerName("localhost");
					themeDisplay.setSiteGroupId(_group.getGroupId());

					Assert.assertEquals(
						expectedURL,
						_assetDisplayPageFriendlyURLProvider.getFriendlyURL(
							FileEntry.class.getName(),
							fileEntry.getFileEntryId(), themeDisplay));
				});
		}
		finally {
			PropsValues.LOCALE_PREPEND_FRIENDLY_URL_STYLE =
				originalLocalePrependFriendlyURLStyle;
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
				LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE,
				WorkflowConstants.STATUS_DRAFT, serviceContext);

		_assetDisplayPageEntryLocalService.addAssetDisplayPageEntry(
			dlFileEntry.getUserId(), _group.getGroupId(),
			_portal.getClassNameId(FileEntry.class.getName()),
			dlFileEntry.getFileEntryId(),
			layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
			AssetDisplayPageConstants.TYPE_SPECIFIC, serviceContext);
	}

	private void _withAndWithoutAssetEntry(
			UnsafeConsumer<FileEntry, Exception> testFunction)
		throws Exception {

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

	@Inject
	private AssetDisplayPageEntryLocalService
		_assetDisplayPageEntryLocalService;

	@Inject
	private AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider;

	@Inject
	private DLAppLocalService _dlAppLocalService;

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