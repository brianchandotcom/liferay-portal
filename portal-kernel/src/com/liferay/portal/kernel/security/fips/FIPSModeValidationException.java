/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.fips;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jorge García Jiménez
 */
public class FIPSModeValidationException extends SecurityException {

	@Override
	public String getMessage() {
		if (_validationErrors == null) {
			return super.getMessage();
		}

		List<String> lines = new ArrayList<>();

		lines.add("The configuration is not allowed in FIPS mode.");

		Map<ValidationErrorType, List<String>> errorMessagesMap = new EnumMap<>(
			ValidationErrorType.class);

		for (ValidationError validationError : _validationErrors) {
			List<String> errorMessages = errorMessagesMap.computeIfAbsent(
				validationError.getValidationErrorType(),
				validationErrorType -> new ArrayList<>());

			errorMessages.add(validationError.getErrorMessage());
		}

		for (Map.Entry<ValidationErrorType, List<String>> entry :
				errorMessagesMap.entrySet()) {

			lines.add(StringPool.BLANK);

			ValidationErrorType validationErrorType = entry.getKey();

			lines.add(validationErrorType.getLabel());

			for (String errorMessage : entry.getValue()) {
				lines.add("- " + errorMessage);
			}
		}

		return StringUtil.merge(lines, StringPool.NEW_LINE);
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