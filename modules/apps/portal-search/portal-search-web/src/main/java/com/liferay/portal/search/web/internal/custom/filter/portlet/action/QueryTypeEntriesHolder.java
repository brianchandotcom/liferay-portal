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

package com.liferay.portal.search.web.internal.custom.filter.portlet.action;

import java.util.ArrayList;
import java.util.List;

/**
 * @author André de Oliveira
 */
public class QueryTypeEntriesHolder {

	public QueryTypeEntriesHolder() {
		add("bool", "Bool");
		add("exists", "Exists");
		add("fuzzy", "Fuzzy");
		add("match", "Match");
		add("match_phrase", "Match Phrase");
		add("match_phrase_prefix", "Match Phrase Prefix");
		add("multi_match", "Multi Match");
		add("prefix", "Prefix");
		add("query_string", "Query String");
		add("regexp", "Regexp");
		add("script", "Script");
		add("simple_query_string", "Simple Query String");
		add("term", "Term");
		add("wildcard", "Wildcard");
	}

	public List<QueryTypeEntry> getQueryTypeEntries() {
		return _queryTypeEntries;
	}

	protected void add(String typeId, String name) {
		_queryTypeEntries.add(
			new QueryTypeEntry() {
				{
					setName(name);
					setTypeId(typeId);
				}
			});
	}

	private final List<QueryTypeEntry> _queryTypeEntries = new ArrayList<>();

}