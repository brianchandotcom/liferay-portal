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

package com.liferay.blogs.web.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetCategoryConstants;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.model.AssetVocabularyConstants;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.comment.CommentManagerUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.IdentityServiceContextFunction;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
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
 * @author Cristina González
 */
@RunWith(Arquillian.class)
@Sync
public class BlogEntriesDisplayContextTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_company = _companyLocalService.getCompany(_group.getCompanyId());
		_layout = LayoutTestUtil.addTypePortletLayout(_group);
	}

	@Test
	public void testGetSearchContainer() throws Exception {
		for (int i = 0; i <= SearchContainer.DEFAULT_DELTA; i++) {
			_addBlogEntry("alpha_" + i);
		}

		SearchContainer<BlogsEntry> searchContainer = _getSearchContainer(
			_getMockHttpServletRequest());

		Assert.assertEquals(
			SearchContainer.DEFAULT_DELTA + 1, searchContainer.getTotal());

		List<BlogsEntry> blogsEntries = searchContainer.getResults();

		Assert.assertEquals(
			blogsEntries.toString(), SearchContainer.DEFAULT_DELTA,
			blogsEntries.size());
	}

	@Test
	public void testGetSearchContainerByComment() throws Exception {
		BlogsEntry blogsEntry = _addBlogEntry(RandomTestUtil.randomString());

		String commentBody = RandomTestUtil.randomString();

		CommentManagerUtil.addComment(
			TestPropsValues.getUserId(), _group.getGroupId(),
			BlogsEntry.class.getName(), blogsEntry.getEntryId(), commentBody,
			new IdentityServiceContextFunction(
				ServiceContextTestUtil.getServiceContext()));

		SearchContainer<BlogsEntry> searchContainer = _getSearchContainer(
			_getMockHttpServletRequestWithSearch(commentBody));

		Assert.assertEquals(1, searchContainer.getTotal());

		List<BlogsEntry> blogsEntries = searchContainer.getResults();

		Assert.assertEquals(blogsEntries.toString(), 1, blogsEntries.size());
	}

	@Test
	public void testGetSearchContainerByInternalAssetCategory()
		throws Exception {

		AssetVocabulary assetVocabulary = _addAssetVocabulary(
			AssetVocabularyConstants.VISIBILITY_TYPE_INTERNAL);

		AssetCategory assetCategory = _addAssetCategory(assetVocabulary);

		BlogsEntry blogsEntry = _addBlogEntry(
			new long[] {assetCategory.getCategoryId()});

		_addBlogEntry(RandomTestUtil.randomString());

		SearchContainer<BlogsEntry> searchContainer = _getSearchContainer(
			_getMockHttpServletRequestWithSearch(assetCategory.getName()));

		Assert.assertEquals(1, searchContainer.getTotal());

		List<BlogsEntry> blogsEntries = searchContainer.getResults();

		Assert.assertEquals(blogsEntries.toString(), 1, blogsEntries.size());

		BlogsEntry returnedBlogsEntry = blogsEntries.get(0);

		Assert.assertEquals(
			returnedBlogsEntry.getTitle(), blogsEntry.getTitle());
	}

	@Test
	public void testGetSearchContainerByPublicAssetCategory() throws Exception {
		AssetVocabulary assetVocabulary = _addAssetVocabulary(
			AssetVocabularyConstants.VISIBILITY_TYPE_PUBLIC);

		AssetCategory assetCategory = _addAssetCategory(assetVocabulary);

		BlogsEntry blogsEntry = _addBlogEntry(
			new long[] {assetCategory.getCategoryId()});

		_addBlogEntry(RandomTestUtil.randomString());

		SearchContainer<BlogsEntry> searchContainer = _getSearchContainer(
			_getMockHttpServletRequestWithSearch(assetCategory.getName()));

		Assert.assertEquals(1, searchContainer.getTotal());

		List<BlogsEntry> blogsEntries = searchContainer.getResults();

		Assert.assertEquals(blogsEntries.toString(), 1, blogsEntries.size());

		BlogsEntry returnedBlogsEntry = blogsEntries.get(0);

		Assert.assertEquals(
			returnedBlogsEntry.getTitle(), blogsEntry.getTitle());
	}

	private AssetCategory _addAssetCategory(AssetVocabulary assetVocabulary)
		throws Exception {

		return _assetCategoryLocalService.addCategory(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			HashMapBuilder.put(
				LocaleUtil.US, RandomTestUtil.randomString()
			).build(),
			null, assetVocabulary.getVocabularyId(), null,
			new ServiceContext());
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

	private BlogsEntry _addBlogEntry(long[] assetCategoryIds) throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		serviceContext.setAssetCategoryIds(assetCategoryIds);

		return _addBlogEntry(RandomTestUtil.randomString(), serviceContext);
	}

	private BlogsEntry _addBlogEntry(String title) throws Exception {
		return _addBlogEntry(
			title,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	private BlogsEntry _addBlogEntry(
			String title, ServiceContext serviceContext)
		throws Exception {

		return _blogsEntryService.addEntry(
			title, RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), 1, 1, 1990, 1, 1, true, false,
			new String[0], RandomTestUtil.randomString(), null, null,
			serviceContext);
	}

	private MockHttpServletRequest _getMockHttpServletRequest()
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());

		return mockHttpServletRequest;
	}

	private MockHttpServletRequest _getMockHttpServletRequestWithSearch(
			String keywords)
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			_getMockHttpServletRequest();

		mockHttpServletRequest.setParameter(
			"mvcRenderCommandName", "/blogs/view");
		mockHttpServletRequest.setParameter("keywords", keywords);

		return mockHttpServletRequest;
	}

	private SearchContainer<BlogsEntry> _getSearchContainer(
			MockHttpServletRequest mockHttpServletRequest)
		throws Exception {

		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			new MockLiferayPortletRenderRequest(mockHttpServletRequest);

		_mvcRenderCommand.render(
			mockLiferayPortletRenderRequest,
			new MockLiferayPortletRenderResponse());

		Object blogEntriesDisplayContext =
			mockLiferayPortletRenderRequest.getAttribute(
				"BLOG_ENTRIES_DISPLAY_CONTEXT");

		return ReflectionTestUtil.invoke(
			blogEntriesDisplayContext, "getSearchContainer", new Class<?>[0],
			null);
	}

	private ThemeDisplay _getThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(_company);
		themeDisplay.setLayout(_layout);
		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		themeDisplay.setRealUser(TestPropsValues.getUser());
		themeDisplay.setScopeGroupId(_layout.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	@Inject
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Inject
	private AssetEntryLocalService _assetEntryLocalService;

	@Inject
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Inject
	private BlogsEntryService _blogsEntryService;

	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	@Inject(
		filter = "component.name=com.liferay.blogs.web.internal.portlet.action.ViewMVCRenderCommand"
	)
	private MVCRenderCommand _mvcRenderCommand;

}