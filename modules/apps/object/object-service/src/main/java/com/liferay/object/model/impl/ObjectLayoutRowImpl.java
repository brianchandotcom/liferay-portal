/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.model.impl;

import com.liferay.object.model.ObjectLayoutColumn;

import java.util.Collections;
import java.util.List;

/**
 * @author Marco Leo
 */
public class ObjectLayoutRowImpl extends ObjectLayoutRowBaseImpl {

	@Override
	public List<ObjectLayoutColumn> getObjectLayoutColumns() {
		return _objectLayoutColumns;
	}

	@Override
	public void setObjectLayoutColumns(
		List<ObjectLayoutColumn> objectLayoutColumns) {

		_objectLayoutColumns = objectLayoutColumns;
	}

	private List<ObjectLayoutColumn> _objectLayoutColumns =
		Collections.emptyList();

}