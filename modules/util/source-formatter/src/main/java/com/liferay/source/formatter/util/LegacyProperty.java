/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.util;

/**
 * @author Hugo Huijser
 */
public class LegacyProperty {

	public LegacyProperty(
		String legacyPropertyName, String moduleName, String newPropertyName,
		String variableName) {

		_legacyPropertyName = legacyPropertyName;
		_moduleName = moduleName;
		_newPropertyName = newPropertyName;
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

	public String getLegacyPropertyName() {
		return _legacyPropertyName;
	}

	public LegacyPropertyType getLegacyPropertyType() {
		return _legacyPropertyType;
	}

	public String getModuleName() {
		return _moduleName;
	}

	public String getNewPropertyName() {
		return _newPropertyName;
	}

	public String getVariableName() {
		return _variableName;
	}

	private final LegacyPropertyAction _legacyPropertyAction;
	private final String _legacyPropertyName;
	private final LegacyPropertyType _legacyPropertyType;
	private final String _moduleName;
	private final String _newPropertyName;
	private final String _variableName;

}