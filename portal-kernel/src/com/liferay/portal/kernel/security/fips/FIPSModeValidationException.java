/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.fips;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Comparator;
import java.util.List;

/**
 * @author Jorge García Jiménez
 */
public class FIPSModeValidationException extends SecurityException {

	@Override
	public String getMessage() {
		if (_validationErrors == null) {
			return super.getMessage();
		}

		StringBundler sb = new StringBundler(
			(_validationErrors.size() * 5) + 1);

		sb.append("The following FIPS boot blockers were found:\n");

		List<ValidationError> validationErrors = ListUtil.sort(
			ListUtil.copy(_validationErrors),
			Comparator.comparing(ValidationError::getValidationErrorType));

		for (ValidationError validationError : validationErrors) {
			sb.append(StringPool.TAB);

			ValidationErrorType validationErrorType =
				validationError.getValidationErrorType();

			sb.append(validationErrorType.getLabel());

			sb.append(": ");
			sb.append(validationError.getErrorMessage());
			sb.append(StringPool.NEW_LINE);
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

	public List<ValidationError> getValidationErrors() {
		return _validationErrors;
	}

	public void setValidationErrors(List<ValidationError> validationErrors) {
		_validationErrors = validationErrors;

		for (ValidationError validationError : validationErrors) {
			addSuppressed(validationError.getSecurityException());
		}
	}

	public static class ValidationError {

		public ValidationError(
			SecurityException securityException,
			ValidationErrorType validationErrorType) {

			_securityException = securityException;
			_validationErrorType = validationErrorType;
		}

		public String getErrorMessage() {
			return _securityException.getMessage();
		}

		public SecurityException getSecurityException() {
			return _securityException;
		}

		public ValidationErrorType getValidationErrorType() {
			return _validationErrorType;
		}

		private final SecurityException _securityException;
		private final ValidationErrorType _validationErrorType;

	}

	public enum ValidationErrorType {

		CLUSTER_LINK("Cluster Link"),
		ENCRYPTION_ALGORITHMS("Encryption Algorithms"),
		KEY_ENCRYPTION_KEY("Key Encryption Key"), PASSWORDS("Passwords"),
		SECRETS_IN_PROPERTIES_FILES("Secrets in Properties Files"),
		SECURITY_PROVIDERS("Security Providers"),
		SESSION_TIMEOUT("Session Timeout"),
		TLS_AND_CERTIFICATE_REVOCATION("TLS and Certificate Revocation");

		public String getLabel() {
			return _label;
		}

		private ValidationErrorType(String label) {
			_label = label;
		}

		private final String _label;

	}

	private List<ValidationError> _validationErrors;

}