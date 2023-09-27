/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.rss.internal.model;

import com.liferay.rss.model.SyndContent;

/**
 * @author Shuyang Zhou
 */
public class SyndContentImpl implements SyndContent {

	@Override
	public String getType() {
		return _type;
	}

	@Override
	public String getValue() {
		return _value;
	}

	@Override
	public void setType(String type) {
		_type = type;
	}

	@Override
	public void setValue(String value) {
		_value = value;
	}

	private String _type;
	private String _value;

}