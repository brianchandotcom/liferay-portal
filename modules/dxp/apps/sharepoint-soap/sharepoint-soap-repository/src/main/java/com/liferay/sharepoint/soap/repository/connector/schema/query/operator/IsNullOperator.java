/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.soap.repository.connector.schema.query.operator;

import com.liferay.sharepoint.soap.repository.connector.schema.query.QueryField;

/**
 * @author Iván Zaera
 */
public class IsNullOperator extends BaseNoValueOperator {

	public IsNullOperator(QueryField queryField) {
		super(queryField);
	}

	@Override
	protected String getNodeName() {
		return "IsNull";
	}

}