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

package com.liferay.sharepoint.connector.schema.query.operator;

import com.liferay.sharepoint.connector.schema.query.QueryField;

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