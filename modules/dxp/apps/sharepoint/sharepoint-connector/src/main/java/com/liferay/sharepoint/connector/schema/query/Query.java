/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.sharepoint.connector.schema.query;

import com.liferay.portal.kernel.xml.simple.Element;
import com.liferay.sharepoint.connector.schema.BaseNode;

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