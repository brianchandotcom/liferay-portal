/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.index;

import org.opensearch.client.opensearch.indices.OpenSearchIndicesClient;

/**
 * @author Michael C. Han
 */
public interface IndexFactory {

	public boolean deleteIndex(
		long companyId, OpenSearchIndicesClient openSearchIndicesClient);

	public boolean initializeIndex(
		long companyId, OpenSearchIndicesClient openSearchIndicesClient);

	public void registerCompanyId(long companyId);

	public void unregisterCompanyId(long companyId);

}