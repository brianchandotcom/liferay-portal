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

package com.liferay.sharepoint.connector.schema.query.operator;

import com.liferay.portal.kernel.xml.simple.Element;
import com.liferay.sharepoint.connector.schema.BaseNode;
import com.liferay.sharepoint.connector.schema.query.QueryClause;
import com.liferay.sharepoint.connector.schema.query.QueryField;

/**
 * @author Iván Zaera
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 */
@Deprecated
public abstract class BaseOperator extends BaseNode implements QueryClause {

	public BaseOperator(QueryField queryField) {
		_queryField = queryField;
	}

	public QueryField getQueryField() {
		return _queryField;
	}

	@Override
	protected void populate(Element element) {
		_queryField.attach(element);
	}

	private final QueryField _queryField;

}