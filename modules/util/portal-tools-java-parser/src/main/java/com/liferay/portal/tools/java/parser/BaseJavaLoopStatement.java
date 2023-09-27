/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.java.parser;

import com.liferay.petra.string.StringBundler;

/**
 * @author Hugo Huijser
 */
public abstract class BaseJavaLoopStatement
	extends BaseJavaTerm implements JavaLoopStatement {

	@Override
	public String getLabelName() {
		return _labelName;
	}

	@Override
	public void setLabelName(String labelName) {
		_labelName = labelName;
	}

	protected StringBundler appendLabelName(String indent) {
		StringBundler sb = new StringBundler(3);

		if (_labelName != null) {
			sb.append(indent);
			sb.append(_labelName);
			sb.append(":");
		}

		return sb;
	}

	private String _labelName;

}