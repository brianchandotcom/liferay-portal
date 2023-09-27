/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.index;

import org.elasticsearch.client.IndicesClient;

/**
 * @author Michael C. Han
 */
public interface IndexFactory {

	public void createIndices(IndicesClient indicesClient, long companyId);

	public void deleteIndices(IndicesClient indicesClient, long companyId);

	public void registerCompanyId(long companyId);

	public void unregisterCompanyId(long companyId);

}