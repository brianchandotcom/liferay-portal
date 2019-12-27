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

package com.liferay.talend.internal.oas;

import com.liferay.talend.common.oas.OASSource;

import javax.json.JsonObject;

import org.talend.components.api.properties.ComponentProperties;
import org.talend.daikon.properties.ValidationResult;

/**
 * @author Igor Beslic
 */
public class LiferayOASSource implements OASSource {

	public LiferayOASSource(
		OASSource oasSource, ValidationResult validationResult) {

		_oasSource = oasSource;
		_validationResult = validationResult;
	}

	@Override
	public JsonObject getOASJsonObject() {
		throw new UnsupportedOperationException(
			"Unable to resolve OAS without URL");
	}

	@Override
	public JsonObject getOASJsonObject(String oasUrl) {
		return _oasSource.getOASJsonObject(oasUrl);
	}

	public ValidationResult getValidationResult() {
		return _validationResult;
	}

	@Override
	public ValidationResult initialize(
		ComponentProperties componentProperties) {

		throw new UnsupportedOperationException(
			"OAS source object has been already initialized");
	}

	public boolean isValid() {
		if (_validationResult.getStatus() == ValidationResult.Result.ERROR) {
			return false;
		}

		return true;
	}

	private final OASSource _oasSource;
	private final ValidationResult _validationResult;

}