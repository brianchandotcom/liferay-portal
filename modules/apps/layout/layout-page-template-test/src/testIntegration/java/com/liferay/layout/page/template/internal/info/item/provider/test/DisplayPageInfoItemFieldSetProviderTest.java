/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.internal.info.item.provider.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.display.page.constants.AssetDisplayPageConstants;
import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalService;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.type.WebURL;
import com.liferay.journal.constants.JournalArticleConstants;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.layout.display.page.constants.LayoutDisplayPageWebKeys;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.info.item.provider.DisplayPageInfoItemFieldSetProvider;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.constants.FriendlyURLResolverConstants;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Lourdes Fernández Besada
 */
@RunWith(Arquillian.class)
public class DisplayPageInfoItemFieldSetProviderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		_group = GroupTestUtil.addGroup();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());

		_journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASS_NAME_ID_DEFAULT, StringPool.BLANK,
			true, RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(), null,
			LocaleUtil.getSiteDefault(), null, false, false, _serviceContext);

		_classNameId = _portal.getClassNameId(JournalArticle.class.getName());

		_layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				_group.getCreatorUserId(), _group.getGroupId(), 0, _classNameId,
				_journalArticle.getDDMStructureId(),
				RandomTestUtil.randomString(),
				LayoutPageTemplateEntryTypeConstants.DISPLAY_PAGE, 0, true, 0,
				0, 0, 0, _serviceContext);

		AssetDisplayPageEntry assetDisplayPageEntry =
			_assetDisplayPageEntryLocalService.addAssetDisplayPageEntry(
				TestPropsValues.getUserId(), _group.getGroupId(), _classNameId,
				_journalArticle.getResourcePrimKey(),
				_layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
				AssetDisplayPageConstants.TYPE_SPECIFIC, _serviceContext);

		_layout = _layoutLocalService.getLayout(
			assetDisplayPageEntry.getPlid());

		_setUpThemeDisplay();
	}

	@Test
	public void testGetInfoFieldValues() throws Exception {
		InfoItemReference infoItemReference = new InfoItemReference(
			JournalArticle.class.getName(),
			_journalArticle.getResourcePrimKey());

		List<InfoFieldValue<Object>> infoFieldValues =
			_displayPageInfoItemFieldSetProvider.getInfoFieldValues(
				infoItemReference,
				String.valueOf(_journalArticle.getDDMStructureId()),
				JournalArticle.class.getSimpleName(), _journalArticle,
				_themeDisplay);

		Assert.assertEquals(
			infoFieldValues.toString(), 2, infoFieldValues.size());

		_assertInfoFieldValue(
			infoFieldValues.get(0), "displayPageURL",
			object -> Assert.assertEquals(
				_assetDisplayPageFriendlyURLProvider.getFriendlyURL(
					infoItemReference, _journalArticle, _themeDisplay),
				object));

		_assertInfoFieldValue(
			infoFieldValues.get(1), _layoutPageTemplateEntry.getName(),
			object -> {
				Assert.assertTrue(object instanceof WebURL);

				WebURL layoutPageTemplateEntryWebURL = (WebURL)object;

				Assert.assertEquals(
					StringBundler.concat(
						_portal.getGroupFriendlyURL(
							_group.getPublicLayoutSet(), _themeDisplay, false,
							false),
						FriendlyURLResolverConstants.
							URL_SEPARATOR_X_CUSTOM_ASSET,
						_layout.getFriendlyURL(LocaleUtil.getSiteDefault()),
						StringPool.SLASH, _classNameId, StringPool.SLASH,
						_journalArticle.getResourcePrimKey()),
					layoutPageTemplateEntryWebURL.getURL());
			});
	}

	private void _assertInfoFieldValue(
			InfoFieldValue<Object> infoFieldValue, String name,
			UnsafeConsumer<Object, Exception> unsafeConsumer)
		throws Exception {

		InfoField infoField = infoFieldValue.getInfoField();

		Assert.assertEquals(name, infoField.getName());

		unsafeConsumer.accept(
			infoFieldValue.getValue(LocaleUtil.getSiteDefault()));
	}

	private void _setUpThemeDisplay() throws Exception {
		_themeDisplay = ContentLayoutTestUtil.getThemeDisplay(
			_company, _group, _layout);

		_themeDisplay.setPortalURL("http://localhost:8080");

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			LayoutDisplayPageWebKeys.LAYOUT_DISPLAY_PAGE_OBJECT_PROVIDER,
			_layoutDisplayPageProvider.getLayoutDisplayPageObjectProvider(
				_group.getGroupId(),
				String.valueOf(
					_journalArticle.getUrlTitle(LocaleUtil.getSiteDefault()))));
		mockHttpServletRequest.setAttribute(WebKeys.LAYOUT, _layout);
		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _themeDisplay);

		_themeDisplay.setRequest(mockHttpServletRequest);

		_themeDisplay.setServerName("localhost");
		_themeDisplay.setServerPort(8080);
	}

	@Inject
	private AssetDisplayPageEntryLocalService
		_assetDisplayPageEntryLocalService;

	@Inject
	private AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider;

	private long _classNameId;
	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private DisplayPageInfoItemFieldSetProvider
		_displayPageInfoItemFieldSetProvider;

	@DeleteAfterTestRun
	private Group _group;

	private JournalArticle _journalArticle;
	private Layout _layout;

	@Inject(
		filter = "component.name=com.liferay.journal.web.internal.layout.display.page.JournalArticleLayoutDisplayPageProvider"
	)
	private LayoutDisplayPageProvider<JournalArticle>
		_layoutDisplayPageProvider;

	@Inject
	private LayoutLocalService _layoutLocalService;

	private LayoutPageTemplateEntry _layoutPageTemplateEntry;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject
	private Portal _portal;

	private ServiceContext _serviceContext;
	private ThemeDisplay _themeDisplay;

}