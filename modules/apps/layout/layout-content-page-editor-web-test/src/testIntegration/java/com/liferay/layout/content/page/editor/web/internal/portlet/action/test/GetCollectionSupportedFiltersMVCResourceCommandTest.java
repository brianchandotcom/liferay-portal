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

package com.liferay.layout.content.page.editor.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.info.collection.provider.CollectionQuery;
import com.liferay.info.collection.provider.FilteredInfoCollectionProvider;
import com.liferay.info.collection.provider.InfoCollectionProvider;
import com.liferay.info.filter.CategoriesInfoFilter;
import com.liferay.info.filter.InfoFilter;
import com.liferay.info.filter.KeywordsInfoFilter;
import com.liferay.info.list.provider.item.selector.criterion.InfoListProviderItemSelectorReturnType;
import com.liferay.info.pagination.InfoPage;
import com.liferay.info.pagination.Pagination;
import com.liferay.item.selector.criteria.InfoListItemSelectorReturnType;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

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

/**
 * @author Víctor Galán
 */
@RunWith(Arquillian.class)
@Sync
public class GetCollectionSupportedFiltersMVCResourceCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_serviceContext = new ServiceContext();

		_serviceContext.setScopeGroupId(_group.getGroupId());
		_serviceContext.setUserId(TestPropsValues.getUserId());

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);

		Bundle bundle = FrameworkUtil.getBundle(
			GetCollectionSupportedFiltersMVCResourceCommandTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_infoCollectionProviderServiceRegistration =
			bundleContext.registerService(
				(Class<InfoCollectionProvider<?>>)
					(Class<?>)InfoCollectionProvider.class,
				new TestInfoCollectionProvider(), null);
	}

	@After
	public void tearDown() {
		ServiceContextThreadLocal.popServiceContext();

		if (_infoCollectionProviderServiceRegistration != null) {
			_infoCollectionProviderServiceRegistration.unregister();
		}
	}

	@Test
	public void testAssetListSupportedFilters() throws PortalException {
		AssetListEntry assetListEntry =
			_assetListEntryLocalService.addDynamicAssetListEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				"Collection Title", _getTypeSettings(), _serviceContext);

		String collectionId = RandomTestUtil.randomString();

		JSONObject jsonObject = ReflectionTestUtil.invoke(
			_mvcResourceCommand, "_getSupportedFiltersJSONObject",
			new Class<?>[] {JSONArray.class},
			JSONUtil.put(
				JSONUtil.put(
					"collectionId", collectionId
				).put(
					"layoutObjectReference",
					JSONUtil.put(
						"classNameId",
						String.valueOf(
							_portal.getClassNameId(
								AssetListEntry.class.getName()))
					).put(
						"classPK",
						String.valueOf(assetListEntry.getAssetListEntryId())
					).put(
						"itemType", BlogsEntry.class.getName()
					).put(
						"type", InfoListItemSelectorReturnType.class.getName()
					)
				)));

		JSONArray supportedFiltersJSONArray = jsonObject.getJSONArray(
			collectionId);

		Assert.assertEquals(2, supportedFiltersJSONArray.length());
		Assert.assertTrue(
			JSONUtil.hasValue(
				supportedFiltersJSONArray,
				CategoriesInfoFilter.FILTER_TYPE_NAME));
		Assert.assertTrue(
			JSONUtil.hasValue(
				supportedFiltersJSONArray,
				KeywordsInfoFilter.FILTER_TYPE_NAME));
	}

	@Test
	public void testFilteredInfoCollectionProviderSupportedFilters() {
		String collectionId = RandomTestUtil.randomString();

		JSONObject jsonObject = ReflectionTestUtil.invoke(
			_mvcResourceCommand, "_getSupportedFiltersJSONObject",
			new Class<?>[] {JSONArray.class},
			JSONUtil.put(
				JSONUtil.put(
					"collectionId", collectionId
				).put(
					"layoutObjectReference",
					JSONUtil.put(
						"itemType", BlogsEntry.class.getName()
					).put(
						"key",
						GetCollectionSupportedFiltersMVCResourceCommandTest.
							TestInfoCollectionProvider.class.getName()
					).put(
						"type",
						InfoListProviderItemSelectorReturnType.class.getName()
					)
				)));

		JSONArray supportedFiltersJSONArray = jsonObject.getJSONArray(
			collectionId);

		Assert.assertEquals(1, supportedFiltersJSONArray.length());
		Assert.assertEquals(
			CategoriesInfoFilter.FILTER_TYPE_NAME,
			supportedFiltersJSONArray.get(0));
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
	private AssetListEntryLocalService _assetListEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private ServiceRegistration<InfoCollectionProvider<?>>
		_infoCollectionProviderServiceRegistration;

	@Inject(
		filter = "mvc.command.name=/layout_content_page_editor/get_collection_supported_filters"
	)
	private MVCResourceCommand _mvcResourceCommand;

	@Inject
	private Portal _portal;

	private ServiceContext _serviceContext;

	private class TestInfoCollectionProvider
		implements FilteredInfoCollectionProvider<BlogsEntry> {

		@Override
		public InfoPage<BlogsEntry> getCollectionInfoPage(
			CollectionQuery collectionQuery) {

			return InfoPage.of(Collections.emptyList(), Pagination.of(0, 0), 0);
		}

		@Override
		public String getLabel(Locale locale) {
			return TestInfoCollectionProvider.class.getSimpleName();
		}

		@Override
		public List<InfoFilter> getSupportedInfoFilters() {
			return Arrays.asList(new CategoriesInfoFilter());
		}

	}

}