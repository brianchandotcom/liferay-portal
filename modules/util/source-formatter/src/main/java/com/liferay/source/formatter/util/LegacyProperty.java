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

package com.liferay.source.formatter.util;

/**
 * @author Hugo Huijser
 */
public class LegacyProperty {

	public LegacyProperty(String name, String variableName) {
		_name = name;
		_variableName = variableName;

		if (variableName.startsWith("_MIGRATED")) {
			_legacyPropertyAction = LegacyPropertyAction.MIGRATED;
		}
		else if (variableName.startsWith("_MODULARIZED")) {
			_legacyPropertyAction = LegacyPropertyAction.MODULARIZED;
		}
		else if (variableName.startsWith("_OBSOLETE")) {
			_legacyPropertyAction = LegacyPropertyAction.OBSOLETE;
		}
		else {
			_legacyPropertyAction = LegacyPropertyAction.RENAMED;
		}

		if (variableName.contains("_PORTAL_")) {
			_legacyPropertyType = LegacyPropertyType.PORTAL;
		}
		else {
			_legacyPropertyType = LegacyPropertyType.SYSTEM;
		}
	}

	public LegacyPropertyAction getLegacyPropertyAction() {
		return _legacyPropertyAction;
	}

	public LegacyPropertyType getLegacyPropertyType() {
		return _legacyPropertyType;
	}

	public String getName() {
		return _name;
	}

	public String getVariableName() {
		return _variableName;
	}

	private final LegacyPropertyAction _legacyPropertyAction;
	private final LegacyPropertyType _legacyPropertyType;
	private final String _name;
	private final String _variableName;

}