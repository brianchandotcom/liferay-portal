/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.index;

import java.util.Date;
import java.util.Set;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Bryan Engler
 */
@ProviderType
public interface SyncReindexManager {

	public void deleteStaleDocuments(
		long companyId, Date date, Set<String> classNames);

	public void deleteStaleDocuments(
		String indexName, Date date, Set<String> classNames);

}