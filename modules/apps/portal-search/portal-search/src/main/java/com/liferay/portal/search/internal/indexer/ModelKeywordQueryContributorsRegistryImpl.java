/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.indexer;

import com.liferay.portal.search.spi.model.query.contributor.KeywordQueryContributor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author André de Oliveira
 */
public class ModelKeywordQueryContributorsRegistryImpl
	implements ModelKeywordQueryContributorsRegistry {

	public ModelKeywordQueryContributorsRegistryImpl(
		Iterable<KeywordQueryContributor> keywordQueryContributors) {

		_keywordQueryContributors = keywordQueryContributors;
	}

	@Override
	public List<KeywordQueryContributor> filterKeywordQueryContributors(
		Collection<String> excludes, Collection<String> includes) {

		List<KeywordQueryContributor> keywordQueryContributors =
			new ArrayList<>();

		_keywordQueryContributors.forEach(keywordQueryContributors::add);

		return IncludeExcludeUtil.filter(
			keywordQueryContributors, includes, excludes,
			object -> getClassName(object));
	}

	protected String getClassName(Object object) {
		Class<?> clazz = object.getClass();

		return clazz.getName();
	}

	private final Iterable<KeywordQueryContributor> _keywordQueryContributors;

}