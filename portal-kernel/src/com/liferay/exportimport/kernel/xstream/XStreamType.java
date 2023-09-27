/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.kernel.xstream;

/**
 * @author Máté Thurzó
 */
public class XStreamType {

	public XStreamType(Class<?> clazz) {
		this(clazz.getName());
	}

	public XStreamType(String typeExpression) {
		_typeExpression = typeExpression;
	}

	public String getTypeExpression() {
		return _typeExpression;
	}

	private final String _typeExpression;

}