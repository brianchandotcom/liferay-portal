/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.model.impl;

import com.liferay.object.model.ObjectLayoutRow;

import java.util.Collections;
import java.util.List;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
public class ObjectLayoutBoxImpl extends ObjectLayoutBoxBaseImpl {

	@Override
	public List<ObjectLayoutRow> getObjectLayoutRows() {
		return _objectLayoutRows;
	}

	@Override
	public void setObjectLayoutRows(List<ObjectLayoutRow> objectLayoutRows) {
		_objectLayoutRows = objectLayoutRows;
	}

	private List<ObjectLayoutRow> _objectLayoutRows = Collections.emptyList();

}