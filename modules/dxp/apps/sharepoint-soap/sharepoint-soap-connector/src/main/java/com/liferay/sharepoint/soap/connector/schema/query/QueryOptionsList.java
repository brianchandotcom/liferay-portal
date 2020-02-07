/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.sharepoint.soap.connector.schema.query;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.xml.simple.Element;
import com.liferay.sharepoint.soap.connector.schema.BaseNode;
import com.liferay.sharepoint.soap.connector.schema.query.option.BaseQueryOption;

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