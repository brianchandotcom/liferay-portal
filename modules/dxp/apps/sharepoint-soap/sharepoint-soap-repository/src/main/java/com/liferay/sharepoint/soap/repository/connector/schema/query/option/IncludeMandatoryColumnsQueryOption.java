/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.soap.repository.connector.schema.query.option;

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