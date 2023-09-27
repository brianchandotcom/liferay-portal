/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.facet.user;

import com.liferay.portal.search.searcher.SearchRequestBuilder;

import java.util.function.Consumer;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author André de Oliveira
 */
@ProviderType
public interface UserFacetSearchContributor {

	public void contribute(
		SearchRequestBuilder searchRequestBuilder,
		Consumer<UserFacetBuilder> userFacetBuilderConsumer);

	@ProviderType
	public interface UserFacetBuilder {

		public UserFacetBuilder aggregationName(String aggregationName);

		public UserFacetBuilder frequencyThreshold(int frequencyThreshold);

		public UserFacetBuilder maxTerms(int maxTerms);

		public UserFacetBuilder selectedUserIds(long... selectedUserIds);

	}

}