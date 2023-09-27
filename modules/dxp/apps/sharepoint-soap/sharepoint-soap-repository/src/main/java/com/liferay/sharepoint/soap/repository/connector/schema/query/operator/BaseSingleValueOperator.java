/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.soap.repository.connector.schema.query.operator;

import com.liferay.portal.kernel.xml.simple.Element;
import com.liferay.sharepoint.soap.repository.connector.schema.query.QueryField;
import com.liferay.sharepoint.soap.repository.connector.schema.query.QueryValue;

/**
 * @author Iván Zaera
 */
public abstract class BaseSingleValueOperator extends BaseOperator {

	public BaseSingleValueOperator(
		QueryField queryField, QueryValue queryValue) {

		super(queryField);

		_queryValue = queryValue;
	}

	public QueryValue getQueryValue() {
		return _queryValue;
	}

	@Override
	protected void populate(Element element) {
		super.populate(element);

		_queryValue.attach(element);
	}

	private final QueryValue _queryValue;

}