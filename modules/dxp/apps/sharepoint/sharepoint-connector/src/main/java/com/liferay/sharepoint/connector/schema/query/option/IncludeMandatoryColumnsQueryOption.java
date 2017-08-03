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

package com.liferay.sharepoint.connector.schema.query.option;

import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Iván Zaera
 */
public class IncludeMandatoryColumnsQueryOption extends BaseQueryOption {

	public IncludeMandatoryColumnsQueryOption(boolean include) {
		_include = include;
	}

	@Override
	protected String getNodeName() {
		return "IncludeMandatoryColumns";
	}

	@Override
	protected String getNodeText() {
		return StringUtil.toUpperCase(String.valueOf(_include));
	}

	private final boolean _include;

}