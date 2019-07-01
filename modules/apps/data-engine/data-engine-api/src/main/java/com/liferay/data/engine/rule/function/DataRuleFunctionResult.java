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

package com.liferay.data.engine.rule.function;

import com.liferay.data.engine.spi.dto.SPIDataDefinitionField;

/**
 * @author Jeyvison Nascimento
 */
public class DataRuleFunctionResult {

	public static DataRuleFunctionResult of(
		SPIDataDefinitionField spiDataDefinitionField, String errorCode) {

		return new DataRuleFunctionResult(spiDataDefinitionField, errorCode);
	}

	public String getErrorCode() {
		return _errorCode;
	}

	public SPIDataDefinitionField getSPIDataDefinitionField() {
		return _spiDataDefinitionField;
	}

	public boolean isValid() {
		return _valid;
	}

	public void setValid(boolean valid) {
		_valid = valid;

		if (valid) {
			_errorCode = null;
		}
	}

	private DataRuleFunctionResult(
		SPIDataDefinitionField spiDataDefinitionField, String errorCode) {

		_spiDataDefinitionField = spiDataDefinitionField;
		_errorCode = errorCode;
	}

	private String _errorCode;
	private final SPIDataDefinitionField _spiDataDefinitionField;
	private boolean _valid;

}