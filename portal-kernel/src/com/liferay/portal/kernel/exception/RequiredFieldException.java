/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.exception;

/**
 * @author Amos Fong
 */
public class RequiredFieldException extends PortalException {

	public RequiredFieldException(String requiredField, String labelId) {
		_requiredField = requiredField;
		_labelId = labelId;
	}

	public String getLabelId() {
		return _labelId;
	}

	public String getRequiredField() {
		return _requiredField;
	}

	private final String _labelId;
	private final String _requiredField;

}