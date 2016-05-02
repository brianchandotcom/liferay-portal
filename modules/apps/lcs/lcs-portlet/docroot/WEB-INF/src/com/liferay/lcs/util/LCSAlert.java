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
 */
public enum LCSAlert {

	ERROR_ENVIRONMENT_MISMATCH(
		"error",
		"the-automatic-activation-token-file-does-not-match-the-environment"),
	ERROR_INVALID_ENVIRONMENT_TYPE(
		"error",
		"the-environment-that-the-portal-is-registered-to-has-the-wrong-type"),
	ERROR_INVALID_TOKEN(
		"error", "the-automatic-activation-token-file-is-invalid"),
	SUCCESS_VALID_TOKEN(
		"success", "the-automatic-activation-token-file-is-valid"),
	WARNING_MISSING_TOKEN(
		"warning", "the-automatic-activation-token-file-is-not-present"),
	WARNING_MULTIPLE_TOKENS(
		"warning", "more-than-one-automatic-activation-token-file-is-present"),
	WARNING_TOKEN_MISMATCH(
		"warning",
		"the-automatic-activation-token-file-does-not-match-the-cached-value");

	public String getCSSClass() {
		return "alert alert-" + getType() + " lcs-alert";
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

	private String _label;
	private String _type;

}