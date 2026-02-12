/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.query;

import java.nio.charset.StandardCharsets;

/**
 * @author Adam Brandizzi
 */
public class WrapperQuery extends Query {

	public WrapperQuery(byte[] source) {
		_source = source;
	}

	public WrapperQuery(String source) {
		_source = source.getBytes(StandardCharsets.UTF_8);
	}

	@Override
	public <T> T accept(QueryVisitor<T> queryVisitor) {
		return queryVisitor.visit(this);
	}

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