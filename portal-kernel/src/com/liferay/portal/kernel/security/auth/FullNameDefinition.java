/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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