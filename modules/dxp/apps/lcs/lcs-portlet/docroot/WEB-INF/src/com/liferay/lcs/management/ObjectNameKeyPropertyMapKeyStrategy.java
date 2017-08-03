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

package com.liferay.lcs.management;

import javax.management.ObjectName;

/**
 * @author Riccardo Ferrari
 */
public class ObjectNameKeyPropertyMapKeyStrategy implements MapKeyStrategy {

	public ObjectNameKeyPropertyMapKeyStrategy(String keyPropertyName) {
		_keyPropertyName = keyPropertyName;
	}

	@Override
	public String getMapKey(ObjectName objectName) {
		return objectName.getKeyProperty(_keyPropertyName);
	}

	private final String _keyPropertyName;

}