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

import javax.management.MBeanServer;
import javax.management.ObjectName;

/**
 * @author Riccardo Ferrari
 */
public class ObjectNameAttributeMapKeyStrategy implements MapKeyStrategy {

	public ObjectNameAttributeMapKeyStrategy(
		MBeanServer mBeanServer, String attributeName) {

		_mBeanServer = mBeanServer;
		_attributeName = attributeName;
	}

	@Override
	public String getMapKey(ObjectName objectName) {
		String mapKey = null;

		try {
			mapKey = String.valueOf(
				_mBeanServer.getAttribute(objectName, _attributeName));
		}
		catch (Exception e) {
			mapKey = objectName.getCanonicalName();
		}

		return mapKey;
	}

	private final String _attributeName;
	private final MBeanServer _mBeanServer;

}