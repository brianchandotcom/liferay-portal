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

package com.liferay.lcs.util;

/**
 * @author Igor Beslic
 * @author Marko Cikos
 */
public enum LCSAlert {

	ERROR_ENVIRONMENT_MISMATCH(
		"danger",
		"the-automatic-activation-token-file-does-not-match-the-environment"),
	ERROR_INVALID_ENVIRONMENT_TYPE(
		"danger",
		"this-server-is-registered-to-the-environment-of-the-wrong-type"),
	ERROR_INVALID_TOKEN(
		"danger", "the-automatic-activation-token-file-is-invalid"),
	ERROR_MISSING_TOKEN(
		"danger", "the-automatic-activation-token-file-is-not-present"),
	ERROR_MULTIPLE_TOKENS(
		"danger", "more-than-one-automatic-activation-token-file-is-present"),
	SUCCESS_CONNECTION_TO_LCS_VALID("success", "connection-to-lcs-is-valid"),
	SUCCESS_VALID_TOKEN(
		"success", "the-automatic-activation-token-file-is-valid"),
	WARNING_HANDSHAKE_EXPIRED(
		"warning", "the-connection-to-liferay-connected-services-has-expired");

	public String getCSSClass() {
		return "alert alert-" + getType();
	}

	public String getLabel() {
		return _label;
	}

	public String getType() {
		return _type;
	}

	private LCSAlert(String type, String label) {
		_type = type;
		_label = label;
	}

	private final String _label;
	private final String _type;

}