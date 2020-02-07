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

package com.liferay.sharepoint.soap.connector.schema.query.join;

import com.liferay.portal.kernel.xml.simple.Element;
import com.liferay.sharepoint.soap.connector.schema.BaseNode;
import com.liferay.sharepoint.soap.connector.schema.query.QueryClause;

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