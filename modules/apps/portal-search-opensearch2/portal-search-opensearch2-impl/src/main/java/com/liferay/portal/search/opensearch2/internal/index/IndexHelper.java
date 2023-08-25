/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.index;

import com.liferay.portal.search.spi.index.listener.CompanyIndexListener;

import java.util.List;

import org.opensearch.client.opensearch.indices.OpenSearchIndicesClient;

/**
 * @author Petteri Karttunen
 */
public interface IndexHelper {

	public void createIndex(
		String indexName, OpenSearchIndicesClient openSearchIndicesClient);

	public void deleteIndex(
		long companyId, String indexName,
		OpenSearchIndicesClient openSearchIndicesClient,
		boolean resetBothIndexNames);

	public List<CompanyIndexListener> getCompanyIndexListeners();

	public String getIndexName(long companyId);

	public boolean hasIndex(
		String indexName, OpenSearchIndicesClient openSearchIndicesClient);

	public void updateMaxResultWindow();

}