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

package com.liferay.asset.auto.tagger.test;

import com.liferay.asset.auto.tagger.AssetAutoTagProvider;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.service.test.ServiceTestUtil;
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

/**
 * @author Alejandro Tardín
 */
public abstract class BaseAssetAutoTaggerTestCase {

	public static final String ASSET_TAG_NAME_AUTO = "auto tag";

	public static final String ASSET_TAG_NAME_MANUAL = "manual tag";

	@Before
	public void setUp() throws Exception {
		ServiceTestUtil.setUser(TestPropsValues.getUser());

		company = CompanyTestUtil.addCompany();

		group = GroupTestUtil.addGroup();

		Registry registry = RegistryUtil.getRegistry();

		Map<String, Object> properties = new HashMap<>();

		properties.put("model.class.name", DLFileEntryConstants.getClassName());

		_assetAutoTagProviderServiceRegistration = registry.registerService(
			AssetAutoTagProvider.class,
			model -> Arrays.asList(ASSET_TAG_NAME_AUTO), properties);
	}

	@After
	public void tearDown() {
		_assetAutoTagProviderServiceRegistration.unregister();
	}

	protected AssetEntry addFileEntryAssetEntry(ServiceContext serviceContext)
		throws PortalException {

		FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
			group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), ContentTypes.TEXT_PLAIN,
			RandomTestUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), new byte[0], serviceContext);

		return AssetEntryLocalServiceUtil.getEntry(
			DLFileEntryConstants.getClassName(), fileEntry.getFileEntryId());
	}

	protected void applyAssetTagName(AssetEntry assetEntry, String assetTagName)
		throws PortalException {

		AssetEntryLocalServiceUtil.updateEntry(
			assetEntry.getUserId(), assetEntry.getGroupId(),
			assetEntry.getClassName(), assetEntry.getClassPK(),
			assetEntry.getCategoryIds(),
			ArrayUtil.append(assetEntry.getTagNames(), assetTagName));
	}

	protected void assertContainsAssetTagName(
		AssetEntry assetEntry, String assetTagName) {

		for (AssetTag assetTag : assetEntry.getTags()) {
			if (StringUtil.equals(assetTag.getName(), assetTagName)) {
				return;
			}
		}

		throw new AssertionError(
			String.format(
				"The asset entry was not tagged with \"%s\"", assetTagName));
	}

	protected void assertDoesNotContainAssetTagName(
		AssetEntry assetEntry, String assetTagName) {

		for (AssetTag assetTag : assetEntry.getTags()) {
			if (StringUtil.equals(assetTag.getName(), assetTagName)) {
				throw new AssertionError(
					String.format(
						"The asset entry was tagged with \"%s\"",
						assetTagName));
			}
		}
	}

	protected void assertHasNoTags(AssetEntry assetEntry) {
		List<AssetTag> tags = assetEntry.getTags();

		Assert.assertEquals(tags.toString(), 0, tags.size());
	}

	protected void withAutoTaggerDisabled(
			UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		_withAutoTagger(false, unsafeRunnable);
	}

	protected void withAutoTaggerEnabled(
			UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		_withAutoTagger(true, unsafeRunnable);
	}

	@DeleteAfterTestRun
	protected Company company;

	protected Group group;

	private void _withAutoTagger(
			boolean enabled, UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		Dictionary<String, Object> dictionary = new HashMapDictionary<>();

		dictionary.put("enabled", enabled);

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.asset.auto.tagger.internal.configuration." +
						"AssetAutoTaggerSystemConfiguration",
					dictionary)) {

			unsafeRunnable.run();
		}
	}

	private ServiceRegistration<AssetAutoTagProvider>
		_assetAutoTagProviderServiceRegistration;

}