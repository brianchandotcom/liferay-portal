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

package com.liferay.sharepoint.connector.schema.query.join;

import com.liferay.portal.kernel.xml.simple.Element;
import com.liferay.sharepoint.connector.schema.BaseNode;
import com.liferay.sharepoint.connector.schema.query.QueryClause;

/**
 * @author Iván Zaera
 */
public abstract class BaseJoin extends BaseNode implements QueryClause {

	public BaseJoin(QueryClause leftQueryClause, QueryClause rightQueryClause) {
		_leftQueryClause = leftQueryClause;
		_rightQueryClause = rightQueryClause;
	}

	public QueryClause getLeftQueryClause() {
		return _leftQueryClause;
	}

	public QueryClause getRightQueryClause() {
		return _rightQueryClause;
	}

	@Override
	protected void populate(Element element) {
		_leftQueryClause.attach(element);
		_rightQueryClause.attach(element);
	}

	private final QueryClause _leftQueryClause;
	private final QueryClause _rightQueryClause;

}