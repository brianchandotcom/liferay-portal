/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.model.impl;

import com.liferay.object.model.ObjectStateTransition;

import java.util.List;

/**
 * @author Marco Leo
 */
public class ObjectStateImpl extends ObjectStateBaseImpl {

	@Override
	public List<ObjectStateTransition> getObjectStateTransitions() {
		return _objectStateTransitions;
	}

	@Override
	public void setObjectStateTransitions(
		List<ObjectStateTransition> objectStateTransitions) {

		_objectStateTransitions = objectStateTransitions;
	}

	private List<ObjectStateTransition> _objectStateTransitions;

}