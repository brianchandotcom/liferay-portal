/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.soap.repository.connector.schema.query;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.xml.simple.Element;
import com.liferay.sharepoint.soap.repository.connector.schema.BaseNode;
import com.liferay.sharepoint.soap.repository.connector.schema.query.option.BaseQueryOption;

/**
 * @author Iván Zaera
 */
public class QueryOptionsList extends BaseNode {

	public QueryOptionsList(BaseQueryOption... baseQueryOptions) {
		if (baseQueryOptions == null) {
			_baseQueryOptions = _EMPTY_BASE_QUERY_OPTIONS;
		}
		else {
			_baseQueryOptions = baseQueryOptions;
		}
	}

	public QueryOptionsList append(BaseQueryOption... baseQueryOptions) {
		return new QueryOptionsList(
			ArrayUtil.append(_baseQueryOptions, baseQueryOptions));
	}

	public boolean contains(
		Class<? extends BaseQueryOption> baseQueryOptionClass) {

		for (BaseQueryOption baseQueryOption : _baseQueryOptions) {
			if (baseQueryOption.getClass() == baseQueryOptionClass) {
				return true;
			}
		}

		return false;
	}

	@Override
	protected String getNodeName() {
		return "QueryOptions";
	}

	@Override
	protected void populate(Element element) {
		super.populate(element);

		for (BaseQueryOption baseQueryOption : _baseQueryOptions) {
			baseQueryOption.attach(element);
		}
	}

	private static final BaseQueryOption[] _EMPTY_BASE_QUERY_OPTIONS =
		new BaseQueryOption[0];

	private final BaseQueryOption[] _baseQueryOptions;

}