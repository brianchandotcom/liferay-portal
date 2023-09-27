/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.list.constants.AssetListEntryTypeConstants;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.info.collection.provider.CollectionQuery;
import com.liferay.info.collection.provider.InfoCollectionProvider;
import com.liferay.info.list.provider.item.selector.criterion.InfoListProviderItemSelectorReturnType;
import com.liferay.info.pagination.InfoPage;
import com.liferay.item.selector.criteria.InfoListItemSelectorReturnType;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.CriteriaSerializer;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributor;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsExperienceLocalService;
import com.liferay.segments.test.util.SegmentsTestUtil;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.After;
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
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Víctor Galán
 */
@RunWith(Arquillian.class)
@Sync
public class GetCollectionFieldMVCResourceCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_layout = LayoutTestUtil.addTypeContentLayout(_group);
		_user = UserTestUtil.addUser(_group.getGroupId());

		_serviceContext = new ServiceContext();

		_serviceContext.setAddGroupPermissions(true);
		_serviceContext.setAddGuestPermissions(true);
		_serviceContext.setScopeGroupId(_group.getGroupId());
		_serviceContext.setUserId(_user.getUserId());

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);

		Bundle bundle = FrameworkUtil.getBundle(
			GetCollectionFieldMVCResourceCommandTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_infoCollectionProviderServiceRegistration =
			bundleContext.registerService(
				(Class<InfoCollectionProvider<?>>)
					(Class<?>)InfoCollectionProvider.class,
				new TestInfoCollectionProvider(), null);

		_originalThemeDisplayDefaultLocale =
			LocaleThreadLocal.getThemeDisplayLocale();

		LocaleThreadLocal.setThemeDisplayLocale(LocaleUtil.US);
	}

	@After
	public void tearDown() {
		ServiceContextThreadLocal.popServiceContext();

		if (_infoCollectionProviderServiceRegistration != null) {
			_infoCollectionProviderServiceRegistration.unregister();
		}

		LocaleThreadLocal.setThemeDisplayLocale(
			_originalThemeDisplayDefaultLocale);
	}

	@Test
	public void testGetCollectionFieldFromCollectionProvider()
		throws Exception {

		BlogsEntry blogsEntry = _addBlogsEntry();

		JSONObject jsonObject = ReflectionTestUtil.invoke(
			_mvcResourceCommand, "_getCollectionFieldsJSONObject",
			new Class<?>[] {
				HttpServletRequest.class, HttpServletResponse.class, int.class,
				boolean.class, boolean.class, String.class, String.class,
				String.class, String.class, String.class, int.class, int.class,
				int.class, String.class, long.class, String.class
			},
			_getHttpServletRequest(), new MockHttpServletResponse(), 0, false,
			false, LocaleUtil.toLanguageId(LocaleUtil.US),
			JSONUtil.put(
				"itemType", BlogsEntry.class.getName()
			).put(
				"key", TestInfoCollectionProvider.class.getName()
			).put(
				"type", InfoListProviderItemSelectorReturnType.class.getName()
			).toString(),
			StringPool.BLANK, StringPool.BLANK, StringPool.BLANK, 1, 20, 0,
			"regular",
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				_layout.getPlid()),
			StringPool.BLANK);

		Assert.assertEquals(1, jsonObject.getInt("length"));

		JSONArray jsonArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(1, jsonArray.length());

		JSONObject itemJSONObject = jsonArray.getJSONObject(0);

		Assert.assertEquals(
			blogsEntry.getTitle(), itemJSONObject.getString("title"));
	}

	@Test
	public void testGetCollectionFieldFromCollectionProviderWithSegments()
		throws Exception {

		_addSegmentsEntry(_user);

		BlogsEntry blogsEntry = _addBlogsEntry();

		JSONObject jsonObject = ReflectionTestUtil.invoke(
			_mvcResourceCommand, "_getCollectionFieldsJSONObject",
			new Class<?>[] {
				HttpServletRequest.class, HttpServletResponse.class, int.class,
				boolean.class, boolean.class, String.class, String.class,
				String.class, String.class, String.class, int.class, int.class,
				int.class, String.class, long.class, String.class
			},
			_getHttpServletRequest(), new MockHttpServletResponse(), 0, false,
			false, LocaleUtil.toLanguageId(LocaleUtil.US),
			JSONUtil.put(
				"itemType", BlogsEntry.class.getName()
			).put(
				"key", TestInfoCollectionProvider.class.getName()
			).put(
				"type", InfoListProviderItemSelectorReturnType.class.getName()
			).toString(),
			StringPool.BLANK, StringPool.BLANK, StringPool.BLANK, 1, 20, 0,
			"regular",
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				_layout.getPlid()),
			StringPool.BLANK);

		Assert.assertEquals(1, jsonObject.getInt("length"));

		JSONArray jsonArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(1, jsonArray.length());

		JSONObject itemJSONObject = jsonArray.getJSONObject(0);

		Assert.assertEquals(
			blogsEntry.getTitle(), itemJSONObject.getString("title"));
	}

	@Test
	public void testGetCollectionFieldFromDynamicCollection() throws Exception {
		BlogsEntry blogsEntry1 = _addBlogsEntry();

		BlogsEntry blogsEntry2 = _addBlogsEntry();

		AssetListEntry assetListEntry =
			_assetListEntryLocalService.addDynamicAssetListEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				"Collection Title", _getTypeSettings(), _serviceContext);

		JSONObject jsonObject = ReflectionTestUtil.invoke(
			_mvcResourceCommand, "_getCollectionFieldsJSONObject",
			new Class<?>[] {
				HttpServletRequest.class, HttpServletResponse.class, int.class,
				boolean.class, boolean.class, String.class, String.class,
				String.class, String.class, String.class, int.class, int.class,
				int.class, String.class, long.class, String.class
			},
			_getHttpServletRequest(), new MockHttpServletResponse(), 0, false,
			false, LocaleUtil.toLanguageId(LocaleUtil.US),
			JSONUtil.put(
				"classNameId",
				String.valueOf(
					_portal.getClassNameId(AssetListEntry.class.getName()))
			).put(
				"classPK", String.valueOf(assetListEntry.getAssetListEntryId())
			).put(
				"itemType", BlogsEntry.class.getName()
			).put(
				"type", InfoListItemSelectorReturnType.class.getName()
			).toString(),
			StringPool.BLANK, StringPool.BLANK, StringPool.BLANK, 2, 20, 0,
			"regular",
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				_layout.getPlid()),
			StringPool.BLANK);

		Assert.assertEquals(2, jsonObject.getInt("length"));

		JSONArray jsonArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(2, jsonArray.length());

		JSONObject itemJSONObject1 = jsonArray.getJSONObject(0);

		Assert.assertEquals(
			blogsEntry1.getTitle(), itemJSONObject1.getString("title"));

		JSONObject itemJSONObject2 = jsonArray.getJSONObject(1);

		Assert.assertEquals(
			blogsEntry2.getTitle(), itemJSONObject2.getString("title"));
	}

	@Test
	public void testGetCollectionFieldFromDynamicCollectionWithSize()
		throws Exception {

		BlogsEntry blogsEntry = _addBlogsEntry();

		_addBlogsEntry();

		AssetListEntry assetListEntry =
			_assetListEntryLocalService.addDynamicAssetListEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				"Collection Title", _getTypeSettings(), _serviceContext);

		JSONObject jsonObject = ReflectionTestUtil.invoke(
			_mvcResourceCommand, "_getCollectionFieldsJSONObject",
			new Class<?>[] {
				HttpServletRequest.class, HttpServletResponse.class, int.class,
				boolean.class, boolean.class, String.class, String.class,
				String.class, String.class, String.class, int.class, int.class,
				int.class, String.class, long.class, String.class
			},
			_getHttpServletRequest(), new MockHttpServletResponse(), 0, false,
			false, LocaleUtil.toLanguageId(LocaleUtil.US),
			JSONUtil.put(
				"classNameId",
				String.valueOf(
					_portal.getClassNameId(AssetListEntry.class.getName()))
			).put(
				"classPK", String.valueOf(assetListEntry.getAssetListEntryId())
			).put(
				"itemType", BlogsEntry.class.getName()
			).put(
				"type", InfoListItemSelectorReturnType.class.getName()
			).toString(),
			StringPool.BLANK, StringPool.BLANK, StringPool.BLANK, 1, 1, 1,
			"regular",
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				_layout.getPlid()),
			StringPool.BLANK);

		Assert.assertEquals(2, jsonObject.getInt("length"));

		JSONArray jsonArray = jsonObject.getJSONArray("items");

		Assert.assertEquals(1, jsonArray.length());

		JSONObject itemJSONObject = jsonArray.getJSONObject(0);

		Assert.assertEquals(
			blogsEntry.getTitle(), itemJSONObject.getString("title"));
	}

	@Test
	public void testGetCollectionFieldWithDifferentSegmentsExperiences()
		throws Exception {

		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		SegmentsEntry segmentsEntry1 = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId());

		SegmentsExperience segmentsExperience1 =
			_segmentsExperienceLocalService.addSegmentsExperience(
				TestPropsValues.getUserId(), layout.getGroupId(),
				segmentsEntry1.getSegmentsEntryId(), layout.getPlid(),
				HashMapBuilder.put(
					LocaleUtil.getDefault(), RandomTestUtil.randomString()
				).build(),
				true, new UnicodeProperties(true), _serviceContext);

		SegmentsEntry segmentsEntry2 = SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId());

		SegmentsExperience segmentsExperience2 =
			_segmentsExperienceLocalService.addSegmentsExperience(
				TestPropsValues.getUserId(), layout.getGroupId(),
				segmentsEntry2.getSegmentsEntryId(), layout.getPlid(),
				HashMapBuilder.put(
					LocaleUtil.getDefault(), RandomTestUtil.randomString()
				).build(),
				true, new UnicodeProperties(true), _serviceContext);

		AssetListEntry assetListEntry =
			_assetListEntryLocalService.addAssetListEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				"Manual title", AssetListEntryTypeConstants.TYPE_MANUAL, null,
				_serviceContext);

		BlogsEntry blogsEntry1 = _addBlogsEntry();
		BlogsEntry blogsEntry2 = _addBlogsEntry();
		BlogsEntry blogsEntry3 = _addBlogsEntry();

		AssetEntry assetEntry1 = _assetEntryLocalService.fetchEntry(
			BlogsEntry.class.getName(), blogsEntry1.getEntryId());
		AssetEntry assetEntry2 = _assetEntryLocalService.fetchEntry(
			BlogsEntry.class.getName(), blogsEntry2.getEntryId());
		AssetEntry assetEntry3 = _assetEntryLocalService.fetchEntry(
			BlogsEntry.class.getName(), blogsEntry3.getEntryId());

		long defaultSegmentsExperienceId =
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				layout.getPlid());

		SegmentsExperience defaultSegmentsExperience =
			_segmentsExperienceLocalService.fetchSegmentsExperience(
				_group.getGroupId(), SegmentsExperienceConstants.KEY_DEFAULT,
				layout.getPlid());

		_assetListEntryLocalService.addAssetEntrySelections(
			assetListEntry.getAssetListEntryId(),
			new long[] {assetEntry1.getEntryId()},
			defaultSegmentsExperience.getSegmentsEntryId(), _serviceContext);

		_assetListEntryLocalService.addAssetEntrySelections(
			assetListEntry.getAssetListEntryId(),
			new long[] {
				assetEntry1.getEntryId(), assetEntry2.getEntryId(),
				assetEntry3.getEntryId()
			},
			segmentsEntry1.getSegmentsEntryId(), _serviceContext);

		JSONObject jsonObject = ReflectionTestUtil.invoke(
			_mvcResourceCommand, "_getCollectionFieldsJSONObject",
			new Class<?>[] {
				HttpServletRequest.class, HttpServletResponse.class, int.class,
				boolean.class, boolean.class, String.class, String.class,
				String.class, String.class, String.class, int.class, int.class,
				int.class, String.class, long.class, String.class
			},
			_getHttpServletRequest(), new MockHttpServletResponse(), 0, false,
			false, LocaleUtil.toLanguageId(LocaleUtil.US),
			JSONUtil.put(
				"classNameId",
				_portal.getClassNameId(AssetListEntry.class.getName())
			).put(
				"classPK", assetListEntry.getAssetListEntryId()
			).put(
				"itemType", AssetEntry.class.getName()
			).put(
				"type", InfoListItemSelectorReturnType.class.getName()
			).toString(),
			StringPool.BLANK, StringPool.BLANK, StringPool.BLANK, 1, 1, 1,
			"regular", defaultSegmentsExperienceId, StringPool.BLANK);

		Assert.assertEquals(1, jsonObject.getInt("length"));

		jsonObject = ReflectionTestUtil.invoke(
			_mvcResourceCommand, "_getCollectionFieldsJSONObject",
			new Class<?>[] {
				HttpServletRequest.class, HttpServletResponse.class, int.class,
				boolean.class, boolean.class, String.class, String.class,
				String.class, String.class, String.class, int.class, int.class,
				int.class, String.class, long.class, String.class
			},
			_getHttpServletRequest(), new MockHttpServletResponse(), 0, false,
			false, LocaleUtil.toLanguageId(LocaleUtil.US),
			JSONUtil.put(
				"classNameId",
				_portal.getClassNameId(AssetListEntry.class.getName())
			).put(
				"classPK", assetListEntry.getAssetListEntryId()
			).put(
				"itemType", AssetEntry.class.getName()
			).put(
				"type", InfoListItemSelectorReturnType.class.getName()
			).toString(),
			StringPool.BLANK, StringPool.BLANK, StringPool.BLANK, 1, 1, 1,
			"regular", segmentsExperience1.getSegmentsExperienceId(),
			StringPool.BLANK);

		Assert.assertEquals(3, jsonObject.getInt("length"));

		jsonObject = ReflectionTestUtil.invoke(
			_mvcResourceCommand, "_getCollectionFieldsJSONObject",
			new Class<?>[] {
				HttpServletRequest.class, HttpServletResponse.class, int.class,
				boolean.class, boolean.class, String.class, String.class,
				String.class, String.class, String.class, int.class, int.class,
				int.class, String.class, long.class, String.class
			},
			_getHttpServletRequest(), new MockHttpServletResponse(), 0, false,
			false, LocaleUtil.toLanguageId(LocaleUtil.US),
			JSONUtil.put(
				"classNameId",
				_portal.getClassNameId(AssetListEntry.class.getName())
			).put(
				"classPK", assetListEntry.getAssetListEntryId()
			).put(
				"itemType", AssetEntry.class.getName()
			).put(
				"type", InfoListItemSelectorReturnType.class.getName()
			).toString(),
			StringPool.BLANK, StringPool.BLANK, StringPool.BLANK, 1, 1, 1,
			"regular", segmentsExperience2.getSegmentsExperienceId(),
			StringPool.BLANK);

		Assert.assertEquals(1, jsonObject.getInt("length"));
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	private BlogsEntry _addBlogsEntry() throws Exception {
		return _blogsEntryLocalService.addEntry(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), _serviceContext);
	}

	private SegmentsEntry _addSegmentsEntry(User user) throws Exception {
		Criteria criteria = new Criteria();

		_userSegmentsCriteriaContributor.contribute(
			criteria, String.format("(firstName eq '%s')", user.getFirstName()),
			Criteria.Conjunction.AND);

		return SegmentsTestUtil.addSegmentsEntry(
			_group.getGroupId(), CriteriaSerializer.serialize(criteria),
			User.class.getName());
	}

	private HttpServletRequest _getHttpServletRequest() throws Exception {
		HttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(WebKeys.LAYOUT, _layout);

		ThemeDisplay themeDisplay = ContentLayoutTestUtil.getThemeDisplay(
			_companyLocalService.fetchCompany(_group.getCompanyId()), _group,
			_layout);

		themeDisplay.setRequest(mockHttpServletRequest);

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		mockHttpServletRequest.setAttribute(WebKeys.USER_ID, _user.getUserId());

		return mockHttpServletRequest;
	}

	private String _getTypeSettings() {
		return UnicodePropertiesBuilder.create(
			true
		).put(
			"anyAssetType",
			String.valueOf(_portal.getClassNameId(BlogsEntry.class))
		).put(
			"classNameIds", BlogsEntry.class.getName()
		).put(
			"groupIds", String.valueOf(_group.getGroupId())
		).put(
			"orderByColumn1", "modifiedDate"
		).put(
			"orderByColumn2", "title"
		).put(
			"orderByType1", "ASC"
		).put(
			"orderByType2", "ASC"
		).buildString();
	}

	@Inject
	private AssetEntryLocalService _assetEntryLocalService;

	@Inject
	private AssetListEntryLocalService _assetListEntryLocalService;

	@Inject
	private BlogsEntryLocalService _blogsEntryLocalService;

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private ServiceRegistration<InfoCollectionProvider<?>>
		_infoCollectionProviderServiceRegistration;
	private Layout _layout;

	@Inject(
		filter = "mvc.command.name=/layout_content_page_editor/get_collection_field"
	)
	private MVCResourceCommand _mvcResourceCommand;

	private Locale _originalThemeDisplayDefaultLocale;

	@Inject
	private Portal _portal;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	private ServiceContext _serviceContext;
	private User _user;

	@Inject(
		filter = "segments.criteria.contributor.key=user",
		type = SegmentsCriteriaContributor.class
	)
	private SegmentsCriteriaContributor _userSegmentsCriteriaContributor;

	private class TestInfoCollectionProvider
		implements InfoCollectionProvider<BlogsEntry> {

		@Override
		public InfoPage<BlogsEntry> getCollectionInfoPage(
			CollectionQuery collectionQuery) {

			return InfoPage.of(
				_blogsEntryLocalService.getGroupEntries(
					_group.getGroupId(), _queryDefinition),
				collectionQuery.getPagination(),
				_blogsEntryLocalService.getGroupEntriesCount(
					_group.getGroupId(), _queryDefinition));
		}

		@Override
		public String getLabel(Locale locale) {
			return TestInfoCollectionProvider.class.getSimpleName();
		}

		private final QueryDefinition<BlogsEntry> _queryDefinition =
			new QueryDefinition<>(
				WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

	}

}