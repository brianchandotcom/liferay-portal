/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	@Override
	public List<String> getDocumentJSONs() {
		return Collections.unmodifiableList(_documentJSONs);
	}

	@Override
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