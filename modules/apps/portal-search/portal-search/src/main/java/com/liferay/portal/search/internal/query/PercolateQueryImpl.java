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

package com.liferay.portal.search.internal.query;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.search.query.PercolateQuery;
import com.liferay.portal.search.query.QueryVisitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Provides support for parsing raw, human readable query syntax. No
 * transformation is made on user input.
 *
 * <p>
 * The actual query syntax and any further processing are dependent on your
 * search engine's implementation details. Consult your search provider's
 * documentation for more information.
 * </p>
 *
 * @author Michael C. Han
 */
public class PercolateQueryImpl
	extends BaseQueryImpl implements PercolateQuery {

	public PercolateQueryImpl(String field, List<String> documentJSONs) {
		_field = field;

		_documentJSONs.addAll(documentJSONs);
	}

	public PercolateQueryImpl(String field, String documentJSON) {
		_field = field;

		_documentJSONs.add(documentJSON);
	}

	public PercolateQueryImpl(String field, String... documentJSONs) {
		_field = field;

		Collections.addAll(_documentJSONs, documentJSONs);
	}

	@Override
	public <T> T accept(QueryVisitor<T> queryVisitor) {
		return queryVisitor.visit(this);
	}

	public List<String> getDocumentJSONs() {
		return Collections.unmodifiableList(_documentJSONs);
	}

	public String getField() {
		return _field;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("{className=");

		Class<?> clazz = getClass();

		sb.append(clazz.getSimpleName());

		sb.append(", field=");
		sb.append(_field);
		sb.append("}");

		return sb.toString();
	}

	private static final long serialVersionUID = 1L;

	private final List<String> _documentJSONs = new ArrayList<>();
	private final String _field;

}