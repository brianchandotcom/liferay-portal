/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.facet.folder;

import com.liferay.portal.search.searcher.SearchRequestBuilder;

import java.util.function.Consumer;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author André de Oliveira
 */
@ProviderType
public interface FolderFacetSearchContributor {

	public void contribute(
		SearchRequestBuilder searchRequestBuilder,
		Consumer<FolderFacetBuilder> folderFacetBuilderConsumer);

	@ProviderType
	public interface FolderFacetBuilder {

		public FolderFacetBuilder aggregationName(String aggregationName);

		public FolderFacetBuilder frequencyThreshold(int frequencyThreshold);

		public FolderFacetBuilder maxTerms(int maxTerms);

		public FolderFacetBuilder selectedFolderIds(long... selectedFolderIds);

	}

}