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

import com.liferay.portal.kernel.xml.simple.Element;
import com.liferay.sharepoint.soap.connector.schema.BaseNode;

/**
 * @author Iván Zaera
 */
public class QueryFieldsList extends BaseNode {

	public QueryFieldsList(QueryField... queryFields) {
		if (queryFields == null) {
			_queryFields = _EMPTY_QUERY_FIELDS;
		}
		else {
			_queryFields = queryFields;
		}
	}

	@Override
	protected String getNodeName() {
		return "ViewFields";
	}

	@Override
	protected void populate(Element element) {
		super.populate(element);

		for (QueryField queryField : _queryFields) {
			queryField.attach(element);
		}
	}

	private static final QueryField[] _EMPTY_QUERY_FIELDS = new QueryField[0];

	private final QueryField[] _queryFields;

}