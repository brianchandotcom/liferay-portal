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