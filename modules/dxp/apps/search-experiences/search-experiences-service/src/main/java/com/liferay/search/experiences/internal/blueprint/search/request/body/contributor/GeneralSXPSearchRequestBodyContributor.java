/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.blueprint.search.request.body.contributor;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.search.experiences.internal.blueprint.parameter.SXPParameterData;
import com.liferay.search.experiences.rest.dto.v1_0.Configuration;
import com.liferay.search.experiences.rest.dto.v1_0.GeneralConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author André de Oliveira
 */
public class GeneralSXPSearchRequestBodyContributor
	implements SXPSearchRequestBodyContributor {

	@Override
	public void contribute(
		Configuration configuration, SearchRequestBuilder searchRequestBuilder,
		SXPParameterData sxpParameterData) {

		GeneralConfiguration generalConfiguration =
			configuration.getGeneralConfiguration();

		if (generalConfiguration == null) {
			return;
		}

		if (ArrayUtil.isNotEmpty(
				generalConfiguration.getClauseContributorsExcludes())) {

			searchRequestBuilder.withSearchContext(
				searchContext -> searchContext.setAttribute(
					"search.full.query.clause.contributors.excludes",
					StringUtil.merge(
						generalConfiguration.getClauseContributorsExcludes())));
		}

		if (ArrayUtil.isNotEmpty(
				generalConfiguration.getClauseContributorsIncludes())) {

			searchRequestBuilder.withSearchContext(
				searchContext -> searchContext.setAttribute(
					"search.full.query.clause.contributors.includes",
					StringUtil.merge(
						generalConfiguration.getClauseContributorsIncludes())));
		}

		if (generalConfiguration.getEmptySearchEnabled() != null) {
			searchRequestBuilder.emptySearchEnabled(
				generalConfiguration.getEmptySearchEnabled());
		}

		if (generalConfiguration.getExplain() != null) {
			searchRequestBuilder.explain(generalConfiguration.getExplain());
		}

		if (generalConfiguration.getIncludeResponseString() != null) {
			searchRequestBuilder.includeResponseString(
				generalConfiguration.getIncludeResponseString());
		}

		if (!Validator.isBlank(generalConfiguration.getQueryString())) {
			searchRequestBuilder.queryString(
				generalConfiguration.getQueryString());
		}

		if (ArrayUtil.isNotEmpty(
				generalConfiguration.getSearchableAssetTypes())) {

			String[] searchableAssetTypes =
				generalConfiguration.getSearchableAssetTypes();

			Set<String> assetTypeNameSet = new HashSet<>();

			for (String searchableAssetType : searchableAssetTypes) {
				String[] assetTypeIdentifier = StringUtil.split(
					searchableAssetType, "&&");

				assetTypeNameSet.add(assetTypeIdentifier[0]);
			}

			String[] assetTypeNames = assetTypeNameSet.toArray(new String[0]);

			searchRequestBuilder.entryClassNames(assetTypeNames);
			searchRequestBuilder.modelIndexerClassNames(assetTypeNames);

			HashMap<String, List<String[]>> assetSubtypeNameHashMap =
				new HashMap<>();

			for (String searchableAssetType : searchableAssetTypes) {
				String[] assetTypeIdentifier = StringUtil.split(
					searchableAssetType, "&&");

				if (assetTypeIdentifier.length <= 1) {
					continue;
				}

				String assetTypeKey = assetTypeIdentifier[0];

				List<String[]> searchableAssetSubtypeIdentifiers;

				if (assetSubtypeNameHashMap.containsKey(assetTypeKey)) {
					searchableAssetSubtypeIdentifiers =
						assetSubtypeNameHashMap.get(assetTypeKey);

					searchableAssetSubtypeIdentifiers.add(assetTypeIdentifier);
				}
				else {
					searchableAssetSubtypeIdentifiers = new ArrayList<>();

					searchableAssetSubtypeIdentifiers.add(assetTypeIdentifier);
				}

				assetSubtypeNameHashMap.put(
					assetTypeKey, searchableAssetSubtypeIdentifiers);
			}

			searchRequestBuilder.withSearchContext(
				searchContext -> searchContext.setAttribute(
					"searchableAssetSubtypesMap", assetSubtypeNameHashMap));
		}

		if (!Validator.isBlank(generalConfiguration.getLanguageId())) {
			searchRequestBuilder.locale(
				LocaleUtil.fromLanguageId(
					generalConfiguration.getLanguageId()));
		}

		if (!Validator.isBlank(generalConfiguration.getTimeZoneId())) {
			searchRequestBuilder.withSearchContext(
				searchContext -> searchContext.setTimeZone(
					TimeZoneUtil.getTimeZone(
						generalConfiguration.getTimeZoneId())));
		}
	}

	@Override
	public String getName() {
		return "generalConfiguration";
	}

}