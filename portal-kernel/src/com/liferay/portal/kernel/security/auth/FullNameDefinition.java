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

package com.liferay.portal.kernel.security.auth;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jorge Ferrer
 */
public class FullNameDefinition {

	public void addFullNameField(FullNameField fullNameField) {
		_fullNameFields.add(fullNameField);
	}

	public void addRequiredField(String requiredFields) {
		_requiredFields.add(requiredFields);
	}

	public List<FullNameField> getFullNameFields() {
		return _fullNameFields;
	}

	public boolean isFieldRequired(String fieldName) {
		return _requiredFields.contains(fieldName);
	}

	private final List<FullNameField> _fullNameFields = new ArrayList<>();
	private final List<String> _requiredFields = new ArrayList<>();

}