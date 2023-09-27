/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.definition;

import com.liferay.portal.workflow.kaleo.definition.exception.KaleoDefinitionValidationException;

/**
 * @author Rafael Praxedes
 */
public class UpdateStatusAction extends Action {

	public UpdateStatusAction(
			String name, String description, String executionType, int status,
			int priority)
		throws KaleoDefinitionValidationException {

		super(
			ActionType.UPDATE_STATUS, name, description, executionType,
			priority);

		_status = status;
	}

	public int getStatus() {
		return _status;
	}

	private final int _status;

}