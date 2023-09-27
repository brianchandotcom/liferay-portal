/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.web.internal.exception;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Stian Sigvartsen
 */
public class UserAttributeMappingException extends PortalException {

	public UserAttributeMappingException(String msg) {
		super(msg);
	}

	public UserAttributeMappingException(
		String prefix, String fieldExpression, String attributeName,
		ErrorType errorType) {

		_prefix = prefix;
		_fieldExpression = fieldExpression;
		_attributeName = attributeName;
		_errorType = errorType;
	}

	public UserAttributeMappingException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public UserAttributeMappingException(Throwable throwable) {
		super(throwable);
	}

	public String getAttributeName() {
		return _attributeName;
	}

	public ErrorType getErrorType() {
		return _errorType;
	}

	public String getFieldExpression() {
		return _fieldExpression;
	}

	public String getPrefix() {
		return _prefix;
	}

	public enum ErrorType {

		DUPLICATE_FIELD_EXPRESSION, INVALID_MAPPING, SAML_ATTRIBUTE_EXPRESSION

	}

	private String _attributeName = StringPool.BLANK;
	private ErrorType _errorType;
	private String _fieldExpression = StringPool.BLANK;
	private String _prefix = StringPool.BLANK;

}