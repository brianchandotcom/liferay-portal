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

package com.liferay.layout.list.retriever.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.info.list.provider.InfoItemRelatedListProvider;
import com.liferay.info.list.provider.InfoListProvider;
import com.liferay.info.list.provider.InfoListProviderContext;
import com.liferay.info.list.provider.item.selector.criterion.InfoItemRelatedListProviderItemSelectorReturnType;
import com.liferay.info.list.provider.item.selector.criterion.InfoListProviderItemSelectorReturnType;
import com.liferay.info.pagination.InfoPage;
import com.liferay.info.pagination.Pagination;
import com.liferay.info.sort.Sort;
import com.liferay.layout.list.retriever.DefaultLayoutListRetrieverContext;
import com.liferay.layout.list.retriever.KeyListObjectReference;
import com.liferay.layout.list.retriever.LayoutListRetriever;
import com.liferay.layout.list.retriever.LayoutListRetrieverTracker;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;

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

/**
 * @author Víctor Galán
 */
@RunWith(Arquillian.class)
public class LayoutListRetrieverTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		ServiceContextThreadLocal.pushServiceContext(
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	@After
	public void tearDown() {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testAssetInfoItemRelatedListProviderLayoutListRetriever()
		throws Exception {

		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<InfoItemRelatedListProvider<?, ?>>
			serviceRegistration = registry.registerService(
				(Class<InfoItemRelatedListProvider<?, ?>>)
					(Class<?>)InfoItemRelatedListProvider.class,
				new AssetEntryInfoItemRelatedListProvider());

		LayoutListRetriever<?, KeyListObjectReference> layoutListRetriever =
			(LayoutListRetriever<?, KeyListObjectReference>)
				_layoutListRetrieverTracker.getLayoutListRetriever(
					InfoItemRelatedListProviderItemSelectorReturnType.class.
						getName());

		KeyListObjectReference keyListObjectReference =
			new KeyListObjectReference(
				JSONUtil.put(
					"key",
					AssetEntryInfoItemRelatedListProvider.class.getName()));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId(),
				new String[] {"tag1", "tag2"});

		FileEntry fileEntry = DLAppLocalServiceUtil.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), ContentTypes.APPLICATION_OCTET_STREAM,
			new byte[0], null, null, serviceContext);

		DefaultLayoutListRetrieverContext layoutListRetrieverContext =
			new DefaultLayoutListRetrieverContext();

		layoutListRetrieverContext.setContextObject(fileEntry);

		List<Object> list = layoutListRetriever.getList(
			keyListObjectReference, layoutListRetrieverContext);

		Assert.assertEquals(list.toString(), 2, list.size());

		AssetTag assetTag1 = (AssetTag)list.get(0);

		Assert.assertEquals("tag1", assetTag1.getName());

		AssetTag assetTag2 = (AssetTag)list.get(1);

		Assert.assertEquals("tag2", assetTag2.getName());

		serviceRegistration.unregister();
	}

	@Test
	public void testInfoListProviderLayoutListRetriever() {
		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<InfoListProvider<?>> serviceRegistration =
			registry.registerService(
				(Class<InfoListProvider<?>>)(Class<?>)InfoListProvider.class,
				new TestInfoListProvider());

		LayoutListRetriever<?, KeyListObjectReference> layoutListRetriever =
			(LayoutListRetriever<?, KeyListObjectReference>)
				_layoutListRetrieverTracker.getLayoutListRetriever(
					InfoListProviderItemSelectorReturnType.class.getName());

		KeyListObjectReference keyListObjectReference =
			new KeyListObjectReference(
				JSONUtil.put("key", TestInfoListProvider.class.getName()));

		List<Object> list = layoutListRetriever.getList(
			keyListObjectReference, new DefaultLayoutListRetrieverContext());

		Assert.assertEquals(list.toString(), 1, list.size());
		Assert.assertEquals(TestInfoListProvider.class.getName(), list.get(0));

		serviceRegistration.unregister();
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutListRetrieverTracker _layoutListRetrieverTracker;

	private static class AssetEntryInfoItemRelatedListProvider
		implements InfoItemRelatedListProvider<AssetEntry, AssetTag> {

		@Override
		public String getLabel(Locale locale) {
			return StringPool.BLANK;
		}

		@Override
		public InfoPage<AssetTag> getRelatedItemsInfoPage(
			AssetEntry assetEntry,
			InfoListProviderContext infoListProviderContext,
			Pagination pagination, Sort sort) {

			return InfoPage.of(assetEntry.getTags());
		}

	}

	private static class TestInfoListProvider
		implements InfoListProvider<String> {

		@Override
		public List<String> getInfoList(
			InfoListProviderContext infoListProviderContext) {

			return getInfoList(infoListProviderContext, null, null);
		}

		@Override
		public List<String> getInfoList(
			InfoListProviderContext infoListProviderContext,
			Pagination pagination, Sort sort) {

			return Collections.singletonList(
				TestInfoListProvider.class.getName());
		}

		@Override
		public int getInfoListCount(
			InfoListProviderContext infoListProviderContext) {

			return 1;
		}

		@Override
		public String getLabel(Locale locale) {
			return StringPool.BLANK;
		}

	}

}