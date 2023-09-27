/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.model.impl;

import com.liferay.object.model.ObjectState;

import java.util.List;

/**
 * @author Marco Leo
 */
public class ObjectStateFlowImpl extends ObjectStateFlowBaseImpl {

	@Override
	public List<ObjectState> getObjectStates() {
		return _objectStates;
	}

	@Override
	public void setObjectStates(List<ObjectState> objectStates) {
		_objectStates = objectStates;
	}

	private List<ObjectState> _objectStates;

}