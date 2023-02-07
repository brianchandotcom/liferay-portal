/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
	public List<KeywordQueryContributor> filterKeywordQueryContributor(
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