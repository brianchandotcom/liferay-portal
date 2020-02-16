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