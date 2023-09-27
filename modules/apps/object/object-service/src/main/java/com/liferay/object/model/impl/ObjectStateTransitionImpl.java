/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.model.impl;

/**
 * @author Marco Leo
 */
public class ObjectStateTransitionImpl extends ObjectStateTransitionBaseImpl {

	public long getTargetObjectStateListTypeEntryId() {
		return _targetObjectStateListTypeEntryId;
	}

	public void setTargetObjectStateListTypeEntryId(
		long targetObjectStateListTypeEntryId) {

		_targetObjectStateListTypeEntryId = targetObjectStateListTypeEntryId;
	}

	private long _targetObjectStateListTypeEntryId;

}