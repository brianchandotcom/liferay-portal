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

package com.liferay.asset.auto.tagger.internal.osgi.commands.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.auto.tagger.AssetAutoTagProvider;
import com.liferay.asset.auto.tagger.test.util.AssetAutoTaggerTestUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;

import java.lang.reflect.Method;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
@Sync
public class AssetAutoTaggerOSGiCommandsTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		ServiceTestUtil.setUser(TestPropsValues.getUser());

		_group = GroupTestUtil.addGroup();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId(), 0);

		Registry registry = RegistryUtil.getRegistry();

		Map<String, Object> properties = new HashMap<>();

		properties.put("model.class.name", DLFileEntryConstants.getClassName());

		_assetAutoTagProviderServiceRegistration = registry.registerService(
			AssetAutoTagProvider.class,
			model -> Arrays.asList(AssetAutoTaggerTestUtil.ASSET_TAG_NAME_AUTO),
			properties);

		_assetAutoTaggerOSGiCommands = registry.getService(
			"com.liferay.asset.auto.tagger.internal.osgi.commands." +
				"AssetAutoTaggerOSGiCommands");
	}

	@Test
	public void testTagAllUntaggedTagsAllTheAssetsThatHaveNoTags()
		throws Exception {

		AssetEntry assetEntryWithNoPreviousTags = _addFileEntryAssetEntry();
		AssetEntry assetEntryWithPreviousTags = _addFileEntryAssetEntry();

		_applyAssetTagName(
			assetEntryWithPreviousTags,
			AssetAutoTaggerTestUtil.ASSET_TAG_NAME_MANUAL);

		AssetAutoTaggerTestUtil.assertContainsAssetTagName(
			assetEntryWithPreviousTags,
			AssetAutoTaggerTestUtil.ASSET_TAG_NAME_MANUAL);

		AssetAutoTaggerTestUtil.assertHasNoTags(assetEntryWithNoPreviousTags);

		AssetAutoTaggerTestUtil.withAutoTaggerEnabled(() -> _tagAllUntagged());

		AssetAutoTaggerTestUtil.assertContainsAssetTagName(
			assetEntryWithNoPreviousTags,
			AssetAutoTaggerTestUtil.ASSET_TAG_NAME_AUTO);

		AssetAutoTaggerTestUtil.assertDoesNotContainAssetTagName(
			assetEntryWithPreviousTags,
			AssetAutoTaggerTestUtil.ASSET_TAG_NAME_AUTO);
	}

	@Test
	public void testUnTagAllRemovesAllTheAutoTags() throws Exception {
		AssetAutoTaggerTestUtil.withAutoTaggerEnabled(
			() -> {
				AssetEntry assetEntry = _addFileEntryAssetEntry();

				_applyAssetTagName(
					assetEntry, AssetAutoTaggerTestUtil.ASSET_TAG_NAME_MANUAL);

				AssetAutoTaggerTestUtil.assertContainsAssetTagName(
					assetEntry, AssetAutoTaggerTestUtil.ASSET_TAG_NAME_AUTO);

				AssetAutoTaggerTestUtil.assertContainsAssetTagName(
					assetEntry, AssetAutoTaggerTestUtil.ASSET_TAG_NAME_MANUAL);

				_untagAll();

				AssetAutoTaggerTestUtil.assertDoesNotContainAssetTagName(
					assetEntry, AssetAutoTaggerTestUtil.ASSET_TAG_NAME_AUTO);

				AssetAutoTaggerTestUtil.assertContainsAssetTagName(
					assetEntry, AssetAutoTaggerTestUtil.ASSET_TAG_NAME_MANUAL);
			});
	}

	private AssetEntry _addFileEntryAssetEntry() throws PortalException {
		FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), ContentTypes.TEXT_PLAIN,
			RandomTestUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), new byte[0], _serviceContext);

		return _assetEntryLocalService.getEntry(
			DLFileEntryConstants.getClassName(), fileEntry.getFileEntryId());
	}

	private void _applyAssetTagName(AssetEntry assetEntry, String assetTagName)
		throws PortalException {

		_assetEntryLocalService.updateEntry(
			assetEntry.getUserId(), assetEntry.getGroupId(),
			assetEntry.getClassName(), assetEntry.getClassPK(),
			assetEntry.getCategoryIds(),
			ArrayUtil.append(assetEntry.getTagNames(), assetTagName));
	}

	private void _tagAllUntagged(String... classNames) throws Exception {
		Class<?> clazz = _assetAutoTaggerOSGiCommands.getClass();

		Method method = clazz.getMethod("tagAllUntagged", String[].class);

		method.invoke(_assetAutoTaggerOSGiCommands, (Object)classNames);
	}

	private void _untagAll(String... classNames) throws Exception {
		Class<?> clazz = _assetAutoTaggerOSGiCommands.getClass();

		Method method = clazz.getMethod("untagAll", String[].class);

		method.invoke(_assetAutoTaggerOSGiCommands, (Object)classNames);
	}

	private Object _assetAutoTaggerOSGiCommands;
	private ServiceRegistration<AssetAutoTagProvider>
		_assetAutoTagProviderServiceRegistration;

	@Inject
	private AssetEntryLocalService _assetEntryLocalService;

	private Group _group;
	private ServiceContext _serviceContext;

}