/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.soap.repository.connector.schema.query.operator;

import com.liferay.sharepoint.soap.repository.connector.schema.query.QueryField;
import com.liferay.sharepoint.soap.repository.connector.schema.query.QueryValue;

/**
 * @author Iván Zaera
 */
public class NotIncludesOperator extends BaseSingleValueOperator {

	public NotIncludesOperator(QueryField queryField, QueryValue queryValue) {
		super(queryField, queryValue);
	}

	@Override
	protected String getNodeName() {
		return "NotIncludes";
	}

}