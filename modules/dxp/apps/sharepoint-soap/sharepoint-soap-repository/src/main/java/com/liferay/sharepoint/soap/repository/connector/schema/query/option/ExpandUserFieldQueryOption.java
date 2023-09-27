/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.soap.repository.connector.schema.query.option;

import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Adorjan Nagy
 */
public class ExpandUserFieldQueryOption extends BaseQueryOption {

	public ExpandUserFieldQueryOption(boolean expand) {
		_expand = expand;
	}

	@Override
	protected String getNodeName() {
		return "ExpandUserField";
	}

	@Override
	protected String getNodeText() {
		return StringUtil.toUpperCase(String.valueOf(_expand));
	}

	private final boolean _expand;

}