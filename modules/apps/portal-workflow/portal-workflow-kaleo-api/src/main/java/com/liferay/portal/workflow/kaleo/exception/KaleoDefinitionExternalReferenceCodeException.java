/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.exception;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.Arrays;
import java.util.List;

/**
 * @author Jhosseph Gonzalez
 */
public class KaleoDefinitionExternalReferenceCodeException
	extends PortalException {

	public List<Object> getArguments() {
		return _arguments;
	}

	public String getMessageKey() {
		return _messageKey;
	}

	public static class MustBeLessThan75Characters
		extends KaleoDefinitionExternalReferenceCodeException {

		public MustBeLessThan75Characters() {
			super(
				Arrays.asList(75, "external-reference-code"),
				"External reference code must be less than 75 characters",
				"only-x-characters-are-allowed-in-the-x-field");
		}

	}

	private KaleoDefinitionExternalReferenceCodeException(
		List<Object> arguments, String message, String messageKey) {

		super(message);

		_arguments = arguments;
		_messageKey = messageKey;
	}

	private KaleoDefinitionExternalReferenceCodeException(
		String message, String messageKey) {

		super(message);

		_messageKey = messageKey;
	}

	private List<Object> _arguments;
	private final String _messageKey;

}