/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.test.util.model;

import com.liferay.info.field.InfoField;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lourdes Fernández Besada
 */
public class MockObject {

	public MockObject(long classPK) {
		_classPK = classPK;
	}

	public void addInfoField(InfoField infoField, Object value) {
		_infoFieldsMap.put(infoField, value);
	}

	public long getClassPK() {
		return _classPK;
	}

	public Map<InfoField<?>, Object> getInfoFieldsMap() {
		return _infoFieldsMap;
	}

	private final long _classPK;
	private final Map<InfoField<?>, Object> _infoFieldsMap = new HashMap<>();

}