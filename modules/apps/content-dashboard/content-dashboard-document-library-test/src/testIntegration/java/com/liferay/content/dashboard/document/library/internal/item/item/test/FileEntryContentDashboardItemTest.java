/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.dashboard.document.library.internal.item.item.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.display.page.constants.AssetDisplayPageConstants;
import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalService;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.content.dashboard.item.ContentDashboardItem;
import com.liferay.content.dashboard.item.ContentDashboardItemFactory;
import com.liferay.content.dashboard.item.ContentDashboardItemVersion;
import com.liferay.content.dashboard.item.VersionableContentDashboardItem;
import com.liferay.content.dashboard.item.action.ContentDashboardItemAction;
import com.liferay.content.dashboard.item.type.ContentDashboardItemSubtype;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderRequest;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.net.URL;
import java.net.URLEncoder;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Mikel Lorza
 */
@RunWith(Arquillian.class)
public class FileEntryContentDashboardItemTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(), 0);
		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(TestPropsValues.getUser()));
	}

	@Test
	public void testGetAllContentDashboardItemVersionsOneVersion()
		throws Exception {

		VersionableContentDashboardItem<FileEntry>
			versionableContentDashboardItem =
				_getVersionableContentDashboardItem(
					1,
					ServiceContextTestUtil.getServiceContext(
						_group.getGroupId()));

		List<ContentDashboardItemVersion> contentDashboardItemVersionList =
			versionableContentDashboardItem.getAllContentDashboardItemVersions(
				_getMockHttpServletRequest());

		Assert.assertEquals(
			contentDashboardItemVersionList.toString(), 1,
			contentDashboardItemVersionList.size());

		ContentDashboardItemVersion contentDashboardItemVersion =
			contentDashboardItemVersionList.get(0);

		Assert.assertEquals("Approved", contentDashboardItemVersion.getLabel());
		Assert.assertEquals("1.0", contentDashboardItemVersion.getVersion());
		Assert.assertEquals("success", contentDashboardItemVersion.getStyle());
	}

	@Test
	public void testGetAllContentDashboardItemVersionsTwoVersions()
		throws Exception {

		VersionableContentDashboardItem<FileEntry>
			versionableContentDashboardItem =
				_getVersionableContentDashboardItem(
					2,
					ServiceContextTestUtil.getServiceContext(
						_group.getGroupId()));

		List<ContentDashboardItemVersion> contentDashboardItemVersionList =
			versionableContentDashboardItem.getAllContentDashboardItemVersions(
				_getMockHttpServletRequest());

		Assert.assertEquals(
			contentDashboardItemVersionList.toString(), 2,
			contentDashboardItemVersionList.size());

		ContentDashboardItemVersion contentDashboardItemVersion =
			contentDashboardItemVersionList.get(0);

		Assert.assertEquals("Approved", contentDashboardItemVersion.getLabel());
		Assert.assertEquals("1.1", contentDashboardItemVersion.getVersion());
		Assert.assertEquals("success", contentDashboardItemVersion.getStyle());
	}

	@Test
	public void testGetAssetCategories() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		AssetVocabulary assetVocabulary =
			_assetVocabularyLocalService.addVocabulary(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(), serviceContext);

		AssetCategory assetCategory1 = _assetCategoryLocalService.addCategory(
			TestPropsValues.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(), assetVocabulary.getVocabularyId(),
			serviceContext);

		serviceContext.setAssetCategoryIds(
			new long[] {assetCategory1.getCategoryId()});

		VersionableContentDashboardItem<FileEntry>
			versionableContentDashboardItem =
				_getVersionableContentDashboardItem(2, serviceContext);

		List<AssetCategory> assetCategoryList =
			versionableContentDashboardItem.getAssetCategories();

		Assert.assertEquals(
			assetCategoryList.toString(), 1, assetCategoryList.size());

		AssetCategory assetCategory2 = assetCategoryList.get(0);

		Assert.assertEquals(
			assetCategory1.getTitle(), assetCategory2.getTitle());
	}

	@Test
	public void testGetAssetCategoriesByVocabulary() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		AssetVocabulary assetVocabulary1 =
			_assetVocabularyLocalService.addVocabulary(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(), serviceContext);

		AssetCategory assetCategory1 = _assetCategoryLocalService.addCategory(
			TestPropsValues.getUserId(), _group.getGroupId(), "assetCategory1",
			assetVocabulary1.getVocabularyId(), serviceContext);

		AssetVocabulary assetVocabulary2 =
			_assetVocabularyLocalService.addVocabulary(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(), serviceContext);

		AssetCategory assetCategory2 = _assetCategoryLocalService.addCategory(
			TestPropsValues.getUserId(), _group.getGroupId(), "assetCategory2",
			assetVocabulary2.getVocabularyId(), serviceContext);

		serviceContext.setAssetCategoryIds(
			new long[] {
				assetCategory1.getCategoryId(), assetCategory2.getCategoryId()
			});

		VersionableContentDashboardItem<FileEntry>
			versionableContentDashboardItem =
				_getVersionableContentDashboardItem(2, serviceContext);

		List<AssetCategory> assetCategories =
			versionableContentDashboardItem.getAssetCategories(
				assetVocabulary2.getVocabularyId());

		Assert.assertEquals(
			assetCategories.toString(), 1, assetCategories.size());

		AssetCategory assetCategory3 = assetCategories.get(0);

		Assert.assertEquals(
			"assetCategory2", assetCategory3.getTitle(LocaleUtil.getDefault()));
	}

	@Test
	public void testGetAssetTags() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAssetTagNames(new String[] {"tag1", "tag2", "tag3"});

		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".jpg",
			MimeTypesUtil.getExtensionContentType(ContentTypes.IMAGE_JPEG),
			new byte[0], null, null, serviceContext);

		VersionableContentDashboardItem<FileEntry>
			versionableContentDashboardItem =
				(VersionableContentDashboardItem<FileEntry>)
					_contentDashboardItemFactory.create(
						fileEntry.getFileEntryId());

		List<AssetTag> assetTagList =
			versionableContentDashboardItem.getAssetTags();

		Assert.assertEquals(assetTagList.toString(), 3, assetTagList.size());

		AssetTag assetTag = assetTagList.get(0);

		Assert.assertEquals("tag1", assetTag.getName());
	}

	@Test
	public void testGetContentDashboardItemActions() throws Exception {
		VersionableContentDashboardItem<FileEntry>
			versionableContentDashboardItem =
				_getVersionableContentDashboardItem(
					1,
					ServiceContextTestUtil.getServiceContext(
						_group.getGroupId()));

		List<ContentDashboardItemAction> contentDashboardItemActions =
			versionableContentDashboardItem.getContentDashboardItemActions(
				_getMockHttpServletRequest(),
				ContentDashboardItemAction.Type.VIEW);

		Assert.assertEquals(
			contentDashboardItemActions.toString(), 1,
			contentDashboardItemActions.size());
	}

	@Test
	public void testGetContentDashboardItemSubtype() throws Exception {
		VersionableContentDashboardItem<FileEntry>
			versionableContentDashboardItem =
				_getVersionableContentDashboardItem(
					1,
					ServiceContextTestUtil.getServiceContext(
						_group.getGroupId()));

		ContentDashboardItemSubtype contentDashboardItemSubtype =
			versionableContentDashboardItem.getContentDashboardItemSubtype();

		Assert.assertNotNull(contentDashboardItemSubtype);

		Assert.assertEquals(
			"Basic Document (Image)",
			contentDashboardItemSubtype.getLabel(LocaleUtil.US));
	}

	@Test
	public void testGetCreateDate() throws Exception {
		VersionableContentDashboardItem<FileEntry>
			versionableContentDashboardItem =
				_getVersionableContentDashboardItem(
					1,
					ServiceContextTestUtil.getServiceContext(
						_group.getGroupId()));

		Assert.assertNotNull(versionableContentDashboardItem.getCreateDate());
	}

	@Test
	public void testGetDefaultContentDashboardItemAction() throws Exception {
		VersionableContentDashboardItem<FileEntry>
			versionableContentDashboardItem =
				_getVersionableContentDashboardItem(
					1,
					ServiceContextTestUtil.getServiceContext(
						_group.getGroupId()));

		ContentDashboardItemAction contentDashboardItemAction =
			versionableContentDashboardItem.
				getDefaultContentDashboardItemAction(
					_getMockHttpServletRequest());

		Assert.assertEquals(
			ContentDashboardItemAction.Type.VIEW,
			contentDashboardItemAction.getType());
	}

	@Test
	public void testGetDefaultLocale() throws Exception {
		VersionableContentDashboardItem<FileEntry>
			versionableContentDashboardItem =
				_getVersionableContentDashboardItem(
					1,
					ServiceContextTestUtil.getServiceContext(
						_group.getGroupId()));

		Assert.assertEquals(
			LocaleUtil.getDefault(),
			versionableContentDashboardItem.getDefaultLocale());
	}

	@Test
	public void testGetDescription() throws Exception {
		VersionableContentDashboardItem<FileEntry>
			versionableContentDashboardItem =
				_getVersionableContentDashboardItem(
					1,
					ServiceContextTestUtil.getServiceContext(
						_group.getGroupId()));

		Assert.assertEquals(
			"description",
			versionableContentDashboardItem.getDescription(
				LocaleUtil.getDefault()));
	}

	@Test
	public void testGetInfoItemReference() throws Exception {
		VersionableContentDashboardItem<FileEntry>
			versionableContentDashboardItem =
				_getVersionableContentDashboardItem(
					1,
					ServiceContextTestUtil.getServiceContext(
						_group.getGroupId()));

		Assert.assertNotNull(
			versionableContentDashboardItem.getInfoItemReference());
	}

	@Test
	public void testGetLatestContentDashboardItemVersions() throws Exception {
		VersionableContentDashboardItem<FileEntry>
			versionableContentDashboardItem =
				_getVersionableContentDashboardItem(
					2,
					ServiceContextTestUtil.getServiceContext(
						_group.getGroupId()));

		List<ContentDashboardItemVersion> contentDashboardItemVersionList =
			versionableContentDashboardItem.
				getLatestContentDashboardItemVersions(LocaleUtil.getDefault());

		Assert.assertEquals(
			contentDashboardItemVersionList.toString(), 1,
			contentDashboardItemVersionList.size());

		ContentDashboardItemVersion contentDashboardItemVersion =
			contentDashboardItemVersionList.get(0);

		Assert.assertEquals("Approved", contentDashboardItemVersion.getLabel());
		Assert.assertEquals("1.1", contentDashboardItemVersion.getVersion());
		Assert.assertEquals("success", contentDashboardItemVersion.getStyle());
	}

	@Test
	public void testGetModifiedDate() throws Exception {
		VersionableContentDashboardItem<FileEntry>
			versionableContentDashboardItem =
				_getVersionableContentDashboardItem(
					1,
					ServiceContextTestUtil.getServiceContext(
						_group.getGroupId()));

		Assert.assertNotNull(versionableContentDashboardItem.getModifiedDate());
	}

	@Test
	public void testGetScopeName() throws Exception {
		VersionableContentDashboardItem<FileEntry>
			versionableContentDashboardItem =
				_getVersionableContentDashboardItem(
					1,
					ServiceContextTestUtil.getServiceContext(
						_group.getGroupId()));

		Assert.assertNotNull(
			versionableContentDashboardItem.getScopeName(
				LocaleUtil.getDefault()));
	}

	@Test
	public void testGetSpecificInformationList() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			new MockLiferayPortletRenderRequest();

		mockLiferayPortletRenderRequest.setAttribute(
			WebKeys.THEME_DISPLAY, new ThemeDisplay());

		mockHttpServletRequest.setAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST,
			mockLiferayPortletRenderRequest);

		serviceContext.setRequest(mockHttpServletRequest);

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		VersionableContentDashboardItem<FileEntry>
			versionableContentDashboardItem =
				_getVersionableContentDashboardItem(
					1,
					ServiceContextTestUtil.getServiceContext(
						_group.getGroupId()));

		List<ContentDashboardItem.SpecificInformation<?>>
			specificInformationList =
				versionableContentDashboardItem.getSpecificInformationList(
					LocaleUtil.getDefault());

		ContentDashboardItem.SpecificInformation<?>
			extensionSpecificInformation = null;

		for (ContentDashboardItem.SpecificInformation<?> specificInformation :
				specificInformationList) {

			if (Objects.equals(specificInformation.getKey(), "extension")) {
				extensionSpecificInformation = specificInformation;

				break;
			}
		}

		Assert.assertNotNull(
			"extension not found", extensionSpecificInformation);

		Assert.assertEquals("jpg", extensionSpecificInformation.getValue());

		ContentDashboardItem.SpecificInformation<?> sizeSpecificInformation =
			null;

		for (ContentDashboardItem.SpecificInformation<?> specificInformation :
				specificInformationList) {

			if (Objects.equals(specificInformation.getKey(), "size")) {
				sizeSpecificInformation = specificInformation;

				break;
			}
		}

		Assert.assertNotNull("size not found", sizeSpecificInformation);
		Assert.assertEquals("0 B", sizeSpecificInformation.getValue());

		Assert.assertTrue(
			ListUtil.exists(
				specificInformationList,
				specificInformation -> Objects.equals(
					specificInformation.getKey(), "file-name")));

		ContentDashboardItem.SpecificInformation<URL>
			webDAVSpecificInformation = null;

		for (ContentDashboardItem.SpecificInformation<?> specificInformation :
				specificInformationList) {

			if (Objects.equals(specificInformation.getKey(), "web-dav-url")) {
				webDAVSpecificInformation =
					(ContentDashboardItem.SpecificInformation<URL>)
						specificInformation;

				break;
			}
		}

		Assert.assertNotNull(
			"web-dav-url not found", webDAVSpecificInformation);

		String url = String.valueOf(webDAVSpecificInformation.getValue());

		Assert.assertTrue(url.contains("webdav"));

		Assert.assertEquals(
			"webdav-help", webDAVSpecificInformation.getHelpText());
	}

	@Test
	public void testGetTitle() throws Exception {
		VersionableContentDashboardItem<FileEntry>
			versionableContentDashboardItem =
				_getVersionableContentDashboardItem(
					1,
					ServiceContextTestUtil.getServiceContext(
						_group.getGroupId()));

		Assert.assertEquals(
			"example.jpg",
			versionableContentDashboardItem.getTitle(LocaleUtil.getDefault()));
	}

	@Test
	public void testGetTypeLabel() throws Exception {
		VersionableContentDashboardItem<FileEntry>
			versionableContentDashboardItem =
				_getVersionableContentDashboardItem(
					1,
					ServiceContextTestUtil.getServiceContext(
						_group.getGroupId()));

		Assert.assertEquals(
			"Document",
			versionableContentDashboardItem.getTypeLabel(
				LocaleUtil.getDefault()));
	}

	@Test
	public void testGetUserId() throws Exception {
		VersionableContentDashboardItem<FileEntry>
			versionableContentDashboardItem =
				_getVersionableContentDashboardItem(
					1,
					ServiceContextTestUtil.getServiceContext(
						_group.getGroupId()));

		Assert.assertEquals(
			TestPropsValues.getUserId(),
			versionableContentDashboardItem.getUserId());
	}

	@Test
	public void testGetUserName() throws Exception {
		VersionableContentDashboardItem<FileEntry>
			versionableContentDashboardItem =
				_getVersionableContentDashboardItem(
					1,
					ServiceContextTestUtil.getServiceContext(
						_group.getGroupId()));

		Assert.assertEquals(
			"Test Test", versionableContentDashboardItem.getUserName());
	}

	@Test
	public void testGetViewVersionsURL() throws Exception {
		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"example.jpg",
			MimeTypesUtil.getExtensionContentType(ContentTypes.IMAGE_JPEG),
			new byte[0], null, null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		VersionableContentDashboardItem<FileEntry>
			versionableContentDashboardItem =
				(VersionableContentDashboardItem<FileEntry>)
					_contentDashboardItemFactory.create(
						fileEntry.getFileEntryId());

		String viewVersionsURL =
			versionableContentDashboardItem.getViewVersionsURL(
				_getMockHttpServletRequest());

		Assert.assertNotNull(viewVersionsURL);

		String mvcRenderCommandNameEncoded = URLEncoder.encode(
			"/document_library/view_file_entry_history", "UTF-8");

		Assert.assertTrue(
			viewVersionsURL.contains(
				"mvcRenderCommandName=" + mvcRenderCommandNameEncoded));

		Assert.assertTrue(
			viewVersionsURL.contains(
				"fileEntryId=" + fileEntry.getFileEntryId()));
	}

	@Test
	public void testIsShowContentDashboardItemVersions() throws Exception {
		VersionableContentDashboardItem<FileEntry>
			versionableContentDashboardItem =
				_getVersionableContentDashboardItem(
					1,
					ServiceContextTestUtil.getServiceContext(
						_group.getGroupId()));

		Assert.assertTrue(
			versionableContentDashboardItem.isShowContentDashboardItemVersions(
				_getMockHttpServletRequest()));
	}

	@Test
	public void testIsViewableWithLayoutPageTemplateEntry() throws Exception {
		VersionableContentDashboardItem<FileEntry>
			versionableContentDashboardItem =
				_getVersionableContentDashboardItem(
					1,
					ServiceContextTestUtil.getServiceContext(
						_group.getGroupId()));

		Assert.assertTrue(
			versionableContentDashboardItem.isViewable(
				_getMockHttpServletRequest()));
	}

	@Test
	public void testIsViewableWithoutLayoutPageTemplateEntry()
		throws Exception {

		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"example.jpg",
			MimeTypesUtil.getExtensionContentType(ContentTypes.IMAGE_JPEG),
			new byte[0], null, null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		VersionableContentDashboardItem<FileEntry>
			versionableContentDashboardItem =
				(VersionableContentDashboardItem<FileEntry>)
					_contentDashboardItemFactory.create(
						fileEntry.getFileEntryId());

		Assert.assertFalse(
			versionableContentDashboardItem.isViewable(
				_getMockHttpServletRequest()));
	}

	private MockHttpServletRequest _getMockHttpServletRequest()
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE,
			new MockLiferayPortletActionResponse());
		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay(mockHttpServletRequest));

		return mockHttpServletRequest;
	}

	private ThemeDisplay _getThemeDisplay(HttpServletRequest httpServletRequest)
		throws Exception {

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			CompanyLocalServiceUtil.fetchCompany(_group.getCompanyId()));
		themeDisplay.setLocale(LocaleUtil.getDefault());
		themeDisplay.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(TestPropsValues.getUser()));
		themeDisplay.setRequest(httpServletRequest);
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	private VersionableContentDashboardItem<FileEntry>
			_getVersionableContentDashboardItem(
				int numVersions, ServiceContext serviceContext)
		throws Exception {

		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"example.jpg",
			MimeTypesUtil.getExtensionContentType(ContentTypes.IMAGE_JPEG),
			new byte[0], null, null, serviceContext);

		fileEntry = _dlAppLocalService.updateFileEntry(
			fileEntry.getUserId(), fileEntry.getFileEntryId(),
			fileEntry.getFileName(), fileEntry.getMimeType(),
			fileEntry.getTitle(), StringUtil.randomString(), "description",
			RandomTestUtil.randomString(), DLVersionNumberIncrease.NONE,
			fileEntry.getContentStream(), fileEntry.getSize(),
			fileEntry.getExpirationDate(), fileEntry.getReviewDate(),
			serviceContext);

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				_group.getCreatorUserId(), _group.getGroupId(), 0,
				_portal.getClassNameId(FileEntry.class.getName()), 0,
				RandomTestUtil.randomString(),
				LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE, 0, true,
				0, 0, 0, 0, serviceContext);

		_assetDisplayPageEntryLocalService.addAssetDisplayPageEntry(
			fileEntry.getUserId(), _group.getGroupId(),
			_portal.getClassNameId(FileEntry.class.getName()),
			fileEntry.getFileEntryId(),
			layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
			AssetDisplayPageConstants.TYPE_DEFAULT, serviceContext);

		if (numVersions > 1) {
			for (int i = 1; i < numVersions; i++) {
				fileEntry = _dlAppLocalService.updateFileEntry(
					fileEntry.getUserId(), fileEntry.getFileEntryId(),
					fileEntry.getFileName(), fileEntry.getMimeType(),
					fileEntry.getTitle(), StringUtil.randomString(),
					fileEntry.getDescription(), RandomTestUtil.randomString(),
					DLVersionNumberIncrease.MINOR, fileEntry.getContentStream(),
					fileEntry.getSize(), fileEntry.getExpirationDate(),
					fileEntry.getReviewDate(), serviceContext);
			}
		}

		return (VersionableContentDashboardItem<FileEntry>)
			_contentDashboardItemFactory.create(fileEntry.getFileEntryId());
	}

	@Inject
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Inject
	private AssetDisplayPageEntryLocalService
		_assetDisplayPageEntryLocalService;

	@Inject
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Inject(
		filter = "component.name=com.liferay.content.dashboard.document.library.internal.item.FileEntryContentDashboardItemFactory"
	)
	private ContentDashboardItemFactory _contentDashboardItemFactory;

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@Inject
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject
	private Portal _portal;

}