/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
		String prefix, String fieldExpression, String samlAttribute,
		ErrorType errorType) {

		_prefix = prefix;
		_fieldExpression = fieldExpression;
		_samlAttribute = samlAttribute;
		_errorType = errorType;
	}

	public UserAttributeMappingException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public UserAttributeMappingException(Throwable throwable) {
		super(throwable);
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

	public String getSamlAttribute() {
		return _samlAttribute;
	}

	public enum ErrorType {

		DUPLICATE_FIELD_EXPRESSION, INVALID_MAPPING, SAML_ATTRIBUTE_EXPRESSION

	}

	private ErrorType _errorType;
	private String _fieldExpression = StringPool.BLANK;
	private String _prefix = StringPool.BLANK;
	private String _samlAttribute = StringPool.BLANK;

}