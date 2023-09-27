/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search;

/**
 * @author Adolfo Pérez
 */
public class RelatedSearchResult<T> {

	public RelatedSearchResult(T model, Summary summary) {
		_model = model;
		_summary = summary;
	}

	public T getModel() {
		return _model;
	}

	public Summary getSummary() {
		return _summary;
	}

	private final T _model;
	private final Summary _summary;

}