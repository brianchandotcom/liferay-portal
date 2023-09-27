/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.rules.engine;

import java.io.Serializable;

/**
 * @author Michael C. Han
 */
public class Fact<T> implements Serializable {

	public Fact(String identifier, T factObject) {
		_identifier = identifier;
		_factObject = factObject;
	}

	public T getFactObject() {
		return _factObject;
	}

	public String getIdentifier() {
		return _identifier;
	}

	private final T _factObject;
	private final String _identifier;

}