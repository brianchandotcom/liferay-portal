/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.module.service.Snapshot;

import java.util.List;

/**
 * @author Michael C. Han
 */
public class RelatedEntryIndexerRegistryUtil {

	public static List<RelatedEntryIndexer> getRelatedEntryIndexers() {
		RelatedEntryIndexerRegistry relatedEntryIndexerRegistry =
			_relatedEntryIndexerRegistrySnapshot.get();

		return relatedEntryIndexerRegistry.getRelatedEntryIndexers();
	}

	public static List<RelatedEntryIndexer> getRelatedEntryIndexers(
		Class<?> clazz) {

		RelatedEntryIndexerRegistry relatedEntryIndexerRegistry =
			_relatedEntryIndexerRegistrySnapshot.get();

		return relatedEntryIndexerRegistry.getRelatedEntryIndexers(clazz);
	}

	public static List<RelatedEntryIndexer> getRelatedEntryIndexers(
		String className) {

		RelatedEntryIndexerRegistry relatedEntryIndexerRegistry =
			_relatedEntryIndexerRegistrySnapshot.get();

		return relatedEntryIndexerRegistry.getRelatedEntryIndexers(className);
	}

	private static final Snapshot<RelatedEntryIndexerRegistry>
		_relatedEntryIndexerRegistrySnapshot = new Snapshot<>(
			RelatedEntryIndexerRegistryUtil.class,
			RelatedEntryIndexerRegistry.class);

}