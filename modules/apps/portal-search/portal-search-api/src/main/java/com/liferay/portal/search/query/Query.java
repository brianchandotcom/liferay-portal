/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.query;

import java.io.Serializable;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public abstract class Query implements Serializable {

	public abstract <T> T accept(QueryVisitor<T> queryVisitor);

	public Float getBoost() {
		return _boost;
	}

	public String getQueryName() {
		return _queryName;
	}

	public void setBoost(Float boost) {
		_boost = boost;
	}

	public void setQueryName(String queryName) {
		_queryName = queryName;
	}

	private static final long serialVersionUID = 1L;

	private Float _boost;
	private String _queryName;

}