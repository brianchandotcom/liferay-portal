/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.publisher.test.util;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.petra.string.StringBundler;

import java.util.Iterator;
import java.util.List;

import org.junit.Assert;

/**
 * Provides a utility method to convert an asset entry to XML format so it can
 * be saved in the Asset Publisher's portlet preferences.
 *
 * @author Tamas Molnar
 */
public class AssetPublisherTestUtil {

	public static void assertAssetEntries(
		List<AssetEntry> expectedAssetEntries,
		List<AssetEntry> actualAssetEntries) {

		Assert.assertEquals(
			actualAssetEntries.toString(), expectedAssetEntries.size(),
			actualAssetEntries.size());

		Iterator<AssetEntry> expectedAssetEntriesIterator =
			expectedAssetEntries.iterator();
		Iterator<AssetEntry> actualAssetEntriesIterator =
			actualAssetEntries.iterator();

		while (expectedAssetEntriesIterator.hasNext() &&
			   actualAssetEntriesIterator.hasNext()) {

			AssetEntry expectedAssetEntry = expectedAssetEntriesIterator.next();
			AssetEntry actualAssetEntry = actualAssetEntriesIterator.next();

			Assert.assertEquals(
				expectedAssetEntry.getClassName(),
				actualAssetEntry.getClassName());
			Assert.assertEquals(
				expectedAssetEntry.getClassUuid(),
				actualAssetEntry.getClassUuid());
		}
	}

	public static String getAssetEntryXml(AssetEntry assetEntry) {
		StringBundler sb = new StringBundler(6);

		sb.append("<?xml version=\"1.0\"?><asset-entry>");
		sb.append("<asset-entry-type>");
		sb.append(assetEntry.getClassName());
		sb.append("</asset-entry-type><asset-entry-uuid>");
		sb.append(assetEntry.getClassUuid());
		sb.append("</asset-entry-uuid></asset-entry>");

		return sb.toString();
	}

}