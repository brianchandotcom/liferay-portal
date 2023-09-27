/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.kernel.xstream;

/**
 * @author Máté Thurzó
 */
public class XStreamAlias {

	public XStreamAlias(Class<?> clazz, String name) {
		_clazz = clazz;
		_name = name;
	}

	public Class<?> getClazz() {
		return _clazz;
	}

	public String getName() {
		return _name;
	}

	private final Class<?> _clazz;
	private final String _name;

}