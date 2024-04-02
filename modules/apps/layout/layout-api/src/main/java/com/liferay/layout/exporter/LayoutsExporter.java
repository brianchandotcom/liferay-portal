/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.exporter;

import java.io.File;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Eudaldo Alonso
 */
@ProviderType
public interface LayoutsExporter {

	public File exportLayoutPageTemplateCollections(
			long[] layoutPageTemplateCollectionIds)
		throws Exception;

	public File exportLayoutPageTemplateEntries(long groupId) throws Exception;

	public File exportLayoutPageTemplateEntries(
			long[] layoutPageTemplateEntryIds, int type)
		throws Exception;

	public File exportLayoutPageTemplateEntriesAndLayoutPageTemplateCollections(
			long[] layoutPageTemplateEntryIds,
			long[] layoutPageTemplateCollectionIds)
		throws Exception;

	public File exportLayoutUtilityPageEntries(long[] layoutUtilityPageEntryIds)
		throws Exception;

}