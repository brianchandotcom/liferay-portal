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

package com.liferay.asset.auto.tagger.test.util;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Dictionary;
import java.util.List;

import org.junit.Assert;

/**
 * @author Alejandro Tardín
 */
public class AssetAutoTaggerTestUtil {

	public static final String ASSET_TAG_NAME_AUTO = "auto tag";

	public static final String ASSET_TAG_NAME_MANUAL = "manual tag";

	public static void assertContainsAssetTagName(
		AssetEntry assetEntry, String assetTagName) {

		for (AssetTag assetTag : assetEntry.getTags()) {
			if (StringUtil.equals(assetTag.getName(), assetTagName)) {
				return;
			}
		}

		throw new AssertionError(
			String.format(
				"The asset entry has not been tagged with '%s'", assetTagName));
	}

	public static void assertDoesNotContainAssetTagName(
		AssetEntry assetEntry, String assetTagName) {

		for (AssetTag assetTag : assetEntry.getTags()) {
			if (StringUtil.equals(assetTag.getName(), assetTagName)) {
				throw new AssertionError(
					String.format(
						"The asset entry has been tagged with '%s'",
						assetTagName));
			}
		}
	}

	public static void assertHasNoTags(AssetEntry assetEntry) {
		List<AssetTag> tags = assetEntry.getTags();

		Assert.assertEquals(tags.toString(), 0, tags.size());
	}

	public static void withAutoTaggerEnabled(UnsafeRunnable unsafeRunnable)
		throws Exception {

		Dictionary<String, Object> dictionary = new HashMapDictionary<>();

		dictionary.put("enabled", true);

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					_CONFIGURATION_PID, dictionary)) {

			unsafeRunnable.run();
		}
	}

	@FunctionalInterface
	public interface UnsafeRunnable {

		public void run() throws Exception;

	}

	private static final String _CONFIGURATION_PID =
		"com.liferay.asset.auto.tagger.internal.configuration." +
			"AssetAutoTaggerSystemConfiguration";

}