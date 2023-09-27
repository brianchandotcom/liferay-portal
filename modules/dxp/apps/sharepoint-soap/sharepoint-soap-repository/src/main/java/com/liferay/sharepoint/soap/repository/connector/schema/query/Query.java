/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.soap.repository.connector.schema.query;

import com.liferay.portal.kernel.xml.simple.Element;
import com.liferay.sharepoint.soap.repository.connector.schema.BaseNode;

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