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

package com.liferay.asset.auto.tagger.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.auto.tagger.AssetAutoTagProvider;
import com.liferay.asset.auto.tagger.AssetAutoTagger;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnsafeRunnable;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;

import java.util.Arrays;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
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
@Sync
public class AssetAutoTaggerTest {

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

		Map<String, Object> properties = new HashMap<>();

		properties.put("model.class.name", DLFileEntryConstants.getClassName());

		Registry registry = RegistryUtil.getRegistry();

		_assetAutoTagProviderServiceRegistration = registry.registerService(
			AssetAutoTagProvider.class, model -> Arrays.asList(_AUTO_TAG),
			properties);
	}

	@After
	public void tearDown() {
		_assetAutoTagProviderServiceRegistration.unregister();
	}

	@Test
	public void testAutoTagsANewAssetOnCreation() throws Exception {
		_withAutoTaggingEnabled(
			() -> {
				AssetEntry assetEntry = _addAssetEntry();

				_assertContainsTag(assetEntry, _AUTO_TAG);
			});
	}

	@Test
	public void testIsDisabledByDefault() throws Exception {
		AssetEntry assetEntry = _addAssetEntry();

		List<AssetTag> assetTags = assetEntry.getTags();

		Assert.assertTrue(assetTags.isEmpty());
	}

	@Test
	public void testKeepsTagCounters() throws Exception {
		_withAutoTaggingEnabled(
			() -> {
				AssetEntry assetEntry = _addAssetEntry();

				AssetTag assetTag = _assetTagLocalService.getTag(
					_group.getGroupId(), _AUTO_TAG);

				Assert.assertEquals(1, assetTag.getAssetCount());

				_assetAutoTagger.untag(assetEntry);

				assetTag = _assetTagLocalService.getTag(
					_group.getGroupId(), _AUTO_TAG);

				Assert.assertEquals(0, assetTag.getAssetCount());
			});
	}

	@Test
	public void testRemovesAutoTags() throws Exception {
		_withAutoTaggingEnabled(
			() -> {
				AssetEntry assetEntry = _addAssetEntry();

				_assertContainsTag(assetEntry, _AUTO_TAG);

				_applyTag(assetEntry, _MANUAL_TAG);

				_assetAutoTagger.untag(assetEntry);

				String[] tagNames = assetEntry.getTagNames();

				Assert.assertEquals(tagNames.toString(), 1, tagNames.length);

				_assertContainsTag(assetEntry, _MANUAL_TAG);
			});
	}

	private AssetEntry _addAssetEntry() throws PortalException {
		FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), ContentTypes.TEXT_PLAIN,
			RandomTestUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), new byte[0], _serviceContext);

		return _assetEntryLocalService.getEntry(
			DLFileEntryConstants.getClassName(), fileEntry.getFileEntryId());
	}

	private void _applyTag(AssetEntry assetEntry, String tag)
		throws PortalException {

		_assetEntryLocalService.updateEntry(
			assetEntry.getUserId(), assetEntry.getGroupId(),
			assetEntry.getClassName(), assetEntry.getClassPK(),
			assetEntry.getCategoryIds(),
			ArrayUtil.append(assetEntry.getTagNames(), tag));
	}

	private void _assertContainsTag(AssetEntry assetEntry, String tag) {
		for (AssetTag assetTag : assetEntry.getTags()) {
			if (StringUtil.equals(assetTag.getName(), tag)) {
				return;
			}
		}

		throw new AssertionError(
			String.format(
				"The asset entry has not been tagged with '%s'", tag));
	}

	private void _withAutoTaggingEnabled(UnsafeRunnable runnable)
		throws Exception {

		Dictionary<String, Object> dictionary = new HashMapDictionary<>();

		dictionary.put("enabled", true);

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					_CONFIGURATION_PID, dictionary)) {

			runnable.run();
		}
	}

	private static final String _AUTO_TAG = "auto tag";

	private static final String _CONFIGURATION_PID =
		"com.liferay.asset.auto.tagger.internal.configuration." +
			"AssetAutoTaggerConfiguration";

	private static final String _MANUAL_TAG = "manual tag";

	@Inject
	private AssetAutoTagger _assetAutoTagger;

	private ServiceRegistration<AssetAutoTagProvider>
		_assetAutoTagProviderServiceRegistration;

	@Inject
	private AssetEntryLocalService _assetEntryLocalService;

	@Inject
	private AssetTagLocalService _assetTagLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private ServiceContext _serviceContext;

	@FunctionalInterface
	private interface UnsafeRunnable {

		public void run() throws Exception;

	}

}