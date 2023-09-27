/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.custom.filter.portlet.action;

/**
 * @author André de Oliveira
 */
public class ConfigurationDisplayContext {

	public OccurEntriesHolder getOccurEntriesHolder() {
		return _occurEntriesHolder;
	}

	public QueryTypeEntriesHolder getQueryTypeEntriesHolder() {
		return _queryTypeEntriesHolder;
	}

	private final OccurEntriesHolder _occurEntriesHolder =
		new OccurEntriesHolder();
	private final QueryTypeEntriesHolder _queryTypeEntriesHolder =
		new QueryTypeEntriesHolder();

}