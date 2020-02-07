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
public class Query extends BaseNode {

	public Query(QueryClause queryClause) {
		_queryClause = queryClause;
	}

	public QueryClause getQueryClause() {
		return _queryClause;
	}

	@Override
	protected String getNodeName() {
		return "Query";
	}

	@Override
	protected void populate(Element element) {
		super.populate(element);

		Element whereElement = element.addElement("Where");

		if (_queryClause != null) {
			_queryClause.attach(whereElement);
		}
	}

	private final QueryClause _queryClause;

}