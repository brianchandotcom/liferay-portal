/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.soap.repository.connector.schema.query.operator;

import com.liferay.portal.kernel.xml.simple.Element;
import com.liferay.sharepoint.soap.repository.connector.schema.BaseNode;
import com.liferay.sharepoint.soap.repository.connector.schema.query.QueryClause;
import com.liferay.sharepoint.soap.repository.connector.schema.query.QueryField;

/**
 * @author Iván Zaera
 */
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