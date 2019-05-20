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

import org.talend.daikon.properties.ValidationResultMutable;

/**
 * @author Zoltán Takács
 */
public class ValidatedSoSSandboxRuntime {

	public ValidatedSoSSandboxRuntime(
		LiferaySourceOrSinkRuntime liferaySourceOrSinkRuntime,
		ValidationResultMutable validationResultMutable) {

		if (validationResultMutable == null) {
			throw new NullPointerException(
				"validationResultMutable must not be null");
		}

		_liferaySourceOrSinkRuntime = liferaySourceOrSinkRuntime;
		_validationResultMutable = validationResultMutable;
	}

	public LiferaySourceOrSinkRuntime getLiferaySourceOrSinkRuntime() {
		return _liferaySourceOrSinkRuntime;
	}

	public ValidationResultMutable getValidationResultMutable() {
		return _validationResultMutable;
	}

	private final LiferaySourceOrSinkRuntime _liferaySourceOrSinkRuntime;
	private final ValidationResultMutable _validationResultMutable;

}