/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.connection;

/**
 * @author André de Oliveira
 */
public class Index {

	public Index(IndexName indexName) {
		_indexName = indexName;
	}

	public String getName() {
		return _indexName.getName();
	}

	private final IndexName _indexName;

}