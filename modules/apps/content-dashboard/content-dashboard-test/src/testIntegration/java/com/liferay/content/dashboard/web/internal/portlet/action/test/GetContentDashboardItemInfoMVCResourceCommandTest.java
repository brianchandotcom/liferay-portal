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

package com.liferay.content.dashboard.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.entry.rel.service.AssetEntryAssetCategoryRelLocalService;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetCategoryConstants;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.model.AssetVocabularyConstants;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.asset.test.util.AssetTestUtil;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.content.dashboard.item.ContentDashboardItem;
import com.liferay.content.dashboard.item.ContentDashboardItemFactory;
import com.liferay.content.dashboard.item.ContentDashboardItemVersion;
import com.liferay.content.dashboard.item.type.ContentDashboardItemSubtype;
import com.liferay.content.dashboard.web.test.util.ContentDashboardTestUtil;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.info.item.InfoItemReference;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.servlet.PortletServlet;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletURL;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.ByteArrayOutputStream;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.portlet.MutablePortletParameters;
import javax.portlet.MutableResourceParameters;
import javax.portlet.PortletParameters;
import javax.portlet.ResourceURL;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Yurena Cabrera
 */
@RunWith(Arquillian.class)
public class GetContentDashboardItemInfoMVCResourceCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_company = _companyLocalService.getCompany(_group.getCompanyId());

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId());
	}

	@Test
	public void testGetFileSpecificFields() throws Exception {
		ContentDashboardItem contentDashboardItem =
			_createContentDashboardFileItem();

		JSONObject jsonObject = ReflectionTestUtil.invoke(
			_mvcResourceCommand, "_getSpecificFieldsJSONObject",
			new Class<?>[] {ContentDashboardItem.class, Locale.class},
			contentDashboardItem, LocaleUtil.SPAIN);

		Assert.assertNotNull(jsonObject);
		Assert.assertNotNull(jsonObject.getString("extension"));
		Assert.assertNotNull(jsonObject.getString("size"));
		Assert.assertNotNull(jsonObject.getString("file-name"));
	}

	@Test
	public void testGetJournalArticleSpecificFields() throws Exception {
		ContentDashboardItem contentDashboardItem =
			_createContentDashboardJournalArticleItem();

		JSONObject jsonObject = ReflectionTestUtil.invoke(
			_mvcResourceCommand, "_getSpecificFieldsJSONObject",
			new Class<?>[] {ContentDashboardItem.class, Locale.class},
			contentDashboardItem, LocaleUtil.SPAIN);

		Assert.assertNotNull(jsonObject);
		Assert.assertNotNull(jsonObject.getString("review-date"));
		Assert.assertNotNull(jsonObject.getString("display-date"));
		Assert.assertNotNull(jsonObject.getString("expiration-date"));
	}

	@Test
	public void testServeResource() throws Exception {
		User user = TestPropsValues.getUser();

		user.setPortraitId(12345L);

		_initCategoryAndVocabulary();

		ContentDashboardItem contentDashboardItem =
			_createContentDashboardFileItem();

		InfoItemReference infoItemReference =
			contentDashboardItem.getInfoItemReference();

		MockLiferayResourceRequest mockLiferayResourceRequest =
			_getMockLiferayPortletResourceRequest(infoItemReference);

		TestMockLiferayResourceResponse mockLiferayResourceResponse =
			new TestMockLiferayResourceResponse();

		_mvcResourceCommand.serveResource(
			mockLiferayResourceRequest, mockLiferayResourceResponse);

		ByteArrayOutputStream byteArrayOutputStream =
			(ByteArrayOutputStream)
				mockLiferayResourceResponse.getPortletOutputStream();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			byteArrayOutputStream.toString());

		JSONObject vocabulariesJSONObject = jsonObject.getJSONObject(
			"vocabularies");

		List<AssetCategory> assetCategories =
			contentDashboardItem.getAssetCategories();

		for (AssetCategory assetCategory : assetCategories) {
			JSONObject vocabularyDataJSONObject =
				vocabulariesJSONObject.getJSONObject(
					String.valueOf(assetCategory.getVocabularyId()));

			JSONArray categoriesJSONArray =
				vocabularyDataJSONObject.getJSONArray("categories");

			Assert.assertEquals(1, categoriesJSONArray.length());

			Assert.assertEquals(
				assetCategory.getTitle(LocaleUtil.getSiteDefault()),
				categoriesJSONArray.getString(0));
		}

		Assert.assertEquals(
			infoItemReference.getClassName(),
			jsonObject.getString("className"));
		Assert.assertEquals(
			infoItemReference.getClassPK(), jsonObject.getLong("classPK"), 0);

		Assert.assertEquals(
			contentDashboardItem.getDescription(LocaleUtil.US),
			jsonObject.getString("description"));
		Assert.assertNotNull(jsonObject.getString("fetchSharingButtonURL"));
		Assert.assertNotNull(
			jsonObject.getString("fetchSharingCollaboratorsURL"));

		JSONArray tagsJSONArray = jsonObject.getJSONArray("tags");

		Assert.assertEquals(
			JSONUtil.putAll(
				ListUtil.toArray(
					contentDashboardItem.getAssetTags(), AssetTag.NAME_ACCESSOR)
			).toString(),
			tagsJSONArray.toString());

		Assert.assertEquals(
			contentDashboardItem.getTitle(LocaleUtil.US),
			jsonObject.getString("title"));

		ContentDashboardItemSubtype contentDashboardItemSubtype =
			contentDashboardItem.getContentDashboardItemSubtype();

		Assert.assertEquals(
			contentDashboardItemSubtype.getLabel(LocaleUtil.US),
			jsonObject.getString("subType"));

		List<ContentDashboardItem.SpecificInformation<?>>
			specificInformationList =
				contentDashboardItem.getSpecificInformationList(LocaleUtil.US);

		Assert.assertEquals(
			String.valueOf(specificInformationList), 5,
			specificInformationList.size());

		JSONObject specificFieldsJSONObject = jsonObject.getJSONObject(
			"specificFields");

		for (ContentDashboardItem.SpecificInformation<?> specificInformation :
				specificInformationList) {

			JSONObject specificFieldJSONObject =
				specificFieldsJSONObject.getJSONObject(
					specificInformation.getKey());

			JSONObject specificInformationJSONObject =
				specificInformation.toJSONObject(
					LanguageUtil.getLanguage(), LocaleUtil.US);

			Assert.assertEquals(
				specificFieldJSONObject.getString("title"),
				specificInformationJSONObject.getString("title"));

			Assert.assertEquals(
				specificFieldJSONObject.getString("value"),
				specificInformationJSONObject.getString("value"));
		}

		JSONObject userJSONObject = jsonObject.getJSONObject("user");

		Assert.assertEquals(
			contentDashboardItem.getUserName(),
			userJSONObject.getString("name"));
		Assert.assertEquals(
			contentDashboardItem.getUserId(), userJSONObject.getLong("userId"));

		_assertContentDashboardItemLatestVersions(
			contentDashboardItem, jsonObject);
	}

	@Test
	public void testServeResourceWithoutSharingButtonAction() throws Exception {
		_initCategoryAndVocabulary();

		ContentDashboardItem contentDashboardItem =
			_createContentDashboardFileItem();

		MockLiferayResourceRequest mockLiferayResourceRequest =
			_getMockLiferayPortletResourceRequest(
				contentDashboardItem.getInfoItemReference());

		TestMockLiferayResourceResponse mockLiferayResourceResponse =
			new TestMockLiferayResourceResponse();

		_mvcResourceCommand.serveResource(
			mockLiferayResourceRequest, mockLiferayResourceResponse);

		ByteArrayOutputStream byteArrayOutputStream =
			(ByteArrayOutputStream)
				mockLiferayResourceResponse.getPortletOutputStream();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			byteArrayOutputStream.toString());

		Assert.assertEquals(
			StringPool.BLANK, jsonObject.getString("fetchSharingButtonURL"));
	}

	@Test
	public void testServeResourceWithoutSharingCollaboratorsAction()
		throws Exception {

		_initCategoryAndVocabulary();

		ContentDashboardItem contentDashboardItem =
			_createContentDashboardFileItem();

		MockLiferayResourceRequest mockLiferayResourceRequest =
			_getMockLiferayPortletResourceRequest(
				contentDashboardItem.getInfoItemReference());

		TestMockLiferayResourceResponse mockLiferayResourceResponse =
			new TestMockLiferayResourceResponse();

		_mvcResourceCommand.serveResource(
			mockLiferayResourceRequest, mockLiferayResourceResponse);

		ByteArrayOutputStream byteArrayOutputStream =
			(ByteArrayOutputStream)
				mockLiferayResourceResponse.getPortletOutputStream();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			byteArrayOutputStream.toString());

		Assert.assertEquals(
			StringPool.BLANK,
			jsonObject.getString("fetchSharingCollaboratorsURL"));
	}

	@Test
	public void testServeResourceWithoutSubtype() throws Exception {
		User user = TestPropsValues.getUser();

		user.setPortraitId(12345L);

		_initCategoryAndVocabulary();

		ContentDashboardItem contentDashboardItem =
			_createContentDashboardBlogItem(user);

		MockLiferayResourceRequest mockLiferayResourceRequest =
			_getMockLiferayPortletResourceRequest(
				contentDashboardItem.getInfoItemReference());

		TestMockLiferayResourceResponse mockLiferayResourceResponse =
			new TestMockLiferayResourceResponse();

		_mvcResourceCommand.serveResource(
			mockLiferayResourceRequest, mockLiferayResourceResponse);

		ByteArrayOutputStream byteArrayOutputStream =
			(ByteArrayOutputStream)
				mockLiferayResourceResponse.getPortletOutputStream();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			byteArrayOutputStream.toString());

		Assert.assertEquals(StringPool.BLANK, jsonObject.getString("subType"));
	}

	@Test
	public void testServeResourceWithoutUser() throws Exception {
		_initCategoryAndVocabulary();

		ContentDashboardItem contentDashboardItem =
			_createContentDashboardFileItem();

		MockLiferayResourceRequest mockLiferayResourceRequest =
			_getMockLiferayPortletResourceRequest(
				contentDashboardItem.getInfoItemReference());

		TestMockLiferayResourceResponse mockLiferayResourceResponse =
			new TestMockLiferayResourceResponse();

		_mvcResourceCommand.serveResource(
			mockLiferayResourceRequest, mockLiferayResourceResponse);

		ByteArrayOutputStream byteArrayOutputStream =
			(ByteArrayOutputStream)
				mockLiferayResourceResponse.getPortletOutputStream();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			byteArrayOutputStream.toString());

		JSONObject userJSONObject = jsonObject.getJSONObject("user");

		Assert.assertEquals(
			contentDashboardItem.getUserName(),
			userJSONObject.getString("name"));
		Assert.assertEquals(
			contentDashboardItem.getUserId(), userJSONObject.getLong("userId"));
		Assert.assertEquals(StringPool.BLANK, userJSONObject.getString("url"));
	}

	private AssetCategory _addAssetCategory(AssetVocabulary assetVocabulary)
		throws Exception {

		return _assetCategoryLocalService.addCategory(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			HashMapBuilder.put(
				LocaleUtil.US, RandomTestUtil.randomString()
			).build(),
			null, assetVocabulary.getVocabularyId(), null, _serviceContext);
	}

	private AssetVocabulary _addAssetVocabulary(int visibilityTypePublic)
		throws Exception {

		return _assetVocabularyLocalService.addVocabulary(
			TestPropsValues.getUserId(), _group.getGroupId(), null,
			HashMapBuilder.put(
				LocaleUtil.US, RandomTestUtil.randomString()
			).build(),
			null, null, visibilityTypePublic, new ServiceContext());
	}

	private void _assertContentDashboardItemLatestVersions(
		ContentDashboardItem<?> contentDashboardItem, JSONObject jsonObject) {

		List<ContentDashboardItemVersion> contentDashboardItemVersions =
			contentDashboardItem.getLatestContentDashboardItemVersions(
				LocaleUtil.US);

		ContentDashboardItemVersion contentDashboardItemVersion =
			contentDashboardItemVersions.get(0);

		JSONObject expectedJSONObject =
			contentDashboardItemVersion.toJSONObject();

		JSONArray actualJSONArray = jsonObject.getJSONArray("latestVersions");

		JSONObject actualJSONObject = actualJSONArray.getJSONObject(0);

		Assert.assertEquals(
			expectedJSONObject.toString(), actualJSONObject.toString());
	}

	private ContentDashboardItem _createContentDashboardBlogItem(User user)
		throws Exception {

		Calendar displayDateCalendar = CalendarFactoryUtil.getCalendar(
			2022, 1, 20);

		BlogsEntry blogsEntry = BlogsEntryLocalServiceUtil.addEntry(
			user.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), displayDateCalendar.getTime(),
			_serviceContext);

		return _contentDashboardBlogsItemFactory.create(
			blogsEntry.getPrimaryKey());
	}

	private ContentDashboardItem _createContentDashboardFileItem()
		throws Exception {

		FileEntry fileEntry = DLAppLocalServiceUtil.addFileEntry(
			"Site", TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "fileName.pdf",
			"application/pdf", new byte[0], new Date(150000), new Date(150000),
			_serviceContext);

		return _contentDashboardFileItemFactory.create(
			fileEntry.getPrimaryKey());
	}

	private ContentDashboardItem _createContentDashboardJournalArticleItem()
		throws Exception {

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		return _contentDashboardJournalItemFactory.create(
			journalArticle.getResourcePrimKey());
	}

	private MockLiferayResourceRequest _getMockLiferayPortletResourceRequest(
			InfoItemReference infoItemReference)
		throws Exception {

		MockLiferayResourceRequest mockLiferayResourceRequest =
			new MockLiferayResourceRequest();

		mockLiferayResourceRequest.setAttribute(WebKeys.LOCALE, LocaleUtil.US);

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		ThemeDisplay themeDisplay = ContentDashboardTestUtil.getThemeDisplay(
			_group);

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		mockLiferayResourceRequest.setAttribute(
			PortletServlet.PORTLET_SERVLET_REQUEST, mockHttpServletRequest);

		mockLiferayResourceRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);
		mockLiferayResourceRequest.setParameter(
			"className", infoItemReference.getClassName());
		mockLiferayResourceRequest.setParameter(
			"classPK", String.valueOf(infoItemReference.getClassPK()));

		return mockLiferayResourceRequest;
	}

	private void _initCategoryAndVocabulary() throws Exception {
		AssetVocabulary assetVocabulary = _addAssetVocabulary(
			AssetVocabularyConstants.VISIBILITY_TYPE_PUBLIC);

		AssetCategory assetCategory = _addAssetCategory(assetVocabulary);

		AssetEntry assetEntry = AssetTestUtil.addAssetEntry(
			_group.getGroupId());

		_assetEntryAssetCategoryRelLocalService.addAssetEntryAssetCategoryRel(
			assetEntry.getEntryId(), assetCategory.getCategoryId());

		_serviceContext.setAssetCategoryIds(
			new long[] {assetCategory.getCategoryId()});

		_serviceContext.setAssetTagNames(new String[] {"tag1"});
	}

	@Inject
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Inject
	private AssetEntryAssetCategoryRelLocalService
		_assetEntryAssetCategoryRelLocalService;

	@Inject
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject(
		filter = "component.name=com.liferay.content.dashboard.blogs.internal.item.BlogsEntryContentDashboardItemFactory"
	)
	private ContentDashboardItemFactory _contentDashboardBlogsItemFactory;

	@Inject(
		filter = "component.name=com.liferay.content.dashboard.document.library.internal.item.FileEntryContentDashboardItemFactory"
	)
	private ContentDashboardItemFactory _contentDashboardFileItemFactory;

	@Inject(
		filter = "component.name=com.liferay.content.dashboard.journal.internal.item.JournalArticleContentDashboardItemFactory"
	)
	private ContentDashboardItemFactory _contentDashboardJournalItemFactory;

	@DeleteAfterTestRun
	private Group _group;

	@Inject(
		filter = "mvc.command.name=/content_dashboard/get_content_dashboard_item_info"
	)
	private MVCResourceCommand _mvcResourceCommand;

	private ServiceContext _serviceContext;

	private static class TestLiferayPortletURL extends MockLiferayPortletURL {

		@Override
		public MutableResourceParameters getResourceParameters() {
			return new MutableResourceParameters() {

				@Override
				public MutablePortletParameters add(
					PortletParameters portletParameters) {

					return null;
				}

				@Override
				public void clear() {
				}

				@Override
				public MutableResourceParameters clone() {
					return null;
				}

				@Override
				public Set<String> getNames() {
					return null;
				}

				@Override
				public String getValue(String s) {
					return null;
				}

				@Override
				public String[] getValues(String s) {
					return new String[0];
				}

				@Override
				public boolean isEmpty() {
					return false;
				}

				@Override
				public boolean removeParameter(String s) {
					return false;
				}

				@Override
				public MutablePortletParameters set(
					PortletParameters portletParameters) {

					return null;
				}

				@Override
				public String setValue(String s, String s1) {
					return null;
				}

				@Override
				public String[] setValues(String s, String... strings) {
					return new String[0];
				}

				@Override
				public int size() {
					return 0;
				}

			};
		}

	}

	private static class TestMockLiferayResourceResponse
		extends MockLiferayResourceResponse {

		@Override
		public ResourceURL createResourceURL() {
			return new TestLiferayPortletURL();
		}

	}

}