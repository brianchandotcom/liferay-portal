/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.analytics.reports.document.library.internal.info.item.test;

import com.liferay.analytics.reports.info.item.AnalyticsReportsInfoItem;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.display.page.constants.AssetDisplayPageConstants;
import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalService;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class FileEntryAnalyticsReportsInfoItemTest {

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
	public void testGetActions() throws Exception {
		Assert.assertEquals(
			Arrays.asList(
				AnalyticsReportsInfoItem.Action.HISTORICAL_VIEWS,
				AnalyticsReportsInfoItem.Action.TOTAL_VIEWS,
				AnalyticsReportsInfoItem.Action.TRAFFIC_CHANNELS),
			_analyticsReportsInfoItem.getActions());
	}

	@Test
	public void testGetAuthorName() throws Exception {
		User user = TestPropsValues.getUser();

		FileEntry fileEntry = _addFileEntry(_group);

		Assert.assertEquals(
			user.getFullName(),
			_analyticsReportsInfoItem.getAuthorName(fileEntry));
	}

	@Test
	public void testGetAuthorUserId() throws Exception {
		User user = TestPropsValues.getUser();

		FileEntry fileEntry = _addFileEntry(_group);

		Assert.assertEquals(
			user.getUserId(),
			_analyticsReportsInfoItem.getAuthorUserId(fileEntry));
	}

	@Test
	public void testGetAvailableLocales() throws Exception {
		FileEntry fileEntry = _addFileEntry(_group);

		GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(),
			Arrays.asList(LocaleUtil.BRAZIL, LocaleUtil.SPAIN),
			LocaleUtil.SPAIN);

		Assert.assertEquals(
			Arrays.asList(LocaleUtil.BRAZIL, LocaleUtil.SPAIN),
			_analyticsReportsInfoItem.getAvailableLocales(fileEntry));
	}

	@Test
	public void testGetCanonicalURL() throws Exception {
		User user = TestPropsValues.getUser();

		FileEntry fileEntry = _addFileEntry(_group);

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				_group.getCreatorUserId(), _group.getGroupId(), 0,
				_portal.getClassNameId(FileEntry.class.getName()), 0,
				RandomTestUtil.randomString(),
				LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE, 0, true,
				0, 0, 0, 0,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_assetDisplayPageEntryLocalService.addAssetDisplayPageEntry(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_portal.getClassNameId(FileEntry.class.getName()),
			fileEntry.getFileEntryId(),
			layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
			AssetDisplayPageConstants.TYPE_SPECIFIC,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		try {
			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(_group.getGroupId());

			MockHttpServletRequest mockHttpServletRequest =
				new MockHttpServletRequest();

			mockHttpServletRequest.setAttribute(
				WebKeys.CURRENT_COMPLETE_URL, StringPool.BLANK);

			ThemeDisplay themeDisplay = new ThemeDisplay();

			themeDisplay.setCompany(
				_companyLocalService.fetchCompany(
					TestPropsValues.getCompanyId()));

			Layout layout = _layoutLocalService.addLayout(
				user.getUserId(), _group.getGroupId(), false,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				StringPool.BLANK, LayoutConstants.TYPE_CONTENT, false,
				StringPool.BLANK, serviceContext);

			themeDisplay.setLayoutSet(layout.getLayoutSet());

			themeDisplay.setRequest(mockHttpServletRequest);
			themeDisplay.setSiteGroupId(_group.getGroupId());
			themeDisplay.setUser(user);

			mockHttpServletRequest.setAttribute(
				WebKeys.THEME_DISPLAY, themeDisplay);

			serviceContext.setRequest(mockHttpServletRequest);

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			Assert.assertNotNull(
				_analyticsReportsInfoItem.getCanonicalURL(
					fileEntry, LocaleUtil.US));
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test
	public void testGetDefaultLocale() throws Exception {
		FileEntry fileEntry = _addFileEntry(_group);

		GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(),
			Arrays.asList(LocaleUtil.BRAZIL, LocaleUtil.SPAIN),
			LocaleUtil.SPAIN);

		Assert.assertEquals(
			LocaleUtil.SPAIN,
			_analyticsReportsInfoItem.getDefaultLocale(fileEntry));
	}

	@Test
	public void testGetPublishDate() throws Exception {
		FileEntry fileEntry = _addFileEntry(_group);

		Assert.assertEquals(
			fileEntry.getCreateDate(),
			_analyticsReportsInfoItem.getPublishDate(fileEntry));
	}

	@Test
	public void testGetTitle() throws Exception {
		FileEntry fileEntry = _addFileEntry(_group);

		Assert.assertEquals(
			fileEntry.getTitle(),
			_analyticsReportsInfoItem.getTitle(fileEntry, LocaleUtil.US));
	}

	private FileEntry _addFileEntry(Group group) throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId());

		Folder folder = _dlAppLocalService.addFolder(
			TestPropsValues.getUserId(), group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			serviceContext);

		return _dlAppLocalService.addFileEntry(
			null, serviceContext.getUserId(), folder.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), ContentTypes.TEXT_PLAIN,
			RandomTestUtil.randomString(), StringPool.BLANK, StringPool.BLANK,
			StringPool.BLANK, "liferay".getBytes(), null, null, serviceContext);
	}

	@Inject(filter = "component.name=*.FileEntryAnalyticsReportsInfoItem")
	private AnalyticsReportsInfoItem<FileEntry> _analyticsReportsInfoItem;

	@Inject
	private AssetDisplayPageEntryLocalService
		_assetDisplayPageEntryLocalService;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject
	private Portal _portal;

	@Inject
	private UserLocalService _userLocalService;

}