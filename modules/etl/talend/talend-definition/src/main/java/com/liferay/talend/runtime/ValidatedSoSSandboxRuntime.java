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

package com.liferay.talend.runtime;

import com.liferay.talend.common.json.JsonClient;
import org.talend.daikon.properties.ValidationResultMutable;

/**
 * @author Zoltán Takács
 */
public class ValidatedSoSSandboxRuntime {

	public ValidatedSoSSandboxRuntime(
		JsonClient jsonClient,
		ValidationResultMutable validationResultMutable) {

		if (validationResultMutable == null) {
			throw new NullPointerException("Validation result mutable is null");
		}

		_jsonClient = jsonClient;
		_validationResultMutable = validationResultMutable;
	}

	public JsonClient getJsonClient() {
		return _jsonClient;
	}

	public ValidationResultMutable getValidationResultMutable() {
		return _validationResultMutable;
	}

	private final JsonClient _jsonClient;
	private final ValidationResultMutable _validationResultMutable;

}