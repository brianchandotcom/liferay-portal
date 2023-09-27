/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.query;

import com.liferay.portal.search.query.QueryVisitor;
import com.liferay.portal.search.query.WrapperQuery;

import java.nio.charset.StandardCharsets;

/**
 * @author Michael C. Han
 */
public class WrapperQueryImpl extends BaseQueryImpl implements WrapperQuery {

	public WrapperQueryImpl(byte[] source) {
		_source = source;
	}

	public WrapperQueryImpl(String source) {
		_source = source.getBytes(StandardCharsets.UTF_8);
	}

	@Override
	public <T> T accept(QueryVisitor<T> queryVisitor) {
		return queryVisitor.visit(this);
	}

	@Override
	public byte[] getSource() {
		return _source;
	}

	public void setSource(byte[] source) {
		_source = source;
	}

	@Override
	public String toString() {
		return new String(_source);
	}

	private static final long serialVersionUID = 1L;

	private byte[] _source;

}