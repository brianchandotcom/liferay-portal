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

package com.liferay.data.engine.rest.internal.rule;

import com.liferay.data.engine.rest.dto.v1_0.DataDefinitionField;

/**
 * @author Jeyvison Nascimento
 */
public class DataRuleFunctionResult {

	public DataDefinitionField getDataDefinitionField() {
		return dataDefinitionField;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public boolean isValid() {
		return valid;
	}

	public void setDataDefinitionField(
		DataDefinitionField dataDefinitionField) {

		this.dataDefinitionField = dataDefinitionField;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	protected DataDefinitionField dataDefinitionField;
	protected String errorCode;
	protected boolean valid;

}